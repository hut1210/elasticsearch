package com.example.demo.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/15 14:28
 */
@Slf4j
public class ExcelUtil {

    public ExcelUtil() {
    }

    /**
     * 导出execl
     *
     * @param response 响应体
     * @param list     数据
     * @param clazz    类
     * @param <T>
     */
    public static <T> void downExcelUtil(HttpServletResponse response, String fileName, List<T> list, Class<T> clazz) {
        Set<String> excludeColumnFiledNames = new HashSet<>();
        excludeColumnFiledNames.add("version");
        excludeColumnFiledNames.add("isDelete");
        excludeColumnFiledNames.add("id");
        excludeColumnFiledNames.add("warehouseNo");
        excludeColumnFiledNames.add("tenantNo");
        excludeColumnFiledNames.add("createUser");
        excludeColumnFiledNames.add("createTime");
        excludeColumnFiledNames.add("updateUser");
        excludeColumnFiledNames.add("updateTime");
        downExcelUtil(response, fileName, list, clazz, excludeColumnFiledNames);
    }


    /**
     * 导出execl
     *
     * @param response 响应体
     * @param list     数据
     * @param clazz    类
     * @param <T>
     */
    public static <T> void downExcelUtil(HttpServletResponse response, String fileName, List<T> list, Class<T> clazz, Set<String> excludeColumnFiledNames) {
        try {
            fileName = fileName + "_" + DateUtil.formatDate(new Date(), DateUtil.Pattern.Date.YYYY_MM_DD_3);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            if (list.isEmpty()) {
                return;
            }
            EasyExcel.write(response.getOutputStream(), clazz)
                    .registerWriteHandler(createTableStyle())
                    .excludeColumnFiledNames(excludeColumnFiledNames)
                    .sheet("data")
                    .doWrite(list);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private static WriteHandler createTableStyle() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为红色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        // 设置字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);
        headWriteFont.setBold(false);
        headWriteCellStyle.setWriteFont(headWriteFont);
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();

        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short) 10);
        contentWriteCellStyle.setWriteFont(contentWriteFont);

        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }


    public static <T> List<T> uploadExcel(InputStream inputStream, Class<T> tClass, Integer headRowNumber) {
        final List<T> rowsList = new ArrayList<T>();
        ReadListener readListener = new AnalysisEventListener<T>() {
            @Override
            public void invoke(T data, AnalysisContext context) {
                rowsList.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        };
        EasyExcel.read(inputStream, tClass, readListener).sheet().headRowNumber(headRowNumber).doRead();
        return rowsList;
    }

    public static <T> List<T> uploadExcel(InputStream inputStream, Class<T> tClass) {
        return uploadExcel(inputStream, tClass, 1);
    }

    public static List<Field> getValueFieldList(Class tClass){
        List<Field> fieldList = ReflectionUtil.getAllField(tClass);
        List<Field> valueFieldList = new ArrayList<>();
        for (Field field : fieldList) {
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if(Objects.isNull(excelProperty)){
                continue;
            }
            valueFieldList.add(field);
        }
        return valueFieldList;
    }
}
