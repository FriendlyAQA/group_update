package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.DataBaseConnector;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Conditions.NOT_EQUAL;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.*;

@Listeners(UniversalVideoListener.class)
public class GroupUpdateUspTests extends BaseTestCase {

    @Test
    public void usp_gu_000() {
        guPage
                .deleteAll()
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .deleteFilterGroups();
    }

    @Test
    public void usp_gu_001() {
        guPage
                .topMenu(GROUP_UPDATE)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void usp_gu_002() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void usp_gu_003() {
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
    public void usp_gu_004() {
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
    public void usp_gu_005() {
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
    public void usp_gu_006() {
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
    public void usp_gu_007() {
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
    public void usp_gu_008() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .createGroupButton()
                .fillName("usp_gu_006")
                .bottomMenu(NEXT)
                .assertElementsArePresent("lblNameInvalid");
    }

    @Test
    public void usp_gu_009() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo("usp_gu_006")
                .editGroupButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "usp_gu_006");
    }

    @Test
    public void usp_gu_010() {
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
    public void usp_gu_011() {
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
    public void usp_gu_012() {
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
    public void usp_gu_013() {
        guPage
                .gotoSetParameters()
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void usp_gu_014() {
        guPage
                .gotoSetParameters()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5);
    }

    @Test
    public void usp_gu_015() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("usp_gu_014")
                .bottomMenu(EDIT)
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .clickOnTable("tblTasks", 1, 1)
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoGroup("usp_gu_014")
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_016() {
        guPage
                .gotoSetParameters()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate();
    }

    @Test
    public void usp_gu_017() {//depends on 16
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("usp_gu_016")
                .bottomMenu(EDIT)
                .assertInputIsDisabled("ddlSend")
                .bottomMenu(NEXT)
                .assertInputIsDisabled("lrbImmediately")
                .bottomMenu(NEXT)
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void usp_gu_018() {
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
    public void usp_gu_019() {//depends on 16
        guPage
                .topMenu(GROUP_UPDATE)
                .checkExportLink();
    }

    @Test
    public void usp_gu_020() {
        guPage
                .gotoSetParameters()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_021() {
        guPage
                .gotoSetParameters()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_022() {
        guPage
                .gotoSetParameters()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 99)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_023() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName(getTestName())
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
    public void usp_gu_024() {
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
                .addCondition(1, "ManagementServer", "NodeAddr", NOT_EQUAL, "127.0.0.1")
                .saveAndActivate(false)
                .assertPresenceOfValue("tblTasks", 2, "Reboot");
    }

    @Test
    public void usp_gu_025() {
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
    public void usp_gu_026() {
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
                .addCondition(1, "ManagementServer", "NodeAddr", NOT_EQUAL, "127.0.0.1")
                .saveAndActivate(false)
                .assertPresenceOfValue("tblTasks", 2, "Factory reset");
    }

    @Test
    public void usp_gu_027() {
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
//                .assertPresenceOfParameter("CPEReprovision")
                .validateAddedTask("Device reprovision", "CPEReprovision");
    }

    @Test
    public void usp_gu_028() {
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
                .addCondition(1, "ManagementServer", "NodeAddr", NOT_EQUAL, "127.0.0.1")
                .saveAndActivate(false)
//                .assertPresenceOfValue("tblTasks", 2, "CPEReprovision");
                .validateAddedTask("Device reprovision", "CPEReprovision");
    }

    @Test
    public void usp_gu_029() {
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
                .selectAction("Onboard request", "1")
                .nextSaveAndActivate()
                .validateAddedTask();
    }

    @Test
    public void usp_gu_030() {
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
                .selectAction("Onboard request", "2")
                .nextSaveAndActivate()
                .validateAddedTask();
    }

    //skipped: 31 - there is 2 instances are available in "Onboard request" action (29, 30)

    @Test
    public void usp_gu_032() {
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
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate(false)
                .assertOnlineDevices()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_033() {
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
    public void usp_gu_034() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .bottomMenu(CANCEL)
                .assertElementsArePresent("tblParameters");
    }

    @Test
    public void usp_gu_035() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Manufacturer", getManufacturer());
    }

    @Test
    public void usp_gu_036() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Model", getModelName());
    }

    @Test
    public void usp_gu_037() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Completed")
                .checkFiltering("State", "Not active")
                .checkFiltering("State", "Error")
                .checkFiltering("State", "All");
    }

    @Test
    public void usp_gu_038() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Manufacturer");
    }

    @Test
    public void usp_gu_039() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Model");
    }

    @Test
    public void usp_gu_040() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Name");
    }

    @Test
    public void usp_gu_041() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Name")
                .validateSorting("Created");
    }

    @Test
    public void usp_gu_042() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Creator");
    }

    @Test
    public void usp_gu_043() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Updated");
    }

    @Test
    public void usp_gu_044() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Activated");
    }

    @Test
    public void usp_gu_045() {
        guPage
                .topMenu(GROUP_UPDATE)
                .selectManufacturer()
                .checkResetView();
    }

    @Test
    public void usp_gu_046() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("Manufacturer")
                .checkResetView()
                .leftMenu(VIEW);
    }

    @Test
    public void usp_gu_047() {
        guPage
                .gotoSetParameters()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatusWithoutRefresh("Completed", 65)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_048() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Scheduled");
    }

    @Test
    public void usp_gu_049() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Running");
    }

    @Test
    public void usp_gu_050() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Paused");
    }

    @Test
    public void usp_gu_051() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Reactivation");
    }

    @Test
    public void usp_gu_052() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsArePresent("lblNoSelectedCpes");
    }

    @Test
    public void usp_gu_053() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("device_created")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_054() {
        DataBaseConnector.createFilterPreconditions(BasePage.getSerial());
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("On Day")
                .clickOn("calFilterDate_image")
                .selectDate(CalendarUtil.getShiftedDate(-10))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_055() {
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
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_056() {
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
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_057() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Today")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_058() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Before Today")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_059() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Yesterday")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_060() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Prev 7 days")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_061() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Prev X days")
                .inputText("txtInt", "9")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    //SKIPPED 62-64: option “Description” is absent from list

    @Test
    public void usp_gu_065() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Like")
                .inputText("txtText", getTestName().substring(1, 5))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_066() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("No like")
                .inputText("txtText", getTestName().substring(1, 5))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_067() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_068() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_069() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Is not null")
                .bottomMenu(CANCEL)
                .assertElementsArePresent("lblHead");
    }

    @Test
    public void usp_gu_070() {
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
    public void usp_gu_071() {
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_072() {
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
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_073() {
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
                .setPeriod(1)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_074() {
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
                .setPeriod(1)
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_075() {
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
                .setPeriod(1)
                .setPeriod(2)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_076() {
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
                .setPeriod(1)
                .setPeriod(2)
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_077() {
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
                .onlineDevicesCheckBox()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_078() {
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
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_079() {
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
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_080() {
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
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_081() {
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
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_082() {
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
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_083() {
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
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_084() {
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
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_085() {
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
                .setPeriod(1)
                .setThreshold(50)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_086() {
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
                .setPeriod(1)
                .setThreshold(50)
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_087() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledTo()
                .selectShiftedDate("calDate", 1)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_088() {
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
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_089() {
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
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .selectShiftedDate("calReactivationStartsOnDay", 2)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_090() {
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
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_091() {
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
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "1")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_092() {
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
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 8)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_093() {
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
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_094() {
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
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_095() {
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
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .selectShiftedDate("calReactivationStartsOnDay", 2)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_096() {
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
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_097() {
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
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_098() {
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
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 8)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_099() {
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
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_100() {
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
                .selectRepeatsDropDown("Weekly")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_101() {
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
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_102() {
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
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_103() {
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
                .selectRepeatsDropDown("Weekly")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_104() {
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
                .selectRepeatsDropDown("Weekly")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 32)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_105() {
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
                .selectRepeatsDropDown("Weekly")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_106() {
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
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_107() {
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
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .selectShiftedDate("calReactivationStartsOnDay", 31)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_108() {
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
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_109() {
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
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_110() {
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
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 31)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_111() {
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
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_112() {
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
                .selectRepeatsDropDown("Yearly")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_113() {
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
                .selectRepeatsDropDown("Yearly")
                .selectShiftedDate("calReactivationStartsOnDay", 2)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_114() {
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
                .selectRepeatsDropDown("Yearly")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_115() {
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
                .selectRepeatsDropDown("Yearly")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 365)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_116() {
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
                .selectRepeatsDropDown("Yearly")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_117() {
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_118() {
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 2)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_119() {
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_120() {
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Location.1", 99)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void usp_gu_121() {//136
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
    public void usp_gu_122() {
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
                .addCondition(1, "ManagementServer", "NodeAddr", NOT_EQUAL, "127.0.0.1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void usp_gu_123() {
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
    public void usp_gu_124() {
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
                .addCondition(1, "ManagementServer", "NodeAddr", NOT_EQUAL, "127.0.0.1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Factory reset");
    }

    @Test
    public void usp_gu_125() {
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

    @Test
    public void usp_gu_126() {
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
                .addCondition(1, "ManagementServer", "NodeAddr", NOT_EQUAL, "127.0.0.1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTask("Device reprovision", "CPEReprovision");
    }

    @Test
    public void usp_gu_127() {
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
                .selectAction("Onboard request", "1")
                .nextSaveAndActivate()
                .validateAddedTask();
    }

    @Test
    public void usp_gu_128() {
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
                .selectAction("Onboard request", "2")
                .nextSaveAndActivate()
                .validateAddedTask();
    }

    //skipped: 129 - there is 2 instances are available in "Onboard request" action (127, 128)
}
