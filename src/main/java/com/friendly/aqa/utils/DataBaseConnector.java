package com.friendly.aqa.utils;

import com.friendly.aqa.pageobject.BasePage;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class DataBaseConnector {
    private static final Logger LOGGER = Logger.getLogger(DataBaseConnector.class);
    private static final Properties PROPS = BasePage.getProps();
    private static Statement stmtObj;
    private static Connection connObj;

    public static void connectDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connObj = DriverManager.getConnection(PROPS.getProperty("db_url"), PROPS.getProperty("db_user"), PROPS.getProperty("db_password"));
            LOGGER.info("Database Connection Open");
            stmtObj = connObj.createStatement();
            LOGGER.info("Statement Object Created");
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void disconnectDb() {
        try {
            stmtObj.close();
            connObj.close();
            LOGGER.info("Database Connection Closed");
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static List<String[]> getTaskList(String ug_id) {
        List<String[]> taskList = new ArrayList<>();
        try {
            stmtObj.execute("SELECT * FROM ftacs.ug_cpe_completed WHERE ug_id = '" + ug_id + "'");
            ResultSet resultSet = stmtObj.getResultSet();
            while (resultSet.next()) {
                String[] row = new String[8];
                for (int i = 0; i < 8; i++) {
                    row[i] = resultSet.getString(i + 1);
                }
                taskList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskList;
    }

    public static String getValueType(String value, String serial) {
        String type = "";
        try {
            stmtObj.execute("SELECT type FROM ftacs.cpe_parameter_name WHERE name='" + value + "' AND id IN (" +
                    "SELECT name_id FROM ftacs.cpe_parameter WHERE cpe_id IN (" +
                    "SELECT cpe_id FROM ftacs.cpe_serial WHERE serial='" + serial + "'))");//TODO remove serial from query;
            ResultSet resultSet = stmtObj.getResultSet();
            if (resultSet.next()) {
                type = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }

    public static String[] getDevice(String serial) {
        String[] device = new String[2];
        try {
            stmtObj.execute("SELECT * FROM ftacs.product_class_group WHERE id IN (SELECT group_id FROM ftacs.cpe_serial WHERE serial='" + serial + "')");
            ResultSet resultSet = stmtObj.getResultSet();
            if (resultSet.next()) {
                for (int i = 0; i < 2; i++) {
                    device[i] = resultSet.getString(i + 2);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (device[0] == null) {
            LOGGER.error("Serial not found on server. Check config.properties 'cpe_serial' field!");
        }
        return device;
    }

    public static void main(String[] args) {
        connectDb();
        System.out.println(Arrays.deepToString(getDevice("")));
        disconnectDb();
    }
}
