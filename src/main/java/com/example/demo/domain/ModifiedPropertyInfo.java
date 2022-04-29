package com.example.demo.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/11/29 15:39
 */
@Data
public class ModifiedPropertyInfo implements Serializable {

    /**
     * 发生变化的属性名称
     */
    private String propertyName;
    /**
     * 发生变化的属性注释
     */
    private String propertyComment;
    /**
     * 修改前的值
     */
    private Object oldValue;
    /**
     * 修改后的值
     */
    private Object newValue;

}
