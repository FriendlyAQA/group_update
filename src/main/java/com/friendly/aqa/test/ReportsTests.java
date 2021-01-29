package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.TopMenu.REPORTS;
import static com.friendly.aqa.pageobject.ReportsPage.Left.*;

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

    @Test
    public void reports_022() {
        reportsPage
                .topMenu(REPORTS)
                .openOnlineDevices()
                .go()
                .bottomMenu(SHOW_ON_MAP)
                .closeMapWindow();
    }

    @Test
    public void reports_023() {
        reportsPage
                .topMenu(REPORTS)
                .openOnlineDevices()
                .selectManufacturer("tr181")
                .go()
                .assertElementsArePresent("canvasChart", "tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_024() {
        reportsPage
                .topMenu(REPORTS)
                .openOnlineDevices()
                .selectModelName("tr181")
                .go()
                .assertElementsArePresent("canvasChart", "tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_025() {
        reportsPage
                .topMenu(REPORTS)
                .openOnlineDevices()
                .selectPeriod("Yesterday")
                .go()
                .assertElementsArePresent("canvasChart", "tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_026() {
        reportsPage
                .topMenu(REPORTS)
                .openOnlineDevices()
                .selectPeriod("Days")
                .inputPeriod("30")
                .go()
                .assertElementsArePresent("canvasChart", "tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_027() {
        reportsPage
                .topMenu(REPORTS)
                .openOfflineDevices()
                .go()
                .assertElementsArePresent("canvasChart", "tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_028() {
        reportsPage
                .topMenu(REPORTS)
                .openOfflineDevices()
                .go()
                .validateExport();
    }

//    reports_029 skipped: cannot check printing automatically

    @Test
    public void reports_030() {
        reportsPage
                .topMenu(REPORTS)
                .openOfflineDevices()
                .go()
                .bottomMenu(SHOW_ON_MAP)
                .closeMapWindow();
    }

    @Test
    public void reports_031() {
        reportsPage
                .topMenu(REPORTS)
                .openOfflineDevices()
                .selectOfflineManufacturer()
                .go()
                .assertElementsArePresent("canvasChart", "tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_032() {
        reportsPage
                .topMenu(REPORTS)
                .openOfflineDevices()
                .selectOfflineModel()
                .go()
                .assertElementsArePresent("canvasChart", "tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_033() {
        reportsPage
                .topMenu(REPORTS)
                .openOfflineDevices()
                .selectPeriod("Months")
                .inputPeriod("2")
                .go()
                .assertElementsArePresent("canvasChart", "tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_034() {
        reportsPage
                .topMenu(REPORTS)
                .openOfflineDevices()
                .selectPeriod("Weeks")
                .inputPeriod("2")
                .go()
                .assertElementsArePresent("canvasChart", "tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_035() {
        reportsPage
                .topMenu(REPORTS)
                .openOfflineDevices()
                .selectPeriod("Days")
                .inputPeriod("2")
                .go()
                .assertElementsArePresent("canvasChart", "tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_036() {
        reportsPage
                .topMenu(REPORTS)
                .openEvents()
                .go()
                .assertElementsArePresent("canvasChart", "tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_037() {
        reportsPage
                .topMenu(REPORTS)
                .openEvents()
                .go()
                .bottomMenu(SHOW_ON_MAP)
                .closeMapWindow();
    }

    @Test
    public void reports_038() {
        reportsPage
                .topMenu(REPORTS)
                .openEvents()
                .go()
                .validateExport();
    }

//    reports_039 skipped: cannot check printing automatically

    @Test
    public void reports_040() {
        reportsPage
                .topMenu(REPORTS)
                .openEvents()
                .selectManufacturerWithEvents()
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_041() {
        reportsPage
                .topMenu(REPORTS)
                .openEvents()
                .selectModelWithEvents()
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_042() {
        reportsPage
                .topMenu(REPORTS)
                .openEvents()
                .priorToDateRB()
                .selectShiftedDate("calDate1", -1)
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_043() {
        reportsPage
                .topMenu(REPORTS)
                .openEvents()
                .showByPeriodRB()
                .selectShiftedDate("calPeriodFrom", -365)
                .selectShiftedDate("calPeriodTo", -1)
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_044() {
        reportsPage
                .topMenu(REPORTS)
                .openEvents()
                .selectEvent("2 PERIODIC")
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_045() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Profiles")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_046() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Profiles")
                .go()
                .validateExport();
    }

//    reports_047 skipped: cannot check printing automatically

    @Test
    public void reports_048() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Profiles")
                .selectShiftedDate("calFromDate", -365)
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_049() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Profiles")
                .selectShiftedDate("calToDate", -1)
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_050() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Profiles")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Create profile")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_051() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Profiles")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete profile")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_052() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Profiles")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Edit profile")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_053() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Profiles")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Import profile")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_054() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Profiles")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Activate profile")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_055() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Profiles")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("File download")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: what is profile: "File deleted"??
    public void reports_056() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Profiles")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("File deleted")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_057() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_058() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .go()
                .validateExport();
    }

//    reports_059 skipped: cannot check printing automatically

    @Test
    public void reports_060() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_061() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calToDate", -1)
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_062() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Reset device")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

//    reports_063 skipped: option 'Reset Failed' is missing

    @Test
    public void reports_064() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Reboot device")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

//    reports_065 skipped: option 'Reboot failed' is missing

    @Test
    public void reports_066() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete device")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_067() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Change parameters")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: what is Device Update:"Add polling"??
    public void reports_068() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Add polling")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: what is Device Update:"Remove polling"??
    public void reports_069() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Remove polling")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_070() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("File download")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_071() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("File deleted")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_072() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Add object")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_073() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete object")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_074() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Add upload")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: what is Device Update:"Delete upload"??
    public void reports_075() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete upload")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_076() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Add diagnostics")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_077() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete diagnostics")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_078() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Custom RPC")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_079() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Reprovision")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: what is Device Update:"Deployment unit"??
    public void reports_080() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Deployment unit")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_081() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Tracing")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: Device Update does not contain "Change attributes" menu item
    public void reports_082() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Device Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Change attributes")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

//    reports_083 skipped: option 'all hub related actions' are missing

    @Test
    public void reports_084() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_085() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .go()
                .validateExport();
    }

//    reports_086 skipped: cannot check printing automatically

    @Test
    public void reports_087() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_088() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calToDate", -1)
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_089() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Create update")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

//    reports_090 skipped: duplicated with 089

    @Test
    public void reports_091() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete update")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_092() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Edit update")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: no data found
    public void reports_093() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Import update")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_094() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Activate update")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_095() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Scheduled activation")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_096() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Stop update")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_097() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Pause update")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_098() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Add set parameters")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_099() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Add file download")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_100() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Add action")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_101() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Add policy")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_102() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Add upload file")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_103() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Add get parameters")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: no data found
    public void reports_104() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Add install software")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: no data found
    public void reports_105() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Add update software")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: no data found
    public void reports_106() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Add uninstall software")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: no data found
    public void reports_107() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete set parameters")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: no data found
    public void reports_108() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete file download")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: no data found
    public void reports_109() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete action")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: no data found
    public void reports_110() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete policy")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: no data found
    public void reports_111() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete file upload")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: no data found
    public void reports_112() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete get parameters")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: no data found
    public void reports_113() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete install software")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: no data found
    public void reports_114() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete update software")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test   //bug: no data found
    public void reports_115() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Delete uninstall software")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_116() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Backup")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_117() {
        reportsPage
                .topMenu(REPORTS)
                .openUserActivity()
                .selectActivity("Group Update")
                .selectShiftedDate("calFromDate", -365)
                .selectActivityType("Restore")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT);
    }

    @Test
    public void reports_118() {
        reportsPage
                .topMenu(REPORTS)
                .openGroupUpdate()
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_119() {
        reportsPage
                .topMenu(REPORTS)
                .openGroupUpdate()
                .go()
                .bottomMenu(SHOW_ON_MAP)
                .closeMapWindow();
    }

    @Test
    public void reports_120() {
        reportsPage
                .topMenu(REPORTS)
                .openGroupUpdate()
                .go()
                .validateExport();
    }

//    reports_121 skipped: cannot check printing automatically

    @Test
    public void reports_122() {
        reportsPage
                .topMenu(REPORTS)
                .openGroupUpdate()
                .selectManufacturerWithGroupUpdate()
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_123() {
        reportsPage
                .topMenu(REPORTS)
                .openGroupUpdate()
                .selectModelWithGroupUpdate()
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_124() {
        reportsPage
                .topMenu(REPORTS)
                .openGroupUpdate()
                .selectUpdateState("Completed")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_125() {
        reportsPage
                .topMenu(REPORTS)
                .openGroupUpdate()
                .dateRB()
                .selectDate()
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_126() {
        reportsPage
                .topMenu(REPORTS)
                .openGroupUpdate()
                .selectUpdateState("Not active")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_127() {
        reportsPage
                .topMenu(REPORTS)
                .openGroupUpdate()
                .startScheduledUpdateGroup()
                .selectUpdateState("Scheduled")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_128() {
        reportsPage
                .topMenu(REPORTS)
                .openGroupUpdate()
                .selectUpdateState("Running")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_129() {
        reportsPage
                .topMenu(REPORTS)
                .openGroupUpdate()
                .selectUpdateState("Paused")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_130() {
        reportsPage
                .topMenu(REPORTS)
                .openGroupUpdate()
                .selectUpdateState("Reactivation")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test   //bug: cannot trigger 'Error' Update Group state
    public void reports_131() {
        reportsPage
                .topMenu(REPORTS)
                .openGroupUpdate()
                .selectUpdateState("Error")
                .go()
                .assertElementsArePresent("tblReport")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_132() {
        reportsPage
                .topMenu(REPORTS)
                .openFirmwareVersions()
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_133() {
        reportsPage
                .topMenu(REPORTS)
                .openFirmwareVersions()
                .go()
                .bottomMenu(SHOW_ON_MAP)
                .closeMapWindow();
    }

    @Test
    public void reports_134() {
        reportsPage
                .topMenu(REPORTS)
                .openFirmwareVersions()
                .go()
                .validateExport();
    }

//    reports_135 skipped: cannot check printing automatically

    @Test
    public void reports_136() {
        reportsPage
                .topMenu(REPORTS)
                .openFirmwareVersions()
                .selectManufacturerWithFirmware()
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_137() {
        reportsPage
                .topMenu(REPORTS)
                .openFirmwareVersions()
                .selectModelWithFirmware()
                .go()
                .assertElementsArePresent("tblReports")
                .assertButtonsAreEnabled(true, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_138() {
        reportsPage
                .topMenu(REPORTS)
                .openInventory()
                .go()
                .assertElementsArePresent("tblDeviceList")
                .assertButtonsAreEnabled(true, REPEAT, EXPORT_TO_CSV, EXPORT_TO_XML, EXPORT_TO_XLS, PRINT, SHOW_ON_MAP);
    }

    @Test
    public void reports_139() {
        reportsPage
                .topMenu(REPORTS)
                .openInventory()
                .go()
                .bottomMenu(EXPORT_TO_CSV)
                .saveFileName()
                .okButtonPopUp()
                .leftMenu(LIST_OF_EXPORTS)
                .validateSavedExport("csv");
    }

    @Test
    public void reports_140() {
        reportsPage
                .topMenu(REPORTS)
                .openInventory()
                .go()
                .bottomMenu(EXPORT_TO_XML)
                .saveFileName()
                .okButtonPopUp()
                .leftMenu(LIST_OF_EXPORTS)
                .validateSavedExport("xml");
    }

    @Test
    public void reports_141() {
        reportsPage
                .topMenu(REPORTS)
                .openInventory()
                .go()
                .validateExport();
    }

//    reports_142 skipped: cannot check printing automatically

    @Test
    public void reports_143() {
        reportsPage
                .topMenu(REPORTS)
                .openInventory()
                .go()
                .bottomMenu(SHOW_ON_MAP)
                .closeMapWindow();
    }
}
