package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.entities.Event;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.Condition.*;
import static com.friendly.aqa.entities.ParameterType.VALUE;
import static com.friendly.aqa.entities.TopMenu.DEVICE_PROFILE;
import static com.friendly.aqa.pageobject.DeviceProfilePage.GlobalButtons.*;
import static com.friendly.aqa.pageobject.DeviceProfilePage.Left.NEW;
import static com.friendly.aqa.pageobject.DeviceProfilePage.Left.VIEW;

@Listeners(UniversalVideoListener.class)
public class DeviceProfileTR181Tests extends BaseTestCase {

    @Test
    public void tr181_dp_001() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .assertMainPageIsDisplayed()
                .assertTableHasContent("tblItems");
    }

    @Test
    public void tr181_dp_002() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoAnyProfile()
                .leftMenu(VIEW)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr181_dp_003() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .assertPresenceOfOptions("ddlManufacturer", "All", "DEFAULT")
                .assertPresenceOfOptions("ddlModelName", "All", "DEFAULT")
                .assertPresenceOfOptions("ddlUpdateStatus", "All", "Active", "Not Active");
    }

    @Test   //Bug: sorting by "Creator' failed!
    public void tr181_dp_004() {
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
    public void tr181_dp_005() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .checkFilteringByStatus();
    }

    @Test
    public void tr181_dp_006() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .checkFilteringByModelName();
    }

    @Test
    public void tr181_dp_007() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .checkFilteringByManufacturer();
    }

    @Test
    public void tr181_dp_008() {
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
    public void tr181_dp_009() {
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
    public void tr181_dp_010() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectManufacturer()
                .selectModel()
                .selectProfileStatus("All")
                .assertColumnContainsValue("State", "Active")
                .assertColumnContainsValue("State", "Not active");
    }

    @Test
    public void tr181_dp_011() {
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
    public void tr181_dp_012() {
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
    public void tr181_dp_013() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectProfileStatus("Not Active")
                .selectItem("Not active")
                .selectProfileStatus("All")
                .assertItemIsSelected();
    }

    @Test
    public void tr181_dp_014() {
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
    public void tr181_dp_015() {
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
    public void tr181_dp_016() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .getExport();
    }

    @Test//swapped with 16
    public void tr181_dp_017() {
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
    public void tr181_dp_018() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .assertButtonsAreEnabled(false, SAVE, SAVE_AND_ACTIVATE)
                .fillName()
                .pause(1000)
                .assertButtonsAreEnabled(true, SAVE_AND_ACTIVATE, SAVE, CANCEL)
                .globalButtons(CANCEL)
                .assertMainPageIsDisplayed()
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void tr181_dp_019() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .pause(1000)
                .addDeviceWithoutTemplate()
                .assertElementIsPresent("lblTemplateNotFound")  //button "Cancel" is absent (STD contradiction)
                .selectManufacturer()
                .selectModel()
                .globalButtons(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void tr181_dp_020() {
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
    public void tr181_dp_021() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .globalButtons(ADVANCED_VIEW)
//                .selectBranch("ManagementServer")
                .setParameter("PeriodicInformInterval, sec", "70")
                .globalButtons(SIMPLE_VIEW)
                .checkParameter("PeriodicInformInterval, sec", "70")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .assertMainPageIsDisplayed()
                .selectProfileStatus("All")
                .assertProfileIsActive(false, getTestName());
    }

    @Test
    public void tr181_dp_022() {
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
                .globalButtons(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void tr181_dp_023() {
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
                .globalButtons(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void tr181_dp_024() {
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
                .globalButtons(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void tr181_dp_025() {
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
                .assertButtonIsActive(true, "btnEditView_btn")
                .selectCondition(0)
                .assertButtonIsActive(false, "btnEditView_btn")
                .assertButtonsAreEnabled(true, SAVE, SAVE_AND_ACTIVATE, CANCEL)
                .globalButtons(CANCEL)
                .assertProfileIsPresent(false, getTestName());
    }

    @Test
    public void tr181_dp_026() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "60")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .assertProfileIsPresent(true, getTestName());
    }

    @Test
    public void tr181_dp_027() {
        dpPage
                .presetFilter("Zip", "61000")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("=")
                .fillValue("61000")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "61")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "61");
    }

    @Test
    public void tr181_dp_028() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("!=")
                .fillValue("61000")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "62")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(false, "PeriodicInformInterval, sec", "62");
    }

    @Test   // depends on 028
    public void tr181_dp_029() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectCondition("tr181_dp_028")
                .editConditionButton()
                .assertButtonsAreEnabled(false, DELETE_CONDITION);
    }

    @Test
    public void tr181_dp_030() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_031() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Information", 1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_032() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Time", 1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_033() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("WiFi", 1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_034() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("IP", 1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_035() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Firewall", 1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_036() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("DHCPv4", 1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_037() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("DHCPv6", 1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_038() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("DNS", 1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_039() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Users", 1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_040() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Ethernet", 1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_041() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Management", 2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_042() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Information", 2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_043() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Time", 2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_044() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("WiFi", 2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_045() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("IP", 2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_046() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Firewall", 2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_047() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("DHCPv4", 2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_048() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("DHCPv6", 2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_049() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("DNS", 2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_050() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Users", 2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_051() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Ethernet", 2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_052() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Management", 99)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_053() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Information", 99)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_054() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Time", 99)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_055() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("WiFi", 99)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_056() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("IP", 99)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_057() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Firewall", 99)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_058() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("DHCPv4", 99)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_059() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("DHCPv6", 99)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_060() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("DNS", 99)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_061() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Users", 99)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_062() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .setParameter("Ethernet", 99)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_063() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr181_dp_062")
                .setParameter("WiFi", 99)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr181_dp_062")
                .checkParameters();
    }

    @Test
    public void tr181_dp_064() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Parameters")
                .selectTab("WiFi")
                .selectTreeObject(true)
                .assertParametersAreSelected(true)
                .selectAnotherTreeObject(true)
                .assertParametersAreSelected(true)
                .selectTreeObject(true)
                .assertParametersAreSelected(false);
    }

    @Test
    public void tr181_dp_065() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Parameters")
                .selectTab("WiFi")
                .selectTreeObject(true, 1)
                .assertParametersAreSelected(true)
                .selectTreeObject(true, 2)
                .assertParametersAreSelected(true);
    }

    @Test
    public void tr181_dp_066() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Download file")
                .downloadManualImageFile("Firmware Image")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, testName);
    }

    @Test
    public void tr181_dp_067() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_068() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_069() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_070() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("WiFi", 1)
                .setAnotherTabParameter(1, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_071() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("IP", 1)
                .setAnotherTabParameter(1, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_072() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Firewall", 1)
                .setAnotherTabParameter(1, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_073() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("DHCPv4", 1)
                .setAnotherTabParameter(1, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_074() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("DHCPv6", 1)
                .setAnotherTabParameter(1, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_075() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("DNS", 1)
                .setAnotherTabParameter(1, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_076() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Users", 1)
                .setAnotherTabParameter(1, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_077() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Ethernet", 1)
                .setAnotherTabParameter(1, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_078() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_079() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_080() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_081() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("WiFi", 1)
                .setAnotherTabParameter(1, true)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_082() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("IP", 1)
                .setAnotherTabParameter(1, true)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_083() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Firewall", 1)
                .setAnotherTabParameter(1, true)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_084() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("DHCPv4", 1)
                .setAnotherTabParameter(1, true)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_085() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("DHCPv6", 1)
                .setAnotherTabParameter(1, true)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_086() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("DNS", 1)
                .setAnotherTabParameter(1, true)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_087() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Users", 1)
                .setAnotherTabParameter(1, true)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_088() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Ethernet", 1)
                .setAnotherTabParameter(1, true)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_089() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_090() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_091() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_092() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("WiFi", 1)
                .setAnotherTabParameter(99, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_093() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("IP", 1)
                .setAnotherTabParameter(99, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_094() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Firewall", 1)
                .setAnotherTabParameter(99, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_095() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("DHCPv4", 1)
                .setAnotherTabParameter(99, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_096() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("DHCPv6", 1)
                .setAnotherTabParameter(99, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_097() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("DNS", 1)
                .setAnotherTabParameter(99, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_098() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Users", 1)
                .setAnotherTabParameter(99, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_099() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .addSummaryParameters()
                .setParameter("Ethernet", 1)
                .setAnotherTabParameter(99, false)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .checkParameters();
    }

    @Test
    public void tr181_dp_100() {
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
    public void tr181_dp_101() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", null, "5", null))
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }

    @Test
//bug:set count of Events to any int, set count of Events to zero, set count of Events to any int, set duration to hours => saved minutes
    public void tr181_dp_102() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }

    @Test
    public void tr181_dp_103() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvent(new Event("1 BOOT", true, null, null))
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }

    @Test
    public void tr181_dp_104() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }

    @Test
    public void tr181_dp_105() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvents(2, new Event("EXAMPLE", false, "5", "8:hours"))
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }

    @Test
    public void tr181_dp_106() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Events")
                .setEvents(99, new Event("EXAMPLE", false, "2", "8:hours"))
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }

    @Test   //bug: editing profile events causes an erase of all events.
    public void tr181_dp_107() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr181_dp_106")
                .selectMainTab("Events")
                .disableAllEvents()
                .setEvent(new Event("2 PERIODIC", null, "5", null))
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr181_dp_106")
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }

    @Test
    public void tr181_dp_108() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("1 BOOT");
    }

    @Test
    public void tr181_dp_109() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("1 BOOT", "Vendor Configuration File", props.getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr181_dp_110() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("1 BOOT", "Action", "Reboot");
    }

    @Test
    public void tr181_dp_111() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("1 BOOT", "Action", "Factory reset");
    }

    @Test//bug: reprovision RB is missing;
    public void tr181_dp_112() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("1 BOOT", "Action", "Device Reprovision");
    }

    @Test
    public void tr181_dp_113() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("1 BOOT", "Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr181_dp_114() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("1 BOOT", "Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr181_dp_115() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("1 BOOT", "Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr181_dp_116() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("1 BOOT", "Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr181_dp_117() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("1 BOOT", "Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr181_dp_118() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("1 BOOT", "Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr181_dp_119() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("1 BOOT", "Custom RPC", "AddObject");
    }

    @Test
    public void tr181_dp_120() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("1 BOOT", "Custom RPC", "DeleteObject");
    }

    @Test
    public void tr181_dp_121() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("1 BOOT", "Custom RPC", "Download");
    }

    @Test
    public void tr181_dp_122() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("1 BOOT", "Custom RPC", "Reboot");
    }

    @Test
    public void tr181_dp_123() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("1 BOOT", "Custom RPC", "FactoryReset");
    }

    @Test
    public void tr181_dp_124() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("1 BOOT", "Custom RPC", "Upload");
    }

    @Test
    public void tr181_dp_125() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("2 PERIODIC");
    }

    @Test
    public void tr181_dp_126() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Vendor Configuration File", props.getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr181_dp_127() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("2 PERIODIC", "Action", "Reboot");
    }

    @Test
    public void tr181_dp_128() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("2 PERIODIC", "Action", "Factory Reset");
    }

    @Test//bug: reprovision RB is missing;
    public void tr181_dp_129() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("2 PERIODIC", "Action", "Device Reprovision");
    }

    @Test
    public void tr181_dp_130() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr181_dp_131() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr181_dp_132() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr181_dp_133() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr181_dp_134() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr181_dp_135() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr181_dp_136() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Custom RPC", "AddObject");
    }

    @Test
    public void tr181_dp_137() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Custom RPC", "DeleteObject");
    }

    @Test
    public void tr181_dp_138() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Custom RPC", "Download");
    }

    @Test
    public void tr181_dp_139() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Custom RPC", "Reboot");
    }

    @Test
    public void tr181_dp_140() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Custom RPC", "FactoryReset");
    }

    @Test
    public void tr181_dp_141() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Custom RPC", "Upload");
    }

    @Test
    public void tr181_dp_142() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("4 VALUE CHANGE");
    }

    @Test
    public void tr181_dp_143() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("4 VALUE CHANGE", "Vendor Configuration File", props.getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr181_dp_144() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("4 VALUE CHANGE", "Action", "Reboot");
    }

    @Test
    public void tr181_dp_145() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("4 VALUE CHANGE", "Action", "Factory Reset");
    }

    @Test//bug: reprovision RB is missing;
    public void tr181_dp_146() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("4 VALUE CHANGE", "Action", "Device Reprovision");
    }

    @Test
    public void tr181_dp_147() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("4 VALUE CHANGE", "Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr181_dp_148() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("4 VALUE CHANGE", "Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr181_dp_149() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("4 VALUE CHANGE", "Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr181_dp_150() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("4 VALUE CHANGE", "Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr181_dp_151() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("4 VALUE CHANGE", "Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr181_dp_152() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("4 VALUE CHANGE", "Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr181_dp_153() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("4 VALUE CHANGE", "Custom RPC", "AddObject");
    }

    @Test
    public void tr181_dp_154() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("4 VALUE CHANGE", "Custom RPC", "DeleteObject");
    }

    @Test
    public void tr181_dp_155() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("4 VALUE CHANGE", "Custom RPC", "Download");
    }

    @Test
    public void tr181_dp_156() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("4 VALUE CHANGE", "Custom RPC", "Reboot");
    }

    @Test
    public void tr181_dp_157() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("4 VALUE CHANGE", "Custom RPC", "FactoryReset");
    }

    @Test
    public void tr181_dp_158() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("4 VALUE CHANGE", "Custom RPC", "Upload");
    }

    @Test
    public void tr181_dp_159() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_160() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("6 CONNECTION REQUEST", "Vendor Configuration File", props.getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr181_dp_161() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("6 CONNECTION REQUEST", "Action", "Reboot");
    }

    @Test
    public void tr181_dp_162() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("6 CONNECTION REQUEST", "Action", "Factory Reset");
    }

    @Test//bug: reprovision RB is missing;
    public void tr181_dp_163() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventAction("6 CONNECTION REQUEST", "Action", "Device Reprovision");
    }

    @Test
    public void tr181_dp_164() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr181_dp_165() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr181_dp_166() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr181_dp_167() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr181_dp_168() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr181_dp_169() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr181_dp_170() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "AddObject");
    }

    @Test
    public void tr181_dp_171() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "DeleteObject");
    }

    @Test
    public void tr181_dp_172() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "Download");
    }

    @Test
    public void tr181_dp_173() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "Reboot");
    }

    @Test
    public void tr181_dp_174() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "FactoryReset");
    }

    @Test
    public void tr181_dp_175() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("6 CONNECTION REQUEST", "Custom RPC", "Upload");
    }

    @Test
    public void tr181_dp_176() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_177() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_178() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_179() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_180() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_181() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_182() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_183() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_184() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_185() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_186() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_187() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_188() {
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
                .selectTab("WiFi")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_189() {
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
                .selectTab("WiFi")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_190() {
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
                .selectTab("WiFi")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_191() {
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
                .selectTab("WiFi")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_192() {
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
                .selectTab("IP")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_193() {
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
                .selectTab("IP")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_194() {
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
                .selectTab("IP")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_195() {
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
                .selectTab("IP")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_196() {
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
                .selectTab("Firewall")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_197() {
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
                .selectTab("Firewall")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_198() {
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
                .selectTab("Firewall")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_199() {
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
                .selectTab("Firewall")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_200() {
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
                .selectTab("DHCPv4")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_201() {
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
                .selectTab("DHCPv4")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_202() {
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
                .selectTab("DHCPv4")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_203() {
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
                .selectTab("DHCPv4")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_204() {
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
                .selectTab("DHCPv6")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_205() {
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
                .selectTab("DHCPv6")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_206() {
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
                .selectTab("DHCPv6")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_207() {
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
                .selectTab("DHCPv6")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_208() {
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
                .selectTab("DNS")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_209() {
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
                .selectTab("DNS")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_210() {
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
                .selectTab("DNS")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_211() {
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
                .selectTab("DNS")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_212() {
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
                .selectTab("Users")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_213() {
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
                .selectTab("Users")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_214() {
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
                .selectTab("Users")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_215() {
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
                .selectTab("Users")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_216() {
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
                .selectTab("Ethernet")
                .setTaskPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_217() {
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
                .selectTab("Ethernet")
                .setTaskPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_218() {
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
                .selectTab("Ethernet")
                .setTaskPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_219() {
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
                .selectTab("Ethernet")
                .setTaskPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_220() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("1 BOOT", "Vendor Configuration File", props.getProperty("upload_url"));
    }

    @Test
    public void tr181_dp_221() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Vendor Configuration File", props.getProperty("upload_url"));
    }

    @Test
    public void tr181_dp_222() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("4 VALUE CHANGE", "Vendor Configuration File", props.getProperty("upload_url"));
    }

    @Test
    public void tr181_dp_223() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("6 CONNECTION REQUEST", "Vendor Configuration File", props.getProperty("upload_url"));
    }

    @Test
    public void tr181_dp_224() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_225() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_226() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_227() {
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
                .selectTab("WiFi")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_228() {
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
                .selectTab("IP")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_229() {
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
                .selectTab("Firewall")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_230() {
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
                .selectTab("DHCPv4")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_231() {
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
                .selectTab("DHCPv6")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_232() {
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
                .selectTab("DNS")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_233() {
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
                .selectTab("Users")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_234() {
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
                .selectTab("Ethernet")
                .getParameter(1, 1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_235() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_236() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_237() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_238() {
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
                .selectTab("WiFi")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_239() {
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
                .selectTab("IP")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_240() {
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
                .selectTab("Firewall")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_241() {
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
                .selectTab("DHCPv4")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_242() {
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
                .selectTab("DHCPv6")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_243() {
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
                .selectTab("DNS")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_244() {
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
                .selectTab("Users")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_245() {
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
                .selectTab("Ethernet")
                .getParameter(1, 2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_246() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_247() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_248() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_249() {
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
                .selectTab("WiFi")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_250() {
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
                .selectTab("IP")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_251() {
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
                .selectTab("Firewall")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_252() {
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
                .selectTab("DHCPv4")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_253() {
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
                .selectTab("DHCPv6")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_254() {
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
                .selectTab("DNS")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_255() {
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
                .selectTab("Users")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_256() {
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
                .selectTab("Ethernet")
                .getParameter(1, 3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_257() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_258() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_259() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_260() {
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
                .selectTab("WiFi")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_261() {
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
                .selectTab("IP")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_262() {
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
                .selectTab("Firewall")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_263() {
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
                .selectTab("DHCPv4")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_264() {
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
                .selectTab("DHCPv6")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_265() {
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
                .selectTab("DNS")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test   //Bug: Parameter name isn't displayed into result table;
    public void tr181_dp_266() {
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
                .selectTab("Users")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_267() {
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
                .selectTab("Ethernet")
                .getParameter(1, 0)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_268() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Backup");
    }

    @Test//bug: editing profile events causes an erase of all events.
    public void tr181_dp_269() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr181_dp_268")
                .expandEvents()
                .editTask("2 PERIODIC")
                .deleteTask("Backup")
                .assertTaskIsAbsent("Backup")
                .saveTaskButton()
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr181_dp_268")
                .selectMainTab("Summary")
                .expandEvents()
                .editTask("2 PERIODIC")
                .assertTaskIsAbsent("Backup");
    }

    @Test
    public void tr181_dp_270() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Restore");
    }

    @Test//bug: editing profile events causes an erase of all events.
    public void tr181_dp_271() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr181_dp_270")
                .expandEvents()
                .editTask("2 PERIODIC")
                .deleteTask("Restore")
                .assertTaskIsAbsent("Restore")
                .saveTaskButton()
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr181_dp_270")
                .selectMainTab("Summary")
                .expandEvents()
                .editTask("2 PERIODIC")
                .assertTaskIsAbsent("Restore");
    }

    @Test//bug: there's no such diagnostic
    public void tr181_dp_272() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Trace diagnostic", "8.8.8.8");
    }

    @Test
    public void tr181_dp_273() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Diagnostics");
    }

    @Test
    public void tr181_dp_274() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Diagnostics");
    }

    @Test//bug: there's no such diagnostic
    public void tr181_dp_275() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "Wi-Fi neighboring diagnostic", " ");
    }

    @Test
    public void tr181_dp_276() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "IPPing diagnostics", "8.8.8.8");
    }

    @Test//bug: there's no such diagnostic
    public void tr181_dp_277() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedEventTask("2 PERIODIC", "NSLoopback diagnostics", "127.0.0.1");
    }

    @Test
    public void tr181_dp_278() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_279() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_280() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_281() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_282() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_283() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_284() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_285() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_286() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_287() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_288() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_289() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_290() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_291() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_292() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_293() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_294() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_295() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_296() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_297() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_298() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_299() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_300() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_301() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_302() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_303() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_304() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_305() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WiFi")
                .setParametersMonitor(CONTAINS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_306() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WiFi")
                .setParametersMonitor(EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_307() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WiFi")
                .setParametersMonitor(GREATER)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_308() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WiFi")
                .setParametersMonitor(GREATER_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_309() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WiFi")
                .setParametersMonitor(LESS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_310() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WiFi")
                .setParametersMonitor(LESS_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_311() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WiFi")
                .setParametersMonitor(NOT_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_312() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WiFi")
                .setParametersMonitor(STARTS_WITH)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_313() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("WiFi")
                .setParametersMonitor(VALUE_CHANGE)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_314() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("IP")
                .setParametersMonitor(CONTAINS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_315() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("IP")
                .setParametersMonitor(EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_316() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("IP")
                .setParametersMonitor(GREATER)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_317() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("IP")
                .setParametersMonitor(GREATER_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_318() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("IP")
                .setParametersMonitor(LESS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_319() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("IP")
                .setParametersMonitor(LESS_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_320() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("IP")
                .setParametersMonitor(NOT_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_321() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("IP")
                .setParametersMonitor(STARTS_WITH)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_322() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("IP")
                .setParametersMonitor(VALUE_CHANGE)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_323() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Firewall")
                .setParametersMonitor(CONTAINS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_324() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Firewall")
                .setParametersMonitor(EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_325() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Firewall")
                .setParametersMonitor(GREATER)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_326() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Firewall")
                .setParametersMonitor(GREATER_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_327() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Firewall")
                .setParametersMonitor(LESS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_328() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Firewall")
                .setParametersMonitor(LESS_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_329() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Firewall")
                .setParametersMonitor(NOT_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_330() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Firewall")
                .setParametersMonitor(STARTS_WITH)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_331() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Firewall")
                .setParametersMonitor(VALUE_CHANGE)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_332() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv4")
                .setParametersMonitor(CONTAINS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_333() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv4")
                .setParametersMonitor(EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_334() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv4")
                .setParametersMonitor(GREATER)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_335() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv4")
                .setParametersMonitor(GREATER_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_336() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv4")
                .setParametersMonitor(LESS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_337() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv4")
                .setParametersMonitor(LESS_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_338() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv4")
                .setParametersMonitor(NOT_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_339() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv4")
                .setParametersMonitor(STARTS_WITH)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_340() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv4")
                .setParametersMonitor(VALUE_CHANGE)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_341() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv6")
                .setParametersMonitor(CONTAINS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_342() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv6")
                .setParametersMonitor(EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_343() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv6")
                .setParametersMonitor(GREATER)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_344() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv6")
                .setParametersMonitor(GREATER_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_345() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv6")
                .setParametersMonitor(LESS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_346() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv6")
                .setParametersMonitor(LESS_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_347() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv6")
                .setParametersMonitor(NOT_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_348() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv6")
                .setParametersMonitor(STARTS_WITH)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_349() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DHCPv6")
                .setParametersMonitor(VALUE_CHANGE)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_350() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DNS")
                .setParametersMonitor(CONTAINS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_351() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DNS")
                .setParametersMonitor(EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_352() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DNS")
                .setParametersMonitor(GREATER)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_353() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DNS")
                .setParametersMonitor(GREATER_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_354() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DNS")
                .setParametersMonitor(LESS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_355() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DNS")
                .setParametersMonitor(LESS_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_356() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DNS")
                .setParametersMonitor(NOT_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_357() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DNS")
                .setParametersMonitor(STARTS_WITH)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_358() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("DNS")
                .setParametersMonitor(VALUE_CHANGE)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_359() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Users")
                .setParametersMonitor(CONTAINS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_360() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Users")
                .setParametersMonitor(EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_361() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Users")
                .setParametersMonitor(GREATER)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_362() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Users")
                .setParametersMonitor(GREATER_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_363() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Users")
                .setParametersMonitor(LESS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_364() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Users")
                .setParametersMonitor(LESS_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_365() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Users")
                .setParametersMonitor(NOT_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_366() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Users")
                .setParametersMonitor(STARTS_WITH)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_367() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Users")
                .setParametersMonitor(VALUE_CHANGE)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_368() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Ethernet")
                .setParametersMonitor(CONTAINS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_369() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Ethernet")
                .setParametersMonitor(EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_370() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Ethernet")
                .setParametersMonitor(GREATER)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_371() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Ethernet")
                .setParametersMonitor(GREATER_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_372() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Ethernet")
                .setParametersMonitor(LESS)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_373() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Ethernet")
                .setParametersMonitor(LESS_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_374() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Ethernet")
                .setParametersMonitor(NOT_EQUAL)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_375() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Ethernet")
                .setParametersMonitor(STARTS_WITH)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_376() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Monitoring")
                .selectEventTab("Ethernet")
                .setParametersMonitor(VALUE_CHANGE)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_377() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr181_dp_376")
                .selectMainTab("Monitoring")
                .selectEventTab("Ethernet")
                .setParametersMonitor(CONTAINS)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr181_dp_376")
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

    @Test
    public void tr181_dp_378() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .checkParametersMonitor()
                .checkAddedMonitorTasks();
    }

    @Test
    public void tr181_dp_379() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .checkParametersMonitor()
                .checkAddedMonitorTask("Vendor Configuration File", props.getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr181_dp_380() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .checkParametersMonitor()
                .checkAddedMonitorAction(null, "Action", "Reboot");
    }

    @Test
    public void tr181_dp_381() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .checkParametersMonitor()
                .checkAddedMonitorTasks();
    }

    @Test
    public void tr181_dp_382() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .checkParametersMonitor()
                .checkAddedMonitorTask("Vendor Configuration File", props.getProperty("upload_url"));
    }

    @Test
    public void tr181_dp_383() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .checkParametersMonitor()
                .checkAddedMonitorTasks();
    }

    @Test
    public void tr181_dp_384() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .checkParametersMonitor()
                .checkAddedMonitorTask("Backup");
    }

    @Test
    public void tr181_dp_385() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .checkParametersMonitor()
                .checkAddedMonitorTask("Restore");
    }

    @Test//bug: there's no such diagnostic
    public void tr181_dp_386() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandParametersMonitor()
                .checkParametersMonitor()
                .checkAddedMonitorTask("Trace diagnostics", "8.8.8.8");
    }

    @Test
    public void tr181_dp_387() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_388() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_389() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_390() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_391() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_392() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_393() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_394() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_395() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_396() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_397() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_398() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_399() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("WiFi")
                .setPolicy(1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_400() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("WiFi")
                .setPolicy(2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_401() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("WiFi")
                .setPolicy(4)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_402() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("WiFi")
                .setPolicy(3)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_403() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("IP")
                .setPolicy(1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_404() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("IP")
                .setPolicy(2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_405() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("IP")
                .setPolicy(4)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_406() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("IP")
                .setPolicy(3)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_407() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Firewall")
                .setPolicy(1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_408() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Firewall")
                .setPolicy(2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_409() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Firewall")
                .setPolicy(4)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_410() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Firewall")
                .setPolicy(3)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_411() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DHCPv4")
                .setPolicy(1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_412() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DHCPv4")
                .setPolicy(2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_413() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DHCPv4")
                .setPolicy(4)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_414() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DHCPv4")
                .setPolicy(3)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_415() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DHCPv6")
                .setPolicy(1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_416() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DHCPv6")
                .setPolicy(2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_417() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DHCPv6")
                .setPolicy(4)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_418() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DHCPv6")
                .setPolicy(3)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_419() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DNS")
                .setPolicy(1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_420() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DNS")
                .setPolicy(2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_421() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DNS")
                .setPolicy(4)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_422() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("DNS")
                .setPolicy(3)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_423() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Users")
                .setPolicy(1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_424() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Users")
                .setPolicy(2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_425() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Users")
                .setPolicy(4)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_426() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Users")
                .setPolicy(3)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_427() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Ethernet")
                .setPolicy(1)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_428() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Ethernet")
                .setPolicy(2)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_429() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Ethernet")
                .setPolicy(4)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_430() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Policy")
                .selectTab("Ethernet")
                .setPolicy(3)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test//depends on 430
    public void tr181_dp_431() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr181_dp_430")
                .selectMainTab("Policy")
                .selectTab("Ethernet")
                .setPolicy(4)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr181_dp_430")
                .selectMainTab("Summary")
                .expandPolicy()
                .checkPolicy();
    }

    @Test
    public void tr181_dp_432() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Download file")
                .downloadFromListFile("Firmware Image")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

    @Test
    public void tr181_dp_433() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Download file")
                .downloadManualImageFile("Firmware Image")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

    @Test//depends on 433
    public void tr181_dp_434() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr181_dp_433")
                .selectMainTab("Download file")
                .editFileEntry()
                .downloadFromListFile("Firmware Image")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr181_dp_433")
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

    @Test
    public void tr181_dp_435() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Download file")
                .downloadFromListFile("Vendor Configuration File")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

    @Test
    public void tr181_dp_436() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .setDefaultPeriodic(false)
                .selectMainTab("Download file")
                .downloadManualImageFile("Vendor Configuration File")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

    @Test
    public void tr181_dp_437() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr181_dp_436")
                .selectMainTab("Download file")
                .editFileEntry()
                .downloadFromListFile("Vendor Configuration File")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .enterIntoProfile("tr181_dp_436")
                .selectMainTab("Download file")
                .checkDownloadFile();
    }

    @Test
    public void tr181_dp_438() {//duplicate with 027
        dpPage
                .presetFilter("Zip", "61000")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("=")
                .fillValue("61000")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "61")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "61");
    }

    @Test   //depends on 438
    public void tr181_dp_439() {//duplicate with 028
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("!=")
                .fillValue("61000")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "62")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(false, "PeriodicInformInterval, sec", "62");
    }

    @Test   //depends on 438
    public void tr181_dp_440() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("^6.+$")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "63")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "63");
    }

    @Test
    public void tr181_dp_441() {
        dpPage
                .presetFilter("User location", "USA")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("location")
                .selectConditionTypeComboBox("=")
                .fillValue("USA")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "64")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "64");
    }

    @Test   //depends on 441
    public void tr181_dp_442() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("location")
                .selectConditionTypeComboBox("!=")
                .fillValue("Ukraine")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "65")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "65");
    }

    @Test   //depends on 441
    public void tr181_dp_443() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("location")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("^US.$")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "66")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "66");
    }

    @Test
    public void tr181_dp_444() {
        dpPage
                .presetFilter("User tag", "Egorych")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("=")
                .fillValue("Egorych")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "67")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "67");
    }

    @Test   //depends on 444
    public void tr181_dp_445() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("!=")
                .fillValue("Zheka")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "68")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "68");
    }

    @Test   //depends on 444
    public void tr181_dp_446() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("Regexp")
                .fillValue(".+ryc.$")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "69")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "69");
    }

    @Test
    public void tr181_dp_447() {
        dpPage
                .presetFilter("User ID", "245")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("userid")
                .selectConditionTypeComboBox("=")
                .fillValue("245")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "70")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "70");
    }

    @Test   //depends on 447
    public void tr181_dp_448() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("userid")
                .selectConditionTypeComboBox("!=")
                .fillValue("246")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "71")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "71");
    }

    @Test   //depends on 447
    public void tr181_dp_449() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("userid")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("\\d{3}")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "72")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "72");
    }

    @Test
    public void tr181_dp_450() {
        dpPage
                .presetFilter("User status", "online")
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("userstatus")
                .selectConditionTypeComboBox("=")
                .fillValue("online")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "60")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "60");
    }

    @Test   //depends on 450
    public void tr181_dp_451() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("userstatus")
                .selectConditionTypeComboBox("!=")
                .fillValue("offline")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "61")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "61");
    }

    @Test   //depends on 450
    public void tr181_dp_452() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("userstatus")
                .selectConditionTypeComboBox("Regexp")
                .fillValue(".{5}e")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "62")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "62");
    }

    @Test//bug: Parameter Device.DeviceSummary not found;
    public void tr181_dp_453() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("Device.DeviceSummary")
                .selectConditionTypeComboBox("=")
                .fillValue("")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "63")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "63");
    }

    @Test//bug: Parameter Device.DeviceSummary not found;
    public void tr181_dp_454() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("Device.DeviceSummary")
                .selectConditionTypeComboBox("!=")
                .fillValue("HC220-G1 v1 00000001")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "64")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "64");
    }

    @Test//bug: Parameter Device.DeviceSummary not found;
    public void tr181_dp_455() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("Device.DeviceSummary")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("HC220-G1 v1 0{8}$")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "65")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "65");
    }

    @Test
    public void tr181_dp_456() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("Device.DeviceInfo.HardwareVersion")
                .selectConditionTypeComboBox("=")
                .fillValue("HC220-G1 v1 00000000")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "66")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "66");
    }

    @Test
    public void tr181_dp_457() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("Device.DeviceInfo.HardwareVersion")
                .selectConditionTypeComboBox("!=")
                .fillValue("HC220-G1 v1 00000001")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "67")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "67");
    }

    @Test
    public void tr181_dp_458() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("Device.DeviceInfo.HardwareVersion")
                .selectConditionTypeComboBox("Regexp")
                .fillValue("HC220-G1 v1 0{8}$")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "68")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "68");
    }

    @Test
    public void tr181_dp_459() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("Device.DeviceInfo.SoftwareVersion")
                .selectConditionTypeComboBox("=")
                .fillValue("1.0.2 0.9 v6048.0 Build 190717 Rel.40046n")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "69")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "69");
    }

    @Test
    public void tr181_dp_460() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("Device.DeviceInfo.SoftwareVersion")
                .selectConditionTypeComboBox("!=")
                .fillValue("incorrect")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "70")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "70");
    }

    @Test
    public void tr181_dp_461() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("Device.DeviceInfo.SoftwareVersion")
                .selectConditionTypeComboBox("Regexp")
                .fillValue(".+40046n$")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "71")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "71");
    }

    @Test
    public void tr181_dp_462() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .newConditionButton()
                .fillConditionName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("Zip")
                .selectConditionTypeComboBox("=")
                .fillValue("61000")
                .globalButtons(NEXT)
                .addFilter()
                .informRadioButton()
                .selectInformComboBox("Device.DeviceInfo.SoftwareVersion")
                .selectConditionTypeComboBox("=")
                .fillValue("1.0.2 0.9 v6048.0 Build 190717 Rel.40046n")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "72")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "72");
    }

    @Test  //depends on 462
    public void tr181_dp_463() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr181_dp_462")
                .editConditionButton()
                .globalButtons(NEXT)
                .selectFilterItem(1)
                .deleteFilter()
                .okButtonPopUp()
                .globalButtons(FINISH)
                .okButtonPopUp()
                .globalButtons(SAVE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, "tr181_dp_462");
    }

    @Test  //depends on 462
    public void tr181_dp_464() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .enterIntoProfile("tr181_dp_462")
                .editConditionButton()
                .globalButtons(DELETE_CONDITION)
                .okButtonPopUp()
                .assertButtonIsActive(false, "btnEditView_btn")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, "tr181_dp_462");
    }

    @Test
    public void tr181_dp_465() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .fullRequestRadioButton()
                .applyProvisionRadioButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName());
    }

    @Test
    public void tr181_dp_466() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .fullRequestRadioButton()
                .applyProvisionRadioButton()
                .performDeviceCheckbox()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName());
    }

    @Test
    public void tr181_dp_467() {
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
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .assertProfileIsPresent(true, getTestName());
    }

//    @Test
//    public void tr181_dp_999() {
//        dpPage
//                .topMenu(DEVICE_PROFILE)
//                .deleteAllProfiles();
//    }
}
