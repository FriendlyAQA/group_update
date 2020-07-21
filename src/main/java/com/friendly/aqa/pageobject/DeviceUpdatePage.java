package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.IGlobalButtons;
import com.friendly.aqa.entities.Table;
import com.friendly.aqa.entities.TopMenu;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;
import static com.friendly.aqa.entities.TopMenu.DEVICE_UPDATE;

public class DeviceUpdatePage extends BasePage {
    private static final Logger logger = Logger.getLogger(DeviceUpdatePage.class);

    @FindBy(id = "btnSaveUsr_btn")
    private WebElement saveButton;

    @Override
    protected String getLeftMenuCssSelector() {
        return null;
    }

    @Override
    public Table getMainTable() {
        return getTable("tbl");
    }

    @Override
    public DeviceUpdatePage topMenu(TopMenu value) {
        return (DeviceUpdatePage) super.topMenu(value);
    }

    @Override
    public DeviceUpdatePage assertMainPageIsDisplayed() {
        return (DeviceUpdatePage) super.assertMainPageIsDisplayed();
    }

    @Override
    public DeviceUpdatePage selectItem(String text) {
        return (DeviceUpdatePage) super.selectItem(text);
    }

    @Override
    public DeviceUpdatePage pause(int millis) {
        return (DeviceUpdatePage) super.pause(millis);
    }

    @Override
    public DeviceUpdatePage selectItem(Table table, String text, int startFromRow) {
        List<Integer> list = table.getRowsWithText(text);
        for (int i : list) {
            if (i >= startFromRow) {
                table.clickOn(i, 0);
                break;
            }
        }
        waitForUpdate();
        return this;
    }

    @Override
    public DeviceUpdatePage newViewButton() {
        return (DeviceUpdatePage) super.newViewButton();
    }

    @Override
    public DeviceUpdatePage assertPresenceOfOptions(String comboBoxId, String... options) {
        return (DeviceUpdatePage) super.assertPresenceOfOptions(comboBoxId, options);
    }

    @Override
    public DeviceUpdatePage fillName() {
        return (DeviceUpdatePage) super.fillName();
    }

    @Override
    public DeviceUpdatePage fillName(String name) {
        return (DeviceUpdatePage) super.fillName(name);
    }

    @Override
    public DeviceUpdatePage assertInputHasText(String id, String text) {
        return (DeviceUpdatePage) super.assertInputHasText(id, text);
    }

    @Override
    public DeviceUpdatePage setViewColumns(int startParam, int endParam) {
        return (DeviceUpdatePage) super.setViewColumns(startParam, endParam);
    }

    @Override
    public DeviceUpdatePage selectColumnFilter(String option) {
        return (DeviceUpdatePage) super.selectColumnFilter(option);
    }

    @Override
    public DeviceUpdatePage selectCompare(String option) {
        return (DeviceUpdatePage) super.selectCompare(option);
    }

    @Override
    public DeviceUpdatePage selectFilterModelName(String value) {
        return (DeviceUpdatePage) super.selectFilterModelName(value);
    }

    @Override
    public DeviceUpdatePage filterRecordsCheckbox() {
        return (DeviceUpdatePage) super.filterRecordsCheckbox();
    }

    @Override
    public DeviceUpdatePage selectView(String value) {
        return (DeviceUpdatePage) super.selectView(value);
    }

    @Override
    public DeviceUpdatePage editButton() {
        return (DeviceUpdatePage) super.editButton();
    }

    @Override
    public DeviceUpdatePage clickOnTable(String id, int row, int column, int tagNum) {
        return (DeviceUpdatePage) super.clickOnTable(id, row, column, tagNum);
    }

    @Override
    public DeviceUpdatePage addSubFilter() {
        return (DeviceUpdatePage) super.addSubFilter();
    }

    @Override
    public DeviceUpdatePage andRadioButton() {
        return (DeviceUpdatePage) super.andRadioButton();
    }

    @Override
    public DeviceUpdatePage deleteFilter() {
        return (DeviceUpdatePage) super.deleteFilter();
    }

    public DeviceUpdatePage selectAnyItem() {
        return selectItem(getModelName());
    }

    public DeviceUpdatePage assertDuplicateNameErrorIsDisplayed() {
        setImplicitlyWait(0);
        List<WebElement> list = driver.findElements(By.id("lblNameInvalid"));
        setDefaultImplicitlyWait();
        if (list.size() == 1) {
            return this;
        }
        String warn = "Error message 'This name is already in use' not found on current page!";
        logger.warn(warn);
        throw new AssertionError(warn);
    }

    public DeviceUpdatePage presetFilter(String parameter, String value) {
        topMenu(DEVICE_UPDATE);
        enterToDevice();
        Table table = new Table("tblUserInfo");
        if (!table.isEmpty()) {
            try {
                int rowNum = table.getRowNumberByText(parameter);
                if (rowNum >= 0 && table.getCellText(rowNum, 1).equals(value)) {
                    return this;
                }
            } catch (AssertionError e) {
                System.out.println("filter does not exist, go to create new one");
            }
        }
        clickOn("btnEditUserInfo_lnk");
        switchToFrame(POPUP);
        WebElement saveButton = driver.findElement(By.id("btnSaveUsr_btn"));
        setUserInfo(parameter, value);
        waitUntilElementIsEnabled("btnSaveUsr_btn");
        saveButton.click();
        okButtonPopUp();
        pause(500);
        return this;
    }

    public void createPreconditionsForSorting() {
        waitForUpdate();
        Table mainTable = getMainTable();
        String[] serials = mainTable.getColumn("Serial");
        for (String serial : serials) {
            mainTable.clickOn(serial);
            clickOn("btnEditUserInfo_lnk");
            switchToFrame(POPUP);
            Table userInfoTable = getTable("tblMain", 18, true);
            String[] items = userInfoTable.getWholeColumn(0);
            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                if (userInfoTable.getInputText(i, 1).isEmpty()) {
                    setUserInfo(userInfoTable, item, getRandomStringValue(10));
                }
            }
            waitUntilElementIsEnabled("btnSaveUsr_btn");
            saveButton.click();
            okButtonPopUp();
            pause(500);
            waitForUpdate();
            topMenu(DEVICE_UPDATE);
            mainTable = getMainTable();
        }
    }

    public DeviceUpdatePage enterToDevice() {
        try {
            getMainTable().clickOn(getSerial());
            pause(500);
        } catch (AssertionError e) {
            selectComboBox(itemsOnPageComboBox, "200");
            waitForUpdate();
            getTable("tbl").clickOn(getSerial());
        }
        waitForUpdate();
        return this;
    }

    public void setUserInfo(String paramName, String value) {
        setUserInfo(new Table("tblMain"), paramName, value);
    }

    public void setUserInfo(Table table, String paramName, String value) {
        int rowNum = table.getRowNumberByText(0, paramName);
        if (rowNum < 0) {
            throw new AssertionError("Parameter name '" + paramName + "' not found");
        }
        WebElement paramCell = table.getCellWebElement(rowNum, 1);
        if (props.getProperty("browser").equals("edge")) {
            BasePage.scrollToElement(paramCell);
        }
        WebElement input = paramCell.findElement(By.tagName("input"));
        input.clear();
        pause(300);
        input.sendKeys(value);
    }

    public DeviceUpdatePage globalButtons(GlobalButtons button) {
        clickGlobalButtons(button);
        return this;
    }

    @Override
    public DeviceUpdatePage okButtonPopUp() {
        return (DeviceUpdatePage) super.okButtonPopUp();
    }

    public DeviceUpdatePage leftMenu(Left item) {
        switchToFrame(ROOT);
        getTable("tblLeftMenu").clickOn(item.value);
        waitForUpdate();
        switchToFrame(DESKTOP);
        return this;
    }

    public enum Left {
        LIST("List"), DEVICE_INFO("Device Info"), DEVICE_SETTINGS("Device Settings"), ADVANCED_VIEW("Advanced View"),
        PROVISION_MANAGER("Provision Manager"), DEVICE_MONITORING("Device Monitoring"), FILE_DOWNLOAD("File Download"),
        FILE_UPLOAD("File Upload"), DEVICE_DIAGNOSTIC("Device Diagnostics"), CUSTOM_RPC("Custom RPC"),
        DEVICE_HISTORY("Device History"), DEVICE_ACTIVITY("Device Activity");

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
        EXPORTS("btnCompletedExports_btn"),
        EXPORT_TO_CSV("btnExport_btn"),
        EXPORT_TO_XML("btnExport2XML_btn"),
        FINISH("btnFinish_btn"),
        GET_CURRENT("UcDeviceSettingsControls1_btnGetCurrent_btn"),
        NEXT("btnNext_btn"),
        PAUSE("btnPause_btn"),
        REFRESH("btnRefresh_btn"),
        REPROVISION("btnCPEReprovision_btn"),
        PREVIOUS("btnPrev_btn"),
        SAVE("btnSave_btn"),
        SAVE_AND_ACTIVATE("btnSaveActivate_btn"),
        SIMPLE_VIEW("btnTabView_btn"),
        SHOW_ON_MAP("btnMap_btn"),
        STOP("btnStop_btn"),
        STOP_WITH_RESET("btnStopWithReset_btn"),
        TRACE("btnTrace_btn");

        GlobalButtons(String id) {
            this.id = id;
        }

        private String id;

        public String getId() {
            return id;
        }
    }

}
