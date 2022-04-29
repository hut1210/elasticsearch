package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/12/2 10:30
 */
public class SymmetryDay {
    public static void main(String[] args) {
        String start = "1970-01-01";
        String end = "3000-01-01";
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        long startDateL;
        long endDateL;
        long oneDay = 24 * 60 * 60 * 1000;
        try {
            startDate = myFormatter.parse(start);
            startDateL = startDate.getTime();
            endDateL = myFormatter.parse(end).getTime();
            while (startDateL < endDateL) {
                String s = myFormatter.format(startDate).replaceAll("-", "");
                String[] str = {s.substring(0, 4), s.substring(4)};
                StringBuffer sb = new StringBuffer(str[1]);
                if (str[0].toString().equals(sb.reverse().toString())) {
                    log(myFormatter.format(startDate));
                }
                startDateL = startDateL + oneDay;
                startDate.setTime(startDateL);
            }

        } catch (Exception  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void log(Object m) {
        System.out.println(m);
        String a="{\"orderLines\":{\"orderLine\":[{\"itemId\":\"tests\",\"amount\":\"168.0000\",\"itemName\":\"测试西\",\"orderLineNo\":\"1\",\"planQty\":\"8\",\"actualPrice\":\"5.25\",\"ownerCode\":\"BJ4418046542626\",\"sourceOrderCode\":\"000202111010843\",\"itemCode\":\"tests\",\"settlementAmount\":\"42.0\",\"subSourceOrderCode\":\"15535797884912640\"}]},\"extendProps\":{\"isAdvanceDelivery\":\"false\",\"platformWarehouseCode\":\"WH0011108\",\"actualAmount\":\"42.0\",\"isJitx\":\"false\",\"isDecryptMobile\":\"false\"},\"deliveryOrder\":{\"orderType\":\"JYCK\",\"buyerNick\":\"测试843\",\"ownerCode\":\"BJ4418046542626\",\"payTime\":\"2021-11-04 09:22:03\",\"freight\":\"0\",\"oaidOrderSourceCode\":\"000202111010843\",\"operateTime\":\"2021-11-19 19:46:00\",\"receiverInfo\":{\"area\":\"南山区\",\"zipCode\":\"\",\"province\":\"广东省\",\"town\":\"\",\"city\":\"深圳市\",\"mobile\":\"18512340843\",\"name\":\"测试843\",\"detailAddress\":\"金骐智谷0843\",\"company\":\"测试843\",\"tel\":\"\",\"email\":\"\"},\"discountAmount\":\"0\",\"shopName\":\"361度outlets店\",\"deliveryOrderCode\":\"DO242746861777472\",\"sourcePlatformName\":\"淘宝网站\",\"warehouseCode\":\"WH0011108\",\"placeOrderTime\":\"2021-11-05 21:12:09\",\"shopNick\":\"361度outlets店\",\"itemAmount\":\"42.0000\",\"gotAmount\":\"42.0000\",\"arAmount\":\"0\",\"insuranceFlag\":\"N\",\"sellerNick\":\"郭瑞华\",\"shopCode\":\"1583\",\"logisticsCode\":\"VP_SF\",\"logisticsName\":\"顺丰速运\",\"totalAmount\":\"42.0000\",\"isUrgency\":\"N\",\"senderInfo\":{\"area\":\"\",\"zipCode\":\"\",\"province\":\"\",\"town\":\"\",\"city\":\"\",\"countryCode\":\"\",\"mobile\":\"13809628045\",\"name\":\"郭瑞华\",\"company\":\"\",\"tel\":\"\",\"email\":\"\"},\"outBizCode\":\"DO242746861777472\",\"sourcePlatformCode\":\"TB\",\"createTime\":\"2021-11-05 21:12:09\"}}";
        String b="{\"body\":{\"deliveryOrder\":{\"deliveryOrderCode\":\"202112021209\",\"pin\":\"PIN10TPT000001\",\"isvSource\":\"ISV0000000000066\",\"orderType\":\"JYCK\",\"warehouseCode\":\"800008602\",\"orderMark\":\"00000000000000000000000000000000000000000000000000\",\"sourcePlatformCode\":\"8\",\"createTime\":\"2021-11-11 11:03:42\",\"placeOrderTime\":\"2021-11-11 10:53:05\",\"payTime\":\"2021-11-11 10:53:08\",\"operateTime\":\"2021-11-11 11:04:29\",\"ownerNo\":\"CBU4418046529406\",\"shopNick\":\"韩味乐韩式酱料\",\"totalAmount\":420,\"discountAmount\":0,\"freight\":0,\"gotAmount\":420,\"logisticsCode\":\"CNST\",\"thirdWayBill\":\"\",\"soSource\":\"20\",\"senderInfo\":{\"name\":\"黄**\",\"zipCode\":\"000000\",\"mobile\":\"*******7173\",\"province\":\"陕西省\",\"city\":\"西安市\",\"area\":\"长安区\",\"detailAddress\":\"韦*街道六**路****长安新界小区\",\"remark\":\"TESTTESTETS;\"},\"receiverInfo\":{\"name\":\"董**\",\"zipCode\":\"000000\",\"mobile\":\"*******9015\",\"province\":\"福建省\",\"city\":\"福州市\",\"area\":\"闽侯县\",\"town\":\"上街镇\",\"detailAddress\":\"马保村武警福建总队后勤保障黄***\",\"remark\":\"【卖家留言】11.11赠送韩式甜辣2.1kg*1桶;\",\"oaid\":\"1nqCvCFnW*******iaic0PoWvp017Mj8ZuWAyHqW19UNdKyic1QicrrLPDsOtGKrUV63iaxy\"},\"invoiceFlag\":0,\"sellerMessage\":\"11.11赠送韩式甜辣2.1kg*1桶\",\"remark\":\"【卖家留言】11.11赠送韩式甜辣2.1kg*1桶;\",\"outLogisticsCode\":\"CNST\"},\"orderLines\":[{\"orderLineNo\":\"13716161\",\"sourceOrderCode\":\"2082622312737619150\",\"itemCode\":\"1432543\",\"itemId\":\"CMG4418719562291\",\"itemName\":\"巧克力\",\"planQty\":2,\"retailPrice\":210,\"actualPrice\":210},{\"orderLineNo\":\"13716171\",\"sourceOrderCode\":\"2082622312737619150\",\"itemCode\":\"1432544\",\"itemId\":\"CMG4418719562291\",\"itemName\":\"牙刷\",\"planQty\":2,\"retailPrice\":210,\"actualPrice\":210}]}}";
        String c="{\"body\":{\"orderLines\":{\"orderLine\":[{\"itemId\":\"tests\",\"amount\":\"168.0000\",\"itemName\":\"测试西\",\"orderLineNo\":\"1\",\"planQty\":\"8\",\"actualPrice\":\"5.25\",\"ownerCode\":\"BJ4418046542626\",\"sourceOrderCode\":\"000202111010843\",\"itemCode\":\"tests\",\"settlementAmount\":\"42.0\",\"subSourceOrderCode\":\"15535797884912640\"}]},\"extendProps\":{\"isAdvanceDelivery\":\"false\",\"platformWarehouseCode\":\"WH0011108\",\"actualAmount\":\"42.0\",\"isJitx\":\"false\",\"isDecryptMobile\":\"false\"},\"deliveryOrder\":{\"orderType\":\"JYCK\",\"buyerNick\":\"测试843\",\"ownerCode\":\"BJ4418046542626\",\"payTime\":\"2021-11-04 09:22:03\",\"freight\":\"0\",\"oaidOrderSourceCode\":\"000202111010843\",\"operateTime\":\"2021-11-19 19:46:00\",\"receiverInfo\":{\"area\":\"南山区\",\"zipCode\":\"\",\"province\":\"广东省\",\"town\":\"\",\"city\":\"深圳市\",\"mobile\":\"18512340843\",\"name\":\"测试843\",\"detailAddress\":\"金骐智谷0843\",\"company\":\"测试843\",\"tel\":\"\",\"email\":\"\"},\"discountAmount\":\"0\",\"shopName\":\"361度outlets店\",\"deliveryOrderCode\":\"DO242746861777472\",\"sourcePlatformName\":\"淘宝网站\",\"warehouseCode\":\"WH0011108\",\"placeOrderTime\":\"2021-11-05 21:12:09\",\"shopNick\":\"361度outlets店\",\"itemAmount\":\"42.0000\",\"gotAmount\":\"42.0000\",\"arAmount\":\"0\",\"insuranceFlag\":\"N\",\"sellerNick\":\"郭瑞华\",\"shopCode\":\"1583\",\"logisticsCode\":\"VP_SF\",\"logisticsName\":\"顺丰速运\",\"totalAmount\":\"42.0000\",\"isUrgency\":\"N\",\"senderInfo\":{\"area\":\"\",\"zipCode\":\"\",\"province\":\"\",\"town\":\"\",\"city\":\"\",\"countryCode\":\"\",\"mobile\":\"13809628045\",\"name\":\"郭瑞华\",\"company\":\"\",\"tel\":\"\",\"email\":\"\"},\"outBizCode\":\"DO242746861777472\",\"sourcePlatformCode\":\"TB\",\"createTime\":\"2021-11-05 21:12:09\"}}}";
        String d="{\"deliveryOrder\":{\"deliveryOrderCode\":\"202112021209\",\"pin\":\"PIN10TPT000001\",\"isvSource\":\"ISV0000000000066\",\"orderType\":\"JYCK\",\"warehouseCode\":\"800008602\",\"orderMark\":\"00000000000000000000000000000000000000000000000000\",\"sourcePlatformCode\":\"8\",\"createTime\":\"2021-11-11 11:03:42\",\"placeOrderTime\":\"2021-11-11 10:53:05\",\"payTime\":\"2021-11-11 10:53:08\",\"operateTime\":\"2021-11-11 11:04:29\",\"ownerNo\":\"CBU4418046529406\",\"shopNick\":\"韩味乐韩式酱料\",\"totalAmount\":420,\"discountAmount\":0,\"freight\":0,\"gotAmount\":420,\"logisticsCode\":\"CNST\",\"thirdWayBill\":\"\",\"soSource\":\"20\",\"senderInfo\":{\"name\":\"黄**\",\"zipCode\":\"000000\",\"mobile\":\"*******7173\",\"province\":\"陕西省\",\"city\":\"西安市\",\"area\":\"长安区\",\"detailAddress\":\"韦*街道六**路****长安新界小区\",\"remark\":\"TESTTESTETS;\"},\"receiverInfo\":{\"name\":\"董**\",\"zipCode\":\"000000\",\"mobile\":\"*******9015\",\"province\":\"福建省\",\"city\":\"福州市\",\"area\":\"闽侯县\",\"town\":\"上街镇\",\"detailAddress\":\"马保村武警福建总队后勤保障黄***\",\"remark\":\"【卖家留言】11.11赠送韩式甜辣2.1kg*1桶;\",\"oaid\":\"1nqCvCFnW*******iaic0PoWvp017Mj8ZuWAyHqW19UNdKyic1QicrrLPDsOtGKrUV63iaxy\"},\"invoiceFlag\":0,\"sellerMessage\":\"11.11赠送韩式甜辣2.1kg*1桶\",\"remark\":\"【卖家留言】11.11赠送韩式甜辣2.1kg*1桶;\",\"outLogisticsCode\":\"CNST\"},\"orderLines\":[{\"orderLineNo\":\"13716161\",\"sourceOrderCode\":\"2082622312737619150\",\"itemCode\":\"1432543\",\"itemId\":\"CMG4418719562291\",\"itemName\":\"巧克力\",\"planQty\":2,\"retailPrice\":210,\"actualPrice\":210},{\"orderLineNo\":\"13716171\",\"sourceOrderCode\":\"2082622312737619150\",\"itemCode\":\"1432544\",\"itemId\":\"CMG4418719562291\",\"itemName\":\"牙刷\",\"planQty\":2,\"retailPrice\":210,\"actualPrice\":210}]}";
    }
}
