package com.friendly.aqa.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements WindowListener {

    View view;
    JRadioButton[] runSpecifiedButtons;
    JRadioButton[] excludeSpecificButtons;
    JTextField[] runSpecifiedFields;
    JTextField[] excludeSpecificFields;
    JCheckBox[] enableTabCheckboxes;
    JCheckBox reRunFailedCheckbox;
    JButton runButton;
    final List<Character> allowedChars = new ArrayList<>(Arrays.asList(new Character[]{44, 45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57}));

    public static void main(String[] args) {
        Controller controller = new Controller();
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
        EventQueue.invokeLater(() -> (controller.view = new View(controller)).setVisible(true));
        controller.run();
    }

    void run() {
        System.out.println("controller thread name:" + Thread.currentThread().getName());
    }

    public void runPressed(boolean start) {
//        runButton.setEnabled(false);
        for (Integer i : getTestSet(runSpecifiedFields[2].getText())) {
            view.addLogString(i + "\n");
        }
        view.addLogString("\n");
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
    }

    public void tabStateChanged(JCheckBox checkBox) {
        for (int i = 0; i < enableTabCheckboxes.length; i++) {
            if (enableTabCheckboxes[i] == checkBox) {
                view.setEnabled(checkBox.isSelected(), runSpecifiedButtons[i], excludeSpecificButtons[i]);
                testSelected(i);
            }
        }
        checkRunButton();
    }

    private Set<Integer> getTestSet(String input) {
        int first = 1, last = 280;
        Set<Integer> out = new TreeSet<>();
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
                    for (; a <= last; a++) {
                        out.add(a);
                    }
                } else {
                    out.add(a);
                }
            } else if (limits.length == 2) {
                int a = limits[0].isEmpty() ? first : Integer.parseInt(limits[0]);
                int b = Math.min(Integer.parseInt(limits[1]), last);
                for (int j = Math.min(a, b); j <= Math.max(a, b); j++) {
                    out.add(j);
                }
            }
        }
        return out;
    }

    public void testSelected(int tabNum) {
        runSpecifiedFields[tabNum].setEnabled(runSpecifiedButtons[tabNum].isSelected() && runSpecifiedButtons[tabNum].isEnabled());
        excludeSpecificFields[tabNum].setEnabled(excludeSpecificButtons[tabNum].isSelected() && excludeSpecificButtons[tabNum].isEnabled());
    }

    public void enableAllTabs(boolean enable) {
        for (JCheckBox enableTabCheckbox : enableTabCheckboxes) {
            enableTabCheckbox.setSelected(enable);
            tabStateChanged(enableTabCheckbox);
        }
        runButton.setEnabled(enable);
    }

    public void testsuiteStarted() {
        runButton.setText("STOP");
        checkRunButton();
    }

    public void testsuiteStopped() {
        runButton.setText("RUN");
        checkRunButton();
    }

    private void checkRunButton() {
        boolean enable = false;
        for (JCheckBox box : enableTabCheckboxes) {
            if (box.isSelected()) {
                enable = true;
                break;
            }
        }
        runButton.setEnabled(enable);
        reRunFailedCheckbox.setEnabled(enable && !view.getFailedFieldText().isEmpty());
    }

    @Override
    public void windowOpened(WindowEvent e) {
        runSpecifiedButtons = view.getRunSpecifiedRadioButtonArray();
        excludeSpecificButtons = view.getExcludeSpecificRadioButtonArray();
        runSpecifiedFields = view.getRunSpecifiedFieldArray();
        excludeSpecificFields = view.getExcludeSpecificFieldArray();
        enableTabCheckboxes = view.getEnableTabCheckBoxArray();
        runButton = view.getRunButton();
        reRunFailedCheckbox = view.getReRunCheckBox();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("closing");
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
}
