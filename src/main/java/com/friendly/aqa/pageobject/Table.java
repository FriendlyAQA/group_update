package com.friendly.aqa.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Iterator;
import java.util.List;

public class Table {
    //    private WebElement table;
    private List<WebElement> rowsList;
    private String[][] textTable;
    private WebElement[][] elementTable;
    private long time;

    Table(WebElement table) {
        time = System.currentTimeMillis();
//        this.table = table;
        rowsList = table.findElements(By.tagName("tr"));
        textTable = new String[rowsList.size()][];
        elementTable = new WebElement[rowsList.size()][];
        parseTable();
    }

    private void parseTable() {
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
                        textTable[i][j] = parseCell(elementTable[i][j]);
                    }
                });
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

    public Table clickOn(int row, int column) {
        elementTable[row][column].click();
        return this;
    }

    public Table print() {
        for (int i = 0; i < textTable.length; i++) {
            for (int j = 0; j < textTable[i].length; j++) {
                System.out.print(textTable[i][j] + " | ");
            }
            System.out.println();
        }
        System.out.println(System.currentTimeMillis() - time);
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

    String[] getColumn(int column) {
        String[] out = new String[textTable.length - 1];
        for (int i = 0; i < textTable.length - 1; i++) {
            out[i] = textTable[i + 1][column];
        }
        return out;
    }

    public String getCellText(int row, int column) {
        return textTable[row][column];
    }

    public WebElement getCellWebElement(int row, int column) {
        return elementTable[row][column];
    }

    public Table setParameter(String paramName, Select option, String value) {
        int rowNum = -1;
        String[] column = getColumn(0);
        for (int i = 0; i < column.length; i++) {
            System.out.println(column[i] + ":" + i);
            if (column[i].equals(paramName)) {
                rowNum = i + 1;
                break;
            }
        }
        if (rowNum < 0) {
            throw new AssertionError("Parameter name '" + paramName + "' not found");
        }
        System.out.println(rowNum);
        WebElement paramCell = getCellWebElement(rowNum, 1);
        new org.openqa.selenium.support.ui.Select(paramCell.findElement(By.tagName("select"))).selectByValue(option.option);
        if (value != null) {
            paramCell.findElement(By.tagName("input")).sendKeys(value);
        }
        return this;
    }

    public enum Select {
        EMPTY_VALUE("sendEmpty"), VALUE("sendValue"), FALSE("0"), TRUE("1"), DO_NOT_SEND("notSend"), NULL("");
        private String option;

        private Select(String option) {
            this.option = option;
        }

        public String getOption() {
            return option;
        }
    }
}
