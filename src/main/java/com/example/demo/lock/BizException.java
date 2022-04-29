package com.example.demo.lock;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/1/7 10:58
 */
public class BizException extends RuntimeException {

    /**
     * 自定义异常编号
     */
    private int code;

    public BizException(int code, String message) {
        super(message);
        this.code = code;

    }

    public BizException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }


}
