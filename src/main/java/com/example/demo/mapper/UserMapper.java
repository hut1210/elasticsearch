package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.domain.User;
import org.springframework.stereotype.Repository;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/19 10:51
 */
// 在对应的Mapper上面继承基本的类 BaseMapper
@Repository // 代表持久层
public interface UserMapper extends BaseMapper<User> {
// 所有的CRUD操作都已经编写完成了
// 你不需要像以前的配置一大堆文件了！
}
