package com.example.demo.domain;


import com.example.demo.annotation.PropertyName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 包装单位
 * @author zhangshuyi1
 */
public class PackUom implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键,包装单位id
	 */
	private Long id;
	
	/**
	 * clps包装编码
	 */
	private String packNo;
	
	/**
	 * 仓库包装单位等级0 1 2 3 4
	 */
	private Integer uomLevel;
	
	/**
	 * 仓库包装单位编码，EA(each,单件)；
 IP(inner package 内包装)；CS(case箱包装)；OT(other其他)
	 @see
	 */
	private String uomNo;
	
	/**
	 * 货主(外系统)箱规编码
	 */
	private String outPackNo;
	
	/**
	 * 货主(外系统)箱规名称,（1）*(20)*(50)
	 */
	private String outPackName;
	
	/**
	 * 货主包装单位编码
	 */
	@PropertyName(name = "包装单位编码")
	private String outUomNo;
	
	/**
	 * 货主包装单位名称
	 */
	@PropertyName(name = "包装单位名称")
	private String outUomName;
	
	/**
	 * 货主包装单位换算数量，以基本单位为准
	 */
	@PropertyName(name = "包装内商品数量")
	private Integer outUomQty;
	
	/**
	 * 商家id
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
	 * 事业部id
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
	 * 长
	 */
	@PropertyName(name = "长度")
	private String length;
	
	/**
	 * 宽
	 */
	@PropertyName(name = "宽度")
	private String width;
	
	/**
	 * 高
	 */
	@PropertyName(name = "高度")
	private String height;
	
	/**
	 * 体积
	 */
	@PropertyName(name = "体积")
	private String volume;
	
	/**
	 * 包装单位毛重
	 */
	@Deprecated
	private String grossWeight;
	
	/**
	 * 包装单位净重
	 */
	@PropertyName(name = "包装重量")
	private String netWeight;
	
	/**
	 * 备注
	 */
	private String remark;
	
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
	 * 删除标志
	 */
	@PropertyName(name = "删除标志")
	private Integer yn;
	
	/**
	 * 时间戳
	 */
	private Timestamp ts;
	
	/**
	 * 版本号
	 */
	private Integer version;
	
	/**
	 * 预留字段1
	 */
	@PropertyName(name = "预留字段1")
	private String reserve1;
	
	/**
	 * 预留字段2
	 */
	private String reserve2;
	
	/**
	 * 是否为测试数据
	 */
	@PropertyName(name = "预留字段1")
	private Integer test;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the packNo
	 */
	public String getPackNo() {
		return packNo;
	}
	
	/**
	 * @param packNo the packNo to set
	 */
	public void setPackNo(String packNo) {
		this.packNo = packNo;
	}
	
	/**
	 * @return the uomLevel
	 */
	public Integer getUomLevel() {
		return uomLevel;
	}
	
	/**
	 * @param uomLevel the uomLevel to set
	 */
	public void setUomLevel(Integer uomLevel) {
		this.uomLevel = uomLevel;
	}
	
	/**
	 * @return the uomNo
	 */
	public String getUomNo() {
		return uomNo;
	}
	
	/**
	 * @param uomNo the uomNo to set
	 */
	public void setUomNo(String uomNo) {
		this.uomNo = uomNo;
	}
	
	/**
	 * @return the outPackNo
	 */
	public String getOutPackNo() {
		return outPackNo;
	}
	
	/**
	 * @param outPackNo the outPackNo to set
	 */
	public void setOutPackNo(String outPackNo) {
		this.outPackNo = outPackNo;
	}
	
	/**
	 * @return the outPackName
	 */
	public String getOutPackName() {
		return outPackName;
	}
	
	/**
	 * @param outPackName the outPackName to set
	 */
	public void setOutPackName(String outPackName) {
		this.outPackName = outPackName;
	}
	
	/**
	 * @return the outUomNo
	 */
	public String getOutUomNo() {
		return outUomNo;
	}
	
	/**
	 * @param outUomNo the outUomNo to set
	 */
	public void setOutUomNo(String outUomNo) {
		this.outUomNo = outUomNo;
	}
	
	/**
	 * @return the outUomName
	 */
	public String getOutUomName() {
		return outUomName;
	}
	
	/**
	 * @param outUomName the outUomName to set
	 */
	public void setOutUomName(String outUomName) {
		this.outUomName = outUomName;
	}
	
	/**
	 * @return the outUomQty
	 */
	public Integer getOutUomQty() {
		return outUomQty;
	}
	
	/**
	 * @param outUomQty the outUomQty to set
	 */
	public void setOutUomQty(Integer outUomQty) {
		this.outUomQty = outUomQty;
	}
	
	/**
	 * @return the sellerId
	 */
	public Long getSellerId() {
		return sellerId;
	}
	
	/**
	 * @param sellerId the sellerId to set
	 */
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	
	/**
	 * @return the sellerNo
	 */
	public String getSellerNo() {
		return sellerNo;
	}
	
	/**
	 * @param sellerNo the sellerNo to set
	 */
	public void setSellerNo(String sellerNo) {
		this.sellerNo = sellerNo;
	}
	
	/**
	 * @return the sellerName
	 */
	public String getSellerName() {
		return sellerName;
	}
	
	/**
	 * @param sellerName the sellerName to set
	 */
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	
	/**
	 * @return the deptId
	 */
	public Long getDeptId() {
		return deptId;
	}
	
	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
	/**
	 * @return the deptNo
	 */
	public String getDeptNo() {
		return deptNo;
	}
	
	/**
	 * @param deptNo the deptNo to set
	 */
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	
	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}
	
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	/**
	 * @return the length
	 */
	public String getLength() {
		return length;
	}
	
	/**
	 * @param length the length to set
	 */
	public void setLength(String length) {
		this.length = length;
	}
	
	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}
	
	/**
	 * @param width the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}
	
	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}
	
	/**
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}
	
	/**
	 * @return the volume
	 */
	public String getVolume() {
		return volume;
	}
	
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	/**
	 * @return the grossWeight
	 */
	@Deprecated
	public String getGrossWeight() {
		return grossWeight;
	}
	
	/**
	 * @param grossWeight the grossWeight to set
	 */
	@Deprecated
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	
	/**
	 * @return the netWeight
	 */
	public String getNetWeight() {
		return netWeight;
	}
	
	/**
	 * @param netWeight the netWeight to set
	 */
	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}
	
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}
	
	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	/**
	 * @return the updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}
	
	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	/**
	 * @return the yn
	 */
	public Integer getYn() {
		return yn;
	}
	
	/**
	 * @param yn the yn to set
	 */
	public void setYn(Integer yn) {
		this.yn = yn;
	}
	
	/**
	 * @return the ts
	 */
	public Timestamp getTs() {
		return ts;
	}
	
	/**
	 * @param ts the ts to set
	 */
	public void setTs(Timestamp ts) {
		this.ts = ts;
	}
	
	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}
	
	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	/**
	 * @return the reserve1
	 */
	public String getReserve1() {
		return reserve1;
	}
	
	/**
	 * @param reserve1 the reserve1 to set
	 */
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	
	/**
	 * @return the reserve2
	 */
	public String getReserve2() {
		return reserve2;
	}
	
	/**
	 * @param reserve2 the reserve2 to set
	 */
	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}
	
	/**
	 * @return the test
	 */
	public Integer getTest() {
		return test;
	}
	
	/**
	 * @param test the test to set
	 */
	public void setTest(Integer test) {
		this.test = test;
	}
	
}