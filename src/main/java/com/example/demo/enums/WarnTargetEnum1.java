package com.example.demo.enums;

import java.util.EnumSet;
import java.util.HashMap;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/5/30 15:46
 */
public enum WarnTargetEnum1 {
    TURNOVER_RATE(101,"周转率异常"),
    STORAGE_AGE(102,"库龄异常"),
    INVENTORY_SURPLUS(103,"盘盈"),
    INVENTORY_LOSS(104,"盘亏"),
    PICKING(201,"拣货异常"),
    SORTING(202,"分拣异常"),
    TO_REVIEW(203,"复核异常"),
    OVERTIME(204,"超时异常"),
    ;

    private final Integer code;
    private final String name;

    WarnTargetEnum1(Integer code, String name) {
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
