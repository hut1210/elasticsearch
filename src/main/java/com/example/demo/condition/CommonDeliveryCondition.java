package com.example.demo.condition;


import com.example.demo.annotation.Filter_Range;
import com.example.demo.annotation.Filter_Term;
import com.example.demo.annotation.Filter_Terms;
import com.example.demo.enums.RangeTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/4 13:43
 */
@Data
public class CommonDeliveryCondition implements Serializable {

    /**
     *日期范围开始时间
     */
    @Filter_Range(fieldName = "first_time",type= RangeTypeEnum.GTE)
    private String createTimeStart;
    /**
     *日期范围结束时间
     */
    @Filter_Range(fieldName = "first_time",type= RangeTypeEnum.LTE)
    private String createTimeEnd;
    /**
     *配送网点编号
     */
    @Filter_Term(fieldName = "old_site_id")
    private String networkCode;
    /**
     * 目的网点
     */
    @Filter_Terms(fieldName = "old_site_id", split = ",")
    private List<String> targetNetworkCode;
    /**
     * 配送状态
     */
    @Filter_Terms(fieldName = "state",split = ",")
    private List<String> status;

    /**
     * 收货网点所属区域
     */
    @Filter_Terms(fieldName = "old_site_area")
    private String oldSiteArea;
}
