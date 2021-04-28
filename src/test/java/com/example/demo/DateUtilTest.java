package com.example.demo;

import com.example.demo.util.DateUtil;

import java.util.Date;

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
    }
}
