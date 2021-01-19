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
    private static final Logger LOGGER = Logger.getLogger(DeviceProfilePage.class);

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

    @FindBy(id = "UcFirmware1_ddlDeliveryMethod")
    private WebElement deliveryComboBox;

    @FindBy(id = "rdFullRequest")
    private WebElement fullRequestRadioButton;

    @FindBy(id = "rdNoRequest")
    private WebElement dontRequestRadioButton;

    @FindBy(id = "rdRequiresReprovision")
    private WebElement applyProvisionRadioButton;

    @FindBy(id = "rdNoReprovision")
    private WebElement dontApplyProvisionRadioButton;

    @FindBy(id = "btnNewView_btn")
    private WebElement newConditionButton;

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
            new Table("tabsMain_tblTabs").clickOn(tab);
            pause(1000);
        } catch (AssertionError e) {
            pause(1000);
            new Table("tabsMain_tblTabs").clickOn(tab);
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
        return newConditionButton(BaseTestCase.getTestName(), ignoreSaveButtonState);
    }

    public DeviceProfilePage newConditionButton(String name, boolean ignoreSaveButtonState) {
        waitForUpdate();
        deleteConditionIfExists(name);
        if (!ignoreSaveButtonState) {
            waitUntilButtonIsEnabled(SAVE);
        }
        newConditionButton.click();
        return this;
    }

    private void deleteConditionIfExists(String testName) {
        conditionComboBox.click();
        List<String> options = getOptionList(conditionComboBox);
        if (options.contains(testName)) {
            String filterId = new Select(conditionComboBox).getOptions().get(options.indexOf(testName)).getAttribute("value");
            String collisionProfileId = DataBaseConnector.getValue("SELECT profile_id FROM ftacs.profile_filter WHERE filter_id='" + filterId + "'");
            if (!collisionProfileId.isEmpty()) {
                System.out.println("DPP:253 - Condition name already exists! trying to find an existing profile #" + collisionProfileId);
                if (deleteProfileById(collisionProfileId)) {
                    System.out.println("Found and deleted!");
                }
            }
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

    public DeviceProfilePage editConditionButton() {
        editConditionButton.click();
        return this;
    }

    public void saveButton() {
        clickButton(saveButton);
    }

    public DeviceProfilePage setEvent(Event event) {
        return (DeviceProfilePage) setEvent(event, new Table("tblEvents"));
    }

    public DeviceProfilePage setEvent(Event event, boolean addTask) {
        return (DeviceProfilePage) setEvent(event, new Table("tblEvents"), addTask);
    }

    public DeviceProfilePage setEvents(int amount, Event event) {
        return (DeviceProfilePage) super.setEvents(amount, event, getTable("tblEvents"));
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

    public DeviceProfilePage createPreconditions() {
        deleteAllProfiles();
        for (int i = 0; i < 2; i++) {
            leftMenu(Left.NEW);
            selectManufacturer();
            selectModel();
            if (paramTable.isDisplayed()) {
                setDefaultPeriodic(false);
            } else {
                selectMainTab("Monitoring");
                setParametersMonitor(Condition.CONTAINS);
            }
            String name = getProtocolPrefix() + "_precondition_" + i;
            fillName(name);
            inputText(name, SAVE_AND_ACTIVATE);
            if (getOptionList(conditionComboBox).size() < 2) {
                newConditionButton(name, true)
                        .inputText(name, NEXT)
                        .bottomMenu(NEXT)
                        .addFilter()
//                        .userInfoRadioButton()
                        .selectUserInfoComboBox("Zip")
                        .selectConditionTypeComboBox("=")
                        .fillValue("61000")
                        .bottomMenu(NEXT)
                        .bottomMenu(FINISH)
                        .okButtonPopUp();
            }
            if (i == 0) {
                bottomMenu(SAVE);
            } else {
                bottomMenu(SAVE_AND_ACTIVATE);
            }
            okButtonPopUp();
            waitForUpdate();
        }
        return this;
    }

    public DeviceProfilePage editTask(String eventName) {
        Table table = new Table("tblEvents");
        table.clickOn(table.getFirstRowWithText(0, eventName), 4);
        return this;
    }

    @Override
    public DeviceProfilePage expandEvents() {
        return (DeviceProfilePage) super.expandEvents();
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
        return setParameter(new Table("tblParameters"), "Device.ManagementServer.PeriodicInformInterval", "60", true);
    }

    public DeviceProfilePage setDefaultPeriodic(boolean addToCheck) {
        Table table = new Table("tblParameters");
        String[] names = table.getColumn(0);
        for (String name : names) {
            if (name.endsWith("PeriodicInformInterval")) {
                return setParameter(table, name, "60", addToCheck);
            }
        }
        throw new AssertionError("Parameter 'PeriodicInformInterval' not found!");
    }

    public DeviceProfilePage setParameter(String paramName, String value) {
        return setParameter(new Table("tblParameters"), paramName, value, true);
    }

    private DeviceProfilePage setParameter(Table table, String paramName, String value, boolean addToCheck) {
        int row = table.getFirstRowWithText(paramName);
        forceCheckboxState(true, table.getInput(row, 0));
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
            getParameterMap().put(hint, value);
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
        Table table = new Table("tblParameters");
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
        waitForUpdate();
        selectMainTab("Summary");
        Table table = new Table("tblParameters");
        String[] names = table.getColumn(0);
        setImplicitlyWait(0);
        for (int i = 0; i < names.length; i++) {
            if (table.getCellWebElement(i + 1, 1).findElements(By.tagName("input")).size() > 0) {
                getParameterMap().put(names[i], table.getInputText(i + 1, 1));
            } else {
                getParameterMap().put(names[i], getSelectedOption(table.getSelect(i + 1, 1)));
            }
        }
        setDefaultImplicitlyWait();
        return this;
    }

    public DeviceProfilePage setParameter(String paramName, ParameterType option, String value) {
        return (DeviceProfilePage) setParameter(new Table("tblParamsValue"), paramName, option, value);
    }

    public void validateAddedMonitorTasks() {
        validateAddedTasks(new Table("tblParamsMonitoring"), null);
    }

    public void validateAddedMonitorTask(String parameter, String value) {
        validateAddedTask(new Table("tblParamsMonitoring"), null, parameter, value);
    }

    public void validateAddedEventTasks(String eventName) {
        validateAddedTasks(new Table("tblEvents"), eventName);
    }

    public void validateAddedEventTask(String eventName, String parameter, String value) {
        validateAddedTask(new Table("tblEvents"), eventName, parameter, value);
    }

    public void validateAddedEventTask(String eventName, String taskName) {
        pause(1000);
        validateAddedTask(new Table("tblEvents"), eventName, taskName);
    }

    public void validateAddedMonitorTask(String taskName) {
        pause(1000);
        validateAddedTask(new Table("tblParamsMonitoring"), null, taskName);
    }

    public void validateAddedEventAction(String eventName, String parameter, String value) {
        validateAddedAction(new Table("tblEvents"), eventName, parameter, value);
    }

    public void validateAddedMonitorAction(String eventName, String parameter, String value) {
        validateAddedAction(new Table("tblParamsMonitoring"), eventName, parameter, value);
    }

    @Override
    public DeviceProfilePage validateParametersMonitor() {
        return (DeviceProfilePage) super.validateParametersMonitor();
    }

    public DeviceProfilePage validateEvents() {
        assertEquals(readEvents("tblEvents"), eventMap, "Events comparison error!");
        return this;
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
            assertEquals(result, getParameterMap().get(name), "Unexpected policy for parameter '" + name + '\'');
            getParameterMap().remove(name);
        }
        assertTrue(getParameterMap().isEmpty(), "Cannot find policy for following parameters:" + getParameterMap());
    }

    @Override
    public DeviceProfilePage addTask(String task) {
        return (DeviceProfilePage) super.addTask(task);
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
        LOGGER.error(warn);
        throw new AssertionError(warn);
    }

    public DeviceProfilePage downloadManually(String fileType) {
        switchToFrame(SUB_FRAME);
        selectDownloadFileType(fileType);
        waitForUpdate();
        manuallyDownloadRadioButton();
        fillDownloadUrl();
        if (elementIsPresent("UcFirmware1_ddlDeliveryMethod")) {
            selectComboBox(deliveryComboBox, "Push");
        }
//        fillUsername();
//        fillPassword();
        saveButton();
        switchToPreviousFrame();
        getParameterMap().put(fileType, getProps().getProperty("file_server"));
        return this;
    }

    public DeviceProfilePage downloadFromListFile(String fileType) {
        switchToFrame(SUB_FRAME);
        selectDownloadFileType(fileType);
        waitForUpdate();
        selectFromListRadioButton();
        if (!fileNameComboBox.isEnabled()) {
            throw new AssertionError("There is no available file to select from list");
        }
        List<String> optList = getOptionList(fileNameComboBox);
        String lastOpt = optList.get(optList.size() - 1);
        selectComboBox(fileNameComboBox, lastOpt);
        if (elementIsPresent("UcFirmware1_ddlDeliveryMethod")) {
            selectComboBox(deliveryComboBox, "Pull");
        }
        saveButton();
        switchToPreviousFrame();
        getParameterMap().put(fileType, lastOpt);
        return this;
    }

    public void validateDownloadFile() {
        switchToFrame(SUB_FRAME);
        Table table = new Table("tblFirmwares");
        String fileType = new ArrayList<>(getParameterMap().keySet()).get(0);
        assertEquals(table.getCellText(1, 1), fileType, "File type entry has unexpected content!");
//        table.assertEndsWith(1, 2, getParameterMap().get(fileType));   //build 156 - this cell is empty
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
        waitUntilButtonIsDisplayed(ADVANCED_VIEW);
        Table paramTbl = new Table("tblParameters");
        int row = paramTbl.getRowsWithText(paramName).get(0);
        WebElement input = paramTbl.getCellWebElement(row, 1).findElement(By.tagName("input"));
        String actual = input.getAttribute("value");
        if (actual.equals(value)) {
            return this;
        }
        String warn = "The value of the parameter '" + paramName + "' doesn't match the declared (" +
                "expected to find '" + value + "', but find '" + actual + "')";
        LOGGER.warn('(' + BaseTestCase.getTestName() + ')' + warn);
        throw new AssertionError(warn);
    }

    public void validateParameters() {
        waitForUpdate();
        Table table = new Table("tblParameters");
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
            String expected = getParameterMap().get(paramName);
            if (expected.equals(actual)) {
                getParameterMap().remove(paramName);
            } else {
                String warn = "The value of the parameter '" + paramName + "' doesn't match the declared (" +
                        "expected to find '" + expected + "', but find '" + actual + "')";
                LOGGER.warn('(' + BaseTestCase.getTestName() + ')' + warn);
                throw new AssertionError(warn);
            }
        }
    }

    public DeviceProfilePage assertMainPageIsDisplayed() {
        assertElementsAreEnabled(filterManufacturerComboBox, filterModelNameComboBox, filterProfileStatusComboBox, resetViewButton);
        return this;
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
            LOGGER.warn('(' + BaseTestCase.getTestName() + ')' + warn);
            throw new AssertionError(warn);
        }
        return this;
    }

    public DeviceProfilePage assertMentionedProfileStateIs(String state) {
        return assertProfileStateIs(state, selectedName);
    }

    public DeviceProfilePage assertCurrentProfileStateIs(String state) {
        return assertProfileStateIs(state, BaseTestCase.getTestName());
    }

    private DeviceProfilePage assertProfileStateIs(String state, String profileName) {
        waitForUpdate();
        Table table = getMainTableWithText(profileName);
        assertEquals(table.getCellText(profileName, "State"), state, "Profile '" + profileName + "' has unexpected state;");
        return this;
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

    private Table getMainTableWithText(String text) {
        return getMainTableWithText(text, "Created");
    }

    public DeviceProfilePage enterIntoProfile(String profileName) {
        Table table = getMainTableWithText(profileName);
        table.clickOn(profileName);
        waitForUpdate();
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
            deleteProfileById(id);
        }
        System.out.println(idSet.size() + " profile(s) for device '" + getDevice(getSerial())[1] + "' removed within " + (System.currentTimeMillis() - start) + " ms");
    }

    public void deleteProfileByNameIfExists(String name) {
        String id = DataBaseConnector.getValue("SELECT id from ftacs.profile WHERE name = '" + name + "';");
        if (!id.isEmpty() && deleteProfileById(id)) {
            System.out.println("Existing profile '" + name + "' has been deleted");
        }
    }

    public boolean deleteProfileById(String id) {
        String response = "empty";
        try {
            response = HttpConnector.sendPostRequest(props.getProperty("ui_url") + "/CpeService.asmx/DeleteProfile", "{\"confIdHolder\":\"" + id + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.equals("{\"d\":true}");
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
            itemsOnPage("200");
            table = getMainTable();
        }
        String[] names = table == null ? new String[0] : table.getColumn("Name");
        Set<String> webNameSet = new HashSet<>(Arrays.asList(names));
        if (elementIsAbsent("btnPager2") && dbNameSet.size() == 0) {
            return;
        }
        if (webNameSet.removeAll(dbNameSet) && webNameSet.size() == 0) {
            return;
        }
        System.out.println("!!!:" + webNameSet);
        String warn = "Filtering by " + (comboBox == 0 ? "manufacturer" : comboBox == 1 ? "model name" : "profile status") + " failed!";
        LOGGER.warn('(' + BaseTestCase.getTestName() + ')' + warn);
        throw new AssertionError(warn);
    }

    public void getExport() {
        Table table = getMainTable();
        List<Integer> activeList = table.getRowsWithText("Active");
        List<Integer> modelList = table.getRowsWithText(getDevice(getSerial())[1]);
        modelList.retainAll(activeList);
        if (modelList.size() < 1) {
            String warn = "There is no active custom profile to export for current device!";
            LOGGER.warn('(' + BaseTestCase.getTestName() + ')' + warn);
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
            LOGGER.warn('(' + BaseTestCase.getTestName() + ')' + warn);
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
        String name = BaseTestCase.getTestName();
        deleteProfileByNameIfExists(name);
        return inputText(name, SAVE_AND_ACTIVATE);
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
    public DeviceProfilePage selectAction(String action) {
        return (DeviceProfilePage) super.selectAction(action);
    }

    @Override
    public DeviceProfilePage selectAction(String action, String instance) {
        return (DeviceProfilePage) super.selectAction(action, instance);
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

    @Override
    public DeviceProfilePage inputNumOfRepetitions(String text) {
        return (DeviceProfilePage) super.inputNumOfRepetitions(text);
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

    public DeviceProfilePage fillConditionName() {
        return inputText(BaseTestCase.getTestName(), NEXT);
    }

    private DeviceProfilePage inputText(String text, BottomButtons targetButton) {
        Timer timer = new Timer();
        while (!timer.timeout()) {
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
    public DeviceProfilePage assertElementsArePresent(String... elementsId) {
        return (DeviceProfilePage) super.assertElementsArePresent(elementsId);
    }

    @Override
    public DeviceProfilePage addFilter() {
        return (DeviceProfilePage) super.addFilter();
    }

    public DeviceProfilePage assertHasRedBorder(boolean expectedRed, String paramName) {
        Table table = new Table("tblParameters");
        int row = table.getRowsWithText(paramName).get(0);
        WebElement input = table.getCellWebElement(row, 1).findElement(By.tagName("input"));
        if (input.getAttribute("style").endsWith("rgb(255, 74, 74);") == expectedRed) {
            return this;
        }
        String warn = "Input field for parameter '" + paramName + "' doesn't have red border!";
        LOGGER.warn('(' + BaseTestCase.getTestName() + ')' + warn);
        throw new AssertionError(warn);
    }

    public DeviceProfilePage assertAddTaskButtonIsActive(String eventName, boolean expectedActive) {
        Table table = new Table("tblEvents");
        WebElement input = table.getInput(eventName, 4);
        if (input.isEnabled() == expectedActive) {
            return this;
        }
        String warn = "Button 'Add task' has unexpected state (disabled)";
        LOGGER.warn('(' + BaseTestCase.getTestName() + ')' + warn);
        throw new AssertionError(warn);
    }

//    public DeviceProfilePage deliveryMethod(String method) {
//        selectComboBox(deliveryComboBox, method);
//        System.out.println("select " + method);
//        return this;
//    }

    public DeviceProfilePage validateApplyingProfile(boolean isExpected) {
        return validateApplyingProfile(isExpected, false);
    }

    public DeviceProfilePage validateApplyingProfile(boolean isExpected, boolean advancedView) {
        String path = new ArrayList<>(getParameterMap().keySet()).get(0);
        String[] arr = path.split("\\.");
        String param = arr[arr.length - 1];
        String value = getParameterMap().get(path);
        return validateApplyingProfile(isExpected, param, value, advancedView);
    }

    public DeviceProfilePage validateApplyingProfile(boolean isExpected, String parameter, String value) {
        return validateApplyingProfile(isExpected, parameter, value, false);
    }

    public DeviceProfilePage validateApplyingProfile(boolean isExpected, String parameter, String value, boolean advancedView) {
        DeviceUpdatePage dUPage = new DeviceUpdatePage();
        dUPage
                .topMenu(TopMenu.DEVICE_UPDATE)
                .openDevice();
        if (advancedView) {
            String path = new ArrayList<>(getParameterMap().keySet()).get(0);
            if (BaseTestCase.getTestName().startsWith("usp")) {
                dUPage
                        .bottomMenu(DeviceUpdatePage.BottomButtons.REPROVISION)
                        .okButtonPopUp();
            }
            dUPage
                    .leftMenu(DeviceUpdatePage.Left.ADVANCED_VIEW)
                    .selectBranch(path.substring(0, path.indexOf(parameter)));
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
                int row = table.getFirstRowWithText(0, parameter);
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
        LOGGER.warn('(' + BaseTestCase.getTestName() + ')' + warn);
        throw new AssertionError(warn);
    }

    public DeviceProfilePage selectTreeObject(boolean clickOnCheckbox) {
        return selectTreeObject(clickOnCheckbox, 1);
    }

    public DeviceProfilePage selectAnotherTreeObject(boolean clickOnCheckbox) {
        return selectTreeObject(clickOnCheckbox, 0);
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
                LOGGER.warn('(' + BaseTestCase.getTestName() + ')' + warn);
                throw new AssertionError(warn);
            }
        }
        return this;
    }

    @Override
    public DeviceProfilePage bottomMenu(IBottomButtons button) {
        clickBottomButton(button);
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
    public DeviceProfilePage manuallyUploadRadioButton() {
        return (DeviceProfilePage) super.manuallyUploadRadioButton();
    }

    @Override
    public DeviceProfilePage manuallyDownloadRadioButton() {
        return (DeviceProfilePage) super.manuallyDownloadRadioButton();
    }

    @Override
    public DeviceProfilePage selectFromListRadioButton() {
        return (DeviceProfilePage) super.selectFromListRadioButton();
    }

    @Override
    public DeviceProfilePage fillDownloadUrl() {
        return (DeviceProfilePage) super.fillDownloadUrl();
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
        clickButton(findElement("btnSave_btn"));
        waitForUpdate();
        return this;
    }

    public DeviceProfilePage setTaskPolicy(int scenario) {
        return (DeviceProfilePage) setPolicy(new Table("tblParamsValue"), scenario);
    }

    public DeviceProfilePage setPolicy(int scenario) {
        return (DeviceProfilePage) setPolicy(new Table("tblPolicy"), scenario);
    }

    @Override
    public DeviceProfilePage leftMenu(ILeft item) {
        return (DeviceProfilePage) super.leftMenu(item);
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

    public enum BottomButtons implements IBottomButtons {

        ACTIVATE("btnActivate_btn"),
        ADVANCED_VIEW("btnAdvView_btn"),
        CANCEL("btnCancel_btn"),
        DEACTIVATE("btnDeactivate_btn"),
        DELETE("btnDelete_btn"),
        DELETE_CONDITION("btnDeleteView_btn"),
        //        DUPLICATE("btnDuplicate_btn"),
//        EDIT("btnEdit_btn"),
        FINISH("btnFinish_btn"),
        NEXT("btnNext_btn"),
        //        PAUSE("btnPause_btn"),
//        REFRESH("btnRefresh_btn"),
//        PREVIOUS("btnPrev_btn"),
        SAVE("btnSave_btn"),
        SAVE_AND_ACTIVATE("btnSaveActivate_btn"),
        SIMPLE_VIEW("btnTabView_btn"),
//        STOP("btnStop_btn"),
        /*STOP_WITH_RESET("btnStopWithReset_btn")*/;

        BottomButtons(String id) {
            this.id = id;
        }

        private final String id;

        public String getId() {
            return id;
        }
    }
}
