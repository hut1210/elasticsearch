package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/20 20:44
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkAgeDto {

    private String enterprise_no;
    private String enterprise_name;
    private String order_type;
    private String order_type_name;
    private Integer status;
}
