package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/17 16:32
 */
public class TestEncoder {
    public static void main(String[] args) throws Exception {
        String str = URLEncoder.encode("中文字段", "UTF-8");
        System.out.println("编码 = " + str);

        String s = URLDecoder.decode(str, "UTF-8");
        System.out.println("解析 = " + s);
    }
}
