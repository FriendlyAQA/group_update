package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.BottomButtons;
import com.friendly.aqa.entities.ILeft;
import com.friendly.aqa.entities.TopMenu;
import com.friendly.aqa.utils.HttpConnector;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;

import static com.friendly.aqa.pageobject.ReportsPage.Left.*;

public class ReportsPage extends BasePage {

    @FindBy(id = "lnkListChart")
    private WebElement devicesDistribution;

    @FindBy(id = "lnkRegistrationReport")
    private WebElement devicesRegistration;

    @FindBy(id = "lnkConnectionReport")
    private WebElement onlineDevices;

    @FindBy(id = "lnkDisconnectedReport")
    private WebElement offlineDevices;

    @FindBy(id = "lnkEventsReport")
    private WebElement events;

    @FindBy(id = "lnkActivityLog")
    private WebElement userActivity;

    @FindBy(id = "lnkUpdatesReport")
    private WebElement groupUpdate;

    @FindBy(id = "lnkFirmwaresReport")
    private WebElement firmwareVersions;

    @FindBy(id = "lnkCustomDeviceListReport")
    private WebElement inventory;

    @FindBy(id = "btnSendUpdate_btn")
    private WebElement go;

    @Override
    public ReportsPage topMenu(TopMenu value) {
        return (ReportsPage) super.topMenu(value);
    }

    public ReportsPage assertItemIsPresent(String itemText) {
        if (findElementsByText(itemText).isEmpty()) {
            throw new AssertionError("Item with text '" + itemText + "' not found on page!");
        }
        return this;
    }

    public ReportsPage openDevicesDistribution() {
        devicesDistribution.click();
        return this;
    }

    public ReportsPage openDevicesRegistration() {
        devicesRegistration.click();
        return this;
    }

    public ReportsPage openOnlineDevices() {
        onlineDevices.click();
        return this;
    }

    public ReportsPage openOfflineDevices() {
        offlineDevices.click();
        return this;
    }

    public ReportsPage openEvents() {
        events.click();
        return this;
    }

    public ReportsPage openUserActivity() {
        userActivity.click();
        return this;
    }

    public ReportsPage openGroupUpdate() {
        groupUpdate.click();
        return this;
    }

    public ReportsPage openFirmwareVersions() {
        firmwareVersions.click();
        return this;
    }

    public ReportsPage openInventory() {
        inventory.click();
        return this;
    }

    public ReportsPage go() {
        go.click();
        return this;
    }

    public void validateExport() {
        try {
            assertTrue(HttpConnector.sendGetRequest(props.getProperty("ui_url") + "/Reports/Export.aspx").contains("Amount of rows:"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new AssertionError("Export validation failed");
        }
    }

    @Override
    public void closeMapWindow() {
        super.closeMapWindow();
        leftMenu(LIST_OF_REPORTS);
        waitForUpdate();
    }

    public ReportsPage bottomMenu(BottomButtons button) {
        clickBottomButton(button);
        return this;
    }

    @Override
    public ReportsPage leftMenu(ILeft item) {
        return (ReportsPage) super.leftMenu(item);
    }

    public enum Left implements ILeft {
        LIST_OF_REPORTS("List of Reports"), LIST_OF_EXPORTS("List of Exports");
        private final String value;

        Left(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
