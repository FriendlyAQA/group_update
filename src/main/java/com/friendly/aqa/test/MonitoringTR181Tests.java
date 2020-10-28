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
* At least 3 devices with different serials MUST BE registered for current model name;
*/

@Listeners(UniversalVideoListener.class)
public class MonitoringTR181Tests extends BaseTestCase {

    @Test
    public void tr181_mo_000() {
        monPage.createPreconditions();
    }

    @Test
    public void tr181_mo_001() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr181_mo_002() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageIsDisplayed()
                .bottomMenu(REFRESH)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr181_mo_003() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageIsDisplayed()
//                .deleteAllMonitors()
//                .deleteAllCustomViews()
                .newViewButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .assertButtonsAreEnabled(true, CANCEL)
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed()
                .assertButtonsAreEnabled(false, ACTIVATE, STOP, DELETE)
                .assertButtonsAreEnabled(true, REFRESH);
    }

    @Test
    public void tr181_mo_004() {
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
    public void tr181_mo_005() {
        monPage
                .topMenu(MONITORING)
                .newViewButton()
                .fillViewName("tr181_mo_004")
                .bottomMenu(NEXT)
                .assertElementsArePresent("lblNameInvalid");
    }

    @Test
    public void tr181_mo_006() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr181_mo_004")
                .editButton()
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr181_mo_007() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr181_mo_004")
                .editButton()
                .forPublicCheckbox()
                .forUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertMainPageIsDisplayed()
                .assertSelectedViewIs("tr181_mo_004")
                .topMenu(GROUP_UPDATE)
                .topMenu(MONITORING)
                .assertSelectedViewIs("tr181_mo_004");
    }

    @Test
    public void tr181_mo_008() {
        monPage
                .topMenu(MONITORING)
                .selectView("Default")
                .assertSelectedViewIs("Default");
    }

    @Test
    public void tr181_mo_009() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr181_mo_004")
                .assertSelectedViewIs("tr181_mo_004");
    }

    @Test
    public void tr181_mo_010() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr181_mo_004")
                .editButton()
                .forPublicCheckbox()
                .forUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("tr181_mo_004")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertSelectedViewIs("Default");
    }

    @Test
    public void tr181_mo_011() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .assertElementsArePresent("lbActivate")
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr181_mo_012() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .assertButtonIsEnabled(false, "btnAddModel_btn");
    }

    @Test
    public void tr181_mo_013() {
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
    public void tr181_mo_014() {
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
    public void tr181_mo_015() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
//                .deleteAllGroups()
                .newGroupButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .bottomMenu(CANCEL)
                .assertInputHasText("tbName", testName);
    }

    @Test
    public void tr181_mo_016() {
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
                .validateSelectedGroup();
    }

    @Test
    public void tr181_mo_017() {
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
    public void tr181_mo_018() {    //is dependent on #016
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .newGroupButton()
                .fillGroupName("tr181_mo_016")
                .bottomMenu(NEXT)
                .assertElementsArePresent("lblNameInvalid");
    }

    @Test
    public void tr181_mo_019() {    //is dependent on #016
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("tr181_mo_016")
                .assertCellStartsWith("tabsSettings_tblTabs", 1, -2, "Devices");
    }

    @Test
    public void tr181_mo_020() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .validateDevicesAmount();
    }

    @Test
    public void tr181_mo_021() {
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
                .validateDevicesAmount(1);
    }

    @Test
    public void tr181_mo_022() {
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
    public void tr181_mo_023() {//is dependent on #016
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .selectSendTo("tr181_mo_016")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "tr181_mo_016");
    }

    @Test
    public void tr181_mo_024() {
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
    public void tr181_mo_025() {
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
    public void tr181_mo_026() {    //is dependent on #025
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_025")
                .immediately()
                .setParameters("Management", 2, 2)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", "tr181_mo_025")
                .enterIntoMonitoring("tr181_mo_025")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_027() {
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
    public void tr181_mo_028() {    //is dependent on #027
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_027")
                .setParameters("Information", 8, 8)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_027")
                .enterIntoMonitoring("tr181_mo_027")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_029() {    //is dependent on #027 and #028
        tr181_mo_028();
    }

    @Test
    public void tr181_mo_030() {
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
    public void tr181_mo_031() {    //is dependent on #030
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_030")
                .setParameters("Management", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_030")
                .enterIntoMonitoring("tr181_mo_030")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_032() {    //is dependent on #030
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_030")
                .setParameters("Management", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_030")
                .enterIntoMonitoring("tr181_mo_030")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_033() {
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
    public void tr181_mo_034() {    //is dependent on #033
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_033")
                .setParameters("Information", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_033")
                .enterIntoMonitoring("tr181_mo_033")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_035() {    //is dependent on #033
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_033")
                .setParameters("Information", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_033")
                .enterIntoMonitoring("tr181_mo_033")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_036() {
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
    public void tr181_mo_037() {    //is dependent on #036
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_036")
                .setParameters("Time", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_036")
                .enterIntoMonitoring("tr181_mo_036")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_038() {    //is dependent on #036
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_036")
                .setParameters("Time", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_036")
                .enterIntoMonitoring("tr181_mo_036")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_039() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("WiFi", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_040() {    //is dependent on #039
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_039")
                .setParameters("WiFi", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_039")
                .enterIntoMonitoring("tr181_mo_039")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_041() {    //is dependent on #039
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_039")
                .setParameters("WiFi", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_039")
                .enterIntoMonitoring("tr181_mo_039")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_042() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("IP", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_043() {    //is dependent on #042
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_042")
                .setParameters("IP", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_042")
                .enterIntoMonitoring("tr181_mo_042")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_044() {    //is dependent on #042
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_042")
                .setParameters("IP", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_042")
                .enterIntoMonitoring("tr181_mo_042")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_045() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("Firewall", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_046() {    //is dependent on #045
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_045")
                .setParameters("Firewall", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_045")
                .enterIntoMonitoring("tr181_mo_045")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_047() {    //is dependent on #045
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_045")
                .setParameters("Firewall", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_045")
                .enterIntoMonitoring("tr181_mo_045")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_048() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("DHCPv4", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_049() {    //is dependent on #048
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_048")
                .setParameters("DHCPv4", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_048")
                .enterIntoMonitoring("tr181_mo_048")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_050() {    //is dependent on #048
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_048")
                .setParameters("DHCPv4", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_048")
                .enterIntoMonitoring("tr181_mo_048")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_051() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("DHCPv6", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_052() {    //is dependent on #051
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_051")
                .setParameters("DHCPv6", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_051")
                .enterIntoMonitoring("tr181_mo_051")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_053() {    //is dependent on #051
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_051")
                .setParameters("DHCPv6", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_051")
                .enterIntoMonitoring("tr181_mo_051")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_054() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("DNS", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_055() {    //is dependent on #054
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_054")
                .setParameters("DNS", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_054")
                .enterIntoMonitoring("tr181_mo_054")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_056() {    //is dependent on #054
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_054")
                .setParameters("DNS", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_054")
                .enterIntoMonitoring("tr181_mo_054")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_057() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("Users", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_058() {    //is dependent on #057
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_057")
                .setParameters("Users", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_057")
                .enterIntoMonitoring("tr181_mo_057")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_059() {    //is dependent on #057
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_057")
                .setParameters("Users", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_057")
                .enterIntoMonitoring("tr181_mo_057")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_060() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setParameters("Ethernet", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_061() {    //is dependent on #060
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_060")
                .setParameters("Ethernet", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_060")
                .enterIntoMonitoring("tr181_mo_060")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_062() {    //is dependent on #060
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_060")
                .setParameters("Ethernet", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_060")
                .enterIntoMonitoring("tr181_mo_060")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_063() {
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
    public void tr181_mo_064() {    //is dependent on #063
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_063")
                .setParameters("Management", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_063")
                .enterIntoMonitoring("tr181_mo_063")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_065() {
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
    public void tr181_mo_066() {    //is dependent on #065
        monPage
                .topMenu(MONITORING)
                .selectItem("tr181_mo_065")
                .bottomMenu(STOP)
                .okButtonPopUp()
                .waitForStatus("Not active", "tr181_mo_065");
    }

    @Test
    public void tr181_mo_067() {    //is dependent on #065
        monPage
                .topMenu(MONITORING)
                .selectItem("tr181_mo_065")
                .bottomMenu(ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running", "tr181_mo_065");
    }

//    @Test
//    public void tr181_mo_068() {    //is dependent on #065
//        monPage
//                .topMenu(MONITORING)
//                .selectItem("tr181_mo_065")
//                .bottomMenu(STOP_WITH_RESET)  //this button is no longer displayed (BT item #9766)
//                .okButtonPopUp()
//                .waitForStatus("Not active", "tr181_mo_065");
//    }

    @Test
    public void tr181_mo_069() {
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
    public void tr181_mo_070() {
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
                .setParameters("WiFi", 0, 0)
                .setParameters("IP", 0, 0)
                .setParameters("Firewall", 0, 0)
                .setParameters("DHCPv4", 0, 0)
                .setParameters("DHCPv6", 0, 0)
                .setParameters("DNS", 0, 0)
                .setParameters("Users", 0, 0)
                .setParameters("Ethernet", 0, 0)
                .selectShiftedDate("calDateTo", 0)
                .setEndDateDelay(2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .waitForStatus("Completed", 150)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_071() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .selectSendTo("All")
                .setAdvancedParameters("Device.ManagementServer", 0, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_072() {
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
    public void tr181_mo_073() {    //is dependent on #072
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_072")
                .scheduledToRadioButton()
                .setParameters("Management", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_072")
                .enterIntoMonitoring("tr181_mo_072")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_074() {    //is dependent on #072
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_072")
                .scheduledToRadioButton()
                .setParameters("Management", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_072")
                .enterIntoMonitoring("tr181_mo_072")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_075() {
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
    public void tr181_mo_076() {    //is dependent on #075
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_075")
                .scheduledToRadioButton()
                .setParameters("Information", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_075")
                .enterIntoMonitoring("tr181_mo_075")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_077() {    //is dependent on #075
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_075")
                .scheduledToRadioButton()
                .setParameters("Information", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_075")
                .enterIntoMonitoring("tr181_mo_075")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_078() {
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
    public void tr181_mo_079() {    //is dependent on #078
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_078")
                .scheduledToRadioButton()
                .setParameters("Time", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_078")
                .enterIntoMonitoring("tr181_mo_078")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_080() {    //is dependent on #078
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_078")
                .scheduledToRadioButton()
                .setParameters("Time", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_078")
                .enterIntoMonitoring("tr181_mo_078")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_081() {
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
                .setParameters("WiFi", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_082() {    //is dependent on #081
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_081")
                .scheduledToRadioButton()
                .setParameters("WiFi", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_081")
                .enterIntoMonitoring("tr181_mo_081")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_083() {    //is dependent on #081
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_081")
                .scheduledToRadioButton()
                .setParameters("WiFi", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_081")
                .enterIntoMonitoring("tr181_mo_081")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_084() {
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
                .setParameters("IP", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_085() {    //is dependent on #084
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_084")
                .scheduledToRadioButton()
                .setParameters("IP", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_084")
                .enterIntoMonitoring("tr181_mo_084")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_086() {    //is dependent on #084
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_084")
                .scheduledToRadioButton()
                .setParameters("IP", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_084")
                .enterIntoMonitoring("tr181_mo_084")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_087() {
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
                .setParameters("Firewall", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_088() {    //is dependent on #087
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_087")
                .scheduledToRadioButton()
                .setParameters("Firewall", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_087")
                .enterIntoMonitoring("tr181_mo_087")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_089() {    //is dependent on #087
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_087")
                .scheduledToRadioButton()
                .setParameters("Firewall", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_087")
                .enterIntoMonitoring("tr181_mo_087")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_090() {
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
                .setParameters("DHCPv4", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_091() {    //is dependent on #090
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_090")
                .scheduledToRadioButton()
                .setParameters("DHCPv4", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_090")
                .enterIntoMonitoring("tr181_mo_090")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_092() {    //is dependent on #090
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_090")
                .scheduledToRadioButton()
                .setParameters("DHCPv4", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_090")
                .enterIntoMonitoring("tr181_mo_090")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_093() {
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
                .setParameters("DHCPv6", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_094() {    //is dependent on #093
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_093")
                .scheduledToRadioButton()
                .setParameters("DHCPv6", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_093")
                .enterIntoMonitoring("tr181_mo_093")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_095() {    //is dependent on #093
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_093")
                .scheduledToRadioButton()
                .setParameters("DHCPv6", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_093")
                .enterIntoMonitoring("tr181_mo_093")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_096() {
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
                .setParameters("DNS", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_097() {    //is dependent on #096
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_096")
                .scheduledToRadioButton()
                .setParameters("DNS", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_096")
                .enterIntoMonitoring("tr181_mo_096")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_098() {    //is dependent on #096
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_096")
                .scheduledToRadioButton()
                .setParameters("DNS", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_096")
                .enterIntoMonitoring("tr181_mo_096")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_099() {
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
                .setParameters("Users", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_100() {    //is dependent on #099
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_099")
                .scheduledToRadioButton()
                .setParameters("Users", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_099")
                .enterIntoMonitoring("tr181_mo_099")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_101() {    //is dependent on #099
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_099")
                .scheduledToRadioButton()
                .setParameters("Users", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_099")
                .enterIntoMonitoring("tr181_mo_099")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_102() {
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
                .setParameters("Ethernet", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_103() {    //is dependent on #102
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_102")
                .scheduledToRadioButton()
                .setParameters("Ethernet", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_102")
                .enterIntoMonitoring("tr181_mo_102")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_104() {    //is dependent on #102
        monPage
                .topMenu(MONITORING)
                .enterIntoMonitoring("tr181_mo_102")
                .scheduledToRadioButton()
                .setParameters("Ethernet", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", "tr181_mo_102")
                .enterIntoMonitoring("tr181_mo_102")
                .validateAddedTasks();
    }

    @Test
    public void tr181_mo_105() {
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
                .setParameters("Management", 0, 1)
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
    public void tr181_mo_106() {
        monPage
                .topMenu(MONITORING)
                .selectItem("tr181_mo_105")
                .bottomMenu(ACTIVATE)
                .okButtonPopUp()
                .bottomMenu(REFRESH)
                .waitForStatus("Scheduled", "tr181_mo_105");
    }

    @Test
    public void tr181_mo_107() {
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
    public void tr181_mo_108() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(IMPORT)
                .selectImportMonitorFile()
                .assertPresenceOfValue("tblModels", 0, BasePage.deviceToString());
    }

    @Test
    public void tr181_mo_109() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(IMPORT)
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr181_mo_110() {
        monPage
                .topMenu(MONITORING)
                .validateFilteringByManufacturer();
    }

    @Test
    public void tr181_mo_111() {
        monPage
                .topMenu(MONITORING)
                .validateFilteringByModelName();
    }

    @Test
    public void tr181_mo_112() {
        monPage
                .topMenu(MONITORING)
//                .deleteAllCustomViews()
                .newViewButton()
                .fillCustomViewName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 99)
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .validateViewColumns();
    }

    @Test
    public void tr181_mo_113() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr181_mo_112")
                .validateSorting("Created");
    }

    @Test
    public void tr181_mo_114() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr181_mo_112")
                .validateSorting("Date from");
    }

    @Test
    public void tr181_mo_115() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr181_mo_112")
                .validateSorting("Date to");
    }

    @Test
    public void tr181_mo_116() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr181_mo_112")
                .validateSorting("Description");
    }

    @Test
    public void tr181_mo_117() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr181_mo_112")
                .validateSorting("Name");
    }

    @Test
    public void tr181_mo_118() {    //Bug: Unclear sorting algorithm by "State" column
        monPage
                .topMenu(MONITORING)
                .selectView("tr181_mo_112")
                .validateSorting("State");
    }

    @Test
    public void tr181_mo_119() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr181_mo_112")
                .validateSorting("Updated");
    }

    @Test
    public void tr181_mo_120() {
        monPage
                .topMenu(MONITORING)
                .selectView("tr181_mo_112")
                .resetView()
                .assertSelectedViewIs( "Default");
    }

    @Test
    public void tr181_mo_121() {
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
    public void tr181_mo_122() {
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
    public void tr181_mo_123() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
//                .deleteAllGroups()
                .newGroupButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .bottomMenu(CANCEL)
                .assertInputHasText("tbName", testName);
    }

    @Test
    public void tr181_mo_124() {
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
                .validateSelectedGroup();
    }

    @Test
    public void tr181_mo_125() {
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
    public void tr181_mo_126() {    //is dependent on #124
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .immediately()
                .newGroupButton()
                .fillGroupName("tr181_mo_124")
                .bottomMenu(NEXT)
                .assertElementsArePresent("lblNameInvalid");
    }

    @Test
    public void tr181_mo_127() {//is dependent on #124
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModelButton()
                .selectSendTo("tr181_mo_124")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "tr181_mo_124");
    }

    @Test
    public void tr181_mo_128() {
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
                .validateSelectedGroup()
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running", 5);
    }

    @Test
    public void tr181_mo_129() {
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
    public void tr181_mo_130() {
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
    public void tr181_mo_131() {
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
    public void tr181_mo_132() {
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
    public void tr181_mo_133() {
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
    public void tr181_mo_134() {
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
    public void tr181_mo_135() {
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
    public void tr181_mo_136() {
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
    public void tr181_mo_137() {
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
    public void tr181_mo_138() {
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
    public void tr181_mo_139() {
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
    public void tr181_mo_140() {
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
    public void tr181_mo_141() {
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
    public void tr181_mo_142() {
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
    public void tr181_mo_143() {
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
    public void tr181_mo_144() {
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
    public void tr181_mo_145() {
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
