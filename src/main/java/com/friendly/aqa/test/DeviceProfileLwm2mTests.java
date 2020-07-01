package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.entities.Event;
import com.friendly.aqa.utils.CalendarUtil;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.Condition.*;
import static com.friendly.aqa.entities.ParameterType.VALUE;
import static com.friendly.aqa.entities.TopMenu.DEVICE_PROFILE;
import static com.friendly.aqa.pageobject.DeviceProfilePage.GlobalButtons.*;
import static com.friendly.aqa.pageobject.DeviceProfilePage.GlobalButtons.SAVE_AND_ACTIVATE;
import static com.friendly.aqa.pageobject.DeviceProfilePage.Left.NEW;
import static com.friendly.aqa.pageobject.DeviceProfilePage.Left.VIEW;

@Listeners(UniversalVideoListener.class)
public class DeviceProfileLwm2mTests extends BaseTestCase {

    @Test
    public void lwm2m_dp_001() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .assertMainPageIsDisplayed()
                .assertTableHasContent("tblItems");
    }

    @Test
    public void lwm2m_dp_002() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoAnyProfile()
                .leftMenu(VIEW)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void lwm2m_dp_003() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .assertPresenceOfOptions("ddlManufacturer", "All", "DEFAULT")
                .assertPresenceOfOptions("ddlModelName", "All", "DEFAULT")
                .assertPresenceOfOptions("ddlUpdateStatus", "All", "Active", "Not Active");
    }

    @Test   //Bug: sorting by "Creator' failed!
    public void lwm2m_dp_004() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectManufacturer("All")
                .selectModel("All")
                .selectProfileStatus("All")
                .checkSorting("Manufacturer")
                .checkSorting("Model name")
                .checkSorting("Name")
                .checkSorting("Created")
//                .checkSorting("Creator")
                .checkSorting("Version");
    }

    @Test
    public void lwm2m_dp_005() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .checkFilteringByStatus();
    }

    @Test
    public void lwm2m_dp_006() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .checkFilteringByModelName();
    }

    @Test
    public void lwm2m_dp_007() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .checkFilteringByManufacturer();
    }

    @Test
    public void lwm2m_dp_008() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectManufacturer("All")
                .selectModel()
                .selectProfileStatus("Active")
                .assertColumnHasSingleValue("State", "Active")
                .selectProfileStatus("Not Active")
                .assertColumnHasSingleValue("State", "Not active");
    }

    @Test
    public void lwm2m_dp_009() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectManufacturer()
                .selectModel("All")
                .selectProfileStatus("Active")
                .assertColumnHasSingleValue("State", "Active")
                .selectProfileStatus("Not Active")
                .assertColumnHasSingleValue("State", "Not active");
    }

    @Test
    public void lwm2m_dp_010() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectManufacturer()
                .selectModel()
                .selectProfileStatus("All")
                .assertColumnContainsValue("State", "Active")
                .assertColumnContainsValue("State", "Not active");
    }

    @Test
    public void lwm2m_dp_011() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .assertButtonsAreEnabled(false, ACTIVATE, DEACTIVATE, DELETE)
                .selectItem("Active", 2)
                .assertButtonsAreEnabled(true, DEACTIVATE, DELETE)
                .assertButtonsAreEnabled(false, ACTIVATE)
                .selectItem("Active", 2)
                .assertButtonsAreEnabled(false, ACTIVATE, DEACTIVATE, DELETE);
    }

    @Test
    public void lwm2m_dp_012() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectProfileStatus("All")
                .assertButtonsAreEnabled(false, ACTIVATE, DEACTIVATE, DELETE)
                .selectItem("Not active", 2)
                .assertButtonsAreEnabled(true, ACTIVATE, DELETE)
                .assertButtonsAreEnabled(false, DEACTIVATE)
                .selectItem("Not active", 2)
                .assertButtonsAreEnabled(false, ACTIVATE, DEACTIVATE, DELETE);
    }

    @Test
    public void lwm2m_dp_013() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectProfileStatus("Not Active")
                .selectItem("Not active")
                .selectProfileStatus("All")
                .assertItemIsSelected();
    }

    @Test
    public void lwm2m_dp_014() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectModel()
                .selectProfileStatus("Active")
                .selectItem("Active")
                .globalButtons(DEACTIVATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .selectProfileStatus("Not Active")
                .assertProfileIsActive(false);
    }

    @Test
    public void lwm2m_dp_015() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectModel()
                .selectProfileStatus("Not Active")
                .selectItem("Not active")
                .globalButtons(ACTIVATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .assertProfileIsActive(true);
    }

    @Test//swapped with 17
    public void lwm2m_dp_016() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .getExport();
    }

    @Test//swapped with 16
    public void lwm2m_dp_017() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectModel()
                .selectProfileStatus("All")
                .selectItem("Active")
                .globalButtons(DELETE)
                .okButtonPopUp()
                .okButtonPopUp()
                .assertProfileIsPresent(false);
    }

    @Test
    public void lwm2m_dp_018() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
//                .selectMainTab("Parameters")
                .setParameter("Device", 1)
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE)
                .fillName()
                .pause(1000)
                .assertButtonsAreEnabled(true, SAVE_AND_ACTIVATE, SAVE, CANCEL)
                .globalButtons(CANCEL)
                .assertMainPageIsDisplayed()
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void lwm2m_dp_019() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName(false)
                .pause(1000)
                .addDeviceWithoutTemplate()
                .assertElementIsPresent("lblTemplateNotFound")  //button "Cancel" is absent (STD contradiction)
                .selectManufacturer()
                .selectModel()
                .globalButtons(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void lwm2m_dp_020() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName(false)
                .fullRequestRadioButton()
                .assertElementIsSelected("rdFullRequest")
                .dontRequestRadioButton()
                .assertElementIsSelected("rdNoRequest")
                .fullRequestRadioButton()
                .assertElementIsSelected("rdFullRequest")
                .applyProvisionRadioButton()
                .assertElementIsSelected("rdRequiresReprovision")
                .dontApplyProvisionRadioButton()
                .assertElementIsSelected("rdNoReprovision")
                .applyProvisionRadioButton()
                .assertElementIsSelected("rdRequiresReprovision");
    }

    @Test
    public void lwm2m_dp_021() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .globalButtons(ADVANCED_VIEW)
//                .selectBranch("ManagementServer")
                .setParameter("Timezone", "Europe/Kharkov")
                .globalButtons(SIMPLE_VIEW)
                .checkParameter("Timezone", "Europe/Kharkov")
                .fillName()
                .globalButtons(SAVE)
                .okButtonPopUp()
                .assertMainPageIsDisplayed()
                .selectProfileStatus("All")
                .assertProfileIsActive(false);
    }

    @Test
    public void lwm2m_dp_022() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .dontRequestRadioButton()
                .dontApplyProvisionRadioButton()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Kharkov")
                .fillName()
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE, CANCEL)
                .globalButtons(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void lwm2m_dp_023() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .dontRequestRadioButton()
                .applyProvisionRadioButton()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Kharkov")
                .fillName()
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE, CANCEL)
                .globalButtons(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void lwm2m_dp_024() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fullRequestRadioButton()
                .dontApplyProvisionRadioButton()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Kharkov")
                .fillName()
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE, CANCEL)
                .globalButtons(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test//bug: red border is absent around empty fields (feature?)
    public void lwm2m_dp_025() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName(false)
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Current Time", "")
                .assertHasRedBorder(true, "Current Time")
                .setParameter("Current Time", "12-34")
                .assertHasRedBorder(false, "Current Time")
                .globalButtons(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void lwm2m_dp_026() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Kharkov")
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .assertProfileIsPresent(true, getTestName());
    }

    @Test
    public void lwm2m_dp_027() {
        dpPage
                .presetFilter("Zip", "61000")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("=")
                .fillValue("61000")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Odessa")
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .checkTargetDevice(true);
    }

    @Test
    public void lwm2m_dp_028() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
//                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("!=")
                .fillValue("61000")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Kyiv")
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
//                .assertProfileIsPresent(true, getTestName());
                .checkTargetDevice(false);
    }

    @Test   // depends on 028
    public void lwm2m_dp_029() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectCondition("lwm2m_dp_028")
                .editConditionButton()
                .assertButtonsAreEnabled(false, DELETE_CONDITION);
    }

    @Test
    public void lwm2m_dp_030() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .setParameter("Device", 1)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void lwm2m_dp_031() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .setParameter("Server", 1)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void lwm2m_dp_032() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .setParameter("Device", 2)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void lwm2m_dp_033() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .setParameter("Server", 2)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void lwm2m_dp_034() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .setParameter("Device", 99)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void lwm2m_dp_035() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .setParameter("Server", 99)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test//depends on 036
    public void lwm2m_dp_036() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_035")
                .setParameter("Server", 99)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("lwm2m_dp_035")
                .checkParameters();
    }

    @Test
    public void lwm2m_dp_037() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName(false)
                .selectMainTab("Parameters")
                .selectTab("Device")
                .globalButtons(ADVANCED_VIEW)
                .selectTreeObject(true)
                .assertParametersAreSelected(true)
                .selectAnotherTreeObject(true)
                .assertParametersAreSelected(true)
                .selectTreeObject(true)
                .assertParametersAreSelected(false);
    }

    @Test
    public void lwm2m_dp_038() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName(false)
                .selectMainTab("Parameters")
                .selectTab("Device")
                .globalButtons(ADVANCED_VIEW)
                .selectTreeObject(false)
                .assertParametersAreSelected(false)
                .selectAnotherTreeObject(true)
                .assertParametersAreSelected(true)
                .selectTreeObject(false)
                .assertParametersAreSelected(false)
                .selectAnotherTreeObject(false)
                .assertParametersAreSelected(true);
    }

    @Test
    public void lwm2m_dp_039() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .addSummaryParameters()
                .setParameter("Device", 1)
                .setAnotherTabParameter(1, false)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void lwm2m_dp_040() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .addSummaryParameters()
                .setParameter("Server", 1)
                .setAnotherTabParameter(1, false)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void lwm2m_dp_041() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .addSummaryParameters()
                .setParameter("Device", 1)
                .setAnotherTabParameter(1, true)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void lwm2m_dp_042() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .addSummaryParameters()
                .setParameter("Server", 1)
                .setAnotherTabParameter(1, true)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void lwm2m_dp_043() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .addSummaryParameters()
                .setParameter("Device", 1)
                .setAnotherTabParameter(99, false)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void lwm2m_dp_044() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .addSummaryParameters()
                .setParameter("Server", 1)
                .setAnotherTabParameter(99, false)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test //bug: default object has overlying expanded objects
    public void lwm2m_dp_045() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Parameters")
                .globalButtons(ADVANCED_VIEW)
                .checkObjectTree();
    }

    @Test
    public void lwm2m_dp_046() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("NOTIFY REQUEST", null, "2", null))
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }

    @Test
//bug:set count of Events to any int, set count of Events to zero, set count of Events to any int, set duration to hours => saved minutes
    public void lwm2m_dp_047() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("NOTIFY REQUEST", false, "5", "3:hours"))
                .assertAddTaskButtonIsActive("NOTIFY REQUEST", true)
                .setEvent(new Event("NOTIFY REQUEST", null, "0", null))
                .assertAddTaskButtonIsActive("NOTIFY REQUEST", false)
                .setEvent(new Event("NOTIFY REQUEST", false, "5", "3:hours"))  //saved 4:minutes
                .assertAddTaskButtonIsActive("NOTIFY REQUEST", true)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }

    @Test
    public void lwm2m_dp_048() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("REGISTRATION REQUEST", true, null, null))
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }

    @Test
    public void lwm2m_dp_049() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("REGISTRATION REQUEST", false, "6", "3:hours"))
                .setEvent(new Event("REGISTRATION REQUEST", true, "1", "1:minutes"))
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }

    @Test
    public void lwm2m_dp_050() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvents(2, new Event("EXAMPLE", false, "5", "8:hours"))
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }

    @Test
    public void lwm2m_dp_051() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvents(99, new Event("EXAMPLE", false, "2", "8:hours"))
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }

    @Test   //bug: editing profile events causes an erase of all events.
    public void lwm2m_dp_052() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_051")
                .selectMainTab("Events")
                .disableAllEvents()
                .setEvent(new Event("REGISTRATION REQUEST", null, "5", null))
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("lwm2m_dp_051")
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }

    @Test
    public void lwm2m_dp_053() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("NOTIFY REQUEST", false, "2", null), true)
                .addTask("Set parameter value")
                .setParameter("Timezone", VALUE, "Europe/Kharkov")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("NOTIFY REQUEST");
    }

    @Test
    public void lwm2m_dp_054() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("NOTIFY REQUEST", false, "3", null), true)
                .addTask("Action")
                .rebootRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("NOTIFY REQUEST", "Action", "Reboot");
    }

    @Test
    public void lwm2m_dp_055() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("NOTIFY REQUEST", false, "4", null), true)
                .addTask("Action")
                .factoryResetRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("NOTIFY REQUEST", "Action", "Factory reset");
    }

    @Test//bug: Save button doesn't work with several RB on 'Action' page;
    public void lwm2m_dp_056() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("NOTIFY REQUEST", false, "5", null), true)
                .addTask("Action")
                .minAndMaxRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("NOTIFY REQUEST", "Action", "Root.IPSO_Humidity.i.Reset Min and Max Measured Values - instance 0");
    }

    @Test//bug: Save button doesn't work with several RB on 'Action' page;
    public void lwm2m_dp_057() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("NOTIFY REQUEST", false, "6", null), true)
                .addTask("Action")
                .cumulativeEnergyButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("NOTIFY REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Cumulative energy - instance 0");
    }

    @Test
    public void lwm2m_dp_058() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("NOTIFY REQUEST", false, "7", null), true)
                .addTask("Action")
                .resetErrors()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("NOTIFY REQUEST", "Action", "Reset errors");
    }

    @Test
    public void lwm2m_dp_059() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("NOTIFY REQUEST", false, "8", null), true)
                .addTask("Action")
                .disableRadiobutton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("NOTIFY REQUEST", "Action", "Disable");
    }

    @Test
    public void lwm2m_dp_060() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("NOTIFY REQUEST", false, "9", null), true)
                .addTask("Action")
                .radioRegistrationUpdateTrigger()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("NOTIFY REQUEST", "Action", "Registration update trigger");
    }

    @Test
    public void lwm2m_dp_061() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("NOTIFY REQUEST", false, "10", null), true)
                .addTask("Action")
                .radioStartOrReset()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("NOTIFY REQUEST", "Action", "Start or reset");
    }

    @Test
    public void lwm2m_dp_062() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("NOTIFY REQUEST", false, "11", null), true)
                .addTask("Action")
                .reprovisionRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("NOTIFY REQUEST", "Action", "Device reprovision");
    }

    @Test
    public void lwm2m_dp_063() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("REGISTRATION REQUEST", false, "12", null), true)
                .addTask("Set parameter value")
                .setParameter("Timezone", VALUE, "Europe/Kharkov")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("REGISTRATION REQUEST");
    }

    @Test
    public void lwm2m_dp_064() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("REGISTRATION REQUEST", false, "13", null), true)
                .addTask("Action")
                .rebootRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("REGISTRATION REQUEST", "Action", "Reboot");
    }

    @Test
    public void lwm2m_dp_065() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("REGISTRATION REQUEST", false, "14", null), true)
                .addTask("Action")
                .factoryResetRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("REGISTRATION REQUEST", "Action", "Factory reset");
    }

    @Test//bug: Save button doesn't work with several RB on 'Action' page;
    public void lwm2m_dp_066() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("REGISTRATION REQUEST", false, "15", null), true)
                .addTask("Action")
                .minAndMaxRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("REGISTRATION REQUEST", "Action", "Root.IPSO_Humidity.i.Reset Min and Max Measured Values - instance 0");
    }

    @Test//bug: Save button doesn't work with several RB on 'Action' page;
    public void lwm2m_dp_067() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("REGISTRATION REQUEST", false, "16", null), true)
                .addTask("Action")
                .cumulativeEnergyButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("REGISTRATION REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Cumulative energy - instance 0");
    }

    @Test
    public void lwm2m_dp_068() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("REGISTRATION REQUEST", false, "17", null), true)
                .addTask("Action")
                .resetErrors()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("REGISTRATION REQUEST", "Action", "Reset errors");
    }

    @Test
    public void lwm2m_dp_069() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("REGISTRATION REQUEST", false, "18", null), true)
                .addTask("Action")
                .disableRadiobutton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("REGISTRATION REQUEST", "Action", "Disable");
    }

    @Test
    public void lwm2m_dp_070() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("REGISTRATION REQUEST", false, "19", null), true)
                .addTask("Action")
                .radioRegistrationUpdateTrigger()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("REGISTRATION REQUEST", "Action", "Registration update trigger");
    }

    @Test
    public void lwm2m_dp_071() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("REGISTRATION REQUEST", false, "20", null), true)
                .addTask("Action")
                .radioStartOrReset()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("REGISTRATION REQUEST", "Action", "Start or reset");
    }

    @Test
    public void lwm2m_dp_072() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("REGISTRATION REQUEST", false, "21", null), true)
                .addTask("Action")
                .reprovisionRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("REGISTRATION REQUEST", "Action", "Device reprovision");
    }

    @Test
    public void lwm2m_dp_073() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", false, "22", null), true)
                .addTask("Set parameter value")
                .setParameter("Timezone", VALUE, "Europe/Kharkov")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("UNREGISTRATION REQUEST");
    }

    @Test
    public void lwm2m_dp_074() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", false, "23", null), true)
                .addTask("Action")
                .rebootRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UNREGISTRATION REQUEST", "Action", "Reboot");
    }

    @Test
    public void lwm2m_dp_075() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", false, "24", null), true)
                .addTask("Action")
                .factoryResetRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UNREGISTRATION REQUEST", "Action", "Factory reset");
    }

    @Test//bug: Save button doesn't work with several RB on 'Action' page;
    public void lwm2m_dp_076() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", false, "25", null), true)
                .addTask("Action")
                .minAndMaxRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UNREGISTRATION REQUEST", "Action", "Root.IPSO_Humidity.i.Reset Min and Max Measured Values - instance 0");
    }

    @Test//bug: Save button doesn't work with several RB on 'Action' page;
    public void lwm2m_dp_077() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", false, "26", null), true)
                .addTask("Action")
                .cumulativeEnergyButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UNREGISTRATION REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Cumulative energy - instance 0");
    }

    @Test
    public void lwm2m_dp_078() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", false, "27", null), true)
                .addTask("Action")
                .resetErrors()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UNREGISTRATION REQUEST", "Action", "Reset errors");
    }

    @Test
    public void lwm2m_dp_079() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", false, "28", null), true)
                .addTask("Action")
                .disableRadiobutton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UNREGISTRATION REQUEST", "Action", "Disable");
    }

    @Test
    public void lwm2m_dp_080() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", false, "29", null), true)
                .addTask("Action")
                .radioRegistrationUpdateTrigger()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UNREGISTRATION REQUEST", "Action", "Registration update trigger");
    }

    @Test
    public void lwm2m_dp_081() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", false, "30", null), true)
                .addTask("Action")
                .radioStartOrReset()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UNREGISTRATION REQUEST", "Action", "Start or reset");
    }

    @Test
    public void lwm2m_dp_082() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", false, "31", null), true)
                .addTask("Action")
                .reprovisionRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UNREGISTRATION REQUEST", "Action", "Device reprovision");
    }

    @Test
    public void lwm2m_dp_083() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UPDATE REQUEST", false, "32", null), true)
                .addTask("Set parameter value")
                .setParameter("Timezone", VALUE, "Europe/Kharkov")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("UPDATE REQUEST");
    }

    @Test
    public void lwm2m_dp_084() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UPDATE REQUEST", false, "33", null), true)
                .addTask("Action")
                .rebootRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UPDATE REQUEST", "Action", "Reboot");
    }

    @Test
    public void lwm2m_dp_085() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UPDATE REQUEST", false, "34", null), true)
                .addTask("Action")
                .factoryResetRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UPDATE REQUEST", "Action", "Factory reset");
    }

    @Test//bug: Save button doesn't work with several RB on 'Action' page;
    public void lwm2m_dp_086() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UPDATE REQUEST", false, "35", null), true)
                .addTask("Action")
                .minAndMaxRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UPDATE REQUEST", "Action", "Root.IPSO_Humidity.i.Reset Min and Max Measured Values - instance 0");
    }

    @Test//bug: Save button doesn't work with several RB on 'Action' page;
    public void lwm2m_dp_087() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UPDATE REQUEST", false, "36", null), true)
                .addTask("Action")
                .cumulativeEnergyButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UPDATE REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Cumulative energy - instance 0");
    }

    @Test
    public void lwm2m_dp_088() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UPDATE REQUEST", false, "37", null), true)
                .addTask("Action")
                .resetErrors()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UPDATE REQUEST", "Action", "Reset errors");
    }

    @Test
    public void lwm2m_dp_089() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UPDATE REQUEST", false, "38", null), true)
                .addTask("Action")
                .disableRadiobutton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UPDATE REQUEST", "Action", "Disable");
    }

    @Test
    public void lwm2m_dp_090() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UPDATE REQUEST", false, "39", null), true)
                .addTask("Action")
                .radioRegistrationUpdateTrigger()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UPDATE REQUEST", "Action", "Registration update trigger");
    }

    @Test
    public void lwm2m_dp_091() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UPDATE REQUEST", false, "40", null), true)
                .addTask("Action")
                .radioStartOrReset()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UPDATE REQUEST", "Action", "Start or reset");
    }

    @Test
    public void lwm2m_dp_092() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UPDATE REQUEST", false, "41", null), true)
                .addTask("Action")
                .reprovisionRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("UPDATE REQUEST", "Action", "Device reprovision");
    }

    @Test
    public void lwm2m_dp_093() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Device")
                .setParametersMonitor(CONTAINS)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_094() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Device")
                .setParametersMonitor(EQUAL)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_095() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Device")
                .setParametersMonitor(GREATER)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_096() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Device")
                .setParametersMonitor(GREATER_EQUAL)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_097() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Device")
                .setParametersMonitor(LESS)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_098() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Device")
                .setParametersMonitor(LESS_EQUAL)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_099() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Device")
                .setParametersMonitor(NOT_EQUAL)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_100() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Device")
                .setParametersMonitor(STARTS_WITH)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_101() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Device")
                .setParametersMonitor(VALUE_CHANGE)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_102() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Server")
                .setParametersMonitor(CONTAINS)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_103() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Server")
                .setParametersMonitor(EQUAL)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_104() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Server")
                .setParametersMonitor(GREATER)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_105() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Server")
                .setParametersMonitor(GREATER_EQUAL)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_106() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Server")
                .setParametersMonitor(LESS)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_107() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Server")
                .setParametersMonitor(LESS_EQUAL)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_108() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Server")
                .setParametersMonitor(NOT_EQUAL)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_109() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Server")
                .setParametersMonitor(STARTS_WITH)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_110() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Server")
                .setParametersMonitor(VALUE_CHANGE)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_111() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Connectivity monitoring")
                .setParametersMonitor(CONTAINS)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_112() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Connectivity monitoring")
                .setParametersMonitor(EQUAL)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_113() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Connectivity monitoring")
                .setParametersMonitor(GREATER)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_114() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Connectivity monitoring")
                .setParametersMonitor(GREATER_EQUAL)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_115() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Connectivity monitoring")
                .setParametersMonitor(LESS)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_116() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Connectivity monitoring")
                .setParametersMonitor(LESS_EQUAL)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_117() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Connectivity monitoring")
                .setParametersMonitor(NOT_EQUAL)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_118() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Connectivity monitoring")
                .setParametersMonitor(STARTS_WITH)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_119() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Connectivity monitoring")
                .setParametersMonitor(VALUE_CHANGE)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test   //bug: Cannot save a profile with edited Monitoring tab settings;
    public void lwm2m_dp_120() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_119")
                .selectMainTab("Monitoring")
                .selectEventTab("Connectivity monitoring")
                .setParametersMonitor(CONTAINS)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("lwm2m_dp_119")
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void lwm2m_dp_121() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Device")
                .setParametersMonitor(VALUE_CHANGE, true)
                .addTask("Set parameter value")
                .setParameter("Current Time", VALUE, CalendarUtil.getTimeStamp())
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .checkParametersMonitor()
                .checkAddedMonitorTasks();
    }

    @Test
    public void lwm2m_dp_122() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .selectEventTab("Device")
                .setParametersMonitor(VALUE_CHANGE, true)
                .addTask("Action")
                .rebootRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .checkParametersMonitor()
                .checkAddedMonitorAction(null, "Action", "Reboot");
    }

    @Test   //bug: Tab "Policy" is absent
    public void lwm2m_dp_123() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Policy")
                .selectTab("Device")
                .setPolicy(1)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test   //bug: Tab "Policy" is absent
    public void lwm2m_dp_124() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Policy")
                .selectTab("Device")
                .setPolicy(2)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test   //bug: Tab "Policy" is absent
    public void lwm2m_dp_125() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Policy")
                .selectTab("Device")
                .setPolicy(4)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test   //bug: Tab "Policy" is absent
    public void lwm2m_dp_126() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Policy")
                .selectTab("Device")
                .setPolicy(3)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test   //bug: Tab "Policy" is absent
    public void lwm2m_dp_127() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Policy")
                .selectTab("Server")
                .setPolicy(1)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test   //bug: Tab "Policy" is absent
    public void lwm2m_dp_128() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Policy")
                .selectTab("Server")
                .setPolicy(2)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test   //bug: Tab "Policy" is absent
    public void lwm2m_dp_129() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Policy")
                .selectTab("Server")
                .setPolicy(4)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test   //bug: Tab "Policy" is absent
    public void lwm2m_dp_130() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Policy")
                .selectTab("Server")
                .setPolicy(3)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test   //bug: Tab "Policy" is absent
    public void lwm2m_dp_131() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Policy")
                .selectTab("Connectivity monitoring")
                .setPolicy(1)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test   //bug: Tab "Policy" is absent
    public void lwm2m_dp_132() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Policy")
                .selectTab("Connectivity monitoring")
                .setPolicy(2)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test   //bug: Tab "Policy" is absent
    public void lwm2m_dp_133() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Policy")
                .selectTab("Connectivity monitoring")
                .setPolicy(4)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test   //bug: Tab "Policy" is absent
    public void lwm2m_dp_134() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Policy")
                .selectTab("Connectivity monitoring")
                .setPolicy(3)
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test   //bug: Tab "Policy" is absent; depends on 134
    public void lwm2m_dp_135() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_134")
                .selectMainTab("Policy")
                .selectTab("Connectivity monitoring")
                .setPolicy(4)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("lwm2m_dp_134")
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void lwm2m_dp_136() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Download file")
                .downloadFromListFile("Firmware Image")
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

    @Test
    public void lwm2m_dp_137() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Download file")
                .downloadManualImageFile("Firmware Image")
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

    @Test//depends on 137
    public void lwm2m_dp_138() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_137")
                .selectMainTab("Download file")
                .editFileEntry()
                .downloadFromListFile("Firmware Image")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("lwm2m_dp_137")
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

//  tests 139-141 are skipped due to "Vendor Configuration File" is absent from dropdown list

    @Test   //bug: cannot upload "Resource Definition" file on server as precondition;
    public void lwm2m_dp_142() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Download file")
                .downloadFromListFile("LWM2M Resource Definition")
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

    @Test   //bug: cannot upload "Resource Definition" file on server as precondition;
    public void lwm2m_dp_143() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Download file")
                .downloadManualImageFile("LWM2M Resource Definition")
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

    @Test      //bug: cannot upload "Resource Definition" file on server as precondition; depends on 143
    public void lwm2m_dp_144() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_143")
                .selectMainTab("Download file")
                .editFileEntry()
                .downloadFromListFile("LWM2M Resource Definition")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("lwm2m_dp_143")
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

    @Test
    public void lwm2m_dp_145() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Download file")
                .downloadFromListFile("LWM2M PSK Credentials")
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

    @Test
    public void lwm2m_dp_146() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Download file")
                .downloadManualImageFile("LWM2M PSK Credentials")
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

    @Test   //depends on 146
    public void lwm2m_dp_147() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_146")
                .selectMainTab("Download file")
                .editFileEntry()
                .downloadFromListFile("LWM2M PSK Credentials")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("lwm2m_dp_146")
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

    @Test//duplicate with 027
    public void lwm2m_dp_148() {
        dpPage
                .presetFilter("Zip", "61000")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("=")
                .fillValue("61000")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Kyiv")
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(true, "Timezone", "Europe/Kyiv");
    }

    @Test
    public void lwm2m_dp_149() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("!=")
                .fillValue("61000")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Kharkiv")
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(false, "Timezone", "Europe/Kharkiv");
    }

    @Test
    public void lwm2m_dp_150() {
        dpPage
                .presetFilter("Zip", "61000")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("^6.+$")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(true);
    }

    @Test
    public void lwm2m_dp_151() {
        dpPage
                .presetFilter("User location", "USA")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("location")
                .selectConditionTypeComboBox("=")
                .fillValue("USA")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(true);
    }

    @Test
    public void lwm2m_dp_152() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("location")
                .selectConditionTypeComboBox("!=")
                .fillValue("Ukraine")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(true);
    }

    @Test
    public void lwm2m_dp_153() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("location")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("^US.$")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(true);
    }

    @Test
    public void lwm2m_dp_154() {
        dpPage
                .presetFilter("User tag", "Egorych")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("=")
                .fillValue("Egorych")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(true);
    }

    @Test
    public void lwm2m_dp_155() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("!=")
                .fillValue("Zheka")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(true);
    }

    @Test
    public void lwm2m_dp_156() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("Regexp")
                .fillValue(".+ryc.$")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(true);
    }

    @Test
    public void lwm2m_dp_157() {
        dpPage
                .presetFilter("User ID", "245")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("userid")
                .selectConditionTypeComboBox("=")
                .fillValue("245")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(true);
    }

    @Test
    public void lwm2m_dp_158() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("userid")
                .selectConditionTypeComboBox("!=")
                .fillValue("246")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(true);
    }

    @Test
    public void lwm2m_dp_159() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("userid")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("\\d{3}")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(true);
    }

    @Test
    public void lwm2m_dp_160() {
        dpPage
                .presetFilter("User status", "online")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("userstatus")
                .selectConditionTypeComboBox("=")
                .fillValue("online")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(true);
    }

    @Test
    public void lwm2m_dp_161() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("userstatus")
                .selectConditionTypeComboBox("!=")
                .fillValue("offline")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(true);
    }

    @Test
    public void lwm2m_dp_162() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .selectUserInfoComboBox("userstatus")
                .selectConditionTypeComboBox("Regexp")
                .fillValue(".{5}e")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .checkTargetDevice(true);
    }

//  tests 163-165 are skipped due to "DeviceSummary" is absent from dropdown list

//  tests 166-168 are skipped due to "SpecVersion" is absent from dropdown list

//  tests 169-171 are skipped due to "HardwareVersion" is absent from dropdown list

//  tests 172-174 are skipped due to "SoftwareVersion" is absent from dropdown list

//  tests 175-177 are skipped due to "ProvisioningCode" is absent from dropdown list

//  tests 178 is skipped due to Inform radiobutton and "DeviceSummary" option are absent



    @Test  //bug: depends on test with 2 conditions (178)
    public void lwm2m_dp_179() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_162")
                .editConditionButton()
                .globalButtons(NEXT)
                .selectFilterItem(1)
                .deleteFilter()
                .okButtonPopUp()
                .globalButtons(FINISH)
                .okButtonPopUp()
                .globalButtons(SAVE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, "lwm2m_dp_162");
    }

    @Test  //depends on 162
    public void lwm2m_dp_180() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_162")
                .editConditionButton()
                .globalButtons(DELETE_CONDITION)
                .okButtonPopUp()
                .assertButtonIsActive(false, "btnEditView_btn")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, "lwm2m_dp_162");
    }

    @Test
    public void lwm2m_dp_181() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .setParameter("Device", 1)
                .fillName()
                .fullRequestRadioButton()
                .applyProvisionRadioButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName());
    }

    @Test  //bug: "Perform device..." checkbox is absent
    public void lwm2m_dp_182() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .setParameter("Device", 1)
                .fillName()
                .fullRequestRadioButton()
                .applyProvisionRadioButton()
                .performDeviceCheckbox()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName());
    }

    @Test  //bug: "Apply for..." checkbox is absent
    public void lwm2m_dp_183() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .setParameter("Device", 1)
                .fillName()
                .fullRequestRadioButton()
                .applyProvisionRadioButton()
                .performDeviceCheckbox()
                .applyForNewDeviceCheckbox()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName());
    }


//    @Test
//    public void lwm2m_dp_999() {
//        dpPage
//                .topMenu(DEVICE_PROFILE)
//                .deleteAllProfiles();
//    }
}
