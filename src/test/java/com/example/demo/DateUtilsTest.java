package com.example.demo;

import com.example.demo.dto.OptionExtendDto;
import com.example.demo.dto.WarnRulesDto;
import com.example.demo.enums.WarnTargetEnum;
import com.example.demo.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/1/26 14:42
 */
public class DateUtilsTest {

    public static void main(String[] args) {
        String synDate = "2021-01-26";
        String dateEnd = DateUtils.turnDate(synDate,DateUtils.DATE_FORMAT,-1);
        String dateStart = DateUtils.turnDate(synDate,DateUtils.DATE_FORMAT,-31);
        System.out.println("dateEnd---->"+dateEnd+"------>dateStart---->"+dateStart);

        String dateEnd1 = DateUtils.formatDate(DateUtils.getDateForEnd(DateUtils.parseDate(synDate,DateUtils.DATE_FORMAT),-1),DateUtils.DATETIME_FORMAT);
        String dateStart1 = DateUtils.formatDate(DateUtils.getDateForBegin(DateUtils.parseDate(synDate,DateUtils.DATE_FORMAT),-31),DateUtils.DATETIME_FORMAT);
        System.out.println("dateEnd1---->"+dateEnd1+"------>dateStart1---->"+dateStart1);

        String str = "触发警告，#{WarnTargetEnum}";
        WarnTargetEnum.ORDER_TARGET.setCode(11);
        System.out.println(str.contains("#{WarnTargetEnum}")+"    "+WarnTargetEnum.ORDER_TARGET.getCode());
        List<OptionExtendDto> optionExtendDtoList = new LinkedList<>();
        for(WarnTargetEnum item:WarnTargetEnum.values()){
            OptionExtendDto optionExtendDto = new OptionExtendDto();
            optionExtendDto.setSel(true);
            optionExtendDtoList.add(optionExtendDto);
        }
        System.out.println(optionExtendDtoList);

        WarnRulesDto warnRulesDto = new WarnRulesDto();
        warnRulesDto.setOptionExtendDtoList(optionExtendDtoList);
        System.out.println(warnRulesDto);
    }
}
