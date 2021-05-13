package com.example.demo.condition;

import com.example.demo.annotation.Filter_Range;
import com.example.demo.annotation.Filter_Term;
import com.example.demo.annotation.mustnot.Filter_Not_Terms;
import com.example.demo.enums.RangeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/5/11 20:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaybillWideCondition implements Serializable {

    /**
     * 日期范围开始时间
     */
    @Filter_Range(fieldName = "update_time",type= RangeTypeEnum.GTE)
    private String createTimeStart;


    @Filter_Not_Terms(fieldName = "waybill_code",split = ",")
    private List<String> waybillCodeList;

    @Filter_Term(fieldName = "waybill_code")
    private String waybillCode;


}
