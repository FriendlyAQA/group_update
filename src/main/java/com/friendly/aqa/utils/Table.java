package com.friendly.aqa.utils;

import com.friendly.aqa.pageobject.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Iterator;
import java.util.List;

public class Table {
    private final static Logger logger = Logger.getLogger(Table.class);
    private List<WebElement> rowsList;
    private String[][] textTable;
    private WebElement[][] elementTable;
    private String prefix;

    public Table(WebElement table) {
        rowsList = table.findElements(By.tagName("tr"));
        textTable = new String[rowsList.size()][];
        elementTable = new WebElement[rowsList.size()][];
        prefix = "";
        parseTable();
        System.out.println(table.getAttribute("outerHTML"));
    }

    private void parseTable() {
        BasePage.setImplicitlyWait(0);
        rowsList.parallelStream()
                .forEach(webElement -> {
                    int i = rowsList.indexOf(webElement);
                    String tagName = i == 0 ? "th" : "td";
                    List<WebElement> tdList = webElement.findElements(By.tagName(tagName));
                    textTable[i] = new String[tdList.size()];
                    elementTable[i] = new WebElement[tdList.size()];
                    Iterator<WebElement> tdIterator = tdList.iterator();
                    for (int j = 0; j < tdList.size(); j++) {
                        elementTable[i][j] = tdIterator.next();
                        String cell = parseCell(elementTable[i][j]);
                        if (cell.isEmpty()) {
                            ((JavascriptExecutor) BasePage.getDriver()).executeScript("arguments[0].scrollIntoView(true);", elementTable[i][j]);
                            cell = parseCell(elementTable[i][j]);
                        }
                        textTable[i][j] = cell;
                    }
                });
        BasePage.setDefaultImplicitlyWait();
    }

    private String parseCell(WebElement td) {
        String response = "";
        if (!td.getText().isEmpty()) {
            response = td.getText();
            return response;
        }
        List<WebElement> els = td.findElements(By.xpath("child::input | child::div | child::a | child::span"));
        if (els.size() == 0) {
            return response;
        }
        for (WebElement el : els) {
            parseCell(el);
        }
        return response;
    }

    public int[] getTableSize() {
        return new int[]{textTable.length, textTable[1].length};
    }

    public Table clickOn(int row, int column) {
        elementTable[row][column].click();
        return this;
    }

    public Table clickOn(String text, int column) {
        clickOn(getRowNumberByText(column, text), column);
        return this;
    }

    public Table clickOn(String text) {
        for (int i = 0; i < textTable.length; i++) {
            for (int j = 0; j < textTable[i].length; j++) {
                if (textTable[i][j].toLowerCase().equals(text.toLowerCase())) {
                    return clickOn(i, j);
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
                System.out.printf("%-" + size[j] + "s%s", strings[j], " | ");
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

    public String getExportLink(String groupName) {
        WebElement cell = getCellWebElement(getRowNumberByText(4, groupName), 11);
        String attr = cell.getAttribute("onclick");
        String id = attr.substring(10, attr.indexOf("event)") - 2);
        return BasePage.getProps().getProperty("ui_url") + "/Update/Export.aspx?updateId=" + id;
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
            logger.warn(warning);
            throw new AssertionError(warning);
        }
        return rowNum;
    }

    public Table checkResults(String parameter, String value) {
        boolean match = false;
        for (String[] row : textTable) {
            int length = row.length;
            if (row[length - 2].equals(prefix + parameter) && row[length - 1].equals(value)) {
                match = true;
                break;
            }
        }
        if (!match) {
            String warning = "Pair '" + parameter + "'='" + value + "' not found";
            logger.warn(warning);
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
        new Select(paramCell.findElement(By.tagName("select"))).selectByValue(option != Parameter.CUSTOM ? option.option : value);
        if (value != null && option == Parameter.VALUE) {
            WebElement input = paramCell.findElement(By.tagName("input"));
            input.clear();
            input.sendKeys(value);
        }
        clickOn(0, 0);
        return this;
    }

    public Table setPolicy(String policyName, Policy notification, Policy aList) {
        int rowNum = getRowNumberByText(0, policyName);
        if (rowNum < 0) {
            throw new AssertionError("Parameter name '" + policyName + "' not found");
        }
        WebElement notificationCell = getCellWebElement(rowNum, 1);
        WebElement aListCell = getCellWebElement(rowNum, 2);
        if (notification != null) {
            new Select(notificationCell.findElement(By.tagName("select"))).selectByValue(notification.option);
        }
        if (aList != null) {
            new Select(aListCell.findElement(By.tagName("select"))).selectByValue(aList.option);
        }
        clickOn(0, 0);
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
            throw new AssertionError("Specified column '" + column + "' contains value '" + value + "', but must NOT!");
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
}
