package com.friendly.aqa.utils;

import com.friendly.aqa.gui.Controller;
import com.friendly.aqa.pageobject.BasePage;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;

public class HttpConnector {

    private static Logger logger = org.apache.log4j.Logger.getLogger(HttpConnector.class);

    public static String getUrlSource(String url) throws IOException {
        Map<String, String> cookie = new HashMap<>();
        cookie.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
        cookie.put("Referer", BasePage.getProps().getProperty("ui_url"));
        return getUrlSource(url, "GET", cookie, null);
    }

    public static String getUrlSource(String url, String requestMethod, Map<String, String> requestProperty, Map<String, String> postParameters) throws IOException {
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
            StringBuilder urlParams = new StringBuilder();
            Set<Map.Entry<String, String>> paramSet = postParameters.entrySet();
            for (Map.Entry<String, String> current : paramSet) {
                if (urlParams.length() > 0) {
                    urlParams.append("&");
                }
                if (current.getKey() != null && current.getValue() != null) {
                    urlParams.append(current.getKey());
                    urlParams.append("=");
                    urlParams.append(current.getValue());
                }
            }
            String urlParameters = urlParams.toString();
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataLength));
            urlConnection.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream())) {
                wr.write(postData);
            } catch (IOException e) {
                logger.warn("IOException happens during POST parameters sending: \" + e.getMessage()");
            }
        }
        InputStream inputStream = urlConnection.getInputStream();
        return toString(inputStream);
    }

    private static String toString(InputStream inputStream) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Callable<String> task = () -> {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                 BufferedWriter writer = new BufferedWriter(
                         new FileWriter(new File("export/" + CalendarUtil.getFileName() + ".xml")))
            ) {
                while ((inputLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                    writer.write(inputLine);
                    writer.newLine();
                }
            } catch (IOException e) {
                logger.warn(e.getMessage());
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
}