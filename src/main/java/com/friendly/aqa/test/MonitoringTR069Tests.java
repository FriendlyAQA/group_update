package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.pageobject.GlobalButtons.*;
import static com.friendly.aqa.pageobject.MonitoringPage.Left.NEW;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.TopMenu.MONITORING;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
                .fillName()
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
                .fillName(targetTestName)
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
}
