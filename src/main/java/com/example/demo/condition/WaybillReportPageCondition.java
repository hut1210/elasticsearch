package com.example.demo.condition;

import lombok.Data;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/10 10:22
 */
@Data
public class WaybillReportPageCondition extends WaybillReportCondition{

    private int pageIndex;

    private int pageSize;
}
