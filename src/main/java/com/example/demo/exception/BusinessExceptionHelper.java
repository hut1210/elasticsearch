package com.example.demo.exception;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/15 14:32
 */
public final class BusinessExceptionHelper {
    private BusinessExceptionHelper() {}

    public static void checkArgument(boolean expression, String message) {
        if(!expression) {
            throw new BusinessException(message);
        }
    }
}
