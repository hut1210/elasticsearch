package com.example.demo.enums;

/**
 * 配送状态
 *
 * @author huteng5
 * @version 1.0
 * @date 2021/2/9 14:52
 */
public enum DistributionStatusEnum {
    SINGED_IN(0, "已签收", "signed_orders"),
    NOT_SIGNED(1, "未签收", ""),
    ;

    DistributionStatusEnum(Integer code, String name, String column) {
        this.code = code;
        this.name = name;
        this.column = column;
    }

    private Integer code;
    private String name;
    private String column;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getColumn() {
        return column;
    }
}
