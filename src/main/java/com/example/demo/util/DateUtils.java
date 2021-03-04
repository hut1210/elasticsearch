package com.example.demo.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {

    public final   static String FORMAT_DEFALUT = "MM/dd/yyyy";
    /**
     * 日期格式yyyy-MM-dd HH:mm:ss字符串常量
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 获得当前时间的day天时间。
     * @param date
     * @param day
     * @return
     */
    public static Date getDateByDay(Date date , int day){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE,day);
        return cal.getTime();
    }

    /**
     * 获取某时间的小时
     * @param date
     * @return
     */
    public static int getHour(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }
    /**
     * 获得day天的时间的0点0分
     * @param date
     * @return
     */
    public static Date getDateForBegin(Date date,int day){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);					//	将传入的时间设置为所需时间
        cal.set(Calendar.DATE,cal.get(Calendar.DATE) + day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date dt = cal.getTime();
        return dt;
    }

    /**
     * 获得day天的时间的23点59分
     * @param date
     * @return
     */
    public static Date getDateForEnd(Date date,int day){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);					//	将传入的时间设置为所需时间
        cal.set(Calendar.DATE,cal.get(Calendar.DATE) + day);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Date dt = cal.getTime();
        return dt;
    }


    /**
     * 获得两个日期（字符串）之间的所有日期
     *
     * @param start_date
     *            String 开始日期 EX：“2012-01-01” 强制输入参数
     * @param end_date
     *            String 结束日期 EX：“2012-01-02” 强制输入参数
     * @return
     */
    public static List<String> getDatesBetween2Date(String start_date,
                                                    String end_date) {
        List<String> result = new ArrayList<String>();
        try {
            start_date = StringUtils.trim(start_date);
            end_date = StringUtils.trim(end_date);
            if (StringUtils.isEmpty(start_date)
                    || StringUtils.isEmpty(end_date)) {
                return result;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int recAfter = diffDate(sdf.parse(end_date), sdf.parse(start_date));
            if (recAfter < 0) {
                String temp = start_date;
                start_date = end_date;
                end_date = temp;
                recAfter = -1 * recAfter;
            }
            result.add(start_date);
            String tempStart_date = new String(start_date.getBytes("UTF-8"),
                    "UTF-8");
            while (recAfter > 0) {
                tempStart_date = DateUtils.turnDate(tempStart_date,
                        "yyyy-MM-dd", 1);
                result.add(tempStart_date);
                recAfter--;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 功能描述：日期相减
     *
     * @param date
     *            Date 日期
     * @param date1
     *            Date 日期
     * @return 返回相减后的日期
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date
     *            日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static String turnDate(String showDate, String format, int interDay) {
        // 日期加指定天数
        Calendar cal = Calendar.getInstance();
        Date tempDate_001 = DateUtils.parseDate(showDate, format);
        if (null == tempDate_001) {
            return null;
        }
        cal.setTime(tempDate_001);
        cal.add(Calendar.DAY_OF_MONTH, interDay);
        String next = DateUtils.formatDate(cal.getTime(), format);
        return next;
    }

    /**
     * 功能描述：以指定的格式来格式化日期
     *
     * @param date
     *            Date 日期
     * @param format
     *            String 格式
     * @return String 日期字符串
     */
    public static String formatDate(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr
     *            String 字符型日期
     * @param format
     *            String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            return (Date) new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取前n天的开始和结束时间
     *
     * @param n
     */
    public static Map<String, Date> getDateWhitBeforeN(int n) {

        Map<String, Date> date = new HashMap<>();

        Calendar start = Calendar.getInstance();
        start.add(Calendar.DAY_OF_MONTH, -n);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);


        Calendar end = Calendar.getInstance();
        end.add(Calendar.DAY_OF_MONTH, 0);
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);

        date.put("start", start.getTime());
        date.put("end", end.getTime());

        return date;

    }

    /**
     * 获取前N个月的时间(年-月),不含当前月
     *
     * @return
     */
    public static List<String> getBeforeNTime(int n) {
        List<String> dateList = new ArrayList<>();
        if (n < 1) {
            return dateList;
        }

        Calendar calendar = Calendar.getInstance();
        //calendar.setTimeInMillis(1581129712000L);

        for (int i = 1; i <= n; i++) {
            calendar.add(Calendar.MONTH, -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            dateList.add(sdf.format(calendar.getTime()));
        }
        Collections.reverse(dateList);
        return dateList;
    }

    /**
     * 获取当年的第一天
     * @return
     */
    public static Date getCurrYearFirst(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取某年第一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取当年的最后一天
     * @return
     */
    public static Date getCurrYearLast(){
        Calendar currCal=Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();

        return currYearLast;
    }
}
