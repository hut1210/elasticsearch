package com.example.demo.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/9 15:49
 */
public class CommonDeliveryConstant {

    public static final String WAYBILL_COLUMN = "waybill_volume";

    public static final String FIRST_TIME="first_time";

    public final static Map<Integer,String> postSaleEventTime = new HashMap();

    static {
        postSaleEventTime.put(1, "00:00:00-00:59:59");
        postSaleEventTime.put(2, "01:00:00-01:59:59");
        postSaleEventTime.put(3, "02:00:00-02:59:59");
        postSaleEventTime.put(4, "03:00:00-03:59:59");
        postSaleEventTime.put(5, "04:00:00-04:59:59");
        postSaleEventTime.put(6, "05:00:00-05:59:59");
        postSaleEventTime.put(7, "06:00:00-06:59:59");
        postSaleEventTime.put(8, "07:00:00-07:59:59");
        postSaleEventTime.put(9, "08:00:00-08:59:59");
        postSaleEventTime.put(10, "09:00:00-09:59:59");
        postSaleEventTime.put(11, "10:00:00-10:59:59");
        postSaleEventTime.put(12, "11:00:00-11:59:59");
        postSaleEventTime.put(13, "12:00:00-12:59:59");
        postSaleEventTime.put(14, "13:00:00-13:59:59");
        postSaleEventTime.put(15, "14:00:00-14:59:59");
        postSaleEventTime.put(16, "15:00:00-15:59:59");
        postSaleEventTime.put(17, "16:00:00-16:59:59");
        postSaleEventTime.put(18, "17:00:00-17:59:59");
        postSaleEventTime.put(19, "18:00:00-18:59:59");
        postSaleEventTime.put(20, "19:00:00-19:59:59");
        postSaleEventTime.put(21, "20:00:00-20:59:59");
        postSaleEventTime.put(22, "21:00:00-21:59:59");
        postSaleEventTime.put(23, "22:00:00-22:59:59");
        postSaleEventTime.put(24, "23:00:00-23:59:59");
    }

    /**
     * 西藏网点
     */
    public final static List siteList = Arrays.asList(1455600,1435954,1435924,715146,572997,477079,287973,4331,999);

    /**
     * 在途状态
     */
    public final static List onTheWayStatusList = Arrays.asList(-775,-1920,-1910,-1890,-1840,-1830,-1820,-1810,-1630
            ,-1620,-1540,-1570,-1560,-1550,-1580,-1530,-1330,18,-1280,-1210,-1200,-1230,-1220,-1190,-1180,-1170,-1160,-1150
            ,-1140,-1130,-275,-830,-770,-470,-460,-450,-350,-340,-330,-300,90,20,80,60,10,13,16);
    /**
     * 配送状态
     */
    public final static List distributionStatusList = Arrays.asList(-1675,-1670,-1800,640,-1610,-285,-850,135,133,610,590
            ,580,570,-480,-320,-240,95,120,130,110);
    /**
     * 已签收状态
     */
    public final static List signedInStatusList = Arrays.asList(-970,-151,70946,70944,600,150);
    /**
     * 取消状态
     */
    public final static List cancelStatusList = Arrays.asList(-790);
    /**
     * 拒收状态
     */
    public final static List rejectionStatusList = Arrays.asList(160);

}
