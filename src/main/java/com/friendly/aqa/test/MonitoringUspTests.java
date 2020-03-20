package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.CalendarUtil;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.pageobject.GlobalButtons.*;
import static com.friendly.aqa.pageobject.MonitoringPage.Left.IMPORT;
import static com.friendly.aqa.pageobject.MonitoringPage.Left.NEW;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.TopMenu.MONITORING;

@Listeners(UniversalVideoListener.class)
public class MonitoringUspTests extends BaseTestCase {

    @Test
    public void usp_mo_001() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void usp_mo_002() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageIsDisplayed()
                .globalButtons(REFRESH)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void usp_mo_003() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageIsDisplayed()
                .newViewButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .assertButtonsAreEnabled(true, CANCEL)
                .globalButtons(CANCEL)
                .assertMainPageIsDisplayed()
                .assertButtonsAreEnabled(false, ACTIVATE, STOP, STOP_WITH_RESET, DELETE)
                .assertButtonsAreEnabled(true, REFRESH);
    }

    @Test
    public void usp_mo_004() {
        monPage
                .topMenu(MONITORING)
                .newViewButton()
                .fillViewName()
                .globalButtons(NEXT)
                .clickOnTable("tblFilter", 1, 1)
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .filterRecordsCheckbox()
                .assertTrue(monPage.isButtonActive("btnDelFilter_btn"))
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertMainPageIsDisplayed();
        setTargetTestName();
    }

    @Test
    public void usp_mo_005() {
        monPage
                .topMenu(MONITORING)
                .newViewButton()
                .fillViewName(targetTestName)
                .globalButtons(NEXT)
                .assertElementIsPresent("lblNameInvalid");
    }

    @Test
    public void usp_mo_006() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .editButton()
                .globalButtons(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void usp_mo_007() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .editButton()
                .forPublicCheckbox()
                .forUserCheckbox()
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertMainPageIsDisplayed()
                .assertEquals(monPage.getSelectedValue("ddlView"), targetTestName)
                .topMenu(GROUP_UPDATE)
                .topMenu(MONITORING)
                .assertEquals(monPage.getSelectedValue("ddlView"), targetTestName);
    }

    @Test
    public void usp_mo_008() {
        monPage
                .topMenu(MONITORING)
                .selectView("Default")
                .assertEquals(monPage.getSelectedValue("ddlView"), "Default");
    }

    @Test
    public void usp_mo_009() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .assertEquals(monPage.getSelectedValue("ddlView"), targetTestName);
    }

    @Test
    public void usp_mo_010() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .editButton()
                .forPublicCheckbox()
                .forUserCheckbox()
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectView(targetTestName)
                .editButton()
                .globalButtons(DELETE_GROUP)
                .okButtonPopUp()
                .assertEquals(monPage.getSelectedValue("ddlView"), "Default");
    }

    @Test
    public void usp_mo_011() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .assertElementIsPresent("lbActivate")
                .globalButtons(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void usp_mo_012() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .assertFalse(monPage.isButtonActive("btnAddModel_btn"));
    }

    @Test
    public void usp_mo_013() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .addDeviceWithoutTemplate()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .selectSendTo()
                .immediately()
                .clickOnTable("tblDataParams", 1, 1, 0)
                .assertButtonsAreEnabled(true, SAVE_AND_ACTIVATE, SAVE, CANCEL, ADVANCED_VIEW);
    }

    @Test
    public void usp_mo_014() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .assertTableHasContent("tabsSettings_tblTabs")
                .globalButtons(ADVANCED_VIEW)
                .assertTableIsEmpty("tabsSettings_tblTabs")
                .globalButtons(SIMPLE_VIEW)
                .assertTableHasContent("tabsSettings_tblTabs");
    }

    @Test
    public void usp_mo_015() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .globalButtons(CANCEL)
                .assertEquals(monPage.getAttributeById("tbName", "value"), testName);
    }

    @Test
    public void usp_mo_016() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .assertFalse(monPage.isButtonActive("btnDelFilter_btn"))
                .filterRecordsCheckbox()
                .assertTrue(monPage.isButtonActive("btnDelFilter_btn"))
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertEquals(monPage.getSelectedValue("ddlSend"), testName);
        setTargetTestName();
    }

    @Test
    public void usp_mo_017() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .compareSelect("Is null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE, SAVE);
    }

    @Test
    public void usp_mo_018() {    //is dependent on #016
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .newGroupButton()
                .fillGroupName(targetTestName)
                .globalButtons(NEXT)
                .assertElementIsPresent("lblNameInvalid");
    }

    @Test
    public void usp_mo_019() {    //is dependent on #016
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo(targetTestName)
                .assertCellStartsWith("tabsSettings_tblTabs", 1, -2, "Devices");
    }

    @Test
    public void usp_mo_020() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .assertCellEndsWith("tabsSettings_tblTabs", 1, -2, " " + getDeviceAmount());
    }

    @Test
    public void usp_mo_021() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("Individual")
                .assertButtonIsActive("btnSelectDevices_btn")
                .selectButton()
                .cancelIndividualSelection()
                .selectButton()
                .selectIndividualDevises(1)
                .assertCellEndsWith("tabsSettings_tblTabs", 1, -2, " " + getDeviceAmount());
    }

    @Test
    public void usp_mo_022() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .selectSendTo("Import from a file")
                .selectImportDevicesFile()
                .pause(2000)
                .assertCellEndsWith("tabsSettings_tblTabs", 1, -2, " " + getDeviceAmount());
    }

    @Test
    public void usp_mo_023() {    //Bug: 'Delete Group' button doesn't delete device group.
        monPage                     //is dependent on #016
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .selectSendTo(targetTestName)
                .editButton()
                .globalButtons(DELETE_GROUP)
                .okButtonPopUp()
                .assertFalse(guPage.isOptionPresent("ddlSend", targetTestName), "Option '" + targetTestName + "' is present on 'Send to' list!\n");
    }

    @Test
    public void usp_mo_024() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE);
    }

    @Test
    public void usp_mo_025() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("Information", 0, 1)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void usp_mo_026() {    //is dependent on #025
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .immediately()
                .setParameters("Information", 2, 2)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void usp_mo_027() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("Information", 0, 7)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void usp_mo_028() {    //is dependent on #027
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Information", 8, 8)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void usp_mo_029() {    //is dependent on #027 and #028
        usp_mo_028();
    }

    @Test
    public void usp_mo_030() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("Information", 0, 0)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void usp_mo_031() {    //is dependent on #030
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Information", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void usp_mo_032() {    //is dependent on #030
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Information", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void usp_mo_033() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("Information", 0, 0)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void usp_mo_034() {    //is dependent on #054
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Information", 0, 0)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void usp_mo_035() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("Information", 0, 0)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .pause(1000)
                .selectItem()
                .globalButtons(ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void usp_mo_036() {    //is dependent on #035
        monPage
                .topMenu(MONITORING)
                .selectItem(targetTestName)
                .globalButtons(STOP)
                .okButtonPopUp()
                .waitForStatus("Not active", targetTestName);
    }

    @Test
    public void usp_mo_037() {    //is dependent on #035
        monPage
                .topMenu(MONITORING)
                .selectItem(targetTestName)
                .globalButtons(ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName);
    }

    @Test
    public void usp_mo_038() {    //is dependent on #035
        monPage
                .topMenu(MONITORING)
                .selectItem(targetTestName)
                .globalButtons(STOP_WITH_RESET)
                .okButtonPopUp()
                .waitForStatus("Not active", targetTestName);
    }

    @Test
    public void usp_mo_039() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .clickOn("calDateTo")
                .selectShiftedDate("calDateTo", 0)
                .setEndDateDelay(-10)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .assertEqualsAlertMessage("Finish date can't scheduled to past")
                .selectShiftedDate("calDateTo", 0)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .assertEqualsAlertMessage("Finish date can't scheduled to past");
    }

    @Test
    public void usp_mo_040() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("Information", 0, 100)
                .setParameters("Information", 0, 0)
                .setParameters("Connectivity monitoring", 0, 0)
                .selectShiftedDate("calDateTo", 0)
                .setEndDateDelay(2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .waitForStatus("Completed", 120)
                .enterIntoGroup()
                .checkResults();
    }

    @Test
    public void usp_mo_041() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setAdvancedParameters("Device.ManagementServer", 0, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active")
                .enterIntoGroup()
                .checkResults();
    }

    @Test
    public void usp_mo_042() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .scheduledToRadioButton()
                .selectShiftedDate("calDateFrom", 0)
                .setScheduledDelay(10)
                .selectSendTo("All")
                .setParameters("Information", 0, 0)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void usp_mo_043() {    //is dependent on #042
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("Information", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void usp_mo_044() {    //is dependent on #042
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("Information", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void usp_mo_045() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .scheduledToRadioButton()
                .selectShiftedDate("calDateFrom", 0)
                .setScheduledDelay(10)
                .selectSendTo("All")
                .setParameters("Information", 0, 1)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .selectItem()
                .globalButtons(STOP)
                .okButtonPopUp()
                .globalButtons(REFRESH)
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void usp_mo_046() {
        monPage
                .topMenu(MONITORING)
                .selectItem(targetTestName)
                .globalButtons(ACTIVATE)
                .okButtonPopUp()
                .globalButtons(REFRESH)
                .waitForStatus("Scheduled", targetTestName);
    }

    @Test
    public void usp_mo_047() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .selectSendTo("All")
                .scheduledToRadioButton()
                .globalButtons(SAVE)
                .okButtonPopUp()
                .assertEqualsAlertMessage("Activation date can't be scheduled to past");
    }

    @Test
    public void usp_mo_048() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .assertPresenceOfValue("tblModels", 0, BasePage.deviceToString());
    }

    @Test
    public void usp_mo_049() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(IMPORT)
                .globalButtons(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void usp_mo_050() {
        monPage
                .topMenu(MONITORING)
                .checkFilteringByManufacturer();
    }

    @Test
    public void usp_mo_051() {
        monPage
                .topMenu(MONITORING)
                .checkFilteringByModelName();
    }

    @Test
    public void usp_mo_052() {
        setTargetTestName();
        monPage
                .topMenu(MONITORING)
                .newViewButton()
                .fillCustomViewName()
                .globalButtons(NEXT)
                .setViewColumns(0, 100)
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .checkViewColumns();
    }

    @Test
    public void usp_mo_053() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Created");
    }

    @Test
    public void usp_mo_054() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Date from");
    }

    @Test
    public void usp_mo_055() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Date to");
    }

    @Test
    public void usp_mo_056() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Description");
    }

    @Test
    public void usp_mo_057() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Name");
    }

    @Test
    public void usp_mo_058() {    //Bug: Unclear sorting algorithm by "State" column
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("State");
    }

    @Test
    public void usp_mo_059() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Updated");
    }

    @Test
    public void usp_mo_060() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .resetView()
                .assertEquals(monPage.getSelectedValue("ddlView"), "Default", "View reset does not occur");
    }

    @Test
    public void usp_mo_061() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .selectSendTo()
                .addAnotherModel()
                .setParametersFor2Devices(true);
    }

    @Test
    public void usp_mo_062() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .selectSendTo()
                .addAnotherModel()
                .setParametersFor2Devices(false);
    }

    @Test
    public void usp_mo_068() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .assertFalse(monPage.isButtonActive("btnDelFilter_btn"))
                .filterRecordsCheckbox()
                .assertTrue(monPage.isButtonActive("btnDelFilter_btn"))
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertEquals(monPage.getSelectedValue("ddlSend"), testName)
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running", 5);
    }

    @Test
    public void usp_mo_069() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .compareSelect("On Day")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_070() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .compareSelect("Prior to")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_071() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .compareSelect("Later than")
                .clickOn("calFilterDate_image")
                .selectDate(CalendarUtil.getMonthBeforeDate())
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_072() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .compareSelect("Today")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_073() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .compareSelect("Before Today")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_074() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .compareSelect("Yesterday")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_075() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .compareSelect("Prev 7 days")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_076() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .compareSelect("Prev X days")
                .inputText("txtInt", "4")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_077() {
        monPage
                .presetFilter("mycust03", testName)
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("=")
                .inputText("txtText", testName)
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_078() {
        monPage
                .presetFilter("mycust03", testName)
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("!=")
                .inputText("txtText", testName)
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_079() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Description")
                .compareSelect("Starts with")
                .inputText("txtText", testName)
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE, SAVE);
    }

    @Test
    public void usp_mo_080() {
        monPage
                .presetFilter("mycust03", testName)
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("Like")
                .inputText("txtText", testName.substring(1, 5))
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_081() {
        monPage
                .presetFilter("mycust03", testName)
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("No like")
                .inputText("txtText", testName.substring(1, 5))
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_082() {
        monPage
                .presetFilter("mycust03", testName)
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("Is null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_083() {
        monPage
                .presetFilter("mycust03", testName)
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_084() {
        monPage
                .presetFilter("mycust03", testName)
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectManufacturer()
                .addAnotherModel()
                .newGroupButton()
                .fillGroupName(testName + "_1")
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("Is null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void usp_mo_085() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("mycust04")
                .compareSelect("Is null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectManufacturer()
                .addAnotherModel()
                .newGroupButton()
                .fillGroupName(testName + "_1")
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("mycust04")
                .compareSelect("Is null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .immediately()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }
}

