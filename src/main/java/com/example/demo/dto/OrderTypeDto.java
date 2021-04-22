package com.example.demo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/20 20:51
 */
@Data
public class OrderTypeDto {
    private String orderType;
    private String orderTypeName;
    private List<PlanStatusDto> planStatusDtoList;
}
