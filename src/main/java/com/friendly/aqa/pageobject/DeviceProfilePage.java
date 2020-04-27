package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.*;
import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.DataBaseConnector;
import com.friendly.aqa.utils.HttpConnector;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.util.*;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;
import static com.friendly.aqa.pageobject.DeviceProfilePage.GlobalButtons.*;
import static com.friendly.aqa.utils.DataBaseConnector.*;

public class DeviceProfilePage extends BasePage {
    private static Logger logger = Logger.getLogger(MonitoringPage.class);

    @Override
    protected String getLeftMenuCssSelector() {
        return "tr[topmenu='Update Group']";
    }

    @Override
    public Table getMainTable() {
        return getTable("tblItems");
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
    public DeviceProfilePage assertTableHasContent(String id) {
        return (DeviceProfilePage) super.assertTableHasContent(id);
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

    @FindBy(id = "btnEditView_btn")
    private WebElement editConditionButton;

    @FindBy(id = "btnSendUpdate_btn")
    protected WebElement saveButton;

    @FindBy(id = "tabsMain_tblTabs")
    private WebElement mainTabTable;

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


    @Override
    public DeviceProfilePage assertElementIsSelected(String id) {
        return (DeviceProfilePage) super.assertElementIsSelected(id);
    }

    public DeviceProfilePage selectMainTab(String tab) {
        new Table(mainTabTable).clickOn(tab);
        waitForUpdate();
        return this;
    }

    public DeviceProfilePage selectTab(String tab) {
        getTabTable().clickOn(tab);
        waitForUpdate();
        return this;
    }

    public DeviceProfilePage selectEventTab(String tab) {
        if (!tab.equals("Management") && !tab.equals("Device")) {
            String start;
            try {
                start = new Table("tblTree").getCellText(0, 0);
            }catch (StaleElementReferenceException e){
                start = new Table("tblTree").getCellText(0, 0);
            }
            new Table("tabsEventSettings_tblTabs").clickOn(tab);
            long from = System.currentTimeMillis();
            do {
                waitForUpdate();
            } while (new Table("tblTree").getCellText(0, 0).equals(start) && System.currentTimeMillis() - from < 10000);
        }
        return this;
    }

    public DeviceProfilePage userInfoRadioButton() {
        userInfoRadioButton.click();
        return this;
    }

    public DeviceProfilePage fullRequestRadioButton() {
        fullRequestRadioButton.click();
        return this;
    }

    public DeviceProfilePage newConditionButton() {
        waitForUpdate();
        conditionComboBox.click();
        List<String> options = getOptionList(conditionComboBox);
        String testName = BaseTestCase.getTestName();
        if (options.contains(testName)) {
            String filterId = new Select(conditionComboBox).getOptions().get(options.indexOf(testName)).getAttribute("value");
            String collisionProfileId = DataBaseConnector.getValue("SELECT profile_id FROM ftacs.profile_filter WHERE filter_id='" + filterId + "'");
            System.out.println("deleting existing profile " + collisionProfileId);
            deleteProfileRequestApi(collisionProfileId);
            selectComboBox(conditionComboBox, testName);
            editConditionButton();
            inputText(testName, DELETE_CONDITION);
            globalButtons(DELETE_CONDITION);
            okButtonPopUp();
        }
        waitForUpdate();
        newConditionButton.click();
        return this;
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

    public DeviceProfilePage saveButton() {
        clickButton(saveButton);
        return this;
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

    public DeviceProfilePage setParameter(String paramName, String value) {
        return setParameter(new Table(paramTable), paramName, value);
    }

    public DeviceProfilePage setParameter(Table table, String paramName, String value) {
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
        WebElement field = table.getInput(row, 1);
        if (field.getAttribute("type").equals("checkbox")) {
            if (!field.isSelected() && value.isEmpty()) {
                field.click();
//                waitForUpdate();
            }
            value = "";
        } else if (!field.getAttribute("value").equals(value)) {
            field.clear();
            field.sendKeys(value + " ");
            waitForUpdate();
            field.sendKeys(Keys.BACK_SPACE);
            waitForUpdate();
        }
        parameterMap.put(hint, value);
        return this;
    }

    public DeviceProfilePage setParameter(String tab, int amount) {
        return setParameter(tab, amount, true);
    }

    public DeviceProfilePage setParameter(String tab, int amount, boolean setValue) {
        if (tab != null) {
            waitForUpdate();
            addSummaryParameter();
            selectMainTab("Parameters");
            selectTab(tab);
        }
        Table table = new Table(paramTable);
        String[] names = table.getColumn(0);
        for (int i = 0; i < Math.min(amount, names.length); i++) {
            String hint = table.getHint(i + 1);
            WebElement input = table.getInput(i + 1, 1);
            String value = "";
            if (input.getAttribute("type").equals("text")) {
                if (!input.getAttribute("value").isEmpty() || !setValue) {
                    value = input.getAttribute("value");
                } else {
                    value = generateValue(hint, i + 1);
                }
            } else if (!setValue) {
                value = "x";
            }
            setParameter(table, names[i], value);
            table.clickOn(0, 0);
            waitForUpdate();
        }
        return this;
    }

    public DeviceProfilePage setAnotherTabParameter(int amount, boolean setValue) {
        getTabTable().clickOn(1, 2);
        waitForUpdate();
        return setParameter(null, amount, setValue);
    }

    public DeviceProfilePage addSummaryParameter() {
        if (parameterMap == null) {
            parameterMap = new HashMap<>();
        }
        waitForUpdate();
        selectMainTab("Summary");
        Table table = new Table(paramTable);
        String[] names = table.getColumn(0);
        for (int i = 0; i < names.length; i++) {
            parameterMap.put(names[i], table.getInputText(i + 1, 1));
        }
        return this;
    }

    public DeviceProfilePage setParameter(String paramName, ParameterType option, String value) {
        return (DeviceProfilePage) setParameter(new Table("tblParamsValue"), paramName, option, value);
    }

    public void checkAddedToEventTasks(String eventName) {
        pause(1000);
        Table table = new Table("tblEvents");
        table.clickOn(table.getRowNumberByText(eventName), 4);
        switchToFrame(POPUP);
        try {
            super.checkAddedTasks();
        } catch (AssertionError e) {
            pause(1000);
            super.checkAddedTasks();
        }
    }

    public void checkAddedToEventTask(String eventName, String taskName) {
        pause(1000);
        Table table = new Table("tblEvents");
        table.clickOn(table.getRowNumberByText(eventName), 4);
        switchToFrame(POPUP);
        table = new Table("tblTasks");
        try {
            table.assertPresenceOfTask(taskName);
        } catch (AssertionError e) {
            pause(1000);
            table.assertPresenceOfParameter(taskName);
        }
    }

    public void checkAddedToEventTask(String eventName, String parameter, String value) {
        Table table = new Table("tblEvents");
        table.clickOn(table.getRowNumberByText(eventName), 4);
        switchToFrame(POPUP);
        super.checkAddedTask(parameter, value);
    }

    public void checkAddedAction(String eventName, String parameter, String value) {
        Table table = new Table("tblEvents");
        table.clickOn(table.getRowNumberByText(eventName), 4);
        switchToFrame(POPUP);
        super.checkAddedTask(parameter, value, 1);
    }

    public DeviceProfilePage addTask(String task) {
        switchToFrame(POPUP);
        selectComboBox(selectTask, task);
        clickButton(addTaskButton);
        return this;
    }

    public DeviceProfilePage checkEvents() {
        return (DeviceProfilePage) super.checkEvents();
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
        throw new AssertionError("Task is still present on page!");
    }

    public DeviceProfilePage downloadImageFile() {
        switchToFrame(SUB_FRAME);
        selectDownloadFileType("Firmware Image");
        waitForUpdate();
        manualRadioButton();
        fillUrl();
        fillUsername();
        fillPassword();
        saveButton();
        switchToPreviousFrame();
        return this;
    }

    public DeviceProfilePage selectCondition(int index) {
        new Select(conditionComboBox).selectByIndex(index);
        return this;
    }

    public DeviceProfilePage selectUserInfoComboBox(String info) {
        selectComboBox(selectUserInfoComboBox, info);
        return this;
    }

    public DeviceProfilePage selectConditionTypeComboBox(String condition) {
        selectComboBox(selectConditionTypeComboBox, condition);
        return this;
    }

    public DeviceProfilePage assertButtonIsActive(boolean expectedActive, String id) {
        return (DeviceProfilePage) super.assertButtonIsActive(expectedActive, id);
    }

    public DeviceProfilePage checkParameter(String paramName, String value) {
        waitForUpdate();
        Table paramTbl = new Table(paramTable);
        int row = paramTbl.getRowsWithText(paramName).get(0);
        WebElement input = paramTbl.getCellWebElement(row, 1).findElement(By.tagName("input"));
        String actual = input.getAttribute("value");
        if (actual.equals(value)) {
            return this;
        }
        throw new AssertionError("The value of the parameter '" + paramName + "' doesn't match the declared (" +
                "expected to find '" + value + "', but find '" + actual + "')");
    }

    public void checkParameters() {
        waitForUpdate();
        Table paramTbl = new Table(paramTable);
        String[] names = paramTbl.getColumn(0);
        for (int i = 0; i < names.length; i++) {
            String paramName = names[i];
            String actual = paramTbl.getInputText(i + 1, 1);
            String expected = parameterMap.get(paramName);
//            System.out.println("Param:" + paramName + "| actual:" + actual + "| expected:" + expected);
            if (expected.equals(actual)) {
                parameterMap.remove(paramName);
            } else {
                throw new AssertionError("The value of the parameter '" + paramName + "' doesn't match the declared (" +
                        "expected to find '" + expected + "', but find '" + actual + "')");
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
            logger.warn(e.getMessage());
        }
        throw new AssertionError("One or more elements not found on Device Profile tab main page");
    }

    public DeviceProfilePage addDeviceWithoutTemplate() {
        return (DeviceProfilePage) super.addDeviceWithoutTemplate();
    }

    public DeviceProfilePage assertProfileIsPresent(boolean isExpected) {
        return assertProfileIsPresent(isExpected, selectedName);
    }

    public DeviceProfilePage assertProfileIsPresent(boolean isExpected, String name) {
        Table table = getMainTable();
        int col = table.getColumnNumber(0, "Name");
        boolean isFound = true;
        try {
            table.getRowNumberByText(col, name);
        } catch (AssertionError e) {
            isFound = false;
        }
        if (isFound != isExpected) {
            throw new AssertionError("Unexpected profile presence (expected to find: " + isExpected + ")");
        }
        return this;
    }

    public DeviceProfilePage assertProfileIsActive(boolean isActive) {
        waitForUpdate();
        Table table = getMainTable();
        int row = table.getRowNumberByText(selectedName);
        int col = table.getColumnNumber(0, "State");
        boolean actualState = table.getCellText(row, col).equals("Active");
        if (actualState == isActive) {
            return this;
        }
        throw new AssertionError("Profile '" + selectedName + "' has unexpected state (expected:'" + isActive + "', but found:'" + actualState + "')!");
    }

    public DeviceProfilePage selectManufacturer() {
        return selectManufacturer(getManufacturer());
    }

    public DeviceProfilePage selectModel() {
        return selectModel(getModelName());
    }

    public DeviceProfilePage selectProfileStatus(String status) {
        selectComboBox(filterProfileStatusComboBox, status);
        return this;
    }

    public DeviceProfilePage enterIntoProfile(String profileName) {
        try {
            enterIntoGroup(profileName);
        } catch (NoSuchElementException e) {
            System.out.println("***********retry to find OK button...***************");
            okButtonPopUp();
            enterIntoGroup(profileName);
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

    public DeviceProfilePage deleteAllProfiles() {
        Set<String> idSet = DataBaseConnector.getProfileSet();
        for (String id : idSet) {
            deleteProfileRequestApi(id);
        }
        return this;
    }

    public DeviceProfilePage deleteProfileRequestApi(String id) {
        String response = "empty";
        try {
            response = HttpConnector.sendPostRequest("http://95.217.85.220/CpeAdmin/CpeService.asmx/DeleteProfile", "{\"confIdHolder\":\"" + id + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.equals("{\"d\":true}")) {
            System.out.println("Profile deleting failed!");
        }
        return this;
    }

    public void checkFilteringByManufacturer() {
        List<String> optionList = getOptionList(filterManufacturerComboBox);
        optionList.remove("All");
        selectModel("All");
        selectProfileStatus("All");
        optionList.forEach(option -> checkFiltering(0, option));
    }

    public void checkFilteringByModelName() {
        List<String> optionList = getOptionList(filterModelNameComboBox);
        optionList.remove("All");
        selectManufacturer("All");
        selectProfileStatus("All");
        optionList.forEach(option -> checkFiltering(1, option));
    }

    public void checkFilteringByStatus() {
        List<String> optionList = getOptionList(filterProfileStatusComboBox);
        optionList.remove("All");
        selectManufacturer("All");
        selectModel("All");
        optionList.forEach(option -> checkFiltering(2, option));
    }

    private void checkFiltering(int comboBox, String filter) { //0 - Manufacturer; 1 - Model Name; 2 - Profile status.
        Set<String> dbNameSet;
        if (comboBox == 0) {
            selectManufacturer(filter);
            dbNameSet = getDeviceProfileNameSetByManufacturer(filter);
        } else if (comboBox == 1) {
            selectModel(filter);
            dbNameSet = getDeviceProfileNameSetByModelName(filter);
        } else {
            selectProfileStatus(filter);
            dbNameSet = getDeviceProfileNameSetByStatus(filter);
        }
        waitForUpdate();
        if (elementIsPresent("btnPager2")) {
            selectComboBox(itemsOnPageComboBox, "200");
            waitForUpdate();
        }
        String[] names = elementIsPresent("tblSample") ? getMainTable().getColumn("Name") : new String[0];
        Set<String> webNameSet = new HashSet<>(Arrays.asList(names));
        if (elementIsAbsent("btnPager2")) {
            dbNameSet.removeAll(webNameSet);
            if (dbNameSet.size() == 0) {
                return;
            }
        } else if (webNameSet.removeAll(dbNameSet) && webNameSet.size() == 0) {
            return;
        }
        throw new AssertionError("Filtering by "
                + (comboBox == 0 ? "manufacturer" : comboBox == 1 ? "model name" : "profile status") + " failed!");
    }

    public DeviceProfilePage getExport() {
        Table table = getMainTable();
        int col = table.getColumnNumber(0, "Name");
        int row = table.getRowsWithText("Active").get(1);
        String item = table.getCellText(row, col);
        int id = getDeviceProfileIdByName(item);
        String link = props.getProperty("ui_url") + "/CPEprofile/Export.aspx?configId=" + id;
        try {
            assertTrue(HttpConnector.sendGetRequest(link).contains("<Name>" + item + "</Name>"));
        } catch (IOException e) {
            throw new AssertionError("Download export file failed!");
        }
        return this;
    }

    public String getDpExportLink(String groupName) {
        return props.getProperty("ui_url") + "/CPEprofile/Export.aspx?configId=" + getDeviceProfileIdByName(groupName);
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
    public DeviceProfilePage assertButtonsAreEnabled(boolean enabled, IGlobalButtons... buttons) {
        return (DeviceProfilePage) super.assertButtonsAreEnabled(enabled, buttons);
    }

    @Override
    public DeviceProfilePage fillName() {
        return inputText(BaseTestCase.getTestName(), SAVE_AND_ACTIVATE);
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

    @Override
    public DeviceProfilePage inputNumOfRepetitions(String text) {
        return (DeviceProfilePage) super.inputNumOfRepetitions(text);
    }

    public DeviceProfilePage fillConditionName() {
        return inputText(BaseTestCase.getTestName(), NEXT);
    }

    public DeviceProfilePage inputText(String text, GlobalButtons waitForActive) {
        for (int i = 0; i < 10; i++) {
            nameField.clear();
            nameField.sendKeys(text + " ");
            waitForUpdate();
            nameField.sendKeys(Keys.BACK_SPACE);
            waitForUpdate();
            if (isButtonActive(waitForActive)) {
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
    public DeviceProfilePage pause(int millis) {
        return (DeviceProfilePage) super.pause(millis);
    }

    @Override
    public DeviceProfilePage assertElementIsPresent(String id) {
        return (DeviceProfilePage) super.assertElementIsPresent(id);
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
        throw new AssertionError("Input field for parameter '" + paramName + "' doesn't have red border!");
    }

    public DeviceProfilePage assertAddTaskButtonIsActive(String eventName, boolean expectedActive) {
        Table table = new Table("tblEvents");
        WebElement input = table.getInput(table.getRowNumberByText(eventName), 4);
        if (input.isEnabled() == expectedActive) {
            return this;
        }
        throw new AssertionError("Button 'Add task' has unexpected state (disabled)");
    }

    public DeviceProfilePage checkTargetDevice(boolean isExpected, String parameter, String value) {
        DeviceUpdatePage dUPage = new DeviceUpdatePage();
        dUPage
                .topMenu(TopMenu.DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DeviceUpdatePage.Left.DEVICE_SETTINGS);
        getTabTable().clickOn("Management");
        for (int i = 0; i < 10; i++) {
            Table table = new Table("tblParamsTable");
            int row = table.getRowNumberByText(0, parameter);
            WebElement cell = table.getCellWebElement(row, 1);
            String text = cell.findElement(By.tagName("input")).getAttribute("value");
            if (isExpected && text.equals(value)) {
                break;
            }
            if ((i == 9 && isExpected) || (!isExpected && text.equals(value))) {
                String warn = isExpected ? "Profile has not been applied to the device, but MUST!" : "Profile has been applied to the device, but MUST NOT!";
                logger.warn(warn);
                throw new AssertionError(warn);
            }
            globalButtons(DeviceUpdatePage.GlobalButtons.GET_CURRENT);
            okButtonPopUp();
        }
        return this;
    }

    public DeviceProfilePage selectTreeObject(boolean clickOnCheckbox) {
        return selectTreeObject(clickOnCheckbox, 0);
    }

    public DeviceProfilePage selectAnotherTreeObject(boolean clickOnCheckbox) {
        return selectTreeObject(clickOnCheckbox, 1);
    }

    public DeviceProfilePage selectTreeObject(boolean clickOnCheckbox, int objNum) {
        Table table = new Table("tblTree");
        List<Integer> rows = table.getRowsWithInput(0);
        table.clickOn(rows.get(objNum), 0, 1);
        waitForUpdate();
        if (clickOnCheckbox) {
            table.clickOn(rows.get(objNum), 0, 0);
            waitForUpdate();
        }
        return this;
    }

    public DeviceProfilePage selectTreeObjectCheckbox(int objNum) {
        Table table = new Table("tblTree");
        List<Integer> rows = table.getRowsWithInput(0);
        table.clickOn(rows.get(objNum), 0, 0);
        waitForUpdate();
        return this;
    }

    public DeviceProfilePage selectTreeObjectCheckbox() {
        return selectTreeObjectCheckbox(0);
    }

    public DeviceProfilePage selectAnotherTreeObjectCheckbox() {
        return selectTreeObjectCheckbox(1);
    }

    public DeviceProfilePage assertParametersAreSelected(boolean expectedState) {
        Table table = new Table("tblParameters");
        for (int i = 1; i < table.getTableSize()[0]; i++) {
            if (table.getInput(i, 0).isSelected() != expectedState) {
                throw new AssertionError("One or more parameter has unexpected state!");
            }
        }
        return this;
    }

    public DeviceProfilePage globalButtons(GlobalButtons button) {
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

    public DeviceProfilePage setPolicy(int scenario) {
        if (parameterMap == null) {
            parameterMap = new HashMap<>();
        }
        Table table = new Table("tblParamsValue");
        int tableSize = table.getTableSize()[0];
        int limit = scenario > 3 || scenario + 1 > tableSize ? tableSize : scenario + 1;
        String[][] all = {{"Off", "Passive", "Active"}, {"Default", "AcsOnly", "All"}};
        for (int i = 1; i < limit; i++) {
            WebElement notification = table.getCellWebElement(i, 1).findElement(By.tagName("select"));
            WebElement accessList = table.getCellWebElement(i, 2).findElement(By.tagName("select"));
            String result;
            if (scenario == 1) {
                selectComboBox(accessList, "ACS only");
                result = "Access=AcsOnly";
            } else if (scenario == 2) {
                selectComboBox(notification, "Off");
                result = "Notification=Off ";
            } else if (scenario == 3) {
                new Select(notification).selectByIndex(i);
                new Select(accessList).selectByIndex(i - 1);
                result = "Notification=" + all[0][i - 1] + " " + (i == 1 ? "" : "Access=" + all[1][i - 1]);
            } else {
                selectComboBox(notification, "Active");
                selectComboBox(accessList, "ACS only");
                result = "Notification=Active Access=AcsOnly";
            }
            parameterMap.put(table.getHint(i), result);
        }
        return this;
    }

    public DeviceProfilePage deleteProfileIfExists() {
        try {
            Table table = getMainTable();
            int row = table.getRowNumberByText(table.getColumnNumber(0, "Name"), BaseTestCase.getTestName());
            selectItem(table, BaseTestCase.getTestName(), 1);
            globalButtons(DELETE);
            okButtonPopUp();
        } catch (AssertionError e) {
            System.out.println("Profile '" + BaseTestCase.getTestName() + "' not found, nothing to delete");
        }
        return this;
    }

    public DeviceProfilePage leftMenu(Left item) {
        switchToFrame(ROOT);
        getTable("tblLeftMenu").clickOn(item.value);
        waitForUpdate();
        switchToFrame(DESKTOP);
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

    public enum GlobalButtons implements IGlobalButtons {

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

        GlobalButtons(String id) {
            this.id = id;
        }

        private final String id;

        public String getId() {
            return id;
        }
    }

    public enum Policy implements IPolicy {
        //      DEFAULT("Default"),
        OFF("0"),
        PASSIVE("1"),
        ACTIVE("2"),
        ACS_ONLY("AcsOnly"),
        ALL("All");

        private final String option;

        public String getOption() {
            return option;
        }

        Policy(String option) {
            this.option = option;
        }
    }
}
