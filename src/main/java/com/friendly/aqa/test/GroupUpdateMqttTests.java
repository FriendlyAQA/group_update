package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Conditions.EQUAL;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.*;

@Listeners(UniversalVideoListener.class)
public class GroupUpdateMqttTests extends BaseTestCase {

    @Test
    public void mqtt_gu_000() {
        guPage
                .deleteAll()
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .deleteFilterGroups();
    }

    @Test
    public void mqtt_gu_001() {
        guPage
                .topMenu(GROUP_UPDATE)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void mqtt_gu_002() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .bottomMenu(CANCEL)
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
                .bottomMenu(CANCEL)
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
                .assertDevicesArePresent();
    }

    @Test
    public void mqtt_gu_005() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .createGroupButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .bottomMenu(CANCEL)
                .pause(1000)
                .validateName();
    }

    @Test
    public void mqtt_gu_006() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("device_created")
                .selectCompare("IsNull")
                .bottomMenu(NEXT)
                .assertButtonIsEnabled(false, "btnDelFilter_btn")
                .filterRecordsCheckbox()
                .assertButtonIsEnabled(true, "btnDelFilter_btn")
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .validateSelectedGroup();
    }

    @Test
    public void mqtt_gu_007() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("cust2")
                .selectCompare("Equal")
                .inputTextField("111")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .validateSelectedGroup()
                .assertElementsArePresent("lblNoSelectedCpes");
    }

    @Test
    public void mqtt_gu_008() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .createGroupButton()
                .fillName("mqtt_gu_006")
                .bottomMenu(NEXT)
                .assertElementsArePresent("lblNameInvalid");
    }

    @Test
    public void mqtt_gu_009() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo("mqtt_gu_006")
                .editGroupButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "mqtt_gu_006");
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
                .assertButtonsAreEnabled(true, NEXT)
                .clickOnTable("tblDevices", 1, 0)
                .assertButtonsAreEnabled(false, NEXT);
    }

    @Test
    //Doesn't work with Edge
    public void mqtt_gu_011() {
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
                .createDeviceGroup()
                .selectColumnFilter("device_created")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectSendTo(getTestName())
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
                .bottomMenu(NEXT)
                .scheduledTo()
                .timeHoursSelect("0")
                .bottomMenu(NEXT)
                .assertEqualsAlertMessage("Update can't be scheduled to the past")
                .checkIsCalendarClickable();
    }

    @Test
    public void mqtt_gu_020(){
        guPage
                .gotoSetParameters()    //bug? Advanced View button is absent from MQTT!!!
                .setAdvancedParameter("Device.FriendlySmartHome.GasDetector.1", 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void mqtt_gu_021(){
        guPage
                .gotoSetParameters()
                .setAdvancedParameter("Device.FriendlySmartHome.GasDetector.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void mqtt_gu_022(){
        guPage
                .gotoSetParameters()
                .setAdvancedParameter("Device.FriendlySmartHome.GasDetector.1", 99)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void mqtt_gu_023(){
        guPage
                .gotoSetParameters()
                .setAdvancedParameter("Device.FriendlySmartHome.Humidity.1", 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void mqtt_gu_024(){
        guPage
                .gotoSetParameters()
                .setAdvancedParameter("Device.FriendlySmartHome.Humidity.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void mqtt_gu_025(){
        guPage
                .gotoSetParameters()
                .setAdvancedParameter("Device.FriendlySmartHome.Humidity.1", 99)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void mqtt_gu_026(){
        guPage
                .gotoSetParameters()
                .setAdvancedParameter("Device.FriendlySmartHome.PowerMeter.1", 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void mqtt_gu_027(){
        guPage
                .gotoSetParameters()
                .setAdvancedParameter("Device.FriendlySmartHome.PowerMeter.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void mqtt_gu_028(){
        guPage
                .gotoSetParameters()
                .setAdvancedParameter("Device.FriendlySmartHome.PowerMeter.1", 99)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void mqtt_gu_029(){
        guPage
                .gotoSetParameters()
                .setAdvancedParameter("Device.FriendlySmartHome.Temperature.1", 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void mqtt_gu_030(){
        guPage
                .gotoSetParameters()
                .setAdvancedParameter("Device.FriendlySmartHome.Temperature.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void mqtt_gu_031(){
        guPage
                .gotoSetParameters()
                .setAdvancedParameter("Device.FriendlySmartHome.Temperature.1", 99)
                .nextSaveAndActivate()
                .validateAddedTasks();
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
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Reboot")
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Reboot");
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
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Reboot")
                .bottomMenu(NEXT)
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
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Factory reset")
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Factory reset");
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
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Factory reset")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "Client ID", EQUAL, "mqtt_demo")
                .saveAndActivate(false)
                .assertPresenceOfValue("tblTasks", 2, "Factory Reset");
    }

    @Test    //bug: Radiobutton  “Reprovision” is not available (V6.0.0 Build 156)
    public void mqtt_gu_036() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Device reprovision")
                .nextSaveAndActivate()
                .validateAddedTask("Device reprovision", "CPEReprovision");
    }

    @Test    //bug: Radiobutton  “Reprovision” is not available (V6.0.0 Build 156)
    public void mqtt_gu_037() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Device reprovision")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "Client ID", EQUAL, "mqtt_demo")
                .saveAndActivate(false)
                .assertPresenceOfValue("tblTasks", 2, "Device reprovision");
    }

    @Test
    public void mqtt_gu_039() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .bottomMenu(SAVE_IMPORT)
                .selectSendTo()
                .showList()
                .assertPresenceOfValue("tblDevices", 0, getSerial())
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp();
    }

    @Test
    public void mqtt_gu_040() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .bottomMenu(CANCEL)
                .assertElementsArePresent("tblParameters");
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
                .validateSorting("Manufacturer");
    }

    @Test
    public void mqtt_gu_045() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Model");
    }

    @Test
    public void mqtt_gu_046() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Name");
    }

    @Test
    public void mqtt_gu_047() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Name")
                .validateSorting("Created");
    }

    @Test
    public void mqtt_gu_048() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Creator");
    }

    @Test
    public void mqtt_gu_049() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Updated");
    }

    @Test
    public void mqtt_gu_050() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Activated");
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
                .itemsOnPage("10");
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
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Is not null")
                .bottomMenu(CANCEL)
                .assertElementsArePresent("lblHead");
    }

    @Test
    public void mqtt_gu_076() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .createGroupButton()
                .fillName()
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .bottomMenu(CANCEL)
                .assertAbsenceOfOptions("ddlSend", getTestName());
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
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Reboot")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Reboot");
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
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Reboot")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "Client ID", EQUAL, "mqtt_demo")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Reboot");
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
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Factory reset")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Factory reset");
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
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Factory reset")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "Client ID", EQUAL, "mqtt_demo")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Factory reset");
    }

    @Test    //bug: Radiobutton  “Reprovision” is not available (V6.0.0 Build 156)
    public void mqtt_gu_140() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Device reprovision")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTask("Device reprovision", "CPEReprovision");
    }

    @Test    //bug: Radiobutton  “Reprovision” is not available (V6.0.0 Build 156)
    public void mqtt_gu_141() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Device reprovision")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "Client ID", EQUAL, "mqtt_demo")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTask("Device reprovision", "CPEReprovision");
    }
}
