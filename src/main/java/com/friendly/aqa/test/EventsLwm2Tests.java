package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.entities.Event;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.BottomButtons.SAVE_AND_ACTIVATE;
import static com.friendly.aqa.entities.Condition.CONTAINS;
import static com.friendly.aqa.entities.ParameterType.VALUE;
import static com.friendly.aqa.entities.TopMenu.DEVICE_UPDATE;
import static com.friendly.aqa.entities.TopMenu.EVENTS;
import static com.friendly.aqa.pageobject.EventsPage.Left.NEW;

@Listeners(UniversalVideoListener.class)
public class EventsLwm2Tests extends BaseTestCase {

   /*
   1. Delete all profiles for current device and set PeriodicInformInterval to low value (e.g. 10 sec) or launch lwm2m_ev_000;
   2. At least 1 device online (with serial specified in config.properties) and 1 device offline MUST be present in the group (027);
   3. Emulator MUST be set to auto change value by any unused parameter (e.g. SSID name) to trigger '4 VALUE CHANGE'
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
                .assertInputHasText("txtName", getTestName())
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

    @Test   //bug: changes don't apply after SAVE button pressing
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
                .validateAddedEventTasks("4 VALUE CHANGE")
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
                .validateAddedEventAction("4 VALUE CHANGE", "Action", "Reboot")
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
                .validateAddedEventAction("4 VALUE CHANGE", "Action", "Factory Reset")
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
                .validateAddedEventAction("4 VALUE CHANGE", "Action", "Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values - instance 0")
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
                .validateAddedEventAction("4 VALUE CHANGE", "Action", "Root.IPSO_Power Measurement.i.Reset Cumulative energy - instance 0")
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
                .validateAddedEventAction("4 VALUE CHANGE", "Action", "Reset errors")
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
                .validateAddedEventAction("4 VALUE CHANGE", "Action", "Disable")
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
                .validateAddedEventAction("4 VALUE CHANGE", "Action", "Registration update trigger")
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
                .validateAddedEventAction("4 VALUE CHANGE", "Action", "Root.Connectivity Statistics.i.StartOrReset - instance 0")
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
                .validateAddedEventAction("4 VALUE CHANGE", "Action", "Device reprovision")
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
                .validateAddedEventTasks("NOTIFY REQUEST")
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
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Reboot")
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
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Factory Reset")
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
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values - instance 0")
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
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Cumulative energy - instance 0")
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
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Reset errors")
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
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Disable")
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
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Registration update trigger")
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
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Root.Connectivity Statistics.i.StartOrReset - instance 0")
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
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Device reprovision")
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
                .validateAddedEventTasks("REGISTRATION REQUEST")
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
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Reboot")
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
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Factory Reset")
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
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values - instance 0")
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
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Cumulative energy - instance 0")
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
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Reset errors")
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
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Disable")
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
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Registration update trigger")
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
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Root.Connectivity Statistics.i.StartOrReset - instance 0")
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
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Device reprovision")
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
                .validateAddedEventTasks("UNREGISTRATION REQUEST")
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
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Reboot")
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
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Factory Reset")
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
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values - instance 0")
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
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Cumulative energy - instance 0")
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
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Reset errors")
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
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Disable")
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
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Registration update trigger")
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
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Root.Connectivity Statistics.i.StartOrReset - instance 0")
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
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Device reprovision")
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
}
