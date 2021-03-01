package com.example.demo.condition;

import lombok.Data;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/18 14:45
 */
@Data
public class PostSalePageCondition extends PostSaleCondition {

    private int pageIndex;

    private int pageSize;
}
