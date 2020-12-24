package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.entities.Event;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.Condition.*;
import static com.friendly.aqa.entities.ParameterType.VALUE;
import static com.friendly.aqa.entities.TopMenu.*;
import static com.friendly.aqa.pageobject.EventsPage.Left.NEW;

@Listeners(UniversalVideoListener.class)
public class EventsTR069Tests extends BaseTestCase {

   /*
   1. Delete all profiles for current device and set PeriodicInformInterval to low value (e.g. 10 sec) or launch tr069_ev_000;
   2. At least 1 device online (with serial specified in config.properties) and 1 device offline MUST be present in the group (027);
   3. Emulator MUST be set to auto change value by any unused parameter (e.g. SSID name) to trigger '4 VALUE CHANGE'
   */

    @Test
    public void tr069_ev_000() {
        evPage
                .topMenu(EVENTS)
                .createPreconditions();
    }

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
//                .deleteAllCustomViews()   //as precondition for next step and tests
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
    public void tr069_ev_004() {
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

    @Test   //depends on 004
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
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "3", "2:hours"))
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
    public void tr069_ev_027() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo("All")
                .immediately()
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_028() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("0 BOOTSTRAP", null, "5", "6:hours"))
                .setEvent(new Event("0 BOOTSTRAP", null, "0", null))
                .setEvent(new Event("0 BOOTSTRAP", null, "2", "5:minutes"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 0 BOOTSTRAP during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_029() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_030() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_031() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "3:minutes"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_032() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "2:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_033() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "6", "8:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_034() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "4", "14:minutes"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_035() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_036() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("0 BOOTSTRAP", null, "5", "6:hours"))
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()     //Cannot trigger several events during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_037() {
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
    public void tr069_ev_038() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Set parameter value")
                .setParameter("PeriodicInformInterval, sec", VALUE, "11")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_039() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Download file")
                .selectDownloadFileType("Vendor Configuration File")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .fillUsername()
                .fillPassword()
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_040() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Action")
                .selectAction("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("1 BOOT", "Action", "Reboot")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_041() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Action")
                .selectAction("Factory Reset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("1 BOOT", "Action", "Factory Reset")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_042() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "5:hours"), true)
                .addTask("Action")
                .selectAction("Device reprovision")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("1 BOOT", "Action", "Device reprovision")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_043() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "6:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetRPCMethods")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_044() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "7:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("SetParameterValues")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_045() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "8:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetParameterValues")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_046() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "9:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("SetParameterAttributes")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_047() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "10:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetParameterAttributes")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_048() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "11:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("AddObject")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_049() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "12:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("DeleteObject")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_050() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "13:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Download")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_051() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "14:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_052() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "15:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("FactoryReset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_053() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "16:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Upload")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_054() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "17:minutes"), true)
                .addTask("Set parameter value")
                .setParameter("PeriodicInformInterval, sec", VALUE, "11")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_055() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "18:minutes"), true)
                .addTask("Download file")
                .selectDownloadFileType("Vendor Configuration File")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .fillUsername()
                .fillPassword()
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_056() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "19:minutes"), true)
                .addTask("Action")
                .selectAction("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("2 PERIODIC", "Action", "Reboot")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_057() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "20:minutes"), true)
                .addTask("Action")
                .selectAction("Factory Reset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("2 PERIODIC", "Action", "Factory Reset")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_058() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "21:minutes"), true)
                .addTask("Action")
                .selectAction("Device reprovision")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("2 PERIODIC", "Action", "Device reprovision")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_059() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "22:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetRPCMethods")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_060() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "23:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("SetParameterValues")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_061() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "24:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetParameterValues")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_062() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "25:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("SetParameterAttributes")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_063() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "26:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetParameterAttributes")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_064() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "27:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("AddObject")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_065() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "28:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("DeleteObject")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_066() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "29:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Download")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_067() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "30:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_068() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "31:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("FactoryReset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_069() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "32:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Upload")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_070() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "33:minutes"), true)
                .addTask("Set parameter value")
                .setParameter("PeriodicInformInterval, sec", VALUE, "11")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()     //
                .stopEvent();
    }

    @Test
    public void tr069_ev_071() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "34:minutes"), true)
                .addTask("Download file")
                .selectDownloadFileType("Vendor Configuration File")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .fillUsername()
                .fillPassword()
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_072() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "35:minutes"), true)
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
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_073() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "36:minutes"), true)
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
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_074() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "37:minutes"), true)
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
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_075() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "38:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetRPCMethods")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_076() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "39:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("SetParameterValues")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_077() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "40:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetParameterValues")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_078() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "41:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("SetParameterAttributes")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_079() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "42:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetParameterAttributes")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_080() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "43:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("AddObject")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_081() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "44:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("DeleteObject")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_082() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "45:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Download")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_083() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "46:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_084() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "47:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("FactoryReset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_085() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "48:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Upload")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_086() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "49:minutes"), true)
                .addTask("Set parameter value")
                .setParameter("PeriodicInformInterval, sec", VALUE, "11")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_087() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "50:minutes"), true)
                .addTask("Download file")
                .selectDownloadFileType("Vendor Configuration File")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .fillUsername()
                .fillPassword()
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_088() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "51:minutes"), true)
                .addTask("Action")
                .selectAction("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("6 CONNECTION REQUEST", "Action", "Reboot")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_089() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "52:minutes"), true)
                .addTask("Action")
                .selectAction("Factory Reset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("6 CONNECTION REQUEST", "Action", "Factory Reset")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_090() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "53:minutes"), true)
                .addTask("Action")
                .selectAction("Device reprovision")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("6 CONNECTION REQUEST", "Action", "Device reprovision")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_091() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "54:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetRPCMethods")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_092() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "55:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("SetParameterValues")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_093() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "56:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetParameterValues")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_094() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "57:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("SetParameterAttributes")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_095() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "58:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetParameterAttributes")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_096() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "59:minutes"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("AddObject")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_097() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "1:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("DeleteObject")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_098() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "2:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Download")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_099() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "3:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_100() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "4:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("FactoryReset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_101() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "5:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Upload")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_102() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "1:hours"), true)
                .addTask("Set parameter value")
                .setParameter("PeriodicInformInterval, sec", VALUE, "11")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_103() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "2:hours"), true)
                .addTask("Download file")
                .selectDownloadFileType("Vendor Configuration File")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .fillUsername()
                .fillPassword()
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_104() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "3:hours"), true)
                .addTask("Action")
                .selectAction("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("7 TRANSFER COMPLETE", "Action", "Reboot")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_105() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "4:hours"), true)
                .addTask("Action")
                .selectAction("Factory Reset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("7 TRANSFER COMPLETE", "Action", "Factory Reset")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_106() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "5:hours"), true)
                .addTask("Action")
                .selectAction("Device reprovision")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("7 TRANSFER COMPLETE", "Action", "Device reprovision")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_107() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "6:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetRPCMethods")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_108() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "7:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("SetParameterValues")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_109() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "8:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetParameterValues")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_110() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "9:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("SetParameterAttributes")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_111() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "10:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetParameterAttributes")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_112() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "11:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("AddObject")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_113() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "12:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("DeleteObject")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_114() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "13:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Download")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_115() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "14:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_116() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "15:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("FactoryReset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_117() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "16:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Upload")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_118() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "1:hours"), true)
                .addTask("Set parameter value")
                .setParameter("PeriodicInformInterval, sec", VALUE, "11")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_119() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "2:hours"), true)
                .addTask("Download file")
                .selectDownloadFileType("Vendor Configuration File")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .fillUsername()
                .fillPassword()
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_120() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "3:hours"), true)
                .addTask("Action")
                .selectAction("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("8 DIAGNOSTICS COMPLETE", "Action", "Reboot")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_121() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "4:hours"), true)
                .addTask("Action")
                .selectAction("Factory Reset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("8 DIAGNOSTICS COMPLETE", "Action", "Factory Reset")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_122() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "5:hours"), true)
                .addTask("Action")
                .selectAction("Device reprovision")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("8 DIAGNOSTICS COMPLETE", "Action", "Device reprovision")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_123() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "6:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetRPCMethods")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_124() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "7:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("SetParameterValues")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_125() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "8:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetParameterValues")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_126() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "9:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("SetParameterAttributes")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_127() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "10:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("GetParameterAttributes")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_128() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "11:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("AddObject")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_129() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "12:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("DeleteObject")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_130() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "13:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Download")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_131() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "14:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_132() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "15:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("FactoryReset")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_133() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "16:hours"), true)
                .addTask("Action")
                .selectAction("Custom RPC")
                .selectMethod("Upload")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_134() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_135() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_136() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_137() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_138() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_139() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_140() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_141() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_142() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_143() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_144() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_145() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_146() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_147() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_148() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_149() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_150() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_151() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_152() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_153() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_154() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_155() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_156() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_157() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_158() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_159() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_160() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_161() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_162() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_163() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_164() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_165() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_166() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_167() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_168() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_169() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_170() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_171() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_172() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_173() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_174() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_175() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_176() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_177() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_178() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_179() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_180() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_181() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_182() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_183() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_184() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_185() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_186() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_187() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_188() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_189() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_190() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_191() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_192() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_193() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_194() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_195() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_196() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_197() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_198() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_199() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_200() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_201() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_202() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_203() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_204() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_205() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_206() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_207() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_208() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_209() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_210() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_211() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_212() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_213() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_214() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_215() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_216() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_217() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_218() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_219() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_220() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_221() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_222() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_223() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_224() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_225() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_226() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_227() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_228() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_229() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_230() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_231() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_232() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_233() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_234() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_235() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_236() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_237() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_238() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_239() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_240() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_241() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_242() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_243() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_244() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_245() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_246() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_247() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_248() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_249() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_250() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_251() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_252() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_253() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_254() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_255() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_256() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_257() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_258() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_259() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_260() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_261() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_262() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_263() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_264() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_265() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_266() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_267() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_268() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_269() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_270() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_271() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_272() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_273() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_274() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_275() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_276() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_277() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_278() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_279() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_280() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_281() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_282() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_283() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_284() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_285() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_286() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_287() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_288() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_289() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_290() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_291() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_292() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_293() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_294() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_295() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_296() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_297() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_298() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_299() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_300() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_301() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_302() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_303() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_304() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_305() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_306() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_307() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_308() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_309() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_310() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_311() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_312() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_313() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_314() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_315() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_316() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_317() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_318() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_319() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_320() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_321() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_322() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_323() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_324() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_325() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_326() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Upload file")
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUploadRadioButton()
                .fillUploadUrl()
                .fillUploadUserName()
                .fillUploadPassword()
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_327() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "18:minutes"), true)
                .addTask("Upload file")
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUploadRadioButton()
                .fillUploadUrl()
                .fillUploadUserName()
                .fillUploadPassword()
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_328() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "34:minutes"), true)
                .addTask("Upload file")
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUploadRadioButton()
                .fillUploadUrl()
                .fillUploadUserName()
                .fillUploadPassword()
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_329() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "50:minutes"), true)
                .addTask("Upload file")
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUploadRadioButton()
                .fillUploadUrl()
                .fillUploadUserName()
                .fillUploadPassword()
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST")
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_330() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "2:hours"), true)
                .addTask("Upload file")
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUploadRadioButton()
                .fillUploadUrl()
                .fillUploadUserName()
                .fillUploadPassword()
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("7 TRANSFER COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_331() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "2:hours"), true)
                .addTask("Upload file")
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUploadRadioButton()
                .fillUploadUrl()
                .fillUploadUserName()
                .fillUploadPassword()
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("8 DIAGNOSTICS COMPLETE")
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr069_ev_332() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "2:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Management")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_333() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "3:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Information")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_334() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "4:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Time")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_335() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "5:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DSL settings")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_336() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "6:minutes"), true)
                .addTask("Get parameter")
                .selectTab("WAN")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_337() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "7:minutes"), true)
                .addTask("Get parameter")
                .selectTab("LAN")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_338() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "8:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Wireless")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_339() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:minutes"), true)
                .addTask("Get parameter")
                .selectTab("VoIP settings")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_340() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Management")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_341() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Information")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_342() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Time")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_343() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "13:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DSL settings")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_344() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "14:minutes"), true)
                .addTask("Get parameter")
                .selectTab("WAN")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_345() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "15:minutes"), true)
                .addTask("Get parameter")
                .selectTab("LAN")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_346() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "16:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Wireless")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_347() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "17:minutes"), true)
                .addTask("Get parameter")
                .selectTab("VoIP settings")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_348() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "18:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Management")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_349() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "19:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Information")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_350() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "20:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Time")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_351() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "21:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DSL settings")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_352() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "22:minutes"), true)
                .addTask("Get parameter")
                .selectTab("WAN")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_353() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "23:minutes"), true)
                .addTask("Get parameter")
                .selectTab("LAN")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_354() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "24:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Wireless")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_355() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "25:minutes"), true)
                .addTask("Get parameter")
                .selectTab("VoIP settings")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_356() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "26:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Management")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_357() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "27:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Information")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_358() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "28:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Time")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_359() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "29:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DSL settings")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_360() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "30:minutes"), true)
                .addTask("Get parameter")
                .selectTab("WAN")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_361() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "31:minutes"), true)
                .addTask("Get parameter")
                .selectTab("LAN")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_362() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "32:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Wireless")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_363() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "33:minutes"), true)
                .addTask("Get parameter")
                .selectTab("VoIP settings")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_364() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "34:minutes"), true)
                .addTask("Backup")
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Backup")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_365() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "35:minutes"), true)
                .addTask("Restore")
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Restore")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_366() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "36:minutes"), true)
                .addTask("Diagnostics")
                .selectDiagnostic("IPPing diagnostics") //Trace diagnostics
                .inputHost("8.8.8.8")
                .inputNumOfRepetitions("3")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "IPPing diagnostics", "8.8.8.8")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: MP252 does not contain such diagnostic
    public void tr069_ev_367() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "37:minutes"), true)
                .addTask("Diagnostics")
                .selectDiagnostic("Download diagnostics")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Download diagnostics")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: MP252 does not contain such diagnostic
    public void tr069_ev_368() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "38:minutes"), true)
                .addTask("Diagnostics")
                .selectDiagnostic("Upload diagnostics")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Upload diagnostics")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: MP252 does not contain such diagnostic
    public void tr069_ev_369() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "39:minutes"), true)
                .addTask("Diagnostics")
                .selectDiagnostic("Wi-Fi neighboring diagnostics")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Wi-Fi neighboring diagnostics")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: MP252 does not contain such diagnostic
    public void tr069_ev_370() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "40:minutes"), true)
                .addTask("Diagnostics")
                .selectDiagnostic("DSL diagnostics")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "DSL diagnostics")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: MP252 does not contain such diagnostic
    public void tr069_ev_371() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "41:minutes"), true)
                .addTask("Diagnostics")
                .selectDiagnostic("NSLoopback diagnostics")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "NSLoopback diagnostics")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_372() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", CONTAINS, "34", "345")
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
    public void tr069_ev_373() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", EQUAL, "123", "123")
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
    public void tr069_ev_374() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformInterval, sec", GREATER, "10", "11")
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
    public void tr069_ev_375() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformInterval, sec", GREATER_EQUAL, "10", "10")
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
    public void tr069_ev_376() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformInterval, sec", LESS, "14", "13")
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
    public void tr069_ev_377() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformInterval, sec", LESS_EQUAL, "10", "10")
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
    public void tr069_ev_378() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", NOT_EQUAL, "123", "121")
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
    public void tr069_ev_379() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", STARTS_WITH, "1", "121")
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
    public void tr069_ev_380() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", VALUE_CHANGE, "", "987")
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
    public void tr069_ev_381() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", CONTAINS, "34", "345")
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
    public void tr069_ev_382() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", EQUAL, "123", "123")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with GREATER condition (no-integer field)
    public void tr069_ev_383() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", GREATER, "10", "11")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with GREATER_EQUAL condition (no-integer field)
    public void tr069_ev_384() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", GREATER_EQUAL, "10", "10")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with LESS condition (no-integer field)
    public void tr069_ev_385() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", LESS, "14", "13")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with LESS_EQUAL condition (no-integer field)
    public void tr069_ev_386() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", LESS_EQUAL, "10", "10")
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
    public void tr069_ev_387() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", NOT_EQUAL, "123", "121")
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
    public void tr069_ev_388() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", STARTS_WITH, "1", "121")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: generated 2 mixed SOAPs, but should be one
    public void tr069_ev_389() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", VALUE_CHANGE, "", "987")
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
    public void tr069_ev_390() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", CONTAINS, "34", "345")
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
    public void tr069_ev_391() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", EQUAL, "123", "123")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with GREATER condition (no-integer field)
    public void tr069_ev_392() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", GREATER, "10", "11")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with GREATER_EQUAL condition (no-integer field)
    public void tr069_ev_393() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", GREATER_EQUAL, "10", "10")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with LESS condition (no-integer field)
    public void tr069_ev_394() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", LESS, "14", "13")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with LESS_EQUAL condition (no-integer field)
    public void tr069_ev_395() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", LESS_EQUAL, "10", "10")
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
    public void tr069_ev_396() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", NOT_EQUAL, "123", "121")
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
    public void tr069_ev_397() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", STARTS_WITH, "1", "121")
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
    public void tr069_ev_398() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", VALUE_CHANGE, "", "987")
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
    public void tr069_ev_399() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", CONTAINS, "32", "PVC: 1/32")
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
    public void tr069_ev_400() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", EQUAL, "PVC: 1/40", "PVC: 1/40")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with GREATER condition (no-integer field)
    public void tr069_ev_401() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", GREATER, "PVC: 1/40", "PVC: 1/41")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with GREATER_EQUAL condition (no-integer field)
    public void tr069_ev_402() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", GREATER_EQUAL, "PVC: 1/40", "PVC: 1/40")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with LESS condition (no-integer field)
    public void tr069_ev_403() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", LESS, "PVC: 1/40", "PVC: 1/32")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with LESS_EQUAL condition (no-integer field)
    public void tr069_ev_404() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", LESS_EQUAL, "PVC: 1/32", "PVC: 1/32")
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
    public void tr069_ev_405() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", NOT_EQUAL, "PVC: 1/40", "PVC: 1/32")
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
    public void tr069_ev_406() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", STARTS_WITH, "PVC", "PVC: 1/33")
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
    public void tr069_ev_407() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", VALUE_CHANGE, "", "PVC: 1/35")
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
    public void tr069_ev_408() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", CONTAINS, "18", "180")
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
    public void tr069_ev_409() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", EQUAL, "240", "240")
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
    public void tr069_ev_410() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", GREATER, "60", "70")
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
    public void tr069_ev_411() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", GREATER_EQUAL, "80", "80")
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
    public void tr069_ev_412() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", LESS, "130", "125")
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
    public void tr069_ev_413() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", LESS_EQUAL, "140", "139")
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
    public void tr069_ev_414() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", NOT_EQUAL, "500", "300")
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
    public void tr069_ev_415() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", STARTS_WITH, "86", "86400")
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
    public void tr069_ev_416() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", VALUE_CHANGE, "", "100")
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
    public void tr069_ev_417() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", CONTAINS, "18", "180")
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
    public void tr069_ev_418() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", EQUAL, "240", "240")
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
    public void tr069_ev_419() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", GREATER, "60", "70")
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
    public void tr069_ev_420() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", GREATER_EQUAL, "80", "80")
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
    public void tr069_ev_421() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", LESS, "130", "125")
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
    public void tr069_ev_422() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", LESS_EQUAL, "140", "139")
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
    public void tr069_ev_423() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", NOT_EQUAL, "500", "300")
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
    public void tr069_ev_424() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", STARTS_WITH, "86", "86400")
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
    public void tr069_ev_425() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", VALUE_CHANGE, "", "100")
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
    public void tr069_ev_426() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", CONTAINS, "1", "10")
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
    public void tr069_ev_427() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", EQUAL, "13", "13")
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
    public void tr069_ev_428() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", GREATER, "11", "12")
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
    public void tr069_ev_429() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", GREATER_EQUAL, "7", "9")
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
    public void tr069_ev_430() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", LESS, "8", "7")
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
    public void tr069_ev_431() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", LESS_EQUAL, "6", "6")
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
    public void tr069_ev_432() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", NOT_EQUAL, "1", "5")
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
    public void tr069_ev_433() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", STARTS_WITH, "1", "11")
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
    public void tr069_ev_434() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", VALUE_CHANGE, "", "1")
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
    public void tr069_ev_435() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", CONTAINS, "1", "Profile1")
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
    public void tr069_ev_436() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", EQUAL, "Profile", "Profile")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with GREATER condition (no-integer field)
    public void tr069_ev_437() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", GREATER, "Profile1", "Profile2")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with GREATER_EQUAL condition (no-integer field)
    public void tr069_ev_438() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", GREATER_EQUAL, "Profile3", "Profile3")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with LESS condition (no-integer field)
    public void tr069_ev_439() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", LESS, "Profile5", "Profile4")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //bug: SOAP is not generated with LESS_EQUAL condition (no-integer field)
    public void tr069_ev_440() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", LESS_EQUAL, "Profile6", "Profile5")
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
    public void tr069_ev_441() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", NOT_EQUAL, "Profile5", "Profile6")
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
    public void tr069_ev_442() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", STARTS_WITH, "Prof", "Profile7")
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
    public void tr069_ev_443() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", VALUE_CHANGE, "", "Profile8")
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
    public void tr069_ev_444() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", VALUE_CHANGE, "", "111", true)
                .addTask("Set parameter value")
                .setParameter("PeriodicInformTime", VALUE, "222")
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
    public void tr069_ev_445() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", VALUE_CHANGE, "", "112", true)
                .addTask("Download file")
                .selectDownloadFileType("Vendor Configuration File")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .fillUsername()
                .fillPassword()
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
    public void tr069_ev_446() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", VALUE_CHANGE, "", "113", true)
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
    public void tr069_ev_447() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", VALUE_CHANGE, "", "114", true)
                .addTask("Policy")
                .setTaskPolicy(1)
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
    public void tr069_ev_448() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", VALUE_CHANGE, "", "115", true)
                .addTask("Upload file")
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUploadRadioButton()
                .fillUploadUrl()
                .fillUploadUserName()
                .fillUploadPassword()
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
    public void tr069_ev_449() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", VALUE_CHANGE, "", "116", true)
                .addTask("Get parameter")
                .selectTab("Management")
                .getParameter(1, 1)
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
    public void tr069_ev_450() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", VALUE_CHANGE, "", "117", true)
                .addTask("Backup")
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTask("Backup")
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_451() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", VALUE_CHANGE, "", "118", true)
                .addTask("Restore")
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTask("Restore")
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_452() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", VALUE_CHANGE, "", "118", true)
                .addTask("Diagnostics")
                .selectDiagnostic("IPPing diagnostics") //Trace diagnostics
                .inputHost("8.8.8.8")
                .inputNumOfRepetitions("3")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTask("IPPing diagnostics", "8.8.8.8")
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_453() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .stopEvent();
    }

    @Test
    public void tr069_ev_454() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .deleteEvent();
    }

    @Test
    public void tr069_ev_455() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("0 BOOTSTRAP", null, "5", "6:hours"))
                .setEvent(new Event("0 BOOTSTRAP", null, "0", null))
                .setEvent(new Event("0 BOOTSTRAP", null, "2", "5:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_ev_456() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_ev_457() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "3:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_ev_458() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "4:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_ev_459() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "5:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_ev_460() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "6:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_ev_461() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "7:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_ev_462() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("2 PERIODIC", true, null, null))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_ev_463() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "8:minutes"))
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "9:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_ev_464() {
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
    public void tr069_ev_465() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", CONTAINS, "34")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_466() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_467() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformInterval, sec", GREATER, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_468() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformInterval, sec", GREATER_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_469() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformInterval, sec", LESS, "14")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_470() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformInterval, sec", LESS_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_471() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", NOT_EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_472() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", STARTS_WITH, "1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_473() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_474() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", CONTAINS, "34")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_475() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_476() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", GREATER, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_477() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", GREATER_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_478() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", LESS, "14")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_479() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", LESS_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_480() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", NOT_EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_481() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", STARTS_WITH, "1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_482() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_483() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", CONTAINS, "34")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_484() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_485() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", GREATER, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_486() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", GREATER_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_487() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", LESS, "14")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_488() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", LESS_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_489() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", NOT_EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_490() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", STARTS_WITH, "1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_491() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_492() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", CONTAINS, "34")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_493() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_494() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", GREATER, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_495() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", GREATER_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_496() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", LESS, "14")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_497() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", LESS_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_498() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", NOT_EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_499() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", STARTS_WITH, "1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_500() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("DestinationAddress", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_501() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", CONTAINS, "34")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_502() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_503() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", GREATER, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_504() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", GREATER_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_505() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", LESS, "14")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_506() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", LESS_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_507() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", NOT_EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_508() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", STARTS_WITH, "1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_509() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("IdleDisconnectTime, sec", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_510() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", CONTAINS, "34")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_511() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_512() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", GREATER, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_513() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", GREATER_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_514() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", LESS, "14")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_515() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", LESS_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_516() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", NOT_EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_517() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", STARTS_WITH, "1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_518() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("DHCPLeaseTime, sec", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_519() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", CONTAINS, "34")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_520() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_521() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", GREATER, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_522() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", GREATER_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_523() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", LESS, "14")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_524() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", LESS_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_525() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", NOT_EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_526() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", STARTS_WITH, "1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_527() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("WEPKeyIndex", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_528() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", CONTAINS, "34")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_529() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_530() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", GREATER, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_531() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", GREATER_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_532() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", LESS, "14")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_533() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", LESS_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_534() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", NOT_EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_535() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", STARTS_WITH, "1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_ev_536() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("Name", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }
}