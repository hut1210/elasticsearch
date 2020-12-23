package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2020/12/21 16:44
 */
@Document(indexName = "student_index")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @Field(type= FieldType.Auto)
    private String studentId;

    @Field(type=FieldType.Auto)
    private String name;

    @Field(type=FieldType.Auto)
    private Integer age;

    @Field(type=FieldType.Auto)
    private List<Double> scores;
}
