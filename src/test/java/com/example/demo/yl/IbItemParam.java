package com.example.demo.yl;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 接收CLPS调拨单明细，
 * @author biangongyin
 */
public class IbItemParam implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 调拨单主键
     */
    private Long ibId;

    /**
     * 主商品ID
     */
    private Long goodsId;

    /**
     * 主商品编号
     */
    private String goodsNo;

    /**
     * 主商品名称
     */
    private String goodsName;

    /**
     * 销售平台商品编码
     */
    private String spGoodsNo;

    /**
     * ISV商品编号
     */
    private String isvGoodsNo;

    /**
     * 计划调拨数量
     */
    private BigDecimal applyOutstoreQty;

    /**
     * 实际调拨出库数量
     */
    private BigDecimal realOutstoreQty;

    /**
     * 实际调拨入库数量
     */
    private BigDecimal realInstoreQty;

    /**
     * 商品等级
     */
    private String goodsLevel;

    /**
     * 行号
     */
    private String orderLine;

    /**
     * 批属性
     */
    private String batchAttributes;

    /**
     * 关联的采购单商品明细id
     */
    private Long poItemAssembleId;

    /**
     * 集装箱号
     */
    private String containerNo;

    /**
     * WMS批次号
     */
    private String innerLotattrs;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIbId() {
        return ibId;
    }

    public void setIbId(Long ibId) {
        this.ibId = ibId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSpGoodsNo() {
        return spGoodsNo;
    }

    public void setSpGoodsNo(String spGoodsNo) {
        this.spGoodsNo = spGoodsNo;
    }

    public String getIsvGoodsNo() {
        return isvGoodsNo;
    }

    public void setIsvGoodsNo(String isvGoodsNo) {
        this.isvGoodsNo = isvGoodsNo;
    }

    public BigDecimal getApplyOutstoreQty() {
        return applyOutstoreQty;
    }

    public void setApplyOutstoreQty(BigDecimal applyOutstoreQty) {
        this.applyOutstoreQty = applyOutstoreQty;
    }

    public BigDecimal getRealOutstoreQty() {
        return realOutstoreQty;
    }

    public void setRealOutstoreQty(BigDecimal realOutstoreQty) {
        this.realOutstoreQty = realOutstoreQty;
    }

    public BigDecimal getRealInstoreQty() {
        return realInstoreQty;
    }

    public void setRealInstoreQty(BigDecimal realInstoreQty) {
        this.realInstoreQty = realInstoreQty;
    }

    public String getGoodsLevel() {
        return goodsLevel;
    }

    public void setGoodsLevel(String goodsLevel) {
        this.goodsLevel = goodsLevel;
    }

    public String getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(String orderLine) {
        this.orderLine = orderLine;
    }

    public String getBatchAttributes() {
        return batchAttributes;
    }

    public void setBatchAttributes(String batchAttributes) {
        this.batchAttributes = batchAttributes;
    }

    public Long getPoItemAssembleId() {
        return poItemAssembleId;
    }

    public void setPoItemAssembleId(Long poItemAssembleId) {
        this.poItemAssembleId = poItemAssembleId;
    }

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getInnerLotattrs() {
        return innerLotattrs;
    }

    public void setInnerLotattrs(String innerLotattrs) {
        this.innerLotattrs = innerLotattrs;
    }
}
