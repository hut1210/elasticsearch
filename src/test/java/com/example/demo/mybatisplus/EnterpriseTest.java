package com.example.demo.mybatisplus;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.DemoApplication;
import com.example.demo.domain.Enterprise;
import com.example.demo.dto.EnterpriseDto;
import com.example.demo.dto.OrderTypeDto;
import com.example.demo.dto.PlanStatusDto;
import com.example.demo.mapper.EnterpriseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/21 10:02
 */
@SpringBootTest(classes = {DemoApplication.class})
@RunWith(SpringRunner.class)
public class EnterpriseTest {

    @Resource
    private EnterpriseMapper enterpriseMapper;

    @Test
    public void test(){
        QueryWrapper<Enterprise> wrapper = new QueryWrapper<>();
        wrapper.groupBy("enterprise_no","enterprise_name");
        List<Enterprise> enterprises = enterpriseMapper.selectList(wrapper);
        enterprises.forEach(System.out::println);
        List<EnterpriseDto> enterpriseDtoList = new ArrayList<>();
        for (int i = 0; i <enterprises.size() ; i++) {
            String enterpriseNo = enterprises.get(i).getEnterpriseNo();
            EnterpriseDto enterpriseDto = new EnterpriseDto();
            enterpriseDto.setEnterprise_no(enterpriseNo);
            enterpriseDto.setEnterprise_name(enterprises.get(i).getEnterpriseName());

            //分组查询业务类型
            QueryWrapper<Enterprise> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("enterprise_no",enterpriseNo);
            wrapper2.groupBy("order_type","order_type_name");
            List<Enterprise> enterprises2 = enterpriseMapper.selectList(wrapper2);

            List<OrderTypeDto> orderTypeDtoList = new ArrayList<>();
            for (int j = 0; j < enterprises2.size(); j++) {
                OrderTypeDto orderTypeDto = new OrderTypeDto();
                orderTypeDto.setOrderTypeName(enterprises2.get(j).getOrderTypeName());
                orderTypeDto.setOrderType(enterprises2.get(j).getOrderType());

                //分组查询计划状态
                QueryWrapper<Enterprise> wrapper3 = new QueryWrapper<>();
                wrapper3.eq("enterprise_no",enterpriseNo);
                wrapper3.eq("order_type",enterprises2.get(i).getOrderType());
                wrapper3.groupBy("status");
                List<Enterprise> enterprises3 = enterpriseMapper.selectList(wrapper3);

                List<PlanStatusDto> planStatusDtoList = new ArrayList<>();
                for (int k = 0; k < enterprises3.size(); k++) {
                    PlanStatusDto planStatusDto = new PlanStatusDto();
                    planStatusDto.setStatus(enterprises3.get(k).getStatus());
                    planStatusDtoList.add(planStatusDto);
                }
                orderTypeDto.setPlanStatusDtoList(planStatusDtoList);
                orderTypeDtoList.add(orderTypeDto);
            }
            enterpriseDto.setOrderTypeDtoList(orderTypeDtoList);
            enterpriseDtoList.add(enterpriseDto);
        }

        System.out.println(JSON.toJSON(enterpriseDtoList));
    }

    @Test
    public void test2(){
        QueryWrapper<Enterprise> wrapper = new QueryWrapper<>();
        wrapper.groupBy("enterprise_no","enterprise_name","order_type","order_type_name");
        List<Enterprise> enterprises = enterpriseMapper.selectList(wrapper);
        enterprises.forEach(System.out::println);
        List<String> collect = enterprises.stream().map(Enterprise::getEnterpriseNo).distinct().collect(Collectors.toList());
    }
}
