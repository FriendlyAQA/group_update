package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.pageobject.DeviceUpdatePage;
import com.friendly.aqa.utils.CalendarUtil;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.friendly.aqa.entities.TopMenu.*;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.GlobalButtons.*;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.GlobalButtons.SEARCH_EXPORT_TO_XML;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.Left.*;

@Listeners(UniversalVideoListener.class)
public class DeviceUpdateTR069Tests extends BaseTestCase {

    /*
    Preconditions:
    1. Before run you should delete all view and custom view (Search) with name like 'tr069_du_011', 'tr069_du_067' (starts with tr069_) etc.;
    2. Devices (emul) must run.
    */
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
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .bottomMenu(NEXT)
                .bottomMenu(CANCEL)
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
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void tr069_du_006() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertInputHasText("txtName", getTestName())
                .bottomMenu(NEXT)
                .setViewColumns(1, 1)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertCheckboxesAreSelected("tblFilter", true, 1, 1)
                .assertCheckboxesAreSelected("tblFilter", false, 1, -2);
    }

    @Test
    public void tr069_du_007() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setViewColumns(1, 1)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .setViewColumns(1, 1)
                .setViewColumns(2, 2)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertCheckboxesAreSelected("tblFilter", true, 1, 2)
                .assertCheckboxesAreSelected("tblFilter", false, 1, 1, -3);
    }

    @Test
    public void tr069_du_008() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setViewColumns(2, 2)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .setViewColumns(3, 4)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertCheckboxesAreSelected("tblFilter", true, 1, 2, 3, 4)
                .assertCheckboxesAreSelected("tblFilter", false, 1, 1, -5);
    }

    @Test
    public void tr069_du_009() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setViewColumns(1, 2)
                .bottomMenu(NEXT)
                .clickOnTable("tbl", 1, 0)
                .downButton()
                .saveTable("tbl")
                .bottomMenu(PREVIOUS)
                .bottomMenu(NEXT)
                .compareTable("tbl");
    }

    @Test
    public void tr069_du_010() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setViewColumns(1, 3)
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
    public void tr069_du_011() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setViewColumns("Model name")
                .bottomMenu(NEXT)
                .addFilter()
                .selectColumnFilter("Model name")
                .selectCompare("=")
                .selectFilterModelName(BasePage.getModelName())
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
    public void tr069_du_012() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr069_du_011")
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
                .assertColumnHasSingleValue("Model name", BasePage.getModelName());
    }

    @Test   //depends on 11, 12
    public void tr069_du_013() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr069_du_011")
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
    public void tr069_du_014() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setViewColumns(1, 99)
                .bottomMenu(FINISH)
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
                .bottomMenu(CANCEL)
                .assertSelectedViewIs("tr069_du_014");
    }

    @Test
    public void tr069_du_045() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("tr069_du_014")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(GROUP_UPDATE)
                .topMenu(DEVICE_UPDATE)
                .resetView()
                .assertSelectedViewIs("tr069_du_014")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
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
                .bottomMenu(DELETE_GROUP)
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
                .bottomMenu(DELETE)
                .cancelButtonPopUp()
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .assertAbsenceOfValue();
    }

    @Test
    public void tr069_du_052() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .saveTable("tbl")
                .bottomMenu(REFRESH)
                .assertPageWasRefreshed();
    }

    @Test
    public void tr069_du_053() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(TRACE)
                .inputSerial()
                .bottomMenu(START)
                .okButtonPopUp()
                .enterToDevice()
                .bottomMenu(SHOW_TRACE)
                .assertTraceWindowIsOpened()
                .bottomMenu(STOP_TRACE)
                .okButtonPopUp()/*
                .leftMenu(LIST)*/;
    }

    @Test
    public void tr069_du_054() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORT_TO_CSV)
                .saveFileName()
                .okButtonPopUp();
    }

    @Test
    public void tr069_du_055() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORT_TO_XML)
                .saveFileName()
                .okButtonPopUp();
    }

    @Test   //depends on 54, 55
    public void tr069_du_056() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORTS)
                .checkSavedExport("csv", "xml");
    }

    @Test   //depends on 54, 55
    public void tr069_du_057() {
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
    public void tr069_du_058() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(SHOW_ON_MAP)
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
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertInputHasText("txtName", getTestName())
                .bottomMenu(NEXT)
                .bottomMenu(CANCEL)
                .assertPresenceOfElements("btnEditView_btn", "btnNewView_btn", "btnDefaultView_btn", "rdSearchExactly", "btnSearch_btn");
    }

    @Test
    public void tr069_du_061() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName("Default")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void tr069_du_062() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertInputHasText("txtName", getTestName())
                .bottomMenu(NEXT)
                .setViewColumns(1, 1)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
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
                .bottomMenu(NEXT)
                .setViewColumns(1, 1)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .setViewColumns(1, 1)
                .setViewColumns(2, 2)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
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
                .bottomMenu(NEXT)
                .setViewColumns(2, 2)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .setViewColumns(3, 4)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
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
                .bottomMenu(NEXT)
                .setViewColumns(1, 2)
                .bottomMenu(NEXT)
                .clickOnTable("tbl", 1, 0)
                .downButton()
                .saveTable("tbl")
                .bottomMenu(PREVIOUS)
                .bottomMenu(NEXT)
                .compareTable("tbl");
    }

    @Test
    public void tr069_du_066() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setViewColumns(1, 3)
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
                .assertPresenceOfElements("tblDeviceInfo");
    }

    @Test
    public void tr069_du_067() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setViewColumns(1, 99)
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
    public void tr069_du_068() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setViewColumns(1, 99)
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
    public void tr069_du_069() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setViewColumns(1, 99)
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
    public void tr069_du_070() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .setViewColumns(1, 99)
                .bottomMenu(FINISH)
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
//                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .selectView("tr069_du_070")
//                .searchButton()
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
//                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .selectView("tr069_du_070")
//                .searchButton()
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
                .selectView("tr069_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .checkSorting("Zip");
    }

    @Test   //bug: after 'Cancel' view doesn't return to previous selected view, Default is selected instead.
    public void tr069_du_099() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_070")
                .editButton()
                .bottomMenu(CANCEL)
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
                .bottomMenu(FINISH)
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
                .bottomMenu(FINISH)
                .okButtonPopUp();
    }

    @Test
    public void tr069_du_101() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("tr069_du_069")
                .editButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "tr069_du_069");
    }

    @Test
    public void tr069_du_102() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Phone number", false);
    }

    @Test
    public void tr069_du_103() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Phone number", true);
    }

    @Test
    public void tr069_du_104() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User ID", false);
    }

    @Test
    public void tr069_du_105() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User ID", true);
    }

    @Test
    public void tr069_du_106() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Full name", false);
    }

    @Test
    public void tr069_du_107() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Full name", true);
    }

    @Test
    public void tr069_du_108() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Username", false);
    }

    @Test
    public void tr069_du_109() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Username", true);
    }

    @Test
    public void tr069_du_110() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User Tag", false);
    }

    @Test
    public void tr069_du_111() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User Tag", true);
    }

    @Test
    public void tr069_du_112() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Serial Number", false);
    }

    @Test
    public void tr069_du_113() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Serial Number", true);
    }

    @Test
    public void tr069_du_114() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("IP address", false);
    }

    @Test
    public void tr069_du_115() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("IP address", true);
    }

    @Test
    public void tr069_du_116() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("MAC address", false);
    }

    @Test
    public void tr069_du_117() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("MAC address", true);
    }

    //skipped: 118-121 due to impossible to validate (E-UTRAN Node B ID, Base Station ID)

    @Test
    public void tr069_du_122() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("ACS Username", false);
    }

    @Test
    public void tr069_du_123() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("ACS Username", true);
    }

    @Test
    public void tr069_du_124() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .searchBy("Serial Number")
                .lookFor(getSerial())
                .deselectCheckbox("rdSearchExactly")
                .searchButton()
                .assertTransferToDeviceInfo();
    }

    @Test
    public void tr069_du_125() {
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
    public void tr069_du_126() {
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
                .assertAbsenceOfValue();
    }

    @Test
    public void tr069_du_127() {
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
    public void tr069_du_128() {
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
                .checkSavedExport("csv");
    }

    @Test
    public void tr069_du_129() {
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
                .checkSavedExport("xml");
    }

    @Test
    public void tr069_du_130() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_INFO)
                .recheckStatus();
    }

    @Test
    public void tr069_du_131() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_132() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_INFO)
                .bottomMenu(FACTORY_RESET)
                .cancelButtonPopUp()
                .bottomMenu(FACTORY_RESET)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .assertLastActivityIs("Reset to factory defaults");
    }

    @Test   //bug: task doesn't created in list
    public void tr069_du_133() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_INFO)
                .bottomMenu(CREATE_TEMPLATE)
                .cancelButtonPopUp()
                .bottomMenu(CREATE_TEMPLATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .assertLastActivityIs("Create template");//TODO validate via DB??
    }

    @Test
    public void tr069_du_134() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_135() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .bottomMenu(START_TRACE)
                .okButtonPopUp()
                .bottomMenu(SHOW_TRACE)
                .assertTraceWindowIsOpened()
                .bottomMenu(SHOW_TRACE)
                .assertTraceWindowIsOpened();
    }

    //skipped: 136 (functional implemented in 135)


    @Test
    public void tr069_du_137() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .bottomMenu(PING)
                .assertPingWindowIsOpened();
    }

    @Test
    public void tr069_du_138() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .bottomMenu(TRACE_ROUTE)
                .assertTracerouteWindowIsOpened();
    }

    @Test
    public void tr069_du_139() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .bottomMenu(REPLACE)
                .assertReplaceWindowIsOpened();
    }

    @Test
    public void tr069_du_140() {
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
    public void tr069_du_141() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .setAllUserInfo()
                .editAccountInfoLink()
                .clearUserInfo()
                .assertAccountInfoIsClear();
    }

    @Test
    public void tr069_du_142() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .listOfMethods()
                .assertMethodIsPresent("SetParameterValues")
                .closePopup();
    }

    @Test
    public void tr069_du_143() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .networkMap()
                .assertMapIsPresent()
                .closePopup();
    }

    @Test
    public void tr069_du_144() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformInterval, sec", "61")
                .bottomMenu(SEND_UPDATE)
                .cancelButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateAbsenceTaskWithValue("61");
    }

    @Test
    public void tr069_du_145() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformTime", CalendarUtil.getDbShiftedDate(0))
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_146() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformInterval, sec", "61")
                .setParameter("PeriodicInformTime", CalendarUtil.getDbShiftedDate(0))
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_147() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformInterval, sec", "62")  //cannot set all params on Management tab due to the possibility of device disconnecting;
                .setParameter("PeriodicInformTime", CalendarUtil.getDbShiftedDate(0))
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_148() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformTime", CalendarUtil.getDbShiftedDate(0))
                .bottomMenu(WAIT_UNTIL_CONNECT)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_149() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .clearProvisionManager()
                .leftMenu(DEVICE_SETTINGS)
                .pause(1000)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformTime", CalendarUtil.getDbShiftedDate(0))
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
    public void tr069_du_150() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .storePath()
                .bottomMenu(GET_CURRENT_SETTINGS)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateGeneratedGets();
    }

    @Test
    public void tr069_du_151() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_152() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_153() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_154() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_155() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_156() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_157() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_158() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_159() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DSL settings", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_160() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DSL settings", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_161() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DSL settings", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_162() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .pause(1000)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("DSL settings", 1)
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
    public void tr069_du_163() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("WAN", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_164() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("WAN", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_165() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("WAN", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_166() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .pause(1000)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("WAN", 1)
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
    public void tr069_du_169() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("LAN", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_170() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("LAN", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_171() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("LAN", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_172() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .pause(1000)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("LAN", 1)
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
    public void tr069_du_173() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .selectTab("Wireless")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter(null, 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_174() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .selectTab("Wireless")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter(null, 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_175() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .selectTab("Wireless")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter(null, 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_176() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .selectTab("Wireless")
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
    public void tr069_du_177() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .selectTab("VoIP settings")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter(null, 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_178() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .selectTab("VoIP settings")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter(null, 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_179() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .selectTab("VoIP settings")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter(null, 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_180() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .selectTab("VoIP settings")
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
    public void tr069_du_181() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DeviceUpdatePage.Left.ADVANCED_VIEW)
                .validateObjectTree();
    }

    @Test
    public void tr069_du_182() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DeviceUpdatePage.Left.ADVANCED_VIEW)
                .selectBranch("ManagementServer")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformTime", CalendarUtil.getDbShiftedDate(0))
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_183() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DeviceUpdatePage.Left.ADVANCED_VIEW)
                .selectBranch("ManagementServer")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformTime", CalendarUtil.getDbShiftedDate(0))
                .bottomMenu(WAIT_UNTIL_CONNECT)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void tr069_du_184() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DeviceUpdatePage.Left.ADVANCED_VIEW)
                .selectBranch("ManagementServer")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("PeriodicInformTime", CalendarUtil.getDbShiftedDate(0))
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
    public void tr069_du_185() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DeviceUpdatePage.Left.ADVANCED_VIEW)
                .bottomMenu(EDIT_TREE)
                .selectTreeObject(true, 0)
                .bottomMenu(STORE_TREE)
                .okButtonPopUp();   //is validation needed??? How???
    }

    @Test
    public void tr069_du_186() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DeviceUpdatePage.Left.ADVANCED_VIEW)
                .bottomMenu(EDIT_TREE)
                .bottomMenu(CLEAR_TREE)
                .bottomMenu(STORE_TREE)
                .okButtonPopUp();   //is validation needed??? How???
    }

    @Test
    public void tr069_du_187() throws IOException {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DeviceUpdatePage.Left.ADVANCED_VIEW)
                .bottomMenu(SAVE_PARAMETERS)
                .validateCsvFile();
    }

    @Test
    public void tr069_du_188() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_189() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_DOWNLOAD)
                .selectDownloadFileType("Firmware Image")
                .fillUrl()
                .bottomMenu(START)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks();
    }

    @Test
    public void tr069_du_190() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_191() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_DOWNLOAD)
                .selectDownloadFileType("Vendor Configuration File")
                .fillUrl()
                .bottomMenu(START)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks();
    }

    //skipped: 192 - cannot select File name dropdown;


    @Test
    public void tr069_du_193() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_194() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_195() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_196() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_197() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_UPLOAD)
                .selectUploadFileType("Vendor Log File")
                .defaultUploadRadioButton()
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateUploadFileTasks();
    }

    @Test
    public void tr069_du_198() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_UPLOAD)
                .selectUploadFileType("Vendor Log File")
                .manualUrlRButton()
                .fillUploadUrl()
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks();
    }

    @Test
    public void tr069_du_199() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_200() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(PORT_MAPPING)
                .storePortMappingPath()
                .bottomMenu(GET_CURRENT_PORTS)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateGeneratedGets();
    }

    @Test
    public void tr069_du_201() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
//                .clearDeviceActivity()
                .leftMenu(PORT_MAPPING)
                .bottomMenu(ADD_PORT)
                .fillAddPortFields("TCP/UDP")
                .bottomMenu(START)
                .okButtonPopUp()
                .validatePortCreating();
    }

    @Test
    public void tr069_du_202() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PORT_MAPPING)
                .bottomMenu(ADD_PORT)
                .fillAddPortFields("TCP/UDP")
                .bottomMenu(WAIT_UNTIL_CONNECT)
                .bottomMenu(START)
                .okButtonPopUp()
                .validatePortCreating();
    }

    @Test
    public void tr069_du_203() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PORT_MAPPING)
                .bottomMenu(ADD_PORT)
                .fillAddPortFields("TCP/UDP")
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(START)
                .okButtonPopUp()
                .validatePortCreating();
    }

    @Test
    public void tr069_du_204() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PORT_MAPPING)
                .bottomMenu(ADD_PORT)
                .fillAddPortFields("TCP")
                .bottomMenu(START)
                .okButtonPopUp()
                .validatePortCreating();
    }

    @Test
    public void tr069_du_205() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PORT_MAPPING)
                .bottomMenu(ADD_PORT)
                .fillAddPortFields("UDP")
                .bottomMenu(START)
                .okButtonPopUp()
                .validatePortCreating();
    }

    @Test
    public void tr069_du_206() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PORT_MAPPING)
                .selectPort()
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .verifyPortDeletion();
    }

    @Test   //bug: 	MP252 doesn't support Trace diagnostic
    public void tr069_du_207() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_DIAGNOSTIC)
                .deleteAllDiagnostics()
                .createDiagnostic("Trace diagnostic")
                .bottomMenu(START)
                .okButtonPopUp()
                .validateDiagnosticCreation();
    }

    @Test
    public void tr069_du_208() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_DIAGNOSTIC)
                .deleteAllDiagnostics()
                .createDiagnostic("IPPing diagnostics")
                .bottomMenu(START)
                .okButtonPopUp()
                .validateDiagnosticCreation();
    }

    @Test   //bug: 	MP252 doesn't support download Diagnostic
    public void tr069_du_209() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_DIAGNOSTIC)
                .deleteAllDiagnostics()
                .createDiagnostic("Download diagnostic")
                .bottomMenu(START)
                .okButtonPopUp()
                .validateDiagnosticCreation();
    }

    @Test   //bug: 	MP252 doesn't support Upload diagnostics
    public void tr069_du_210() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_DIAGNOSTIC)
                .deleteAllDiagnostics()
                .createDiagnostic("Upload diagnostics")
                .bottomMenu(START)
                .okButtonPopUp()
                .validateDiagnosticCreation();
    }

    @Test   //bug: 	MP252 doesn't support NSLoopback diagnostics
    public void tr069_du_211() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_DIAGNOSTIC)
                .deleteAllDiagnostics()
                .createDiagnostic("NSLoopback diagnostics")
                .bottomMenu(START)
                .okButtonPopUp()
                .validateDiagnosticCreation();
    }

    @Test   //bug: 	MP252 doesn't support Wi-Fi neighboring diagnostics
    public void tr069_du_212() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_DIAGNOSTIC)
                .deleteAllDiagnostics()
                .createDiagnostic("Wi-Fi neighboring diagnostics")
                .bottomMenu(START)
                .okButtonPopUp()
                .validateDiagnosticCreation();
    }

    @Test   //bug: 	MP252 doesn't support DSL diagnostics
    public void tr069_du_213() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_DIAGNOSTIC)
                .deleteAllDiagnostics()
                .createDiagnostic("DSL diagnostics")
                .bottomMenu(START)
                .okButtonPopUp()
                .validateDiagnosticCreation();
    }

    @Test   //bug: 	MP252 doesn't support NSlookup diagnostics
    public void tr069_du_214() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_DIAGNOSTIC)
                .deleteAllDiagnostics()
                .createDiagnostic("NSlookup diagnostics")
                .bottomMenu(START)
                .okButtonPopUp()
                .validateDiagnosticCreation();
    }

    @Test
    public void tr069_du_215() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("AddObject")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr069_du_216() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("DeleteObject")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr069_du_217() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("Download")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr069_du_218() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("FactoryReset")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr069_du_219() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("GetParameterAttributes")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr069_du_220() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("GetParameterNames")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr069_du_221() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("GetParameterValues")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr069_du_222() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("GetRPCMethods")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr069_du_223() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("Reboot")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr069_du_224() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("SetParameterAttributes")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr069_du_225() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("SetParameterValues")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .assertTableHasContent("tblParameters");    //extra spaces obstruct correctly request validation.
    }

    @Test
    public void tr069_du_226() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(CUSTOM_RPC)
                .selectMethod("Upload")
                .bottomMenu(START)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateCustomRpcTasks();
    }

    @Test
    public void tr069_du_227() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_228() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void tr069_du_229() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(CUSTOM_RPC)
                .selectRPC()
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .verifyRpcDeletion();
    }

    @Test
    public void tr069_du_230() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_HISTORY)
                .assertElementsArePresent("lblEventName", "ddlEvents", "lblShowByDate", "calTo_textBox", "calTo_image")
                .assertTableHasContent("tblItems")
                .bottomMenu(REFRESH)
                .assertTableHasContent("tblItems")
                .assertElementsArePresent("lblEventName", "ddlEvents", "lblShowByDate", "calTo_textBox", "calTo_image");
    }

    @Test
    public void tr069_du_231() throws IOException {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_HISTORY)
                .bottomMenu(SAVE)
                .validateHistoryFile();
    }

    @Test
    public void tr069_du_232() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_HISTORY)
                .pause(1000)
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .assertTableIsEmpty("tblItems");
    }

    @Test
    public void tr069_du_233() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .editParameterValue()
                .validateEditedProvision();
    }

    @Test
    public void tr069_du_234() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .editParameterPriority()
                .validateEditedProvision();
    }

    @Test
    public void tr069_du_235() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .bottomMenu(EDIT)
                .selectProvision("Parameters")
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .validateProvisionDeletion();
    }

    @Test   //depends on 228
    public void tr069_du_236() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .editProvisionRequest()
                .bottomMenu(START)
                .validateEditedRequest();
    }

    @Test   //depends on 228
    public void tr069_du_237() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .bottomMenu(EDIT)
                .editPriority("RPC")
                .validateEditedProvision();
    }

    @Test   //depends on 228
    public void tr069_du_238() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .bottomMenu(EDIT)
                .selectProvision("RPC")
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .validateProvisionDeletion();
    }

    //skipped:229 - cannot change object path;

    @Test   //depends on 203
    public void tr069_du_240() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .bottomMenu(EDIT)
                .editPriority("Objects")
                .validateEditedProvision();
    }

    @Test   //depends on 203
    public void tr069_du_241() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .bottomMenu(EDIT)
                .selectProvision("Objects")
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .validateProvisionDeletion();
    }

    @Test   //depends on 194
    public void tr069_du_242() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .editProvisionFileUrl()
                .bottomMenu(START)
                .validateEditedProvision();
    }

    @Test   //depends on 194
    public void tr069_du_243() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .editProvisionFilePriority()
                .bottomMenu(START)
                .validateEditedProvision();
    }

    @Test   //depends on 194
    public void tr069_du_244() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .bottomMenu(EDIT)
                .selectProvision("Download file")
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .validateProvisionDeletion();
    }
}
