package com.xula.base.utils;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 时间处理工具
 * @author xla
 */
public class DateUtil {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_HH = "yyyy-MM-dd hh:mm:ss";

    /**
     * 字符串转为Date工具
     *
     * @param dateStr   日期字符串
     * @param formatStr
     * @return
     */
    public static Date stringToDate(String dateStr, String formatStr) {
        if (StringUtils.isBlank(formatStr)) {
            formatStr = DATE_FORMAT_HH;
        }
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 时间增加相应的值 1=year;2=month;5=date;11=HOUR_OF_DAY
     *
     * @param filed  标示增加的年,月,日
     * @param offset 表示增加量
     * @return
     */
    public static Date getDateAddOffSet(int filed, int offset) {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(filed, offset);
        date = c.getTime();
        return date;
    }

    /**
     * 时间增加相应的值 1=year;2=month;5=date
     *
     * @param filed  标示增加的年,月,日
     * @param offset 表示增加量
     * @param date   日期
     * @return
     */
    public static Date getDateAddOffSet(int filed, int offset, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(filed, offset);
        date = c.getTime();
        return date;
    }


    /**
     * 时间增加相应的值 1=year;2=month;5=date
     *
     * @param filed   标示增加的年,月,日
     * @param offset  表示增加量
     * @param dateStr 日期
     * @return
     */
    public static String getDateAddOffSet(int filed, int offset, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = stringToDate(dateStr, "yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(filed, offset);
        date = c.getTime();
        return sdf.format(date);
    }


    /**
     * 获取当前日期属于当年的第几周 周日为每周第一天
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        // 设置周日为每周第一天
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 将时间戳转换为时间
     * @param s
     * @param formatStr
     * @return
     */
    public static String stampToDate(String s, String formatStr) {
        if (StringUtils.isBlank(formatStr)) {
            formatStr = DATE_FORMAT ;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatStr);
        long lt = new Long(s);
        Date date = new Date(lt);
        return simpleDateFormat.format(date);
    }


    /**
     * 时间格式化
     * @param date 日期
     * @return
     */
    public static String formatYMD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

}
