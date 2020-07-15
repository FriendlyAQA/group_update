package com.friendly.aqa.utils;

import com.friendly.aqa.gui.Controller;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static com.friendly.aqa.pageobject.BasePage.getProtocolPrefix;
import static com.friendly.aqa.pageobject.BasePage.getSerial;

public class XmlWriter {
    private static Logger logger = Logger.getLogger(XmlWriter.class);

    public static void createTestngConfig(Set<Controller.TabTask> testSuite) {
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
                for (String className : classnameSet) {
                    writer.write("\t\t\t<class name=\"com.friendly.aqa.test." + className + "\">\n");
                    writer.write("\t\t\t\t<methods>\n");
                    Set<String> testSet = classMap.get(className);
                    for (String testName : testSet) {
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

    public static void createImportCpeFile() {
        String pathName = "import/" + getProtocolPrefix() + "_import_cpe.xml";
        String header = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n<serials>\n\t<serial Manufacturer=\"";
        String serial = getSerial();
        String[] deviceArr = DataBaseConnector.getDevice(serial);
        String device = deviceArr[0] + "\" Model=\"" + deviceArr[1] + "\">" + serial;
        String footer = "</serial>\n</serials>";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(pathName)))) {
            writer.write(header);
            writer.write(device);
            writer.write(footer);
        } catch (IOException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createImportGroupFile() {
        String pathName = "import/" + getProtocolPrefix() + "_import_group.xml";
        String[] deviceArr = DataBaseConnector.getDevice(getSerial());
        String protocol = getProtocolPrefix().toUpperCase();
        if (protocol.equals("TR181")) {
            protocol = "TR069";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n").append("<Update>\n").append("\t<Name>imported_group</Name>\n")
                .append("\t<ProductGroupValue>\n").append("\t\t<ProductGroup>\n").append("\t\t\t<Manufacturer>")
                .append(deviceArr[0]).append("</Manufacturer>\n\t\t\t<ModelName>").append(deviceArr[1])
                .append("</ModelName>\n\t\t\t<ProtocolId>").append(protocol).append("</ProtocolId>\n")
                .append("\t\t</ProductGroup>\n").append("\t</ProductGroupValue>\n").append("\t<Tasks>\n").append("\t\t<TaskAction>\n")
                .append("\t\t\t<Name>CPEReprovision</Name>\n").append("\t\t</TaskAction>\n").append("\t</Tasks>\n")
                .append("\t<ScheduledDate>01/01/1</ScheduledDate>\n\t<ActivateDate>").append(CalendarUtil.getImportGroupDate())
                .append("</ActivateDate>\n\t<Period1> - /0/0</Period1>\n").append("\t<Period2> - /0/0</Period2>\n")
                .append("\t<Online>false</Online>\n").append("\t<AskToConnect>true</AskToConnect>\n").append("\t<Location>0</Location>\n")
                .append("\t<Threshold>0</Threshold>\n").append("\t<StopFail>false</StopFail>\n").append("\t<Reactivation>\n")
                .append("\t\t<Start>0001-01-01T00:00:00</Start>\n").append("\t\t<Time />\n").append("\t\t<Finish>0001-01-01T00:00:00</Finish>\n")
                .append("\t\t<RepeatCount>0</RepeatCount>\n").append("\t\t<FailOnly>false</FailOnly>\n")
                .append("\t\t<RepeatEvery>0</RepeatEvery>\n").append("\t\t<Expression />\n")
                .append("\t\t<RandomCount>0</RandomCount>\n").append("\t</Reactivation>\n").append("</Update>");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(pathName)))) {
            writer.write(sb.toString());
        } catch (IOException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }
    }
}
