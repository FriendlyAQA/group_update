package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    final List<Character> allowedChars = new ArrayList<>(Arrays.asList(new Character[]{32, 44, 45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57}));

//    public Controller() {
//        allowedChars = new ArrayList<>(Arrays.asList(new Character[]{32, 44, 45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57}));
//    }


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
        runButton.setEnabled(false);
    }

    public void textChanged(JTextField field) {
        char[] chars = field.getText().toCharArray();
        StringBuilder out = new StringBuilder();
        for (char c : chars) {
            if (allowedChars.contains(c)) {
                out.append(c);
            }
        }
        int position = field.getCaretPosition();
        field.setText(out.toString().replaceAll("-+", "-"));
        if (position < out.toString().length()) {
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
