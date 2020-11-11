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
public class DeviceUpdateLwm2mTests extends BaseTestCase {

    /*
    Preconditions:
    1. Before run you should delete all view and custom view (Search) with name like 'lwm2m_du_011', 'lwm2m_du_067' (starts with lwm2m_) etc.;
    2. Devices (emul) must run, (emul restart);
    3. Trace for target device must be stopped (lwm2m_du_135);
    4. At least one active profile for target device must exist (lwm2m_du_134);
    5. All files for Download tasks must be added to server.
    */
    @Test
    public void lwm2m_du_001() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void lwm2m_du_002() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(LIST)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void lwm2m_du_003() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertPresenceOfOptions("ddlView", "Default")
                .assertPresenceOfOptions("ddlManuf", "All")
                .assertPresenceOfOptions("ddlModel", "All");
    }

    @Test
    public void lwm2m_du_004() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .deleteAllCustomViews()   //as precondition for next step and tests
                .newViewButton()
                .fillName()
                .assertButtonsAreEnabled(false, PREVIOUS, FINISH)
                .assertButtonsAreEnabled(true, CANCEL, NEXT)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertInputHasText("txtName", getTestName())
                .bottomMenu(NEXT)
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed()
                .assertButtonsAreEnabled(false, DELETE)
                .assertButtonsAreEnabled(true, REFRESH, TRACE, EXPORT_TO_CSV, EXPORT_TO_XML, EXPORTS, SHOW_ON_MAP)
                .assertAbsenceOfOptions("ddlView", getTestName());
    }

    @Test
    public void lwm2m_du_005() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName("Default")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void lwm2m_du_006() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertInputHasText("txtName", getTestName())
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 1)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertCheckboxesAreSelected("tblFilter", true, 1, 1)
                .assertCheckboxesAreSelected("tblFilter", false, 1, -2);
    }

    @Test
    public void lwm2m_du_007() {
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
    public void lwm2m_du_008() {
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
    public void lwm2m_du_009() {
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
    public void lwm2m_du_010() {
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
    public void lwm2m_du_011() {
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
    public void lwm2m_du_012() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("lwm2m_du_011")
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
    public void lwm2m_du_013() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("lwm2m_du_011")
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
    public void lwm2m_du_014() {
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
    public void lwm2m_du_015() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterModelName(getModelName())
                .createPreconditionsForSorting();
    }

    @Test
    public void lwm2m_du_016() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("Created");
    }

    @Test
    public void lwm2m_du_017() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("Firmware");
    }

    @Test
    public void lwm2m_du_018() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("Latitude");
    }

    @Test
    public void lwm2m_du_019() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("Longitude");
    }

    @Test
    public void lwm2m_du_020() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("Manufacturer");
    }

    @Test
    public void lwm2m_du_021() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("Model name");
    }

    @Test
    public void lwm2m_du_022() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("mycust03");
    }

    @Test
    public void lwm2m_du_023() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("mycust04");
    }

    @Test
    public void lwm2m_du_024() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("mycust05");
    }

    @Test
    public void lwm2m_du_025() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("mycust06");
    }

    @Test
    public void lwm2m_du_026() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("mycust07");
    }

    @Test
    public void lwm2m_du_027() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("mycust08");
    }

    @Test
    public void lwm2m_du_028() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("mycust09");
    }

    @Test
    public void lwm2m_du_029() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("mycust10");
    }

    @Test
    public void lwm2m_du_030() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("OUI");
    }

    @Test
    public void lwm2m_du_031() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("Phone number");
    }

    @Test
    public void lwm2m_du_032() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("Protocol type");
    }

    @Test   //bug: Serials with underlines cause incorrect sorting
    public void lwm2m_du_033() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("Serial");
    }

    @Test
    public void lwm2m_du_034() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("Status");
    }

    @Test
    public void lwm2m_du_035() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("Last connection");
    }

    @Test
    public void lwm2m_du_036() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("User ID");
    }

    @Test
    public void lwm2m_du_037() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("User location");
    }

    @Test
    public void lwm2m_du_038() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("User login");
    }

    @Test
    public void lwm2m_du_039() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("User name");
    }

    @Test
    public void lwm2m_du_040() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("User name")
                .validateSorting("User status");
    }

    @Test
    public void lwm2m_du_041() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("User tag");
    }

    @Test
    public void lwm2m_du_042() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("lwm2m_du_014")
                .validateSorting("Zip");
    }

    @Test
    public void lwm2m_du_043() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertChangingView();
    }

    @Test
    public void lwm2m_du_044() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("lwm2m_du_014")
                .editButton()
                .bottomMenu(CANCEL)
                .assertSelectedViewIs("lwm2m_du_014");
    }

    @Test
    public void lwm2m_du_045() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("lwm2m_du_014")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(GROUP_UPDATE)
                .topMenu(DEVICE_UPDATE)
                .resetView()
                .assertSelectedViewIs("lwm2m_du_014")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp();
    }

    @Test
    public void lwm2m_du_046() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .validateFiltering("Model name");
    }

    @Test
    public void lwm2m_du_047() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .validateFiltering("Manufacturer");
    }

    @Test
    public void lwm2m_du_048() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("lwm2m_du_014")
                .selectView("Default")
                .assertTableColumnAmountIs(7, "tbl");
    }

    @Test
    public void lwm2m_du_049() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("lwm2m_du_011")
                .assertTableColumnAmountIs(2, "tbl");
    }

    @Test
    public void lwm2m_du_050() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("lwm2m_du_014")
                .editButton()
                .bottomMenu(DELETE_VIEW)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "lwm2m_du_014");
    }

    @Test
    public void lwm2m_du_051() {
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
    public void lwm2m_du_052() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .checkRefreshPage();
    }

    @Test
    public void lwm2m_du_053() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(TRACE)
                .inputSerial()
                .bottomMenu(START)
                .okButtonPopUp()
                .enterToDevice()
                .bottomMenu(SHOW_TRACE)
                .assertTraceWindowIsOpened()/*
                .bottomMenu(STOP_TRACE, 60)
                .okButtonPopUp()
                .leftMenu(LIST)*/;
    }

    @Test
    public void lwm2m_du_054() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORT_TO_CSV)
                .saveFileName()
                .okButtonPopUp();
    }

    @Test
    public void lwm2m_du_055() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORT_TO_XML)
                .saveFileName()
                .okButtonPopUp();
    }

    @Test   //depends on 54, 55
    public void lwm2m_du_056() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORTS)
                .validateSavedExport("csv", "xml");
    }

    @Test   //depends on 54, 55
    public void lwm2m_du_057() {
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
    public void lwm2m_du_058() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(SHOW_ON_MAP)
                .closeMapWindow();
    }

    @Test
    public void lwm2m_du_059() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .assertPresenceOfOptions("ddlView", "Default")
                .assertPresenceOfOptions("ddlSearchOption", "Phone number", "User ID", "User name", "User login"
                        , "User Tag", "Serial Number", "IP address", "MAC address", "Base Station ID", "E-UTRAN Node B ID", "ACS Username")
                .assertElementsArePresent("btnEditView_btn", "btnNewView_btn", "btnDefaultView_btn", "rdSearchExactly", "btnSearch_btn");
    }

    @Test
    public void lwm2m_du_060() {
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
                .assertElementsArePresent("btnEditView_btn", "btnNewView_btn", "btnDefaultView_btn", "rdSearchExactly", "btnSearch_btn");
    }

    @Test
    public void lwm2m_du_061() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName("Default")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void lwm2m_du_062() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName()
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertInputHasText("txtName", getTestName())
                .bottomMenu(NEXT)
                .setVisibleColumns(1, 1)
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .assertCheckboxesAreSelected("tblFilter", true, 1, 1)
                .assertCheckboxesAreSelected("tblFilter", false, 1, -2);
    }

    @Test
    public void lwm2m_du_063() {
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
    public void lwm2m_du_064() {
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
    public void lwm2m_du_065() {
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
    public void lwm2m_du_066() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
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
                .lookFor(getSerial().substring(0, 3))
                .searchButton()
                .assertElementsArePresent("tbl");// TODO validate columns order
    }

    @Test
    public void lwm2m_du_067() {
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
                .sortingOrder("Unsorted")
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .searchBy("Serial Number")
                .lookFor(getSerial().substring(0, 3))
                .searchButton()
                .assertSortingPerformedBy("Created", null);
    }

    @Test
    public void lwm2m_du_068() {
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
    public void lwm2m_du_069() {
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
    public void lwm2m_du_070() {
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
    public void lwm2m_du_071() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterModelName(getModelName())
                .createPreconditionsForSorting();
    }

    @Test
    public void lwm2m_du_072() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Created");
    }

    @Test
    public void lwm2m_du_073() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
//                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .selectView("lwm2m_du_070")
//                .searchButton()
                .validateSorting("Firmware");
    }

    @Test
    public void lwm2m_du_074() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Latitude");
    }

    @Test
    public void lwm2m_du_075() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Longitude");
    }

    @Test
    public void lwm2m_du_076() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Manufacturer");
    }

    @Test
    public void lwm2m_du_077() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Model name");
    }

    @Test
    public void lwm2m_du_078() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust03");
    }

    @Test
    public void lwm2m_du_079() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust04");
    }

    @Test
    public void lwm2m_du_080() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust05");
    }

    @Test
    public void lwm2m_du_081() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust06");
    }

    @Test
    public void lwm2m_du_082() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust07");
    }

    @Test
    public void lwm2m_du_083() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust08");
    }

    @Test
    public void lwm2m_du_084() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust09");
    }

    @Test
    public void lwm2m_du_085() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust10");
    }

    @Test
    public void lwm2m_du_086() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("OUI");
    }

    @Test
    public void lwm2m_du_087() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Phone number");
    }

    @Test   //bug: sorting by 'Protocol type' failed;
    public void lwm2m_du_088() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Protocol type");
    }

    @Test   //bug: Serials with underlines cause incorrect sorting
    public void lwm2m_du_089() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Serial");
    }

    @Test
    public void lwm2m_du_090() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .lookFor(getSerial().substring(0, 1))
                .deselectCheckbox("rdSearchExactly")
                .searchButton()
                .validateSorting("Status");
    }

    @Test
    public void lwm2m_du_091() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Last connection");
    }

    @Test
    public void lwm2m_du_092() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
//                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .selectView("lwm2m_du_070")
//                .searchButton()
                .validateSorting("User ID");
    }

    @Test
    public void lwm2m_du_093() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User location");
    }

    @Test
    public void lwm2m_du_094() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User login");
    }

    @Test
    public void lwm2m_du_095() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User name");
    }

    @Test
    public void lwm2m_du_096() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User status");
    }

    @Test
    public void lwm2m_du_097() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User tag");
    }

    @Test
    public void lwm2m_du_098() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Zip");
    }

    @Test   //bug: after 'Cancel' view doesn't return to previous selected view, Default is selected instead.
    public void lwm2m_du_099() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .editButton()
                .bottomMenu(CANCEL)
                .assertSelectedViewIs("lwm2m_du_070");
    }

    @Test
    public void lwm2m_du_100() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_070")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(GROUP_UPDATE)
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .resetView()
                .assertSelectedViewIs("lwm2m_du_070")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp();
    }

    @Test
    public void lwm2m_du_101() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("lwm2m_du_069")
                .editButton()
                .bottomMenu(DELETE_VIEW)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "lwm2m_du_069");
    }

    @Test
    public void lwm2m_du_102() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Phone number", false);
    }

    @Test
    public void lwm2m_du_103() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Phone number", true);
    }

    @Test
    public void lwm2m_du_104() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User ID", false);
    }

    @Test
    public void lwm2m_du_105() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User ID", true);
    }

    @Test
    public void lwm2m_du_106() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User name", false);
    }

    @Test
    public void lwm2m_du_107() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User name", true);
    }

    @Test
    public void lwm2m_du_108() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User login", false);
    }

    @Test
    public void lwm2m_du_109() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User login", true);
    }

    @Test
    public void lwm2m_du_110() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User Tag", false);
    }

    @Test
    public void lwm2m_du_111() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User Tag", true);
    }

    @Test
    public void lwm2m_du_112() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Serial Number", false);
    }

    @Test
    public void lwm2m_du_113() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Serial Number", true);
    }

    @Test
    public void lwm2m_du_114() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("IP address", false);
    }

    @Test
    public void lwm2m_du_115() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("IP address", true);
    }

    @Test
    public void lwm2m_du_116() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("MAC address", false);
    }

    @Test
    public void lwm2m_du_117() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("MAC address", true);
    }

    //skipped: 118-121 due to impossible to validate (E-UTRAN Node B ID, Base Station ID)

    @Test
    public void lwm2m_du_122() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("ACS Username", false);
    }

    @Test
    public void lwm2m_du_123() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("ACS Username", true);
    }

    @Test
    public void lwm2m_du_124() {
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
    public void lwm2m_du_125() {
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
    public void lwm2m_du_126() {
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
    public void lwm2m_du_127() {
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
    public void lwm2m_du_128() {
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
    public void lwm2m_du_129() {
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
    public void lwm2m_du_130() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_INFO)
                .recheckStatus();
    }

    @Test
    public void lwm2m_du_131() {
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
    public void lwm2m_du_132() {
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
    public void lwm2m_du_133() {
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
    public void lwm2m_du_134() {
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
    public void lwm2m_du_135() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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
    public void lwm2m_du_137() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .bottomMenu(PING)
                .assertPingWindowIsOpened();
    }

    @Test
    public void lwm2m_du_138() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .bottomMenu(TRACE_ROUTE)
                .assertTracerouteWindowIsOpened();
    }

//    @Test   //skipped: due to absence of REPLACE button for LWM2M protocol
//    public void lwm2m_du_139() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .bottomMenu(REPLACE)
//                .assertReplaceWindowIsOpened();
//    }

    @Test
    public void lwm2m_du_140() {
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
    public void lwm2m_du_141() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .setAllUserInfo()
                .editAccountInfoLink()
                .clearUserInfo()
                .assertAccountInfoIsClear();
    }

    @Test
    public void lwm2m_du_142() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .listOfMethods()
                .assertMethodIsPresent("Reboot")
                .closePopup();
    }

    @Test
    public void lwm2m_du_143() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Current Time", now())
                .bottomMenu(SEND_UPDATE)
                .cancelButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateAbsenceTaskWithValue();
    }

    @Test
    public void lwm2m_du_144() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Device", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void lwm2m_du_145() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Device", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void lwm2m_du_146() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Device", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void lwm2m_du_147() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Device", 1)
                .bottomMenu(WAIT_UNTIL_CONNECT)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void lwm2m_du_148() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .clearProvisionManager()
                .leftMenu(DEVICE_SETTINGS)
                .pause(1000)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Device", 1)
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test   //bug: 'Get parameter names' and 'Get parameter attributes' aren't generated in Device Activity
    public void lwm2m_du_149() {
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
    public void lwm2m_du_150() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Server", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void lwm2m_du_151() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Server", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void lwm2m_du_152() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Server", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void lwm2m_du_153() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .clearProvisionManager()
                .leftMenu(DEVICE_SETTINGS)
                .pause(1000)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Server", 1)
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test   //bug:Connectivity monitoring tab has all read only fields
    public void lwm2m_du_154() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Connectivity monitoring", 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test   //bug:Connectivity monitoring tab has all read only fields
    public void lwm2m_du_155() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Connectivity monitoring", 2)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test   //bug:Connectivity monitoring tab has all read only fields
    public void lwm2m_du_156() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DEVICE_SETTINGS)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Connectivity monitoring", 99)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test   //bug:Connectivity monitoring tab has all read only fields
    public void lwm2m_du_157() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .clearProvisionManager()
                .leftMenu(DEVICE_SETTINGS)
                .pause(1000)
                .bottomMenu(EDIT_SETTINGS)
                .setParameter("Connectivity monitoring", 1)
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test
    public void lwm2m_du_158() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(ADVANCED_VIEW)
                .validateObjectTree(); // use .validateObjectTree1() instead if failed!
    }

    @Test
    public void lwm2m_du_159() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(ADVANCED_VIEW)
                .selectBranch("ManagementServer")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter(null, 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void lwm2m_du_160() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(ADVANCED_VIEW)
                .selectBranch("ManagementServer")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter(null, 1)
                .bottomMenu(WAIT_UNTIL_CONNECT)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void lwm2m_du_161() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(ADVANCED_VIEW)
                .selectBranch("ManagementServer")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter(null, 1)
                .bottomMenu(ADD_TO_PROVISION)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .pause(1000)
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks()
                .leftMenu(PROVISION_MANAGER)
                .validateProvisionTasks();
    }

    @Test
    public void lwm2m_du_162() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(ADVANCED_VIEW)
                .bottomMenu(EDIT_TREE)
                .selectTreeObject(true, 0)
                .bottomMenu(STORE_TREE)
                .okButtonPopUp();   //is validation needed??? How???
    }

    @Test
    public void lwm2m_du_163() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(ADVANCED_VIEW)
                .bottomMenu(EDIT_TREE)
                .bottomMenu(CLEAR_TREE)
                .bottomMenu(STORE_TREE)
                .okButtonPopUp();   //is validation needed??? How???
    }

    @Test
    public void lwm2m_du_164() throws IOException {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(ADVANCED_VIEW)
                .bottomMenu(SAVE_PARAMETERS)
                .validateCsvFile();
    }

    @Test
    public void lwm2m_du_165() {
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
    public void lwm2m_du_166() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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

    @Test   //bug: option 'Vendor Configuration File' is absent from File type dropdown;
    public void lwm2m_du_167() {
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

    @Test   //bug: option 'Vendor Configuration File' is absent from File type dropdown;
    public void lwm2m_du_168() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
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

    //skipped: 169 - cannot select File name dropdown;


    @Test   //bug: option 'Vendor Configuration File' is absent from File type dropdown;
    public void lwm2m_du_170() {
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

    @Test   //bug: option 'Vendor Configuration File' is absent from File type dropdown;
    public void lwm2m_du_171() {
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

    @Test   //bug: cannot add LWM2M Resource Definition file in File Management tab
    public void lwm2m_du_172() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_DOWNLOAD)
                .selectDownloadFileType("LWM2M Resource Definition")
                .selectFromListRadioButton()
                .selectFileName()
                .bottomMenu(START)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks();
    }

    @Test
    public void lwm2m_du_173() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_DOWNLOAD)
                .selectDownloadFileType("LWM2M Resource Definition")
                .fillDownloadUrl()
                .bottomMenu(START)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks();
    }

    @Test
    public void lwm2m_du_174() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_DOWNLOAD)
                .selectDownloadFileType("LWM2M PSK Credentials")
                .selectFromListRadioButton()
                .selectFileName()
                .bottomMenu(START)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks();
    }

    @Test
    public void lwm2m_du_175() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_DOWNLOAD)
                .selectDownloadFileType("LWM2M PSK Credentials")
                .fillDownloadUrl()
                .bottomMenu(ADD_TO_PROVISION)   //added for dependent 183,184,185 and 171 has bug
                .bottomMenu(START)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateDownloadFileTasks();
    }

    @Test
    public void lwm2m_du_176() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(FILE_DOWNLOAD)
                .selectDownloadFileType("LWM2M PSK Credentials")
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
    public void lwm2m_du_177() {
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
    public void lwm2m_du_178() throws IOException {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_HISTORY)
                .bottomMenu(SAVE)
                .validateHistoryFile();
    }

    @Test
    public void lwm2m_du_179() {
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
    public void lwm2m_du_180() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .editParameterValue()
                .validateEditedProvision();
    }

    @Test
    public void lwm2m_du_181() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .editParameterPriority()
                .validateEditedProvision();
    }

    @Test
    public void lwm2m_du_182() {
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

    @Test   //depends on 171 (175)
    public void lwm2m_du_183() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .editProvisionFileUrl()
                .bottomMenu(START)
                .validateEditedProvision();
    }

    @Test   //depends on 171 (175)
    public void lwm2m_du_184() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .editProvisionFilePriority()
                .bottomMenu(START)
                .validateEditedProvision();
    }

    @Test   //depends on 171 (175)
    public void lwm2m_du_185() {
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
