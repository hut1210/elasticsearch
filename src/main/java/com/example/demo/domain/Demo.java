package com.example.demo.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hut
 * @date 2022/12/14 9:49 下午
 */
@Data
public class Demo {

    @ExcelProperty(value = "字符串",index = 0)
    private String str;

    @ExcelProperty(value = "数字",index = 1)
    private String num;

    @ExcelProperty(value = "日期",index = 2)
    private String date;
}
