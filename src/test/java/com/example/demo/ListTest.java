package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.condition.CommonDeliveryCondition;
import com.example.demo.dto.CommonDeliveryOverviewDto;
import com.example.demo.dto.IndexOverviewDto;
import com.example.demo.dto.NetWorkDto;
import com.example.demo.dto.PieChatDto;
import com.example.demo.enums.CommonDeliveryEnum;
import com.example.demo.enums.SourceEnum;
import com.example.demo.util.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/4 15:01
 */
public class ListTest {

    public static void main(String[] args) {
        List<CommonDeliveryOverviewDto> list = new ArrayList<>();
        int totalSize = 12;
        System.out.println(list.size());
        for (int i = 0; i < totalSize; i++) {
            list.add(CommonDeliveryOverviewDto.builder()
                    .networkName("西藏网点"+i)
                    .bizType(String.valueOf(i))
                    .waybillAmount("1000")
                    .distributionAmount("200")
                    .signedInAmount("300")
                    .cancelAmount("10")
                    .rejectionAmount("20")
                    .deliveryCompletionRate("80%")
                    .avgDistributionDuration(2)
                    .build());
        }
        System.out.println(list.size());
        list.forEach(System.out::println);

        System.out.println(defaultCommonDeliveryCondition(new CommonDeliveryCondition()));
        Map responseMap = new HashMap();
        //概览数据集合
        responseMap.put("distributionList",doGetIndexOverview(new CommonDeliveryCondition()));
        System.out.println(JSONObject.toJSONString(responseMap));

        System.out.println(JSONObject.toJSONString(getIndexOverview2(new CommonDeliveryCondition())));

        System.out.println(JSONObject.toJSONString(doGetCommonDeliveryLineChart(new CommonDeliveryCondition(),"")));

        List<String> names= list.stream().map(CommonDeliveryOverviewDto::getNetworkName).collect(Collectors.toList());
        names.forEach(System.out::println);

    }

    private static CommonDeliveryCondition defaultCommonDeliveryCondition(CommonDeliveryCondition commonDeliveryCondition){
        if (StringUtils.isEmpty(commonDeliveryCondition.getCreateTimeStart())) {
            //commonDeliveryCondition.setCreateTimeStart(DateUtils.formatDate(DateUtils.getDateForBegin(new Date(), -8), DateUtils.DATETIME_FORMAT));
        }
        if (StringUtils.isEmpty(commonDeliveryCondition.getCreateTimeEnd())) {
            //commonDeliveryCondition.setCreateTimeEnd(DateUtils.formatDate(DateUtils.getDateForEnd(new Date(), -1), DateUtils.DATETIME_FORMAT));
        }

        return commonDeliveryCondition;
    }

    /**
     * 获取概览数据集合
     * @param commonDeliveryCondition
     * @return
     */
    private static List doGetIndexOverview(CommonDeliveryCondition commonDeliveryCondition){
        List<Map> distributionList = new ArrayList<>();
        //运单总量
        //TODO 获取运单总量
        String totalAmount = "1000";
        IndexOverviewDto totalAmountDto = IndexOverviewDto.builder()
                .title("运单总量")
                .num(totalAmount)
                .note("以选择时间条件为基础，统计所有目的网点为西藏网点的运单总量")
                .build();
        distributionList.add(JSONObject.parseObject(JSON.toJSONString(totalAmountDto)));
        for (CommonDeliveryEnum commonDeliveryEnum : CommonDeliveryEnum.values()){
            //TODO 获取其他数量
            String num = String.valueOf(commonDeliveryEnum.getNum());
            String percent = String.valueOf(new BigDecimal(num)
                    .divide(new BigDecimal(totalAmount),4,BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .setScale(2,BigDecimal.ROUND_HALF_UP));
            distributionList.add(JSONObject.parseObject(
                    JSON.toJSONString(IndexOverviewDto.builder()
                            .title(commonDeliveryEnum.getName())
                            .num(num)
                            .note(commonDeliveryEnum.getNote())
                            .percent(percent)
                            .build())));
        }
        return distributionList;
    }

    /**
     * 指标概览
     *
     * @param commonDeliveryCondition
     * @return
     */
    private static Map getIndexOverview2(CommonDeliveryCondition commonDeliveryCondition) {
        Map responseMap = new HashMap();
        //概览数据集合
        responseMap.put("distributionList", doGetIndexOverview(commonDeliveryCondition));
        //运单总量折线图
        responseMap.put("waybillAmountLineChart", doGetWaybillAmountLineChart(commonDeliveryCondition));
        //配送总量折线图
        responseMap.put("distributionAmountLineChart", doGetDistributionAmountLineChart(commonDeliveryCondition));

        //配送状态饼图
        responseMap.put("distributionStatusPieChat", doGetDistributionStatusPieChat(commonDeliveryCondition));
        //业务类型饼图
        responseMap.put("businessTypePieChat", doGetBusinessTypePieChat(commonDeliveryCondition));
        //共配来源饼图
        responseMap.put("commonDistributionSourcePieChat", doGetCommonDistributionSourcePieChat(commonDeliveryCondition));

        return responseMap;
    }

    /**
     * 配送状态饼图
     * @param commonDeliveryCondition
     * @return
     */
    private static Map doGetDistributionStatusPieChat(CommonDeliveryCondition commonDeliveryCondition) {
        Map distributionStatusMap = new HashMap();
        List<Map> distributionStatusList = new ArrayList<>();
        Map map1 = new HashMap();
        map1.put("subTitle", "已签收");
        map1.put("num", 10450);

        Map map2 = new HashMap();
        map2.put("subTitle", "未签收");
        map2.put("num", 34);

        distributionStatusList.add(JSONObject.parseObject(
                JSON.toJSONString(map1)));
        distributionStatusList.add(JSONObject.parseObject(
                JSON.toJSONString(map2)));

        distributionStatusMap.put("title", "配送状态占比");
        distributionStatusMap.put("data", distributionStatusList);
        return distributionStatusMap;
    }

    /**
     * 业务类型饼图
     * @param commonDeliveryCondition
     * @return
     */
    private static Map doGetBusinessTypePieChat(CommonDeliveryCondition commonDeliveryCondition) {
        Map businessTypeMap = new HashMap();
        List<Map> businessTypeList = new ArrayList<>();
        Map map3 = new HashMap();
        map3.put("subTitle", "京东自营");
        map3.put("num", 5945);

        Map map4 = new HashMap();
        map4.put("subTitle", "终端共配");
        map4.put("num", 4055);

        businessTypeList.add(JSONObject.parseObject(
                JSON.toJSONString(map3)));
        businessTypeList.add(JSONObject.parseObject(
                JSON.toJSONString(map4)));
        businessTypeMap.put("title", "业务类型占比");
        businessTypeMap.put("total", 10000);
        businessTypeMap.put("data", businessTypeList);
        return businessTypeMap;
    }

    /**
     * 共配来源饼图
     * @param commonDeliveryCondition
     * @return
     */
    private static Map doGetCommonDistributionSourcePieChat(CommonDeliveryCondition commonDeliveryCondition) {
        Map commonDistributionMap = new HashMap();
        List<Map> commonDistributionList = new ArrayList<>();

        int totalAmount = 1000;
        for (SourceEnum sourceEnum : SourceEnum.values()) {
            String percent = String.valueOf(new BigDecimal(sourceEnum.getNum())
                    .divide(new BigDecimal(totalAmount), 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .setScale(2, BigDecimal.ROUND_HALF_UP));
            commonDistributionList.add(JSONObject.parseObject(
                    JSON.toJSONString(PieChatDto.builder()
                            .sunTitle(sourceEnum.getSubTitle())
                            .num(sourceEnum.getNum())
                            .percent(percent)
                            .build())));
        }
        commonDistributionMap.put("title", "共配来源占比");
        commonDistributionMap.put("data", commonDistributionList);

        return commonDistributionMap;
    }

    private static Map doGetWaybillAmountLineChart(CommonDeliveryCondition commonDeliveryCondition) {
        Map map = new HashMap();
        List xdataList = new ArrayList<>();
        List seriesList = Arrays.asList(new String[]{"2021-02-02", "2021-02-03", "2021-02-04"});
        for (int i = 0; i < seriesList.size(); i++) {
            xdataList.add(i);
        }
        map.put("xdata", xdataList);
        map.put("series", seriesList);
        return map;
    }

    private static Map doGetDistributionAmountLineChart(CommonDeliveryCondition commonDeliveryCondition) {
        Map map = new HashMap();
        List xdataList = new ArrayList<>();
        List seriesList = Arrays.asList(new String[]{"2021-02-02", "2021-02-03", "2021-02-04"});
        for (int i = 0; i < seriesList.size(); i++) {
            xdataList.add(i);
        }
        map.put("xdata", xdataList);
        map.put("series", seriesList);
        return map;
    }

    /**
     * 获取运单总量、配送总量折线图
     *
     * @param commonDeliveryCondition
     * @param column
     * @return
     */
    public static Map doGetCommonDeliveryLineChart(CommonDeliveryCondition commonDeliveryCondition, String column) {
        Map map = new HashMap();
        List xdataList = new ArrayList<>();
        List seriesList = new ArrayList<>();
        Map lineChartMap = new HashMap();
        lineChartMap.put("2021-02-08","10");
        lineChartMap.put("2021-02-09","20");
        lineChartMap.put("2021-02-10","30");
        lineChartMap.keySet().forEach(key -> {
            seriesList.add(key);
            xdataList.add(lineChartMap.get(key));
        });
        map.put("xdata", xdataList);
        map.put("series", seriesList);
        return map;
    }

    @Test
    public void test2(){
        List<NetWorkDto> list = new ArrayList<>();
        list.add(NetWorkDto.builder()
                .netWorkCode("1455600")
                .networkArea("拉萨市")
                .build());
        list.add(NetWorkDto.builder()
                .netWorkCode("1435954")
                .networkArea("那曲地区")
                .build());
        list.add(NetWorkDto.builder()
                .netWorkCode("1435924")
                .networkArea("昌都地区")
                .build());
        list.add(NetWorkDto.builder()
                .netWorkCode("715146")
                .networkArea("拉萨市")
                .build());
        Map<String,List<NetWorkDto>> networkGroup = list.stream().collect(Collectors.groupingBy(NetWorkDto::getNetworkArea));
        System.out.println(networkGroup);
        for (String key:networkGroup.keySet()){
            System.out.println(networkGroup.get(key));
        }
    }
}
