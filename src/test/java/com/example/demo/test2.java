package com.example.demo;

import com.example.demo.util.DateUtils;
import com.example.demo.util.ReportUtils;
import com.example.demo.util.XZReportUtils;
import org.checkerframework.checker.units.qual.A;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/15 9:49
 */
public class test2 {

    public static void main(String[] args) {
        List list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);

        /*int size = (int) Math.ceil((double) list.size() / 2);
        System.out.println((int) Math.ceil((double) list.size() / 2));
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                int temp = i * 2;
                if (temp + 2 > list.size()) {
                    System.out.println(i + "   " + list.subList(temp, list.size()));
                } else {
                    System.out.println(i + "   " + list.subList(temp, temp + 2));
                }
            }
        }*/

        /*int size = (int) Math.ceil((double) list.size() / 3);
        System.out.println((int) Math.ceil((double) list.size() / 3));
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                int temp = i * 3;
                if (temp + 3 > list.size()) {
                    System.out.println(i + "   " + list.subList(temp, list.size()));
                } else {
                    System.out.println(i + "   " + list.subList(temp, temp + 3));
                }
            }
        }*/

        int size = (int) Math.ceil((double) list.size() / 5);
        System.out.println((int) Math.ceil((double) list.size() / 5));
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                int temp = i * 5;
                if (temp + 5 > list.size()) {
                    System.out.println(i + "   " + list.subList(temp, list.size()));
                } else {
                    System.out.println(i + "   " + list.subList(temp, temp + 5));
                }
            }
        }

        /*System.out.println(list.subList(0, 1));
        System.out.println(list.subList(0, 2));
        System.out.println(list.subList(0, 3));
        System.out.println(list.subList(0, 4));*/

        String str = "2021-02-19 00:00:00";
        String monthKey = str.substring(5, 7);
        Integer month = Integer.parseInt(monthKey);
        System.out.println(monthKey+"   "+month);
        long monthCount = 0L;
        TreeMap<Integer, Long> monthMap = new TreeMap<>();
        if (monthMap.containsKey(month)) {
            monthCount = 100L + monthMap.get(monthKey);
            monthMap.put(month, monthCount);
        } else {
            monthMap.put(month, 100L);
        }

        System.out.println(monthMap);

        System.out.println(ReportUtils.getDayCount(new BigDecimal("0"), new BigDecimal("0")));

        System.out.println(ReportUtils.getDayCount(0L, 0L));

        System.out.println("2021-03-11".substring(0, 10));

        Integer i = new Integer(0);
        if(i instanceof Integer){
            System.out.println(true);
        }
        System.out.println(i==0);
        System.out.println(i.equals(0));

        Long l = new Long(0);
        System.out.println(l==0);
        System.out.println(l.equals(0L));

        //System.out.println(new BigDecimal("---").compareTo(BigDecimal.ZERO));
        String linkRelativeRatio = XZReportUtils.getDayCount(new BigDecimal("1"), new BigDecimal("1"));
        if (!XZReportUtils.isNull(new BigDecimal("1")) && !XZReportUtils.isNull(new BigDecimal("1"))){
            System.out.println("rise ---- "+(new BigDecimal(linkRelativeRatio).compareTo(BigDecimal.ZERO) > 0 ? 1 : 0));
        }
        System.out.println(linkRelativeRatio);

        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(5);
        integerList.add(3);
        integerList.add(8);
        /*integerList.add(2);
        integerList.add(6);
        integerList.add(4);
        integerList.add(7);
        integerList.add(10);*/

        integerList = integerList.stream().sorted((i1,i2)->i1.compareTo(i2)).collect(Collectors.toList()).subList(0,5);
        System.out.println(integerList);


    }
}

