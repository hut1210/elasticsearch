package com.example.demo.enums;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/4 20:11
 */
public enum SourceEnum {
    YUANTONG(1,"圆通",100),
    SUNFENG(2,"顺丰",200),
    ZHONGTONG(3,"中通",300),
    YUNDA(4,"韵达",300),
    SHENTONG(5,"申通",100),
    ;
    SourceEnum(Integer code, String subTitle, Integer num){
        this.code=code;
        this.subTitle=subTitle;
        this.num=num;
    }
    private Integer code;
    private String subTitle;
    private Integer num;

    public Integer getCode() {
        return code;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public Integer getNum() {
        return num;
    }
}
