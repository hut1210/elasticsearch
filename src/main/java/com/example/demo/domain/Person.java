package com.example.demo.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/4/25 14:43
 */
@Data
public class Person {
    private Long id;
    private String name;
    private Date birthDay;
}
