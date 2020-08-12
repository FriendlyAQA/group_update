package com.friendly.aqa.entities;

import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.Timer;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Table {
    private final static Logger LOGGER = Logger.getLogger(Table.class);
    private final static Pattern cellTextPattern = Pattern.compile("<(div|span|a|xmp).*?>(.*?)</(\\1)>");
    private List<WebElement> rowsList;
    private String[][] textTable;
    private WebElement[][] elementTable;
    private final WebElement table;
    private boolean retryInit;

    public Table(WebElement table) {
//        long start = System.currentTimeMillis();
        this.table = table;
        rowsList = table.findElements(By.tagName("tr"));
        textTable = new String[rowsList.size()][];
        elementTable = new WebElement[rowsList.size()][];
        parseTable();
//        System.out.println("Parsing completed in " + (System.currentTimeMillis() - start) + " ms");
    }

    public Table(String id) {
        this(BasePage.findElement(id));
    }

    private void parseTable() {
        BasePage.setImplicitlyWait(0);
        rowsList.parallelStream()
                .forEach(webElement -> {
                    int i = rowsList.indexOf(webElement);
                    List<WebElement> tdList = webElement.findElements(By.tagName("td"));
                    if (tdList.size() == 0) {
                        tdList = webElement.findElements(By.tagName("th"));
                    }
                    elementTable[i] = tdList.toArray(new WebElement[0]);
                });
        Pattern row = Pattern.compile("<(tr).*?>(.*?)</(\\1)>");
        Pattern cell = Pattern.compile("<(t[dh]).*?>(.*?)</(\\1)>");
        String tableHtml = table.getAttribute("outerHTML")
                .replaceAll("\t", "")
                .replaceAll("\n", "")
                .replaceAll("&nbsp;", " ")
                .replaceAll("&amp;", "&")
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">");
        Matcher mRow = row.matcher(tableHtml);
        int i = 0;
        while (mRow.find()) {
            List<String> cellList = new ArrayList<>();
            Matcher mCell = cell.matcher(mRow.group(2));
            while (mCell.find()) {
                cellList.add(getCellContent(mCell.group(2)));
            }
            try {
                textTable[i] = cellList.toArray(new String[0]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(tableHtml);
                System.out.println("textTable.length:" + textTable.length);
                System.out.println("i:" + i);
                if (retryInit) {
                    throw new ArrayIndexOutOfBoundsException(e.getMessage());
                } else {
                    retryInit = true;
                    pause(1500);
                    rowsList = table.findElements(By.tagName("tr"));
                    textTable = new String[rowsList.size()][];
                    elementTable = new WebElement[rowsList.size()][];
                    parseTable();
                }
            }
            i++;
        }
        BasePage.setDefaultImplicitlyWait();
    }

    private static String getCellContent(String input) {
        String out = input;
//        Pattern cellTextPattern = Pattern.compile("<(div|span|a|xmp).*?>(.*?)</(\\1)>");
        Matcher m = cellTextPattern.matcher(input);
        while (m.find()) {
            out = getCellContent(m.group(2));
        }
        if (out.startsWith("<input")) {
            return "";
        } else {
            return out;
        }
    }

    public Table clickOn(int row, int column, int tagNum) { //TODO: rewrite negative tagNum
        if (column < 0) {
            column += textTable[row].length;
        }
        if (tagNum == 99) {
            elementTable[row][column].click();
        } else {
            List<WebElement> tagList = elementTable[row][column].findElements(By.xpath("child::img | child::span | child::input | child::select"));
            if (tagNum < 0) {
                tagNum += tagList.size();
            }
            tagList.get(tagNum).click();
        }
        return this;
    }

    public void clickOn(int row, int column) {
        clickOn(row, column, 99);
    }

    public Table clickOn(String text, int column) {
        clickOn(getRowNumberByText(column, text), column);
        return this;
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

    public boolean hasAsymmetry() {
        if (textTable.length <= 1) {
            return false;
        }
        int length = textTable[0].length;
        for (int i = 1; i < textTable.length; i++) {
            if (textTable[i].length != length) {
                return true;
            }
        }
        return false;
    }

    public List<WebElement> getExpandableRowList() {
        List<WebElement> imgList = table.findElements(By.tagName("img"));
        List<WebElement> out = new ArrayList<>();
        imgList
                .stream()
                .filter(el -> el.getAttribute("src").endsWith("expand.png"))
                .filter(WebElement::isDisplayed)
                .forEach(out::add);
        return out;
    }

    public List<Integer> getRowsWithText(String text) {
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < textTable.length; i++) {
            String[] row = textTable[i];
            for (String cell : row) {
                if (cell.toLowerCase().equals(text.toLowerCase())) {
                    out.add(i);
                }
            }
        }
        return out;
    }

    public List<Integer> getRowsWithInput(int column) {
        BasePage.setImplicitlyWait(0);
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < elementTable.length; i++) {
            if (elementTable[i][column].isDisplayed()) {
                List<WebElement> list = elementTable[i][column].findElements(By.tagName("input"));
                if (!list.isEmpty()) {
                    out.add(i);
                }
            }
        }
        BasePage.setDefaultImplicitlyWait();
        return out;
    }

    public Table clickOn(String text) {
        List<String> debugList = new ArrayList<>();
        for (int i = 0; i < textTable.length; i++) {
            for (int j = 0; j < textTable[i].length; j++) {
                if (textTable[i][j].toLowerCase().trim().equals(text.toLowerCase())) {
                    return clickOn(i, j, 99);
                } else {
                    debugList.add(textTable[i][j].toLowerCase());
                }
            }
        }
        String warning = "Text '" + text + "' not found in current table";
        LOGGER.warn(warning);
        print();
        System.out.println("text '" + text.toLowerCase() + "' not equals with:" + debugList.toString());
        throw new AssertionError(warning);
    }

    public int[] getTableSize() {
        int maxCellRow = 0;
        for (String[] row : textTable) {
            maxCellRow = Math.max(maxCellRow, row.length);
        }
        return new int[]{textTable.length, maxCellRow};
    }

    public int getRowLength(int row) {
        return textTable[row].length;
    }

    //    @SuppressWarnings("unused")
    public Table print() {
        int maxCells = getTableSize()[1];
        int[] size = new int[maxCells];
        for (String[] rows : textTable) {
            for (int j = 0; j < rows.length; j++) {
                int k = rows[j].length();
                size[j] = Math.max(size[j], k);
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

    @SuppressWarnings("unused")
    public Table pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String[] getColumn(int column) {
        return getColumn(column, false);
    }

    public String[] getColumn(int column, boolean normalize) { //Returns column without header (top row)
        String[] out = new String[textTable.length - 1];
        for (int i = 0; i < textTable.length - 1; i++) {
            out[i] = textTable[i + 1][column];
            if (normalize) {
                StringBuilder sb = new StringBuilder(out[i]);
                if (out[i].matches("\\d+/\\d/.+")) {
                    sb.insert(2, '0');
                }
                if (out[i].matches("^\\d/.+")) {
                    sb.insert(0, '0');
                }
                out[i] = sb.toString().replaceAll("^\\s$", "");
            }
        }
        return out;
    }

    public String[] getWholeColumn(int column) { //Returns column with header
        String[] out = new String[textTable.length];
        for (int i = 0; i < textTable.length; i++) {
            out[i] = textTable[i][column];
        }
        return out;
    }

    public String[] getColumn(String column) {
        return getColumn(getColumnNumber(0, column));
    }

    public String getCellText(String searchText, int resultColumn) {
        return textTable[getRowNumberByText(searchText)][resultColumn];
    }

    public String getCellText(String searchText, String columnHeader) {
        return textTable[getRowNumberByText(searchText)][getColumnNumber(0, columnHeader)];
    }

    public String getCellText(int row, int column) {
        if (column < 0) {
            column += textTable[row].length;
        }
        return textTable[row][column];
    }

    public void assertStartsWith(int row, int column, String expectedText) {
        if (column < 0) {
            column = textTable[row].length + column;
        }
        if (!textTable[row][column].startsWith(expectedText)) {
            throw new AssertionError("Text in cell (tab) #[" + row + "," + column + "] doesn't start with '" + expectedText + "'!");
        }
    }

    public void assertEndsWith(int row, int column, String expectedText) {
        if (column < 0) {
            column = textTable[row].length + column;
        }
        if (!textTable[row][column].endsWith(expectedText)) {
            throw new AssertionError("Text in cell (tab) #[" + row + "," + column + "] doesn't end with '" + expectedText
                    + "'! (Actual cell text is:'" + textTable[row][column] + "')");
        }
    }

    public WebElement getCellWebElement(int row, int column) {
        return elementTable[row][column];
    }

    public WebElement getInput(int row, int column) {
        return elementTable[row][column].findElement(By.tagName("input"));
    }

    public WebElement getSelect(int row, int column) {
        BasePage.setImplicitlyWait(0);
        List<WebElement> list = elementTable[row][column].findElements(By.tagName("select"));
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<Integer> getRowsWithSelectList(int column) {
        List<Integer> out = new ArrayList<>();
        for (int i = 1; i < elementTable.length; i++) {
            if (getSelect(i, column) != null) {
                out.add(i);
            }
        }
        return out;
    }

    public String getInputText(int row, int column) {
        WebElement input = getInput(row, column);
        if (input.getAttribute("type").equals("text")) {
            return getInput(row, column).getAttribute("value");
        } else {
            return "";
        }
    }

    public boolean isEmpty() {
        return elementTable.length == 0;
    }

    public int getRowNumberByText(int columnNum, String text) {
        int rowNum = -1;
        String[] column = getWholeColumn(columnNum);    // changed from getColumn()
        for (int i = 0; i < column.length; i++) {
            if (column[i].toLowerCase().trim().equals(text.toLowerCase())) {
                rowNum = i;
                break;
            }
        }
        if (rowNum < 0) {
            String warning = "Text '" + text + "' not found in column #" + columnNum + " of current table";
            print();
            LOGGER.warn(warning);
            throw new AssertionError(warning);
        }
        return rowNum;
    }

    public int getRowNumberByText(String text) {
        for (int i = 0; i < textTable.length; i++) {
            for (int j = 0; j < textTable[i].length; j++) {
                if (textTable[i][j].toLowerCase().equals(text.toLowerCase())) {
                    return i;
                }
            }
        }
        print();
        throw new AssertionError("Text '" + text + "' not found on page!");
    }

    public String getHint(int row) {
        return elementTable[row][0].findElement(By.tagName("span")).getAttribute("hintbody");
    }

//    @SuppressWarnings("unused")
//    public void printResults() {
//        Set<Map.Entry<String, String>> entrySet = parameterMap.entrySet();
//        for (Map.Entry<String, String> entry : entrySet) {
//            System.out.println(entry.getKey() + ":" + entry.getValue());
//        }
//    }

    public boolean contains(String value) {
        for (String[] rows : textTable) {
            for (String cell : rows) {
                if (cell.toLowerCase().equals(value.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void assertPresenceOfParameter(String value) {
        for (String[] row : textTable) {
            int length = row.length;
            if (row[length - 2].equals(value)) {
                return;
            }
        }
        String warning = "Specified table does not contain value '" + value + "'";
        print();
        throw new AssertionError(warning);
    }

    public void assertPresenceOfTask(String value) {
        for (String[] row : textTable) {
            int length = row.length;
            if (row[length - 3].equals(value)) {
                return;
            }
        }
        String warning = "Specified table does not contain value '" + value + "'";
        print();
        throw new AssertionError(warning);
    }

    @SuppressWarnings("unused")
    public void assertAbsenceOfValue(String value) {
        for (String[] row : textTable) {
            int length = row.length;
            if (row[length - 2].equals(value)) {
                String warning = "Specified table contains value '" + value + "', but must NOT!";
                throw new AssertionError(warning);
            }
        }
    }

    public Table assertPresenceOfValue(int column, String value) {
        for (String[] row : textTable) {
            int cellNum = column < 0 ? row.length + column : column;
            if (row[cellNum].toLowerCase().equals((value).toLowerCase())) {
                return this;
            }
        }
        throw new AssertionError("Specified column '" + column + "' does not contain value '" + value + "'");
    }

//    @SuppressWarnings("unused")
//    public Table assertAbsenceOfValue(int column, String value) {
//        if (getRowNumberByText(column, value) >= 0) {
//            throw new AssertionError("Specified column '" + column + "' contains value '" + value + "', but MUST NOT!");
//        }
//        return this;
//    }
//
//    public static void flushResults() {
//        parameterMap = null;
//    }

    public String[] getRow(int rowNum) {
        return textTable[rowNum];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table1 = (Table) o;
        return Arrays.deepEquals(textTable, table1.textTable);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rowsList, table, retryInit);
        result = 31 * result + Arrays.hashCode(textTable);
        result = 31 * result + Arrays.hashCode(elementTable);
        return result;
    }
}
