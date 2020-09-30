package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.entities.Event;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.Condition.*;
import static com.friendly.aqa.entities.Condition.VALUE_CHANGE;
import static com.friendly.aqa.entities.ParameterType.VALUE;
import static com.friendly.aqa.entities.TopMenu.DEVICE_PROFILE;
import static com.friendly.aqa.pageobject.DeviceProfilePage.BottomButtons.*;
import static com.friendly.aqa.pageobject.DeviceProfilePage.BottomButtons.SAVE_AND_ACTIVATE;
import static com.friendly.aqa.pageobject.DeviceProfilePage.Left.NEW;
import static com.friendly.aqa.pageobject.DeviceProfilePage.Left.VIEW;

@Listeners(UniversalVideoListener.class)
public class DeviceProfileTR069Tests extends BaseTestCase {

    @Test
    public void tr069_dp_001() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .assertMainPageIsDisplayed()
                .assertTableHasContent("tblItems");
    }

    @Test
    public void tr069_dp_002() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoAnyProfile()
                .leftMenu(VIEW)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_dp_003() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .assertPresenceOfOptions("ddlManufacturer", "All", "DEFAULT")
                .assertPresenceOfOptions("ddlModelName", "All", "DEFAULT")
                .assertPresenceOfOptions("ddlUpdateStatus", "All", "Active", "Not Active");
    }

    @Test   //Bug: sorting by "Creator' failed!
    public void tr069_dp_004() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectManufacturer("All")
                .selectModel("All")
                .selectProfileStatus("All")
                .validateSorting("Manufacturer")
                .validateSorting("Model name")
                .validateSorting("Name")
                .validateSorting("Created")
//                .checkSorting("Creator")
                .validateSorting("Version");
    }

    @Test
    public void tr069_dp_005() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .validateFilteringByStatus();
    }

    @Test
    public void tr069_dp_006() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .validateFilteringByModelName();
    }

    @Test
    public void tr069_dp_007() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .validateFilteringByManufacturer();
    }

    @Test
    public void tr069_dp_008() {
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
    public void tr069_dp_009() {
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
    public void tr069_dp_010() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectManufacturer()
                .selectModel()
                .selectProfileStatus("All")
                .assertColumnContainsValue("State", "Active")
                .assertColumnContainsValue("State", "Not active");
    }

    @Test
    public void tr069_dp_011() {
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
    public void tr069_dp_012() {
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
    public void tr069_dp_013() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectProfileStatus("Not Active")
                .selectItem("Not active")
                .selectProfileStatus("All")
                .assertItemIsSelected();
    }

    @Test
    public void tr069_dp_014() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectModel()
                .selectProfileStatus("Active")
                .selectItem("Active")
                .bottomMenu(DEACTIVATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .selectProfileStatus("Not Active")
                .assertProfileIsActive(false);
    }

    @Test
    public void tr069_dp_015() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectModel()
                .selectProfileStatus("Not Active")
                .selectItem("Not active")
                .bottomMenu(ACTIVATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .assertProfileIsActive(true);
    }

    @Test//swapped with 17
    public void tr069_dp_016() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .getExport();
    }

    @Test//swapped with 16
    public void tr069_dp_017() {
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
    public void tr069_dp_018() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE)
                .fillName()
                .pause(1000)
                .assertButtonsAreEnabled(true, SAVE_AND_ACTIVATE, SAVE, CANCEL)
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed()
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void tr069_dp_019() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .pause(1000)
                .addDeviceWithoutTemplate()
                .assertPresenceOfElements("lblTemplateNotFound")  //button "Cancel" is absent (STD contradiction)
                .selectManufacturer()
                .selectModel()
                .bottomMenu(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void tr069_dp_020() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
    public void tr069_dp_021() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .bottomMenu(ADVANCED_VIEW)
//                .selectBranch("ManagementServer")
                .setParameter("PeriodicInformInterval, sec", "70")
                .bottomMenu(SIMPLE_VIEW)
                .validateParameter("PeriodicInformInterval, sec", "70")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .assertMainPageIsDisplayed()
                .selectProfileStatus("All")
                .assertProfileIsActive(false, getTestName());
    }

    @Test
    public void tr069_dp_022() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .dontRequestRadioButton()
                .dontApplyProvisionRadioButton()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "60")
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE, CANCEL)
                .bottomMenu(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void tr069_dp_023() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .dontRequestRadioButton()
                .applyProvisionRadioButton()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "60")
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE, CANCEL)
                .bottomMenu(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void tr069_dp_024() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .fullRequestRadioButton()
                .dontApplyProvisionRadioButton()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "60")
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE, CANCEL)
                .bottomMenu(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void tr069_dp_025() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("ConnectionRequestPassword", "")
                .assertHasRedBorder(true, "ConnectionRequestPassword")
                .setParameter("ConnectionRequestPassword", "ftacs")
                .assertHasRedBorder(false, "ConnectionRequestPassword")
                .setParameter("PeriodicInformEnable", " ")
                .setParameter("PeriodicInformEnable", "")
                .setParameter("PeriodicInformInterval, sec", "")
                .assertHasRedBorder(true, "PeriodicInformInterval, sec")
                .setParameter("PeriodicInformInterval, sec", "60")
                .assertHasRedBorder(false, "PeriodicInformInterval, sec")
                .selectCondition(1)
                .assertButtonIsEnabled(true, "btnEditView_btn")
                .selectCondition(0)
                .assertButtonIsEnabled(false, "btnEditView_btn")
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE, CANCEL)
                .bottomMenu(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void tr069_dp_026() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "60")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .assertProfileIsPresent(true, getTestName());
    }

    @Test
    public void tr069_dp_027() {
        dpPage
                .presetFilter("Zip", "61000")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "61")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
//                .selectProfileStatus("Active")
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "61");
    }

    @Test
    public void tr069_dp_028() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "62")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(false, "PeriodicInformInterval, sec", "62");
    }

    @Test   // depends on 028
    public void tr069_dp_029() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectCondition("tr069_dp_028")
                .editConditionButton()
                .assertButtonsAreEnabled(false, DELETE_CONDITION);
    }

    @Test
    public void tr069_dp_030() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addSummaryParameters()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "60")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_031() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Information", 1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_032() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Time", 1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_033() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("DSL settings", 1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_034() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("WAN", 1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_035() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("LAN", 1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_036() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Wireless", 1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_037() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Management", 2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_038() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Information", 2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_039() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Time", 2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_040() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("DSL settings", 2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_041() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("WAN", 2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_042() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("LAN", 2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_043() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Wireless", 2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_044() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Management", 99)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_045() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Information", 99)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_046() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Time", 99)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_047() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("DSL settings", 99)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_048() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("WAN", 99)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_049() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("LAN", 99)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_050() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Wireless", 99)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test//depends on 050
    public void tr069_dp_051() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr069_dp_050")
                .setParameter("Wireless", 99)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr069_dp_050")
                .validateParameters();
    }

    @Test
    public void tr069_dp_052() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Parameters")
                .selectTab("Wireless")
                .selectTreeObject(true)
                .assertParametersAreSelected(true)
                .selectAnotherTreeObject(true)
                .assertParametersAreSelected(true)
                .selectTreeObject(true)
                .assertParametersAreSelected(false);
    }

    @Test
    public void tr069_dp_053() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Parameters")
                .selectTab("Wireless")
                .selectTreeObject(true, 1)
                .assertParametersAreSelected(true)
                .selectTreeObject(true, 2)
                .assertParametersAreSelected(true);
    }

    @Test
    public void tr069_dp_054() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Download file")
                .downloadManualImageFile("Firmware Image")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, testName);
    }

    @Test
    public void tr069_dp_055() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addSummaryParameters()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "60")
                .setAnotherTabParameter(1, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_056() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Information", 1)
                .setAnotherTabParameter(1, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_057() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Time", 1)
                .setAnotherTabParameter(1, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_058() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("DSL settings", 1)
                .setAnotherTabParameter(1, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_059() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("WAN", 1)
                .setAnotherTabParameter(1, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_060() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("LAN", 1)
                .setAnotherTabParameter(1, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_061() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Wireless", 1)
                .setAnotherTabParameter(1, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_062() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("VoIP settings", 1)
                .setAnotherTabParameter(1, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_063() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addSummaryParameters()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "60")
                .setAnotherTabParameter(1, true)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_064() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Information", 1)
                .setAnotherTabParameter(1, true)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_065() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Time", 1)
                .setAnotherTabParameter(1, true)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_066() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("DSL settings", 1)
                .setAnotherTabParameter(1, true)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_067() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("WAN", 1)
                .setAnotherTabParameter(1, true)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_068() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("LAN", 1)
                .setAnotherTabParameter(1, true)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_069() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Wireless", 1)
                .setAnotherTabParameter(1, true)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_070() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("VoIP settings", 1)
                .setAnotherTabParameter(1, true)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_071() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addSummaryParameters()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "60")
                .setAnotherTabParameter(99, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_072() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Information", 1)
                .setAnotherTabParameter(99, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_073() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Time", 1)
                .setAnotherTabParameter(99, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_074() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("DSL settings", 1)
                .setAnotherTabParameter(99, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_075() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("WAN", 1)
                .setAnotherTabParameter(99, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_076() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("LAN", 1)
                .setAnotherTabParameter(99, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_077() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Wireless", 1)
                .setAnotherTabParameter(99, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_078() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("VoIP settings", 1)
                .setAnotherTabParameter(99, false)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .validateParameters();
    }

    @Test
    public void tr069_dp_079() {
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
    public void tr069_dp_080() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", null, "5", null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
    }

    @Test
//bug:set count of Events to any int, set count of Events to zero, set count of Events to any int, set duration to hours => saved minutes
    public void tr069_dp_081() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("0 BOOTSTRAP", false, "5", "4:hours"))
                .assertAddTaskButtonIsActive("0 BOOTSTRAP", true)
                .setEvent(new Event("0 BOOTSTRAP", null, "0", null))
                .assertAddTaskButtonIsActive("0 BOOTSTRAP", false)
                .setEvent(new Event("0 BOOTSTRAP", false, "5", "4:hours"))  //saved 4:minutes
                .assertAddTaskButtonIsActive("0 BOOTSTRAP", true)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_dp_082() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", true, null, null))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_dp_083() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "6", "3:hours"))
                .setEvent(new Event("1 BOOT", true, "1", "1:minutes"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_dp_084() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvents(2, new Event("EXAMPLE", false, "5", "8:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_dp_085() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvents(99, new Event("EXAMPLE", false, "2", "8:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
    }

    @Test   //bug: editing profile events causes an erase of all events.
    public void tr069_dp_086() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr069_dp_85")
                .selectMainTab("Events")
                .disableAllEvents()
                .setEvent(new Event("2 PERIODIC", null, "5", null))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr069_dp_85")
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents();
    }

    @Test
    public void tr069_dp_087() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Set parameter value")
                .setParameter("PeriodicInformInterval, sec", VALUE, "61")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("1 BOOT");
    }

    @Test
    public void tr069_dp_088() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Download file")
                .selectDownloadFileType("Vendor Configuration File")
                .manualRadioButton()
                .fillUrl()
                .fillUsername()
                .fillPassword()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("1 BOOT", "Vendor Configuration File", props.getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr069_dp_089() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .rebootRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("1 BOOT", "Action", "Reboot");
    }

    @Test
    public void tr069_dp_090() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .factoryResetRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("1 BOOT", "Action", "Factory reset");
    }

    @Test//bug: reprovision RB is missing;
    public void tr069_dp_091() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .reprovisionRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("1 BOOT", "Action", "Device Reprovision");
    }

    @Test
    public void tr069_dp_092() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetRPCMethods")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("1 BOOT", "Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr069_dp_093() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("SetParameterValues")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("1 BOOT", "Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr069_dp_094() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetParameterValues")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("1 BOOT", "Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr069_dp_095() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetParameterNames")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("1 BOOT", "Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr069_dp_096() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("SetParameterAttributes")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("1 BOOT", "Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr069_dp_097() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetParameterAttributes")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("1 BOOT", "Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr069_dp_098() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("AddObject")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("1 BOOT", "Custom RPC", "AddObject");
    }

    @Test
    public void tr069_dp_099() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("DeleteObject")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("1 BOOT", "Custom RPC", "DeleteObject");
    }

    @Test
    public void tr069_dp_100() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("Download")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("1 BOOT", "Custom RPC", "Download");
    }

    @Test
    public void tr069_dp_101() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("Reboot")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("1 BOOT", "Custom RPC", "Reboot");
    }

    @Test
    public void tr069_dp_102() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("FactoryReset")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("1 BOOT", "Custom RPC", "FactoryReset");
    }

    @Test
    public void tr069_dp_103() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("Upload")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("1 BOOT", "Custom RPC", "Upload");
    }

    @Test
    public void tr069_dp_104() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Set parameter value")
                .setParameter("PeriodicInformInterval, sec", VALUE, "61")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("2 PERIODIC");
    }

    @Test
    public void tr069_dp_105() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Download file")
                .selectDownloadFileType("Vendor Configuration File")
                .manualRadioButton()
                .fillUrl()
                .fillUsername()
                .fillPassword()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Vendor Configuration File", props.getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr069_dp_106() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .rebootRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("2 PERIODIC", "Action", "Reboot");
    }

    @Test
    public void tr069_dp_107() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .factoryResetRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("2 PERIODIC", "Action", "Factory Reset");
    }

    @Test//bug: reprovision RB is missing;
    public void tr069_dp_108() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .reprovisionRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("2 PERIODIC", "Action", "Device Reprovision");
    }

    @Test
    public void tr069_dp_109() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetRPCMethods")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr069_dp_110() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("SetParameterValues")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr069_dp_111() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetParameterValues")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr069_dp_112() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetParameterNames")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr069_dp_113() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("SetParameterAttributes")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr069_dp_114() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetParameterAttributes")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr069_dp_115() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("AddObject")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Custom RPC", "AddObject");
    }

    @Test
    public void tr069_dp_116() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("DeleteObject")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Custom RPC", "DeleteObject");
    }

    @Test
    public void tr069_dp_117() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("Download")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Custom RPC", "Download");
    }

    @Test
    public void tr069_dp_118() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("Reboot")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Custom RPC", "Reboot");
    }

    @Test
    public void tr069_dp_119() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("FactoryReset")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Custom RPC", "FactoryReset");
    }

    @Test
    public void tr069_dp_120() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("Upload")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Custom RPC", "Upload");
    }

    @Test
    public void tr069_dp_121() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Set parameter value")
                .setParameter("PeriodicInformInterval, sec", VALUE, "61")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("4 VALUE CHANGE");
    }

    @Test
    public void tr069_dp_122() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Download file")
                .selectDownloadFileType("Vendor Configuration File")
                .manualRadioButton()
                .fillUrl()
                .fillUsername()
                .fillPassword()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("4 VALUE CHANGE", "Vendor Configuration File", props.getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr069_dp_123() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .rebootRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("4 VALUE CHANGE", "Action", "Reboot");
    }

    @Test
    public void tr069_dp_124() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .factoryResetRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("4 VALUE CHANGE", "Action", "Factory Reset");
    }

    @Test//bug: reprovision RB is missing;
    public void tr069_dp_125() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .reprovisionRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("4 VALUE CHANGE", "Action", "Device Reprovision");
    }

    @Test
    public void tr069_dp_126() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetRPCMethods")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("4 VALUE CHANGE", "Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr069_dp_127() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("SetParameterValues")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("4 VALUE CHANGE", "Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr069_dp_128() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetParameterValues")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("4 VALUE CHANGE", "Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr069_dp_129() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetParameterNames")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("4 VALUE CHANGE", "Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr069_dp_130() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("SetParameterAttributes")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("4 VALUE CHANGE", "Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr069_dp_131() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetParameterAttributes")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("4 VALUE CHANGE", "Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr069_dp_132() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("AddObject")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("4 VALUE CHANGE", "Custom RPC", "AddObject");
    }

    @Test
    public void tr069_dp_133() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("DeleteObject")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("4 VALUE CHANGE", "Custom RPC", "DeleteObject");
    }

    @Test
    public void tr069_dp_134() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("Download")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("4 VALUE CHANGE", "Custom RPC", "Download");
    }

    @Test
    public void tr069_dp_135() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("Reboot")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("4 VALUE CHANGE", "Custom RPC", "Reboot");
    }

    @Test
    public void tr069_dp_136() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("FactoryReset")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("4 VALUE CHANGE", "Custom RPC", "FactoryReset");
    }

    @Test
    public void tr069_dp_137() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("Upload")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("4 VALUE CHANGE", "Custom RPC", "Upload");
    }

    @Test
    public void tr069_dp_138() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Set parameter value")
                .setParameter("PeriodicInformInterval, sec", VALUE, "61")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_139() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Download file")
                .selectDownloadFileType("Vendor Configuration File")
                .manualRadioButton()
                .fillUrl()
                .fillUsername()
                .fillPassword()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("6 CONNECTION REQUEST", "Vendor Configuration File", props.getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr069_dp_140() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .rebootRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("6 CONNECTION REQUEST", "Action", "Reboot");
    }

    @Test
    public void tr069_dp_141() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .factoryResetRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("6 CONNECTION REQUEST", "Action", "Factory Reset");
    }

    @Test//bug: reprovision RB is missing;
    public void tr069_dp_142() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .reprovisionRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventAction("6 CONNECTION REQUEST", "Action", "Device Reprovision");
    }

    @Test
    public void tr069_dp_143() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetRPCMethods")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr069_dp_144() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("SetParameterValues")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr069_dp_145() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetParameterValues")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr069_dp_146() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetParameterNames")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr069_dp_147() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("SetParameterAttributes")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr069_dp_148() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("GetParameterAttributes")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr069_dp_149() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("AddObject")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "AddObject");
    }

    @Test
    public void tr069_dp_150() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("DeleteObject")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "DeleteObject");
    }

    @Test
    public void tr069_dp_151() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("Download")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "Download");
    }

    @Test
    public void tr069_dp_152() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("Reboot")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "Reboot");
    }

    @Test
    public void tr069_dp_153() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("FactoryReset")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "FactoryReset");
    }

    @Test
    public void tr069_dp_154() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Action")
                .customRpcRadioButton()
                .selectMethod("Upload")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "Upload");
    }

    @Test
    public void tr069_dp_155() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_156() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_157() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_158() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_159() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_160() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_161() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_162() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Information")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_163() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_164() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_165() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_166() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Time")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_167() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_168() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_169() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_170() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DSL settings")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_171() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_172() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_173() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_174() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("WAN")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_175() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_176() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_177() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_178() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("LAN")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_179() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_180() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_181() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_182() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Wireless")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_183() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_184() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_185() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_186() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("VoIP settings")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_187() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", false, "3", null), true)
                .addTask("Upload file")
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUrlRadioButton()
                .fillUploadUrl()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("1 BOOT", "Vendor Configuration File", props.getProperty("upload_url"));
    }

    @Test
    public void tr069_dp_188() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Upload file")
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUrlRadioButton()
                .fillUploadUrl()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Vendor Configuration File", props.getProperty("upload_url"));
    }

    @Test
    public void tr069_dp_189() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("4 VALUE CHANGE", false, "3", null), true)
                .addTask("Upload file")
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUrlRadioButton()
                .fillUploadUrl()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("4 VALUE CHANGE", "Vendor Configuration File", props.getProperty("upload_url"));
    }

    @Test
    public void tr069_dp_190() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Upload file")
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUrlRadioButton()
                .fillUploadUrl()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("6 CONNECTION REQUEST", "Vendor Configuration File", props.getProperty("upload_url"));
    }

    @Test
    public void tr069_dp_191() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Management")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_192() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Information")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_193() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Time")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_194() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("DSL settings")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_195() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("WAN")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_196() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("LAN")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_197() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Wireless")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_198() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("VoIP settings")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_199() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Management")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_200() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Information")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_201() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Time")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_202() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("DSL settings")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_203() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("WAN")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_204() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("LAN")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_205() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Wireless")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_206() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("VoIP settings")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_207() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Management")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_208() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Information")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_209() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Time")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_210() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("DSL settings")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_211() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("WAN")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_212() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("LAN")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_213() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Wireless")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_214() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("VoIP settings")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_215() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Management")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_216() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Information")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_217() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Time")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_218() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("DSL settings")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_219() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("WAN")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_220() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("LAN")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_221() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("Wireless")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_222() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Get parameter")
                .selectTab("VoIP settings")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr069_dp_223() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Backup")
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Backup");
    }

    @Test//bug: editing profile events causes an erase of all events.
    public void tr069_dp_224() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr069_dp_223")
                .expandEvents()
                .editTask("2 PERIODIC")
                .deleteTask("Backup")
                .assertTaskIsAbsent("Backup")
                .saveTaskButton()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr069_dp_223")
                .selectMainTab("Summary")
                .expandEvents()
                .editTask("2 PERIODIC")
                .assertTaskIsAbsent("Backup");
    }

    @Test
    public void tr069_dp_225() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Restore")
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Restore");
    }

    @Test//bug: editing profile events causes an erase of all events.
    public void tr069_dp_226() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr069_dp_225")
                .expandEvents()
                .editTask("2 PERIODIC")
                .deleteTask("Restore")
                .assertTaskIsAbsent("Restore")
                .saveTaskButton()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr069_dp_225")
                .selectMainTab("Summary")
                .expandEvents()
                .editTask("2 PERIODIC")
                .assertTaskIsAbsent("Restore");
    }

    @Test//bug: there's no such diagnostic
    public void tr069_dp_227() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Diagnostics")
                .selectDiagnostic("Trace diagnostic")
                .inputHost("8.8.8.8")
                .inputNumOfRepetitions("3")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Trace diagnostic", "8.8.8.8");
    }

    @Test//bug: there's no such diagnostic
    public void tr069_dp_228() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Diagnostics")
                .selectDiagnostic("Download diagnostics")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Diagnostics");
    }

    @Test//bug: there's no such diagnostic
    public void tr069_dp_229() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Diagnostics")
                .selectDiagnostic("Upload diagnostics")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Diagnostics");
    }

    @Test//bug: there's no such diagnostic
    public void tr069_dp_230() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Diagnostics")
                .selectDiagnostic("Wi-Fi neighboring diagnostic")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "Wi-Fi neighboring diagnostic", " ");
    }

    @Test
    public void tr069_dp_231() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Diagnostics")
                .selectDiagnostic("IPPing diagnostics")
                .inputHost("8.8.8.8")
                .inputNumOfRepetitions("3")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "IPPing diagnostics", "8.8.8.8");
    }

    @Test//bug: there's no such diagnostic
    public void tr069_dp_232() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Diagnostics")
                .selectDiagnostic("NSLoopback diagnostics")
                .inputDnsField("8.8.8.8")
                .inputHost("127.0.0.1")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .validateEvents()
                .validateAddedEventTask("2 PERIODIC", "NSLoopback diagnostics", "127.0.0.1");
    }

    @Test
    public void tr069_dp_233() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(CONTAINS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_234() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_235() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(GREATER)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_236() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(GREATER_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_237() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(LESS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_238() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(LESS_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_239() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(NOT_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_240() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(STARTS_WITH)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_241() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(VALUE_CHANGE)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_242() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Information")
                .setParametersMonitor(CONTAINS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_243() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Information")
                .setParametersMonitor(EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_244() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Information")
                .setParametersMonitor(GREATER)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_245() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Information")
                .setParametersMonitor(GREATER_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_246() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Information")
                .setParametersMonitor(LESS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_247() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Information")
                .setParametersMonitor(LESS_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_248() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Information")
                .setParametersMonitor(NOT_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_249() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Information")
                .setParametersMonitor(STARTS_WITH)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_250() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Information")
                .setParametersMonitor(VALUE_CHANGE)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_251() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Time")
                .setParametersMonitor(CONTAINS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_252() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Time")
                .setParametersMonitor(EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_253() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Time")
                .setParametersMonitor(GREATER)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_254() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Time")
                .setParametersMonitor(GREATER_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_255() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Time")
                .setParametersMonitor(LESS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_256() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Time")
                .setParametersMonitor(LESS_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_257() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Time")
                .setParametersMonitor(NOT_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_258() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Time")
                .setParametersMonitor(STARTS_WITH)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_259() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Time")
                .setParametersMonitor(VALUE_CHANGE)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_260() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DSL settings")
                .setParametersMonitor(CONTAINS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_261() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DSL settings")
                .setParametersMonitor(EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_262() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DSL settings")
                .setParametersMonitor(GREATER)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_263() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DSL settings")
                .setParametersMonitor(GREATER_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_264() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DSL settings")
                .setParametersMonitor(LESS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_265() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DSL settings")
                .setParametersMonitor(LESS_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_266() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DSL settings")
                .setParametersMonitor(NOT_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_267() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DSL settings")
                .setParametersMonitor(STARTS_WITH)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_268() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DSL settings")
                .setParametersMonitor(VALUE_CHANGE)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_269() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WAN")
                .setParametersMonitor(CONTAINS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_270() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WAN")
                .setParametersMonitor(EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_271() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WAN")
                .setParametersMonitor(GREATER)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_272() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WAN")
                .setParametersMonitor(GREATER_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_273() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WAN")
                .setParametersMonitor(LESS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_274() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WAN")
                .setParametersMonitor(LESS_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_275() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WAN")
                .setParametersMonitor(NOT_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_276() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WAN")
                .setParametersMonitor(STARTS_WITH)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_277() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WAN")
                .setParametersMonitor(VALUE_CHANGE)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_278() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("LAN")
                .setParametersMonitor(CONTAINS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_279() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("LAN")
                .setParametersMonitor(EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_280() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("LAN")
                .setParametersMonitor(GREATER)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_281() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("LAN")
                .setParametersMonitor(GREATER_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_282() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("LAN")
                .setParametersMonitor(LESS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_283() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("LAN")
                .setParametersMonitor(LESS_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_284() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("LAN")
                .setParametersMonitor(NOT_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_285() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("LAN")
                .setParametersMonitor(STARTS_WITH)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_286() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("LAN")
                .setParametersMonitor(VALUE_CHANGE)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_287() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Wireless")
                .setParametersMonitor(CONTAINS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_288() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Wireless")
                .setParametersMonitor(EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_289() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Wireless")
                .setParametersMonitor(GREATER)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_290() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Wireless")
                .setParametersMonitor(GREATER_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_291() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Wireless")
                .setParametersMonitor(LESS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_292() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Wireless")
                .setParametersMonitor(LESS_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_293() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Wireless")
                .setParametersMonitor(NOT_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_294() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Wireless")
                .setParametersMonitor(STARTS_WITH)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_295() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Wireless")
                .setParametersMonitor(VALUE_CHANGE)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_296() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("VoIP settings")
                .setParametersMonitor(CONTAINS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_297() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("VoIP settings")
                .setParametersMonitor(EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_298() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("VoIP settings")
                .setParametersMonitor(GREATER)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_299() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("VoIP settings")
                .setParametersMonitor(GREATER_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_300() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("VoIP settings")
                .setParametersMonitor(LESS)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_301() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("VoIP settings")
                .setParametersMonitor(LESS_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_302() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("VoIP settings")
                .setParametersMonitor(NOT_EQUAL)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_303() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("VoIP settings")
                .setParametersMonitor(STARTS_WITH)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_304() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("VoIP settings")
                .setParametersMonitor(VALUE_CHANGE)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_305() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr069_dp_304")
                .selectMainTab("Monitoring")
                .selectEventTab("VoIP settings")
                .setParametersMonitor(CONTAINS)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr069_dp_304")
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .validateParametersMonitor();
    }

    @Test
    public void tr069_dp_306() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(VALUE_CHANGE, true)
                .addTask("Set parameter value")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTasks();
    }

    @Test
    public void tr069_dp_307() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(VALUE_CHANGE, true)
                .addTask("Download file")
                .selectDownloadFileType("Vendor Configuration File")
                .manualRadioButton()
                .fillUrl()
                .fillUsername()
                .fillPassword()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTask("Vendor Configuration File", props.getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr069_dp_308() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(VALUE_CHANGE, true)
                .addTask("Action")
                .rebootRadioButton()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorAction(null, "Action", "Reboot");
    }

    @Test
    public void tr069_dp_309() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(VALUE_CHANGE, true)
                .addTask("Policy")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTasks();
    }

    @Test
    public void tr069_dp_310() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(VALUE_CHANGE, true)
                .addTask("Upload file")
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUrlRadioButton()
                .fillUploadUrl()
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTask("Vendor Configuration File", props.getProperty("upload_url"));
    }

    @Test
    public void tr069_dp_311() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(VALUE_CHANGE, true)
                .addTask("Get parameter")
                .selectTab("Management")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTasks();
    }

    @Test
    public void tr069_dp_312() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(VALUE_CHANGE, true)
                .addTask("Backup")
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTask("Backup");
    }

    @Test
    public void tr069_dp_313() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(VALUE_CHANGE, true)
                .addTask("Restore")
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTask("Restore");
    }

    @Test//bug: there's no such diagnostic
    public void tr069_dp_314() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Management")
                .setParametersMonitor(VALUE_CHANGE, true)
                .addTask("Diagnostics")
                .selectDiagnostic("Trace diagnostics")
                .inputHost("8.8.8.8")
                .inputNumOfRepetitions("3")
                .saveTaskButton()
                .saveTaskButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .validateParametersMonitor()
                .validateAddedMonitorTask("Trace diagnostics", "8.8.8.8");
    }

    @Test
    public void tr069_dp_315() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Management")
                .setPolicy(1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_316() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Management")
                .setPolicy(2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_317() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Management")
                .setPolicy(4)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_318() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Management")
                .setPolicy(3)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_319() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Information")
                .setPolicy(1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_320() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Information")
                .setPolicy(2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_321() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Information")
                .setPolicy(4)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_322() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Information")
                .setPolicy(3)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_323() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Management")
                .setPolicy(1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_324() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Management")
                .setPolicy(2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_325() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Management")
                .setPolicy(4)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_326() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Management")
                .setPolicy(3)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_327() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DSL settings")
                .setPolicy(1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_328() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DSL settings")
                .setPolicy(2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_329() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DSL settings")
                .setPolicy(4)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_330() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DSL settings")
                .setPolicy(3)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_331() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("WAN")
                .setPolicy(1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_332() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("WAN")
                .setPolicy(2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_333() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("WAN")
                .setPolicy(4)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_334() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("WAN")
                .setPolicy(3)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_335() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("LAN")
                .setPolicy(1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_336() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("LAN")
                .setPolicy(2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_337() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("LAN")
                .setPolicy(4)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_338() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("LAN")
                .setPolicy(3)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_339() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Wireless")
                .setPolicy(1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_340() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Wireless")
                .setPolicy(2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_341() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Wireless")
                .setPolicy(4)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_342() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Wireless")
                .setPolicy(3)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_343() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("VoIP settings")
                .setPolicy(1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_344() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("VoIP settings")
                .setPolicy(2)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_345() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("VoIP settings")
                .setPolicy(4)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_346() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("VoIP settings")
                .setPolicy(3)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test//depends on 346
    public void tr069_dp_347() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr069_dp_346")
                .selectMainTab("Policy")
                .selectTab("VoIP settings")
                .setPolicy(4)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr069_dp_346")
                .selectMainTab("Summary")
                .expandPolicy()
                .validatePolicy();
    }

    @Test
    public void tr069_dp_348() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Download file")
                .downloadFromListFile("Firmware Image")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test
    public void tr069_dp_349() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Download file")
                .downloadManualImageFile("Firmware Image")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test//depends on 349
    public void tr069_dp_350() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr069_dp_349")
                .selectMainTab("Download file")
                .editFileEntry()
                .downloadFromListFile("Firmware Image")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr069_dp_349")
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test
    public void tr069_dp_351() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Download file")
                .downloadFromListFile("Vendor Configuration File")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test
    public void tr069_dp_352() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Download file")
                .downloadManualImageFile("Vendor Configuration File")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test   //Depends on 352
    public void tr069_dp_353() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr069_dp_352")
                .selectMainTab("Download file")
                .editFileEntry()
                .downloadFromListFile("Vendor Configuration File")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr069_dp_352")
                .selectMainTab("Download file")
                .validateDownloadFile();
    }

    @Test
    public void tr069_dp_354() {//duplicate with 027
        dpPage
                .presetFilter("Zip", "61000")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "61")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "61");
    }

    @Test   //depends on 354
    public void tr069_dp_355() {//duplicate with 028
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "62")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(false, "PeriodicInformInterval, sec", "62");
    }

    @Test   //depends on 354
    public void tr069_dp_356() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "63")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "63");
    }

    @Test
    public void tr069_dp_357() {
        dpPage
                .presetFilter("User location", "USA")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "64")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "64");
    }

    @Test   //depends on 357
    public void tr069_dp_358() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "65")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "65");
    }

    @Test   //depends on 357
    public void tr069_dp_359() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "66")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "66");
    }

    @Test
    public void tr069_dp_360() {
        dpPage
                .presetFilter("User tag", "Egorych")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("=")
                .fillValue("Egorych")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "67")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "67");
    }

    @Test   //depends on 360
    public void tr069_dp_361() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("!=")
                .fillValue("Zheka")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "68")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "68");
    }

    @Test   //depends on 360
    public void tr069_dp_362() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("Regexp")
                .fillValue(".+ryc.$")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "69")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "69");
    }

    @Test
    public void tr069_dp_363() {
        dpPage
                .presetFilter("User ID", "245")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "70")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "70");
    }

    @Test   //depends on 363
    public void tr069_dp_364() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "71")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "71");
    }

    @Test   //depends on 363
    public void tr069_dp_365() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "72")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "72");
    }

    @Test
    public void tr069_dp_366() {
        dpPage
                .presetFilter("User status", "online")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "60")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "60");
    }

    @Test   //depends on 366
    public void tr069_dp_367() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "61")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "61");
    }

    @Test   //depends on 366
    public void tr069_dp_368() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "62")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "62");
    }

    @Test//bug: parameter "DeviceSummary" is absent in device tree!
    public void tr069_dp_369() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceSummary")
                .selectConditionTypeComboBox("=")
                .fillValue("parameter is absent")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "63")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "63");
    }

    @Test//bug: parameter "DeviceSummary" is absent in device tree!
    public void tr069_dp_370() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceSummary")
                .selectConditionTypeComboBox("!=")
                .fillValue("wrong")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "64")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "64");
    }

    @Test//bug: parameter "DeviceSummary" is absent in device tree!
    public void tr069_dp_371() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceSummary")
                .selectConditionTypeComboBox("Regexp")
                .fillValue(".?")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "65")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "65");
    }

    @Test
    public void tr069_dp_372() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.SpecVersion")
                .selectConditionTypeComboBox("=")
                .fillValue("1.0")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "63")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "63");
    }

    @Test
    public void tr069_dp_373() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.SpecVersion")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("\\d\\.\\d")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "64")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "64");
    }

    @Test
    public void tr069_dp_374() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.SpecVersion")
                .selectConditionTypeComboBox("!=")
                .fillValue("21.0")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "65")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "65");
    }

    @Test
    public void tr069_dp_375() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.HardwareVersion")
                .selectConditionTypeComboBox("=")
                .fillValue("111")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "66")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "66");
    }

    @Test
    public void tr069_dp_376() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.HardwareVersion")
                .selectConditionTypeComboBox("!=")
                .fillValue("222")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "67")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "67");
    }

    @Test
    public void tr069_dp_377() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.HardwareVersion")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("\\d{3}")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "68")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "68");
    }

    @Test
    public void tr069_dp_378() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.SoftwareVersion")
                .selectConditionTypeComboBox("=")
                .fillValue("3.4.1_p052_build_25")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "69")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "69");
    }

    @Test
    public void tr069_dp_379() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.SoftwareVersion")
                .selectConditionTypeComboBox("!=")
                .fillValue("incorrect")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "70")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "70");
    }

    @Test
    public void tr069_dp_380() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.SoftwareVersion")
                .selectConditionTypeComboBox("Regexp")
                .fillValue(".+build_25$")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "71")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "71");
    }

    @Test
    public void tr069_dp_381() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.ProvisioningCode")
                .selectConditionTypeComboBox("=")
                .fillValue("FTW!!!")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "69")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "69");
    }

    @Test
    public void tr069_dp_382() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.ProvisioningCode")
                .selectConditionTypeComboBox("!=")
                .fillValue("incorrect")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "70")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "70");
    }

    @Test
    public void tr069_dp_383() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .bottomMenu(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.ProvisioningCode")
                .selectConditionTypeComboBox("Regexp")
                .fillValue(".{6}")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "71")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "71");
    }

    @Test
    public void tr069_dp_384() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
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
                .selectInformComboBox("InternetGatewayDevice.DeviceInfo.SoftwareVersion")
                .selectConditionTypeComboBox("=")
                .fillValue("3.4.1_p052_build_25")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "72")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .validateTargetDevice(true, "PeriodicInformInterval, sec", "72");
    }

    @Test  //depends on 384
    public void tr069_dp_385() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr069_dp_384")
                .editConditionButton()
                .bottomMenu(NEXT)
                .selectFilterItem(1)
                .deleteFilter()
                .okButtonPopUp()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, "tr069_dp_384");
    }

    @Test  //depends on 384
    public void tr069_dp_386() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr069_dp_384")
                .editConditionButton()
                .bottomMenu(DELETE_CONDITION)
                .okButtonPopUp()
                .assertButtonIsEnabled(false, "btnEditView_btn")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, "tr069_dp_384");
    }

    @Test
    public void tr069_dp_387() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .fullRequestRadioButton()
                .applyProvisionRadioButton()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName());
    }

    @Test
    public void tr069_dp_388() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .fullRequestRadioButton()
                .applyProvisionRadioButton()
                .performDeviceCheckbox()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName());
    }

    @Test
    public void tr069_dp_389() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
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
//    public void tr069_dp_999() {
//        dpPage
//                .topMenu(DEVICE_PROFILE)
//                .deleteAllProfiles();
//    }
}
