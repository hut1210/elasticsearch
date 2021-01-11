package com.example.demo.event;

import com.example.demo.domain.WarnRules;
import org.springframework.context.ApplicationEvent;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/8 21:47
 */
public class WarnEvent extends ApplicationEvent {

    private String data;

    private WarnRules warnRules;

    public WarnEvent(Object source,WarnRules warnRules) {
        super(source);
        this.warnRules = warnRules;
    }

    public WarnRules getWarnRules() {
        return warnRules;
    }
}
