package com.friendly.aqa.utils;

import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.test.BaseTestCase;
import org.apache.log4j.Logger;
import org.testng.asserts.Assertion;

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

    public static List<String[]> getTaskList() {
        String ug_id = getGroupUpdateId(BaseTestCase.getTestName());
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

    public static String getGroupUpdateId(String groupName) {
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
        return getValueSet(query);
    }

    public static Set<String> getMonitorNameSetByModelName(String modelName) {
        String query = "SELECT name FROM ftacs.qoe_monitoring_parent WHERE id IN (" +
                "SELECT parent_id FROM ftacs.qoe_monitoring WHERE group_id IN (" +
                "SELECT group_id FROM ftacs.product_class WHERE model='" + modelName + "'))";
        return getValueSet(query);
    }

    public static Set<String> getDeviceProfileNameSetByManufacturer(String manufacturer) {
        String query = "SELECT name FROM ftacs.profile WHERE group_id IN (" +
                "SELECT group_id FROM ftacs.product_class WHERE manuf_id IN (" +
                "SELECT id FROM ftacs.manufacturer WHERE NAME='" + manufacturer + "'))";
        return getValueSet(query);
    }

    public static Set<String> getDeviceProfileNameSetByModelName(String modelName) {
        String query = "SELECT name FROM ftacs.profile WHERE group_id IN (" +
                "SELECT group_id FROM ftacs.product_class WHERE model='" + modelName + "')";
        return getValueSet(query);
    }

    public static Set<String> getDeviceProfileNameSetByStatus(String status) {
        String query = "SELECT name FROM ftacs.profile WHERE is_active='" + (status.equals("Active") ? "1" : "0") + "'";
        System.out.println(query);
        return getValueSet(query);
    }

    public static Set<String> getProfileSet() {
        String query = "SELECT id FROM ftacs.profile WHERE group_id IN (" +
                "SELECT group_id FROM ftacs.product_class WHERE id IN (" +
                "SELECT product_class_id FROM ftacs.cpe WHERE serial='" + BasePage.getSerial() + "'))";
        return getValueSet(query);
    }

    private static Set<String> getValueSet(String query) {
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
        return getValueSet("SELECT serial FROM ftacs.cpe WHERE product_class_id IN (" +
                "SELECT id FROM ftacs.product_class WHERE model IN (" +
                "SELECT model FROM ftacs.product_class WHERE id IN (" +
                "SELECT product_class_id FROM ftacs.cpe WHERE serial='" + serial + "')));").size();
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

    public static String getValue(String query) {
        try {
            stmtObj.execute(query);
            ResultSet resultSet = stmtObj.getResultSet();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDeviceId(String serial) {
        return getValue("SELECT id FROM ftacs.cpe WHERE serial='" + serial + "'");
    }

    public static void createFilterPreconditions(String serial) {
        Set<String> deviceSet = getValueSet("SELECT id FROM ftacs.cpe WHERE product_class_id IN (SELECT product_class_id FROM ftacs.cpe WHERE serial='" + serial + "')");
        List<String> devices = new ArrayList<>(deviceSet);
        int[] shift = {0, -1, -10};
        try {
            for (int i = 0; i < Math.min(devices.size(), shift.length); i++) {
                stmtObj.execute("UPDATE `ftacs`.`cpe` SET `created`='" + CalendarUtil.getDbShiftedDate(shift[i]) + "' WHERE `id`=" + devices.get(i) + ";");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(devices);
    }

    public static Map<String, Set<String>> getCustomDeviceInfoByColumn(String column, boolean exactMatchOnly) {
        Map<String, Set<String>> out = new HashMap<>();
        if (column.equalsIgnoreCase("ACS Username")) {
            List<String> list = new ArrayList<>(getValueSet("SELECT value FROM `ftacs`.`cpe_parameter` WHERE name_id IN (SELECT id FROM `ftacs`.`cpe_parameter_name` WHERE name LIKE '%Device.ManagementServer.Username')"));
            list.removeAll(Collections.singleton(null));
            list.sort(Comparator.reverseOrder());
            if (list.isEmpty() || list.get(0).isEmpty()) {
                return null;
            }
            String filter = list.get(0);
            String request = "SELECT serial FROM `ftacs`.`cpe` WHERE id IN (" +
                    "SELECT cpe_id FROM `ftacs`.`cpe_parameter` WHERE name_id IN (" +
                    "SELECT id FROM `ftacs`.`cpe_parameter_name` WHERE name LIKE '%Device.ManagementServer.Username') and value = '" + filter + "')";
            out.put(filter, getValueSet(request));
            return out;
        }
        if (column.endsWith("address")) {
            String ipOrMac = column.startsWith("IP") ? "'InternetGatewayDevice.WANDevice._.WANConnectionDevice._.WANPPPConnection._.ExternalIPAddress' OR name LIKE 'Device.IP.Interface._.IPv4Address._.IPAddress'" : "'%.MACAddress'";
            List<String> list = new ArrayList<>(getValueSet("SELECT value FROM `ftacs`.`cpe_parameter` WHERE name_id IN (SELECT id FROM `ftacs`.`cpe_parameter_name` WHERE name LIKE " + ipOrMac + ")"));
            list.removeAll(Collections.singleton(null));
            list.sort(Comparator.reverseOrder());
            if (list.isEmpty() || list.get(0).isEmpty()) {
                return null;
            }
            String suffix, filter;
            if (!exactMatchOnly && list.get(0).length() > 5) {
                filter = list.get(0).substring(0, 5);
                suffix = "LIKE '%" + filter + "%')";
            } else {
                filter = list.get(0);
                suffix = "= '" + filter + "')";
            }
            String request = "SELECT serial FROM `ftacs`.`cpe` WHERE id IN (" +
                    "SELECT cpe_id FROM `ftacs`.`cpe_parameter` WHERE name_id IN (" +
                    "SELECT id FROM `ftacs`.`cpe_parameter_name` WHERE name LIKE " + ipOrMac + ") AND value " + suffix;
            out.put(filter, getValueSet(request));
            return out;
        }
        if (column.equalsIgnoreCase("serial")) {
            List<String> list = new ArrayList<>(getValueSet("SELECT serial FROM `ftacs`.`cpe`;"));
            list.removeAll(Collections.singleton(null));
            list.sort(Comparator.reverseOrder());
            if (list.isEmpty() || list.get(0).isEmpty()) {
                return null;
            }
            String filter = list.get(0);
            if (exactMatchOnly) {
                out.put(filter, new HashSet<>(Collections.singletonList(filter)));
                return out;
            }
            filter = filter.substring(0, 1);
            out.put(filter, getValueSet("SELECT serial FROM `ftacs`.`cpe` WHERE serial LIKE '" + filter + "%';"));
            return out;
        }
        if (exactMatchOnly) {
            List<String> list = new ArrayList<>(getValueSet("SELECT " + column + " FROM `ftacs`.`cust_device1`"));
            list.removeAll(Collections.singleton(null));
            list.sort(Comparator.reverseOrder());
            if (list.isEmpty() || list.get(0).isEmpty()) {
                return null;
            }
            String filter = list.get(0);
            String request = "SELECT serial FROM `ftacs`.`cust_device1` WHERE " + column + " = '" + filter + "';";
            out.put(filter, getValueSet(request));
            return out;
        }

        String match = null;
        char[] chars = "0123456789abcdefghijklmnopqrstuvwxyz()*,-./".toCharArray();
        for (char c : chars) {
            String request = "SELECT serial FROM `ftacs`.`cust_device1` WHERE " + column + " LIKE '%" + c + "%';";
            Set<String> deviceSet = getValueSet(request);
            if (deviceSet.size() > 1) {
                out.put(String.valueOf(c), deviceSet);
                return out;
            }
            if (deviceSet.size() == 1) {
                match = String.valueOf(c);
            }
        }
        out.put(match, getValueSet("SELECT serial FROM `ftacs`.`cust_device1` WHERE " + column + " LIKE '%" + match + "%';"));
        return out;
    }

    public static String getMacAddress() {
        Set<String> set = getValueSet("SELECT value FROM `ftacs`.`cpe_parameter` WHERE name_id IN (SELECT id FROM `ftacs`.`cpe_parameter_name` WHERE name LIKE '%.MACAddress') AND cpe_id = '" + getDeviceId(BasePage.getSerial()) + "';");
        set.removeIf(String::isEmpty);
        if (set.isEmpty()) {
            throw new AssertionError("Device doesn't have MAC address!");
        }
        return set.iterator().next();
    }

    public static void main(String[] args) {
        connectDb();
        System.out.println("'"+getValue("SELECT id from ftacs.profile WHERE name = 'usp_dp_031';")+"'");
        disconnectDb();
    }

    public static String getGroupId(String serial) {
        return getValue("SELECT group_id FROM `ftacs`.`product_class` WHERE id IN (" +
                "SELECT product_class_id FROM `ftacs`.`cpe` WHERE SERIAL = '" + serial + "');");
    }
}
