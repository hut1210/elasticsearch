package com.example.demo.condition;

import com.example.demo.annotation.Filter_Range;
import com.example.demo.annotation.Filter_Term;
import com.example.demo.enums.FormatEnum;
import com.example.demo.enums.RangeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/4 13:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonDeliveryCondition {

    /**
     *日期范围开始时间
     */
    @Filter_Range(fieldName = "first_time",type= RangeTypeEnum.GTE,format = FormatEnum.DATETIME)
    private String createTimeStart;
    /**
     *日期范围开始时间
     */
    @Filter_Range(fieldName = "first_time",type= RangeTypeEnum.LTE,format = FormatEnum.DATETIME)
    private String createTimeEnd;
    /**
     *配送网点编号
     */
    @Filter_Term(fieldName = "branches_name")
    private String networkCode;
}
