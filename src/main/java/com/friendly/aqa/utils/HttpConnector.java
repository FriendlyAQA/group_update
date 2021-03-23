package com.friendly.aqa.utils;

import com.friendly.aqa.pageobject.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
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

    public static String sendSoapRequest(String request) throws IOException {
        URL urlObject = new URL(BasePage.getProps().getProperty("api_url"));
        String userPassword = BasePage.getProps().getProperty("api_user") + ":" + BasePage.getProps().getProperty("api_password");
        String encoding = Base64.getEncoder().encodeToString(userPassword.getBytes());
        HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("User-Agent", "CPE admin autotest tool");
        urlConnection.setRequestProperty("Authorization", "Basic " + encoding);
        urlConnection.setInstanceFollowRedirects(false);
        byte[] postData = request.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        urlConnection.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
        urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataLength));
        urlConnection.setDoOutput(true);
        try (DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream())) {
            dos.write(postData);
        } catch (IOException e) {
            logger.warn("IOException happened during POST parameters sending: " + e.getMessage());
        }
        InputStream inputStream = urlConnection.getInputStream();
        return toString(inputStream, false);
    }

    public static String sendRegisterRequest(String request) throws IOException {
        URL urlObject = new URL("http://95.217.85.220:8080/ftacs/ACS");
        HttpURLConnection urlConnection = (HttpURLConnection) urlObject.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("User-Agent", "CPE admin autotest tool");
        urlConnection.setInstanceFollowRedirects(false);
        byte[] postData = request.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        urlConnection.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
        urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataLength));
        urlConnection.setDoOutput(true);
        try (DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream())) {
            dos.write(postData);
        } catch (IOException e) {
            logger.warn("IOException happened during POST parameters sending: " + e.getMessage());
        }
        InputStream inputStream = urlConnection.getInputStream();
        System.out.println("gotoToString");
        System.out.println(System.currentTimeMillis());
        return toString(inputStream, false);
    }

    public static void main(String[] args) {
        try {
            System.out.println(System.currentTimeMillis());
            String s = sendRegisterRequest("<soap-env:Envelope xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:cwmp=\"urn:dslforum-org:cwmp-1-2\" xmlns:soap-enc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:soap-env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soap-env:Header><cwmp:ID soap-env:mustUnderstand=\"1\"/></soap-env:Header><soap-env:Body><cwmp:Inform><DeviceId><Manufacturer>TP-Link</Manufacturer><OUI>50D4F7</OUI><ProductClass>HC220-G1</ProductClass><SerialNumber>fakeSerial3</SerialNumber></DeviceId><Event soap-enc:arrayType=\"EventStruct[1]\"><EventStruct><EventCode>0 BOOTSTRAP</EventCode><CommandKey/></EventStruct></Event><MaxEnvelopes>0</MaxEnvelopes><CurrentTime>2021-03-17T13:44:48</CurrentTime><RetryCount>0</RetryCount><ParameterList soap-enc:arrayType=\"ParameterValueStruct[4]\"><ParameterValueStruct><Name>Device.DeviceInfo.HardwareVersion</Name><Value xsi:type=\"xsd:string\">HC220-G1 v1 00000000</Value></ParameterValueStruct><ParameterValueStruct><Name>Device.DeviceInfo.SoftwareVersion</Name><Value xsi:type=\"xsd:string\">1.0.2 0.9 v6048.0 Build 190717 Rel.40046n</Value></ParameterValueStruct><ParameterValueStruct><Name>Device.ManagementServer.ConnectionRequestURL</Name><Value xsi:type=\"string\">http://95.217.85.220:9997/fakeSerial3</Value></ParameterValueStruct><ParameterValueStruct><Name>Device.ManagementServer.ParameterKey</Name><Value xsi:type=\"xsd:string\">6611813</Value></ParameterValueStruct><ParameterValueStruct><Name>Device.Ethernet.Interface.1.MACAddress</Name><Value xsi:type=\"xsd:string\">aa:bb:cc:11:22:33</Value></ParameterValueStruct></ParameterList></cwmp:Inform></soap-env:Body></soap-env:Envelope>");
            System.out.println(System.currentTimeMillis());
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                        System.out.println("Directory 'export' has been created");
                    }
                    writer = new BufferedWriter(new FileWriter("export/" + CalendarUtil.getFileName() + ".xml"));
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