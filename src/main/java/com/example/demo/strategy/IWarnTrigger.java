package com.example.demo.strategy;

import com.example.demo.domain.WarnRules;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/9 12:22
 */
public interface IWarnTrigger {

    /**
     * 触发规则
     */
    void triggerRule(WarnRules warnRules);

}
