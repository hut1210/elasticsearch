package com.example.demo;

import com.example.demo.util.DateUtil;
import com.example.demo.util.DateUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/22 17:27
 */
public class DateUtilTest {
    public static void main(String[] args) {
        int day = Integer.parseInt("0");
        Date date = DateUtil.addDate(new Date(), -day);
        String s = DateUtil.formatDateByFormat(date, DateUtil.DATETIME_FORMAT);
        System.out.println(s);

        System.out.println(DateUtils.getNearly6StartAndEnd().get("start") + "  " + DateUtils.getNearly6StartAndEnd().get("end"));
        System.out.println(DateUtils.getDateForEnd(DateUtils.parseDate(DateUtils.getNearly6StartAndEnd().get("start"), DateUtils.DATE_FORMAT),0));
        Map<String, Date> dateWhitBeforeN = DateUtils.getDateWhitBeforeN(60);
        System.out.println(dateWhitBeforeN.get("start")+"   "+dateWhitBeforeN.get("end"));

        System.out.println(DateUtils.getDateForBegin(new Date(),0)+" "+DateUtils.getDateForEnd(new Date(),0));


    }
}
