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

    public static String getValueType(String value) {
        String type = "";
        try {
            stmtObj.execute("SELECT type FROM ftacs.cpe_parameter_name WHERE name='" + value + "'");
            ResultSet resultSet = stmtObj.getResultSet();
            if (resultSet.next()) {
                type = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return type;
    }

    public static String getGroupId(String groupName) {
        String groupId = "";
        try {
            stmtObj.execute("SELECT id FROM ftacs.update_group WHERE name='" + groupName + "'");
            ResultSet resultSet = stmtObj.getResultSet();
            if (resultSet.next()) {
                groupId = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupId;
    }

    public static String[] getDevice(String serial) {
        String[] device = new String[2];
        try {
            stmtObj.execute("SELECT * FROM ftacs.product_class_group WHERE id IN (" +
                    "SELECT group_id FROM ftacs.product_class WHERE id IN (" +
                    "SELECT product_class_id FROM ftacs.cpe WHERE serial='" + serial + "'))");
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

    public static Set<String> getMonitorNameSetByManufacturer(String manufacturer) {
        String query = "SELECT name FROM ftacs.qoe_monitoring_parent WHERE id IN (" +
                "SELECT parent_id FROM ftacs.qoe_monitoring WHERE group_id IN (" +
                "SELECT group_id FROM ftacs.product_class WHERE manuf_id IN (" +
                "SELECT id FROM ftacs.manufacturer WHERE NAME='" + manufacturer + "')))";
        return getSet(query);
    }

    public static Set<String> getMonitorNameSetByModelName(String modelName) {
        String query = "SELECT name FROM ftacs.qoe_monitoring_parent WHERE id IN (" +
                "SELECT parent_id FROM ftacs.qoe_monitoring WHERE group_id IN (" +
                "SELECT group_id FROM ftacs.product_class WHERE model='" + modelName + "'))";
        return getSet(query);
    }

    public static Set<String> getDeviceProfileNameSetByManufacturer(String manufacturer) {
        String query = "SELECT name FROM ftacs.profile WHERE group_id IN (" +
                "SELECT group_id FROM ftacs.product_class WHERE manuf_id IN (" +
                "SELECT id FROM ftacs.manufacturer WHERE NAME='" + manufacturer + "'))";
        return getSet(query);
    }

    public static Set<String> getDeviceProfileNameSetByModelName(String modelName) {
        String query = "SELECT name FROM ftacs.profile WHERE group_id IN (" +
                "SELECT group_id FROM ftacs.product_class WHERE model='" + modelName + "')";
        return getSet(query);
    }

    public static Set<String> getDeviceProfileNameSetByStatus(String status) {
        String query = "SELECT name FROM ftacs.profile WHERE is_active='" + (status.equals("Active") ? "1" : "0") + "'";
        return getSet(query);
    }

    private static Set<String> getSet(String query) {
        Set<String> nameSet = new HashSet<>();
        try {
            stmtObj.execute(query);
            ResultSet resultSet = stmtObj.getResultSet();
            while (resultSet.next()) {
                nameSet.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nameSet;
    }

    public static int getDeviceAmount(String serial) {
        int amount = 0;
        try {
            stmtObj.execute("SELECT * FROM ftacs.cpe WHERE product_class_id IN (" +
                    "SELECT product_class_id FROM ftacs.cpe WHERE serial='" + serial + "')");
            ResultSet resultSet = stmtObj.getResultSet();
            while (resultSet.next()) {
                amount++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amount;
    }

    public static int getDeviceProfileIdByName(String name) {
        int profileId = -1;
        try {
            stmtObj.execute("SELECT id FROM ftacs.profile WHERE name='" + name + "'");
            ResultSet resultSet = stmtObj.getResultSet();
            if (resultSet.next()) {
                profileId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profileId;
    }

    public static void main(String[] args) {
        connectDb();
        System.out.println(getDeviceProfileIdByName("qwe"));
        disconnectDb();
    }
}
