package com.example.demo.domain.convert;

import com.example.demo.domain.WarnRules;
import com.example.demo.dto.WarnRulesDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/11 11:03
 */
@Mapper
public interface WarnRulesDtoConvert extends ConvertDtoPoList<WarnRulesDto, WarnRules> {

    WarnRulesDtoConvert INSTANCE = Mappers.getMapper(WarnRulesDtoConvert.class);
}
