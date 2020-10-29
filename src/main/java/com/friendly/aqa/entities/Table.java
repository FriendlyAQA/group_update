package com.friendly.aqa.entities;

import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.Timer;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
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
    private final String tableId;
    Timer timer;


    public Table(WebElement table) {
        timer = new Timer();
        this.table = table;
        tableId = table.getAttribute("id");
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
        Matcher m = cellTextPattern.matcher(input);
        while (m.find()) {
            out = getCellContent(m.group(2));
            if (!out.replaceAll(" ", "").isEmpty() && !out.startsWith("<")) {   // experimental!!!
                break;                                                                           // remove if
            }                                                                                    // parsing fails!!!
        }
        if (out.startsWith("<input") || out.startsWith("<select")) {
            return "";
        } else {
            return out;
        }
    }

    public Table clickOn(int row, int column, int tagNum) {
        if (column < 0) {
            column += textTable[row].length;
        }
        if (tagNum == 99) {
            try {
                BasePage.scrollToElement(elementTable[row][column]).click();
            } catch (ElementNotInteractableException e) {
                throw new AssertionError("Element <" + elementTable[row][column].getTagName() + "> is present," +
                        " but not interactable (hidden?) on a page!");
            }
        } else {
            List<WebElement> tagList = elementTable[row][column].findElements(By.xpath("child::img | child::span | child::input | child::select"));
            if (tagNum < 0) {
                tagNum += tagList.size();
            }
            try {
                BasePage.scrollToElement(tagList.get(tagNum)).click();
            } catch (ElementNotInteractableException e) {
                throw new AssertionError("Element <" + tagList.get(tagNum) + "> is present," +
                        " but not interactable (hidden?) on a page!");
            }

        }
        return this;
    }

    public void clickOn(int row, int column) {
        clickOn(row, column, 99);
    }

    public Table clickOn(String firstRowWithText, int column) {
        clickOn(getFirstRowWithText(firstRowWithText), column);
        return this;
    }

    public void clickOn(String text) {
        clickOn(text, false);
//        System.out.println("Clicked1:" + timer.stop());
    }

    public void clickOn(String text, boolean retry) {
        for (int i = 0; i < textTable.length; i++) {
            for (int j = 0; j < textTable[i].length; j++) {
                if (textTable[i][j].trim().equalsIgnoreCase(text)) {
                    clickOn(i, j, 99);
                    return;
                }
            }
        }
        String warning = "Text '" + text + "' not found in current table";
        LOGGER.warn(warning);
        print();
        if (!retry) {
            System.out.println("try to find once more time...");
            pause(1000);
            new Table(tableId).clickOn(text, true);
        } else {
            throw new AssertionError(warning);
        }
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

    public boolean isAsymmetric() {
        if (elementTable.length <= 1) {
            return false;
        }
        int length = elementTable[0].length;
        for (int i = 1; i < elementTable.length; i++) {
            if (elementTable[i].length != length) {
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
            if (!rowsList.get(i).isDisplayed()) {
                continue;
            }
            String[] row = textTable[i];
            for (String cell : row) {
                if (cell.equalsIgnoreCase(text)) {
                    out.add(i);
                }
            }
        }
        return out;
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
                if (out[i].matches("^\\d/.+")) {
                    sb.insert(0, '0');
                }
                if (out[i].matches("^\\d{2}/\\d/.+")) {
                    sb.insert(3, '0');
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

    public String getCellText(String fromRowWithText, int targetColumn) {
        return textTable[getFirstRowWithText(fromRowWithText)][targetColumn];
    }

    public String getCellText(String fromRowWithText, String columnHeader) {
        return textTable[getFirstRowWithText(fromRowWithText)][getColumnNumber(0, columnHeader)];
    }

    public String getCellText(int row, int column) {
        if (column < 0) {
            column += textTable[row].length;
        }
        return textTable[row][column];
    }

    public String getCellText(int row, String columnHeader) {
        return getCellText(row, getColumnNumber(0, columnHeader));
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
        if (isEmpty()) {
            throw new AssertionError("Table is empty!");
        }
        try {
            BasePage.scrollToElement(rowsList.get(row));
            return elementTable[row][column].findElement(By.tagName("input"));
        } catch (ArrayIndexOutOfBoundsException e) {
            print();
            throw new AssertionError("Cell " + row + ":" + column + " not found!\n" + e.getMessage());
        }
    }

    public WebElement getInput(String fromRowWithText, int column) {
        return getInput(getFirstRowWithText(fromRowWithText), column);
    }

    public WebElement getSelect(int row, int column) {
        BasePage.setImplicitlyWait(0);
        List<WebElement> list = elementTable[row][column].findElements(By.tagName("select"));
        BasePage.setDefaultImplicitlyWait();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public WebElement getSelect(String fromRowWithText, int column) {
        return getSelect(getFirstRowWithText(fromRowWithText), column);
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
            return getInput(row, column).getAttribute("value").trim();
        } else {
            return "";
        }
    }

    public boolean isEmpty() {
        return elementTable.length == 0;
    }

    public int getFirstRowWithText(int columnNum, String text) {
        int rowNum = -1;
        String[] column = getWholeColumn(columnNum);    // changed from getColumn()
        for (int i = 0; i < column.length; i++) {
            if (column[i].trim().equalsIgnoreCase(text)) {
                rowNum = i;
                break;
            }
        }
        if (rowNum < 0) {
            print();
            for (WebElement[] row : elementTable) {
                BasePage.scrollToElement(row[columnNum]);
            }
            throw new AssertionError("Text '" + text + "' not found in column #" + columnNum + " of current table");
        }
        return rowNum;
    }

    public int getFirstRowWithText(String text) {
        for (int i = 0; i < textTable.length; i++) {
            for (int j = 0; j < textTable[i].length; j++) {
                if (textTable[i][j].equalsIgnoreCase(text) && elementTable[i][j].isDisplayed()) {
                    return i;
                }
            }
        }
        print();
        for (WebElement[] row : elementTable) {
            BasePage.scrollToElement(row[0]);
        }
        throw new AssertionError("Text '" + text + "' not found in table!");
    }

    public String getHint(int row) {
        int colNum = getColumnNumber(0, "Parameter name");
        WebElement span = elementTable[row][colNum].findElement(By.tagName("span"));
        String hint = span.getAttribute("hintbody");
        if (hint == null) {
            hint = span.getAttribute("tiptext");
        }
        return hint;
    }

    public String getHint(String parameter) {
        return getHint(getFirstRowWithText(parameter));
    }

    public boolean contains(String value) {
        for (String[] rows : textTable) {
            for (String cell : rows) {
                if (cell.equalsIgnoreCase(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int textCellMatches(String regex) {
        for (int i = 0; i < textTable.length; i++) {
            String[] rows = textTable[i];
            for (String cell : rows) {
                if (cell.toLowerCase().matches(regex)) {
                    return i;
                }
            }
        }
        return -1;
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
            if (row[cellNum].equalsIgnoreCase((value))) {
                return this;
            }
        }
        throw new AssertionError("Specified column '" + column + "' does not contain value '" + value + "'");
    }

    public String[] getRow(int rowNum) {
        return textTable[rowNum];
    }

    public int getVisibleRowsNumber() {
        return (int) rowsList.parallelStream().filter(WebElement::isDisplayed).count();
    }

    public List<String> getNotEmptyContentList() {
        List<String> out = new ArrayList<>();
        for (String[] rows : textTable) {
            for (String cell : rows) {
                if (!cell.isEmpty()) {
                    out.add(cell);
                }
            }
        }
        return out;
    }

    public List<Integer> getVisibleRowsWithInput(int column) {
        BasePage.setImplicitlyWait(0);
        List<Integer> out = new ArrayList<>();
        List<WebElement> cellList = new ArrayList<>();
        for (WebElement[] cells : elementTable) {
            cellList.add(cells[column]);
        }
        cellList.parallelStream()
                .filter(WebElement::isDisplayed)
                .filter(e -> e.findElements(By.tagName("input")).size() > 0)
                .filter(e -> e.findElement(By.tagName("input")).isEnabled())
                .forEach(e -> out.add(cellList.indexOf(e)));
        BasePage.setDefaultImplicitlyWait();
        out.sort(Comparator.naturalOrder());
        return out;
    }

//    public List<Integer> getVisibleRowsWithInput(int column) {
//        BasePage.setImplicitlyWait(0);
//        List<Integer> out = new ArrayList<>();
//        for (int i = 0; i < elementTable.length; i++) {
//            if (elementTable[i][column].isDisplayed()) {
//                List<WebElement> list = elementTable[i][column].findElements(By.tagName("input"));
//                if (!list.isEmpty()) {
//                    out.add(i);
//                }
//            }
//        }
//        BasePage.setDefaultImplicitlyWait();
//        return out;
//    }

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
