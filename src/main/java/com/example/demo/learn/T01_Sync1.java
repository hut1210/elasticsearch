package com.example.demo.learn;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/9/3 10:19
 */
public class T01_Sync1 {

    private static class T{}

    public static void main(String[] args) {
        T t = new T();
        System.out.println(ClassLayout.parseInstance(t).toPrintable());
    }
}
