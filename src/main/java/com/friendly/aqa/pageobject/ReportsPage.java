package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.BottomButtons;
import com.friendly.aqa.entities.ILeft;
import com.friendly.aqa.entities.TopMenu;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.DataBaseConnector;
import com.friendly.aqa.utils.HttpConnector;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static com.friendly.aqa.pageobject.ReportsPage.Left.LIST_OF_REPORTS;

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

    @FindBy(id = "lrdDateFrom")
    private WebElement priorToRB;

    @FindBy(id = "lrdPeriod")
    private WebElement showByPeriodRB;

    @FindBy(id = "ddlPeriodType")
    private WebElement periodCombobox;

    @FindBy(id = "txtPeriod")
    private WebElement periodInputField;

    @FindBy(id = "ddlEvents")
    private WebElement eventCombobox;

    @FindBy(id = "ddlLogType")
    private WebElement activityCombobox;

    @FindBy(id = "ddlActivType")
    private WebElement activityTypeCombobox;

    @Override
    public ReportsPage topMenu(TopMenu value) {
        return (ReportsPage) super.topMenu(value);
    }

    @Override
    public ReportsPage selectShiftedDate(String id, int daysToShift) {
        return (ReportsPage) super.selectShiftedDate(id, daysToShift);
    }

    public ReportsPage assertItemIsPresent(String itemText) {
        if (findElementsByText(itemText).isEmpty()) {
            throw new AssertionError("Item with text '" + itemText + "' not found on page!");
        }
        return this;
    }

    public ReportsPage inputPeriod(String days) {
        inputText(periodInputField, days);
        return this;
    }

    public ReportsPage selectManufacturer(String protocol) {
        return (ReportsPage) super.selectManufacturer(getDevice(protocol)[0]);
    }

    public ReportsPage selectModel(String protocol) {
        return (ReportsPage) super.selectModel(getDevice(protocol)[1]);
    }

    public ReportsPage selectModelName(String protocol) {
        return (ReportsPage) super.selectFilterModelName(getDevice(protocol)[1]);
    }

    private String[] getDevice(String protocol) {
        return DataBaseConnector.getDevice(props.getProperty(protocol + "_cpe_serial"));
    }

    public ReportsPage selectPeriod(String period) {
        selectComboBox(periodCombobox, period);
        return this;
    }

    public ReportsPage selectEvent(String event) {
        selectComboBox(eventCombobox, event);
        return this;
    }

    public ReportsPage selectActivity(String event) {
        selectComboBox(activityCombobox, event);
        return this;
    }

    public ReportsPage selectActivityType(String type) {
        selectComboBox(activityTypeCombobox, type);
        return this;
    }

    public ReportsPage priorToDateRB() {
        priorToRB.click();
        return this;
    }

    public ReportsPage showByPeriodRB() {
        showByPeriodRB.click();
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

    public ReportsPage selectOfflineManufacturer() {
        selectComboBox(manufacturerComboBox, setLatestOfflineDevice()[0]);
        return this;
    }

    public ReportsPage selectOfflineModel() {
        selectComboBox(filterModelNameComboBox, setLatestOfflineDevice()[1]);
        return this;
    }

    public String[] setLatestOfflineDevice() {
        String id = DataBaseConnector.getValue("SELECT id FROM ftacs.cpe ORDER BY updated ASC LIMIT 1");
        try {
            String sDate = CalendarUtil.getDbShiftedDate(-62);
            Date markerDate = CalendarUtil.getDbDate(sDate);
            Date latestDate = CalendarUtil.getDbDate(DataBaseConnector.getValue("SELECT updated FROM ftacs.cpe WHERE id='" + id + "'"));
            if (latestDate.compareTo(markerDate) > 0) {
                System.out.println("UPDATE `ftacs`.`cpe` SET `updated`='" + sDate + "' WHERE `id`=" + id + ";");
                DataBaseConnector.getValue("UPDATE `ftacs`.`cpe` SET `updated`='" + sDate + "' WHERE `id`='" + id + "';");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new AssertionError("Cannot get data from DB!");
        }
        return DataBaseConnector.getDevice(DataBaseConnector.getValue("SELECT serial FROM ftacs.cpe WHERE id='" + id + "'"));
    }

    public ReportsPage selectManufacturerWithEvents() {
        selectComboBox(manufacturerComboBox, getDeviceWithEvents()[0]);
        return this;
    }

    public ReportsPage selectModelWithEvents() {
        selectComboBox(filterModelNameComboBox, getDeviceWithEvents()[1]);
        return this;
    }

    private String[] getDeviceWithEvents() {
        String serial = DataBaseConnector.getValue("SELECT c.serial FROM ftacs.cpe c JOIN ftacs.event_monitoring_cpe e ON (c.id=e.cpe_id) ORDER BY e.updated LIMIT 1");
        return DataBaseConnector.getDevice(serial);
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
