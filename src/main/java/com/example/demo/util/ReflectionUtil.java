package com.example.demo.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiPredicate;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/15 14:28
 */
public class ReflectionUtil {

    private static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    public static Method findSimpleMethod(Class<?> clazz, String name) {

        if (!org.apache.commons.lang3.StringUtils.isEmpty(name)) {
            Class searchType = clazz;
            Method[] methods = searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods();
            int var6 = methods.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                Method methodprim = methods[var7];
                if (name.equals(methodprim.getName())) {
                    return methodprim;
                }
            }
        }
        return null;
    }

    public static Object getPropertyValue(Object dataItem, String... propertyNames) {
        Object result = dataItem;
        for (String name : propertyNames) {
            String methodName = "get" + upCaps(name.charAt(0)) + name.substring(1);
            try {
                result = result.getClass().getMethod(methodName).invoke(result);
            } catch (IllegalAccessException e) {
                return null;
            } catch (InvocationTargetException e) {
                return null;
            } catch (NoSuchMethodException e) {
                return null;
            }
            if (result == null) {
                return null;
            }
        }
        return result;
    }

    private static char upCaps(char c) {
        if (c >= 'a' && c <= 'z') {
            return (char) (c - 32);
        }
        return c;
    }

    /**
     * @Description: java类属性转数据库字段
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/7/20
     */
    public static String propertyToField(String property) {
        if (null == property) {
            return "";
        }
        char[] chars = property.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char c : chars) {
            if (CharUtils.isAsciiAlphaUpper(c)) {
                sb.append("_" + org.apache.commons.lang3.StringUtils.lowerCase(CharUtils.toString(c)));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * @Description:  数据库字段转java类属性
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/7/20
     */
    public static String fieldToProperty(String field) {
        if (null == field) {
            return "";
        }
        char[] chars = field.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    sb.append(org.apache.commons.lang3.StringUtils.upperCase(CharUtils.toString(chars[j])));
                    i++;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * @Description: 获取对象所有字段
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/7/21
     */
    public static List<Field> getObjectAllField(Object object) {
        return getAllField(object.getClass());
    }

    /**
     * @Description: 获取对象所有字段
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/7/21
     */
    public static List<Field> getAllField(Class clazz) {
        // 获取该类的成员变量
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }

    /**
     * @Description: 组装数据
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/7/21
     */
    public static Object assembleObject(Object object, List<Field> fieldList, Map<String, String[]> parameterMap) throws Exception {
        for (Field field : fieldList) {
            if (parameterMap.containsKey(field.getName())) {
                field.setAccessible(true);
                String value = parameterMap.get(field.getName())[0];
                if (StringUtils.isBlank(value)) {
                    continue;
                }
                if (field.getType().getCanonicalName().equals("int")) {
                    field.setInt(object, Integer.parseInt(value));
                } else if (field.getType().getCanonicalName().equals("double")) {
                    field.setDouble(object, Double.parseDouble(value));
                } else if (field.getType().getCanonicalName().equals("boolean")) {
                    field.setBoolean(object, Boolean.parseBoolean(value));
                } else if (field.getType().getCanonicalName().equals("java.util.Date")) {
                    field.set(object, DateUtil.parseDate(value));
                } else if (field.getType().getCanonicalName().equals("java.lang.String")) {
                    field.set(object, value);
                }
            }
        }
        return object;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @return 父类中的方法对象
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return null;
    }

    /**
     * 直接调用对象方法, 而忽略修饰符(private, protected, default)
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @param parameters     : 父类中的方法参数
     * @return 父类中方法的执行结果
     */
    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes,
                                      Object[] parameters) {
        //根据 对象、方法名和对应的方法参数 通过反射 调用上面的方法获取 Method 对象
        Method method = getDeclaredMethod(object, methodName, parameterTypes);

        if (method != null) {
            //抑制Java对方法进行检查,主要是针对私有方法而言
            method.setAccessible(true);
            try {
                //调用object 的 method 所代表的方法，其方法的参数是 parameters
                return method.invoke(object, parameters);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("调用方法出错", e);
            }
        } else {
            throw new RuntimeException("调用方法出错");
        }
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return null;
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @param value     : 将要设置的值
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);
        //抑制Java对其的检查
        if (field != null) {
            field.setAccessible(true);
            try {
                //将 object 中 field 所代表的值 设置为 value
                field.set(object, value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException("设置属性值出错", e);
            }
        } else {
            throw new RuntimeException("没有获取到对应属性");
        }
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @return : 父类中的属性值
     */
    public static Object getFieldValue(Object object, String fieldName) {

        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);
        if(field == null){
            return null;
        }
        //抑制Java对其的检查
        field.setAccessible(true);
        try {
            //获取 object 中 field 所代表的属性值
            return field.get(object);
        } catch (Exception e) {
            throw new RuntimeException("没有获取到对应属性的属性值", e);
        }
    }

    /**
     * @Description: 两个Object进行比较
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 18/05/2021
     */
    public static Map<String, String> compareTwoObject(Object obj1, Object obj2, String[] ignoreFields) throws IllegalAccessException {
        Map<String, String> diffMap = new LinkedHashMap<>();
        List<String> ignoreFieldList = Arrays.asList(ignoreFields);
        Class<?> clazz1 = obj1.getClass();
        Class<?> clazz2 = obj2.getClass();
        Field[] fields1 = clazz1.getDeclaredFields();
        Field[] fields2 = clazz2.getDeclaredFields();
        BiPredicate biPredicate = (object1, object2) -> {
            if (object1 == null && object2 == null) {
                return true;
            }
            if (object1 == null && object2 != null){
                return false;
            }
            if (object1 instanceof BigDecimal && object2 instanceof BigDecimal) {
                if (object1 == null && object2 == null) {
                    return true;
                }
                if (object1 == null ^ object2 == null) {
                    return false;
                }
                return ((BigDecimal) object1).compareTo((BigDecimal) object2) == 0;
            }

            if (object1.equals(object2)) {
                return true;
            }
            return false;
        };

        for (Field field1 : fields1) {
            for (Field field2 : fields2) {
                if (!ignoreFieldList.contains(field1.getName()) || !ignoreFieldList.contains(field2.getName())) {
                    continue;
                }
                if (field1.getName().equals(field2.getName())) {
                    field1.setAccessible(true);
                    field2.setAccessible(true);
                    if (!biPredicate.test(field1.get(obj1), field2.get(obj2))) {
                        diffMap.put(field1.getName(), field1.getName());
                    }
                }
            }
        }
        return diffMap;
    }

    /**
     * @Description: 对象转换为Map
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 18/05/2021
     */
    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if(field.get(obj) != null){
                map.put(field.getName(), field.get(obj));
            }
        }
        return map;
    }
}
