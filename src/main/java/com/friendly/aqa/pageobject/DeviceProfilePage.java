package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.*;
import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.DataBaseConnector;
import com.friendly.aqa.utils.HttpConnector;
import com.friendly.aqa.utils.Timer;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.util.*;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;
import static com.friendly.aqa.pageobject.DeviceProfilePage.BottomButtons.*;
import static com.friendly.aqa.utils.DataBaseConnector.*;

public class DeviceProfilePage extends BasePage {
    private static final Logger logger = Logger.getLogger(DeviceProfilePage.class);

    @Override
    protected String getLeftMenuCssSelector() {
        return "tr[topmenu='Update Group']";
    }

    @Override
    public String getMainTableId() {
        return "tblItems";
    }

    @Override
    public DeviceProfilePage topMenu(TopMenu value) {
        return (DeviceProfilePage) super.topMenu(value);
    }

    @Override
    public DeviceProfilePage assertTableIsEmpty(String id) {
        return (DeviceProfilePage) super.assertTableIsEmpty(id);
    }

    @Override
    public DeviceProfilePage assertTableHasContent(String tableId) {
        return (DeviceProfilePage) super.assertTableHasContent(tableId);
    }

    @Override
    public DeviceProfilePage assertPresenceOfOptions(String comboBoxId, String... options) {
        return (DeviceProfilePage) super.assertPresenceOfOptions(comboBoxId, options);
    }

    @Override
    public DeviceProfilePage selectManufacturer(String manufacturer) {
        return (DeviceProfilePage) super.selectManufacturer(manufacturer);
    }

    @Override
    public DeviceProfilePage selectBranch(String branch) {
        return (DeviceProfilePage) super.selectBranch(branch);
    }

    @Override
    public DeviceProfilePage selectModel(String modelName) {
        return (DeviceProfilePage) super.selectModel(modelName);
    }

    public DeviceProfilePage presetFilter(String parameter, String value) {
        new DeviceUpdatePage().presetFilter(parameter, value);
        return this;
    }

    @FindBy(id = "ddlManufacturer")
    private WebElement filterManufacturerComboBox;

    @FindBy(id = "frmSubFrame")
    private WebElement subFrame;

    @FindBy(id = "ddlModelName")
    private WebElement filterModelNameComboBox;

    @FindBy(id = "ddlUpdateStatus")
    private WebElement filterProfileStatusComboBox;

    @FindBy(id = "ddlCondType")
    private WebElement selectConditionTypeComboBox;

    @FindBy(id = "ddlFilters")
    private WebElement conditionComboBox;

    @FindBy(id = "ddlCust")
    private WebElement selectUserInfoComboBox;

    @FindBy(id = "ddlInform")
    private WebElement selectInformComboBox;

    @FindBy(id = "rdFullRequest")
    private WebElement fullRequestRadioButton;

    @FindBy(id = "rdNoRequest")
    private WebElement dontRequestRadioButton;

    @FindBy(id = "rdRequiresReprovision")
    private WebElement applyProvisionRadioButton;

    @FindBy(id = "rdNoReprovision")
    private WebElement dontApplyProvisionRadioButton;

    @FindBy(id = "rd8")
    private WebElement minAndMaxRadioButton;

    @FindBy(id = "rd10")
    private WebElement cumulativeEnergyButton;

    @FindBy(id = "btnNewView_btn")
    private WebElement newConditionButton;

    @FindBy(id = "btnAddTask_btn")
    private WebElement addTaskButton;

    @FindBy(id = "rdCust")
    private WebElement userInfoRadioButton;

    @FindBy(id = "rdInfo")
    private WebElement informRadioButton;

    @FindBy(id = "btnEditView_btn")
    private WebElement editConditionButton;

    @FindBy(id = "btnSendUpdate_btn")
    private WebElement saveButton;

    @FindBy(id = "btnCancel_btn")
    private WebElement cancelButton;

    @FindBy(id = "tblParameters")
    private WebElement paramTable;

    @FindBy(id = "tblEvents")
    private WebElement eventsTable;

    @FindBy(id = "tblParamsMonitoring")
    private WebElement monitorTable;

    @FindBy(id = "tblPolicy")
    private WebElement policyTable;

    @FindBy(id = "txtValue")
    private WebElement valueInputField;

    @FindBy(id = "cbSendBackup")
    private WebElement performDeviceCheckbox;

    @FindBy(id = "cbsendBackupForExisting")
    private WebElement applyForNewDeviceCheckbox;

    @FindBy(id = "rdDevice.LocalAgent.Controller.i.SendOnBoardRequest()")
    private WebElement sendOnBoardRequestRadioButton;

    @FindBy(id = "ddlDevice.LocalAgent.Controller.i.SendOnBoardRequest()")
    private WebElement instanceCombobox;


    public DeviceProfilePage setAnyAdvancedParameter() {
        selectAnotherBranch();
        setParameter(1);
        return this;
    }


    public DeviceProfilePage performDeviceCheckbox() {
        performDeviceCheckbox.click();
        pause(1000);
        waitForUpdate();
        return this;
    }

    public DeviceProfilePage applyForNewDeviceCheckbox() {
        applyForNewDeviceCheckbox.click();
        waitForUpdate();
        return this;
    }

    @Override
    public DeviceProfilePage assertElementIsSelected(String id) {
        return (DeviceProfilePage) super.assertElementIsSelected(id);
    }

    public DeviceProfilePage selectMainTab(String tab) {//TODO something...
        try {
            new Table(mainTabTable).clickOn(tab);
            pause(1000);
        } catch (AssertionError e) {
            pause(1000);
            new Table(mainTabTable).clickOn(tab);
            pause(1000);
        }
        waitForUpdate();
        return this;
    }

    @Override
    public DeviceProfilePage selectTab(String tab) {
        return (DeviceProfilePage) super.selectTab(tab);
    }

    public DeviceProfilePage selectEventTab(String tab) {
        return selectTab(tab, new Table("tabsEventSettings_tblTabs"));
    }

    @Override
    public DeviceProfilePage selectTab(String tab, Table tabTable) {
        return (DeviceProfilePage) super.selectTab(tab, tabTable);
    }

    public DeviceProfilePage userInfoRadioButton() {
        userInfoRadioButton.click();
        return this;
    }

    public DeviceProfilePage informRadioButton() {
        informRadioButton.click();
        return this;
    }

    public DeviceProfilePage fullRequestRadioButton() {
        waitForUpdate();
        fullRequestRadioButton.click();
        return this;
    }

    public DeviceProfilePage newConditionButton() {
        return newConditionButton(false);
    }

    public DeviceProfilePage newConditionButton(boolean ignoreSaveButtonState) {
        waitForUpdate();
        deleteConditionIfExists();
        if (!ignoreSaveButtonState) {
            waitUntilButtonIsEnabled(SAVE);
        }
        newConditionButton.click();
        return this;
    }

    private void deleteConditionIfExists() {
        conditionComboBox.click();
        List<String> options = getOptionList(conditionComboBox);
        String testName = BaseTestCase.getTestName();
        if (options.contains(testName)) {
            String filterId = new Select(conditionComboBox).getOptions().get(options.indexOf(testName)).getAttribute("value");
            String collisionProfileId = DataBaseConnector.getValue("SELECT profile_id FROM ftacs.profile_filter WHERE filter_id='" + filterId + "'");
            System.out.println("DPP:253 - Condition name already exists! Checking for existing profile... " + collisionProfileId);
            deleteProfileByApiRequest(collisionProfileId);
            selectComboBox(conditionComboBox, testName);
            editConditionButton();
            inputText(testName, DELETE_CONDITION);
            bottomMenu(DELETE_CONDITION);
            okButtonPopUp();
        }
        waitForUpdate();
    }

    public DeviceProfilePage dontRequestRadioButton() {
        dontRequestRadioButton.click();
        return this;
    }

    public DeviceProfilePage applyProvisionRadioButton() {
        applyProvisionRadioButton.click();
        return this;
    }

    public DeviceProfilePage dontApplyProvisionRadioButton() {
        dontApplyProvisionRadioButton.click();
        return this;
    }

    public DeviceProfilePage minAndMaxRadioButton() {
        minAndMaxRadioButton.click();
        return this;
    }

    public DeviceProfilePage cumulativeEnergyButton() {
        cumulativeEnergyButton.click();
        return this;
    }

    public DeviceProfilePage editConditionButton() {
        editConditionButton.click();
        return this;
    }

    public DeviceProfilePage sendOnBoardRequestRadioButton() {
        sendOnBoardRequestRadioButton.click();
        return this;
    }

    public void saveButton() {
        clickButton(saveButton);
    }

    @Override
    public DeviceProfilePage setEvent(Event event) {
        return (DeviceProfilePage) super.setEvent(event);
    }

    @Override
    public DeviceProfilePage setEvent(Event event, boolean addTask) {
        return (DeviceProfilePage) super.setEvent(event, addTask);
    }

    @Override
    public DeviceProfilePage setEvents(int amount, Event event) {
        return (DeviceProfilePage) super.setEvents(amount, event);
    }

    @Override
    public DeviceProfilePage disableAllEvents() {
        return (DeviceProfilePage) super.disableAllEvents();

    }

    @Override
    public DeviceProfilePage setParametersMonitor(Condition condition) {
        return (DeviceProfilePage) super.setParametersMonitor(condition);
    }

    @Override
    public DeviceProfilePage setParametersMonitor(Condition condition, boolean addTask) {
        return (DeviceProfilePage) super.setParametersMonitor(condition, addTask);
    }

    @Override
    public DeviceProfilePage resetErrors() {//
        return (DeviceProfilePage) super.resetErrors();
    }

    @Override
    public DeviceProfilePage disableRadiobutton() {//
        return (DeviceProfilePage) super.disableRadiobutton();
    }

    @Override
    public DeviceProfilePage radioRegistrationUpdateTrigger() {//
        return (DeviceProfilePage) super.radioRegistrationUpdateTrigger();
    }

    @Override
    public DeviceProfilePage radioStartOrReset() {//
        return (DeviceProfilePage) super.radioStartOrReset();
    }

    public DeviceProfilePage editTask(String eventName) {
        Table table = new Table("tblEvents");
        table.clickOn(table.getRowNumberByText(0, eventName), 4);
        return this;
    }

    public DeviceProfilePage expandEvents() {
        driver.findElement(By.id("imgSpoilerEvents")).click();
        return this;
    }

    public DeviceProfilePage expandParametersMonitor() {
        driver.findElement(By.id("imgSpoilerParametersMonitoring")).click();
        return this;
    }

    public DeviceProfilePage expandPolicy() {
        driver.findElement(By.id("imgSpoilerPolicy")).click();
        return this;
    }

    public DeviceProfilePage setDefaultPeriodic() {
        return setParameter(new Table(paramTable), "Device.ManagementServer.PeriodicInformInterval", "60", true);
    }

    public DeviceProfilePage setDefaultPeriodic(boolean addToCheck) {
        Table table = new Table(paramTable);
        String[] names = table.getColumn(0);
        for (String name : names) {
            if (name.endsWith("PeriodicInformInterval")) {
                return setParameter(table, name, "60", addToCheck);
            }
        }
        throw new AssertionError("Parameter 'PeriodicInformInterval' not found!");
    }

    public DeviceProfilePage setParameter(String paramName, String value) {
        return setParameter(new Table(paramTable), paramName, value, true);
    }

    private DeviceProfilePage setParameter(Table table, String paramName, String value, boolean addToCheck) {
        int row = table.getRowNumberByText(paramName);
        if (parameterMap == null) {
            parameterMap = new HashMap<>();
        }
        WebElement box = table.getInput(row, 0);
        if (!box.isSelected()) {
            box.click();
            waitForUpdate();
        }
        String hint = table.getHint(row);
        WebElement input = null;
        WebElement select = null;
        setImplicitlyWait(0);
        try {
            input = table.getInput(row, 1);
        } catch (NoSuchElementException e) {
            select = table.getSelect(row, 1);
        } finally {
            setDefaultImplicitlyWait();
        }
        if (input != null) {
            if (input.getAttribute("type").equals("checkbox")) {
                if (/*!input.isSelected() && */value.isEmpty()) {
                    input.click();
//                waitForUpdate();
                }
                value = "";
            } else if (!input.getAttribute("value").equals(value)) {
                input.clear();
                input.sendKeys(value + " ");
                waitForUpdate();
                input.sendKeys(Keys.BACK_SPACE);
                waitForUpdate();
            }
        } else if (select != null) {
            selectComboBox(select, value);
        }
        if (addToCheck) {
            parameterMap.put(hint, value);
        }
        return this;
    }

    public DeviceProfilePage setParameter(int amount) {
        setParameter(null, amount);
        return this;
    }

    public DeviceProfilePage setParameter(String tab, int amount) {
        return setParameter(tab, amount, true);
    }

    private DeviceProfilePage setParameter(String tab, int amount, boolean setValue) {
        if (tab != null) {
            waitForUpdate();
            addSummaryParameters();
            selectMainTab("Parameters");
            selectTab(tab);
        }
        Table table = new Table(paramTable);
        String[] names = table.getColumn(0);
        for (int i = 0; i < Math.min(Math.abs(amount), names.length); i++) {
            String hint = table.getHint(i + 1);
            WebElement input = null;
            WebElement select = null;
            setImplicitlyWait(0);
            List<WebElement> inputList = table.getCellWebElement(i + 1, 1).findElements(By.tagName("input"));
            if (inputList.size() > 0) {
                input = inputList.get(0);
            } else {
                List<WebElement> selectList = table.getCellWebElement(i + 1, 1).findElements(By.tagName("select"));
                if (selectList.size() > 0) {
                    select = selectList.get(0);
                }
            }
            setDefaultImplicitlyWait();
            String value = "";
            if (input != null) {
                if (input.getAttribute("type").equals("text")) {
                    String currentValue = input.getAttribute("value");
                    if (setValue) {
                        value = generateValue(hint, currentValue);
                    }
                } else if (!setValue) {
                    value = "x";
                }
            } else if (select != null) {
                String selected = getSelectedOption(select);
                if (setValue) {
                    for (String opt : getOptionList(select)) {
                        if (!opt.equals(selected)) {
                            value = opt;
                            break;
                        }
                    }
                } else {
                    value = selected;
                }
            }
            setParameter(table, names[i], value, true);
            table.clickOn(0, 0);
            waitForUpdate();
        }
        return this;
    }

    public DeviceProfilePage setAnotherTabParameter(int amount, boolean setValue) {
        Table tabTable = getTabTable();
        if (tabTable.getTableSize()[1] < 5) {
            tabTable.clickOn(1, 1);
        } else {
            tabTable.clickOn(1, 2);
        }
        waitForUpdate();
        return setParameter(null, amount, setValue);
    }

    public DeviceProfilePage addSummaryParameters() {
        if (parameterMap == null) {
            parameterMap = new HashMap<>();
        }
        waitForUpdate();
        selectMainTab("Summary");
        Table table = new Table(paramTable);
        String[] names = table.getColumn(0);
        setImplicitlyWait(0);
        for (int i = 0; i < names.length; i++) {
            if (table.getCellWebElement(i + 1, 1).findElements(By.tagName("input")).size() > 0) {
                parameterMap.put(names[i], table.getInputText(i + 1, 1));
            } else {
                parameterMap.put(names[i], getSelectedOption(table.getSelect(i + 1, 1)));
            }
        }
        setDefaultImplicitlyWait();
        return this;
    }

    public DeviceProfilePage setParameter(String paramName, ParameterType option, String value) {
        return (DeviceProfilePage) setParameter(new Table("tblParamsValue"), paramName, option, value);
    }

    private void validateAddedTasks(Table table, String eventName) {
        pause(1000);
        int row = eventName == null ? 1 : table.getRowNumberByText(eventName);
        table.clickOn(row, -1);
        switchToFrame(POPUP);
        try {
            super.validateAddedTasks();
        } catch (AssertionError e) {
            pause(1000);
            super.validateAddedTasks();
        }
        cancelButton.click();
    }

    public void validateAddedMonitorTasks() {
        validateAddedTasks(new Table("tblParamsMonitoring"), null);
    }

    public void validateAddedEventTasks(String eventName) {
        validateAddedTasks(new Table("tblEvents"), eventName);
    }

    public void validateAddedMonitorTask(String parameter, String value) {
        validateAddedTask(new Table("tblParamsMonitoring"), null, parameter, value);
    }

    public void validateAddedEventTask(String eventName, String parameter, String value) {
        validateAddedTask(new Table("tblEvents"), eventName, parameter, value);
    }

    private void validateAddedTask(Table table, String name, String parameter, String value) {
        int row = name == null ? 1 : table.getRowNumberByText(name);
        table.clickOn(row, -1);
        switchToFrame(POPUP);
        super.validateAddedTask(parameter, value);
        cancelButton.click();
    }

    public void validateAddedEventTask(String eventName, String taskName) {
        pause(1000);
        validateAddedTask(new Table("tblEvents"), eventName, taskName);
    }

    public void validateAddedMonitorTask(String taskName) {
        pause(1000);
        validateAddedTask(new Table("tblParamsMonitoring"), null, taskName);
    }

    private void validateAddedTask(Table table, String name, String taskName) {
        pause(1000);
        int row = name == null ? 1 : table.getRowNumberByText(name);
        table.clickOn(row, -1);
        switchToFrame(POPUP);
        table = new Table("tblTasks");
        try {
            table.assertPresenceOfTask(taskName);
        } catch (AssertionError e) {
            pause(1000);
            table.assertPresenceOfParameter(taskName);
        }
    }

    public void validateAddedEventAction(String eventName, String parameter, String value) {
        validateAddedAction(new Table("tblEvents"), eventName, parameter, value);
    }

    public void validateAddedMonitorAction(String eventName, String parameter, String value) {
        validateAddedAction(new Table("tblParamsMonitoring"), eventName, parameter, value);
    }

    private void validateAddedAction(Table table, String eventName, String parameter, String value) {
        int row = eventName == null ? 1 : table.getRowNumberByText(eventName);
        table.clickOn(row, -1);
        switchToFrame(POPUP);
        super.validateAddedTask("tblTasks", parameter, value, 1);
    }

    @Override
    public DeviceProfilePage validateParametersMonitor() {
        return (DeviceProfilePage) super.validateParametersMonitor();
    }

    @Override
    public DeviceProfilePage validateEvents() {
        return (DeviceProfilePage) super.validateEvents();
    }

    public void validatePolicy() {
        Table table = new Table("tblPolicy");
        String[] names = table.getColumn(0);
        List<String> notifyList = Arrays.asList("Default", "Off", "Passive", "Active");
        List<String> accessList = Arrays.asList("Default", "ACS only", "All");
        String[] notifyAnswer = {"", "Notification=Off ", "Notification=Passive ", "Notification=Active "};
        String[] accessAnswer = {"", "Access=AcsOnly", "Access=All"};
        for (int i = 1; i < table.getTableSize()[0]; i++) {
            String name = names[i - 1];
            WebElement notifySelect = table.getSelect(i, 1);
            WebElement accessSelect = table.getSelect(i, 2);
            String notification = notifySelect == null ? "" : getSelectedOption(notifySelect);
            String access = accessSelect == null ? "" : getSelectedOption(accessSelect);
            if ((notification.equals("Default") || notification.isEmpty()) && (access.equals("Default") || access.isEmpty())) {
                continue;
            }
            String result = "";
            if (!notification.isEmpty()) {
                result += notifyAnswer[notifyList.indexOf(notification)];
            }
            if (!access.isEmpty()) {
                result += accessAnswer[accessList.indexOf(access)];
            }
            assertEquals(result, parameterMap.get(name), "Unexpected policy for parameter '" + name + '\'');
            parameterMap.remove(name);
        }
        assertTrue(parameterMap.isEmpty(), "Cannot find policy for following parameters:" + parameterMap);
    }

    public DeviceProfilePage addTask(String task) {
        switchToFrame(POPUP);
        waitUntilElementIsDisplayed(addTaskButton);
        selectComboBox(selectTask, task);
        clickButton(addTaskButton);
        return this;
    }

    public DeviceProfilePage deleteTask(String taskName) {
        switchToFrame(POPUP);
        selectItem(new Table("tblTasks"), taskName, 1);
        clickOn("btnDelete_btn");
        return this;
    }

    public DeviceProfilePage assertTaskIsAbsent(String taskName) {
        waitForUpdate();
        if (elementIsAbsent("tblTasks") || !new Table("tblTasks").contains(taskName)) {
            return this;
        }
        String warn = "Task is still present on page!";
        logger.error(warn);
        throw new AssertionError(warn);
    }

    public DeviceProfilePage downloadManualImageFile(String fileType) {
        if (parameterMap == null) {
            parameterMap = new HashMap<>();
        }
        switchToFrame(SUB_FRAME);
        selectDownloadFileType(fileType);
        waitForUpdate();
        manualRadioButton();
        fillUrl();
//        fillUsername();
//        fillPassword();
        saveButton();
        switchToPreviousFrame();
        parameterMap.put(fileType, getProps().getProperty("ftp_config_file_url"));
        return this;
    }

    public DeviceProfilePage downloadFromListFile(String fileType) {
        if (parameterMap == null) {
            parameterMap = new HashMap<>();
        }
        switchToFrame(SUB_FRAME);
        selectDownloadFileType(fileType);
        waitForUpdate();
        selectFromListRadioButton();
        List<String> optList = getOptionList(fileNameComboBox);
        String lastOpt = optList.get(optList.size() - 1);
        selectComboBox(fileNameComboBox, lastOpt);
        saveButton();
        switchToPreviousFrame();
        parameterMap.put(fileType, lastOpt);
        return this;
    }

    public void validateDownloadFile() {
        switchToFrame(SUB_FRAME);
        Table table = new Table("tblFirmwares");
        String fileType = new ArrayList<>(parameterMap.keySet()).get(0);
        assertEquals(table.getCellText(1, 1), fileType);
        table.assertEndsWith(1, 2, parameterMap.get(fileType));
    }

    public DeviceProfilePage selectCondition(int index) {
        new Select(conditionComboBox).selectByIndex(index);
        return this;
    }

    public DeviceProfilePage selectInstance(String value) {
        selectComboBox(instanceCombobox, value);
        return this;
    }

    public DeviceProfilePage selectUserInfoComboBox(String info) {
        selectComboBox(selectUserInfoComboBox, info);
        return this;
    }

    public DeviceProfilePage selectInformComboBox(String info) {
        selectComboBox(selectInformComboBox, info);
        return this;
    }

    public DeviceProfilePage selectConditionTypeComboBox(String condition) {
        selectComboBox(selectConditionTypeComboBox, condition);
        return this;
    }

    public DeviceProfilePage assertButtonIsEnabled(boolean expectedActive, String id) {
        return (DeviceProfilePage) super.assertButtonIsEnabled(expectedActive, id);
    }

    public DeviceProfilePage validateParameter(String paramName, String value) {
        waitForUpdate();
        Table paramTbl = new Table(paramTable);
        int row = paramTbl.getRowsWithText(paramName).get(0);
        WebElement input = paramTbl.getCellWebElement(row, 1).findElement(By.tagName("input"));
        String actual = input.getAttribute("value");
        if (actual.equals(value)) {
            return this;
        }
        String warn = "The value of the parameter '" + paramName + "' doesn't match the declared (" +
                "expected to find '" + value + "', but find '" + actual + "')";
        logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
        throw new AssertionError(warn);
    }

    public void validateParameters() {
        waitForUpdate();
        Table table = new Table(paramTable);
        String[] names = table.getColumn(0);
        for (int i = 0; i < names.length; i++) {
            String paramName = names[i];
            String actual;
            WebElement cell = table.getCellWebElement(i + 1, 1);
            setImplicitlyWait(0);
            if (cell.findElements(By.tagName("input")).size() > 0) {
                actual = table.getInputText(i + 1, 1);
            } else {
                actual = getSelectedOption(cell.findElement(By.tagName("select")));
            }
            setDefaultImplicitlyWait();
            String expected = parameterMap.get(paramName);
            if (expected.equals(actual)) {
                parameterMap.remove(paramName);
            } else {
                String warn = "The value of the parameter '" + paramName + "' doesn't match the declared (" +
                        "expected to find '" + expected + "', but find '" + actual + "')";
                logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
                throw new AssertionError(warn);
            }
        }
    }

    public DeviceProfilePage assertMainPageIsDisplayed() {
        try {
            boolean manufacturerComboBox = filterManufacturerComboBox.isDisplayed() && filterManufacturerComboBox.isEnabled();
            boolean modelNameComboBox = filterModelNameComboBox.isDisplayed() && filterModelNameComboBox.isEnabled();
            boolean viewComboBox = filterProfileStatusComboBox.isDisplayed() && filterProfileStatusComboBox.isEnabled();
            boolean resetViewBtn = resetViewButton.isDisplayed() && resetViewButton.isEnabled();
            if (viewComboBox && manufacturerComboBox && modelNameComboBox && resetViewBtn) {
                return this;
            }
        } catch (NoSuchElementException e) {
            logger.warn('(' + BaseTestCase.getTestName() + ')' + e.getMessage());
        }
        String warn = "One or more elements not found on Device Profile tab main page";
        logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
        throw new AssertionError(warn);
    }

    public DeviceProfilePage addDeviceWithoutTemplate() {
        return (DeviceProfilePage) super.addDeviceWithoutTemplate();
    }

    public DeviceProfilePage assertProfileIsPresent(boolean isExpected) {
        return assertProfileIsPresent(isExpected, selectedName);
    }

    public DeviceProfilePage assertProfileIsPresent(boolean isExpected, String name) {
        setDefaultImplicitlyWait();
        Table table = getMainTable();
        if (table.contains(name) != isExpected) {
            String warn = "Unexpected profile presence (expected to find: " + isExpected + ")";
            logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
            throw new AssertionError(warn);
        }
        return this;
    }

    public DeviceProfilePage assertProfileIsActive(boolean isActive) {
        return assertProfileIsActive(isActive, selectedName);
    }

    public DeviceProfilePage assertProfileIsActive(boolean isActive, String profileName) {
        waitForUpdate();
        Table table = getMainTable();
        int row = table.getRowNumberByText(profileName);
        int col = table.getColumnNumber(0, "State");
        boolean actualState = table.getCellText(row, col).equals("Active");
        if (actualState == isActive) {
            return this;
        }
        String warn = "Profile '" + profileName + "' has unexpected state (expected:'" + isActive + "', but found:'" + actualState + "')!";
        logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
        throw new AssertionError(warn);
    }

    public DeviceProfilePage selectFilterItem(int itemNum) {
        clickOnTable("tblItems", itemNum, 0, 0);
        return this;
    }

    @Override
    public DeviceProfilePage deleteFilter() {
        return (DeviceProfilePage) super.deleteFilter();
    }

    public DeviceProfilePage selectManufacturer() {
        return selectManufacturer(getManufacturer());
    }

    public DeviceProfilePage selectModel() {
        return selectModel(getModelName());
    }

    public DeviceProfilePage selectProfileStatus(String status) {
        selectComboBox(filterProfileStatusComboBox, status);
        waitForUpdate();
        return this;
    }

    public DeviceProfilePage enterIntoProfile(String profileName) {
        try {
            enterIntoItem(profileName);
        } catch (NoSuchElementException e) {
            System.out.println("DPP:873 - ***********retry to find OK button...***************");
            logger.warn("DPP:873 - ***********retry to find OK button...***************");
            okButtonPopUp();
            enterIntoItem(profileName);
        }
        return this;
    }

    public DeviceProfilePage enterIntoProfile() {
        return enterIntoProfile(BaseTestCase.getTestName());
    }

    public DeviceProfilePage enterIntoAnyProfile() {
        getMainTable().clickOn(1, 1);
        return this;
    }

    public void deleteAllProfiles() {
        long start = System.currentTimeMillis();
        Set<String> idSet = DataBaseConnector.getProfileSet();
        for (String id : idSet) {
            deleteProfileByApiRequest(id);
        }
        System.out.println(idSet.size() + " profile(s) for device '" + getDevice(getSerial())[1] + "' removed within " + (System.currentTimeMillis() - start) + " ms");
    }

    public void deleteProfileByApiRequest(String id) {
        String response = "empty";
        try {
            response = HttpConnector.sendPostRequest("http://95.217.85.220/CpeAdmin/CpeService.asmx/DeleteProfile", "{\"confIdHolder\":\"" + id + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.equals("{\"d\":true}")) {
            System.out.println("DPP:896 - Profile deleting failed/not found!");
        }
    }

    public void validateFilteringByManufacturer() {
        List<String> optionList = getOptionList(filterManufacturerComboBox);
        optionList.remove("All");
        selectModel("All");
        selectProfileStatus("All");
        optionList.forEach(option -> validateFiltering(0, option));
    }

    public void validateFilteringByModelName() {
        List<String> optionList = getOptionList(filterModelNameComboBox);
        optionList.remove("All");
        selectManufacturer("All");
        selectProfileStatus("All");
        optionList.forEach(option -> validateFiltering(1, option));
    }

    public void validateFilteringByStatus() {
        List<String> optionList = getOptionList(filterProfileStatusComboBox);
        optionList.remove("All");
        selectManufacturer("All");
        selectModel("All");
        optionList.forEach(option -> validateFiltering(2, option));
    }

    private void validateFiltering(int comboBox, String filter) { //0 - Manufacturer; 1 - Model Name; 2 - Profile status.
        Set<String> dbNameSet;
        Set<String> columnSet = new HashSet<>();
        Table table = null;
        if (comboBox == 0) {
            selectManufacturer(filter);
            dbNameSet = getDeviceProfileNameSetByManufacturer(filter);
            if (elementIsPresent("tblItems")) {
                table = getMainTable();
                columnSet.addAll(Arrays.asList(table.getColumn("Manufacturer")));
            }
        } else if (comboBox == 1) {
            selectModel(filter);
            dbNameSet = getDeviceProfileNameSetByModelName(filter);
            if (elementIsPresent("tblItems")) {
                table = getMainTable();
                columnSet.addAll(Arrays.asList(table.getColumn("Model name")));
            }
        } else {
            selectProfileStatus(filter);
            dbNameSet = getDeviceProfileNameSetByStatus(filter);
            if (elementIsPresent("tblItems")) {
                table = getMainTable();
                columnSet.addAll(Arrays.asList(table.getColumn("State")));
            }
        }
        if (columnSet.size() > 1) {
            throw new AssertionError("Filtered column has more than one value!");
        }
        if (elementIsPresent("btnPager2")) {
            selectComboBox(itemsOnPageComboBox, "200");
            waitForUpdate();
        }
        String[] names = table == null ? new String[0] : table.getColumn("Name");
        Set<String> webNameSet = new HashSet<>(Arrays.asList(names)); // TODO: Проверить что элементов не больше 1!!!!
        if (elementIsAbsent("btnPager2")) {
            dbNameSet.removeAll(webNameSet);
            if (dbNameSet.size() == 0) {
                return;
            }
        } else if (webNameSet.removeAll(dbNameSet) && webNameSet.size() == 0) {
            return;
        }
        System.out.println("!!!:" + webNameSet);
        String warn = "Filtering by " + (comboBox == 0 ? "manufacturer" : comboBox == 1 ? "model name" : "profile status") + " failed!";
        logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
        throw new AssertionError(warn);
    }

    public void getExport() {
        Table table = getMainTable();
        List<Integer> activeList = table.getRowsWithText("Active");
        List<Integer> modelList = table.getRowsWithText(getDevice(getSerial())[1]);
        modelList.retainAll(activeList);
        if (modelList.size() < 1) {
            String warn = "There is no active custom profile to export for current device!";
            logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
            throw new AssertionError(warn);
        }
        int row = modelList.get(0);
        int col = table.getColumnNumber(0, "Name");
        String item = table.getCellText(row, col);
        int id = getDeviceProfileIdByName(item);
        String link = props.getProperty("ui_url") + "/CPEprofile/Export.aspx?configId=" + id;
        try {
            assertTrue(HttpConnector.sendGetRequest(link).contains("<Name>" + item + "</Name>"));
        } catch (IOException e) {
            String warn = "Download export file failed!";
            logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
            throw new AssertionError(warn);
        }
    }

    @Override
    public DeviceProfilePage selectItem(String text) {
        return (DeviceProfilePage) super.selectItem(text);
    }

    @Override
    public DeviceProfilePage selectItem(String text, int startFromRow) {
        return (DeviceProfilePage) super.selectItem(text, startFromRow);
    }

    @Override
    public DeviceProfilePage assertColumnHasSingleValue(String column, String value) {
        return (DeviceProfilePage) super.assertColumnHasSingleValue(column, value);
    }

    @Override
    public DeviceProfilePage assertColumnContainsValue(String column, String value) {
        return (DeviceProfilePage) super.assertColumnContainsValue(column, value);
    }

    @Override
    public DeviceProfilePage okButtonPopUp() {
        return (DeviceProfilePage) super.okButtonPopUp();
    }

    @Override
    public DeviceProfilePage assertButtonsAreEnabled(boolean enabled, IBottomButtons... buttons) {
        return (DeviceProfilePage) super.assertButtonsAreEnabled(enabled, buttons);
    }

    @Override
    public DeviceProfilePage fillName() {
        switchToFrame(DESKTOP);
        return inputText(BaseTestCase.getTestName(), SAVE_AND_ACTIVATE);
    }

    public DeviceProfilePage fillName(boolean waitForSaveButtonIsActive) {
        if (waitForSaveButtonIsActive) {
            return inputText(BaseTestCase.getTestName(), SAVE_AND_ACTIVATE);
        }
        nameField.sendKeys(BaseTestCase.getTestName());
        return this;
    }

    @Override
    public DeviceProfilePage rebootRadioButton() {
        return (DeviceProfilePage) super.rebootRadioButton();
    }

    @Override
    public DeviceProfilePage factoryResetRadioButton() {
        return (DeviceProfilePage) super.factoryResetRadioButton();
    }

    @Override
    public DeviceProfilePage reprovisionRadioButton() {
        return (DeviceProfilePage) super.reprovisionRadioButton();
    }

    @Override
    public DeviceProfilePage customRpcRadioButton() {
        return (DeviceProfilePage) super.customRpcRadioButton();
    }

    @Override
    public DeviceProfilePage selectMethod(String value) {
        return (DeviceProfilePage) super.selectMethod(value);
    }

    @Override
    public DeviceProfilePage selectDiagnostic(String value) {
        return (DeviceProfilePage) super.selectDiagnostic(value);
    }

    @Override
    public DeviceProfilePage inputDnsField(String text) {
        return (DeviceProfilePage) super.inputDnsField(text);
    }

    @Override
    public DeviceProfilePage inputHost(String text) {
        return (DeviceProfilePage) super.inputHost(text);
    }


    public DeviceProfilePage editFileEntry() {
        waitForUpdate();
        switchToFrame(SUB_FRAME);
        pause(1000);
//        new Table("tblFirmwares").print();
        clickOnTable("tblFirmwares", 1, 1, 99);
        waitForUpdate();
        return this;
    }

    @Override
    public DeviceProfilePage inputNumOfRepetitions(String text) {
        return (DeviceProfilePage) super.inputNumOfRepetitions(text);
    }

    public DeviceProfilePage fillConditionName() {
        return inputText(BaseTestCase.getTestName(), NEXT);
    }

    private DeviceProfilePage inputText(String text, BottomButtons targetButton) {
        for (int i = 0; i < 10; i++) {
            nameField.clear();
            nameField.sendKeys(text + " ");
            waitForUpdate();
            nameField.sendKeys(Keys.BACK_SPACE);
            waitForUpdate();
            if (isButtonActive(targetButton)) {
                waitForUpdate();
                break;
            }
        }
        return this;
    }

    public DeviceProfilePage fillValue(String value) {
//        waitForUpdate();
        valueInputField.sendKeys(value);
//        waitForUpdate();
        return this;
    }

    @Override
    public DeviceProfilePage pause(long millis) {
        return (DeviceProfilePage) super.pause(millis);
    }

    @Override
    public DeviceProfilePage assertPresenceOfElements(String... ids) {
        return (DeviceProfilePage) super.assertPresenceOfElements(ids);
    }

    @Override
    public DeviceProfilePage addFilter() {
        return (DeviceProfilePage) super.addFilter();
    }

    public DeviceProfilePage assertHasRedBorder(boolean expectedRed, String paramName) {
        Table table = new Table(paramTable);
        int row = table.getRowsWithText(paramName).get(0);
        WebElement input = table.getCellWebElement(row, 1).findElement(By.tagName("input"));
        if (input.getAttribute("style").endsWith("rgb(255, 74, 74);") == expectedRed) {
            return this;
        }
        String warn = "Input field for parameter '" + paramName + "' doesn't have red border!";
        logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
        throw new AssertionError(warn);
    }

    public DeviceProfilePage assertAddTaskButtonIsActive(String eventName, boolean expectedActive) {
        Table table = new Table("tblEvents");
        WebElement input = table.getInput(table.getRowNumberByText(eventName), 4);
        if (input.isEnabled() == expectedActive) {
            return this;
        }
        String warn = "Button 'Add task' has unexpected state (disabled)";
        logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
        throw new AssertionError(warn);
    }

    public DeviceProfilePage validateTargetDevice(boolean isExpected) {
        return validateTargetDevice(isExpected, false);
    }

    public DeviceProfilePage validateTargetDevice(boolean isExpected, boolean advancedView) {
        String path = new ArrayList<>(parameterMap.keySet()).get(0);
        String[] arr = path.split("\\.");
        String param = arr[arr.length - 1];
        String value = parameterMap.get(path);
        return validateTargetDevice(isExpected, param, value, advancedView);
    }

    public DeviceProfilePage validateTargetDevice(boolean isExpected, String parameter, String value) {
        return validateTargetDevice(isExpected, parameter, value, false);
    }

    public DeviceProfilePage validateTargetDevice(boolean isExpected, String parameter, String value, boolean advancedView) {
        DeviceUpdatePage dUPage = new DeviceUpdatePage();
        dUPage
                .topMenu(TopMenu.DEVICE_UPDATE)
                .enterToDevice();
        if (advancedView) {
            dUPage
                    .bottomMenu(DeviceUpdatePage.BottomButtons.REPROVISION)
                    .okButtonPopUp()
                    .leftMenu(DeviceUpdatePage.Left.ADVANCED_VIEW);
        } else {
            dUPage.leftMenu(DeviceUpdatePage.Left.DEVICE_SETTINGS);
            Table tabTable = getTabTable();
            if (tabTable.contains("Management"))
                tabTable.clickOn("Management");
            else {
                tabTable.clickOn("Device");
            }
        }
        boolean textFound = false;
        Timer timer = new Timer(61000);
        while (!timer.timeout()) {
            try {
                Table table = new Table("tblParamsTable");
                int row = table.getRowNumberByText(0, parameter);
                WebElement cell = table.getCellWebElement(row, 1);
                String text = cell.findElement(By.tagName("input")).getAttribute("value");
                if (text.equals(value)) {
                    textFound = true;
                    break;
                }
            } catch (StaleElementReferenceException e) {
                System.out.println("DPP:1177 - StaleElementReferenceException handled");
            }
        }
        if (textFound == isExpected) {
            return this;
        }
        String warn = isExpected ? "Profile has not been applied to the device, but MUST!" : "Profile has been applied to the device, but MUST NOT!";
        logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
        throw new AssertionError(warn);
    }

    public DeviceProfilePage selectTreeObject(boolean clickOnCheckbox) {
        return selectTreeObject(clickOnCheckbox, 0);
    }

    public DeviceProfilePage selectAnotherTreeObject(boolean clickOnCheckbox) {
        return selectTreeObject(clickOnCheckbox, 1);
    }

    @Override
    public DeviceProfilePage selectTreeObject(boolean clickOnCheckbox, int objNum) {
        return (DeviceProfilePage) super.selectTreeObject(clickOnCheckbox, objNum);
    }

    public DeviceProfilePage clickOn(String id) {
        return (DeviceProfilePage) super.clickOn(id);
    }

//    public DeviceProfilePage selectTreeObjectCheckbox(int objNum) {
//        Table table = new Table("tblTree");
//        List<Integer> rows = table.getRowsWithInput(0);
//        table.clickOn(rows.get(objNum), 0, 0);
//        waitForUpdate();
//        return this;
//    }
//
//    public DeviceProfilePage selectTreeObjectCheckbox() {
//        return selectTreeObjectCheckbox(0);
//    }
//
//    public DeviceProfilePage selectAnotherTreeObjectCheckbox() {
//        return selectTreeObjectCheckbox(1);
//    }

    public DeviceProfilePage assertParametersAreSelected(boolean expectedState) {
        Table table = new Table("tblParameters");
        for (int i = 1; i < table.getTableSize()[0]; i++) {
            if (table.getInput(i, 0).isSelected() != expectedState) {
                String warn = "One or more parameter has unexpected state!";
                logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
                throw new AssertionError(warn);
            }
        }
        return this;
    }

    public DeviceProfilePage bottomMenu(BottomButtons button) {
        clickGlobalButtons(button);
        return this;
    }

    public DeviceProfilePage selectCondition(String condition) {
        selectComboBox(conditionComboBox, condition);
        return this;
    }

    @Override
    public DeviceProfilePage selectDownloadFileType(String type) {
        return (DeviceProfilePage) super.selectDownloadFileType(type);
    }

    @Override
    public DeviceProfilePage selectUploadFileType(String type) {
        return (DeviceProfilePage) super.selectUploadFileType(type);
    }

    @Override
    public DeviceProfilePage manuallyUrlRadioButton() {
        return (DeviceProfilePage) super.manuallyUrlRadioButton();
    }

    @Override
    public DeviceProfilePage manualRadioButton() {
        return (DeviceProfilePage) super.manualRadioButton();
    }

    @Override
    public DeviceProfilePage selectFromListRadioButton() {
        return (DeviceProfilePage) super.selectFromListRadioButton();
    }

    @Override
    public DeviceProfilePage fillUrl() {
        return (DeviceProfilePage) super.fillUrl();
    }

    @Override
    public DeviceProfilePage fillUploadUrl() {
        return (DeviceProfilePage) super.fillUploadUrl();
    }

    @Override
    public DeviceProfilePage fillUsername() {
        return (DeviceProfilePage) super.fillUsername();
    }

    @Override
    public DeviceProfilePage fillPassword() {
        return (DeviceProfilePage) super.fillPassword();
    }

    @Override
    public DeviceProfilePage getParameter(int row, int column) {
        return (DeviceProfilePage) super.getParameter(row, column);
    }

    public DeviceProfilePage saveTaskButton() {
        clickButton(driver.findElement(By.id("btnSave_btn")));
        waitForUpdate();
        return this;
    }

    public DeviceProfilePage setTaskPolicy(int scenario) {
        return setPolicy(new Table("tblParamsValue"), scenario);
    }

    public DeviceProfilePage setPolicy(int scenario) {
        return setPolicy(new Table("tblPolicy"), scenario);
    }

    public DeviceProfilePage setPolicy(Table table, int scenario) {
        if (parameterMap == null) {
            parameterMap = new HashMap<>();
        }
        int shift = 1;
        if (scenario == 1) {
            List<Integer> rowsList = table.getRowsWithSelectList(2);
            if (!rowsList.isEmpty()) {
                shift = rowsList.get(0);
            } else {
                Table treeTable = new Table("tblTree");
                String branch = driver.findElement(By.id("divPath")).getText();
                for (int i = 1; i < treeTable.getTableSize()[0]; i++) {
                    treeTable.clickOn(i, 0, 0);
                    waitForUpdate();
                    String newBranch = driver.findElement(By.id("divPath")).getText();
                    if (newBranch.equals(branch)) {
                        continue;
                    }
                    table = new Table("tblPolicy");
                    branch = newBranch;
                    rowsList = table.getRowsWithSelectList(2);
                    if (!rowsList.isEmpty()) {
                        shift = rowsList.get(0);
                        break;
                    }
                }
            }
        }
        int tableSize = table.getTableSize()[0];
        int limit = scenario > 3 || scenario + 1 > tableSize ? tableSize : scenario + shift;
        String[][] all = {{"Off", "Passive", "Active"}, {"Default", "AcsOnly", "All"}};
        for (int i = shift; i < limit; i++) {
            WebElement notification = table.getSelect(i, 1);
            WebElement accessList = table.getSelect(i, 2);
            String result = "";
            waitForUpdate();
            if (scenario == 1) {
                if (accessList != null) {
                    selectComboBox(accessList, "ACS only");
                    result = "Access=AcsOnly";
                }
            } else if (scenario == 2) {
                if (notification != null) {
                    selectComboBox(notification, "Off");
                    result = "Notification=Off ";
                }
            } else if (scenario == 3) {
                if (notification != null) {
                    new Select(notification).selectByIndex(i);
                    result = "Notification=" + all[0][i - 1] + " ";
                    waitForUpdate();
                }
                if (accessList != null) {
                    new Select(accessList).selectByIndex(i - 1);
                    result += (i == 1 ? "" : "Access=" + all[1][i - 1]);
                }
            } else {
                if (notification != null) {
                    selectComboBox(notification, "Active");
                    result = "Notification=Active ";
                    waitForUpdate();
                }
                if (accessList != null) {
                    selectComboBox(accessList, "ACS only");
                    result += "Access=AcsOnly";
                }
            }
            if (result.isEmpty()) {
                String warn = "Cannot complete test on current tab for this device!";
                logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
                throw new AssertionError(warn);
            }
            parameterMap.put(table.getHint(i), result);
        }
        return this;
    }

    public DeviceProfilePage leftMenu(Left item) {
        switchToFrame(DESKTOP);
        String stringTotal = driver.findElement(By.id("pager2_lblCount")).getText();
        int total = Integer.parseInt(stringTotal);
        if (total >= 14) {
            descendingSortByCreateColumn();
        }
        switchToFrame(ROOT);
        Timer timer = new Timer();
        Table table = getTable("tblLeftMenu");
        while (!table.contains(item.value) && !timer.timeout()) {
            table = getTable("tblLeftMenu");
        }
        table.clickOn(item.value);
        waitForUpdate();
        switchToFrame(DESKTOP);
        return this;
    }

    private void descendingSortByCreateColumn() {
        Table table = new Table("tblItems");
        int colNum = table.getColumnNumber(0, "Created");
        boolean descending = table.getCellWebElement(0, colNum).findElement(By.tagName("img")).getAttribute("src").endsWith("down.png");
        if (!descending) {
            table.clickOn(0, colNum);
        }
    }

    public DeviceProfilePage marker(String marker) {
        System.out.println("marker " + marker);
        return this;
    }

    public enum Left {
        VIEW("View"), IMPORT("Import"), NEW("New");
        private final String value;

        Left(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum BottomButtons implements IBottomButtons {

        ACTIVATE("btnActivate_btn"),
        ADVANCED_VIEW("btnAdvView_btn"),
        CANCEL("btnCancel_btn"),
        DEACTIVATE("btnDeactivate_btn"),
        DELETE("btnDelete_btn"),
        DELETE_CONDITION("btnDeleteView_btn"),
        DUPLICATE("btnDuplicate_btn"),
        EDIT("btnEdit_btn"),
        FINISH("btnFinish_btn"),
        NEXT("btnNext_btn"),
        PAUSE("btnPause_btn"),
        REFRESH("btnRefresh_btn"),
        PREVIOUS("btnPrev_btn"),
        SAVE("btnSave_btn"),
        SAVE_AND_ACTIVATE("btnSaveActivate_btn"),
        SIMPLE_VIEW("btnTabView_btn"),
        STOP("btnStop_btn"),
        STOP_WITH_RESET("btnStopWithReset_btn");

        BottomButtons(String id) {
            this.id = id;
        }

        private final String id;

        public String getId() {
            return id;
        }
    }

//    public enum Policy implements IPolicy {
//        //      DEFAULT("Default"),
//        OFF("0"),
//        PASSIVE("1"),
//        ACTIVE("2"),
//        ACS_ONLY("AcsOnly"),
//        ALL("All");
//
//        private final String option;
//
//        public String getOption() {
//            return option;
//        }
//
//        Policy(String option) {
//            this.option = option;
//        }
//    }
}
