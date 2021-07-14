package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.*;
import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.*;
import com.friendly.aqa.utils.Timer;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.*;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

import static com.friendly.aqa.entities.TopMenu.DEVICE_UPDATE;
import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.BottomButtons.*;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.Left.*;

public class DeviceUpdatePage extends BasePage {
    private static final Logger LOGGER = Logger.getLogger(DeviceUpdatePage.class);

    @FindBy(id = "btnSaveUsr_btn")
    private WebElement saveButton;

    @FindBy(id = "btnDel_btn")
    private WebElement deleteButton;

    @FindBy(id = "btnRefresh_btn")
    private WebElement refreshButton;

    @FindBy(id = "btnClose_btn")
    private WebElement closeButton;

    @FindBy(id = "btnReCheck_lnk")
    private WebElement recheckStatus;

    @FindBy(id = "txtSerial")
    private WebElement inputSerial;

    @FindBy(id = "Description")
    private WebElement portDescription;

    @FindBy(id = "RemoteIp")
    private WebElement remoteHost;

    @FindBy(id = "InternalIp")
    private WebElement computerIp;

    @FindBy(id = "RemotePort")
    private WebElement extPort;

    @FindBy(id = "InternalPort")
    private WebElement intPort;

    @FindBy(id = "Protocol")
    private WebElement protocol;

    @FindBy(id = "ddlDates")
    private WebElement dateCombobox;

    @FindBy(id = "ddlSearchOption")
    private WebElement searchByCombobox;

    @FindBy(id = "ddlColumns1")
    private WebElement sortByCombobox;

    @FindBy(id = "ddlSort1")
    private WebElement sortingOrderCombobox;

    @FindBy(id = "ddlDiagType")
    private WebElement diagnosticCombobox;

    @FindBy(id = "rdDelAll")
    private WebElement deleteAllRButton;

    @FindBy(id = "btnClearAll_btn")
    private WebElement clearAllRButton;

    @FindBy(id = "rdTarget")
    private WebElement defaultUploadRButton;

    @FindBy(id = "rdUrl")
    private WebElement manualUrlRButton;

    @FindBy(id = "btnEditUserInfo_lnk")
    private WebElement editAccountInfoLink;

    @FindBy(id = "hlCollapseAll")
    private WebElement collapseLink;

    @FindBy(id = "hlExpandAll")
    private WebElement expandLink;

    @FindBy(id = "lblNoReprovisionFound")
    private WebElement noProvision;

    @FindBy(id = "tbl")
    private WebElement mainTable;

    public DeviceUpdatePage() {
    }

    @Override
    public DeviceUpdatePage topMenu(TopMenu value) {
        return (DeviceUpdatePage) super.topMenu(value);
    }

    public DeviceUpdatePage assertMainPageIsDisplayed() {
        super.assertMainPageIsDisplayed();
        assertElementsAreEnabled(mainTable);
        return this;
    }

    @Override
    public DeviceUpdatePage pause(long millis) {
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

    public DeviceUpdatePage selectPort() {
        Table table = getTable("tblParameters");
        int row = table.textCellMatches("^tr069_du.+");
        if (row < 0) {
            throw new AssertionError("There's no suitable port to select!");
        }
        table.clickOn(row, 0, 0);
        getParameterMap().put(table.getCellText(row, "Internal port"), table.getCellText(row, "Protocol"));
        return this;
    }

    public DeviceUpdatePage selectRPC() {
        Table table = getTable("tblParameters");
        table.clickOn(1, 0, 0);
        getParameterMap().put(table.getCellText(1, "Parameter name"), table.getCellText(1, "Request"));
        return this;
    }

    public DeviceUpdatePage selectProvision(String tab) {
        getTable("tabsMain_tblTabs").clickOn(tab);
        waitForUpdate();
        Table table = getTable("tblItems");
        table.clickOn(1, 0, 0);
        getParameterMap().put(tab, table.getCellText(1, "Updated"));
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
    public DeviceUpdatePage validateName() {
        return (DeviceUpdatePage) super.validateName();
    }

    @Override
    public DeviceUpdatePage setVisibleColumns(int startParam, int endParam) {
        return (DeviceUpdatePage) super.setVisibleColumns(startParam, endParam);
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

    @Override
    public DeviceUpdatePage resetView() {
        return (DeviceUpdatePage) super.resetView();
    }

    @Override
    public DeviceUpdatePage clickOnTable(String id, int row, int column) {
        return (DeviceUpdatePage) super.clickOnTable(id, row, column);
    }

    @Override
    public DeviceUpdatePage assertButtonsAreEnabled(boolean enabled, IBottomButtons... buttons) {
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

    @Override
    public DeviceUpdatePage selectDownloadFileType(String type) {
        setImplicitlyWait(0);
        List<WebElement> list = findElements("UcFirmware1_ddlFileType");
        if (!list.isEmpty() && !list.get(0).isDisplayed()) {
            bottomMenu(ADD);
        }
        setDefaultImplicitlyWait();
        super.selectDownloadFileType(type);
        return this;
    }

    @Override
    public DeviceUpdatePage selectUploadFileType(String type) {
        waitForUpdate();
        setImplicitlyWait(0);
        List<WebElement> list = findElements("ddlFileType");
//        if (!list.isEmpty() && !list.get(0).isDisplayed()) {    //tr069_du_196
        if (list.isEmpty() || !list.get(0).isDisplayed()) {   //!!11.02.2021
            bottomMenu(ADD);
        }
        setDefaultImplicitlyWait();
        return (DeviceUpdatePage) super.selectUploadFileType(type);
    }

    @Override
    public DeviceUpdatePage selectFromListRadioButton() {
        return (DeviceUpdatePage) super.selectFromListRadioButton();
    }

    public DeviceUpdatePage searchButton() {
        waitUntilElementIsEnabled("btnSearch_btn");
        searchButton.click();
        waitForUpdate();
        return this;
    }

    public DeviceUpdatePage selectDeliveryMethod(String method) {
        selectComboBox(deliveryMethodComboBox, method);
        return this;
    }

    public DeviceUpdatePage selectDeliveryProtocol(String protocol) {
        selectComboBox(deliveryProtocolCombobox, protocol);
        return this;
    }

    public DeviceUpdatePage lookFor(String value) {
        inputText("tbDeviceID", value);
        return this;
    }

    public DeviceUpdatePage inputSerial() {
        waitUntilButtonIsEnabled(CANCEL);    //!replaced top
        inputSerial.sendKeys(getSerial());
        return this;
    }

    public DeviceUpdatePage refreshButton() {
        switchToFrame(POPUP);
        refreshButton.click();
        waitForUpdate();
        return this;
    }

    public DeviceUpdatePage deleteFileEntry() {
        waitForUpdate();
        clickOnTable("tblFirmwares", 1, 0);
        waitForUpdate();
        bottomMenu(DELETE);
        okButtonPopUp();
        return this;
    }

    public void validateFiltering(String filter) {
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
            throw new AssertionError("Unexpected sorting order by column '" + column + "' (Expected: " + (isAscending ? "ascending" : "descending") + ").");
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
                return;
            }
        }
        throw new AssertionError("View has not been changed! Make sure that you have enough different view for this test case!");
    }

    @Override
    public DeviceUpdatePage assertSelectedViewIs(String expectedView) {
        return (DeviceUpdatePage) super.assertSelectedViewIs(expectedView);
    }

    public DeviceUpdatePage presetFilter(String parameter, String value) {
        topMenu(DEVICE_UPDATE);
        openDevice();
        for (int i = 0; i < 2; i++) {
            try {
                Table table = new Table("tblUserInfo");
                if (!table.isEmpty()) {
                    try {
                        int rowNum = table.getFirstRowWithText(0, parameter);
                        if (table.getCellText(rowNum, 1).equals(value)) {
                            return this;
                        }
                    } catch (AssertionError e) {
                        System.out.println("entry '" + parameter + "' does not exist, going to create a new one");
                        break;
                    }
                }
            } catch (StaleElementReferenceException e) {
                System.out.println(e.getClass().getSimpleName() + " handled");
            }
        }
        editAccountInfoLink.click();
        switchToFrame(POPUP);
        setUserInfo(parameter, value);
        pause(500);
        if (buttonIsActive("btnSaveUsr_btn")) {
            saveButton.click();
            okButtonPopUp();
        } else {
            cancelButton.click();
        }
        waitForUpdate();
        switchToFrame(DESKTOP);
        return this;
    }

    public void createPreconditionsForSorting() {
        waitForUpdate();
        Table mainTable = getMainTable();
        String[] serials = mainTable.getColumn("Serial");
        for (String serial : serials) {
            mainTable.clickOn(serial);
            setAllUserInfo();
            topMenu(DEVICE_UPDATE);
            mainTable = getMainTable();
        }
    }

    public DeviceUpdatePage setAllUserInfo() {
        editAccountInfoLink.click();
        switchToFrame(POPUP);
        Table userInfoTable = getSymmetricTable("tblMain");
        String[] items = userInfoTable.getWholeColumn(0);
        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            WebElement cell = userInfoTable.getCellWebElement(i, 1);
            if (userInfoTable.hasInput(i, 1) && userInfoTable.getInputText(i, 1).isEmpty()) {
                setUserInfo(userInfoTable, item, getRandomStringValue(10));
            }
        }
        pause(500);
        if (buttonIsActive("btnSaveUsr_btn")) {
            saveButton.click();
            okButtonPopUp();
        } else {
            cancelButton.click();
        }
        waitForUpdate();
        switchToFrame(DESKTOP);
        return this;
    }

    public DeviceUpdatePage editAccountInfoLink() {
        editAccountInfoLink.click();
        return this;
    }

    public DeviceUpdatePage clearUserInfo() {
        switchToFrame(POPUP);
        showGreenPointer(clearAllRButton).click();
        okButtonPopUp();
        return this;
    }

    public void assertAccountInfoIsClear() {
        Timer timer = new Timer();
        while (!timer.timeout()) {
            Table table = getTable("tblUserInfo");
            if (table.getTableSize()[0] == 0) {
                return;
            }
            if (table.getTableSize()[0] == 1 && table.getCellText(0, 0).equalsIgnoreCase("domain")) {
                return;
            }
        }
        throw new AssertionError("Account info is not empty!");
    }

    public DeviceUpdatePage enterToAnyDevice() {
        Table table = getMainTable();
        String[] serials = table.getColumn("Serial");
        for (String serial : serials) {
            if (!getSerial().equals(serial)) {
                parameterSet = new HashSet<>();
                parameterSet.add(serial);
                return openDevice(serial);
            }
        }
        throw new AssertionError("there are no suitable devices to be selected!");
    }

    public DeviceUpdatePage openDevice(String serial) {
        Table table = getMainTable();
        if (!table.contains(serial)) {
            itemsOnPage("200");
            table = getMainTable();
        }
        table.clickOn(serial);
        waitForUpdate();
        waitUntilButtonIsDisplayed(REPROVISION);
        return this;
    }

    public DeviceUpdatePage openDevice() {
        return openDevice(getSerial());
    }

    @Override
    public DeviceUpdatePage deselectCheckbox(String id) {
        WebElement checkbox = findElement(id);
        return (DeviceUpdatePage) setCheckboxState(false, checkbox);
    }

    @Override
    public DeviceUpdatePage selectCheckbox(String id) {
        WebElement checkbox = findElement(id);
        return (DeviceUpdatePage) setCheckboxState(true, checkbox);
    }

    public void setUserInfo(String paramName, String value) {
        setUserInfo(new Table("tblMain"), paramName, value);
    }

    public void setUserInfo(Table table, String paramName, String value) {
        int rowNum = table.getFirstRowWithText(0, paramName);
        if (rowNum < 0) {
            throw new AssertionError("Parameter name '" + paramName + "' not found");
        }
        WebElement paramCell = table.getCellWebElement(rowNum, 1);
        if (props.getProperty("browser").equals("edge")) {
            scrollToElement(paramCell);
        }
        WebElement input = paramCell.findElement(By.tagName("input"));
        input.clear();
        pause(300);
        input.sendKeys(value);
    }

    public DeviceUpdatePage bottomMenu(BottomButtons button) {
        if (button == EDIT_SETTINGS) {
            pause(1000); //!
        }
        clickBottomButton(button);
        return this;
    }

    public DeviceUpdatePage setParameter(String parameter, String value) {
        waitUntilButtonIsEnabled(GET_CURRENT_SETTINGS); //!
        Table table = getTable("tblParamsTable");
        int rowNum = table.getFirstRowWithText(parameter);
        String hint = table.getHint(parameter);
        WebElement paramCell = table.getCellWebElement(rowNum, 1);
        if (value != null) {
            waitForUpdate();
            WebElement input = paramCell.findElement(By.tagName("input"));
            input.clear();
            input.sendKeys(value);
        }
        getParameterMap().put(hint, value);
        if (!BROWSER.equals("edge")) {
            table.clickOn(0, 0);
        }
        return this;
    }

    @Override
    public DeviceUpdatePage okButtonPopUp() {
        pause(500);
        return (DeviceUpdatePage) super.okButtonPopUp();
    }

    @Override
    public DeviceUpdatePage selectTab(String tab) {
        waitUntilButtonIsDisplayed(GET_CURRENT_SETTINGS);
        return (DeviceUpdatePage) super.selectTab(tab);
    }

    @Override
    public DeviceUpdatePage cancelButtonPopUp() {
        return (DeviceUpdatePage) super.cancelButtonPopUp();
    }


    @Override
    public DeviceUpdatePage leftMenu(ILeft item) {
        return (DeviceUpdatePage) super.leftMenu(item);
    }

    public DeviceUpdatePage selectAnyDevice() { //except target device
        waitUntilBottomMenuIsDownloaded();//!
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
        String value = getStoredParameter();
        waitForUpdate();
        setSinglePage();
        if (getMainTable().contains(value)) {
            throw new AssertionError("Value '" + value + "' is still present in the table!");
        }
    }

    public void assertAbsenceOfSerial() {
        String serial = getStoredParameter();
        waitForUpdate();
        setSinglePage();
        if (getMainTable().contains(serial, "Serial")) {
            throw new AssertionError("Serial '" + serial + "' is still present in the table!");
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
        leftMenu(DEVICE_ACTIVITY);
        waitForUpdate();
        if (pager.getText().equals("No records found")) {
            return this;
        }
        waitUntilButtonIsDisplayed(REFRESH);
        deleteAllRButton.click();
        bottomMenu(DELETE);
        okButtonPopUp();
        return this;
    }

    public DeviceUpdatePage clearProvisionManager() {
        leftMenu(PROVISION_MANAGER);
        waitForUpdate();
        if (noProvision.getText().equals("There is no provision data to display")) {
            return this;
        }
        bottomMenu(EDIT);
        waitUntilButtonIsDisplayed(CANCEL);
        Table tabTable;
        while (!(tabTable = getTable("tabsMain_tblTabs")).isEmpty()) {
            tabTable.clickOn(tabTable.getNotEmptyContentList().get(0));
            waitForUpdate();
            Table table = getTable("tblItems");
            table.clickOn(0, 0, 0);
            bottomMenu(DELETE);
            okButtonPopUp();
            waitForUpdate();
            if (noProvision.getText().equals("There is no provision data to display")) {
                return this;
            }
        }
        throw new AssertionError("Unexpected Provision Manager behavior");
    }

    @Override
    public DeviceUpdatePage saveFileName() {
        return (DeviceUpdatePage) super.saveFileName();
    }

    public DeviceUpdatePage deleteExportEntry() {
        switchToFrame(POPUP);
        waitUntilElementIsDisplayed(deleteButton);
        Table table = getMainTable();
        table.clickOn(1, 0);
        parameterSet = new HashSet<>(1);
        parameterSet.add(table.getCellText(1, "Name"));
        showGreenPointer(deleteButton).click();
        return this;
    }

    private String getStoredParameter() {
        return parameterSet.iterator().next();
    }

    public DeviceUpdatePage deleteAllExportEntries() {
        switchToFrame(POPUP);
        Table table = getMainTable();
        parameterSet = new HashSet<>(1);
        parameterSet.add(getSelectedOption(dateCombobox));
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
        if (!pager.getText().equals("No data found") &&
                getOptionList(dateCombobox).contains(getStoredParameter())) {
            throw new AssertionError("List of exports is not empty!");
        }
        closeButton.click();
    }

    @Override
    public void closeMapWindow() {
        super.closeMapWindow();
        openDevice();
        waitForUpdate();
    }

    public void validateSearchBy(String option, boolean exactMatch) {
        searchBy(option);
        String[][] opt = {{"Phone number", "telephone"}, {"User ID", "userid"}, {"User name", "name"},
                {"User login", "login_name"}, {"User Tag", "user_tag"}, {"Serial Number", "Serial"},
                {"IP address", "IP address"}, {"MAC address", "MAC address"}, {"ACS Username", "ACS Username"}};
        if (exactMatch) {
            selectCheckbox("rdSearchExactly");
        } else {
            deselectCheckbox("rdSearchExactly");
        }
        for (String[] strings : opt) {
            if (option.equalsIgnoreCase(strings[0])) {
                itemsOnPage("200");
                Map<String, Set<String>> dbDeviceMap = DataBaseConnector.getCustomDeviceInfoByColumn(strings[1], exactMatch);
                if (dbDeviceMap != null && !dbDeviceMap.isEmpty()) {
                    String key = dbDeviceMap.keySet().iterator().next();
                    lookFor(key);
                    clickOn("tbDeviceID");
                    searchButton();
                    Set<String> set = dbDeviceMap.get(key);
                    setSinglePage();
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
                        if (!pager.getText().equals("Total:")) {
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
                if (elementIsAbsent("pager2_lblPagerTotal") || !pager.getText().equalsIgnoreCase("No data found")) {
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
        assertElementsArePresent("btnReCheck_img");
    }

    public void assertLastActivityIs(String activity) {
        Table table = getMainTable();
        if (!table.getCellText(1, 2).equalsIgnoreCase(activity)) {
            throw new AssertionError("Activity '" + activity + "' not found in the top row of activity list!");
        }
    }

    public void assertActivityIsPresent(String activity) {
        setSinglePage(); //!
        Table table = getMainTable();
        if (!table.contains(activity)) {
            throw new AssertionError("Activity '" + activity + "' is absent from activity list!");
        }
    }

    private void assertWindowIsOpened(String validText) {
        switchToFrame(POPUP);
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(IMPLICITLY_WAIT * 2))
                .pollingEvery(Duration.ofMillis(100))
                .until(ExpectedConditions.textToBePresentInElementValue(By.id("txtResponse"), validText));
        closeButton.click();
        switchToPreviousFrame();
    }

    public void assertPingWindowIsOpened() {
        assertWindowIsOpened("Ping statistics for");
    }

    public void assertTracerouteWindowIsOpened() {
        assertWindowIsOpened("Trace complete");
    }

    public void assertReplaceWindowIsOpened() {
        switchToFrame(POPUP);
        assertElementsArePresent(false, "lblReplaceCpeHeader");
        cancelButton.click();
        switchToPreviousFrame();
    }

    public DeviceUpdatePage createProfileOverSoapApi() {    //lwm2m and usp only
        String parameter = props.getProperty(getProtocolPrefix() + "_profile_parameter");
        String value = "" + (int) (100 * Math.random());
        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ftac=\"http://ftacs.com/\">" +
                "<soapenv:Header/><soapenv:Body><ftac:createProfile><profileList><Profile><fullTree>0</fullTree><name>" +
                getProtocolPrefix() + "_precondition_profile</name><productClassGroupId>" +
                DataBaseConnector.getGroupId(getSerial()) + "</productClassGroupId><profileParameterList>" +
                "<profileParameter><name>" + parameter + "</name><value>" + value + "</value></profileParameter>" +
                "</profileParameterList><isActive>1</isActive><version>1.0.0</version></Profile>" +
                "</profileList><user>AutoTest</user></ftac:createProfile></soapenv:Body></soapenv:Envelope>";
        String expectedResponse = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soap:Body><ns2:createProfileResponse xmlns:ns2=\"http://ftacs.com/\"/></soap:Body></soap:Envelope>";
        try {
            assertEquals(HttpConnector.sendSoapRequest(request), expectedResponse, "Unexpected response after profile set!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    private void deviceInfoFakeLink(String link) {
        Table table = getTable("tblDeviceInfo");
        int row = table.getFirstRowWithText(0, link);
        table.clickOn(row, 1, 0);
    }

    public DeviceUpdatePage listOfMethods() {
        deviceInfoFakeLink("List of methods:");
        return this;
    }

    public DeviceUpdatePage networkMap() {
        deviceInfoFakeLink("Network map:");
        return this;
    }

    public DeviceUpdatePage assertMapIsPresent() {
        switchToFrame(POPUP);
        assertElementsArePresent(false, "tblBig");
        switchToPreviousFrame();
        return this;
    }

    public DeviceUpdatePage assertMethodIsPresent(String methodName) {
        switchToFrame(POPUP);
        Table table = getTable("tblFunctions");
        table.assertPresenceOfValue(0, methodName);
        switchToPreviousFrame();
        return this;
    }

    private void setParameter(Table table, String paramName, String value) {
        int rowNum = table.getFirstRowWithText(paramName);
        int colNum = table.getColumnNumber(0, "Parameter value");
        String hint = table.getHint(paramName);
        getParameterMap().put(hint, value);
        WebElement input = null;
        WebElement select = null;
        setImplicitlyWait(0);
        List<WebElement> inputList = table.getCellWebElement(rowNum, colNum).findElements(By.tagName("input"));
        if (inputList.size() > 0) {
            input = inputList.get(0);
        } else {
            List<WebElement> selectList = table.getCellWebElement(rowNum, colNum).findElements(By.tagName("select"));
            if (selectList.size() > 0) {
                select = selectList.get(0);
            }
        }
        setDefaultImplicitlyWait();
        if (input != null && input.isDisplayed() && input.isEnabled()) {
            if (input.getAttribute("type").equals("checkbox")) {
                input.click();
            } else if (input.getAttribute("value").equals("*****")) {
                input.clear();
                input.sendKeys("" + (int) (Math.random() * 1000000));
                waitForUpdate();
                input.sendKeys(Keys.BACK_SPACE);
                waitForUpdate();
            } else if (!input.getAttribute("value").equals(value)) {
                input.clear();
                input.sendKeys(value + " ");
                waitForUpdate();
                input.sendKeys(Keys.BACK_SPACE);
                waitForUpdate();
            }
        } else if (select != null && select.isEnabled()) {
            selectComboBox(select, value);
        }
//        table.clickOn(rowNum - 1, colNum);    //usp_du_153 ↓
        table.clickOn(0, colNum);
        table.clickOn(rowNum, colNum);
    }

    public DeviceUpdatePage setParameter(String tab, int amount) {
        if (tab != null) {
            waitForUpdate();
            selectTab(tab);
        }
        Table table = getTable("tblParamsTable");
        Timer timer = new Timer(10000);
        while (table.isEmpty() && !timer.timeout()) {
            selectNextNode();
            table = getTable("tblParamsTable");
        }
        String[] names = table.getColumn("Parameter name");
        int valueColNum = table.getColumnNumber(0, "Parameter value");
        int localAmount = amount;
        boolean actionPerformed = false;
        for (int i = 0; i < Math.min(localAmount, names.length); i++) {
            if (table.getVisibleRowsNumber() == 0) {
                break;
            }
            String hint = table.getHint(i + 1);
            WebElement input = null;
            WebElement select = null;
            setImplicitlyWait(0);
            List<WebElement> inputList = table.getCellWebElement(i + 1, valueColNum).findElements(By.tagName("input"));
            if (inputList.size() > 0) {
                if (!inputList.get(0).isEnabled()) {
                    localAmount++;
                    continue;
                }
                input = inputList.get(0);
            } else {
                List<WebElement> selectList = table.getCellWebElement(i + 1, valueColNum).findElements(By.tagName("select"));
                if (selectList.size() > 0) {
                    if (!selectList.get(0).isEnabled()) {
                        localAmount++;
                        continue;
                    }
                    select = selectList.get(0);
                }
            }
            setDefaultImplicitlyWait();
            String value;
            if (input != null) {
                if (input.getAttribute("type").equals("text")) {
//                    if (names[i].equalsIgnoreCase("password") || names[i].equalsIgnoreCase("KeyPassphrase")) {
//                        value = "*****";
//                    } else {
                    String currentValue = input.getAttribute("value");
                    if (currentValue.equals("false")) {
                        value = "true";
                    } else if (currentValue.equals("true")) {
                        value = "false";
                    } else {
                        value = generateValue(hint, currentValue);
                    }
//                    }
                } else {
                    value = input.isSelected() ? "0" : "1";
                }
            } else if (select != null) {
                String selected = getSelectedOption(select);
                value = selected;
                for (String opt : getOptionList(select)) {
                    if (!opt.equals(selected)) {
                        value = opt;
                        break;
                    }
                }
            } else if (++localAmount < names.length) {
                continue;
            } else break;
            setParameter(table, names[i], value);
//            table.clickOn(i, -1);
            actionPerformed = true;
            waitForUpdate();
        }
        if (!actionPerformed) {
            selectNextNode();
            setParameter(null, amount);
        }
        return this;
    }

    private void selectNextNode() {
        Table nodeTable = getTable("tblTree");
        int pointer = 0;
        for (int i = 0; i < nodeTable.getTableSize()[0]; i++) {
            if (nodeTable.getCellWebElement(i, 0).getAttribute("outerHTML").contains("bold;")) {
                pointer = i;
                break;
            }
        }
        boolean success = false;
        for (int i = pointer + 1; i < nodeTable.getTableSize()[0]; i++) {
            if (nodeTable.getCellWebElement(i, 0).findElement(By.tagName("span")).getAttribute("class").endsWith("disable")) {
                continue;
            }
            nodeTable.clickOn(i, 0, 0);
            nodeTable.clickOn(i, 0, -1);
            success = true;
            waitForUpdate();
            break;
        }
        if (!success) {
            String warn = "There are no clickable objects!";
            LOGGER.warn(warn);
            throw new AssertionError(warn);
        }
        waitForUpdate();
    }

    public void validateAbsenceTaskWithValue() {
        if (elementIsAbsent(getMainTableId())) {
            return;
        }
        String[] col = getMainTable().getColumn("Value");
        String value = getParameterMap().values().iterator().next();
        if (Arrays.asList(col).contains(value)) {
            throw new AssertionError("Task with value '" + value + "' is present in the list!");
        }
    }

    public DeviceUpdatePage validateActivityTasks() {
        setSinglePage();
        Set<Map.Entry<String, String>> entrySet = getParameterMap().entrySet();
        Table table = getMainTable();
        out:
        for (Map.Entry<String, String> entry : entrySet) {
//            validateAddedTask("tbl", entry.getKey(), entry.getValue(), 6);
            String parameter = entry.getKey();
            if (!table.contains(parameter)) {
                throw new AssertionError("Parameter '" + entry.getKey() + "' not found!");
            }
            List<Integer> rows = table.getRowsWithText(parameter);
            for (int row : rows) {
                int col = table.getColumnNumber(row, parameter);
                if (table.getCellText(row, col + 1).equalsIgnoreCase(entry.getValue())) {
                    continue out;
                }
            }
            scrollTo(pager);
            table.print();
            throw new AssertionError("Pair '" + parameter + ":" + entry.getValue() + "' not found!");
        }
        return this;
    }

    public void validateProvisionTasks() {
        validateActivityTasks("tblItems", 4);
    }

    public void validateProvisionDownloadTasks() {
        validateProvisionTasks("Download file");
    }

    public void validateProvisionRpcTasks() {
        validateProvisionTasks("RPC");
    }

    private void validateProvisionTasks(String tab) {
        Table tabTable = getTable("tabsMain_tblTabs");
        if (!tabTable.getNotEmptyContentList().contains(tab)) {
            throw new AssertionError(tab + " tab not found!");
        }
        tabTable.clickOn(tab);
        Table table = getTable("tblItems");
        Set<Map.Entry<String, String>> entrySet = getParameterMap().entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            if (!table.contains(entry.getValue())) {
                table.print();
                throw new AssertionError("Item '" + entry.getValue() + "' not found on current table!");
            }
        }
    }

    public DeviceUpdatePage validateCustomRpcTasks() {
        return validateDownloadFileTasks();
    }

    public DeviceUpdatePage validateDownloadFileTasks() {
        return validateActivityTasks("tbl", 7);
    }

    private DeviceUpdatePage validateActivityTasks(String tableId, int shift) {
        Set<Map.Entry<String, String>> entrySet = getParameterMap().entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            validateAddedPairs(tableId, entry.getKey(), entry.getValue(), shift);
        }
        return this;
    }

    public void validateUploadFileTasks() {
        Set<Map.Entry<String, String>> entrySet = getParameterMap().entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            Table table = getMainTable();
            List<Integer> list = table.getRowsWithText(entry.getKey());
            for (int i : list) {
                if (table.getCellText(i, "Parameter name").matches(entry.getValue())) {
                    showGreenPointer(table.getCellWebElement(i, "Parameter name"));
                    return;
                }
                showRedPointer(table.getCellWebElement(i, "Parameter name"));
            }
            table.print();
            throw new AssertionError("Cannot find entry that matches the regex '" + entry.getValue() + "'");
        }
    }

    @Override
    public DeviceUpdatePage selectBranch(String branch) {
        return (DeviceUpdatePage) super.selectBranch(branch);
    }

    @Override
    public DeviceUpdatePage selectTreeObject(boolean clickOnCheckbox, int objNum) {
        return (DeviceUpdatePage) super.selectTreeObject(clickOnCheckbox, objNum);
    }

    public DeviceUpdatePage selectFileName() {
        return (DeviceUpdatePage) super.selectFileName("Download");
    }

    public DeviceUpdatePage defaultUploadRadioButton() {
        waitForUpdate();
        defaultUploadRButton.click();
        waitForUpdate();
        String url = "^" + props.getProperty("file_server") + "/?" + DataBaseConnector.getGroupId(getSerial())
                + '_' + getSerial() + '_' + CalendarUtil.getDateByPattern("yyyy.MM.dd") + '_' + ".+\\."
                + (getSelectedOption(selectUploadFileTypeComboBox).startsWith("Vendor Conf") ? "cfg$" : "log$");
        getParameterMap().put("Upload", url);
        return this;
    }

    @Override
    public DeviceUpdatePage fillDownloadUrl() {
        waitUntilButtonIsDisplayed(ADD_TO_PROVISION);
        return (DeviceUpdatePage) super.fillDownloadUrl();
    }

    @Override
    public DeviceUpdatePage fillUploadUrl() {
        super.fillUploadUrl();
        return this;
    }

    public DeviceUpdatePage manualUrlRButton() {
        waitForUpdate();
        manualUrlRButton.click();
        waitForUpdate();
        return this;
    }

    @Override
    public void validateObjectTree() {
        Timer timer = new Timer(10000);
        while (!timer.timeout()) {
            try {
                WebElement table = findElement("tblTree");
                List<WebElement> rowList = table.findElements(By.tagName("tr"));
                if (rowList.get(1).isDisplayed()) {
                    showGreenPointer(collapseLink).click();
                } else {
                    expandLink.click();
                    continue;
                }
                assertFalse(rowList.get(1).isDisplayed(), "Three has unexpected state; Expected: collapsed, actual: expanded");
                hidePointer(collapseLink);
                showGreenPointer(expandLink).click();
                assertTrue(rowList.get(1).isDisplayed(), "Three has unexpected state; Expected: expanded, actual: collapsed");
                WebElement node = table.findElement(By.tagName("img"));
                boolean state = node.getAttribute("src").endsWith("collapse.png");
                hidePointer(expandLink);
                showGreenPointer(node).click();
                assertFalse(state == node.getAttribute("src").endsWith("collapse.png"), "Node did not change state after clicking!");
                hidePointer(node);
            } catch (StaleElementReferenceException e) {
                continue;
            }
            return;
        }
        throw new AssertionError("Time out while checking Parameter three");
    }

    public DeviceUpdatePage changeAccessList() {
        Table table = getTable("tblParamsTable");
        int size = table.getTableSize()[0];
        for (int i = 1; i < size; i++) {
            WebElement select = table.getSelect(i, 2);
            if (select != null && select.isDisplayed() && getSelectedOption(select).equals("All")) {
                selectComboBox(select, "ACS only");
                return this;
            }
        }
        throw new AssertionError("There is no available option to select!");
    }

    public void validateCsvFile() throws IOException {
        if (!HttpConnector.sendGetRequest(props.getProperty("ui_url") + "/CPEs/ExportParams.aspx?fileType=csv").contains(getModelName())) {
            throw new AssertionError("Model Name not found in downloaded csv file!");
        }
    }

    public void validateHistoryFile() throws IOException {
        String header = "Device History from " + findElement("calTo_textBox").getAttribute("value");
        String request = HttpConnector.sendGetRequest(props.getProperty("ui_url") + "/CPEs/HistoryExport.aspx");
        if (!request.contains(header)) {
            System.out.println(request);
            throw new AssertionError("Text '" + header + "' not found in downloaded history file!");
        }
    }

    public DeviceUpdatePage storePath(String path) {
        waitForUpdate();
        getParameterMap().put("Get parameter attributes", path);
        getParameterMap().put("Get parameter values", path);
        getParameterMap().put("Get parameter names", path);
        return this;
        //InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.1.
    }

    public void assertTreeIsClear() {
        WebElement table = findElement("tblTree");
        List<WebElement> checkBoxes = table.findElements(By.tagName("input"));
        for (WebElement box : checkBoxes) {
            if (box.isSelected()) {
                throw new AssertionError("Tree has selected nodes!");
            }
        }
    }

    public void validateGeneratedGets() {
        validateActivityTasks("tbl", 7);
    }

    public DeviceUpdatePage storePath() {
        return storePath(getElementText("divPath") + '.');
    }

    public DeviceUpdatePage storePortMappingPath() {
        return storePath("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.1.");
    }

    public DeviceUpdatePage fillAddPortFields(String protocol) {
        String desc = BaseTestCase.getTestName();
        String intIp = "192.168.1.111";
        String extIp = "255.255.255.255";
        String intPort = String.valueOf((int) (Math.random() * 65536));
        String extPort = String.valueOf((int) (Math.random() * 65536));
        inputText2(remoteHost, extIp);
        getParameterMap().put("Remote host", extIp);
        inputText(this.extPort, extPort);
        getParameterMap().put("External port", extPort);
        inputText(this.intPort, intPort);
        getParameterMap().put("Internal port", intPort);
        inputText2(computerIp, intIp);
        getParameterMap().put("Internal client", intIp);
        selectComboBox(this.protocol, protocol);
        getParameterMap().put("Protocol", protocol);
        inputText2(portDescription, desc);
        getParameterMap().put("Description", desc);
        pause(1000);
        return this;
    }

    public void validatePortCreating() {
        Timer timer = new Timer(150000);
        setSinglePage();
        List<String[]> tasks = new ArrayList<>(2);
        String[] example = {"", getParameterMap().get("Description"), getParameterMap().get("Internal client"), getParameterMap().get("Internal port"),
                "", getParameterMap().get("Remote host"), getParameterMap().get("External port")};
        if (getParameterMap().get("Protocol").contains("TCP")) {
            example[4] = "TCP";
            tasks.add(example.clone());
        }
        if (getParameterMap().get("Protocol").contains("UDP")) {
            example[4] = "UDP";
            tasks.add(example);
        }
        while (!timer.timeout()) {
            int success = 0;
            Table table = getTable("tblParameters");
            List<Integer> rows = table.getRowsWithText(getParameterMap().get("Description"));
            for (String[] task : tasks) {
                for (int row : rows) {
                    if (Arrays.deepEquals(table.getRow(row), task)) {
                        success++;
                        break;
                    }
                }
            }
            if (success == tasks.size()) {
                return;
            }
            pause(1000);
        }
        throw new AssertionError("One or more port mapping item not found or incomplete!");
    }

    public void verifyPortDeletion() {
        verifyItemDeletion("Internal port", "Protocol");
    }

    public void verifyRpcDeletion() {
        if (getTable("tblParameters").getCellText(0, 0).equals("Method name")) {
            return;
        }
        verifyItemDeletion("Parameter name", "Request");
    }

    public void verifyItemDeletion(String keyColumn, String valueColumn) {
        String targetPort = getParameterMap().keySet().iterator().next();
        String targetProtocol = getParameterMap().get(targetPort);
        setSinglePage();
        Timer timer = new Timer(60000);
        while (!timer.timeout()) {
            boolean fail = false;
            Table table = getTable("tblParameters");
            String[] ports = table.getColumn(keyColumn);
            String[] protocols = table.getColumn(valueColumn);
            for (int i = 0; i < ports.length; i++) {
                String port = ports[i];
                String protocol = protocols[i];
                if (targetPort.equals(port) && targetProtocol.equals(protocol)) {
                    fail = true;
                    break;
                }
            }
            if (!fail) {
                return;
            }
            pause(1000);
        }
    }

    public DeviceUpdatePage createDiagnostic(String diagType) {
        waitUntilButtonIsDisplayed(ADD_TO_PROVISION);//!
        try {
            selectComboBox(diagnosticCombobox, diagType);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            throw new AssertionError("Current device doesn't support " + diagType + "!");
        }
        waitForUpdate();
        setImplicitlyWait(0);
        List<WebElement> list = findElements("txtHost");
        list.addAll(findElements("txtDnsServer"));
        if (list.size() > 0) {
            inputText(list.get(0), "8.8.8.8 ");
        }
        setDefaultImplicitlyWait();
        parameterSet = new HashSet<>(1);
        parameterSet.add(diagType);
        return this;
    }

    public void validateDiagnosticCreation() {
        Table table = getTable("tblParameters");
        String diagnostic = getStoredParameter();
        if (!table.contains(diagnostic)) {
            throw new AssertionError(diagnostic + " not found!");
        }
    }

    public DeviceUpdatePage deleteAllDiagnostics() {
        while (elementIsPresent("pager2_lblPagerTotal")) {
            setSinglePage();
            getTable("tblParameters").clickOn(0, 0);
            bottomMenu(DELETE);
            okButtonPopUp();
        }
        return this;
    }

    @Override
    public DeviceUpdatePage selectMethod(String methodName) {
        if (elementIsAbsent("ddlMethods")) {
            bottomMenu(ADD);
        }
        waitUntilButtonIsEnabled(CANCEL);
        super.selectMethod(methodName);
        waitForUpdate();
        getParameterMap().put("Custom RPC", getElementText("txtRequest").replaceAll("\n", "").replaceAll("\\s+<", "<"));
        return this;
    }

    public DeviceUpdatePage editParameterValue() {
        String oldValue = editParameter()[0];
        String newValue = oldValue.equals("0") ? "1" : "0";
        Table table = getTable("tblItems");
        int colNum = table.getColumnNumber(0, "Value");
        WebElement input = table.getInput(1, colNum);
        input.clear();
        input.sendKeys(newValue);
        getParameterMap().put("Value", newValue);
        bottomMenu(START);
        return this;
    }

    public DeviceUpdatePage editParameterPriority() {
        String oldValue = editParameter()[1];
        Table table = getTable("tblItems");
        int colNum = table.getColumnNumber(0, "Priority");
        WebElement select = table.getSelect(1, colNum);
        selectNewPriority(select, oldValue);
        bottomMenu(START);
        return this;
    }

    public String[] editParameter() {
        getTable("tabsMain_tblTabs").clickOn("Parameters");
        waitForUpdate();
        Table table = getTable("tblItems");
        String[] out = {table.getCellText(1, "Value"), table.getCellText(1, "Priority")};
        bottomMenu(EDIT);
        return out;
    }

    public void validateEditedProvision() {
        pause(500);
        Table table = getTable("tblItems");
        String columnHeader = getParameterMap().keySet().iterator().next();
        String actual = table.getCellText(1, columnHeader);
        String expected = getParameterMap().get(columnHeader);
        if (!actual.equals(expected)) {
            throw new AssertionError("Unexpected item record at column '" + columnHeader + "'. Expected: " + expected + "; actual: " + actual);
        }
    }

    public void validateProvisionDeletion() {
        waitForUpdate();
        String tab = getParameterMap().keySet().iterator().next();
        String value = getParameterMap().get(tab);
        if (elementIsAbsent("tabsMain_tblTabs")) {
            return;
        }
        Table tabTable = getTable("tabsMain_tblTabs");
        if (!tabTable.contains(tab)) {
            return;
        }
        tabTable.clickOn(tab);
        waitForUpdate();
        String[] column = getTable("tblItems").getColumn("Updated");
        if (Arrays.asList(column).contains(value)) {
            throw new AssertionError("Deleted provision is still present in the list!");
        }
    }

    public DeviceUpdatePage editProvisionRequest() {
        getTable("tabsMain_tblTabs").clickOn("RPC");
        waitForUpdate();
        Table table = getTable("tblItems");
        String originalText = table.getCellText(1, "Request");
        String modifiedText = originalText.replaceAll("GetParameterValues", "GetParameterAttributes").replaceAll("Model", "");
        bottomMenu(EDIT);
        table = getTable("tblItems");
        WebElement requestCell = table.getCellWebElement(1, table.getColumnNumber(0, "Request"));
        WebElement textArea = requestCell.findElement(By.tagName("textarea"));
        textArea.clear();
        textArea.sendKeys(modifiedText);
        getParameterMap().put("Request", modifiedText);
        return this;
    }

    public void validateEditedRequest() {
        String actual = getTable("tblItems").getCellText(1, "Request");
        String expected = getParameterMap().entrySet().iterator().next().getValue();
        if (!actual.equals(expected)) {
            throw new AssertionError("Request validation failed!\nExpected: " + expected + "\nactual  : " + actual);
        }
    }

    public DeviceUpdatePage editPriority(String tab) {
        selectMainTab(tab);
        Table table = getTable("tblItems");
        if (tab.equals("Objects")) {
            table.clickOn(1, 1);
            waitForUpdate();
            table = getTable("tblItems");
        }
        int colNum = table.getColumnNumber(0, "Priority");
        WebElement select = table.getSelect(1, colNum);
        String oldValue = getSelectedOption(select);
        selectNewPriority(select, oldValue);
        bottomMenu(START);
        return this;
    }

    private void selectNewPriority(WebElement select, String oldOption) {
        List<String> list = getOptionList(select);
        for (String option : list) {
            if (option.equals(oldOption)) {
                continue;
            }
            selectComboBox(select, option);
            getParameterMap().put("Priority", option);
            break;
        }
    }

    public DeviceUpdatePage editProvisionFileUrl() {
        selectMainTab("Download file");
        String oldUrl = getTable("tblItems").getCellText(1, "URL");
        String newUrl = oldUrl.replaceAll("//(\\d+\\.){3}\\d+", "//8.8.8.8");
        bottomMenu(EDIT);
        getTable("tblItems").clickOn(1, 1);
        waitForUpdate();
        manuallyDownloadRadioButton.click();
        waitForUpdate();
        inputText2(urlField, newUrl);
        bottomMenu(OK);
        getParameterMap().put("URL", newUrl);
        return this;
    }

    public DeviceUpdatePage editProvisionFilePriority() {
        selectMainTab("Download file");
        bottomMenu(EDIT);
        getTable("tblItems").clickOn(1, 1);
        waitForUpdate();
        WebElement select = findElement("UcFirmware1_ddlPriority");
        String oldPriority = getSelectedOption(select);
        selectNewPriority(select, oldPriority);
        bottomMenu(OK);
        return this;
    }

    @Override
    public DeviceUpdatePage clickIfPresent(IBottomButtons button) {
        return (DeviceUpdatePage) super.clickIfPresent(button);
    }

    @Override
    public DeviceUpdatePage deleteAllCustomViews() {
        return (DeviceUpdatePage) super.deleteAllCustomViews();
    }

    public enum Left implements ILeft {
        LIST("List"), DEVICE_INFO("Device Info"), DEVICE_SETTINGS("Device Settings"), ADVANCED_VIEW("Advanced View"),
        PROVISION_MANAGER("Provision Manager"), DEVICE_MONITORING("Device Monitoring"), FILE_DOWNLOAD("File Download"),
        FILE_UPLOAD("File Upload"), DEVICE_DIAGNOSTIC("Device Diagnostics"), CUSTOM_RPC("Custom RPC"),
        DEVICE_HISTORY("Device History"), DEVICE_ACTIVITY("Device Activity"), SEARCH("Search"), PORT_MAPPING("Port Mapping");

        private final String value;

        Left(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum BottomButtons implements IBottomButtons {

        ADD("btnAdd_btn"),
        ADD_PORT("btnAdd_btn"),
        ADD_TO_PROVISION("addToProvision"),
        CANCEL("btnCancel_btn"),
        CLEAR_TREE("UcDeviceSettingsControls1_btnClearTree_btn"),
        CREATE_TEMPLATE("btnCreateProfile_btn"),
        DELETE("btnDelete_btn"),
        DELETE_VIEW("btnDeleteView_btn"),
        EDIT("btnEdit_btn"),
        EDIT_SETTINGS("UcDeviceSettingsControls1_btnChange_btn"),
        EDIT_TREE("UcDeviceSettingsControls1_btnEditTree_btn"),
        EXPORTS("btnCompletedExports_btn"),
        EXPORT_TO_CSV("btnExport_btn"),
        EXPORT_TO_XML("btnExport2XML_btn"),
        FACTORY_RESET("btnReset_btn"),
        FINISH("btnFinish_btn"),
        GET_CURRENT_SETTINGS("UcDeviceSettingsControls1_btnGetCurrent_btn"),
        GET_CURRENT_PORTS("btnGetCurrent_btn"),
        NEXT("btnNext_btn"),
        OK("btnSave1_btn"),
        REFRESH("btnRefresh_btn"),
        REPLACE("btnReplaceCpe_btn"),
        REPROVISION("btnCPEReprovision_btn"),
        PING("btnPing_btn"),
        PREVIOUS("btnPrev_btn"),
        REBOOT("btnReboot_btn"),
        SAVE("btnSave_btn"),
        SEARCH_EXPORT_TO_CSV("btnExportToCsv_btn"),
        SEARCH_EXPORT_TO_XML("btnExportToXml_btn"),
        SEND_UPDATE("UcDeviceSettingsControls1_btnSendUpdate_btn"),
        SHOW_ON_MAP("btnMap_btn"),
        SHOW_TRACE("btnShowTrace_btn"),
        START("btnSendUpdate_btn"),
        START_TRACE("btnSetTrace_btn"),
        STOP_TRACE("btnStopTrace_btn"),
        STORE_TREE("UcDeviceSettingsControls1_btnSendTree_btn"),
        TRACE("btnTrace_btn"),
        TRACE_ROUTE("btnTracert_btn"),
        WAIT_UNTIL_CONNECT("rbWait");

        BottomButtons(String id) {
            this.id = id;
        }

        private final String id;

        public String getId() {
            return id;
        }
    }
}
