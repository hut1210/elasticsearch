package com.example.demo.domain.convert;

import com.example.demo.domain.Person;
import com.example.demo.dto.PersonDto;
import com.example.demo.util.DateUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author hut
 * @date 2022/12/15 8:30 下午
 */
@Mapper(uses = DateMapper.class)
public interface PersonDtoConvert extends ConvertDtoPoList<PersonDto, Person>{

    PersonDtoConvert INSTANCE = Mappers.getMapper(PersonDtoConvert.class);
}
