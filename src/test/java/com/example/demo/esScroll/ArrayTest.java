package com.example.demo.esScroll;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/7/9 15:39
 */
public class ArrayTest {
    public static void main(String[] args) {
        String[] str = new String[]{"1","2","3","4","5","6","7"};
        String[] subarray = ArrayUtils.subarray(str, 0, 3);
        for (String s :subarray){
            System.out.println(s);
        }
    }
}
