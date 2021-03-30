package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/30 18:02
 * 完成趋势
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompletionTrendDto {

    /**
     * 仓库编号
     */
    private String warehouseNo;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 累计未完成数量
     */
    private double unfinishedQty;
    /**
     * 完成数量
     */
    private double finishedQty;

    /**
     * 累计未完成金额
     */
    private double unfinishedAmount;
    /**
     * 完成金额
     */
    private double finishedAmount;
}
