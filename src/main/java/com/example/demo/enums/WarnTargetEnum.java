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

    private  Integer code;
    private  String name;

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

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
}
