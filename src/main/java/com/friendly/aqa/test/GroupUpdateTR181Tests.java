package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.DataBaseConnector;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.ParameterType.VALUE;
import static com.friendly.aqa.entities.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Conditions.EQUAL;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.*;

@Listeners(UniversalVideoListener.class)
public class GroupUpdateTR181Tests extends BaseTestCase {

    @Test
    public void tr181_gu_001() {
        guPage
                .deleteAll()
                .topMenu(GROUP_UPDATE)
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
                .assertDevicesArePresent();
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
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .bottomMenu(CANCEL)
                .validateName();
    }

    @Test
    public void tr181_gu_006() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("device_created")
                .selectCompare("IsNull")
                .bottomMenu(NEXT)
                .assertButtonIsEnabled(false, "btnDelFilter_btn")
                .filterRecordsCheckbox()
                .assertButtonIsEnabled(true, "btnDelFilter_btn")
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .validateSelectedGroup();
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
                .validateSelectedGroup()
                .assertElementsArePresent("lblNoSelectedCpes");
    }

    @Test
    public void tr181_gu_008() {//depends on 006
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .createGroupButton()
                .fillName("tr181_gu_006")
                .bottomMenu(NEXT)
                .assertElementsArePresent("lblNameInvalid");
    }

    @Test
    public void tr181_gu_009() {//depends on 006
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo("tr181_gu_006")
                .editGroupButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "tr181_gu_006");
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
                .assertButtonsAreEnabled(true, NEXT)
                .clickOnTable("tblDevices", 1, 0)
                .assertButtonsAreEnabled(false, NEXT);
    }

    @Test
    //Doesn't work with Edge
    public void tr181_gu_011() {
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
                .selectSendTo(getTestName())
                .showList()
                .assertPresenceOfValue("tblDevices", 0, getSerial());
    }

    @Test
    public void tr181_gu_013() {
        guPage
                .gotoSetParameters()
                .assertElementsArePresent("tblParamsValue")
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
    }

    @Test
    public void tr181_gu_015() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("tr181_gu_014")
                .bottomMenu(EDIT)
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .clickOnTable("tblTasks", "Device.ManagementServer.PeriodicInformInterval")
                .setParameter("PeriodicInformInterval, sec", VALUE, "61")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoGroup("tr181_gu_014")
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_016() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate();
    }

    @Test
    public void tr181_gu_017() {//depends on 16
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("tr181_gu_016")
                .bottomMenu(EDIT)
                .assertInputIsDisabled("ddlSend")
                .bottomMenu(NEXT)
                .assertInputIsDisabled("lrbImmediately")
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
                .scheduledTo()
                .timeHoursSelect("0")
                .bottomMenu(NEXT)
                .assertEqualsAlertMessage("Update can't be scheduled to the past")
                .checkIsCalendarClickable();
    }

    @Test
    public void tr181_gu_019() {//depends on 16
        guPage
                .topMenu(GROUP_UPDATE)
                .checkExportLink();
    }

    @Test
    public void tr181_gu_020() {
        guPage
                .gotoSetParameters()
                .setParameter("Username", VALUE, "ftacs")
                .setParameter("Password", VALUE, "ftacs")
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_021() {
        guPage
                .gotoSetParameters()
                .setParameter("Username", VALUE, "ftacs")
                .nextSaveAndActivate()
                .validateAddedTask("Device.ManagementServer.Username", "ftacs");
    }

    @Test
    public void tr181_gu_022() {
        guPage
                .gotoSetParameters("Information")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_023() {
        guPage
                .gotoSetParameters("Time")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_024() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_025() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_026() {
        guPage
                .gotoSetParameters("WiFi")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_027() {
        guPage
                .gotoSetParameters("WiFi")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_028() {
        guPage
                .gotoSetParameters("WiFi")
                .setParameter(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_029() {
        guPage
                .gotoSetParameters("IP")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_030() {
        guPage
                .gotoSetParameters("IP")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_031() {
        guPage
                .gotoSetParameters("IP")
                .setParameter(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_032() {
        guPage
                .gotoSetParameters("Firewall")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_033() {
        guPage
                .gotoSetParameters("Firewall")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_034() {
        guPage
                .gotoSetParameters("Firewall")
                .setParameter(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_035() {
        guPage
                .gotoSetParameters("DHCPv4")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_036() {
        guPage
                .gotoSetParameters("DHCPv4")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_037() {
        guPage
                .gotoSetParameters("DHCPv4")
                .setParameter(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_038() {
        guPage
                .gotoSetParameters("DHCPv6")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_039() {
        guPage
                .gotoSetParameters("DHCPv6")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_040() {
        guPage
                .gotoSetParameters("DHCPv6")
                .setParameter(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_041() {
        guPage
                .gotoSetParameters("DNS")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_042() {
        guPage
                .gotoSetParameters("DNS")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_043() {
        guPage
                .gotoSetParameters("DNS")
                .setParameter(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_044() {
        guPage
                .gotoSetParameters("Users")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_045() {
        guPage
                .gotoSetParameters("Users")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_046() {
        guPage
                .gotoSetParameters("Users")
                .setParameter(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_047() {
        guPage
                .gotoSetParameters("Ethernet")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_048() {
        guPage
                .gotoSetParameters("Ethernet")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_049() {
        guPage
                .gotoSetParameters("Ethernet")
                .setParameter(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_050() {
        guPage
                .goToSetPolicies("Management")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_051() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_052() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_053() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_054() {
        guPage
                .goToSetPolicies("Information")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_055() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_056() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_057() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_058() {
        guPage
                .goToSetPolicies("Time")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_059() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_060() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_061() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_062() {
        guPage
                .goToSetPolicies("WiFi")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_063() {
        guPage
                .goToSetPolicies("WiFi")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_064() {
        guPage
                .goToSetPolicies("WiFi")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_065() {
        guPage
                .goToSetPolicies("WiFi")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_066() {
        guPage
                .goToSetPolicies("IP")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_067() {
        guPage
                .goToSetPolicies("IP")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_068() {
        guPage
                .goToSetPolicies("IP")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_069() {
        guPage
                .goToSetPolicies("IP")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_070() {
        guPage
                .goToSetPolicies("Firewall")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_071() {
        guPage
                .goToSetPolicies("Firewall")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_072() {
        guPage
                .goToSetPolicies("Firewall")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_073() {
        guPage
                .goToSetPolicies("Firewall")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_074() {
        guPage
                .goToSetPolicies("DHCPv4")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_075() {
        guPage
                .goToSetPolicies("DHCPv4")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_076() {
        guPage
                .goToSetPolicies("DHCPv4")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_077() {
        guPage
                .goToSetPolicies("DHCPv4")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_078() {
        guPage
                .goToSetPolicies("DHCPv6")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_079() {
        guPage
                .goToSetPolicies("DHCPv6")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_080() {
        guPage
                .goToSetPolicies("DHCPv6")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_081() {
        guPage
                .goToSetPolicies("DHCPv6")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_082() {
        guPage
                .goToSetPolicies("DNS")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_083() {
        guPage
                .goToSetPolicies("DNS")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_084() {
        guPage
                .goToSetPolicies("DNS")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_085() {
        guPage
                .goToSetPolicies("DNS")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_086() {
        guPage
                .goToSetPolicies("Users")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_087() {
        guPage
                .goToSetPolicies("Users")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_088() {
        guPage
                .goToSetPolicies("Users")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_089() {
        guPage
                .goToSetPolicies("Users")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_090() {
        guPage
                .goToSetPolicies("Ethernet")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_091() {
        guPage
                .goToSetPolicies("Ethernet")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_092() {
        guPage
                .goToSetPolicies("Ethernet")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_093() {
        guPage
                .goToSetPolicies("Ethernet")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_094() {
        guPage
                .gotoAction()
                .selectAction("Device reprovision")
                .nextSaveAndActivate()
                .validateAddedTask("Device reprovision", "CPEReprovision");
    }

    @Test
    public void tr181_gu_095() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetRPCMethods")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr181_gu_096() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetParameterNames")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr181_gu_097() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetParameterAttributes")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr181_gu_098() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetParameterValues")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr181_gu_099() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("SetParameterValues")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr181_gu_100() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("SetParameterAttributes")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr181_gu_101() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("AddObject")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "AddObject");
    }

    @Test
    public void tr181_gu_102() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("DeleteObject")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "DeleteObject");
    }

    @Test
    public void tr181_gu_103() {
        guPage
                .gotoSetParameters("time", true)
                .setAllParameters()
                .setAnyAdvancedParameter()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_104() {
        guPage
                .gotoSetParameters("time", true)
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_105() {
        guPage
                .gotoSetParameters("time", true)
                .setParameter(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
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
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_107() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .bottomMenu(SAVE_IMPORT)
                .selectSendTo()
                .showList()
                .assertPresenceOfValue("tblDevices", 0, getSerial())
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp();
    }

    @Test
    public void tr181_gu_108() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .bottomMenu(CANCEL)
                .assertElementsArePresent("tblParameters");
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
                .validateSorting("Manufacturer");
    }

    @Test
    public void tr181_gu_113() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Model");
    }

    @Test
    public void tr181_gu_114() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Name");
    }

    @Test
    public void tr181_gu_115() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Name")
                .validateSorting("Created");
    }

    @Test
    public void tr181_gu_116() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Creator");
    }

    @Test
    public void tr181_gu_117() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Updated");
    }

    @Test
    public void tr181_gu_118() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Activated");
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
                .itemsOnPage("10");
    }

    @Test
    public void tr181_gu_121() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_122() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_123() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_124() {
        guPage
                .gotoGetParameter("WiFi")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_125() {
        guPage
                .gotoGetParameter("IP")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_126() {
        guPage
                .gotoGetParameter("Firewall")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_127() {
        guPage
                .gotoGetParameter("DHCPv4")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_128() {
        guPage
                .gotoGetParameter("DHCPv6")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_129() {
        guPage
                .gotoGetParameter("DNS")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_130() {
        guPage
                .gotoGetParameter("Users")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_131() {
        guPage
                .gotoGetParameter("Ethernet")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_132() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_133() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_134() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_135() {
        guPage
                .gotoGetParameter("WiFi")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_136() {
        guPage
                .gotoGetParameter("IP")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_137() {
        guPage
                .gotoGetParameter("Firewall")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_138() {
        guPage
                .gotoGetParameter("DHCPv4")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_139() {
        guPage
                .gotoGetParameter("DHCPv6")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_140() {
        guPage
                .gotoGetParameter("DNS")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_141() {
        guPage
                .gotoGetParameter("Users")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_142() {
        guPage
                .gotoGetParameter("Ethernet")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_143() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_144() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_145() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_146() {
        guPage
                .gotoGetParameter("WiFi")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_147() {
        guPage
                .gotoGetParameter("IP")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_148() {
        guPage
                .gotoGetParameter("Firewall")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_149() {
        guPage
                .gotoGetParameter("DHCPv4")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_150() {
        guPage
                .gotoGetParameter("DHCPv6")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_151() {
        guPage
                .gotoGetParameter("DNS")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_152() {
        guPage
                .gotoGetParameter("Users")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_153() {
        guPage
                .gotoGetParameter("Ethernet")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_154() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_155() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_156() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_157() {
        guPage
                .gotoGetParameter("WiFi")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_158() {
        guPage
                .gotoGetParameter("IP")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_159() {
        guPage
                .gotoGetParameter("Firewall")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_160() {
        guPage
                .gotoGetParameter("DHCPv4")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_161() {
        guPage
                .gotoGetParameter("DHCPv6")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_162() {
        guPage
                .gotoGetParameter("DNS")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test   //Bug: Parameter name isn't displayed into result table;
    public void tr181_gu_163() {
        guPage
                .gotoGetParameter("Users")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_164() {
        guPage
                .gotoGetParameter("Ethernet")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_165() {
        guPage
                .gotoBackup()
                .saveAndActivate()
                .assertPresenceOfValue("tblTasks", 0, "Backup");
        //add deleting backup for Reports
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
                .validateAddedTask("Trace diagnostic", "8.8.8.8");
    }

    @Test //bug: NSLookUp diagnostics is absent from Diagnostic list
    public void tr181_gu_172() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("NSLookUp diagnostics")
                .inputDnsField("8.8.8.8")
                .inputHost("127.0.0.1")
                .nextSaveAndActivate()
                .validateAddedTask("NSLookupDiagnostics", "8.8.8.8");
    }

    @Test
    public void tr181_gu_173() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("IPPing diagnostics")
                .inputHost("8.8.8.8")
                .nextSaveAndActivate()
                .validateAddedTask("IPPing diagnostics", "8.8.8.8");
    }

    @Test
    public void tr181_gu_174() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Download diagnostics")
                .nextSaveAndActivate()
                .validateAddedTask("Download diagnostics", "http://127.0.0.1/webdav/Test.cfg");
    }

    @Test
    public void tr181_gu_175() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Download diagnostics")
//                .addToMonitoringCheckBox()
                .nextSaveAndActivate()
                .validateAddedTask("Download diagnostics", "http://127.0.0.1/webdav/Test.cfg");
    }

    @Test
    public void tr181_gu_176() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Upload diagnostics")
                .nextSaveAndActivate()
                .validateAddedTask("Upload diagnostics", "http://127.0.0.1/webdav/");
    }

    @Test
    public void tr181_gu_177() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Upload diagnostics")
//                .addToMonitoringCheckBox()
                .nextSaveAndActivate()
                .validateAddedTask("Upload diagnostics", "http://127.0.0.1/webdav/");
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
//                .addToMonitoringCheckBox()
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
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_181() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Vendor Configuration File")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .fillUsername()
                .fillPassword()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_182() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Firmware Image")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl(/*BasePage.getProps().getProperty("ftp_image_file_url")*/)
//                .fillUserName(BasePage.getProps().getProperty("ftp_user"))
//                .fillPassword(BasePage.getProps().getProperty("ftp_password"))
                .nextSaveAndActivate()
                .validateDownloadFileTasks();
    }

    @Test
    public void tr181_gu_183() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Vendor Configuration File")
                .selectFromListRadioButton()
                .selectFileName("Vendor Configuration File")
                .nextSaveAndActivate()
                .validateDownloadFileTasks();
    }

    @Test
    public void tr181_gu_184() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Firmware Image")
                .selectFromListRadioButton()
                .selectFileName("Firmware Image")
                .nextSaveAndActivate()
                .validateDownloadFileTasks();
    }

    @Test
    public void tr181_gu_185() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUploadRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .fillUploadUrl()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_186() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Log File")
                .manuallyUploadRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .fillUploadUrl()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_187() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .nextSaveAndActivate()
                .validateAddedTasks();
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
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .nextSaveAndActivate()
                .validateAddedTasks();
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
                .selectUploadFileType("Vendor Log File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_190() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Log File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_191() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test configuration file upload")
                .bottomMenu(NEXT)
                .validateAddedTasks()
                .clickOnTable("tblTasks", 1, 0)
                .deleteButton()
                .assertResultTableIsAbsent();
    }

    @Test
    public void tr181_gu_192() {
        guPage
                .gotoAction()
                .selectAction("Reboot")
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void tr181_gu_193() {
        guPage
                .gotoAction()
                .selectAction("Reboot")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void tr181_gu_194() {
        guPage
                .gotoAction()
                .selectAction("Factory reset")
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Factory reset");
    }

    @Test
    public void tr181_gu_195() {
        guPage
                .gotoAction()
                .selectAction("Factory reset")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .assertPresenceOfParameter("Factory reset");
    }

    @Test
    public void tr181_gu_196() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "Reboot");
    }

    @Test
    public void tr181_gu_197() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .validateAddedTask("Custom RPC", "Reboot");
    }

    @Test
    public void tr181_gu_198() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Download")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "Download");
    }

    @Test
    public void tr181_gu_199() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Download")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .validateAddedTask("Custom RPC", "Download");
    }

    @Test   //needs running device
    public void tr181_gu_200() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
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
                .validateAddedTask("Custom RPC", "Upload");
    }

    @Test
    public void tr181_gu_201() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Upload")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .validateAddedTask("Custom RPC", "Upload");
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
                .selectAction("Custom RPC")
                .selectMethod("FactoryReset")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Reactivation", 30)
                .enterIntoGroup()
                .validateAddedTask("Custom RPC", "FactoryReset");
    }

    @Test
    public void tr181_gu_203() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("FactoryReset")
                .bottomMenu(NEXT)
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .validateAddedTask("Custom RPC", "FactoryReset");
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
                .assertElementsArePresent("lblNoSelectedCpes");
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
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
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
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
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
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
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
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
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
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
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
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
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
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
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
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
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
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_218() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("=")
                .inputText("txtText", getTestName())
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_219() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("!=")
                .inputText("txtText", getTestName())
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_220() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("Hardware version")
                .selectCompare("Starts with")
                .inputText("txtText", getTestName())
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsArePresent("lblNoSelectedCpes");
    }

    @Test
    public void tr181_gu_221() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Like")
                .inputText("txtText", getTestName().substring(1, 5))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_222() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("No like")
                .inputText("txtText", getTestName().substring(1, 5))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_223() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_224() {
        guPage
                .presetFilter("mycust03", getTestName())
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Is not null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .assertElementsAreAbsent("lblNoSelectedCpes")
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr181_gu_225() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Is not null")
                .bottomMenu(CANCEL)
                .assertElementsArePresent("lblHead");
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
                .assertAbsenceOfOptions("ddlSend", getTestName());
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .validateAddedTasks();
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
                .validateAddedTasks();
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
                .validateAddedTasks();
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .validateAddedTasks();
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .validateAddedTask("Device.ManagementServer.PeriodicInformInterval", "60")
                .validateAddedTask("Device.ManagementServer.Username", "ftacs");
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

    @Test
    public void tr181_gu_294() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Device reprovision")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTask("Device reprovision", "CPEReprovision");
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
                .scheduledTo()
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
                .validateAddedTasks();
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
                .scheduledTo()
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
                .scheduledTo()
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
                .scheduledTo()
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
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Download file")
                .addTaskButton()
                .selectDownloadFileType("Vendor Configuration File")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .fillUsername()
                .fillPassword()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
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
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Download file")
                .addTaskButton()
                .selectDownloadFileType("Firmware Image")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .fillUsername()
                .fillPassword()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
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
                .scheduledTo()
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
                .scheduledTo()
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
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Upload file")
                .addTaskButton()
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUploadRadioButton()
                .fillUploadUrl()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
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
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Upload file")
                .addTaskButton()
                .selectUploadFileType("Vendor Log File")
                .manuallyUploadRadioButton()
                .fillUploadUrl()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
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
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Upload file")
                .addTaskButton()
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
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
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Reboot")
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
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Factory reset")
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
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTask("Custom RPC", "Reboot");
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
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Custom RPC")
                .selectMethod("Download")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTask("Custom RPC", "Download");
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
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Custom RPC")
                .selectMethod("Upload")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTask("Custom RPC", "Upload");
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
                .scheduledTo()
                .setDelay(10)
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Custom RPC")
                .selectMethod("FactoryReset")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTask("Custom RPC", "FactoryReset");
    }

    @Test   //precondition actions for Reports tab tests (107-112)
    public void tr181_gu_364() {
        guPage
                .goToSetPolicies("Ethernet")
                .setPolicy(2)
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .addNewTask("Download file")
                .addTaskButton()
                .selectDownloadFileType("Vendor Configuration File")
                .selectFromListRadioButton()
                .selectFileName("Vendor Configuration File")
                .bottomMenu(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .bottomMenu(NEXT)
                .addNewTask("Upload file")
                .addTaskButton()
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .bottomMenu(NEXT)
                .addNewTask("Get parameter")
                .addTaskButton()
                .getParameter(1, 2)
                .bottomMenu(NEXT)
                .addNewTask("Backup")
                .addTaskButton()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .bottomMenu(EDIT)
                .bottomMenu(NEXT)
                .bottomMenu(NEXT)
                .selectItemToDelete()
                .deleteButton()
                .bottomMenu(SAVE)
                .okButtonPopUp();
    }

}
