package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/2/17 11:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("goods_category")
public class GoodsCategory implements Serializable {

    private Long id;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 父id
     */
    private Long pid;
    /**
     * 分类等级
     */
    private Integer level;

    /**
     * 层级编码
     */
    private String treeCode;

    /**
     * 租户id
     */
    private String tenantId;

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

    @TableField(exist=false)
    private List<GoodsCategory> children;
}
