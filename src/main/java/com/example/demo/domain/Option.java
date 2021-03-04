package com.example.demo.domain;

import java.io.Serializable;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/3/3 17:42
 */
public class Option implements Serializable {
    String code;
    String name;

    public Option() {
    }

    public Option(String code, String value) {
        this.code=code;
        this.name=value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
