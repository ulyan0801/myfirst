package com.my.springboot.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间日期工具类
 */
public class DateUtils {
    public static final int MSEC = 1;
    public static final int SEC = 1000;
    public static final int MIN = 60000;
    public static final int HOUR = 3600000;
    public static final int DAY = 86400000;
    private static SimpleDateFormat sf;
    private static SimpleDateFormat sdf;

    /**
     * 获取系统时间 格式为："yyyy/MM/dd "
     **/
    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /**
     * 获取系统时间 格式为："yyyy "
     **/
    public static String getCurrentYear() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy");
        return sf.format(d);
    }

    /**
     * 获取系统时间 格式为："MM"
     **/
    public static String getCurrentMonth() {
        Date d = new Date();
        sf = new SimpleDateFormat("MM");
        return sf.format(d);
    }

    /**
     * 获取系统时间 格式为："dd"
     **/
    public static String getCurrentDay() {
        Date d = new Date();
        sf = new SimpleDateFormat("dd");
        return sf.format(d);
    }

    /**
     * 获取当前时间戳 * * @return
     */
    public static long getCurrentTime() {
        long d = new Date().getTime() / 1000;
        return d;
    }

    /**
     * 时间戳转换成字符窜
     */
    public static String getDateToString(long time) {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /**
     * 时间戳中获取年
     */
    public static String getYearFromTime(long time) {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("yyyy");
        return sf.format(d);
    }

    /**
     * 时间戳中获取月
     */
    public static String getMonthFromTime(long time) {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("MM");
        return sf.format(d);
    }

    /**
     * 时间戳中获取日
     */
    public static String getDayFromTime(long time) {
        Date d = new Date(time * 1000);
        sf = new SimpleDateFormat("dd");
        return sf.format(d);
    }

    /**
     * 将字符串转为时间戳
     */
    public static long getStringToDate(String time) {
        sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) { // TODO Auto-generated catch block e.printStackTrace();

        }
        return date.getTime();
    }

    /**
     * 2018-12-13T13:33:43.441+08:00--->2018-12-13   13:33:43
     *
     * @param str
     * @return
     */
    public static String getGeneralTime(String str) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * get the one day of past
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-dd
     *
     * @param startDateStr the first date
     * @param endDateStr   the second date
     * @return true <br/>false
     */
    public static boolean isDate2Bigger(String startDateStr, String endDateStr) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(startDateStr);
            dt2 = sdf.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = false;
        } else if (dt1.getTime() <= dt2.getTime()) {
            isBigger = true;
        }
        return isBigger;
    }

    /**
     * 比较两个日期的大小间隔是否超过30天，日期格式为yyyy-MM-dd
     *
     * @param startDateStr the start date
     * @param endDateStr   the end date
     * @return true <br/>false
     */
    public static boolean isDateOverOneMonth(String startDateStr, String endDateStr) {
        boolean isOverOneMonth = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(startDateStr);
            dt2 = sdf.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dt1Time = dt1.getTime();
        long dt2Time = dt2.getTime();
        int period = (int) ((dt2Time - dt1Time) / (24 * 60 * 60 * 1000));

        if (period > 30) {
            isOverOneMonth = true;
        }

        return isOverOneMonth;
    }

    /**
     * 比较两个日期之间间隔的天数
     *
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    public static int getIntervalDays(String startDateStr, String endDateStr, SimpleDateFormat sdf) {
        int intervalDays;
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(startDateStr);
            dt2 = sdf.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dt1Time = dt1.getTime();
        long dt2Time = dt2.getTime();
        intervalDays = (int) ((dt2Time - dt1Time) / (24 * 60 * 60 * 1000));
        return intervalDays;
    }

    /**
     * 获取两个日期之间的所有日期
     *
     * @param startTime 开始日期
     * @param endTime   结束日期
     * @return
     */
    public static List<String> getIntervalDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 比较两个日期之间的间隔
     *
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    public static String getIntervalTime(String startDateStr, String endDateStr, SimpleDateFormat sdf) {
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(startDateStr);
            dt2 = sdf.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        double dt1Time = dt1.getTime();
        double dt2Time = dt2.getTime();
        double time = dt2Time - dt1Time;

        int intervalMins = (int) (time / (MIN));
        int intervalHours = (int) (time / (HOUR));
        int intervalDays = (int) (time / (DAY));

        int intervalWeeks = (intervalDays / 7);
        int intervalMonths = (intervalDays / 30);
        int intervalYears = (intervalDays / 365);

        if (intervalYears < 1) {//in year
            if (intervalMonths < 1) {//in month
                if (intervalWeeks < 1) {//in week
                    if (intervalDays < 1) {//in day
                        if (intervalHours < 1) {//in hour
                            if (intervalMins < 10) {//in minute
                                return "刚刚";
                            }
                            return intervalMins + "分钟前";
                        }
                        return intervalHours + "小时前";
                    }
                    return intervalDays + "天前";
                }
                return intervalWeeks + "周前";
            }
            return intervalMonths + "月前";
        }
        return intervalYears + "年前";
    }

    /***
     * 获取一天之内所有的时间点的集合
     * @return
     */
    public static List<String> getOneDayTimeList() {
        ArrayList<String> timeList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            timeList.add(i + ":00");
        }
        return timeList;
    }

}