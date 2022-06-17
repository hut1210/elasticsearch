package com.example.demo.yl;


import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 调拨单
 * @author biangongyin
 * @Date 2021-09-20 17:53:30
 */
public class IbOrderDataConvert {

    private static final Integer ORACLE_APPID = 17;
    private static final String ORACLE_PARAM = "CRM";
    private static final String ORDER_QUANTITY_UOM = "TIN";
    private static final String ORG_NAME = "220300";
    private static final String ORDER_TYPE_CODE = "奶粉内部调拨转移订单";
    private static final String ACCNT_NAME = "奶粉天津电商产品调拨内部客户";
    private static final String RESERVED_1 = "分仓调拨价目表-奶粉（新）";
    private static final String RETURN_REASON = "其他";
    private static final String ORDER_SOURCES_NAME = "EDI";
    private static final String SALESREP_ID = "-3";
    private static final String ORDER_SOURCE_ID = "6";
    private static final String RESERVED_9 = "E82";

    /**
     * 数据组装.
     * @param ibMainParam
     */
    public String dataConvert(IbMainParam ibMainParam) {

        StringBuilder headerBuilder = new StringBuilder();
        headerBuilder.append("<esb:MsgHeader>");
        headerBuilder.append("<esb:P_APP_ID>").append(ORACLE_APPID).append("</esb:P_APP_ID>");
        headerBuilder.append("<esb:P_APP_NAME>").append(ORACLE_PARAM).append("</esb:P_APP_NAME>");
        headerBuilder.append("</esb:MsgHeader>");

        StringBuilder lineItemBuider = new StringBuilder();
        if (ibMainParam.getIbItemParamList() != null) {
            for (IbItemParam item : ibMainParam.getIbItemParamList()) {
                lineItemBuider.append("<esb:OE_ORDER_LINES_ITEM>");
                lineItemBuider.append("<esb:LINE_TYPE_ID>").append(6290).append("</esb:LINE_TYPE_ID>");
                lineItemBuider.append("<esb:LINE_NUMBER>").append(item.getId()).append("</esb:LINE_NUMBER>");
                lineItemBuider.append("<esb:PROD_NUM>").append(item.getIsvGoodsNo()).append("</esb:PROD_NUM>");
                lineItemBuider.append("<esb:ORDER_QUANTITY_UOM>").append(ORDER_QUANTITY_UOM).append("</esb:ORDER_QUANTITY_UOM>");
                lineItemBuider.append("<esb:ORDERED_QUANTITY>").append(item.getRealOutstoreQty()).append("</esb:ORDERED_QUANTITY>");
                lineItemBuider.append("<esb:RETURN_REASON>").append(RETURN_REASON).append("</esb:RETURN_REASON>");
                lineItemBuider.append("<esb:RESERVED_9>").append(RESERVED_9).append("</esb:RESERVED_9>");
                /*lineItemBuider.append("<esb:RESERVED_10>").append("").append("</esb:RESERVED_10>");
                lineItemBuider.append("<esb:RESERVED_8>").append().append("</esb:RESERVED_8>");
                lineItemBuider.append("<esb:RESERVED_7>").append().append("</esb:RESERVED_7>");*/
                lineItemBuider.append("</esb:OE_ORDER_LINES_ITEM>");
            }
        }

        StringBuilder headerItemBuider = new StringBuilder();
        headerItemBuider.append("<esb:InputCollection>").append("<esb:InputItem>").append("<esb:OE_ORDER_HEADERS_ITEM>");
        headerItemBuider.append("<esb:USER_ID>").append(10017564).append("</esb:USER_ID>");
        headerItemBuider.append("<esb:RESP_ID>").append(65294).append("</esb:RESP_ID>");
        headerItemBuider.append("<esb:APPL_ID>").append(660).append("</esb:APPL_ID>");
        headerItemBuider.append("<esb:PRI_KEY>").append(String.valueOf(ibMainParam.getId())).append("</esb:PRI_KEY>");
        headerItemBuider.append("<esb:SOURCE_CODE>").append(ORACLE_PARAM).append("</esb:SOURCE_CODE>");
        headerItemBuider.append("<esb:SOA_ORDER_NUMBER>").append(ibMainParam.getIbNo()).append("</esb:SOA_ORDER_NUMBER>");
        headerItemBuider.append("<esb:ORG_ID>").append(12411).append("</esb:ORG_ID>");
        headerItemBuider.append("<esb:ORG_NAME>").append(ORG_NAME).append("</esb:ORG_NAME>");
        headerItemBuider.append("<esb:ORDER_SOURCES_NAME>").append(ORDER_SOURCES_NAME).append("</esb:ORDER_SOURCES_NAME>");
        headerItemBuider.append("<esb:ORDER_TYPE_CODE>").append(ORDER_TYPE_CODE).append("</esb:ORDER_TYPE_CODE>");
        headerItemBuider.append("<esb:ORDERED_DATE>").append(date2String(new Date())).append("</esb:ORDERED_DATE>");
        headerItemBuider.append("<esb:ORDER_TYPE_ID>").append(6584).append("</esb:ORDER_TYPE_ID>");
        headerItemBuider.append("<esb:ORDER_SOURCE_ID>").append(ORDER_SOURCE_ID).append("</esb:ORDER_SOURCE_ID>");
        headerItemBuider.append("<esb:ACCNT_NAME>").append(ACCNT_NAME).append("</esb:ACCNT_NAME>");
        headerItemBuider.append("<esb:SALESREP_ID>").append(SALESREP_ID).append("</esb:SALESREP_ID>");
        headerItemBuider.append("<esb:TRANSACTIONAL_CURR_CODE>").append("CNY").append("</esb:TRANSACTIONAL_CURR_CODE>");
        headerItemBuider.append("<esb:ATTRIBUTE10>").append("").append("</esb:ATTRIBUTE10>");
        headerItemBuider.append("<esb:ATTRIBUTE8>").append("").append("</esb:ATTRIBUTE8>");
        headerItemBuider.append("<esb:ACCNT_NUM>").append(1201140050).append("</esb:ACCNT_NUM>");
        headerItemBuider.append("<esb:ATTRIBUTE7>").append(ORACLE_PARAM).append("</esb:ATTRIBUTE7>");
        headerItemBuider.append("<esb:RESERVED_1>").append(RESERVED_1).append("</esb:RESERVED_1>");


        StringBuilder infoBuilder = new StringBuilder();
        infoBuilder.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:esb=\"http://soa.yili.com/ESB_EBS_OM_AddSaleOrderInfoSrv\">");
        infoBuilder.append("<soapenv:Header/>").append("<soapenv:Body>");
        infoBuilder.append("<esb:ESB_EBS_OM_00001Request>");
        infoBuilder.append(headerBuilder).append(headerItemBuider);
        infoBuilder.append("<esb:OE_ORDER_LINES_INFO>").append(lineItemBuider).append("</esb:OE_ORDER_LINES_INFO>");
        infoBuilder.append("</esb:OE_ORDER_HEADERS_ITEM>").append("</esb:InputItem>").append("</esb:InputCollection>");
        infoBuilder.append("</esb:ESB_EBS_OM_00001Request>");
        infoBuilder.append("</soapenv:Body>");
        infoBuilder.append("</soapenv:Envelope>");

        return infoBuilder.toString();
    }

    /**
     * 时间格式转换
     * @param date
     * @return
     */
    public String date2String(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String dateString = formatter.format(date);
        return dateString;
    }
    
    public void getStr(String str) {
    	System.out.println(str);
    }
}
