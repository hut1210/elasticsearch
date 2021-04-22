package com.example.demo.rule;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/22 11:26
 * 规则模板
 */
public abstract class AbstractRule implements BaseRule{

    protected <T> T convert(RuleDto dto) {
        return (T) dto;
    }

    @Override
    public boolean execute(RuleDto dto) {
        return executeRule(convert(dto));
    }

    protected <T> boolean executeRule(T t) {
        return true;
    }
}
