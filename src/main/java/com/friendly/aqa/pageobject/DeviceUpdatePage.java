package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.IGlobalButtons;
import com.friendly.aqa.entities.Table;
import com.friendly.aqa.entities.TopMenu;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.DataBaseConnector;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.text.ParseException;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;
import static com.friendly.aqa.entities.TopMenu.*;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.GlobalButtons.*;

public class DeviceUpdatePage extends BasePage {
    private static final Logger logger = Logger.getLogger(DeviceUpdatePage.class);
    private Date xmlFileTime;
    private Date csvFileTime;

    @FindBy(id = "btnSaveUsr_btn")
    private WebElement saveButton;

    @FindBy(id = "btnCancel_btn")
    private WebElement cancelButton;

    @FindBy(id = "btnDel_btn")
    private WebElement deleteButton;

    @FindBy(id = "btnRefresh_btn")
    private WebElement refreshButton;

    @FindBy(id = "btnClose_btn")
    private WebElement closeButton;

    @FindBy(id = "IsDefaultViewForUser")
    private WebElement defaultViewCheckbox;

    @FindBy(id = "btnReCheck_lnk")
    private WebElement recheckStatus;

    @FindBy(id = "btnReCheck_img")
    private WebElement recheckIcon;

    @FindBy(id = "txtSerial")
    private WebElement inputSerial;

    @FindBy(id = "ddlSearchOption")
    private WebElement searchByCombobox;

    @FindBy(id = "ddlColumns1")
    private WebElement sortByCombobox;

    @FindBy(id = "ddlSort1")
    private WebElement sortingOrderCombobox;

    @FindBy(id = "btnSearch_btn")
    private WebElement searchButton;

    @FindBy(id = "rdDelAll")
    private WebElement deleteAllRButton;


    @Override
    protected String getLeftMenuCssSelector() {
        return null;
    }

    @Override
    public Table getMainTable() {
        return getTable("tbl");
    }

    @Override
    public DeviceUpdatePage topMenu(TopMenu value) {
        return (DeviceUpdatePage) super.topMenu(value);
    }

    @Override
    public DeviceUpdatePage assertMainPageIsDisplayed() {
        assertPresenceOfElements("tbl");
        return (DeviceUpdatePage) super.assertMainPageIsDisplayed();
    }

    @Override
    public DeviceUpdatePage selectItem(String text) {
        return (DeviceUpdatePage) super.selectItem(text);
    }

    @Override
    public DeviceUpdatePage pause(int millis) {
        return (DeviceUpdatePage) super.pause(millis);
    }

    @Override
    public DeviceUpdatePage selectItem(Table table, String text, int startFromRow) {
        List<Integer> list = table.getRowsWithText(text);
        for (int i : list) {
            if (i >= startFromRow) {
                table.clickOn(i, 0);
                break;
            }
        }
        waitForUpdate();
        return this;
    }

    @Override
    public DeviceUpdatePage newViewButton() {
        return (DeviceUpdatePage) super.newViewButton();
    }

    @Override
    public DeviceUpdatePage assertPresenceOfOptions(String comboBoxId, String... options) {
        return (DeviceUpdatePage) super.assertPresenceOfOptions(comboBoxId, options);
    }

    @Override
    public DeviceUpdatePage fillName() {
        return (DeviceUpdatePage) super.fillName();
    }

    @Override
    public DeviceUpdatePage fillName(String name) {
        return (DeviceUpdatePage) super.fillName(name);
    }

    @Override
    public DeviceUpdatePage assertInputHasText(String id, String text) {
        return (DeviceUpdatePage) super.assertInputHasText(id, text);
    }

    @Override
    public DeviceUpdatePage setViewColumns(int startParam, int endParam) {
        return (DeviceUpdatePage) super.setViewColumns(startParam, endParam);
    }

    @Override
    public DeviceUpdatePage selectColumnFilter(String option) {
        return (DeviceUpdatePage) super.selectColumnFilter(option);
    }

    @Override
    public DeviceUpdatePage selectCompare(String option) {
        return (DeviceUpdatePage) super.selectCompare(option);
    }

    @Override
    public DeviceUpdatePage selectFilterModelName(String value) {
        return (DeviceUpdatePage) super.selectFilterModelName(value);
    }

    @Override
    public DeviceUpdatePage filterRecordsCheckbox() {
        return (DeviceUpdatePage) super.filterRecordsCheckbox();
    }

    @Override
    public DeviceUpdatePage selectView(String value) {
        return (DeviceUpdatePage) super.selectView(value);
    }

    @Override
    public DeviceUpdatePage editButton() {
        return (DeviceUpdatePage) super.editButton();
    }

    @Override
    public DeviceUpdatePage clickOnTable(String id, int row, int column, int tagNum) {
        return (DeviceUpdatePage) super.clickOnTable(id, row, column, tagNum);
    }

    @Override
    public DeviceUpdatePage addSubFilter() {
        return (DeviceUpdatePage) super.addSubFilter();
    }

    @Override
    public DeviceUpdatePage andRadioButton() {
        return (DeviceUpdatePage) super.andRadioButton();
    }

    @Override
    public DeviceUpdatePage deleteFilter() {
        return (DeviceUpdatePage) super.deleteFilter();
    }

    public DeviceUpdatePage defaultViewForCurrentUserCheckbox() {
        defaultViewCheckbox.click();
        return this;
    }

    @Override
    public DeviceUpdatePage resetView() {
        return (DeviceUpdatePage) super.resetView();
    }

    @Override
    public DeviceUpdatePage clickOnTable(String id, int row, int column) {
        return (DeviceUpdatePage) super.clickOnTable(id, row, column);
    }

    @Override
    public DeviceUpdatePage assertButtonsAreEnabled(boolean enabled, IGlobalButtons... buttons) {
        return (DeviceUpdatePage) super.assertButtonsAreEnabled(enabled, buttons);
    }

    @Override
    public DeviceUpdatePage downButton() {
        return (DeviceUpdatePage) super.downButton();
    }

    @Override
    public DeviceUpdatePage upButton() {
        return (DeviceUpdatePage) super.upButton();
    }

    @Override
    public DeviceUpdatePage topButton() {
        return (DeviceUpdatePage) super.topButton();
    }

    @Override
    public DeviceUpdatePage bottomButton() {
        return (DeviceUpdatePage) super.bottomButton();
    }

    public DeviceUpdatePage searchButton() {
        waitUntilElementIsEnabled("btnSearch_btn");
        searchButton.click();
        waitForUpdate();
        return this;
    }

    public DeviceUpdatePage lookFor(String value) {
        inputText("tbDeviceID", value);
        return this;
    }

    public DeviceUpdatePage inputSerial() {
        inputSerial.sendKeys(getSerial());
        waitUntilButtonIsEnabled(START);
        return this;
    }

    public DeviceUpdatePage refreshButton() {
        switchToFrame(POPUP);
        refreshButton.click();
        waitForUpdate();
        return this;
    }

    public void assertDuplicateNameErrorIsDisplayed() {
        setImplicitlyWait(0);
        List<WebElement> list = driver.findElements(By.id("lblNameInvalid"));
        setDefaultImplicitlyWait();
        if (list.size() == 1) {
            return;
        }
        String warn = "Error message 'This name is already in use' not found on current page!";
        logger.warn(warn);
        throw new AssertionError(warn);
    }

    public void checkFiltering(String filter) {
        WebElement comboBox = filter.equals("Manufacturer") ? filterManufacturerComboBox : filterModelNameComboBox;
        List<String> optionList = getOptionList(comboBox);
        optionList.remove("All");
        for (String option : optionList) {
            selectComboBox(comboBox, option);
            waitForUpdate();
            Set<String> itemSet = new HashSet<>(Arrays.asList(getMainTable().getColumn(filter)));
            if (itemSet.size() > 1 || (itemSet.size() == 1 && !itemSet.iterator().next().equals(option))) {
                throw new AssertionError("Column '" + filter + "' has unexpected content!\n"
                        + "Expected: " + option + ", but found: " + itemSet);
            }
        }
    }

    public DeviceUpdatePage searchBy(String value) {
        selectComboBox(searchByCombobox, value);
        searchByCombobox.click();
        return this;
    }

    public DeviceUpdatePage sortByColumn(String value) {
        selectComboBox(sortByCombobox, value);
        sortByCombobox.click();
        return this;
    }

    public DeviceUpdatePage sortingOrder(String value) {
        selectComboBox(sortingOrderCombobox, value);
        sortingOrderCombobox.click();
        return this;
    }

    public void assertSortingPerformedBy(String column, Boolean isAscending) {
        Table table = getMainTable();
        WebElement cell = table.getCellWebElement(0, table.getColumnNumber(0, column));
        setImplicitlyWait(0);
        List<WebElement> img = cell.findElements(By.tagName("img"));
        setDefaultImplicitlyWait();
        if (isAscending == null) {
            if (img.size() != 0) {
                throw new AssertionError("Unexpected sorting found by column '" + column + "' (Expected: unsorted).");
            }
        } else if (img.size() == 0) {
            throw new AssertionError("No sorting found by column '" + column + "' (Expected: " + (isAscending ? "ascending" : "descending") + ").");
        } else if (img.get(0).getAttribute("src").endsWith("down.png") == isAscending) {
            throw new AssertionError("Unexpected sorting type found by column '" + column + "' (Expected: " + (isAscending ? "ascending" : "descending") + ").");
        }
    }

    public void assertChangingView() {
        selectView("Default");
        String[] defColumns = getMainTable().getRow(0);
        List<String> optList = getOptionList(filterViewComboBox);
        for (String option : optList) {
            if (option.equals("Default")) {
                continue;
            }
            selectView(option);
            String[] newColumns = getMainTable().getRow(0);
            if (!Arrays.deepEquals(newColumns, defColumns)) {
//                System.out.println(option + ": OK\n" + Arrays.deepToString(defColumns) + "\n" + Arrays.deepToString(newColumns));
                return;
            }
//            System.out.println(option + ": Fail\n" + Arrays.deepToString(defColumns) + "\n" + Arrays.deepToString(newColumns));
        }
        throw new AssertionError("View has not been changed! Make sure that you have enough different view for this test case!");
    }

    public DeviceUpdatePage assertSelectedViewIs(String expectedView) {
        if (getSelectedValue(filterViewComboBox).toLowerCase().equals(expectedView.toLowerCase())) {
            return this;
        }
        throw new AssertionError("Actual and expected view don't match! Expected: " + expectedView
                + "; actual: " + getSelectedValue(filterViewComboBox));
    }

    public DeviceUpdatePage presetFilter(String parameter, String value) {
        topMenu(DEVICE_UPDATE);
        enterToDevice();
        Table table = new Table("tblUserInfo");
        if (!table.isEmpty()) {
            try {
                int rowNum = table.getRowNumberByText(parameter);
                if (rowNum >= 0 && table.getCellText(rowNum, 1).equals(value)) {
                    return this;
                }
            } catch (AssertionError e) {
                System.out.println("filter does not exist, go to create new one");
            }
        }
        clickOn("btnEditUserInfo_lnk");
        switchToFrame(POPUP);
        WebElement saveButton = driver.findElement(By.id("btnSaveUsr_btn"));
        setUserInfo(parameter, value);
        waitUntilElementIsEnabled("btnSaveUsr_btn");
        saveButton.click();
        okButtonPopUp();
        pause(500);
        return this;
    }

    public void createPreconditionsForSorting() {
        waitForUpdate();
        Table mainTable = getMainTable();
        String[] serials = mainTable.getColumn("Serial");
        for (String serial : serials) {
            mainTable.clickOn(serial);
            clickOn("btnEditUserInfo_lnk");
            switchToFrame(POPUP);
            Table userInfoTable = getTable("tblMain", 18, true);
            String[] items = userInfoTable.getWholeColumn(0);
            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                if (userInfoTable.getInputText(i, 1).isEmpty()) {
                    setUserInfo(userInfoTable, item, getRandomStringValue(10));
                }
            }
            try {
                waitUntilElementIsEnabled("btnSaveUsr_btn");
                saveButton.click();
                okButtonPopUp();
                pause(500);
                waitForUpdate();
            } catch (AssertionError e) {
                cancelButton.click(); // in case no one item has been changed
            }
            topMenu(DEVICE_UPDATE);
            mainTable = getMainTable();
        }
    }

    public DeviceUpdatePage enterToDevice() {
        try {
            getMainTable().clickOn(getSerial());
            pause(500);
        } catch (AssertionError e) {
            selectComboBox(itemsOnPageComboBox, "200");
            waitForUpdate();
            getTable("tbl").clickOn(getSerial());
        }
        waitForUpdate();
        return this;
    }

    public DeviceUpdatePage deselectCheckbox(String id) {
        WebElement checkbox = findElement(id);
        if (checkbox.isSelected()) {
            checkbox.click();
        }
        return this;
    }

    public DeviceUpdatePage selectCheckbox(String id) {
        WebElement checkbox = findElement(id);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        return this;
    }

    public void setUserInfo(String paramName, String value) {
        setUserInfo(new Table("tblMain"), paramName, value);
    }

    public void setUserInfo(Table table, String paramName, String value) {
        int rowNum = table.getRowNumberByText(0, paramName);
        if (rowNum < 0) {
            throw new AssertionError("Parameter name '" + paramName + "' not found");
        }
        WebElement paramCell = table.getCellWebElement(rowNum, 1);
        if (props.getProperty("browser").equals("edge")) {
            BasePage.scrollToElement(paramCell);
        }
        WebElement input = paramCell.findElement(By.tagName("input"));
        input.clear();
        pause(300);
        input.sendKeys(value);
    }

    public DeviceUpdatePage globalButtons(GlobalButtons button) {
        clickGlobalButtons(button);
        return this;
    }

    @Override
    public DeviceUpdatePage okButtonPopUp() {
        return (DeviceUpdatePage) super.okButtonPopUp();
    }

    @Override
    public DeviceUpdatePage cancelButtonPopUp() {
        return (DeviceUpdatePage) super.cancelButtonPopUp();
    }

    public DeviceUpdatePage leftMenu(Left item) {
        switchToFrame(ROOT);
        getTable("tblLeftMenu").clickOn(item.value);
        waitForUpdate();
        switchToFrame(DESKTOP);
        return this;
    }

    public DeviceUpdatePage selectAnyDevice() { //except target device
        Table table = getMainTable();
        String[] serials = table.getColumn("Serial");
        for (int i = 0; i < serials.length; i++) {
            String serial = serials[i];
            if (!getSerial().equals(serial)) {
                table.clickOn(i + 1, 0);
                parameterSet = new HashSet<>();
                parameterSet.add(serial);
                return this;
            }
        }
        throw new AssertionError("there are no suitable devices to be selected!");
    }

    public void assertAbsenceOfValue() {
        String value = parameterSet.iterator().next();
        waitForUpdate();
        if (elementIsPresent("btnPager2") && elementIsPresent("ddlPageSizes")) {
            selectComboBox(itemsOnPageComboBox, "200");
            waitForUpdate();
        }
        if (getMainTable().contains(value)) {
            throw new AssertionError("Value '" + value + "' still present in the table!");
        }
    }

    public DeviceUpdatePage assertTraceWindowIsOpened() {
        switchToNewWindow();
        assertEquals(findElement("lblTitle").getText(), "Trace of: Serial = " + getSerial()
                + " / ID = " + DataBaseConnector.getDeviceId(getSerial()));
        closeNewWindow();
        return this;
    }

    public DeviceUpdatePage clearDeviceActivity() {
        leftMenu(Left.DEVICE_ACTIVITY);
        waitForUpdate1();
        waitUntilButtonIsDisplayed(DELETE);
        deleteAllRButton.click();
        globalButtons(DELETE);
        okButtonPopUp();
        return this;
    }

    public DeviceUpdatePage saveFileName() {
        switchToFrame(ROOT);
        String message = findElement("spnAlert").getText();
        Pattern datePattern = Pattern.compile("Inventory_.+_(.+)\\)\\.");
        Pattern extPattern = Pattern.compile("\\)\\.([xmlcsv]{3})'");
        Matcher m = datePattern.matcher(message);
        Matcher extM = extPattern.matcher(message);
        if (m.find()) {
            try {
                Date date = CalendarUtil.getDate(m.group(1));
                if (extM.find()) {
                    if (extM.group(1).equals("csv")) {
                        csvFileTime = date;
                    } else if (extM.group(1).equals("xml")) {
                        xmlFileTime = date;
                    } else {
                        throw new AssertionError("File extension parsing error!");
                    }
                }
            } catch (ParseException e) {
                System.out.println("Date parsing error! \n" + message);
            }
        }
        return this;
    }

    public void checkSavedExport(String... extensions) {
        switchToFrame(POPUP);
        Table table = getTable("tbl");
        for (String ext : extensions) {
            if (!ext.equalsIgnoreCase("csv") && !ext.equalsIgnoreCase("xml")) {
                throw new AssertionError("Unsupported file type!");
            }
            if (ext.equalsIgnoreCase("csv")) {
                table.assertPresenceOfValue(1, "Report(Inventory_Default_" + CalendarUtil.getCsvFileFormat(csvFileTime) + ").csv");
            }
            if (ext.equalsIgnoreCase("xml")) {
                table.assertPresenceOfValue(1, "Report(Inventory_Default_" + CalendarUtil.getCsvFileFormat(xmlFileTime) + ").xml");
            }
        }
    }

    public DeviceUpdatePage deleteExportEntry() {
        switchToFrame(POPUP);
        Table table = getTable("tbl");
        table.clickOn(1, 0);
        parameterSet = new HashSet<>(1);
        parameterSet.add(table.getCellText(1, 1));
        deleteButton.click();
        return this;
    }

    public DeviceUpdatePage deleteAllExportEntries() {
        switchToFrame(POPUP);
        Table table = getTable("tbl");
        table.clickOn(0, 0);
        deleteButton.click();
        return this;
    }

    public DeviceUpdatePage assertAbsenceOfDeletedExportItem() {
        switchToFrame(POPUP);
        assertAbsenceOfValue();
        return this;
    }

    public void assertExportEntryListIsEmpty() {
        switchToFrame(POPUP);
        if (!findElement("pager2_lblPagerTotal").getText().equals("No data found")) {
            throw new AssertionError("List of exports is not empty!");
        }
        closeButton.click();
    }

    public void closeMapWindow() {
        switchToFrame(ROOT);
        pause(2000);
        getTable("tblPopupTitle").clickOn(0, 1);
        switchToFrame(DESKTOP);
        enterToDevice();
        waitForUpdate();
    }

    public void validateSearchBy(String option, boolean exactMatch) {
        searchBy(option);
        String[][] opt = {{"Phone number", "telephone"}, {"User ID", "userid"}, {"Full name", "name"}, {"Username", "login_name"}, {"User Tag", "user_tag"},
                {"Serial Number", "Serial"}, {"IP address", "IP address"}, {"MAC address", "MAC address"}, {"ACS Username", "ACS Username"}};
        if (exactMatch) {
            selectCheckbox("rdSearchExactly");
        } else {
            deselectCheckbox("rdSearchExactly");
        }
        for (String[] strings : opt) {
            if (option.equalsIgnoreCase(strings[0])) {
                if (elementIsPresent("pager2_tblPager")) {
                    selectComboBox(itemsOnPageComboBox, "200");
                    waitForUpdate();
                }
                Map<String, Set<String>> dbDeviceMap = DataBaseConnector.getCustomDeviceInfoByColumn(strings[1], exactMatch);
                if (dbDeviceMap != null && !dbDeviceMap.isEmpty()) {
                    String key = dbDeviceMap.keySet().iterator().next();
                    System.out.println("key:" + key);
                    lookFor(key);
                    clickOn("tbDeviceID");
                    searchButton();
                    Set<String> set = dbDeviceMap.get(key);
                    int dbResponseSize = set.size();
                    String error = "Search by '" + option + "' failed. Expected number of items: " + dbResponseSize + ", but actual: ";
                    if (dbResponseSize == 1) {
                        if (elementIsPresent("pager2_lblPagerTotal")) {
                            throw new AssertionError(error + "0 or more than 1");
                        }
                        Table infoTable = getTable("tblDeviceInfo");
                        String actual = infoTable.getCellText("Serial Number:", 1);
                        String expected = set.iterator().next();
                        if (!actual.equalsIgnoreCase(expected)) {
                            throw new AssertionError("Wrong device found! Expected: " + expected + "; actual: " + actual);
                        }
                        leftMenu(Left.SEARCH);
                    } else {
                        if (elementIsAbsent("pager2_lblPagerTotal")) {
                            throw new AssertionError(error + "1");
                        }
                        if (!findElement("pager2_lblPagerTotal").getText().equals("Total:")) {
                            throw new AssertionError(error + "0");
                        }
                        Table table = getMainTable();
                        int tableSize = table.getTableSize()[0] - 1;
                        if (dbResponseSize != tableSize) {
                            throw new AssertionError(error + (tableSize));
                        }
                        set.removeAll(Arrays.asList(table.getColumn("Serial")));
                        if (set.size() != 0) {
                            throw new AssertionError("one or more devices were not found: " + set.toString());
                        }
                    }
                }
                lookFor("*wrong*");
                clickOn("tbDeviceID");
                searchButton();
                if (elementIsAbsent("pager2_lblPagerTotal") || !findElement("pager2_lblPagerTotal").getText().equalsIgnoreCase("No data found")) {
                    throw new AssertionError("Unexpected search result! Expected: empty list (No data found)");
                }
                return;
            }
        }
    }

    public void assertTransferToDeviceInfo() {
        if (elementIsPresent("pager2_lblPagerTotal")) {
            throw new AssertionError("Search failed! Expected: only one device '" + getSerial() + "' found!");
        }
        Table infoTable = getTable("tblDeviceInfo");
        String actual = infoTable.getCellText("Serial Number:", 1);
        if (!actual.equalsIgnoreCase(getSerial())) {
            throw new AssertionError("Wrong device found! Expected: " + getSerial() + "; actual: " + actual);
        }
    }

    public void recheckStatus() {
        waitForClickableOf(recheckStatus).click();
        waitForClickableOf(recheckStatus);
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(100))
                .until(ExpectedConditions.invisibilityOf(findElement("divCheckStatusProgress")));
        assertPresenceOfElements("btnReCheck_img");
    }

    public void assertLastActivityIs(String activity) {
        Table table = getTable("tblParameters");
        if (!table.getCellText(1, 2).equalsIgnoreCase(activity)) {
            throw new AssertionError("Activity '" + activity + "' not found in the top row of activity list!");
        }
    }

    public void assertActivityIsPresent(String activity) {
        Table table = getTable("tblParameters");
        if (!table.contains(activity)) {
            throw new AssertionError("Activity '" + activity + "' not found in the top row of activity list!");
        }
    }

    public enum Left {
        LIST("List"), DEVICE_INFO("Device Info"), DEVICE_SETTINGS("Device Settings"), ADVANCED_VIEW("Advanced View"),
        PROVISION_MANAGER("Provision Manager"), DEVICE_MONITORING("Device Monitoring"), FILE_DOWNLOAD("File Download"),
        FILE_UPLOAD("File Upload"), DEVICE_DIAGNOSTIC("Device Diagnostics"), CUSTOM_RPC("Custom RPC"),
        DEVICE_HISTORY("Device History"), DEVICE_ACTIVITY("Device Activity"), SEARCH("Search");

        private final String value;

        Left(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum GlobalButtons implements IGlobalButtons {

        ACTIVATE("btnActivate_btn"),
        ADVANCED_VIEW("btnAdvView_btn"),
        CANCEL("btnCancel_btn"),
        CREATE_TEMPLATE("btnCreateProfile_btn"),
        DEACTIVATE("btnDeactivate_btn"),
        DELETE("btnDelete_btn"),
        DELETE_GROUP("btnDeleteView_btn"),
        DUPLICATE("btnDuplicate_btn"),
        EDIT("btnEdit_btn"),
        EXPORTS("btnCompletedExports_btn"),
        EXPORT_TO_CSV("btnExport_btn"),
        EXPORT_TO_XML("btnExport2XML_btn"),
        FACTORY_RESET("btnReset_btn"),
        FINISH("btnFinish_btn"),
        GET_CURRENT("UcDeviceSettingsControls1_btnGetCurrent_btn"),
        NEXT("btnNext_btn"),
        PAUSE("btnPause_btn"),
        REFRESH("btnRefresh_btn"),
        REPROVISION("btnCPEReprovision_btn"),
        PREVIOUS("btnPrev_btn"),
        REBOOT("btnReboot_btn"),
        SAVE("btnSave_btn"),
        SAVE_AND_ACTIVATE("btnSaveActivate_btn"),
        SEARCH_EXPORT_TO_CSV("btnExportToCsv_btn"),
        SEARCH_EXPORT_TO_XML("btnExportToXml_btn"),
        SHOW_ON_MAP("btnMap_btn"),
        SHOW_TRACE("btnShowTrace_btn"),
        SIMPLE_VIEW("btnTabView_btn"),
        START("btnSendUpdate_btn"),
        STOP_TRACE("btnStopTrace_btn"),
        STOP("btnStop_btn"),
        STOP_WITH_RESET("btnStopWithReset_btn"),
        TRACE("btnTrace_btn");

        GlobalButtons(String id) {
            this.id = id;
        }

        private final String id;

        public String getId() {
            return id;
        }
    }

}
