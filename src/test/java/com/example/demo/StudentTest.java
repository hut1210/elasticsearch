package com.example.demo;

import com.example.demo.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.ml.EvaluateDataFrameRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/5 17:50
 */
//@SpringBootTest
@Slf4j
public class StudentTest {

    @Test
    public void test() throws Exception {
        Student student = new Student();
        setStudent(student);
        System.out.println(student);

        Student student2 = new Student();
        setStudent(student2);
        System.out.println(student2);

        Student aClass = new Student();
        /*setCommonValues(aClass);
        System.out.println(aClass);*/
        BeanUtil.setCommonValues(aClass);
        System.out.println(aClass);
    }

    private void setStudent(Student student){
        student.setName("zs");
        student.setAge(18);
    }

    public static void setCommonValues(Object obj) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> aClass = obj.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField.getName());
            String name = declaredField.getName();
            String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
            /*if(name.equals("createTime")){
                Method method = aClass.getDeclaredMethod(methodName, Date.class);
                method.invoke(obj, new Date());
            }
            if(name.equals("createUser")){
                Method method = aClass.getDeclaredMethod(methodName, String.class);
                method.invoke(obj, "1");
            }
            if(name.equals("updateTime")){
                Method method = aClass.getDeclaredMethod(methodName, Date.class);
                method.invoke(obj, new Date());
            }
            if(name.equals("updateUser")){
                Method method = aClass.getDeclaredMethod(methodName, String.class);
                method.invoke(obj, "1");
            }
            if(name.equals("isDelete")){
                Method method = aClass.getDeclaredMethod(methodName, int.class);
                method.invoke(obj, 0);
            }*/
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

        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            //System.out.println(declaredMethod);
        }

    }
}
