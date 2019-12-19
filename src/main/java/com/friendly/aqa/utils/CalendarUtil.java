package com.friendly.aqa.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
    private static DateFormat dateFormat = new SimpleDateFormat("MM/d/yyyy");
    private static DateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private static DateFormat fileNameFormat = new SimpleDateFormat("MM-dd-yyyy_HH-mm");
    private static DateFormat hourAndMinutes = new SimpleDateFormat("HH-mm");

    private static Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static String[] getDelay(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        return hourAndMinutes.format(calendar.getTime()).split("-");
    }

    static String getYesterdayDateString() {
        return dateFormat.format(yesterday());
    }

    static String getTodayDateString() {
        return dateFormat.format(new Date());
    }

    static String getFileName() {
        return fileNameFormat.format(new Date());
    }

    static String getTimeStamp() {
        return fullDateFormat.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(getFileName());
    }
}

