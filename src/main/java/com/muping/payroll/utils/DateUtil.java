package com.muping.payroll.utils;

import java.util.Calendar;
import java.util.Date;

//日期工具类
//用于查找一天之中不同时段的记录
public class DateUtil {

    /**
     * 2003.10.28 ------> 2003.10.28 00:00:00
     * @param now
     * @return
     */
    public static Date BeginDate(Date now){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        now = calendar.getTime();
        return now;
    }

    /**
     * 2003.10.28------->2003.10.28 23:59:59
     * @param now
     * @return
     */
    public static Date endDate(Date now){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE, 1);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        calendar.add(Calendar.SECOND, -1);
        now = calendar.getTime();
        return now;
    }

    public static long getSecondsByLast(Date d1,Date d2){
        return Math.abs(d1.getTime()-d2.getTime())/1000;
    }

    /**
     * 计算两个时间之间间隔多少小时
     * @param endDate
     * @param nowDate
     * @return
     */
    public static long getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day*24+hour;
    }
}
