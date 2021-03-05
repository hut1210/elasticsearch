package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.NetWorkDto;
import com.example.demo.dto.OptionExtendDto;
import com.example.demo.dto.WarnRulesDto;
import com.example.demo.enums.WarnTargetEnum;
import com.example.demo.util.DateUtils;
import com.example.demo.util.ReportUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/26 14:42
 */
public class DateUtilsTest {

    public static void main(String[] args) {
        String synDate = "2021-01-26";
        String dateEnd = DateUtils.turnDate(synDate,DateUtils.DATE_FORMAT,-1);
        String dateStart = DateUtils.turnDate(synDate,DateUtils.DATE_FORMAT,-31);
        System.out.println("dateEnd---->"+dateEnd+"------>dateStart---->"+dateStart);

        String dateEnd1 = DateUtils.formatDate(DateUtils.getDateForEnd(DateUtils.parseDate(synDate,DateUtils.DATE_FORMAT),-1),DateUtils.DATETIME_FORMAT);
        String dateStart1 = DateUtils.formatDate(DateUtils.getDateForBegin(DateUtils.parseDate(synDate,DateUtils.DATE_FORMAT),-31),DateUtils.DATETIME_FORMAT);
        System.out.println("dateEnd1---->"+dateEnd1+"------>dateStart1---->"+dateStart1);

        String str = "触发警告，#{WarnTargetEnum}";
        WarnTargetEnum.ORDER_TARGET.setCode(11);
        System.out.println(str.contains("#{WarnTargetEnum}")+"    "+WarnTargetEnum.ORDER_TARGET.getCode());
        List<OptionExtendDto> optionExtendDtoList = new LinkedList<>();
        for(WarnTargetEnum item:WarnTargetEnum.values()){
            OptionExtendDto optionExtendDto = new OptionExtendDto();
            optionExtendDto.setSel(true);
            optionExtendDtoList.add(optionExtendDto);
        }
        System.out.println(optionExtendDtoList);

        WarnRulesDto warnRulesDto = new WarnRulesDto();
        warnRulesDto.setOptionExtendDtoList(optionExtendDtoList);
        System.out.println(warnRulesDto);

        //计算开始时间和结束时间的差值
        int difference = DateUtils.diffDate(new Date(),new Date());
        Date startTime = DateUtils.getDateForBegin(new Date(),-(difference+1));
        Date endTime = DateUtils.getDateForEnd(new Date(),-(difference+1));
        System.out.println(difference+"  "+startTime+"   "+endTime);


        String percent = String.valueOf(new BigDecimal("100")
                .divide(new BigDecimal("200"), 4, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal("100"))
                .setScale(2, BigDecimal.ROUND_HALF_UP));

        String percent2 = ReportUtils.toPercent(new BigDecimal("100"), new BigDecimal("200"));

        System.out.println(percent+"    "+percent2);

        List<String> datesBetween2Date = DateUtils.getDatesBetween2Date("2021-02-23", "2021-02-27");
        List xdataList = new ArrayList<>();
        List seriesList = new ArrayList<>();
        datesBetween2Date.forEach(date->{
            seriesList.add(date);
            xdataList.add(date);
        });
        seriesList.forEach(System.out::println);
        System.out.println("***************************************");
        xdataList.forEach(System.out::println);

        System.out.println("当年第一天 = "+DateUtils.formatDate(DateUtils.getCurrYearFirst(), DateUtils.DATE_FORMAT));

        String date = "2021-02-25";
        System.out.println(date.substring(5, 7));
        System.out.println("***************************************");
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        getDayByMonth(currentYear,1).forEach(System.out::println);
        TreeMap<Integer,List<String>> map = new TreeMap<>();
        System.out.println("当前年 = "+currentYear);
        for (int i=1;i<13;i++){
            map.put(i,getDayByMonth(currentYear,i));
        }
        System.out.println("map = "+ JSON.toJSONString(map));

        String one = "01";
        String ten = "10";
        System.out.println(Integer.parseInt(one)+"   "+Integer.parseInt(ten));

        List<String> datesBetween2Date2 = DateUtils.getDatesBetween2Date("2021-02-23 00:00:00", "2021-02-27 23:59:59");
        System.out.println("#############################");
        datesBetween2Date2.forEach(System.out::println);

        Map<String, Date> dateWhitBeforeN = DateUtils.getDateWhitBeforeN(0);
        System.out.println(DateUtils.formatDate(dateWhitBeforeN.get("start"),DateUtils.DATE_FORMAT)+"   "+DateUtils.formatDate(dateWhitBeforeN.get("end"),DateUtils.DATE_FORMAT));
    }

    public static List<String> getDayByMonth(int yearParam,int monthParam){
        List<String> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(yearParam, monthParam-1, 1);
        int year = calendar.get(Calendar.YEAR);//年份
        int month = calendar.get(Calendar.MONTH) + 1;//月份
        int day = calendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String date=null;
            if(month<10 && i<10){
                date = String.valueOf(year)+"-0"+month+"-0"+i;
            }
            if(month<10 && i>=10){
                date = String.valueOf(year)+"-0"+month+"-"+i;
            }
            if(month>=10 && i<10){
                date = String.valueOf(year)+"-"+month+"-0"+i;
            }
            if(month>=10 && i>=10){
                date = String.valueOf(year)+"-"+month+"-"+i;
            }

            list.add(date);
        }
        return list;
    }
}
