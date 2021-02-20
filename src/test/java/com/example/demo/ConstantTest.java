package com.example.demo;

import com.example.demo.condition.CommonDeliveryCondition;
import com.example.demo.constant.CommonDeliveryConstant;
import com.example.demo.enums.CommonDeliveryEnum;
import org.apache.lucene.util.CollectionUtil;
import org.junit.platform.commons.util.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/18 17:33
 */
public class ConstantTest {
    public static void main(String[] args) {
        List seriesList = Arrays.asList(CommonDeliveryConstant.postSaleEventTime.keySet());
        Collections.sort(seriesList);
        seriesList.forEach(System.out::println);

        CommonDeliveryCondition commonDeliveryCondition = new CommonDeliveryCondition();
        for (CommonDeliveryEnum commonDeliveryEnum : CommonDeliveryEnum.values()) {
            CommonDeliveryCondition c = modifyCommonDeliveryCondition(commonDeliveryCondition,commonDeliveryEnum);
            System.out.println("commonDeliveryCondition="+commonDeliveryCondition);
            System.out.println("c="+c);
        }
        /*CommonDeliveryCondition c = modifyCommonDeliveryCondition(commonDeliveryCondition,CommonDeliveryEnum.INTRANSIT_AMOUNT);
        System.out.println("commonDeliveryCondition="+commonDeliveryCondition);
        System.out.println("c="+c);*/

        CommonDeliveryCondition commonDeliveryCondition1 = new CommonDeliveryCondition();
        CommonDeliveryCondition c1 =modifyCommonDeliveryCondition(commonDeliveryCondition1,null);
        System.out.println("commonDeliveryCondition1="+commonDeliveryCondition1);
        System.out.println("c1="+c1);
    }

    private static CommonDeliveryCondition modifyCommonDeliveryCondition(CommonDeliveryCondition commonDeliveryCondition, CommonDeliveryEnum commonDeliveryEnum) {
        if (commonDeliveryEnum != null) {
            CommonDeliveryCondition tempCommonDeliveryCondition = new CommonDeliveryCondition();
            BeanUtils.copyProperties(commonDeliveryCondition,tempCommonDeliveryCondition);
            switch (commonDeliveryEnum.getCode()) {
                case 1:
                    tempCommonDeliveryCondition.setStatus(CommonDeliveryConstant.onTheWayStatusList);
                    break;
                case 2:
                    tempCommonDeliveryCondition.setStatus(CommonDeliveryConstant.distributionStatusList);
                    break;
                case 3:
                    tempCommonDeliveryCondition.setStatus(CommonDeliveryConstant.signedInStatusList);
                    break;
                case 4:
                    tempCommonDeliveryCondition.setStatus(CommonDeliveryConstant.cancelStatusList);
                    break;
                case 5:
                    tempCommonDeliveryCondition.setStatus(CommonDeliveryConstant.rejectionStatusList);
                    break;
                default:
            }
            return tempCommonDeliveryCondition;
        }
        return commonDeliveryCondition;
    }
}
