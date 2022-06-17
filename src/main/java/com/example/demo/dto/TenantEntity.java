package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description: 租户基础实体类
 * @author: by hanpeng
 * @create: 2020-09-26 15:38
 **/
@NoArgsConstructor
@Getter
@Setter
public class TenantEntity extends BaseEntity{

    private String tenantNo;
}
