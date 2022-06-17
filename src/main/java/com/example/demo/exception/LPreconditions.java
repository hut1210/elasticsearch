package com.example.demo.exception;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/15 14:26
 */
public class LPreconditions {

    /**
     * 判断参数是否为空，如果为空，抛出IllegalArgumentException
     * @param reference
     * @param errorMessage
     * @param <T>
     * @return
     */
    public static <T> T checkParameterNotNull(T reference,  Object errorMessage) {
        if (reference == null) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
        return reference;
    }
    /**
     * 判断结果是否为空，如果为空，抛出BusinessException
     * @param reference
     * @param errorMessage
     * @param <T>
     * @return
     */
    public static <T> T checkResultNotNull(T reference,  Object errorMessage) {
        if (reference == null) {
            throw new BusinessException(String.valueOf(errorMessage));
        }
        return reference;
    }

    public static void checkParameterNotNull(String paramter, String errorMessage){
        Preconditions.checkArgument(StringUtils.isNotBlank(paramter), errorMessage);
    }

    public static void checkParameterNotNull(Collection<?> collection, String errorMessage){
        Preconditions.checkArgument(!CollectionUtils.isEmpty(collection), errorMessage);
    }

    public static void checkParameterNotNull(Map<?, ?> map, String errorMessage){
        Preconditions.checkArgument(!CollectionUtils.isEmpty(map), errorMessage);
    }
}

