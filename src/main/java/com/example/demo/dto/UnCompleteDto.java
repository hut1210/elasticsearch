package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/30 20:22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnCompleteDto {
    /**
     * 仓库编号
     */
    private String warehouseNo;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 未完成数量
     */
    private double unCompleteQty;
    /**
     * 未完成重量
     */
    private double unCompleteWeight;
}
