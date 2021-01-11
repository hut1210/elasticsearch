package com.example.demo.enums;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/5 15:44
 */
public interface MarkEnum {

    /**
     * 获取位数
     */
    int bit();

    /**
     * 位置值
     */
    char value();

    /**
     * 该标记的名称
     */
    String desc();

    /**
     * 该位置的名称
     */
    MarkEnum getMarkEnum(int bit, char value);
}
