package com.example.demo.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/17 11:37
 */
public class Request<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Valid
    @NotNull(message = "请求信息为空！")
    private T data;

    public Request() {
    }

    public Request(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
