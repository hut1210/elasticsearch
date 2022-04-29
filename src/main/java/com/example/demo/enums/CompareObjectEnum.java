package com.example.demo.enums;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/1/13 13:33
 */
public enum CompareObjectEnum {
    STATUS("status","0","停用","1","启用"),
    ;

    CompareObjectEnum(String name, String value1, String decs1, String value2, String decs2) {
        this.name = name;
        this.value1 = value1;
        this.decs1 = decs1;
        this.value2 = value2;
        this.decs2 = decs2;
    }

    private String name;
    private String value1;
    private String decs1;
    private String value2;
    private String decs2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getDecs1() {
        return decs1;
    }

    public void setDecs1(String decs1) {
        this.decs1 = decs1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getDecs2() {
        return decs2;
    }

    public void setDecs2(String decs2) {
        this.decs2 = decs2;
    }
}
