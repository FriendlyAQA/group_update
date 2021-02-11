package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.entities.Event;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.BottomButtons.SAVE_AND_ACTIVATE;
import static com.friendly.aqa.entities.Condition.*;
import static com.friendly.aqa.entities.Condition.VALUE_CHANGE;
import static com.friendly.aqa.entities.ParameterType.VALUE;
import static com.friendly.aqa.entities.TopMenu.DEVICE_UPDATE;
import static com.friendly.aqa.entities.TopMenu.EVENTS;
import static com.friendly.aqa.pageobject.EventsPage.Left.NEW;

@Listeners(UniversalVideoListener.class)
public class EventsMqttTests extends BaseTestCase {

   /*
   1. Delete all profiles for current device and set PeriodicInformInterval to low value (e.g. 10 sec) or launch mqtt_ev_000;
   2. At least 1 device online (with serial specified in config.properties) and 1 device offline MUST be present in the group (027);
   3. Emulator MUST be set to auto update value by any unused parameter (e.g. temperature) to trigger 'MQTT PUBLISH'
   */

    @Test
    public void mqtt_ev_000() {
        evPage
                .topMenu(EVENTS)
                .createPreconditions();
    }

    @Test
    public void mqtt_ev_001() {
        evPage
                .topMenu(EVENTS)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void mqtt_ev_002() {
        evPage
                .topMenu(EVENTS)
                .checkRefreshPage();
    }

    @Test
    public void mqtt_ev_003() {
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
    public void mqtt_ev_004() {
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
    public void mqtt_ev_005() {
        evPage
                .topMenu(EVENTS)
                .newViewButton()
                .fillName("Default")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test   //depends on 004
    public void mqtt_ev_006() {
        evPage
                .topMenu(EVENTS)
                .selectView("mqtt_ev_004")
                .editButton()
                .bottomMenu(CANCEL)
                .assertSelectedViewIs("mqtt_ev_004");
    }

    @Test   //depends on 004
    public void mqtt_ev_007() {
        evPage
                .topMenu(EVENTS)
                .selectView("mqtt_ev_004")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(DEVICE_UPDATE)
                .topMenu(EVENTS)
                .resetView()
                .assertSelectedViewIs("mqtt_ev_004")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp();
    }

    @Test   //depends on 004
    public void mqtt_ev_008() {
        evPage
                .topMenu(EVENTS)
                .selectView("mqtt_ev_004")
                .selectView("Default")
                .assertTableColumnAmountIs(7, "tbl");
    }

    @Test   //depends on 004
    public void mqtt_ev_009() {
        evPage
                .topMenu(EVENTS)
                .selectView("mqtt_ev_004")
                .assertTableColumnAmountIs(2, "tbl");
    }

    @Test   //depends on 004
    public void mqtt_ev_010() {
        evPage
                .topMenu(EVENTS)
                .selectView("mqtt_ev_004")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "mqtt_ev_004");
    }

    @Test
    public void mqtt_ev_011() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE)
                .bottomMenu(CANCEL)
                .assertButtonsAreEnabled(false, ACTIVATE, DEACTIVATE, DELETE)
                .assertButtonsAreEnabled(true, REFRESH);
    }

    @Test
    public void mqtt_ev_012() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE);
    }

    @Test
    public void mqtt_ev_013() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE);
    }

    @Test   //bug: button ADVANCED_VIEW is absent from bottom menu
    public void mqtt_ev_014() {
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
    public void mqtt_ev_015() {
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
    public void mqtt_ev_016() {
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
    public void mqtt_ev_017() {
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
    public void mqtt_ev_018() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .newGroupButton()
                .fillGroupName("mqtt_ev_016")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void mqtt_ev_019() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("mqtt_ev_016")
                .validateDevicesAmountIs(1);
    }

    @Test
    public void mqtt_ev_020() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("All")
                .validateDevicesAmount();
    }

    @Test
    public void mqtt_ev_021() {    //depends on 016
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
    public void mqtt_ev_022() {
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
    public void mqtt_ev_023() {    //depends on 016
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .selectSendTo("mqtt_ev_016")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "mqtt_ev_016");
    }

    @Test
    public void mqtt_ev_024() {
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
    public void mqtt_ev_025() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT CONNECTION", null, "3", "2:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 1);
    }

    @Test
    public void mqtt_ev_026() {    //depends on 025
        evPage
                .topMenu(EVENTS)
                .enterIntoItem("mqtt_ev_025")
                .immediately()
                .selectMainTab("Events")
                .setEvent(new Event("MQTT CONNECTION", null, "1", "1:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoItem("mqtt_ev_025")
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void mqtt_ev_027() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo("All")
                .immediately()
                .selectMainTab("Events")
                .setEvent(new Event("MQTT CONNECTION", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void mqtt_ev_028() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT CONNECTION", null, "5", "6:hours"))
                .setEvent(new Event("MQTT CONNECTION", null, "0", null))
                .setEvent(new Event("MQTT CONNECTION", null, "2", "5:minutes"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void mqtt_ev_029() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT DISCONNECTION", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void mqtt_ev_030() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT FACTORY RESET", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void mqtt_ev_031() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT PUBLISH", null, "1", "3:minutes"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void mqtt_ev_032() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT SUBSCRIBE", null, "1", "2:hours"))
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
    public void mqtt_ev_033() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT CONNECTION", true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()
                .stopEvent();
    }

    @Test
    public void mqtt_ev_034() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT DISCONNECTION", null, "5", "6:hours"))
                .setEvent(new Event("MQTT FACTORY RESET", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
//                .assertLogfileContainsEventSoap()     //Cannot trigger several events during testcase run
                .stopEvent();
    }

    @Test
    public void mqtt_ev_035() {
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

//    @Test   //skipped: due to the lack of editable fields
//    public void mqtt_ev_036() {
//        evPage
//                .createImmediatelyEventOn("Events")
//                .setEvent(new Event("MQTT CONNECTION", null, "1", "1:hours"), true)
//                .addTask("Set parameter value")
//                .setParameter("PeriodicInformInterval", VALUE, "59")
//                .saveButton()
//                .saveButton()
//                .bottomMenu(SAVE_AND_ACTIVATE)
//                .okButtonPopUp()
//                .enterIntoItem()
//                .expandEvents()
//                .validateEvents()
//                .validateAddedEventTasks("MQTT CONNECTION")
////                .assertLogfileContainsEventSoap()
//                .stopEvent();
//    }

    @Test
    public void mqtt_ev_037() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT CONNECTION", null, "1", "3:hours"), true)
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
    public void mqtt_ev_038() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT CONNECTION", null, "1", "4:hours"), true)
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

    @Test   //bug: RB "Device reprovision" is absent from available actions list
    public void mqtt_ev_039() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT CONNECTION", null, "1", "5:hours"), true)
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

//    @Test   //skipped: due to the lack of editable fields
//    public void mqtt_ev_040() {
//        evPage
//                .createImmediatelyEventOn("Events")
//                .setEvent(new Event("MQTT DISCONNECTION", null, "1", "1:hours"), true)
//                .addTask("Set parameter value")
//                .setParameter("PeriodicInformInterval", VALUE, "61")
//                .saveButton()
//                .saveButton()
//                .bottomMenu(SAVE_AND_ACTIVATE)
//                .okButtonPopUp()
//                .enterIntoItem()
//                .expandEvents()
//                .validateEvents()
//                .validateAddedEventTasks("MQTT DISCONNECTION")
////                .assertLogfileContainsEventSoap()
//                .stopEvent();
//    }

    @Test
    public void mqtt_ev_041() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT DISCONNECTION", null, "1", "3:hours"), true)
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
    public void mqtt_ev_042() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT DISCONNECTION", null, "1", "4:hours"), true)
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

    @Test   //bug: RB "Device reprovision" is absent from available actions list
    public void mqtt_ev_043() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT DISCONNECTION", null, "1", "5:hours"), true)
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

//    @Test   //skipped: due to the lack of editable fields
//    public void mqtt_ev_044() {
//        evPage
//                .createImmediatelyEventOn("Events")
//                .setEvent(new Event("MQTT FACTORY RESET", null, "1", "1:hours"), true)
//                .addTask("Set parameter value")
//                .setParameter("PeriodicInformInterval", VALUE, "61")
//                .saveButton()
//                .saveButton()
//                .bottomMenu(SAVE_AND_ACTIVATE)
//                .okButtonPopUp()
//                .enterIntoItem()
//                .expandEvents()
//                .validateEvents()
//                .validateAddedEventTasks("MQTT FACTORY RESET")
////                .assertLogfileContainsEventSoap()
//                .stopEvent();
//    }

    @Test
    public void mqtt_ev_045() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT FACTORY RESET", null, "1", "3:hours"), true)
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
    public void mqtt_ev_046() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT FACTORY RESET", null, "1", "4:hours"), true)
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

    @Test   //bug: RB "Device reprovision" is absent from available actions list
    public void mqtt_ev_047() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT FACTORY RESET", null, "1", "5:hours"), true)
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

//    @Test   //skipped: due to the lack of editable fields
//    public void mqtt_ev_048() {
//        evPage
//                .createImmediatelyEventOn("Events")
//                .setEvent(new Event("MQTT PUBLISH", null, "1", "1:hours"), true)
//                .addTask("Set parameter value")
//                .setParameter("PeriodicInformInterval", VALUE, "61")
//                .saveButton()
//                .saveButton()
//                .bottomMenu(SAVE_AND_ACTIVATE)
//                .okButtonPopUp()
//                .enterIntoItem()
//                .expandEvents()
//                .validateEvents()
//                .validateAddedEventTasks("MQTT PUBLISH")
//                .assertLogfileContainsEventSoap()
//                .stopEvent();
//    }

    @Test
    public void mqtt_ev_049() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT PUBLISH", null, "1", "3:hours"), true)
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
    public void mqtt_ev_050() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT PUBLISH", null, "1", "4:hours"), true)
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

    @Test   //bug: RB "Device reprovision" is absent from available actions list
    public void mqtt_ev_051() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT PUBLISH", null, "1", "5:hours"), true)
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

    //skipped: 52-60 due to the lack of "Management" tab

    @Test
    public void mqtt_ev_061() {
        evPage
                .createImmediatelyEventOn("Parameters")
//                .selectParametersTab("Information")
                .setParametersMonitor("ModelName", CONTAINS, "QT", "MQTT")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()   //Tab "Information" does not have suitable field to set parameter value
                .stopEvent();
    }

    @Test
    public void mqtt_ev_062() {
        evPage
                .createImmediatelyEventOn("Parameters")
//                .selectParametersTab("Information")
                .setParametersMonitor("ModelName", EQUAL, "MQTT", "MQTT")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()   //Tab "Information" does not have suitable field to set parameter value
                .stopEvent();
    }

    @Test
    public void mqtt_ev_063() {
        evPage
                .createImmediatelyEventOn("Parameters")
//                .selectParametersTab("Information")
                .setParametersMonitor("ModelName", GREATER, "10", "11")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()   //Tab "Information" does not have suitable field to set parameter value
                .stopEvent();
    }

    @Test
    public void mqtt_ev_064() {
        evPage
                .createImmediatelyEventOn("Parameters")
//                .selectParametersTab("Information")
                .setParametersMonitor("ModelName", GREATER_EQUAL, "10", "10")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()   //Tab "Information" does not have suitable field to set parameter value
                .stopEvent();
    }

    @Test
    public void mqtt_ev_065() {
        evPage
                .createImmediatelyEventOn("Parameters")
//                .selectParametersTab("Information")
                .setParametersMonitor("ModelName", LESS, "14", "13")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()   //Tab "Information" does not have suitable field to set parameter value
                .stopEvent();
    }

    @Test
    public void mqtt_ev_066() {
        evPage
                .createImmediatelyEventOn("Parameters")
//                .selectParametersTab("Information")
                .setParametersMonitor("ModelName", LESS_EQUAL, "10", "10")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()   //Tab "Information" does not have suitable field to set parameter value
                .stopEvent();
    }

    @Test
    public void mqtt_ev_067() {
        evPage
                .createImmediatelyEventOn("Parameters")
//                .selectParametersTab("Information")
                .setParametersMonitor("ModelName", NOT_EQUAL, "123", "MQTT")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()   //Tab "Information" does not have suitable field to set parameter value
                .stopEvent();
    }

    @Test
    public void mqtt_ev_068() {
        evPage
                .createImmediatelyEventOn("Parameters")
//                .selectParametersTab("Information")
                .setParametersMonitor("ModelName", STARTS_WITH, "M", "MQTT")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()   //Tab "Information" does not have suitable field to set parameter value
                .stopEvent();
    }

    @Test
    public void mqtt_ev_069() {
        evPage
                .createImmediatelyEventOn("Parameters")
//                .selectParametersTab("Information")
                .setParametersMonitor("ModelName", VALUE_CHANGE, "", "MQTT")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()   //Tab "Information" does not have suitable field to set parameter value
                .stopEvent();
    }

    @Test
    public void mqtt_ev_070() {
        evPage
                .createImmediatelyEventOn("Parameters")
//                .selectParametersTab("Information")
                .setParametersMonitor("ModelName", VALUE_CHANGE, "", "MQTT", true)
                .addTask("Set parameter value")
                .setParameter("ModelName", VALUE, "MQTT")
                .saveButton()
                .saveButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTasks()
//                .triggerEventOnParameter()
//                .assertLogfileContainsEventSoap()   //Tab "Information" does not have suitable field to set parameter value
                .stopEvent();
    }

    @Test
    public void mqtt_ev_071() {
        evPage
                .createImmediatelyEventOn("Parameters")
//                .selectParametersTab("Information")
                .setParametersMonitor("ModelName", VALUE_CHANGE, "", "MQTT", true)
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
//                .assertLogfileContainsEventSoap()   //Tab "Information" does not have suitable field to set parameter value
                .stopEvent();
    }

    //skipped: 70-71 due to the lack of editable fields

    @Test
    public void mqtt_ev_072() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT SUBSCRIBE", true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .stopEvent();
    }

    @Test
    public void mqtt_ev_073() {
        evPage
                .createImmediatelyEventOn("Events")
                .setEvent(new Event("MQTT SUBSCRIBE", true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .deleteEvent();
    }

    @Test
    public void mqtt_ev_074() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("MQTT CONNECTION", null, "5", "6:hours"))
                .setEvent(new Event("MQTT CONNECTION", null, "0", null))
                .setEvent(new Event("MQTT CONNECTION", null, "2", "5:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void mqtt_ev_075() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("MQTT DISCONNECTION", null, "1", "2:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void mqtt_ev_076() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("MQTT FACTORY RESET", null, "1", "3:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void mqtt_ev_077() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("MQTT PUBLISH", null, "1", "4:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void mqtt_ev_078() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("MQTT SUBSCRIBE", null, "1", "5:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void mqtt_ev_079() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("MQTT CONNECTION", true, null, null))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void mqtt_ev_080() {
        evPage
                .createScheduledEventsOn("Events")
                .setEvent(new Event("MQTT DISCONNECTION", null, "1", "6:minutes"))
                .setEvent(new Event("MQTT FACTORY RESET", null, "1", "7:minutes"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoItem()
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void mqtt_ev_081() {
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

    //skipped: 82-99 due to the lack of editable fields
}
