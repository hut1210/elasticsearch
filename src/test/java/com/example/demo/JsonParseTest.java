package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.dto.CommonDeliveryDto;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/22 15:19
 */
public class JsonParseTest {

    public static void main(String[] args) {
        String str = "[{\"code\":\"999\",\"num\":17784,\"latitude\":\"29.673571000\",\"name\":\"拉萨赛马场营业部\",\"longitude\":\"91.129837000\"},{\"code\":\"4331\",\"num\":11997,\"latitude\":\"29.632479000\",\"name\":\"拉萨堆龙营业部\",\"longitude\":\"90.977040000\"},{\"code\":\"287973\",\"num\":3921,\"latitude\":\"29.664696000\",\"name\":\"林芝营业部\",\"longitude\":\"94.370758000\"},{\"code\":\"715146\",\"num\":3730,\"latitude\":\"29.624166029\",\"name\":\"拉萨柳梧新区营业部\",\"longitude\":\"91.082825813\"},{\"code\":\"572997\",\"num\":3669,\"latitude\":\"29.289902000\",\"name\":\"日喀则营业部\",\"longitude\":\"88.876259000\"}]";
        List<Map> strList = JSONArray.parseArray(str).toJavaList(Map.class);

        List<Object> list2 = JSON.parseArray(str);
        List<Map<String,Object>> listw = new ArrayList<Map<String,Object>>();
        for (Object object : list2){
            Map<String,Object> ageMap = new HashMap<String,Object>();
            Map <String,Object> ret = (Map<String, Object>) object;//取出list里面的值转为map
            listw.add(ret);
        }
        listw.forEach(System.out::println);

        /*//自定义比较器
        Comparator<Map.Entry<String, Long>> valCmp = new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return (int) (o2.getValue() - o1.getValue());  // 降序排序，如果想升序就反过来
            }
        };
        List<Map.Entry<String, Object>> list = new ArrayList<Map.Entry<String, Object>>();
        Collections.sort(listw, valCmp);*/

        List<Map> list = strList.stream().sorted(Comparator.comparingInt(e -> (Integer) (e.get("num")))).collect(Collectors.toList());
        //list.forEach(System.out::println);


        if (listw != null && listw .size() > 1) {
            Collections.sort(listw , new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Integer o1Value = Integer.valueOf(o1.get("num").toString());
                    Integer o2Value = Integer.valueOf(o2.get("num").toString());
                    return o2Value.compareTo(o1Value);
                }
            });
        }

        listw.forEach(System.out::println);

    }
}
