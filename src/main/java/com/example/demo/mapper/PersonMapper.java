package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.Person;
import org.springframework.stereotype.Repository;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/4/25 14:44
 */
@Repository
public interface PersonMapper extends BaseMapper<Person> {

}
