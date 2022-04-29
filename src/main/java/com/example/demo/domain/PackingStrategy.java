package com.example.demo.domain;
import com.example.demo.annotation.PropertyName;
import com.example.demo.util.DateUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/11/29 15:52
 */
public class PackingStrategy implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 主键,包装策略id
     */
    private Long id;

    /**
     * 商家ID
     */
    private Long sellerId;

    /**
     * 商家编码
     */
    private String sellerNo;

    /**
     * 商家名称
     */
    private String sellerName;

    /**
     * 事业部ID
     */
    private Long deptId;

    /**
     * 事业部编码
     */
    private String deptNo;

    /**
     * 事业部名称
     */
    private String deptName;

    /**
     * 主单位
     */
    @PropertyName(name = "主单位")
    private Byte primaryUnit;

    /**
     * 内包装
     */
    @PropertyName(name = "内包装")
    private Byte innerPackaging;

    /**
     * 箱包装
     */
    @PropertyName(name = "箱包装")
    private Byte boxPacking;

    /**
     * 托盘
     */
    @PropertyName(name = "托盘")
    private Byte tray;

    /**
     * 状态：0停用 1启动
     */
    @PropertyName(name = "状态")
    private Byte status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 时间戳
     */
    private Timestamp ts;

    /**
     * 删除标识
     */
    @PropertyName(name = "删除标识")
    private Integer yn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Byte getPrimaryUnit() {
        return primaryUnit;
    }

    public void setPrimaryUnit(Byte primaryUnit) {
        this.primaryUnit = primaryUnit;
    }

    public Byte getInnerPackaging() {
        return innerPackaging;
    }

    public void setInnerPackaging(Byte innerPackaging) {
        this.innerPackaging = innerPackaging;
    }

    public Byte getBoxPacking() {
        return boxPacking;
    }

    public void setBoxPacking(Byte boxPacking) {
        this.boxPacking = boxPacking;
    }

    public Byte getTray() {
        return tray;
    }

    public void setTray(Byte tray) {
        this.tray = tray;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreateTime() {
        try {
            return DateUtils.formatDate(this.createTime, "yyyy-MM-dd HH:mm:ss");
        } catch (Exception var2) {
            return null;
        }
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        try {
            return DateUtils.formatDate(this.updateTime, "yyyy-MM-dd HH:mm:ss");
        } catch (Exception var2) {
            return null;
        }
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Timestamp getTs() {
        return ts;
    }

    public void setTs(Timestamp ts) {
        this.ts = ts;
    }

    public Integer getYn() {
        return yn;
    }

    public void setYn(Integer yn) {
        this.yn = yn;
    }
}
