package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.entities.Event;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.TopMenu.*;
import static com.friendly.aqa.pageobject.EventsPage.Left.NEW;

@Listeners(UniversalVideoListener.class)
public class EventsTR069Tests extends BaseTestCase {

   /*
   1. At least 1 Event item MUST exist; (004)
   */

    @Test
    public void tr069_ev_001() {
        evPage
                .topMenu(EVENTS)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_ev_002() {
        evPage
                .topMenu(EVENTS)
                .checkRefreshPage();
    }

    @Test
    public void tr069_ev_003() {
        evPage
                .topMenu(EVENTS)
                .assertButtonsAreEnabled(false, ACTIVATE, DEACTIVATE, DELETE)
                .assertButtonsAreEnabled(true, REFRESH)
                .deleteAllCustomViews()   //as precondition for next step and tests
                .newViewButton()
                .fillName()
                .assertButtonsAreEnabled(false, PREVIOUS, FINISH)
                .assertButtonsAreEnabled(true, CANCEL, NEXT)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertInputHasText("txtName", getTestName())
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed()
                .assertButtonsAreEnabled(false, ACTIVATE, DEACTIVATE, DELETE)
                .assertButtonsAreEnabled(true, REFRESH)
                .assertAbsenceOfOptions("ddlView", getTestName());
    }

    @Test
    public void tr069_ev_004() {
        evPage
                .topMenu(EVENTS)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns("Model name")
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Model name")
                .selectCompare("=")
                .selectFilterModelName(getModelName())
                .bottomMenu(NEXT)
                .filterRecordsCheckbox()
                .assertButtonIsEnabled(true, "btnDelFilter_btn")
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .validateViewColumns();
    }

    @Test
    public void tr069_ev_005() {
        evPage
                .topMenu(EVENTS)
                .newViewButton()
                .fillName("Default")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test   //depends on 004
    public void tr069_ev_006() {
        evPage
                .topMenu(EVENTS)
                .selectView("tr069_ev_004")
                .editButton()
                .bottomMenu(CANCEL)
                .assertSelectedViewIs("tr069_ev_004");
    }

    @Test   //depends on 004
    public void tr069_ev_007() {
        evPage
                .topMenu(EVENTS)
                .selectView("tr069_ev_004")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(DEVICE_UPDATE)
                .topMenu(EVENTS)
                .resetView()
                .assertSelectedViewIs("tr069_ev_004")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp();
    }

    @Test   //depends on 004
    public void tr069_ev_008() {
        evPage
                .topMenu(EVENTS)
                .selectView("tr069_ev_004")
                .selectView("Default")
                .assertTableColumnAmountIs(7, "tbl");
    }

    @Test   //depends on 004
    public void tr069_ev_009() {
        evPage
                .topMenu(EVENTS)
                .selectView("tr069_ev_004")
                .assertTableColumnAmountIs(2, "tbl");
    }

    @Test
    public void tr069_ev_010() {
        evPage
                .topMenu(EVENTS)
                .selectView("tr069_ev_004")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "tr069_ev_004");
    }

    @Test
    public void tr069_ev_011() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE)
                .bottomMenu(CANCEL)
                .assertButtonsAreEnabled(false, ACTIVATE, DEACTIVATE, DELETE)
                .assertButtonsAreEnabled(true, REFRESH);
    }

    @Test
    public void tr069_ev_012() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE);
    }

    @Test
    public void tr069_ev_013() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE);
    }

    @Test
    public void tr069_ev_014() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .bottomMenu(ADVANCED_VIEW)
                .assertAdvancedViewApplied()
                .bottomMenu(SIMPLE_VIEW)
                .assertSimpleViewApplied();
    }

    @Test
    public void tr069_ev_015() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(CANCEL)
                .assertAbsenceOfOptions("ddlSend", getTestName());
    }

//    @Test
//    public void tr069_ev_016() {
//        evPage
//                .topMenu(EVENTS)
//                .leftMenu(NEW)
//                .selectManufacturer()
//                .selectModelName()
//                .newGroupButton()
//                .fillGroupName()
//                .bottomMenu(NEXT)
//                .addFilter()
//                .selectColumnFilter("Created")
//                .selectCompare("Is not null")
//                .bottomMenu(NEXT)
//                .assertButtonIsEnabled(false, "btnDelFilter_btn")
//                .filterRecordsCheckbox()
//                .assertButtonIsEnabled(true, "btnDelFilter_btn")
//                .bottomMenu(FINISH)
//                .okButtonPopUp()
//                .validateSelectedGroup();
//    }

    @Test
    public void tr069_ev_016() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Serial")
                .selectCompare("=")
                .inputTextField(getSerial())
                .bottomMenu(NEXT)
                .assertButtonIsEnabled(false, "btnDelFilter_btn")
                .filterRecordsCheckbox()
                .assertButtonIsEnabled(true, "btnDelFilter_btn")
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .validateSelectedGroup();
    }

    @Test
    public void tr069_ev_017() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .newGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Created")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertWarningMessageIsDisplayed();
    }

    @Test
    public void tr069_ev_018() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .newGroupButton()
                .fillGroupName("tr069_ev_016")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void tr069_ev_019() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("tr069_ev_016")
                .validateDevicesAmountIs(1);
    }

    @Test
    public void tr069_ev_020() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("All")
                .validateDevicesAmount();
    }

    @Test
    public void tr069_ev_021() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("Individual")
                .assertButtonIsEnabled(true, "btnSelectDevices_btn")
                .selectButton()
                .cancelButtonPopUp()
                .selectSendTo()
                .validateDevicesAmountIs(1);
    }

    @Test
    public void tr069_ev_022() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("Import from a file")
                .selectImportDevicesFile()
                .validateDevicesAmountIs(1);
    }

    @Test
    public void tr069_ev_023() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("tr069_ev_016")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "tr069_ev_016");
    }

    @Test
    public void tr069_ev_024() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .immediately()
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void tr069_ev_025() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "2:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 1);
    }

    @Test
    public void tr069_ev_026() {    //depends on 025
        evPage
                .topMenu(EVENTS)
                .enterIntoItem("tr069_ev_025")
                .immediately()
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "1:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem("tr069_ev_025")
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_ev_030() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .assertLogfileContainsEventSoap();
    }


}
