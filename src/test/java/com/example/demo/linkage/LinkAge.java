package com.example.demo.linkage;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.EnterpriseDto;
import com.example.demo.dto.LinkAgeDto;
import com.example.demo.dto.OrderTypeDto;
import com.example.demo.dto.PlanStatusDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/20 20:43
 */
public class LinkAge {

    public static void main(String[] args) {
        List<LinkAgeDto> linkAgeDtoList = new ArrayList<>();
        linkAgeDtoList.add(LinkAgeDto.builder()
                .enterprise_no("SCPA0001032209447")
                .enterprise_name("地球村经销商")
                .order_type("CG")
                .order_type_name("普通采购订单")
                .status(3000)
                .build());
        linkAgeDtoList.add(LinkAgeDto.builder()
                .enterprise_no("SCPA0001032209456")
                .enterprise_name("济南地球村")
                .order_type("CG")
                .order_type_name("普通采购订单")
                .status(1000)
                .build());
        linkAgeDtoList.add(LinkAgeDto.builder()
                .enterprise_no("SCPA0001032209456")
                .enterprise_name("济南地球村")
                .order_type("CG")
                .order_type_name("普通采购订单")
                .status(3000)
                .build());

        Map<String, List<LinkAgeDto>> collect = linkAgeDtoList.stream().collect(Collectors.groupingBy(LinkAgeDto::getEnterprise_no));
        System.out.println("collect =" + JSON.toJSON(collect));
        for (String s : collect.keySet()) {
            Map<String, List<LinkAgeDto>> collect1 = collect.get(s).stream().collect(Collectors.groupingBy(LinkAgeDto::getOrder_type));
            System.out.println("collect collect1 = " + JSON.toJSON(collect1));
        }

        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < linkAgeDtoList.size(); i++) {
            String enterprise_no = linkAgeDtoList.get(i).getEnterprise_no();
            String enterprise_name = linkAgeDtoList.get(i).getEnterprise_name();

            EnterpriseDto enterpriseDto = new EnterpriseDto();
            enterpriseDto.setEnterprise_name(enterprise_name);
            enterpriseDto.setEnterprise_no(enterprise_no);

            OrderTypeDto orderTypeDto = new OrderTypeDto();
            orderTypeDto.setOrderType(linkAgeDtoList.get(i).getOrder_type());
            orderTypeDto.setOrderTypeName(linkAgeDtoList.get(i).getOrder_type_name());

            PlanStatusDto planStatusDto = new PlanStatusDto();
            planStatusDto.setStatus(linkAgeDtoList.get(i).getStatus());

            List<OrderTypeDto> orderTypeDtoList = new ArrayList<>();
            orderTypeDtoList.add(orderTypeDto);

            List<PlanStatusDto> planStatusDtoList = new ArrayList<>();
            planStatusDtoList.add(planStatusDto);

            enterpriseDto.setOrderTypeDtoList(orderTypeDtoList);
            orderTypeDto.setPlanStatusDtoList(planStatusDtoList);

            if (map.containsKey(enterprise_no)) {
                List<EnterpriseDto> enterpriseDtoList = (List<EnterpriseDto>) map.get("child");
            } else {
                map.put("name", enterprise_name);
                map.put("code", enterprise_no);
                map.put("child", enterpriseDto);
            }
        }

        //System.out.println(JSON.toJSON(map));

        //******************************************************
        for (int i = 0; i < linkAgeDtoList.size(); i++) {
            int finalI = i;
            List<LinkAgeDto> collect1 = linkAgeDtoList.stream().filter(item -> item.getEnterprise_no().equals(linkAgeDtoList.get(finalI).getEnterprise_no())).collect(Collectors.toList());
            System.out.println("collect1 = " + collect1);
        }

        List<String> enterpriseNos = linkAgeDtoList.stream().map(LinkAgeDto::getEnterprise_no).distinct().collect(Collectors.toList());
        enterpriseNos.forEach(System.out::println);
    }
}
