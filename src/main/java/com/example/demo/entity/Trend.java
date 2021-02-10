package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/9 13:58
 */
@Data
public class Trend implements Serializable {

    private Long value;

    private String name;

    private BigDecimal value1;

    @Override
    public String toString() {
        return "Trend{" +
                "value=" + value +
                ", name='" + name + '\'' +
                ", value1=" + value1 +
                '}';
    }
}
