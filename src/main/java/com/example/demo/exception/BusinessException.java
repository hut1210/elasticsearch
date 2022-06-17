package com.example.demo.exception;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/15 14:27
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
