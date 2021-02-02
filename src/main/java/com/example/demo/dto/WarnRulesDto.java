package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/11 11:06
 */
@Data
public class WarnRulesDto implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 预警名称
     */
    private String warnName;
    /**
     *预警类型
     */
    private Integer warnType;
    /**
     * 预警指标
     */
    private Integer warnTarget;
    /**
     * 预警时间范围
     */
    private Integer warnTime;
    /**
     * 预警时间范围 开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date warnStartTime;
    /**
     * 预警时间范围 结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date warnEndTime;
    /**
     * 对比公式
     */
    private Integer compareFormula;
    /**
     * 阈值
     */
    private int threshold;
    /**
     *计算时段开始时间
     */
    @JsonFormat(pattern="HH:mm:ss",timezone = "GMT+8")
    private Date calculationPeriodStart;
    /**
     *计算时段结束时间
     */
    @JsonFormat(pattern="HH:mm:ss",timezone = "GMT+8")
    private Date calculationPeriodEnd;
    /**
     * 预警方式
     */
    private Integer warnMode;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 删除标志(0有效 1无效)
     */
    private int isDelete;

    private List<OptionExtendDto> optionExtendDtoList;
}
