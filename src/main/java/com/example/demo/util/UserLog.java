package com.example.demo.util;

import com.xkzhangsan.xkbeancomparator.BeanComparator;
import com.xkzhangsan.xkbeancomparator.CompareResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/1/13 11:05
 */
public class UserLog {
    private static final Map<String, String> propertyTranslationMap = new HashMap<>();

    static {
        propertyTranslationMap.put("name", "用户名");
        propertyTranslationMap.put("age", "年龄");
    }

    public static CompareResult getCompareResult(Object source, Object target){
        return BeanComparator.getCompareResult(source, target, propertyTranslationMap);
    }
}
