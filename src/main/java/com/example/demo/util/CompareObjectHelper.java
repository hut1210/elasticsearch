package com.example.demo.util;

import com.example.demo.annotation.PropertyName;
import com.example.demo.enums.CompareObjectEnum;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/1/13 13:56
 */
public class CompareObjectHelper<T> {
    private static List<String> ignoreList = new ArrayList(Arrays.asList("serialVersionUID", "createTime", "updateTime", "createUser", "updateUser", "ts"));

    public static <T> CompareObjectHelper<T> getInstance() {
        return new CompareObjectHelper<T>();
    }

    public void doAround(T oldObj, T newObj, CompareObjectProcessor compareObjectProcessor) throws Exception {
        try {
            Class clazz = oldObj.getClass();
            Field[] fields = newObj.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!ignoreList.contains(field.getName())) {
                    PropertyName annotation;
                    annotation = field.getAnnotation(PropertyName.class);
                    if (annotation != null) {
                        PropertyDescriptor pd;
                        pd = new PropertyDescriptor(field.getName(), clazz);
                        Method getMethod = pd.getReadMethod();
                        Object o1 = getMethod.invoke(oldObj);
                        Object o2 = getMethod.invoke(newObj);
                        if (o1 == null || o2 == null) {
                            continue;
                        }
                        if (!String.valueOf(o1).equals(String.valueOf(o2))) {
                            compareObjectProcessor.doProcess(field, annotation, o1, o2);
                        }
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
