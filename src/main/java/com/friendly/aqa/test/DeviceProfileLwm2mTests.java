package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.entities.Event;
import com.friendly.aqa.utils.CalendarUtil;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.Condition.*;
import static com.friendly.aqa.entities.ParameterType.VALUE;
import static com.friendly.aqa.entities.TopMenu.DEVICE_PROFILE;
import static com.friendly.aqa.pageobject.DeviceProfilePage.BottomButtons.*;
import static com.friendly.aqa.pageobject.DeviceProfilePage.BottomButtons.SAVE_AND_ACTIVATE;
import static com.friendly.aqa.pageobject.DeviceProfilePage.Left.NEW;
import static com.friendly.aqa.pageobject.DeviceProfilePage.Left.VIEW;

@Listeners(UniversalVideoListener.class)
public class DeviceProfileLwm2mTests extends BaseTestCase {

    @Test
    public void lwm2m_dp_000() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .createPreconditions();
    }

    @Test
    public void lwm2m_dp_001() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .assertMainPageIsDisplayed()
                .assertTableIsNotEmpty("tblItems");
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
                .validateSorting("Manufacturer")
                .validateSorting("Model name")
                .validateSorting("Name")
                .validateSorting("Created")
                .validateSorting("Creator")
                .validateSorting("Version");
    }

    @Test
    public void lwm2m_dp_005() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .validateFilteringByStatus();
    }

    @Test
    public void lwm2m_dp_006() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .validateFilteringByModelName();
    }

    @Test
    public void lwm2m_dp_007() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .validateFilteringByManufacturer();
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
                .bottomMenu(DEACTIVATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .selectProfileStatus("Not Active")
                .assertMentionedProfileStateIs("Not active");
    }

    @Test
    public void lwm2m_dp_015() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectModel()
                .selectProfileStatus("Not Active")
                .selectItem("Not active")
                .bottomMenu(ACTIVATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .assertMentionedProfileStateIs("Active");
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
                .bottomMenu(DELETE)
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
                .bottomMenu(CANCEL)
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
                .assertElementsArePresent("lblTemplateNotFound")  //button "Cancel" is absent (STD contradiction)
                .selectManufacturer()
                .selectModel()
                .bottomMenu(CANCEL)
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
                .doNotRequestRadioButton()
                .assertElementIsSelected("rdNoRequest")
                .fullRequestRadioButton()
                .assertElementIsSelected("rdFullRequest")
                .applyProvisionRadioButton()
                .assertElementIsSelected("rdRequiresReprovision")
                .doNotApplyProvisionRadioButton()
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
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Root.Device.0")
                .setParameter("Timezone", "Europe/Kharkov")
                .bottomMenu(SIMPLE_VIEW)
                .validateParameter("Timezone", "Europe/Kharkov")
                .fillName()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .assertMainPageIsDisplayed()
                .selectProfileStatus("All")
                .assertCurrentProfileStateIs("Not active");
    }

    @Test
    public void lwm2m_dp_022() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .doNotRequestRadioButton()
                .doNotApplyProvisionRadioButton()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Kharkov")
                .fillName()
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE, CANCEL)
                .bottomMenu(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void lwm2m_dp_023() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .doNotRequestRadioButton()
                .applyProvisionRadioButton()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Kharkov")
                .fillName()
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE, CANCEL)
                .bottomMenu(CANCEL)
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
                .doNotApplyProvisionRadioButton()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Kharkov")
                .fillName()
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE, CANCEL)
                .bottomMenu(CANCEL)
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
                .bottomMenu(CANCEL)
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
                .bottomMenu(SAVE_AND_ACTIVATE)
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
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("=")
                .fillValue("61000")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Odessa")
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true);
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
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("!=")
                .fillValue("61000")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Kyiv")
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
//                .assertProfileIsPresent(true, getTestName());
                .validateApplyingProfile(false);
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test//depends on 035
    public void lwm2m_dp_036() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_035")
                .setParameter("Server", 99)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("lwm2m_dp_035")
                .validateParameters();
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
                .bottomMenu(ADVANCED_VIEW)
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
                .bottomMenu(ADVANCED_VIEW)
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test //bug: default object has overlying expandable objects
    public void lwm2m_dp_045() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .validateObjectTree();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
    }

    @Test   //bug: cannot delete or modify '4 VALUE CHANGE' event
    public void lwm2m_dp_052() {//depends on 051
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_051")
                .selectMainTab("Events")
                .disableAllEvents()
                .setEvent(new Event("REGISTRATION REQUEST", null, "5", null))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("lwm2m_dp_051")
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("NOTIFY REQUEST");
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
                .selectAction("Reboot")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Reboot");
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
                .selectAction("Factory reset")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Factory reset");
    }

    @Test
    public void lwm2m_dp_056() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("NOTIFY REQUEST", false, "5", null), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values - instance 0");
    }

    @Test
    public void lwm2m_dp_057() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("NOTIFY REQUEST", false, "6", null), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Cumulative energy")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Cumulative energy - instance 0");
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
                .selectAction("Reset errors")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Reset errors");
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
                .selectAction("Disable")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Disable");
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
                .selectAction("Registration update trigger")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Registration update trigger");
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
                .selectAction("Root.Connectivity Statistics.i.StartOrReset")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Root.Connectivity Statistics.i.StartOrReset - instance 0");
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
                .selectAction("Device reprovision")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("NOTIFY REQUEST", "Action", "Device reprovision");
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("REGISTRATION REQUEST");
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
                .selectAction("Reboot")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Reboot");
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
                .selectAction("Factory reset")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Factory reset");
    }

    @Test
    public void lwm2m_dp_066() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("REGISTRATION REQUEST", false, "15", null), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values - instance 0");
    }

    @Test
    public void lwm2m_dp_067() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("REGISTRATION REQUEST", false, "16", null), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Cumulative energy")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Cumulative energy - instance 0");
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
                .selectAction("Reset errors")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Reset errors");
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
                .selectAction("Disable")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Disable");
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
                .selectAction("Registration update trigger")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Registration update trigger");
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
                .selectAction("Root.Connectivity Statistics.i.StartOrReset")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Root.Connectivity Statistics.i.StartOrReset - instance 0");
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
                .selectAction("Device reprovision")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("REGISTRATION REQUEST", "Action", "Device reprovision");
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("UNREGISTRATION REQUEST");
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
                .selectAction("Reboot")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Reboot");
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
                .selectAction("Factory reset")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Factory reset");
    }

    @Test
    public void lwm2m_dp_076() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", false, "25", null), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values - instance 0");
    }

    @Test
    public void lwm2m_dp_077() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UNREGISTRATION REQUEST", false, "26", null), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Cumulative energy")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Cumulative energy - instance 0");
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
                .selectAction("Reset errors")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Reset errors");
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
                .selectAction("Disable")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Disable");
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
                .selectAction("Registration update trigger")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Registration update trigger");
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
                .selectAction("Root.Connectivity Statistics.i.StartOrReset")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Root.Connectivity Statistics.i.StartOrReset - instance 0");
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
                .selectAction("Device reprovision")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UNREGISTRATION REQUEST", "Action", "Device reprovision");
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("UPDATE REQUEST");
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
                .selectAction("Reboot")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UPDATE REQUEST", "Action", "Reboot");
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
                .selectAction("Factory reset")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UPDATE REQUEST", "Action", "Factory reset");
    }

    @Test
    public void lwm2m_dp_086() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UPDATE REQUEST", false, "35", null), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UPDATE REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Min and Max Measured Values - instance 0");
    }

    @Test
    public void lwm2m_dp_087() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("UPDATE REQUEST", false, "36", null), true)
                .addTask("Action")
                .selectAction("Root.IPSO_Power Measurement.i.Reset Cumulative energy")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UPDATE REQUEST", "Action", "Root.IPSO_Power Measurement.i.Reset Cumulative energy - instance 0");
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
                .selectAction("Reset errors")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UPDATE REQUEST", "Action", "Reset errors");
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
                .selectAction("Disable")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UPDATE REQUEST", "Action", "Disable");
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
                .selectAction("Registration update trigger")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UPDATE REQUEST", "Action", "Registration update trigger");
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
                .selectAction("Root.Connectivity Statistics.i.StartOrReset")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UPDATE REQUEST", "Action", "Root.Connectivity Statistics.i.StartOrReset - instance 0");
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
                .selectAction("Device reprovision")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("UPDATE REQUEST", "Action", "Device reprovision");
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test   //bug: Cannot save a profile with edited Monitoring tab;
    public void lwm2m_dp_120() {//depends on 119
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_119")
                .selectMainTab("Monitoring")
                .selectEventTab("Connectivity monitoring")
                .setParametersMonitor(CONTAINS)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("lwm2m_dp_119")
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .validateParametersMonitor();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTasks();
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
                .selectAction("Reboot")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorAction(null, "Action", "Reboot");
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test   //bug: Tab "Policy" is absent
    public void lwm2m_dp_135() {//depends on 134
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_134")
                .selectMainTab("Policy")
                .selectTab("Connectivity monitoring")
                .setPolicy(4)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("lwm2m_dp_134")
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test
    public void lwm2m_dp_137() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Download file")
                .downloadManually("Firmware Image")
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test//depends on 137
    public void lwm2m_dp_138() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_137")
                .selectMainTab("Download file")
                .editFileEntry()
                .downloadFromListFile("Firmware Image")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("lwm2m_dp_137")
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

//    @Test //skipped due to option "Vendor Configuration File" is absent from 'File type' list
//    public void lwm2m_dp_139() {
//        dpPage
//                .topMenu(DEVICE_PROFILE)
//                .leftMenu(NEW)
//                .selectManufacturer()
//                .selectModel()
//                .selectMainTab("Download file")
//                .downloadFromListFile("Vendor Configuration File")
//                .fillName()
//                .bottomMenu(SAVE_AND_ACTIVATE)
//                .okButtonPopUp()
//                .enterIntoProfile()
//                .selectMainTab("Download file")
//                .validateDownloadFile();
//    }
//
//    @Test
//    public void lwm2m_dp_140() {
//        dpPage
//                .topMenu(DEVICE_PROFILE)
//                .leftMenu(NEW)
//                .selectManufacturer()
//                .selectModel()
//                .selectMainTab("Download file")
//                .downloadManually("Vendor Configuration File")
//                .fillName()
//                .bottomMenu(SAVE_AND_ACTIVATE)
//                .okButtonPopUp()
//                .enterIntoProfile()
//                .selectMainTab("Download file")
//                .validateDownloadFile();
//    }
//
//    @Test   //depends on 140
//    public void lwm2m_dp_141() {
//        dpPage
//                .topMenu(DEVICE_PROFILE)
//                .enterIntoProfile("tr181_dp_140")
//                .selectMainTab("Download file")
//                .editFileEntry()
//                .downloadFromListFile("Vendor Configuration File")
//                .bottomMenu(SAVE)
//                .okButtonPopUp()
//                .enterIntoProfile("tr181_dp_140")
//                .selectMainTab("Download file")
//                .validateDownloadFile();
//    }

    @Test
    public void lwm2m_dp_142() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Download file")
                .downloadFromListFile("LWM2M Resource Definition")
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test
    public void lwm2m_dp_143() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Download file")
                .downloadManually("LWM2M Resource Definition")
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test
    public void lwm2m_dp_144() {//depends on 143
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_143")
                .selectMainTab("Download file")
                .editFileEntry()
                .downloadFromListFile("LWM2M Resource Definition")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("lwm2m_dp_143")
                .selectMainTab("Download file")
                .validateDownloadFile();
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
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test
    public void lwm2m_dp_146() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Download file")
                .downloadManually("LWM2M PSK Credentials")
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test   //depends on 146
    public void lwm2m_dp_147() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_146")
                .selectMainTab("Download file")
                .editFileEntry()
                .downloadFromListFile("LWM2M PSK Credentials")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("lwm2m_dp_146")
                .selectMainTab("Download file")
                .validateDownloadFile();
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
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("=")
                .fillValue("61000")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Kyiv")
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true, "Timezone", "Europe/Kyiv");
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
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("!=")
                .fillValue("61000")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Kharkiv")
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(false, "Timezone", "Europe/Kharkiv");
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
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("^6.+$")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
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
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("location")
                .selectConditionTypeComboBox("=")
                .fillValue("USA")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
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
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("location")
                .selectConditionTypeComboBox("!=")
                .fillValue("Ukraine")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
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
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("location")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("^US.$")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test
    public void lwm2m_dp_154() {
        dpPage
                .presetFilter("User tag", "user_tag")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("=")
                .fillValue("user_tag")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
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
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("!=")
                .fillValue("user_tag_2")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //depends on 154
    public void lwm2m_dp_156() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("Regexp")
                .fillValue(".+r_ta.$")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
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
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("userid")
                .selectConditionTypeComboBox("=")
                .fillValue("245")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
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
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("userid")
                .selectConditionTypeComboBox("!=")
                .fillValue("246")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
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
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("userid")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("\\d{3}")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
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
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("userstatus")
                .selectConditionTypeComboBox("=")
                .fillValue("online")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
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
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("userstatus")
                .selectConditionTypeComboBox("!=")
                .fillValue("offline")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test  //depends on 160
    public void lwm2m_dp_162() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("userstatus")
                .selectConditionTypeComboBox("Regexp")
                .fillValue(".{5}e")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceSummary' contain?
    public void lwm2m_dp_163() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceSummary")
                .selectConditionInformComboBox("=")
                .fillValue("???")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceSummary' contain?
    public void lwm2m_dp_164() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceSummary")
                .selectConditionInformComboBox("!=")
                .fillValue("????")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceSummary' contain?
    public void lwm2m_dp_165() {  //depends on 162
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceSummary")
                .selectConditionInformComboBox("Regexp")
                .fillValue(".*")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceInfo.SpecVersion' contain?
    public void lwm2m_dp_166() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.SpecVersion")
                .selectConditionInformComboBox("=")
                .fillValue("???")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceInfo.SpecVersion' contain?
    public void lwm2m_dp_167() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.SpecVersion")
                .selectConditionInformComboBox("!=")
                .fillValue("????")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceInfo.SpecVersion' contain?
    public void lwm2m_dp_168() {  //depends on 162
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.SpecVersion")
                .selectConditionInformComboBox("Regexp")
                .fillValue(".*")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceInfo.HardwareVersion' contain?
    public void lwm2m_dp_169() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.HardwareVersion")
                .selectConditionInformComboBox("=")
                .fillValue("???")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceInfo.HardwareVersion' contain?
    public void lwm2m_dp_170() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.HardwareVersion")
                .selectConditionInformComboBox("!=")
                .fillValue("????")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceInfo.HardwareVersion' contain?
    public void lwm2m_dp_171() {  //depends on 162
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.HardwareVersion")
                .selectConditionInformComboBox("Regexp")
                .fillValue(".*")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceInfo.SoftwareVersion' contain?
    public void lwm2m_dp_172() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.SoftwareVersion")
                .selectConditionInformComboBox("=")
                .fillValue("???")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceInfo.SoftwareVersion' contain?
    public void lwm2m_dp_173() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.SoftwareVersion")
                .selectConditionInformComboBox("!=")
                .fillValue("????")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceInfo.SoftwareVersion' contain?
    public void lwm2m_dp_174() {  //depends on 162
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.SoftwareVersion")
                .selectConditionInformComboBox("Regexp")
                .fillValue(".*")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceInfo.ProvisioningCode' contain?
    public void lwm2m_dp_175() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.ProvisioningCode")
                .selectConditionInformComboBox("=")
                .fillValue("???")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceInfo.ProvisioningCode' contain?
    public void lwm2m_dp_176() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.ProvisioningCode")
                .selectConditionInformComboBox("!=")
                .fillValue("????")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceInfo.ProvisioningCode' contain?
    public void lwm2m_dp_177() {  //depends on 162
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.ProvisioningCode")
                .selectConditionInformComboBox("Regexp")
                .fillValue(".*")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true);
    }

    @Test   //bug: what kind of data does the parameter 'InternetGatewayDevice.DeviceSummary' contain?
    public void lwm2m_dp_178() {
        dpPage
                .presetFilter("Zip", "61000")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("=")
                .fillValue("61000")
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceSummary")
                .selectConditionInformComboBox("=")
                .fillValue("???")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Timezone", "Europe/Kyiv")
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true, "Timezone", "Europe/Kyiv");
    }

    @Test
    public void lwm2m_dp_179() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_178")
                .editConditionButton()
                .bottomMenu(NEXT)
                .selectFilterItem(1)
                .deleteFilter()
                .okButtonPopUp()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, "lwm2m_dp_178");
    }

    @Test  //depends on 162
    public void lwm2m_dp_180() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_162")
                .editConditionButton()
                .bottomMenu(DELETE_CONDITION)
                .okButtonPopUp()
                .assertButtonIsEnabled(false, "btnEditView_btn")
                .bottomMenu(SAVE)
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
                .bottomMenu(SAVE_AND_ACTIVATE)
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
                .bottomMenu(SAVE_AND_ACTIVATE)
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
                .bottomMenu(SAVE_AND_ACTIVATE)
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
