package com.example.demo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/20 20:50
 */
@Data
public class EnterpriseDto {

    private String enterprise_no;
    private String enterprise_name;
    private List<OrderTypeDto> orderTypeDtoList;
}
