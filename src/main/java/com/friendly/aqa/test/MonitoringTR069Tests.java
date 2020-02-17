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
    public void tr069_mo_018() {
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
    public void tr069_mo_019() {
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
        monPage
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
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE);
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
                .getTable("tblDataParams")
                .clickOn(1, 1, 0);
        monPage
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .getMainTable()
                .assertPresenceOfValue(2, testName);
        setTargetTestName();
    }
}
