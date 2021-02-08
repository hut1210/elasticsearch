package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/7 11:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {

    private String name;
    private int age;
}
