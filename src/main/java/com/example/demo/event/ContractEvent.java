package com.example.demo.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @author huteng5
 * @version 1.0
 * @date 2020/12/31 15:55
 */
public class ContractEvent extends ApplicationEvent {

    private String data;

    public ContractEvent(Object source,String data) {
        super(source);
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
