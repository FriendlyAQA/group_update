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
public class DeviceUpdateUspTests extends BaseTestCase {

    /*
    Preconditions:
    1. Before run you should delete all view and custom view (Search) with name like 'usp_du_011', 'usp_du_067' (starts with usp_) etc.;
    2. Devices (emulator) must run, (emulator restart);
    3. At least one active profile for target device must exist (usp_du_134);
    4. Trace for target device must be stopped (usp_du_135);
    5. All files for Download tasks must be added to server.
    */
    @Test
    public void usp_du_001() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void usp_du_002() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(LIST)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void usp_du_003() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertPresenceOfOptions("ddlView", "Default")
                .assertPresenceOfOptions("ddlManuf", "All")
                .assertPresenceOfOptions("ddlModel", "All");
    }

    @Test
    public void usp_du_004() {
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
    public void usp_du_005() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName("Default")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void usp_du_006() {
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
    public void usp_du_007() {
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
    public void usp_du_008() {
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
    public void usp_du_009() {
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
    public void usp_du_010() {
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
    public void usp_du_011() {
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
    public void usp_du_012() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("usp_du_011")
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
    public void usp_du_013() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("usp_du_011")
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
    public void usp_du_014() {
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
    public void usp_du_015() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterModelName(getModelName())
                .createPreconditionsForSorting();
    }

    @Test
    public void usp_du_016() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("Created");
    }

    @Test
    public void usp_du_017() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("Firmware");
    }

    @Test
    public void usp_du_018() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("Latitude");
    }

    @Test
    public void usp_du_019() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("Longitude");
    }

    @Test
    public void usp_du_020() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("Manufacturer");
    }

    @Test
    public void usp_du_021() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("Model name");
    }

    @Test
    public void usp_du_022() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("mycust03");
    }

    @Test
    public void usp_du_023() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("mycust04");
    }

    @Test
    public void usp_du_024() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("mycust05");
    }

    @Test
    public void usp_du_025() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("mycust06");
    }

    @Test
    public void usp_du_026() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("mycust07");
    }

    @Test
    public void usp_du_027() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("mycust08");
    }

    @Test
    public void usp_du_028() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("mycust09");
    }

    @Test
    public void usp_du_029() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("mycust10");
    }

    @Test
    public void usp_du_030() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("OUI");
    }

    @Test
    public void usp_du_031() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("Phone number");
    }

    @Test
    public void usp_du_032() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("Protocol type");
    }

    @Test
    public void usp_du_033() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("Serial");
    }

    @Test
    public void usp_du_034() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("Status");
    }

    @Test
    public void usp_du_035() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("Last connection");
    }

    @Test
    public void usp_du_036() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("User ID");
    }

    @Test
    public void usp_du_037() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("User location");
    }

    @Test
    public void usp_du_038() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("User login");
    }

    @Test
    public void usp_du_039() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("User name");
    }

    @Test
    public void usp_du_040() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("User name")
                .validateSorting("User status");
    }

    @Test
    public void usp_du_041() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("User tag");
    }

    @Test
    public void usp_du_042() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("usp_du_014")
                .validateSorting("Zip");
    }

    @Test
    public void usp_du_043() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertChangingView();
    }

    @Test
    public void usp_du_044() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("usp_du_014")
                .editButton()
                .bottomMenu(CANCEL)
                .assertSelectedViewIs("usp_du_014");
    }

    @Test
    public void usp_du_045() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("usp_du_014")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(GROUP_UPDATE)
                .topMenu(DEVICE_UPDATE)
                .resetView()
                .assertSelectedViewIs("usp_du_014")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp();
    }

    @Test
    public void usp_du_046() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .validateFiltering("Model name");
    }

    @Test
    public void usp_du_047() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .validateFiltering("Manufacturer");
    }

    @Test
    public void usp_du_048() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("usp_du_014")
                .selectView("Default")
                .assertTableColumnAmountIs(7, "tbl");
    }

    @Test
    public void usp_du_049() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("usp_du_011")
                .assertTableColumnAmountIs(2, "tbl");
    }

    @Test
    public void usp_du_050() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("usp_du_014")
                .editButton()
                .bottomMenu(DELETE_VIEW)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "usp_du_014");
    }

    @Test
    public void usp_du_051() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterModelName(getModelName())
                .selectAnyDevice()
                .bottomMenu(DELETE)
                .cancelButtonPopUp()
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .assertAbsenceOfValue();
    }

    @Test
    public void usp_du_052() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .checkRefreshPage();
    }

    @Test
    public void usp_du_053() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(TRACE)
                .inputSerial()
                .bottomMenu(START)
                .okButtonPopUp()
                .openDevice()
                .bottomMenu(SHOW_TRACE)
                .assertTraceWindowIsOpened()/*
                .bottomMenu(STOP_TRACE, 60)
                .okButtonPopUp()
                .leftMenu(LIST)*/;
    }

    @Test
    public void usp_du_054() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORT_TO_CSV)
                .saveFileName()
                .okButtonPopUp();
    }

    @Test
    public void usp_du_055() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORT_TO_XML)
                .saveFileName()
                .okButtonPopUp();
    }

    @Test   //depends on 54, 55
    public void usp_du_056() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORTS)
                .validateSavedExport("csv", "xml");
    }

    @Test   //depends on 54, 55
    public void usp_du_057() {
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
    public void usp_du_058() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(SHOW_ON_MAP)
                .closeMapWindow();
    }

    @Test
    public void usp_du_059() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .assertPresenceOfOptions("ddlView", "Default")
                .assertPresenceOfOptions("ddlSearchOption", "Phone number", "User ID", "User name", "User login"
                        , "User Tag", "Serial Number", "IP address", "MAC address", "Base Station ID", "E-UTRAN Node B ID", "ACS Username")
                .assertElementsArePresent("btnEditView_btn", "btnNewView_btn", "btnDefaultView_btn", "rdSearchExactly", "btnSearch_btn");
    }

    @Test
    public void usp_du_060() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .deleteAllCustomViews()
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
    public void usp_du_061() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName("Default")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void usp_du_062() {
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
    public void usp_du_063() {
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
    public void usp_du_064() {
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
    public void usp_du_065() {
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
    public void usp_du_066() {
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
                .selectCheckbox("rdSearchExactly")
                .searchButton()
                .assertElementsArePresent("tblDeviceInfo");
    }

    @Test
    public void usp_du_067() {
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
    public void usp_du_068() {
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
    public void usp_du_069() {
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
    public void usp_du_070() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 99)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .searchBy("Serial Number")
                .lookFor(getSerial().substring(0, 3))
                .searchButton()
                .validateViewColumns();
    }

    @Test
    public void usp_du_071() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterModelName(getModelName())
                .createPreconditionsForSorting();
    }

    @Test
    public void usp_du_072() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Created");
    }

    @Test
    public void usp_du_073() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
//                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .selectView("usp_du_070")
//                .searchButton()
                .validateSorting("Firmware");
    }

    @Test
    public void usp_du_074() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Latitude");
    }

    @Test
    public void usp_du_075() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Longitude");
    }

    @Test
    public void usp_du_076() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Manufacturer");
    }

    @Test
    public void usp_du_077() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Model name");
    }

    @Test
    public void usp_du_078() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust03");
    }

    @Test
    public void usp_du_079() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust04");
    }

    @Test
    public void usp_du_080() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust05");
    }

    @Test
    public void usp_du_081() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust06");
    }

    @Test
    public void usp_du_082() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust07");
    }

    @Test
    public void usp_du_083() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust08");
    }

    @Test
    public void usp_du_084() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust09");
    }

    @Test
    public void usp_du_085() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust10");
    }

    @Test
    public void usp_du_086() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("OUI");
    }

    @Test
    public void usp_du_087() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Phone number");
    }

    @Test   //bug: sorting by 'Protocol type' failed;
    public void usp_du_088() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Protocol type");
    }

    @Test
    public void usp_du_089() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Serial");
    }

    @Test   //Bug: column "Status" is absent from search view settings list; (bug in STD?)
    public void usp_du_090() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .lookFor(getSerial().substring(0, 1))
                .deselectCheckbox("rdSearchExactly")
                .searchButton()
                .validateSorting("Status");
    }

    @Test
    public void usp_du_091() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Last connection");
    }

    @Test
    public void usp_du_092() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
//                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .selectView("usp_du_070")
//                .searchButton()
                .validateSorting("User ID");
    }

    @Test
    public void usp_du_093() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User location");
    }

    @Test
    public void usp_du_094() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User login");
    }

    @Test
    public void usp_du_095() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User name");
    }

    @Test
    public void usp_du_096() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User status");
    }

    @Test
    public void usp_du_097() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User tag");
    }

    @Test
    public void usp_du_098() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Zip");
    }

    @Test   //bug: after 'Cancel' view doesn't return to previous selected view, Default is selected instead.
    public void usp_du_099() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .editButton()
                .bottomMenu(CANCEL)
                .assertSelectedViewIs("usp_du_070");
    }

    @Test
    public void usp_du_100() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_070")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(GROUP_UPDATE)
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .resetView()
                .assertSelectedViewIs("usp_du_070")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp();
    }

    @Test
    public void usp_du_101() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("usp_du_069")
                .editButton()
                .bottomMenu(DELETE_VIEW)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "usp_du_069");
    }

    @Test
    public void usp_du_102() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Phone number", false);
    }

    @Test
    public void usp_du_103() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Phone number", true);
    }

    @Test
    public void usp_du_104() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User ID", false);
    }

    @Test
    public void usp_du_105() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User ID", true);
    }

    @Test
    public void usp_du_106() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User name", false);
    }

    @Test
    public void usp_du_107() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User name", true);
    }

    @Test
    public void usp_du_108() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User login", false);
    }

    @Test
    public void usp_du_109() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User login", true);
    }

    @Test
    public void usp_du_110() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User Tag", false);
    }

    @Test
    public void usp_du_111() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User Tag", true);
    }

    @Test
    public void usp_du_112() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Serial Number", false);
    }

    @Test
    public void usp_du_113() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Serial Number", true);
    }

    @Test
    public void usp_du_114() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("IP address", false);
    }

    @Test
    public void usp_du_115() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("IP address", true);
    }

    @Test
    public void usp_du_116() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("MAC address", false);
    }

    @Test
    public void usp_du_117() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("MAC address", true);
    }

    //skipped: 118-121 due to impossible to validate (E-UTRAN Node B ID, Base Station ID)

    @Test
    public void usp_du_122() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("ACS Username", false);
    }

    @Test
    public void usp_du_123() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("ACS Username", true);
    }

    @Test
    public void usp_du_124() {
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
    public void usp_du_125() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Serial Number", false);
    }

    @Test
    public void usp_du_126() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .searchBy("Serial Number")
                .lookFor(getPartialSerial(4))
                .deselectCheckbox("rdSearchExactly")
                .searchButton()
                .selectAnyDevice()
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .assertAbsenceOfValue();
    }

    @Test
    public void usp_du_127() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .searchBy("Serial Number")
                .lookFor(getPartialSerial(4))
                .deselectCheckbox("rdSearchExactly")
                .searchButton()
                .saveTable("tbl")
                .bottomMenu(REFRESH)
                .compareTable("tbl");
    }

    @Test
    public void usp_du_128() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .searchBy("Serial Number")
                .lookFor(getPartialSerial(4))
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
    public void usp_du_129() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .searchBy("Serial Number")
                .lookFor(getPartialSerial(4))
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
    public void usp_du_130() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_INFO)
                .recheckStatus();
    }

    @Test
    public void usp_du_131() {
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
    public void usp_du_132() {
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
    public void usp_du_133() {
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
    public void usp_du_134() {
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
    public void usp_du_135() {
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
    public void usp_du_137() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .bottomMenu(PING)
                .assertPingWindowIsOpened();
    }

    @Test
    public void usp_du_138() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .bottomMenu(TRACE_ROUTE)
                .assertTracerouteWindowIsOpened();
    }

//    @Test   //skipped 139: due to absence of REPLACE button for mqtt protocol

    @Test
    public void usp_du_140() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToAnyDevice()
                .bottomMenu(DELETE)
                .cancelButtonPopUp()
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .assertAbsenceOfValue();
    }

    @Test
    public void usp_du_141() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .setAllUserInfo()
                .editAccountInfoLink()
                .clearUserInfo()
                .assertAccountInfoIsClear();
    }

    @Test
    public void usp_du_142() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .listOfMethods()
                .assertMethodIsPresent("Reboot")
                .closePopup();
    }

    //skipped due to Device Settings subtabs don't contain suitable fields to edit

//    @Test
//    public void usp_du_143() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .clearDeviceActivity()
//                .leftMenu(DEVICE_SETTINGS)
//                .bottomMenu(EDIT_SETTINGS)
//                .setParameter("Information", 1)
//                .bottomMenu(SEND_UPDATE)
//                .cancelButtonPopUp()
//                .leftMenu(DEVICE_ACTIVITY)
//                .validateAbsenceTaskWithValue();
//    }
//
//    @Test
//    public void usp_du_144() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .clearDeviceActivity()
//                .leftMenu(DEVICE_SETTINGS)
//                .bottomMenu(EDIT_SETTINGS)
//                .setParameter("Information", 1)
//                .bottomMenu(SEND_UPDATE)
//                .okButtonPopUp()
//                .okButtonPopUp()
//                .leftMenu(DEVICE_ACTIVITY)
//                .validateTasks();
//    }
//
//    @Test
//    public void usp_du_145() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .clearDeviceActivity()
//                .leftMenu(DEVICE_SETTINGS)
//                .bottomMenu(EDIT_SETTINGS)
//                .setParameter("Information", 2)
//                .bottomMenu(SEND_UPDATE)
//                .okButtonPopUp()
//                .okButtonPopUp()
//                .leftMenu(DEVICE_ACTIVITY)
//                .validateTasks();
//    }
//
//    @Test
//    public void usp_du_146() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .clearDeviceActivity()
//                .leftMenu(DEVICE_SETTINGS)
//                .bottomMenu(EDIT_SETTINGS)
//                .setParameter("Information", 99)
//                .bottomMenu(SEND_UPDATE)
//                .okButtonPopUp()
//                .okButtonPopUp()
//                .leftMenu(DEVICE_ACTIVITY)
//                .validateTasks();
//    }
//
//    @Test
//    public void usp_du_147() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .clearDeviceActivity()
//                .leftMenu(DEVICE_SETTINGS)
//                .bottomMenu(EDIT_SETTINGS)
//                .setParameter("Information", 1)
//                .bottomMenu(WAIT_UNTIL_CONNECT)
//                .bottomMenu(SEND_UPDATE)
//                .okButtonPopUp()
//                .okButtonPopUp()
//                .leftMenu(DEVICE_ACTIVITY)
//                .validateTasks();
//    }
//
//    @Test
//    public void usp_du_148() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .clearDeviceActivity()
//                .clearProvisionManager()
//                .leftMenu(DEVICE_SETTINGS)
//                .pause(1000)
//                .bottomMenu(EDIT_SETTINGS)
//                .setParameter("Information", 1)
//                .bottomMenu(ADD_TO_PROVISION)
//                .bottomMenu(SEND_UPDATE)
//                .okButtonPopUp()
//                .okButtonPopUp()
//                .leftMenu(DEVICE_ACTIVITY)
//                .validateTasks()
//                .leftMenu(PROVISION_MANAGER)
//                .validateProvisionTasks();
//    }
//
//    @Test   //bug: 'Get parameter names' and 'Get parameter attributes' aren't generated in Device Activity
//    public void usp_du_149() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .clearDeviceActivity()
//                .leftMenu(DEVICE_SETTINGS)
//                .storePath()
//                .bottomMenu(GET_CURRENT_SETTINGS)
//                .okButtonPopUp()
//                .leftMenu(DEVICE_ACTIVITY)
//                .validateGeneratedGets();
//    }

    @Test
    public void usp_du_150() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(ADVANCED_VIEW)
                .validateObjectTree();
    }

    @Test
    public void usp_du_151() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter(null, 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void usp_du_152() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(ADVANCED_VIEW)
                .selectBranch("Device.Location.1")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter(null, 1)
                .bottomMenu(WAIT_UNTIL_CONNECT)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void usp_du_153() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .clearDeviceActivity()
                .leftMenu(ADVANCED_VIEW)
                .selectBranch("Device.DeviceInfo.FirmwareImage.0")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter(null, 1)
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
    public void usp_du_154() {
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
    public void usp_du_155() {
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
    public void usp_du_156() throws IOException {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(ADVANCED_VIEW)
                .validateCsvFile();
    }

    @Test
    public void usp_du_157() {
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
    public void usp_du_158() throws IOException {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(DEVICE_HISTORY)
                .validateHistoryFile();
    }

    @Test
    public void usp_du_159() {
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
    public void usp_du_160() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PROVISION_MANAGER)
                .editParameterValue()
                .validateEditedProvision();
    }

    @Test
    public void usp_du_161() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .openDevice()
                .leftMenu(PROVISION_MANAGER)
                .editParameterPriority()
                .validateEditedProvision();
    }

    @Test
    public void usp_du_162() {
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
}
