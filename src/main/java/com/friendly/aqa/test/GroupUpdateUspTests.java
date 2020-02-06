package com.friendly.aqa.test;

import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.HttpConnector;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.friendly.aqa.pageobject.BasePage.*;
import static com.friendly.aqa.pageobject.BasePage.waitForUpdate;
import static com.friendly.aqa.pageobject.GlobalButtons.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.NEW;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.utils.Table.Conditions.EQUAL;
import static com.friendly.aqa.utils.Table.Parameter.VALUE;
import static org.testng.Assert.*;
import static org.testng.Assert.assertFalse;

public class GroupUpdateUspTests extends BaseTestCase {
    @Test
    public void usp_gu_001() {
        guPage.deleteAll();
        guPage.topMenu(GROUP_UPDATE);
        waitForUpdate();
        assertTrue(guPage.mainTableIsAbsent());
    }

    @Test
    public void usp_gu_002() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .globalButtons(CANCEL);
        assertTrue(guPage.mainTableIsAbsent());
    }

    @Test
    public void usp_gu_003() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName()
                .globalButtons(CANCEL);
        waitForUpdate();
        assertTrue(guPage.mainTableIsAbsent());
    }

    @Test
    public void usp_gu_004() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName()
                .selectSendTo()
                .showList();
        assertTrue(guPage.serialNumberTableIsPresent());
    }

    @Test
    public void usp_gu_005() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName()
                .createGroup();
        assertTrue(guPage.isButtonPresent(FINISH));
        guPage
                .globalButtons(CANCEL);
        waitForUpdate();
        guPage.pause(500);
        assertEquals(guPage.getAttributeById("txtName", "value"), testName);
    }

    @Test
    public void usp_gu_006() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("device_created")
                .compareSelect("IsNull")
                .globalButtons(NEXT);
        assertFalse(guPage.isButtonActive("btnDelFilter_btn"));
        guPage.filterRecordsCheckbox();
        assertTrue(guPage.isButtonActive("btnDelFilter_btn"));
        guPage
                .globalButtons(FINISH)
                .okButtonPopUp();
        setTargetTestName();
        assertEquals(testName, guPage.getSelectedValue("ddlSend"));
    }

    @Test
    public void usp_gu_007() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("cust2")
                .compareSelect("Equal")
                .inputTextField("111")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertEquals(testName, guPage.getSelectedValue("ddlSend"), "Created group isn't selected!\n");
        assertTrue(guPage.isElementDisplayed("lblNoSelectedCpes"), "Warning 'No devices selected' isn't displayed!\n");
    }

    @Test
    public void usp_gu_008() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName()
                .createGroup()
                .fillName(targetTestName)
                .globalButtons(NEXT);
        assertTrue(guPage.isElementDisplayed("lblNameInvalid"), "Warning 'This name is already in use' isn't displayed!\n");
    }

    @Test
    public void usp_gu_009() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName()
                .selectSendTo(targetTestName)
                .editGroupButton()
                .globalButtons(DELETE_GROUP)
                .okButtonPopUp();
        assertFalse(guPage.isOptionPresent("ddlSend", targetTestName), "Option '" + targetTestName + "' is still present on 'Send to' list!\n");
    }

    @Test
    public void usp_gu_010() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName()
                .selectSendTo("Individual")
                .getTable("tblDevices")
                .clickOn(1, 0);
        waitForUpdate();
        assertTrue(guPage.isButtonActive(NEXT));
        guPage.getTable("tblDevices")
                .clickOn(1, 0);
        waitForUpdate();
        assertFalse(guPage.isButtonActive(NEXT));
    }

    @Test
    //Doesn't work with Edge
    public void usp_gu_011() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName()
                .selectSendTo("Import")
                .selectImportDevicesFile()
                .showList()
                .getTable("tblDevices").assertPresenceOfValue(0,BasePage.getSerial());
    }

    @Test
    public void usp_gu_012() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("device_created")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectSendTo(testName)
                .showList();
        guPage.getTable("tblDevices").assertPresenceOfValue(0, BasePage.getSerial());
    }

    @Test
    public void usp_gu_013() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton();
        assertTrue(guPage.isElementPresent("tblParamsValue"));
        assertFalse(guPage.isButtonActive(SAVE_AND_ACTIVATE));
    }

    @Test
    public void usp_gu_014() {
        guPage
                .gotoSetParameters()
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp();
        waitForUpdate();
        assertEquals(guPage
                .getMainTable()
                .getCellText(4, testName, 1), "Not active");
        setTargetTestName();
    }

    @Test
    public void usp_gu_015() {
        guPage
                .topMenu(GROUP_UPDATE)
                .getMainTable()
                .clickOn(targetTestName, 4);
        guPage
                .globalButtons(EDIT)
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .getTable("tblTasks")
                .clickOn("Root.Device.0.UTC Offset", 3)
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+01:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .getMainTable()
                .clickOn(targetTestName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+01:00");
    }

    @Test
    public void usp_gu_016() {
        guPage
                .gotoSetParameters()
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate();
        setTargetTestName();
    }

    @Test
    public void usp_gu_017() {
        guPage
                .topMenu(GROUP_UPDATE)
                .getMainTable()
                .clickOn(targetTestName, 4);
        guPage
                .globalButtons(EDIT);
        assertFalse(guPage.isInputActive("ddlSend"));
        guPage
                .globalButtons(NEXT);
        assertFalse(guPage.isInputActive("lrbImmediately"));
        guPage
                .globalButtons(NEXT);
        assertFalse(guPage.isButtonActive(SAVE_AND_ACTIVATE));
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .timeHoursSelect("0")
                .globalButtons(NEXT);
        waitForUpdate();
        assertEquals(guPage.getAlertTextAndClickOk(), "Update can't scheduled to past"/*"Can't be scheduled to the past"*/);
        guPage
                .checkIsCalendarClickable();
    }

    @Test
    public void usp_gu_019() throws IOException {
        guPage
                .topMenu(GROUP_UPDATE);
        System.out.println(guPage
                .getMainTable()
                .getExportLink(targetTestName));
        assertTrue(HttpConnector.getUrlSource(guPage
                .getMainTable()
                .getExportLink(targetTestName))
                .contains("\"Root.Device.0.UTC Offset\" value=\"+02:00\""));
    }

    @Test
    public void usp_gu_020() {
        guPage
                .gotoSetParameters("Device")
                .setParameter("Timezone", VALUE, "Europe/Kharkov2")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .setPrefix("Root.Device.0.")
                .checkResults("Timezone", "Europe/Kharkov2")
                .checkResults("UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_021() {
        guPage
                .gotoSetParameters("Device")
                .setParameter("Timezone", VALUE, "Europe/Kharkov3");
        guPage
                .nextSaveAndActivate()
                .setPrefix("Root.Device.0.")
                .checkResults("Timezone", "Europe/Kharkov3");
    }

    @Test
    public void usp_gu_022() {
        guPage
                .gotoSetParameters("Device")
                .setParameter(0);
        guPage
                .nextSaveAndActivate()
                .setPrefix("Root.Device.0.")
                .checkResults();
    }

    @Test
    public void usp_gu_023() {
        guPage
                .gotoSetParameters("Server")
                .setParameter(0);
        guPage
                .nextSaveAndActivate()
                .setPrefix("Root.usp Server.0.")
                .checkResults();
    }

    @Test
    public void usp_gu_024() {
        guPage
                .gotoSetParameters("Server")
                .setParameter("Lifetime", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .setPrefix("Root.usp Server.0.")
                .checkResults("Lifetime", "60");
    }

    @Test
    public void usp_gu_025() {
        guPage
                .gotoSetParameters("Server")
                .setParameter("Default Maximum Period", VALUE, "10")
                .setParameter("Default Minimum Period", VALUE, "1");
        guPage
                .nextSaveAndActivate()
                .setPrefix("Root.usp Server.0.")
                .checkResults("Default Maximum Period", "10")
                .checkResults("Default Minimum Period", "1");
    }

    @Test
    public void usp_gu_26() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(2)
                .addTaskButton()
                .rebootRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void usp_gu_27() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(2)
                .addTaskButton()
                .rebootRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "Binding mode", EQUAL, "60");
        guPage
                .saveAndActivate()
                .checkResults("Action", "Reboot");
    }

    @Test
    public void usp_gu_28() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(2)
                .addTaskButton()
                .factoryResetRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("FactoryReset");
    }

    @Test
    public void usp_gu_29() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(2)
                .addTaskButton()
                .factoryResetRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "Binding mode", EQUAL, "60");
        guPage
                .saveAndActivate()
                .checkResults("Action", "FactoryReset");
    }

    @Test
    public void usp_gu_30() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(2)
                .addTaskButton()
                .resetMinMaxValues()
                .nextSaveAndActivate();
        guPage
                .getMainTable()
                .clickOn(targetTestName, 4)
                .getTable("tblPeriod")
                .checkResults("Status", "Completed");
    }

    @Test
    public void usp_gu_31() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(2)
                .addTaskButton()
                .resetCumulativeEnergy()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("resetCumulativeEnergy");
    }

    @Test
    public void usp_gu_32() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(2)
                .addTaskButton()
                .resetErrors()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("resetErrors");
    }

    @Test
    public void usp_gu_33() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(2)
                .addTaskButton()
                .radioDisable()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("radioDisable");
    }

    @Test
    public void usp_gu_34() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(2)
                .addTaskButton()
                .radioRegistrationUpdateTrigger()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("radioRegistrationUpdateTrigger");
    }

    @Test
    public void usp_gu_35() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(2)
                .addTaskButton()
                .radioStartOrReset()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("radioStartOrReset");
    }

    @Test
    public void usp_gu_36() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(2)
                .addTaskButton()
                .reprovisionRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Reprovision");
    }

    @Test   //Test fails
    public void usp_gu_37() {
        guPage
                .gotoSetParameters("ManagementServer", true)
                .setAllParameters()
                .setAnyAdvancedParameter();  //Re-work required
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test  //Test fails
    public void usp_gu_38() {
        guPage
                .gotoSetParameters("ManagementServer", true)
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test  //Test fails
    public void usp_gu_39() {
        guPage
                .gotoSetParameters("ManagementServer", true)
                .setParameter(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test  //Test failed
    public void usp_gu_40() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tabsSettings_tblTabs")
                .clickOn("Timezone")
                .getTable("tblParamsValue")
                .setParameter(1);
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running", 5)
                .readTasksFromDB()
                .clickOn(testName)
                .getTable("tblTasks")
                .checkResults()
                .getTable("tblPeriod")
                .checkResults("Online devices", "True");
    }

    @Test
    public void usp_gu_41() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .assertElementIsPresent("lblTitle1");
    }

    @Test
    public void usp_gu_42() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .globalButtons(CANCEL)
                .assertElementIsPresent("tblParameters");
    }

    @Test
    public void usp_gu_43() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Manufacturer", getManufacturer());
    }

    @Test
    public void usp_gu_44() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Model", getModelName());
    }

    @Test
    public void usp_gu_45() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Completed")
                .checkFiltering("State", "Not active")
                .checkFiltering("State", "Error")
                .checkFiltering("State", "All");
    }

    @Test
    public void usp_gu_46() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Manufacturer");
    }

    @Test
    public void usp_gu_47() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Model");
    }

    @Test
    public void usp_gu_48() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name");
    }

    @Test
    public void usp_gu_49() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name")
                .checkSorting("Created");
    }

    @Test
    public void usp_gu_50() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Creator");
    }

    @Test
    public void usp_gu_51() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Updated");
    }

    @Test
    public void usp_gu_52() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Activated");
    }

    @Test
    public void usp_gu_53() {
        guPage
                .topMenu(GROUP_UPDATE)
                .selectManufacturer()
                .checkResetView();
    }

    @Test
    public void usp_gu_54() {
        guPage
                .topMenu(GROUP_UPDATE)
                .getMainTable()
                .clickOn("Manufacturer");
        guPage
                .checkResetView();
        guPage
                .leftMenu(VIEW)
                .itemsOnPage("10")
                .pause(5000);
    }

    @Test  //Test failed
    public void usp_gu_55() {
        guPage
                .gotoSetParameters("Device")
                .setParameter(1);
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatusWithoutRefresh("Completed", 65)
                .clickOn(testName, 4)
                .getTable("tblTasks")
                .checkResults();
    }

    @Test
    public void usp_gu_56() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Scheduled")
                .resetView();
    }

    @Test
    public void usp_gu_57() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Running")
                .resetView();
    }

    @Test
    public void usp_gu_58() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Paused")
                .resetView();
    }

    @Test
    public void usp_gu_59() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Reactivation")
                .resetView();
    }

    @Test
    public void usp_gu_60() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("device_created")
                .compareSelect("Is null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertTrue(guPage.isElementDisplayed("lblNoSelectedCpes"), "Cannot find label'No devices selected'!\n");
    }

    @Test
    public void usp_gu_61() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("device_created")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected!\n");
        guPage.globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test //doesn't work correctly (filter 'Created - on day')
    public void usp_gu_62() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("device_created")
                .compareSelect("On Day")
                .clickOn("calFilterDate_image")
                .selectTodayDate(CalendarUtil.getTodayDateString())
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_63() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Prior to")
                .clickOn("calFilterDate_image")
                .selectTodayDate(CalendarUtil.getTodayDateString())
                .inputText("txtTimeHour", CalendarUtil.getHours())
                .inputText("txtTimeMinute", CalendarUtil.getMinutes())
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_64() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Later than")
                .clickOn("calFilterDate_image")
                .selectTodayDate(CalendarUtil.getMonthBeforeDate())
                .inputText("txtTimeHour", CalendarUtil.getHours())
                .inputText("txtTimeMinute", CalendarUtil.getMinutes())
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Later than'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_65() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Today")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Today'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_66() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Before Today")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Before Today'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_67() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Yesterday")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Yesterday'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_68() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Prev 7 days")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Prev 7 days'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_69() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Prev X days")
                .inputText("txtInt", "4")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Prev X days'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_70() {
        guPage
                .presetFilter("mycust03", testName)
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("=")
                .inputText("txtText", testName)
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - equals'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_71() {
        guPage
                .presetFilter("mycust03", testName)
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("!=")
                .inputText("txtText", testName)
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - not equals'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_72() {
        guPage
                .presetFilter("mycust03", testName)
                .gotoAddFilter()
                .selectColumnFilter("Description")
                .compareSelect("Starts with")
                .inputText("txtText", testName)
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertTrue(guPage.isElementDisplayed("lblNoSelectedCpes"), "Cannot find label'No devices selected'!\n");
    }

    @Test
    public void usp_gu_73() {
        guPage
                .presetFilter("mycust03", testName)
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("Like")
                .inputText("txtText", testName.substring(1, 5))
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Like'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_74() {
        guPage
                .presetFilter("mycust03", testName)
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("No like")
                .inputText("txtText", testName.substring(1, 5))
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - No like'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_75() {
        guPage
                .presetFilter("mycust03", testName)
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("Is null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Is null'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_76() {
        guPage
                .presetFilter("mycust03", testName)
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Is not null'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .nextSaveAndActivate()
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_77() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Is not null")
                .globalButtons(CANCEL);
        waitForUpdate();
        assertTrue(guPage.isElementDisplayed("lblHead"), "Filter creation didn't cancel properly!\n");
    }

    @Test
    public void usp_gu_78() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName()
                .createGroup()
                .fillName()
                .globalButtons(NEXT)
                .globalButtons(PREVIOUS)
                .globalButtons(CANCEL);
        waitForUpdate();
        assertFalse(guPage.isOptionPresent("ddlSend", testName), "Option '" + testName + "' is present on 'Send to' list!\n");
    }

    @Test
    public void usp_gu_79() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_80() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_81() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_82() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_83() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_84() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_85() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_86() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_87() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_88() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_89() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_90() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_91() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_92() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_93() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_94() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_95() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_96() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_97() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_98() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_99() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 8)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .runOnFailed()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .selectShiftedDate("calReactivationStartsOnDay", 2)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("2")
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 8)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .runOnFailed()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Weekly")
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .immediately()
                .selectRepeatsDropDown("Weekly")
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .immediately()
                .selectRepeatsDropDown("Weekly")
                .endAOnRadiobutton()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Weekly")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Weekly")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 32)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Weekly")
                .runOnFailed()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .selectShiftedDate("calReactivationStartsOnDay", 31)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("2")
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 31)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .runOnFailed()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_121() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 365)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .runOnFailed()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Root.Device.0.UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00")
                .setParameter("Timezone", VALUE, "Europe/Kharkov1");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("UTC Offset", "+02:00")
                .checkResults("Timezone", "Europe/Kharkov1");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("UTC Offset", VALUE, "+02:00");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("UTC Offset", "+02:00");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter(0);
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults();
    }

    @Test
    public void usp_gu_129() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter(0);
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults();
    }

    @Test
    public void usp_gu_130() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("Default Maximum Period", VALUE, "10");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Default Maximum Period", "10");
    }

    @Test
    public void usp_gu_131() {
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
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("Default Maximum Period", VALUE, "10")
                .setParameter("Default Minimum Period", VALUE, "1");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("Default Maximum Period", "10")
                .checkResults("Default Minimum Period", "1");
    }

    @Test
    public void usp_gu_132() {
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
                .addNewTask(2)
                .addTaskButton()
                .rebootRadioButton()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void usp_gu_133() {
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
                .addNewTask(2)
                .addTaskButton()
                .rebootRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "Binding mode", EQUAL, "60");
        guPage
                .saveAndActivate()
                .checkResults("Action", "Reboot");
    }

    @Test
    public void usp_gu_134() {
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
                .addNewTask(2)
                .addTaskButton()
                .factoryResetRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("FactoryReset");
    }

    @Test
    public void usp_gu_135() {
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
                .addNewTask(2)
                .addTaskButton()
                .factoryResetRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "Binding mode", EQUAL, "60");
        guPage
                .saveAndActivate()
                .checkResults("Action", "FactoryReset");
    }

    @Test
    public void usp_gu_136() {
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
                .addNewTask(2)
                .addTaskButton()
                .resetMinMaxValues()
                .nextSaveAndActivate();
        guPage
                .getMainTable()
                .clickOn(targetTestName, 4)
                .getTable("tblPeriod")
                .checkResults("Status", "Completed");
    }

    @Test
    public void usp_gu_137() {
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
                .addNewTask(2)
                .addTaskButton()
                .resetCumulativeEnergy()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("resetCumulativeEnergy");
    }

    @Test
    public void usp_gu_138() {
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
                .addNewTask(2)
                .addTaskButton()
                .resetErrors()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("resetErrors");
    }

    @Test
    public void usp_gu_139() {
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
                .addNewTask(2)
                .addTaskButton()
                .radioDisable()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("radioDisable");
    }

    @Test
    public void usp_gu_140() {
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
                .addNewTask(2)
                .addTaskButton()
                .radioRegistrationUpdateTrigger()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("radioRegistrationUpdateTrigger");
    }

    @Test
    public void usp_gu_141() {
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
                .addNewTask(2)
                .addTaskButton()
                .radioStartOrReset()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("radioStartOrReset");
    }

    @Test
    public void usp_gu_142() {
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
                .addNewTask(2)
                .addTaskButton()
                .reprovisionRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Reprovision");
    }

    @Test   //Test fails
    public void usp_gu_143() {
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
                .addNewTask(1)
                .addTaskButton()
                .globalButtons(ADVANCED_VIEW);
        //.setAllParameters()
        //.setAnyAdvancedParameter();  //Re-work required
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void usp_gu_144() {
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
                .addNewTask(1)
                .addTaskButton()
                .globalButtons(ADVANCED_VIEW);
        // .setParameter("UTC Offset", VALUE, "+02:00");
        //.setAnyAdvancedParameter();  //Re-work required
        guPage
                .nextSaveAndActivate();

        guPage
                .getTable("tblTasks")
                .checkResults("UTC Offset", "+02:00");
    }

    @Test
    public void usp_gu_145() {
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
                .addNewTask(1)
                .addTaskButton()
                .globalButtons(ADVANCED_VIEW);
        //  .setParameter("UTC Offset", VALUE, "+02:00");
        //.setAnyAdvancedParameter();  //Re-work required
        guPage
                .nextSaveAndActivate();

        guPage
                .getTable("tblTasks")
                .checkResults("UTC Offset", "+02:00");
    }
}
