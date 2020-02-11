package com.friendly.aqa.pageobject;

import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.Table;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.time.Duration;
import java.util.*;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;
import static com.friendly.aqa.pageobject.GlobalButtons.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.NEW;
import static com.friendly.aqa.pageobject.TopMenu.DEVICE_UPDATE;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;

public class GroupUpdatePage extends BasePage {
    private static Logger logger = Logger.getLogger(GroupUpdatePage.class);

    public GroupUpdatePage() {
        super();
        switchToFrame(DESKTOP);
    }

    @Override
    protected String getLeftMenuCssSelector() {
        return "tr[topmenu='Update Group']";
    }

    @FindBy(id = "ddlManufacturer")
    private WebElement manufacturerComboBox;

    @FindBy(id = "ddlModelName")
    private WebElement modelComboBox;

    @FindBy(name = "ddlUpdateStatus")
    private WebElement updStatusComboBox;

    @FindBy(id = "txtName")
    private WebElement nameField;

    @FindBy(id = "ddlSend")
    private WebElement sendToField;

    @FindBy(id = "txtText")
    private WebElement inputTextField;

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

    @FindBy(id = "ddlPageSizes")
    private WebElement itemsOnPageComboBox;

    @FindBy(id = "lrbImmediately")
    private WebElement immediatelyRadioButton;

    @FindBy(id = "lrbWaitScheduled")
    private WebElement scheduledToRadioButton;

    @FindBy(id = "btnAddFilter_btn")
    private WebElement addFilterButton;

    @FindBy(id = "ddlColumns")
    private WebElement selectColumnFilter;

    @FindBy(id = "ddlCondition")
    private WebElement compareSelect;

    @FindBy(id = "btnOk_btn")
    private WebElement okButtonPopUp;

    @FindBy(id = "ddlTasks")
    private WebElement selectTask;

    @FindBy(id = "btnAddTask_btn")
    private WebElement addTaskButton;

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

    @FindBy(how = How.ID, using = "tbl_tasks")
    private List<WebElement> taskTableList;

    @FindBy(id = "fuImport")
    private WebElement importGuField;

    @FindBy(id = "fuSerials")
    private WebElement importDeviceField;

    @FindBy(id = "txtHost")
    private WebElement inputHostField;

    @FindBy(id = "txtNumberRepetitions")
    private WebElement numOfRepetitionsField;

    @FindBy(id = "txtDnsServer")
    private WebElement inputDnsField;

    @FindBy(id = "frmImportFromFile")
    private WebElement importFrame;

    @FindBy(id = "calDate_image")
    private WebElement calendarIcon;

    @FindBy(id = "calDate_calendar")
    private WebElement divCalendar;

    @FindBy(id = "UcFirmware1_ddlFileType")
    private WebElement selectFileTypeComboBox;

    @FindBy(id = "ddlFileType")
    private WebElement selectUploadFileTypeComboBox;

    @FindBy(id = "ddlDiagnostics")
    private WebElement diagnosticTypeComboBox;

    @FindBy(id = "UcFirmware1_rdUrl")
    private WebElement manualRadioButton;

    @FindBy(id = "UcFirmware1_tbUrl")
    private WebElement urlField;

    @FindBy(id = "tbUrl")
    private WebElement uploadUrlField;

    @FindBy(id = "UcFirmware1_tbLogin")
    private WebElement userNameField;

    @FindBy(id = "tbLogin")
    private WebElement userNameUploadField;

    @FindBy(id = "UcFirmware1_tbPass")
    private WebElement passwordField;

    @FindBy(id = "UcFirmware1_rdTarget")
    private WebElement fromListRadioButton;

    @FindBy(id = "btnDelete_btn")
    private WebElement deleteButton;

    @FindBy(id = "UcFirmware1_ddlFileName")
    private WebElement fileNameComboBox;

    @FindBy(id = "ddlMethods")
    private WebElement selectMethodComboBox;

    @FindBy(id = "rdUrlUpload")
    private WebElement manuallyUrlRadioButton;

    @FindBy(id = "rdReboot")
    private WebElement rebootRadioButton;

    @FindBy(id = "rdFactoryReset")
    private WebElement factoryResetRadioButton;

    @FindBy(id = "rdReset Min and Max Measured Values")
    private WebElement resetMinMaxValues;

    @FindBy(id = "rdReset Cumulative energy")
    private WebElement resetCumulativeEnergy;

    @FindBy(id = "rdResetErrors")
    private WebElement resetErrors;

    @FindBy(id = "rdDisable")
    private WebElement radioDisable;

    @FindBy(id = "rdRegistrationUpdateTrigger")
    private WebElement radioRegistrationUpdateTrigger;

    @FindBy(id = "rdStartOrReset")
    private WebElement radioStartOrReset;

    @FindBy(id = "cbqoe_task")
    private WebElement addToQoeCheckBox;

    @FindBy(id = "rdCPEReprovision")
    private WebElement reprovisionRadioButton;

    @FindBy(id = "rdCustomRPC")
    private WebElement customRpcRadioButton;

    @FindBy(id = "tbName")
    private WebElement descriptionFileUploadField;

    @FindBy(id = "rdDefaultUpload")
    private WebElement defaultUploadRadioButton;

    @FindBy(id = "btnDefaultView_btn")
    private WebElement resetViewButton;

    public GroupUpdatePage topMenu(TopMenu value) {
        super.topMenu(value);
        switchToFrame(DESKTOP);
        waitForUpdate();
        return this;
    }

    public GroupUpdatePage selectImportDevicesFile() {
        switchToFrame(DESKTOP);
        driver.switchTo().frame(importFrame);
        String inputText = new File(getImportCpeFile()).getAbsolutePath();
        importDeviceField.sendKeys(inputText);
        driver.switchTo().parentFrame();
        return this;
    }

    public GroupUpdatePage selectImportGuFile() {
        switchToFrame(DESKTOP);
        String inputText = new File(getImportGroupFile()).getAbsolutePath();
        System.out.println(inputText);
        importGuField.sendKeys(inputText);
        ((JavascriptExecutor) BasePage.getDriver()).executeScript("__doPostBack('btnSaveConfiguration','')");
        return this;
    }

    public void assertResultTableIsAbsent() {
//        waitForUpdate();
        if (!taskTableList.isEmpty()) {
            throw new AssertionError("Task table was found on page");
        }
    }

    public void assertElementIsPresent(String id) {
        List<WebElement> list = driver.findElements(By.id(id));
        if (list.size() == 0) {
            String warn = "Element with id='" + id + "' not found on the Group Update page";
            logger.warn(warn);
            throw new AssertionError(warn);
        }
    }

    public GroupUpdatePage presetFilter(String parameter, String value) {
        topMenu(DEVICE_UPDATE)
                .getTable("tbl")
                .clickOn(getSerial(), 3);
        waitForUpdate();
        clickOn("btnEditUserInfo_lnk");
        switchToFrame(USER_INFO);
        WebElement saveButton = driver.findElement(By.id("btnSaveUsr_btn"));
//        pause(2000);
        while (!saveButton.isDisplayed()) {
            System.out.println("not displayed");
            pause(100);
        }
        getTable("tblMain")
                .setUserInfo(parameter, value);
        while (!saveButton.isEnabled()) {
            pause(100);
        }
        saveButton.click();
        okButtonPopUp();

        return this;
    }

    public Table getMainTable() {
        return getTable("tblParameters");
    }

    public Table getTable(String id) {
        return getTable(id, null);
    }

    public Table getTable(String id, FrameSwitch frame) {
        waitForUpdate();
        if (frame != null) {
            switchToFrame(frame);
        }
        WebElement tableEl = driver.findElement(By.id(id));
        setImplicitlyWait(0);
        Table table = new Table(tableEl);
        setDefaultImplicitlyWait();
        return table;
    }

    public GroupUpdatePage clickOn(String id) {
        driver.findElement(By.id(id)).click();
        return this;
    }

    public void executeScript(String script) {
        ((JavascriptExecutor) BasePage.getDriver()).executeScript(script);
    }

    public GroupUpdatePage selectTodayDate(String date) {
        executeScript("CalendarPopup_FindCalendar('calFilterDate').SelectDate('" + date + "')");
        return this;
    }

    public GroupUpdatePage selectShiftedDate(String id, int value) {
        executeScript("CalendarPopup_FindCalendar('" + id + "').SelectDate('" + CalendarUtil.getShiftedDate(value) + "')");
        return this;
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
                        logger.info("First day of month in Sunday. Test case 19 is not effective");
                    }
                    if (exception) {
                        logger.info("An exception was caught while checking grayed-out dates");
                    }
                    try {
                        cell.click();
                    } catch (ElementNotInteractableException e) {
                        logger.info("An exception was caught when click on current date");
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

    public GroupUpdatePage selectFileName(int index) {
        new Select(fileNameComboBox).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage selectMethod(String value) {
        selectComboBox(selectMethodComboBox, value);
        return this;
    }

    public GroupUpdatePage onlineDevicesCheckBox() {
        onlineDevicesCheckBox.click();
        return this;
    }

    public GroupUpdatePage addToQoeCheckBox() {
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

    public GroupUpdatePage manualRadioButton() {
        waitForUpdate();
        manualRadioButton.click();
        return this;
    }

    public GroupUpdatePage manuallyUrlRadioButton() {
        waitForUpdate();
        manuallyUrlRadioButton.click();
        return this;
    }

    public GroupUpdatePage rebootRadioButton() {
        waitForUpdate();
        rebootRadioButton.click();
        return this;
    }

    public GroupUpdatePage factoryResetRadioButton() {
        waitForUpdate();
        factoryResetRadioButton.click();
        return this;
    }

    public GroupUpdatePage resetMinMaxValues() {
        waitForUpdate();
        resetMinMaxValues.click();
        return this;
    }

    public GroupUpdatePage resetCumulativeEnergy() {
        waitForUpdate();
        resetCumulativeEnergy.click();
        return this;
    }

    public GroupUpdatePage resetErrors() {
        waitForUpdate();
        resetErrors.click();
        return this;
    }

    public GroupUpdatePage radioDisable() {
        waitForUpdate();
        radioDisable.click();
        return this;
    }

    public GroupUpdatePage radioRegistrationUpdateTrigger() {
        waitForUpdate();
        radioRegistrationUpdateTrigger.click();
        return this;
    }

    public GroupUpdatePage radioStartOrReset() {
        waitForUpdate();
        radioStartOrReset.click();
        return this;
    }

    public GroupUpdatePage reprovisionRadioButton() {
        waitForUpdate();
        reprovisionRadioButton.click();
        return this;
    }

    public GroupUpdatePage editGroupButton() {
        waitForUpdate();
        editGroupButton.click();
        return this;
    }


    public GroupUpdatePage customRpcRadioButton() {
        waitForUpdate();
        customRpcRadioButton.click();
        return this;
    }

    public GroupUpdatePage defaultUploadRadioButton() {
        waitForUpdate();
        defaultUploadRadioButton.click();
        return this;
    }

    public GroupUpdatePage fromListRadioButton() {
        waitForUpdate();
        fromListRadioButton.click();
        return this;
    }

    public GroupUpdatePage deleteButton() {
        waitForUpdate();
        deleteButton.click();
        return this;
    }

    public GroupUpdatePage okButtonPopUp() {
        switchToFrame(ROOT);
        okButtonPopUp.click();
        switchToFrame(DESKTOP);
        return this;
    }

    public void addFilter() {
        addFilterButton.click();
    }

    public GroupUpdatePage compareSelect(String option) {
        selectComboBox(compareSelect, option);
        return this;
    }

    public GroupUpdatePage selectColumnFilter(String option) {
        selectComboBox(selectColumnFilter, option);
        return this;
    }

    public GroupUpdatePage scheduledToRadioButton() {
        scheduledToRadioButton.click();
        return this;
    }

    public GroupUpdatePage immediately() {
        immediatelyRadioButton.click();
        return this;
    }

    public boolean isElementDisplayed(String id) {
        waitForUpdate();
        return driver.findElement(By.id(id)).isDisplayed();
    }

    public GroupUpdatePage itemsOnPage(String number) {
        waitForUpdate();
        try {
            if (new Select(itemsOnPageComboBox).getFirstSelectedOption().getText().equals(number)) {
                return this;
            }
        } catch (NoSuchElementException e) {
            logger.info("Select 'Items/page' not found");
            return this;
        }
        int index;
        switch (number) {
            case "7":
                index = 0;
                break;
            case "10":
                index = 1;
                break;
            case "20":
                index = 3;
                break;
            case "30":
                index = 4;
                break;
            case "50":
                index = 5;
                break;
            case "100":
                index = 6;
                break;
            case "200":
                index = 7;
                break;
            default:
                index = 2;
        }
        if (props.getProperty("browser").equals("edge")) {
            scrollTo(itemsOnPageComboBox);
        }
        new Select(itemsOnPageComboBox).selectByIndex(index);

        return this;
    }

    public GroupUpdatePage showList() {
        waitForUpdate();
        showListButton.click();
        return this;
    }

    public GroupUpdatePage selectSendTo() {
        new Select(sendToField).selectByValue("All");
        return this;
    }

    public GroupUpdatePage selectSendTo(String sendTo) {
        selectComboBox(sendToField, sendTo);
        return this;
    }

    public GroupUpdatePage fillName(String name) {
        nameField.sendKeys(name);
        return this;
    }

    public GroupUpdatePage fillName() {
        nameField.sendKeys(BaseTestCase.getTestName());
        return this;
    }

    public GroupUpdatePage fillUrl(String url) {
        urlField.sendKeys(url);
        return this;
    }

    public GroupUpdatePage fillDescriptionUploadFile(String desc) {
        descriptionFileUploadField.sendKeys(desc);
        return this;
    }

    public GroupUpdatePage fillUploadUrl(String url) {
        uploadUrlField.clear();
        uploadUrlField.sendKeys(url);
        userNameUploadField.click();
        return this;
    }

    public GroupUpdatePage fillUserName(String userName) {
        userNameField.sendKeys(userName);
        return this;
    }

    public GroupUpdatePage fillpassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    public GroupUpdatePage inputTextField(String text) {
        inputTextField.sendKeys(text);
        return this;
    }

    public GroupUpdatePage inputHostField(String text) {
        inputHostField.sendKeys(text);
        return this;
    }

    public GroupUpdatePage inputDnsField(String text) {
        inputDnsField.sendKeys(text);
        return this;
    }

    public GroupUpdatePage numOfRepetitionsField(String text) {
        numOfRepetitionsField.sendKeys(text);
        return this;
    }

    public GroupUpdatePage selectRepeatsDropDown(String value) {
        selectComboBox(reactivationRepeatsDropDown, value);
        return this;
    }

    public GroupUpdatePage selectRepeatEveryHourDropDown(String value) {
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
        try {
            selectComboBox(diagnosticTypeComboBox, value);
        } catch (NoSuchElementException e) {
            String warn = value + " type is not supported by current device!";
            logger.warn(warn);
            throw new AssertionError(warn);
        }
        return this;
    }

    public GroupUpdatePage selectManufacturer() {
        return selectManufacturer(getManufacturer());
    }

    public GroupUpdatePage selectManufacturer(String manufacturer) {
        selectComboBox(manufacturerComboBox, manufacturer);
        return this;
    }

    public GroupUpdatePage selectModel() {
        selectModel(getModelName());
        return this;
    }

    public GroupUpdatePage selectModel(String modelName) {
        selectComboBox(modelComboBox, modelName);
        return this;
    }

    private void selectComboBox(WebElement combobox, String value) {
        waitForUpdate();
        List<WebElement> options = combobox.findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (option.getText().toLowerCase().equals(value.toLowerCase())) {
                new Select(combobox).selectByValue(option.getAttribute("value"));
                return;
            }
        }
        new Select(combobox).selectByValue(value);
    }

    public GroupUpdatePage deleteFilterGroups() {
        List<WebElement> optList;
        List<String> standard = new ArrayList<>(Arrays.asList("NotSet", "All", "Individual", "Random", "Import"));
        while ((optList = sendToField.findElements(By.tagName("option"))).size() > 5) {
            for (WebElement option : optList) {
                String value = option.getAttribute("value");
                if (standard.contains(value)) {
                    continue;
                }
                new Select(sendToField).selectByValue(value);
                editGroupButton();
                try {
                    globalButtons(DELETE_GROUP);
                } catch (NoSuchElementException e) {
                    switchToPrevious();
                    executeScript("SelectSendTp();");
                    System.out.println("Execution script forced");
                }
                okButtonPopUp();
                break;
            }
        }
        return this;
    }

    public boolean isOptionPresent(String comboBoxId, String text) {
        waitForUpdate();
        WebElement combobox = driver.findElement(By.id(comboBoxId));
        List<WebElement> options = combobox.findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (option.getText().toLowerCase().equals(text.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public GroupUpdatePage globalButtons(GlobalButtons button) {
        clickGlobalButtons(button);
        return this;
    }

    public GroupUpdatePage leftMenu(GroupUpdatePage.Left item) {
        switchToFrame(ROOT);
        leftMenuClick(item.getValue());
        waitForUpdate();
        switchToFrame(DESKTOP);
        return this;
    }

    public GroupUpdatePage createGroup() {
        waitForUpdate();
        createGroupButton.click();
        waitForUpdate();
        return this;
    }

    public void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Table nextSaveAndActivate() {
        return nextSaveAndActivate(true);
    }

    public Table nextSaveAndActivate(boolean waitForCompleted) {
        globalButtons(NEXT);
        return saveAndActivate(waitForCompleted);
    }

    public Table saveAndActivate() {
        return saveAndActivate(true);
    }

    public Table saveAndActivate(boolean waitForCompleted) {
        globalButtons(SAVE_AND_ACTIVATE);
        okButtonPopUp();
        Table table;
        if (waitForCompleted) {
            table = waitForStatus("Completed", 30);
        } else {
            table = getMainTable();
        }
        table.readTasksFromDB()
                .clickOn(BaseTestCase.getTestName(), 4);
        return new Table("tblTasks");
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
            logger.info("List '" + option + "' is empty. Nothing to filter");
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
            logger.warn(warn);
            itemsOnPage("10");
            throw new AssertionError(warn);
        }
        itemsOnPage("10");
        return this;
    }

    public GroupUpdatePage checkSorting(String column) {
//        itemsOnPage("200");
        waitForUpdate();
        Table table = getMainTable();
        int colNum = table.getColumnNumber(0, column);
        table.clickOn(0, colNum);
        waitForUpdate();
        table = getMainTable();
        String[] arr = table.getColumn(colNum);
        String[] arr2 = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arr);
        if (!Arrays.deepEquals(arr, arr2)) {
            String warn = "Sorting check failed";
            logger.warn(warn);
            throw new AssertionError(warn);
        }
        table.clickOn(0, colNum);
        waitForUpdate();
        table = getMainTable();
        arr = table.getColumn(colNum);
        arr2 = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arr, Comparator.reverseOrder());
        if (!Arrays.deepEquals(arr, arr2)) {
            String warn = "Reverse sorting check failed";
            logger.warn(warn);
            throw new AssertionError(warn);
        }
        return this;
    }

    public GroupUpdatePage addCondition(int rowNumber, String branch, String conditionName, Table.Conditions condition, String value) {
        WebElement button = driver.findElement(By.id("btnAddTaskParameter-" + rowNumber + "_btn"));
        button.click();
        Table treeTable = getTable("tblTree", CONDITIONS);
        treeTable.clickOn(branch);
        Table paramTable = getTable("tblParamsValue", CONDITIONS);
        paramTable.setCondition(conditionName, condition, value);
        WebElement saveButton = driver.findElement(By.id("btnSave_btn"));
        new FluentWait<>(driver).withMessage("Element was not found")
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(100))
                .until(ExpectedConditions.attributeToBe(saveButton, "class", "button_default"));
        saveButton.click();
        return this;
    }

    public void resetView() {
        resetViewButton.click();
        waitForUpdate();
    }

    public void checkResetView() {
        waitForUpdate();
        resetView();
        if (BROWSER.equals("chrome")) {
            pause(500);
        }
        Table table = getMainTable();
        boolean man = new Select(manufacturerComboBox).getFirstSelectedOption().getText().equals("All");
        boolean model = new Select(modelComboBox).getFirstSelectedOption().getText().equals("All");
        boolean status = new Select(updStatusComboBox).getFirstSelectedOption().getText().equals("All");
        String[] arr = table.getColumn("Created");
        String[] arr2 = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arr, Comparator.reverseOrder());
        boolean sortedByCreated = Arrays.deepEquals(arr, arr2);
        if (!(man && model && status && sortedByCreated)) {
            String warn = "\"Reset View\" check failed";
            logger.warn(warn);
            throw new AssertionError(warn);
        }
    }

    public boolean serialNumberTableIsPresent() {
        return serialNumberTableList.size() != 0;
    }

    public boolean mainTableIsAbsent() {
        return noDataFound.size() == 1;
    }

    public GroupUpdatePage selectFileType(int index) {
        new Select(selectFileTypeComboBox).selectByIndex(index);
        return this;
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

    public Table waitForStatus(String status, int timeout) {
        long start = System.currentTimeMillis();
        Table table;
        while (!(table = getMainTable()).getCellText(4, BaseTestCase.getTestName(), 1).equals(status)) {
            globalButtons(REFRESH);
            if (System.currentTimeMillis() - start > timeout * 1000) {
                throw new AssertionError("Timed out while waiting for status " + status);
            }
        }
        return table;
    }

    public Table waitForStatusWithoutRefresh(String status, int timeout) {
        String groupName = BaseTestCase.getTestName();
        long start = System.currentTimeMillis();
        Table table = null;
        for (int i = 0; i < 2; i++) {
            try {
                while (!(table = getMainTable()).getCellText(4, groupName, 1).equals(status)) {
                    if (System.currentTimeMillis() - start > timeout * 1000) {
                        throw new AssertionError("Timed out while waiting for status " + status);
                    }
                }
                return table;
            } catch (StaleElementReferenceException e) {
                System.out.println(e.getMessage());
            }
        }
        return table;
    }

    public GroupUpdatePage gotoAddFilter() {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .createGroup()
                .fillName()
                .globalButtons(NEXT)
                .addFilter();
        return this;
    }

    public GroupUpdatePage setThreshold(int value) {
        thresholdField.sendKeys(String.valueOf(value));
        return this;
    }

    public GroupUpdatePage inputText(String id, String text) {
        driver.findElement(By.id(id)).sendKeys(text);
        return this;
    }

    public Table goToSetPolicies(String tab) {
        goto_("Policy");
        if (tab != null && !tab.toLowerCase().equals("management")) {
            getTable("tabsSettings_tblTabs").clickOn(tab);
        }
        return getTable("tblParamsValue");
    }

    public Table gotoSetParameters(String tab, boolean advancedView) {
        goto_("Set parameter value");
        if (tab != null) {
            getTable("tabsSettings_tblTabs").clickOn(tab);
        }
        if (advancedView) {
            globalButtons(ADVANCED_VIEW);
        }
        return getTable("tblParamsValue");
    }

    public void setScheduledParameters(String tab) {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .getTable("tabsSettings_tblTabs")
                .clickOn(tab)
                .getTable("tblParamsValue")
                .setParameter(2);
        globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(BaseTestCase.getTestName(), 4);
        getTable("tblTasks")
                .checkResults();
    }

    public void setScheduledPolicy(String tab) {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Policy")
                .addTaskButton();
        if (tab != null && !tab.toLowerCase().equals("management")) {
            getTable("tabsSettings_tblTabs")
                    .clickOn(tab);
        }
        getTable("tblParamsValue")
                .setPolicy(3);
        globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(BaseTestCase.getTestName(), 4);
        getTable("tblTasks")
                .checkResults();
    }

    public void getScheduledParameter(String tab, int column) {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Get parameter")
                .addTaskButton();
        if (tab != null && !tab.toLowerCase().equals("management")) {
            getTable("tabsSettings_tblTabs")
                    .clickOn(tab);
        }
        getTable("tblParamsValue")
                .getParameter(1, column);
        globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(BaseTestCase.getTestName(), 4);
        getTable("tblTasks")
                .checkResults();
    }

    public void scheduledCallCustomRPC(String method) {
        topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .customRpcRadioButton()
                .selectMethod(method)
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(BaseTestCase.getTestName(), 4);
        getTable("tblTasks")
                .checkResults("CustomRPC", method);
    }

    public Table gotoSetParameters(String tab) {
        return gotoSetParameters(tab, false);
    }

    public Table gotoSetParameters() {
        return gotoSetParameters(null);
    }

    public Table gotoGetParameter() {
        return gotoGetParameter("tblParamsValue");
    }

    public Table gotoGetParameter(String tableId) {
        return gotoGetParameter(tableId, false);
    }

    public Table gotoGetParameter(String tab, boolean advancedView) {
        goto_("Get parameter");
        if (tab != null && !tab.toLowerCase().equals("management")) {
            getTable("tabsSettings_tblTabs").clickOn(tab);
        }
        if (advancedView) {
            globalButtons(ADVANCED_VIEW);
        }
        return getTable("tblParamsValue");
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
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(taskName)
                .addTaskButton();
        return this;
    }

    public GroupUpdatePage gotoAction() {
        return goto_("Action");
    }

    public GroupUpdatePage gotoDiagnostic() {
        return goto_("Diagnostic");
    }

    public void deleteAll() {
        topMenu(GROUP_UPDATE);
        while (noDataFound.size() == 0) {
            switchToFrame(DESKTOP);
            getMainTable().clickOn(0, 0);
            globalButtons(DELETE)
                    .okButtonPopUp();
        }
    }

    public String getSelectedValue(String inputId) {
        return getSelectedValue(driver.findElement(By.id(inputId)));
    }

    private String getSelectedValue(WebElement comboBox) {
        List<WebElement> optList = comboBox.findElements(By.tagName("option"));
        for (WebElement el : optList) {
            if (el.getAttribute("selected") != null) {
                return el.getText();
            }
        }
        return null;
    }

    public GroupUpdatePage gotoRestore() {
        return goto_("Restore");
    }

    public boolean isButtonActive(String id) {
        return !driver.findElement(By.id(id)).getAttribute("class").equals("button_disabled");
    }

    public void filterRecordsCheckbox() {
        driver.findElement(By.id("tblTree")).findElement(By.tagName("input")).click();
    }

    public enum Left {
        VIEW("View"), IMPORT("Import"), NEW("New");
        private String value;

        Left(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
