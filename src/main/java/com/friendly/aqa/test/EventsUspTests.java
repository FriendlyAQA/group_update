package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.entities.Event;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.BottomButtons.SAVE_AND_ACTIVATE;
import static com.friendly.aqa.entities.Condition.*;
import static com.friendly.aqa.entities.TopMenu.DEVICE_UPDATE;
import static com.friendly.aqa.entities.TopMenu.EVENTS;
import static com.friendly.aqa.pageobject.EventsPage.Left.NEW;

@Listeners(UniversalVideoListener.class)
public class EventsUspTests extends BaseTestCase {

   /*
   1. Delete all profiles for current device and set PeriodicInformInterval to low value (e.g. 10 sec) or launch usp_ev_000;
   2. At least 1 device online (with serial specified in config.properties) and 1 device offline MUST be present in the group (027);
   */

    @Test
    public void usp_ev_000() {
        evPage
                .topMenu(EVENTS)
                .createPreconditions();
    }

    @Test
    public void usp_ev_001() {
        evPage
                .topMenu(EVENTS)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void usp_ev_002() {
        evPage
                .topMenu(EVENTS)
                .checkRefreshPage();
    }

    @Test
    public void usp_ev_003() {
        evPage
                .topMenu(EVENTS)
                .assertButtonsAreEnabled(false, ACTIVATE, DEACTIVATE, DELETE)
                .assertButtonsAreEnabled(true, REFRESH)
                .newViewButton()
                .fillViewName()
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
    public void usp_ev_004() {
        evPage
                .topMenu(EVENTS)
                .newViewButton()
                .fillViewName()
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
    public void usp_ev_005() {
        evPage
                .topMenu(EVENTS)
                .newViewButton()
                .fillName("Default")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test   //depends on 004
    public void usp_ev_006() {
        evPage
                .topMenu(EVENTS)
                .selectView("usp_ev_004")
                .editButton()
                .bottomMenu(CANCEL)
                .assertSelectedViewIs("usp_ev_004");
    }

    @Test   //depends on 004
    public void usp_ev_007() {
        evPage
                .topMenu(EVENTS)
                .selectView("usp_ev_004")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(DEVICE_UPDATE)
                .topMenu(EVENTS)
                .resetView()
                .assertSelectedViewIs("usp_ev_004")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp();
    }

    @Test   //depends on 004
    public void usp_ev_008() {
        evPage
                .topMenu(EVENTS)
                .selectView("usp_ev_004")
                .selectView("Default")
                .assertTableColumnAmountIs(7, "tbl");
    }

    @Test   //depends on 004
    public void usp_ev_009() {
        evPage
                .topMenu(EVENTS)
                .selectView("usp_ev_004")
                .assertTableColumnAmountIs(2, "tbl");
    }

    @Test   //depends on 004
    public void usp_ev_010() {
        evPage
                .topMenu(EVENTS)
                .selectView("usp_ev_004")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "usp_ev_004");
    }

    @Test
    public void usp_ev_011() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE)
                .bottomMenu(CANCEL)
                .assertButtonsAreEnabled(false, ACTIVATE, DEACTIVATE, DELETE)
                .assertButtonsAreEnabled(true, REFRESH);
    }

    @Test
    public void usp_ev_012() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE);
    }

    @Test
    public void usp_ev_013() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE);
    }

    @Test
    public void usp_ev_014() {
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
    public void usp_ev_015() {
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

    @Test
    public void usp_ev_016() {
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
    public void usp_ev_017() {
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
    public void usp_ev_018() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .newGroupButton()
                .fillGroupName("usp_ev_016")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void usp_ev_019() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("usp_ev_016")
                .validateDevicesAmountIs(1);
    }

    @Test
    public void usp_ev_020() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("All")
                .validateDevicesAmount();
    }

    @Test
    public void usp_ev_021() {    //depends on 016
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
    public void usp_ev_022() {
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
    public void usp_ev_023() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("usp_ev_016")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "usp_ev_016");
    }

    @Test
    public void usp_ev_024() {
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
    public void usp_ev_025() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Boot!", null, "3", "2:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 1);
    }

    @Test
    public void usp_ev_026() {    //depends on 025
        evPage
                .topMenu(EVENTS)
                .enterIntoItem("usp_ev_025")
                .immediately()
                .selectMainTab("Events")
                .setEvent(new Event("Boot!", null, "1", "1:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem("usp_ev_025")
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void usp_ev_027() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo("All")
                .immediately()
                .selectMainTab("Events")
                .setEvent(new Event("Boot!", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_028() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Boot!", null, "5", "6:hours"))
                .setEvent(new Event("Boot!", null, "0", null))
                .setEvent(new Event("Boot!", null, "2", "5:minutes"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_029() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("DUStateChange!", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_030() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("OnBoardRequest", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_031() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Periodic!", null, "1", "3:minutes"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_032() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Push!", null, "1", "2:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .triggerConnectionRequest()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_033() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Boot!", true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_034() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("DUStateChange!", null, "5", "6:hours"))
                .setEvent(new Event("OnBoardRequest", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()     //Cannot trigger several events during testcase run
                .stopEvent();
    }

    @Test
    public void usp_ev_035() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvents(99, new Event(null, true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()     //Cannot trigger several events during testcase run
                .stopEvent();
    }

    @Test
    public void usp_ev_036() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Boot!", null, "1", "3:hours"), true)
                .addTask("Action")
                .selectAction("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_037() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Boot!", null, "1", "4:hours"), true)
                .addTask("Action")
                .selectAction("Factory Reset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

//    @Test   //skipped: instance 0 is absent from list for "Onboard request"
//    public void usp_ev_038() {
//        evPage
//                .createImmediatelyEventOn("Events")
//                .setEvent(new Event("Boot!", null, "1", "5:hours"), true)
//                .addTask("Action")
//                .selectAction("Onboard request", "0")
//                .saveButton()
//                .saveButton()
//                .bottomMenu(SAVE_AND_ACTIVATE)
//                .okButtonPopUp()
//                .enterIntoItem()
//                .expandEvents()
//                .validateEvents()
//                .validateAddedEventAction()
////                .assertLogfileContainsEventSoap()
//                .stopEvent();
//    }

    @Test
    public void usp_ev_039() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Boot!", null, "1", "6:hours"), true)
                .addTask("Action")
                .selectAction("Onboard request", "1")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //instance number changed from 3 to 2 (3 is absent from list)
    public void usp_ev_040() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Boot!", null, "1", "7:hours"), true)
                .addTask("Action")
                .selectAction("Onboard request", "2")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_041() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Boot!", null, "1", "7:hours"), true)
                .addTask("Action")
                .selectAction("Device reprovision")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_042() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("DUStateChange!", null, "1", "3:hours"), true)
                .addTask("Action")
                .selectAction("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_043() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("DUStateChange!", null, "1", "4:hours"), true)
                .addTask("Action")
                .selectAction("Factory Reset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

//    @Test   //skipped: instance 0 is absent from list for "Onboard request"
//    public void usp_ev_044() {
//        evPage
//                .createImmediatelyEventOn("Events")
//                .setEvent(new Event("DUStateChange!", null, "1", "5:hours"), true)
//                .addTask("Action")
//                .selectAction("Onboard request", "0")
//                .saveButton()
//                .saveButton()
//                .bottomMenu(SAVE_AND_ACTIVATE)
//                .okButtonPopUp()
//                .enterIntoItem()
//                .expandEvents()
//                .validateEvents()
//                .validateAddedEventAction()
////                .assertLogfileContainsEventSoap()
//                .stopEvent();
//    }

    @Test
    public void usp_ev_045() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("DUStateChange!", null, "1", "6:hours"), true)
                .addTask("Action")
                .selectAction("Onboard request", "1")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //instance number changed from 3 to 2 (3 is absent from list)
    public void usp_ev_046() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("DUStateChange!", null, "1", "7:hours"), true)
                .addTask("Action")
                .selectAction("Onboard request", "2")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_047() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("DUStateChange!", null, "1", "8:hours"), true)
                .addTask("Action")
                .selectAction("Device reprovision")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_048() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("OnBoardRequest", null, "1", "9:hours"), true)
                .addTask("Action")
                .selectAction("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_049() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("OnBoardRequest", null, "1", "10:hours"), true)
                .addTask("Action")
                .selectAction("Factory Reset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

//    @Test   //skipped: instance 0 is absent from list for "Onboard request"
//    public void usp_ev_050() {
//        evPage
//                .createImmediatelyEventOn("Events")
//                .setEvent(new Event("OnBoardRequest", null, "1", "11:hours"), true)
//                .addTask("Action")
//                .selectAction("Onboard request", "0")
//                .saveButton()
//                .saveButton()
//                .bottomMenu(SAVE_AND_ACTIVATE)
//                .okButtonPopUp()
//                .enterIntoItem()
//                .expandEvents()
//                .validateEvents()
//                .validateAddedEventAction()
////                .assertLogfileContainsEventSoap()
//                .stopEvent();
//    }

    @Test
    public void usp_ev_051() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("OnBoardRequest", null, "1", "12:hours"), true)
                .addTask("Action")
                .selectAction("Onboard request", "1")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //instance number changed from 3 to 2 (3 is absent from list)
    public void usp_ev_052() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("OnBoardRequest", null, "1", "13:hours"), true)
                .addTask("Action")
                .selectAction("Onboard request", "2")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_053() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("OnBoardRequest", null, "1", "14:hours"), true)
                .addTask("Action")
                .selectAction("Device reprovision")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_054() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Periodic!", null, "1", "15:hours"), true)
                .addTask("Action")
                .selectAction("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_055() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Periodic!", null, "1", "16:hours"), true)
                .addTask("Action")
                .selectAction("Factory Reset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

//    @Test   //skipped: instance 0 is absent from list for "Onboard request"
//    public void usp_ev_056() {
//        evPage
//                .createImmediatelyEventOn("Events")
//                .setEvent(new Event("Periodic!", null, "1", "17:hours"), true)
//                .addTask("Action")
//                .selectAction("Onboard request", "0")
//                .saveButton()
//                .saveButton()
//                .bottomMenu(SAVE_AND_ACTIVATE)
//                .okButtonPopUp()
//                .enterIntoItem()
//                .expandEvents()
//                .validateEvents()
//                .validateAddedEventAction()
////                .assertLogfileContainsEventSoap()
//                .stopEvent();
//    }

    @Test
    public void usp_ev_057() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Periodic!", null, "1", "18:hours"), true)
                .addTask("Action")
                .selectAction("Onboard request", "1")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //instance number changed from 3 to 2 (3 is absent from list)
    public void usp_ev_058() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Periodic!", null, "1", "19:hours"), true)
                .addTask("Action")
                .selectAction("Onboard request", "2")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_059() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Periodic!", null, "1", "20:hours"), true)
                .addTask("Action")
                .selectAction("Device reprovision")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_060() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", CONTAINS, "57", "5784")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_061() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", EQUAL, "5785", "5785")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_062() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", GREATER, "5785", "5786")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_063() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", GREATER_EQUAL, "5787", "5787")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_064() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", LESS, "5789", "5788")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_065() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", LESS_EQUAL, "5789", "5789")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_066() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", NOT_EQUAL, "5789", "5790")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_067() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", STARTS_WITH, "5", "5781")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_068() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", VALUE_CHANGE, "", "5782")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_069() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", VALUE_CHANGE, "", "5783", true)
                .addTask("Action")
                .selectAction("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTask("Reboot")
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void usp_ev_070() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Boot!", true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .stopEvent();
    }

    @Test
    public void usp_ev_071() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("Boot!", true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .deleteEvent();
    }

    @Test
    public void usp_ev_072() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("Boot!", null, "5", "6:hours"))
                .setEvent(new Event("Boot!", null, "0", null))
                .setEvent(new Event("Boot!", null, "2", "5:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void usp_ev_073() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("DUStateChange!", null, "1", "3:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void usp_ev_074() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("OnBoardRequest", null, "1", "3:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void usp_ev_075() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("Periodic!", null, "1", "3:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void usp_ev_076() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("Push!", null, "1", "2:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .triggerConnectionRequest();
    }

    @Test
    public void usp_ev_077() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("Boot!", true, null, null))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void usp_ev_078() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("DUStateChange!", null, "5", "6:hours"))
                .setEvent(new Event("OnBoardRequest", null, "1", "3:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void usp_ev_079() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvents(99, new Event(null, true, null, null))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void usp_ev_080() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", CONTAINS, "57", "5784")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_ev_081() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", EQUAL, "5785", "5785")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_ev_082() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", GREATER, "5785", "5786")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_ev_083() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", GREATER_EQUAL, "5787", "5787")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_ev_084() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", LESS, "5789", "5788")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_ev_085() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", LESS_EQUAL, "5789", "5789")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_ev_086() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", NOT_EQUAL, "5789", "5790")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_ev_087() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", STARTS_WITH, "5", "5781")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_ev_088() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("MTP USP")
                .setParametersMonitor("Port", VALUE_CHANGE, "", "5782")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }
}
