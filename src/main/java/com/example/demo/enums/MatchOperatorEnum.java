package com.example.demo.enums;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/4 14:08
 */
public enum MatchOperatorEnum {
    AND("AND"),
    OR("OR");

    private String value;

    private MatchOperatorEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
