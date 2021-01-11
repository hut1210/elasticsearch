package com.example.demo;

import org.elasticsearch.search.aggregations.BucketOrder;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/11 10:59
 */
public class BeanUtilsTest {
    public static void main(String[] args) {
        List<Student> studentList =  new ArrayList<>();
        Student student = new Student();
        student.setAge(18);
        student.setName("zs");
        studentList.add(student);
        /**
         * BeanUtils不能拷贝集合数据
         */
        List<Student> students = new ArrayList<>();
        BeanUtils.copyProperties(studentList,students);
        System.out.println("students--->"+students.size());
    }
}
