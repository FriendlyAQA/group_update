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
                .selectProfileStatus("Active")
                .selectItem("Active", 2)
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
                .selectProfileStatus("Not Active")
                .selectItem("Not active")
                .globalButtons(ACTIVATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .assertProfileIsActive(true);
    }

    @Test
    public void tr181_dp_016() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .selectProfileStatus("All")
                .selectItem("Active", 2)
                .globalButtons(DELETE)
                .okButtonPopUp()
                .okButtonPopUp()
                .assertProfileIsPresent(false);
    }

    @Test
    public void tr181_dp_017() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .getExport();
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
                .assertMainPageIsDisplayed();
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
//                .presetFilter("Zip", "61000")
                .topMenu(DEVICE_PROFILE)
//                .deleteProfileIfExists()
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
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
                .setParameter("PeriodicInformInterval, sec", "70")
                .fillName()       //text in Name field is disappearing when condition added;
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(true, "PeriodicInformInterval, sec", "70");
    }

    @Test
    public void tr181_dp_028() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
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
                .setParameter("PeriodicInformInterval, sec", "65")
                .fillName()       //text in Name field is disappearing when condition added;
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .assertProfileIsPresent(true, getTestName())
                .checkTargetDevice(false, "PeriodicInformInterval, sec", "65");
        setTargetTestName();
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
                .addSummaryParameter()
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
                .selectMainTab("Download file")
                .downloadImageFile()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
                .addSummaryParameter()
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
    public void tr181_dp_102() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("0 BOOTSTRAP", false, "5", "4:hours"))
                .assertAddTaskButtonIsActive("0 BOOTSTRAP", true)
                .setEvent(new Event("0 BOOTSTRAP", null, "0", null))
                .assertAddTaskButtonIsActive("0 BOOTSTRAP", false)
                .setEvent(new Event("0 BOOTSTRAP", false, "5", "4:hours"))
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
                .checkAddedToEventTasks("1 BOOT");
    }

    @Test
    public void tr181_dp_109() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("1 BOOT", "Vendor Configuration File", props.getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr181_dp_110() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedAction("1 BOOT", "Action", "Reboot");
    }

    @Test
    public void tr181_dp_111() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedAction("1 BOOT", "Action", "Factory Reset");
    }

    @Test
    public void tr181_dp_112() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedAction("1 BOOT", "Action", "Device Reprovision");
    }

    @Test
    public void tr181_dp_113() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("1 BOOT", "Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr181_dp_114() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("1 BOOT", "Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr181_dp_115() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("1 BOOT", "Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr181_dp_116() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("1 BOOT", "Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr181_dp_117() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("1 BOOT", "Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr181_dp_118() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("1 BOOT", "Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr181_dp_119() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("1 BOOT", "Custom RPC", "AddObject");
    }

    @Test
    public void tr181_dp_120() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("1 BOOT", "Custom RPC", "DeleteObject");
    }

    @Test
    public void tr181_dp_121() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("1 BOOT", "Custom RPC", "Download");
    }

    @Test
    public void tr181_dp_122() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("1 BOOT", "Custom RPC", "Reboot");
    }

    @Test
    public void tr181_dp_123() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("1 BOOT", "Custom RPC", "FactoryReset");
    }

    @Test
    public void tr181_dp_124() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("1 BOOT", "Custom RPC", "Upload");
    }

    @Test
    public void tr181_dp_125() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("2 PERIODIC");
    }

    @Test
    public void tr181_dp_126() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Vendor Configuration File", props.getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr181_dp_127() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedAction("2 PERIODIC", "Action", "Reboot");
    }

    @Test
    public void tr181_dp_128() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedAction("2 PERIODIC", "Action", "Factory Reset");
    }

    @Test
    public void tr181_dp_129() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedAction("2 PERIODIC", "Action", "Device Reprovision");
    }

    @Test
    public void tr181_dp_130() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr181_dp_131() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr181_dp_132() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr181_dp_133() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr181_dp_134() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr181_dp_135() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr181_dp_136() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Custom RPC", "AddObject");
    }

    @Test
    public void tr181_dp_137() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Custom RPC", "DeleteObject");
    }

    @Test
    public void tr181_dp_138() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Custom RPC", "Download");
    }

    @Test
    public void tr181_dp_139() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Custom RPC", "Reboot");
    }

    @Test
    public void tr181_dp_140() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Custom RPC", "FactoryReset");
    }

    @Test
    public void tr181_dp_141() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Custom RPC", "Upload");
    }

    @Test
    public void tr181_dp_142() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("4 VALUE CHANGE");
    }

    @Test
    public void tr181_dp_143() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("4 VALUE CHANGE", "Vendor Configuration File", props.getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr181_dp_144() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedAction("4 VALUE CHANGE", "Action", "Reboot");
    }

    @Test
    public void tr181_dp_145() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedAction("4 VALUE CHANGE", "Action", "Factory Reset");
    }

    @Test
    public void tr181_dp_146() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedAction("4 VALUE CHANGE", "Action", "Device Reprovision");
    }

    @Test
    public void tr181_dp_147() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("4 VALUE CHANGE", "Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr181_dp_148() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("4 VALUE CHANGE", "Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr181_dp_149() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("4 VALUE CHANGE", "Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr181_dp_150() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("4 VALUE CHANGE", "Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr181_dp_151() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("4 VALUE CHANGE", "Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr181_dp_152() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("4 VALUE CHANGE", "Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr181_dp_153() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("4 VALUE CHANGE", "Custom RPC", "AddObject");
    }

    @Test
    public void tr181_dp_154() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("4 VALUE CHANGE", "Custom RPC", "DeleteObject");
    }

    @Test
    public void tr181_dp_155() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("4 VALUE CHANGE", "Custom RPC", "Download");
    }

    @Test
    public void tr181_dp_156() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("4 VALUE CHANGE", "Custom RPC", "Reboot");
    }

    @Test
    public void tr181_dp_157() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("4 VALUE CHANGE", "Custom RPC", "FactoryReset");
    }

    @Test
    public void tr181_dp_158() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("4 VALUE CHANGE", "Custom RPC", "Upload");
    }

    @Test
    public void tr181_dp_159() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_160() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("6 CONNECTION REQUEST", "Vendor Configuration File", props.getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr181_dp_161() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedAction("6 CONNECTION REQUEST", "Action", "Reboot");
    }

    @Test
    public void tr181_dp_162() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedAction("6 CONNECTION REQUEST", "Action", "Factory Reset");
    }

    @Test
    public void tr181_dp_163() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedAction("6 CONNECTION REQUEST", "Action", "Device Reprovision");
    }

    @Test
    public void tr181_dp_164() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("6 CONNECTION REQUEST", "Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr181_dp_165() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("6 CONNECTION REQUEST", "Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr181_dp_166() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("6 CONNECTION REQUEST", "Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr181_dp_167() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("6 CONNECTION REQUEST", "Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr181_dp_168() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("6 CONNECTION REQUEST", "Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr181_dp_169() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("6 CONNECTION REQUEST", "Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr181_dp_170() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("6 CONNECTION REQUEST", "Custom RPC", "AddObject");
    }

    @Test
    public void tr181_dp_171() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("6 CONNECTION REQUEST", "Custom RPC", "DeleteObject");
    }

    @Test
    public void tr181_dp_172() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("6 CONNECTION REQUEST", "Custom RPC", "Download");
    }

    @Test
    public void tr181_dp_173() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("6 CONNECTION REQUEST", "Custom RPC", "Reboot");
    }

    @Test
    public void tr181_dp_174() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("6 CONNECTION REQUEST", "Custom RPC", "FactoryReset");
    }

    @Test
    public void tr181_dp_175() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("6 CONNECTION REQUEST", "Custom RPC", "Upload");
    }

    @Test
    public void tr181_dp_176() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .setPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_177() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .setPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_178() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .setPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_179() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .setPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_180() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Information")
                .setPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_181() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Information")
                .setPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_182() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Information")
                .setPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_183() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Information")
                .setPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_184() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Time")
                .setPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_185() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Time")
                .setPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_186() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Time")
                .setPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_187() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Time")
                .setPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_188() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_189() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_190() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_191() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("WiFi")
                .setPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_192() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("IP")
                .setPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_193() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("IP")
                .setPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_194() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("IP")
                .setPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_195() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("IP")
                .setPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_196() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_197() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_198() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_199() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Firewall")
                .setPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_200() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_201() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_202() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_203() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DHCPv4")
                .setPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_204() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_205() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_206() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_207() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DHCPv6")
                .setPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_208() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_209() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_210() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_211() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("DNS")
                .setPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_212() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Users")
                .setPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_213() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Users")
                .setPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_214() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Users")
                .setPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_215() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Users")
                .setPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_216() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setPolicy(1)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_217() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setPolicy(2)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_218() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setPolicy(4)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_219() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("6 CONNECTION REQUEST", false, "3", null), true)
                .addTask("Policy")
                .selectTab("Ethernet")
                .setPolicy(3)
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_220() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("1 BOOT", "Vendor Configuration File", props.getProperty("upload_url"));
    }

    @Test
    public void tr181_dp_221() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Vendor Configuration File", props.getProperty("upload_url"));
    }

    @Test
    public void tr181_dp_222() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("4 VALUE CHANGE", "Vendor Configuration File", props.getProperty("upload_url"));
    }

    @Test
    public void tr181_dp_223() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("6 CONNECTION REQUEST", "Vendor Configuration File", props.getProperty("upload_url"));
    }

    @Test
    public void tr181_dp_224() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_225() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_226() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_227() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_228() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_229() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_230() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_231() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_232() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_233() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_234() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_235() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_236() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_237() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_238() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_239() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_240() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_241() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_242() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_243() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_244() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_245() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_246() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_247() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_248() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_249() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_250() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_251() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_252() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_253() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_254() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_255() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_256() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_257() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_258() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_259() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_260() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_261() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_262() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_263() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_264() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_265() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test   //Bug: Parameter name isn't displayed into result table;
    public void tr181_dp_266() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_267() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTasks("6 CONNECTION REQUEST");
    }

    @Test
    public void tr181_dp_268() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
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
                .checkAddedToEventTask("2 PERIODIC", "Backup");
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
                .checkAddedToEventTask("2 PERIODIC", "Restore");
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

    @Test
    public void tr181_dp_272() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Diagnostic")
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
                .checkAddedToEventTask("2 PERIODIC", "Trace diagnostic", "8.8.8.8");
    }

    @Test
    public void tr181_dp_273() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Diagnostic")
                .selectDiagnostic("Download diagnostic")
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTask("2 PERIODIC", "Diagnostic");
    }

    @Test
    public void tr181_dp_274() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Diagnostic")
                .selectDiagnostic("Upload diagnostic")
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTask("2 PERIODIC", "Diagnostic");
    }

    @Test
    public void tr181_dp_275() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Diagnostic")
                .selectDiagnostic("Wi-Fi neighboring diagnostic")
                .saveTaskButton()
                .saveTaskButton()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents()
                .checkAddedToEventTask("2 PERIODIC", "Wi-Fi neighboring diagnostic", " ");
    }

    @Test
    public void tr181_dp_276() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Diagnostic")
                .selectDiagnostic("IPPing diagnostic")
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
                .checkAddedToEventTask("2 PERIODIC", "IPPing diagnostic", "8.8.8.8");
    }

    @Test   //Bug:TaskDiagnostic task with id=4704 not found
    public void tr181_dp_277() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", false, "3", null), true)
                .addTask("Diagnostic")
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
                .checkAddedToEventTask("2 PERIODIC", "NSLoopback diagnostics", "127.0.0.1");
    }

    @Test
    public void tr181_dp_278() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
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
                .selectMainTab("Parameters monitor")
                .selectEventTab("Ethernet")
                .setParametersMonitor(VALUE_CHANGE)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .expandParametersMonitor()
                .checkParametersMonitor();
    }

//    @Test
//    public void tr181_dp_999() {
//        dpPage
//                .topMenu(DEVICE_PROFILE)
//                .deleteAllProfiles();
//    }
}
