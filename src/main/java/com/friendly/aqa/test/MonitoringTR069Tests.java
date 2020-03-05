package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.utils.CalendarUtil;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.pageobject.GlobalButtons.*;
import static com.friendly.aqa.pageobject.MonitoringPage.Left.IMPORT;
import static com.friendly.aqa.pageobject.MonitoringPage.Left.NEW;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.TopMenu.MONITORING;

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
                .globalButtons(REFRESH)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_mo_003() {
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
    public void tr069_mo_004() {
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
    public void tr069_mo_005() {
        monPage
                .topMenu(MONITORING)
                .newViewButton()
                .fillViewName(targetTestName)
                .globalButtons(NEXT)
                .assertElementIsPresent("lblNameInvalid");
    }

    @Test
    public void tr069_mo_006() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .editViewButton()
                .globalButtons(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_mo_007() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .editViewButton()
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
    public void tr069_mo_008() {
        monPage
                .topMenu(MONITORING)
                .selectView("Default")
                .assertEquals(monPage.getSelectedValue("ddlView"), "Default");
    }

    @Test
    public void tr069_mo_009() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .assertEquals(monPage.getSelectedValue("ddlView"), targetTestName);
    }

    @Test
    public void tr069_mo_010() {
        monPage
                .topMenu(MONITORING)
                .selectView("Default")
                .editViewButton()
                .forPublicCheckbox()
                .forUserCheckbox()
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectView(targetTestName)
                .editViewButton()
                .globalButtons(DELETE_GROUP)
                .okButtonPopUp()
                .assertEquals(monPage.getSelectedValue("ddlView"), "Default");
    }

    @Test
    public void tr069_mo_011() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .assertElementIsPresent("lbActivate")
                .globalButtons(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_mo_012() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .assertFalse(monPage.isButtonActive("btnAddModel_btn"));
    }

    @Test
    public void tr069_mo_013() {
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
    public void tr069_mo_014() {
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
    public void tr069_mo_015() {
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
    public void tr069_mo_016() {
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
    public void tr069_mo_017() {
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
    public void tr069_mo_018() {    //is dependent on #016
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
    public void tr069_mo_019() {    //is dependent on #016
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
    public void tr069_mo_020() {
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
    public void tr069_mo_021() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("Individual")
                .assertButtonAreActive("btnSelectDevices_btn")
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
                .addModel()
                .selectSendTo("Import from a file")
                .selectImportDevicesFile()
                .pause(2000)
                .assertCellEndsWith("tabsSettings_tblTabs", 1, -2, " 2");
    }

    @Test
    public void tr069_mo_023() {    //Bug: 'Delete Group' button doesn't delete device group.
        monPage                     //is dependent on #016
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .selectSendTo(targetTestName)
                .editViewButton()
                .globalButtons(DELETE_GROUP)
                .okButtonPopUp()
                .assertFalse(guPage.isOptionPresent("ddlSend", targetTestName), "Option '" + targetTestName + "' is present on 'Send to' list!\n");
    }

    @Test
    public void tr069_mo_024() {
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
    public void tr069_mo_025() {
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
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_026() {    //is dependent on #025
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .immediately()
                .setParameters("Management", 2, 2)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_027() {
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
    public void tr069_mo_028() {    //is dependent on #027
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
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("Management", 0, 0)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_031() {    //is dependent on #030
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Management", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_032() {    //is dependent on #030
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Management", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_033() {
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
    public void tr069_mo_034() {    //is dependent on #033
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
    public void tr069_mo_035() {    //is dependent on #033
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
    public void tr069_mo_036() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("Time", 0, 0)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_037() {    //is dependent on #036
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Time", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_038() {    //is dependent on #036
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Time", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_039() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("DSL settings", 0, 0)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_040() {    //is dependent on #039
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("DSL settings", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_041() {    //is dependent on #039
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("DSL settings", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_042() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("WAN", 0, 0)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_043() {    //is dependent on #042
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("WAN", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_044() {    //is dependent on #042
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("WAN", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_045() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("LAN", 0, 0)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_046() {    //is dependent on #045
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("LAN", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_047() {    //is dependent on #045
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("LAN", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_048() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("Wireless", 0, 0)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_049() {    //is dependent on #048
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Wireless", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_050() {    //is dependent on #048
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Wireless", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_051() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setParameters("VoIP settings", 0, 0)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_052() {    //is dependent on #051
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("VoIP settings", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_053() {    //is dependent on #051
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("VoIP settings", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_054() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_055() {    //is dependent on #054
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .setParameters("Management", 0, 0)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_056() {
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
    public void tr069_mo_057() {    //is dependent on #056
        monPage
                .topMenu(MONITORING)
                .selectItem(targetTestName)
                .globalButtons(STOP)
                .okButtonPopUp()
                .waitForStatus("Not active", targetTestName);
    }

    @Test
    public void tr069_mo_058() {    //is dependent on #056
        monPage
                .topMenu(MONITORING)
                .selectItem(targetTestName)
                .globalButtons(ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName);
    }

    @Test
    public void tr069_mo_059() {    //is dependent on #056
        monPage
                .topMenu(MONITORING)
                .selectItem(targetTestName)
                .globalButtons(STOP_WITH_RESET)
                .okButtonPopUp()
                .waitForStatus("Not active", targetTestName);
    }

    @Test
    public void tr069_mo_060() {
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
    public void tr069_mo_061() {
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
                .setParameters("Time", 0, 0)
                .setParameters("DSL settings", 0, 0)
                .setParameters("WAN", 0, 0)
                .setParameters("LAN", 0, 0)
                .setParameters("Wireless", 0, 0)
                .setParameters("VoIP settings", 0, 0)
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
    public void tr069_mo_062() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .immediately()
                .selectSendTo("All")
                .setAdvancedParameters("InternetGatewayDevice.ManagementServer", 0, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active")
                .enterIntoGroup()
                .checkResults();
    }

    @Test
    public void tr069_mo_063() {
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
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_064() {    //is dependent on #063
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("Management", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_065() {    //is dependent on #063
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("Management", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_066() {
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
    public void tr069_mo_067() {    //is dependent on #066
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
    public void tr069_mo_068() {    //is dependent on #066
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
    public void tr069_mo_069() {
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
                .setParameters("Time", 0, 0)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_070() {    //is dependent on #069
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("Time", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_071() {    //is dependent on #069
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("Time", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_072() {
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
                .setParameters("DSL settings", 0, 0)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_073() {    //is dependent on #072
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("DSL settings", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_074() {    //is dependent on #072
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("DSL settings", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_075() {
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
                .setParameters("WAN", 0, 0)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_076() {    //is dependent on #075
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("WAN", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_077() {    //is dependent on #075
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("WAN", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_078() {
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
                .setParameters("LAN", 0, 0)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_079() {    //is dependent on #078
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("LAN", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_080() {    //is dependent on #078
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("LAN", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_081() {
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
                .setParameters("Wireless", 0, 0)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_082() {    //is dependent on #081
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("Wireless", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_083() {    //is dependent on #081
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("Wireless", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_084() {
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
                .setParameters("VoIP settings", 0, 0)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled")
                .enterIntoGroup()
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_085() {    //is dependent on #084
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("VoIP settings", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_086() {    //is dependent on #084
        monPage
                .topMenu(MONITORING)
                .enterIntoGroup(targetTestName)
                .scheduledToRadioButton()
                .setParameters("VoIP settings", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", targetTestName)
                .enterIntoGroup(targetTestName)
                .checkResults();
    }

    @Test
    public void tr069_mo_087() {
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
                .setParameters("VoIP settings", 0, 1)
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
    public void tr069_mo_088() {
        monPage
                .topMenu(MONITORING)
                .selectItem(targetTestName)
                .globalButtons(ACTIVATE)
                .okButtonPopUp()
                .globalButtons(REFRESH)
                .waitForStatus("Scheduled", targetTestName);
    }

    @Test
    public void tr069_mo_089() {
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
    public void tr069_mo_090() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .assertPresenceOfValue("tblModels", 0, "AudioCodes MP252");
    }

    @Test
    public void tr069_mo_091() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(IMPORT)
                .globalButtons(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_mo_092() {
        monPage
                .topMenu(MONITORING)
                .checkFilteringByManufacturer();
    }

    @Test
    public void tr069_mo_093() {
        monPage
                .topMenu(MONITORING)
                .checkFilteringByModelName();
    }

    @Test
    public void tr069_mo_094() {
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
    public void tr069_mo_095() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Created");
    }

    @Test
    public void tr069_mo_096() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Date from");
    }

    @Test
    public void tr069_mo_097() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Date to");
    }

    @Test
    public void tr069_mo_098() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Description");
    }

    @Test
    public void tr069_mo_099() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Name");
    }

    @Test
    public void tr069_mo_100() {    //Bug: Unclear sorting algorithm by "State" column
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("State");
    }

    @Test
    public void tr069_mo_101() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .checkSorting("Updated");
    }

    @Test
    public void tr069_mo_102() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName)
                .resetView()
                .assertEquals(monPage.getSelectedValue("ddlView"), "Default", "View reset does not occur");
    }

    @Test
    public void tr069_mo_103() {
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
    public void tr069_mo_104() {
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
    public void tr069_mo_105() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel()
                .addModel()
                .newGroupButton()
                .assertElementIsPresent("lblHead")
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .assertButtonsAreEnabled(true, CANCEL)
                .globalButtons(CANCEL)
                .assertElementIsPresent("tbName")
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE, SAVE)
                .assertButtonsAreEnabled(true, CANCEL, ADVANCED_VIEW);
    }

    @Test
    public void tr069_mo_110() {
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
    public void tr069_mo_111() {
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
    public void tr069_mo_112() {
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
    public void tr069_mo_113() {
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
    public void tr069_mo_114() {
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
    public void tr069_mo_115() {
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
    public void tr069_mo_116() {
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
    public void tr069_mo_117() {
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
    public void tr069_mo_118() {
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
    public void tr069_mo_119() {
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
    public void tr069_mo_120() {
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
    public void tr069_mo_121() {
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
    public void tr069_mo_122() {
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
    public void tr069_mo_123() {
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
    public void tr069_mo_124() {
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
    public void tr069_mo_125() {
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
    public void tr069_mo_126() {
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
    public void tr069_mo_127() {
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