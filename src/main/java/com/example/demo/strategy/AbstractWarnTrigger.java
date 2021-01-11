package com.example.demo.strategy;

import com.example.demo.domain.WarnRules;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/9 12:24
 */
public abstract class AbstractWarnTrigger implements IWarnTrigger{

    /**
     * 触发规则
     */
    public abstract void triggerRule(WarnRules warnRules);
}
