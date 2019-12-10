package com.friendly.aqa.test;

import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.HttpGetter;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.friendly.aqa.test.BaseTestCase;

import java.io.IOException;

import static com.friendly.aqa.pageobject.BasePage.waitForUpdate;
import static com.friendly.aqa.pageobject.GlobalButtons.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.*;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.utils.Table.Conditions.EQUAL;
import static com.friendly.aqa.utils.Table.Parameter.*;
import static com.friendly.aqa.utils.Table.Policy.*;

public class GroupUpdateTests extends BaseTestCase {
    @Test
    public void test_001() {
        groupUpdatePage.deleteAll();
        groupUpdatePage.topMenu(GROUP_UPDATE);
        waitForUpdate();
        Assert.assertTrue(groupUpdatePage.mainTableIsAbsent());
    }

    @Test
    public void test_002() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .globalButtons(CANCEL);
        Assert.assertTrue(groupUpdatePage.mainTableIsAbsent());
    }

    @Test
    public void test_003() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName(testName)
                .globalButtons(CANCEL);
        waitForUpdate();
        Assert.assertTrue(groupUpdatePage.mainTableIsAbsent());
    }

    @Test
    public void test_004() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName(testName)
                .selectSendTo()
                .showList();
        Assert.assertTrue(groupUpdatePage.serialNumberTableIsPresent());
    }

    @Test
    public void test_005() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName(testName)
                .createGroup()
                .assertButtonIsPresent(FINISH);
        groupUpdatePage
                .globalButtons(CANCEL);
        waitForUpdate();
//        groupUpdatePage.pause(500);
        Assert.assertEquals(groupUpdatePage.getAttributeById("txtName", "value"), testName);
    }

    @Test
    //A Bug was found while pressing "Next" button when "Name group" is filled;
    public void test_006() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName(testName)
                .selectSendTo()
                .createGroup()
                .fillName("test_group_name");
        //Bug is present when press "Next" button
    }

    @Test
    public void test_011() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName(testName)
                .selectSendTo("Individual")
                .getTable("tblDevices")
                .clickOn(1, 0);
        waitForUpdate();
        Assert.assertTrue(groupUpdatePage.isButtonActive(NEXT));
        groupUpdatePage.getTable("tblDevices")
                .clickOn(1, 0);
        waitForUpdate();
        Assert.assertFalse(groupUpdatePage.isButtonActive(NEXT));
    }

    @Test
    //Doesn't work with Edge
    public void test_012() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName(testName)
                .selectSendTo("Import")
                .selectImportDevicesFile()
                .showList();
        Assert.assertEquals(groupUpdatePage.getTable("tblDevices").getCellText(1, 0), "FT001SN00001SD18F7FFF521");
    }

    @Test
    public void test_014() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName(testName)
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
                .goToSetParameters(testName)
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        groupUpdatePage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp();
        waitForUpdate();
        Assert.assertEquals(groupUpdatePage
                .getMainTable()
                .getCellText(4, testName, 1), "Not active");
        setTargetTestName();
    }

    @Test
    public void test_016() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .getMainTable()
                .clickOn(targetTestName, 4);
        groupUpdatePage
                .globalButtons(EDIT)
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .getTable("tblTasks")
                .clickOn("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", 3);
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "61");
        groupUpdatePage
                .globalButtons(NEXT)
                .globalButtons(SAVE)

                .okButtonPopUp()
                .getMainTable()
                .clickOn(targetTestName, 4);
        groupUpdatePage
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.ManagementServer.")
                .checkResults("PeriodicInformInterval", "61");
    }

    @Test
    public void test_017() {
        groupUpdatePage
                .goToSetParameters(testName)
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        groupUpdatePage
                .saveAndActivate(testName);
        setTargetTestName();
    }

    @Test
    public void test_018() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .getMainTable()
                .clickOn(targetTestName, 4);
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
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName(testName)
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .timeHoursSelect(0)
                .globalButtons(NEXT);
        waitForUpdate();
        Assert.assertEquals(groupUpdatePage.getAlertTextAndClickOk(), "Update can't scheduled to past"/*"Can't be scheduled to the past"*/);
        groupUpdatePage
                .checkIsCalendarClickable();
    }

    @Test
    public void test_021() throws IOException {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        Assert.assertTrue(HttpGetter.getUrlSource(groupUpdatePage
                .getMainTable()
                .getExportLink(targetTestName))
                .contains("\"InternetGatewayDevice.ManagementServer.PeriodicInformInterval\" value=\"60\""));
    }

    @Test
    public void test_022() {
        groupUpdatePage
                .goToSetParameters(testName)
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .setParameter("Username", VALUE, "ftacs")
                .setParameter("Password", VALUE, "ftacs");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.ManagementServer.")
                .checkResults("PeriodicInformInterval", "60")
                .checkResults("Username", "ftacs")
                .checkResults("Password", "ftacs");
    }

    @Test
    public void test_023() {
        groupUpdatePage
                .goToSetParameters(testName)
                .setParameter("Username", VALUE, "ftacs");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.Username", "ftacs");
    }

    @Test
    public void test_024() {
        groupUpdatePage
                .goToSetParameters(testName, "tabsSettings_tblTabs")
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
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.Time.")
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
                .goToSetParameters(testName, "tabsSettings_tblTabs")
                .clickOn("Time");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("LocalTimeZone", VALUE, "GMT+2");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.Time.")
                .checkResults("LocalTimeZone", "GMT+2");
    }

    @Test
    public void test_026() {
        groupUpdatePage
                .goToSetParameters(testName, "tabsSettings_tblTabs")
                .clickOn("Time");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("NTPServer1", VALUE, "time.windows.com")
                .setParameter("NTPServer2", VALUE, "time.nist.gov");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.Time.")
                .checkResults("NTPServer1", "time.windows.com")
                .checkResults("NTPServer2", "time.nist.gov");
    }

    @Test
    public void test_027() {
        groupUpdatePage
                .goToSetParameters(testName, "tabsSettings_tblTabs")
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
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.2.")
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
                .goToSetParameters(testName, "tabsSettings_tblTabs")
                .clickOn("WAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("Enable", FALSE, null);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.2.")
                .checkResults("Enable", "0");
    }

    @Test
    public void test_029() {
        groupUpdatePage
                .goToSetParameters(testName, "tabsSettings_tblTabs")
                .clickOn("WAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("Enable", TRUE, null)
                .setParameter("Name", VALUE, "PPPoE_Connection_2")
                .setParameter("DNSServers", VALUE, "8.8.4.4");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.2.")
                .checkResults("Enable", "1")
                .checkResults("Name", "PPPoE_Connection_2")
                .checkResults("DNSServers", "8.8.4.4");
    }

    @Test
    public void test_030() {
        groupUpdatePage
                .goToSetParameters(testName, "tabsSettings_tblTabs")
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
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.")
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
                .goToSetParameters(testName, "tabsSettings_tblTabs")
                .clickOn("LAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("ReservedAddresses", VALUE, "192.168.100");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.")
                .checkResults("ReservedAddresses", "192.168.100");
    }

    @Test
    public void test_032() {
        groupUpdatePage
                .goToSetParameters(testName, "tabsSettings_tblTabs")
                .clickOn("LAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("MinAddress", VALUE, "192.168.1.50")
                .setParameter("MaxAddress", VALUE, "192.168.1.200");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.")
                .checkResults("MinAddress", "192.168.1.50")
                .checkResults("MaxAddress", "192.168.1.200");
    }

    @Test
    public void test_033() {
        groupUpdatePage
                .goToSetParameters(testName, "tabsSettings_tblTabs")
                .clickOn("Wireless");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("Enable", TRUE, null)
                .setParameter("Channel", VALUE, "6")
                .setParameter("SSID", VALUE, "Home_WiFi")
                .setParameter("BeaconType", CUSTOM, "WPA")
                .setParameter("WEPKeyIndex", VALUE, "0")
                .setParameter("BasicEncryptionModes", CUSTOM, "None")
                .setParameter("BasicAuthenticationMode", CUSTOM, "EAPAuthentication")
                .setParameter("WPAEncryptionModes", CUSTOM, "AESEncryption")
                .setParameter("WPAAuthenticationMode", CUSTOM, "EAPAuthentication")
                .setParameter("IEEE11iEncryptionModes", CUSTOM, "AESEncryption")
                .setParameter("IEEE11iAuthenticationMode", CUSTOM, "EAPandPSKAuthentication");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.")
                .checkResults("Enable", "1")
                .checkResults("Channel", "6")
                .checkResults("SSID", "Home_WiFi")
                .checkResults("BeaconType", "WPA")
                .checkResults("WEPKeyIndex", "0")
                .checkResults("BasicEncryptionModes", "None")
                .checkResults("BasicAuthenticationMode", "EAPAuthentication")
                .checkResults("WPAEncryptionModes", "AESEncryption")
                .checkResults("WPAAuthenticationMode", "EAPAuthentication")
                .checkResults("IEEE11iEncryptionModes", "AESEncryption")
                .checkResults("IEEE11iAuthenticationMode", "EAPandPSKAuthentication");
    }

    @Test
    public void test_034() {
        groupUpdatePage
                .goToSetParameters(testName, "tabsSettings_tblTabs")
                .clickOn("Wireless");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("Channel", VALUE, "11");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.")
                .checkResults("Channel", "11");
    }

    @Test
    public void test_035() {
        groupUpdatePage
                .goToSetParameters(testName, "tabsSettings_tblTabs")
                .clickOn("Wireless");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("Channel", VALUE, "1")
                .setParameter("SSID", VALUE, "WiFi");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.")
                .checkResults("Channel", "1")
                .checkResults("SSID", "WiFi");
    }

    @Test
    public void test_036() {
        groupUpdatePage
                .goToSetParameters("audiocodes", "MP252", testName, "tabsSettings_tblTabs")
                .clickOn("DSL settings");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("Enable", TRUE, null)
                .setParameter("LinkType", CUSTOM, "PPPoE")
                .setParameter("DestinationAddress", VALUE, "35.12.48.78")
                .setParameter("ATMEncapsulation", CUSTOM, "LLC");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.")
                .checkResults("Enable", "1")
                .checkResults("LinkType", "PPPoE")
                .checkResults("DestinationAddress", "35.12.48.78")
                .checkResults("ATMEncapsulation", "LLC");
    }

    @Test
    public void test_037() {
        groupUpdatePage
                .goToSetParameters("audiocodes", "MP252", testName, "tabsSettings_tblTabs")
                .clickOn("DSL settings");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("DestinationAddress", VALUE, "95.217.85.220");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.")
                .checkResults("DestinationAddress", "95.217.85.220");
    }

    @Test
    public void test_038() {
        groupUpdatePage
                .goToSetParameters("audiocodes", "MP252", testName, "tabsSettings_tblTabs")
                .clickOn("DSL settings");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("LinkType", CUSTOM, "EoA")
                .setParameter("ATMEncapsulation", CUSTOM, "VCMUX");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.")
                .checkResults("LinkType", "EoA")
                .checkResults("ATMEncapsulation", "VCMUX");
    }

    @Test
    public void test_039() {
        groupUpdatePage
                .goToSetParameters("audiocodes", "MP252", testName, "tabsSettings_tblTabs")
                .clickOn("VoIP settings");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("Name", VALUE, "VoIP")
                .setParameter("Enable", CUSTOM, "Enabled")
                .setParameter("Reset", FALSE, null)
                .setParameter("SignalingProtocol", VALUE, "SIP")
                .setParameter("DTMFMethod", CUSTOM, "SIPInfo")
                .setParameter("Region", VALUE, "USA")
                .setParameter("DigitMapEnable", TRUE, null)
                .setParameter("DigitMap", EMPTY_VALUE, null)
                .setParameter("STUNEnable", TRUE, null)
                .setParameter("STUNServer", VALUE, "12.13.14.15")
                .setParameter("FaxPassThrough", CUSTOM, "Auto")
                .setParameter("ModemPassThrough", CUSTOM, "Auto");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.")
                .checkResults("Name", "VoIP")
                .checkResults("Enable", "Enabled")
                .checkResults("Reset", "0")
                .checkResults("SignalingProtocol", "SIP")
                .checkResults("DTMFMethod", "SIPInfo")
                .checkResults("Region", "USA")
                .checkResults("DigitMapEnable", "1")
                .checkResults("DigitMap", " ")
                .checkResults("STUNEnable", "1")
                .checkResults("STUNServer", "12.13.14.15")
                .checkResults("FaxPassThrough", "Auto")
                .checkResults("ModemPassThrough", "Auto");
    }

    @Test
    public void test_040() {
        groupUpdatePage
                .goToSetParameters("audiocodes", "MP252", testName, "tabsSettings_tblTabs")
                .clickOn("VoIP settings");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("Name", VALUE, "VoIP_new");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.")
                .checkResults("Name", "VoIP_new");
    }

    @Test
    public void test_041() {
        groupUpdatePage
                .goToSetParameters("audiocodes", "MP252", testName, "tabsSettings_tblTabs")
                .clickOn("VoIP settings");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("Region", VALUE, "EU")
                .setParameter("DigitMapEnable", FALSE, null);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.")
                .checkResults("Region", "EU")
                .checkResults("DigitMapEnable", "0");
    }

    @Test
    public void test_042() {
        groupUpdatePage
                .goToSetPolicies(testName, "tblParamsValue")
                .setPolicy("URL", ACTIVE, ALL)
                .setPolicy("Username", ACTIVE, ALL)
                .setPolicy("Password", ACTIVE, ALL)
                .setPolicy("PeriodicInformEnable", ACTIVE, ALL)
                .setPolicy("PeriodicInformInterval, sec", ACTIVE, ALL)
                .setPolicy("PeriodicInformTime", ACTIVE, ALL)
                .setPolicy("ParameterKey", ACTIVE, ALL)
                .setPolicy("ConnectionRequestURL", ACTIVE, ALL)
                .setPolicy("ConnectionRequestUsername", ACTIVE, ALL)
                .setPolicy("ConnectionRequestPassword", ACTIVE, ALL)
                .setPolicy("UpgradesManaged", ACTIVE, ALL);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.ManagementServer.")
                .checkResults("URL", "Notification=Active Access=All")
                .checkResults("Username", "Notification=Active Access=All")
                .checkResults("Password", "Notification=Active Access=All")
                .checkResults("PeriodicInformEnable", "Notification=Active Access=All")
                .checkResults("PeriodicInformInterval", "Notification=Active Access=All")
                .checkResults("PeriodicInformTime", "Notification=Active Access=All")
                .checkResults("ParameterKey", "Notification=Active Access=All")
                .checkResults("ConnectionRequestURL", "Notification=Active Access=All")
                .checkResults("ConnectionRequestUsername", "Notification=Active Access=All")
                .checkResults("ConnectionRequestPassword", "Notification=Active Access=All")
                .checkResults("UpgradesManaged", "Notification=Active Access=All");
    }

    @Test
    public void test_043() {
        groupUpdatePage
                .goToSetPolicies(testName, "tblParamsValue")
                .setPolicy("URL", ACTIVE, ACS_ONLY)
                .setPolicy("URL", DEFAULT, null);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.URL", "Access=AcsOnly");
    }

    @Test
    public void test_044() {
        groupUpdatePage
                .goToSetPolicies(testName, "tblParamsValue")
                .setPolicy("Username", OFF, ALL)
                .setPolicy("Password", OFF, ALL)
                .setPolicy("Username", null, DEFAULT)
                .setPolicy("Password", null, DEFAULT);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.ManagementServer.")
                .checkResults("Username", "Notification=Off ")
                .checkResults("Password", "Notification=Off ");
    }

    @Test
    public void test_045() {
        groupUpdatePage
                .goToSetPolicies(testName, "tblParamsValue")
                .setPolicy("URL", PASSIVE, null)
                .setPolicy("Username", ACTIVE, null)
                .setPolicy("Password", OFF, null)
                .setPolicy("PeriodicInformEnable", ACTIVE, null)
                .setPolicy("PeriodicInformEnable", DEFAULT, null)
                .setPolicy("PeriodicInformInterval, sec", null, ACS_ONLY)
                .setPolicy("PeriodicInformTime", null, ALL)
                .setPolicy("ParameterKey", null, ALL)
                .setPolicy("ParameterKey", null, DEFAULT);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.ManagementServer.")
                .checkResults("URL", "Notification=Passive ")
                .checkResults("Username", "Notification=Active ")
                .checkResults("Password", "Notification=Off ")
                .checkResults("PeriodicInformInterval", "Access=AcsOnly")
                .checkResults("PeriodicInformTime", "Access=All")
                .assertAbsenceOfParameter("PeriodicInformEnable")
                .assertAbsenceOfParameter("ParameterKey");
    }

    @Test
    public void test_046() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("Time");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("NTPServer1", ACTIVE, ALL)
                .setPolicy("NTPServer2", ACTIVE, ALL)
                .setPolicy("CurrentLocalTime", ACTIVE, ALL)
                .setPolicy("LocalTimeZone", ACTIVE, ALL)
                .setPolicy("LocalTimeZoneName", ACTIVE, ALL)
                .setPolicy("DaylightSavingsUsed", ACTIVE, ALL)
                .setPolicy("DaylightSavingsStart", ACTIVE, ALL)
                .setPolicy("DaylightSavingsEnd", ACTIVE, ALL);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.Time.")
                .checkResults("NTPServer1", "Notification=Active Access=All")
                .checkResults("NTPServer2", "Notification=Active Access=All")
                .checkResults("CurrentLocalTime", "Notification=Active Access=All")
                .checkResults("LocalTimeZone", "Notification=Active Access=All")
                .checkResults("LocalTimeZoneName", "Notification=Active Access=All")
                .checkResults("DaylightSavingsUsed", "Notification=Active Access=All")
                .checkResults("DaylightSavingsStart", "Notification=Active Access=All")
                .checkResults("DaylightSavingsEnd", "Notification=Active Access=All");
    }

    @Test
    public void test_047() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("Time");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("NTPServer1", ACTIVE, ACS_ONLY)
                .setPolicy("NTPServer1", DEFAULT, null);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.Time.NTPServer1", "Access=AcsOnly");
    }

    @Test
    public void test_048() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("Time");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("NTPServer1", OFF, ALL)
                .setPolicy("NTPServer2", OFF, ALL)
                .setPolicy("NTPServer1", null, DEFAULT)
                .setPolicy("NTPServer2", null, DEFAULT);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.Time.")
                .checkResults("NTPServer1", "Notification=Off ")
                .checkResults("NTPServer2", "Notification=Off ");
    }

    @Test
    public void test_049() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("Time");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("NTPServer1", PASSIVE, null)
                .setPolicy("NTPServer2", ACTIVE, null)
                .setPolicy("CurrentLocalTime", OFF, null)
                .setPolicy("LocalTimeZone", ACTIVE, null)
                .setPolicy("LocalTimeZone", DEFAULT, null)
                .setPolicy("LocalTimeZoneName", null, ACS_ONLY)
                .setPolicy("DaylightSavingsUsed", null, ALL)
                .setPolicy("DaylightSavingsStart", null, ALL)
                .setPolicy("DaylightSavingsStart", null, DEFAULT);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.Time.")
                .checkResults("NTPServer1", "Notification=Passive ")
                .checkResults("NTPServer2", "Notification=Active ")
                .checkResults("CurrentLocalTime", "Notification=Off ")
                .checkResults("LocalTimeZoneName", "Access=AcsOnly")
                .checkResults("DaylightSavingsUsed", "Access=All")
                .assertAbsenceOfParameter("LocalTimeZone")
                .assertAbsenceOfParameter("DaylightSavingsStart");
    }

    @Test
    public void test_050() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("WAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Enable", ACTIVE, ALL)
                .setPolicy("ConnectionStatus", ACTIVE, ALL)
                .setPolicy("PossibleConnectionTypes", ACTIVE, ALL)
                .setPolicy("ConnectionType", ACTIVE, ALL)
                .setPolicy("RouteProtocolRx", ACTIVE, ALL)
                .setPolicy("Name", ACTIVE, ALL)
                .setPolicy("Username", ACTIVE, ALL)
                .setPolicy("Password", ACTIVE, ALL)
                .setPolicy("PPPoEACName", ACTIVE, ALL)
                .setPolicy("PPPoEServiceName", ACTIVE, ALL)
                .setPolicy("DNSEnabled", ACTIVE, ALL)
                .setPolicy("DNSOverrideAllowed", ACTIVE, ALL)
                .setPolicy("DNSServers", ACTIVE, ALL)
                .setPolicy("MaxMRUSize", ACTIVE, ALL)
                .setPolicy("NATEnabled", ACTIVE, ALL)
                .setPolicy("ConnectionTrigger", ACTIVE, ALL)
                .setPolicy("AutoDisconnectTime, sec", ACTIVE, ALL)
                .setPolicy("IdleDisconnectTime, sec", ACTIVE, ALL);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.2.")
                .checkResults("Enable", "Notification=Active Access=All")
                .checkResults("ConnectionStatus", "Notification=Active Access=All")
                .checkResults("PossibleConnectionTypes", "Notification=Active Access=All")
                .checkResults("ConnectionType", "Notification=Active Access=All")
                .checkResults("RouteProtocolRx", "Notification=Active Access=All")
                .checkResults("Name", "Notification=Active Access=All")
                .checkResults("Username", "Notification=Active Access=All")
                .checkResults("Password", "Notification=Active Access=All")
                .checkResults("PPPoEACName", "Notification=Active Access=All")
                .checkResults("PPPoEServiceName", "Notification=Active Access=All")
                .checkResults("DNSEnabled", "Notification=Active Access=All")
                .checkResults("DNSOverrideAllowed", "Notification=Active Access=All")
                .checkResults("DNSServers", "Notification=Active Access=All")
                .checkResults("MaxMRUSize", "Notification=Active Access=All")
                .checkResults("NATEnabled", "Notification=Active Access=All")
                .checkResults("ConnectionTrigger", "Notification=Active Access=All")
                .checkResults("AutoDisconnectTime", "Notification=Active Access=All")
                .checkResults("IdleDisconnectTime", "Notification=Active Access=All");
    }

    @Test
    public void test_051() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("WAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Enable", ACTIVE, ACS_ONLY)
                .setPolicy("Enable", DEFAULT, null);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.2.")
                .checkResults("Enable", "Access=AcsOnly");
    }

    @Test
    public void test_052() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("WAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Username", OFF, ALL)
                .setPolicy("Password", OFF, ALL)
                .setPolicy("Username", null, DEFAULT)
                .setPolicy("Password", null, DEFAULT);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.2.")
                .checkResults("Username", "Notification=Off ")
                .checkResults("Password", "Notification=Off ");
    }

    @Test
    public void test_053() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("WAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Enable", PASSIVE, null)
                .setPolicy("ConnectionStatus", ACTIVE, null)
                .setPolicy("PossibleConnectionTypes", OFF, null)
                .setPolicy("ConnectionType", ACTIVE, null)
                .setPolicy("ConnectionType", DEFAULT, null)
                .setPolicy("RouteProtocolRx", null, ACS_ONLY)
                .setPolicy("Name", null, ALL)
                .setPolicy("Username", null, ALL)
                .setPolicy("Username", null, DEFAULT);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.2.")
                .checkResults("Enable", "Notification=Passive ")
                .checkResults("ConnectionStatus", "Notification=Active ")
                .checkResults("PossibleConnectionTypes", "Notification=Off ")
                .checkResults("RouteProtocolRx", "Access=AcsOnly")
                .checkResults("Name", "Access=All")
                .assertAbsenceOfParameter("ConnectionType")
                .assertAbsenceOfParameter("Username");
    }

    @Test
    public void test_054() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("LAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("DHCPServerConfigurable", ACTIVE, ALL)
                .setPolicy("DHCPServerEnable", ACTIVE, ALL)
                .setPolicy("DHCPRelay", ACTIVE, ALL)
                .setPolicy("MinAddress", ACTIVE, ALL)
                .setPolicy("MaxAddress", ACTIVE, ALL)
                .setPolicy("ReservedAddresses", ACTIVE, ALL)
                .setPolicy("SubnetMask", ACTIVE, ALL)
                .setPolicy("DNSServers", ACTIVE, ALL)
                .setPolicy("DomainName", ACTIVE, ALL)
                .setPolicy("IPRouters", ACTIVE, ALL)
                .setPolicy("DHCPLeaseTime, sec", ACTIVE, ALL);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.")
                .checkResults("DHCPServerConfigurable", "Notification=Active Access=All")
                .checkResults("DHCPServerEnable", "Notification=Active Access=All")
                .checkResults("DHCPRelay", "Notification=Active Access=All")
                .checkResults("MinAddress", "Notification=Active Access=All")
                .checkResults("MaxAddress", "Notification=Active Access=All")
                .checkResults("ReservedAddresses", "Notification=Active Access=All")
                .checkResults("SubnetMask", "Notification=Active Access=All")
                .checkResults("DNSServers", "Notification=Active Access=All")
                .checkResults("DomainName", "Notification=Active Access=All")
                .checkResults("IPRouters", "Notification=Active Access=All")
                .checkResults("DHCPLeaseTime", "Notification=Active Access=All");
    }

    @Test
    public void test_055() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("LAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("DHCPServerConfigurable", ACTIVE, ACS_ONLY)
                .setPolicy("DHCPServerConfigurable", DEFAULT, null);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.")
                .checkResults("DHCPServerConfigurable", "Access=AcsOnly");
    }

    @Test
    public void test_056() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("LAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("MinAddress", OFF, ALL)
                .setPolicy("MaxAddress", OFF, ALL)
                .setPolicy("MinAddress", null, DEFAULT)
                .setPolicy("MaxAddress", null, DEFAULT);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.")
                .checkResults("MinAddress", "Notification=Off ")
                .checkResults("MaxAddress", "Notification=Off ");
    }

    @Test
    public void test_057() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("LAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("DHCPServerConfigurable", PASSIVE, null)
                .setPolicy("DHCPServerEnable", ACTIVE, null)
                .setPolicy("DHCPRelay", OFF, null)
                .setPolicy("MinAddress", ACTIVE, null)
                .setPolicy("MinAddress", DEFAULT, null)
                .setPolicy("MaxAddress", null, ACS_ONLY)
                .setPolicy("ReservedAddresses", null, ALL)
                .setPolicy("SubnetMask", null, ALL)
                .setPolicy("SubnetMask", null, DEFAULT);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.")
                .checkResults("DHCPServerConfigurable", "Notification=Passive ")
                .checkResults("DHCPServerEnable", "Notification=Active ")
                .checkResults("DHCPRelay", "Notification=Off ")
                .checkResults("MaxAddress", "Access=AcsOnly")
                .checkResults("ReservedAddresses", "Access=All")
                .assertAbsenceOfParameter("MinAddress")
                .assertAbsenceOfParameter("SubnetMask");
    }

    @Test
    public void test_058() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("Wireless");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Enable", ACTIVE, ALL)
                .setPolicy("BSSID", ACTIVE, ALL)
                .setPolicy("Channel", ACTIVE, ALL)
                .setPolicy("SSID", ACTIVE, ALL)
                .setPolicy("BeaconType", ACTIVE, ALL)
                .setPolicy("WEPKeyIndex", ACTIVE, ALL)
                .setPolicy("WEPEncryptionLevel", ACTIVE, ALL)
                .setPolicy("BasicEncryptionModes", ACTIVE, ALL)
                .setPolicy("BasicAuthenticationMode", ACTIVE, ALL)
                .setPolicy("WPAEncryptionModes", ACTIVE, ALL)
                .setPolicy("WPAAuthenticationMode", ACTIVE, ALL)
                .setPolicy("IEEE11iEncryptionModes", ACTIVE, ALL)
                .setPolicy("IEEE11iAuthenticationMode", ACTIVE, ALL);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.")
                .checkResults("Enable", "Notification=Active Access=All")
                .checkResults("BSSID", "Notification=Active Access=All")
                .checkResults("Channel", "Notification=Active Access=All")
                .checkResults("SSID", "Notification=Active Access=All")
                .checkResults("BeaconType", "Notification=Active Access=All")
                .checkResults("WEPKeyIndex", "Notification=Active Access=All")
                .checkResults("WEPEncryptionLevel", "Notification=Active Access=All")
                .checkResults("BasicEncryptionModes", "Notification=Active Access=All")
                .checkResults("BasicAuthenticationMode", "Notification=Active Access=All")
                .checkResults("WPAEncryptionModes", "Notification=Active Access=All")
                .checkResults("WPAAuthenticationMode", "Notification=Active Access=All")
                .checkResults("IEEE11iEncryptionModes", "Notification=Active Access=All")
                .checkResults("IEEE11iAuthenticationMode", "Notification=Active Access=All");
    }

    @Test
    public void test_059() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("Wireless");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Enable", ACTIVE, ACS_ONLY)
                .setPolicy("Enable", DEFAULT, null);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.")
                .checkResults("Enable", "Access=AcsOnly");
    }

    @Test
    public void test_060() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("Wireless");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Enable", OFF, ALL)
                .setPolicy("BSSID", OFF, ALL)
                .setPolicy("Enable", null, DEFAULT)
                .setPolicy("BSSID", null, DEFAULT);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.")
                .checkResults("Enable", "Notification=Off ")
                .checkResults("BSSID", "Notification=Off ");
    }

    @Test
    public void test_061() {
        groupUpdatePage
                .goToSetPolicies(testName, "tabsSettings_tblTabs")
                .clickOn("Wireless");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Enable", PASSIVE, null)
                .setPolicy("BSSID", ACTIVE, null)
                .setPolicy("Channel", OFF, null)
                .setPolicy("SSID", ACTIVE, null)
                .setPolicy("SSID", DEFAULT, null)
                .setPolicy("BeaconType", null, ACS_ONLY)
                .setPolicy("WEPKeyIndex", null, ALL)
                .setPolicy("WEPEncryptionLevel", null, ALL)
                .setPolicy("WEPEncryptionLevel", null, DEFAULT);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.")
                .checkResults("Enable", "Notification=Passive ")
                .checkResults("BSSID", "Notification=Active ")
                .checkResults("Channel", "Notification=Off ")
                .checkResults("BeaconType", "Access=AcsOnly")
                .checkResults("WEPKeyIndex", "Access=All")
                .assertAbsenceOfParameter("SSID")
                .assertAbsenceOfParameter("WEPEncryptionLevel");
    }

    @Test
    public void test_062() {
        groupUpdatePage
                .goToSetPolicies("audiocodes", "MP252", testName, "tabsSettings_tblTabs")
                .clickOn("DSL settings");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Enable", ACTIVE, ALL)
                .setPolicy("LinkStatus", ACTIVE, ALL)
                .setPolicy("LinkType", ACTIVE, ALL)
                .setPolicy("DestinationAddress", ACTIVE, ALL)
                .setPolicy("ATMEncapsulation", ACTIVE, ALL);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.")
                .checkResults("Enable", "Notification=Active Access=All")
                .checkResults("LinkStatus", "Notification=Active Access=All")
                .checkResults("LinkType", "Notification=Active Access=All")
                .checkResults("DestinationAddress", "Notification=Active Access=All")
                .checkResults("ATMEncapsulation", "Notification=Active Access=All");
    }

    @Test
    public void test_063() {
        groupUpdatePage
                .goToSetPolicies("audiocodes", "MP252", testName, "tabsSettings_tblTabs")
                .clickOn("DSL settings");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Enable", ACTIVE, ACS_ONLY)
                .setPolicy("Enable", DEFAULT, null);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.")
                .checkResults("Enable", "Access=AcsOnly");
    }

    @Test
    public void test_064() {
        groupUpdatePage
                .goToSetPolicies("audiocodes", "MP252", testName, "tabsSettings_tblTabs")
                .clickOn("DSL settings");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Enable", OFF, ALL)
                .setPolicy("LinkStatus", OFF, ALL)
                .setPolicy("Enable", null, DEFAULT)
                .setPolicy("LinkStatus", null, DEFAULT);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.")
                .checkResults("Enable", "Notification=Off ")
                .checkResults("LinkStatus", "Notification=Off ");
    }

    @Test
    public void test_065() {
        groupUpdatePage
                .goToSetPolicies("audiocodes", "MP252", testName, "tabsSettings_tblTabs")
                .clickOn("DSL settings");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Enable", PASSIVE, null)
                .setPolicy("LinkStatus", ACTIVE, ACS_ONLY)
                .setPolicy("LinkType", OFF, ALL)
                .setPolicy("DestinationAddress", ACTIVE, ALL)
                .setPolicy("DestinationAddress", DEFAULT, DEFAULT);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANDSLLinkConfig.")
                .checkResults("Enable", "Notification=Passive ")
                .checkResults("LinkStatus", "Notification=Active Access=AcsOnly")
                .checkResults("LinkType", "Notification=Off Access=All")
                .assertAbsenceOfParameter("DestinationAddress");
    }

    @Test
    public void test_066() {
        groupUpdatePage
                .goToSetPolicies("audiocodes", "MP252", testName, "tabsSettings_tblTabs")
                .clickOn("VoIP settings");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Name", ACTIVE, ALL)
                .setPolicy("Enable", ACTIVE, ALL)
                .setPolicy("Reset", ACTIVE, ALL)
                .setPolicy("SignalingProtocol", ACTIVE, ALL)
                .setPolicy("DTMFMethod", ACTIVE, ALL)
                .setPolicy("Region", ACTIVE, ALL)
                .setPolicy("DigitMapEnable", ACTIVE, ALL)
                .setPolicy("DigitMap", ACTIVE, ALL)
                .setPolicy("STUNEnable", ACTIVE, ALL)
                .setPolicy("STUNServer", ACTIVE, ALL)
                .setPolicy("FaxPassThrough", ACTIVE, ALL)
                .setPolicy("ModemPassThrough", ACTIVE, ALL);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.")
                .checkResults("Name", "Notification=Active Access=All")
                .checkResults("Enable", "Notification=Active Access=All")
                .checkResults("Reset", "Notification=Active Access=All")
                .checkResults("SignalingProtocol", "Notification=Active Access=All")
                .checkResults("DTMFMethod", "Notification=Active Access=All")
                .checkResults("Region", "Notification=Active Access=All")
                .checkResults("DigitMapEnable", "Notification=Active Access=All")
                .checkResults("DigitMap", "Notification=Active Access=All")
                .checkResults("STUNEnable", "Notification=Active Access=All")
                .checkResults("STUNServer", "Notification=Active Access=All")
                .checkResults("FaxPassThrough", "Notification=Active Access=All")
                .checkResults("ModemPassThrough", "Notification=Active Access=All");
    }

    @Test
    public void test_067() {
        groupUpdatePage
                .goToSetPolicies("audiocodes", "MP252", testName, "tabsSettings_tblTabs")
                .clickOn("VoIP settings");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Enable", ACTIVE, ACS_ONLY)
                .setPolicy("Enable", DEFAULT, null);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.")
                .checkResults("Enable", "Access=AcsOnly");
    }

    @Test
    public void test_068() {
        groupUpdatePage
                .goToSetPolicies("audiocodes", "MP252", testName, "tabsSettings_tblTabs")
                .clickOn("VoIP settings");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Enable", OFF, ALL)
                .setPolicy("Reset", OFF, ALL)
                .setPolicy("Enable", null, DEFAULT)
                .setPolicy("Reset", null, DEFAULT);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.")
                .checkResults("Enable", "Notification=Off ")
                .checkResults("Reset", "Notification=Off ");
    }

    @Test
    public void test_069() {
        groupUpdatePage
                .goToSetPolicies("audiocodes", "MP252", testName, "tabsSettings_tblTabs")
                .clickOn("VoIP settings");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setPolicy("Name", PASSIVE, null)
                .setPolicy("Enable", ACTIVE, null)
                .setPolicy("Reset", OFF, null)
                .setPolicy("SignalingProtocol", ACTIVE, null)
                .setPolicy("SignalingProtocol", DEFAULT, null)
                .setPolicy("DTMFMethod", null, ACS_ONLY)
                .setPolicy("Region", null, ALL)
                .setPolicy("DigitMapEnable", null, ALL)
                .setPolicy("DigitMapEnable", null, DEFAULT);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.Services.VoiceService.1.VoiceProfile.1.")
                .checkResults("Name", "Notification=Passive ")
                .checkResults("Enable", "Notification=Active ")
                .checkResults("Reset", "Notification=Off ")
                .checkResults("DTMFMethod", "Access=AcsOnly")
                .checkResults("Region", "Access=All")
                .assertAbsenceOfParameter("SignalingProtocol")
                .assertAbsenceOfParameter("DigitMapEnable");
    }

    @Test //Moved to end
    public void test_250() {
        groupUpdatePage
                .gotoFileDownload(testName)
                .selectFileType(2)
                .manualRadioButton()
                .fillUrl(BasePage.getProps().getProperty("ftp_config_file_url"))
                .fillUserName(BasePage.getProps().getProperty("ftp_user"))
                .fillpassword(BasePage.getProps().getProperty("ftp_password"))
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("Vendor Configuration File", BasePage.getProps().getProperty("ftp_config_file_url"));
    }

    @Test //Moved to end
    public void test_251() {
        groupUpdatePage
                .gotoFileDownload(testName)
                .selectFileType(1)
                .manualRadioButton()
                .fillUrl(BasePage.getProps().getProperty("ftp_image_file_url"))
                .fillUserName(BasePage.getProps().getProperty("ftp_user"))
                .fillpassword(BasePage.getProps().getProperty("ftp_password"))
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("Firmware Image", BasePage.getProps().getProperty("ftp_image_file_url"));
    }

    @Test //Moved to end
    public void test_252() {
        groupUpdatePage
                .gotoFileDownload(testName)
                .selectFileType(2)
                .fromListRadioButton()
                .selectFileName(1)
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("Vendor Configuration File", "http://95.217.85.220:82/uploads/fake_config.cfg");
    }

    @Test //Moved to end
    public void test_253() {
        groupUpdatePage
                .gotoFileDownload(testName)
                .selectFileType(1)
                .fromListRadioButton()
                .selectFileName(1)
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("Firmware Image", "http://95.217.85.220:82/uploads/fake_image.img");
    }

    @Test
    public void test_074() {
        groupUpdatePage
                .gotoFileUpload("audiocodes", "MP262", testName)
                .selectUploadFileType(1)
                .manuallyUrlRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .fillUploadUrl("http://95.217.85.220:82/uploads")
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("Vendor Configuration File", "http://95.217.85.220:82/uploads");
    }

    @Test
    public void test_075() {
        groupUpdatePage
                .gotoFileUpload("audiocodes", "MP262", testName)
                .selectUploadFileType(2)
                .manuallyUrlRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .fillUploadUrl("http://95.217.85.220:82/uploads")
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("Vendor Log File", "http://95.217.85.220:82/uploads");
    }

    @Test
    public void test_076() {
        groupUpdatePage
                .gotoFileUpload("audiocodes", "MP262", testName)
                .selectUploadFileType(1)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("Vendor Configuration File", "http://95.217.85.220:82/uploads");
    }

    @Test
    public void test_079() {
        groupUpdatePage
                .gotoFileUpload("audiocodes", "MP262", testName)
                .selectUploadFileType(2)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("Vendor Log File", "http://95.217.85.220:82/uploads");
    }

    @Test
    public void test_080() {
        groupUpdatePage
                .gotoFileUpload(testName)
                .selectUploadFileType(1)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test configuration file upload")
                .globalButtons(NEXT)
                .getTable("tblTasks")
                .checkResults("Vendor Configuration File", "http://95.217.85.220:82/uploads")
                .clickOn(1, 0);
        groupUpdatePage
                .deleteButton()
                .assertResultTableIsAbsent();
    }

    @Test //Moved to end
    public void test_254() {
        groupUpdatePage
                .gotoAction(testName)
                .rebootRadioButton()
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .assertPresenceOfParameter("Reboot");
    }

    @Test //Moved to end
    public void test_255() {
        groupUpdatePage
                .gotoAction(testName)
                .factoryResetRadioButton()
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .assertPresenceOfParameter("FactoryReset");
    }

    @Test
    public void test_083() {
        groupUpdatePage
                .gotoAction(testName)
                .reprovisionRadioButton()
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("CPEReprovision", "prov_attrib, custom_rpc, prov_object, profile, provision, file");
    }

    @Test
    public void test_084() {
        groupUpdatePage
                .gotoAction(testName)
                .customRpcRadioButton()
                .selectMethod(8)
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("CustomRPC", "GetRPCMethods");
    }

    @Test
    public void test_085() {
        groupUpdatePage
                .gotoAction(testName)
                .customRpcRadioButton()
                .selectMethod(6)
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("CustomRPC", "GetParameterNames");
    }

    @Test
    public void test_086() {
        groupUpdatePage
                .gotoAction(testName)
                .customRpcRadioButton()
                .selectMethod(5)
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("CustomRPC", "GetParameterAttributes");
    }

    @Test
    public void test_087() {
        groupUpdatePage
                .gotoAction(testName)
                .customRpcRadioButton()
                .selectMethod(7)
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("CustomRPC", "GetParameterValues");
    }

    @Test
    public void test_088() {
        groupUpdatePage
                .gotoAction(testName)
                .customRpcRadioButton()
                .selectMethod(11)
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("CustomRPC", "SetParameterValues");
    }

    @Test
    public void test_089() {
        groupUpdatePage
                .gotoAction(testName)
                .customRpcRadioButton()
                .selectMethod(10)
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("CustomRPC", "SetParameterAttributes");
    }

    @Test
    public void test_256() {
        groupUpdatePage
                .gotoAction(testName)
                .customRpcRadioButton()
                .selectMethod(9)
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("CustomRPC", "Reboot");
    }

    @Test
    public void test_257() {
        groupUpdatePage
                .gotoAction(testName)
                .customRpcRadioButton()
                .selectMethod(3)
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("CustomRPC", "Download");
    }

    @Test
    public void test_092() {
        groupUpdatePage
                .gotoAction(testName)
                .customRpcRadioButton()
                .selectMethod(1)
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("CustomRPC", "AddObject");
    }

    @Test
    public void test_093() {
        groupUpdatePage
                .gotoAction(testName)
                .customRpcRadioButton()
                .selectMethod(2)
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("CustomRPC", "DeleteObject");
    }

    @Test
    public void test_258() {
        groupUpdatePage
                .gotoAction(/*"Greenpacket", "Lte WiMAX Gateway",*/testName)
                .customRpcRadioButton()
                .selectMethod(12)
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("CustomRPC", "Upload");
    }

    @Test
    public void test_259() {
        groupUpdatePage
                .gotoAction(testName)
                .customRpcRadioButton()
                .selectMethod(4)
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("CustomRPC", "FactoryReset");
    }

    @Test
    public void test_096() {
        groupUpdatePage
                .goToSetParameters("sercomm", "Smart Box TURBO+", testName, "tblParamsValue", true)
                .setParameter("ConnectionRequestPassword", VALUE, "pass")
                .setParameter("ConnectionRequestUsername", VALUE, "user")
                .setParameter("EnableCWMP", VALUE, "true")
                .setParameter("ManageableDeviceNotificationLimit", VALUE, "10")
                .setParameter("Password", VALUE, "password")
                .setParameter("PeriodicInformEnable", TRUE, null)
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .setParameter("PeriodicInformTime", VALUE, "60")
                .setParameter("UpgradesManaged", TRUE, null)
                .setParameter("URL", VALUE, "127.0.0.1")
                .setParameter("Username", VALUE, "username")
                .setParameter("X_SC_ACSCA", VALUE, "some_param_1")
                .setParameter("X_SC_BindService", VALUE, "some_param_2")
                .setParameter("X_SC_BootStrap", VALUE, "some_param_3")
                .setParameter("X_SC_CONURLPrefix", VALUE, "some_param_4")
                .setParameter("X_SC_ConnectionRequestPort", VALUE, "some_param_5")
                .setParameter("X_SC_DLSCA", VALUE, "some_param_6")
                .setParameter("X_SC_DownloadTool", VALUE, "some_param_7")
                .setParameter("X_SC_EventStruct", VALUE, "some_param_8")
                .setParameter("X_SC_Option43Override", VALUE, "some_param_9")
                .setParameter("X_SC_ReservedPort", VALUE, "some_param_10")
                .setParameter("X_SC_ReservedPortRange", VALUE, "some_param_11")
                .setParameter("X_SC_RestoreDefault", VALUE, "some_param_12")
                .setParameter("X_SC_ScheduleKey", VALUE, "some_param_13")
                .setParameter("X_SC_ScheduleTime", VALUE, "some_param_14")
                .setParameter("X_SC_WanIfaces", VALUE, "some_param_15")
                .setParameter("X_SC_WanIpAddress", VALUE, "some_param_16");
        groupUpdatePage
                .getTable("tblTree")
                .clickOn(0, "LANDevice")
                .clickOn(0, "LANDevice.1")
                .clickOn(0, "WLANConfiguration")
                .clickOn(2, "WLANConfiguration.1");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("SSID", VALUE, "new_ssid_name");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.ManagementServer.")
                .checkResults("ConnectionRequestPassword", "pass")
                .checkResults("ConnectionRequestUsername", "user")
                .checkResults("EnableCWMP", "true")
                .checkResults("ManageableDeviceNotificationLimit", "10")
                .checkResults("Password", "password")
                .checkResults("PeriodicInformEnable", "1")
                .checkResults("PeriodicInformInterval", "60")
                .checkResults("PeriodicInformTime", "60")
                .checkResults("UpgradesManaged", "1")
                .checkResults("URL", "127.0.0.1")
                .checkResults("Username", "username")
                .checkResults("X_SC_ACSCA", "some_param_1")
                .checkResults("X_SC_BindService", "some_param_2")
                .checkResults("X_SC_BootStrap", "some_param_3")
                .checkResults("X_SC_CONURLPrefix", "some_param_4")
                .checkResults("X_SC_ConnectionRequestPort", "some_param_5")
                .checkResults("X_SC_DLSCA", "some_param_6")
                .checkResults("X_SC_DownloadTool", "some_param_7")
                .checkResults("X_SC_EventStruct", "some_param_8")
                .checkResults("X_SC_Option43Override", "some_param_9")
                .checkResults("X_SC_ReservedPort", "some_param_10")
                .checkResults("X_SC_ReservedPortRange", "some_param_11")
                .checkResults("X_SC_RestoreDefault", "some_param_12")
                .checkResults("X_SC_ScheduleKey", "some_param_13")
                .checkResults("X_SC_ScheduleTime", "some_param_14")
                .checkResults("X_SC_WanIfaces", "some_param_15")
                .checkResults("X_SC_WanIpAddress", "some_param_16")
                .setPrefix("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.")
                .checkResults("SSID", "new_ssid_name");
    }

    @Test
    public void test_097() {
        groupUpdatePage
                .goToSetParameters("sercomm", "Smart Box TURBO+", testName, "tblParamsValue", true)
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.ManagementServer.")
                .checkResults("PeriodicInformInterval", "60");
    }

    @Test
    public void test_098() {
        groupUpdatePage
                .goToSetParameters("sercomm", "Smart Box TURBO+", testName, "tblTree", true)
                .clickOn(0, "WANDevice")
                .clickOn(0, "WANDevice.1")
                .clickOn(0, "WANCommonInterfaceConfig");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("EnabledForInternet", VALUE, "true");
        groupUpdatePage
                .getTable("tblTree")
                .clickOn(0, "DeviceConfig")
                .clickOn(0, "LEDConfig");
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("IPSegments", VALUE, "192.168.1.0/24");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANCommonInterfaceConfig.")
                .checkResults("EnabledForInternet", "true")
                .setPrefix("InternetGatewayDevice.DeviceConfig.LEDConfig.")
                .checkResults("IPSegments", "192.168.1.0/24");
    }

    @Test
    public void test_099() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel("Smart Box TURBO+")
                .fillName(testName)
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .onlineDevicesCheckBox()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "59");
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "59");
        groupUpdatePage
                .getTable("tblPeriod")
                .checkResults("Online devices", "True");
    }

    @Test
    public void test_100() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .assertElementIsPresent("lblTitle1");
    }

    @Test
    public void test_101() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .globalButtons(CANCEL)
                .assertElementIsPresent("tblParameters");
    }

    @Test
    public void test_102() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Manufacturer", "Sercomm");
    }

    @Test
    public void test_103() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Model", "MP262");
    }

    @Test
    public void test_104() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Completed")
                .checkFiltering("State", "Not active")
                .checkFiltering("State", "Error")
                .checkFiltering("State", "All");
    }

    @Test
    public void test_128() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Manufacturer");
    }

    @Test
    public void test_129() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Model");
    }

    @Test
    public void test_130() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name");
    }

    @Test
    public void test_131() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name")
                .checkSorting("Created");
    }

    @Test
    public void test_132() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Creator");
    }

    @Test
    public void test_133() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Updated");
    }

    @Test
    public void test_134() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Activated");
    }

    @Test
    public void test_135() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .selectManufacturer("Sercomm")
                .checkResetView();
    }

    @Test
    public void test_136() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .getMainTable()
                .clickOn("Manufacturer");
        groupUpdatePage
                .checkResetView();
        groupUpdatePage
                .leftMenu(VIEW)
                .itemsOnPage("10")
                .pause(5000);
    }

    @Test
    public void test_139() {
        groupUpdatePage
                .gotoGetParameter(testName)
                .clickOn(1, 1, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.URL", "names");
    }

    @Test
    public void test_140() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("Information");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(1, 1, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.DeviceInfo.Manufacturer", "names");
    }

    @Test
    public void test_141() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("Time");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(1, 1, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.Time.NTPServer1", "names");
    }

    @Test
    public void test_142() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("WAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(1, 1, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.2.")
                .checkResults("Enable", "names");
    }

    @Test
    public void test_143() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("LAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(3, 1, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.")
                .checkResults("DHCPRelay", "names");
    }

    @Test
    public void test_144() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("Wireless");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(4, 1, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.")
                .checkResults("SSID", "names");
    }

    @Test
    public void test_145() {
        groupUpdatePage
                .gotoGetParameter(testName)
                .clickOn(1, 2, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.URL", "values");
    }

    @Test
    public void test_146() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("Information");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(1, 2, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.DeviceInfo.Manufacturer", "values");
    }

    @Test
    public void test_147() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("Time");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(1, 2, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.Time.NTPServer1", "values");
    }

    @Test
    public void test_148() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("WAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(1, 2, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.2.")
                .checkResults("Enable", "values");
    }

    @Test
    public void test_149() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("LAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(3, 2, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.")
                .checkResults("DHCPRelay", "values");
    }

    @Test
    public void test_150() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("Wireless");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(4, 2, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.")
                .checkResults("SSID", "values");
    }

    @Test
    public void test_151() {
        groupUpdatePage
                .gotoGetParameter(testName)
                .clickOn(1, 3, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.URL", "attributes");
    }

    @Test
    public void test_152() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("Information");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(1, 3, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.DeviceInfo.Manufacturer", "attributes");
    }

    @Test
    public void test_153() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("Time");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(1, 3, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.Time.NTPServer1", "attributes");
    }

    @Test
    public void test_154() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("WAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(1, 3, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.2.")
                .checkResults("Enable", "attributes");
    }

    @Test
    public void test_155() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("LAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(3, 3, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.")
                .checkResults("DHCPRelay", "attributes");
    }

    @Test
    public void test_156() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("Wireless");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(4, 3, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.")
                .checkResults("SSID", "attributes");
    }

    @Test
    public void test_157() {
        groupUpdatePage
                .gotoGetParameter(testName)
                .clickOn(1, 1, 0)
                .clickOn(1, 2, 0)
                .clickOn(1, 3, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.URL", "values,names,attributes");
    }

    @Test
    public void test_158() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("Information");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(1, 1, 0)
                .clickOn(1, 2, 0)
                .clickOn(1, 3, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.DeviceInfo.Manufacturer", "values,names,attributes");
    }

    @Test
    public void test_159() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("Time");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(1, 1, 0)
                .clickOn(1, 2, 0)
                .clickOn(1, 3, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.Time.NTPServer1", "values,names,attributes");
    }

    @Test
    public void test_160() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("WAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(1, 1, 0)
                .clickOn(1, 2, 0)
                .clickOn(1, 3, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.WANDevice.1.WANConnectionDevice.1.WANPPPConnection.2.")
                .checkResults("Enable", "values,names,attributes");
    }

    @Test
    public void test_161() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("LAN");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(3, 1, 0)
                .clickOn(3, 2, 0)
                .clickOn(3, 3, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.LANHostConfigManagement.")
                .checkResults("DHCPRelay", "values,names,attributes");
    }

    @Test
    public void test_162() {
        groupUpdatePage
                .gotoGetParameter(testName, "tabsSettings_tblTabs")
                .clickOn("Wireless");
        groupUpdatePage
                .getTable("tblParamsValue")
                .clickOn(4, 1, 0)
                .clickOn(4, 2, 0)
                .clickOn(4, 3, 0);
        groupUpdatePage
                .saveAndActivate(testName)
                .getTable("tblTasks")
                .setPrefix("InternetGatewayDevice.LANDevice.1.WLANConfiguration.1.")
                .checkResults("SSID", "values,names,attributes");
    }
}