package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.*;
import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.*;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.TopMenu.MONITORING;
import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;
import static com.friendly.aqa.pageobject.MonitoringPage.Left.NEW;
import static com.friendly.aqa.utils.DataBaseConnector.getMonitorNameSetByManufacturer;
import static com.friendly.aqa.utils.DataBaseConnector.getMonitorNameSetByModelName;

public class MonitoringPage extends BasePage {

    @Override
    public MonitoringPage topMenu(TopMenu value) {
        return (MonitoringPage) super.topMenu(value);
    }

    public MonitoringPage bottomMenu(BottomButtons button) {
        clickBottomButton(button);
        return this;
    }

    @FindBy(id = "btnAddModel_btn")
    private WebElement addModelButton;

    @FindBy(id = "IsDefaultViewForPublic")
    private WebElement forPublicCheckbox;

    @FindBy(id = "cbDateTo")
    private WebElement endDateCheckbox;

    @FindBy(id = "tbTimeToHour")
    private WebElement endDateHours;

    @FindBy(id = "tbTimeToMinute")
    private WebElement endDateMinutes;

    @Override
    public MonitoringPage newViewButton() {
        return (MonitoringPage) super.newViewButton();
    }

    public MonitoringPage newGroupButton() {
        return newViewButton();
    }

    @Override
    public MonitoringPage fillName() {
        nameTextField.sendKeys(BaseTestCase.getTestName());
        return this;
    }

    public MonitoringPage fillGroupName() {
        return (MonitoringPage) super.fillName();
    }

    public MonitoringPage fillGroupName(String name) {
        return (MonitoringPage) super.fillName(name);
    }

    public MonitoringPage forPublicCheckbox() {
        forPublicCheckbox.click();
        return this;
    }

    public MonitoringPage forUserCheckbox() {
        defaultViewCheckbox.click();
        return this;
    }

    @Override
    public MonitoringPage editButton() {
        return (MonitoringPage) super.editButton();
    }

    @Override
    public MonitoringPage clickOnTable(String id, int row, int column) {
        return (MonitoringPage) super.clickOnTable(id, row, column);
    }

    @Override
    public MonitoringPage clickOnTable(String id, int row, int column, int tagNum) {
        return (MonitoringPage) super.clickOnTable(id, row, column, tagNum);
    }

    @Override
    public MonitoringPage assertEqualsAlertMessage(String expectedMessage) {
        return (MonitoringPage) super.assertEqualsAlertMessage(expectedMessage);
    }

    @Override
    public MonitoringPage selectItem() {
        return (MonitoringPage) super.selectItem();
    }

    @Override
    public MonitoringPage selectItem(String groupName) {
        return (MonitoringPage) super.selectItem(groupName);
    }

    public void createPreconditions() {
        topMenu(MONITORING)
                .deleteAllMonitors()
                .deleteAllCustomViews()
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .deleteAllGroups();
    }

    public MonitoringPage enterIntoMonitoring(String itemName) {
        return (MonitoringPage) super.enterIntoItem(itemName);
    }

    public MonitoringPage enterIntoGroup() {
        return enterIntoMonitoring(BaseTestCase.getTestName());
    }

    public MonitoringPage deleteAllGroups() {
        return (MonitoringPage) deleteAll(sendToComboBox);
    }

    public MonitoringPage addModelButton() {
        return (MonitoringPage) clickButton(addModelButton);
    }

    public MonitoringPage addAnotherModel() {
        List<String> list = getOptionList(modelComboBox);
        list.remove("Select a model");
        list.remove(props.getProperty("device_without_template").split(":")[1]);
        list.remove(getModelName());
        if (list.isEmpty()) {
            throw new AssertionError("Cannot find another model name with template!");
        }
        setImplicitlyWait(0);
        for (String item : list) {
            selectModel(item);
            addModelButton();
            switchToFrame(ROOT);
            waitForUpdate();
            if (!okButtonAlertPopUp.isDisplayed()) {
                switchToFrame(DESKTOP);
                selectSendTo();
                setDefaultImplicitlyWait();
                return this;
            }
            okButtonAlertPopUp.click();
            switchToFrame(DESKTOP);
        }
        throw new AssertionError("Cannot find another model name with template!");
    }

    public void setParametersFor2Devices(boolean theSameParameter) {
        int params0 = 0, params1 = 0;
        Set<String> tabSet1 = new HashSet<>(getTabTable().getNotEmptyContentList());
        Table devTable = getTable("tblModels");
        String[] devices = devTable.getWholeColumn(0);
        devTable.clickOn(0, 0);
        waitForUpdate();
        Set<String> tabSet0 = new HashSet<>(getTabTable().getNotEmptyContentList());
        tabSet0.retainAll(tabSet1);
        tabSet0.remove("Summary");
        tabSet0.removeIf(cell -> cell.startsWith("Devices:"));
        Iterator<String> tabIterator = tabSet0.iterator();
        if (tabSet0.isEmpty()) {
            throw new AssertionError("Cannot find suitable (the same) tab for both devices!");
        }
        out:
        while (tabIterator.hasNext()) {
            getTabTable().clickOn(tabIterator.next());
            waitForUpdate();
            String[] dev0 = getParamTable().getColumn(0);
            waitForUpdate();
            devTable.clickOn(1, 0);
            waitForUpdate();
            String[] dev1 = getParamTable().getColumn(0);
            for (int i = 0; i < dev0.length; i++) {
                params0 = i + 1;
                for (int j = 0; j < dev1.length; j++) {
                    if (dev0[i].equals(dev1[j]) == theSameParameter) {
                        params1 = j + 1;
                        break out;
                    }
                }
            }
            if (/*params1 < 0 && */!tabIterator.hasNext()) {
                throw new AssertionError("Cannot find suitable parameters!");
            }
            devTable.clickOn(0, 0);
            waitForUpdate();
        }
        String hint2 = getParamTable().clickOn(params1, 1, 0).getHint(params1);
        getTable("tblModels").clickOn(0, 0);
        waitForUpdate();
        String hint1 = getParamTable().clickOn(params0, 1, 0).getHint(params0);
        immediately();
        bottomMenu(SAVE);
        okButtonPopUp();
        enterIntoGroup();
        checkDevice(devices[0], hint1);
        checkDevice(devices[1], hint2);
    }

    private void checkDevice(String device, String hint) {
        getTable("tblModels").clickOn(device);
        selectTab("Summary");
        waitForUpdate();
        String[] devParams = getParamTable().getColumn(0);
        if (getParamTable().getVisibleRowsWithInput(1).size() != 1) {
            throw new AssertionError("Unexpected number of parameters!");
        }
        boolean success = false;
        for (String value : devParams) {
            if (value.equals(hint)) {
                success = true;
                break;
            }
        }
        if (!success) {
            throw new AssertionError("Parameter " + hint + " not found!");
        }
    }

    public MonitoringPage setParameters(String tab, int startParam, int endParam) {
        return setParameters(false, tab, startParam, endParam);
    }

    public MonitoringPage setAdvancedParameters(String branch, int startParam, int endParam) {
        return setParameters(true, branch, startParam, endParam);
    }

    private MonitoringPage setParameters(boolean advancedView, String tab, int startParam, int endParam) {
        if (parameterSet == null) {
            selectTab("Summary");
            waitForUpdate();
            String[] params = new String[0];
            try {
                setImplicitlyWait(1);
                params = getParamTable().getColumn(0);
            } catch (NoSuchElementException e) {
                System.out.println("There are no default parameters");
            } finally {
                setDefaultImplicitlyWait();
            }
            parameterSet = new HashSet<>(Arrays.asList(params));
        }
        if (advancedView) {
            if (buttonIsActive(ADVANCED_VIEW)) {
                getTabTable().clickOn(1, 1);
                bottomMenu(ADVANCED_VIEW);
                waitForUpdate();
            }
            selectBranch(tab);
        } else if (tab != null) {
            getTabTable().clickOn(tab);
        }
        waitForUpdate();
        Table table = getParamTable();
        int lastParam = Math.min(endParam, table.getTableSize()[0] - 2);
        startParam = Math.min(startParam, lastParam);
        for (int i = startParam; i < lastParam + 1; i++) {
            WebElement checkBox = table.getCellWebElement(i + 1, 1).findElement(By.tagName("input"));
            checkBox.click();
            String hint = table.getHint(i + 1);
            if (hint.endsWith("DeviceInfo.UpTime")) {
                continue;
            }
            if (!parameterSet.add(hint)) {
                parameterSet.remove(hint);
            }
            waitForUpdate();
        }
        table.clickOn(0, 0);
        return this;
    }

    public MonitoringPage setSingleParameter() {
        getTabTable().clickOn(1, 1);
        waitForUpdate();
        getParamTable().clickOn(1, 1, 0);
        return this;
    }

    @Override
    public MonitoringPage selectTab(String tab) {
        getTabTable().clickOn(tab);
        return this;
    }

    @Override
    public MonitoringPage setVisibleColumns(int startParam, int endParam) {
        return (MonitoringPage) super.setVisibleColumns(startParam, endParam);
    }

    @Override
    public MonitoringPage validateAddedTasks() {
        selectTab("Summary");
        waitForUpdate();
        String[] params = getParamTable().getColumn(0);
        for (String param : params) {
            if (!parameterSet.remove(param)) {
                throw new AssertionError("Unexpected parameter: '" + param + "' detected on the 'Summary' tab!");
            }
        }
        if (parameterSet.size() > 0) {
            StringBuilder unexpected = new StringBuilder("Parameters:\n");
            for (String param : parameterSet) {
                unexpected.append(param).append("\n");
            }
            throw new AssertionError(unexpected.append("not found on the 'Summary' tab!").toString());
        }
        return this;
    }

    @Override
    public MonitoringPage immediately() {
        if (endDateCheckbox.isSelected()) {
            endDateCheckbox.click();
        }
        return (MonitoringPage) super.immediately();
    }

    @Override
    public MonitoringPage scheduledTo() {
        return (MonitoringPage) super.scheduledTo();
    }

    @Override
    public MonitoringPage selectView(String value) {
        return (MonitoringPage) super.selectView(value);
    }

    public void validateFilteringByManufacturer() {
        List<String> optionList = getOptionList(filterManufacturerComboBox);
        optionList.remove("All");
        optionList.forEach(option -> validateMonitoringFiltering(true, option));
    }

    public void validateFilteringByModelName() {
        List<String> optionList = getOptionList(filterModelNameComboBox);
        optionList.remove("All");
        optionList.forEach(option -> validateMonitoringFiltering(false, option));
    }

    private void validateMonitoringFiltering(boolean byManufacturer, String filter) {
        Set<String> dbNameSet;
        if (byManufacturer) {
            selectFilterManufacturer(filter);
            dbNameSet = getMonitorNameSetByManufacturer(filter);
        } else {
            selectFilterModelName(filter);
            dbNameSet = getMonitorNameSetByModelName(filter);
        }
        waitForUpdate();
        if (elementIsPresent("btnPager2")) {
            itemsOnPage("200");
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
        throw new AssertionError("Filtering by " + (byManufacturer ? "manufacturer" : "model name") + "failed!");
    }

    @Override
    public MonitoringPage selectFilterManufacturer(String value) {
        return (MonitoringPage) super.selectFilterManufacturer(value);
    }

    @Override
    public MonitoringPage selectFilterModelName(String value) {
        return (MonitoringPage) super.selectFilterModelName(value);
    }

    public MonitoringPage selectButton() {
        selectButton.click();
        return this;
    }

    public MonitoringPage selectIndividualDevises(int amount) {
        switchToFrame(POPUP);
        Table table = getTable("tblDevices");
        for (int i = 1; i < amount + 1; i++) {
            table.clickOn(i, 0, 0);
        }
        selectButton();
        switchToFrame(DESKTOP);
        return this;
    }

    @Override
    public MonitoringPage selectImportDevicesFile() {
        return (MonitoringPage) super.selectImportDevicesFile();
    }

    public MonitoringPage selectManufacturer() {
        return selectManufacturer(getManufacturer());
    }

    @Override
    public MonitoringPage selectManufacturer(String manufacturer) {
        return (MonitoringPage) super.selectManufacturer(manufacturer);
    }

    private Table getParamTable() {
        return new Table("tblDataParams");
    }

    @Override
    public MonitoringPage selectModel() {
        return (MonitoringPage) super.selectModel();
    }

    @Override
    public MonitoringPage selectModel(String value) {
        return (MonitoringPage) super.selectModel(value);
    }

    @Override
    public MonitoringPage selectColumnFilter(String option) {
        return (MonitoringPage) super.selectColumnFilter(option);
    }

    @Override
    public MonitoringPage selectCompare(String option) {
        return (MonitoringPage) super.selectCompare(option);
    }

    public MonitoringPage waitForStatus(String status) {
        waitForStatus(status, 5);
        return this;
    }

    public MonitoringPage waitForStatus(String status, String testName) {
        waitForStatus(status, testName, 5);
        return this;
    }

    @Override
    public MonitoringPage selectSendTo() {
        return (MonitoringPage) super.selectSendTo();
    }

    @Override
    public MonitoringPage selectSendTo(String sendTo) {
        return (MonitoringPage) super.selectSendTo(sendTo);
    }

    @Override
    public MonitoringPage assertButtonsAreEnabled(boolean enabled, IBottomButtons... buttons) {
        return (MonitoringPage) super.assertButtonsAreEnabled(enabled, buttons);
    }

    @Override
    public MonitoringPage assertButtonsArePresent(IBottomButtons... buttons) {
        return (MonitoringPage) super.assertButtonsArePresent(buttons);
    }

    @Override
    public MonitoringPage selectShiftedDate(String id, int value) {
        return (MonitoringPage) super.selectShiftedDate(id, value);
    }

    public MonitoringPage clickOn(String id) {
        driver.findElement(By.id(id)).click();
        return this;
    }

    @Override
    public MonitoringPage assertTableIsEmpty(String id) {
        return (MonitoringPage) super.assertTableIsEmpty(id);
    }

    @Override
    public MonitoringPage assertTableIsNotEmpty(String tableId) {
        return (MonitoringPage) super.assertTableIsNotEmpty(tableId);
    }

    public MonitoringPage cancelIndividualSelection() {
        switchToFrame(POPUP);
        cancelButton.click();
        switchToPreviousFrame();
        return this;
    }

    @Override
    public MonitoringPage assertButtonIsEnabled(boolean expectedActive, String id) {
        return (MonitoringPage) super.assertButtonIsEnabled(expectedActive, id);
    }

    @Override
    public MonitoringPage assertEquals(String actual, String expected) {
        return (MonitoringPage) super.assertEquals(actual, expected);
    }

    @Override
    public MonitoringPage assertEquals(String actual, String expected, String message) {
        return (MonitoringPage) super.assertEquals(actual, expected, message);
    }

    @Override
    public MonitoringPage assertTrue(boolean condition) {
        return (MonitoringPage) super.assertTrue(condition);
    }

    @Override
    public MonitoringPage assertTrue(boolean condition, String message) {
        return (MonitoringPage) super.assertTrue(condition, message);
    }

    @Override
    public MonitoringPage assertFalse(boolean condition) {
        return (MonitoringPage) super.assertFalse(condition);
    }

    @Override
    public MonitoringPage assertFalse(boolean condition, String message) {
        return (MonitoringPage) super.assertFalse(condition, message);
    }

    public MonitoringPage fillViewName() {
        return (MonitoringPage) super.fillName();
    }

    @Override
    public MonitoringPage addDeviceWithoutTemplate() {
        return (MonitoringPage) super.addDeviceWithoutTemplate();

    }

    public MonitoringPage fillViewName(String name) {
        return (MonitoringPage) super.fillName(name);
    }

    @Override
    public MonitoringPage addFilter() {
        return (MonitoringPage) super.addFilter();
    }

    @Override
    public MonitoringPage filterRecordsCheckbox() {
        return (MonitoringPage) super.filterRecordsCheckbox();
    }

    @Override
    public MonitoringPage okButtonPopUp() {
        return (MonitoringPage) super.okButtonPopUp();
    }

    @Override
    public MonitoringPage inputText(String id, String text) {
        return (MonitoringPage) super.inputText(id, text);
    }

    @Override
    public MonitoringPage assertSelectedViewIs(String expectedView) {
        return (MonitoringPage) super.assertSelectedViewIs(expectedView);
    }

    public MonitoringPage setEndDateDelay(int minutes) {
        String[] time = CalendarUtil.getDelay(minutes);
        if (!endDateCheckbox.isSelected()) {
            endDateCheckbox.click();
        }
        endDateHours.clear();
        endDateHours.sendKeys(time[0]);
        endDateMinutes.clear();
        endDateMinutes.sendKeys(time[1]);
        return this;
    }

    public MonitoringPage setScheduledDelay(int minutes) {
        String[] time = CalendarUtil.getDelay(minutes);
        scheduledHours.clear();
        scheduledHours.sendKeys(time[0]);
        scheduledMinutes.clear();
        scheduledMinutes.sendKeys(time[1]);
        return this;
    }

    @Override
    public MonitoringPage waitForStatus(String status, int timeoutSec) {
        return (MonitoringPage) super.waitForStatus(status, timeoutSec);
    }

    @Override
    public MonitoringPage selectDate(String date) {
        return (MonitoringPage) super.selectDate(date);
    }

    @Override
    public MonitoringPage pause(long millis) {
        return (MonitoringPage) super.pause(millis);
    }

    @Override
    public MonitoringPage assertElementsArePresent(String... elementsId) {
        return (MonitoringPage) super.assertElementsArePresent(elementsId);
    }

    public MonitoringPage presetFilter(String parameter, String value) {
        new DeviceUpdatePage().presetFilter(parameter, value);
        return this;
    }

    public MonitoringPage selectImportMonitorFile() {
        XmlWriter.createImportMonitorFile();
        switchToFrame(DESKTOP);
        String inputText = new File("import/" + getProtocolPrefix() + "_import_monitor.xml").getAbsolutePath();
        importField.sendKeys(inputText);
        executeScript("__doPostBack('btnSaveConfiguration','')");
        return this;
    }

    public MonitoringPage leftMenu(Left item) {
        return (MonitoringPage) super.leftMenu(item);
    }

    @Override
    public MonitoringPage deleteAllCustomViews() {
        return (MonitoringPage) super.deleteAllCustomViews();
    }

    public MonitoringPage fillCustomViewName() {
        nameField.sendKeys(BaseTestCase.getTestName());
        return this;
    }

    @Override
    public MonitoringPage validateName() {
        assertEquals(nameTextField.getAttribute("value"), BaseTestCase.getTestName());
        return this;
    }

    public MonitoringPage partOfMacAddress() {
        inputText(inputTextField, DataBaseConnector.getMacAddress().substring(0, 8));
        return this;
    }

    public MonitoringPage deleteAllMonitors() {
        if (getMainTable().isEmpty()) {
            return this;
        }
        if (elementIsPresent("btnPager2")) {
            itemsOnPage("200");
        }
        getMainTable().clickOn(0, 0);
        bottomMenu(DELETE);
        okButtonPopUp();
        return this;
    }

    public enum Left implements ILeft {
        /*VIEW("View"),*/ IMPORT("Import"), NEW("New");
        private final String value;

        Left(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
