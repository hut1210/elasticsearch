package com.example.demo;

import com.example.demo.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/5/12 11:23
 */
public class DateUtilsNewTest {
    public static void main(String[] args) {
        String format = "HH:mm:ss";
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
        boolean flag = false;
        try {
            Date nowTime = new SimpleDateFormat(format).parse(sf.format(new Date()));
            Date startTime = new SimpleDateFormat(format).parse("09:00:00");
            Date endTime = new SimpleDateFormat(format).parse("12:00:00");
            if (DateUtils.isEffectiveDate(nowTime, startTime, endTime)) {
                flag = true;
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }System.out.println(flag);
    }
}
