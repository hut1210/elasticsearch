package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 销售订单明细表(SalesItem)表数据传输对象
 *
 * @author hujingping
 * @since 2022-02-28 13:59:05
 */
@Data
public class SalesItemDto extends TenantEntity {
                        
    /**
    * 销售订单ID
    */
    private String salesNo;
                            
    /**
    * 行号
    */
    private String lineNo;    
                        
    /**
    * 系统商品编码
    */
    private String goodsNo;
    /**
     * 商品条码
     */
    private String goodsBarcode;
    /**
     * 平台店铺商品编码
      */
    private String spShopGoodsNo;
    /**
    * 店铺商品编码
    */
    private String shopGoodsNo;    
                        
    /**
    * 商品名称
    */
    private String goodsName;
    /**
     * 包装单位
     */
    private String uomNo;
    /**
     * 包装单位编码
     */
    private String uomName;
    /**
     * 包装单位数量
     */
    private BigDecimal uomQty;
    /**
    * 计划商品数量
    */
    @Positive(message = "商品数量不能小于等于0")
    @Digits(integer = 12, fraction = 4, message = "单据明细行计划出库数量长度不能超过{integer}位,小数点后不能超过{fraction}位")
    private BigDecimal applyQty;    
                        
    /**
    * 计划商品等级
    */
    private String applyGoodsLevel;    
                        
    /**
    * 计划商品等级名称
    */
    private String applyGoodsLevelName;    
                        
    /**
    * 实际商品数量
    */
    private BigDecimal realQty;    
                        
    /**
    * 实际商品等级
    */
    private String realGoodsLevel;    
                        
    /**
    * 实际商品等级名称
    */
    private String realGoodsLevelName;    
                        
    /**
    * 单价
    */
    private BigDecimal unitPrice;
    /**
     * 折后价
     */
    private BigDecimal discountPrice;

    private String goodsPackNo;
    private String goodsPackName;
    private BigDecimal applyUnitQty;
    private BigDecimal realUnitQty;


}

