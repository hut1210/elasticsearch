package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/10 19:59
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonDeliveryDto {
    private String amount;
    private String key;
}
