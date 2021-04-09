package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.friendly.aqa.entities.TopMenu.DEVICE_UPDATE;
import static com.friendly.aqa.entities.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.BottomButtons.*;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.Left.*;

@Listeners(UniversalVideoListener.class)
public class DeviceUpdateTR181Tests extends BaseTestCase {

    /*
    Preconditions:
    1. Before run you should delete all view and custom view (Search) with name like 'lwm2m_du_011', 'lwm2m_du_067' (starts with lwm2m_) etc.;
    2. Devices (emulator) must run;
    3. At least one active profile for target device must exist (tr181_du_134);
    4. Trace for target device must be stopped (tr181_du_135);
    5. All files for Download and Upload tasks must be added to server.
    */
    @Test
    public void tr181_du_001() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr181_du_002() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(LIST)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr181_du_003() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertPresenceOfOptions("ddlView", "Default")
                .assertPresenceOfOptions("ddlManuf", "All")
                .assertPresenceOfOptions("ddlModel", "All");
    }

    @Test
    public void tr181_du_004() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .deleteAllCustomViews()   //as precondition for next step and tests
                .newViewButton()
                .fillName()
                .assertButtonsAreEnabled(false, PREVIOUS, FINISH)
                .assertButtonsAreEnabled(true, CANCEL, NEXT)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .validateName()
                .bottomMenu(NEXT)
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed()
                .assertButtonsAreEnabled(false, DELETE)
                .assertButtonsAreEnabled(true, REFRESH, TRACE, EXPORT_TO_CSV, EXPORT_TO_XML, EXPORTS, SHOW_ON_MAP)
                .assertAbsenceOfOptions("ddlView", getTestName());
    }

    @Test
    public void tr181_du_005() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName("Default")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void tr181_du_006() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .validateName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 1)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertCheckboxesAreSelected("tblFilter", true, 1, 1)
                .assertCheckboxesAreSelected("tblFilter", false, 1, -2);
    }

    @Test
    public void tr181_du_007() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 1)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .setVisibleColumns(1, 1)
                .setVisibleColumns(2, 2)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertCheckboxesAreSelected("tblFilter", true, 1, 2)
                .assertCheckboxesAreSelected("tblFilter", false, 1, 1, -3);
    }

    @Test
    public void tr181_du_008() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns(2, 2)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .setVisibleColumns(3, 4)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertCheckboxesAreSelected("tblFilter", true, 1, 2, 3, 4)
                .assertCheckboxesAreSelected("tblFilter", false, 1, 1, -5);
    }

    @Test
    public void tr181_du_009() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 2)
                .bottomMenu(NEXT)
                .clickOnTable("tbl", 1, 0)
                .downButton()
                .saveTable("tbl")
                .bottomMenu(PREVIOUS)
                .bottomMenu(NEXT)
                .compareTable("tbl");
    }

    @Test
    public void tr181_du_010() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 3)
                .bottomMenu(NEXT)
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
    public void tr181_du_011() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns("Model name")
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Model name")
                .selectCompare("=")
                .selectFilterModelName(getModelName())
                .bottomMenu(NEXT)
                .filterRecordsCheckbox()
                .assertButtonIsEnabled(true, "btnDelFilter_btn")
                .clickOnTable("tblTree", 0, 0, 1)
                .bottomMenu(CANCEL)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .validateViewColumns();
    }

    @Test   //depends on 11
    public void tr181_du_012() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr181_du_011")
                .editButton()
                .bottomMenu(NEXT)
                .bottomMenu(NEXT)
                .clickOnTable("tblTree", 0, 0, 1)
                .addSubFilter()
                .selectColumnFilter("Created")
                .andRadioButton()
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertColumnHasSingleValue("Model name", getModelName());
    }

    @Test   //depends on 11, 12
    public void tr181_du_013() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr181_du_011")
                .editButton()
                .bottomMenu(NEXT)
                .bottomMenu(NEXT)
                .filterRecordsCheckbox()
                .deleteFilter()
                .okButtonPopUp()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertColumnHasSeveralValues("Model name");
    }

    @Test
    public void tr181_du_014() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 99)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .validateViewColumns();
    }

    @Test
    public void tr181_du_015() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterModelName(getModelName())
                .createPreconditionsForSorting();
    }

    @Test
    public void tr181_du_016() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("Created");
    }

    @Test
    public void tr181_du_017() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("Firmware");
    }

    @Test
    public void tr181_du_018() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("Latitude");
    }

    @Test
    public void tr181_du_019() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("Longitude");
    }

    @Test
    public void tr181_du_020() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("Manufacturer");
    }

    @Test
    public void tr181_du_021() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("Model name");
    }

    @Test
    public void tr181_du_022() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("mycust03");
    }

    @Test
    public void tr181_du_023() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("mycust04");
    }

    @Test
    public void tr181_du_024() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("mycust05");
    }

    @Test
    public void tr181_du_025() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("mycust06");
    }

    @Test
    public void tr181_du_026() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("mycust07");
    }

    @Test
    public void tr181_du_027() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("mycust08");
    }

    @Test
    public void tr181_du_028() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("mycust09");
    }

    @Test
    public void tr181_du_029() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("mycust10");
    }

    @Test
    public void tr181_du_030() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("OUI");
    }

    @Test
    public void tr181_du_031() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("Phone number");
    }

    @Test   //bug: sorting by 'Protocol type' failed;
    public void tr181_du_032() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("Protocol type");
    }

    @Test   //depends on tr181_du_014
    public void tr181_du_033() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("Serial");
    }

    @Test
    public void tr181_du_034() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("Status");
    }

    @Test
    public void tr181_du_035() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("Last connection");
    }

    @Test
    public void tr181_du_036() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("User ID");
    }

    @Test
    public void tr181_du_037() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("User location");
    }

    @Test
    public void tr181_du_038() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("User login");
    }

    @Test
    public void tr181_du_039() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("User name");
    }

    @Test
    public void tr181_du_040() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("User name")
                .validateSorting("User status");
    }

    @Test
    public void tr181_du_041() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("User tag");
    }

    @Test
    public void tr181_du_042() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("tr181_du_014")
                .validateSorting("Zip");
    }

    @Test
    public void tr181_du_043() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertChangingView();
    }

    @Test
    public void tr181_du_044() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr181_du_014")
                .editButton()
                .bottomMenu(CANCEL)
                .assertSelectedViewIs("tr181_du_014");
    }

    @Test
    public void tr181_du_045() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr181_du_014")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(GROUP_UPDATE)
                .topMenu(DEVICE_UPDATE)
                .resetView()
                .assertSelectedViewIs("tr181_du_014")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp();
    }

    @Test
    public void tr181_du_046() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .validateFiltering("Model name");
    }

    @Test
    public void tr181_du_047() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .validateFiltering("Manufacturer");
    }

    @Test
    public void tr181_du_048() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr181_du_014")
                .selectView("Default")
                .assertTableColumnAmountIs(7, "tbl");
    }

    @Test
    public void tr181_du_049() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr181_du_011")
                .assertTableColumnAmountIs(2, "tbl");
    }

    @Test
    public void tr181_du_050() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr181_du_014")
                .editButton()
                .bottomMenu(DELETE_VIEW)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "tr181_du_014");
    }

    @Test
    public void tr181_du_051() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterModelName(getModelName())
                .selectAnyDevice()
                .bottomMenu(DELETE)
                .cancelButtonPopUp()
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .assertAbsenceOfSerial();
    }

    @Test
    public void tr181_du_052() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .checkRefreshPage();
    }

    @Test
    public void tr181_du_053() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(TRACE)
                .inputSerial()
                .bottomMenu(START)
                .okButtonPopUp()
                .openDevice()
                .bottomMenu(SHOW_TRACE)
                .assertTraceWindowIsOpened()/*
                .bottomMenu(STOP_TRACE)
                .okButtonPopUp()
                .leftMenu(LIST)*/;
    }

    @Test
    public void tr181_du_054() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORT_TO_CSV)
                .saveFileName()
                .okButtonPopUp();
    }

    @Test
    public void tr181_du_055() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORT_TO_XML)
                .saveFileName()
                .okButtonPopUp();
    }

    @Test   //depends on 54, 55
    public void tr181_du_056() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORTS)
                .validateSavedExport("csv", "xml");
    }

    @Test   //depends on 54, 55
    public void tr181_du_057() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORTS)
                .deleteExportEntry()
                .okButtonPopUp()
                .refreshButton()
                .assertAbsenceOfDeletedExportItem()
                .deleteAllExportEntries()
                .okButtonPopUp()
                .assertExportEntryListIsEmpty();
    }

    @Test
    public void tr181_du_058() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(SHOW_ON_MAP)
                .closeMapWindow();
    }

    @Test
    public void tr181_du_059() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .assertPresenceOfOptions("ddlView", "Default")
                .assertPresenceOfOptions("ddlSearchOption", "Phone number", "User ID", "User name", "User login"
                        , "User Tag", "Serial Number", "IP address", "MAC address", "Base Station ID", "E-UTRAN Node B ID", "ACS Username")
                .assertElementsArePresent("btnEditView_btn", "btnNewView_btn", "btnDefaultView_btn", "rdSearchExactly", "btnSearch_btn");
    }

    @Test
    public void tr181_du_060() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .validateName()
                .bottomMenu(NEXT)
                .bottomMenu(CANCEL)
                .assertElementsArePresent("btnEditView_btn", "btnNewView_btn", "btnDefaultView_btn", "rdSearchExactly", "btnSearch_btn");
    }

    @Test
    public void tr181_du_061() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName("Default")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void tr181_du_062() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .validateName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 1)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertCheckboxesAreSelected("tblFilter", true, 1, 1)
                .assertCheckboxesAreSelected("tblFilter", false, 1, -2);
    }

    @Test
    public void tr181_du_063() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 1)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .setVisibleColumns(1, 1)
                .setVisibleColumns(2, 2)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertCheckboxesAreSelected("tblFilter", true, 1, 2)
                .assertCheckboxesAreSelected("tblFilter", false, 1, 1, -3);
    }

    @Test
    public void tr181_du_064() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns(2, 2)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .setVisibleColumns(3, 4)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertCheckboxesAreSelected("tblFilter", true, 1, 2, 3, 4)
                .assertCheckboxesAreSelected("tblFilter", false, 1, 1, -5);
    }

    @Test
    public void tr181_du_065() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 2)
                .bottomMenu(NEXT)
                .clickOnTable("tbl", 1, 0)
                .downButton()
                .saveTable("tbl")
                .bottomMenu(PREVIOUS)
                .bottomMenu(NEXT)
                .compareTable("tbl");
    }

    @Test
    public void tr181_du_066() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .deleteAllCustomViews()
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 3)
                .bottomMenu(NEXT)
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
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .searchBy("Serial Number")
                .lookFor(getSerial())
                .searchButton()
                .assertElementsArePresent("tblDeviceInfo");
    }

    @Test
    public void tr181_du_067() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 99)
                .bottomMenu(NEXT)
                .bottomMenu(NEXT)
                .sortByColumn("Created")
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .searchBy("Serial Number")
                .lookFor(getSerial().substring(0, 3))
                .searchButton()
                .assertSortingPerformedBy("Created", null);
    }

    @Test
    public void tr181_du_068() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 99)
                .bottomMenu(NEXT)
                .bottomMenu(NEXT)
                .sortByColumn("Created")
                .sortingOrder("Ascending")
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .searchBy("Serial Number")
                .lookFor(getSerial().substring(0, 3))
                .searchButton()
                .assertSortingPerformedBy("Created", true);
    }

    @Test
    public void tr181_du_069() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 99)
                .bottomMenu(NEXT)
                .bottomMenu(NEXT)
                .sortByColumn("Created")
                .sortingOrder("Descending")
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .searchBy("Serial Number")
                .lookFor(getSerial().substring(0, 3))
                .searchButton()
                .assertSortingPerformedBy("Created", false);
    }

    @Test
    public void tr181_du_070() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 99)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .lookFor(getSerial().substring(0, 3))//!
                .searchBy("Serial Number")//!
                .searchButton()
                .validateViewColumns();
    }

    @Test
    public void tr181_du_071() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterModelName(getModelName())
                .createPreconditionsForSorting();
    }

    @Test   //bug: from 072 till 098, not 100% reproduced, sometimes displays Default view instead tr181_du_070
    public void tr181_du_072() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Created");
    }

    @Test
    public void tr181_du_073() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
//                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .selectView("tr181_du_070")
//                .searchButton()
                .validateSorting("Firmware");
    }

    @Test
    public void tr181_du_074() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Latitude");
    }

    @Test
    public void tr181_du_075() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Longitude");
    }

    @Test
    public void tr181_du_076() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Manufacturer");
    }

    @Test
    public void tr181_du_077() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Model name");
    }

    @Test
    public void tr181_du_078() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust03");
    }

    @Test
    public void tr181_du_079() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust04");
    }

    @Test
    public void tr181_du_080() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust05");
    }

    @Test
    public void tr181_du_081() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust06");
    }

    @Test
    public void tr181_du_082() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust07");
    }

    @Test
    public void tr181_du_083() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust08");
    }

    @Test
    public void tr181_du_084() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust09");
    }

    @Test
    public void tr181_du_085() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust10");
    }

    @Test
    public void tr181_du_086() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("OUI");
    }

    @Test
    public void tr181_du_087() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Phone number");
    }

    @Test   //bug: sorting by 'Protocol type' failed;
    public void tr181_du_088() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Protocol type");
    }

    @Test
    public void tr181_du_089() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Serial");
    }

    @Test   //Bug: column "Status" is absent from search view settings list; (bug in STD?)
    public void tr181_du_090() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .lookFor(getSerial().substring(0, 1))
                .deselectCheckbox("rdSearchExactly")
                .searchButton()
                .validateSorting("Status");
    }

    @Test
    public void tr181_du_091() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Last connection");
    }

    @Test
    public void tr181_du_092() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
//                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .selectView("tr181_du_070")
//                .searchButton()
                .validateSorting("User ID");
    }

    @Test
    public void tr181_du_093() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User location");
    }

    @Test
    public void tr181_du_094() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User login");
    }

    @Test
    public void tr181_du_095() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User name");
    }

    @Test
    public void tr181_du_096() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User status");
    }

    @Test
    public void tr181_du_097() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User tag");
    }

    @Test
    public void tr181_du_098() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Zip");
    }

    @Test   //bug: after 'Cancel' view doesn't return to previous selected view, Default is selected instead.
    public void tr181_du_099() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .editButton()
                .bottomMenu(CANCEL)
                .assertSelectedViewIs("tr181_du_070");
    }

    @Test
    public void tr181_du_100() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_070")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(GROUP_UPDATE)
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .resetView()
                .assertSelectedViewIs("tr181_du_070")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp();
    }

    @Test
    public void tr181_du_101() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr181_du_069")
                .editButton()
                .bottomMenu(DELETE_VIEW)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "tr181_du_069");
    }

    @Test
    public void tr181_du_102() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Phone number", false);
    }

    @Test
    public void tr181_du_103() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Phone number", true);
    }

    @Test
    public void tr181_du_104() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User ID", false);
    }

    @Test
    public void tr181_du_105() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User ID", true);
    }

    @Test
    public void tr181_du_106() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User name", false);
    }

    @Test
    public void tr181_du_107() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User name", true);
    }

    @Test
    public void tr181_du_108() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User login", false);
    }

    @Test
    public void tr181_du_109() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User login", true);
    }

    @Test
    public void tr181_du_110() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User Tag", false);
    }

    @Test
    public void tr181_du_111() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User Tag", true);
    }

    @Test
    public void tr181_du_112() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Serial Number", false);
    }

    @Test
    public void tr181_du_113() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Serial Number", true);
    }

    @Test
    public void tr181_du_114() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("IP address", false);
    }

    @Test
    public void tr181_du_115() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("IP address", true);
    }

    @Test
    public void tr181_du_116() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("MAC address", false);
    }

    @Test
    public void tr181_du_117() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("MAC address", true);
    }

    //skipped: 118-121 due to impossible to validate (E-UTRAN Node B ID, Base Station ID)

    @Test
    public void tr181_du_122() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("ACS Username", false);
    }

    @Test
    public void tr181_du_123() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("ACS Username", true);
    }

    @Test
    public void tr181_du_124() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .searchBy("Serial Number")
                .lookFor(getSerial())
                .selectCheckbox("rdSearchExactly")
                .searchButton()
                .assertTransferToDeviceInfo();
    }

    @Test
    public void tr181_du_125() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Serial Number", false);
    }

    @Test
    public void tr181_du_126() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .searchBy("Serial Number")
                .lookFor(getPartialSerial(-4))
                .deselectCheckbox("rdSearchExactly")
                .searchButton()
                .selectAnyDevice()
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .assertAbsenceOfSerial();
    }

    @Test
    public void tr181_du_127() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .searchBy("Serial Number")
                .lookFor(getPartialSerial(-4))
                .deselectCheckbox("rdSearchExactly")
                .searchButton()
                .saveTable("tbl")
                .bottomMenu(REFRESH)
                .compareTable("tbl");
    }

    @Test
    public void tr181_du_128() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .searchBy("Serial Number")
                .lookFor(getPartialSerial(-4))
                .deselectCheckbox("rdSearchExactly")
                .searchButton()
                .bottomMenu(SEARCH_EXPORT_TO_CSV)
                .saveFileName()
                .okButtonPopUp()
                .leftMenu(LIST)
                .bottomMenu(EXPORTS)
                .validateSavedExport("csv");
    }

    @Test
    public void tr181_du_129() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .searchBy("Serial Number")
                .lookFor(getPartialSerial(-4))
                .deselectCheckbox("rdSearchExactly")
                .searchButton()
                .bottomMenu(SEARCH_EXPORT_TO_XML)
                .saveFileName()
                .okButtonPopUp()
                .leftMenu(LIST)
                .bottomMenu(EXPORTS)
                .validateSavedExport("xml");
    }

    @Test
    public void tr181_du_130() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_INFO)
                .recheckStatus();
    }

    @Test
    public void tr181_du_131() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_INFO)
                .bottomMenu(REBOOT)
                .cancelButtonPopUp()
                .bottomMenu(REBOOT)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .assertLastActivityIs("Reboot");
    }

    @Test
    public void tr181_du_132() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_INFO)
                .bottomMenu(FACTORY_RESET)
                .cancelButtonPopUp()
                .bottomMenu(FACTORY_RESET)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .assertLastActivityIs("Reset to factory defaults");
    }

    @Test
    public void tr181_du_133() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_INFO)
                .bottomMenu(CREATE_TEMPLATE)
                .cancelButtonPopUp()
                .bottomMenu(CREATE_TEMPLATE)
                .okButtonPopUp()
                .okButtonPopUp();
    }

    @Test
    public void tr181_du_134() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_INFO)
                .bottomMenu(REPROVISION)
                .cancelButtonPopUp()
                .bottomMenu(REPROVISION)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .assertActivityIsPresent("Profile: set parameter values");
    }

    @Test
    public void tr181_du_135() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clickIfPresent(STOP_TRACE)
                .bottomMenu(START_TRACE)
                .okButtonPopUp()
                .bottomMenu(SHOW_TRACE)
                .assertTraceWindowIsOpened()
                .bottomMenu(SHOW_TRACE)
                .assertTraceWindowIsOpened();
    }

    //skipped: 136 (functional implemented in 135)


    @Test
    public void tr181_du_137() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .bottomMenu(PING)
                .assertPingWindowIsOpened();
    }

    @Test
    public void tr181_du_138() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .bottomMenu(TRACE_ROUTE)
                .assertTracerouteWindowIsOpened();
    }

    @Test
    public void tr181_du_139() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .bottomMenu(REPLACE)
                .assertReplaceWindowIsOpened();
    }

    @Test
    public void tr181_du_140() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToAnyDevice()
                .bottomMenu(DELETE)
                .cancelButtonPopUp()
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .assertAbsenceOfSerial();
    }

    @Test
    public void tr181_du_141() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .setAllUserInfo()
                .editAccountInfoLink()
                .clearUserInfo()
                .assertAccountInfoIsClear();
    }

    @Test
    public void tr181_du_142() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .listOfMethods()
                .assertMethodIsPresent("SetParameterValues")
                .closePopup();
    }

    @Test
    public void tr181_du_143() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .networkMap()
                .assertMapIsPresent()
                .closePopup();
    }

    @Test
    public void tr181_du_144() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformInterval, sec", "61")
                .bottomMenu(SEND_UPDATE)
                .cancelButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateAbsenceTaskWithValue(/*"61"*/);
    }

    @Test
    public void tr181_du_145() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformTime", now())
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_146() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformInterval, sec", "61")
                .setParameter("PeriodicInformTime", now())
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_147() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformInterval, sec", "62")  //cannot set all params on Management tab due to the possibility of device disconnecting;
                .setParameter("PeriodicInformTime", now())
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_148() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformTime", now())
                .bottomMenu(WAIT_UNTIL_CONNECT)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_149() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .clearProvisionManager()
                .leftMenu(DEVICE_SETTINGS)
                .pause(1000)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformTime", now())
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test
    public void tr181_du_150() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .storePath()
                .bottomMenu(GET_CURRENT_SETTINGS)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateGeneratedGets();
    }

    @Test
    public void tr181_du_151() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Information", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_152() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Information", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_153() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Information", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_154() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
//                .pause(1000)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Information", 1)
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test
    public void tr181_du_155() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Time", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_156() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Time", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_157() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Time", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_158() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Time", 1)
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test
    public void tr181_du_159() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("WiFi", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_160() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("WiFi", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_161() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("WiFi", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_162() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("WiFi", 1)
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test
    public void tr181_du_163() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("IP", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_164() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("IP", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_165() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("IP", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_166() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("IP", 1)
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    //skipped: 167, 168 due to "add/delete object" buttons are absent

    @Test
    public void tr181_du_169() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Firewall", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_170() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Firewall", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_171() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Firewall", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_172() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Firewall", 1)
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test
    public void tr181_du_173() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DHCPv4", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_174() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DHCPv4", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_175() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DHCPv4", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_176() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DHCPv4", 1)
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test
    public void tr181_du_177() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DHCPv6", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_178() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DHCPv6", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_179() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DHCPv6", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_180() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DHCPv6", 1)
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test
    public void tr181_du_181() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DNS", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_182() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DNS", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_183() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DNS", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_184() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DNS", 1)
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test
    public void tr181_du_185() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Users", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_186() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Users", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_187() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Users", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_188() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Users", 1)
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test
    public void tr181_du_189() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Ethernet", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_190() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Ethernet", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_191() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Ethernet", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_192() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Ethernet", 1)
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test
    public void tr181_du_193() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(ADVANCED_VIEW)
                .validateObjectTree(); // use .validateObjectTree1() instead if failed!
    }

    @Test
    public void tr181_du_194() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(ADVANCED_VIEW)
                .selectBranch("ManagementServer")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformTime", now())
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_195() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(ADVANCED_VIEW)
                .selectBranch("ManagementServer")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformTime", now())
                .bottomMenu(WAIT_UNTIL_CONNECT)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr181_du_196() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(ADVANCED_VIEW)
                .selectBranch("ManagementServer")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformTime", now())
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test
    public void tr181_du_197() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(ADVANCED_VIEW)
                .bottomMenu(EDIT_TREE)
                .selectTreeObject(true, 0)
                .bottomMenu(STORE_TREE)
                .okButtonPopUp();   //is validation needed??? How???
    }

    @Test
    public void tr181_du_198() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(ADVANCED_VIEW)
                .bottomMenu(EDIT_TREE)
                .bottomMenu(CLEAR_TREE)
                .bottomMenu(STORE_TREE)
                .okButtonPopUp();   //is validation needed??? How???
    }

    @Test
    public void tr181_du_199() throws IOException {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(ADVANCED_VIEW)
                .validateCsvFile();
    }

    @Test
    public void tr181_du_200() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_DOWNLOAD)
                .selectDownloadFileType("Firmware Image")
                .selectFromListRadioButton()
                .selectFileName()
                .bottomMenu(START)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks();
    }

    @Test
    public void tr181_du_201() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_DOWNLOAD)
                .selectDownloadFileType("Firmware Image")
                .fillDownloadUrl()
                .bottomMenu(START)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks();
    }

    @Test
    public void tr181_du_202() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_DOWNLOAD)
                .selectDownloadFileType("Vendor Configuration File")
                .selectFromListRadioButton()
                .selectFileName()
                .bottomMenu(START)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks();
    }

    @Test
    public void tr181_du_203() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_DOWNLOAD)
                .selectDownloadFileType("Vendor Configuration File")
                .fillDownloadUrl()
                .bottomMenu(START)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks();
    }

    //skipped: 204 - cannot select File name dropdown;


    @Test
    public void tr181_du_205() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_DOWNLOAD)
                .selectDownloadFileType("Vendor Configuration File")
                .selectFromListRadioButton()
                .selectFileName()
                .bottomMenu(WAIT_UNTIL_CONNECT)
                .bottomMenu(START)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks();
    }

    @Test
    public void tr181_du_206() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_DOWNLOAD)
                .selectDownloadFileType("Vendor Configuration File")
                .selectFromListRadioButton()
                .selectFileName()
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(START)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionDownloadTasks();
    }

    @Test
    public void tr181_du_207() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_UPLOAD)
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateUploadFileTasks();
    }

    @Test
    public void tr181_du_208() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_UPLOAD)
                .selectUploadFileType("Vendor Configuration File")
                .manualUrlRButton()
                .fillUploadUrl()
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks();
    }

    @Test
    public void tr181_du_209() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_UPLOAD)
                .selectUploadFileType("Vendor Log File")
                .defaultUploadRadioButton()
                .bottomMenu(START)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateUploadFileTasks();
    }

    @Test
    public void tr181_du_210() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_UPLOAD)
                .selectUploadFileType("Vendor Log File")
                .manualUrlRButton()
                .fillUploadUrl()
                .bottomMenu(START)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks();
    }

    @Test
    public void tr181_du_211() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_UPLOAD)
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .bottomMenu(WAIT_UNTIL_CONNECT)
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateUploadFileTasks();
    }

    @Test
    public void tr181_du_212() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(PORT_MAPPING)
                .storePath("Device.NAT.PortMapping.")
                .bottomMenu(GET_CURRENT_PORTS)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateGeneratedGets();
    }

    @Test   //bug: 'Description' and 'Protocol' fields are empty after saving or  tab "port mapping" not found
    public void tr181_du_213() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
//                .clearDeviceActivity()
                .leftMenu(PORT_MAPPING)
                .bottomMenu(ADD_PORT)
                .fillAddPortFields("TCP/UDP")
                .bottomMenu(START)
                .okButtonPopUp()
                .validatePortCreating();
    }

    @Test   //bug: 'Description' and 'Protocol' fields are empty after saving or  tab "port mapping" not found
    public void tr181_du_214() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PORT_MAPPING)
                .bottomMenu(ADD_PORT)
                .fillAddPortFields("TCP/UDP")
                .bottomMenu(WAIT_UNTIL_CONNECT)
                .bottomMenu(START)
                .okButtonPopUp()
                .validatePortCreating();
    }

    @Test   //bug: 'Description' and 'Protocol' fields are empty after saving or tab "port mapping" not found
    public void tr181_du_215() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PORT_MAPPING)
                .bottomMenu(ADD_PORT)
                .fillAddPortFields("TCP/UDP")
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(START)
                .okButtonPopUp()
                .validatePortCreating();
    }

    @Test   //bug: 'Description' and 'Protocol' fields are empty after saving or tab "port mapping" not found
    public void tr181_du_216() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PORT_MAPPING)
                .bottomMenu(ADD_PORT)
                .fillAddPortFields("TCP")
                .bottomMenu(START)
                .okButtonPopUp()
                .validatePortCreating();
    }

    @Test   //bug: 'Description' and 'Protocol' fields are empty after saving or tab "port mapping" not found
    public void tr181_du_217() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PORT_MAPPING)
                .bottomMenu(ADD_PORT)
                .fillAddPortFields("UDP")
                .bottomMenu(START)
                .okButtonPopUp()
                .validatePortCreating();
    }

    @Test   //bug: 'Description' and 'Protocol' fields are empty after saving or tab "port mapping" not found
    public void tr181_du_218() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PORT_MAPPING)
                .selectPort()
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .verifyPortDeletion();
    }

    @Test   //bug: "An unexpected occurrence happened. Log file updated." after pressing 'Create'
    public void tr181_du_219() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_DIAGNOSTIC)
                .deleteAllDiagnostics()
                .createDiagnostic("Trace diagnostics")
                .bottomMenu(START)
                .okButtonPopUp()
                .validateDiagnosticCreation();
    }

    @Test
    public void tr181_du_220() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_DIAGNOSTIC)
                .deleteAllDiagnostics()
                .createDiagnostic("IPPing diagnostics")
                .bottomMenu(START)
                .okButtonPopUp()
                .validateDiagnosticCreation();
    }

    @Test
    public void tr181_du_221() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_DIAGNOSTIC)
                .deleteAllDiagnostics()
                .createDiagnostic("Download diagnostics")
                .bottomMenu(START)
                .okButtonPopUp()
                .validateDiagnosticCreation();
    }

    @Test
    public void tr181_du_222() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_DIAGNOSTIC)
                .deleteAllDiagnostics()
                .createDiagnostic("Upload diagnostics")
                .bottomMenu(START)
                .okButtonPopUp()
                .validateDiagnosticCreation();
    }

    @Test   //bug: "An unexpected occurrence happened. Log file updated." after pressing 'Create'
    public void tr181_du_223() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_DIAGNOSTIC)
                .deleteAllDiagnostics()
                .createDiagnostic("Wi-Fi neighboring diagnostics")
                .bottomMenu(START)
                .okButtonPopUp()
                .validateDiagnosticCreation();
    }

    @Test
    public void tr181_du_224() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_DIAGNOSTIC)
                .deleteAllDiagnostics()
                .createDiagnostic("NSLoopback diagnostics")
                .bottomMenu(START)
                .okButtonPopUp()
                .validateDiagnosticCreation();
    }

    @Test
    public void tr181_du_225() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("AddObject")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr181_du_226() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("DeleteObject")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr181_du_227() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("Download")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr181_du_228() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("FactoryReset")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr181_du_229() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("GetParameterAttributes")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr181_du_230() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("GetParameterNames")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr181_du_231() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("GetParameterValues")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr181_du_232() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("GetRPCMethods")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr181_du_233() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("Reboot")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr181_du_234() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("SetParameterAttributes")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr181_du_235() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("SetParameterValues")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .assertTableIsNotEmpty("tbl");    //extra spaces obstruct correctly request validation.
    }

    @Test
    public void tr181_du_236() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("Upload")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr181_du_237() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("GetParameterValues")
                .bottomMenu(WAIT_UNTIL_CONNECT)
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr181_du_238() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("GetParameterValues")
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionRpcTasks();
    }

    @Test
    public void tr181_du_239() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(CUSTOM_RPC)
                .selectRPC()
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .verifyRpcDeletion();
    }

    @Test
    public void tr181_du_240() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_HISTORY)
                .assertElementsArePresent("lblEventName", "ddlEvents", "lblShowByDate", "calTo_textBox", "calTo_image")
                .assertTableIsNotEmpty("tblItems")
                .bottomMenu(REFRESH)
                .assertTableIsNotEmpty("tblItems")
                .assertElementsArePresent("lblEventName", "ddlEvents", "lblShowByDate", "calTo_textBox", "calTo_image");
    }

    @Test
    public void tr181_du_241() throws IOException {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_HISTORY)
                .validateHistoryFile();
    }

    @Test
    public void tr181_du_242() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_HISTORY)
                .pause(1000)
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .assertTableIsEmpty("tblItems");
    }

    @Test
    public void tr181_du_243() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PROVISION_MANAGER)
                .editParameterValue()
                .validateEditedProvision();
    }

    @Test
    public void tr181_du_244() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PROVISION_MANAGER)
                .editParameterPriority()
                .validateEditedProvision();
    }

    @Test
    public void tr181_du_245() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PROVISION_MANAGER)
                .bottomMenu(EDIT)
                .selectProvision("Parameters")
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .validateProvisionDeletion();
    }

    @Test   //depends on 238
    public void tr181_du_246() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PROVISION_MANAGER)
                .editProvisionRequest()
                .bottomMenu(START)
                .validateEditedRequest();
    }

    @Test   //depends on 238
    public void tr181_du_247() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PROVISION_MANAGER)
                .bottomMenu(EDIT)
                .editPriority("RPC")
                .validateEditedProvision();
    }

    @Test   //depends on 238
    public void tr181_du_248() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PROVISION_MANAGER)
                .bottomMenu(EDIT)
                .selectProvision("RPC")
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .validateProvisionDeletion();
    }

    //skipped:249 - cannot change object path (deprecated functionality)

    @Test   //bug: depends on 215
    public void tr181_du_250() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PROVISION_MANAGER)
                .bottomMenu(EDIT)
                .editPriority("Objects")
                .validateEditedProvision();
    }

    @Test   //bug: depends on 215
    public void tr181_du_251() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PROVISION_MANAGER)
                .bottomMenu(EDIT)
                .selectProvision("Objects")
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .validateProvisionDeletion();
    }

    @Test   //depends on 206
    public void tr181_du_252() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PROVISION_MANAGER)
                .editProvisionFileUrl()
                .bottomMenu(START)
                .validateEditedProvision();
    }

    @Test   //depends on 206
    public void tr181_du_253() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PROVISION_MANAGER)
                .editProvisionFilePriority()
                .bottomMenu(START)
                .validateEditedProvision();
    }

    @Test   //depends on 206
    public void tr181_du_254() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PROVISION_MANAGER)
                .bottomMenu(EDIT)
                .selectProvision("Download file")
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .validateProvisionDeletion();
    }

    @Test   //precondition actions for Reports tab tests
    public void tr181_du_255() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .changeAccessList()
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(FILE_UPLOAD)
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .bottomMenu(START)
                .okButtonPopUp()
                .deleteFileEntry()
                .okButtonPopUp();
    }
}
