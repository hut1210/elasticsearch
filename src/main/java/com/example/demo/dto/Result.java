package com.example.demo.dto;

import com.example.demo.enums.ErrorCode;
import lombok.Data;

/**
 * @author hut
 * @date 2022/12/13 8:45 下午
 */
@Data
//@Schema(description = "响应")
public class Result<T> {
    //@Schema(description = "编码 0表示成功，其他值表示失败")
    private int code = 0;

    //@Schema(description = "消息内容")
    private String msg = "success";

    //@Schema(description = "响应数据")
    private T data;

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error() {
        return error(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    public static <T> Result<T> error(String msg) {
        return error(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), msg);
    }

    public static <T> Result<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg());
    }

    public static <T> Result<T> error(int code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
