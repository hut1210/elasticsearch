package com.example.demo.util;

import com.example.demo.annotation.PropertyName;

import java.lang.reflect.Field;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/1/13 13:58
 */
public interface CompareObjectProcessor {
    /**
     * 差异对比
     * @param field
     * @param annotation
     * @param o1
     * @param o2
     */
    void doProcess(Field field, PropertyName annotation, Object o1, Object o2);
}
