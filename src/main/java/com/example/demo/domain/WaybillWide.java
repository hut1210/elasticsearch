package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/5/8 13:47
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaybillWide {
    @TableId
    private Long id;
    private String busiName;
    private Date requireStartTime;
    private String goodWeight;
    private String pickupSiteArea;
    private Date firstTimeDate;
    private String storeCityName;
    private String storeCityId;
    private String fromCountyName;
    private String pickupSiteName;
    private String arriveAreaId;
    private Date firstTime;
    private String distributeStoreName;
    private String payment;
    private String pickupSiteId;
    private String state;
    private String fromCityId;
    private Date realStartTime;
    private String pickingStoreName;
    private String fromProvinceName;
    private String operatorSite;
    private Date stateCreateTime;
    private String receivedMoney;
    private String waybillCode;
    private String goodVolume;
    private String fromProvinceId;
    private String storeProvinceId;
    private String storeCountyId;
    private String goodNumber;
    private String pickingStoreId;
    private Date updateTime;
    private String thirdWaybillCode;
    private String toCountyName;
    private String businessType;
    private String cId;
    private String fromCountyId;
    private String codMoney;
    private String distributeType;
    private String oldSiteId;
    private String toCountyId;
    private String fromCityName;
    private String arriveArea;
    private String oldSiteName;
    private String storeProvinceName;
    private String toProvinceName;
    private String oldSiteArea;
    private String operatorSiteId;
    private String toCityName;
    private Date realStartDate;
    private String relWaybillCode;
    private String toCityId;
    private String storeCountyName;
    private String distributionType;
    private String toProvinceId;
    private String distributeStoreId;
}
