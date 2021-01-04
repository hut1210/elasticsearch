package com.example.demo.util;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/4 15:03
 */
public class CaseFormat {
    public static final char UNDERLINE = '_';


    public static String toUnderscore(String str) {

        if (str == null || str.trim().length() == 0) {
            return str;
        }
        StringBuilder result = new StringBuilder();

        for (int i = 0, j = str.length(); i < j; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i != 0) {
                    result.append(UNDERLINE);
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static String toCamel(String str) {
        if (str == null || str.trim().length() == 0) {
            return str;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0, j = str.length(); i < j; i++) {
            char c = str.charAt(i);
            if (c == UNDERLINE) {
                if (++i < j) {
                    result.append(Character.toUpperCase(str.charAt(i)));
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
