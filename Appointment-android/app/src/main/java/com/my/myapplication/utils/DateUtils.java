package com.my.myapplication.utils;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//日期处理工具类
public class DateUtils {
    public static String getWeek(String date) {
        try {
            // 获取星期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置日期格式
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(date));
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            String weekday;
            switch (dayOfWeek) {
                case Calendar.SUNDAY:
                    weekday = "星期日";
                    break;
                case Calendar.MONDAY:
                    weekday = "星期一";
                    break;
                case Calendar.TUESDAY:
                    weekday = "星期二";
                    break;
                case Calendar.WEDNESDAY:
                    weekday = "星期三";
                    break;
                case Calendar.THURSDAY:
                    weekday = "星期四";
                    break;
                case Calendar.FRIDAY:
                    weekday = "星期五";
                    break;
                case Calendar.SATURDAY:
                    weekday = "星期六";
                    break;
                default:
                    weekday = "未知";
                    break;
            }
            return weekday;
        } catch (Exception e) {

        }
        return "";
    }

    public static String getNowWeek() {
        // 获取当前日期时间
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        String weekday = "";
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                weekday = "星期日";
                break;
            case Calendar.MONDAY:
                weekday = "星期一";
                break;
            case Calendar.TUESDAY:
                weekday = "星期二";
                break;
            case Calendar.WEDNESDAY:
                weekday = "星期三";
                break;
            case Calendar.THURSDAY:
                weekday = "星期四";
                break;
            case Calendar.FRIDAY:
                weekday = "星期五";
                break;
            case Calendar.SATURDAY:
                weekday = "星期六";
                break;
            default:
                weekday = "未知";
                break;
        }
        return weekday;
    }

    public static String getNowDate() {
        try {
            // 这里是调用耗时操作方法
            // 抓取北京时间
            URL url = new URL("https://time.tianqi.com");
            // 连接到我们需要抓取时间的网址
            URLConnection uc = url.openConnection();
            uc.connect();
            // 抓取北京时间，获取到的时间为long类型
            long time = uc.getDate();

// 创建一个适用于中国时区的Calendar对象
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
            calendar.setTimeInMillis(time);

// 转换为日期对象
            Date date = calendar.getTime();

// 格式化日期对象为字符串
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(date);


//            // 获取星期
            calendar.setTime(sdf.parse(formattedDate));


            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            String weekday;
            switch (dayOfWeek) {
                case Calendar.SUNDAY:
                    weekday = "星期日";
                    break;
                case Calendar.MONDAY:
                    weekday = "星期一";
                    break;
                case Calendar.TUESDAY:
                    weekday = "星期二";
                    break;
                case Calendar.WEDNESDAY:
                    weekday = "星期三";
                    break;
                case Calendar.THURSDAY:
                    weekday = "星期四";
                    break;
                case Calendar.FRIDAY:
                    weekday = "星期五";
                    break;
                case Calendar.SATURDAY:
                    weekday = "星期六";
                    break;
                default:
                    weekday = "未知";
                    break;
            }
            return formattedDate + " " + weekday;
        } catch (Exception e) {

        }
        return "";
    }


    public static String addDate(String date, Integer cnt) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置日期格式
            Date parse = sdf.parse(date);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(parse);
            rightNow.add(Calendar.DAY_OF_YEAR, cnt);
            Date time1 = rightNow.getTime();
            String format = sdf.format(time1);
            return format;
        } catch (Exception e) {

        }
        return "";
    }
}
