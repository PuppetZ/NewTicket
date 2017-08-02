package com.zz.utilsdemo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by zhangjing on 2015/8/2.
 * 时间转化类
 */

public class DateHelper {
    /**
     * 获取当前日期
     *
     * @return   str
     */
    public static String getCurrentDate() {
        Calendar calendar = java.util.Calendar.getInstance();
        SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    /**
     * 格式化指定日期
     *
     * @param date date
     * @return str
     */

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * 两个时间之间的间隔天数
     *
     * @param smdate 开始时间
     * @param bdate  结束时间
     * @return 天数
     */
    public static int daysBetween(String smdate, String bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 格式化秒
     *
     * @param second 秒
     * @return str
     */
    public static String formatSecond(String second) {
        String dateString = "0秒";
        if (second != "") {
            Double s = Double.parseDouble(second);
            String format;
            Object[] array;
            Integer hours = (int) (s / (60 * 60));
            Integer minutes = (int) (s / 60 - hours * 60);
            Integer seconds = (int) (s - minutes * 60 - hours * 60 * 60);
            if (hours > 0) {
                format = "%1$,d时%2$,d分%3$,d秒";
                array = new Object[]{hours, minutes, seconds};
            } else if (minutes > 0) {
                format = "%1$,d分%2$,d秒";
                array = new Object[]{minutes, seconds};
            } else {
                format = "%1$,d秒";
                array = new Object[]{seconds};
            }
            dateString = String.format(format, array);
        }
        return dateString;
    }


    /**
     * 时间转化成秒
     * "yyyyMMdd:hhmmss" 20100113:151902
     *
     * @param timeString
     * @param formatString
     * @return
     */
    public static String getFormatDate(String timeString, String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        long unixLong = 0;
        String date = "";
        try {
            switch (timeString.length()) {
                case 13:
                    unixLong = Long.parseLong(timeString);
                    break;
                case 10:
                    unixLong = Long.parseLong(timeString) * 1000;
                    break;
            }
            date = format.format(unixLong);
        } catch (Exception ex) {
            return "";
        }
        return date;
    }

    /**
     * 根据时间获取小时的str
     *
     * @param dateString   dateString
     * @param formatString formatString(时间格式)
     * @return time的str
     */
    public static String getTimeFromDate(String dateString, String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        try {
            return String.valueOf(format.parse(dateString).getTime());
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * UTC（2016-08-08T05:29:24Z）时间转化为本地时间
     * yyyy-MM-dd'T'HH:mm:ss'Z'
     *
     * @param value 时间str
     * @return date
     */
    public static Date date2Date(String value) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将UTC时间转换为东八区时间
     * yyyy-MM-dd'T'HH:mm:ss
     *
     * @param UTCTime UTCTime
     * @return date的str
     */
    public static String getLocalTimeFromUTC(String UTCTime) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date UTCDate = null;
        String localTimeStr = null;
        try {
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            UTCDate = format.parse(UTCTime);
            format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            localTimeStr = format.format(UTCDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localTimeStr.replace("T", " ");
    }
}
