package com.friendly.aqa.gui;

import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.test.GroupUpdateTR069Tests;
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
    private Set<String>[] tabTestAmount;
    private JCheckBox reRunFailedCheckbox;
    private JButton runButton;
    private JProgressBar progressBar;
    private Set<String>[] writtenTestSet;
    private Set<String> failedTestSet;
    private int[] lastTestNumber;
    private int testSum;
    private final List<Character> allowedChars = new ArrayList<>(Arrays.asList(new Character[]{44, 45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57}));
    private final String[] classNames = {"DeviceProfileTests", "DeviceUpdateTests", "GroupUpdateTests", "MonitoringTests", "EventsTests", "FileManagementTests", "ReportsTests", "SettingsTests"};
    private static Controller controller;
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Controller.class);
    private int passedTestCount;
    private int failedTestCount;
    private String reRunBuffer;


    @SuppressWarnings("unchecked")
    public Controller() {
        controller = this;
        tabTestAmount = new Set[8];
        writtenTestSet = new Set[8];
        for (int i = 0; i < 8; i++) {
            tabTestAmount[i] = new TreeSet<>();
            writtenTestSet[i] = new TreeSet<>();
        }
        handleWrittenTests();
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

    private void handleWrittenTests() {
        lastTestNumber = new int[8];
        for (Method method : GroupUpdateTR069Tests.class.getDeclaredMethods()) {
            String name = method.getName();
            writtenTestSet[2].add(name);
        }
        tabTestAmount[2] = writtenTestSet[2];
        String testName = ((TreeSet<String>) writtenTestSet[2]).last();
        lastTestNumber[2] = Integer.parseInt(testName.substring(testName.length() - 3));
        testSum = tabTestAmount[2].size();
    }

    private void calculateTestSum() {
        testSum = 0;
        for (int i = 0; i < tabTestAmount.length; i++) {
            if (enableTabCheckboxes[i].isSelected()) {
                testSum += tabTestAmount[i].size();
            }
        }
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
        XmlWriter.createXml(createTaskMap());
        calculateTestSum();
        view.setToExecValue(testSum);
        progressBar.setMaximum(testSum);
        progressBar.setValue(0);
        failedTestSet = new HashSet<>();
        TestNG testng = new TestNG();
        List<String> suites = new ArrayList<>();
        suites.add("resources/testng.xml");
        testng.setTestSuites(suites);
        testng.run();
    }

    private Map<String, Set<String>> createTaskMap() {
        if (reRunFailedCheckbox.isSelected() && reRunFailedCheckbox.isEnabled()) {
            for (Set<String> tabSet : tabTestAmount) {
                tabSet.retainAll(failedTestSet);
            }
        }
        Map<String, Set<String>> taskMap = new LinkedHashMap<>(8);
        for (int i = 0; i < tabTestAmount.length; i++) {
            Set<String> tabTestSet = new TreeSet<>(tabTestAmount[i]);
            if (!tabTestSet.isEmpty()) {
                taskMap.put(classNames[i], tabTestSet);
            }
        }
        return taskMap;
    }

    private Set<String> getTestSet(String input, int tabNum) {
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
                    for (; a <= lastTestNumber[tabNum]; a++) {
                        integerSet.add(a);
                    }
                } else {
                    integerSet.add(a);
                }
            } else if (limits.length == 2) {
                int a = limits[0].isEmpty() ? 1 : Integer.parseInt(limits[0]);
                int b = Math.min(Integer.parseInt(limits[1]), lastTestNumber[tabNum]);
                for (int j = Math.min(a, b); j <= Math.max(a, b); j++) {
                    integerSet.add(j);
                }
            }
        }
        Set<String> stringSet = new TreeSet<>();
        for (int i : integerSet) {
            stringSet.add(String.format("%s%03d", "tr069_gu_", i));
        }
        return stringSet;
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
        createExecutableTestSet(field);
    }

    private void createExecutableTestSet(JTextField field) {
        createExecutableTestSet(field, getTabNumber(field));
        calculateTestSum();
        view.setToExecValue(testSum);
        checkRunButton();
    }

    private void createExecutableTestSet(JTextField field, int tabNum) {
        Set<String> out = new TreeSet<>(writtenTestSet[tabNum]);
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
        tabTestAmount[tabNum] = out;
    }

    private void createAllExecutableTestSet() {
        for (int i = 0; i < 8; i++) {
            if (enableTabCheckboxes[i].isSelected()) {
                if (runSpecifiedButtons[i].isSelected()) {
                    createExecutableTestSet(runSpecifiedFields[i], i);
                } else {
                    createExecutableTestSet(excludeSpecificFields[i], i);
                }
            } else {
                tabTestAmount[i] = new HashSet<>();
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
        for (JCheckBox enableTabCheckbox : enableTabCheckboxes) {
            enableTabCheckbox.setSelected(enable);
            tabStateChanged(enableTabCheckbox);
        }
        runButton.setEnabled(enable);
    }

    public void tabStateChanged(JCheckBox checkBox) {
        if (!checkBox.isSelected()) {
            view.getRunEntireCheckBox().setSelected(false);
        }
        for (int i = 0; i < enableTabCheckboxes.length; i++) {
            if (enableTabCheckboxes[i] == checkBox) {
                view.setEnabled(checkBox.isSelected(), runSpecifiedButtons[i], excludeSpecificButtons[i]);
                radioButtonSelected(i);
            }
        }
        checkRunButton();
    }

    public void radioButtonSelected(int tabNum) {
        runSpecifiedFields[tabNum].setEnabled(runSpecifiedButtons[tabNum].isSelected() && runSpecifiedButtons[tabNum].isEnabled());
        excludeSpecificFields[tabNum].setEnabled(excludeSpecificButtons[tabNum].isSelected() && excludeSpecificButtons[tabNum].isEnabled());
        tabTestAmount[tabNum] = new TreeSet<>(writtenTestSet[tabNum]);
        textChanged(runSpecifiedButtons[tabNum].isSelected() ? runSpecifiedFields[tabNum] : excludeSpecificFields[tabNum]);
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
        checkRunButton();
        view.getShowReportButton().setEnabled(true);
    }

    private void checkRunButton() {
        boolean enable = false;
        if (testSum != 0) {
            for (JCheckBox box : enableTabCheckboxes) {
                if (box.isSelected()) {
                    enable = true;
                    break;
                }
            }
        }
        runButton.setEnabled(enable);
        reRunFailedCheckbox.setEnabled(enable && !view.getFailedFieldText().isEmpty());
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
                view.setEnabled(false, enableTabCheckboxes[i], runSpecifiedFields[i], excludeSpecificFields[i], runSpecifiedButtons[i], excludeSpecificButtons[i]);
            } else {
                if (enableTabCheckboxes[i].isSelected()) {
                    view.setEnabled(true, enableTabCheckboxes[i], runSpecifiedButtons[i].isSelected() ? runSpecifiedFields[i] : excludeSpecificFields[i], runSpecifiedButtons[i], excludeSpecificButtons[i]);
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
        private TextArea textArea;
        private StringBuilder buffer = new StringBuilder();

        public TextAreaOutputStream(TextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) {
            buffer.append((char) b);
            if (b == 10) {
                textArea.append(buffer.toString());
                buffer = new StringBuilder();
            }
        }
    }
}
