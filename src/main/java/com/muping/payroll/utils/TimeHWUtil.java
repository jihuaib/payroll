package com.muping.payroll.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeHWUtil {

    private static String formatDateByPattern(Date date, String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    public static String getCron(java.util.Date  date){
        String dateFormat="ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }

    /**
     * 根据当前日期获取星期天
     * @return
     */
    public static Date getSunday(Date date) throws Exception {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        if(Calendar.DAY_OF_WEEK==cDay.getFirstDayOfWeek()){ //如果刚好是周日，直接返回
            return date;
        }else{//如果不是周日，加一周计算
            cDay.add(Calendar.DAY_OF_YEAR, 7);
            cDay.set(Calendar.DAY_OF_WEEK, 1);
            cDay.add(Calendar.DAY_OF_MONTH,1);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cDay.getTime()));
        }
    }

    /**
     * 获取月末
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) throws Exception {
        Calendar cDay = Calendar.getInstance();
        cDay.setTime(date);
        cDay.set(Calendar.DAY_OF_MONTH, cDay.getActualMaximum(Calendar.DAY_OF_MONTH));
        cDay.add(Calendar.DAY_OF_MONTH,1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cDay.getTime()));
    }
}
