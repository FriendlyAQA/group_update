package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.*;
import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.File;
import java.time.Duration;
import java.util.*;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.NEW;

public class GroupUpdatePage extends BasePage {
    private static final Logger LOGGER = Logger.getLogger(GroupUpdatePage.class);
    private Map<String, String> optionMap;

    @FindBy(id = "btnAddTaskParameter-1_btn")
    private WebElement addConditionButton;

    @FindBy(id = "rdDefaultUpload")
    private WebElement defaultUploadRadioButton;

    @FindBy(name = "btnEditView$btn")
    private WebElement editGroupButton;

    @FindBy(name = "btnNewView$btn")
    private WebElement createGroupButton;

    @FindBy(id = "calDate_textBox")
    private WebElement calendarInput;

    @FindBy(id = "ddlRepeatType")
    private WebElement reactivationRepeatsDropDown;

    @FindBy(id = "ddlRepeatHour")
    private WebElement reactivationRepeatEveryHourDropDown;

    @FindBy(id = "ddlRepeatDay")
    private WebElement reactivationRepeatEveryDayDropDown;

    @FindBy(id = "ddlRepeatMonth")
    private WebElement reactivationRepeatEveryMonthDropDown;

    @FindBy(id = "rbReactivationEndsOccurrences")
    private WebElement reactivationEndsAfterRadiobutton;

    @FindBy(id = "rbReactivationEndsDay")
    private WebElement reactivationEndsOn;

    @FindBy(id = "chbxFailedCPEOnly")
    private WebElement reactivationReRunFailedCheckbox;

    @FindBy(id = "tmReactivation_ddlHour")
    private WebElement startsOnHours;

    @FindBy(id = "tmReactivation_ddlMinute")
    private WebElement startsOnMinutes;

    @FindBy(name = "btnShowDevices$btn")
    private WebElement showListButton;

    @FindBy(id = "spnConfirm")
    private WebElement confirmMessage;

    @FindBy(id = "txtFailedMax")
    private WebElement thresholdField;

    @FindBy(id = "lblWait")
    private WebElement waitUntilConnectRadioButton;

    @FindBy(id = "cbOnlineOnly")
    private WebElement onlineDevicesCheckBox;

    @FindBy(id = "lblReactivationSummaryText")
    private WebElement summary;

    @FindBy(id = "tmScheduled_ddlHour")
    private WebElement timeHoursSelect;

    @FindBy(id = "tmScheduled_ddlMinute")
    private WebElement timeMinutesSelect;

    @FindBy(how = How.ID, using = "lblDBError")
    private List<WebElement> noDataFound;

    @FindBy(how = How.ID, using = "tblTasks")
    private List<WebElement> taskTableList;

    @FindBy(id = "calDate_image")
    private WebElement calendarIcon;

    @FindBy(id = "calDate_calendar")
    private WebElement divCalendar;

    @FindBy(id = "btnDelete_btn")
    private WebElement deleteButton;

    @FindBy(id = "btnSave_btn")
    private WebElement saveButton;

    @FindBy(id = "btnAdvancedView_btn")
    private WebElement advancedViewButton;

    @FindBy(id = "btnShowDevices_btn")
    private WebElement showDevicesButton;

    @Override
    public GroupUpdatePage topMenu(TopMenu value) {
        return (GroupUpdatePage) super.topMenu(value);
    }

    @Override
    public GroupUpdatePage selectImportDevicesFile() {
        return (GroupUpdatePage) super.selectImportDevicesFile();
    }

    public GroupUpdatePage selectImportGuFile() {
        XmlWriter.createImportGroupFile();
        String inputText = new File("import/" + getProtocolPrefix() + "_import_group.xml").getAbsolutePath();
        importField.sendKeys(inputText);
        executeScript("onFileChanged(this);");
        return this;
    }

    public void assertResultTableIsAbsent() {
        if (!taskTableList.isEmpty()) {
            throw new AssertionError("Task table was found on page");
        }
    }

    public GroupUpdatePage assertEquals(String actual, String expected) {
        Assert.assertEquals(actual, expected);
        return this;
    }

    public GroupUpdatePage assertEquals(String actual, String expected, String message) {
        Assert.assertEquals(actual, expected, message);
        return this;
    }

    public GroupUpdatePage assertTrue(boolean condition) {
        Assert.assertTrue(condition);
        return this;
    }

    public GroupUpdatePage assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
        return this;
    }

    public GroupUpdatePage assertFalse(boolean condition) {
        Assert.assertFalse(condition);
        return this;
    }

    public GroupUpdatePage assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
        return this;
    }

    public GroupUpdatePage assertMainPageIsDisplayed() {
        assertElementsAreEnabled(profileStatusCombobox, manufacturerComboBox, modelComboBox, resetViewButton);
        return this;
    }

    public GroupUpdatePage presetFilter(String parameter, String value) {
        new DeviceUpdatePage().presetFilter(parameter, value);
        return this;
    }

    @Override
    public GroupUpdatePage filterRecordsCheckbox() {
        return (GroupUpdatePage) super.filterRecordsCheckbox();
    }

    @Override
    public String getMainTableId() {
        return "tblParameters";
    }

    @Override
    public GroupUpdatePage clickOnTable(String id, int row, int column) {
        return (GroupUpdatePage) super.clickOnTable(id, row, column);
    }

    @Override
    public GroupUpdatePage clickOnTable(String id, String text) {
        return (GroupUpdatePage) super.clickOnTable(id, text);
    }

    public GroupUpdatePage clickOnTask(String task) {
        switchToFrame(TASKS);
        return clickOnTable("tblTasks", task);
    }

    public Table getParamTable() {
        return getTable("tblParamsValue");
    }

    @Override
    public GroupUpdatePage clickOn(String id) {
        return (GroupUpdatePage) super.clickOn(id);
    }

    @Override
    public GroupUpdatePage clickButton(WebElement button) {
        return (GroupUpdatePage) super.clickButton(button);
    }

    @Override
    public GroupUpdatePage selectDate(String date) {
        return (GroupUpdatePage) super.selectDate(date);
    }

    @Override
    public GroupUpdatePage selectShiftedDate(String id, int days) {
        super.selectShiftedDate(id, days);
        String time = "Scheduled to " + calendarInput.getAttribute("value") + " " + getSelectedOption(timeHoursSelect) + ":" + getSelectedOption(timeMinutesSelect) + ":00";
        optionMap.put("Update state", time);
        if (!summary.getText().equals("Not in use")) {
            optionMap.put("Reactivation", summary.getText());
        }
        return this;
    }

    @Override
    public GroupUpdatePage pause(long millis) {
        return (GroupUpdatePage) super.pause(millis);
    }

    @Override
    public GroupUpdatePage selectItem() {
        return (GroupUpdatePage) super.selectItem();
    }

    @Override
    public GroupUpdatePage selectItem(String groupName) {
        return (GroupUpdatePage) super.selectItem(groupName);
    }

    @Override
    public GroupUpdatePage assertButtonIsEnabled(boolean expectedActive, String id) {
        return (GroupUpdatePage) super.assertButtonIsEnabled(expectedActive, id);
    }

    @Override
    public GroupUpdatePage assertPresenceOfValue(String tableId, int column, String value) {
        return (GroupUpdatePage) super.assertPresenceOfValue(tableId, column, value);
    }

    public GroupUpdatePage addModelButton() {
        waitForUpdate();
        return clickButton(addModelButton);
    }

    public GroupUpdatePage assertInputIsDisabled(String id) {
        WebElement el = findElement(id);
        if (el.isEnabled()) {
            showRedPointer(el);
            throw new AssertionError("Unexpected state of element #'" + id + "' (must be grayed out);");
        }
        showBluePointer(el);
        return this;
    }

    public void checkExportLink() {
        String lookFor;
        switch (getProtocolPrefix()) {
            case "lwm2m":
                lookFor = "\"Root.Device.0.UTC Offset\" value=\"+02:00\"";
                break;
            case "mqtt":
                lookFor = "<Param fullname=\"Device.FriendlySmartHome.PowerMeter.1.CurrentPower\" value=\"";
                break;
            case "usp":
                lookFor = "<Param fullname=\"Device.Location.1.";
                break;
            default:
                lookFor = "Device.ManagementServer.PeriodicInformInterval\" value=\"10\"";
        }
        try {
            System.out.println(HttpConnector.sendGetRequest(getExportLink(getProtocolPrefix() + "_gu_016")));
            assertTrue(HttpConnector.sendGetRequest(getExportLink(getProtocolPrefix() + "_gu_016")).contains(lookFor));
        } catch (Exception e) {
            throw new AssertionError("File export failed or file has unexpected content!");
        }
    }

    public void checkIsCalendarClickable() {
        boolean exception = false, repeat = false;
        setImplicitlyWait(0);
        calendarIcon.click();
        Table calendar = new Table(divCalendar.findElement(By.tagName("table")));
        for (int i = 2; i < calendar.getTableSize()[0]; i++) {
            for (int j = 0; j < calendar.getTableSize()[1]; j++) {
                WebElement cell = calendar.getCellWebElement(i, j);
                String attr = cell.getAttribute("onclick");
                if (attr != null) {
                    setDefaultImplicitlyWait();
                    if (!repeat) {
                        LOGGER.info("First day of month in Sunday. Test case 19 is not effective");
                    }
                    if (exception) {
                        LOGGER.info("An exception was caught while checking grayed-out dates");
                    }
                    try {
                        cell.click();
                    } catch (ElementNotInteractableException e) {
                        LOGGER.info("An exception was caught when click on current date");
                    }
                    return;
                }
                try {
                    cell.click();
                } catch (ElementNotInteractableException e) {
                    exception = true;
                }
                repeat = true;
            }
        }
        throw new AssertionError("Current date isn't found");
    }

    public GroupUpdatePage timeHoursSelect(String value) {
        new Select(timeHoursSelect).selectByValue(value);
        return this;
    }

    public void timeMinutesSelect(String value) {
        new Select(timeMinutesSelect).selectByValue(value);
    }

//    public GroupUpdatePage validateDownloadFileTasks() {
//        Set<Map.Entry<String, String>> entrySet = getParameterMap().entrySet();
//        for (Map.Entry<String, String> entry : entrySet) {
//            validateAddedTask("tblTasks", entry.getKey(), entry.getValue(), 0);
//        }
//        return this;
//    }

    public GroupUpdatePage validateDetails() {
        Table table = getTable("tblChilds");
        boolean manufacturer = table.getCellText(1, "Manufacturer").equals(getManufacturer());
        showPointer(manufacturer, table.getCellWebElement(1, "Manufacturer"));
        boolean modelName = table.getCellText(1, "Model name").equals(getModelName());
        showPointer(modelName, table.getCellWebElement(1, "Model name"));
        String amountStr = table.getCellText(1, "Amount");
        String sendTo = table.getCellText(1, "Send to");
        int amountInt = sendTo.equals("Individual") ? parameterSet.size() : DataBaseConnector.getDeviceAmount(getSerial());
        boolean amount = amountStr.equals(String.valueOf(amountInt)) || sendTo.startsWith("View:");//startsWith("Group:")
        showPointer(amount, table.getCellWebElement(1, "Amount"));
        if (manufacturer && modelName && amount) {
            return this;
        }
        throw new AssertionError("Detail Validation failed!");
    }

    @Override
    public GroupUpdatePage selectFileName(String fileType) {
        return (GroupUpdatePage) super.selectFileName(fileType);
    }

    @Override
    public GroupUpdatePage selectMethod(String value) {
        return (GroupUpdatePage) super.selectMethod(value);
    }

    public GroupUpdatePage onlineDevicesCheckBox() {
        onlineDevicesCheckBox.click();
        optionMap.put("Online devices", "True");
        return this;
    }

    public GroupUpdatePage setPeriod(int num) {
        driver.findElement(By.id("cbPeriod" + num)).click();
        int shift = num * 10 + num - 1;
        String[] timeStart = CalendarUtil.getDelay(shift);
        String[] timeFinish = CalendarUtil.getDelay(shift + 10);
        new Select(driver.findElement(By.id("tmPeriod" + num + "Start_ddlHour"))).selectByValue(timeStart[0].replaceAll("^0", ""));
        new Select(driver.findElement(By.id("tmPeriod" + num + "Start_ddlMinute"))).selectByValue(timeStart[1].replaceAll("^0", ""));
        new Select(driver.findElement(By.id("tmPeriod" + num + "Finish_ddlHour"))).selectByValue(timeFinish[0].replaceAll("^0", ""));
        new Select(driver.findElement(By.id("tmPeriod" + num + "Finish_ddlMinute"))).selectByValue(timeFinish[1].replaceAll("^0", ""));
        driver.findElement(By.id("txtPeriod" + num + "GroupSize")).sendKeys("3");
        driver.findElement(By.id("txtPeriod" + num + "Timeout")).sendKeys("5");
        optionMap.put("Period " + num, timeStart[0] + ":" + timeStart[1] + "-" + timeFinish[0] + ":" + timeFinish[1] + ", Amount 3, Per interval of 5 Minutes");
        return this;
    }

    public GroupUpdatePage waitUntilConnect() {
        waitUntilConnectRadioButton.click();
        return this;
    }

    public GroupUpdatePage addTaskButton() {
        waitForUpdate();
        addTaskButton.click();
        return this;
    }

    @Override
    public GroupUpdatePage addNewTask(String taskType) {
        switchToFrame(TASKS);
        return (GroupUpdatePage) super.addNewTask(taskType);
    }

    @Override
    public GroupUpdatePage manuallyDownloadRadioButton() {
        return (GroupUpdatePage) super.manuallyDownloadRadioButton();
    }

    @Override
    public GroupUpdatePage manuallyUploadRadioButton() {
        return (GroupUpdatePage) super.manuallyUploadRadioButton();
    }

    public GroupUpdatePage editGroupButton() {
        waitForUpdate();
        editGroupButton.click();
        return this;
    }

    public GroupUpdatePage defaultUploadRadioButton() {
        String fileType = getSelectedOption(selectUploadFileTypeComboBox);
        String path = props.getProperty("file_server");
        path = path.endsWith("/") ? path.substring(0, path.length() - 1) : path;
        getParameterMap().put(fileType, path);
        getLastTask().setParameterName(fileType);
        getLastTask().setValue(path);
        waitForUpdate();
        defaultUploadRadioButton.click();
        return this;
    }

    @Override
    public GroupUpdatePage selectFromListRadioButton() {
        return (GroupUpdatePage) super.selectFromListRadioButton();
    }

    public GroupUpdatePage fromListRadioButton() {
        waitForUpdate();
        fromListRadioButton.click();
        return this;
    }

    public GroupUpdatePage deleteButton() {
        deleteButton.click();
        waitForUpdate();
        return this;
    }

    @Override
    public GroupUpdatePage okButtonPopUp() {
        return (GroupUpdatePage) super.okButtonPopUp();
    }

    @Override
    public GroupUpdatePage selectCompare(String option) {
        return (GroupUpdatePage) super.selectCompare(option);
    }

    @Override
    public GroupUpdatePage scheduledTo() {
        return (GroupUpdatePage) super.scheduledTo();
    }

    @Override
    public GroupUpdatePage immediately() {
        return (GroupUpdatePage) super.immediately();
    }

    @Override
    public GroupUpdatePage itemsOnPage(String number) {
        return (GroupUpdatePage) super.itemsOnPage(number);
    }

    public GroupUpdatePage showList() {
        return clickButton(showListButton);
    }

    @Override
    public GroupUpdatePage selectSendTo() {
        return (GroupUpdatePage) super.selectSendTo();
    }

    @Override
    public GroupUpdatePage selectSendTo(String sendTo) {
        return (GroupUpdatePage) super.selectSendTo(sendTo);
    }

    public GroupUpdatePage fillName(String name) {
        nameTextField.sendKeys(name);
        return this;
    }

    public GroupUpdatePage fillName() {
        nameTextField.sendKeys(BaseTestCase.getTestName());
        return this;
    }

    public GroupUpdatePage fillGroupName() {
        return (GroupUpdatePage) super.fillName();
    }

    public GroupUpdatePage fillGroupName(String name) {
        return (GroupUpdatePage) super.fillName(name);
    }

    @Override
    public GroupUpdatePage fillDownloadUrl() {
        return (GroupUpdatePage) super.fillDownloadUrl();
    }

    public GroupUpdatePage fillDescriptionUploadFile(String desc) {
        nameTextField.sendKeys(desc);
        return this;
    }

    @Override
    public GroupUpdatePage fillUploadUrl() {
        return (GroupUpdatePage) super.fillUploadUrl();
    }

    @Override
    public GroupUpdatePage fillUsername() {
        return (GroupUpdatePage) super.fillUsername();
    }

    public GroupUpdatePage fillUserName(String userName) {
        userNameField.sendKeys(userName);
        return this;
    }

    public GroupUpdatePage fillPassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    @Override
    public GroupUpdatePage fillPassword() {
        return (GroupUpdatePage) super.fillPassword();
    }

    @Override
    public GroupUpdatePage inputDnsField(String text) {
        return (GroupUpdatePage) super.inputDnsField(text);
    }

    @Override
    public GroupUpdatePage inputHost(String text) {
        return (GroupUpdatePage) super.inputHost(text);
    }

    @Override
    public GroupUpdatePage inputNumOfRepetitions(String text) {
        return (GroupUpdatePage) super.inputNumOfRepetitions(text);
    }

    public GroupUpdatePage repeats(String value) {
        selectComboBox(reactivationRepeatsDropDown, value);
        optionMap.put("Reactivation", summary.getText());
        return this;
    }

    public GroupUpdatePage repeatEveryHours(String hours) {
        waitForUpdate();
        selectComboBox(reactivationRepeatEveryHourDropDown, hours);
        optionMap.put("Reactivation", summary.getText());
        return this;
    }

    public GroupUpdatePage repeatEveryDays(String days) {
        selectComboBox(reactivationRepeatEveryDayDropDown, days);
        optionMap.put("Reactivation", summary.getText());
        return this;
    }

    public GroupUpdatePage numberOfReactivations(String number) {
        inputText("txtReactivationEndsOccurrences", number);
        waitForUpdate();
        optionMap.put("Reactivation", summary.getText());
        return this;
    }

    public GroupUpdatePage repeatEveryMonth(String value) {
        selectComboBox(reactivationRepeatEveryMonthDropDown, value);
        optionMap.put("Reactivation", summary.getText());
        return this;
    }

    public GroupUpdatePage endsAfter() {
        reactivationEndsAfterRadiobutton.click();
        return this;
    }

    public GroupUpdatePage reactivationEndsOn() {
        reactivationEndsOn.click();
        waitForUpdate();
        optionMap.put("Reactivation", summary.getText());
        return this;
    }

    public GroupUpdatePage reRunOnFailed() {
        reactivationReRunFailedCheckbox.click();
        return this;
    }

    @Override
    public GroupUpdatePage selectDiagnostic(String value) {
        return (GroupUpdatePage) super.selectDiagnostic(value);
    }

    public GroupUpdatePage selectManufacturer() {
        return (GroupUpdatePage) selectManufacturer(getManufacturer());
    }

    @Override
    public GroupUpdatePage selectModel() {
        return (GroupUpdatePage) super.selectModel();
    }

    @Override
    public GroupUpdatePage selectColumnFilter(String option) {
        return (GroupUpdatePage) super.selectColumnFilter(option);
    }

    public void deleteFilterGroups() {
        deleteAll(sendToComboBox);
    }

    public GroupUpdatePage bottomMenu(BottomButtons button) {
        clickBottomButton(button);
        return this;
    }

    @Override
    public GroupUpdatePage waitForUpdate() {
        return (GroupUpdatePage) super.waitForUpdate();
    }

    public GroupUpdatePage leftMenu(Left item) {
        optionMap = new HashMap<>();
        optionMap.put("Update state", "Completed");
        optionMap.put("Online devices", "False");
        return (GroupUpdatePage) super.leftMenu(item);
    }

    public GroupUpdatePage createGroupButton() {
        waitForUpdate();
        createGroupButton.click();
        waitForUpdate();
        return this;
    }

    public GroupUpdatePage saveAndActivate() {
        return saveAndActivate(true);
    }

    public GroupUpdatePage saveAndActivate(boolean waitForCompleted) {
        bottomMenu(SAVE_AND_ACTIVATE);
        okButtonPopUp();
        if (waitForCompleted) {
            waitForStatus("Completed", 30);
        }
        readTasksFromDb();
        enterIntoGroup();
        validateDetails();
        return this;
    }

    public void checkFiltering(String by) {
        Set<String> dbNameSet;
        if (by.equalsIgnoreCase("Manufacturer")) {
            selectComboBox(manufacturerComboBox, getManufacturer());
            dbNameSet = DataBaseConnector.getGroupUpdateByManufacturer();
        } else {
            selectComboBox(modelComboBox, getModelName());
            dbNameSet = DataBaseConnector.getGroupUpdateByModel();
        }
        itemsOnPage("200");
        Table table = getMainTable();
        Set<String> webNameSet = new HashSet<>(Arrays.asList(table.getColumn("Name")));
        if (webNameSet.size() > dbNameSet.size()) {
            webNameSet.removeAll(dbNameSet);
            throw new AssertionError("Webpage contains unexpected Update Group items:" + webNameSet);
        }
        dbNameSet.removeAll(webNameSet);
        if (!dbNameSet.isEmpty()) {
            throw new AssertionError("Webpage does not contain expected Update Group items:" + dbNameSet);
        }
    }

    public GroupUpdatePage checkFilteringByState(String option) {
        switchToFrame(DESKTOP);
        List<WebElement> options = profileStatusCombobox.findElements(By.tagName("option"));
        for (WebElement opt : options) {
            if (opt.getText().equalsIgnoreCase(option)) {
                if (BROWSER.equals("edge")) {
                    scrollTo(profileStatusCombobox);
                }
                new Select(profileStatusCombobox).selectByValue(opt.getAttribute("value"));
                waitForUpdate();
                break;
            }
        }
        if (option.equals("Error") && noDataFound.size() == 1) {
            return this;
        }
        Table table;
        itemsOnPage("200");
        try {
            table = getMainTable();
        } catch (NoSuchElementException e) {
            LOGGER.info("List '" + option + "' is empty. Nothing to filter");
            return this;
        }
        String[] arr = table.getColumn("State");
        Set<String> set = new HashSet<>(Arrays.asList(arr));
        if (option.equals("All") && set.size() > 1) {
            itemsOnPage("10");
            return this;
        }
        if (set.size() != 1 && !set.contains(option)) {
            String warn = "Filtering failed on dropdown 'State'";
            LOGGER.warn('(' + BaseTestCase.getTestName() + ')' + warn);
            itemsOnPage("10");
            throw new AssertionError(warn);
        }
        itemsOnPage("10");
        return this;
    }

    @Override
    public GroupUpdatePage selectAction(String action) {
        return (GroupUpdatePage) super.selectAction(action);
    }

    @Override
    public GroupUpdatePage selectAction(String action, String instance) {
        return (GroupUpdatePage) super.selectAction(action, instance);
    }

    public GroupUpdatePage addCondition(String branch, String conditionName, Conditions condition, String value) {
        addConditionButton.click();
        switchToFrame(CONDITIONS);
        selectBranch(branch);
        Table paramTable = getTable("tblParamsValue");
        setCondition(paramTable, conditionName, condition, value);
        waitForUpdate();
        saveButton();
        return this;
    }

    public GroupUpdatePage addCondition(int rowNumber, String branch, String conditionName, Conditions condition, String value) {
        WebElement button = driver.findElement(By.id("btnAddTaskParameter-" + rowNumber + "_btn"));
        button.click();
        pause(1000);
        Table treeTable = getTable("tblTree", CONDITIONS);
        int row = treeTable.getFirstRowWithText(branch);
        treeTable.clickOn(row, 0, 0);
        Table paramTable = getTable("tblParamsValue", CONDITIONS);
        setCondition(paramTable, conditionName, condition, value);
        new FluentWait<>(driver).withMessage("Element was not found")
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(100))
                .until(ExpectedConditions.attributeToBe(saveButton, "class", "button_default"));
        saveButton();
        return this;
    }

    public GroupUpdatePage saveButton() {
        return saveButton(false);
    }

    public GroupUpdatePage saveButton(boolean switchToDesktopFrame) {
        saveButton.click();
        if (switchToDesktopFrame) {
            switchToFrame(DESKTOP); //11.06.21 tr069_gu_010
        }
        return this;
    }

    public GroupUpdatePage advancedViewButton() {
        advancedViewButton.click();
        return this;
    }

    public GroupUpdatePage showListButton() {
        showDevicesButton.click();
        return this;
    }

    public void validateDevicesAmount() {
        switchToFrame(POPUP);
        Table table = getTable("tblDevices");
        WebElement counter = findElement("pager2_lblCount");
        assertEquals(table.getTableSize()[0] - 1, parameterSet.size(), "Unexpected number of serials!");
        assertEquals(counter.getText(), String.valueOf(parameterSet.size()), "Unexpected number of devices!");
        for (String serial : parameterSet) {
            assertTrue(table.contains(serial), "Serial '" + serial + "' not found!");
        }
        pause(1000);
        closePopup();
    }

    public GroupUpdatePage validateDevicesAmountMessage() {
        switchToFrame(ROOT);
        assertEquals(confirmMessage.getText(), "Are you sure you want to activate?\nTotal devices targeted " + parameterSet.size() + ".", "");
        return this;
    }

    @Override
    public GroupUpdatePage selectButton() {
        return (GroupUpdatePage) super.selectButton();
    }

    public GroupUpdatePage selectDevice(int amount) {
        switchToFrame(POPUP);
        Table table = getTable("tblDevices");
        parameterSet = new HashSet<>(amount);
        for (int i = 0; i < amount; i++) {
            table.clickOn(i + 1, 0);
            parameterSet.add(table.getCellText(i + 1, 1));
        }
        return this;
    }

    @Override
    public GroupUpdatePage closePopup() {
        return (GroupUpdatePage) super.closePopup();
    }

    public void setCondition(Table table, String conditionName, Conditions condition, String value) {
        int rowNum = table.getFirstRowWithText(0, conditionName);
        if (rowNum < 0) {
            throw new AssertionError("Condition name '" + conditionName + "' not found");
        }
        WebElement conditionCell = table.getCellWebElement(rowNum, 1);
        WebElement valueCell = table.getCellWebElement(rowNum, 2);
        if (BROWSER.equals("edge")) {
            BasePage.scrollToElement(conditionCell);
        }
        if (condition != null) {
            new Select(conditionCell.findElement(By.tagName("select"))).selectByValue(condition.value);
        }
        if (value != null && condition != Conditions.VALUE_CHANGE) {
            valueCell.findElement(By.tagName("input")).sendKeys(value);
        }
        table.clickOn(0, 0);
    }

    public GroupUpdatePage checkResetView() {
        waitForUpdate();
        resetView();
        if (BROWSER.equals("chrome")) {
            pause(500);
        }
        Table table = getMainTable();
        boolean man = getSelectedOption(manufacturerComboBox).equals("All");
        boolean model = getSelectedOption(modelComboBox).equals("All");
        boolean status = getSelectedOption(profileStatusCombobox).equals("All");
        List<WebElement> pointerList = table.getCellWebElement(0, table.getColumnNumber(0, "Created")).findElements(By.tagName("img"));
        boolean descendingByCreated = pointerList.size() == 1 && pointerList.get(0).getAttribute("src").endsWith("down.png");
        if (!(man && model && status && descendingByCreated)) {
            String warn = "\"Reset View\" check failed";
            LOGGER.warn('(' + BaseTestCase.getTestName() + ')' + warn);
            throw new AssertionError(warn);
        }
        return this;
    }

    public void assertDevicesArePresent() {
        switchToFrame(POPUP);
        assertElementsArePresent(false, "tblDevices");
    }

    public GroupUpdatePage assertDeviceIsPresent() {
        switchToFrame(POPUP);
        assertTrue(getTable("tblDevices").contains(getSerial()), "Target device is absent from list!");
//        assertPresenceOfValue("tblDevices", 0, getSerial());
        closePopup();
        return this;
    }

    public GroupUpdatePage selectFileType(int index) {
        new Select(selectDownloadFileTypeComboBox).selectByIndex(index);
        return this;
    }

    @Override
    public GroupUpdatePage selectDownloadFileType(String type) {
        return (GroupUpdatePage) super.selectDownloadFileType(type);
    }

    @Override
    public GroupUpdatePage selectUploadFileType(String type) {
        return (GroupUpdatePage) super.selectUploadFileType(type);
    }

    public GroupUpdatePage selectUploadFileType(int index) {
        new Select(selectUploadFileTypeComboBox).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage setDelay(int minutes) {
        String[] time = CalendarUtil.getDelay(minutes);
        timeHoursSelect(time[0].replaceAll("^0", ""));
        timeMinutesSelect(time[1].replaceAll("^0", ""));
        String result = "Scheduled to " + calendarInput.getAttribute("value") + " " + getSelectedOption(timeHoursSelect) + ":" + getSelectedOption(timeMinutesSelect) + ":00";
        optionMap.put("Update state", result);
        return this;
    }

    public GroupUpdatePage startOnTimeDelay(int minutes) {
        String[] time = CalendarUtil.getDelay(minutes);
        selectComboBox(startsOnHours, time[0].replaceAll("^0", ""));
        selectComboBox(startsOnMinutes, time[1].replaceAll("^0", ""));
        optionMap.put("Reactivation", summary.getText());
        return this;
    }

    public GroupUpdatePage startsOnDayDelay(int days) {
        return selectShiftedDate("calReactivationStartsOnDay", days);
    }

    public GroupUpdatePage endsOnDayDelay(int days) {
        return selectShiftedDate("calReactivationEndsOnDay", days);
    }

    public GroupUpdatePage scheduleInDays(int days) {
        return selectShiftedDate("calDate", days);
    }

    public GroupUpdatePage waitForStatusWithoutRefresh(String status, int timeout) {
        String groupName = BaseTestCase.getTestName();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            try {
                while (!getMainTable().getCellText(groupName, 1).equals(status)) {
                    if (System.currentTimeMillis() - start > timeout * 1000L) {
                        throw new AssertionError("Timed out while waiting for status " + status);
                    }
                }
                return this;
            } catch (StaleElementReferenceException e) {
                System.out.println(e.getMessage());
            }
        }
        return this;
    }

    public GroupUpdatePage createDeviceGroup() {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .createGroupButton()
                .fillGroupName()
                .pause(1000)
                .bottomMenu(NEXT)
                .addFilter();
        return this;
    }

    public GroupUpdatePage setThreshold(int value) {
        thresholdField.sendKeys(String.valueOf(value));
        optionMap.put("Threshold", value + " %");
        return this;
    }

    public GroupUpdatePage endsAfter(String times) {
        inputText("txtReactivationEndsOccurrences", times);
        optionMap.put("Reactivation", optionMap.get("Reactivation") + "; " + times + " times");
        return this;
    }

    @Override
    public GroupUpdatePage inputText(String id, String text) {
        return (GroupUpdatePage) super.inputText(id, text);
    }

    public GroupUpdatePage goToSetPolicies(String tab) {
        goto_("Policy");
        if (tab != null) {
            selectTab(tab);
        }
        return this;
    }

    public GroupUpdatePage gotoSetParameters(String tab, boolean advancedView) {
        goto_("Set parameter value");
        if (tab != null) {
            selectTab(tab);
        }
        if (advancedView) {
            advancedViewButton.click();
        }
        return this;
    }

    public void setScheduledParameters(String tab) {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .selectSendTo()
                .addNewTask("Set parameter value")
                .addTaskButton()
                .selectTab(tab)
                .setParameter(2)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    public void setScheduledPolicy(String tab) {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .selectSendTo()
                .addNewTask("Policy")
                .addTaskButton()
                .selectTab(tab)
                .setPolicy(3)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateDetails()
                .bottomMenu(EDIT)
                .validateTask();
    }

    public void getScheduledParameter(String tab, int column) {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .selectSendTo()
                .addNewTask("Get parameter")
                .addTaskButton()
                .selectTab(tab)
                .getParameter(1, column)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateDetails()
                .bottomMenu(EDIT)
                .validateTask();
    }

    public void scheduledCallCustomRPC(String method) {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .selectSendTo()
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Custom RPC")
                .selectMethod(method)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateDetails()
                .bottomMenu(EDIT)
                .validateTask();
    }

    public void immediatelyActivateAndValidate() {
        bottomMenu(NEXT)
                .immediately()
                .saveAndActivate()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    public GroupUpdatePage saveAndValidateScheduledTasks() {
        return bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    public GroupUpdatePage gotoSetParameters(String tab) {
        return gotoSetParameters(tab, false);
    }

    public GroupUpdatePage gotoSetParameters() {
        return gotoSetParameters(null);
    }

    public GroupUpdatePage gotoGetParameter(String tableId) {
        return gotoGetParameter(tableId, false);
    }

    public GroupUpdatePage gotoGetParameter(String tab, boolean advancedView) {
        goto_("Get parameter");
        if (tab != null) {
            selectTab(tab);
        }
        if (advancedView) {
            bottomMenu(ADVANCED_VIEW);
        }
        return this;
    }

    @Override
    public GroupUpdatePage selectTab(String tab) {
        return (GroupUpdatePage) super.selectTab(tab);
    }

    @Override
    public GroupUpdatePage selectTab(String tab, Table tabTable) {
        return (GroupUpdatePage) super.selectTab(tab, tabTable);
    }

    public GroupUpdatePage gotoFileDownload() {
        return goto_("Download file");
    }

    public GroupUpdatePage gotoFileUpload() {
        return goto_("Upload file");
    }

    public GroupUpdatePage gotoBackup() {
        return goto_("Backup");
    }

    private GroupUpdatePage goto_(String taskName) {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName(BaseTestCase.getTestName())
                .addModelButton()
                .selectSendTo()
                .addNewTask(taskName)
                .addTaskButton()
        /*.getLastTask(taskName)*/;
        return this;
    }

    public GroupUpdatePage gotoAction() {
        return goto_("Action");
    }

    public GroupUpdatePage gotoDiagnostic() {
        return goto_("Diagnostics");
    }

    public GroupUpdatePage deleteAll() {
        topMenu(GROUP_UPDATE);
        itemsOnPage("200");
        setImplicitlyWait(0);
        while (noDataFound.size() == 0) {
            switchToFrame(DESKTOP);
            WebElement masterCheckbox = findElement(getMainTableId()).findElement(By.tagName("input"));
            setCheckboxState(true, masterCheckbox);
//            getMainTable().clickOn(0, 0);
            bottomMenu(DELETE);
            okButtonPopUp();
        }
        setDefaultImplicitlyWait();
        return this;
    }

    public GroupUpdatePage gotoRestore() {
        return goto_("Restore");
    }

    @Override
    public GroupUpdatePage getParameter(int row, int column) {
        return (GroupUpdatePage) super.getParameter(row, column);
    }

    public GroupUpdatePage validateName() {
        assertEquals(nameTextField.getAttribute("value"), BaseTestCase.getTestName());
        return this;
    }

    public GroupUpdatePage setParameter(String tab, String paramName, ParameterType option, String value) {
        getTabTable().clickOn(tab);
        return setParameter(getParamTable(), paramName, option, value);
    }

    public GroupUpdatePage setParameter(String paramName, ParameterType option, String value) {
        return setParameter(getParamTable(), paramName, option, value);
    }

    @Override
    public GroupUpdatePage setParameter(Table table, String paramName, ParameterType option, String value) {
        return (GroupUpdatePage) super.setParameter(table, paramName, option, value);
    }

    public GroupUpdatePage setParameter(int amount) {
        Table table = getParamTable();
        setImplicitlyWait(0);
//        int counter = (amount >= table.getTableSize()[0]) ? table.getTableSize()[0] : amount + 1;
        int size = table.getTableSize()[0];
        int counter = Math.min(size, amount + 1);
        for (int i = 1; i < counter; i++) {
            WebElement paramVal = table.getCellWebElement(i, 1);
            List<WebElement> selectList = paramVal.findElements(By.tagName("select"));
            if (selectList.size() == 0) {
                if (counter < size - 1) {
                    counter++;
                }
                continue;
            }
            String hint = table.getHint(i);
//            List<WebElement> optionList = selectList.get(0).findElements(By.tagName("option"));
            List<WebElement> optionList = new Select(selectList.get(0)).getOptions();
            ParameterType option;
            String value = "1";
            String attr = optionList.get(1).getAttribute("value");
            if (attr.equals("sendEmpty")) {
                option = ParameterType.VALUE;
                String val = generateValue(hint, "1");
                if (val != null) {
                    value = val;
                }
            } else if (attr.equals("true")) {
                option = ParameterType.TRUE;
            } else {
                option = ParameterType.CUSTOM;
                value = attr;
            }
            setParameter(table, table.getCellText(i, 0), option, value);
        }
        setDefaultImplicitlyWait();
        return this;
    }

    public GroupUpdatePage setAllParameters() {
        setParameter(99);
        return this;
    }

    public GroupUpdatePage setParameter(String tab, int amount) {
        if (tab != null) {
            getTabTable().clickOn(tab);
        }
        setParameter(amount);
        return this;
    }

    @Override
    public GroupUpdatePage selectBranch(String branch) {
        return (GroupUpdatePage) super.selectBranch(branch);
    }

    public GroupUpdatePage setAnyAdvancedParameter() {
        selectAnotherBranch();
        setParameter(1);
        return this;
    }

    public GroupUpdatePage setAdvancedParameter(String branch, int amount) {
        if (branch == null) {
            selectAnotherBranch();
        } else {
            selectBranch(branch);
        }
        setParameter(amount);
        return this;
    }

    public GroupUpdatePage setAllPolicies() {
        Table table = getParamTable();
        String[] names = table.getColumn(0);
        for (int i = 1; i < table.getTableSize()[0]; i++) {
            setPolicy(table, names[i - 1], Policy.ACTIVE, Policy.ALL);
//            String hint = table.getHint(i);
            getLastTask().setParameterName(table.getHint(i));
            getLastTask().setValue("Notification=Active Access=All");
//            getParameterMap().put(hint, "Notification=Active Access=All");
        }
        return this;
    }

    public GroupUpdatePage setPolicy(int scenario) {
        Table table = getParamTable();
        int length = table.getTableSize()[0];
        String[] names = table.getColumn(0);
        int counter = (scenario == 0 || scenario >= length) ? length : scenario + 1;
        if (scenario >= length) {
            LOGGER.warn('(' + BaseTestCase.getTestName() + ')' + "Number of parameters on current tab is not enough to execute this testcase");
        }
        if (scenario == 1) {
            setPolicy(table, names[0], null, Policy.ACS_ONLY);
//            String hint = table.getHint(1);
            getLastTask().setParameterName(table.getHint(1));
            getLastTask().setValue("Access=AcsOnly");
//            getParameterMap().put(hint, "Access=AcsOnly");
        } else if (scenario == 2) {
            for (int i = 1; i < counter; i++) {
                setPolicy(table, names[i - 1], Policy.OFF, null);
//                String hint = table.getHint(i);
                getLastTask().setParameterName(table.getHint(i));
                getLastTask().setValue("Notification=Off");
//                getParameterMap().put(hint, "Notification=Off ");
            }
        } else {
            Policy[] notify = {null, Policy.OFF, Policy.PASSIVE, Policy.ACTIVE};
            Policy[] access = {null, Policy.ACS_ONLY, Policy.ALL, null};
            String[] results = {null, "Notification=Off Access=AcsOnly", "Notification=Passive Access=All", "Notification=Active"};
            for (int i = 1; i < counter; i++) {
//                String hint = table.getHint(i);
                String name = names[i - 1];
                setPolicy(table, name, notify[i], access[i]);
                getLastTask().setParameterName(table.getHint(i));
                getLastTask().setValue(results[i]);
//                getParameterMap().put(hint, results[i]);
            }
        }
        return this;
    }

    public void setPolicy(Table table, String policyName, Policy notification, Policy accessList) {
        int rowNum = table.getFirstRowWithText(0, policyName);
        WebElement notificationCell = table.getCellWebElement(rowNum, 1);
        WebElement accessListCell = table.getCellWebElement(rowNum, 2);
        if (BROWSER.equals("edge")) {
            scrollToElement(notificationCell);
        }
        if (notification != null) {
            new Select(notificationCell.findElement(By.tagName("select"))).selectByValue(notification.getOption());
        }
        waitForUpdate();
        if (accessList != null) {
            new Select(accessListCell.findElement(By.tagName("select"))).selectByValue(accessList.getOption());
        }
        waitForUpdate();
    }

    public GroupUpdatePage validateOptions() {
        for (Map.Entry<String, String> entrySet : optionMap.entrySet()) {
            validateAddedPairs("tblPeriod", entrySet.getKey(), entrySet.getValue(), 0);
        }
        return this;
    }

    @Override
    public GroupUpdatePage validateTask() {
        switchToFrame(TASKS);
        return (GroupUpdatePage) super.validateTask();
    }

    @Override
    public GroupUpdatePage readTasksFromDb() {
        return (GroupUpdatePage) super.readTasksFromDb();
    }

    public GroupUpdatePage assertOnlineDevices() {
        Table table = getTable("tblPeriod");
        Assert.assertEquals(table.getCellText(table.getTableSize()[0] - 1, 0), "Online devices");
        Assert.assertEquals(table.getCellText(table.getTableSize()[0] - 1, 1), "True");
        return this;
    }

    @Override
    public GroupUpdatePage waitForStatus(String status, int timeoutSec) {
        if (optionMap.get("Update state").length() < 11) {
            optionMap.put("Update state", status);
        }
        return (GroupUpdatePage) super.waitForStatus(status, timeoutSec);
    }

    public GroupUpdatePage enterIntoGroup(String groupName) {
        enterIntoItem(groupName);
        return this;
    }

    public GroupUpdatePage enterIntoGroup() {
        return enterIntoGroup(BaseTestCase.getTestName());
    }

    public GroupUpdatePage clickOnHeader(String header) {
        return enterIntoGroup(header);
    }

    @Override
    public GroupUpdatePage assertElementsArePresent(String... elementsId) {
        return (GroupUpdatePage) super.assertElementsArePresent(elementsId);
    }

    @Override
    public GroupUpdatePage assertElementsAreAbsent(String... elementsId) {
        return (GroupUpdatePage) super.assertElementsAreAbsent(elementsId);
    }

    @Override
    public GroupUpdatePage assertButtonsAreEnabled(boolean enabled, IBottomButtons... buttons) {
        return (GroupUpdatePage) super.assertButtonsAreEnabled(enabled, buttons);
    }

    @Override
    public GroupUpdatePage assertButtonsArePresent(IBottomButtons... buttons) {
        return (GroupUpdatePage) super.assertButtonsArePresent(buttons);
    }

    @Override
    public GroupUpdatePage assertEqualsAlertMessage(String expectedMessage) {
        return (GroupUpdatePage) super.assertEqualsAlertMessage(expectedMessage);
    }

    public GroupUpdatePage assertSummaryTextEquals(String text) {
        assertEquals(summary.getText(), text);
        return this;
    }

    public GroupUpdatePage selectItemToDelete() {
        Table table = getTable("tblTasks", TASKS);
        int tableSize = table.getTableSize()[0];
        for (int i = 1; i < tableSize; i++) {
            if (!table.getCellText(i, 1).equals("Backup")) {
                setCheckboxState(true, table.getInput(i, 0));
            }
        }
        return this;
    }

    public void assertConditionIsPresent() {
        Table table = getTable("tblTasks");
        int column = table.getColumnNumber(0, "Conditions");
        WebElement cell = table.getCellWebElement(1, column);
        List<WebElement> buttonList = cell.findElements(By.tagName("input"));
        showPointer(!buttonList.isEmpty(), cell);
        if (buttonList.isEmpty()) {
            throw new AssertionError("Condition not found!");
        }
    }

    public enum Left implements ILeft {
        VIEW("View"), IMPORT("Import"), NEW("New");
        private final String value;

        Left(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Policy {
        //        DEFAULT("-1"),
        OFF("0"),
        PASSIVE("1"),
        ACTIVE("2"),
        ACS_ONLY("1"),
        ALL("2");

        private final String option;

        public String getOption() {
            return option;
        }

        Policy(String option) {
            this.option = option;
        }
    }

    public enum Conditions {
        EQUAL(2, "2"),
        NOT_EQUAL(7, "6"),
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
