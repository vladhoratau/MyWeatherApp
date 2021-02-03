package com.example.myweatherapp.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    public static Integer getHourFromUnix(Long inputDate) {
        Date date = new java.util.Date(inputDate * 1000L);
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        simpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        return date.getHours();
    }

    public static String getDayFromUnix(Long inputDate) {
        Date date = new java.util.Date(inputDate * 1000L);
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd-MMM");
        simpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        return simpleDateFormat.format(date);
    }

    public static String getTomorrowDate() {
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        return simpleDateFormat.format(date);
    }

    public static List<Long> getLastDaysUnix(Integer noOfdays) {
        List<Long> unixDateList = new ArrayList<>();
        for (int i = 0; i < noOfdays; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -i);
            unixDateList.add(calendar.getTime().getTime() / 1000);
        }
        return unixDateList;
    }
}
