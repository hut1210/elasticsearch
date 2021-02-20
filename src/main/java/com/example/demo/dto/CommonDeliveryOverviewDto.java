package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/4 14:23
 * 共配业务
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonDeliveryOverviewDto {

    /**
     * 网点名称
     */
    private String networkName;
    /**
     * 业务类型
     */
    private String bizType;
    /**
     * 运单量
     */
    private String waybillAmount="0";
    /**
     * 在途单量
     */
    private String onTheWayAmount="0";
    /**
     * 配送单量
     */
    private String distributionAmount="0";
    /**
     * 已签收单量
     */
    private String signedInAmount="0";
    /**
     * 取消单量
     */
    private String cancelAmount="0";
    /**
     * 拒收单量
     */
    private String rejectionAmount="0";
    /**
     * 派送完成率
     */
    private String deliveryCompletionRate;
    /**
     * 平均配送时长
     */
    private Integer avgDistributionDuration;
}
