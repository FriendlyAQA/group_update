package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.DataBaseConnector;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.pageobject.MonitoringPage.Left.IMPORT;
import static com.friendly.aqa.pageobject.MonitoringPage.Left.NEW;
import static com.friendly.aqa.entities.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.entities.TopMenu.MONITORING;

/*
Preconditions:
* Each tested manufacturer MUST have at least 2 registered models;
* Devices (emuls) MAY NOT run;
*/

@Listeners(UniversalVideoListener.class)
public class MonitoringTR069Tests extends BaseTestCase {

    @Test
    public void tr069_mo_001() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_mo_002() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageIsDisplayed()
                .bottomMenu(REFRESH)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_mo_003() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageIsDisplayed()
                .deleteAllMonitors()
                .deleteAllCustomViews()
                .newViewButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .assertButtonsAreEnabled(true, CANCEL)
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed()
                .assertButtonsAreEnabled(false, ACTIVATE, STOP, STOP_WITH_RESET, DELETE)
                .assertButtonsAreEnabled(true, REFRESH);
    }

    @Test
    public void tr069_mo_004() {
        monPage
                .topMenu(MONITORING)
                .newViewButton()
                .fillViewName()
                .bottomMenu(NEXT)
                .clickOnTable("tblFilter", 1, 1)
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .filterRecordsCheckbox()
                .assertButtonIsEnabled(true, "btnDelFilter_btn")
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_mo_005() {
        monPage
                .topMenu(MONITORING)
                .newViewButton()
                .fillViewName("tr069_mo_004")
                .bottomMenu(NEXT)
                .assertPresenceOfElements("lblNameInvalid");
    }

    @Test
    public void tr069_mo_006() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr069_mo_004")
                .editButton()
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_mo_007() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr069_mo_004")
                .editButton()
                .forPublicCheckbox()
                .forUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertMainPageIsDisplayed()
                .assertSelectedViewIs("tr069_mo_004")
                .topMenu(GROUP_UPDATE)
                .topMenu(MONITORING)
                .assertSelectedViewIs("tr069_mo_004");
    }

    @Test
    public void tr069_mo_008() {
        monPage
                .topMenu(MONITORING)
                .selectView("Default")
                .assertSelectedViewIs("Default");
    }

    @Test
    public void tr069_mo_009() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr069_mo_004")
                .assertSelectedViewIs("tr069_mo_004");
    }

    @Test
    public void tr069_mo_010() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr069_mo_004")
                .editButton()
                .forPublicCheckbox()
                .forUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("tr069_mo_004")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertSelectedViewIs("Default");
    }

    @Test
    public void tr069_mo_011() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .assertPresenceOfElements("lbActivate")
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_mo_012() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .assertButtonIsEnabled(false, "btnAddModel_btn");
    }

    @Test
    public void tr069_mo_013() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .addDeviceWithoutTemplate()
                .addModelButton()
                .assertEqualsAlertMessage("Template for this model doesn't exist")
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .selectSendTo()
                .immediately()
                .selectTab("Management")
                .assertButtonsAreEnabled(true, SAVE_AND_ACTIVATE, SAVE, CANCEL, ADVANCED_VIEW);
    }

    @Test
    public void tr069_mo_014() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .selectTab("Management")
                .bottomMenu(ADVANCED_VIEW)
                .assertTableIsEmpty("tabsSettings_tblTabs")
                .bottomMenu(SIMPLE_VIEW)
                .assertTableHasContent("tabsSettings_tblTabs");
    }

    @Test
    public void tr069_mo_015() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .deleteAllGroups()
                .newGroupButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .bottomMenu(CANCEL)
                .assertInputHasText("tbName", testName);
    }

    @Test
    public void tr069_mo_016() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .assertButtonIsEnabled(false, "btnDelFilter_btn")
                .filterRecordsCheckbox()
                .assertButtonIsEnabled(true, "btnDelFilter_btn")
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertSelectedOptionIs("ddlSend", testName);
    }

    @Test
    public void tr069_mo_017() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE, SAVE);
    }

    @Test
    public void tr069_mo_018() {    //is dependent on #016
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .newGroupButton()
                .fillGroupName("tr069_mo_016")
                .bottomMenu(NEXT)
                .assertPresenceOfElements("lblNameInvalid");
    }

    @Test
    public void tr069_mo_019() {    //is dependent on #016
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("tr069_mo_016")
                .assertCellStartsWith("tabsSettings_tblTabs", 1, -2, "Devices");
    }

    @Test
    public void tr069_mo_020() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .assertCellEndsWith("tabsSettings_tblTabs", 1, -2, " " + getDeviceAmount());
    }

    @Test
    public void tr069_mo_021() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("Individual")
                .assertButtonIsEnabled(true, "btnSelectDevices_btn")
                .selectButton()
                .cancelIndividualSelection()
                .selectButton()
                .selectIndividualDevises(1)
                .assertCellEndsWith("tabsSettings_tblTabs", 1, -2, " 1");
    }

    @Test
    public void tr069_mo_022() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .selectSendTo("Import from a file")
                .selectImportDevicesFile()
                .pause(2000)
                .assertCellMatches("tabsSettings_tblTabs", 1, -2, ".+\\d+$");
    }

    @Test
    public void tr069_mo_023() {//is dependent on #016
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .selectSendTo("tr069_mo_016")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "tr069_mo_016");
    }

    @Test
    public void tr069_mo_024() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE);
    }

    @Test
    public void tr069_mo_025() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("Management", 0, 1)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_026() {    //is dependent on #025
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_025")
                .immediately()
                .setParameters("Management", 2, 2)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", "tr069_mo_025")
                .enterIntoMonitoring("tr069_mo_025")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_027() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("Information", 0, 7)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_028() {    //is dependent on #027
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_027")
                .setParameters("Information", 8, 8)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_027")
                .enterIntoMonitoring("tr069_mo_027")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_029() {    //is dependent on #027 and #028
        tr069_mo_028();
    }

    @Test
    public void tr069_mo_030() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("Management", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_031() {    //is dependent on #030
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_030")
                .setParameters("Management", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_030")
                .enterIntoMonitoring("tr069_mo_030")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_032() {    //is dependent on #030
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_030")
                .setParameters("Management", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_030")
                .enterIntoMonitoring("tr069_mo_030")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_033() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("Information", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_034() {    //is dependent on #033
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_033")
                .setParameters("Information", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_033")
                .enterIntoMonitoring("tr069_mo_033")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_035() {    //is dependent on #033
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_033")
                .setParameters("Information", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_033")
                .enterIntoMonitoring("tr069_mo_033")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_036() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("Time", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_037() {    //is dependent on #036
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_036")
                .setParameters("Time", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_036")
                .enterIntoMonitoring("tr069_mo_036")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_038() {    //is dependent on #036
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_036")
                .setParameters("Time", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_036")
                .enterIntoMonitoring("tr069_mo_036")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_039() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("DSL settings", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_040() {    //is dependent on #039
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_039")
                .setParameters("DSL settings", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_039")
                .enterIntoMonitoring("tr069_mo_039")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_041() {    //is dependent on #039
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_039")
                .setParameters("DSL settings", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_039")
                .enterIntoMonitoring("tr069_mo_039")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_042() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("WAN", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_043() {    //is dependent on #042
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_042")
                .setParameters("WAN", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_042")
                .enterIntoMonitoring("tr069_mo_042")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_044() {    //is dependent on #042
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_042")
                .setParameters("WAN", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_042")
                .enterIntoMonitoring("tr069_mo_042")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_045() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("LAN", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_046() {    //is dependent on #045
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_045")
                .setParameters("LAN", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_045")
                .enterIntoMonitoring("tr069_mo_045")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_047() {    //is dependent on #045
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_045")
                .setParameters("LAN", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_045")
                .enterIntoMonitoring("tr069_mo_045")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_048() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("Wireless", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_049() {    //is dependent on #048
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_048")
                .setParameters("Wireless", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_048")
                .enterIntoMonitoring("tr069_mo_048")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_050() {    //is dependent on #048
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_048")
                .setParameters("Wireless", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_048")
                .enterIntoMonitoring("tr069_mo_048")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_051() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("VoIP settings", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_052() {    //is dependent on #051
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_051")
                .setParameters("VoIP settings", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_051")
                .enterIntoMonitoring("tr069_mo_051")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_053() {    //is dependent on #051
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_051")
                .setParameters("VoIP settings", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_051")
                .enterIntoMonitoring("tr069_mo_051")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_054() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("Management", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_055() {    //is dependent on #054
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_054")
                .setParameters("Management", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_054")
                .enterIntoMonitoring("tr069_mo_054")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_056() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("Management", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .pause(1000)
                .selectItem()
                .bottomMenu(ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_057() {    //is dependent on #056
        monPage
                .topMenu(MONITORING)
                .selectItem("tr069_mo_056")
                .bottomMenu(STOP)
                .okButtonPopUp()
                .waitForStatus("Not active", "tr069_mo_056");
    }

    @Test
    public void tr069_mo_058() {    //is dependent on #056
        monPage
                .topMenu(MONITORING)
                .selectItem("tr069_mo_056")
                .bottomMenu(ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr069_mo_056");
    }

    @Test
    public void tr069_mo_059() {    //is dependent on #056
        monPage
                .topMenu(MONITORING)
                .selectItem("tr069_mo_056")
                .bottomMenu(STOP_WITH_RESET)
                .okButtonPopUp()
                .waitForStatus("Not active", "tr069_mo_056");
    }

    @Test
    public void tr069_mo_060() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .clickOn("calDateTo")
                .selectShiftedDate("calDateTo", 0)
                .setEndDateDelay(-10)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .assertEqualsAlertMessage("Ending date can't be scheduled to the past")
                .selectShiftedDate("calDateTo", 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .assertEqualsAlertMessage("Ending date can't be scheduled to the past");
    }

    @Test
    public void tr069_mo_061() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("Management", 0, 100)
                .setParameters("Information", 0, 0)
                .setParameters("Time", 0, 0)
                .setParameters("DSL settings", 0, 0)
                .setParameters("WAN", 0, 0)
                .setParameters("LAN", 0, 0)
                .setParameters("Wireless", 0, 0)
                .setParameters("VoIP settings", 0, 0)
                .selectShiftedDate("calDateTo", 0)
                .setEndDateDelay(2).setEndDateDelay(2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .waitForStatus("Completed", 150)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_062() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setAdvancedParameters("InternetGatewayDevice.ManagementServer", 0, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_063() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .scheduledToRadioButton()
                .selectShiftedDate("calDateFrom", 0)
                .setScheduledDelay(10)
                .selectSendTo("All")
                .setParameters("Management", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_064() {    //is dependent on #063
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_063")
                .scheduledToRadioButton()
                .setParameters("Management", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_063")
                .enterIntoMonitoring("tr069_mo_063")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_065() {    //is dependent on #063
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_063")
                .scheduledToRadioButton()
                .setParameters("Management", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_063")
                .enterIntoMonitoring("tr069_mo_063")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_066() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .scheduledToRadioButton()
                .selectShiftedDate("calDateFrom", 0)
                .setScheduledDelay(10)
                .selectSendTo("All")
                .setParameters("Information", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_067() {    //is dependent on #066
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_066")
                .scheduledToRadioButton()
                .setParameters("Information", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_066")
                .enterIntoMonitoring("tr069_mo_066")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_068() {    //is dependent on #066
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_066")
                .scheduledToRadioButton()
                .setParameters("Information", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_066")
                .enterIntoMonitoring("tr069_mo_066")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_069() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .scheduledToRadioButton()
                .selectShiftedDate("calDateFrom", 0)
                .setScheduledDelay(10)
                .selectSendTo("All")
                .setParameters("Time", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_070() {    //is dependent on #069
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_069")
                .scheduledToRadioButton()
                .setParameters("Time", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_069")
                .enterIntoMonitoring("tr069_mo_069")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_071() {    //is dependent on #069
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_069")
                .scheduledToRadioButton()
                .setParameters("Time", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_069")
                .enterIntoMonitoring("tr069_mo_069")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_072() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .scheduledToRadioButton()
                .selectShiftedDate("calDateFrom", 0)
                .setScheduledDelay(10)
                .selectSendTo("All")
                .setParameters("DSL settings", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_073() {    //is dependent on #072
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_072")
                .scheduledToRadioButton()
                .setParameters("DSL settings", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_072")
                .enterIntoMonitoring("tr069_mo_072")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_074() {    //is dependent on #072
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_072")
                .scheduledToRadioButton()
                .setParameters("DSL settings", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_072")
                .enterIntoMonitoring("tr069_mo_072")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_075() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .scheduledToRadioButton()
                .selectShiftedDate("calDateFrom", 0)
                .setScheduledDelay(10)
                .selectSendTo("All")
                .setParameters("WAN", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_076() {    //is dependent on #075
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_075")
                .scheduledToRadioButton()
                .setParameters("WAN", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_075")
                .enterIntoMonitoring("tr069_mo_075")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_077() {    //is dependent on #075
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_075")
                .scheduledToRadioButton()
                .setParameters("WAN", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_075")
                .enterIntoMonitoring("tr069_mo_075")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_078() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .scheduledToRadioButton()
                .selectShiftedDate("calDateFrom", 0)
                .setScheduledDelay(10)
                .selectSendTo("All")
                .setParameters("LAN", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_079() {    //is dependent on #078
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_078")
                .scheduledToRadioButton()
                .setParameters("LAN", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_078")
                .enterIntoMonitoring("tr069_mo_078")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_080() {    //is dependent on #078
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_078")
                .scheduledToRadioButton()
                .setParameters("LAN", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_078")
                .enterIntoMonitoring("tr069_mo_078")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_081() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .scheduledToRadioButton()
                .selectShiftedDate("calDateFrom", 0)
                .setScheduledDelay(10)
                .selectSendTo("All")
                .setParameters("Wireless", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_082() {    //is dependent on #081
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_081")
                .scheduledToRadioButton()
                .setParameters("Wireless", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_081")
                .enterIntoMonitoring("tr069_mo_081")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_083() {    //is dependent on #081
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_081")
                .scheduledToRadioButton()
                .setParameters("Wireless", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_081")
                .enterIntoMonitoring("tr069_mo_081")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_084() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .scheduledToRadioButton()
                .selectShiftedDate("calDateFrom", 0)
                .setScheduledDelay(10)
                .selectSendTo("All")
                .setParameters("VoIP settings", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_085() {    //is dependent on #084
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_084")
                .scheduledToRadioButton()
                .setParameters("VoIP settings", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_084")
                .enterIntoMonitoring("tr069_mo_084")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_086() {    //is dependent on #084
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr069_mo_084")
                .scheduledToRadioButton()
                .setParameters("VoIP settings", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr069_mo_084")
                .enterIntoMonitoring("tr069_mo_084")
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_087() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .scheduledToRadioButton()
                .selectShiftedDate("calDateFrom", 0)
                .setScheduledDelay(10)
                .selectSendTo("All")
                .setParameters("VoIP settings", 0, 1)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .selectItem()
                .bottomMenu(STOP)
                .okButtonPopUp()
                .bottomMenu(REFRESH)
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_mo_088() {
        monPage
                .topMenu(MONITORING)
                .selectItem("tr069_mo_087")
                .bottomMenu(ACTIVATE)
                .okButtonPopUp()
                .bottomMenu(REFRESH)
                .waitForStatus("Scheduled", "tr069_mo_087");
    }

    @Test
    public void tr069_mo_089() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .selectSendTo("All")
                .scheduledToRadioButton()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .assertEqualsAlertMessage("Activation date can't be scheduled to the past");
    }

    @Test
    public void tr069_mo_090() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .assertPresenceOfValue("tblModels", 0, BasePage.deviceToString());
    }

    @Test
    public void tr069_mo_091() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(IMPORT)
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_mo_092() {
        monPage
                .topMenu(MONITORING)
                .validateFilteringByManufacturer();
    }

    @Test
    public void tr069_mo_093() {
        monPage
                .topMenu(MONITORING)
                .validateFilteringByModelName();
    }

    @Test
    public void tr069_mo_094() {
        monPage
                .topMenu(MONITORING)
                .deleteAllCustomViews()
                .newViewButton()
                .fillCustomViewName()
                .bottomMenu(NEXT)
                .setViewColumns(1, 99)
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .validateViewColumns();
    }

    @Test
    public void tr069_mo_095() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr069_mo_094")
                .validateSorting("Created");
    }

    @Test
    public void tr069_mo_096() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr069_mo_094")
                .validateSorting("Date from");
    }

    @Test
    public void tr069_mo_097() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr069_mo_094")
                .validateSorting("Date to");
    }

    @Test
    public void tr069_mo_098() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr069_mo_094")
                .validateSorting("Description");
    }

    @Test
    public void tr069_mo_099() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr069_mo_094")
                .validateSorting("Name");
    }

    @Test
    public void tr069_mo_100() {    //Bug: Unclear sorting algorithm by "State" column
        monPage
                .topMenu(MONITORING)
                .selectView("tr069_mo_094")
                .validateSorting("State");
    }

    @Test
    public void tr069_mo_101() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr069_mo_094")
                .validateSorting("Updated");
    }

    @Test
    public void tr069_mo_102() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr069_mo_094")
                .resetView()
                .assertSelectedOptionIs("ddlView", "Default");
    }

    @Test
    public void tr069_mo_103() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .selectSendTo()
                .addAnotherModel()
                .setParametersFor2Devices(true);
    }

    @Test
    public void tr069_mo_104() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .selectSendTo()
                .addAnotherModel()
                .setParametersFor2Devices(false);
    }

    @Test
    public void tr069_mo_105() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .deleteAllGroups()
                .newGroupButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .bottomMenu(CANCEL)
                .assertInputHasText("tbName", testName);
    }

    @Test
    public void tr069_mo_106() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .assertButtonIsEnabled(false, "btnDelFilter_btn")
                .filterRecordsCheckbox()
                .assertButtonIsEnabled(true, "btnDelFilter_btn")
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertSelectedOptionIs("ddlSend", testName);
    }

    @Test
    public void tr069_mo_107() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE, SAVE);
    }

    @Test
    public void tr069_mo_108() {    //is dependent on #106
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .newGroupButton()
                .fillGroupName("tr069_mo_106")
                .bottomMenu(NEXT)
                .assertPresenceOfElements("lblNameInvalid");
    }

    @Test
    public void tr069_mo_109() {//is dependent on #106
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .selectSendTo("tr069_mo_106")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "tr069_mo_106");
    }

    @Test
    public void tr069_mo_110() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertSelectedOptionIs("ddlSend", testName)
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running", 5);
    }

    @Test
    public void tr069_mo_111() {
        DataBaseConnector.createFilterPreconditions(BasePage.getSerial());
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("On Day")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_112() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Prior to")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_113() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Later than")
                .clickOn("calFilterDate_image")
                .selectDate(CalendarUtil.getMonthBeforeDate())
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_114() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Today")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_115() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Before Today")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_116() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Yesterday")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_117() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Prev 7 days")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_118() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Prev X days")
                .inputText("txtInt", "4")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_119() {
        monPage
                .presetFilter("mycust03", testName)
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .selectCompare("=")
                .inputText("txtText", testName)
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_120() {
        monPage
                .presetFilter("mycust03", testName)
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .selectCompare("!=")
                .inputText("txtText", testName)
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_121() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("MAC address")
                .selectCompare("Starts with")
                .partOfMacAddress()
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE, SAVE);
    }

    @Test
    public void tr069_mo_122() {
        monPage
                .presetFilter("mycust03", testName)
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .selectCompare("Like")
                .inputText("txtText", testName.substring(1, 5))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_123() {
        monPage
                .presetFilter("mycust03", testName)
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .selectCompare("No like")
                .inputText("txtText", testName.substring(1, 5))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_124() {
        monPage
                .presetFilter("mycust03", testName)
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_125() {
        monPage
                .presetFilter("mycust03", testName)
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_126() {
        monPage
                .presetFilter("mycust03", testName)
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectManufacturer()
                .addAnotherModel()
                .newGroupButton()
                .fillGroupName(testName + "_1")
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("mycust03")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }

    @Test
    public void tr069_mo_127() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("mycust04")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectManufacturer()
                .addAnotherModel()
                .newGroupButton()
                .fillGroupName(testName + "_1")
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("mycust04")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running");
    }
}