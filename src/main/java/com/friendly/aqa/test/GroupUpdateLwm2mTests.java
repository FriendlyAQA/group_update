package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.DataBaseConnector;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.ParameterType.VALUE;
import static com.friendly.aqa.entities.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Conditions.EQUAL;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.IMPORT;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.NEW;

@Listeners(UniversalVideoListener.class)
public class GroupUpdateLwm2mTests extends BaseTestCase {

    @Test
    public void lwm2m_gu_000() {
        guPage
                .deleteAll()
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .deleteFilterGroups();
    }

    @Test
    public void lwm2m_gu_001() {
        guPage
                .topMenu(GROUP_UPDATE)
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
                .bottomMenu(CANCEL)
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
                .addModelButton()
                .selectSendTo()
                .showList()
                .assertDevicesArePresent();
    }

    @Test
    public void lwm2m_gu_005() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .createGroupButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .bottomMenu(CANCEL)
                .pause(1000)
                .validateName();
    }

    @Test
    public void lwm2m_gu_006() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
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
    public void lwm2m_gu_007() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("cust2")
                .selectCompare("Equal")
                .inputTextField("111")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .validateSelectedGroup()
                .assertNoDeviceAlertIsPresent()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, SAVE, SAVE_AND_ACTIVATE);
    }

    @Test
    public void lwm2m_gu_008() {//depends on 006
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .createGroupButton()
                .fillGroupName("lwm2m_gu_006")
                .bottomMenu(NEXT)
                .assertElementsArePresent("lblNameInvalid");
    }

    @Test
    public void lwm2m_gu_009() {//depends on 006
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .selectSendTo("lwm2m_gu_006")
                .editGroupButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "usp_gu_006");
    }

    @Test
    public void lwm2m_gu_010() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Device reprovision")
                .saveButton(true)
                .selectSendTo("Individual")
                .selectButton()
                .selectDevice(2)
                .closePopup()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .validateDevicesAmountMessage()
                .okButtonPopUp()
                .enterIntoGroup()
                .validateDetails()
                .bottomMenu(EDIT)
                .showListButton()
                .validateDevicesAmount();
    }

    @Test
    public void lwm2m_gu_011() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .selectSendTo("Import")
                .selectImportDevicesFile()
                .showList()
                .assertDeviceIsPresent();
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
                .selectSendTo(getTestName())
                .showList()
                .assertDeviceIsPresent();
    }

    @Test
    public void lwm2m_gu_013() {
        guPage
                .gotoSetParameters()
                .assertElementsArePresent(false, "tblParamsValue")
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void lwm2m_gu_014() {
        guPage
                .gotoSetParameters()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5);
    }

    @Test//depends on 14
    public void lwm2m_gu_015() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("lwm2m_gu_014")
                .bottomMenu(EDIT)
                .clickOnTask("Root.Device.0.UTC Offset")
                .setParameter("UTC Offset", VALUE, "+01:00")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoGroup("lwm2m_gu_014")
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void lwm2m_gu_016() {
        guPage
                .gotoSetParameters()
                .setParameter("UTC Offset", VALUE, "+02:00")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate();
    }

    @Test
    public void lwm2m_gu_017() {//depends on 16
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("lwm2m_gu_016")
                .bottomMenu(EDIT)
                .assertInputIsDisabled("ddlSend")
                .bottomMenu(NEXT)
                .assertInputIsDisabled("lrbImmediately")
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void lwm2m_gu_018() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .timeHoursSelect("0")
                .assertSummaryTextEquals("Update can't be scheduled to the past")
                .checkIsCalendarClickable();
    }

    @Test
    public void lwm2m_gu_019() {//depends on 16
        guPage
                .topMenu(GROUP_UPDATE)
                .checkExportLink();
    }

    @Test
    public void lwm2m_gu_020() {
        guPage
                .gotoSetParameters()
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_021() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_022() {
        guPage
                .gotoSetParameters()
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_023() {
        guPage
                .gotoSetParameters("Server")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_024() {
        guPage
                .gotoSetParameters("Server")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_025() {
        guPage
                .gotoSetParameters("Server")
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_026() {
        guPage
                .gotoAction()
                .selectAction("Reboot")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_027() {
        guPage
                .gotoAction()
                .selectAction("Reboot")
                .saveButton()
                .addCondition("ManagementServer", "Content format", EQUAL, "TLV/PLAIN")
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate(false)
                .bottomMenu(EDIT)
                .validateTask()
                .assertConditionIsPresent();
    }

    @Test
    public void lwm2m_gu_028() {
        guPage
                .gotoAction()
                .selectAction("Factory reset")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_029() {
        guPage
                .gotoAction()
                .selectAction("Factory reset")
                .saveButton()
                .addCondition("ManagementServer", "Content format", EQUAL, "TLV/PLAIN")
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate(false)
                .bottomMenu(EDIT)
                .validateTask()
                .assertConditionIsPresent();
    }

    @Test
    public void lwm2m_gu_030() {
        guPage
                .gotoAction()
                .selectAction("Root.IPSO_Temperature.i.Reset Min and Max Measured Values 0 instance")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_031() {
        guPage
                .gotoAction()
                .selectAction("Root.IPSO_Power Measurement.i.Reset Cumulative energy 0 instance")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_032() {
        guPage
                .gotoAction()
                .selectAction("Reset errors")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_033() {
        guPage
                .gotoAction()
                .selectAction("Disable")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_034() {
        guPage
                .gotoAction()
                .selectAction("Registration update trigger")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_035() {
        guPage
                .gotoAction()
                .selectAction("Root.Connectivity Statistics.i.Start 0 instance")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test // bug: Device reprovision is absent from Action list
    public void lwm2m_gu_036() {
        guPage
                .gotoAction()
                .selectAction("Device reprovision")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_037() {
        guPage
                .gotoSetParameters("Device", true)
                .setAllParameters()
                .selectBranch("Root.LWM2M Server.0")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_038() {
        guPage
                .gotoSetParameters("Device", true)
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_039() {
        guPage
                .gotoSetParameters("Device", true)
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_040() {
        guPage
                .gotoSetParameters()
                .setParameter("Device", 1)
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .onlineDevicesCheckBox()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoGroup()
                .validateDetails()
                .assertOnlineDevices()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void lwm2m_gu_041() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .bottomMenu(SAVE_IMPORT)
                .selectSendTo()
                .showList()
                .assertDeviceIsPresent()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp();
    }

    @Test
    public void lwm2m_gu_042() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .bottomMenu(CANCEL)
                .assertElementsArePresent("tblParameters");
    }

    @Test
    public void lwm2m_gu_043() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Manufacturer");
    }

    @Test
    public void lwm2m_gu_044() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Model");
    }

    @Test
    public void lwm2m_gu_045() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Completed")
                .checkFilteringByState("Not active")
                .checkFilteringByState("Error")
                .checkFilteringByState("All");
    }

    @Test   //irrelevant ("Manufacturer" column is absent)
    public void lwm2m_gu_046() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Manufacturer");
    }

    @Test   //irrelevant ("Model" column is absent)
    public void lwm2m_gu_047() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Model");
    }

    @Test
    public void lwm2m_gu_048() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Name");
    }

    @Test
    public void lwm2m_gu_049() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Created");
    }

    @Test
    public void lwm2m_gu_050() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Creator");
    }

    @Test
    public void lwm2m_gu_051() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Updated");
    }

    @Test
    public void lwm2m_gu_052() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Activated");
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
                .clickOnHeader("Name")
                .checkResetView();
    }

    @Test
    public void lwm2m_gu_055() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatusWithoutRefresh("Completed", 65)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void lwm2m_gu_056() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Scheduled");
    }

    @Test
    public void lwm2m_gu_057() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Running");
    }

    @Test
    public void lwm2m_gu_058() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Paused");
    }

    @Test
    public void lwm2m_gu_059() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Reactivation");
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
                .assertNoDeviceAlertIsPresent();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_070() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("=")
                .inputText("txtText", getTestName())
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_071() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("!=")
                .inputText("txtText", getTestName())
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_072() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("Hardware version")
                .selectCompare("Starts with")
                .inputText("txtText", getTestName())
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertNoDeviceAlertIsPresent();
    }

    @Test
    public void lwm2m_gu_073() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Like")
                .inputText("txtText", getTestName().substring(1, 5))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_074() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("No like")
                .inputText("txtText", getTestName().substring(1, 5))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_076() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void lwm2m_gu_077() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Is not null")
                .bottomMenu(CANCEL)
                .assertElementsArePresent("lblHead");
    }

    @Test
    public void lwm2m_gu_078() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .createGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .bottomMenu(CANCEL)
                .assertAbsenceOfOptions("ddlSend", getTestName());
    }

    @Test
    public void lwm2m_gu_079() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_080() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .waitUntilConnect()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_081() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_082() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .waitUntilConnect()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_083() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .setPeriod(2)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_084() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .setPeriod(2)
                .waitUntilConnect()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_085() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .onlineDevicesCheckBox()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_086() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .onlineDevicesCheckBox()
                .waitUntilConnect()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_087() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_088() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .waitUntilConnect()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_089() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void lwm2m_gu_090() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .waitUntilConnect()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void lwm2m_gu_091() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .setPeriod(2)
                .onlineDevicesCheckBox()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void lwm2m_gu_092() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .setPeriod(2)
                .onlineDevicesCheckBox()
                .waitUntilConnect()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void lwm2m_gu_093() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .setThreshold(50)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_094() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .setThreshold(50)
                .waitUntilConnect()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_095() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .scheduleInDays(1)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_096() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Hourly")
                .repeatEveryHours("1")
                .startsOnDayDelay(0)
                .startOnTimeDelay(20)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_097() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Hourly")
                .repeatEveryHours("1")
                .startsOnDayDelay(1)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_098() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Hourly")
                .repeatEveryHours("2")
                .startOnTimeDelay(20)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_099() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Hourly")
                .repeatEveryHours("1")
                .startOnTimeDelay(20)
                .endsAfter()
                .numberOfReactivations("2")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_100() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Hourly")
                .repeatEveryHours("1")
                .startOnTimeDelay(20)
                .reactivationEndsOn()
                .endsOnDayDelay(7)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_101() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Hourly")
                .repeatEveryHours("1")
                .startOnTimeDelay(20)
                .reRunOnFailed()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_102() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Daily")
                .repeatEveryDays("1")
                .startOnTimeDelay(20)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_103() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Daily")
                .repeatEveryDays("1")
                .startsOnDayDelay(2)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_104() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Daily")
                .repeatEveryDays("2")
                .startOnTimeDelay(20)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_105() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Daily")
                .repeatEveryDays("1")
                .endsAfter()
                .endsAfter("2")
                .startOnTimeDelay(20)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_106() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Daily")
                .repeatEveryDays("1")
                .reactivationEndsOn()
                .startOnTimeDelay(20)
                .endsOnDayDelay(7)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_107() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Daily")
                .repeatEveryDays("1")
                .startOnTimeDelay(20)
                .reRunOnFailed()
                .saveAndValidateScheduledTasks();
    }

    @Test   //bug: Test fails if run in Friday :)))
    public void lwm2m_gu_108() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Weekly")
                .startOnTimeDelay(20)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_109() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Weekly")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_110() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Weekly")
                .reactivationEndsOn()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_111() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Weekly")
                .startOnTimeDelay(20)
                .endsAfter()
                .numberOfReactivations("2")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_112() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Weekly")
                .startOnTimeDelay(20)
                .reactivationEndsOn()
                .endsOnDayDelay(32)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_113() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Weekly")
                .startOnTimeDelay(20)
                .reRunOnFailed()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_114() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Monthly")
                .repeatEveryMonth("1")
                .startOnTimeDelay(20)
                .saveAndValidateScheduledTasks();
    }

    @Test   //bug: wednesday, 30.06.2021 An unexpected occurrence happened. Log file updated.
    public void lwm2m_gu_115() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Monthly")
                .repeatEveryMonth("1")
                .startsOnDayDelay(31)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_116() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Monthly")
                .repeatEveryMonth("2")
                .startsOnDayDelay(0)
                .startOnTimeDelay(20)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_117() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Monthly")
                .repeatEveryMonth("1")
                .startOnTimeDelay(20)
                .endsAfter()
                .numberOfReactivations("2")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_118() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Monthly")
                .repeatEveryMonth("1")
                .startOnTimeDelay(20)
                .reactivationEndsOn()
                .endsOnDayDelay(31)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_119() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Monthly")
                .repeatEveryMonth("1")
                .startOnTimeDelay(20)
                .reRunOnFailed()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_120() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Yearly")
                .startOnTimeDelay(20)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_121() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Yearly")
                .startsOnDayDelay(2)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_122() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Yearly")
                .startOnTimeDelay(20)
                .endsAfter()
                .numberOfReactivations("2")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_123() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Yearly")
                .startOnTimeDelay(20)
                .reactivationEndsOn()
                .endsOnDayDelay(365)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_124() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Yearly")
                .startOnTimeDelay(20)
                .reRunOnFailed()
                .saveAndValidateScheduledTasks();
    }

    @Test   //79==125==127
    public void lwm2m_gu_125() {
        guPage
                .gotoSetParameters()
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_126() {
        guPage
                .gotoSetParameters("Device")
                .setParameter(2)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_127() {
        guPage
                .gotoSetParameters("Device")
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_128() {
        guPage
                .gotoSetParameters("Device")
                .setAllParameters()
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_129() {
        guPage
                .gotoSetParameters("Server")
                .setAllParameters()
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_130() {
        guPage
                .gotoSetParameters("Server")
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_131() {
        guPage
                .gotoSetParameters("Server")
                .setParameter(2)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_132() {
        guPage
                .gotoAction()
                .selectAction("Reboot")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_133() {
        guPage
                .gotoAction()
                .selectAction("Reboot")
                .saveButton()
                .addCondition("ManagementServer", "Content format", EQUAL, "TLV/PLAIN")
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks()
                .assertConditionIsPresent();
    }

    @Test
    public void lwm2m_gu_134() {
        guPage
                .gotoAction()
                .selectAction("Factory reset")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_135() {
        guPage
                .gotoAction()
                .selectAction("Factory reset")
                .saveButton()
                .addCondition("ManagementServer", "Content format", EQUAL, "TLV/PLAIN")
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks()
                .assertConditionIsPresent();
    }

    @Test
    public void lwm2m_gu_136() {
        guPage
                .gotoAction()
                .selectAction("Root.IPSO_Temperature.i.Reset Min and Max Measured Values 0 instance")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_137() {
        guPage
                .gotoAction()
                .selectAction("Root.IPSO_Power Measurement.i.Reset Cumulative energy 0 instance")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_138() {
        guPage
                .gotoAction()
                .selectAction("Reset errors")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_139() {
        guPage
                .gotoAction()
                .selectAction("Disable")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_140() {
        guPage
                .gotoAction()
                .selectAction("Registration update trigger")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_141() {
        guPage
                .gotoAction()
                .selectAction("Root.Connectivity Statistics.i.Start 0 instance")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_142() {
        guPage
                .gotoAction()
                .selectAction("Device reprovision")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_143() {
        guPage
                .gotoSetParameters()
                .advancedViewButton()
                .setAdvancedParameter("Root.IPSO_Power Measurement.0", 99)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_144() {
        guPage
                .gotoSetParameters()
                .advancedViewButton()
                .setAdvancedParameter("Root.IPSO_Power Measurement.0", 1)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void lwm2m_gu_145() {
        guPage
                .gotoSetParameters()
                .advancedViewButton()
                .setAdvancedParameter("Root.IPSO_Power Measurement.0", 2)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }
}
