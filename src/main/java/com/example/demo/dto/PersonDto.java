package com.example.demo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author hut
 * @date 2022/12/15 8:27 下午
 */
@Data
public class PersonDto {
    @ExcelProperty(value = "名字",index = 0)
    private String name;
    @ExcelProperty(value = "生日",index = 1)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone ="GMT+8")
    private String birthDay;
}
