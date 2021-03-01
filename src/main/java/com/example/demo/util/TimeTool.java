package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lwl
 * @version V1.0
 * @Package com.jd.soms.report.common.entity
 * @date 2021/2/20 16:05
 * @Remark 类说明:根据日期天数查询
 */
public class TimeTool {

    /**
     * 根据两个日期的天数差 返回两个新日期
     * 举个栗子 ==> 2021-02-01 -- 2021-02-08
     * return ==> 2021-01-25 -- 2021-02-01
     */
    public static Map<String, String> disposeTime(String beginDate, String endDate, String format) throws Exception {
        Map<String, String> dateMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (beginDate.compareTo(endDate) < 0) {
            Calendar calendarBegin = Calendar.getInstance();
            calendarBegin.setTime(sdf.parse(beginDate));
            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(sdf.parse(endDate));
            int dayDate1 = calendarBegin.get(Calendar.DAY_OF_MONTH);
            int dayDate2 = calendarEnd.get(Calendar.DAY_OF_MONTH);
            int diffit = dayDate1 - dayDate2;
            calendarBegin.add(Calendar.DATE, diffit);
            calendarEnd.add(Calendar.DATE, diffit);
            dateMap.put("begin", sdf.format(calendarBegin));
            dateMap.put("end", sdf.format(calendarEnd));
        }
        return dateMap;
    }


    /**
     * 计算两个日期之间的小时差
     *
     * @param startTime
     * @param endTime
     * @param format
     * @return
     * @throws Exception
     */
    public static long hourDifference(String startTime, String endTime, String format) throws Exception {
        //按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long diff;
        //获得两个时间的毫秒时间差异
        diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
        //计算差多少小时
        long hour = diff % nd / nh;
        return hour;
    }

    public static long dateDiff(String startTime, String endTime, String format) throws Exception {
        //按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000*24*60*60;//一天的毫秒数
        long nh = 1000*60*60;//一小时的毫秒数
        long nm = 1000*60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数
        long diff;
        //获得两个时间的毫秒时间差异
        diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
        long hour = diff/nh;//计算差多少天
        return hour ;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(dateDiff("2021-02-25 09:00:00", "2021-02-26 09:00:00", DateUtils.DATETIME_FORMAT));
    }

}