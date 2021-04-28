package com.example.demo.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/4/22 17:27
 */
public class DateUtil {
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public DateUtil() {
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
