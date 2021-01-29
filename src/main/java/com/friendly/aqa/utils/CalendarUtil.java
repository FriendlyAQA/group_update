package com.friendly.aqa.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarUtil {
    private static final DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
    private static final DateFormat shiftedStartFormat = new SimpleDateFormat("M/d/yyyy HH:mm");
    private static final DateFormat importGroupDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private static final DateFormat fullDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CET' yyyy", Locale.ENGLISH);
    private static final DateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private static final DateFormat fileNameFormat = new SimpleDateFormat("MM-dd-yyyy_HH-mm");
    private static final DateFormat hourAndMinutes = new SimpleDateFormat("HH-mm");
    private static final DateFormat hours = new SimpleDateFormat("HH");
    private static final DateFormat minutes = new SimpleDateFormat("mm");
    private static final DateFormat csvFileFormat = new SimpleDateFormat("M-d-yyyy h-mm-ss a");
    private static final DateFormat csvFileFormat2 = new SimpleDateFormat("M-d-yyyy H-mm-ss");

    public static Date getDay(int day) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public static Date getShiftedDateBy(int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    public static long getMidnightMillis() {
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 23);
        date.set(Calendar.MINUTE, 59);
        date.set(Calendar.SECOND, 58);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTime().getTime();
    }

    public static Date getDate(String date) throws ParseException {
        return csvFileFormat2.parse(date);
    }

    public static Date getDbDate(String date) throws ParseException {
        return dbDateFormat.parse(date);
    }

    public static String convertDate(String dbDate) {
        try {
            return dateFormat.format(getDbDate(dbDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new AssertionError("Cannot convert Date format!");
    }

    public static String getDateByPattern(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
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

    public static String getShiftedTimeBy(int minutes) {
        return shiftedStartFormat.format(getShiftedDateBy(minutes));
    }

    public static String getDbShiftedDate(int numOfDays) {
        return dbDateFormat.format(getDay(numOfDays));
    }

    public static String getMonthBeforeDate() {
        return dateFormat.format(getDay(-30));
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

    public static void main(String[] args) {
        System.out.println(getShiftedTimeBy(10));
    }
}
