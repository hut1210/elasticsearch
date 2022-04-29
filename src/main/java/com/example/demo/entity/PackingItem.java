package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/2/15 15:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("goods_packing_item")
public class PackingItem implements Serializable {
    private Long id;

    private Long packingId;

    /**
     * 平台包装单位等级（1 2 3 4）
     */
    @NotNull(message = "平台包装单位等级不能为空")
    private Integer uomLevel;

    /**
     * 平台包装单位编码（EA IP CS PL）
     */
    private String uomNo;

    /**
     * 包装单位编码
     */
    @NotBlank(message = "包装单位编码不能为空")
    private String outUomNo;

    /**
     * 包装单位名称
     */
    @NotBlank(message = "包装单位名称不能为空")
    private String outUomName;

    /**
     * 主单位数量
     */
    @NotNull(message = "主单位数量不能为空")
    private Integer mainUnitQty;

    /**
     * 长
     */
    @Digits(integer = 17, fraction = 3, message = "长为数字，整数位最大{integer}位，小数最大{fraction}位")
    private BigDecimal length;

    /**
     * 宽
     */
    @Digits(integer = 17, fraction = 3, message = "宽必须为数字，整数位最大{integer}位，小数最大{fraction}位")
    private BigDecimal width;

    /**
     * 高
     */
    @Digits(integer = 17, fraction = 3, message = "高必须为数字，整数位最大{integer}位，小数最大{fraction}位")
    private BigDecimal height;

    /**
     * 体积
     */
    @Digits(integer = 17, fraction = 3, message = "体积必须为数字，整数位最大{integer}位，小数最大{fraction}位")
    private BigDecimal volume;

    /**
     * 包装重量
     */
    private BigDecimal packWeight;

    /**
     * 字段添加填充内容
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
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
     * 删除标志(0有效 1无效)
     */
    private Byte isDelete;
}
