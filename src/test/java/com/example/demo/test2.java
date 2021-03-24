package com.example.demo;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
    }
}

