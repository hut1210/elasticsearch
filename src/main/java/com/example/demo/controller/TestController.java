package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.apiversion.annotation.ApiVersion;
import com.example.demo.condition.CommonDeliveryCondition;
import com.example.demo.domain.User;
import com.example.demo.dto.TestDto;
import com.example.demo.enums.PostUrlEnum;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.DateUtils;
import com.example.demo.util.HttpUtil;
import org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/7 11:53
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Resource
    private HttpUtil httpUtil;

    @Resource
    private UserMapper userMapper;
    @PostMapping("/index")
    public String index(@RequestBody Map<String, Object> requestMap){
        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        if (StringUtils.isEmpty(commonDeliveryCondition.getCreateTimeStart())) {
            commonDeliveryCondition.setCreateTimeStart(DateUtils.formatDate(DateUtils.getDateForBegin(new Date(), -8), DateUtils.DATE_FORMAT));
        }
        if (StringUtils.isEmpty(commonDeliveryCondition.getCreateTimeEnd())) {
            commonDeliveryCondition.setCreateTimeEnd(DateUtils.formatDate(DateUtils.getDateForEnd(new Date(), -1), DateUtils.DATE_FORMAT));
        }
        String result = httpUtil.doPost(JSONObject.toJSONString(commonDeliveryCondition), PostUrlEnum.COMMONDELIVERYINDEXOVERVIEW.getUrl());
        System.out.println("result===="+result);
        System.out.println("requestMap===="+requestMap.toString());
        Map map = new HashMap<>();
        map.put("code",200);
        map.put("msg","请求成功");
        map.put("data",result);
        return JSONObject.toJSONString(map);
    }

    @GetMapping("getUserList")
    public String getUserList(){
        System.out.println("当前是1.0版本");
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);

        return JSONObject.toJSONString(users);
    }

    @GetMapping("getUserList")
    @ApiVersion(2.0)
    public String getUserList2(){
        System.out.println("当前是2.0版本");
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);

        return JSONObject.toJSONString(users);
    }

    @PostMapping("getUserList")
    @ApiVersion(3.0)
    public String getUserList3(){
        System.out.println("当前是3.0版本");
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);

        return JSONObject.toJSONString(users);
    }
}
