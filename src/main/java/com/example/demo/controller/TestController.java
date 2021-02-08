package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.dto.TestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/7 11:53
 */
@RestController
@RequestMapping("test")
public class TestController {

    @PostMapping("/index")
    public String index(@RequestBody Map<String, Object> requestMap){
        System.out.println("requestMap===="+requestMap.toString());
        Map map = new HashMap<>();
        map.put("code",200);
        map.put("msg","请求成功");
        map.put("data",null);
        return JSONObject.toJSONString(map);
    }
}
