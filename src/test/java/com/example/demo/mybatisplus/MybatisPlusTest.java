package com.example.demo.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.DemoApplication;
import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/19 10:46
 */
@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
public class MybatisPlusTest {

    // 继承了BaseMapper，所有的方法都来自己父类
    // 我们也可以编写自己的扩展方法！
    @Resource
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        // 参数是一个 Wrapper ，条件构造器，这里我们先不用 null
        // 查询全部用户
        System.out.println(userMapper);
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    // 测试插入
    @Test
    public void testInsert() {
        User user = new User();
        user.setName("测试Insert4");
        user.setAge(22);
        user.setEmail("1396833909@qq.com");
        int result = userMapper.insert(user); // 帮我们自动生成id
        System.out.println(result); // 受影响的行数
        System.out.println(user); // 发现，id会自动回填
    }

    // 测试插入
    @Test
    public void testInsert2() {
        User user = new User();
        user.setId(6L);
        user.setName("测试Insert");
        user.setAge(3);
        user.setEmail("1396833909@qq.com");
        int result = userMapper.insert(user); // 帮我们自动生成id
        System.out.println(result); // 受影响的行数
        System.out.println(user); // 发现，id会自动回填
    }

    // 测试更新
    @Test
    public void testUpdate() {
        User user = new User();
        // 通过条件自动拼接动态sql
        user.setId(6L);
        user.setName("测试更新Update");
        user.setAge(18);
        // 注意：updateById 但是参数是一个 对象！
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    // 测试乐观锁成功！
    @Test
    public void testOptimisticLocker() {
        // 1、查询用户信息
        User user = userMapper.selectById(1L);
        // 2、修改用户信息
        user.setName("momo");
        user.setEmail("1396833909@qq.com");
        // 3、执行更新操作
        userMapper.updateById(user);
    }

    // 测试乐观锁失败！多线程下
    @Test
    public void testOptimisticLocker2() {
        // 线程 1
        User user = userMapper.selectById(1L);
        user.setName("kuangshen111");
        user.setEmail("24736743@qq.com");
        // 模拟另外一个线程执行了插队操作
        User user2 = userMapper.selectById(1L);
        user2.setName("kuangshen222");
        user2.setEmail("24736743@qq.com");
        userMapper.updateById(user2);
        // 自旋锁来多次尝试提交！
        userMapper.updateById(user); // 如果没有乐观锁就会覆盖插队线程的值！
    }

    // 测试查询
    @Test
    public void testSelectById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    // 测试批量查询！
    @Test
    public void testSelectByBatchId() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    // 按条件查询之一使用map操作
    @Test
    public void testSelectByMap() {
        HashMap<String, Object> map = new HashMap<>();
        // 自定义要查询
        map.put("name", "Jack");
        map.put("age", 20);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    // 测试分页查询
    @Test
    public void testPage() {
        // 参数一：当前页
        // 参数二：页面大小
        // 使用了分页插件之后，所有的分页操作也变得简单的！
        Page<User> page = new Page<>(1, 5);
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
    }

    // 测试删除
    @Test
    public void testDeleteById() {
        userMapper.deleteById(1384027827814457345L);
    }

    // 通过id批量删除
    @Test
    public void testDeleteBatchId() {
        userMapper.deleteBatchIds(Arrays.asList(6L, 1383987802125021186L));
    }

    // 通过map删除
    @Test
    public void testDeleteMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "kuangshen222");
        userMapper.deleteByMap(map);
    }

    @Test
    public void testSearchByWrapper() {
        // 查询name不为空的用户，并且邮箱不为空的用户，年龄大于等于12
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name")
                .isNotNull("email")
                .ge("age", 12);
        userMapper.selectList(wrapper).forEach(System.out::println);
    }

    @Test
    public void testSearchByWrapper2() {
        // 查询name不为空的用户，并且邮箱不为空的用户，年龄大于等于12
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "测试Insert");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    @Test
    public void testSearchByWrapper3() {
        // 查询年龄在 20 ~ 30 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age", 20, 30); // 区间
        Integer count = userMapper.selectCount(wrapper);// 查询结果数
        System.out.println(count);
    }

    // 模糊查询
    @Test
    public void testSearchByWrapper4() {
        // 查询年龄在 20 ~ 30 岁之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 左和右 t%
        wrapper.notLike("name", "a")
                .likeRight("email", "1");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    // 模糊查询
    @Test
    public void testSearchByWrapper5(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // id 在子查询中查出来
        wrapper.inSql("id","select id from user where id=1384063604896227330");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    //测试六
    @Test
    public void testSearchByWrapper6(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 通过id进行排序
        wrapper.orderByDesc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }
}
