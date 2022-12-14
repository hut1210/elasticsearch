package com.example.demo.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.example.demo.domain.Demo;
import com.example.demo.dto.Result;
import com.example.demo.easyexcel.CommentWriteHandler;
import com.example.demo.easyexcel.ExcelError;
import com.example.demo.easyexcel.SendListExcel;
import com.example.demo.easyexcel.SendListListener;
import com.example.demo.easyexcel2.AbstractEasyExcelDataListener;
import com.example.demo.easyexcel2.ExcelDto;
import com.example.demo.easyexcel2.MyDataEasyExcelListener;
import com.example.demo.listener.UploadDataListener;
import com.example.demo.lock.BizException;
import com.example.demo.param.ImportDataDto;
import com.example.demo.util.RedisUtils2;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hut
 * @date 2022/12/13 8:39 下午
 */
@RestController
@RequestMapping("/excel2")
public class Excel2Controller {

    @Autowired
    RedisUtils2 redisUtil;
    @Autowired
    GsonBuilder gsonBuilder;
    private static final String PREFIX = "easyExcel_";
    public static final String SEND_LIST = "sendList_";
    public static final String SEND_LIST_ERROR = "_error";
    public static final Long EXPIRE_TIME = 60 * 10L;

    @GetMapping(value = "importExcel")
    public Result importExcel(
            @RequestParam(value = "file", required = true) MultipartFile file
    ) throws IOException {
        AbstractEasyExcelDataListener listener = new MyDataEasyExcelListener();
        EasyExcel.read(file.getInputStream()).sheet().headRowNumber(1).registerReadListener(listener).doRead();
        ExcelDto excelDto = listener.getExcelDto();
        return Result.ok(excelDto);
    }

    @GetMapping(value = "importExcel2")
    public Result importExcel2(
            @RequestParam(value = "file", required = true) MultipartFile file
    ) throws IOException {
        ImportDataDto importDataDto = new ImportDataDto();
        importDataDto.setRowCountLimit(1000);
        List failDataList = new ArrayList();
        importDataDto.setFailDataList(failDataList);
        UploadDataListener uploadDataListener = new UploadDataListener<Demo>(importDataDto) {
            @Override
            public void validData(Demo data) {
                System.out.println(data);
                if (!isNumber(data.getNum(), 2)) {
                    throw new BizException(500, data.getNum() + "不是数字");
                }
            }
        };
        EasyExcel.read(file.getInputStream()).head(Demo.class).registerReadListener(uploadDataListener).sheet().headRowNumber(1).doRead();

        return Result.ok(importDataDto);
    }

    public static boolean isNumber(String str, int scale) {
        String reg = "^(\\d{1,8})(\\.\\d{1," + scale + "})?$";
        return str.matches(reg);

    }

    @GetMapping("downloadExcelTemplate")
    //@ApiOperation("下载Excel模板")
    public void export(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "发送名单管理模板.xlsx";
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        EasyExcel.write(response.getOutputStream(), SendListExcel.class).sheet("sheet1").doWrite(new ArrayList<SendListExcel>());
    }

    @GetMapping("downloadErrorExcel")
    /*@ApiImplicitParams(value = {
            @ApiImplicitParam(name = "uuid", dataType = "String", value = "校验参数失败后，返回的uuid")
    })
    @ApiOperation("下载批注后到错误excel")*/
    public void downloadErrorExcel(
            HttpServletResponse response,
            @RequestParam(value = "uuid", required = true) String uuid
    ) throws IOException {
        SimpleDateFormat fDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileName = fDate.format(new Date()) + ".xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        CommentWriteHandler commentWriteHandler = new CommentWriteHandler();
        String key = PREFIX + SEND_LIST + uuid;
        String listExcelJson = (String) redisUtil.get(key);
        String listExcelErrorlJson = (String) redisUtil.get(key + SEND_LIST_ERROR);
        Gson gson = gsonBuilder.create();
        if (listExcelJson != null && listExcelErrorlJson != null) {
            Type listExcelJsonType = new TypeToken<List<SendListExcel>>() {
            }.getType();
            List<SendListExcel> sendListExcels = gson.fromJson(listExcelJson, listExcelJsonType);
            Type listExcelErrorlJsonType = new TypeToken<Map<Integer, List<ExcelError>>>() {
            }.getType();
            Map<Integer, List<ExcelError>> errorMap = gson.fromJson(listExcelErrorlJson, listExcelErrorlJsonType);
            commentWriteHandler.setExcelErrorMap(errorMap);
            EasyExcel.write(response.getOutputStream(), SendListExcel.class)
                    .inMemory(Boolean.TRUE)
                    .sheet("sheet1")
                    //注册批注拦截器
                    .registerWriteHandler(commentWriteHandler)
                    .doWrite(sendListExcels);
        }
    }
}
