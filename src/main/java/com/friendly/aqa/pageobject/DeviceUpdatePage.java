package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.IGlobalButtons;
import com.friendly.aqa.entities.Table;
import com.friendly.aqa.entities.TopMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;
import static com.friendly.aqa.entities.TopMenu.DEVICE_UPDATE;

public class DeviceUpdatePage extends BasePage {
    @Override
    protected String getLeftMenuCssSelector() {
        return null;
    }

    @Override
    public Table getMainTable() {
        return null;
    }

    @Override
    public DeviceUpdatePage topMenu(TopMenu value) {
        return (DeviceUpdatePage) super.topMenu(value);
    }

    public DeviceUpdatePage presetFilter(String parameter, String value) {
        topMenu(DEVICE_UPDATE);
        enterToDevice();
        clickOn("btnEditUserInfo_lnk");
        switchToFrame(POPUP);
        WebElement saveButton = driver.findElement(By.id("btnSaveUsr_btn"));
        new FluentWait<>(driver)
                .withMessage("Save button not found")
                .withTimeout(Duration.ofSeconds(Integer.parseInt(props.getProperty("driver_implicitly_wait"))))
                .pollingEvery(Duration.ofMillis(100))
                .until(ExpectedConditions.visibilityOf(saveButton));
        for (int i = 0; i < 10; i++) {
            setUserInfo(parameter, value);
            if (saveButton.isEnabled()) {
                break;
            }
            if (i == 9) {
                throw new AssertionError("Save button not active");
            }
            pause(500);
        }
        saveButton.click();
        okButtonPopUp();
        pause(500);
        return this;
    }

    public DeviceUpdatePage enterToDevice() {
        try {
            getTable("tbl").clickOn(getSerial());
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
        Table table = getTable("tblMain");
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

    public DeviceUpdatePage leftMenu(Left item) {
        switchToFrame(ROOT);
        getTable("tblLeftMenu").clickOn(item.value);
        waitForUpdate();
        switchToFrame(DESKTOP);
        return this;
    }

    public enum Left {
        DEVICE_INFO("Device Info"), DEVICE_SETTINGS("Device Settings"), ADVANCED_VIEW("Advanced View"),
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
        FINISH("btnFinish_btn"),
        GET_CURRENT("UcDeviceSettingsControls1_btnGetCurrent_btn"),
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
