package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/2/15 14:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("goods_packing")
public class Packing extends BaseEntity {

    /**
     * 租户编码
     */
    @NotBlank(message = "租户编码不能为空")
    private String tenantNo;

    /**
     * 所属企业编码
     */
    @NotBlank(message = "所属企业编码不能为空")
    private String enterpriseNo;

    /**
     * 所属企业名称
     */
    private String enterpriseName;

    /**
     * 平台包装策略编码
     */
    private String packNo;

    /**
     * 包装策略编码
     */
    private String outPackNo;

    /**
     * 包装策略名称
     */
    private String packName;

    /**
     * 状态
     */
    private Byte status;
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
    @TableLogic
    private Byte isDelete;
}
