package com.example.demo.lock;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/1/7 10:58
 */
public class ConcurrentLockException extends BizException {

    public ConcurrentLockException(int code, String message) {
        super(code, message);
    }

    public ConcurrentLockException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
