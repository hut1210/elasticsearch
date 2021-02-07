package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/4 17:56
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndexOverviewDto {
    private String title;
    private Integer num;
    private String percent;
    private String note;
}
