package com.example.demo.rule;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/22 11:27
 */
public class AddressRule extends AbstractRule {
    @Override
    public boolean execute(RuleDto dto) {
        System.out.println("AddressRule invoke!");
        if (dto.getAddress().startsWith(RuleConstant.MATCH_ADDRESS_START)) {
            return true;
        }
        return false;
    }
}
