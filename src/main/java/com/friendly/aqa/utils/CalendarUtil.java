package com.friendly.aqa.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
    private static DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
    private static DateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private static DateFormat fileNameFormat = new SimpleDateFormat("MM-dd-yyyy_HH-mm");
    private static DateFormat hourAndMinutes = new SimpleDateFormat("HH-mm");
    private static DateFormat hours = new SimpleDateFormat("HH");
    private static DateFormat minutes = new SimpleDateFormat("mm");

    private static Date getday(int day) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public static String[] getDelay(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        return hourAndMinutes.format(calendar.getTime()).split("-");
    }

    public static String getMonthBeforeDate() {
        return dateFormat.format(getday(-30));
    }

    public static String getTomorrowDateString() {
        return dateFormat.format(getday(1));
    }

    public static String getTodayDateString() {
        return dateFormat.format(new Date());
    }

    public static String getHours() {
        return hours.format(new Date());
    }

    public static String getMinutes() {
        return minutes.format(new Date());
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

