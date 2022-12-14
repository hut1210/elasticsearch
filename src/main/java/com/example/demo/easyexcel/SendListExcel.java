package com.example.demo.easyexcel;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hut
 * @date 2022/12/13 8:51 下午
 */
@Data
@ExcelIgnoreUnannotated()
@ContentRowHeight(10)
@HeadRowHeight(20)
public class SendListExcel implements Serializable {
    @ExcelProperty(value = "账号",index = 0)
    @ColumnWidth(20)
    private String account;
    @ExcelProperty(value = "模板编号",index = 1)
    @ColumnWidth(30)
    private String templateCode;
    @ExcelProperty(value = "类型",index = 2)
    @ColumnWidth(15)
    private String accountType;
}
