package com.friendly.aqa.utils;

import com.friendly.aqa.pageobject.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Table {
    private final static Logger LOGGER = Logger.getLogger(Table.class);
    private List<WebElement> rowsList;
    private String[][] textTable;
    private WebElement[][] elementTable;
    private String prefix;
    private WebElement table;
    private Properties props;

    public Table(WebElement table) {
        this.table = table;
        props = BasePage.getProps();
        rowsList = table.findElements(By.tagName("tr"));
        textTable = new String[rowsList.size()][];
        elementTable = new WebElement[rowsList.size()][];
        prefix = "";
        parseTable();
    }

    private void parseTable() {
        BasePage.setImplicitlyWait(0);
        rowsList.parallelStream()
                .forEach(webElement -> {
                    int i = rowsList.indexOf(webElement);
                    String tagName = i == 0 ? "th" : "td";
                    List<WebElement> tdList = webElement.findElements(By.tagName(tagName));
                    elementTable[i] = tdList.toArray(new WebElement[0]);
                });
        Pattern row = Pattern.compile("<(tr).*?>(.*?)</(\\1)>");
        Pattern cell = Pattern.compile("<(t[dh]).*?>(.*?)</(\\1)>");
        String tableHtml = table.getAttribute("outerHTML")
                .replace("\t", "")
                .replace("\n", "");
        Matcher mRow = row.matcher(tableHtml);
        int i = 0;
        while (mRow.find()) {
            List<String> cellList = new ArrayList<>();
            Matcher mCell = cell.matcher(mRow.group(2));
            while (mCell.find()) {
                cellList.add(getCellContent(mCell.group(2).replace("&nbsp;", " ")));
            }
            textTable[i] = cellList.toArray(new String[0]);
            i++;
        }
        BasePage.setDefaultImplicitlyWait();
    }

    private static String getCellContent(String input) {
        String out = input;
        Pattern p = Pattern.compile("<(div|span|a).*?>(.*?)</(\\1)>");
        Matcher m = p.matcher(input);
        while (m.find()) {
            out = getCellContent(m.group(2));
        }
        if (out.startsWith("<input")) {
            return "";
        } else {
            return out;
        }
    }

    public int[] getTableSize() {
        return new int[]{textTable.length, textTable[1].length};
    }

    public Table clickOn(int row, int column, int tagNum) {
        if (tagNum < 0) {
            elementTable[row][column].click();
        } else {
            List<WebElement> tagList = elementTable[row][column].findElements(By.xpath("child::img | child::span | child::input | child::select"));
            tagList.get(tagNum).click();
        }
        return this;
    }

    public Table clickOn(int row, int column) {
        return clickOn(row, column, -1);
    }

    public Table clickOn(String text, int column) {
        clickOn(getRowNumberByText(column, text), column);
        return this;
    }

    public Table clickOn(String text) {
        return clickOn(-1, text);
    }

    public int getColumnNumber(int row, String text) {
        String[] strings = textTable[row];
        for (int i = 0; i < strings.length; i++) {
            String cell = strings[i];
            if (cell.equals(text)) {
                return i;
            }
        }
        return -1;
    }

    public Table clickOn(int tagNum, String text) {
        for (int i = 0; i < textTable.length; i++) {
            for (int j = 0; j < textTable[i].length; j++) {
                if (textTable[i][j].toLowerCase().equals(text.toLowerCase())) {
                    return clickOn(i, j, tagNum);
                }
            }
        }
        return this;
    }

    public Table print() {
        int[] size = new int[textTable[1].length];
        for (String[] strings : textTable) {
            for (int j = 0; j < strings.length; j++) {
                int k = strings[j].length();
                if (k > size[j]) {
                    size[j] = k;
                }
            }
        }
        for (String[] strings : textTable) {
            System.out.print("| ");
            for (int j = 0; j < strings.length; j++) {
                System.out.printf("%-" + (size[j] + 1) + "s%s", strings[j], "| ");
            }
            System.out.println();
        }
        return this;
    }

    public Table pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String[] getColumn(int column) {
        String[] out = new String[textTable.length - 1];
        for (int i = 0; i < textTable.length - 1; i++) {
            out[i] = textTable[i + 1][column];
        }
        return out;
    }

    public String[] getColumn(String column) {
        return getColumn(getColumnNumber(0, column));
    }

    public Table readTasksFromDB(String groupName) {
        List<String[]> groupList;
        int count = Integer.parseInt(props.getProperty("pending_tasks_check_time"));
        for (int i = 0; i < count; i++) {
            long start = System.currentTimeMillis();
            groupList = DataBaseConnector.getTaskList(getGroupId(groupName));
            if (groupList.isEmpty()) {
                String warn = "There are no tasks created by '" + groupName + "' Group Update";
                LOGGER.warn(warn);
                throw new AssertionError(warn);
            }
            Set<String> StateSet = new HashSet<>();
            for (String[] line : groupList) {
                StateSet.add(line[2]);
            }
            if (!StateSet.contains("1")) {
                if (StateSet.size() != 1 || !StateSet.contains("2")) {
                    LOGGER.info("All tasks created. One or more tasks failed or rejected");
                }
                return this;
            }
//            System.out.println("contains pending");
            long timeout;
            if ((timeout = 1000 - System.currentTimeMillis() + start) > 0) {
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        LOGGER.info("All tasks created. One or more tasks remains in pending state");
        return this;
    }

    public String getExportLink(String groupName) {
        return props.getProperty("ui_url") + "/Update/Export.aspx?updateId=" + getGroupId(groupName);
    }

    private String getGroupId(String groupName) {
        WebElement cell = getCellWebElement(getRowNumberByText(4, groupName), 11);
        String attr = cell.getAttribute("onclick");
        return attr.substring(10, attr.indexOf("event)") - 2);
    }

    public String getCellText(int row, int column) {
        return textTable[row][column];
    }

    public String getCellText(int searchColumn, String searchText, int resultColumn) {
        return textTable[getRowNumberByText(searchColumn, searchText)][resultColumn];
    }

    public WebElement getCellWebElement(int row, int column) {
        return elementTable[row][column];
    }

    public Table setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    private int getRowNumberByText(int columnNum, String text) {
        int rowNum = -1;
        String[] column = getColumn(columnNum);
        for (int i = 0; i < column.length; i++) {
            if (column[i].toLowerCase().equals(text.toLowerCase())) {
                rowNum = i + 1;
                break;
            }
        }
        if (rowNum < 0) {
            String warning = "Text '" + text + "' not found in current table";
            LOGGER.warn(warning);
            throw new AssertionError(warning);
        }
        return rowNum;
    }

    public Table checkResults(String parameter, String value) {
        boolean match = false;
        for (String[] row : textTable) {
            try {
                int length = row.length;
                if (row[length - 2].equals(prefix + parameter) && row[length - 1].equals(value)) {
                    match = true;
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
        if (!match) {
            String warning = "Pair '" + parameter + "'='" + value + "' not found";
            LOGGER.warn(warning);
            throw new AssertionError(warning);
        }
        return this;
    }

    public Table setParameter(String paramName, Parameter option, String value) {
        int rowNum = getRowNumberByText(0, paramName);
        if (rowNum < 0) {
            throw new AssertionError("Parameter name '" + paramName + "' not found");
        }
        WebElement paramCell = getCellWebElement(rowNum, 1);
        if (props.getProperty("browser").equals("edge")) {
            ((JavascriptExecutor) BasePage.getDriver()).executeScript("arguments[0].scrollIntoView(true);", paramCell);
        }
        new Select(paramCell.findElement(By.tagName("select"))).selectByValue(option != Parameter.CUSTOM ? option.option : value);
        if (value != null && option == Parameter.VALUE) {
            WebElement input = paramCell.findElement(By.tagName("input"));
            input.clear();
            input.sendKeys(value);
        }
        if (BasePage.BROWSER.equals("firefox") || BasePage.BROWSER.equals("chrome") || BasePage.BROWSER.equals("ie")) {
//            clickOn(rowNum, 1, 0);
            clickOn(0,0);
        }
        return this;
    }

    public Table setCondition(String conditionName, Conditions condition, String value) {
        int rowNum = getRowNumberByText(0, conditionName);
        if (rowNum < 0) {
            throw new AssertionError("Condition name '" + conditionName + "' not found");
        }
        WebElement conditionCell = getCellWebElement(rowNum, 1);
        WebElement valueCell = getCellWebElement(rowNum, 2);
        if (BasePage.BROWSER.equals("edge")) {
            ((JavascriptExecutor) BasePage.getDriver()).executeScript("arguments[0].scrollIntoView(true);", conditionCell);
        }
        if (condition != null) {
            new Select(conditionCell.findElement(By.tagName("select"))).selectByValue(condition.value);
        }
        if (value != null && condition != Conditions.VALUE_CHANGE) {
            valueCell.findElement(By.tagName("input")).sendKeys(value);
        }
        clickOn(0, 0);
        return this;
    }

    public Table setPolicy(String policyName, Policy notification, Policy accessList) {
        int rowNum = getRowNumberByText(0, policyName);
        if (rowNum < 0) {
            throw new AssertionError("Policy name '" + policyName + "' not found");
        }
        WebElement notificationCell = getCellWebElement(rowNum, 1);
        WebElement accessListCell = getCellWebElement(rowNum, 2);
        if (BasePage.BROWSER.equals("edge")) {
            ((JavascriptExecutor) BasePage.getDriver()).executeScript("arguments[0].scrollIntoView(true);", notificationCell);
        }
        if (notification != null) {
            new Select(notificationCell.findElement(By.tagName("select"))).selectByValue(notification.option);
        }
        BasePage.waitForUpdate();
        if (accessList != null) {
            new Select(accessListCell.findElement(By.tagName("select"))).selectByValue(accessList.option);
        }
        BasePage.waitForUpdate();
//        clickOn(0, 0);
        return this;
    }

    public Table assertPresenceOfParameter(String value) {
        for (String[] row : textTable) {
            int length = row.length;
            if (row[length - 2].equals(value) || row[length - 2].equals(prefix + value)) {
                return this;
            }
        }
        String warning = "Specified table does not contain value '" + value + "'";
        throw new AssertionError(warning);
    }

    public Table assertAbsenceOfParameter(String value) {
        for (String[] row : textTable) {
            int length = row.length;
            if (row[length - 2].equals(value) || row[length - 2].equals(prefix + value)) {
                String warning = "Specified table contains value '" + value + "', but must NOT!";
                throw new AssertionError(warning);
            }
        }
        return this;
    }

    public Table assertPresenceOfValue(int column, String value) {
        if (getRowNumberByText(column, value) < 0) {
            throw new AssertionError("Specified column '" + column + "' does not contain value: " + value);
        }
        return this;
    }

    public Table assertAbsenceOfValue(int column, String value) {
        if (getRowNumberByText(column, value) >= 0) {
            throw new AssertionError("Specified column '" + column + "' contains value '" + value + "', but MUST NOT!");
        }
        return this;
    }

    public enum Parameter {
        EMPTY_VALUE("sendEmpty"),
        VALUE("sendValue"),
        FALSE("0"),
        TRUE("1"),
        DO_NOT_SEND("notSend"),
        NULL(""),
        CUSTOM(null);

        private String option;

        Parameter(String option) {
            this.option = option;
        }
    }

    public enum Policy {
        DEFAULT("-1"),
        OFF("0"),
        PASSIVE("1"),
        ACTIVE("2"),
        ACS_ONLY("1"),
        ALL("2");

        private String option;

        Policy(String option) {
            this.option = option;
        }
    }

    public enum Conditions {
        CONTAINS(1, "5"),
        EQUAL(2, "2"),
        GREATER(3, "1"),
        GREATER_EQUAL(4, "8"),
        LESS(5, "4"),
        LESS_EQUAL(6, "3"),
        NOT_EQUAL(7, "6"),
        STARTS_WITH(8, "7"),
        VALUE_CHANGE(9, "9");

        Conditions(int index, String value) {
            this.index = index;
            this.value = value;
        }

        int index;
        String value;

        public String getValue() {
            return value;
        }
    }
}
