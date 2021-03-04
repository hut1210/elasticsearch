package com.example.demo.condition;

import lombok.Data;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/2 16:13
 */
@Data
public class WorkBillPageCondition extends WorkBillCondition {

    private int pageIndex;

    private int pageSize;
}
