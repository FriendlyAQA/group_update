package com.friendly.aqa.database;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class DataBase {
    private static Statement stmtObj;
    private static Connection connObj;
    private final static Logger logger = Logger.getLogger(DataBase.class);

    public static void connectDb(String url, String user, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connObj = DriverManager.getConnection(url, user, password);
            logger.info("Database Connection Open");
            stmtObj = connObj.createStatement();
            logger.info("Statement Object Created");
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void disconnectDb() {
        try {
            stmtObj.close();
            connObj.close();
            logger.info("Database Connection Closed");
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        printPendingTasksForSerial("FT001SN000010016E3F0D47666");
        System.out.println(getPendingTaskName("FT001SN000010016E3F0D47666"));
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
                logger.info("There's no pending task for specified serial");
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
            logger.info(Arrays.deepToString(row));
        }
    }

}
