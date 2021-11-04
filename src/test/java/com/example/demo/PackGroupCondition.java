package com.example.demo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/10/27 16:00
 */
@Data
public class PackGroupCondition implements Serializable {
    /**
     * 仓库编码集合
     */
    private List<String> warehouseNoList;
}
