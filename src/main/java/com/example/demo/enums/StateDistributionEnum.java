package com.example.demo.enums;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/5 11:22
 */
public enum StateDistributionEnum {
    TO_BE_REVIEWED(1,"待审核","20"),
    UNCLAIMED(2,"待领取","60"),
    TO_BE_ANSWERED(3,"待回复","80"),
    TO_BE_CONFIRMED(4,"待确认","100"),
    INVALID(5,"已作废","160"),
    REJECTED(6,"已驳回","180"),
    FORWARDED(7,"已转发","200"),
    CLOSED(8,"已关闭","220"),
    ;
    StateDistributionEnum(Integer code, String name, String status){
        this.code=code;
        this.name=name;
        this.status=status;
    }
    private Integer code;
    private String name;
    private String status;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
