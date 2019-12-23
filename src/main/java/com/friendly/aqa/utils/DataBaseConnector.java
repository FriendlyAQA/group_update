package com.friendly.aqa.utils;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class DataBaseConnector {
    private static Statement stmtObj;
    private static Connection connObj;
    private final static Logger LOGGER = Logger.getLogger(DataBaseConnector.class);

    public static void connectDb(String url, String user, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connObj = DriverManager.getConnection(url, user, password);
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

    public static void main(String[] args) {
        connectDb("jdbc:mysql://95.217.85.220", "ftacs", "ftacs");
        for (String[] line : getTaskList("607")) {
            System.out.println(Arrays.deepToString(line));
        }
        disconnectDb();
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

    private static List<String[]> getPendingTasksForSerial(String serial) {
        List<String[]> pendingTaskList = new ArrayList<>();
        try {
            stmtObj.execute("SELECT * FROM ftacs.cpe WHERE serial = '" + serial + "'");
            ResultSet resultSet = stmtObj.getResultSet();
            String cpe_id;
            if (resultSet.next()) {
                cpe_id = resultSet.getString(1);
            } else {
                LOGGER.info("There's no pending task for specified serial");
                return pendingTaskList;
            }
            stmtObj.execute("SELECT * FROM ftacs.cpe_pending_task WHERE cpe_id = '" + cpe_id + "'");
            resultSet = stmtObj.getResultSet();
            while (resultSet.next()) {
                String[] row = new String[10];
                for (int i = 0; i < 10; i++) {
                    row[i] = resultSet.getString(i + 1);
                }
                pendingTaskList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pendingTaskList;
    }

    public static String getPendingTaskName(String serial) {
        String response = "";
        List<String[]> pendingTaskList = getPendingTasksForSerial(serial);
        if (pendingTaskList.size() > 0) {
            response = pendingTaskList.get(0)[7];
        }
        return response;
    }

    private static void printPendingTasksForSerial(String serial) {
        List<String[]> pendingTaskList = getPendingTasksForSerial(serial);
        for (String[] row : pendingTaskList) {
            LOGGER.info(Arrays.deepToString(row));
        }
    }
}
