package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/30 17:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteProgressDto {

    /**
     * 仓库编号
     */
    private String warehouseNo;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 当日下单数量
     */
    private double applyQty;
    /**
     * 完成数量
     */
    private double completeQty;
    /**
     * 百分比
     */
    private String percent;
}
