package com.example.demo.enums;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/4 17:34
 */
public enum CommonDeliveryEnum {
    INTRANSIT_AMOUNT(1, "在途总单量", "以选择时间条件为基础，统计所有目的网点为西藏网点的在途总单量",200),
    DISTRIBUTION_AMOUNT(2, "配送单量", "以选择时间条件为基础，统计所有目的网点为西藏网点的配送单量",300),
    SINGEDIN_AMOUNT(2, "已签收单量", "以选择时间条件为基础，统计所有目的网点为西藏网点的已签收单量",400),
    CANCEL_AMOUNT(2, "取消单量", "以选择时间条件为基础，统计所有目的网点为西藏网点的取消单量",20),
    REJECTION_AMOUNT(2, "拒收单量", "以选择时间条件为基础，统计所有目的网点为西藏网点的拒收单量",10),
    ;

    CommonDeliveryEnum(Integer code, String name, String note, Integer num) {
        this.code = code;
        this.name = name;
        this.note = note;
        this.num = num;
    }

    private Integer code;
    private String name;
    private String note;
    private Integer num;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public Integer getNum() {
        return num;
    }
}
