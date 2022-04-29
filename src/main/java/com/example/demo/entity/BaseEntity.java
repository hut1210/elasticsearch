package com.example.demo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/2/21 10:54
 */
@NoArgsConstructor
@Data
public class BaseEntity implements Serializable {
    private Long id;
}
