package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.*;
import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.HttpConnector;
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
import java.io.IOException;
import java.time.Duration;
import java.util.*;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.CONDITIONS;
import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.DESKTOP;
//import static com.friendly.aqa.pageobject.DeviceUpdatePage.BottomButtons.ADD;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.*;

public class GroupUpdatePage extends BasePage {
    private static final Logger LOGGER = Logger.getLogger(GroupUpdatePage.class);

    public GroupUpdatePage() {
        super();
        switchToFrame(DESKTOP);
    }

    @FindBy(name = "ddlUpdateStatus")
    private WebElement updStatusComboBox;

    @FindBy(name = "btnEditView$btn")
    private WebElement editGroupButton;

    @FindBy(name = "btnNewView$btn")
    private WebElement createGroupButton;

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

    @FindBy(name = "btnShowDevices$btn")
    private WebElement showListButton;

    @FindBy(id = "txtFailedMax")
    private WebElement thresholdField;

    @FindBy(id = "lblWait")
    private WebElement waitUntilConnectRadioButton;

    @FindBy(id = "cbOnlineOnly")
    private WebElement onlineDevicesCheckBox;

    @FindBy(id = "tmScheduled_ddlHour")
    private WebElement timeHoursSelect;

    @FindBy(id = "tmScheduled_ddlMinute")
    private WebElement timeMinutesSelect;

    @FindBy(how = How.ID, using = "lblDBError")
    private List<WebElement> noDataFound;

    @FindBy(how = How.ID, using = "tblDevices")
    private List<WebElement> serialNumberTableList;

    @FindBy(how = How.ID, using = "tblTasks")
    private List<WebElement> taskTableList;

    @FindBy(id = "fuImport")
    private WebElement importGuField;

    @FindBy(id = "calDate_image")
    private WebElement calendarIcon;

    @FindBy(id = "calDate_calendar")
    private WebElement divCalendar;

    @FindBy(id = "btnDelete_btn")
    private WebElement deleteButton;

    @FindBy(id = "cbqoe_task")
    private WebElement addToQoeCheckBox;

    @FindBy(id = "tbName")
    private WebElement descriptionFileUploadField;

    public GroupUpdatePage topMenu(TopMenu value) {
        return (GroupUpdatePage) super.topMenu(value);
    }

    @Override
    public GroupUpdatePage selectImportDevicesFile() {
        return (GroupUpdatePage) super.selectImportDevicesFile();
    }

    public GroupUpdatePage selectImportGuFile() {
        switchToFrame(DESKTOP);
        String inputText = new File("import/" + getProtocolPrefix() + "_import_group.xml").getAbsolutePath();
        importGuField.sendKeys(inputText);
        ((JavascriptExecutor) getDriver()).executeScript("__doPostBack('btnSaveConfiguration','')");
        waitUntilButtonIsDisplayed(SAVE_AND_ACTIVATE);
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
        assertElementsAreEnabled(updStatusComboBox, manufacturerComboBox, modelComboBox, resetViewButton);
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
    public GroupUpdatePage selectShiftedDate(String id, int value) {
        return (GroupUpdatePage) super.selectShiftedDate(id, value);
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

    public GroupUpdatePage assertInputIsDisabled(String id) {
        assertFalse(findElement(id).isEnabled(), "Unexpected state of element #'" + id + "' (must be grayed out);");
        return this;
    }

    public void checkExportLink() {
        String fragment = BaseTestCase.getTestName().startsWith("lwm2m")
                ? "\"Root.Device.0.UTC Offset\" value=\"+02:00\""
                : "Device.ManagementServer.PeriodicInformInterval\" value=\"60\"";
        try {
            assertTrue(HttpConnector.sendGetRequest(
                    getGuExportLink("tr069_gu_016"))
                    .contains(fragment), "File export failed or file has unexpected content!");
        } catch (IOException e) {
            e.printStackTrace();
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

    public GroupUpdatePage selectFileName(String fileType) {
        return (GroupUpdatePage) super.selectFileName(fileType);
    }

    public GroupUpdatePage validateDownloadFileTasks() {
        Set<Map.Entry<String, String>> entrySet = getParameterMap().entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            validateAddedTask("tblTasks", entry.getKey(), entry.getValue(), 0);
        }
        return this;
    }

    public GroupUpdatePage selectFileName(int index) {
        new Select(fileNameComboBox).selectByIndex(index);
        return this;
    }

//    public GroupUpdatePage selectFileName(String fileName) {
//        String[] arr = fileName.split("/");
//        String shortName = arr[arr.length - 1];
//        selectComboBox(fileNameComboBox, shortName);
//        return this;
//    }

    @Override
    public GroupUpdatePage selectMethod(String value) {
        return (GroupUpdatePage) super.selectMethod(value);
    }

    public GroupUpdatePage onlineDevicesCheckBox() {
        onlineDevicesCheckBox.click();
        return this;
    }

    public GroupUpdatePage addToMonitoringCheckBox() {
        addToQoeCheckBox.click();
        return this;
    }

    public GroupUpdatePage setPeriod(int num) {
        driver.findElement(By.id("cbPeriod" + num)).click();
        String[] timeStart = CalendarUtil.getDelay(num * 10);
        String[] timeFinish = CalendarUtil.getDelay(num * 10 + 10);
        new Select(driver.findElement(By.id("tmPeriod" + num + "Start_ddlHour"))).selectByValue(timeStart[0].replaceAll("^0", ""));
        new Select(driver.findElement(By.id("tmPeriod" + num + "Start_ddlMinute"))).selectByValue(timeStart[1].replaceAll("^0", ""));
        new Select(driver.findElement(By.id("tmPeriod" + num + "Finish_ddlHour"))).selectByValue(timeFinish[0].replaceAll("^0", ""));
        new Select(driver.findElement(By.id("tmPeriod" + num + "Finish_ddlMinute"))).selectByValue(timeFinish[1].replaceAll("^0", ""));
        driver.findElement(By.id("txtPeriod" + num + "GroupSize")).sendKeys("3");
        driver.findElement(By.id("txtPeriod" + num + "Timeout")).sendKeys("5");
        return this;
    }

    public GroupUpdatePage waitUntilConnectRadioButton() {
        waitUntilConnectRadioButton.click();
        return this;
    }

    public GroupUpdatePage addTaskButton() {
        waitForUpdate();
        addTaskButton.click();
        return this;
    }

    public GroupUpdatePage addNewTask(String value) {
        selectComboBox(selectTask, value);
        return this;
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
        waitForUpdate();
        defaultUploadRadioButton.click();
        return this;
    }

    @Override
    public GroupUpdatePage selectFromListRadioButton() {
        return (GroupUpdatePage) super.selectFromListRadioButton();
    }

    @Override
    public GroupUpdatePage validateAddedTasks() {
        return (GroupUpdatePage) super.validateAddedTasks();
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

    public GroupUpdatePage selectSendTo() {
        return (GroupUpdatePage) super.selectSendTo();
    }

    public GroupUpdatePage selectSendTo(String sendTo) {
        return (GroupUpdatePage) super.selectSendTo(sendTo);
    }

    public GroupUpdatePage fillName(String name) {
        return (GroupUpdatePage) super.fillName(name);
    }

    public GroupUpdatePage fillName() {
        return (GroupUpdatePage) super.fillName();
    }

    @Override
    public GroupUpdatePage fillDownloadUrl() {
        return (GroupUpdatePage) super.fillDownloadUrl();
    }

    public GroupUpdatePage fillDescriptionUploadFile(String desc) {
        descriptionFileUploadField.sendKeys(desc);
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

//    public GroupUpdatePage inputTextField(String text) {
//        inputTextField.sendKeys(text);
//        return this;
//    }

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

    public GroupUpdatePage selectRepeatsDropDown(String value) {
        selectComboBox(reactivationRepeatsDropDown, value);
        return this;
    }

    public GroupUpdatePage selectRepeatEveryHourDropDown(String value) {
        waitForUpdate();
        selectComboBox(reactivationRepeatEveryHourDropDown, value);
        return this;
    }

    public GroupUpdatePage selectRepeatEveryDayDropDown(String value) {
        selectComboBox(reactivationRepeatEveryDayDropDown, value);
        return this;
    }

    public GroupUpdatePage selectRepeatEveryMonthDropDown(String value) {
        selectComboBox(reactivationRepeatEveryMonthDropDown, value);
        return this;
    }

    public GroupUpdatePage endAfterRadiobutton() {
        reactivationEndsAfterRadiobutton.click();
        return this;
    }

    public GroupUpdatePage endAOnRadiobutton() {
        reactivationEndsOn.click();
        return this;
    }

    public GroupUpdatePage runOnFailed() {
        reactivationReRunFailedCheckbox.click();
        return this;
    }

    public GroupUpdatePage selectDiagnostic(String value) {
        return (GroupUpdatePage) super.selectDiagnostic(value);
    }

    public GroupUpdatePage selectManufacturer() {
        return (GroupUpdatePage) selectManufacturer(getManufacturer());
    }

    public GroupUpdatePage selectModel() {
        return (GroupUpdatePage) super.selectModel();
    }

    @Override
    public GroupUpdatePage selectColumnFilter(String option) {
        return (GroupUpdatePage) super.selectColumnFilter(option);
    }

    public GroupUpdatePage deleteFilterGroups() {
        return (GroupUpdatePage) deleteAll(sendToComboBox);
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
        return (GroupUpdatePage) super.leftMenu(item);
    }

    public GroupUpdatePage createGroupButton() {
        waitForUpdate();
        createGroupButton.click();
        waitForUpdate();
        return this;
    }

    public GroupUpdatePage nextSaveAndActivate() {
        return nextSaveAndActivate(true);
    }

    public GroupUpdatePage nextSaveAndActivate(boolean waitForCompleted) {
        bottomMenu(NEXT);
        return saveAndActivate(waitForCompleted);
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
        return this;
    }

    public GroupUpdatePage checkFiltering(String dropdown, String option) {
        switchToFrame(DESKTOP);
        WebElement comboBox;
        switch (dropdown) {
            case "Manufacturer":
                comboBox = manufacturerComboBox;
                break;
            case "Model":
                comboBox = modelComboBox;
                break;
            case "State":
                comboBox = updStatusComboBox;
                break;
            default:
                throw new AssertionError("Incorrect dropdown name '" + dropdown + "'");
        }
        List<WebElement> options = comboBox.findElements(By.tagName("option"));
        for (WebElement opt : options) {
            if (opt.getText().toLowerCase().equals(option.toLowerCase())) {
                if (BROWSER.equals("edge")) {
                    scrollTo(comboBox);
                }
                new Select(comboBox).selectByValue(opt.getAttribute("value"));
                waitForUpdate();
                break;
            }
        }
        if (dropdown.equals("State") && option.equals("Error") && noDataFound.size() == 1) {
            return this;
        }
        Table table;
        try {
            table = getMainTable();
            itemsOnPage("200");
        } catch (NoSuchElementException e) {
            LOGGER.info("List '" + option + "' is empty. Nothing to filter");
            return this;
        }
        String[] arr = table.getColumn(dropdown);
        Set<String> set = new HashSet<>(Arrays.asList(arr));
        if (dropdown.equals("State") && option.equals("All") && set.size() > 1) {
            itemsOnPage("10");
            return this;
        }
        if (set.size() != 1 && !set.contains(option)) {
            String warn = "Filtering failed on dropdown '" + dropdown + "'";
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

    public GroupUpdatePage addCondition(int rowNumber, String branch, String conditionName, Conditions condition, String value) {
        WebElement button = driver.findElement(By.id("btnAddTaskParameter-" + rowNumber + "_btn"));
        button.click();
        pause(1000);
        Table treeTable = getTable("tblTree", CONDITIONS);
        treeTable.clickOn(branch);
        Table paramTable = getTable("tblParamsValue", CONDITIONS);
        setCondition(paramTable, conditionName, condition, value);
        WebElement saveButton = driver.findElement(By.id("btnSave_btn"));
        new FluentWait<>(driver).withMessage("Element was not found")
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(100))
                .until(ExpectedConditions.attributeToBe(saveButton, "class", "button_default"));
        saveButton.click();
        return this;
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
        boolean status = getSelectedOption(updStatusComboBox).equals("All");
        String[] arr = table.getColumn("Created");
        String[] arr2 = arr.clone();
        Arrays.sort(arr, Comparator.reverseOrder());
        boolean sortedByCreated = Arrays.deepEquals(arr, arr2);
        if (!(man && model && status && sortedByCreated)) {
            String warn = "\"Reset View\" check failed";
            LOGGER.warn('(' + BaseTestCase.getTestName() + ')' + warn);
            throw new AssertionError(warn);
        }
        return this;
    }

    public boolean serialNumberTableIsPresent() {
        return serialNumberTableList.size() != 0;
    }

    public void assertDevicesArePresent() {
        assertElementsArePresent("tblDevices");
    }

    public GroupUpdatePage selectFileType(int index) {
        new Select(selectDownloadFileTypeComboBox).selectByIndex(index);
        return this;
    }

    @Override
    public GroupUpdatePage selectDownloadFileType(String type) {
        setImplicitlyWait(0);
        List<WebElement> list = findElements("UcFirmware1_ddlFileType");
        if (!list.isEmpty() && !list.get(0).isDisplayed()) {
            bottomMenu(ADD);
        }
        setDefaultImplicitlyWait();
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
        return this;
    }

    public GroupUpdatePage waitForStatusWithoutRefresh(String status, int timeout) {
        String groupName = BaseTestCase.getTestName();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            try {
                while (!getMainTable().getCellText(groupName, 1).equals(status)) {
                    if (System.currentTimeMillis() - start > timeout * 1000) {
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
                .createGroupButton()
                .fillName()
                .pause(1000)
                .bottomMenu(NEXT)
                .addFilter();
        return this;
    }

    public GroupUpdatePage setThreshold(int value) {
        thresholdField.sendKeys(String.valueOf(value));
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

    public GroupUpdatePage gotoSetParameters(boolean advancedView) {
        return gotoSetParameters(null, advancedView);
    }

    public GroupUpdatePage gotoSetParameters(String tab, boolean advancedView) {
        goto_("Set parameter value");
        if (tab != null) {
            selectTab(tab);
        }
        if (advancedView) {
            bottomMenu(ADVANCED_VIEW);
        }
        return this;
    }

    public void setScheduledParameters(String tab) {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .selectTab(tab)
                .setParameter(2)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup();
        validateAddedTasks();
    }

    public void setScheduledPolicy(String tab) {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Policy")
                .addTaskButton()
                .waitForUpdate()
                .selectTab(tab)
                .setPolicy(3)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    public void getScheduledParameter(String tab, int column) {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Get parameter")
                .addTaskButton()
                .selectTab(tab)
                .getParameter(1, column)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    public void scheduledCallCustomRPC(String method) {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Custom RPC")
                .selectMethod(method)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTask("Custom RPC", method);
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
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask(taskName)
                .addTaskButton();
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
        while (noDataFound.size() == 0) {
            switchToFrame(DESKTOP);
            getMainTable().clickOn(0, 0);
            bottomMenu(DELETE)
                    .okButtonPopUp();
        }
        return this;
    }

    public GroupUpdatePage gotoRestore() {
        return goto_("Restore");
    }

    @Override
    public GroupUpdatePage getParameter(int row, int column) {
        return (GroupUpdatePage) super.getParameter(row, column);
    }

    public GroupUpdatePage setParameter(String tab, String paramName, ParameterType option, String value) {
        getTabTable().clickOn(tab);
        return setParameter(getParamTable(), paramName, option, value);
    }

    public GroupUpdatePage setParameter(String paramName, ParameterType option, String value) {
        return setParameter(getParamTable(), paramName, option, value);
    }

    public GroupUpdatePage setParameter(Table table, String paramName, ParameterType option, String value) {
//        int rowNum = table.getRowNumberByText(paramName);
//        String hint = table.getHint(rowNum);
//        WebElement paramCell = table.getCellWebElement(rowNum, 1);
//        if (props.getProperty("browser").equals("edge")) {
//            scrollToElement(paramCell);
//        }
//        new Select(paramCell.findElement(By.tagName("select"))).selectByValue(option != Parameter.CUSTOM ? option.getOption() : value);
//        if (value != null && option == Parameter.VALUE) {
//            waitForUpdate();
//            WebElement input = paramCell.findElement(By.tagName("input"));
//            input.clear();
//            input.sendKeys(value);
//        }
//        getParameterMap().put(hint, value);
//        if (!BROWSER.equals("edge")) {
//            table.clickOn(0, 0);
//        }
//        return this;
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
            String hint = table.getHint(i);
            getParameterMap().put(hint, "Notification=Active Access=All");
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
            String hint = table.getHint(1);
            getParameterMap().put(hint, "Access=AcsOnly");
        } else if (scenario == 2) {
            for (int i = 1; i < counter; i++) {
                setPolicy(table, names[i - 1], Policy.OFF, null);
                String hint = table.getHint(i);
                getParameterMap().put(hint, "Notification=Off ");
            }
        } else {
            Policy[] notify = {null, Policy.OFF, Policy.PASSIVE, Policy.ACTIVE};
            Policy[] access = {null, Policy.ACS_ONLY, Policy.ALL, null};
            String[] results = {null, "Notification=Off Access=AcsOnly", "Notification=Passive Access=All", "Notification=Active "};
            for (int i = 1; i < counter; i++) {
                String hint = table.getHint(i);
                String name = names[i - 1];
                setPolicy(table, name, notify[i], access[i]);
                getParameterMap().put(hint, results[i]);
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

    public GroupUpdatePage validateAddedTask(String parameter, String value) {
        return (GroupUpdatePage) super.validateAddedTask(parameter, value);
    }

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
        return (GroupUpdatePage) super.waitForStatus(status, timeoutSec);
    }

    public GroupUpdatePage enterIntoGroup(String groupName) {
        enterIntoItem(groupName);
        return this;
    }

    public GroupUpdatePage enterIntoGroup() {
        return enterIntoGroup(BaseTestCase.getTestName());
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
