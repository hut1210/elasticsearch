package com.example.demo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/27 11:43
 */
@Data
public class OptionExtendDto implements Serializable {
    boolean sel;
    Integer type;
    String name;
    String value;

    public OptionExtendDto() {
    }

}
