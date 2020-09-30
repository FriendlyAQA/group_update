package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.pageobject.DeviceUpdatePage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.friendly.aqa.entities.TopMenu.DEVICE_UPDATE;
import static com.friendly.aqa.entities.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.BottomButtons.*;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.BottomButtons.DELETE;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.Left.*;

@Listeners(UniversalVideoListener.class)
public class DeviceUpdateMqttTests extends BaseTestCase {

    /*
    Preconditions:
    1. Before run you should delete all view and custom view (Search) with name like 'mqtt_du_011', 'mqtt_du_067' (starts with mqtt_) etc.;
    2. Devices (emul) must run, (emul restart);
    3. Trace for target device must be stopped (mqtt_du_135);
    4. At least one active profile for target device must exist (mqtt_du_134);
    5. All files for Download tasks must be added to server.
    */
    @Test
    public void mqtt_du_001() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void mqtt_du_002() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(LIST)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void mqtt_du_003() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertPresenceOfOptions("ddlView", "Default")
                .assertPresenceOfOptions("ddlManuf", "All")
                .assertPresenceOfOptions("ddlModel", "All");
    }

    @Test
    public void mqtt_du_004() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .deleteAllCustomViews()
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
    public void mqtt_du_005() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .newViewButton()
                .fillName("Default")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void mqtt_du_006() {
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
    public void mqtt_du_007() {
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
    public void mqtt_du_008() {
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
    public void mqtt_du_009() {
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
    public void mqtt_du_010() {
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
    public void mqtt_du_011() {
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
    public void mqtt_du_012() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("mqtt_du_011")
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
    public void mqtt_du_013() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("mqtt_du_011")
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
    public void mqtt_du_014() {
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
    public void mqtt_du_015() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterModelName(BasePage.getModelName())
                .createPreconditionsForSorting();
    }

    @Test
    public void mqtt_du_016() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("Created");
    }

    @Test
    public void mqtt_du_017() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("Firmware");
    }

    @Test
    public void mqtt_du_018() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("Latitude");
    }

    @Test
    public void mqtt_du_019() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("Longitude");
    }

    @Test
    public void mqtt_du_020() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("Manufacturer");
    }

    @Test
    public void mqtt_du_021() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("Model name");
    }

    @Test
    public void mqtt_du_022() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("mycust03");
    }

    @Test
    public void mqtt_du_023() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("mycust04");
    }

    @Test
    public void mqtt_du_024() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("mycust05");
    }

    @Test
    public void mqtt_du_025() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("mycust06");
    }

    @Test
    public void mqtt_du_026() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("mycust07");
    }

    @Test
    public void mqtt_du_027() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("mycust08");
    }

    @Test
    public void mqtt_du_028() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("mycust09");
    }

    @Test
    public void mqtt_du_029() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("mycust10");
    }

    @Test
    public void mqtt_du_030() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("OUI");
    }

    @Test
    public void mqtt_du_031() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("Phone number");
    }

    @Test
    public void mqtt_du_032() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("Protocol type");
    }

    @Test
    public void mqtt_du_033() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("Serial");
    }

    @Test
    public void mqtt_du_034() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("Status");
    }

    @Test
    public void mqtt_du_035() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("Updated");
    }

    @Test
    public void mqtt_du_036() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("User ID");
    }

    @Test
    public void mqtt_du_037() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("User location");
    }

    @Test
    public void mqtt_du_038() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("User login");
    }

    @Test
    public void mqtt_du_039() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("User name");
    }

    @Test
    public void mqtt_du_040() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("User name")
                .validateSorting("User status");
    }

    @Test
    public void mqtt_du_041() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("User tag");
    }

    @Test
    public void mqtt_du_042() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterManufacturer("All")
                .selectFilterModelName("All")
                .selectView("mqtt_du_014")
                .validateSorting("Zip");
    }

    @Test
    public void mqtt_du_043() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .assertChangingView();
    }

    @Test
    public void mqtt_du_044() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("mqtt_du_014")
                .editButton()
                .bottomMenu(CANCEL)
                .assertSelectedViewIs("mqtt_du_014");
    }

    @Test
    public void mqtt_du_045() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("mqtt_du_014")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(GROUP_UPDATE)
                .topMenu(DEVICE_UPDATE)
                .resetView()
                .assertSelectedViewIs("mqtt_du_014")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp();
    }

    @Test
    public void mqtt_du_046() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .validateFiltering("Model name");
    }

    @Test
    public void mqtt_du_047() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .validateFiltering("Manufacturer");
    }

    @Test
    public void mqtt_du_048() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("mqtt_du_014")
                .selectView("Default")
                .assertTableColumnNumberIs(7, "tbl");
    }

    @Test
    public void mqtt_du_049() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("mqtt_du_011")
                .assertTableColumnNumberIs(2, "tbl");
    }

    @Test
    public void mqtt_du_050() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectView("mqtt_du_014")
                .editButton()
                .bottomMenu(DELETE_VIEW)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "mqtt_du_014");
    }

    @Test
    public void mqtt_du_051() {
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
    public void mqtt_du_052() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .saveTable("tbl")
                .bottomMenu(REFRESH)
                .assertPageWasRefreshed();
    }

    @Test
    public void mqtt_du_053() {
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
    public void mqtt_du_054() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORT_TO_CSV)
                .saveFileName()
                .okButtonPopUp();
    }

    @Test
    public void mqtt_du_055() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORT_TO_XML)
                .saveFileName()
                .okButtonPopUp();
    }

    @Test   //depends on 54, 55
    public void mqtt_du_056() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(EXPORTS)
                .validateSavedExport("csv", "xml");
    }

    @Test   //depends on 54, 55
    public void mqtt_du_057() {
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
    public void mqtt_du_058() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .bottomMenu(SHOW_ON_MAP)
                .closeMapWindow();
    }

    @Test
    public void mqtt_du_059() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .assertPresenceOfOptions("ddlView", "Default")
                .assertPresenceOfOptions("ddlSearchOption", "Phone number", "User ID", "Full name", "Username"
                        , "User Tag", "Serial Number", "IP address", "MAC address", "Base Station ID", "E-UTRAN Node B ID", "ACS Username")
                .assertPresenceOfElements("btnEditView_btn", "btnNewView_btn", "btnDefaultView_btn", "rdSearchExactly", "btnSearch_btn");
    }

    @Test
    public void mqtt_du_060() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .deleteAllCustomViews()
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
    public void mqtt_du_061() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .newViewButton()
                .fillName("Default")
                .bottomMenu(NEXT)
                .assertDuplicateNameErrorIsDisplayed();
    }

    @Test
    public void mqtt_du_062() {
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
    public void mqtt_du_063() {
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
    public void mqtt_du_064() {
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
    public void mqtt_du_065() {
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
    public void mqtt_du_066() {
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
                .selectCheckbox("rdSearchExactly")
                .searchButton()
                .assertPresenceOfElements("tblDeviceInfo");
    }

    @Test
    public void mqtt_du_067() {
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
    public void mqtt_du_068() {
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
    public void mqtt_du_069() {
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
    public void mqtt_du_070() {
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
    public void mqtt_du_071() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .selectFilterModelName(BasePage.getModelName())
                .createPreconditionsForSorting();
    }

    @Test
    public void mqtt_du_072() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Created");
    }

    @Test
    public void mqtt_du_073() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
//                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .selectView("mqtt_du_070")
//                .searchButton()
                .validateSorting("Firmware");
    }

    @Test
    public void mqtt_du_074() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Latitude");
    }

    @Test
    public void mqtt_du_075() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Longitude");
    }

    @Test
    public void mqtt_du_076() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Manufacturer");
    }

    @Test
    public void mqtt_du_077() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Model name");
    }

    @Test
    public void mqtt_du_078() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust03");
    }

    @Test
    public void mqtt_du_079() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust04");
    }

    @Test
    public void mqtt_du_080() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust05");
    }

    @Test
    public void mqtt_du_081() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust06");
    }

    @Test
    public void mqtt_du_082() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust07");
    }

    @Test
    public void mqtt_du_083() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust08");
    }

    @Test
    public void mqtt_du_084() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust09");
    }

    @Test
    public void mqtt_du_085() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("mycust10");
    }

    @Test
    public void mqtt_du_086() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("OUI");
    }

    @Test
    public void mqtt_du_087() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Phone number");
    }

    @Test   //bug: sorting by 'Protocol type' failed;
    public void mqtt_du_088() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Protocol type");
    }

    @Test
    public void mqtt_du_089() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Serial");
    }

    @Test   //Bug: column "Status" is absent from search view settings list; (bug in STD?)
    public void mqtt_du_090() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .lookFor(getSerial().substring(0, 1))
                .deselectCheckbox("rdSearchExactly")
                .searchButton()
                .validateSorting("Status");
    }

    @Test
    public void mqtt_du_091() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Updated");
    }

    @Test
    public void mqtt_du_092() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
//                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .selectView("mqtt_du_070")
//                .searchButton()
                .validateSorting("User ID");
    }

    @Test
    public void mqtt_du_093() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User location");
    }

    @Test
    public void mqtt_du_094() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User login");
    }

    @Test
    public void mqtt_du_095() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User name");
    }

    @Test
    public void mqtt_du_096() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User status");
    }

    @Test
    public void mqtt_du_097() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("User tag");
    }

    @Test
    public void mqtt_du_098() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .searchBy("Serial Number")
                .deselectCheckbox("rdSearchExactly")
                .lookFor(getSerial().substring(0, 1))
                .searchButton()
                .validateSorting("Zip");
    }

    @Test   //bug: after 'Cancel' view doesn't return to previous selected view, Default is selected instead.
    public void mqtt_du_099() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .editButton()
                .bottomMenu(CANCEL)
                .assertSelectedViewIs("mqtt_du_070");
    }

    @Test
    public void mqtt_du_100() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_070")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectView("Default")
                .topMenu(GROUP_UPDATE)
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .resetView()
                .assertSelectedViewIs("mqtt_du_070")
                .selectView("Default")
                .editButton()
                .defaultViewForCurrentUserCheckbox()
                .bottomMenu(FINISH)
                .okButtonPopUp();
    }

    @Test
    public void mqtt_du_101() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .selectView("mqtt_du_069")
                .editButton()
                .bottomMenu(DELETE_VIEW)
                .okButtonPopUp()
                .assertSelectedViewIs("Default")
                .assertAbsenceOfOptions("ddlView", "mqtt_du_069");
    }

    @Test
    public void mqtt_du_102() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Phone number", false);
    }

    @Test
    public void mqtt_du_103() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Phone number", true);
    }

    @Test
    public void mqtt_du_104() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User ID", false);
    }

    @Test
    public void mqtt_du_105() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User ID", true);
    }

    @Test
    public void mqtt_du_106() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Full name", false);
    }

    @Test
    public void mqtt_du_107() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Full name", true);
    }

    @Test
    public void mqtt_du_108() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Username", false);
    }

    @Test
    public void mqtt_du_109() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Username", true);
    }

    @Test
    public void mqtt_du_110() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User Tag", false);
    }

    @Test
    public void mqtt_du_111() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("User Tag", true);
    }

    @Test
    public void mqtt_du_112() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Serial Number", false);
    }

    @Test
    public void mqtt_du_113() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("Serial Number", true);
    }

    @Test
    public void mqtt_du_114() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("IP address", false);
    }

    @Test
    public void mqtt_du_115() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("IP address", true);
    }

    @Test
    public void mqtt_du_116() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("MAC address", false);
    }

    @Test
    public void mqtt_du_117() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("MAC address", true);
    }

    //skipped: 118-121 due to impossible to validate (E-UTRAN Node B ID, Base Station ID)

    @Test
    public void mqtt_du_122() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("ACS Username", false);
    }

    @Test
    public void mqtt_du_123() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .leftMenu(SEARCH)
                .validateSearchBy("ACS Username", true);
    }

    @Test
    public void mqtt_du_124() {
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
    public void mqtt_du_125() {
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
    public void mqtt_du_126() {
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
    public void mqtt_du_127() {
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
    public void mqtt_du_128() {
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
    public void mqtt_du_129() {
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
    public void mqtt_du_130() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_INFO)
                .recheckStatus();
    }

    @Test
    public void mqtt_du_131() {
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
    public void mqtt_du_132() {
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
    public void mqtt_du_133() {
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
    public void mqtt_du_134() {
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
    public void mqtt_du_135() {
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
    public void mqtt_du_137() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .bottomMenu(PING)
                .assertPingWindowIsOpened();
    }

    @Test
    public void mqtt_du_138() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .bottomMenu(TRACE_ROUTE)
                .assertTracerouteWindowIsOpened();
    }

//    @Test   //skipped: due to absence of REPLACE button for mqtt protocol
//    public void mqtt_du_139() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .bottomMenu(REPLACE)
//                .assertReplaceWindowIsOpened();
//    }

    @Test
    public void mqtt_du_140() {
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
    public void mqtt_du_141() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .setAllUserInfo()
                .editAccountInfoLink()
                .clearUserInfo()
                .assertAccountInfoIsClear();
    }

    @Test
    public void mqtt_du_142() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .listOfMethods()
                .assertMethodIsPresent("Reboot")
                .closePopup();
    }

    //skipped due to device Settings tabs don't contain suitable fields to edit

//    @Test
//    public void mqtt_du_143() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .clearDeviceActivity()
//                .leftMenu(DEVICE_SETTINGS)
//                .bottomMenu(EDIT_SETTINGS)
//                .setParameter("Management", 1)
//                .bottomMenu(SEND_UPDATE)
//                .cancelButtonPopUp()
//                .leftMenu(DEVICE_ACTIVITY)
//                .validateAbsenceTaskWithValue();
//    }
//
//    @Test
//    public void mqtt_du_144() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .clearDeviceActivity()
//                .leftMenu(DEVICE_SETTINGS)
//                .bottomMenu(EDIT_SETTINGS)
//                .setParameter("Management", 1)
//                .bottomMenu(SEND_UPDATE)
//                .okButtonPopUp()
//                .okButtonPopUp()
//                .leftMenu(DEVICE_ACTIVITY)
//                .validateTasks();
//    }
//
//    @Test
//    public void mqtt_du_145() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .clearDeviceActivity()
//                .leftMenu(DEVICE_SETTINGS)
//                .bottomMenu(EDIT_SETTINGS)
//                .setParameter("Management", 2)
//                .bottomMenu(SEND_UPDATE)
//                .okButtonPopUp()
//                .okButtonPopUp()
//                .leftMenu(DEVICE_ACTIVITY)
//                .validateTasks();
//    }
//
//    @Test
//    public void mqtt_du_146() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .clearDeviceActivity()
//                .leftMenu(DEVICE_SETTINGS)
//                .bottomMenu(EDIT_SETTINGS)
//                .setParameter("Management", 99)
//                .bottomMenu(SEND_UPDATE)
//                .okButtonPopUp()
//                .okButtonPopUp()
//                .leftMenu(DEVICE_ACTIVITY)
//                .validateTasks();
//    }
//
//    @Test
//    public void mqtt_du_147() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .clearDeviceActivity()
//                .leftMenu(DEVICE_SETTINGS)
//                .bottomMenu(EDIT_SETTINGS)
//                .setParameter("Management", 1)
//                .bottomMenu(WAIT_UNTIL_CONNECT)
//                .bottomMenu(SEND_UPDATE)
//                .okButtonPopUp()
//                .okButtonPopUp()
//                .leftMenu(DEVICE_ACTIVITY)
//                .validateTasks();
//    }
//
//    @Test
//    public void mqtt_du_148() {
//        duPage
//                .topMenu(DEVICE_UPDATE)
//                .enterToDevice()
//                .clearDeviceActivity()
//                .clearProvisionManager()
//                .leftMenu(DEVICE_SETTINGS)
//                .pause(1000)
//                .bottomMenu(EDIT_SETTINGS)
//                .setParameter("Management", 1)
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
//    public void mqtt_du_149() {
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
//
//    @Test
//    public void mqtt_du_150() {
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
//    public void mqtt_du_151() {
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
//    public void mqtt_du_152() {
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
//    public void mqtt_du_153() {
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

    @Test
    public void mqtt_du_154() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DeviceUpdatePage.Left.ADVANCED_VIEW)
                .validateObjectTree(); // use .validateObjectTree1() instead if failed!
    }

    @Test
    public void mqtt_du_155() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DeviceUpdatePage.Left.ADVANCED_VIEW)
                .selectBranch("Device.FriendlySmartHome.PowerMeter.1")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter(null, 1)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test
    public void mqtt_du_156() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DeviceUpdatePage.Left.ADVANCED_VIEW)
                .selectBranch("Device.FriendlySmartHome.PowerMeter.1")
                .bottomMenu(EDIT_SETTINGS)
                .setParameter(null, 1)
                .bottomMenu(WAIT_UNTIL_CONNECT)
                .bottomMenu(SEND_UPDATE)
                .okButtonPopUp()
                .okButtonPopUp()
                .leftMenu(DEVICE_ACTIVITY)
                .validateTasks();
    }

    @Test   //bug: checkbox 'add to provision' is absent from mqtt bottom menu
    public void mqtt_du_157() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .clearDeviceActivity()
                .leftMenu(DeviceUpdatePage.Left.ADVANCED_VIEW)
                .selectBranch("Device.FriendlySmartHome.PowerMeter.1")
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
    public void mqtt_du_158() {
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
    public void mqtt_du_159() {
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
    public void mqtt_du_160() throws IOException {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DeviceUpdatePage.Left.ADVANCED_VIEW)
                .bottomMenu(SAVE_PARAMETERS)
                .validateCsvFile();
    }

    @Test
    public void mqtt_du_161() {
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
    public void mqtt_du_162() throws IOException {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(DEVICE_HISTORY)
                .bottomMenu(SAVE)
                .validateHistoryFile();
    }

    @Test
    public void mqtt_du_163() {
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
    public void mqtt_du_164() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .editParameterValue()
                .validateEditedProvision();
    }

    @Test
    public void mqtt_du_165() {
        duPage
                .topMenu(DEVICE_UPDATE)
                .enterToDevice()
                .leftMenu(PROVISION_MANAGER)
                .editParameterPriority()
                .validateEditedProvision();
    }

    @Test
    public void mqtt_du_166() {
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
}
