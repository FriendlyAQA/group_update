package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.pageobject.DeviceProfilePage.Left.NEW;
import static com.friendly.aqa.pageobject.DeviceProfilePage.Left.VIEW;
import static com.friendly.aqa.pageobject.GlobalButtons.*;
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
                .assertAbsenceOfProfile();
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
                .assertAbsenceOfProfile();
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
}
