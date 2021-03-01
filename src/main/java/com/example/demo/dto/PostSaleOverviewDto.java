package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/5 10:56
 * 售后事件实时监控对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostSaleOverviewDto {

    /**
     * 工单号
     */
    private String workOrderNo;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 线索类型
     */
    private String clueType;

    /**
     * 线索值
     */
    private String clueValue;
    /**
     * 工单类型
     */
    private String workOrderType;
    /**
     * 工单状态
     */
    private String workOrderStatus;
    /**
     * 问题类型
     */
    private String problemType;
    /**
     * 处理方
     */
    private String processingParty;
    /**
     * 是否升级状态
     */
    private String upgradeStatus;
    /**
     * 处理总时长
     */
    private String totalProcessingTime;

}
