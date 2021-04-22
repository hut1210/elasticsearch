package com.example.demo.rule;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/22 11:25
 * 规则抽象
 */
public interface BaseRule {
    /**
     * 执行方法
     * @param dto
     * @return
     */
    boolean execute(RuleDto dto);
}
