package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.pageobject.GlobalButtons.*;
import static com.friendly.aqa.pageobject.MonitoringPage.Left.NEW;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.TopMenu.MONITORING;
import static org.testng.Assert.*;

@Listeners(UniversalVideoListener.class)
public class MonitoringTR069Tests extends BaseTestCase {

    @Test
    public void tr069_mo_001() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageDisplayed();
    }

    @Test
    public void tr069_mo_002() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageDisplayed()
                .globalButtons(REFRESH)
                .assertMainPageDisplayed();
    }

    @Test
    public void tr069_mo_003() {
        monPage
                .topMenu(MONITORING)
                .assertMainPageDisplayed()
                .newViewButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .assertButtonsAreEnabled(true, CANCEL)
                .globalButtons(CANCEL)
                .assertMainPageDisplayed()
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
                .getTable("tblFilter")
                .clickOn(1, 1);
        monPage
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .filterRecordsCheckbox();
        assertTrue(monPage.isButtonActive("btnDelFilter_btn"));
        monPage
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertMainPageDisplayed();
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
                .assertMainPageDisplayed();
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
                .assertMainPageDisplayed();
        assertEquals(monPage.getSelectedValue("ddlView"), targetTestName);
        monPage
                .topMenu(GROUP_UPDATE)
                .topMenu(MONITORING);
        assertEquals(monPage.getSelectedValue("ddlView"), targetTestName);
    }

    @Test
    public void tr069_mo_008() {
        monPage
                .topMenu(MONITORING)
                .selectView("Default");
        assertEquals(monPage.getSelectedValue("ddlView"), "Default");
    }

    @Test
    public void tr069_mo_009() {
        monPage
                .topMenu(MONITORING)
                .selectView(targetTestName);
        assertEquals(monPage.getSelectedValue("ddlView"), targetTestName);
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
                .okButtonPopUp();
        assertEquals(monPage.getSelectedValue("ddlView"), "Default");
    }

    @Test
    public void tr069_mo_011() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .assertElementIsPresent("lbActivate")
                .globalButtons(CANCEL)
                .assertMainPageDisplayed();
    }

    @Test
    public void tr069_mo_012() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer();
        assertFalse(monPage.isButtonActive("btnAddModel_btn"));
    }

    @Test
    public void tr069_mo_013() {
        monPage
                .topMenu(MONITORING)
                .leftMenu(NEW)
                .fillName()
                .selectManufacturer()
                .selectModel("MP282")
                .addModel();
        assertEquals(monPage.getAlertTextAndClickOk(), "Template for this model doesn't exist");
        monPage
                .selectModel()
                .addModel()
                .selectSendTo()
                .immediately()
                .getTable("tblDataParams")
                .clickOn(1, 1, 0);
        monPage
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
                .globalButtons(CANCEL);
        assertEquals(monPage.getAttributeById("tbName", "value"), testName);
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
                .globalButtons(NEXT);
        assertFalse(monPage.isButtonActive("btnDelFilter_btn"));
        monPage.filterRecordsCheckbox();
        assertTrue(monPage.isButtonActive("btnDelFilter_btn"));
        monPage
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertEquals(monPage.getSelectedValue("ddlSend"), testName);
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
                .getTabTable()
                .assertStartWith(1, -2, "Devices");
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
                .getTabTable()
                .assertEndsWith(1, -2, " " + getDeviceAmount());
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
                .getTabTable()
                .assertEndsWith(1, -2, " 1");
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
                .getTabTable()
                .assertEndsWith(1, -2, " 1");
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
                .okButtonPopUp();
        assertFalse(guPage.isOptionPresent("ddlSend", targetTestName), "Option '" + targetTestName + "' is present on 'Send to' list!\n");
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
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_026() {    //is dependent on #025
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn("tr069_mo_025");
        monPage
                .immediately()
                .setParameters("Management", 2, 2)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", targetTestName)
                .checkResults(targetTestName);
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
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_028() {    //is dependent on #027
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("Information", 8, 8)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
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
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_031() {    //is dependent on #030
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("Management", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
    }

    @Test
    public void tr069_mo_032() {    //is dependent on #030
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("Management", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
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
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_034() {    //is dependent on #033
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("Information", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
    }

    @Test
    public void tr069_mo_035() {    //is dependent on #033
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("Information", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
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
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_037() {    //is dependent on #036
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("Time", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
    }

    @Test
    public void tr069_mo_038() {    //is dependent on #036
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("Time", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
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
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_040() {    //is dependent on #039
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("DSL settings", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
    }

    @Test
    public void tr069_mo_041() {    //is dependent on #039
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("DSL settings", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
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
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_043() {    //is dependent on #042
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("WAN", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
    }

    @Test
    public void tr069_mo_044() {    //is dependent on #042
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("WAN", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
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
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_046() {    //is dependent on #045
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("LAN", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
    }

    @Test
    public void tr069_mo_047() {    //is dependent on #045
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("LAN", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
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
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_049() {    //is dependent on #048
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("Wireless", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
    }

    @Test
    public void tr069_mo_050() {    //is dependent on #048
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("Wireless", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
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
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_052() {    //is dependent on #051
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("VoIP settings", 1, 3)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
    }

    @Test
    public void tr069_mo_053() {    //is dependent on #051
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("VoIP settings", 4, 100)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
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
                .checkResults();
        setTargetTestName();
    }

    @Test
    public void tr069_mo_055() {    //is dependent on #054
        monPage
                .topMenu(MONITORING)
                .getMainTable()
                .clickOn(targetTestName);
        monPage
                .setParameters("Management", 0, 0)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Running", targetTestName)
                .checkResults(targetTestName);
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
                .selectGroup();
        monPage
                .globalButtons(ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running")
                .checkResults();
    }
}
