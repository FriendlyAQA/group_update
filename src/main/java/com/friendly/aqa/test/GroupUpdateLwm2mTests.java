package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
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
                .globalButtons(CANCEL)
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
                .globalButtons(CANCEL)
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
                .createGroup()
                .assertTrue(guPage.isButtonPresent(FINISH))
                .globalButtons(CANCEL)
                .waitForUpdate().pause(500)
                .assertEquals(guPage.getAttributeById("txtName", "value"), testName);
    }

    @Test
    public void lwm2m_gu_006() {
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
    public void lwm2m_gu_007() {
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
    public void lwm2m_gu_008() {
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
    public void lwm2m_gu_009() {
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
    public void lwm2m_gu_013() {
        guPage
                .gotoSetParameters(null)
                .assertElementIsPresent("tblParamsValue")
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void lwm2m_gu_014() {
        guPage
                .gotoSetParameters()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5);
        setTargetTestName();
    }

    @Test
    public void lwm2m_gu_015() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup(targetTestName)
                .globalButtons(EDIT)
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .clickOnTable("tblTasks", "Root.Device.0.UTC Offset")
                .setParameter("UTC Offset", VALUE, "+01:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoGroup(targetTestName)
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_016() {
        guPage
                .gotoSetParameters()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate();
        setTargetTestName();
    }

    @Test
    public void lwm2m_gu_017() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup(targetTestName)
                .globalButtons(EDIT)
                .assertFalse(guPage.isInputActive("ddlSend"))
                .globalButtons(NEXT)
                .assertFalse(guPage.isInputActive("lrbImmediately"))
                .globalButtons(NEXT)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .timeHoursSelect("0")
                .globalButtons(NEXT)
                .assertEqualsAlertMessage("Update can't be scheduled to the past")
                .checkIsCalendarClickable();
    }

    @Test
    public void lwm2m_gu_019() throws IOException {
        guPage
                .topMenu(GROUP_UPDATE)
                .assertTrue(HttpConnector.sendGetRequest(guPage
                        .getGuExportLink(targetTestName))
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
                .globalButtons(NEXT)
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
                .assertPresenceOfParameter("Factory Reset");
    }

    @Test
    public void lwm2m_gu_029() {
        guPage
                .gotoAction()
                .factoryResetRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "Content format", EQUAL, "TLV/PLAIN")
                .saveAndActivate(false)
                .assertPresenceOfValue("tblTasks", 2, "Factory Reset");
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
                .radioDisable()
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
                .assertPresenceOfParameter("Device Reprovision");
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
                .globalButtons(NEXT)
                .immediately()
                .onlineDevicesCheckBox()
                .globalButtons(NEXT)
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
                .globalButtons(CANCEL)
                .assertElementIsPresent("tblParameters");
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
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
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
                .gotoAddFilter()
                .selectColumnFilter("device_created")
                .compareSelect("Is null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertTrue(guPage.isElementDisplayed("lblNoSelectedCpes"), "Cannot find label'No devices selected'!\n");
    }

    @Test
    public void lwm2m_gu_061() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("device_created")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Is not null'!\n").globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_062() {
        DataBaseConnector.createFilterPreconditions(BasePage.getSerial());
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("On Day")
                .clickOn("calFilterDate_image")
                .selectDate(CalendarUtil.getShiftedDate(-10))
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - On Day'!\n")
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_063() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Prior to")
                .clickOn("calFilterDate_image")
                .selectDate(CalendarUtil.getShiftedDate(-9))
                .inputText("txtTimeHour", CalendarUtil.getHours())
                .inputText("txtTimeMinute", CalendarUtil.getMinutes())
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Prior to'!\n")
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_064() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Later than")
                .clickOn("calFilterDate_image")
                .selectDate(CalendarUtil.getShiftedDate(-9))
                .inputText("txtTimeHour", CalendarUtil.getHours())
                .inputText("txtTimeMinute", CalendarUtil.getMinutes())
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Later than'!\n")
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_065() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Today")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Today'!\n")
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_066() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Before Today")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Before Today'!\n")
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_067() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Yesterday")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Yesterday'!\n")
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_068() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Prev 7 days")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Prev 7 days'!\n")
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_069() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Prev X days")
                .inputText("txtInt", "9")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Prev X days'!\n")
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
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
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("=")
                .inputText("txtText", testName)
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - equals'!\n")
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
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
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("!=")
                .inputText("txtText", "123")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - not equals'!\n")
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
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
                .gotoAddFilter()
                .selectColumnFilter("Hardware version")
                .compareSelect("Starts with")
                .inputText("txtText", testName)
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertTrue(guPage.isElementDisplayed("lblNoSelectedCpes"), "Cannot find label'No devices selected'!\n");
    }

    @Test
    public void lwm2m_gu_073() {
        guPage
                .presetFilter("mycust03", testName)
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("Like")
                .inputText("txtText", testName.substring(1, 5))
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Like'!\n")
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
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
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("No like")
                .inputText("txtText", "abc")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - No like'!\n")
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
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
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("Is null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Is null'!\n")
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
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
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Is not null'!\n")
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_077() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Is not null")
                .globalButtons(CANCEL)
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
                .createGroup()
                .fillName()
                .globalButtons(NEXT)
                .globalButtons(PREVIOUS)
                .globalButtons(CANCEL)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setPeriod(2)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setPeriod(2)
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .onlineDevicesCheckBox()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .immediately()
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .immediately()
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .immediately()
                .setPeriod(1)
                .setPeriod(2)
                .onlineDevicesCheckBox()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .immediately()
                .setPeriod(1)
                .setPeriod(2)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setThreshold(50)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setThreshold(50)
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .selectShiftedDate("calDate", 1)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .selectShiftedDate("calReactivationStartsOnDay", 2)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("2")
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "1")
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 8)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .runOnFailed()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .selectShiftedDate("calReactivationStartsOnDay", 2)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("2")
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 8)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .runOnFailed()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_108() {
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
                .selectRepeatsDropDown("Weekly")
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_109() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .selectRepeatsDropDown("Weekly")
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_110() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .selectRepeatsDropDown("Weekly")
                .endAOnRadiobutton()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_111() {
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
                .selectRepeatsDropDown("Weekly")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_112() {
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
                .selectRepeatsDropDown("Weekly")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 32)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void lwm2m_gu_113() {
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
                .selectRepeatsDropDown("Weekly")
                .runOnFailed()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .selectShiftedDate("calReactivationStartsOnDay", 31)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("2")
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 31)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .runOnFailed()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .selectShiftedDate("calReactivationStartsOnDay", 2)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 365)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .runOnFailed()
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .setParameter("Timezone", VALUE, "Europe/Kharkov1")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(99)
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(99)
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("Server", "Default Maximum Period", VALUE, "10")
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("Server", 2)
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .rebootRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "Binding mode", EQUAL, "60")
                .globalButtons(SAVE)
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
                .assertPresenceOfParameter("Factory Reset");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .factoryResetRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "Binding mode", EQUAL, "60")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Factory Reset");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .resetMinMaxValues()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .resetCumulativeEnergy()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .resetErrors()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .radioDisable()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .radioRegistrationUpdateTrigger()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .radioStartOrReset()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .assertPresenceOfParameter("Device Reprovision");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .globalButtons(ADVANCED_VIEW)
                .setAllParameters()
                .setAnyAdvancedParameter()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .globalButtons(ADVANCED_VIEW)
                .setParameter(1)
                .setAnyAdvancedParameter()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .globalButtons(ADVANCED_VIEW)
                .setParameter(2)
                .setAnyAdvancedParameter()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }
}
