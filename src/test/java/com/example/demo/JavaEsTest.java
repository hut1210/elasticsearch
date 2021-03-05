package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.constant.ReportConstant;
import com.example.demo.domain.Option;
import com.example.demo.dto.CommonDeliveryOverviewDto;
import com.example.demo.dto.NetWorkDto;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.junit.Before;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;


/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/23 11:22
 */
public class JavaEsTest {

    private String IP;
    private int PORT;

    @Before
    public void init(){
        this.IP = "192.168.？.？";
        this.PORT = 9300;
    }
    public void esClient(){

    }

    public static void main(String[] args) {
        for (int i = 0; i < 13; i++) {
            String hour = new StringBuilder(String.valueOf(i)).toString();
            if (i < 10) {
                hour = new StringBuilder("0").append(i).toString();
            }
            System.out.println(hour);
        }

        List<NetWorkDto> list = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            list.add(NetWorkDto.builder()
                    .networkArea("阿里地区")
                    .netWorkName("营业部"+i)
                    .build());
        }
        list.add(NetWorkDto.builder()
                .networkArea("阿里地区1")
                .netWorkName("营业部")
                .build());

        list.forEach(System.out::println);

        String str = "[{\"netWorkCode\":\"1455600\",\"netWorkName\":\"拉萨纳金营业部\",\"networkArea\":\"拉萨市\"}," +
                "{\"netWorkCode\":\"1435954\",\"netWorkName\":\"那曲色尼营业部\",\"networkArea\":\"那曲地区\"}," +
                "{\"netWorkCode\":\"1435924\",\"netWorkName\":\"昌都卡若营业部\",\"networkArea\":\"昌都地区\"}," +
                "{\"netWorkCode\":\"715146\",\"netWorkName\":\"拉萨柳梧新区营业部\",\"networkArea\":\"拉萨市\"}," +
                "{\"netWorkCode\":\"572997\",\"netWorkName\":\"日喀则营业部\",\"networkArea\":\"日喀则地区\"}," +
                "{\"netWorkCode\":\"477079\",\"netWorkName\":\"拉萨东城营业部\",\"networkArea\":\"拉萨市\"}," +
                "{\"netWorkCode\":\"287973\",\"netWorkName\":\"林芝营业部\",\"networkArea\":\"林芝市\"}," +
                "{\"netWorkCode\":\"4331\",\"netWorkName\":\"拉萨堆龙营业部\",\"networkArea\":\"拉萨市\"}," +
                "{\"netWorkCode\":\"999\",\"netWorkName\":\"拉萨赛马场营业部\",\"networkArea\":\"拉萨市\"}," +
                "{\"netWorkCode\":\"1435974\",\"netWorkName\":\"阿里改则共配站\",\"networkArea\":\"阿里地区\"}," +
                "{\"netWorkCode\":\"1435972\",\"netWorkName\":\"阿里革吉共配站\",\"networkArea\":\"阿里地区\"}," +
                "{\"netWorkCode\":\"1435970\",\"netWorkName\":\"阿里日土共配站\",\"networkArea\":\"阿里地区\"}," +
                "{\"netWorkCode\":\"1435968\",\"netWorkName\":\"阿里噶尔共配站\",\"networkArea\":\"阿里地区\"}," +
                "{\"netWorkCode\":\"1435966\",\"netWorkName\":\"那曲双湖共配站\",\"networkArea\":\"那曲地区\"}," +
                "{\"netWorkCode\":\"1435964\",\"netWorkName\":\"那曲尼玛共配站\",\"networkArea\":\"那曲地区\"}," +
                "{\"netWorkCode\":\"1435962\",\"netWorkName\":\"那曲巴青共配站\",\"networkArea\":\"那曲地区\"}," +
                "{\"netWorkCode\":\"1435960\",\"netWorkName\":\"那曲申扎共配站\",\"networkArea\":\"那曲地区\"}," +
                "{\"netWorkCode\":\"1435958\",\"netWorkName\":\"那曲安多共配站\",\"networkArea\":\"那曲地区\"}," +
                "{\"netWorkCode\":\"1435956\",\"netWorkName\":\"那曲聂荣共配站\",\"networkArea\":\"那曲地区\"}," +
                "{\"netWorkCode\":\"1435954\",\"netWorkName\":\"那曲色尼共配站\",\"networkArea\":\"那曲地区\"}," +
                "{\"netWorkCode\":\"1435952\",\"netWorkName\":\"山南洛扎共配站\",\"networkArea\":\"山南地区\"}," +
                "{\"netWorkCode\":\"1435950\",\"netWorkName\":\"山南措美共配站\",\"networkArea\":\"山南地区\"}," +
                "{\"netWorkCode\":\"1435948\",\"netWorkName\":\"山南曲松共配站\",\"networkArea\":\"山南地区\"}," +
                "{\"netWorkCode\":\"1435946\",\"netWorkName\":\"山南琼结共配站\",\"networkArea\":\"山南地区\"}," +
                "{\"netWorkCode\":\"1435944\",\"netWorkName\":\"山南桑日共配站\",\"networkArea\":\"山南地区\"}," +
                "{\"netWorkCode\":\"1435942\",\"netWorkName\":\"山南扎囊共配站\",\"networkArea\":\"山南地区\"}," +
                "{\"netWorkCode\":\"1435940\",\"netWorkName\":\"林芝察隅共配站\",\"networkArea\":\"林芝市\"}," +
                "{\"netWorkCode\":\"1435938\",\"netWorkName\":\"昌都洛隆共配站\",\"networkArea\":\"昌都地区\"}," +
                "{\"netWorkCode\":\"1435936\",\"netWorkName\":\"昌都左贡共配站\",\"networkArea\":\"昌都地区\"}," +
                "{\"netWorkCode\":\"1435934\",\"netWorkName\":\"昌都察雅共配站\",\"networkArea\":\"昌都地区\"}," +
                "{\"netWorkCode\":\"1435932\",\"netWorkName\":\"昌都丁青共配站\",\"networkArea\":\"昌都地区\"}," +
                "{\"netWorkCode\":\"1435930\",\"netWorkName\":\"昌都类乌齐共配站\",\"networkArea\":\"昌都地区\"}," +
                "{\"netWorkCode\":\"1435928\",\"netWorkName\":\"昌都贡觉共配站\",\"networkArea\":\"昌都地区\"}," +
                "{\"netWorkCode\":\"1435926\",\"netWorkName\":\"昌都江达共配站\",\"networkArea\":\"昌都地区\"}," +
                "{\"netWorkCode\":\"1435922\",\"netWorkName\":\"日喀则岗巴共配站\",\"networkArea\":\"日喀则地区\"}," +
                "{\"netWorkCode\":\"1435920\",\"netWorkName\":\"日喀则萨嘎共配站\",\"networkArea\":\"日喀则地区\"}," +
                "{\"netWorkCode\":\"1435914\",\"netWorkName\":\"日喀则吉隆共配站\",\"networkArea\":\"日喀则地区\"}," +
                "{\"netWorkCode\":\"1435912\",\"netWorkName\":\"日喀则仲巴共配站\",\"networkArea\":\"日喀则地区\"}," +
                "{\"netWorkCode\":\"1435910\",\"netWorkName\":\"日喀则定结共配站\",\"networkArea\":\"日喀则地区\"},{" +
                "\"netWorkCode\":\"1435908\",\"netWorkName\":\"日喀则康马共配站\",\"networkArea\":\"日喀则地区\"}," +
                "{\"netWorkCode\":\"1435906\",\"netWorkName\":\"日喀则仁布共配站\",\"networkArea\":\"日喀则地区\"}," +
                "{\"netWorkCode\":\"1435904\",\"netWorkName\":\"日喀则谢通门共配站\",\"networkArea\":\"日喀则地区\"}," +
                "{\"netWorkCode\":\"1434778\",\"netWorkName\":\"日喀则昂仁共配站\",\"networkArea\":\"日喀则地区\"}," +
                "{\"netWorkCode\":\"1434776\",\"netWorkName\":\"日喀则拉孜共配站\",\"networkArea\":\"日喀则地区\"}," +
                "{\"netWorkCode\":\"1434774\",\"netWorkName\":\"日喀则萨迦共配站\",\"networkArea\":\"日喀则地区\"}," +
                "{\"netWorkCode\":\"1434772\",\"netWorkName\":\"日喀则定日共配站\",\"networkArea\":\"日喀则地区\"}," +
                "{\"netWorkCode\":\"1434770\",\"netWorkName\":\"日喀则南木林共配站\",\"networkArea\":\"日喀则地区\"}," +
                "{\"netWorkCode\":\"1434768\",\"netWorkName\":\"拉萨墨竹工卡共配站\",\"networkArea\":\"拉萨市\"}," +
                "{\"netWorkCode\":\"1434766\",\"netWorkName\":\"拉萨当雄共配站\",\"networkArea\":\"拉萨市\"}," +
                "{\"netWorkCode\":\"1434764\",\"netWorkName\":\"拉萨林周共配站\",\"networkArea\":\"拉萨市\"}," +
                "{\"netWorkCode\":\"1434762\",\"netWorkName\":\"拉萨达孜共配站\",\"networkArea\":\"拉萨市\"}]";

        JSONArray jsonArray = JSONArray.parseArray(str);
        List<NetWorkDto> list1 = new ArrayList<>();
        list1 = JSONArray.parseArray(str).toJavaList(NetWorkDto.class);
        System.out.println(list1.size());
        Map<String,String> map = new HashMap<>();
        if(!StringUtils.isEmpty(str)) {
            list = JSONArray.parseArray(str).toJavaList(NetWorkDto.class);
            //所有西藏网点
            //map=list.stream().collect(Collectors.toMap(NetWorkDto::getNetWorkCode, NetWorkDto::getNetWorkName));
            map = list.stream().filter(distinByKey(x->x.getNetWorkCode())).collect(toMap(x->x.getNetWorkCode(),x->x.getNetWorkName()));

            List<Option> options1 = map.entrySet()
                    .stream()
                    .map(e -> {
                        Option option = new Option();
                        option.setCode(e.getKey());
                        option.setName(e.getValue());
                        return option;
                    })
                    .collect(Collectors.toList());
            //网点所属区域
            List<String> networkAreaList = list.stream().map(NetWorkDto::getNetworkArea).distinct().collect(Collectors.toList());

            System.out.println(map.toString());
            System.out.println(networkAreaList.size());
            networkAreaList.forEach(System.out::println);
            options1.forEach(item->{
                System.out.println(item.getCode()+"  "+item.getName());
            });

            TreeMap hourMap = new TreeMap();
            List<String> hourList = ReportConstant.hourList;
            for (int i=0;i<hourList.size();i++){
                hourMap.put(Integer.parseInt(hourList.get(i).trim()),hourList.get(i));
            }
            System.out.println(JSON.toJSONString(hourMap));
        }

    }



    private static <T> Predicate<T> distinByKey(Function<? super T, ?> handle){
        ConcurrentHashMap ch = new ConcurrentHashMap();
        return rs->ch.putIfAbsent(handle.apply(rs),Boolean.TRUE)==null;
    }

}