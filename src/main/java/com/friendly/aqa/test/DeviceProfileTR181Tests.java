package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
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
                .selectBranch("ManagementServer")
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
//                .pause(5000)
                .assertHasRedBorder(true, "ConnectionRequestPassword")
                .setParameter("ConnectionRequestPassword", "ftacs")
                .assertHasRedBorder(false, "ConnectionRequestPassword")
                .setParameter("PeriodicInformEnable", "")
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

    @Test       //text in Name field is disappearing when condition added;
    public void tr181_dp_027() {
        dpPage
                .topMenu(DEVICE_PROFILE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .selectMainTab("Parameters")
                .selectTab("Management")
                .setParameter("PeriodicInformInterval, sec", "60")
                .newConditionRadioButton()
                .fillName()
                .globalButtons(NEXT)
                .addFilter()
                .userInfoRadioButton()
                .selectUserInfoComboBox("user_tag")
                .selectConditionTypeComboBox("!=")
                .fillValue("1")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .fillName()
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectProfileStatus("Active")
                .assertProfileIsPresent(true, getTestName());
    }
}
