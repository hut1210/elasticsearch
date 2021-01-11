package com.example.demo.domain;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2020/12/17 18:15
 */
@Data
public class WarnSubscribe implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 订阅人姓名
     */
    private String  subscriberName;
    /**
     *预警类型
     */
    private Integer warnType;
    /**
     * 预警指标
     */
    private Integer warnTarget;
    /**
     * 预警方式
     */
    private Integer warnMode;
    /**
     * 预警任务
     */
    private Long warnRulesId;
    /**
     *订阅有效期开始时间
     */
    private Date validityPeriodStart;
    /**
     *订阅有效期结束时间
     */
    private Date validityPeriodEnd;
    /**
     *邮箱账号
     */
    private String mail;
    /**
     * 备注
     */
    private String remark;
    /**
     * 手机
     */
    private String phone;
    /**
     * 订阅状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 删除标志(0有效 1无效)
     */
    private int isDelete;
    /**
     * 时间戳
     */
    private Timestamp ts;
}
