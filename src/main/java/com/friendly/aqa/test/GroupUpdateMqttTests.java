package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.utils.XmlWriter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.pageobject.BasePage.getManufacturer;
import static com.friendly.aqa.pageobject.BasePage.getModelName;
import static com.friendly.aqa.pageobject.GlobalButtons.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.*;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Conditions.EQUAL;

@Listeners(UniversalVideoListener.class)
public class GroupUpdateMqttTests extends BaseTestCase {
    @Test
    public void mqtt_gu_001() {
        guPage
                .deleteAll()
                .topMenu(GROUP_UPDATE)
                .waitForUpdate()
                .assertMainPageIsDisplayed();
    }

    @Test
    public void mqtt_gu_002() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .globalButtons(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void mqtt_gu_003() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .deleteFilterGroups()
                .globalButtons(CANCEL)
                .waitForUpdate()
                .assertMainPageIsDisplayed();
    }

    @Test
    public void mqtt_gu_004() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .showList()
                .assertTrue(guPage.serialNumberTableIsPresent());
    }

    @Test
    public void mqtt_gu_005() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .createGroup()
                .assertTrue(guPage.isButtonPresent(FINISH))
                .globalButtons(CANCEL)
                .waitForUpdate().pause(500)
                .assertEquals(guPage.getAttributeById("txtName", "value"), testName);
    }

    @Test
    public void mqtt_gu_006() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("device_created")
                .compareSelect("IsNull")
                .globalButtons(NEXT)
                .assertFalse(guPage.isButtonActive("btnDelFilter_btn")).filterRecordsCheckbox()
                .assertTrue(guPage.isButtonActive("btnDelFilter_btn"))
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertEquals(testName, guPage.getSelectedValue("ddlSend"));
        setTargetTestName();
    }

    @Test
    public void mqtt_gu_007() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("cust2")
                .compareSelect("Equal")
                .inputTextField("111")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertEquals(testName, guPage.getSelectedValue("ddlSend"), "Created group isn't selected!\n")
                .assertTrue(guPage.isElementDisplayed("lblNoSelectedCpes"), "Warning 'No devices selected' isn't displayed!\n");
    }

    @Test
    public void mqtt_gu_008() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .createGroup()
                .fillName(targetTestName)
                .globalButtons(NEXT)
                .assertTrue(guPage.isElementDisplayed("lblNameInvalid"), "Warning 'This name is already in use' isn't displayed!\n");
    }

    @Test
    public void mqtt_gu_009() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo(targetTestName)
                .editGroupButton()
                .globalButtons(DELETE_GROUP)
                .okButtonPopUp()
                .assertFalse(guPage.isOptionPresent("ddlSend", targetTestName), "Option '" + targetTestName + "' is still present on 'Send to' list!\n");
    }

    @Test
    public void mqtt_gu_010() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo("Individual")
                .clickOnTable("tblDevices", 1, 0)
                .waitForUpdate()
                .assertButtonsAreEnabled(true, NEXT)
                .clickOnTable("tblDevices", 1, 0)
                .waitForUpdate()
                .assertButtonsAreEnabled(false, NEXT);
    }

    @Test
    //Doesn't work with Edge
    public void mqtt_gu_011() {
        XmlWriter.createImportCpeFile();
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo("Import")
                .selectImportDevicesFile()
                .showList()
                .assertPresenceOfValue("tblDevices", 0, getSerial());
    }

    @Test
    public void mqtt_gu_012() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("device_created")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectSendTo(testName)
                .showList()
                .assertPresenceOfValue("tblDevices", 0, getSerial());
    }

    @Test
    public void mqtt_gu_018() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .timeHoursSelect("0")
                .globalButtons(NEXT)
                .assertEqualsAlertMessage("Update can't be scheduled to the past")
                .checkIsCalendarClickable();
    }

    @Test
    public void mqtt_gu_020(){
        guPage
                .gotoSetParameters(true)
                .setAdvancedParameter("Device.FriendlySmartHome.GasDetector.1", 2)
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void mqtt_gu_021(){
        guPage
                .gotoSetParameters(true)
                .setAdvancedParameter("Device.FriendlySmartHome.GasDetector.1", 1)
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void mqtt_gu_022(){
        guPage
                .gotoSetParameters(true)
                .setAdvancedParameter("Device.FriendlySmartHome.GasDetector.1", 99)
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void mqtt_gu_023(){
        guPage
                .gotoSetParameters(true)
                .setAdvancedParameter("Device.FriendlySmartHome.Humidity.1", 2)
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void mqtt_gu_024(){
        guPage
                .gotoSetParameters(true)
                .setAdvancedParameter("Device.FriendlySmartHome.Humidity.1", 1)
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void mqtt_gu_025(){
        guPage
                .gotoSetParameters(true)
                .setAdvancedParameter("Device.FriendlySmartHome.Humidity.1", 99)
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void mqtt_gu_026(){
        guPage
                .gotoSetParameters(true)
                .setAdvancedParameter("Device.FriendlySmartHome.PowerMeter.1", 2)
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void mqtt_gu_027(){
        guPage
                .gotoSetParameters(true)
                .setAdvancedParameter("Device.FriendlySmartHome.PowerMeter.1", 1)
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void mqtt_gu_028(){
        guPage
                .gotoSetParameters(true)
                .setAdvancedParameter("Device.FriendlySmartHome.PowerMeter.1", 99)
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void mqtt_gu_029(){
        guPage
                .gotoSetParameters(true)
                .setAdvancedParameter("Device.FriendlySmartHome.Temperature.1", 2)
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void mqtt_gu_030(){
        guPage
                .gotoSetParameters(true)
                .setAdvancedParameter("Device.FriendlySmartHome.Temperature.1", 1)
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void mqtt_gu_031(){
        guPage
                .gotoSetParameters(true)
                .setAdvancedParameter("Device.FriendlySmartHome.Temperature.1", 99)
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void mqtt_gu_032() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .rebootRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("tblTasks", "Reboot");
    }

    @Test
    public void mqtt_gu_033() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .rebootRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "Client ID", EQUAL, "mqtt_demo")
                .saveAndActivate(false)
                .assertPresenceOfValue("tblTasks", 2, "Reboot");
    }

    @Test
    public void mqtt_gu_034() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .factoryResetRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("tblTasks", "Factory Reset");
    }

    @Test
    public void mqtt_gu_035() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .factoryResetRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "Client ID", EQUAL, "mqtt_demo")
                .saveAndActivate(false)
                .assertPresenceOfValue("tblTasks", 2, "Factory Reset");
    }

    //Radiobutton  “Reprovision” is not available (V6.0.0 Build 139)
    @Test
    public void mqtt_gu_036() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .reprovisionRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("tblTasks", "Device Reprovision");
    }

    @Test
    public void mqtt_gu_037() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .reprovisionRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "Client ID", EQUAL, "mqtt_demo")
                .saveAndActivate(false)
                .assertPresenceOfValue("tblTasks", 2, "Device Reprovision");
    }

    @Test
    public void mqtt_gu_039() {
        XmlWriter.createImportGroupFile();
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .selectSendTo()
                .showList()
                .assertPresenceOfValue("tblDevices", 0, getSerial());
    }

    @Test
    public void mqtt_gu_040() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .globalButtons(CANCEL)
                .assertElementIsPresent("tblParameters");
    }

    @Test
    public void mqtt_gu_041() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Manufacturer", getManufacturer());
    }

    @Test
    public void mqtt_gu_042() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Model", getModelName());
    }

    @Test
    public void mqtt_gu_043() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Completed")
                .checkFiltering("State", "Not active")
                .checkFiltering("State", "Error")
                .checkFiltering("State", "All");
    }

    @Test
    public void mqtt_gu_044() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Manufacturer");
    }

    @Test
    public void mqtt_gu_045() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Model");
    }

    @Test
    public void mqtt_gu_046() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name");
    }

    @Test
    public void mqtt_gu_047() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name")
                .checkSorting("Created");
    }

    @Test
    public void mqtt_gu_048() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Creator");
    }

    @Test
    public void mqtt_gu_049() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Updated");
    }

    @Test
    public void mqtt_gu_050() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Activated");
    }

    @Test
    public void mqtt_gu_051() {
        guPage
                .topMenu(GROUP_UPDATE)
                .selectManufacturer()
                .checkResetView();
    }

    @Test
    public void mqtt_gu_052() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("Manufacturer")
                .checkResetView()
                .leftMenu(VIEW)
                .itemsOnPage("10")
                .pause(5000);
    }

    @Test
    public void mqtt_gu_054() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Scheduled");
    }

    @Test
    public void mqtt_gu_055() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Running");
    }

    @Test
    public void mqtt_gu_056() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Paused");
    }

    @Test
    public void mqtt_gu_057() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Reactivation");
    }

    @Test
    public void mqtt_gu_075() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Is not null")
                .globalButtons(CANCEL)
                .waitForUpdate()
                .assertTrue(guPage.isElementDisplayed("lblHead"), "Filter creation didn't cancel properly!\n");
    }

    @Test
    public void mqtt_gu_076() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .createGroup()
                .fillName()
                .globalButtons(NEXT)
                .globalButtons(PREVIOUS)
                .globalButtons(CANCEL)
                .waitForUpdate()
                .assertFalse(guPage.isOptionPresent("ddlSend", testName), "Option '" + testName + "' is present on 'Send to' list!\n");
    }

    @Test
    public void mqtt_gu_136() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .rebootRadioButton()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("tblTasks", "Reboot");
    }

    @Test
    public void mqtt_gu_137() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .rebootRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "Client ID", EQUAL, "mqtt_demo")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("tblTasks", "Reboot");
    }

    @Test
    public void mqtt_gu_138() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .factoryResetRadioButton()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("tblTasks", "Factory Reset");
    }

    @Test
    public void mqtt_gu_139() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .factoryResetRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "Client ID", EQUAL, "mqtt_demo")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("tblTasks", "Factory Reset");
    }

    //Radiobutton  “Reprovision” is not available (V6.0.0 Build 139)
    @Test
    public void mqtt_gu_140() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .reprovisionRadioButton()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("tblTasks", "Device Reprovision");
    }

    @Test
    public void mqtt_gu_141() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .reprovisionRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "Client ID", EQUAL, "mqtt_demo")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("tblTasks", "Device Reprovision");
    }
}
