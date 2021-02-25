package com.friendly.aqa.gui;

import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.XmlWriter;
import org.apache.log4j.PropertyConfigurator;
import org.testng.TestNG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller implements WindowListener, Runnable {

    private View view;
    private JRadioButton[] runSpecifiedButtons, excludeSpecificButtons;
    private JTextField[] runSpecifiedFields, excludeSpecificFields;
    private JCheckBox[] enableTabCheckboxes;
    private final Set<String>[][] tabTestAmount;
    private JCheckBox reRunFailedCheckbox;
    private JCheckBox[][] protocolCheckBoxes;
    private JButton runButton;
    private JProgressBar progressBar;
    private final Set<String>[][] sourceTestSet;
    private Set<String> failedTestSet;
    private final int[][] lastTestNumber;
    private int testSum;
    private final List<Character> allowedChars = new ArrayList<>(Arrays.asList(new Character[]{44, 45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57}));
    private final String[][] classNames = {
            {"DeviceProfileTR069Tests", "DeviceProfileTR181Tests", "DeviceProfileLwm2mTests", "DeviceProfileMqttTests", "DeviceProfileUspTests"},
            {"DeviceUpdateTR069Tests", "DeviceUpdateTR181Tests", "DeviceUpdateLwm2mTests", "DeviceUpdateMqttTests", "DeviceUpdateUspTests"},
            {"GroupUpdateTR069Tests", "GroupUpdateTR181Tests", "GroupUpdateLwm2mTests", "GroupUpdateMqttTests", "GroupUpdateUspTests"},
            {"MonitoringTR069Tests", "MonitoringTR181Tests", "MonitoringLwm2mTests", "MonitoringMqttTests", "MonitoringUspTests"},
            {"EventsTR069Tests", "EventsTR181Tests", "EventsLwm2Tests", "EventsMqttTests", "EventsUspTests"},
            {"ReportsTests"},
            {"FileManagementTests"},
            {"SettingsTests"}};
    private final String[][] testNames = {
            {"tr069_dp_", "tr181_dp_", "lwm2m_dp_", "mqtt_dp_", "usp_dp_"},
            {"tr069_du_", "tr181_du_", "lwm2m_du_", "mqtt_du_", "usp_du_"},
            {"tr069_gu_", "tr181_gu_", "lwm2m_gu_", "mqtt_gu_", "usp_gu_"},
            {"tr069_mo_", "tr181_mo_", "lwm2m_mo_", "mqtt_mo_", "usp_mo_"},
            {"tr069_ev_", "tr181_ev_", "lwm2m_ev_", "mqtt_ev_", "usp_ev_"},
            {"reports_"},
            {"fileMng_"},
            {"settings_"}};
    private final String[] tabNames = {"Device Profile", "Device Update", "Group Update", "Monitoring", "Events", "Reports", "File Management", "Settings"};
    private static Controller controller;
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Controller.class);
    private int passedTestCount;
    private int failedTestCount;
    private String reRunBuffer;


    @SuppressWarnings("unchecked")
    public Controller() {
        controller = this;
        tabTestAmount = new Set[8][];
        sourceTestSet = new Set[8][];
        lastTestNumber = new int[8][];
        for (int i = 0; i < 8; i++) {
            int length = i < 5 ? 5 : 1;
            tabTestAmount[i] = new Set[length];
            sourceTestSet[i] = new Set[length];
            lastTestNumber[i] = new int[length];
            for (int j = 0; j < length; j++) {
                tabTestAmount[i][j] = new TreeSet<>();
                sourceTestSet[i][j] = new TreeSet<>();
            }
        }
        handleSourceTests();
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(() -> (view = new View(this)).setVisible(true));
    }

    private void handleSourceTests() {
        String classPath = "com.friendly.aqa.test.";
        for (int i = 0; i < classNames.length; i++) {
            String[] tab = classNames[i];
            for (int j = 0; j < tab.length; j++) {
                String clazz = tab[j];
                try {
                    for (Method method : Class.forName(classPath + clazz).getDeclaredMethods()) {
                        String name = method.getName();
                        sourceTestSet[i][j].add(name);
                        tabTestAmount[i][j].add(name);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (!sourceTestSet[i][j].isEmpty()) {
                    String lastTestName = ((TreeSet<String>) sourceTestSet[i][j]).last();
                    lastTestNumber[i][j] = Integer.parseInt(lastTestName.substring(lastTestName.length() - 3));
                }
            }
        }
    }

    private void calculateTestSum() {
        testSum = 0;
        for (int i = 0; i < 5; i++) {
            if (enableTabCheckboxes[i].isSelected()) {
                for (int j = 0; j < tabTestAmount[i].length; j++) {
                    if (protocolCheckBoxes[i][j].isSelected())
                        testSum += tabTestAmount[i][j].size();
                }
            }
        }
        for (int i = 5; i < 8; i++) {
            if (enableTabCheckboxes[i].isSelected()) {
                testSum += tabTestAmount[i][0].size();
            }
        }
        view.setToExecValue(testSum);
    }

    public void runPressed(boolean start) {
        runButton.setEnabled(false);
        if (start) {
            view.clearAll();
            new Thread(this).start();
        } else {
            BaseTestCase.interruptTestRunning(true);
        }
    }

    public void run() {
        XmlWriter.createTestngConfig(createTaskMap());
        calculateTestSum();
        progressBar.setMaximum(testSum);
        progressBar.setValue(0);
        failedTestSet = new HashSet<>();
        TestNG testng = new TestNG();
        List<String> suites = new ArrayList<>();
        suites.add("resources/testng.xml");
        testng.setTestSuites(suites);
        testng.run();
    }

    private Set<TabTask> createTaskMap() {
        if (reRunFailedCheckbox.isSelected() && reRunFailedCheckbox.isEnabled()) {
            for (Set<String>[] tabSet : tabTestAmount) {
                for (Set<String> testSet : tabSet)
                    testSet.retainAll(failedTestSet);
            }
        }
        Set<TabTask> taskSet = new LinkedHashSet<>(8);
        for (int i = 0; i < tabTestAmount.length; i++) {//Tabs
            if (!enableTabCheckboxes[i].isSelected()) {
                continue;
            }
            TabTask task = new TabTask(tabNames[i]);
            for (int j = 0; j < tabTestAmount[i].length; j++) {//Protocols
                if (i < 5 && !protocolCheckBoxes[i][j].isSelected()) {
                    continue;
                }
                Set<String> testSet = tabTestAmount[i][j];
                if (testSet.isEmpty()) {
                    continue;
                }
                task.addTestSet(classNames[i][j], testSet);
            }
            if (!task.isEmpty()) {
                taskSet.add(task);
            }
        }
        return taskSet;
    }

    private int tabProtocolCount(int tabNum) {
        int count = 0;
        JCheckBox[] protocolCheckBox = protocolCheckBoxes[tabNum];
        for (int i = 0; i < 5; i++) {
            JCheckBox protocol = protocolCheckBox[i];
            if (protocol.isSelected()) {
                count++;
            }
        }
        return count;
    }

    private boolean isSingleProtocol(int tabNum) {
        return tabNum > 4 || tabProtocolCount(tabNum) == 1;
    }

    private int getActiveProtocol(int tabNum) {
        int activeProtocol = -1;
        if (tabNum > 4) {
            return 0;
        }
        JCheckBox[] protocolCheckBox = protocolCheckBoxes[tabNum];
        for (int i = 0; i < protocolCheckBox.length; i++) {
            if (protocolCheckBox[i].isSelected()) {
                activeProtocol = i;
                break;
            }
        }
        return activeProtocol;
    }

    private Set<String> getTestSet(String input, int tabNum) {
        int activeProtocol = getActiveProtocol(tabNum);
        Set<Integer> integerSet = new HashSet<>();
        List<String> rangeList = new ArrayList<>(Arrays.asList(input.split(",")));
        for (int i = 0; i < rangeList.size(); i++) {
            String range = rangeList.get(i);
            String[] limits = range.split("-");
            if (limits.length == 0) {
                continue;
            }
            if (limits.length == 1 && !limits[0].isEmpty()) {
                int a = Integer.parseInt(limits[0]);
                if (input.endsWith("-") && i == rangeList.size() - 1) {
                    for (; a <= lastTestNumber[tabNum][activeProtocol]; a++) {
                        integerSet.add(a);
                    }
                } else {
                    integerSet.add(a);
                }
            } else if (limits.length == 2) {
                int a = limits[0].isEmpty() ? 1 : Integer.parseInt(limits[0]);
                int b = Math.min(Integer.parseInt(limits[1]), lastTestNumber[tabNum][activeProtocol]);
                for (int j = Math.min(a, b); j <= Math.max(a, b); j++) {
                    integerSet.add(j);
                }
            }
        }
        Set<String> testSet = new TreeSet<>();
        for (int i : integerSet) {
            testSet.add(String.format("%s%03d", testNames[tabNum][activeProtocol], i));
        }
        return testSet;
    }

    public void textChanged(JTextField field) {
        char[] chars = field.getText().toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (allowedChars.contains(c)) {
                sb.append(c);
            }
        }
        int position = field.getCaretPosition();
        String out = sb.toString()
                .replaceAll("-+,?", "-")
                .replaceAll("^-,", "-")
                .replaceAll(",-", ",")
                .replaceAll(",+", ",");
        out = out.replaceAll("(.+)?-\\d*-", out.length() > 0 ? out.substring(0, out.length() - 1) : "");
        field.setText(out);
        if (position < sb.toString().length()) {
            field.setCaretPosition(position);
        }
        createExecutableTabTestSet(field);
    }

    private void createExecutableTabTestSet(JTextField field) {
        createExecutableTabTestSet(field, getTabNumber(field));
        calculateTestSum();
        redefineStartButtonState();
    }

    private void createExecutableTabTestSet(JTextField field, int tabNum) {
        int activeProtocol = getActiveProtocol(tabNum);
        if (activeProtocol < 0) {
            return;
        }
        Set<String> out = new TreeSet<>(sourceTestSet[tabNum][activeProtocol]);//java.lang.ArrayIndexOutOfBoundsException: 5
        Pattern p = Pattern.compile("-?\\d+\\S*");
        String input = field.getText();
        Matcher m = p.matcher(input);
        if (m.find()) {
            if (runSpecifiedButtons[tabNum].isSelected()) {
                out.retainAll(getTestSet(input, tabNum));
            } else {
                out.removeAll(getTestSet(input, tabNum));
            }
        }
        tabTestAmount[tabNum][activeProtocol] = out;
    }

    private void createAllExecutableTestSet() {
        for (int i = 0; i < 8; i++) {
            if (enableTabCheckboxes[i].isSelected() && getActiveProtocol(i) >= 0) {
                if (runSpecifiedButtons[i].isSelected()) {
                    createExecutableTabTestSet(runSpecifiedFields[i], i);
                } else {
                    createExecutableTabTestSet(excludeSpecificFields[i], i);
                }
            } else {
                cleanTabTestAmount(i);
            }
        }
    }

    private int getTabNumber(JTextField field) {
        for (int i = 0; i < 8; i++) {
            if (field == runSpecifiedFields[i]) {
                return i;
            }
        }
        for (int i = 0; i < 8; i++) {
            if (field == excludeSpecificFields[i]) {
                return i;
            }
        }
        return -1;
    }

    public void enableAllTabs(boolean enable) {
        for (int i = 0; i < enableTabCheckboxes.length; i++) {
            enableTabCheckboxes[i].setSelected(enable);
            tabStateChanged(i);
        }
    }

    public void enableSelectedTabs(boolean enable) {
        for (int i = 0; i < enableTabCheckboxes.length; i++) {
            enableTabCheckboxes[i].setSelected(enableTabCheckboxes[i].isSelected());
            tabStateChanged(i);
        }
    }

    public void tabStateChanged(int tabNum) {
        JCheckBox checkBox = enableTabCheckboxes[tabNum];
        if (!checkBox.isSelected()) {
            view.getRunEntireCheckBox().setSelected(false);
        }
        if (tabNum < 5) {
            view.setEnabled(checkBox.isSelected(), protocolCheckBoxes[tabNum]);
        }
        view.setEnabled(checkBox.isSelected() && isSingleProtocol(tabNum), runSpecifiedButtons[tabNum], excludeSpecificButtons[tabNum]);
        runSpecifiedFields[tabNum].setEnabled(runSpecifiedButtons[tabNum].isSelected() && runSpecifiedButtons[tabNum].isEnabled());
        excludeSpecificFields[tabNum].setEnabled(excludeSpecificButtons[tabNum].isSelected() && excludeSpecificButtons[tabNum].isEnabled());
        textChanged(runSpecifiedButtons[tabNum].isSelected() ? runSpecifiedFields[tabNum] : excludeSpecificFields[tabNum]);
        calculateTestSum();
        redefineStartButtonState();
    }

    public void protocolChanged(JCheckBox source) {
        for (int i = 0; i < 5; i++) {
            JCheckBox[] tabProtocolArray = protocolCheckBoxes[i];
            for (int j = 0; j < 6; j++) {
                if (tabProtocolArray[j] == source) {
                    if (j == 5) {
                        for (int k = 0; k < 5; k++) {
                            tabProtocolArray[k].setSelected(tabProtocolArray[5].isSelected());
                        }
                    } else if (!source.isSelected()) {
                        tabProtocolArray[5].setSelected(false);
                    } else if (tabProtocolCount(i) == 5) {
                        tabProtocolArray[5].setSelected(true);
                    }
                    if (!isSingleProtocol(i)) {
                        runSpecifiedFields[i].setText("");
                        excludeSpecificFields[i].setText("");
                    }
                    tabStateChanged(i);
                }
            }
        }
    }

    private void cleanTabTestAmount(int tabNum) {
        for (int i = 0; i < tabTestAmount[tabNum].length; i++) {
            tabTestAmount[tabNum][i] = new LinkedHashSet<>();
        }
    }

    public void testSuiteStarted() {
        passedTestCount = 0;
        failedTestCount = 0;
        runButton.setText("STOP");
        runButton.setEnabled(true);
        view.getShowReportButton().setEnabled(false);
    }

    public void testSuiteStopped() {
        runButton.setText("RUN");
        redefineStartButtonState();
        view.getShowReportButton().setEnabled(true);
        reRunFailedCheckbox.setSelected(false);
        view.getRunEntireCheckBox().setSelected(false);
        view.getRunEntireCheckBox().setEnabled(true);
        enableSelectedTabs(true);
    }

    private void redefineStartButtonState() {
        boolean readyToStart = false;
        if (testSum != 0) {
            for (int i = 0; i < enableTabCheckboxes.length; i++) {
                JCheckBox box = enableTabCheckboxes[i];
                if (box.isSelected() && getActiveProtocol(i) >= 0) {
                    readyToStart = true;
                    break;
                }
            }
        }
        runButton.setEnabled(readyToStart);
        reRunFailedCheckbox.setEnabled(readyToStart && failedTestCount > 0);
    }

    public void testPassed(String testName) {
        view.setPassedFieldText(++passedTestCount);
        view.addTestResult(true, testName);
        progressBar.setValue(passedTestCount + failedTestCount);
    }

    public void testFailed(String testName) {
        failedTestSet.add(testName);
        view.setFailedFieldText(++failedTestCount);
        view.addTestResult(false, testName);
        progressBar.setValue(passedTestCount + failedTestCount);
    }

    public void reRunClicked(boolean isSelected) {
        view.getRunEntireCheckBox().setEnabled(!isSelected);
        for (int i = 0; i < 8; i++) {
            if (isSelected) {
                view.setEnabled(false, enableTabCheckboxes[i], runSpecifiedFields[i], excludeSpecificFields[i]
                        , runSpecifiedButtons[i], excludeSpecificButtons[i]);
            } else {
                if (enableTabCheckboxes[i].isSelected()) {
                    view.setEnabled(true, enableTabCheckboxes[i], runSpecifiedButtons[i].isSelected()
                            ? runSpecifiedFields[i]
                            : excludeSpecificFields[i], runSpecifiedButtons[i], excludeSpecificButtons[i]);
                }
            }
        }
        if (isSelected) {
            reRunBuffer = view.getToExecText();
            view.setToExecValue(view.getFailedFieldText());
        } else {
            view.setToExecValue(reRunBuffer);
            createAllExecutableTestSet();
        }
    }

    public static Controller getController() {
        return controller;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        view.setToExecValue(testSum);
        runSpecifiedButtons = view.getRunSpecifiedRadioButtonArray();
        excludeSpecificButtons = view.getExcludeSpecificRadioButtonArray();
        runSpecifiedFields = view.getRunSpecifiedFieldArray();
        excludeSpecificFields = view.getExcludeSpecificFieldArray();
        enableTabCheckboxes = view.getEnableTabCheckBoxArray();
        protocolCheckBoxes = view.getProtocolCheckBoxArray();
        runButton = view.getRunButton();
        reRunFailedCheckbox = view.getReRunCheckBox();
        progressBar = view.getProgressBar();
        PrintStream out = new PrintStream(new TextAreaOutputStream(view.getTextArea()));
        System.out.println("System output is redirecting to application frame");
        System.setOut(out);
        System.setErr(out);
        PropertyConfigurator.configure("resources/log4j.properties");
        logger.info("Application started\n");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        logger.info("Application closed\n\n\n");
        if (BasePage.getDriver() != null) {
            BasePage.getDriver().quit();
        }
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public static void main(String[] args) {
        new Controller();
    }

    public void showReport() {
        try {
            Desktop.getDesktop().open(new File("test-output/index.html"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class TextAreaOutputStream extends OutputStream {
        private final TextArea textArea;
        private StringBuilder buffer = new StringBuilder();

        public TextAreaOutputStream(TextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) {
            buffer.append((char) b);
            if (b == 10) {
                if (!buffer.toString().startsWith("JavaScript")) {
                    textArea.append(buffer.toString());
                }
                buffer = new StringBuilder();
            }
        }
    }

    public static class TabTask {
        private final String tabName;
        private final Map<String, Set<String>> classMap;

        public TabTask(String tabName) {
            this.tabName = tabName;
            classMap = new LinkedHashMap<>(5);
        }

        public void addTestSet(String className, Set<String> testSet) {
            classMap.put(className, testSet);
        }

        public String getTabName() {
            return tabName;
        }

        public Map<String, Set<String>> getClassMap() {
            return classMap;
        }

        public boolean isEmpty() {
            return classMap.isEmpty();
        }
    }
}
