package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.TopMenu.*;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.GlobalButtons.*;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.Left.*;

@Listeners(UniversalVideoListener.class)
public class DeviceUpdateTR069Tests extends BaseTestCase {

    @Test
    public void tr069_du_001() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_du_002() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(LIST)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_du_003() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertPresenceOfOptions("ddlView", "Default")
                .assertPresenceOfOptions("ddlManuf", "All")
                .assertPresenceOfOptions("ddlModel", "All");
    }

    @Test
    public void tr069_du_004() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .assertButtonsAreEnabled(false, PREVIOUS, FINISH)
                .assertButtonsAreEnabled(true, CANCEL, NEXT)
                .globalButtons(NEXT)
                .globalButtons(PREVIOUS)
                .globalButtons(NEXT)
                .globalButtons(CANCEL)
                .assertMainPageIsDisplayed()
                .assertButtonsAreEnabled(false, DELETE)
                .assertButtonsAreEnabled(true, REFRESH, TRACE, EXPORT_TO_CSV, EXPORT_TO_XML, EXPORTS, SHOW_ON_MAP);
    }

    @Test
    public void tr069_du_005() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName("DEFAULT")
                .globalButtons(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void tr069_du_006() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .globalButtons(NEXT)
                .globalButtons(PREVIOUS)
                .assertInputHasText("txtName", getTestName())
                .globalButtons(NEXT)
                .setViewColumns(1, 1)
                .globalButtons(NEXT)
                .globalButtons(PREVIOUS)
                .assertCheckboxesAreSelected("tblFilter", true, 1, 1)
                .assertCheckboxesAreSelected("tblFilter", false, 1, -2);
    }

    @Test
    public void tr069_du_007() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .globalButtons(NEXT)
                .setViewColumns(1, 1)
                .globalButtons(NEXT)
                .globalButtons(PREVIOUS)
                .setViewColumns(1, 1)
                .setViewColumns(2, 2)
                .globalButtons(NEXT)
                .globalButtons(PREVIOUS)
                .assertCheckboxesAreSelected("tblFilter", true, 1, 2)
                .assertCheckboxesAreSelected("tblFilter", false, 1, 1, -3);
    }

    @Test
    public void tr069_du_008() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .globalButtons(NEXT)
                .setViewColumns(2, 2)
                .globalButtons(NEXT)
                .globalButtons(PREVIOUS)
                .setViewColumns(3, 4)
                .globalButtons(NEXT)
                .globalButtons(PREVIOUS)
                .assertCheckboxesAreSelected("tblFilter", true, 1, 2, 3, 4)
                .assertCheckboxesAreSelected("tblFilter", false, 1, 1, -5);
    }

    @Test
    public void tr069_du_009() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .globalButtons(NEXT)
                .setViewColumns(1, 2)
                .globalButtons(NEXT)
                .clickOnTable("tbl", 1, 0)
                .downButton()
                .saveTable("tbl")
                .globalButtons(PREVIOUS)
                .globalButtons(NEXT)
                .compareTable("tbl");
    }

    @Test
    public void tr069_du_010() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .globalButtons(NEXT)
                .setViewColumns(1, 3)
                .globalButtons(NEXT)
                .clickOnTable("tbl", 1, 0)
                .assertButtonsAreEnabled(true, PREVIOUS, NEXT, FINISH, CANCEL)
                .downButton()
                .assertButtonsAreEnabled(true, PREVIOUS, NEXT, FINISH, CANCEL)
                .bottomButton()
                .assertButtonsAreEnabled(true, PREVIOUS, NEXT, FINISH, CANCEL)
                .upButton()
                .assertButtonsAreEnabled(true, PREVIOUS, NEXT, FINISH, CANCEL)
                .topButton()
                .assertButtonsAreEnabled(true, PREVIOUS, NEXT, FINISH, CANCEL);
    }

    @Test
    public void tr069_du_011() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .globalButtons(NEXT)
                .setViewColumns("Model name")
                .globalButtons(NEXT)
                .addFilter()
                .selectColumnFilter("Model name")
                .selectCompare("=")
                .selectFilterModelName(BasePage.getModelName())
                .globalButtons(NEXT)
                .filterRecordsCheckbox()
                .assertButtonIsEnabled(true, "btnDelFilter_btn")
                .clickOnTable("tblTree", 0, 0, 1)
                .globalButtons(CANCEL)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .validateViewColumns();
    }

    @Test   //depends on 11
    public void tr069_du_012() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr069_du_011")
                .editButton()
                .globalButtons(NEXT)
                .globalButtons(NEXT)
                .clickOnTable("tblTree", 0, 0, 1)
                .addSubFilter()
                .selectColumnFilter("Created")
                .andRadioButton()
                .selectCompare("Is not null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertColumnHasSingleValue("Model name", BasePage.getModelName());
    }

    @Test   //depends on 11, 12
    public void tr069_du_013() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr069_du_011")
                .editButton()
                .globalButtons(NEXT)
                .globalButtons(NEXT)
                .filterRecordsCheckbox()
                .deleteFilter()
                .okButtonPopUp()
                .globalButtons(FINISH)
                .okButtonPopUp()
                .assertColumnHasSeveralValues("Model name");
    }

    @Test
    public void tr069_du_014() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .globalButtons(NEXT)
                .setViewColumns(1, 99)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .validateViewColumns();
    }

    @Test
    public void tr069_du_015() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterModelName(BasePage.getModelName())
                .createPreconditionsForSorting();
    }

    @Test   //Bug: sorting by
    public void tr069_du_016() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("Created")
                .checkSorting("Firmware")
                .checkSorting("Latitude")
                .checkSorting("Longitude")
                .checkSorting("Manufacturer")
                .checkSorting("Model name")
                .checkSorting("mycust03")
                .checkSorting("mycust04")
                .checkSorting("mycust05")
                .checkSorting("mycust06")
                .checkSorting("mycust07")
                .checkSorting("mycust08")
                .checkSorting("mycust09")
                .checkSorting("mycust10")
                .checkSorting("OUI")
                .checkSorting("Phone number")
                .checkSorting("Protocol type")
                .checkSorting("Serial")
                .checkSorting("Status")
                .checkSorting("Updated")
                .checkSorting("User ID")
                .checkSorting("User location")
                .checkSorting("User login")
                .checkSorting("User name")
                .checkSorting("User status")
                .checkSorting("User tag")
                .checkSorting("Zip");
    }


}
