package com.friendly.aqa.gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class View extends JFrame {

    public View(Controller controller) {
        this.controller = controller;
        initComponents();
        initArrays();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        System.out.println("view thread name:" + Thread.currentThread().getName());
    }

    private void initComponents() {

        buttonGroup0 = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel0 = new javax.swing.JPanel();
        enableTabCheckBox0 = new javax.swing.JCheckBox();
        runSpecifiedRadioButton0 = new javax.swing.JRadioButton();
        excludeSpecificRadioButton0 = new javax.swing.JRadioButton();
        runSpecifiedField0 = new javax.swing.JTextField();
        defaultLabel0 = new javax.swing.JLabel();
        excludeSpecificField0 = new javax.swing.JTextField();
        skippedlabel0 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        enableTabCheckBox1 = new javax.swing.JCheckBox();
        runSpecifiedRadioButton1 = new javax.swing.JRadioButton();
        excludeSpecificRadioButton1 = new javax.swing.JRadioButton();
        runSpecifiedField1 = new javax.swing.JTextField();
        defaultLabel1 = new javax.swing.JLabel();
        excludeSpecificField1 = new javax.swing.JTextField();
        skippedlabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        enableTabCheckBox2 = new javax.swing.JCheckBox();
        runSpecifiedRadioButton2 = new javax.swing.JRadioButton();
        excludeSpecificRadioButton2 = new javax.swing.JRadioButton();
        runSpecifiedField2 = new javax.swing.JTextField();
        defaultLabel2 = new javax.swing.JLabel();
        excludeSpecificField2 = new javax.swing.JTextField();
        skippedlabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        enableTabCheckBox3 = new javax.swing.JCheckBox();
        runSpecifiedRadioButton3 = new javax.swing.JRadioButton();
        excludeSpecificRadioButton3 = new javax.swing.JRadioButton();
        runSpecifiedField3 = new javax.swing.JTextField();
        defaultLabel3 = new javax.swing.JLabel();
        excludeSpecificField3 = new javax.swing.JTextField();
        skippedlabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        enableTabCheckBox4 = new javax.swing.JCheckBox();
        runSpecifiedRadioButton4 = new javax.swing.JRadioButton();
        excludeSpecificRadioButton4 = new javax.swing.JRadioButton();
        runSpecifiedField4 = new javax.swing.JTextField();
        defaultLabel4 = new javax.swing.JLabel();
        excludeSpecificField4 = new javax.swing.JTextField();
        skippedlabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        enableTabCheckBox5 = new javax.swing.JCheckBox();
        runSpecifiedRadioButton5 = new javax.swing.JRadioButton();
        excludeSpecificRadioButton5 = new javax.swing.JRadioButton();
        runSpecifiedField5 = new javax.swing.JTextField();
        defaultLabel5 = new javax.swing.JLabel();
        excludeSpecificField5 = new javax.swing.JTextField();
        skippedlabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        enableTabCheckBox6 = new javax.swing.JCheckBox();
        runSpecifiedRadioButton6 = new javax.swing.JRadioButton();
        excludeSpecificRadioButton6 = new javax.swing.JRadioButton();
        runSpecifiedField6 = new javax.swing.JTextField();
        defaultLabel6 = new javax.swing.JLabel();
        excludeSpecificField6 = new javax.swing.JTextField();
        skippedlabel6 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        enableTabCheckBox7 = new javax.swing.JCheckBox();
        runSpecifiedRadioButton7 = new javax.swing.JRadioButton();
        excludeSpecificRadioButton7 = new javax.swing.JRadioButton();
        runSpecifiedField7 = new javax.swing.JTextField();
        defaultLabel7 = new javax.swing.JLabel();
        excludeSpecificField7 = new javax.swing.JTextField();
        skippedlabel7 = new javax.swing.JLabel();
        runButton = new javax.swing.JButton();
        runEntireCheckBox = new javax.swing.JCheckBox();
        scrollPane2 = new java.awt.ScrollPane();
        textArea = new java.awt.TextArea();
        passedField = new javax.swing.JTextField();
        passedlabel = new javax.swing.JLabel();
        failedField = new javax.swing.JTextField();
        failedLabel = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        reRunCheckBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jSeparator1 = new javax.swing.JSeparator();
        testResultsLabel = new javax.swing.JLabel();
        logLabel = new javax.swing.JLabel();
        toExecutionLabel = new javax.swing.JLabel();
        toExecValue = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Automation UI");
        setIconImage(new ImageIcon("friendly.png").getImage());
        setPreferredSize(new java.awt.Dimension(745, 527));
        setResizable(false);

        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        enableTabCheckBox0.setText("Enable tests on current tab");
        enableTabCheckBox0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableTabCheckBoxPerformed(evt);
            }
        });

        buttonGroup0.add(runSpecifiedRadioButton0);
        runSpecifiedRadioButton0.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedRadioButton0.setSelected(true);
        runSpecifiedRadioButton0.setText("Run specific");
        runSpecifiedRadioButton0.setEnabled(false);
        runSpecifiedRadioButton0.setMaximumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton0.setMinimumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton0.setPreferredSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        buttonGroup0.add(excludeSpecificRadioButton0);
        excludeSpecificRadioButton0.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificRadioButton0.setText("Exclude specific");
        excludeSpecificRadioButton0.setEnabled(false);
        excludeSpecificRadioButton0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        runSpecifiedField0.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedField0.setEnabled(false);
        runSpecifiedField0.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        defaultLabel0.setForeground(new java.awt.Color(255, 0, 0));
        defaultLabel0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        defaultLabel0.setText("(default: all)");
        defaultLabel0.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        defaultLabel0.setMaximumSize(new java.awt.Dimension(126, 14));
        defaultLabel0.setMinimumSize(new java.awt.Dimension(126, 14));
        defaultLabel0.setPreferredSize(new java.awt.Dimension(126, 14));

        excludeSpecificField0.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificField0.setEnabled(false);
        excludeSpecificField0.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        skippedlabel0.setForeground(new java.awt.Color(255, 0, 0));
        skippedlabel0.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        skippedlabel0.setText("(tests that will be skipped)");
        skippedlabel0.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel0Layout = new javax.swing.GroupLayout(jPanel0);
        jPanel0.setLayout(jPanel0Layout);
        jPanel0Layout.setHorizontalGroup(
                jPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel0Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(enableTabCheckBox0)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(runSpecifiedRadioButton0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(runSpecifiedField0)
                                        .addComponent(defaultLabel0, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(skippedlabel0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificRadioButton0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificField0, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(325, Short.MAX_VALUE))
        );
        jPanel0Layout.setVerticalGroup(
                jPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel0Layout.createSequentialGroup()
                                .addGroup(jPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel0Layout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addComponent(enableTabCheckBox0))
                                        .addGroup(jPanel0Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificRadioButton0)
                                                        .addComponent(runSpecifiedRadioButton0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificField0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(runSpecifiedField0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(defaultLabel0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(skippedlabel0))))
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Device Profile", jPanel0);

        enableTabCheckBox1.setText("Enable tests on current tab");
        enableTabCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableTabCheckBoxPerformed(evt);
            }
        });

        buttonGroup1.add(runSpecifiedRadioButton1);
        runSpecifiedRadioButton1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedRadioButton1.setSelected(true);
        runSpecifiedRadioButton1.setText("Run specific");
        runSpecifiedRadioButton1.setEnabled(false);
        runSpecifiedRadioButton1.setMaximumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton1.setMinimumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton1.setPreferredSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        buttonGroup1.add(excludeSpecificRadioButton1);
        excludeSpecificRadioButton1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificRadioButton1.setText("Exclude specific");
        excludeSpecificRadioButton1.setEnabled(false);
        excludeSpecificRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        runSpecifiedField1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedField1.setEnabled(false);
        runSpecifiedField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        defaultLabel1.setForeground(new java.awt.Color(255, 0, 0));
        defaultLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        defaultLabel1.setText("(default: all)");
        defaultLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        defaultLabel1.setMaximumSize(new java.awt.Dimension(126, 14));
        defaultLabel1.setMinimumSize(new java.awt.Dimension(126, 14));
        defaultLabel1.setPreferredSize(new java.awt.Dimension(126, 14));

        excludeSpecificField1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificField1.setEnabled(false);
        excludeSpecificField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        skippedlabel1.setForeground(new java.awt.Color(255, 0, 0));
        skippedlabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        skippedlabel1.setText("(tests that will be skipped)");
        skippedlabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(enableTabCheckBox1)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(runSpecifiedRadioButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(runSpecifiedField1)
                                        .addComponent(defaultLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(skippedlabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificRadioButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificField1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(325, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addComponent(enableTabCheckBox1))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificRadioButton1)
                                                        .addComponent(runSpecifiedRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(runSpecifiedField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(defaultLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(skippedlabel1))))
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Device Update", jPanel1);

        enableTabCheckBox2.setSelected(true);
        enableTabCheckBox2.setText("Enable tests on current tab");
        enableTabCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableTabCheckBoxPerformed(evt);
            }
        });

        buttonGroup2.add(runSpecifiedRadioButton2);
        runSpecifiedRadioButton2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedRadioButton2.setSelected(true);
        runSpecifiedRadioButton2.setText("Run specific");
        runSpecifiedRadioButton2.setMaximumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton2.setMinimumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton2.setPreferredSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        buttonGroup2.add(excludeSpecificRadioButton2);
        excludeSpecificRadioButton2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificRadioButton2.setText("Exclude specific");
        excludeSpecificRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        runSpecifiedField2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        defaultLabel2.setForeground(new java.awt.Color(255, 0, 0));
        defaultLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        defaultLabel2.setText("(default: all)");
        defaultLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        defaultLabel2.setMaximumSize(new java.awt.Dimension(126, 14));
        defaultLabel2.setMinimumSize(new java.awt.Dimension(126, 14));
        defaultLabel2.setPreferredSize(new java.awt.Dimension(126, 14));

        excludeSpecificField2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificField2.setEnabled(false);
        excludeSpecificField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        skippedlabel2.setForeground(new java.awt.Color(255, 0, 0));
        skippedlabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        skippedlabel2.setText("(tests that will be skipped)");
        skippedlabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(enableTabCheckBox2)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(runSpecifiedRadioButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(runSpecifiedField2)
                                        .addComponent(defaultLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(skippedlabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificRadioButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificField2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(325, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addComponent(enableTabCheckBox2))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificRadioButton2)
                                                        .addComponent(runSpecifiedRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(runSpecifiedField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(defaultLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(skippedlabel2))))
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Group Update", jPanel2);

        enableTabCheckBox3.setText("Enable tests on current tab");
        enableTabCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableTabCheckBoxPerformed(evt);
            }
        });

        buttonGroup3.add(runSpecifiedRadioButton3);
        runSpecifiedRadioButton3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedRadioButton3.setSelected(true);
        runSpecifiedRadioButton3.setText("Run specific");
        runSpecifiedRadioButton3.setEnabled(false);
        runSpecifiedRadioButton3.setMaximumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton3.setMinimumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton3.setPreferredSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        buttonGroup3.add(excludeSpecificRadioButton3);
        excludeSpecificRadioButton3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificRadioButton3.setText("Exclude specific");
        excludeSpecificRadioButton3.setEnabled(false);
        excludeSpecificRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        runSpecifiedField3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedField3.setEnabled(false);
        runSpecifiedField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        defaultLabel3.setForeground(new java.awt.Color(255, 0, 0));
        defaultLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        defaultLabel3.setText("(default: all)");
        defaultLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        defaultLabel3.setMaximumSize(new java.awt.Dimension(126, 14));
        defaultLabel3.setMinimumSize(new java.awt.Dimension(126, 14));
        defaultLabel3.setPreferredSize(new java.awt.Dimension(126, 14));

        excludeSpecificField3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificField3.setEnabled(false);
        excludeSpecificField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        skippedlabel3.setForeground(new java.awt.Color(255, 0, 0));
        skippedlabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        skippedlabel3.setText("(tests that will be skipped)");
        skippedlabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(enableTabCheckBox3)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(runSpecifiedRadioButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(runSpecifiedField3)
                                        .addComponent(defaultLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(skippedlabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificRadioButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificField3, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(325, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addComponent(enableTabCheckBox3))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificRadioButton3)
                                                        .addComponent(runSpecifiedRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(runSpecifiedField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(defaultLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(skippedlabel3))))
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Monitoring", jPanel3);

        enableTabCheckBox4.setText("Enable tests on current tab");
        enableTabCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableTabCheckBoxPerformed(evt);
            }
        });

        buttonGroup4.add(runSpecifiedRadioButton4);
        runSpecifiedRadioButton4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedRadioButton4.setSelected(true);
        runSpecifiedRadioButton4.setText("Run specific");
        runSpecifiedRadioButton4.setEnabled(false);
        runSpecifiedRadioButton4.setMaximumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton4.setMinimumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton4.setPreferredSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        buttonGroup4.add(excludeSpecificRadioButton4);
        excludeSpecificRadioButton4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificRadioButton4.setText("Exclude specific");
        excludeSpecificRadioButton4.setEnabled(false);
        excludeSpecificRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        runSpecifiedField4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedField4.setEnabled(false);
        runSpecifiedField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        defaultLabel4.setForeground(new java.awt.Color(255, 0, 0));
        defaultLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        defaultLabel4.setText("(default: all)");
        defaultLabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        defaultLabel4.setMaximumSize(new java.awt.Dimension(126, 14));
        defaultLabel4.setMinimumSize(new java.awt.Dimension(126, 14));
        defaultLabel4.setPreferredSize(new java.awt.Dimension(126, 14));

        excludeSpecificField4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificField4.setEnabled(false);
        excludeSpecificField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        skippedlabel4.setForeground(new java.awt.Color(255, 0, 0));
        skippedlabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        skippedlabel4.setText("(tests that will be skipped)");
        skippedlabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(enableTabCheckBox4)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(runSpecifiedRadioButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(runSpecifiedField4)
                                        .addComponent(defaultLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(skippedlabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificRadioButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificField4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(325, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addComponent(enableTabCheckBox4))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificRadioButton4)
                                                        .addComponent(runSpecifiedRadioButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(runSpecifiedField4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(defaultLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(skippedlabel4))))
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Events", jPanel4);

        enableTabCheckBox5.setText("Enable tests on current tab");
        enableTabCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableTabCheckBoxPerformed(evt);
            }
        });

        buttonGroup6.add(runSpecifiedRadioButton5);
        runSpecifiedRadioButton5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedRadioButton5.setSelected(true);
        runSpecifiedRadioButton5.setText("Run specific");
        runSpecifiedRadioButton5.setEnabled(false);
        runSpecifiedRadioButton5.setMaximumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton5.setMinimumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton5.setPreferredSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        buttonGroup6.add(excludeSpecificRadioButton5);
        excludeSpecificRadioButton5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificRadioButton5.setText("Exclude specific");
        excludeSpecificRadioButton5.setEnabled(false);
        excludeSpecificRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        runSpecifiedField5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedField5.setEnabled(false);
        runSpecifiedField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        defaultLabel5.setForeground(new java.awt.Color(255, 0, 0));
        defaultLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        defaultLabel5.setText("(default: all)");
        defaultLabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        defaultLabel5.setMaximumSize(new java.awt.Dimension(126, 14));
        defaultLabel5.setMinimumSize(new java.awt.Dimension(126, 14));
        defaultLabel5.setPreferredSize(new java.awt.Dimension(126, 14));

        excludeSpecificField5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificField5.setEnabled(false);
        excludeSpecificField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        skippedlabel5.setForeground(new java.awt.Color(255, 0, 0));
        skippedlabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        skippedlabel5.setText("(tests that will be skipped)");
        skippedlabel5.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(enableTabCheckBox5)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(runSpecifiedRadioButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(runSpecifiedField5)
                                        .addComponent(defaultLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(skippedlabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificRadioButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificField5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(325, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addComponent(enableTabCheckBox5))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificRadioButton5)
                                                        .addComponent(runSpecifiedRadioButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(runSpecifiedField5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(defaultLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(skippedlabel5))))
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Reports", jPanel5);

        enableTabCheckBox6.setText("Enable tests on current tab");
        enableTabCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableTabCheckBoxPerformed(evt);
            }
        });

        buttonGroup5.add(runSpecifiedRadioButton6);
        runSpecifiedRadioButton6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedRadioButton6.setSelected(true);
        runSpecifiedRadioButton6.setText("Run specific");
        runSpecifiedRadioButton6.setEnabled(false);
        runSpecifiedRadioButton6.setMaximumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton6.setMinimumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton6.setPreferredSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        buttonGroup5.add(excludeSpecificRadioButton6);
        excludeSpecificRadioButton6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificRadioButton6.setText("Exclude specific");
        excludeSpecificRadioButton6.setEnabled(false);
        excludeSpecificRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        runSpecifiedField6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedField6.setEnabled(false);
        runSpecifiedField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        defaultLabel6.setForeground(new java.awt.Color(255, 0, 0));
        defaultLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        defaultLabel6.setText("(default: all)");
        defaultLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        defaultLabel6.setMaximumSize(new java.awt.Dimension(126, 14));
        defaultLabel6.setMinimumSize(new java.awt.Dimension(126, 14));
        defaultLabel6.setPreferredSize(new java.awt.Dimension(126, 14));

        excludeSpecificField6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificField6.setEnabled(false);
        excludeSpecificField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        skippedlabel6.setForeground(new java.awt.Color(255, 0, 0));
        skippedlabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        skippedlabel6.setText("(tests that will be skipped)");
        skippedlabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(enableTabCheckBox6)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(runSpecifiedRadioButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(runSpecifiedField6)
                                        .addComponent(defaultLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(skippedlabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificRadioButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificField6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(325, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addComponent(enableTabCheckBox6))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificRadioButton6)
                                                        .addComponent(runSpecifiedRadioButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(runSpecifiedField6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(defaultLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(skippedlabel6))))
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("File Management", jPanel6);

        enableTabCheckBox7.setText("Enable tests on current tab");
        enableTabCheckBox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableTabCheckBoxPerformed(evt);
            }
        });

        buttonGroup7.add(runSpecifiedRadioButton7);
        runSpecifiedRadioButton7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedRadioButton7.setSelected(true);
        runSpecifiedRadioButton7.setText("Run specific");
        runSpecifiedRadioButton7.setEnabled(false);
        runSpecifiedRadioButton7.setMaximumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton7.setMinimumSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton7.setPreferredSize(new java.awt.Dimension(121, 25));
        runSpecifiedRadioButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        buttonGroup7.add(excludeSpecificRadioButton7);
        excludeSpecificRadioButton7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificRadioButton7.setText("Exclude specific");
        excludeSpecificRadioButton7.setEnabled(false);
        excludeSpecificRadioButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testSelectActionPerformed(evt);
            }
        });

        runSpecifiedField7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        runSpecifiedField7.setEnabled(false);
        runSpecifiedField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        defaultLabel7.setForeground(new java.awt.Color(255, 0, 0));
        defaultLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        defaultLabel7.setText("(default: all)");
        defaultLabel7.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        defaultLabel7.setMaximumSize(new java.awt.Dimension(126, 14));
        defaultLabel7.setMinimumSize(new java.awt.Dimension(126, 14));
        defaultLabel7.setPreferredSize(new java.awt.Dimension(126, 14));

        excludeSpecificField7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        excludeSpecificField7.setEnabled(false);
        excludeSpecificField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textChanged(evt);
            }
        });

        skippedlabel7.setForeground(new java.awt.Color(255, 0, 0));
        skippedlabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        skippedlabel7.setText("(tests that will be skipped)");
        skippedlabel7.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(enableTabCheckBox7)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(runSpecifiedRadioButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(runSpecifiedField7)
                                        .addComponent(defaultLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(skippedlabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificRadioButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(excludeSpecificField7, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(325, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addGap(35, 35, 35)
                                                .addComponent(enableTabCheckBox7))
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificRadioButton7)
                                                        .addComponent(runSpecifiedRadioButton7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(excludeSpecificField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(runSpecifiedField7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(defaultLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(skippedlabel7))))
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Settings", jPanel7);

        jTabbedPane1.setSelectedIndex(2);

        runButton.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        runButton.setText("RUN");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        runEntireCheckBox.setText("Run entire test suite");
        runEntireCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runEntireActionPerformed(evt);
            }
        });

        textArea.setEditable(false);
        scrollPane2.add(textArea);

        passedField.setEditable(false);
        passedField.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        passedField.setForeground(new java.awt.Color(0, 153, 0));
        passedField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        passedlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        passedlabel.setText("Passed");

        failedField.setEditable(false);
        failedField.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        failedField.setForeground(new java.awt.Color(255, 0, 51));
        failedField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        failedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        failedLabel.setText("Failed");

        jProgressBar1.setStringPainted(true);

        reRunCheckBox.setText("Re-run failed tests");
        reRunCheckBox.setEnabled(false);

        jTextPane1.setEditable(false);
        jTextPane1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jScrollPane1.setViewportView(jTextPane1);

        testResultsLabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        testResultsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        testResultsLabel.setText("Test results");

        logLabel.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        logLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logLabel.setText("Log");

        toExecutionLabel.setText("To execution:");

        toExecValue.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jTabbedPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(testResultsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(scrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(logLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(runEntireCheckBox)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(reRunCheckBox)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(toExecutionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(toExecValue))
                                                                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(passedField)
                                                                        .addComponent(passedlabel, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(failedField)
                                                                        .addComponent(failedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(testResultsLabel)
                                        .addComponent(logLabel))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(passedlabel)
                                                        .addComponent(toExecValue)
                                                        .addComponent(toExecutionLabel)
                                                        .addComponent(reRunCheckBox)
                                                        .addComponent(runEntireCheckBox)
                                                        .addComponent(failedLabel))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(passedField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(failedField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void runButtonActionPerformed(ActionEvent evt) {
        controller.runPressed(runButton.getText().equals("RUN"));
    }

    public void addTestResult(boolean pass, String testName) {
        appendToPane(jTextPane1, testName + (pass ? " passed\n" : " failed\n"), pass ? Color.GREEN.darker() : Color.RED);
    }

    private void textChanged(KeyEvent evt) {
        JTextField textField = (JTextField) evt.getSource();
        controller.textChanged(textField);
    }

    private void runEntireActionPerformed(ActionEvent evt) {
        boolean isSelected = ((JCheckBox) evt.getSource()).isSelected();
        controller.enableAllTabs(isSelected);
    }

    private void enableTabCheckBoxPerformed(ActionEvent evt) {
        JCheckBox checkBox = (JCheckBox) evt.getSource();
        controller.tabStateChanged(checkBox);
    }

    private void testSelectActionPerformed(ActionEvent evt) {
        JRadioButton radioButton = (JRadioButton) evt.getSource();
        int num = -1;
        for (int i = 0; i < runSpecifiedRadioButtonArray.length; i++) {
            if (radioButton == runSpecifiedRadioButtonArray[i]) {
                num = i;
                break;
            }
        }
        for (int i = 0; i < excludeSpecificRadioButtonArray.length; i++) {
            if (radioButton == excludeSpecificRadioButtonArray[i]) {
                num = i;
                break;
            }
        }
        controller.testSelected(num);
    }

    public void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        tp.setEditable(true);
        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
        tp.setEditable(false);
    }

    public void setEnabled(boolean enable, JComponent... components) {
        for (JComponent component : components) {
            component.setEnabled(enable);
        }
    }

    public void addLogEntry(String text) {
        textArea.append(text);
    }

    public void setPassedFieldText(int count) {
        passedField.setText(String.valueOf(count));
    }

    public void setFailedFieldText(int count) {
        failedField.setText(String.valueOf(count));
    }

    public void clearAll() {
        jTextPane1.setText("");
        textArea.setText("");
        passedField.setText("");
        failedField.setText("");

    }

    public void setToExecValue(int text) {
        toExecValue.setText(String.valueOf(text));
    }

    private void initArrays() {
        runSpecifiedRadioButtonArray = new JRadioButton[]{runSpecifiedRadioButton0, runSpecifiedRadioButton1, runSpecifiedRadioButton2, runSpecifiedRadioButton3, runSpecifiedRadioButton4, runSpecifiedRadioButton5, runSpecifiedRadioButton6, runSpecifiedRadioButton7};
        excludeSpecificRadioButtonArray = new JRadioButton[]{excludeSpecificRadioButton0, excludeSpecificRadioButton1, excludeSpecificRadioButton2, excludeSpecificRadioButton3, excludeSpecificRadioButton4, excludeSpecificRadioButton5, excludeSpecificRadioButton6, excludeSpecificRadioButton7};
        enableTabCheckBoxArray = new JCheckBox[]{enableTabCheckBox0, enableTabCheckBox1, enableTabCheckBox2, enableTabCheckBox3, enableTabCheckBox4, enableTabCheckBox5, enableTabCheckBox6, enableTabCheckBox7};
        runSpecifiedFieldArray = new JTextField[]{runSpecifiedField0, runSpecifiedField1, runSpecifiedField2, runSpecifiedField3, runSpecifiedField4, runSpecifiedField5, runSpecifiedField6, runSpecifiedField7};
        excludeSpecificFieldArray = new JTextField[]{excludeSpecificField0, excludeSpecificField1, excludeSpecificField2, excludeSpecificField3, excludeSpecificField4, excludeSpecificField5, excludeSpecificField6, excludeSpecificField7};
        addWindowListener(controller);
    }

    public JButton getRunButton() {
        return runButton;
    }

    public JRadioButton[] getRunSpecifiedRadioButtonArray() {
        return runSpecifiedRadioButtonArray;
    }

    public JRadioButton[] getExcludeSpecificRadioButtonArray() {
        return excludeSpecificRadioButtonArray;
    }

    public JCheckBox[] getEnableTabCheckBoxArray() {
        return enableTabCheckBoxArray;
    }

    public JTextField[] getRunSpecifiedFieldArray() {
        return runSpecifiedFieldArray;
    }

    public JTextField[] getExcludeSpecificFieldArray() {
        return excludeSpecificFieldArray;
    }

    public JCheckBox getReRunCheckBox() {
        return reRunCheckBox;
    }

    public String getFailedFieldText() {
        return failedField.getText();
    }

    public JProgressBar getProgressBar() {
        return jProgressBar1;
    }

    private ButtonGroup buttonGroup0;
    private ButtonGroup buttonGroup1;
    private ButtonGroup buttonGroup2;
    private ButtonGroup buttonGroup3;
    private ButtonGroup buttonGroup4;
    private ButtonGroup buttonGroup5;
    private ButtonGroup buttonGroup6;
    private ButtonGroup buttonGroup7;
    private JLabel defaultLabel0;
    private JLabel defaultLabel1;
    private JLabel defaultLabel2;
    private JLabel defaultLabel3;
    private JLabel defaultLabel4;
    private JLabel defaultLabel5;
    private JLabel defaultLabel6;
    private JLabel defaultLabel7;
    private JCheckBox enableTabCheckBox0;
    private JCheckBox enableTabCheckBox1;
    private JCheckBox enableTabCheckBox2;
    private JCheckBox enableTabCheckBox3;
    private JCheckBox enableTabCheckBox4;
    private JCheckBox enableTabCheckBox5;
    private JCheckBox enableTabCheckBox6;
    private JCheckBox enableTabCheckBox7;
    private JTextField excludeSpecificField0;
    private JTextField excludeSpecificField1;
    private JTextField excludeSpecificField2;
    private JTextField excludeSpecificField3;
    private JTextField excludeSpecificField4;
    private JTextField excludeSpecificField5;
    private JTextField excludeSpecificField6;
    private JTextField excludeSpecificField7;
    private JRadioButton excludeSpecificRadioButton0;
    private JRadioButton excludeSpecificRadioButton1;
    private JRadioButton excludeSpecificRadioButton2;
    private JRadioButton excludeSpecificRadioButton3;
    private JRadioButton excludeSpecificRadioButton4;
    private JRadioButton excludeSpecificRadioButton5;
    private JRadioButton excludeSpecificRadioButton6;
    private JRadioButton excludeSpecificRadioButton7;
    private JTextField failedField;
    private JLabel failedLabel;
    private JPanel jPanel0;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JPanel jPanel7;
    private JProgressBar jProgressBar1;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JTabbedPane jTabbedPane1;
    private JTextPane jTextPane1;
    private JLabel logLabel;
    private JTextField passedField;
    private JLabel passedlabel;
    private JCheckBox reRunCheckBox;
    private JButton runButton;
    private JCheckBox runEntireCheckBox;
    private JTextField runSpecifiedField0;
    private JTextField runSpecifiedField1;
    private JTextField runSpecifiedField2;
    private JTextField runSpecifiedField3;
    private JTextField runSpecifiedField4;
    private JTextField runSpecifiedField5;
    private JTextField runSpecifiedField6;
    private JTextField runSpecifiedField7;
    private JRadioButton runSpecifiedRadioButton0;
    private JRadioButton runSpecifiedRadioButton1;
    private JRadioButton runSpecifiedRadioButton2;
    private JRadioButton runSpecifiedRadioButton3;
    private JRadioButton runSpecifiedRadioButton4;
    private JRadioButton runSpecifiedRadioButton5;
    private JRadioButton runSpecifiedRadioButton6;
    private JRadioButton runSpecifiedRadioButton7;
    private ScrollPane scrollPane2;
    private JLabel skippedlabel0;
    private JLabel skippedlabel1;
    private JLabel skippedlabel2;
    private JLabel skippedlabel3;
    private JLabel skippedlabel4;
    private JLabel skippedlabel5;
    private JLabel skippedlabel6;
    private JLabel skippedlabel7;
    private JLabel testResultsLabel;
    private TextArea textArea;
    private javax.swing.JLabel toExecValue;
    private javax.swing.JLabel toExecutionLabel;
    // End
    private Controller controller;
    private JRadioButton[] runSpecifiedRadioButtonArray;
    private JRadioButton[] excludeSpecificRadioButtonArray;
    private JCheckBox[] enableTabCheckBoxArray;
    private JTextField[] runSpecifiedFieldArray;
    private JTextField[] excludeSpecificFieldArray;
}

