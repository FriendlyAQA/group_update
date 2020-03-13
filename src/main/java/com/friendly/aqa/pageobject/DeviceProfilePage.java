package com.friendly.aqa.pageobject;

import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.Table;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
    public DeviceProfilePage selectModel(String modelName) {
        return (DeviceProfilePage) super.selectModel(modelName);
    }

    @FindBy(id = "ddlManufacturer")
    private WebElement filterManufacturerComboBox;

    @FindBy(id = "ddlModelName")
    private WebElement filterModelNameComboBox;

    @FindBy(id = "ddlUpdateStatus")
    private WebElement filterProfileStatusComboBox;

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

    @Override
    public DeviceProfilePage assertColumnHasSingleValue(String column, String value) {
        return (DeviceProfilePage) super.assertColumnHasSingleValue(column, value);
    }

    @Override
    public DeviceProfilePage assertColumnContainsValue(String column, String value) {
        return (DeviceProfilePage) super.assertColumnContainsValue(column, value);
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
}
