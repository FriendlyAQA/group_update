package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.TopMenu.REPORTS;

@Listeners(UniversalVideoListener.class)
public class ReportsTests extends BaseTestCase {

    @Test
    public void reports_001() {
        reportsPage
                .topMenu(REPORTS)
                .assertItemIsPresent("Devices distribution")
                .assertItemIsPresent("Devices registration")
                .assertItemIsPresent("Online devices")
                .assertItemIsPresent("Offline devices")
                .assertItemIsPresent("Events")
                .assertItemIsPresent("User activity")
                .assertItemIsPresent("Group update")
                .assertItemIsPresent("Firmware versions")
                .assertItemIsPresent("Inventory");
    }

    @Test
    public void reports_002() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesDistribution()
                .go()
                .assertElementsArePresent("tblReports", "canvasPie");
    }

    @Test
    public void reports_003() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesDistribution()
                .go()
                .validateExport();
    }

//    reports_004 skipped: cannot check printing automatically

    @Test
    public void reports_005() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesDistribution()
                .go()
                .bottomMenu(SHOW_ON_MAP)
                .closeMapWindow();
    }

    @Test
    public void reports_006() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesDistribution()
                .priorToDateRB()
                .selectShiftedDate("calDate1", -1)
                .go()
                .assertElementsArePresent("tblReports", "canvasPie")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_007() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesDistribution()
                .showByPeriodRB()
                .selectShiftedDate("calPeriodFrom", -365)
                .selectShiftedDate("calPeriodTo", -1)
                .go()
                .assertElementsArePresent("tblReports", "canvasPie")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_008() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesDistribution()
                .selectManufacturer("tr181")
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_009() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesDistribution()
                .selectManufacturer("tr181")
                .priorToDateRB()
                .selectShiftedDate("calDate1", -1)
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_010() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesDistribution()
                .selectManufacturer("tr181")
                .showByPeriodRB()
                .selectShiftedDate("calPeriodFrom", -365)
                .selectShiftedDate("calPeriodTo", -1)
                .go()
                .assertElementsArePresent("tblReports", "canvasPie")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_011() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesRegistration()
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_012() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesRegistration()
                .selectShiftedDate("calFromDate", -365)
                .go()
                .validateExport();
    }

//    reports_013 skipped: cannot check printing automatically

    @Test
    public void reports_014() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesRegistration()
                .selectShiftedDate("calFromDate", -365)
                .go()
                .bottomMenu(SHOW_ON_MAP)
                .closeMapWindow();
    }

    @Test
    public void reports_015() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesRegistration()
                .selectManufacturer("tr181")
                .selectShiftedDate("calFromDate", -365)
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_016() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesRegistration()
                .selectModel("tr181")
                .selectShiftedDate("calFromDate", -365)
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_017() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesRegistration()
                .selectShiftedDate("calFromDate", -365)
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_018() {
        reportsPage
                .topMenu(REPORTS)
                .openDevicesRegistration()
                .selectShiftedDate("calToDate", -1)
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_019() {
        reportsPage
                .topMenu(REPORTS)
                .openOnlineDevices()
                .go()
                .assertElementsArePresent("canvasChart", "tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_020() {
        reportsPage
                .topMenu(REPORTS)
                .openOnlineDevices()
                .go()
                .validateExport();
    }

//    reports_021 skipped: cannot check printing automatically



}
