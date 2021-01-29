package com.example.myweatherapp.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Integer getHourFromUnix(Long inputDate) {
        Date date = new java.util.Date(inputDate * 1000L);
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        simpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        return date.getHours();
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
}
