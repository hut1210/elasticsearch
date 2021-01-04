package com.example.demo.builder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 *
 * @author mafayun
 * @Date 2019-12-16 16:15
 */
public interface BuilderHelper<T> {

    boolean isOperate();

    void fill(Field field, Annotation annotation, Object value);

    void build(T t);

}
