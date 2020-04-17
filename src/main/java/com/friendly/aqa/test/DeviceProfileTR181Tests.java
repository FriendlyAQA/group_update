package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.utils.DataBaseConnector;
import com.friendly.aqa.utils.Event;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.pageobject.DeviceProfilePage.Left.*;
import static com.friendly.aqa.pageobject.DeviceProfilePage.GlobalButtons.*;
import static com.friendly.aqa.pageobject.TopMenu.DEVICE_PROFILE;

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
                .setEvents(99, new Event("EXAMPLE", false, "5", "8:hours"))
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoProfile()
                .selectMainTab("Summary")
                .expandEvents()
                .checkEvents();
    }
}
