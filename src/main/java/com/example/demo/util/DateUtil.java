package com.example.demo.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/22 17:27
 */
public class DateUtil {
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final Map<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();

    public DateUtil() {
    }

    /**
     * 日期的格式化定义
     * @author internet
     * @date 2021-1-6 11:01:38
     */
    public static class Pattern {
        private Pattern() {}

        public static class Date {
            private Date() {}

            /**
             * yyyy-MM-dd
             */
            public static final String YYYY_MM_DD = "yyyy-MM-dd";
            /**
             * yyyy_MM_dd
             */
            public static final String YYYY_MM_DD_3 = "yyyy_MM_dd";
            /**
             * yyyy-M-dd
             */
            public static final String YYYY_M_DD = "yyyy-M-dd";
            /**
             * yyyy-M-d
             */
            public static final String YYYY_M_D = "yyyy-M-d";
            /**
             * yyyy/MM/dd
             */
            public static final String YYYY_MM_DD_2 = "yyyy/MM/dd";
            /**
             * yyyy/M/dd
             */
            public static final String YYYY_M_DD_2 = "yyyy/M/dd";
            /**
             * yyyy/M/d
             */
            public static final String YYYY_M_D_2 = "yyyy/M/d";
            /**
             * yyyyMMdd
             */
            public static final String YYYYMMDD = "yyyyMMdd";
            /**
             * yyyyMdd
             */
            public static final String YYYYMDD = "yyyyMdd";
            /**
             * yyyyMd
             */
            public static final String YYYYMD = "yyyyMd";

            /**
             * MM-dd-yyyy
             */
            public static final String MM_DD_YYYY = "MM-dd-yyyy";
            /**
             * M-dd-yyyy
             */
            public static final String M_DD_YYYY = "M-dd-yyyy";
            /**
             * M-d-yyyy
             */
            public static final String M_D_YYYY = "M-d-yyyy";
            /**
             * MM/dd/yyyy
             */
            public static final String MM_DD_YYYY_2 = "MM/dd/yyyy";
            /**
             * M/dd/yyyy
             */
            public static final String M_DD_YYYY_2 = "M/dd/yyyy";
            /**
             * M/d/yyyy
             */
            public static final String M_D_YYYY_2 = "M/d/yyyy";
            /**
             * MMddyyyy
             */
            public static final String MMDDYYYY = "MMddyyyy";
            /**
             * Mddyyyy
             */
            public static final String MDDYYYY = "Mddyyyy";
            /**
             * Mdyyyy
             */
            public static final String MDYYYY = "Mdyyyy";
        }

        public static class DateTime {
            private DateTime() {}

            /**
             * yyyy-MM-dd HH:mm:ss
             */
            public static final String YYYY_MM_DD_SPACE_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
            /**
             * yyyy-M-d H:m:s
             */
            public static final String YYYY_M_D_SPACE_H_M_S = "yyyy-M-d H:m:s";
            /**
             * yyyy/MM/dd HH:mm:ss
             */
            public static final String YYYY_MM_DD_SPACE_HH_MM_SS2 = "yyyy/MM/dd HH:mm:ss";
            /**
             * yyyyMMddHHmmss
             */
            public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
            /**
             * yyyyMMddHHmm
             */
            public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
            /**
             * HH:mm yyyy-MM-dd
             */
            public static final String HH_MM_SPACE_YYYY_MM_DD = "HH:mm yyyy-MM-dd";
            /**
             * HH:mm yyyy/MM/dd
             */
            public static final String HH_MM_SPACE_YYYY_MM_DD2 = "HH:mm yyyy/MM/dd";
            /**
             * H:mm yyyy-MM-dd
             */
            public static final String H_MM_SPACE_YYYY_MM_DD = "H:mm yyyy-MM-dd";
            /**
             /**
             * H:mm yyyy/MM/dd
             */
            public static final String H_MM_SPACE_YYYY_MM_DD2 = "H:mm yyyy/MM/dd";
            /**
             * H:mm,yyyy-MM-dd
             */
            public static final String H_MM_COMMA_YYYY_MM_DD = "H:mm,yyyy-MM-dd";
            /**
             * H:mm,yyyy-MM-dd
             */
            public static final String H_MM_COMMA_YYYY_MM_DD2 = "H:mm,yyyy/MM/dd";
            /**
             * H:mm,yyyy-M-d
             */
            public static final String H_MM_COMMA_YYYY_M_D = "H:mm,yyyy-M-d";
            /**
             * H:m,yyyy-M-d
             */
            public static final String H_M_COMMA_YYYY_M_D = "H:m,yyyy-M-d";
            /**
             * H:mm,yyyy-M-d
             */
            public static final String H_MM_COMMA_YYYY_M_D2 = "H:mm,yyyy/M/d";
            /**
             * H:m,yyyy-M-d
             */
            public static final String H_M_COMMA_YYYY_M_D2 = "H:m,yyyy/M/d";
        }
    }

    public static void main(String[] args) {
        String str = getDataStr(new Date());
        Date date = parseDate(str, "yyyy-MM-dd HH:mm:ss");
        System.out.println(getDataStr(date));
    }

    public static String getDataStr(Date date) {
        return getLocalDateStr(date, "");
    }

    private static String getLocalDateStr(Date date, String format) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(StringUtils.isBlank(format) ? "yyyy-MM-dd HH:mm:ss" : format);
        return localDateTime.format(dateTimeFormatter);
    }

    public static Date addDate(Date date, int day) {
        long millis = getMillis(date) + (long)day * 24L * 3600L * 1000L;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public static Date addDate(Date date, long millis) {
        long millis1 = getMillis(date) + millis;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis1);
        return calendar.getTime();
    }

    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    public static Date parseDate(String dateStr, String format) {
        ZoneId zoneId = ZoneId.systemDefault();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, formatter);
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * 转换不带时分秒的日期字符串到Date
     * 非空校验在下层方法
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        LocalDate localDate = parseLocalDate(date, Pattern.Date.YYYY_MM_DD);
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * 转换不带时分秒的日期字符串到LocalDate
     * @param date
     * @param pattern {@link Pattern.Date}
     * @return
     */
    public static LocalDate parseLocalDate(String date, String pattern) {
        if(StringUtils.isBlank(date)) { throw new NullPointerException("时间为空"); }
        DateTimeFormatter formatter = createCacheFormatter(pattern);
        return LocalDate.parse(date, formatter);
    }

    /**
     * DateTimeFormatter缓存处理
     * @param pattern {@link Pattern}
     * @return DateTimeFormatter对象
     */
    private static DateTimeFormatter createCacheFormatter(String pattern) {
        if(StringUtils.isBlank(pattern)) { throw new NullPointerException("格式化规则为空"); }
        DateTimeFormatter formatter = FORMATTER_CACHE.get(pattern);
        if(null == formatter) {
            formatter = DateTimeFormatter.ofPattern(pattern);
            FORMATTER_CACHE.put(pattern, formatter);
        }

        return formatter;
    }

    public static String formatDate(Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    public static String formatDate(Date date, String style) {
        return formatDateByFormat(date, style);
    }

    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                return getLocalDateStr(date, format);
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

        return result;
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(1);
    }

    public static int getMonth(Date date) {
        return Calendar.getInstance().get(2) + 1;
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(5);
    }

    public static String getSystem_Date(long time) {
        Date date = new Date(time);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    public static String getSystem_Date(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    public static String getSystem_Date() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }
}
