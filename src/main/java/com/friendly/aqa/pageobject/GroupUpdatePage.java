package com.friendly.aqa.pageobject;

import com.friendly.aqa.utils.Table;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
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
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;

public class GroupUpdatePage extends BasePage {
    private final static Logger LOGGER = Logger.getLogger(GroupUpdatePage.class);

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

    @FindBy(name = "btnEditView$btn")
    private WebElement editGroupButton;

    @FindBy(name = "btnNewView$btn")
    private WebElement createGroupButton;

    @FindBy(name = "btnShowDevices$btn")
    private WebElement showList;

    @FindBy(name = "btnBrowse$btn")
    private WebElement browseButton;

    @FindBy(name = "btnShowDevices$btn")
    private WebElement showListButton;

//    @FindBy(name = "btnDefaultView$btn")
//    private WebElement resetViewButton;

    @FindBy(id = "ddlPageSizes")
    private WebElement itemsOnPageComboBox;

    @FindBy(id = "lrbImmediately")
    private WebElement immediatelyRadioButton;

    @FindBy(id = "lrbWaitScheduled")
    private WebElement scheduledToRadioButton;

    @FindBy(id = "btnAddFilter_btn")
    private WebElement addFilterButton;

    @FindBy(id = "btnDelFilter_btn")
    private WebElement deleteFilterButton;

    @FindBy(id = "ddlColumns")
    private WebElement selectColumnFilter;

    @FindBy(id = "ddlCondition")
    private WebElement compareSelect;

    @FindBy(css = "input[name^='node']")
    private WebElement filterCreatedCheckBox;

    @FindBy(id = "btnOk_btn")
    private WebElement okButtonPopUp;

    @FindBy(id = "btnCancel_btn")
    private WebElement cancelButtonPopUp;

    @FindBy(id = "ddlTasks")
    private WebElement selectTask;

    @FindBy(id = "btnAddTask_btn")
    private WebElement addTaskButton;

    @FindBy(id = "cbPeriod1")
    private WebElement period1CheckBox;

    @FindBy(id = "cbPeriod2")
    private WebElement period2CheckBox;

    @FindBy(id = "txtFailedMax")
    private WebElement thresholdField;

    @FindBy(id = "lblWait")
    private WebElement waitUntilConnectRadioButton;

    @FindBy(id = "lblPush")
    private WebElement requestToConnectRadioButton;

    @FindBy(id = "cbOnlineOnly")
    private WebElement onlineDevicesCheckBox;

    @FindBy(id = "tmScheduled_ddlHour")
    private WebElement timeHoursSelect;

    @FindBy(id = "tmScheduled_ddlMinute")
    private WebElement timeMinutesSelect;

    @FindBy(id = "tblParameters")
    private WebElement mainTable;

    @FindBy(how = How.ID, using = "tblParameters")
    private List<WebElement> mainTableIsPresent;

    @FindBy(how = How.ID, using = "lblDBError")
    private List<WebElement> noDataFound;

    @FindBy(how = How.ID, using = "tblDevices")
    private List<WebElement> serialNumberTableList;

    @FindBy(how = How.ID, using = "tbl_tasks")
    private List<WebElement> taskTableList;

    @FindBy(id = "fuSerials")
    private WebElement importDevicesField;

    @FindBy(id = "fuImport")
    private WebElement importGuField;

    @FindBy(id = "frmImportFromFile")
    private WebElement importFrame;

    @FindBy(id = "frmPopup2")
    private WebElement conditionFrame;

    @FindBy(id = "calDate_image")
    private WebElement calendarIcon;

    @FindBy(id = "calDate_calendar")
    private WebElement divCalendar;

    @FindBy(id = "tabsSettings_tblTabs")
    private WebElement paramTabsTable;

    @FindBy(id = "UcFirmware1_ddlFileType")
    private WebElement selectFileTypeComboBox;

    @FindBy(id = "ddlFileType")
    private WebElement selectUploadFileTypeComboBox;

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

    @FindBy(id = "UcFirmware1_tbSize")
    private WebElement fileSizeField;

    @FindBy(id = "UcFirmware1_tbDelay")
    private WebElement delayField;

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

    @FindBy(id = "rdCPEReprovision")
    private WebElement reprovisionRadioButton;

    @FindBy(id = "rdCustomRPC")
    private WebElement customRpcRadioButton;

    @FindBy(id = "tbName")
    private WebElement descriptionFileUploadField;

    @FindBy(id = "rdDefaultUpload")
    private WebElement defaultUploadRadioButton;

    @FindBy(id = "lblSelect")
    private WebElement importLabel;

    @FindBy(id = "btnDefaultView_btn")
    private WebElement resetViewButton;


//    @FindBy(id = "tblParamsValue")
//    private WebElement paramTable;

    public void insertImportFile() {
        waitForUpdate();
        importGuField.sendKeys("D:\\Users\\asp4r\\Desktop\\UpdateGroup(5461_22.10.2019 14-40-05).xml");
    }

    public GroupUpdatePage topMenu(TopMenu value) {
        super.topMenu(value);
        switchToFrame(DESKTOP);
        waitForUpdate();
        return this;
    }

    public GroupUpdatePage selectImportDevicesFile() {
        switchToFrame(DESKTOP);
        driver.switchTo().frame(importFrame);
        String inputText = new File(props.getProperty("import_devices_file_path")).getAbsolutePath();
        importDevicesField.sendKeys(inputText);
        driver.switchTo().parentFrame();
        return this;
    }

    public GroupUpdatePage selectImportGuFile() {
        switchToFrame(DESKTOP);
        String inputText = new File(props.getProperty("import_group_update_file_path")).getAbsolutePath();
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

    public GroupUpdatePage assertElementIsPresent(String id) {
        List<WebElement> list = driver.findElements(By.id(id));
        if (list.size() == 0) {
            String warn = "Element with id='" + id + "' not found on the Group Update page";
            LOGGER.warn(warn);
            throw new AssertionError(warn);
        }
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
//                    int start = attr.indexOf(".SelectDate('") + 13;
//                    int end = attr.length() - 2;
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

    public GroupUpdatePage timeHoursSelect(int index) {
        new Select(timeHoursSelect).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage timeMinutesSelect(int index) {
        new Select(timeMinutesSelect).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage selectFileName(int index) {
        new Select(fileNameComboBox).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage selectMethod(int index) {
        new Select(selectMethodComboBox).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage onlineDevicesCheckBox() {
        onlineDevicesCheckBox.click();
        return this;
    }

    public GroupUpdatePage requestToConnectRadioButton() {
        requestToConnectRadioButton.click();
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

    public GroupUpdatePage addNewTask(int index) {
        new Select(selectTask).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage filterCreatedCheckBox() {
        filterCreatedCheckBox.click();
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

    public GroupUpdatePage reprovisionRadioButton() {
        waitForUpdate();
        reprovisionRadioButton.click();
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

    public GroupUpdatePage cancelButtonPopUp() {
        switchToFrame(ROOT);
        cancelButtonPopUp.click();
        switchToFrame(DESKTOP);
        return this;
    }

    public GroupUpdatePage deleteFilter() {
        deleteFilterButton.click();
        return this;
    }

    public GroupUpdatePage addFilter() {
        addFilterButton.click();
        return this;
    }

    public GroupUpdatePage compareSelect(String sendTo) {
        new Select(compareSelect).selectByValue(sendTo);
        return this;
    }

    public GroupUpdatePage compareSelect(int index) {
        new Select(compareSelect).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage selectColumnFilter(String sendTo) {
        new Select(selectColumnFilter).selectByValue(sendTo);
        return this;
    }

    public GroupUpdatePage selectColumnFilter(int index) {
        new Select(selectColumnFilter).selectByIndex(index);
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

    public GroupUpdatePage itemsOnPage(String number) {
        waitForUpdate();
        if (new Select(itemsOnPageComboBox).getFirstSelectedOption().getText().equals(number)) {
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
        new Select(sendToField).selectByValue(sendTo);
        return this;
    }

    public GroupUpdatePage selectSendTo(int index) {
        new Select(sendToField).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage fillName(String name) {
        nameField.sendKeys(name);
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

    public GroupUpdatePage fillFileSize(String fileSize) {
        fileSizeField.sendKeys(fileSize);
        return this;
    }

    public GroupUpdatePage filldelay(String delay) {
        fileSizeField.sendKeys(delay);
        return this;
    }

    public GroupUpdatePage selectManufacturer(String manufacturer) {
        waitForUpdate();
        new Select(manufacturerComboBox).selectByValue(manufacturer);
        return this;
    }

    public GroupUpdatePage selectManufacturer(int index) {
        waitForUpdate();
        new Select(manufacturerComboBox).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage selectManufacturer() {
        return selectManufacturer("tp-link");
    }

    public GroupUpdatePage selectModel(String modelName) {
        waitForUpdate();
        List<WebElement> options = modelComboBox.findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (option.getText().toLowerCase().equals(modelName.toLowerCase())) {
                new Select(modelComboBox).selectByValue(option.getAttribute("value"));
                return this;
            }
        }
        new Select(modelComboBox).selectByValue(modelName);
        return this;
    }

    public GroupUpdatePage selectModel(int index) {
        waitForUpdate();
        new Select(modelComboBox).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage selectModel() {
        waitForUpdate();
        Select modelName = new Select(modelComboBox);
        modelName.selectByIndex(modelName.getOptions().size() - 1);
        return this;
    }

    public GroupUpdatePage globalButtons(GlobalButtons button) {
        clickGlobalButtons(button);
        return this;
    }

    public GroupUpdatePage leftMenu(GroupUpdatePage.Left item) {
        switchToFrame(ROOT);
        leftMenuClick(item.getValue());
        switchToFrame(DESKTOP);
        return this;
    }

    public GroupUpdatePage createGroup() {
        waitForUpdate();
        createGroupButton.click();
        waitForUpdate();
        return this;
    }

    public GroupUpdatePage pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public GroupUpdatePage saveAndActivate(String groupName) {
        globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Completed", groupName, 30)
                .getMainTable()
                .readTasksFromDB(groupName)//TEST
                .clickOn(groupName, 4);
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
        itemsOnPage("200");
        Table table = getMainTable();
        String[] arr = table.getColumn(dropdown);
        Set<String> set = new HashSet<>(Arrays.asList(arr));
        if (dropdown.equals("State") && option.equals("All") && set.size() == 2) {
            return this;
        }
        if (!(set.size() == 1 && set.contains(option))) {
            String warn = "Filtering failed on dropdown '" + dropdown + "'";
            LOGGER.warn(warn);
            throw new AssertionError(warn);
        }
        return this;
    }

    public GroupUpdatePage checkSorting(String column) {
        itemsOnPage("200");
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
            LOGGER.warn(warn);
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
            LOGGER.warn(warn);
            throw new AssertionError(warn);
        }
        return this;
    }

    public GroupUpdatePage addCondition(int rowNumber, String branch, String conditionName, Table.Conditions condition, String value) {
        WebElement button = driver.findElement(By.id("btnAddTaskParameter-" + rowNumber + "_btn"));
        button.click();
//        driver.switchTo().defaultContent();
//        driver.switchTo().frame(conditionFrame);
        Table treeTable = getTable("tblTree", CONDITIONS);
        treeTable.print().clickOn(0, branch);
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

    public GroupUpdatePage resetView() {
        resetViewButton.click();
        waitForUpdate();
        return this;
    }

    public void checkResetView() {
        waitForUpdate();
        resetView();
        if (BROWSER.equals("chrome")){
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
            LOGGER.warn(warn);
            throw new AssertionError(warn);
        }
    }

    public boolean serialNumberTableIsPresent() {
        return serialNumberTableList.size() != 0;
    }
//
//    public boolean mainTableIsPresent() {
//        System.out.println(mainTableIsPresent.size());
//        return mainTableIsPresent.size() == 1;
//    }

    public boolean mainTableIsAbsent() {
        System.out.println(noDataFound.size());
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

    public GroupUpdatePage waitForStatus(String status, String groupName, int timeout) {
        long start = System.currentTimeMillis();
        while (!getMainTable().getCellText(4, groupName, 1).equals(status)) {
            globalButtons(REFRESH);
            if (System.currentTimeMillis() - start > timeout * 1000) {
                throw new AssertionError("Timed out while waiting for status " + status);
            }
        }
        return this;
    }

    public Table goToSetPolicies(String manufacturer, String model, String groupName, String tableId) {
        return goto_(manufacturer, model, groupName, 4)
                .getTable(tableId);
    }

    public Table goToSetPolicies(String groupName, String tableId) {
        return goToSetPolicies("sercomm", "Smart Box TURBO+", groupName, tableId);
    }

    public Table goToSetParameters(String manufacturer, String model, String groupName, String tableId, boolean advancedView) {
        GroupUpdatePage out = goto_(manufacturer, model, groupName, 1);
        if (advancedView) {
            globalButtons(ADVANCED_VIEW);
        }
        return out.getTable(tableId);
    }

    public Table goToSetParameters(String manufacturer, String model, String groupName, String tableId) {
        return goToSetParameters(manufacturer, model, groupName, tableId, false);
    }

    public Table goToSetParameters(String groupName, String tableId) {
        return goToSetParameters("sercomm", "Smart Box TURBO+", groupName, tableId);
    }

    public Table goToSetParameters(String groupName) {
        return goToSetParameters(groupName, "tblParamsValue");
    }

    public Table gotoGetParameter(String groupName) {
        return gotoGetParameter(groupName, "tblParamsValue");
    }

    public Table gotoGetParameter(String groupName, String tableId) {
        return gotoGetParameter("sercomm", "Smart Box TURBO+", groupName, tableId);
    }

    public Table gotoGetParameter(String manufacturer, String model, String groupName, String tableId) {
        return gotoGetParameter(manufacturer, model, groupName, tableId, false);
    }

    public Table gotoGetParameter(String manufacturer, String model, String groupName, String tableId, boolean advancedView) {
        GroupUpdatePage out = goto_(manufacturer, model, groupName, 6);
        if (advancedView) {
            globalButtons(ADVANCED_VIEW);
        }
        return out.getTable(tableId);
    }

    public GroupUpdatePage gotoGetParameter(String manufacturer, String model, String groupName) {
        return goto_(manufacturer, model, groupName, 6);
    }

    public GroupUpdatePage gotoFileDownload(String manufacturer, String model, String groupName) {
        return goto_(manufacturer, model, groupName, 2);
    }

    public GroupUpdatePage gotoFileUpload(String manufacturer, String model, String groupName) {
        return goto_(manufacturer, model, groupName, 5);
    }

    public GroupUpdatePage gotoFileUpload(String groupName) {
        return gotoFileUpload("sercomm", "Smart Box TURBO+", groupName);
    }

    public GroupUpdatePage gotoFileDownload(String groupName) {
        return gotoFileDownload("sercomm", "Smart Box TURBO+", groupName);
    }

    private GroupUpdatePage goto_(String manufacturer, String model, String groupName, int index) {
        topMenu(GROUP_UPDATE);
        return leftMenu(NEW)
                .selectManufacturer(manufacturer.toLowerCase())
                .selectModel(model)
                .fillName(groupName)
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(index)
                .addTaskButton();
    }

    public GroupUpdatePage gotoAction(String manufacturer, String model, String groupName) {
        return goto_(manufacturer, model, groupName, 3);
    }

    public GroupUpdatePage gotoAction(String groupName) {
        return gotoAction("sercomm", "Smart Box TURBO+", groupName);
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
