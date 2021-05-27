package com.example.demo.xz;

import com.alibaba.fastjson.JSON;
import com.example.demo.builder.QueryBuilder;
import com.example.demo.builder.helper.AggregationHelper;
import com.example.demo.condition.TermsAggregationCondition;
import com.example.demo.condition.WarehouseCondition;
import com.example.demo.service.EsQueryService;
import com.example.demo.util.ReportUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/5/18 20:22
 */
@SpringBootTest
@Slf4j
public class StockTest {

    @Resource
    private EsQueryService esQueryService;

    private String wmsStockIndex = "wms_stock_index";

    private String wmsStockType = "_doc";

    @Test
    public void test() {
        Map<String, Long> map = new HashMap<>();
        TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("warehouse_no");
        SearchSourceBuilder ssb = QueryBuilder.buildGroup(new WarehouseCondition(), termsAggregationCondition);
        log.info("CommonDistributionManager.getGoodsStock ssb = {}", ssb);
        SearchResponse sr = esQueryService.queryByIndexAndSourceBuilder(wmsStockIndex, wmsStockType, ssb);
        Map<String, Map<String, String>> stringMapMap = ReportUtils.analySearchResponse2Map(sr, termsAggregationCondition);
        System.out.println(stringMapMap);
        for (String key : stringMapMap.keySet()) {
            Map<String, String> map1 = stringMapMap.get(key);
            System.out.println(map1 + "    " + map1.get("warehouse_no") + "    " + map1.get("warehouse_no_doc_count"));
        }
    }
    @Test
    public void test1(){
        List<String> warehouseNoList = new ArrayList<>();
        warehouseNoList.add("12347");
        warehouseNoList.add("123456");
        BigDecimal stockAmount = BigDecimal.ZERO;
        for (int i = 0; i < warehouseNoList.size(); i++) {
            WarehouseCondition warehouseCondition = new WarehouseCondition();
            warehouseCondition.setWarehouseNo(warehouseNoList.get(i));
            TermsAggregationCondition termsAggregationCondition = new TermsAggregationCondition("warehouse_no");
            termsAggregationCondition.sum("sumQty", "qty");
            SearchSourceBuilder ssb = QueryBuilder.buildGroup(warehouseCondition, termsAggregationCondition);
            log.info("CommonDistributionManager getSiteData ---- ssb = {}", ssb);
            //查询es内容
            SearchResponse sr = esQueryService.queryByIndexAndSourceBuilder(wmsStockIndex, wmsStockType, ssb);
            Map<String, Map<String, String>> stringMap = ReportUtils.analySearchResponse2Map(sr, termsAggregationCondition);
            BigDecimal tempStockAmount = BigDecimal.ZERO;
            if (stringMap != null) {
                for (String key : stringMap.keySet()) {
                    Map<String, String> map = stringMap.get(key);
                    System.out.println(map);
                    BigDecimal amount = map.get("warehouse_no_sumQty") != null ? new BigDecimal(map.get("warehouse_no_sumQty")) : BigDecimal.ZERO;
                    tempStockAmount = tempStockAmount.add(amount);
                }
            }
            stockAmount = stockAmount.add(tempStockAmount);
        }
        System.out.println(stockAmount);
    }

    @Test
    public void test2() {
        Map<String, Map<String, String>> map = new HashMap<>();
        Map<String, String> temp1 = new HashMap<>();
        temp1.put("sortingCenterAmount", "0");
        temp1.put("warehouseAmount", "0");
        temp1.put("siteAmount", "0");
        temp1.put("stockAmount", "0");
        temp1.put("waybillAmount", "0");

        map.put("拉萨市", temp1);

        Map<String, String> temp2 = new HashMap<>();
        temp2.put("sortingCenterAmount", "0");
        temp2.put("warehouseAmount", "0");
        temp2.put("siteAmount", "0");
        temp2.put("stockAmount", "0");
        temp2.put("waybillAmount", "0");

        map.put("日喀则地区", temp2);

        System.out.println(JSON.toJSONString(map));

    }

    @Test
    public void test3() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取前月的第一天
        for (int i = 0; i < 6; i++) {
            System.out.println("-----------i:" + i);
            Calendar cal_1 = Calendar.getInstance();//获取当前日期
            cal_1.add(Calendar.MONTH, i - 5);
            cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
            String firstDay = format.format(cal_1.getTime());
            System.out.println("-----------firstDay:" + firstDay);
            //获取前月的最后一天
            Calendar cale = Calendar.getInstance();
            cale.add(Calendar.MONTH, i - 4);//设置为1号,当前日期既为本月第一天
            cale.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
            String lastDay = format.format(cale.getTime());
            System.out.println("-----------lastDay:" + lastDay);
        }

        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH,  - 5);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String firstDay = format.format(cal_1.getTime());

        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);//设置为1号,当前日期既为本月第一天
        cale.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
        String lastDay = format.format(cale.getTime());
        System.out.println(firstDay+"-----------:" + lastDay);

        String substring = "2020-12-01".substring(0, 7);
        System.out.println(substring);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            //获取当前日期
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, i - 5);
            //设置为1号,当前日期既为本月第一天
            cal.set(Calendar.DAY_OF_MONTH, 1);
            String day = format.format(cal.getTime());
            list.add(day.substring(0,7));
        }
        list.forEach(System.out::println);

    }

    @Test
    public void test4() {
        Map<String, Long> map1 = new HashMap<String, Long>();
        map1.put("one", 1L);
        map1.put("two", 2L);
        map1.put("three", 3L);

        Map<String, Long> map2 = new HashMap<String, Long>();
        map2.put("ten", 10L);
        map2.put("nine", 9L);
        map2.put("eight", 8L);
        map2.put("one", 8L);

        /*// 合并
        Map<String, String> combineResultMap = new HashMap<String, String>();
        combineResultMap.putAll(map1);
        combineResultMap.putAll(map2);

        // 合并后打印出所有内容
        for (Map.Entry<String, String> entry : combineResultMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }*/

        map1.forEach((key, value) -> map2.merge(key, value, Long::sum));
        //循环打印map
        map2.forEach((key, value) -> {
            System.out.println(key + ":" + value);
        });
    }

    @Test
    public void test5() {
        Map<String, List<Map<String, Long>>> map1 = new HashMap<>();

        for (int i = 0; i < 3; i++) {
            List<Map<String, Long>> mapList = new ArrayList<>();
            Map<String, Long> tempMap = new HashMap<>();
            tempMap.put("运单数量", 1L);
            tempMap.put("库存数量", 1L);
            tempMap.put("站点数量", 1L);
            mapList.add(tempMap);
            map1.put("拉萨市", mapList);
        }
        for (int i = 0; i < 3; i++) {
            List<Map<String, Long>> mapList = new ArrayList<>();
            Map<String, Long> tempMap = new HashMap<>();
            tempMap.put("运单数量", 2L);
            tempMap.put("库存数量", 2L);
            tempMap.put("站点数量", 2L);
            mapList.add(tempMap);
            map1.put("阿里地区", mapList);
        }

        Map<String, List<Map<String, Long>>> map2 = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            List<Map<String, Long>> mapList = new ArrayList<>();
            Map<String, Long> tempMap = new HashMap<>();
            tempMap.put("运单数量", 1L);
            tempMap.put("库存数量", 2L);
            tempMap.put("站点数量", 3L);
            mapList.add(tempMap);
            map2.put("拉萨市", mapList);
        }

        System.out.println(map1 + "   " + map2);
        for (String str1 : map1.keySet()) {
            if (map2.containsKey(str1)) {
                //两个map，key一样合并value
                List<Map<String, Long>> mapList1 = map1.get(str1);
                List<Map<String, Long>> mapList2 = map2.get(str1);
                List<Map<String, Long>> mapList3 = new ArrayList<>();
                if (mapList1.size() > 0 && mapList2.size() > 0) {
                    Map<String, Long> map3 = mapList1.get(0);
                    Map<String, Long> map4 = mapList2.get(0);

                    map3.forEach((key, value) -> map4.merge(key, value, Long::sum));
                    //循环打印map
                    map4.forEach((key, value) -> {
                        System.out.println(key + ":" + value);
                    });
                    mapList3.add(map4);
                }
                map2.put(str1, mapList3);
            } else {
                map2.put(str1, map1.get(str1));
            }
        }
        System.out.println(map2);
    }

    public static List<Map<String, Object>> merge(List<Map<String, Object>> m1, List<Map<String, Object>> m2, String string) {
        m1.addAll(m2);
        Set<String> set = new HashSet<>();
        return m1.stream().collect(Collectors.groupingBy(o -> {
            //暂存所有key
            set.addAll(o.keySet());
            return o.get(string);
        })).entrySet().stream().map(o -> {
            //合并
            Map<String, Object> map = o.getValue().stream().flatMap(m -> {
                return m.entrySet().stream();
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b));
            //为没有key的赋值0
            set.stream().forEach(k -> {
                if (!map.containsKey(k))
                    map.put(k, 0);
            });
            return map;
        }).collect(Collectors.toList());

    }

    public static List<Map<String, Object>> merge(List<Map<String, Object>> m1, List<Map<String, Object>> m2) {

        m1.addAll(m2);

        Set<String> set = new HashSet<>();

        return m1.stream()
                .collect(Collectors.groupingBy(o -> {
                    //暂存所有key
                    set.addAll(o.keySet());
                    //按a_id分组
                    return o.get("a_id");
                })).entrySet().stream().map(o -> {

                    //合并
                    Map<String, Object> map = o.getValue().stream().flatMap(m -> {
                        return m.entrySet().stream();
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b));

                    //为没有的key赋值0
                    set.stream().forEach(k -> {
                        if (!map.containsKey(k)) map.put(k, 0);
                    });

                    return map;
                }).collect(Collectors.toList());
    }

    @Test
    public void test6() throws ParseException {
        String time = "2021-05-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date = sdf.parse(time);

        calendar.setTime(date);
        int day=calendar.get(Calendar.DATE);
        //此处修改为+1则是获取后一天，-1则是获取前一天
        calendar.set(Calendar.DATE,day-1);
        String lastDay = sdf.format(calendar.getTime());
        System.out.println(lastDay);
    }
}
