package com.friendly.aqa.utils;

import com.friendly.aqa.pageobject.BasePage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class DiscManager {
    private static boolean isRunning;
    private static boolean isEndOfFileReached;
    private static final String FILE_PATH = BasePage.getProps().getProperty("server_log_path");
    private static final String TIME_REGEX = "^(\\d{4}-\\d{2}-\\d{2}\\s)?(\\d{2}[:,]){3}\\d{3}.*?\\sINFO\\s{2}.+";
    private static String time;
    private static String regex;

    static void run() {
        if (!new File(FILE_PATH).exists()) {
            throw new AssertionError("server.log file not found at '" + FILE_PATH + "'!");
        }
        new Thread(() -> {
            long newLogfileTime = CalendarUtil.getMidnightMillis();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH), StandardCharsets.UTF_8))) {
                System.out.println("Reading log file thread '" + Thread.currentThread().getName() + "' opened (" + new Date() + ")");
                String line;
                while (isRunning) {
                    line = reader.readLine();
                    if (!isEndOfFileReached) {
                        if (line != null) {
                            continue;
                        }
                        isEndOfFileReached = true;
                    }
                    if (line != null && regex != null) {
                        if (line.matches(TIME_REGEX)) {
                            time = line;
                            continue;
                        }
                        if (line.matches(regex)) {
                            System.out.println(time);
                            System.out.println(line);
                            regex = null;
                        }
                    } else if (System.currentTimeMillis() > newLogfileTime) {
                        reader.close();
                        System.out.println("Reader closed (" + new Date() + ")");
                        pause(4000);
                        System.out.println("Trying to reopen new log file (a new day is coming)");
                        run();
                        break;
                    } else {
                        pause(500);
                        Thread.yield();
                    }
                }
            } catch (IOException e) {
                System.out.println("Error happened during log file reading!");
                isRunning = false;
            }
            System.out.println("Reading log file thread '" + Thread.currentThread().getName() + "' stopped");
        }).start();
    }

    private static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isReady() {
        return isEndOfFileReached;
    }

    public static void startReading() {
        if (!isRunning) {
            isRunning = true;
            run();
        }
    }

    public static void setRegex(String regex) {
        DiscManager.regex = regex;
    }

    public static boolean isEventFound() {
        return regex == null;
    }

    public static void stopReading() {
        isRunning = false;
        isEndOfFileReached = false;
    }

    public static void main(String[] args) {
        run();
    }
}
