package com.friendly.aqa.utils;

import java.io.*;
import java.util.*;

import com.friendly.aqa.gui.Controller;
import org.apache.log4j.Logger;

public class XmlWriter {
    private static Logger logger = Logger.getLogger(XmlWriter.class);

    public static void createXml(Set<Controller.TabTask> testSuite) {
        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\">\n" +
                "<suite name=\"CPE Admin Automation UI\">\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("resources/testng.xml")))) {
            writer.write(header);
            for (Controller.TabTask task : testSuite) {
                String tabName = task.getTabName();
                writer.write("\t<test verbose=\"2\" name=\"" + tabName + "\">\n\t\t<classes>\n");
                Map<String, Set<String>> classMap = task.getClassMap();
                Set<String> classnameSet = classMap.keySet();
                for (String className: classnameSet){
                    writer.write("\t\t\t<class name=\"com.friendly.aqa.test." + className + "\">\n");
                    writer.write("\t\t\t\t<methods>\n");
                    Set<String> testSet = classMap.get(className);
                    for (String testName: testSet){
                        writer.write("\t\t\t\t\t<include name=\"" + testName + "\"/>\n");
                    }
                    writer.write("\t\t\t\t</methods>\n");
                    writer.write("\t\t\t</class>\n");
                }
                writer.write("\t\t</classes>\n");
                writer.write("\t</test>\n");
            }
            writer.write("</suite>");
        } catch (IOException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }
    }
}
