package com.friendly.aqa.utils;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger;

public class XmlWriter {
    private static Logger logger = Logger.getLogger(XmlWriter.class);

    public static void createXml(Map<String, Set<String>> testSuite) {
        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\">\n" +
                "<suite name=\"All Test Suite\">\n" +
                "\t<test verbose=\"2\" name=\"CPE Admin Automation UI\">\n" +
                "\t\t<classes>\n";
        String footer = "\t\t</classes>\n" +
                "\t</test>\n" +
                "</suite>";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("testng.xml")))) {
            writer.write(header);
            Set<String> keySet = testSuite.keySet();
            for (String className : keySet) {
                writer.write("\t\t\t<class name=\"com.friendly.aqa.test." + className + "\">\n");
                writer.write("\t\t\t\t<methods>\n");
                for (String s : testSuite.get(className)) {
                    writer.write("\t\t\t\t\t<include name=\"" + s + "\"/>\n");
                }
                writer.write("\t\t\t\t</methods>\n");
                writer.write("\t\t\t</class>\n");
            }
            writer.write(footer);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String className = "GroupUpdateTests";
        Set<String> methods = new TreeSet<>();
        methods.add("test_001");
        methods.add("test_002");
//        methods.add("test_003");
        Map<String, Set<String>> map = new HashMap<>();
        map.put(className, methods);
        createXml(map);
    }
}
