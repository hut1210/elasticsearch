package com.example.demo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/21 9:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enterprise {

    private Long id;
    private String enterpriseNo;
    private String enterpriseName;
    private String orderType;
    private String orderTypeName;
    private Integer status;
}
