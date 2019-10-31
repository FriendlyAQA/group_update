package testRunner;

import com.friendly.aqa.utils.HttpGetter;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.BaseTestCase;

import java.io.IOException;

import static com.friendly.aqa.pageobject.GlobalButtons.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.NEW;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.utils.Table.Select.*;

public class FunctionalTests extends BaseTestCase {
    @Test
    public void test_001() {
        systemPage.topMenu(GROUP_UPDATE);
        groupUpdatePage.waitForRefresh();
        Assert.assertTrue(groupUpdatePage.noDataFoundLabelIsPresent());
    }

    @Test
    public void test_002() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .globalButtons(CANCEL);
        Assert.assertTrue(groupUpdatePage.noDataFoundLabelIsPresent());
    }

    @Test
    public void test_003() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("auto_test_1")
                .globalButtons(CANCEL);
        Assert.assertTrue(groupUpdatePage.noDataFoundLabelIsPresent());
    }

    @Test
    public void test_004() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("autotest")
                .selectSendTo()
                .showList();
        Assert.assertTrue(groupUpdatePage.serialNumberTableIsPresent());
    }

    @Test
    public void test_005() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("1234")
                .createGroup();
        Assert.assertTrue(groupUpdatePage.isButtonPresent(FINISH));
        groupUpdatePage
                .globalButtons(CANCEL)
                .waitForRefresh();
        Assert.assertEquals(groupUpdatePage.getAttributeById("txtName", "value"), "1234");
    }

    @Test
    //A Bug was found while pressing "Next" button when "Name group" is filled;
    public void test_006() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("1234")
                .selectSendTo()
                .createGroup()
                .fillName("test_group_name");
        //Bug is present when press "Next" button
    }

    @Test
    public void test_011() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("auto_test_1")
                .selectSendTo("Individual")
                .getTable("tblDevices")
                .clickOn(1, 0);
        groupUpdatePage.waitForRefresh();
        Assert.assertTrue(groupUpdatePage.isButtonActive(NEXT));
        groupUpdatePage.getTable("tblDevices")
                .clickOn(1, 0);
        groupUpdatePage.waitForRefresh();
        Assert.assertFalse(groupUpdatePage.isButtonActive(NEXT));
    }

    @Test
    //Doesn't work with Edge
    public void test_012() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("auto_test_1")
                .selectSendTo("Import")
                .selectImportFile()
                .showList();
        Assert.assertEquals(groupUpdatePage.getTable("tblDevices").getCellText(1, 0), "FT001SN00001SD18F7FFF521");
    }

    @Test
    public void test_014() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("auto_test_1")
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton();
        Assert.assertTrue(groupUpdatePage.isElementPresent("tblParamsValue"));
        Assert.assertFalse(groupUpdatePage.isButtonActive(SAVE_AND_ACTIVATE));
    }

    @Test
    public void test_015() {
        groupUpdatePage
                .goToSetParameters("auto_test_1")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForRefresh();
        Assert.assertEquals(groupUpdatePage
                .getMainTable()
                .getCellText(4, "auto_test_1", 1), "Not active");
    }

    @Test
    public void test_016() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .getMainTable()
                .clickOn("auto_test_1", 4);
        groupUpdatePage
                .globalButtons(EDIT)
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .getTable("tblTasks")
                .clickOn("PeriodicInformInterval", 3);
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "61");
        groupUpdatePage
                .globalButtons(NEXT)
                .globalButtons(SAVE)

                .okButtonPopUp()
                .getMainTable()
                .clickOn("auto_test_1", 4);
        Assert.assertEquals(groupUpdatePage
                .getTable("tblTasks")
                .getCellText(2, "PeriodicInformInterval", 3), "61");
    }

    @Test
    public void test_017() {
        groupUpdatePage
                .goToSetParameters("auto_test_2")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForRefresh();
        softAssert(groupUpdatePage.getMainTable().getCellText(4, "auto_test_2", 1),
                "Completed", "Running");
    }

    @Test
    public void test_018() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .getMainTable()
                .clickOn("auto_test_2", 4);
        groupUpdatePage
                .globalButtons(EDIT);
        Assert.assertFalse(groupUpdatePage.isInputActive("ddlSend"));
        groupUpdatePage
                .globalButtons(NEXT);
        Assert.assertFalse(groupUpdatePage.isInputActive("lrbImmediately"));
        groupUpdatePage
                .globalButtons(NEXT);
        Assert.assertFalse(groupUpdatePage.isButtonActive(SAVE_AND_ACTIVATE));
    }

    @Test
    public void test_019() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("auto_test_2")
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .timeHoursSelect(0)
                .globalButtons(NEXT)
                .waitForRefresh();
        Assert.assertEquals(groupUpdatePage.getAlertTextAndClickOk(), "Can't be scheduled to the past");
        groupUpdatePage
                .checkCalendarClickable();
    }

    @Test
    public void test_021() throws IOException {
        systemPage.topMenu(GROUP_UPDATE);
        groupUpdatePage.waitForRefresh();
        Assert.assertTrue(HttpGetter.getUrlSource(groupUpdatePage
                .getMainTable()
                .getExportLink("auto_test_2"))
                .contains("\"InternetGatewayDevice.ManagementServer.PeriodicInformInterval\" value=\"60\""));
    }

    @Test
    public void test_022() {
        groupUpdatePage
                .goToSetParameters("auto_test_3")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .setParameter("Username", VALUE, "ftacs")
                .setParameter("Password", VALUE, "ftacs");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Completed", "auto_test_3", 10)
                .getMainTable()
                .clickOn("auto_test_3", 4);
        groupUpdatePage
                .getTable("tblTasks")
                .checkResults("PeriodicInformInterval", "60")
                .checkResults("Username", "ftacs")
                .checkResults("Password", "ftacs");
    }

    @Test
    public void test_023() {
        groupUpdatePage
                .goToSetParameters("auto_test_4")
                .setParameter("Username", VALUE, "ftacs");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Completed", "auto_test_4", 10)
                .getMainTable()
                .clickOn("auto_test_4", 4);
        groupUpdatePage
                .getTable("tblTasks")
                .checkResults("Username", "ftacs");
    }

    @Test
    public void test_024() {
        groupUpdatePage
                .goToSetParameters("auto_test_5", "tabsSettings_tblTabs")
                .clickOn("Time");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("NTPServer1", VALUE, "time.windows.com")
                .setParameter("NTPServer2", VALUE, "time.nist.gov")
                .setParameter("LocalTimeZone", VALUE, "GMT+2")
                .setParameter("LocalTimeZoneName", VALUE, "Ukraine")
                .setParameter("DaylightSavingsUsed", TRUE, null)
                .setParameter("DaylightSavingsStart", VALUE, "29.03.2020 02:00")
                .setParameter("DaylightSavingsEnd", VALUE, "25.10.2020 03:00");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Completed", "auto_test_5", 10)
                .getMainTable()
                .clickOn("auto_test_5", 4);
        groupUpdatePage
                .getTable("tblTasks")
                .checkResults("NTPServer1", "time.windows.com")
                .checkResults("NTPServer2", "time.nist.gov")
                .checkResults("LocalTimeZone", "GMT+2")
                .checkResults("LocalTimeZoneName", "Ukraine")
                .checkResults("DaylightSavingsUsed", "1")
                .checkResults("DaylightSavingsStart", "29.03.2020 02:00")
                .checkResults("DaylightSavingsEnd", "25.10.2020 03:00");
    }

    @Test
    public void test_025() {
        groupUpdatePage
                .goToSetParameters("auto_test_6", "tabsSettings_tblTabs")
                .clickOn("Time");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("LocalTimeZone", VALUE, "GMT+2");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Completed", "auto_test_6", 10)
                .getMainTable()
                .clickOn("auto_test_6", 4);
        groupUpdatePage
                .getTable("tblTasks")
                .checkResults("LocalTimeZone", "GMT+2");
    }

    @Test
    public void test_026() {
        groupUpdatePage
                .goToSetParameters("auto_test_7", "tabsSettings_tblTabs")
                .clickOn("Time");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("NTPServer1", VALUE, "time.windows.com")
                .setParameter("NTPServer2", VALUE, "time.nist.gov");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Completed", "auto_test_7", 10)
                .getMainTable()
                .clickOn("auto_test_7", 4);
        groupUpdatePage
                .getTable("tblTasks")
                .checkResults("NTPServer1", "time.windows.com")
                .checkResults("NTPServer2", "time.nist.gov");
    }

    @Test
    public void test_027() {
        groupUpdatePage
                .goToSetParameters("auto_test_8", "tabsSettings_tblTabs")
                .clickOn("WAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("Enable", TRUE, null)
                .setParameter("ConnectionType", CUSTOM, "PPPoE_Bridged")
                .setParameter("RouteProtocolRx", CUSTOM, "Off")
                .setParameter("Name", VALUE, "PPPoE_Connection")
                .setParameter("Username", VALUE, "admin")
                .setParameter("Password", VALUE, "qwerty123")
                .setParameter("PPPoEACName", VALUE, "PPPoE_AC_Name")
                .setParameter("PPPoEServiceName", VALUE, "PPPoE_Service_Name")
                .setParameter("DNSEnabled", TRUE, null)
                .setParameter("DNSOverrideAllowed", FALSE, null)
                .setParameter("DNSServers", VALUE, "8.8.8.8")
                .setParameter("MaxMRUSize", VALUE, "1492")
                .setParameter("NATEnabled", TRUE, null)
                .setParameter("ConnectionTrigger", CUSTOM, "AlwaysOn")
                .setParameter("AutoDisconnectTime, sec", VALUE, "86400")
                .setParameter("IdleDisconnectTime, sec", VALUE, "0");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Completed", "auto_test_8", 10)
                .getMainTable()
                .clickOn("auto_test_8", 4);
        groupUpdatePage
                .getTable("tblTasks")
                .checkResults("Enable", "1")
                .checkResults("ConnectionType", "PPPoE_Bridged")
                .checkResults("RouteProtocolRx", "Off")
                .checkResults("Name", "PPPoE_Connection")
                .checkResults("Username", "admin")
                .checkResults("Password", "qwerty123")
                .checkResults("PPPoEACName", "PPPoE_AC_Name")
                .checkResults("PPPoEServiceName", "PPPoE_Service_Name")
                .checkResults("DNSEnabled", "1")
                .checkResults("DNSOverrideAllowed", "0")
                .checkResults("DNSServers", "8.8.8.8")
                .checkResults("MaxMRUSize", "1492")
                .checkResults("NATEnabled", "1")
                .checkResults("ConnectionTrigger", "AlwaysOn")
                .checkResults("AutoDisconnectTime", "86400")
                .checkResults("IdleDisconnectTime", "0");
    }

    @Test
    public void test_028() {
        groupUpdatePage
                .goToSetParameters("auto_test_9", "tabsSettings_tblTabs")
                .clickOn("WAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("Enable", FALSE, null);
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Completed", "auto_test_9", 10)
                .getMainTable()
                .clickOn("auto_test_9", 4);
        groupUpdatePage
                .getTable("tblTasks")
                .checkResults("Enable", "0");
    }

    @Test
    public void test_029() {
        groupUpdatePage
                .goToSetParameters("auto_test_10", "tabsSettings_tblTabs")
                .clickOn("WAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("Enable", TRUE, null)
                .setParameter("Name", VALUE, "PPPoE_Connection_2")
                .setParameter("DNSServers", VALUE, "8.8.4.4");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Completed", "auto_test_10", 10)
                .getMainTable()
                .clickOn("auto_test_10", 4);
        groupUpdatePage
                .getTable("tblTasks")
                .checkResults("Enable", "1")
                .checkResults("Name", "PPPoE_Connection_2")
                .checkResults("DNSServers", "8.8.4.4");
    }

    @Test
    public void test_030() {
        groupUpdatePage
                .goToSetParameters("auto_test_11", "tabsSettings_tblTabs")
                .clickOn("LAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("DHCPServerConfigurable", TRUE, null)
                .setParameter("DHCPServerEnable", TRUE, null)
                .setParameter("DHCPRelay", FALSE, null)
                .setParameter("MinAddress", VALUE, "192.168.1.2")
                .setParameter("MaxAddress", VALUE, "192.168.1.254")
                .setParameter("ReservedAddresses", EMPTY_VALUE, null)
                .setParameter("SubnetMask", VALUE, "255.255.255.0")
                .setParameter("DNSServers", VALUE, "8.8.8.8")
                .setParameter("DomainName", VALUE, "friendly")
                .setParameter("IPRouters", EMPTY_VALUE, null)
                .setParameter("DHCPLeaseTime, sec", VALUE, "7200");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Completed", "auto_test_11", 10)
                .getMainTable()
                .clickOn("auto_test_11", 4);
        groupUpdatePage
                .getTable("tblTasks")
                .checkResults("DHCPServerConfigurable", "1")
                .checkResults("DHCPServerEnable", "1")
                .checkResults("DHCPRelay", "0")
                .checkResults("MinAddress", "192.168.1.2")
                .checkResults("MaxAddress", "192.168.1.254")
                .checkResults("ReservedAddresses", " ")
                .checkResults("SubnetMask", "255.255.255.0")
                .checkResults("DNSServers", "8.8.8.8")
                .checkResults("DomainName", "friendly")
                .checkResults("IPRouters", " ")
                .checkResults("DHCPLeaseTime", "7200");
    }

    @Test
    public void test_031() {
        groupUpdatePage
                .goToSetParameters("auto_test_12", "tabsSettings_tblTabs")
                .clickOn("LAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("ReservedAddresses", VALUE, "192.168.100");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Completed", "auto_test_12", 10)
                .getMainTable()
                .clickOn("auto_test_12", 4);
        groupUpdatePage
                .getTable("tblTasks")
                .checkResults("ReservedAddresses", "192.168.100");
    }

    @Test
    public void test_032() {
        groupUpdatePage
                .goToSetParameters("auto_test_13", "tabsSettings_tblTabs")
                .clickOn("LAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("MinAddress", VALUE, "192.168.1.50")
                .setParameter("MaxAddress", VALUE, "192.168.1.200");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Completed", "auto_test_13", 10)
                .getMainTable()
                .clickOn("auto_test_13", 4);
        groupUpdatePage
                .getTable("tblTasks")
                .checkResults("MinAddress", "192.168.1.50")
                .checkResults("MaxAddress", "192.168.1.200");
    }

    @Test
    public void test_033() {
        groupUpdatePage
                .goToSetParameters("auto_test_14", "tabsSettings_tblTabs")
                .clickOn("Wireless");
        System.out.println(groupUpdatePage
                .getTable("tblParamsValue").getCellText(11,0));

//                .setParameter("Enable", TRUE, null)
//                .setParameter("Channel", VALUE, "6")
//                .setParameter("SSID", VALUE, "Home_WiFi")
//                .setParameter("BeaconType", CUSTOM, "WPA")
//                .setParameter("WEPKeyIndex", VALUE, "0")
//                .setParameter("BasicEncryptionModes", CUSTOM, "None")
//                .setParameter("BasicAuthenticationMode", CUSTOM, "EAPAuthentication")
//                .setParameter("WPAEncryptionModes", CUSTOM, "AESEncryption")
//                .setParameter("WPAAuthenticationMode", CUSTOM, "EAPAuthentication")
//                .setParameter("IEEE11iEncryptionModes", CUSTOM, "AESEncryption")
//                .setParameter("IEEE11iAuthenticationMode", CUSTOM, "EAPandPSKAuthentication");
//        groupUpdatePage
//                .waitForRefresh()
//                .globalButtons(NEXT)
//                .globalButtons(SAVE_AND_ACTIVATE)
//                .okButtonPopUp()
//                .waitForStatus("Completed", "auto_test_14", 10)
//                .getMainTable()
//                .clickOn("auto_test_14", 4);
//        groupUpdatePage
//                .getTable("tblTasks")
//                .checkResults("Enable", "1")
//                .checkResults("Channel", "6")
//                .checkResults("SSID", "Home_WiFi")
//                .checkResults("BeaconType", "WPA")
//                .checkResults("WEPKeyIndex", "0")
//                .checkResults("BasicEncryptionModes", "None")
//                .checkResults("BasicAuthenticationMode", "EAPAuthentication")
//                .checkResults("WPAEncryptionModes", "AESEncryption")
//                .checkResults("WPAAuthenticationMode", "EAPAuthentication")
//                .checkResults("IEEE11iEncryptionModes", "AESEncryption")
//                .checkResults("IEEE11iAuthenticationMode", "EAPandPSKAuthentication");
    }
}