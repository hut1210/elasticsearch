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
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.mapstruct.ap.shaded.freemarker.template.utility.DateUtil;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试接口
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

    /**
     * 测试
     * @param requestMap
     * @return
     */
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

    /**
     * 获取用户列表版本1.0
     * @return
     */
    @GetMapping("getUserList")
    public String getUserList(){
        System.out.println("当前是1.0版本");
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);

        return JSONObject.toJSONString(users);
    }

    /**
     * 获取用户列表版本2.0
     * @return
     */
   /* @GetMapping("getUserList")
    @ApiVersion(2.0)
    public String getUserList2(){
        System.out.println("当前是2.0版本");
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);

        return JSONObject.toJSONString(users);
    }*/

    /**
     * 获取用户列表版本3.0
     * @return
     */
    /*@PostMapping("getUserList")
    @ApiVersion(3.0)
    public String getUserList3(){
        System.out.println("当前是3.0版本");
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);

        return JSONObject.toJSONString(users);
    }*/

    @Resource
    private ResourceLoader resourceLoader;

    @GetMapping("/download")
    public void download(HttpServletResponse response) {
        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            String filename = "批量导入订单.xlsx";
            String path = "templates/批量导入订单.xlsx";
            org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:"+path);

            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("charset", "utf-8");
            response.addHeader("Pragma", "no-cache");
            String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);

            inputStream = resource.getInputStream();
            servletOutputStream = response.getOutputStream();
            IOUtils.copy(inputStream, servletOutputStream);
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (servletOutputStream != null) {
                    servletOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
