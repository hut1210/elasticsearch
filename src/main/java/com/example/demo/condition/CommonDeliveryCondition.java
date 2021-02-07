package com.example.demo.condition;

import lombok.Data;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/4 13:43
 */
@Data
public class CommonDeliveryCondition {

    /**
     *日期范围开始时间
     */
    private String createTimeStart;
    /**
     *日期范围开始时间
     */
    private String createTimeEnd;
    /**
     *配送网点编号
     */
    private String networkCode;
}
