package com.example.demo.condition;

import com.example.demo.annotation.Filter_Term;
import com.example.demo.annotation.Filter_Wildcard;
import lombok.Data;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/5 10:57
 */
@Data
public class PostSaleCondition {

    /**
     * 查询时间
     */
    @Filter_Term(fieldName = "create_time")
    private String createTime;
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
}
