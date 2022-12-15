package com.example.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.domain.Demo;
import com.example.demo.domain.Person;
import com.example.demo.domain.convert.PersonConvert;
import com.example.demo.domain.convert.PersonDtoConvert;
import com.example.demo.dto.PersonDto;
import com.example.demo.dto.Result;
import com.example.demo.listener.UploadDataListener;
import com.example.demo.lock.BizException;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.param.ImportDataDto;
import com.example.demo.service.PersonService;
import com.example.demo.template.CacheLoadble;
import com.example.demo.template.RedisCacheTemplate;
import com.example.demo.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/4/25 14:48
 */
@RestController
@RequestMapping("person")
@Slf4j
public class PersonController {

    @Resource
    private PersonMapper personMapper;

    @Resource
    private PersonService personService;

    @Autowired
    private RedisCacheTemplate<Person> redisCacheTemplate;

    @RequestMapping(value = "/getOne/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Person getOne(@PathVariable("name") String name) throws InterruptedException, JsonProcessingException {
        log.info("This is getOnePerson has been called......");
        return redisCacheTemplate.findCache("person:" + name, 30_000, new CacheLoadble<Person>() {
            @Override
            public Person load() {
                log.info("查询数据库 name={}", name);
                //查询数据库
                LambdaQueryWrapper<Person> lambdaQueryWrapper = new LambdaQueryWrapper();
                lambdaQueryWrapper.eq(Person::getName, name);
                Person person = personMapper.selectOne(lambdaQueryWrapper);
                return person;
            }
        }, true);
    }

    @ResponseBody
    @PostMapping("/save")
    public String savePerson(@RequestBody Person person){
        personMapper.insert(person);
        return "Success";
    }
    @GetMapping(value = "importExcel")
    public Result importExcel(
            @RequestParam(value = "file", required = true) MultipartFile file
    ) throws IOException {
        ImportDataDto importDataDto = new ImportDataDto();
        importDataDto.setRowCountLimit(1000);
        List failDataList = new ArrayList();
        importDataDto.setFailDataList(failDataList);
        UploadDataListener uploadDataListener = new UploadDataListener<PersonDto>(importDataDto) {
            @Override
            public void validData(PersonDto data) {
                System.out.println(data);
                if(StringUtils.isBlank(data.getName())){
                    throw new BizException(500,"姓名不能为空");
                }
                if(StringUtils.isBlank(data.getBirthDay())){
                    throw new BizException(500,"生日不能为空");
                }
            }

            @Override
            public void saveData(List<PersonDto> cachedDataList) {
                //personService.saveBatch(PersonConvert.INSTANCT.convert(cachedDataList));
                personService.saveBatch(PersonDtoConvert.INSTANCE.dtoToPo(cachedDataList));
            }

        };
        EasyExcel.read(file.getInputStream()).head(PersonDto.class).registerReadListener(uploadDataListener).sheet().headRowNumber(1).doRead();

        return Result.ok(importDataDto);
    }

}
