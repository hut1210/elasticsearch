package com.example.demo.page;

import com.example.demo.dto.Request;

import java.io.Serializable;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/17 11:36
 */
public class PageRequest<T> extends Request<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Page page;

    public PageRequest() {
    }

    public PageRequest(T data, Page page) {
        super(data);
        this.page = page;
    }

    public Page getPage() {
        return this.page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
