package com.example.demo.domain.convert;

import com.example.demo.domain.Person;
import com.example.demo.dto.PersonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hut
 * @date 2022/12/15 9:19 下午
 */
@Mapper(uses = DateMapper.class)
public interface PersonConvert {
    PersonConvert INSTANCT = Mappers.getMapper(PersonConvert.class);

    /*@Mapping(target = "birthDay", dateFormat = "yyyy-MM-dd")*/
    // 忽略id，不进行映射
    @Mapping(target = "id", ignore = true)
    Person convert(PersonDto personDto);

    List<Person> convert(List<PersonDto> personDto);
}
