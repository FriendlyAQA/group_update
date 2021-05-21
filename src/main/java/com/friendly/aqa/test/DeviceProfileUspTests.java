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
import static com.friendly.aqa.pageobject.DeviceProfilePage.BottomButtons.CANCEL;
import static com.friendly.aqa.pageobject.DeviceProfilePage.Left.NEW;
import static com.friendly.aqa.pageobject.DeviceProfilePage.Left.VIEW;

@Listeners(UniversalVideoListener.class)
public class DeviceProfileUspTests extends BaseTestCase {

    @Test
    public void usp_dp_000() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .createPreconditions();
    }

    @Test
    public void usp_dp_001() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .assertMainPageIsDisplayed()
                .assertTableIsNotEmpty("tblItems");
    }

    @Test
    public void usp_dp_002() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoAnyProfile()
                .leftMenu(VIEW)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void usp_dp_003() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .assertPresenceOfOptions("ddlManufacturer", "All", "DEFAULT")
                .assertPresenceOfOptions("ddlModelName", "All", "DEFAULT")
                .assertPresenceOfOptions("ddlUpdateStatus", "All", "Active", "Not Active");
    }

    @Test   //Bug: sorting by "Creator' failed!
    public void usp_dp_004() {
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
    public void usp_dp_005() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .validateFiltering("Status");
    }

    @Test
    public void usp_dp_006() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .validateFiltering("Model name");
    }

    @Test
    public void usp_dp_007() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .validateFiltering("Manufacturer");
    }

    @Test
    public void usp_dp_008() {
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
    public void usp_dp_009() {
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
    public void usp_dp_010() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectManufacturer()
                .selectModel()
                .selectProfileStatus("All")
                .assertColumnContainsValue("State", "Active")
                .assertColumnContainsValue("State", "Not active");
    }

    @Test
    public void usp_dp_011() {
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
    public void usp_dp_012() {
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
    public void usp_dp_013() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectProfileStatus("Not Active")
                .selectItem("Not active")
                .selectProfileStatus("All")
                .assertItemIsSelected();
    }

    @Test
    public void usp_dp_014() {
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
    public void usp_dp_015() {
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
    public void usp_dp_016() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .getExport();
    }

    @Test//swapped with 16
    public void usp_dp_017() {
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
    public void usp_dp_018() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .setParameter(null, 1)
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE)
                .fillName()
                .assertButtonsAreEnabled(true, SAVE_AND_ACTIVATE, SAVE, CANCEL)
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed()
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void usp_dp_019() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName(false)
                .addDeviceWithoutTemplate()
                .assertElementsArePresent("lblTemplateNotFound")  //button "Cancel" is absent (STD contradiction)
                .selectManufacturer()
                .selectModel()
                .bottomMenu(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void usp_dp_020() {
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
    public void usp_dp_021() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .setAnyAdvancedParameter()
                .selectMainTab("Summary")
                .fillName()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .assertMainPageIsDisplayed()
                .selectProfileStatus("All")
                .assertCurrentProfileStateIs("Not active");
    }

    @Test
    public void usp_dp_022() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .doNotRequestRadioButton()
                .doNotApplyProvisionRadioButton()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .setAnyAdvancedParameter()
                .fillName()
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE, CANCEL)
                .bottomMenu(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void usp_dp_023() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .doNotRequestRadioButton()
                .applyProvisionRadioButton()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .setAnyAdvancedParameter()
                .fillName()
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE, CANCEL)
                .bottomMenu(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void usp_dp_024() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fullRequestRadioButton()
                .doNotApplyProvisionRadioButton()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .setAnyAdvancedParameter()
                .fillName()
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE, CANCEL)
                .bottomMenu(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void usp_dp_025() {
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
////                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("=")
                .fillValue("61000")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true, true);
    }

    @Test
    public void usp_dp_026() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
//                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("!=")
                .fillValue("61000")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(false, true);
    }

    @Test   // depends on 026
    public void usp_dp_027() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectCondition("usp_dp_026")
                .editConditionButton()
                .assertButtonsAreEnabled(false, DELETE_CONDITION);
    }

    @Test
    public void usp_dp_028() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName(false)
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectTreeObject(true)
                .assertParametersAreSelected(true)
                .selectAnotherTreeObject(true)
                .assertParametersAreSelected(true)
                .selectTreeObject(true)
                .assertParametersAreSelected(false);
    }

    @Test
    public void usp_dp_029() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName(false)
                .selectMainTab("Parameters")
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
    public void usp_dp_030() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
//                .addSummaryParameters()
                .setParameter("MTP USP", 1)
                .setAnotherTabParameter(1, false)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void usp_dp_031() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
//                .addSummaryParameters()
                .setParameter("MTP USP", 1)
                .setAnotherTabParameter(1, true)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void usp_dp_032() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .setParameter("MTP USP", 1)
                .setAnotherTabParameter(99, false)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

//    @Test //bug: default object has overlying expanded objects
//    public void usp_dp_033() {
//        dpPage
//                .topMenu(DEVICE_PROFILE)
//                .leftMenu(NEW)
//                .selectManufacturer()
//                .selectModel()
//                .selectMainTab("Parameters")
//                .bottomMenu(ADVANCED_VIEW)
//                .validateObjectTree();
//    }

    @Test
    public void usp_dp_034() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Boot!", null, "2", null))
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
    public void usp_dp_035() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("DUStateChange!", false, "5", "3:hours"))
                .assertAddTaskButtonIsActive("DUStateChange!", true)
                .setEvent(new Event("DUStateChange!", null, "0", null))
                .assertAddTaskButtonIsActive("DUStateChange!", false)
                .setEvent(new Event("DUStateChange!", false, "5", "3:hours"))  //saved 4:minutes
                .assertAddTaskButtonIsActive("DUStateChange!", true)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void usp_dp_036() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("OnBoardRequest", true, null, null))
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void usp_dp_037() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Periodic!", false, "6", "3:hours"))
                .setEvent(new Event("Periodic!", true, "1", "1:minutes"))
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void usp_dp_038() {
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
    public void usp_dp_039() {
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

    @Test
    public void usp_dp_040() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("usp_dp_039")
                .selectMainTab("Events")
                .disableAllEvents()
                .setEvent(new Event("Push!", null, "5", null))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("usp_dp_039")
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void usp_dp_041() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Boot!", false, "2", null), true)
                .addTask("Set parameter value")
                .clickOn("btnAdvancedView_btn")
                .selectBranch("Device.Location.1")
                .setParameter("Longitude", VALUE, "1234")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("Boot!");
    }

    @Test
    public void usp_dp_042() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Boot!", false, "3", null), true)
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
                .validateAddedEventAction("Boot!", "Action", "Reboot");
    }

    @Test
    public void usp_dp_043() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Boot!", false, "4", null), true)
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
                .validateAddedEventAction("Boot!", "Action", "Factory reset");
    }

    @Test   //bug: device may not have that instance
    public void usp_dp_044() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Boot!", false, "4", null), true)
                .addTask("Action")
                .selectAction("Onboard request Select instance", "0")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("Boot!", "Action", "Onboard request - instance 0");
    }

    @Test
    public void usp_dp_045() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Boot!", false, "4", null), true)
                .addTask("Action")
                .selectAction("Onboard request Select instance", "1")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("Boot!", "Action", "Onboard request - instance 1");
    }

    @Test   //bug: device may not have that instance
    public void usp_dp_046() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Boot!", false, "4", null), true)
                .addTask("Action")
                .selectAction("Onboard request Select instance", "2")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("Boot!", "Action", "Onboard request - instance 2");
    }

    @Test
    public void usp_dp_047() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Boot!", false, "4", null), true)
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
                .validateAddedEventAction("Boot!", "Action", "Device reprovision");
    }

    @Test
    public void usp_dp_048() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("DUStateChange!", false, "2", null), true)
                .addTask("Set parameter value")
                .clickOn("btnAdvancedView_btn")
                .selectBranch("Device.Location.1")
                .setParameter("Longitude", VALUE, "1234")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("DUStateChange!");
    }

    @Test
    public void usp_dp_049() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("DUStateChange!", false, "3", null), true)
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
                .validateAddedEventAction("DUStateChange!", "Action", "Reboot");
    }

    @Test
    public void usp_dp_050() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("DUStateChange!", false, "4", null), true)
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
                .validateAddedEventAction("DUStateChange!", "Action", "Factory reset");
    }

    @Test   //bug: device may not have that instance
    public void usp_dp_051() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("DUStateChange!", false, "4", null), true)
                .addTask("Action")
                .selectAction("Onboard request Select instance", "0")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("DUStateChange!", "Action", "Onboard request - instance 0");
    }

    @Test
    public void usp_dp_052() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("DUStateChange!", false, "4", null), true)
                .addTask("Action")
                .selectAction("Onboard request Select instance", "1")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("DUStateChange!", "Action", "Onboard request - instance 1");
    }

    @Test   //bug: device may not have that instance
    public void usp_dp_053() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("DUStateChange!", false, "4", null), true)
                .addTask("Action")
                .selectAction("Onboard request Select instance", "2")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("DUStateChange!", "Action", "Onboard request - instance 2");
    }

    @Test
    public void usp_dp_054() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("DUStateChange!", false, "4", null), true)
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
                .validateAddedEventAction("DUStateChange!", "Action", "Device reprovision");
    }

    @Test
    public void usp_dp_055() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Periodic!", false, "2", null), true)
                .addTask("Set parameter value")
                .clickOn("btnAdvancedView_btn")
                .selectBranch("Device.Location.1")
                .setParameter("Longitude", VALUE, "1234")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("Periodic!");
    }

    @Test
    public void usp_dp_056() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Periodic!", false, "3", null), true)
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
                .validateAddedEventAction("Periodic!", "Action", "Reboot");
    }

    @Test
    public void usp_dp_057() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Periodic!", false, "4", null), true)
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
                .validateAddedEventAction("Periodic!", "Action", "Factory reset");
    }

    @Test   //bug: device may not have that instance
    public void usp_dp_058() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Periodic!", false, "4", null), true)
                .addTask("Action")
                .selectAction("Onboard request Select instance", "0")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("Periodic!", "Action", "Onboard request - instance 0");
    }

    @Test
    public void usp_dp_059() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Periodic!", false, "4", null), true)
                .addTask("Action")
                .selectAction("Onboard request Select instance", "1")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("Periodic!", "Action", "Onboard request - instance 1");
    }

    @Test   //bug: device may not have that instance
    public void usp_dp_060() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Periodic!", false, "4", null), true)
                .addTask("Action")
                .selectAction("Onboard request Select instance", "2")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("Periodic!", "Action", "Onboard request - instance 2");
    }

    @Test
    public void usp_dp_061() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Periodic!", false, "4", null), true)
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
                .validateAddedEventAction("Periodic!", "Action", "Device reprovision");
    }

    @Test
    public void usp_dp_062() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Timer!", false, "2", null), true)
                .addTask("Set parameter value")
                .clickOn("btnAdvancedView_btn")
                .selectBranch("Device.Location.1")
                .setParameter("Longitude", VALUE, "1234")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("Timer!");
    }

    @Test
    public void usp_dp_063() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Timer!", false, "3", null), true)
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
                .validateAddedEventAction("Timer!", "Action", "Reboot");
    }

    @Test
    public void usp_dp_064() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Timer!", false, "4", null), true)
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
                .validateAddedEventAction("Timer!", "Action", "Factory reset");
    }

    @Test   //bug: device may not have that instance
    public void usp_dp_065() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Timer!", false, "4", null), true)
                .addTask("Action")
                .selectAction("Onboard request Select instance", "0")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("Timer!", "Action", "Onboard request - instance 0");
    }

    @Test
    public void usp_dp_066() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Timer!", false, "4", null), true)
                .addTask("Action")
                .selectAction("Onboard request Select instance", "1")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("Timer!", "Action", "Onboard request - instance 1");
    }

    @Test   //bug: device may not have that instance
    public void usp_dp_067() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Timer!", false, "4", null), true)
                .addTask("Action")
                .selectAction("Onboard request Select instance", "2")
                .saveTaskButton()
                .saveTaskButton()
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("Timer!", "Action", "Onboard request - instance 2");
    }

    @Test
    public void usp_dp_068() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Events")
                .setEvent(new Event("Timer!", false, "4", null), true)
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
                .validateAddedEventAction("Timer!", "Action", "Device reprovision");
    }

    @Test
    public void usp_dp_069() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .setParametersMonitor(CONTAINS)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_dp_070() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .setParametersMonitor(EQUAL)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_dp_071() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .setParametersMonitor(GREATER)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_dp_072() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .setParametersMonitor(GREATER_EQUAL)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_dp_073() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .setParametersMonitor(LESS)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_dp_074() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .setParametersMonitor(LESS_EQUAL)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_dp_075() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .setParametersMonitor(NOT_EQUAL)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_dp_076() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .setParametersMonitor(STARTS_WITH)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_dp_077() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .setParametersMonitor(VALUE_CHANGE)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test   //bug: Cannot save a profile with edited Monitoring tab settings;
    public void usp_dp_078() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("usp_dp_077")
                .selectMainTab("Monitoring")
                .setParametersMonitor(CONTAINS)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("usp_dp_077")
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void usp_dp_079() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
                .setParametersMonitor(VALUE_CHANGE, true)
                .addTask("Set parameter value")
                .clickOn("btnAdvancedView_btn")
                .selectBranch("Device.Location.1")
                .setParameter("Longitude", VALUE, "1234")
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
    public void usp_dp_080() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Monitoring")
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

    @Test
    public void usp_dp_081() {
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
    public void usp_dp_082() {
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

    @Test
    public void usp_dp_083() {   //depends on 82
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("usp_dp_082")
                .selectMainTab("Download file")
                .editFileEntry()
                .downloadFromListFile("Firmware Image")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("usp_dp_082")
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test
    public void usp_dp_084() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Download file")
                .downloadFromListFile("Vendor Configuration File")
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test
    public void usp_dp_085() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Download file")
                .downloadManually("Vendor Configuration File")
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test   //depends on 85
    public void usp_dp_086() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("usp_dp_085")
                .selectMainTab("Download file")
                .editFileEntry()
                .downloadFromListFile("Vendor Configuration File")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("usp_dp_085")
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test   //duplicate with 025
    public void usp_dp_087() {
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
//                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("=")
                .fillValue("61000")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true, true);
    }

    @Test   //duplicate with 026
    public void usp_dp_088() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
//                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("!=")
                .fillValue("61000")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
//                .selectProfileStatus("Active")
                .validateApplyingProfile(false, true);
    }

    @Test
    public void usp_dp_089() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
//                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("^6.+$")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true, true);
    }

    @Test
    public void usp_dp_090() {
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
//                .userInfoRadioButton()
                .selectUserInfoComboBox("location")
                .selectConditionTypeComboBox("=")
                .fillValue("USA")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true, true);
    }

    @Test
    public void usp_dp_091() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
//                .userInfoRadioButton()
                .selectUserInfoComboBox("location")
                .selectConditionTypeComboBox("!=")
                .fillValue("Ukraine")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true, true);
    }

    @Test
    public void usp_dp_092() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
//                .userInfoRadioButton()
                .selectUserInfoComboBox("location")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("^US.$")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true, true);
    }

    @Test
    public void usp_dp_093() {
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
//                .userInfoRadioButton()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("=")
                .fillValue("user_tag")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true, true);
    }

    @Test
    public void usp_dp_094() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
//                .userInfoRadioButton()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("!=")
                .fillValue("user_tag_2")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true, true);
    }

    @Test   //depends on 093
    public void usp_dp_095() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
//                .userInfoRadioButton()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("Regexp")
                .fillValue(".+r_ta.$")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true, true);
    }

    @Test
    public void usp_dp_096() {
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
//                .userInfoRadioButton()
                .selectUserInfoComboBox("userid")
                .selectConditionTypeComboBox("=")
                .fillValue("245")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true, true);
    }

    @Test
    public void usp_dp_097() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
//                .userInfoRadioButton()
                .selectUserInfoComboBox("userid")
                .selectConditionTypeComboBox("!=")
                .fillValue("246")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true, true);
    }

    @Test
    public void usp_dp_098() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
//                .userInfoRadioButton()
                .selectUserInfoComboBox("userid")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("\\d{3}")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true, true);
    }

    @Test
    public void usp_dp_099() {
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
//                .userInfoRadioButton()
                .selectUserInfoComboBox("userstatus")
                .selectConditionTypeComboBox("=")
                .fillValue("online")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true, true);
    }

    @Test
    public void usp_dp_100() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
//                .userInfoRadioButton()
                .selectUserInfoComboBox("userstatus")
                .selectConditionTypeComboBox("!=")
                .fillValue("offline")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true, true);
    }

    @Test
    public void usp_dp_101() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .newConditionButton(true)
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
//                .userInfoRadioButton()
                .selectUserInfoComboBox("userstatus")
                .selectConditionTypeComboBox("Regexp")
                .fillValue(".{5}e")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .setParameter(-1)
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .validateApplyingProfile(true, true);
    }

    @Test   //bug: Error message: "parameter with name 'Device.DeviceSummary' not found"
    public void usp_dp_102() {
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
                .selectInformComboBox("Device.DeviceSummary")
                .selectConditionInformComboBox("=")
                .fillValue("???")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Port", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true, "MTP USP");
    }

    @Test   //bug: Error message: "parameter with name 'Device.DeviceSummary' not found"
    public void usp_dp_103() {
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
                .selectInformComboBox("Device.DeviceSummary")
                .selectConditionInformComboBox("!=")
                .fillValue("????")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Port", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true, "MTP USP");
    }

    @Test   //bug: Error message: "parameter with name 'Device.DeviceSummary' not found"
    public void usp_dp_104() {  //depends on 162
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
                .selectInformComboBox("Device.DeviceSummary")
                .selectConditionInformComboBox("Regexp")
                .fillValue(".*")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Port", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true, "MTP USP");
    }

    @Test   //bug: what kind of data does the parameter 'Device.DeviceInfo.HardwareVersion' contain?
    public void usp_dp_105() {
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
                .selectInformComboBox("Device.DeviceInfo.HardwareVersion")
                .selectConditionInformComboBox("=")
                .fillValue("???")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Port", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true, "MTP USP");
    }

    @Test   //bug: what kind of data does the parameter 'Device.DeviceInfo.HardwareVersion' contain?
    public void usp_dp_106() {
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
                .selectInformComboBox("Device.DeviceInfo.HardwareVersion")
                .selectConditionInformComboBox("!=")
                .fillValue("????")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Port", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true, "MTP USP");
    }

    @Test   //bug: what kind of data does the parameter 'Device.DeviceInfo.HardwareVersion' contain?
    public void usp_dp_107() {  //depends on 162
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
                .selectInformComboBox("Device.DeviceInfo.HardwareVersion")
                .selectConditionInformComboBox("Regexp")
                .fillValue(".*")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Port", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true, "MTP USP");
    }

    @Test   //bug: what kind of data does the parameter 'Device.DeviceInfo.SoftwareVersion' contain?
    public void usp_dp_108() {
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
                .selectInformComboBox("Device.DeviceInfo.SoftwareVersion")
                .selectConditionInformComboBox("=")
                .fillValue("???")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Port", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true, "MTP USP");
    }

    @Test   //bug: what kind of data does the parameter 'Device.DeviceInfo.SoftwareVersion' contain?
    public void usp_dp_109() {
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
                .selectInformComboBox("Device.DeviceInfo.SoftwareVersion")
                .selectConditionInformComboBox("!=")
                .fillValue("????")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Port", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true, "MTP USP");
    }

    @Test   //bug: what kind of data does the parameter 'Device.DeviceInfo.SoftwareVersion' contain?
    public void usp_dp_110() {  //depends on 162
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
                .selectInformComboBox("Device.DeviceInfo.SoftwareVersion")
                .selectConditionInformComboBox("Regexp")
                .fillValue(".*")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Port", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true, "MTP USP");
    }

    @Test   //bug: what kind of data does the parameter 'Device.DeviceSummary' contain?
    public void usp_dp_111() {
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
//                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("=")
                .fillValue("61000")
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("Device.DeviceSummary")
                .selectConditionInformComboBox("=")
                .fillValue("???")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Device")
                .setParameter("Port", CalendarUtil.getTimeStamp())
                .fillName()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .validateApplyingProfile(true, "MTP USP");
    }

    @Test  //bug: depends on test with 2 conditions (111)
    public void usp_dp_112() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("lwm2m_dp_111")
                .editConditionButton()
                .bottomMenu(NEXT)
                .selectFilterItem(1)
                .deleteFilter()
                .okButtonPopUp()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, "lwm2m_dp_111");
    }

    @Test  //depends on 101
    public void usp_dp_113() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("usp_dp_101")
                .editConditionButton()
                .bottomMenu(DELETE_CONDITION)
                .okButtonPopUp()
                .assertButtonIsEnabled(false, "btnEditView_btn")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, "usp_dp_101");
    }

    @Test
    public void usp_dp_114() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .setParameter(1)
                .fillName()
                .fullRequestRadioButton()
                .applyProvisionRadioButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName());
    }

    @Test
    public void usp_dp_115() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .setParameter(1)
                .fillName()
                .fullRequestRadioButton()
                .applyProvisionRadioButton()
                .performDeviceCheckbox()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName());
    }

    @Test
    public void usp_dp_116() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Parameters")
                .bottomMenu(ADVANCED_VIEW)
                .setParameter(1)
                .fillName()
                .fullRequestRadioButton()
                .applyProvisionRadioButton()
                .performDeviceCheckbox()
                .applyForNewDeviceCheckbox()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName());
    }
}
