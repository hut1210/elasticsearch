package com.example.demo.domain;

import lombok.Data;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/11/29 15:40
 */
@Data
public class PropertyModelInfo {

    /**
     * 属性名称
     */
    private String propertyName;
    /**
     * 属性注释
     */
    private String propertyComment;
    /**
     * 属性值
     */
    private Object value;
    /**
     * 返回值类型
     */
    private Class<?> returnType;

}
