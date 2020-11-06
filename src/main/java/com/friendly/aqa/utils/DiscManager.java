package com.friendly.aqa.utils;

import com.friendly.aqa.pageobject.BasePage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DiscManager {
    private static boolean isRunning;
    private static boolean isEndOfFileReached;
    private static final String FILE_PATH = /*"resources/server.log"*/BasePage.getProps().getProperty("server_log_path");
    private static final String TIME_REGEX = "^(\\d{2}[:,]){3}\\d{3}\\sINFO\\s{2}.+";
    private static String time;
    private static String regex;
    private static List<String> regexList;

    static void run() {
        if (!new File(FILE_PATH).exists()) {
            throw new AssertionError("server.log file not found at '" + FILE_PATH + "'!");
        }
        new Thread(() -> {
//            Timer timer = new Timer(30000);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH), StandardCharsets.UTF_8))) {
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
                    } else {
                        pause();
                        Thread.yield();
                    }
                }
            } catch (IOException e) {
                System.out.println("Error happens during log file reading!");
                isRunning = false;
            }
            System.out.println("Reading log file Thread '" + Thread.currentThread().getName() + "' stopped");
        }).start();
    }

    private static void pause() {
        try {
            Thread.sleep(1000);
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
    }

    public static void main(String[] args) {
        run();
    }
}
