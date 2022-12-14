package com.example.demo.easyexcel;

import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * @author hut
 * @date 2022/12/13 8:54 下午
 */
public class CommentWriteHandler extends AbstractRowWriteHandlerAdapter {
    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
        if (!isHead){
            Sheet sheet = writeSheetHolder.getSheet();
            if (excelErrorMap.containsKey(relativeRowIndex)) {
                List<ExcelError> excelErrors = excelErrorMap.get(relativeRowIndex);
                excelErrors.forEach(obj -> {
                    setCellCommon(sheet, obj.getRow() + 1, obj.getColumn(), obj.getErrorMsg());
                });
            }
        }
    }
}
