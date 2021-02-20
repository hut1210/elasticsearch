package com.example.demo.enums;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/10 11:31
 */
public enum WaybillReportEnum {
    DISTRIBUTION_AMOUNT(1, "配送量", "统计所有始发网点不为西藏网点，并且目的网点为西藏网点的运单总量", "delivery_volume"),
    COLLECT_AMOUNT(2, "揽收量", "以选择时间条件为基础，统计所有始发网点为西藏网点的运单总量", "collection_volume"),
    ;

    WaybillReportEnum(Integer code, String name, String note, String column) {
        this.code = code;
        this.name = name;
        this.note = note;
        this.column = column;
    }

    private Integer code;
    private String name;
    private String note;
    private String column;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public String getColumn() {
        return column;
    }
}
