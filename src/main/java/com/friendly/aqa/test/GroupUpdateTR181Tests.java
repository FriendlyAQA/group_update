package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.DataBaseConnector;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.ParameterType.VALUE;
import static com.friendly.aqa.entities.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Conditions.EQUAL;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.IMPORT;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.NEW;

@Listeners(UniversalVideoListener.class)
public class GroupUpdateTR181Tests extends BaseTestCase {

    @Test
    public void tr181_gu_000() {
        guPage
                .deleteAll()
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .deleteFilterGroups();
    }

    @Test
    public void tr181_gu_001() {
        guPage
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
                .addModelButton()
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
                .addModelButton()
                .createGroupButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .bottomMenu(CANCEL)
                .pause(1000)
                .validateName();
    }

    @Test
    public void tr181_gu_006() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
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
                .assertNoDeviceAlertIsPresent()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, SAVE, SAVE_AND_ACTIVATE);
    }

    @Test   //depends on 006
    public void tr181_gu_008() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .createGroupButton()
                .fillGroupName("tr181_gu_006")
                .bottomMenu(NEXT)
                .assertElementsArePresent("lblNameInvalid");
    }

    @Test   //depends on 006
    public void tr181_gu_009() {//depends on 006
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
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
                .addModelButton()
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Device reprovision")
                .saveButton(true)
                .selectSendTo("Individual")
                .selectButton()
                .selectDevice(2)
                .closePopup()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .validateDevicesAmountMessage()
                .okButtonPopUp()
                .enterIntoGroup()
                .validateDetails()
                .bottomMenu(EDIT)
                .showListButton()
                .validateDevicesAmount();
    }

    @Test
    public void tr181_gu_011() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .selectSendTo("Import")
                .selectImportDevicesFile()
                .showList()
                .assertDeviceIsPresent();
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
                .assertDeviceIsPresent();
    }

    @Test
    public void tr181_gu_013() {
        guPage
                .gotoSetParameters()
                .assertElementsArePresent(false, "tblParamsValue")
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void tr181_gu_014() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5);
    }

    @Test   //depends on 014
    public void tr181_gu_015() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("tr181_gu_014")
                .bottomMenu(EDIT)
                .clickOnTask("Device.ManagementServer.PeriodicInformInterval")
                .setParameter("PeriodicInformInterval, sec", VALUE, "61")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoGroup("tr181_gu_014")
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void tr181_gu_016() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate();
    }

    @Test   //depends on 016
    public void tr181_gu_017() {//depends on 16
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("tr181_gu_016")
                .bottomMenu(EDIT)
                .assertInputIsDisabled("ddlSend")
                .bottomMenu(NEXT)
                .assertInputIsDisabled("lrbImmediately")
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void tr181_gu_018() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .timeHoursSelect("0")
                .assertSummaryTextEquals("Update can't be scheduled to the past")
                .checkIsCalendarClickable();
    }

    @Test   //depends on 16
    public void tr181_gu_019() {
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
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_021() {
        guPage
                .gotoSetParameters()
                .setParameter("Username", VALUE, "ftacs")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_022() {
        guPage
                .gotoSetParameters("Information")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_023() {
        guPage
                .gotoSetParameters("Time")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_024() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_025() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_026() {
        guPage
                .gotoSetParameters("WiFi")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_027() {
        guPage
                .gotoSetParameters("WiFi")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_028() {
        guPage
                .gotoSetParameters("WiFi")
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_029() {
        guPage
                .gotoSetParameters("IP")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_030() {
        guPage
                .gotoSetParameters("IP")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_031() {
        guPage
                .gotoSetParameters("IP")
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_032() {
        guPage
                .gotoSetParameters("Firewall")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_033() {
        guPage
                .gotoSetParameters("Firewall")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_034() {
        guPage
                .gotoSetParameters("Firewall")
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_035() {
        guPage
                .gotoSetParameters("DHCPv4")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_036() {
        guPage
                .gotoSetParameters("DHCPv4")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_037() {
        guPage
                .gotoSetParameters("DHCPv4")
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_038() {
        guPage
                .gotoSetParameters("DHCPv6")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_039() {
        guPage
                .gotoSetParameters("DHCPv6")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_040() {
        guPage
                .gotoSetParameters("DHCPv6")
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_041() {
        guPage
                .gotoSetParameters("DNS")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_042() {
        guPage
                .gotoSetParameters("DNS")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_043() {
        guPage
                .gotoSetParameters("DNS")
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_044() {
        guPage
                .gotoSetParameters("Users")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_045() {
        guPage
                .gotoSetParameters("Users")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_046() {
        guPage
                .gotoSetParameters("Users")
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_047() {
        guPage
                .gotoSetParameters("Ethernet")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_048() {
        guPage
                .gotoSetParameters("Ethernet")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_049() {
        guPage
                .gotoSetParameters("Ethernet")
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_050() {
        guPage
                .goToSetPolicies("Management")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_051() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_052() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_053() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_054() {
        guPage
                .goToSetPolicies("Information")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_055() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_056() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_057() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_058() {
        guPage
                .goToSetPolicies("Time")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_059() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_060() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_061() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_062() {
        guPage
                .goToSetPolicies("WiFi")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_063() {
        guPage
                .goToSetPolicies("WiFi")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_064() {
        guPage
                .goToSetPolicies("WiFi")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_065() {
        guPage
                .goToSetPolicies("WiFi")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_066() {
        guPage
                .goToSetPolicies("IP")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_067() {
        guPage
                .goToSetPolicies("IP")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_068() {
        guPage
                .goToSetPolicies("IP")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_069() {
        guPage
                .goToSetPolicies("IP")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_070() {
        guPage
                .goToSetPolicies("Firewall")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_071() {
        guPage
                .goToSetPolicies("Firewall")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_072() {
        guPage
                .goToSetPolicies("Firewall")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_073() {
        guPage
                .goToSetPolicies("Firewall")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_074() {
        guPage
                .goToSetPolicies("DHCPv4")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_075() {
        guPage
                .goToSetPolicies("DHCPv4")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_076() {
        guPage
                .goToSetPolicies("DHCPv4")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_077() {
        guPage
                .goToSetPolicies("DHCPv4")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_078() {
        guPage
                .goToSetPolicies("DHCPv6")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_079() {
        guPage
                .goToSetPolicies("DHCPv6")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_080() {
        guPage
                .goToSetPolicies("DHCPv6")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_081() {
        guPage
                .goToSetPolicies("DHCPv6")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_082() {
        guPage
                .goToSetPolicies("DNS")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_083() {
        guPage
                .goToSetPolicies("DNS")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_084() {
        guPage
                .goToSetPolicies("DNS")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_085() {
        guPage
                .goToSetPolicies("DNS")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_086() {
        guPage
                .goToSetPolicies("Users")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_087() {
        guPage
                .goToSetPolicies("Users")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_088() {
        guPage
                .goToSetPolicies("Users")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_089() {
        guPage
                .goToSetPolicies("Users")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_090() {
        guPage
                .goToSetPolicies("Ethernet")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_091() {
        guPage
                .goToSetPolicies("Ethernet")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_092() {
        guPage
                .goToSetPolicies("Ethernet")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_093() {
        guPage
                .goToSetPolicies("Ethernet")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_094() {
        guPage
                .gotoAction()
                .selectAction("Device reprovision")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_095() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetRPCMethods")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_096() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetParameterNames")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_097() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetParameterAttributes")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_098() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetParameterValues")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_099() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("SetParameterValues")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_100() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("SetParameterAttributes")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_101() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("AddObject")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_102() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("DeleteObject")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_103() {
        guPage
                .gotoSetParameters("time", true)
                .setAllParameters()
                .setAnyAdvancedParameter()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_104() {
        guPage
                .gotoSetParameters("time", true)
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_105() {
        guPage
                .gotoSetParameters("time", true)
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_106() {
        guPage
                .gotoSetParameters()
                .setParameter("Time", 1)
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .onlineDevicesCheckBox()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running", 5)
                .readTasksFromDb()
                .enterIntoGroup()
                .validateDetails()
                .assertOnlineDevices()
                .bottomMenu(EDIT)
                .validateTask();
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
                .assertDeviceIsPresent()
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
                .checkFiltering("Manufacturer");
    }

    @Test
    public void tr181_gu_110() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Model");
    }

    @Test
    public void tr181_gu_111() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Completed")
                .checkFilteringByState("Not active")
                .checkFilteringByState("Error")
                .checkFilteringByState("All");
    }

    @Test   //irrelevant ("Manufacturer" column is absent)
    public void tr181_gu_112() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Manufacturer");
    }

    @Test   //irrelevant ("Model" column is absent)
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
                .clickOnHeader("Name")
                .checkResetView();
    }

    @Test
    public void tr181_gu_121() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_122() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_123() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_124() {
        guPage
                .gotoGetParameter("WiFi")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_125() {
        guPage
                .gotoGetParameter("IP")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_126() {
        guPage
                .gotoGetParameter("Firewall")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_127() {
        guPage
                .gotoGetParameter("DHCPv4")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_128() {
        guPage
                .gotoGetParameter("DHCPv6")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_129() {
        guPage
                .gotoGetParameter("DNS")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_130() {
        guPage
                .gotoGetParameter("Users")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_131() {
        guPage
                .gotoGetParameter("Ethernet")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_132() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_133() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_134() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_135() {
        guPage
                .gotoGetParameter("WiFi")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_136() {
        guPage
                .gotoGetParameter("IP")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_137() {
        guPage
                .gotoGetParameter("Firewall")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_138() {
        guPage
                .gotoGetParameter("DHCPv4")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_139() {
        guPage
                .gotoGetParameter("DHCPv6")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_140() {
        guPage
                .gotoGetParameter("DNS")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_141() {
        guPage
                .gotoGetParameter("Users")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_142() {
        guPage
                .gotoGetParameter("Ethernet")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_143() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_144() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_145() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_146() {
        guPage
                .gotoGetParameter("WiFi")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_147() {
        guPage
                .gotoGetParameter("IP")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_148() {
        guPage
                .gotoGetParameter("Firewall")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_149() {
        guPage
                .gotoGetParameter("DHCPv4")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_150() {
        guPage
                .gotoGetParameter("DHCPv6")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_151() {
        guPage
                .gotoGetParameter("DNS")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_152() {
        guPage
                .gotoGetParameter("Users")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_153() {
        guPage
                .gotoGetParameter("Ethernet")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_154() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_155() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_156() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_157() {
        guPage
                .gotoGetParameter("WiFi")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_158() {
        guPage
                .gotoGetParameter("IP")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_159() {
        guPage
                .gotoGetParameter("Firewall")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_160() {
        guPage
                .gotoGetParameter("DHCPv4")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_161() {
        guPage
                .gotoGetParameter("DHCPv6")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_162() {
        guPage
                .gotoGetParameter("DNS")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test   //Bug: Parameter name isn't displayed into result table;
    public void tr181_gu_163() {
        guPage
                .gotoGetParameter("Users")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_164() {
        guPage
                .gotoGetParameter("Ethernet")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_165() {
        guPage
                .gotoBackup()
                .immediatelyActivateAndValidate();
        //add deleting backup for Reports
    }

    @Test
    public void tr181_gu_166() {
        guPage
                .gotoBackup()
                .addCondition("Device.ManagementServer", "PeriodicInformInterval, sec", EQUAL, "10")
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate(false)
                .bottomMenu(EDIT)
                .validateTask()
                .assertConditionIsPresent();
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
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_169() {
        guPage
                .gotoRestore()
                .addCondition("Device.Time", "NTPServer1", EQUAL, "www.nist.gov")
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate(false)
                .bottomMenu(EDIT)
                .validateTask()
                .assertConditionIsPresent();
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
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test //bug: NSLookUp diagnostics is absent from Diagnostic list
    public void tr181_gu_172() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("NSLookUp diagnostics")
                .inputDnsField("8.8.8.8")
                .inputHost("127.0.0.1")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_173() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("IPPing diagnostics")
                .inputHost("8.8.8.8")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_174() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Download diagnostics")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_175() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Download diagnostics")
//                .addToMonitoringCheckBox()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_176() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Upload diagnostics")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_177() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Upload diagnostics")
//                .addToMonitoringCheckBox()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test //bug: Wi-Fi Neighboring diagnostic is absent from Diagnostic list
    public void tr181_gu_178() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Wi-Fi Neighboring diagnostic")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test //bug: Wi-Fi Neighboring diagnostic is absent from Diagnostic list
    public void tr181_gu_179() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Wi-Fi Neighboring diagnostic")
//                .addToMonitoringCheckBox()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_180() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(1)
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatusWithoutRefresh("Completed", 65)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
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
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_182() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Firmware Image")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_183() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Vendor Configuration File")
                .selectFromListRadioButton()
                .selectFileName("Vendor Configuration File")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_184() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Firmware Image")
                .selectFromListRadioButton()
                .selectFileName("Firmware Image")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_185() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUploadRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .fillUploadUrl()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_186() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Log File")
                .manuallyUploadRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .fillUploadUrl()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_187() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test//Probably step 'waitUntilConnect' skipped in STD
    public void tr181_gu_188() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .waitUntilConnect()
                .saveAndActivate()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void tr181_gu_189() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Log File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .waitUntilConnect()
                .saveAndActivate()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void tr181_gu_190() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Log File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_191() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test configuration file upload")
                .saveButton()
                .validateTask()
                .clickOnTable("tblTasks", 1, 0)
                .deleteButton()
                .assertResultTableIsAbsent();
    }

    @Test
    public void tr181_gu_192() {
        guPage
                .gotoAction()
                .selectAction("Reboot")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_193() {
        guPage
                .gotoAction()
                .selectAction("Reboot")
                .saveButton()
                .addCondition("Device.Time", "NTPServer1", EQUAL, "www.nist.gov")
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate(false)
                .bottomMenu(EDIT)
                .validateTask()
                .assertConditionIsPresent();
    }

    @Test
    public void tr181_gu_194() {
        guPage
                .gotoAction()
                .selectAction("Factory reset")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_195() {
        guPage
                .gotoAction()
                .selectAction("Factory reset")
                .saveButton()
                .addCondition("Device.Time", "NTPServer1", EQUAL, "www.nist.gov")
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate(false)
                .bottomMenu(EDIT)
                .validateTask()
                .assertConditionIsPresent();
    }

    @Test
    public void tr181_gu_196() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_197() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .saveButton()
                .addCondition("Device.Time", "NTPServer1", EQUAL, "www.nist.gov")
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate(false)
                .bottomMenu(EDIT)
                .validateTask()
                .assertConditionIsPresent();
    }

    @Test
    public void tr181_gu_198() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Download")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_199() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Download")
                .saveButton()
                .addCondition("Device.Time", "NTPServer1", EQUAL, "www.nist.gov")
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate(false)
                .bottomMenu(EDIT)
                .validateTask()
                .assertConditionIsPresent();
    }

    @Test
    public void tr181_gu_200() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Upload")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .selectItem()
                .bottomMenu(PAUSE)
                .okButtonPopUp()
                .waitForStatus("Paused", 5)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void tr181_gu_201() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Upload")
                .saveButton()
                .addCondition("Device.Time", "NTPServer1", EQUAL, "www.nist.gov")
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate(false)
                .bottomMenu(EDIT)
                .validateTask()
                .assertConditionIsPresent();
    }

    @Test
    public void tr181_gu_202() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("FactoryReset")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .repeats("Hourly")
                .repeatEveryHours("1")
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Reactivation", 30)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void tr181_gu_203() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("FactoryReset")
                .saveButton()
                .addCondition("Device.Time", "NTPServer1", EQUAL, "www.nist.gov")
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate(false)
                .bottomMenu(EDIT)
                .validateTask()
                .assertConditionIsPresent();
    }

    @Test
    public void tr181_gu_204() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Scheduled");
    }

    @Test
    public void tr181_gu_205() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Running");
    }

    @Test
    public void tr181_gu_206() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Paused");
    }

    @Test
    public void tr181_gu_207() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Reactivation");
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
                .assertNoDeviceAlertIsPresent();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_210() {
        DataBaseConnector.createFilterPreconditions(getSerial());
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("On Day")
                .clickOn("calFilterDate_image")
                .selectDate(CalendarUtil.getShiftedDate(-10))
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .assertNoDeviceAlertIsPresent();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr181_gu_223() {
        guPage
                .presetFilter("mycust03", "")
                .createDeviceGroup()
                .selectColumnFilter("mycust03")
                .selectCompare("Is null")
                .bottomMenu(NEXT)
                .bottomMenu(FINISH)
                .okButtonPopUp()
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .immediatelyActivateAndValidate();
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
                .addModelButton()
                .createGroupButton()
                .fillGroupName()
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .bottomMenu(CANCEL)
                .assertAbsenceOfOptions("ddlSend", getTestName());
    }

    @Test
    public void tr181_gu_227() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_228() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .waitUntilConnect()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_229() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_230() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .waitUntilConnect()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_231() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .setPeriod(2)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_232() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .setPeriod(2)
                .waitUntilConnect()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_233() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .onlineDevicesCheckBox()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_234() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .onlineDevicesCheckBox()
                .waitUntilConnect()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_235() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_236() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .waitUntilConnect()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_237() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void tr181_gu_238() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .waitUntilConnect()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void tr181_gu_239() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .setPeriod(2)
                .onlineDevicesCheckBox()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void tr181_gu_240() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .setPeriod(1)
                .setPeriod(2)
                .onlineDevicesCheckBox()
                .waitUntilConnect()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void tr181_gu_241() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .setThreshold(50)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_242() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .setPeriod(1)
                .setThreshold(50)
                .waitUntilConnect()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_243() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .scheduleInDays(1)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_244() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Hourly")
                .repeatEveryHours("1")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_245() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Hourly")
                .repeatEveryHours("1")
                .startsOnDayDelay(2)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_246() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Hourly")
                .repeatEveryHours("2")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_247() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Hourly")
                .repeatEveryHours("1")
                .endsAfter()
                .numberOfReactivations("1")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_248() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Hourly")
                .repeatEveryHours("1")
                .reactivationEndsOn()
                .endsOnDayDelay(7)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_249() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Hourly")
                .repeatEveryHours("1")
                .reRunOnFailed()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_250() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Daily")
                .repeatEveryDays("1")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_251() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Daily")
                .repeatEveryDays("1")
                .startsOnDayDelay(2)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_252() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Daily")
                .repeatEveryDays("2")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_253() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Daily")
                .repeatEveryDays("1")
                .endsAfter()
                .endsAfter("2")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_254() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Daily")
                .repeatEveryDays("1")
                .reactivationEndsOn()
                .endsOnDayDelay(7)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_255() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Daily")
                .repeatEveryDays("1")
                .reRunOnFailed()
                .saveAndValidateScheduledTasks();
    }

    @Test   //bug: Test fails if run in Friday :)))
    public void tr181_gu_256() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Weekly")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_257() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .repeats("Weekly")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void tr181_gu_258() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .repeats("Weekly")
                .reactivationEndsOn()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void tr181_gu_259() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Weekly")
                .endsAfter()
                .numberOfReactivations("2")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_260() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Weekly")
                .reactivationEndsOn()
                .endsOnDayDelay(32)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_261() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Weekly")
                .reRunOnFailed()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_262() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Monthly")
                .repeatEveryMonth("1")
                .saveAndValidateScheduledTasks();
    }

    @Test   //bug: wednesday, 30.06.2021 An unexpected occurrence happened. Log file updated.
    public void tr181_gu_263() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Monthly")
                .repeatEveryMonth("1")
                .startsOnDayDelay(31)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_264() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Monthly")
                .repeatEveryMonth("2")
                .startsOnDayDelay(0)
                .startOnTimeDelay(20)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_265() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Monthly")
                .repeatEveryMonth("1")
                .endsAfter()
                .numberOfReactivations("2")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_266() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Monthly")
                .repeatEveryMonth("1")
                .reactivationEndsOn()
                .endsOnDayDelay(31)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_267() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Monthly")
                .repeatEveryMonth("1")
                .reRunOnFailed()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_268() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Yearly")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_269() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Yearly")
                .startsOnDayDelay(2)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_270() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Yearly")
                .endsAfter()
                .numberOfReactivations("2")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_271() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Yearly")
                .reactivationEndsOn()
                .endsOnDayDelay(365)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_272() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Yearly")
                .reRunOnFailed()
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_273() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .setParameter("Username", VALUE, "ftacs")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
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
                .gotoAction()
                .selectAction("Device reprovision")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
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
                .gotoSetParameters()
                .advancedViewButton()
                .setAdvancedParameter("Device.Time", 2)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
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
                .gotoBackup()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_349() {
        guPage
                .gotoRestore()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_350() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("IPPing diagnostics")
                .inputHost("8.8.8.8")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();

    }

    @Test
    public void tr181_gu_351() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Vendor Configuration File")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .fillUsername()
                .fillPassword()
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_352() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Firmware Image")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .fillUsername()
                .fillPassword()
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_353() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Vendor Configuration File")
                .fromListRadioButton()
                .selectFileName("Vendor Configuration File")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_354() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Firmware Image")
                .fromListRadioButton()
                .selectFileName("Firmware Image")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_355() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Configuration File")
                .manuallyUploadRadioButton()
                .fillUploadUrl()
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_356() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Log File")
                .manuallyUploadRadioButton()
                .fillUploadUrl()
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_357() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_358() {
        guPage
                .gotoAction()
                .selectAction("Reboot")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_359() {
        guPage
                .gotoAction()
                .selectAction("Factory reset")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_360() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_361() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Download")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_362() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Upload")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr181_gu_363() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("FactoryReset")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test   //precondition actions for Reports tab tests (107-112)
    public void tr181_gu_364() {
        guPage
                .goToSetPolicies("Ethernet")
                .setPolicy(2)
                .saveButton()
                .addNewTask("Set parameter value")
                .addTaskButton()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .saveButton()
                .addNewTask("Download file")
                .addTaskButton()
                .selectDownloadFileType("Vendor Configuration File")
                .selectFromListRadioButton()
                .selectFileName("Vendor Configuration File")
                .saveButton()
                .addNewTask("Action")
                .addTaskButton()
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .saveButton()
                .addNewTask("Upload file")
                .addTaskButton()
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .saveButton()
                .addNewTask("Get parameter")
                .addTaskButton()
                .getParameter(1, 2)
                .saveButton()
                .addNewTask("Backup")
                .addTaskButton()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .enterIntoGroup()
                .bottomMenu(EDIT)
                .validateTask()
                .selectItemToDelete()
                .deleteButton()
                .bottomMenu(SAVE)
                .okButtonPopUp();
    }

}
