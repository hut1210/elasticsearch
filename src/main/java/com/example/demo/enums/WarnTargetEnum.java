package com.example.demo.enums;

/**
 * 预警指标
 * @author huteng5
 * @version 1.0
 * @date 2020/12/16 9:26
 */
public enum WarnTargetEnum {
    ORDER_TARGET(1,"order"),
    STOCK_TARGET(2,"stock"),
    LOGISTICS_TARGET(3,"logistics"),
    ;

    private final Integer code;
    private final String name;

    WarnTargetEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
