package com.example.demo.dto;

import lombok.Data;

import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/28 14:10
 */
@Data
public class PieChartCollect {

    private String title;

    private Integer totalAmount;

    private List<PieChart> list;
}
