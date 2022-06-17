package com.example.demo.yl;

import java.io.Serializable;
import java.util.List;

/**
 * 接收CLPS调拨单实体类
 * @author biangongyin
 */
public class IbMainParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 调拨单编码
     */
    private String ibNo;

    /**
     * isv调拨单编码
     */
    private String isvIbNo;

    /**
     * CLPS出库单号
     */
    private String outsideNo;

    /**
     * CLPS入库单号
     */
    private String insideNo;

    /**
     * 调拨出库单来源 1:ISV、CLPS、ECLP、EDI、门店要货
     */
    private Byte ibSource;

    /**
     * 调拨单状态 初始化、已下发库房、拣货下架、包裹出库、出库单拉回、全部入库、部分入库
     */
    private Integer ibStatus;

    /**
     * 用于回传,回传类型
     */
    private Byte backType;
    /**
     * 用于回传,回传原因
     */
    private String statusReason;

    /**
     * 调拨单取消状态 取消中、取消失败、取消成功、
     */
    private Integer ibCancleStatus;

    /**
     * 商家Id
     */
    private Long sellerId;

    /**
     * 商家编号
     */
    private String sellerNo;

    /**
     * 商家名称
     */
    private String sellerName;

    /**
     * 事业部Id
     */
    private Long deptId;

    /**
     * 事业部编号
     */
    private String deptNo;

    /**
     * 事业部名称
     */
    private String deptName;


    /**
     * 承运商ID
     */
    private Long shipperId;

    /**
     * 承运商编码
     */
    private String shipperNo;

    /**
     * 承运商名称
     */
    private String shipperName;

    /**
     * 三方运单号
     */
    private String thirdWayBillNo;

    /**
     * 库房Id
     */
    private Long originWarehouseId;

    /**
     * 库房No
     */
    private String originWarehouseNo;

    /**
     * 库房名称
     */
    private String originWarehouseName;

    /**
     * 库房Id
     */
    private Long targetWarehouseId;

    /**
     * 库房No
     */
    private String targetWarehouseNo;

    /**
     * 库房名称
     */
    private String targetWarehouseName;



    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人\操作人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    private List<IbItemParam> ibItemParamList;

    /**
     * 完成时间
     */
    private Long completeTime;

    /**
     * 创建时间字符串
     */
    private String strCreateTime;
    /**
     * 完成时间字符串
     */
    private String strCompleteTime;
    /**
     * 状态字符串
     */
    private String strIbStatus;
    /**
     * 来源字符串
     */
    private String strIbSource;

    /**
     * 差异状态
     */
    private Integer ibDiffStatus;

    private String strIbDiffStatus;

    private String strUpdateTime;

    /**
     * 发货完成时间
     */
    private Long deliveryTime;

    /**
     * 发货完成时间
     */
    private String strDeliveryTime;

    /**
     * eclp 调拨出回传的箱号
     */
    private List<String>  boxNos;

    /**
     * 是否需要计算运费，0不需要，1计算运费
     */
    private Byte yunFeeFlag;

    /**
     * 计费方式
     */
    private Byte yunFeeType;

    /**
     * 是否是特殊仓库
     */
    private Boolean specialWarehouse;

    /**
     * 车型
     */
    private String carModel;

    public List<String> getBoxNos() {
        return boxNos;
    }

    public void setBoxNos(List<String> boxNos) {
        this.boxNos = boxNos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIbNo() {
        return ibNo;
    }

    public void setIbNo(String ibNo) {
        this.ibNo = ibNo;
    }

    public String getIsvIbNo() {
        return isvIbNo;
    }

    public void setIsvIbNo(String isvIbNo) {
        this.isvIbNo = isvIbNo;
    }

    public String getOutsideNo() {
        return outsideNo;
    }

    public void setOutsideNo(String outsideNo) {
        this.outsideNo = outsideNo;
    }

    public String getInsideNo() {
        return insideNo;
    }

    public void setInsideNo(String insideNo) {
        this.insideNo = insideNo;
    }

    public Byte getIbSource() {
        return ibSource;
    }

    public void setIbSource(Byte ibSource) {
        this.ibSource = ibSource;
    }

    public Integer getIbStatus() {
        return ibStatus;
    }

    public void setIbStatus(Integer ibStatus) {
        this.ibStatus = ibStatus;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public Integer getIbCancleStatus() {
        return ibCancleStatus;
    }

    public void setIbCancleStatus(Integer ibCancleStatus) {
        this.ibCancleStatus = ibCancleStatus;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerNo() {
        return sellerNo;
    }

    public void setSellerNo(String sellerNo) {
        this.sellerNo = sellerNo;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getOriginWarehouseId() {
        return originWarehouseId;
    }

    public void setOriginWarehouseId(Long originWarehouseId) {
        this.originWarehouseId = originWarehouseId;
    }

    public String getOriginWarehouseNo() {
        return originWarehouseNo;
    }

    public void setOriginWarehouseNo(String originWarehouseNo) {
        this.originWarehouseNo = originWarehouseNo;
    }

    public String getOriginWarehouseName() {
        return originWarehouseName;
    }

    public void setOriginWarehouseName(String originWarehouseName) {
        this.originWarehouseName = originWarehouseName;
    }

    public Long getTargetWarehouseId() {
        return targetWarehouseId;
    }

    public void setTargetWarehouseId(Long targetWarehouseId) {
        this.targetWarehouseId = targetWarehouseId;
    }

    public String getTargetWarehouseNo() {
        return targetWarehouseNo;
    }

    public void setTargetWarehouseNo(String targetWarehouseNo) {
        this.targetWarehouseNo = targetWarehouseNo;
    }

    public String getTargetWarehouseName() {
        return targetWarehouseName;
    }

    public void setTargetWarehouseName(String targetWarehouseName) {
        this.targetWarehouseName = targetWarehouseName;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public List<IbItemParam> getIbItemParamList() {
        return ibItemParamList;
    }

    public void setIbItemParamList(List<IbItemParam> ibItemParamList) {
        this.ibItemParamList = ibItemParamList;
    }

    public Byte getBackType() {
        return backType;
    }

    public void setBackType(Byte backType) {
        this.backType = backType;
    }

    public Long getShipperId() {
        return shipperId;
    }

    public void setShipperId(Long shipperId) {
        this.shipperId = shipperId;
    }

    public String getShipperNo() {
        return shipperNo;
    }

    public void setShipperNo(String shipperNo) {
        this.shipperNo = shipperNo;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getThirdWayBillNo() {
        return thirdWayBillNo;
    }

    public void setThirdWayBillNo(String thirdWayBillNo) {
        this.thirdWayBillNo = thirdWayBillNo;
    }

    public Long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Long completeTime) {
        this.completeTime = completeTime;
    }

    public String getStrCreateTime() {
        return strCreateTime;
    }

    public void setStrCreateTime(String strCreateTime) {
        this.strCreateTime = strCreateTime;
    }

    public String getStrCompleteTime() {
        return strCompleteTime;
    }

    public void setStrCompleteTime(String strCompleteTime) {
        this.strCompleteTime = strCompleteTime;
    }

    public String getStrIbStatus() {
        return strIbStatus;
    }

    public void setStrIbStatus(String strIbStatus) {
        this.strIbStatus = strIbStatus;
    }

    public String getStrIbSource() {
        return strIbSource;
    }

    public void setStrIbSource(String strIbSource) {
        this.strIbSource = strIbSource;
    }

    public Integer getIbDiffStatus() {
        return ibDiffStatus;
    }

    public void setIbDiffStatus(Integer ibDiffStatus) {
        this.ibDiffStatus = ibDiffStatus;
    }

    public String getStrIbDiffStatus() {
        return strIbDiffStatus;
    }

    public void setStrIbDiffStatus(String strIbDiffStatus) {
        this.strIbDiffStatus = strIbDiffStatus;
    }

    public String getStrUpdateTime() {
        return strUpdateTime;
    }

    public void setStrUpdateTime(String strUpdateTime) {
        this.strUpdateTime = strUpdateTime;
    }

    public Long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getStrDeliveryTime() {
        return strDeliveryTime;
    }

    public void setStrDeliveryTime(String strDeliveryTime) {
        this.strDeliveryTime = strDeliveryTime;
    }

    public Byte getYunFeeFlag() {
        return yunFeeFlag;
    }

    public void setYunFeeFlag(Byte yunFeeFlag) {
        this.yunFeeFlag = yunFeeFlag;
    }

    public Byte getYunFeeType() {
        return yunFeeType;
    }

    public void setYunFeeType(Byte yunFeeType) {
        this.yunFeeType = yunFeeType;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Boolean getSpecialWarehouse() {
        return specialWarehouse;
    }

    public void setSpecialWarehouse(Boolean specialWarehouse) {
        this.specialWarehouse = specialWarehouse;
    }
}
