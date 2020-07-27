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
                .fillName("Default")
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

    @Test
    public void tr069_du_016() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("Created");
    }

    @Test
    public void tr069_du_017() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("Firmware");
    }

    @Test
    public void tr069_du_018() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("Latitude");
    }

    @Test
    public void tr069_du_019() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("Longitude");
    }

    @Test
    public void tr069_du_020() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("Manufacturer");
    }

    @Test
    public void tr069_du_021() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("Model name");
    }

    @Test
    public void tr069_du_022() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("mycust03");
    }

    @Test
    public void tr069_du_023() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("mycust04");
    }

    @Test
    public void tr069_du_024() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("mycust05");
    }

    @Test
    public void tr069_du_025() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("mycust06");
    }

    @Test
    public void tr069_du_026() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("mycust07");
    }

    @Test
    public void tr069_du_027() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("mycust08");
    }

    @Test
    public void tr069_du_028() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("mycust09");
    }

    @Test
    public void tr069_du_029() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("mycust10");
    }

    @Test
    public void tr069_du_030() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("OUI");
    }

    @Test
    public void tr069_du_031() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("Phone number");
    }

    @Test   //bug: sorting by 'Protocol type' failed;
    public void tr069_du_032() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("Protocol type");
    }

    @Test
    public void tr069_du_033() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("Serial");
    }

    @Test
    public void tr069_du_034() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("Status");
    }

    @Test
    public void tr069_du_035() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("Updated");
    }

    @Test
    public void tr069_du_036() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("User ID");
    }

    @Test
    public void tr069_du_037() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("User location");
    }

    @Test
    public void tr069_du_038() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("User login");
    }

    @Test
    public void tr069_du_039() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("User name");
    }

    @Test
    public void tr069_du_040() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("User name")
                .checkSorting("User status");
    }

    @Test
    public void tr069_du_041() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("User tag");
    }

    @Test
    public void tr069_du_042() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr069_du_014")
                .checkSorting("Zip");
    }

    @Test
    public void tr069_du_043() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertChangingView();
    }

    @Test
    public void tr069_du_044() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr069_du_014")
                .editButton()
                .globalButtons(CANCEL)
                .assertSelectedViewIs("tr069_du_014");
    }

    @Test
    public void tr069_du_045() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr069_du_014")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(GROUP_UPDATE)
                .topMenu(DEVICE_UPDATE)
                .resetView()
                .assertSelectedViewIs("tr069_du_014")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .globalButtons(FINISH)
                .okButtonPopUp();
    }

    @Test
    public void tr069_du_046() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .checkFiltering("Model name");
    }

    @Test
    public void tr069_du_047() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .checkFiltering("Manufacturer");
    }

    @Test
    public void tr069_du_048() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr069_du_014")
                .selectView("Default")
                .assertTableColumnNumberIs(7, "tbl");
    }

    @Test
    public void tr069_du_049() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr069_du_011")
                .assertTableColumnNumberIs(2, "tbl");
    }

    @Test
    public void tr069_du_050() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr069_du_014")
                .editButton()
                .globalButtons(DELETE_GROUP)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "tr069_du_014");
    }

    @Test
    public void tr069_du_051() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterModelName(BasePage.getModelName())
                .selectAnyDevice()
                .globalButtons(DELETE)
                .cancelButtonPopUp()
                .globalButtons(DELETE)
                .okButtonPopUp()
                .assertAbsenceOfValue();
    }

    @Test
    public void tr069_du_052() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .saveTable("tbl")
                .globalButtons(REFRESH)
                .assertPageWasRefreshed();
    }

    @Test
    public void tr069_du_053() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .globalButtons(TRACE)
                .inputSerial()
                .globalButtons(START)
                .okButtonPopUp()
                .enterToDevice()
                .globalButtons(SHOW_TRACE)
                .assertTraceWindowIsOpened()
                .globalButtons(STOP_TRACE)
                .okButtonPopUp()/*
                .leftMenu(LIST)*/;
    }

    @Test
    public void tr069_du_054() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .globalButtons(EXPORT_TO_CSV)
                .saveFileName()
                .okButtonPopUp();
    }

    @Test
    public void tr069_du_055() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .globalButtons(EXPORT_TO_XML)
                .saveFileName()
                .okButtonPopUp();
    }

    @Test   //depends on 54, 55
    public void tr069_du_056() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .globalButtons(EXPORTS)
                .checkSavedExport();
    }

    @Test   //depends on 54, 55
    public void tr069_du_057() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .globalButtons(EXPORTS)
                .deleteExportEntry()
                .okButtonPopUp()
                .refreshButton()
                .assertAbsenceOfDeletedExportItem()
                .deleteAllExportEntries()
                .okButtonPopUp()
                .assertExportEntryListIsEmpty();
    }

    @Test
    public void tr069_du_058() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .globalButtons(SHOW_ON_MAP)
                .closeMapWindow();
    }

    @Test
    public void tr069_du_059() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .assertPresenceOfOptions("ddlView", "Default")
                .assertPresenceOfOptions("ddlSearchOption", "Phone number", "User ID", "Full name", "Username"
                        , "User Tag", "Serial Number", "IP address", "MAC address", "Base Station ID", "E-UTRAN Node B ID", "ACS Username")
                .assertPresenceOfElements("btnEditView_btn", "btnNewView_btn", "btnDefaultView_btn", "rdSearchExactly", "btnSearch_btn");
    }

    @Test
    public void tr069_du_060() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .globalButtons(NEXT)
                .globalButtons(PREVIOUS)
                .assertInputHasText("txtName", getTestName())
                .globalButtons(NEXT)
                .globalButtons(CANCEL)
                .assertPresenceOfElements("btnEditView_btn", "btnNewView_btn", "btnDefaultView_btn", "rdSearchExactly", "btnSearch_btn");
    }

    @Test
    public void tr069_du_061() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName("Default")
                .globalButtons(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void tr069_du_062() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
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
    public void tr069_du_063() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
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
    public void tr069_du_064() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
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
    public void tr069_du_065() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
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
    public void tr069_du_066() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
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
                .assertButtonsAreEnabled(true, PREVIOUS, NEXT, FINISH, CANCEL)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .searchBy("Serial Number")
                .lookFor(getSerial())
                .searchButton()
                .assertPresenceOfElements("tblDeviceInfo");
    }

    @Test
    public void tr069_du_067() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .globalButtons(NEXT)
                .setViewColumns(1, 99)
                .globalButtons(NEXT)
                .globalButtons(NEXT)
                .sortByColumn("Created")
                .globalButtons(FINISH)
                .okButtonPopUp()
                .searchBy("Serial Number")
                .lookFor(getSerial().substring(0, 3))
                .searchButton()
                .assertSortingByColumnIs("Created", null);
    }

    @Test
    public void tr069_du_068() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .globalButtons(NEXT)
                .setViewColumns(1, 99)
                .globalButtons(NEXT)
                .globalButtons(NEXT)
                .sortByColumn("Created")
                .sortingOrder("Ascending")
                .globalButtons(FINISH)
                .okButtonPopUp()
                .searchBy("Serial Number")
                .lookFor(getSerial().substring(0, 3))
                .searchButton()
                .assertSortingByColumnIs("Created", true);
    }

    @Test
    public void tr069_du_069() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .globalButtons(NEXT)
                .setViewColumns(1, 99)
                .globalButtons(NEXT)
                .globalButtons(NEXT)
                .sortByColumn("Created")
                .sortingOrder("Descending")
                .globalButtons(FINISH)
                .okButtonPopUp()
                .searchBy("Serial Number")
                .lookFor(getSerial().substring(0, 3))
                .searchButton()
                .assertSortingByColumnIs("Created", false);
    }

    @Test
    public void tr069_du_070() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .globalButtons(NEXT)
                .setViewColumns(1, 99)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .searchBy("Serial Number")
                .lookFor(getSerial().substring(0, 3))
                .searchButton()
                .validateViewColumns();
    }

    @Test
    public void tr069_du_071() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterModelName(BasePage.getModelName())
                .createPreconditionsForSorting();
    }

    @Test   //bug: from 072 till 098, not 100% reproduced, sometimes displays Default view instead tr069_du_070
    public void tr069_du_072() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("Created");
    }

    @Test
    public void tr069_du_073() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("Firmware");
    }

    @Test
    public void tr069_du_074() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("Latitude");
    }

    @Test
    public void tr069_du_075() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("Longitude");
    }

    @Test
    public void tr069_du_076() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("Manufacturer");
    }

    @Test
    public void tr069_du_077() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("Model name");
    }

    @Test
    public void tr069_du_078() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("mycust03");
    }

    @Test
    public void tr069_du_079() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("mycust04");
    }

    @Test
    public void tr069_du_080() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("mycust05");
    }

    @Test
    public void tr069_du_081() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("mycust06");
    }

    @Test
    public void tr069_du_082() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("mycust07");
    }

    @Test
    public void tr069_du_083() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("mycust08");
    }

    @Test
    public void tr069_du_084() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("mycust09");
    }

    @Test
    public void tr069_du_085() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("mycust10");
    }

    @Test
    public void tr069_du_086() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("OUI");
    }

    @Test
    public void tr069_du_087() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("Phone number");
    }

    @Test   //bug: sorting by 'Protocol type' failed;
    public void tr069_du_088() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("Protocol type");
    }

    @Test
    public void tr069_du_089() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("Serial");
    }

    @Test   //Bug: column "Status" is absent from search view settings list; (bug in STD?)
    public void tr069_du_090() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .lookFor(getSerial().substring(0, 1))
                .deselectCheckbox("rdSearchExactly")
                .searchButton()
                .checkSorting("Status");
    }

    @Test
    public void tr069_du_091() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("Updated");
    }

    @Test
    public void tr069_du_092() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("User ID");
    }

    @Test
    public void tr069_du_093() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("User location");
    }

    @Test
    public void tr069_du_094() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("User login");
    }

    @Test
    public void tr069_du_095() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("User name");
    }

    @Test
    public void tr069_du_096() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("User status");
    }

    @Test
    public void tr069_du_097() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("User tag");
    }

    @Test
    public void tr069_du_098() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070") //comment it
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
//                .selectView("tr069_du_070") //uncomment it to get around view bug
                .searchButton()
                .checkSorting("Zip");
    }

    @Test   //bug: after Cancel view doesn't return to previous selected view, but Default is selected.
    public void tr069_du_099() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .editButton()
                .globalButtons(CANCEL)
                .assertSelectedViewIs("tr069_du_070");
    }

    @Test
    public void tr069_du_100() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(GROUP_UPDATE)
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .resetView()
                .assertSelectedViewIs("tr069_du_070")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .globalButtons(FINISH)
                .okButtonPopUp();
    }
}
