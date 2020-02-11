package com.friendly.aqa.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarUtil {
    private static DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
    private static DateFormat fullDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CET' yyyy", Locale.ENGLISH);
    private static DateFormat fileNameFormat = new SimpleDateFormat("MM-dd-yyyy_HH-mm");
    private static DateFormat hourAndMinutes = new SimpleDateFormat("HH-mm");
    private static DateFormat hours = new SimpleDateFormat("HH");
    private static DateFormat minutes = new SimpleDateFormat("mm");

    private static Date getDay(int day) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public static String[] getDelay(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        return hourAndMinutes.format(calendar.getTime()).split("-");
    }

    public static String getShiftedDate(int countDays) {
        return dateFormat.format(getDay(countDays));
    }

    public static String getMonthBeforeDate() {
        return dateFormat.format(getDay(-30));
    }

    public static String getTomorrowDateString() {
        return dateFormat.format(getDay(1));
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
        System.out.println(getTimeStamp());
    }
}
//"Tue Feb 03 11:30:55 CET 1970"
