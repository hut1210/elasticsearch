package com.example.demo.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/8 11:49
 */
public class BeanUtil {

    public static void setCommonValues(Object obj) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> aClass = obj.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);

            Method method = null;
            switch (name){
                case "createTime":
                case "updateTime":
                    method = aClass.getDeclaredMethod(methodName, Date.class);
                    method.invoke(obj, new Date());
                    break;
                case "createUser":
                case "updateUser":
                    method = aClass.getDeclaredMethod(methodName, String.class);
                    method.invoke(obj, "1");
                    break;
                case "isDelete":
                    method = aClass.getDeclaredMethod(methodName, int.class);
                    method.invoke(obj, 0);
                    break;
            }

        }
    }
}
