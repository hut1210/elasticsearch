package com.example.demo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/17 14:07
 */
@Slf4j
@Component
public class ObjectFillHandler implements MetaObjectHandler {

    public final static String DEFAULT_USERNAME = "system";

    /**
     * 获取当前用户，为空返回默认system
     *
     * @return
     */
    private String getCurrentUsername() {
        return DEFAULT_USERNAME;
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);

        this.setFieldValByName("createUser", getCurrentUsername(), metaObject);
        this.setFieldValByName("updateUser", getCurrentUsername(), metaObject);
        this.setFieldValByName("isDelete", 0, metaObject);
        this.setFieldValByName("version", 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
