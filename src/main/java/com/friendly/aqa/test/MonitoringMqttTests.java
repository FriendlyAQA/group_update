package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.CalendarUtil;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.GlobalButtons.*;
import static com.friendly.aqa.pageobject.MonitoringPage.Left.IMPORT;
import static com.friendly.aqa.pageobject.MonitoringPage.Left.NEW;
import static com.friendly.aqa.entities.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.entities.TopMenu.MONITORING;

@Listeners(UniversalVideoListener.class)
public class MonitoringMqttTests extends BaseTestCase {

    @Test
    public void mqtt_mo_001() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void mqtt_mo_002() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageIsDisplayed()
                .bottomMenu(REFRESH)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void mqtt_mo_003() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageIsDisplayed()
                .newViewButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .assertButtonsAreEnabled(true, CANCEL)
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed()
                .assertButtonsAreEnabled(false, ACTIVATE, STOP, STOP_WITH_RESET, DELETE)
                .assertButtonsAreEnabled(true, REFRESH);
    }

    @Test
    public void mqtt_mo_004() {
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
                .assertTrue(monPage.isButtonActive("btnDelFilter_btn"))
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertMainPageIsDisplayed();
        setTargetTestName();
    }

    @Test
    public void mqtt_mo_005() {
        monPage
                .topMenu(MONITORING)
                .newViewButton()
                .fillViewName(targetTestName)
                .bottomMenu(NEXT)
                .assertPresenceOfElements("lblNameInvalid");
    }

    @Test
    public void mqtt_mo_006() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .editButton()
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void mqtt_mo_007() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .editButton()
                .forPublicCheckbox()
                .forUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertMainPageIsDisplayed()
                .assertEquals(monPage.getSelectedOption("ddlView"), targetTestName)
                .topMenu(GROUP_UPDATE)
                .topMenu(MONITORING)
                .assertEquals(monPage.getSelectedOption("ddlView"), targetTestName);
    }

    @Test
    public void mqtt_mo_008() {
        monPage
                .topMenu(MONITORING)
                .selectView("Default")
                .assertEquals(monPage.getSelectedOption("ddlView"), "Default");
    }

    @Test
    public void mqtt_mo_009() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .assertEquals(monPage.getSelectedOption("ddlView"), targetTestName);
    }

    @Test
    public void mqtt_mo_010() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .editButton()
                .forPublicCheckbox()
                .forUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView(targetTestName)
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertEquals(monPage.getSelectedOption("ddlView"), "Default");
    }

    @Test
    public void mqtt_mo_011() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .assertPresenceOfElements("lbActivate")
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void mqtt_mo_012() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .assertFalse(monPage.isButtonActive("btnAddModel_btn"));
    }

    @Test
    public void mqtt_mo_013() {
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
    public void mqtt_mo_014() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .assertTableHasContent("tabsSettings_tblTabs")
                .bottomMenu(ADVANCED_VIEW)
                .assertTableIsEmpty("tabsSettings_tblTabs")
                .bottomMenu(SIMPLE_VIEW)
                .assertTableHasContent("tabsSettings_tblTabs");
    }

    @Test
    public void mqtt_mo_015() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .bottomMenu(CANCEL)
                .assertEquals(monPage.getAttributeById("tbName", "value"), testName);
    }

    @Test
    public void mqtt_mo_016() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .assertFalse(monPage.isButtonActive("btnDelFilter_btn"))
                .filterRecordsCheckbox()
                .assertTrue(monPage.isButtonActive("btnDelFilter_btn"))
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertEquals(monPage.getSelectedOption("ddlSend"), testName);
        setTargetTestName();
    }

    @Test
    public void mqtt_mo_017() {
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
    public void mqtt_mo_018() {    //is dependent on #016
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
                .bottomMenu(NEXT)
                .assertPresenceOfElements("lblNameInvalid");
    }

    @Test
    public void mqtt_mo_019() {    //is dependent on #016
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
    public void mqtt_mo_020() {
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
    public void mqtt_mo_021() {
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
                .assertCellEndsWith("tabsSettings_tblTabs", 1, -2, " 1");
    }

    @Test
    public void mqtt_mo_022() {
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
                .assertCellMatches("tabsSettings_tblTabs", 1, -2, ".+\\d+$");
    }

    @Test
    public void mqtt_mo_023() {    //Bug: 'Delete Group' button doesn't delete device group.
        monPage                     //is dependent on #016
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .selectSendTo(targetTestName)
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertFalse(guPage.isOptionPresent("ddlSend", targetTestName), "Option '" + targetTestName + "' is present on 'Send to' list!\n");
    }

    @Test
    public void mqtt_mo_024() {
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
    public void mqtt_mo_025() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("Management", 0, 1)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active")
                .enterIntoGroup()
                .checkAddedTasks();
        setTargetTestName();
    }

    @Test
    public void mqtt_mo_026() {    //is dependent on #025
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .immediately()
                .setParameters("Management", 2, 2)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkAddedTasks();
    }

    @Test
    public void mqtt_mo_027() {
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkAddedTasks();
        setTargetTestName();
    }

    @Test
    public void mqtt_mo_028() {    //is dependent on #027
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Information", 8, 8)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkAddedTasks();
    }

    @Test
    public void mqtt_mo_029() {    //is dependent on #027 and #028
        mqtt_mo_028();
    }

    @Test
    public void mqtt_mo_030() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("Management", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkAddedTasks();
        setTargetTestName();
    }

    @Test
    public void mqtt_mo_031() {    //is dependent on #030
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Management", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkAddedTasks();
    }

    @Test
    public void mqtt_mo_032() {    //is dependent on #030
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Management", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkAddedTasks();
    }

    @Test
    public void mqtt_mo_033() {
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkAddedTasks();
        setTargetTestName();
    }

    @Test
    public void mqtt_mo_034() {    //is dependent on #033
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Information", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkAddedTasks();
    }

    @Test
    public void mqtt_mo_035() {    //is dependent on #033
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Information", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkAddedTasks();
    }

    @Test
    public void mqtt_mo_036() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("Management", 0, 0)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkAddedTasks();
        setTargetTestName();
    }

    @Test
    public void mqtt_mo_037() {    //is dependent on #054
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Management", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkAddedTasks();
    }

    @Test
    public void mqtt_mo_038() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
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
                .checkAddedTasks();
        setTargetTestName();
    }

    @Test
    public void mqtt_mo_039() {    //is dependent on #056
        monPage
                .topMenu(MONITORING)
                .selectItem(targetTestName)
                .bottomMenu(STOP)
                .okButtonPopUp()
                .waitForStatus("Not active", targetTestName);
    }

    @Test
    public void mqtt_mo_040() {    //is dependent on #056
        monPage
                .topMenu(MONITORING)
                .selectItem(targetTestName)
                .bottomMenu(ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName);
    }

    @Test
    public void mqtt_mo_041() {    //is dependent on #056
        monPage
                .topMenu(MONITORING)
                .selectItem(targetTestName)
                .bottomMenu(STOP_WITH_RESET)
                .okButtonPopUp()
                .waitForStatus("Not active", targetTestName);
    }

    @Test
    public void mqtt_mo_042() {
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
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .assertEqualsAlertMessage("Finish date can't scheduled to past")
                .selectShiftedDate("calDateTo", 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .assertEqualsAlertMessage("Finish date can't scheduled to past");
    }

    @Test
    public void mqtt_mo_043() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("Management", 0, 100)
                .setParameters("Information", 0, 0)
                .setParameters("Connectivity monitoring", 0, 0)
                .selectShiftedDate("calDateTo", 0)
                .setEndDateDelay(2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .waitForStatus("Completed", 120)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void mqtt_mo_044() {
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
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active")
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void mqtt_mo_045() {
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
                .setParameters("Management", 0, 0)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .checkAddedTasks();
        setTargetTestName();
    }

    @Test
    public void mqtt_mo_046() {    //is dependent on #063
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("Management", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkAddedTasks();
    }

    @Test
    public void mqtt_mo_047() {    //is dependent on #063
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("Management", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkAddedTasks();
    }

    @Test
    public void mqtt_mo_048() {
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
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .checkAddedTasks();
        setTargetTestName();
    }

    @Test
    public void mqtt_mo_049() {    //is dependent on #066
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("Information", 1, 3)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkAddedTasks();
    }

    @Test
    public void mqtt_mo_050() {    //is dependent on #066
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("Information", 4, 100)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkAddedTasks();
    }

    @Test
    public void mqtt_mo_051() {
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
                .checkAddedTasks();
        setTargetTestName();
    }

    @Test
    public void mqtt_mo_052() {
        monPage
                .topMenu(MONITORING)
                .selectItem(targetTestName)
                .bottomMenu(ACTIVATE)
                .okButtonPopUp()
                .bottomMenu(REFRESH)
                .waitForStatus("Scheduled", targetTestName);
    }

    @Test
    public void mqtt_mo_053() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .selectSendTo("All")
                .scheduledToRadioButton()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .assertEqualsAlertMessage("Activation date can't be scheduled to past");
    }

    @Test
    public void mqtt_mo_054() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .assertPresenceOfValue("tblModels", 0, BasePage.deviceToString());
    }

    @Test
    public void mqtt_mo_055() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(IMPORT)
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void mqtt_mo_056() {
        monPage
                .topMenu(MONITORING)
                .checkFilteringByManufacturer();
    }

    @Test
    public void mqtt_mo_057() {
        monPage
                .topMenu(MONITORING)
                .checkFilteringByModelName();
    }

    @Test
    public void mqtt_mo_058() {
        setTargetTestName();
        monPage
                .topMenu(MONITORING)
                .newViewButton()
                .fillCustomViewName()
                .bottomMenu(NEXT)
                .setViewColumns(0, 100)
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .validateViewColumns();
    }

    @Test
    public void mqtt_mo_059() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Created");
    }

    @Test
    public void mqtt_mo_060() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Date from");
    }

    @Test
    public void mqtt_mo_061() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Date to");
    }

    @Test
    public void mqtt_mo_062() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Description");
    }

    @Test
    public void mqtt_mo_063() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Name");
    }

    @Test
    public void mqtt_mo_064() {    //Bug: Unclear sorting algorithm by "State" column
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("State");
    }

    @Test
    public void mqtt_mo_065() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Updated");
    }

    @Test
    public void mqtt_mo_066() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .resetView()
                .assertEquals(monPage.getSelectedOption("ddlView"), "Default", "View reset does not occur");
    }

    @Test
    public void mqtt_mo_067() {
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
    public void mqtt_mo_068() {
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
    public void mqtt_mo_074() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .assertFalse(monPage.isButtonActive("btnDelFilter_btn"))
                .filterRecordsCheckbox()
                .assertTrue(monPage.isButtonActive("btnDelFilter_btn"))
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertEquals(monPage.getSelectedOption("ddlSend"), testName)
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running", 5);
    }

    @Test
    public void mqtt_mo_075() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
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
    public void mqtt_mo_076() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
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
    public void mqtt_mo_077() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
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
    public void mqtt_mo_078() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
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
    public void mqtt_mo_079() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
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
    public void mqtt_mo_080() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
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
    public void mqtt_mo_081() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
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
    public void mqtt_mo_082() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
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
    public void mqtt_mo_083() {
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
    public void mqtt_mo_084() {
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
    public void mqtt_mo_085() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Description")
                .selectCompare("Starts with")
                .inputText("txtText", testName)
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE, SAVE);
    }

    @Test
    public void mqtt_mo_086() {
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
    public void mqtt_mo_087() {
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
    public void mqtt_mo_088() {
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
    public void mqtt_mo_089() {
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
    public void mqtt_mo_090() {
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
    public void mqtt_mo_091() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
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
