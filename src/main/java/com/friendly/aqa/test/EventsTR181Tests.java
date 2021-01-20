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
public class EventsTR181Tests extends BaseTestCase {

   /*
   1. Delete all profiles for current device and set PeriodicInformInterval to low value (e.g. 10 sec) or launch tr181_ev_000;
   2. At least 1 device online (with serial specified in config.properties) and 1 device offline MUST be present in the group (027);
   3. Emulator MUST be set to auto change value by any unused parameter (e.g. SSID name) to trigger '4 VALUE CHANGE' and
      duplicate the same parameter in config.properties file;
   4. Clean Device Activity.
   */

    @Test
    public void tr181_ev_000() {
        evPage
                .topMenu(EVENTS)
                .createPreconditions();
    }

    @Test
    public void tr181_ev_001() {
        evPage
                .topMenu(EVENTS)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr181_ev_002() {
        evPage
                .topMenu(EVENTS)
                .checkRefreshPage();
    }

    @Test
    public void tr181_ev_003() {
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
    public void tr181_ev_004() {
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
    public void tr181_ev_005() {
        evPage
                .topMenu(EVENTS)
                .newViewButton()
                .fillName("Default")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test   //depends on 004
    public void tr181_ev_006() {
        evPage
                .topMenu(EVENTS)
                .selectView("tr181_ev_004")
                .editButton()
                .bottomMenu(CANCEL)
                .assertSelectedViewIs("tr181_ev_004");
    }

    @Test   //depends on 004
    public void tr181_ev_007() {
        evPage
                .topMenu(EVENTS)
                .selectView("tr181_ev_004")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(DEVICE_UPDATE)
                .topMenu(EVENTS)
                .resetView()
                .assertSelectedViewIs("tr181_ev_004")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp();
    }

    @Test   //depends on 004
    public void tr181_ev_008() {
        evPage
                .topMenu(EVENTS)
                .selectView("tr181_ev_004")
                .selectView("Default")
                .assertTableColumnAmountIs(7, "tbl");
    }

    @Test   //depends on 004
    public void tr181_ev_009() {
        evPage
                .topMenu(EVENTS)
                .selectView("tr181_ev_004")
                .assertTableColumnAmountIs(2, "tbl");
    }

    @Test   //depends on 004
    public void tr181_ev_010() {
        evPage
                .topMenu(EVENTS)
                .selectView("tr181_ev_004")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "tr181_ev_004");
    }

    @Test
    public void tr181_ev_011() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE)
                .bottomMenu(CANCEL)
                .assertButtonsAreEnabled(false, ACTIVATE, DEACTIVATE, DELETE)
                .assertButtonsAreEnabled(true, REFRESH);
    }

    @Test
    public void tr181_ev_012() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE);
    }

    @Test
    public void tr181_ev_013() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE);
    }

    @Test
    public void tr181_ev_014() {
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
    public void tr181_ev_015() {
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
    public void tr181_ev_016() {
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
    public void tr181_ev_017() {
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
    public void tr181_ev_018() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .newGroupButton()
                .fillGroupName("tr181_ev_016")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void tr181_ev_019() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("tr181_ev_016")
                .validateDevicesAmountIs(1);
    }

    @Test
    public void tr181_ev_020() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("All")
                .validateDevicesAmount();
    }

    @Test
    public void tr181_ev_021() {    //depends on 016
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
    public void tr181_ev_022() {
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
    public void tr181_ev_023() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("tr181_ev_016")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "tr181_ev_016");
    }

    @Test
    public void tr181_ev_024() {
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
    public void tr181_ev_025() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "3", "2:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 1);
    }

    @Test
    public void tr181_ev_026() {    //depends on 025
        evPage
                .topMenu(EVENTS)
                .enterIntoItem("tr181_ev_025")
                .immediately()
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "1:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem("tr181_ev_025")
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr181_ev_027() {
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
    public void tr181_ev_028() {
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
    public void tr181_ev_029() {
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
    public void tr181_ev_030() {
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
    public void tr181_ev_031() {
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
    public void tr181_ev_032() {
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
    public void tr181_ev_033() {
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
    public void tr181_ev_034() {
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
    public void tr181_ev_035() {
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
    public void tr181_ev_036() {
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
    public void tr181_ev_037() {
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
    public void tr181_ev_038() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_039() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_040() {
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
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_041() {
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
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_042() {
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
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_043() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_044() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_045() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_046() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_047() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_048() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_049() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_050() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_051() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_052() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_053() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_054() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_055() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_056() {
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
                .validateAddedEventAction()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_057() {
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
                .validateAddedEventAction()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_058() {
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
                .validateAddedEventAction()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_059() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_060() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_061() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_062() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_063() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_064() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_065() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_066() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_067() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_068() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_069() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_070() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_071() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_072() {
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
                .validateAddedEventAction()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_073() {
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
                .validateAddedEventAction()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_074() {
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
                .validateAddedEventAction()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_075() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_076() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_077() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_078() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_079() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_080() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_081() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_082() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_083() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_084() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_085() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_086() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_087() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_088() {
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
                .validateAddedEventAction()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_089() {
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
                .validateAddedEventAction()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_090() {
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
                .validateAddedEventAction()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_091() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_092() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_093() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_094() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_095() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_096() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_097() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_098() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_099() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_100() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_101() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_102() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_103() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_104() {
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
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_105() {
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
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_106() {
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
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_107() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_108() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_109() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_110() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_111() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_112() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_113() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_114() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_115() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_116() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_117() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_118() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_119() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_120() {
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
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_121() {
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
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_122() {
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
                .validateAddedEventAction()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_123() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_124() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_125() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_126() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_127() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_128() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_129() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_130() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_131() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_132() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_133() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_134() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_135() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_136() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_137() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_138() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_139() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_140() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_141() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_142() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_143() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_144() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_145() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_146() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_147() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_148() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_149() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_150() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_151() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_152() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_153() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_154() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_155() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_156() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_157() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_158() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_159() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_160() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_161() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_162() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_163() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_164() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_165() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_166() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_167() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_168() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_169() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_170() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_171() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_172() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_173() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_174() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_175() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_176() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_177() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_178() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_179() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_180() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_181() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_182() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_183() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_184() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_185() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_186() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_187() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_188() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_189() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_190() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_191() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_192() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_193() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_194() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_195() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_196() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_197() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_198() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_199() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_200() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_201() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_202() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_203() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_204() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_205() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_206() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_207() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_208() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_209() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_210() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_211() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_212() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_213() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_214() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_215() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_216() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_217() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_218() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_219() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_220() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_221() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_222() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_223() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_224() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_225() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_226() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_227() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_228() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_229() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_230() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_231() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_232() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_233() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_234() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_235() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_236() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_237() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_238() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_239() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_240() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_241() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_242() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_243() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_244() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_245() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_246() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_247() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_248() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_249() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_250() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_251() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_252() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_253() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("IP")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_254() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_255() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_256() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_257() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_258() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_259() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_260() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_261() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_262() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_263() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_264() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_265() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_266() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_267() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_268() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_269() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_270() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_271() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_272() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_273() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_274() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_275() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_276() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_277() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_278() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_279() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_280() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_281() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_282() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_283() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_284() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_285() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_286() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_287() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_288() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_289() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_290() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_291() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_292() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_293() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_294() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_295() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_296() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_297() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_298() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_299() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_300() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_301() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_302() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_303() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_304() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_305() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_306() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_307() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_308() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_309() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_310() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_311() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_312() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_313() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_314() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_315() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_316() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_317() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_318() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_319() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_320() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_321() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_322() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_323() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_324() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_325() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_326() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_327() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_328() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_329() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_330() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_331() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_332() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_333() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_334() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_335() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_336() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_337() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_338() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_339() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_340() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_341() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_342() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_343() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_344() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_345() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_346() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_347() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_348() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_349() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_350() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_351() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_352() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_353() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_354() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_355() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_356() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_357() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_358() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_359() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_360() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_361() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_362() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_363() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_364() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_365() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_366() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_367() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_368() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_369() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_370() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_371() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_372() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_373() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("Users")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_374() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "1:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_375() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_376() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "3:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_377() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "4:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_378() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "5:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_379() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "6:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_380() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "7:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_381() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "8:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_382() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_383() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_384() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_385() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_386() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "13:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_387() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "14:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_388() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "15:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_389() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "16:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_390() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "17:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_391() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "18:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_392() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "19:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_393() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "20:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_394() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "21:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_395() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "22:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_396() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "23:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(4)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_397() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "24:hours"), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setTaskPolicy(3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_398() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 1 BOOT during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_399() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_400() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_401() {
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
                .validateAddedEventTasks()
                .triggerConnectionRequest()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_402() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 7 TRANSFER COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_403() {
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
                .validateAddedEventTasks()
//                .assertLogfileContainsEventSoap()     //Cannot trigger 8 DIAGNOSTICS COMPLETE during testcase run
                .stopEvent();
    }

    @Test
    public void tr181_ev_404() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_405() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_406() {
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_407() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "5:minutes"), true)
                .addTask("Get parameter")
                .selectTab("WiFi")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_408() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "6:minutes"), true)
                .addTask("Get parameter")
                .selectTab("IP")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_409() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "7:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Firewall")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_410() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "8:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DHCPv4")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_411() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "9:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DHCPv6")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_412() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "10:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DNS")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_413() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "11:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Users")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_414() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "12:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Ethernet")
                .getParameter(1, 1)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_415() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "13:minutes"), true)
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_416() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "14:minutes"), true)
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_417() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "15:minutes"), true)
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_418() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "16:minutes"), true)
                .addTask("Get parameter")
                .selectTab("WiFi")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_419() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "17:minutes"), true)
                .addTask("Get parameter")
                .selectTab("IP")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_420() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "18:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Firewall")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_421() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "21:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DHCPv4")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_422() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "22:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DHCPv6")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_423() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "23:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DNS")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_424() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "24:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Users")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_425() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "25:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Ethernet")
                .getParameter(1, 2)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_426() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "26:minutes"), true)
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_427() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "27:minutes"), true)
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_428() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "28:minutes"), true)
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_429() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "29:minutes"), true)
                .addTask("Get parameter")
                .selectTab("WiFi")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_430() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "30:minutes"), true)
                .addTask("Get parameter")
                .selectTab("IP")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_431() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "31:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Firewall")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_432() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "32:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DHCPv4")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_433() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "33:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DHCPv6")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_434() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "34:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DNS")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_435() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "35:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Users")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_436() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "36:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Ethernet")
                .getParameter(1, 3)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_437() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "37:minutes"), true)
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_438() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "38:minutes"), true)
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_439() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "39:minutes"), true)
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
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_440() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "40:minutes"), true)
                .addTask("Get parameter")
                .selectTab("WiFi")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_441() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "41:minutes"), true)
                .addTask("Get parameter")
                .selectTab("IP")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_442() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "42:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Firewall")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_443() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "43:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DHCPv4")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_444() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "44:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DHCPv6")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_445() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "45:minutes"), true)
                .addTask("Get parameter")
                .selectTab("DNS")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test   //Bug: Parameter name isn't displayed into result table;
    public void tr181_ev_446() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "46:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Users")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_447() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "47:minutes"), true)
                .addTask("Get parameter")
                .selectTab("Ethernet")
                .getParameter(1, 0)
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_448() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "48:minutes"), true)
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
    public void tr181_ev_449() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "49:minutes"), true)
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
    public void tr181_ev_450() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "50:minutes"), true)
                .addTask("Diagnostics")
                .selectDiagnostic("Trace diagnostics")
                .inputHost("8.8.8.8")
                .inputNumOfRepetitions("3")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Trace diagnostics", "8.8.8.8")
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_451() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "51:minutes"), true)
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

    @Test
    public void tr181_ev_452() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "52:minutes"), true)
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

    @Test
    public void tr181_ev_453() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "53:minutes"), true)
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

    @Test
    public void tr181_ev_454() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "50:minutes"), true)
                .addTask("Diagnostics")
                .selectDiagnostic("IPPing diagnostics")
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

    @Test
    public void tr181_ev_455() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "55:minutes"), true)
                .addTask("Diagnostics")
                .selectDiagnostic("NSLoopback diagnostics")
                .inputDnsField("8.8.8.8")
                .inputHost("127.0.0.1")
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
    public void tr181_ev_456() {
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
    public void tr181_ev_457() {
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
    public void tr181_ev_458() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformInterval, sec", GREATER, "10", "11")
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
    public void tr181_ev_459() {
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
    public void tr181_ev_460() {
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
    public void tr181_ev_461() {
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
    public void tr181_ev_462() {
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
    public void tr181_ev_463() {
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
    public void tr181_ev_464() {
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
    public void tr181_ev_465() {
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
    public void tr181_ev_466() {
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
    public void tr181_ev_467() {
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
    public void tr181_ev_468() {
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
    public void tr181_ev_469() {
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
    public void tr181_ev_470() {
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
    public void tr181_ev_471() {
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
    public void tr181_ev_472() {
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

    @Test
    public void tr181_ev_473() {
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
    public void tr181_ev_474() {
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
    public void tr181_ev_475() {
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
    public void tr181_ev_476() {
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
    public void tr181_ev_477() {
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
    public void tr181_ev_478() {
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
    public void tr181_ev_479() {
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
    public void tr181_ev_480() {
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
    public void tr181_ev_481() {
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
    public void tr181_ev_482() {
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
    public void tr181_ev_483() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", CONTAINS, "34", "345")
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
    public void tr181_ev_484() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", EQUAL, "123", "123")
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
    public void tr181_ev_485() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", GREATER, "10", "11")
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
    public void tr181_ev_486() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", GREATER_EQUAL, "10", "10")
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
    public void tr181_ev_487() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", LESS, "14", "13")
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
    public void tr181_ev_488() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", LESS_EQUAL, "10", "10")
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
    public void tr181_ev_489() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", NOT_EQUAL, "123", "121")
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
    public void tr181_ev_490() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", STARTS_WITH, "1", "121")
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
    public void tr181_ev_491() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", VALUE_CHANGE, "", "987")
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
    public void tr181_ev_492() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", CONTAINS, "72", "7210")
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
    public void tr181_ev_493() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", EQUAL, "7211", "7211")
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
    public void tr181_ev_494() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", GREATER, "7211", "7212")
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
    public void tr181_ev_495() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", GREATER_EQUAL, "7213", "7213")
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
    public void tr181_ev_496() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", LESS, "7215", "7214")
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
    public void tr181_ev_497() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", LESS_EQUAL, "7215", "7215")
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
    public void tr181_ev_498() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", NOT_EQUAL, "7215", "7216")
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
    public void tr181_ev_499() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", STARTS_WITH, "72", "7217")
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
    public void tr181_ev_500() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", VALUE_CHANGE, "", "7218")
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
    public void tr181_ev_501() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", CONTAINS, "34", "345")
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
    public void tr181_ev_502() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", EQUAL, "123", "123")
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
    public void tr181_ev_503() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", GREATER, "10", "11")
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
    public void tr181_ev_504() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", GREATER_EQUAL, "10", "10")
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
    public void tr181_ev_505() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", LESS, "14", "13")
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
    public void tr181_ev_506() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", LESS_EQUAL, "10", "10")
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
    public void tr181_ev_507() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", NOT_EQUAL, "123", "121")
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
    public void tr181_ev_508() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", STARTS_WITH, "1", "121")
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
    public void tr181_ev_509() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", VALUE_CHANGE, "", "987")
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
    public void tr181_ev_510() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", CONTAINS, "36", "3600")
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
    public void tr181_ev_511() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", EQUAL, "3601", "3601")
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
    public void tr181_ev_512() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", GREATER, "3601", "3602")
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
    public void tr181_ev_513() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", GREATER_EQUAL, "3602", "3603")
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
    public void tr181_ev_514() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", LESS, "3605", "3604")
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
    public void tr181_ev_515() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", LESS_EQUAL, "3605", "3605")
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
    public void tr181_ev_516() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", NOT_EQUAL, "3602", "3606")
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
    public void tr181_ev_517() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", STARTS_WITH, "360", "3607")
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
    public void tr181_ev_518() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", VALUE_CHANGE, "", "3608")
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
    public void tr181_ev_519() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", CONTAINS, "36", "3600")
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
    public void tr181_ev_520() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", EQUAL, "3601", "3601")
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
    public void tr181_ev_521() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", GREATER, "3601", "3602")
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
    public void tr181_ev_522() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", GREATER_EQUAL, "3602", "3603")
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
    public void tr181_ev_523() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", LESS, "3605", "3604")
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
    public void tr181_ev_524() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", LESS_EQUAL, "3605", "3605")
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
    public void tr181_ev_525() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", NOT_EQUAL, "3602", "3606")
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
    public void tr181_ev_526() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", STARTS_WITH, "360", "3607")
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
    public void tr181_ev_527() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", VALUE_CHANGE, "", "3608")
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
    public void tr181_ev_528() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", CONTAINS, "36", "3600")
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
    public void tr181_ev_529() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", EQUAL, "3601", "3601")
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
    public void tr181_ev_530() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", GREATER, "3601", "3602")
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
    public void tr181_ev_531() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", GREATER_EQUAL, "3602", "3603")
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
    public void tr181_ev_532() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", LESS, "3605", "3604")
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
    public void tr181_ev_533() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", LESS_EQUAL, "3605", "3605")
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
    public void tr181_ev_534() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", NOT_EQUAL, "3602", "3606")
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
    public void tr181_ev_535() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", STARTS_WITH, "360", "3607")
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
    public void tr181_ev_536() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", VALUE_CHANGE, "", "3608")
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
    public void tr181_ev_537() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", CONTAINS, "36", "3600")
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
    public void tr181_ev_538() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", EQUAL, "3601", "3601")
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
    public void tr181_ev_539() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", GREATER, "3601", "3602")
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
    public void tr181_ev_540() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", GREATER_EQUAL, "3602", "3603")
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
    public void tr181_ev_541() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", LESS, "3605", "3604")
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
    public void tr181_ev_542() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", LESS_EQUAL, "3605", "3605")
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
    public void tr181_ev_543() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", NOT_EQUAL, "3602", "3606")
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
    public void tr181_ev_544() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", STARTS_WITH, "360", "3607")
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
    public void tr181_ev_545() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", VALUE_CHANGE, "", "3608")
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
    public void tr181_ev_546() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", CONTAINS, "36", "3600")
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
    public void tr181_ev_547() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", EQUAL, "3601", "3601")
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
    public void tr181_ev_548() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", GREATER, "3601", "3602")
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
    public void tr181_ev_549() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", GREATER_EQUAL, "3602", "3603")
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
    public void tr181_ev_550() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", LESS, "3605", "3604")
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
    public void tr181_ev_551() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", LESS_EQUAL, "3605", "3605")
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
    public void tr181_ev_552() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", NOT_EQUAL, "3602", "3606")
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
    public void tr181_ev_553() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", STARTS_WITH, "360", "3607")
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
    public void tr181_ev_554() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", VALUE_CHANGE, "", "3608")
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
    public void tr181_ev_555() {
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
    public void tr181_ev_556() {
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
    public void tr181_ev_557() {
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
    public void tr181_ev_558() {
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
    public void tr181_ev_559() {
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
    public void tr181_ev_560() {
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
    public void tr181_ev_561() {
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
    public void tr181_ev_562() {
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
    public void tr181_ev_563() {
        evPage
                .createImmediatelyEventOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", VALUE_CHANGE, "", "118", true)
                .addTask("Diagnostics")
                .selectDiagnostic("Trace diagnostics")
                .inputHost("8.8.8.8")
                .inputNumOfRepetitions("3")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTask("Trace diagnostics", "8.8.8.8")
                .triggerEventOnParameter()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void tr181_ev_564() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .stopEvent();
    }

    @Test
    public void tr181_ev_565() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("2 PERIODIC", true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .deleteEvent();
    }

    @Test
    public void tr181_ev_566() {
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
    public void tr181_ev_567() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("1 BOOT", null, "1", "2:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr181_ev_568() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "3:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr181_ev_569() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "4:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr181_ev_570() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "5:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr181_ev_571() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("7 TRANSFER COMPLETE", null, "1", "6:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr181_ev_572() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("8 DIAGNOSTICS COMPLETE", null, "1", "7:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr181_ev_573() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("2 PERIODIC", true, null, null))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr181_ev_574() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("4 VALUE CHANGE", null, "1", "8:minutes"))
                .setEvent(new Event("6 CONNECTION REQUEST", null, "1", "9:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr181_ev_575() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvents(99, new Event(null, null, "1", "10:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr181_ev_576() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", CONTAINS, "34")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_577() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_578() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformInterval, sec", GREATER, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_579() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformInterval, sec", GREATER_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_580() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformInterval, sec", LESS, "14")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_581() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformInterval, sec", LESS_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_582() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", NOT_EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_583() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", STARTS_WITH, "1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_584() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Management")
                .setParametersMonitor("PeriodicInformTime", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_585() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", CONTAINS, "34")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_586() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_587() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", GREATER, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_588() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", GREATER_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_589() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", LESS, "14")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_590() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", LESS_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_591() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", NOT_EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_592() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", STARTS_WITH, "1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_593() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Information")
                .setParametersMonitor("ProvisioningCode", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_594() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", CONTAINS, "34")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_595() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_596() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", GREATER, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_597() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", GREATER_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_598() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", LESS, "14")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_599() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", LESS_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_600() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", NOT_EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_601() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", STARTS_WITH, "1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_602() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Time")
                .setParametersMonitor("NTPServer2", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_603() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", CONTAINS, "34")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_604() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_605() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", GREATER, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_606() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", GREATER_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_607() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", LESS, "14")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_608() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", LESS_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_609() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", NOT_EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_610() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", STARTS_WITH, "1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_611() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("WiFi")
                .setParametersMonitor("SSIDReference", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_612() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", CONTAINS, "104")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_613() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", EQUAL, "711")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_614() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", GREATER, "7211")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_615() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", GREATER_EQUAL, "7213")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_616() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", LESS, "7215")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_617() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", LESS_EQUAL, "7215")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_618() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", NOT_EQUAL, "7215")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_619() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", STARTS_WITH, "75")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_620() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("IP")
                .selectBranch("IPv6Address.1")
                .setParametersMonitor("PreferredLifetime", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_621() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", CONTAINS, "34")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_622() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_623() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", GREATER, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_624() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", GREATER_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_625() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", LESS, "14")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_626() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", LESS_EQUAL, "10")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_627() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", NOT_EQUAL, "123")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_628() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", STARTS_WITH, "1")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_629() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Firewall")
                .setParametersMonitor("Config", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_630() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", CONTAINS, "36")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_631() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", EQUAL, "3601")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_632() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", GREATER, "3601")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_633() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", GREATER_EQUAL, "3602")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_634() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", LESS, "3605")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_635() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", LESS_EQUAL, "3605")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_636() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", NOT_EQUAL, "3602")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_637() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", STARTS_WITH, "360")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_638() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv4")
                .selectBranch("Pool.1")
                .setParametersMonitor("LeaseTime", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_639() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", CONTAINS, "36")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_640() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", EQUAL, "3601")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_641() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", GREATER, "3601")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_642() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", GREATER_EQUAL, "3602")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_643() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", LESS, "3605")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_644() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", LESS_EQUAL, "3605")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_645() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", NOT_EQUAL, "3602")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_646() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", STARTS_WITH, "360")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_647() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DHCPv6")
                .selectBranch("Option.1")
                .setParametersMonitor("Tag", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_648() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", CONTAINS, "36")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_649() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", EQUAL, "3601")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_650() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", GREATER, "3601")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_651() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", GREATER_EQUAL, "3602")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_652() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", LESS, "3605")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_653() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", LESS_EQUAL, "3605")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_654() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", NOT_EQUAL, "3602")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_655() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", STARTS_WITH, "360")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_656() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("DNS")
                .selectBranch("Forwarding.1")
                .setParametersMonitor("DNSServer", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_657() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", CONTAINS, "36")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_658() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", EQUAL, "3601")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_659() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", GREATER, "3601")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_660() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", GREATER_EQUAL, "3602")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_661() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", LESS, "3605")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_662() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", LESS_EQUAL, "3605")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_663() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", NOT_EQUAL, "3602")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_664() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", STARTS_WITH, "360")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_665() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Users")
                .selectBranch("User.1")
                .setParametersMonitor("Password", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_666() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", CONTAINS, "36")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_667() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", EQUAL, "3601")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_668() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", GREATER, "3601")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_669() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", GREATER_EQUAL, "3602")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_670() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", LESS, "3605")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_671() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", LESS_EQUAL, "3605")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_672() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", NOT_EQUAL, "3602")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_673() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", STARTS_WITH, "360")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr181_ev_674() {
        evPage
                .createScheduledEventsOn("Parameters")
                .selectParametersTab("Ethernet")
                .selectBranch("Interface.1")
                .setParametersMonitor("Alias", VALUE_CHANGE, "")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }
}
