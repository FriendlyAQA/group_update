package com.friendly.aqa.utils;

import com.friendly.aqa.pageobject.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

public class HttpConnector {
    private static final Logger logger = org.apache.log4j.Logger.getLogger(HttpConnector.class);

    public static String sendGetRequest(String url) throws IOException {
        Map<String, String> requestProperty = getRequestProperty();
        return sendRequest(url, "GET", requestProperty, null);
    }

    public static String sendPostRequest(String url, String postParameters) throws IOException {
        Map<String, String> requestProperty = getRequestProperty();
        return sendRequest(url, "POST", requestProperty, postParameters);
    }

    private static String sendRequest(String url, String requestMethod, Map<String, String> requestProperty, String postParameters) throws IOException {
        URL urlObject = new URL(url);
        boolean requestMethodIsPost = (requestMethod != null && postParameters != null && requestMethod.equals("POST"));
        HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();
        urlConnection.setRequestMethod(requestMethodIsPost ? "POST" : "GET");
        Set<Map.Entry<String, String>> propertySet = requestProperty.entrySet();

        for (Map.Entry<String, String> current : propertySet) {
            if (current.getKey() != null && current.getValue() != null) {
                urlConnection.setRequestProperty(current.getKey(), current.getValue());
            }
        }
        if (requestMethodIsPost) {
            urlConnection.setInstanceFollowRedirects(false);

            byte[] postData = postParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataLength));
            urlConnection.setDoOutput(true);
            try (DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream())) {
                dos.write(postData);
            } catch (IOException e) {
                logger.warn("IOException happened during POST parameters sending: " + e.getMessage());
            }
        }
        InputStream inputStream = urlConnection.getInputStream();
        return toString(inputStream, !requestMethodIsPost);
    }

    private static String toString(InputStream inputStream, boolean writeOnDisc) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Callable<String> task = () -> {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            BufferedWriter writer = null;
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                if (writeOnDisc) {
                    if (new File("export").mkdir()) {
                        System.out.println("Directory 'export' was created");
                    }
                    writer = new BufferedWriter(new FileWriter(new File("export/" + CalendarUtil.getFileName() + ".xml")));
                }
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                    if (writeOnDisc) {
                        writer.write(inputLine);
                        writer.newLine();
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getClass().getSimpleName());
                e.printStackTrace();
                logger.warn(e.getMessage());
            } finally {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            }
            return stringBuilder.toString();
        };
        Future<String> future = executor.submit(task);
        String result = "Connection failed!";
        int timeout = Integer.parseInt(BasePage.getProps().getProperty("driver_implicitly_wait"));
        try {
            result = future.get(timeout, TimeUnit.SECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException ex) {
            logger.warn(ex.getClass().getSimpleName() + " caught while loading the HTML page");
        } finally {
            future.cancel(true);
        }
        return result;
    }

    private static Map<String, String> getRequestProperty() {
        Map<String, String> requestProperty = new HashMap<>();
        requestProperty.put("User-Agent", ((JavascriptExecutor) BasePage.getDriver()).executeScript("return navigator.userAgent;").toString());
        requestProperty.put("Referer", BasePage.getProps().getProperty("ui_url"));
        Set<Cookie> cookies = BasePage.getDriver().manage().getCookies();
        for (Cookie c : cookies) {
            requestProperty.put("Cookie", c.getName() + "=" + c.getValue());
        }
        return requestProperty;
    }
}