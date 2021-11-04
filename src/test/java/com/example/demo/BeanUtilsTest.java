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
        System.out.println("studentList--->"+studentList);
        System.out.println("students--->"+students);

        PackGroupCondition condition1 = new PackGroupCondition();
        List<String> warehouseNoList = new ArrayList<>();
        warehouseNoList.add("1");
        warehouseNoList.add("2");
        warehouseNoList.add("3");
        condition1.setWarehouseNoList(warehouseNoList);

        PackGroupCondition condition2 = new PackGroupCondition();
        BeanUtils.copyProperties(condition1,condition2);
        System.out.println(condition1);
        System.out.println(condition2);
    }
}
