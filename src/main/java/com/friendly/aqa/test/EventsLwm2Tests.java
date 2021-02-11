package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.entities.Event;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.BottomButtons.SAVE_AND_ACTIVATE;
import static com.friendly.aqa.entities.Condition.*;
import static com.friendly.aqa.entities.ParameterType.VALUE;
import static com.friendly.aqa.entities.TopMenu.DEVICE_UPDATE;
import static com.friendly.aqa.entities.TopMenu.EVENTS;
import static com.friendly.aqa.pageobject.EventsPage.Left.NEW;

@Listeners(UniversalVideoListener.class)
public class EventsLwm2Tests extends BaseTestCase {

   /*
   1. Delete all profiles for current device or launch lwm2m_ev_000;
   2. At least 1 device online (with serial specified in config.properties) and 1 device offline MUST be present in the group (027);
   3. Emulator MUST be set to auto change value by any unused parameter (e.g. temp. sensor) to trigger '4 VALUE CHANGE'
   4. Set Lifetime(sec) in Emulator settings to low value (e.g. 60 sec) for faster generation "UPDATE REQUEST"
   */

    @Test
    public void lwm2m_ev_000() {
        evPage
                .topMenu(EVENTS)
                .createPreconditions();
    }

    @Test
    public void lwm2m_ev_001() {
        evPage
                .topMenu(EVENTS)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void lwm2m_ev_002() {
        evPage
                .topMenu(EVENTS)
                .checkRefreshPage();
    }

    @Test
    public void lwm2m_ev_003() {
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
                .validateName()
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed()
                .assertButtonsAreEnabled(false, ACTIVATE, DEACTIVATE, DELETE)
                .assertButtonsAreEnabled(true, REFRESH)
                .assertAbsenceOfOptions("ddlView", getTestName());
    }

    @Test
    public void lwm2m_ev_004() {
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
    public void lwm2m_ev_005() {
        evPage
                .topMenu(EVENTS)
                .newViewButton()
                .fillName("Default")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test   //depends on 004
    public void lwm2m_ev_006() {
        evPage
                .topMenu(EVENTS)
                .selectView("lwm2m_ev_004")
                .editButton()
                .bottomMenu(CANCEL)
                .assertSelectedViewIs("lwm2m_ev_004");
    }

    @Test   //depends on 004
    public void lwm2m_ev_007() {
        evPage
                .topMenu(EVENTS)
                .selectView("lwm2m_ev_004")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(DEVICE_UPDATE)
                .topMenu(EVENTS)
                .resetView()
                .assertSelectedViewIs("lwm2m_ev_004")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp();
    }

    @Test   //depends on 004
    public void lwm2m_ev_008() {
        evPage
                .topMenu(EVENTS)
                .selectView("lwm2m_ev_004")
                .selectView("Default")
                .assertTableColumnAmountIs(7, "tbl");
    }

    @Test   //depends on 004
    public void lwm2m_ev_009() {
        evPage
                .topMenu(EVENTS)
                .selectView("lwm2m_ev_004")
                .assertTableColumnAmountIs(2, "tbl");
    }

    @Test   //depends on 004
    public void lwm2m_ev_010() {
        evPage
                .topMenu(EVENTS)
                .selectView("lwm2m_ev_004")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "lwm2m_ev_004");
    }

    @Test
    public void lwm2m_ev_011() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE)
                .bottomMenu(CANCEL)
                .assertButtonsAreEnabled(false, ACTIVATE, DEACTIVATE, DELETE)
                .assertButtonsAreEnabled(true, REFRESH);
    }

    @Test
    public void lwm2m_ev_012() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE);
    }

    @Test
    public void lwm2m_ev_013() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE);
    }

    @Test
    public void lwm2m_ev_014() {
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
    public void lwm2m_ev_015() {
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
    public void lwm2m_ev_016() {
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
    public void lwm2m_ev_017() {
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
    public void lwm2m_ev_018() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .newGroupButton()
                .fillGroupName("lwm2m_ev_016")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void lwm2m_ev_019() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("lwm2m_ev_016")
                .validateDevicesAmountIs(1);
    }

    @Test
    public void lwm2m_ev_020() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("All")
                .validateDevicesAmount();
    }

    @Test
    public void lwm2m_ev_021() {    //depends on 016
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
    public void lwm2m_ev_022() {
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
    public void lwm2m_ev_023() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("lwm2m_ev_016")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "lwm2m_ev_016");
    }

    @Test
    public void lwm2m_ev_024() {
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
    public void lwm2m_ev_025() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "3", "2:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 1);
    }

    @Test   //bug: changes for 4 VALUE CHANGE event don't apply after SAVE button pressing
    public void lwm2m_ev_026() {    //depends on 025
        evPage
                .topMenu(EVENTS)
                .enterIntoItem("lwm2m_ev_025")
                .immediately()
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "1:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem("lwm2m_ev_025")
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void lwm2m_ev_027() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo("All")
                .immediately()
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_028() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "5", "6:hours"))
                .setEvent(new Event("4 VALUE CHANGE", null, "0", null))
                .setEvent(new Event("4 VALUE CHANGE", null, "2", "5:minutes"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_029() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_030() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("REGISTRATION REQUEST", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_031() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", null, "1", "3:minutes"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_032() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("UPDATE REQUEST", null, "1", "2:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .triggerConnectionRequest()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    //33-34 skipped due to lwm2m has 5 events only

    @Test
    public void lwm2m_ev_035() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_036() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "5", "6:hours"))
                .setEvent(new Event("REGISTRATION REQUEST", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()     //Cannot trigger several events during testcase run
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_037() {
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
    public void lwm2m_ev_038() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "1:hours"), true)
                .addTask("Set parameter value")
                .setParameter("UTC Offset", VALUE, "+02:00")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_039() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "3:hours"), true)
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
    public void lwm2m_ev_040() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "4:hours"), true)
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

    @Test
    public void lwm2m_ev_041() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "5:hours"), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values")
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
    public void lwm2m_ev_042() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "6:hours"), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Cumulative energy")
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
    public void lwm2m_ev_043() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "7:hours"), true)
                .addTask("Action")
                .selectAction("Reset errors")
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
    public void lwm2m_ev_044() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "8:hours"), true)
                .addTask("Action")
                .selectAction("Disable")
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
    public void lwm2m_ev_045() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Action")
                .selectAction("Registration update trigger")
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
    public void lwm2m_ev_046() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Action")
                .selectAction("Root.Connectivity Statistics.i.StartOrReset")
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
    public void lwm2m_ev_047() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "5:hours"), true)
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
    public void lwm2m_ev_048() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "1", "1:hours"), true)
                .addTask("Set parameter value")
                .setParameter("UTC Offset", VALUE, "+02:00")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_049() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "1", "3:hours"), true)
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
    public void lwm2m_ev_050() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "1", "4:hours"), true)
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

    @Test
    public void lwm2m_ev_051() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "1", "5:hours"), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values")
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
    public void lwm2m_ev_052() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "1", "6:hours"), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Cumulative energy")
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
    public void lwm2m_ev_053() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "1", "7:hours"), true)
                .addTask("Action")
                .selectAction("Reset errors")
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
    public void lwm2m_ev_054() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "1", "8:hours"), true)
                .addTask("Action")
                .selectAction("Disable")
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
    public void lwm2m_ev_055() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "1", "9:hours"), true)
                .addTask("Action")
                .selectAction("Registration update trigger")
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
    public void lwm2m_ev_056() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "1", "10:hours"), true)
                .addTask("Action")
                .selectAction("Root.Connectivity Statistics.i.StartOrReset")
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
    public void lwm2m_ev_057() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "1", "5:hours"), true)
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
    public void lwm2m_ev_058() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("REGISTRATION REQUEST", null, "1", "1:hours"), true)
                .addTask("Set parameter value")
                .setParameter("UTC Offset", VALUE, "+02:00")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_059() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("REGISTRATION REQUEST", null, "1", "3:hours"), true)
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
    public void lwm2m_ev_060() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("REGISTRATION REQUEST", null, "1", "4:hours"), true)
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

    @Test
    public void lwm2m_ev_061() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("REGISTRATION REQUEST", null, "1", "5:hours"), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values")
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
    public void lwm2m_ev_062() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("REGISTRATION REQUEST", null, "1", "6:hours"), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Cumulative energy")
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
    public void lwm2m_ev_063() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("REGISTRATION REQUEST", null, "1", "7:hours"), true)
                .addTask("Action")
                .selectAction("Reset errors")
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
    public void lwm2m_ev_064() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("REGISTRATION REQUEST", null, "1", "8:hours"), true)
                .addTask("Action")
                .selectAction("Disable")
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
    public void lwm2m_ev_065() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("REGISTRATION REQUEST", null, "1", "9:hours"), true)
                .addTask("Action")
                .selectAction("Registration update trigger")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction()
//                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Registration update trigger")
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_066() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("REGISTRATION REQUEST", null, "1", "10:hours"), true)
                .addTask("Action")
                .selectAction("Root.Connectivity Statistics.i.StartOrReset")
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
    public void lwm2m_ev_067() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("REGISTRATION REQUEST", null, "1", "5:hours"), true)
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
    public void lwm2m_ev_068() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", null, "1", "1:hours"), true)
                .addTask("Set parameter value")
                .setParameter("UTC Offset", VALUE, "+02:00")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_069() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", null, "1", "3:hours"), true)
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
    public void lwm2m_ev_070() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", null, "1", "4:hours"), true)
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

    @Test
    public void lwm2m_ev_071() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", null, "1", "5:hours"), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values")
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
    public void lwm2m_ev_072() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", null, "1", "6:hours"), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Cumulative energy")
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
    public void lwm2m_ev_073() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", null, "1", "7:hours"), true)
                .addTask("Action")
                .selectAction("Reset errors")
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
    public void lwm2m_ev_074() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", null, "1", "8:hours"), true)
                .addTask("Action")
                .selectAction("Disable")
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
    public void lwm2m_ev_075() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", null, "1", "9:hours"), true)
                .addTask("Action")
                .selectAction("Registration update trigger")
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
    public void lwm2m_ev_076() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", null, "1", "10:hours"), true)
                .addTask("Action")
                .selectAction("Root.Connectivity Statistics.i.StartOrReset")
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
    public void lwm2m_ev_077() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", null, "1", "5:hours"), true)
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
    public void lwm2m_ev_078() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", CONTAINS, "2:0", "+02:00")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_079() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", EQUAL, "+03:00", "+03:00")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_080() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", GREATER, "+03:00", "+04:00")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_081() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", GREATER_EQUAL, "+04:00", "+05:00")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_082() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", LESS, "+07:00", "+06:00")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_083() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", LESS_EQUAL, "+07:00", "+07:00")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_084() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", NOT_EQUAL, "+07:00", "+08:00")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_085() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", STARTS_WITH, "+0", "+09:00")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_086() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", VALUE_CHANGE, "", "+10:00")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_087() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", CONTAINS, "6", "56")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_088() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", EQUAL, "57", "57")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated in log (data type in DB is 'INTEGER', must be 'unsignedInt'?)
    public void lwm2m_ev_089() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", GREATER, "57", "58")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated in log (data type in DB is 'INTEGER', must be 'unsignedInt'?)
    public void lwm2m_ev_090() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", GREATER_EQUAL, "59", "59")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated in log (data type in DB is 'INTEGER', must be 'unsignedInt'?)
    public void lwm2m_ev_091() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", LESS, "61", "60")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated in log (data type in DB is 'INTEGER', must be 'unsignedInt'?)
    public void lwm2m_ev_092() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", LESS_EQUAL, "62", "61")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_093() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", NOT_EQUAL, "65", "62")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_094() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", STARTS_WITH, "6", "63")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_095() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", VALUE_CHANGE, "", "64")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_096() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", CONTAINS, "6", "56")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()    //tab "Connectivity monitoring" doesn't have editable fields;
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_097() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", EQUAL, "57", "57")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()    //tab "Connectivity monitoring" doesn't have editable fields;
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_098() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", GREATER, "57", "58")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()    //tab "Connectivity monitoring" doesn't have editable fields;
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_099() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", GREATER_EQUAL, "59", "59")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()    //    tab "Connectivity monitoring" doesn't have editable fields;
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_100() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", LESS, "61", "60")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//               .triggerEventOnParameter()    //tab "Connectivity monitoring" doesn't have editable fields;
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_101() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", LESS_EQUAL, "62", "61")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()    //tab "Connectivity monitoring" doesn't have editable fields;
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_102() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", NOT_EQUAL, "65", "62")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()    //tab "Connectivity monitoring" doesn't have editable fields;
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_103() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", STARTS_WITH, "6", "63")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()    //tab "Connectivity monitoring" doesn't have editable fields;
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_104() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", VALUE_CHANGE, "", "64")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()    //tab "Connectivity monitoring" doesn't have editable fields;
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_105() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", VALUE_CHANGE, "", "+01:00", true)
                .addTask("Set parameter value")
                .setParameter("UTC Offset", VALUE, "+02:00")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTasks()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_106() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", VALUE_CHANGE, "", "+02:00", true)
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
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void lwm2m_ev_107() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .deleteEvent();
    }

    @Test
    public void lwm2m_ev_108() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "5", "6:hours"))
                .setEvent(new Event("4 VALUE CHANGE", null, "0", null))
                .setEvent(new Event("4 VALUE CHANGE", null, "2", "5:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void lwm2m_ev_109() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "1", "2:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void lwm2m_ev_110() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("REGISTRATION REQUEST", null, "1", "3:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void lwm2m_ev_111() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", null, "1", "4:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void lwm2m_ev_112() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("UPDATE REQUEST", null, "1", "5:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void lwm2m_ev_113() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", true, null, null))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void lwm2m_ev_114() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "1", "6:minutes"))
                .setEvent(new Event("REGISTRATION REQUEST", null, "1", "7:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void lwm2m_ev_115() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvents(99, new Event(null, null, "1", "10:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void lwm2m_ev_116() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", CONTAINS, "2:0")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_117() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", EQUAL, "+03:00")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_118() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", GREATER, "+03:00")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_119() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", GREATER_EQUAL, "+04:00")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_120() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", LESS, "+07:00")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_121() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", LESS_EQUAL, "+07:00")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_122() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", NOT_EQUAL, "+07:00")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_123() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", STARTS_WITH, "+9")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_124() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Device")
                .setParametersMonitor("UTC Offset", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_125() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", CONTAINS, "6")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_126() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", EQUAL, "57")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_127() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", GREATER, "57")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_128() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", GREATER_EQUAL, "59")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_129() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", LESS, "61")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_130() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", LESS_EQUAL, "62")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_131() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", NOT_EQUAL, "65")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_132() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", STARTS_WITH, "6")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_133() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Server")
                .setParametersMonitor("Lifetime", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_134() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", CONTAINS, "6")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_135() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", EQUAL, "57")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_136() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", GREATER, "57")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_137() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", GREATER_EQUAL, "59")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_138() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", LESS, "61")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_139() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", LESS_EQUAL, "62")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_140() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", NOT_EQUAL, "65")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_141() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", STARTS_WITH, "6")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void lwm2m_ev_142() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Connectivity monitoring")
                .setParametersMonitor("SMNC", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }
}
