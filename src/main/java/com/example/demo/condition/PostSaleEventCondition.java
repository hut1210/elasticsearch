package com.example.demo.condition;

import com.example.demo.annotation.Filter_Range;
import com.example.demo.annotation.Filter_Term;
import com.example.demo.annotation.Filter_Terms;
import com.example.demo.annotation.Filter_Wildcard;
import com.example.demo.enums.RangeTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/23 14:58
 */
@Data
public class PostSaleEventCondition {
    /**
     * 查询开始时间
     */
    @Filter_Range(fieldName = "create_time",type= RangeTypeEnum.GTE)
    private String createTimeStart;
    /**
     * 查询结束时间
     */
    @Filter_Range(fieldName = "create_time",type= RangeTypeEnum.LTE)
    private String createTimeEnd;
    /**
     * 工单类型
     */
    @Filter_Term(fieldName = "work_order_type")
    private String workOrderType;
    /**
     * 线索类型
     */
    @Filter_Term(fieldName = "originate_clue_type")
    private String clueType;
    /**
     * 创建人
     */
    @Filter_Wildcard(fieldName = "create_name")
    private String createUser;

    /**
     * 工单状态
     */
    @Filter_Terms(fieldName = "status", split = ",")
    private List<String> status;
}
