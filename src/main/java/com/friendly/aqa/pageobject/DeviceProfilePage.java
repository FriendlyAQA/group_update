package com.friendly.aqa.pageobject;

import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.HttpConnector;
import com.friendly.aqa.utils.Table;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.DESKTOP;
import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.ROOT;
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

    @FindBy(id = "rdFullRequest")
    private WebElement fullRequestRadioButton;

    @FindBy(id = "rdNoRequest")
    private WebElement dontRequestRadioButton;

    @FindBy(id = "rdRequiresReprovision")
    private WebElement applyProvisionRadioButton;

    @FindBy(id = "rdNoReprovision")
    private WebElement dontApplyProvisionRadioButton;

    @FindBy(id = "btnNewView_btn")
    private WebElement newConditionRadioButton;

    @FindBy(id = "rdCust")
    private WebElement userInfoRadioButton;

    @FindBy(id = "tabsMain_tblTabs")
    private WebElement mainTabTable;

    @FindBy(id = "tblParameters")
    private WebElement paramTable;

    @FindBy(id = "txtValue")
    private WebElement valueInputField;


    @Override
    public DeviceProfilePage assertElementIsSelected(String id) {
        return (DeviceProfilePage) super.assertElementIsSelected(id);
    }

    public DeviceProfilePage selectMainTab(String tab) {
        new Table(mainTabTable).clickOn(tab);
        return this;
    }

    public DeviceProfilePage selectTab(String tab) {
        getTabTable().clickOn(tab);
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

    public DeviceProfilePage newConditionRadioButton() {
        waitForUpdate();
        newConditionRadioButton.click();
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

    public DeviceProfilePage setParameter(String paramName, String value) {
        Table paramTbl = new Table(paramTable);
        int row = paramTbl.getRowsContainText(paramName).get(0);
        WebElement input = paramTbl.getCellWebElement(row, 1).findElement(By.tagName("input"));
        if (input.getAttribute("type").equals("checkbox")) {
            input.click();
        } else {
            for (int i = 0; i < 10; i++) {
                input.sendKeys(Keys.BACK_SPACE);
            }
            waitForUpdate();
            input.sendKeys(value);
        }
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

    public DeviceProfilePage assertButtonIsActive(boolean assertActive, String id) {
        return (DeviceProfilePage) super.assertButtonIsActive(assertActive, id);
    }

    public DeviceProfilePage checkParameter(String paramName, String value) {
        waitForUpdate();
        Table paramTbl = new Table(paramTable);
        int row = paramTbl.getRowsContainText(paramName).get(0);
        WebElement input = paramTbl.getCellWebElement(row, 1).findElement(By.tagName("input"));
        String actual = input.getAttribute("value");
        if (actual.equals(value)) {
            return this;
        }
        throw new AssertionError("The value of the parameter '" + paramName + "' doesn't match the declared (" +
                "expected to find '" + value + "', but find '" + actual + "')");
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
        return (DeviceProfilePage) enterIntoGroup(profileName);
    }

    public DeviceProfilePage enterIntoProfile() {
        return enterIntoProfile(BaseTestCase.getTestName());
    }

    public DeviceProfilePage enterIntoAnyProfile() {
        getMainTable().clickOn(1, 1);
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
        int row = table.getRowsContainText("Active").get(1);
        String item = table.getCellText(row, col);
        int id = getDeviceProfileIdByName(item);
        String link = props.getProperty("ui_url") + "/CPEprofile/Export.aspx?configId=" + id;
        System.out.println(link);
        try {
            assertTrue(HttpConnector.getUrlSource(link).contains("<Name>" + item + "</Name>"));
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
        pause(500);
        waitForUpdate();
        super.fillName();
        waitForUpdate();
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
        int row = table.getRowsContainText(paramName).get(0);
        WebElement input = table.getCellWebElement(row, 1).findElement(By.tagName("input"));
        if (input.getAttribute("style").endsWith("rgb(255, 74, 74);") == expectedRed) {
            return this;
        }
        throw new AssertionError("Input field for parameter '" + paramName + "' doesn't have red border!");
    }

    public DeviceProfilePage globalButtons(GlobalButtons button) {
        clickGlobalButtons(button);
        return this;
    }

    public DeviceProfilePage leftMenu(DeviceProfilePage.Left item) {
        switchToFrame(ROOT);
        getTable("tblLeftMenu").clickOn(item.value);
        waitForUpdate();
        switchToFrame(DESKTOP);
        return this;
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

    public enum GlobalButtons implements IGlobalButtons {

        ACTIVATE("btnActivate_btn"),
        ADVANCED_VIEW("btnAdvView_btn"),
        CANCEL("btnCancel_btn"),
        DEACTIVATE("btnDeactivate_btn"),
        DELETE("btnDelete_btn"),
        DELETE_GROUP("btnDeleteView_btn"),
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

        private String id;

        public String getId() {
            return id;
        }
    }
}
