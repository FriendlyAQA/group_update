package com.friendly.aqa.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarUtil {
    private static DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
    private static DateFormat importGroupDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private static DateFormat fullDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CET' yyyy", Locale.ENGLISH);
    private static DateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private static DateFormat fileNameFormat = new SimpleDateFormat("MM-dd-yyyy_HH-mm");
    private static DateFormat hourAndMinutes = new SimpleDateFormat("HH-mm");
    private static DateFormat hours = new SimpleDateFormat("HH");
    private static DateFormat minutes = new SimpleDateFormat("mm");
    private static DateFormat csvFileFormat = new SimpleDateFormat("M-d-yyyy h-mm-ss a");
    private static DateFormat csvFileFormat2 = new SimpleDateFormat("M-d-yyyy H-mm-ss");

    private static Date getDay(int day) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public static Date getDate(String date) throws ParseException {
        return csvFileFormat2.parse(date);
    }

    public static String getCsvFileFormat(Date date) {
        System.out.println("date:" + date);
        return csvFileFormat.format(date);
    }

    public static String[] getDelay(int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        return hourAndMinutes.format(calendar.getTime()).split("-");
    }

    public static String getShiftedDate(int numOfDays) {
        return dateFormat.format(getDay(numOfDays));
    }

    public static String getDbShiftedDate(int numOfDays) {
        return dbDateFormat.format(getDay(numOfDays));
    }

    public static String getMonthBeforeDate() {
        return dateFormat.format(getDay(-30));
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

    public static String getImportGroupDate() {
        return importGroupDateFormat.format(new Date());
    }

    public static String getTimeStamp() {
        return fullDateFormat.format(new Date());
    }

//    public static void main(String[] args) {
//        System.out.println(csvFileFormat.format(new Date()));
//    }
}
