package com.lzh.mdzhihudaily_mvp.utils;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Leo on 16/7/15.
 */
public class DateUtil {

    public static final String FORMAT_YMD = "yyyyMMdd";
    public static final String FORMAT_MM_DD = "M月d日";

    public static Date getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        return date;
    }

    public static String getStringDate(String format, String stringDate) {
        String result = "";
        if (stringDate != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = stringToDate(FORMAT_YMD, stringDate);
            result = simpleDateFormat.format(date);
        }
        return result;
    }

    public static Date stringToDate(String format, String dateString) {
        Date date = null;
        if (dateString != null) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                date = simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                Logger.d(e);
            }
        }
        return date;
    }

    public static String dateToString(String format, Date date) {
        String result = "";
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            result = simpleDateFormat.format(date);
        }
        return result;
    }

    public static String getDateWeek(String dateString) {
        String result = "";
        if (dateString != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(stringToDate(FORMAT_YMD, dateString));
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

}
