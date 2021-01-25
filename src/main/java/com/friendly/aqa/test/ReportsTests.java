package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.SHOW_ON_MAP;
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

}
