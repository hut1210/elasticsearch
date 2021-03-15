package com.example.demo.condition;

import lombok.Data;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/8 13:58
 */
@Data
public class CommonDeliveryPageCondition extends CommonDeliveryCondition {

    private int pageIndex;

    private int pageSize;
}
