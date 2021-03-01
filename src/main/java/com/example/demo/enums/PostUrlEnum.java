package com.example.demo.enums;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/25 21:04
 */
public enum PostUrlEnum {
    COMMONDELIVERYPAGE(1, "分页查询共配业务列表","https://uat-proxy.jd.com/XZService/queryCommonDeliveryPage"),
    COMMONDELIVERYINDEXOVERVIEW(2, "共配业务指标概览","https://uat-proxy.jd.com/XZService/queryCommonDeliveryIndexOverview"),
    WAYBILLREPORTPAGE(3,"分页查询运单业务报表","https://uat-proxy.jd.com/XZService/queryWaybillReportPage"),
    WAYBILLINDEXOVERVIEW(4,"运单业务指标概览","https://uat-proxy.jd.com/XZService/queryWaybillIndexOverview"),
    POSTSALEPAGE(5,"分页查询售后事件实时监控报表列表","https://uat-proxy.jd.com/XZService/queryPostSalePage"),
    POSTSALEINDEXOVERVIEW(6,"售后事件实时监控指标概览","https://uat-proxy.jd.com/XZService/queryPostSaleIndexOverview"),
    ;

    PostUrlEnum(Integer code, String desc, String url) {
        this.code = code;
        this.desc = desc;
        this.url = url;
    }

    private Integer code;
    private String desc;
    private String url;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
