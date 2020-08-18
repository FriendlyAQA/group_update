package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.DataBaseConnector;
import com.friendly.aqa.utils.HttpConnector;
import com.friendly.aqa.utils.XmlWriter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.friendly.aqa.pageobject.BasePage.getManufacturer;
import static com.friendly.aqa.pageobject.BasePage.getModelName;
import static com.friendly.aqa.entities.GlobalButtons.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.IMPORT;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.NEW;
import static com.friendly.aqa.entities.ParameterType.VALUE;
import static com.friendly.aqa.entities.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Conditions.EQUAL;

@Listeners(UniversalVideoListener.class)
public class GroupUpdateLwm2mTests extends BaseTestCase {
    @Test
    public void lwm2m_gu_001() {
        guPage
                .topMenu(GROUP_UPDATE)
                .deleteAll()
                .waitForUpdate()
                .assertMainPageIsDisplayed();
    }

    @Test
    public void lwm2m_gu_002() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void lwm2m_gu_003() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .deleteFilterGroups()
                .bottomMenu(CANCEL)
                .waitForUpdate()
                .assertMainPageIsDisplayed();
    }

    @Test
    public void lwm2m_gu_004() {
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
    public void lwm2m_gu_005() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .createGroupButton()
                .assertTrue(guPage.isButtonPresent(FINISH))
                .bottomMenu(CANCEL)
                .waitForUpdate().pause(500)
                .assertEquals(guPage.getAttributeById("txtName", "value"), testName);
    }

    @Test
    public void lwm2m_gu_006() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("device_created")
                .selectCompare("IsNull")
                .bottomMenu(NEXT)
                .assertFalse(guPage.isButtonActive("btnDelFilter_btn")).filterRecordsCheckbox()
                .assertTrue(guPage.isButtonActive("btnDelFilter_btn"))
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertEquals(testName, guPage.getSelectedOption("ddlSend"));
    }

    @Test
    public void lwm2m_gu_007() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("cust2")
                .selectCompare("Equal")
                .inputTextField("111")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertEquals(testName, guPage.getSelectedOption("ddlSend"), "Created group isn't selected!\n")
                .assertTrue(guPage.isElementDisplayed("lblNoSelectedCpes"), "Warning 'No devices selected' isn't displayed!\n");
    }

    @Test
    public void lwm2m_gu_008() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .createGroupButton()
                .fillName("lwm2m_gu_006")
                .bottomMenu(NEXT)
                .assertTrue(guPage.isElementDisplayed("lblNameInvalid"), "Warning 'This name is already in use' isn't displayed!\n");
    }

    @Test
    public void lwm2m_gu_009() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo("lwm2m_gu_006")
                .editGroupButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertFalse(guPage.isOptionPresent("ddlSend", "lwm2m_gu_006"), "Option 'lwm2m_gu_006' is still present on 'Send to' list!\n");
    }

    @Test
    public void lwm2m_gu_010() {
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
    public void lwm2m_gu_011() {
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
    public void lwm2m_gu_012() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("device_created")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectSendTo(testName)
                .showList()
                .assertPresenceOfValue("tblDevices", 0, getSerial());
    }

    @Test
    public void lwm2m_gu_013() {
        guPage
                .gotoSetParameters(null)
                .assertPresenceOfElements("tblParamsValue")
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void lwm2m_gu_014() {
        guPage
                .gotoSetParameters()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5);
    }

    @Test
    public void lwm2m_gu_015() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("lwm2m_gu_014")
                .bottomMenu(EDIT)
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .clickOnTable("tblTasks", "Root.Device.0.UTC Offset")
                .setParameter("UTC Offset", VALUE, "+01:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoGroup("lwm2m_gu_014")
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_016() {
        guPage
                .gotoSetParameters()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate();
    }

    @Test
    public void lwm2m_gu_017() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("lwm2m_gu_016")
                .bottomMenu(EDIT)
                .assertFalse(guPage.isInputActive("ddlSend"))
                .bottomMenu(NEXT)
                .assertFalse(guPage.isInputActive("lrbImmediately"))
                .bottomMenu(NEXT)
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void lwm2m_gu_018() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .timeHoursSelect("0")
                .bottomMenu(NEXT)
                .assertEqualsAlertMessage("Update can't be scheduled to the past")
                .checkIsCalendarClickable();
    }

    @Test
    public void lwm2m_gu_019() throws IOException {
        guPage
                .topMenu(GROUP_UPDATE)
                .assertTrue(HttpConnector.sendGetRequest(guPage
                        .getGuExportLink("lwm2m_gu_016"))
                        .contains("\"Root.Device.0.UTC Offset\" value=\"+02:00\""));
    }

    @Test
    public void lwm2m_gu_020() {
        guPage
                .gotoSetParameters(null)
                .setParameter(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_021() {
        guPage
                .gotoSetParameters(null)
                .setParameter(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_022() {
        guPage
                .gotoSetParameters(null)
                .setAllParameters()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_023() {
        guPage
                .gotoSetParameters("Server")
                .setAllParameters()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_024() {
        guPage
                .gotoSetParameters("Server")
                .setParameter(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_025() {
        guPage
                .gotoSetParameters("Server")
                .setParameter(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_026() {
        guPage
                .gotoAction()
                .rebootRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void lwm2m_gu_027() {
        guPage
                .gotoAction()
                .rebootRadioButton()
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "Content format", EQUAL, "TLV/PLAIN")
                .saveAndActivate(false)
                .assertPresenceOfValue("tblTasks", 2, "Reboot");
    }

    @Test
    public void lwm2m_gu_028() {
        guPage
                .gotoAction()
                .factoryResetRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Factory reset");
    }

    @Test
    public void lwm2m_gu_029() {
        guPage
                .gotoAction()
                .factoryResetRadioButton()
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "Content format", EQUAL, "TLV/PLAIN")
                .saveAndActivate(false)
                .assertPresenceOfValue("tblTasks", 2, "Factory reset");
    }

    @Test // bug: group state is 'Not active' instead of 'Completed'
    public void lwm2m_gu_030() {
        guPage
                .gotoAction()
                .resetMinMaxValues()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Reset Min and Max Measured Values");
    }

    @Test // bug: group state is 'Not active' instead of 'Completed'
    public void lwm2m_gu_031() {
        guPage
                .gotoAction()
                .resetCumulativeEnergy()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Reset Cumulative energy");
    }

    @Test // bug: group state is 'Not active' instead of 'Completed'
    public void lwm2m_gu_032() {
        guPage
                .gotoAction()
                .resetErrors()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Reset errors");
    }

    @Test // bug: group state is 'Not active' instead of 'Completed'
    public void lwm2m_gu_033() {
        guPage
                .gotoAction()
                .disableRadiobutton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Disable");
    }

    @Test // bug: group state is 'Not active' instead of 'Completed'
    public void lwm2m_gu_034() {
        guPage
                .gotoAction()
                .radioRegistrationUpdateTrigger()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Registration update trigger");
    }

    @Test // bug: group state is 'Not active' instead of 'Completed'
    public void lwm2m_gu_035() {
        guPage
                .gotoAction()
                .radioStartOrReset()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Start or reset");
    }

    @Test
    public void lwm2m_gu_036() {
        guPage
                .gotoAction()
                .reprovisionRadioButton()
                .nextSaveAndActivate()
                .checkAddedTask("Device reprovision", "CPEReprovision");
    }

    @Test
    public void lwm2m_gu_037() {
        guPage
                .gotoSetParameters(null, true)
                .setAllParameters()
                .setAnyAdvancedParameter()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_038() {
        guPage
                .gotoSetParameters(null, true)
                .setParameter(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_039() {
        guPage
                .gotoSetParameters(null, true)
                .setParameter(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_040() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .onlineDevicesCheckBox()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .clickOnTable("tabsSettings_tblTabs", "Device")
                .setParameter(1)
                .nextSaveAndActivate(false)
                .assertOnlineDevices()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_041() {
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
    public void lwm2m_gu_042() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .bottomMenu(CANCEL)
                .assertPresenceOfElements("tblParameters");
    }

    @Test
    public void lwm2m_gu_043() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Manufacturer", getManufacturer());
    }

    @Test
    public void lwm2m_gu_044() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Model", getModelName());
    }

    @Test
    public void lwm2m_gu_045() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Completed")
                .checkFiltering("State", "Not active")
                .checkFiltering("State", "Error")
                .checkFiltering("State", "All");
    }

    @Test
    public void lwm2m_gu_046() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Manufacturer");
    }

    @Test
    public void lwm2m_gu_047() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Model");
    }

    @Test
    public void lwm2m_gu_048() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name");
    }

    @Test
    public void lwm2m_gu_049() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name")
                .checkSorting("Created");
    }

    @Test
    public void lwm2m_gu_050() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Creator");
    }

    @Test
    public void lwm2m_gu_051() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Updated");
    }

    @Test
    public void lwm2m_gu_052() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Activated");
    }

    @Test
    public void lwm2m_gu_053() {
        guPage
                .topMenu(GROUP_UPDATE)
                .selectManufacturer()
                .checkResetView();
    }

    @Test
    public void lwm2m_gu_054() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("Manufacturer")
                .checkResetView();
    }

    @Test
    public void lwm2m_gu_055() {
        guPage
                .gotoSetParameters(null)
                .setParameter(1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatusWithoutRefresh("Completed", 65)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_056() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Scheduled");
    }

    @Test
    public void lwm2m_gu_057() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Running");
    }

    @Test
    public void lwm2m_gu_058() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Paused");
    }

    @Test
    public void lwm2m_gu_059() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Reactivation");
    }

    @Test
    public void lwm2m_gu_060() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("device_created")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertTrue(guPage.isElementDisplayed("lblNoSelectedCpes"), "Cannot find label'No devices selected'!\n");
    }

    @Test
    public void lwm2m_gu_061() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("device_created")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Is not null'!\n").bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_062() {
        DataBaseConnector.createFilterPreconditions(getSerial());
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("On Day")
                .clickOn("calFilterDate_image")
                .selectDate(CalendarUtil.getShiftedDate(-10))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - On Day'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_063() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Prior to")
                .clickOn("calFilterDate_image")
                .selectDate(CalendarUtil.getShiftedDate(-9))
                .inputText("txtTimeHour", CalendarUtil.getHours())
                .inputText("txtTimeMinute", CalendarUtil.getMinutes())
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Prior to'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_064() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Later than")
                .clickOn("calFilterDate_image")
                .selectDate(CalendarUtil.getShiftedDate(-9))
                .inputText("txtTimeHour", CalendarUtil.getHours())
                .inputText("txtTimeMinute", CalendarUtil.getMinutes())
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Later than'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_065() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Today")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Today'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_066() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Before Today")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Before Today'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_067() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Yesterday")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Yesterday'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_068() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Prev 7 days")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Prev 7 days'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_069() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Prev X days")
                .inputText("txtInt", "9")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Prev X days'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_070() {
        guPage
                .presetFilter("mycust03", testName)
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("=")
                .inputText("txtText", testName)
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - equals'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_071() {
        guPage
                .presetFilter("mycust03", testName)
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("!=")
                .inputText("txtText", "123")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - not equals'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_072() {
        guPage
                .presetFilter("mycust03", testName)
                .createDeviceGroup()
                .selectColumnFilter("Hardware version")
                .selectCompare("Starts with")
                .inputText("txtText", testName)
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertTrue(guPage.isElementDisplayed("lblNoSelectedCpes"), "Cannot find label'No devices selected'!\n");
    }

    @Test
    public void lwm2m_gu_073() {
        guPage
                .presetFilter("mycust03", testName)
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Like")
                .inputText("txtText", testName.substring(1, 5))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Like'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_074() {
        guPage
                .presetFilter("mycust03", testName)
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("No like")
                .inputText("txtText", "abc")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - No like'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_075() {
        guPage
                .presetFilter("mycust03", "")
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Is null'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_076() {
        guPage
                .presetFilter("mycust03", testName)
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Is not null'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_077() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Is not null")
                .bottomMenu(CANCEL)
                .waitForUpdate()
                .assertTrue(guPage.isElementDisplayed("lblHead"), "Filter creation didn't cancel properly!\n");
    }

    @Test
    public void lwm2m_gu_078() {
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
                .waitForUpdate()
                .assertFalse(guPage.isOptionPresent("ddlSend", testName), "Option '" + testName + "' is present on 'Send to' list!\n");
    }

    @Test
    public void lwm2m_gu_079() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_080() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_081() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_082() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_083() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setPeriod(2)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_084() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setPeriod(2)
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_085() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .onlineDevicesCheckBox()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_086() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_087() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_088() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_089() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_090() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_091() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .setPeriod(2)
                .onlineDevicesCheckBox()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_092() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .setPeriod(2)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_093() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setThreshold(50)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_094() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setThreshold(50)
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_095() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .selectShiftedDate("calDate", 1)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_096() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_097() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .selectShiftedDate("calReactivationStartsOnDay", 2)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_098() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_099() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "1")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_100() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 8)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_101() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_102() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_103() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .selectShiftedDate("calReactivationStartsOnDay", 2)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_104() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_105() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_106() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 8)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_107() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test   //bug: Test fails if run in Friday :)
    public void lwm2m_gu_108() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Weekly")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test   //bug: Test fails if run in Friday :)
    public void lwm2m_gu_109() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .selectRepeatsDropDown("Weekly")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test   //bug: Test fails if run in Friday :)
    public void lwm2m_gu_110() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .selectRepeatsDropDown("Weekly")
                .endAOnRadiobutton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test   //bug: Test fails if run in Friday :)
    public void lwm2m_gu_111() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Weekly")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test   //bug: Test fails if run in Friday :)
    public void lwm2m_gu_112() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Weekly")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 32)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test   //bug: Test fails if run in Friday :)
    public void lwm2m_gu_113() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Weekly")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_114() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_115() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .selectShiftedDate("calReactivationStartsOnDay", 31)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_116() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_117() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_118() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 31)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_119() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_120() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_121() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .selectShiftedDate("calReactivationStartsOnDay", 2)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_122() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_123() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 365)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_124() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_125() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_126() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .setParameter("Timezone", VALUE, "Europe/Kharkov1")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_127() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_128() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(99)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_129() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(99)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_130() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("Server", "Default Maximum Period", VALUE, "10")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_131() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("Server", 2)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_132() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .rebootRadioButton()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void lwm2m_gu_133() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .rebootRadioButton()
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "Binding mode", EQUAL, "60")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfValue("tblTasks", 2, "Reboot");
    }

    @Test
    public void lwm2m_gu_134() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .factoryResetRadioButton()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Factory reset");
    }

    @Test
    public void lwm2m_gu_135() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .factoryResetRadioButton()
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "Binding mode", EQUAL, "60")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Factory reset");
    }

    @Test // bug: group state is 'Not active' instead of 'Scheduled'
    public void lwm2m_gu_136() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .resetMinMaxValues()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Reset Min and Max Measured Values");
    }

    @Test // bug: group state is 'Not active' instead of 'Scheduled'
    public void lwm2m_gu_137() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .resetCumulativeEnergy()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Reset Cumulative energy");
    }

    @Test // bug: group state is 'Not active' instead of 'Scheduled'
    public void lwm2m_gu_138() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .resetErrors()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Reset errors");
    }

    @Test // bug: group state is 'Not active' instead of 'Scheduled'
    public void lwm2m_gu_139() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .disableRadiobutton()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Disable");
    }

    @Test // bug: group state is 'Not active' instead of 'Scheduled'
    public void lwm2m_gu_140() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .radioRegistrationUpdateTrigger()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Registration update trigger");
    }

    @Test // bug: group state is 'Not active' instead of 'Scheduled'
    public void lwm2m_gu_141() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .radioStartOrReset()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Start or reset");
    }

    @Test
    public void lwm2m_gu_142() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .reprovisionRadioButton()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device reprovision", "CPEReprovision");
    }

    @Test
    public void lwm2m_gu_143() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAllParameters()
                .setAnyAdvancedParameter()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_144() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setParameter(1)
                .setAnyAdvancedParameter()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_145() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setParameter(2)
                .setAnyAdvancedParameter()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }
}
