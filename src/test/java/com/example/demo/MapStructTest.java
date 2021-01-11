package com.example.demo;

import com.example.demo.domain.WarnRules;
import com.example.demo.domain.convert.WarnRulesDtoConvert;
import com.example.demo.dto.WarnRulesDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/11 11:08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MapStructTest {

    @Test
    public void test(){
        List<WarnRules> warnRulesList = new ArrayList<>();
        WarnRules warnRules = new WarnRules();
        warnRules.setId(1L);
        warnRules.setWarnName("test1");
        warnRules.setWarnTarget(1);
        warnRulesList.add(warnRules);

        WarnRules warnRules1 = new WarnRules();
        warnRules1.setId(2L);
        warnRules1.setWarnName("test2");
        warnRules1.setWarnTarget(2);
        warnRulesList.add(warnRules1);

        WarnRules warnRules2 = new WarnRules();
        warnRules2.setId(3L);
        warnRules2.setWarnName("test3");
        warnRules2.setWarnTarget(3);
        warnRulesList.add(warnRules2);
        List<WarnRulesDto> warnRulesDtos = WarnRulesDtoConvert.INSTANCE.poToDto(warnRulesList);
        System.out.println("warnRulesDtos---->"+warnRulesDtos.size());
        warnRulesDtos.forEach(System.out::println);
    }
}
