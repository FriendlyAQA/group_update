package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.DataBaseConnector;
import com.friendly.aqa.utils.HttpConnector;
import com.friendly.aqa.utils.XmlWriter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.friendly.aqa.pageobject.BasePage.getManufacturer;
import static com.friendly.aqa.pageobject.BasePage.getModelName;
import static com.friendly.aqa.entities.GlobalButtons.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.*;
import static com.friendly.aqa.entities.ParameterType.VALUE;
import static com.friendly.aqa.entities.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Conditions.EQUAL;

@Listeners(UniversalVideoListener.class)
public class GroupUpdateTR181Tests extends BaseTestCase {

    @Test
    public void tr181_gu_001() {
        guPage
                .deleteAll()
                .topMenu(GROUP_UPDATE)
                .waitForUpdate()
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr181_gu_002() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr181_gu_003() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .deleteFilterGroups()
                .bottomMenu(CANCEL)
                .waitForUpdate()
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr181_gu_004() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .showList()
                .assertTrue(guPage.serialNumberTableIsPresent());
    }

    @Test
    public void tr181_gu_005() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .createGroupButton()
                .assertTrue(guPage.isButtonPresent(FINISH))
                .bottomMenu(CANCEL)
                .waitForUpdate()
                .pause(500)
                .assertEquals(guPage.getAttributeById("txtName", "value"), testName);
    }

    @Test
    public void tr181_gu_006() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("device_created")
                .selectCompare("IsNull")
                .bottomMenu(NEXT)
                .assertFalse(guPage.isButtonActive("btnDelFilter_btn"))
                .filterRecordsCheckbox()
                .assertTrue(guPage.isButtonActive("btnDelFilter_btn"))
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertEquals(testName, guPage.getSelectedOption("ddlSend"));
        setTargetTestName();
    }

    @Test
    public void tr181_gu_007() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("cust2")
                .selectCompare("Equal")
                .inputTextField("111")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertEquals(testName, guPage.getSelectedOption("ddlSend"), "Created group isn't selected!\n")
                .assertTrue(guPage.isElementDisplayed("lblNoSelectedCpes"), "Warning 'No devices selected' isn't displayed!\n");
    }

    @Test
    public void tr181_gu_008() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .createGroupButton()
                .fillName("tr181_gu_006")
                .bottomMenu(NEXT)
                .assertTrue(guPage.isElementDisplayed("lblNameInvalid"), "Warning 'This name is already in use' isn't displayed!\n");
    }

    @Test
    public void tr181_gu_009() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo(targetTestName)
                .editGroupButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertFalse(guPage.isOptionPresent("ddlSend", targetTestName), "Option '" + targetTestName + "' is still present on 'Send to' list!\n");
    }

    @Test
    public void tr181_gu_010() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo("Individual")
                .clickOnTable("tblDevices", 1, 0)
                .waitForUpdate()
                .assertButtonsAreEnabled(true, NEXT)
                .clickOnTable("tblDevices", 1, 0)
                .waitForUpdate()
                .assertButtonsAreEnabled(false, NEXT);
    }

    @Test
    //Doesn't work with Edge
    public void tr181_gu_011() {
        XmlWriter.createImportCpeFile();
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo("Import")
                .selectImportDevicesFile()
                .showList()
                .assertPresenceOfValue("tblDevices", 0, getSerial());
    }

    @Test
    public void tr181_gu_012() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("device_created")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .selectSendTo(testName)
                .showList()
                .assertPresenceOfValue("tblDevices", 0, getSerial());
    }

    @Test
    public void tr181_gu_013() {
        guPage
                .gotoSetParameters()
                .assertPresenceOfElements("tblParamsValue")
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void tr181_gu_014() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5);
        setTargetTestName();
    }

    @Test
    public void tr181_gu_015() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup(targetTestName)
                .bottomMenu(EDIT)
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .clickOnTable("tblTasks", "Device.ManagementServer.PeriodicInformInterval")
                .setParameter("PeriodicInformInterval, sec", VALUE, "61")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoGroup(targetTestName)
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_016() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate();
        setTargetTestName();
    }

    @Test
    public void tr181_gu_017() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup(targetTestName)
                .bottomMenu(EDIT)
                .assertFalse(guPage.isInputActive("ddlSend"))
                .bottomMenu(NEXT)
                .assertFalse(guPage.isInputActive("lrbImmediately"))
                .bottomMenu(NEXT)
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void tr181_gu_018() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .timeHoursSelect("0")
                .bottomMenu(NEXT)
                .assertEqualsAlertMessage("Update can't be scheduled to the past")
                .checkIsCalendarClickable();
    }

    @Test
    public void tr181_gu_019() throws IOException {
        guPage
                .topMenu(GROUP_UPDATE)
                .assertTrue(HttpConnector.sendGetRequest(guPage
                        .getGuExportLink("tr181_gu_016"))
                        .contains("\"Device.ManagementServer.PeriodicInformInterval\" value=\"60\""));
    }

    @Test
    public void tr181_gu_020() {
        guPage
                .gotoSetParameters()
                .setParameter("Username", VALUE, "ftacs")
                .setParameter("Password", VALUE, "ftacs")
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_021() {
        guPage
                .gotoSetParameters()
                .setParameter("Username", VALUE, "ftacs")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.Username", "ftacs");
    }

    @Test
    public void tr181_gu_022() {
        guPage
                .gotoSetParameters("Information")
                .setParameter(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_023() {
        guPage
                .gotoSetParameters("Time")
                .setAllParameters()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_024() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_025() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_026() {
        guPage
                .gotoSetParameters("WiFi")
                .setAllParameters()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_027() {
        guPage
                .gotoSetParameters("WiFi")
                .setParameter(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_028() {
        guPage
                .gotoSetParameters("WiFi")
                .setParameter(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_029() {
        guPage
                .gotoSetParameters("IP")
                .setAllParameters()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_030() {
        guPage
                .gotoSetParameters("IP")
                .setParameter(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_031() {
        guPage
                .gotoSetParameters("IP")
                .setParameter(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_032() {
        guPage
                .gotoSetParameters("Firewall")
                .setAllParameters()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_033() {
        guPage
                .gotoSetParameters("Firewall")
                .setParameter(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_034() {
        guPage
                .gotoSetParameters("Firewall")
                .setParameter(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_035() {
        guPage
                .gotoSetParameters("DHCPv4")
                .setAllParameters()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_036() {
        guPage
                .gotoSetParameters("DHCPv4")
                .setParameter(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_037() {
        guPage
                .gotoSetParameters("DHCPv4")
                .setParameter(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_038() {
        guPage
                .gotoSetParameters("DHCPv6")
                .setAllParameters()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_039() {
        guPage
                .gotoSetParameters("DHCPv6")
                .setParameter(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_040() {
        guPage
                .gotoSetParameters("DHCPv6")
                .setParameter(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_041() {
        guPage
                .gotoSetParameters("DNS")
                .setAllParameters()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_042() {
        guPage
                .gotoSetParameters("DNS")
                .setParameter(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_043() {
        guPage
                .gotoSetParameters("DNS")
                .setParameter(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_044() {
        guPage
                .gotoSetParameters("Users")
                .setAllParameters()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_045() {
        guPage
                .gotoSetParameters("Users")
                .setParameter(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_046() {
        guPage
                .gotoSetParameters("Users")
                .setParameter(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_047() {
        guPage
                .gotoSetParameters("Ethernet")
                .setAllParameters()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_048() {
        guPage
                .gotoSetParameters("Ethernet")
                .setParameter(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_049() {
        guPage
                .gotoSetParameters("Ethernet")
                .setParameter(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_050() {
        guPage
                .goToSetPolicies("Management")
                .setAllPolicies()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_051() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_052() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_053() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_054() {
        guPage
                .goToSetPolicies("Information")
                .setAllPolicies()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_055() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_056() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_057() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_058() {
        guPage
                .goToSetPolicies("Time")
                .setAllPolicies()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_059() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_060() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_061() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_062() {
        guPage
                .goToSetPolicies("WiFi")
                .setAllPolicies()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_063() {
        guPage
                .goToSetPolicies("WiFi")
                .setPolicy(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_064() {
        guPage
                .goToSetPolicies("WiFi")
                .setPolicy(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_065() {
        guPage
                .goToSetPolicies("WiFi")
                .setPolicy(3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_066() {
        guPage
                .goToSetPolicies("IP")
                .setAllPolicies()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_067() {
        guPage
                .goToSetPolicies("IP")
                .setPolicy(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_068() {
        guPage
                .goToSetPolicies("IP")
                .setPolicy(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_069() {
        guPage
                .goToSetPolicies("IP")
                .setPolicy(3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_070() {
        guPage
                .goToSetPolicies("Firewall")
                .setAllPolicies()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_071() {
        guPage
                .goToSetPolicies("Firewall")
                .setPolicy(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_072() {
        guPage
                .goToSetPolicies("Firewall")
                .setPolicy(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_073() {
        guPage
                .goToSetPolicies("Firewall")
                .setPolicy(3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_074() {
        guPage
                .goToSetPolicies("DHCPv4")
                .setAllPolicies()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_075() {
        guPage
                .goToSetPolicies("DHCPv4")
                .setPolicy(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_076() {
        guPage
                .goToSetPolicies("DHCPv4")
                .setPolicy(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_077() {
        guPage
                .goToSetPolicies("DHCPv4")
                .setPolicy(3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_078() {
        guPage
                .goToSetPolicies("DHCPv6")
                .setAllPolicies()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_079() {
        guPage
                .goToSetPolicies("DHCPv6")
                .setPolicy(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_080() {
        guPage
                .goToSetPolicies("DHCPv6")
                .setPolicy(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_081() {
        guPage
                .goToSetPolicies("DHCPv6")
                .setPolicy(3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_082() {
        guPage
                .goToSetPolicies("DNS")
                .setAllPolicies()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_083() {
        guPage
                .goToSetPolicies("DNS")
                .setPolicy(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_084() {
        guPage
                .goToSetPolicies("DNS")
                .setPolicy(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_085() {
        guPage
                .goToSetPolicies("DNS")
                .setPolicy(3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_086() {
        guPage
                .goToSetPolicies("Users")
                .setAllPolicies()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_087() {
        guPage
                .goToSetPolicies("Users")
                .setPolicy(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_088() {
        guPage
                .goToSetPolicies("Users")
                .setPolicy(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_089() {
        guPage
                .goToSetPolicies("Users")
                .setPolicy(3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_090() {
        guPage
                .goToSetPolicies("Ethernet")
                .setAllPolicies()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_091() {
        guPage
                .goToSetPolicies("Ethernet")
                .setPolicy(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_092() {
        guPage
                .goToSetPolicies("Ethernet")
                .setPolicy(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_093() {
        guPage
                .goToSetPolicies("Ethernet")
                .setPolicy(3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test   //bug: "Reprovision" RB is absent from Action list;
    public void tr181_gu_094() {
        guPage
                .gotoAction()
                .reprovisionRadioButton()
                .nextSaveAndActivate()
                .checkAddedTask("Device reprovision", "CPEReprovision");
    }

    @Test
    public void tr181_gu_095() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("GetRPCMethods")
                .nextSaveAndActivate()
                .checkAddedTask("Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr181_gu_096() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("GetParameterNames")
                .nextSaveAndActivate()
                .checkAddedTask("Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr181_gu_097() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("GetParameterAttributes")
                .nextSaveAndActivate()
                .checkAddedTask("Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr181_gu_098() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("GetParameterValues")
                .nextSaveAndActivate()
                .checkAddedTask("Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr181_gu_099() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("SetParameterValues")
                .nextSaveAndActivate()
                .checkAddedTask("Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr181_gu_100() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("SetParameterAttributes")
                .nextSaveAndActivate()
                .checkAddedTask("Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr181_gu_101() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("AddObject")
                .nextSaveAndActivate()
                .checkAddedTask("Custom RPC", "AddObject");
    }

    @Test
    public void tr181_gu_102() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("DeleteObject")
                .nextSaveAndActivate()
                .checkAddedTask("Custom RPC", "DeleteObject");
    }

    @Test
    public void tr181_gu_103() {
        guPage
                .gotoSetParameters("time", true)
                .setAllParameters()
                .setAnyAdvancedParameter()
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_104() {
        guPage
                .gotoSetParameters("time", true)
                .setParameter(1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_105() {
        guPage
                .gotoSetParameters("time", true)
                .setParameter(2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_106() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .onlineDevicesCheckBox()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("Time", 1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running", 5)
                .readTasksFromDb()
                .enterIntoGroup()
                .assertOnlineDevices()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_107() {
        XmlWriter.createImportGroupFile();
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .selectSendTo()
                .showList()
                .assertPresenceOfValue("tblDevices", 0, getSerial());
//                .assertElementIsPresent("lblTitle1");
    }

    @Test
    public void tr181_gu_108() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .bottomMenu(CANCEL)
                .assertPresenceOfElements("tblParameters");
    }

    @Test
    public void tr181_gu_109() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Manufacturer", getManufacturer());
    }

    @Test
    public void tr181_gu_110() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Model", getModelName());
    }

    @Test
    public void tr181_gu_111() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Completed")
                .checkFiltering("State", "Not active")
                .checkFiltering("State", "Error")
                .checkFiltering("State", "All");
    }

    @Test
    public void tr181_gu_112() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Manufacturer");
    }

    @Test
    public void tr181_gu_113() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Model");
    }

    @Test
    public void tr181_gu_114() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name");
    }

    @Test
    public void tr181_gu_115() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name")
                .checkSorting("Created");
    }

    @Test
    public void tr181_gu_116() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Creator");
    }

    @Test
    public void tr181_gu_117() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Updated");
    }

    @Test   //reported bug #9389
    public void tr181_gu_118() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Activated");
    }

    @Test
    public void tr181_gu_119() {
        guPage
                .topMenu(GROUP_UPDATE)
                .selectManufacturer()
                .checkResetView();
    }

    @Test
    public void tr181_gu_120() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("Manufacturer")
                .checkResetView()
                .leftMenu(VIEW)
                .itemsOnPage("10")
                .pause(5000);
    }

    @Test
    public void tr181_gu_121() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_122() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_123() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_124() {
        guPage
                .gotoGetParameter("WiFi")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_125() {
        guPage
                .gotoGetParameter("IP")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_126() {
        guPage
                .gotoGetParameter("Firewall")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_127() {
        guPage
                .gotoGetParameter("DHCPv4")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_128() {
        guPage
                .gotoGetParameter("DHCPv6")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_129() {
        guPage
                .gotoGetParameter("DNS")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_130() {
        guPage
                .gotoGetParameter("Users")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_131() {
        guPage
                .gotoGetParameter("Ethernet")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_132() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_133() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_134() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_135() {
        guPage
                .gotoGetParameter("WiFi")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_136() {
        guPage
                .gotoGetParameter("IP")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_137() {
        guPage
                .gotoGetParameter("Firewall")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_138() {
        guPage
                .gotoGetParameter("DHCPv4")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_139() {
        guPage
                .gotoGetParameter("DHCPv6")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_140() {
        guPage
                .gotoGetParameter("DNS")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_141() {
        guPage
                .gotoGetParameter("Users")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_142() {
        guPage
                .gotoGetParameter("Ethernet")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_143() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_144() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_145() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_146() {
        guPage
                .gotoGetParameter("WiFi")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_147() {
        guPage
                .gotoGetParameter("IP")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_148() {
        guPage
                .gotoGetParameter("Firewall")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_149() {
        guPage
                .gotoGetParameter("DHCPv4")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_150() {
        guPage
                .gotoGetParameter("DHCPv6")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_151() {
        guPage
                .gotoGetParameter("DNS")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_152() {
        guPage
                .gotoGetParameter("Users")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_153() {
        guPage
                .gotoGetParameter("Ethernet")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_154() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_155() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_156() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_157() {
        guPage
                .gotoGetParameter("WiFi")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_158() {
        guPage
                .gotoGetParameter("IP")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_159() {
        guPage
                .gotoGetParameter("Firewall")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_160() {
        guPage
                .gotoGetParameter("DHCPv4")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_161() {
        guPage
                .gotoGetParameter("DHCPv6")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_162() {
        guPage
                .gotoGetParameter("DNS")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test   //Bug: Parameter name isn't displayed into result table;
    public void tr181_gu_163() {
        guPage
                .gotoGetParameter("Users")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_164() {
        guPage
                .gotoGetParameter("Ethernet")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_165() {
        guPage
                .gotoBackup()
                .saveAndActivate()
                .assertPresenceOfValue("tblTasks", 0, "Backup");
    }

    @Test
    public void tr181_gu_166() {
        guPage
                .gotoBackup()
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .assertPresenceOfValue("tblTasks", 0, "Backup")
                .assertPresenceOfValue("tblTasks", 1, "Present");
    }

    @Test
    public void tr181_gu_167() {
        guPage
                .gotoBackup()
                .clickOnTable("tblTasks", 1, 0)
                .deleteButton()
                .assertResultTableIsAbsent();
    }

    @Test
    public void tr181_gu_168() {
        guPage
                .gotoRestore()
                .saveAndActivate()
                .assertPresenceOfValue("tblTasks", 0, "Restore");
    }

    @Test
    public void tr181_gu_169() {
        guPage
                .gotoRestore()
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .readTasksFromDb()  //No waiting for "Completed" status
                .enterIntoGroup()
                .assertPresenceOfValue("tblTasks", 0, "Restore")
                .assertPresenceOfValue("tblTasks", 1, "Present");
    }

    @Test
    public void tr181_gu_170() {
        guPage
                .gotoRestore()
                .clickOnTable("tblTasks", 1, 0)
                .deleteButton()
                .assertResultTableIsAbsent();
    }

    @Test //bug: Trace diagnostic is absent from Diagnostic list
    public void tr181_gu_171() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Trace diagnostic")
                .inputHost("8.8.8.8")
                .inputNumOfRepetitions("3")
                .nextSaveAndActivate()
                .checkAddedTask("Trace diagnostic", "8.8.8.8");
    }

    @Test //bug: NSLookUp diagnostics is absent from Diagnostic list
    public void tr181_gu_172() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("NSLookUp diagnostics")
                .inputDnsField("8.8.8.8")
                .inputHost("127.0.0.1")
                .nextSaveAndActivate()
                .checkAddedTask("NSLookupDiagnostics", "8.8.8.8");
    }

    @Test
    public void tr181_gu_173() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("IPPing diagnostics")
                .inputHost("8.8.8.8")
                .nextSaveAndActivate()
                .checkAddedTask("IPPing diagnostics", "8.8.8.8");
    }

    @Test
    public void tr181_gu_174() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Download diagnostics")
                .nextSaveAndActivate()
                .checkAddedTask("Download diagnostics", "http://127.0.0.1/webdav/Test.cfg");
    }

    @Test
    public void tr181_gu_175() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Download diagnostics")
                .addToMonitoringCheckBox()
                .nextSaveAndActivate()
                .checkAddedTask("Download diagnostics", "http://127.0.0.1/webdav/Test.cfg");
    }

    @Test
    public void tr181_gu_176() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Upload diagnostics")
                .nextSaveAndActivate()
                .checkAddedTask("Upload diagnostics", "http://127.0.0.1/webdav/");
    }

    @Test
    public void tr181_gu_177() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Upload diagnostics")
                .addToMonitoringCheckBox()
                .nextSaveAndActivate()
                .checkAddedTask("Upload diagnostics", "http://127.0.0.1/webdav/");
    }

    @Test //bug: Wi-Fi Neighboring diagnostic is absent from Diagnostic list
    public void tr181_gu_178() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Wi-Fi Neighboring diagnostic")
                .nextSaveAndActivate()
                .assertPresenceOfValue("tblTasks", -2, "Wi-Fi Neighboring diagnostic");
    }

    @Test //bug: Wi-Fi Neighboring diagnostic is absent from Diagnostic list
    public void tr181_gu_179() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Wi-Fi Neighboring diagnostic")
                .addToMonitoringCheckBox()
                .nextSaveAndActivate()
                .assertPresenceOfValue("tblTasks", -2, "Wi-Fi Neighboring diagnostic");
    }

    @Test
    public void tr181_gu_180() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatusWithoutRefresh("Completed", 65)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_181() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Vendor Configuration File")
                .manualRadioButton()
                .fillUrl(BasePage.getProps().getProperty("ftp_config_file_url"))
                .fillUserName(BasePage.getProps().getProperty("ftp_user"))
                .fillPassword(BasePage.getProps().getProperty("ftp_password"))
                .nextSaveAndActivate()
                .checkAddedTask("Vendor Configuration File", BasePage.getProps().getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr181_gu_182() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Firmware Image")
                .manualRadioButton()
                .fillUrl(BasePage.getProps().getProperty("ftp_image_file_url"))
                .fillUserName(BasePage.getProps().getProperty("ftp_user"))
                .fillPassword(BasePage.getProps().getProperty("ftp_password"))
                .nextSaveAndActivate()
                .checkAddedTask("Firmware Image", BasePage.getProps().getProperty("ftp_image_file_url"));
    }

    @Test
    public void tr181_gu_183() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Vendor Configuration File")
                .fromListRadioButton()
                .selectFileName(1)
                .nextSaveAndActivate()
                .assertPresenceOfValue("tblTasks", 2, "Vendor Configuration File");
    }

    @Test
    public void tr181_gu_184() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Firmware Image")
                .fromListRadioButton()
                .selectFileName(1)
                .nextSaveAndActivate()
                .assertPresenceOfValue("tblTasks", 2, "Firmware Image");
    }

    @Test
    public void tr181_gu_185() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType(1)
                .manuallyUrlRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .fillUploadUrl()
                .nextSaveAndActivate()
                .checkAddedTask("Vendor Configuration File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr181_gu_186() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType(2)
                .manuallyUrlRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .fillUploadUrl()
                .nextSaveAndActivate()
                .checkAddedTask("Vendor Log File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr181_gu_187() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType(1)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .nextSaveAndActivate()
                .checkAddedTask("Vendor Configuration File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr181_gu_188() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Upload file")
                .addTaskButton()
                .selectUploadFileType(1)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .nextSaveAndActivate()
                .checkAddedTask("Vendor Configuration File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr181_gu_189() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Upload file")
                .addTaskButton()
                .selectUploadFileType(2)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .nextSaveAndActivate()
                .checkAddedTask("Vendor Log File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr181_gu_190() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType(2)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .nextSaveAndActivate()
                .checkAddedTask("Vendor Log File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr181_gu_191() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType(1)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test configuration file upload")
                .bottomMenu(NEXT)
                .checkAddedTask("Vendor Configuration File", BasePage.getProps().getProperty("upload_url"))
                .clickOnTable("tblTasks", 1, 0)
                .deleteButton()
                .assertResultTableIsAbsent();
    }

    @Test
    public void tr181_gu_192() {
        guPage
                .gotoAction()
                .rebootRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void tr181_gu_193() {
        guPage
                .gotoAction()
                .rebootRadioButton()
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void tr181_gu_194() {
        guPage
                .gotoAction()
                .factoryResetRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Factory reset");
    }

    @Test
    public void tr181_gu_195() {
        guPage
                .gotoAction()
                .factoryResetRadioButton()
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .assertPresenceOfParameter("Factory reset");
    }

    @Test
    public void tr181_gu_196() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("Reboot")
                .nextSaveAndActivate()
                .checkAddedTask("Custom RPC", "Reboot");
    }

    @Test
    public void tr181_gu_197() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("Reboot")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .checkAddedTask("Custom RPC", "Reboot");
    }

    @Test
    public void tr181_gu_198() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("Download")
                .nextSaveAndActivate()
                .checkAddedTask("Custom RPC", "Download");
    }

    @Test
    public void tr181_gu_199() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("Download")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .checkAddedTask("Custom RPC", "Download");
    }

    @Test   //needs running device
    public void tr181_gu_200() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("Upload")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectItem()
                .bottomMenu(PAUSE)
                .okButtonPopUp()
                .waitForStatus("Paused", 5)
                .readTasksFromDb()
                .pause(1000)
                .enterIntoGroup()
                .checkAddedTask("Custom RPC", "Upload");
    }

    @Test
    public void tr181_gu_201() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("Upload")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .checkAddedTask("Custom RPC", "Upload");
    }

    @Test
    public void tr181_gu_202() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .customRpcRadioButton()
                .selectMethod("FactoryReset")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Reactivation", 30)
                .enterIntoGroup()
                .checkAddedTask("Custom RPC", "FactoryReset");
    }

    @Test
    public void tr181_gu_203() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("FactoryReset")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .checkAddedTask("Custom RPC", "FactoryReset");
    }

    @Test
    public void tr181_gu_204() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Scheduled");
    }

    @Test
    public void tr181_gu_205() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Running");
    }

    @Test
    public void tr181_gu_206() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Paused");
    }

    @Test
    public void tr181_gu_207() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Reactivation");
    }

    @Test
    public void tr181_gu_208() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("device_created")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertTrue(guPage.isElementDisplayed("lblNoSelectedCpes"), "Cannot find label'No devices selected'!\n");
    }

    @Test
    public void tr181_gu_209() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("device_created")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_210() {
        DataBaseConnector.createFilterPreconditions(BasePage.getSerial());
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("On Day")
                .clickOn("calFilterDate_image")
                .selectDate(CalendarUtil.getShiftedDate(-10))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_211() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Prior to")
                .clickOn("calFilterDate_image")
                .selectDate(CalendarUtil.getShiftedDate(-9))
                .inputText("txtTimeHour", CalendarUtil.getHours())
                .inputText("txtTimeMinute", CalendarUtil.getMinutes())
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_212() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Later than")
                .clickOn("calFilterDate_image")
                .selectDate(CalendarUtil.getShiftedDate(-9))
                .inputText("txtTimeHour", CalendarUtil.getHours())
                .inputText("txtTimeMinute", CalendarUtil.getMinutes())
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Later than'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_213() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Today")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Today'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_214() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Before Today")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Before Today'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_215() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Yesterday")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Yesterday'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_216() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Prev 7 days")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Prev 7 days'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_217() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Prev X days")
                .inputText("txtInt", "9")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Prev X days'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_218() {
        guPage
                .presetFilter("mycust03", testName)
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("=")
                .inputText("txtText", testName)
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - equals'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_219() {
        guPage
                .presetFilter("mycust03", testName)
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("!=")
                .inputText("txtText", testName)
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - not equals'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_220() {
        guPage
//                .presetFilter("mycust03", testName)
                .createDeviceGroup()
                .selectColumnFilter("Hardware version")
                .selectCompare("Starts with")
                .inputText("txtText", testName)
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertTrue(guPage.isElementDisplayed("lblNoSelectedCpes"), "Cannot find label'No devices selected'!\n");
    }

    @Test
    public void tr181_gu_221() {
        guPage
                .presetFilter("mycust03", testName)
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Like")
                .inputText("txtText", testName.substring(1, 5))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Like'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_222() {
        guPage
                .presetFilter("mycust03", testName)
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("No like")
                .inputText("txtText", testName.substring(1, 5))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - No like'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_223() {
        guPage
                .presetFilter("mycust03", testName)
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Is null'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_224() {
        guPage
                .presetFilter("mycust03", testName)
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Is not null'!\n")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_225() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Is not null")
                .bottomMenu(CANCEL)
                .waitForUpdate()
                .assertTrue(guPage.isElementDisplayed("lblHead"), "Filter creation didn't cancel properly!\n");
    }

    @Test
    public void tr181_gu_226() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .createGroupButton()
                .fillName()
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .bottomMenu(CANCEL)
                .waitForUpdate()
                .assertFalse(guPage.isOptionPresent("ddlSend", testName), "Option '" + testName + "' is present on 'Send to' list!\n");
    }

    @Test
    public void tr181_gu_227() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_228() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_229() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_230() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_231() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setPeriod(2)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_232() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setPeriod(2)
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_233() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .onlineDevicesCheckBox()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_234() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_235() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_236() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_237() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_238() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_239() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .setPeriod(2)
                .onlineDevicesCheckBox()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_240() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .setPeriod(2)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_241() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setThreshold(50)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_242() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setThreshold(50)
                .waitUntilConnectRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_243() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .selectShiftedDate("calDate", 1)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_244() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_245() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .selectShiftedDate("calReactivationStartsOnDay", 2)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_246() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_247() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "1")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_248() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 8)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_249() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Hourly")
                .selectRepeatEveryHourDropDown("1")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_250() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_251() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .selectShiftedDate("calReactivationStartsOnDay", 2)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_252() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_253() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_254() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 8)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_255() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Daily")
                .selectRepeatEveryDayDropDown("1")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_256() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Weekly")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_257() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .selectRepeatsDropDown("Weekly")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_258() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .selectRepeatsDropDown("Weekly")
                .endAOnRadiobutton()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_259() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Weekly")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_260() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Weekly")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 32)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_261() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Weekly")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_262() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_263() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .selectShiftedDate("calReactivationStartsOnDay", 31)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_264() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_265() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_266() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 31)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_267() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Monthly")
                .selectRepeatEveryMonthDropDown("1")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_268() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_269() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .selectShiftedDate("calReactivationStartsOnDay", 2)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_270() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .endAfterRadiobutton()
                .inputText("txtReactivationEndsOccurrences", "2")
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_271() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .endAOnRadiobutton()
                .selectShiftedDate("calReactivationEndsOnDay", 365)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_272() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .selectRepeatsDropDown("Yearly")
                .runOnFailed()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr181_gu_273() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .setParameter("Username", VALUE, "ftacs")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device.ManagementServer.PeriodicInformInterval", "60")
                .checkAddedTask("Device.ManagementServer.Username", "ftacs");
    }

    @Test
    public void tr181_gu_274() {
        guPage.setScheduledParameters("Information");
    }

    @Test
    public void tr181_gu_275() {
        guPage.setScheduledParameters("WiFi");
    }

    @Test
    public void tr181_gu_276() {
        guPage.setScheduledParameters("IP");
    }

    @Test
    public void tr181_gu_277() {
        guPage.setScheduledParameters("Firewall");
    }

    @Test
    public void tr181_gu_278() {
        guPage.setScheduledParameters("DHCPv4");
    }

    @Test
    public void tr181_gu_279() {
        guPage.setScheduledParameters("DHCPv6");
    }

    @Test
    public void tr181_gu_280() {
        guPage.setScheduledParameters("DNS");
    }

    @Test
    public void tr181_gu_281() {
        guPage.setScheduledParameters("Users");
    }

    @Test
    public void tr181_gu_282() {
        guPage.setScheduledParameters("Ethernet");
    }

    @Test
    public void tr181_gu_283() {
        guPage.setScheduledPolicy("Management");
    }

    @Test
    public void tr181_gu_284() {
        guPage.setScheduledPolicy("Information");
    }

    @Test
    public void tr181_gu_285() {
        guPage.setScheduledPolicy("Time");
    }

    @Test
    public void tr181_gu_286() {
        guPage.setScheduledPolicy("WiFi");
    }

    @Test
    public void tr181_gu_287() {
        guPage.setScheduledPolicy("IP");
    }

    @Test
    public void tr181_gu_288() {
        guPage.setScheduledPolicy("Firewall");
    }

    @Test
    public void tr181_gu_289() {
        guPage.setScheduledPolicy("DHCPv4");
    }

    @Test
    public void tr181_gu_290() {
        guPage.setScheduledPolicy("DHCPv6");
    }

    @Test
    public void tr181_gu_291() {
        guPage.setScheduledPolicy("DNS");
    }

    @Test
    public void tr181_gu_292() {
        guPage.setScheduledPolicy("Users");
    }

    @Test
    public void tr181_gu_293() {
        guPage.setScheduledPolicy("Ethernet");
    }

    @Test   //bug: "Reprovision" RB is absent from Action list;
    public void tr181_gu_294() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .reprovisionRadioButton()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Device reprovision", "CPEReprovision");
    }

    @Test
    public void tr181_gu_295() {
        guPage.scheduledCallCustomRPC("GetRPCMethods");
    }

    @Test
    public void tr181_gu_296() {
        guPage.scheduledCallCustomRPC("GetParameterNames");
    }

    @Test
    public void tr181_gu_297() {
        guPage.scheduledCallCustomRPC("GetParameterAttributes");
    }

    @Test
    public void tr181_gu_298() {
        guPage.scheduledCallCustomRPC("GetParameterValues");
    }

    @Test
    public void tr181_gu_299() {
        guPage.scheduledCallCustomRPC("SetParameterValues");
    }

    @Test
    public void tr181_gu_300() {
        guPage.scheduledCallCustomRPC("SetParameterAttributes");
    }

    @Test
    public void tr181_gu_301() {
        guPage.scheduledCallCustomRPC("AddObject");
    }

    @Test
    public void tr181_gu_302() {
        guPage.scheduledCallCustomRPC("DeleteObject");
    }

    @Test
    public void tr181_gu_303() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .bottomMenu(ADVANCED_VIEW)
                .setAdvancedParameter("Device.Time", 2)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTasks();
    }

    @Test
    public void tr181_gu_304() {
        guPage.getScheduledParameter("Management", 1);
    }

    @Test
    public void tr181_gu_305() {
        guPage.getScheduledParameter("Information", 1);
    }

    @Test
    public void tr181_gu_306() {
        guPage.getScheduledParameter("Time", 1);
    }

    @Test
    public void tr181_gu_307() {
        guPage.getScheduledParameter("WiFi", 1);
    }

    @Test
    public void tr181_gu_308() {
        guPage.getScheduledParameter("IP", 1);
    }

    @Test
    public void tr181_gu_309() {
        guPage.getScheduledParameter("Firewall", 1);
    }

    @Test
    public void tr181_gu_310() {
        guPage.getScheduledParameter("DHCPv4", 1);
    }

    @Test
    public void tr181_gu_311() {
        guPage.getScheduledParameter("DHCPv6", 1);
    }

    @Test
    public void tr181_gu_312() {
        guPage.getScheduledParameter("DNS", 1);
    }

    @Test
    public void tr181_gu_313() {
        guPage.getScheduledParameter("Users", 1);
    }

    @Test
    public void tr181_gu_314() {
        guPage.getScheduledParameter("Ethernet", 1);
    }

    @Test
    public void tr181_gu_315() {
        guPage.getScheduledParameter("Management", 2);
    }

    @Test
    public void tr181_gu_316() {
        guPage.getScheduledParameter("Information", 2);
    }

    @Test
    public void tr181_gu_317() {
        guPage.getScheduledParameter("Time", 2);
    }

    @Test
    public void tr181_gu_318() {
        guPage.getScheduledParameter("WiFi", 2);
    }

    @Test
    public void tr181_gu_319() {
        guPage.getScheduledParameter("IP", 2);
    }

    @Test
    public void tr181_gu_320() {
        guPage.getScheduledParameter("Firewall", 2);
    }

    @Test
    public void tr181_gu_321() {
        guPage.getScheduledParameter("DHCPv4", 2);
    }

    @Test
    public void tr181_gu_322() {
        guPage.getScheduledParameter("DHCPv6", 2);
    }

    @Test
    public void tr181_gu_323() {
        guPage.getScheduledParameter("DNS", 2);
    }

    @Test
    public void tr181_gu_324() {
        guPage.getScheduledParameter("Users", 2);
    }

    @Test
    public void tr181_gu_325() {
        guPage.getScheduledParameter("Ethernet", 2);
    }

    @Test
    public void tr181_gu_326() {
        guPage.getScheduledParameter("Management", 3);
    }

    @Test
    public void tr181_gu_327() {
        guPage.getScheduledParameter("Information", 3);
    }

    @Test
    public void tr181_gu_328() {
        guPage.getScheduledParameter("Time", 3);
    }

    @Test
    public void tr181_gu_329() {
        guPage.getScheduledParameter("WiFi", 3);
    }

    @Test
    public void tr181_gu_330() {
        guPage.getScheduledParameter("IP", 3);
    }

    @Test
    public void tr181_gu_331() {
        guPage.getScheduledParameter("Firewall", 3);
    }

    @Test
    public void tr181_gu_332() {
        guPage.getScheduledParameter("DHCPv4", 3);
    }

    @Test
    public void tr181_gu_333() {
        guPage.getScheduledParameter("DHCPv6", 3);
    }

    @Test
    public void tr181_gu_334() {
        guPage.getScheduledParameter("DNS", 3);
    }

    @Test
    public void tr181_gu_335() {
        guPage.getScheduledParameter("Users", 3);
    }

    @Test
    public void tr181_gu_336() {
        guPage.getScheduledParameter("Ethernet", 3);
    }

    @Test
    public void tr181_gu_337() {
        guPage.getScheduledParameter("Management", 0);
    }

    @Test
    public void tr181_gu_338() {
        guPage.getScheduledParameter("Information", 0);
    }

    @Test
    public void tr181_gu_339() {
        guPage.getScheduledParameter("Time", 0);
    }

    @Test
    public void tr181_gu_340() {
        guPage.getScheduledParameter("WiFi", 0);
    }

    @Test
    public void tr181_gu_341() {
        guPage.getScheduledParameter("IP", 0);
    }

    @Test
    public void tr181_gu_342() {
        guPage.getScheduledParameter("Firewall", 0);
    }

    @Test
    public void tr181_gu_343() {
        guPage.getScheduledParameter("DHCPv4", 0);
    }

    @Test
    public void tr181_gu_344() {
        guPage.getScheduledParameter("DHCPv6", 0);
    }

    @Test
    public void tr181_gu_345() {
        guPage.getScheduledParameter("DNS", 0);
    }

    @Test   //Bug: Parameter name isn't displayed into result table;
    public void tr181_gu_346() {
        guPage.getScheduledParameter("Users", 0);
    }

    @Test
    public void tr181_gu_347() {
        guPage.getScheduledParameter("Ethernet", 0);
    }

    @Test
    public void tr181_gu_348() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Backup")
                .addTaskButton()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfValue("tblTasks", 0, "Backup");
    }

    @Test
    public void tr181_gu_349() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Restore")
                .addTaskButton()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfValue("tblTasks", 0, "Restore");
    }

    @Test
    public void tr181_gu_350() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Diagnostics")
                .addTaskButton()
                .selectDiagnostic("IPPing diagnostics")
                .inputHost("8.8.8.8")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfValue("tblTasks", -2, "IPPing diagnostics");

    }

    @Test
    public void tr181_gu_351() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Download file")
                .addTaskButton()
                .selectDownloadFileType("Vendor Configuration File")
                .manualRadioButton()
                .fillUrl(BasePage.getProps().getProperty("ftp_config_file_url"))
                .fillUserName(BasePage.getProps().getProperty("ftp_user"))
                .fillPassword(BasePage.getProps().getProperty("ftp_password"))
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Vendor Configuration File", BasePage.getProps().getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr181_gu_352() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Download file")
                .addTaskButton()
                .selectDownloadFileType("Firmware Image")
                .manualRadioButton()
                .fillUrl(BasePage.getProps().getProperty("ftp_image_file_url"))
                .fillUserName(BasePage.getProps().getProperty("ftp_user"))
                .fillPassword(BasePage.getProps().getProperty("ftp_password"))
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Firmware Image", BasePage.getProps().getProperty("ftp_image_file_url"));
    }

    @Test
    public void tr181_gu_353() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Download file")
                .addTaskButton()
                .selectDownloadFileType("Vendor Configuration File")
                .fromListRadioButton()
                .selectFileName(1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfValue("tblTasks", 2, "Vendor Configuration File");
    }

    @Test
    public void tr181_gu_354() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Download file")
                .addTaskButton()
                .selectDownloadFileType("Firmware Image")
                .fromListRadioButton()
                .selectFileName(1)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfValue("tblTasks", -2, "Firmware Image");
    }

    @Test
    public void tr181_gu_355() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Upload file")
                .addTaskButton()
                .selectUploadFileType(1)
                .manuallyUrlRadioButton()
                .fillUploadUrl()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Vendor Configuration File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr181_gu_356() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Upload file")
                .addTaskButton()
                .selectUploadFileType(2)
                .manuallyUrlRadioButton()
                .fillUploadUrl()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Vendor Log File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr181_gu_357() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Upload file")
                .addTaskButton()
                .selectUploadFileType(1)
                .defaultUploadRadioButton()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Vendor Configuration File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr181_gu_358() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .rebootRadioButton()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void tr181_gu_359() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .factoryResetRadioButton()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Factory reset");
    }

    @Test
    public void tr181_gu_360() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .customRpcRadioButton()
                .selectMethod("Reboot")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Custom RPC", "Reboot");
    }

    @Test
    public void tr181_gu_361() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .customRpcRadioButton()
                .selectMethod("Download")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Custom RPC", "Download");
    }

    @Test
    public void tr181_gu_362() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .customRpcRadioButton()
                .selectMethod("Upload")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Custom RPC", "Upload");
    }

    @Test
    public void tr181_gu_363() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .customRpcRadioButton()
                .selectMethod("FactoryReset")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .checkAddedTask("Custom RPC", "FactoryReset");
    }

}
