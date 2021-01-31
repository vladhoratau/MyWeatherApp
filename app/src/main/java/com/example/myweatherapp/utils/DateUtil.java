package com.example.myweatherapp.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
        Calendar calendar = Calendar.getInstance();
        List<Date> dateList = new ArrayList<>();
        List<Long> unixDateList = new ArrayList<>();
        for (int i = 1; i <= noOfdays; i++) {
            calendar.add(Calendar.DATE, -1);
            dateList.add(calendar.getTime());
        }
        for (int j = 0; j < dateList.size(); j++) {
            unixDateList.add(dateList.get(j).getTime() / 1000);
        }
        return unixDateList;
    }

}
