package com.example.demo.condition;

import com.example.demo.annotation.FilterShould;
import com.example.demo.annotation.Filter_Term;
import com.example.demo.annotation.Filter_Terms;
import lombok.Data;

import java.io.Serializable;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/4 15:17
 */
@Data
public class StudentCondition implements Serializable {
    private static final long serialVersionUID = 1L;

    @Filter_Term(fieldName = "name")
    private String name;

    @Filter_Terms(fieldName = "age",split = ",")
    private String age;
}
