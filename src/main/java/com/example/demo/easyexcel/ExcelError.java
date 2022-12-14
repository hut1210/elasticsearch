package com.example.demo.easyexcel;

import java.io.Serializable;

/**
 * @author hut
 * @date 2022/12/13 8:54 下午
 */
public class ExcelError implements Serializable {
    /** 第几行 从1开始计数 */
    private int row;
    /** 第几列  从1开始计数 */
    private int column;
    /** 错误消息 */
    private String errorMsg;

    public ExcelError(int row, int column, String errorMsg) {
        this.row = row;
        this.column = column;
        this.errorMsg = errorMsg;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public String toString() {
        return "ExcelError{" +
                "row=" + row +
                ", column=" + column +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
