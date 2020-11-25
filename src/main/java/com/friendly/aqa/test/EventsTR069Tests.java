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
   1. Delete all profiles for current device and set PeriodicInformInterval to low value (e.g. 10 sec);
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr069_ev_031() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .assertLogfileContainsEventSoap()     //
                .stopEvent();
    }

    @Test
    public void tr069_ev_087() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("InternetGatewayDevice.ManagementServer.PeriodicInformTime", CONTAINS, "34", "345")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("InternetGatewayDevice.ManagementServer.PeriodicInformTime", EQUAL, "123", "123")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", GREATER, "10", "11")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", GREATER_EQUAL, "10", "10")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", LESS, "14", "13")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", LESS_EQUAL, "10", "10")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("InternetGatewayDevice.ManagementServer.PeriodicInformTime", NOT_EQUAL, "123", "121")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("InternetGatewayDevice.ManagementServer.PeriodicInformTime", STARTS_WITH, "1", "121")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("InternetGatewayDevice.ManagementServer.PeriodicInformTime", VALUE_CHANGE, "", "987")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("InternetGatewayDevice.DeviceInfo.ProvisioningCode", CONTAINS, "34", "345")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("InternetGatewayDevice.DeviceInfo.ProvisioningCode", EQUAL, "123", "123")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("InternetGatewayDevice.DeviceInfo.ProvisioningCode", GREATER, "10", "11")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("InternetGatewayDevice.DeviceInfo.ProvisioningCode", GREATER_EQUAL, "10", "10")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("InternetGatewayDevice.DeviceInfo.ProvisioningCode", LESS, "14", "13")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("InternetGatewayDevice.DeviceInfo.ProvisioningCode", LESS_EQUAL, "10", "10")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("InternetGatewayDevice.DeviceInfo.ProvisioningCode", NOT_EQUAL, "123", "121")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("InternetGatewayDevice.DeviceInfo.ProvisioningCode", STARTS_WITH, "1", "121")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("InternetGatewayDevice.DeviceInfo.ProvisioningCode", VALUE_CHANGE, "", "987")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("InternetGatewayDevice.Time.NTPServer2", CONTAINS, "34", "345")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("InternetGatewayDevice.Time.NTPServer2", EQUAL, "123", "123")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("InternetGatewayDevice.Time.NTPServer2", GREATER, "10", "11")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("InternetGatewayDevice.Time.NTPServer2", GREATER_EQUAL, "10", "10")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("InternetGatewayDevice.Time.NTPServer2", LESS, "14", "13")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("InternetGatewayDevice.Time.NTPServer2", LESS_EQUAL, "10", "10")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("InternetGatewayDevice.Time.NTPServer2", NOT_EQUAL, "123", "121")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("InternetGatewayDevice.Time.NTPServer2", STARTS_WITH, "1", "121")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("InternetGatewayDevice.Time.NTPServer2", VALUE_CHANGE, "", "987")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.DestinationAddress", CONTAINS, "32", "PVC: 1/32")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.DestinationAddress", EQUAL, "PVC: 1/40", "PVC: 1/40")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.DestinationAddress", GREATER, "PVC: 1/40", "PVC: 1/41")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.DestinationAddress", GREATER_EQUAL, "PVC: 1/40", "PVC: 1/40")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.DestinationAddress", LESS, "PVC: 1/40", "PVC: 1/32")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.DestinationAddress", LESS_EQUAL, "PVC: 1/32", "PVC: 1/32")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.DestinationAddress", NOT_EQUAL, "PVC: 1/40", "PVC: 1/32")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.DestinationAddress", STARTS_WITH, "PVC", "PVC: 1/33")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("DSL settings")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.DestinationAddress", VALUE_CHANGE, "", "PVC: 1/35")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.1.IdleDisconnectTime", CONTAINS, "18", "180")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.1.IdleDisconnectTime", EQUAL, "240", "240")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.1.IdleDisconnectTime", GREATER, "60", "70")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.1.IdleDisconnectTime", GREATER_EQUAL, "80", "80")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.1.IdleDisconnectTime", LESS, "130", "125")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.1.IdleDisconnectTime", LESS_EQUAL, "140", "139")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.1.IdleDisconnectTime", NOT_EQUAL, "500", "300")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.1.IdleDisconnectTime", STARTS_WITH, "86", "86400")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("WAN")
                .setParametersMonitor("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.1.IdleDisconnectTime", VALUE_CHANGE, "", "100")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.DHCPLeaseTime", CONTAINS, "18", "180")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.DHCPLeaseTime", EQUAL, "240", "240")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.DHCPLeaseTime", GREATER, "60", "70")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.DHCPLeaseTime", GREATER_EQUAL, "80", "80")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.DHCPLeaseTime", LESS, "130", "125")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.DHCPLeaseTime", LESS_EQUAL, "140", "139")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.DHCPLeaseTime", NOT_EQUAL, "500", "300")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.DHCPLeaseTime", STARTS_WITH, "86", "86400")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("LAN")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.DHCPLeaseTime", VALUE_CHANGE, "", "100")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WEPKeyIndex", CONTAINS, "1", "10")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WEPKeyIndex", EQUAL, "13", "13")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WEPKeyIndex", GREATER, "11", "12")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WEPKeyIndex", GREATER_EQUAL, "7", "9")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WEPKeyIndex", LESS, "8", "7")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WEPKeyIndex", LESS_EQUAL, "6", "6")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WEPKeyIndex", NOT_EQUAL, "1", "5")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WEPKeyIndex", STARTS_WITH, "1", "11")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("Wireless")
                .setParametersMonitor("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.WEPKeyIndex", VALUE_CHANGE, "", "1")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Name", CONTAINS, "1", "Profile1")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Name", EQUAL, "Profile", "Profile")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Name", GREATER, "Profile1", "Profile2")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Name", GREATER_EQUAL, "Profile3", "Profile3")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Name", LESS, "Profile5", "Profile4")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Name", LESS_EQUAL, "Profile6", "Profile5")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Name", NOT_EQUAL, "Profile5", "Profile6")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Name", STARTS_WITH, "Prof", "Profile7")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
                .selectParametersTab("VoIP settings")
                .setParametersMonitor("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.Name", VALUE_CHANGE, "", "Profile8")
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
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Parameters")
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


}
