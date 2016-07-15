package com.lzh.mdzhihudaily.utils;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Leo on 16/7/15.
 */
public class DateUtil {

    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();

    private static final Object object = new Object();

    private static SimpleDateFormat getDateFormat(String format) throws RuntimeException {
        SimpleDateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(format);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }
        dateFormat.applyPattern(format);
        return dateFormat;
    }

    public static Date getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        return date;
    }

    public static String dateToString(String format, Date date) {
        String result = "";
        if (date != null) {
            result = getDateFormat(format).format(date);
        }
        return result;
    }

    public static Date stringToDate(String format, String dateString) {
        Date date = null;
        try {
            date = getDateFormat(format).parse(dateString);
        } catch (ParseException e) {
            Logger.e(e, "格式异常");
        }
        return date;
    }

    public static String getDateWeek(Date date) {
        String result = "";
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            switch (week) {
                case 1:
                    result = "星期日";
                    break;
                case 2:
                    result = "星期一";
                    break;
                case 3:
                    result = "星期二";
                    break;
                case 4:
                    result = "星期三";
                    break;
                case 5:
                    result = "星期四";
                    break;
                case 6:
                    result = "星期五";
                    break;
                case 7:
                    result = "星期六";
                    break;
            }
        }
        return result;
    }

    public static String getPreviousDate(String format, String dateString) {
        Date date = stringToDate(format, dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return dateToString(format, calendar.getTime());
    }


}
