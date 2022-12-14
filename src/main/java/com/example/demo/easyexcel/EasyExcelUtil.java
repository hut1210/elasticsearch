package com.example.demo.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @author hut
 * @date 2022/12/13 8:55 下午
 */
@Slf4j
public class EasyExcelUtil {
    /**
     * 获取Excel单元格的索引
     *
     * @param obj        JavaBean对象
     * @param fieldValue JavaBean字段值
     * @return
     */
    public static Integer getCellIndex(Object obj, String fieldValue) {
        try {
            Field declaredField = obj.getClass().getDeclaredField(fieldValue);
            ExcelProperty annotation = declaredField.getAnnotation(ExcelProperty.class);
            if (annotation == null) {
                return null;
            }
            return annotation.index();
        } catch (NoSuchFieldException e) {
            log.error("error:", e);
        }
        return null;
    }
}
