package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/5 10:14
 * 运单业务报表对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaybillReportOverviewDto {

    /**
     * 日期
     */
    private String createTime;
    /**
     * 网点类型
     */
    private String networkType;
    /**
     * 网点所属区域
     */
    private String networkArea;
    /**
     * 西藏网点
     */
    private String networkName;
    /**
     * 运单总量
     */
    private String waybillTotalAmount;
    /**
     * 配送量
     */
    private String distributionAmount;
    /**
     * 揽收量
     */
    private String collectAmount;
}
