package com.example.demo;

import lombok.Data;

import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/5 17:50
 */
@Data
public class Student {

    private String name;

    private Integer age;

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
}
