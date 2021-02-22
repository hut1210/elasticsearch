package com.example.demo.condition;

import com.example.demo.annotation.*;
import com.example.demo.annotation.mustnot.Filter_Not_Terms;
import com.example.demo.enums.FormatEnum;
import com.example.demo.enums.RangeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/5 10:08
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaybillReportCondition {

    /**
     * 日期范围开始时间
     */
    @Filter_Range(fieldName = "first_time",type= RangeTypeEnum.GTE)
    private String createTimeStart;
    /**
     * 日期范围结束时间
     */
    @Filter_Range(fieldName = "first_time",type= RangeTypeEnum.LTE)
    private String createTimeEnd;
    /**
     * 网点所属区域
     */
    @Filter_Term(fieldName = "arrive_area")
    private String networkArea;
    /**
     * 网点业务类型
     */
    @Filter_Term(fieldName = "branch_business_type")
    private String networkBusinessType;
    /**
     * 目的网点
     */
    @Filter_Terms(fieldName = "old_site_id",split = ",")
    private List<String> targetNetworkCode;
}
