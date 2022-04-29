package com.example.demo.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/11/29 17:23
 */
public class LogData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String opModel;
    private String opCode;
    private String opUserId;
    private Date opTime;
    private String opKey;
    private String opBeforeValue;
    private String opAfterValue;
    private String opContent;
    private Date createTime;
    private Date updateTime;
    private String createUser;
    private String updateUser;
    private byte yn;

    public LogData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpModel() {
        return this.opModel;
    }

    public void setOpModel(String opModel) {
        this.opModel = opModel;
    }

    public String getOpCode() {
        return this.opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getOpUserId() {
        return this.opUserId;
    }

    public void setOpUserId(String opUserId) {
        this.opUserId = opUserId;
    }

    public Date getOpTime() {
        return this.opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public String getOpKey() {
        return this.opKey;
    }

    public void setOpKey(String opKey) {
        this.opKey = opKey;
    }

    public String getOpBeforeValue() {
        return this.opBeforeValue;
    }

    public void setOpBeforeValue(String opBeforeValue) {
        this.opBeforeValue = opBeforeValue;
    }

    public String getOpAfterValue() {
        return this.opAfterValue;
    }

    public void setOpAfterValue(String opAfterValue) {
        this.opAfterValue = opAfterValue;
    }

    public String getOpContent() {
        return this.opContent;
    }

    public void setOpContent(String opContent) {
        this.opContent = opContent;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public byte getYn() {
        return this.yn;
    }

    public void setYn(byte yn) {
        this.yn = yn;
    }
}
