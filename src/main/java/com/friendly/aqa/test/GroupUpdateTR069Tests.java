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
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.*;

@Listeners(UniversalVideoListener.class)
public class GroupUpdateTR069Tests extends BaseTestCase {

    @Test
    public void tr069_gu_000() {
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
    public void tr069_gu_001() {
        guPage
                .topMenu(GROUP_UPDATE)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_gu_002() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .bottomMenu(CANCEL)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_gu_003() {
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
    public void tr069_gu_004() {
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
    public void tr069_gu_005() {
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
    public void tr069_gu_006() {
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
    public void tr069_gu_007() {
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
    public void tr069_gu_008() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .createGroupButton()
                .fillGroupName("tr069_gu_006")
                .bottomMenu(NEXT)
                .assertElementsArePresent("lblNameInvalid");
    }

    @Test   //depends on 006
    public void tr069_gu_009() {//depends on 006
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .addModelButton()
                .selectSendTo("tr069_gu_006")
                .editGroupButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "tr069_gu_006");
    }

    @Test   //BT item #12153
    public void tr069_gu_010() {
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
    public void tr069_gu_011() {
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
    public void tr069_gu_012() {
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
    public void tr069_gu_013() {
        guPage
                .gotoSetParameters()
                .assertElementsArePresent(false, "tblParamsValue")
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void tr069_gu_014() {
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
    public void tr069_gu_015() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("tr069_gu_014")
                .bottomMenu(EDIT)
                .clickOnTask("InternetGatewayDevice.ManagementServer.PeriodicInformInterval")
                .setParameter("PeriodicInformInterval, sec", VALUE, "61")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoGroup("tr069_gu_014")
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void tr069_gu_016() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate();
    }

    @Test   //depends on 016
    public void tr069_gu_017() {//depends on 16
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("tr069_gu_016")
                .bottomMenu(EDIT)
                .assertInputIsDisabled("ddlSend")
                .bottomMenu(NEXT)
                .assertInputIsDisabled("lrbImmediately")
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void tr069_gu_018() {
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
    public void tr069_gu_019() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkExportLink();
    }

    @Test
    public void tr069_gu_020() {
        guPage
                .gotoSetParameters()
                .setParameter("Username", VALUE, "ftacs")
                .setParameter("Password", VALUE, "ftacs")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_021() {
        guPage
                .gotoSetParameters()
                .setParameter("Username", VALUE, "ftacs")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_022() {
        guPage
                .gotoSetParameters("Time")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_023() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_024() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_025() {
        guPage
                .gotoSetParameters("WAN")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_026() {
        guPage
                .gotoSetParameters("WAN")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_027() {
        guPage
                .gotoSetParameters("WAN")
                .setParameter(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_028() {
        guPage
                .gotoSetParameters("LAN")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_029() {
        guPage
                .gotoSetParameters("LAN")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_030() {
        guPage
                .gotoSetParameters("LAN")
                .setParameter(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_031() {
        guPage
                .gotoSetParameters("Wireless")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_032() {
        guPage
                .gotoSetParameters("Wireless")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_033() {
        guPage
                .gotoSetParameters("Wireless")
                .setParameter(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_034() {
        guPage
                .gotoSetParameters("DSL settings")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_035() {
        guPage
                .gotoSetParameters("DSL settings")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_036() {
        guPage
                .gotoSetParameters("DSL settings")
                .setParameter(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_037() {
        guPage
                .gotoSetParameters("VoIP settings")
                .setAllParameters()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_038() {
        guPage
                .gotoSetParameters("VoIP settings")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_039() {
        guPage
                .gotoSetParameters("VoIP settings")
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_040() {
        guPage
                .goToSetPolicies("Management")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_041() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_042() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_043() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_044() {
        guPage
                .goToSetPolicies("Time")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_045() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_046() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_047() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_048() {
        guPage
                .goToSetPolicies("WAN")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_049() {
        guPage
                .goToSetPolicies("WAN")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_050() {
        guPage
                .goToSetPolicies("WAN")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_051() {
        guPage
                .goToSetPolicies("WAN")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_052() {
        guPage
                .goToSetPolicies("LAN")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_053() {
        guPage
                .goToSetPolicies("LAN")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_054() {
        guPage
                .goToSetPolicies("LAN")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_055() {
        guPage
                .goToSetPolicies("LAN")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_056() {
        guPage
                .goToSetPolicies("Wireless")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_057() {
        guPage
                .goToSetPolicies("Wireless")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_058() {
        guPage
                .goToSetPolicies("Wireless")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_059() {
        guPage
                .goToSetPolicies("Wireless")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_060() {
        guPage
                .goToSetPolicies("DSL settings")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_061() {
        guPage
                .goToSetPolicies("DSL settings")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_062() {
        guPage
                .goToSetPolicies("DSL settings")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_063() {
        guPage
                .goToSetPolicies("DSL settings")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_064() {
        guPage
                .goToSetPolicies("VoIP settings")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_065() {
        guPage
                .goToSetPolicies("VoIP settings")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_066() {
        guPage
                .goToSetPolicies("VoIP settings")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_067() {
        guPage
                .goToSetPolicies("VoIP settings")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_068() {
        guPage
                .gotoAction()
                .selectAction("Device reprovision")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_069() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetRPCMethods")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_070() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetParameterNames")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_071() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetParameterAttributes")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_072() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetParameterValues")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_073() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("SetParameterValues")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_074() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("SetParameterAttributes")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_075() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("AddObject")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_076() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("DeleteObject")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_077() {
        guPage
                .gotoSetParameters("time", true)
                .setAllParameters()
                .setAnyAdvancedParameter()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_078() {
        guPage
                .gotoSetParameters("time", true)
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_079() {
        guPage
                .gotoSetParameters("time", true)
                .setParameter(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_080() {
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
    public void tr069_gu_081() {
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
    public void tr069_gu_082() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .bottomMenu(CANCEL)
                .assertElementsArePresent("tblParameters");
    }

    @Test
    public void tr069_gu_083() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Manufacturer");
    }

    @Test
    public void tr069_gu_084() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Model");
    }

    @Test
    public void tr069_gu_085() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Completed")
                .checkFilteringByState("Not active")
                .checkFilteringByState("Error")
                .checkFilteringByState("All");
    }

    @Test   //irrelevant ("Manufacturer" column is absent)
    public void tr069_gu_086() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Manufacturer");
    }

    @Test   //irrelevant ("Model" column is absent)
    public void tr069_gu_087() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Model");
    }

    @Test
    public void tr069_gu_088() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Name");
    }

    @Test
    public void tr069_gu_089() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Created");
    }

    @Test
    public void tr069_gu_090() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Creator");
    }

    @Test
    public void tr069_gu_091() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Updated");
    }

    @Test
    public void tr069_gu_092() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Activated");
    }

    @Test
    public void tr069_gu_093() {
        guPage
                .topMenu(GROUP_UPDATE)
                .selectManufacturer()
                .checkResetView();
    }

    @Test
    public void tr069_gu_094() {
        guPage
                .topMenu(GROUP_UPDATE)
                .clickOnHeader("Name")
                .checkResetView();
    }

    @Test
    public void tr069_gu_095() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_096() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_097() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_098() {
        guPage
                .gotoGetParameter("WAN")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_099() {
        guPage
                .gotoGetParameter("LAN")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_100() {
        guPage
                .gotoGetParameter("Wireless")
                .getParameter(1, 1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_101() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_102() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_103() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_104() {
        guPage
                .gotoGetParameter("WAN")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_105() {
        guPage
                .gotoGetParameter("LAN")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_106() {
        guPage
                .gotoGetParameter("Wireless")
                .getParameter(1, 2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_107() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_108() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_109() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_110() {
        guPage
                .gotoGetParameter("WAN")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_111() {
        guPage
                .gotoGetParameter("LAN")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_112() {
        guPage
                .gotoGetParameter("Wireless")
                .getParameter(1, 3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_113() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_114() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_115() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_116() {
        guPage
                .gotoGetParameter("WAN")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_117() {
        guPage
                .gotoGetParameter("LAN")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_118() {
        guPage
                .gotoGetParameter("Wireless")
                .getParameter(1, 0)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_119() {
        guPage
                .gotoBackup()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_120() {
        guPage
                .gotoBackup()
                .addCondition(1, "Time", "NTPServer1", EQUAL, "www.nist.gov")
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate(false)
                .bottomMenu(EDIT)
                .validateTask()
                .assertConditionIsPresent();
    }

    @Test
    public void tr069_gu_121() {
        guPage
                .gotoBackup()
                .clickOnTable("tblTasks", 1, 0)
                .deleteButton()
                .assertResultTableIsAbsent();
    }

    @Test
    public void tr069_gu_122() {
        guPage
                .gotoRestore()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_123() {
        guPage
                .gotoRestore()
                .addCondition(1, "Time", "NTPServer1", EQUAL, "www.nist.gov")
                .bottomMenu(NEXT)
                .immediately()
                .saveAndActivate(false)
                .bottomMenu(EDIT)
                .validateTask()
                .assertConditionIsPresent();
    }

    @Test
    public void tr069_gu_124() {
        guPage
                .gotoRestore()
                .clickOnTable("tblTasks", 1, 0)
                .deleteButton()
                .assertResultTableIsAbsent();
    }

    @Test
    public void tr069_gu_125() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("IPPing diagnostics")
                .inputHost("8.8.8.8")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test //bug: Trace diagnostics is absent from Diagnostic list of Group Update
    public void tr069_gu_126() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Trace Diagnostics")
                .inputHost("8.8.8.8")
                .inputNumOfRepetitions("3")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_127() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Download diagnostics")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_128() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Upload diagnostics")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test //bug: Wi-Fi Neighboring diagnostics is absent from Diagnostic list of Group Update
    public void tr069_gu_129() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Wi-Fi neighboring diagnostics")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test //bug: DSL diagnostics is absent from Diagnostic list of Group Update
    public void tr069_gu_130() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("DSL diagnostics")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test //bug: NSLoopback diagnostics is absent from Diagnostic list of Group Update
    public void tr069_gu_131() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("NSLoopback diagnostics")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_132() {
        guPage.setScheduledParameters("Time");
    }

    @Test
    public void tr069_gu_133() {
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
                .selectItem()
                .bottomMenu(ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running", 5)
                .enterIntoGroup()
                .validateDetails()
                .validateOptions()
                .bottomMenu(EDIT)
                .validateTask();
    }

    @Test
    public void tr069_gu_134() {
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
    public void tr069_gu_135() {
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
    public void tr069_gu_136() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Firmware Image")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_137() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Vendor Configuration File")
                .selectFromListRadioButton()
                .selectFileName("Vendor Configuration File")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_138() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Firmware Image")
                .selectFromListRadioButton()
                .selectFileName("Firmware Image")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_139() {
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
    public void tr069_gu_140() {
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
    public void tr069_gu_141() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test//Probably step 'waitUntilConnect' skipped in STD
    public void tr069_gu_142() {
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
    public void tr069_gu_143() {
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
    public void tr069_gu_144() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Log File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_145() {
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
    public void tr069_gu_146() {
        guPage
                .gotoAction()
                .selectAction("Reboot")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_147() {
        guPage
                .gotoAction()
                .selectAction("Factory reset")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_148() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_149() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Download")
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_150() {
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
    public void tr069_gu_151() {
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
    public void tr069_gu_152() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Scheduled");
    }

    @Test
    public void tr069_gu_153() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Running");
    }

    @Test
    public void tr069_gu_154() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Paused");
    }

    @Test
    public void tr069_gu_155() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFilteringByState("Reactivation");
    }

    @Test
    public void tr069_gu_156() {
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
    public void tr069_gu_157() {
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
    public void tr069_gu_158() {
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
    public void tr069_gu_159() {
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
    public void tr069_gu_160() {
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
    public void tr069_gu_161() {
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
    public void tr069_gu_162() {
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
    public void tr069_gu_163() {
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
    public void tr069_gu_164() {
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
    public void tr069_gu_165() {
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
    public void tr069_gu_166() {
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
    public void tr069_gu_167() {
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
    public void tr069_gu_168() {
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
    public void tr069_gu_169() {
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
    public void tr069_gu_170() {
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
    public void tr069_gu_171() {
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
    public void tr069_gu_172() {
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
    public void tr069_gu_173() {
        guPage
                .createDeviceGroup()
                .selectColumnFilter("Created")
                .selectCompare("Is not null")
                .bottomMenu(CANCEL)
                .assertElementsArePresent("lblHead");
    }

    @Test
    public void tr069_gu_174() {
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
    public void tr069_gu_175() {
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
    public void tr069_gu_176() {
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
    public void tr069_gu_177() {
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
    public void tr069_gu_178() {
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
    public void tr069_gu_179() {
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
    public void tr069_gu_180() {
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
    public void tr069_gu_181() {
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
    public void tr069_gu_182() {
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
    public void tr069_gu_183() {
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
    public void tr069_gu_184() {
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
    public void tr069_gu_185() {
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
    public void tr069_gu_186() {
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
    public void tr069_gu_187() {
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
    public void tr069_gu_188() {
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
    public void tr069_gu_189() {
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
    public void tr069_gu_190() {
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
    public void tr069_gu_191() {
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
    public void tr069_gu_192() {
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
    public void tr069_gu_193() {
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
    public void tr069_gu_194() {
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
    public void tr069_gu_195() {
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
    public void tr069_gu_196() {
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
    public void tr069_gu_197() {
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
    public void tr069_gu_198() {
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
    public void tr069_gu_199() {
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
    public void tr069_gu_200() {
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
    public void tr069_gu_201() {
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
    public void tr069_gu_202() {
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
    public void tr069_gu_203() {
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

    @Test   //bug: Test fails if run in Friday :)))
    public void tr069_gu_204() {
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

    @Test   //bug: Test fails if run in Friday :)))
    public void tr069_gu_205() {
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

    @Test   //bug: Test fails if run in Friday :)))
    public void tr069_gu_206() {
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

    @Test   //bug: Test fails if run in Friday :)))
    public void tr069_gu_207() {
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

    @Test   //bug: Test fails if run in Friday :)))
    public void tr069_gu_208() {
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
    public void tr069_gu_209() {
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

    @Test
    public void tr069_gu_210() {
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
    public void tr069_gu_211() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "10")
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .repeats("Monthly")
                .repeatEveryMonth("2")
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr069_gu_212() {
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
    public void tr069_gu_213() {
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
    public void tr069_gu_214() {
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
    public void tr069_gu_215() {
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
    public void tr069_gu_216() {
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
    public void tr069_gu_217() {
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
    public void tr069_gu_218() {
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
    public void tr069_gu_219() {
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
    public void tr069_gu_220() {
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
    public void tr069_gu_221() {
        guPage.setScheduledParameters("WAN");
    }

    @Test
    public void tr069_gu_222() {
        guPage.setScheduledParameters("LAN");
    }

    @Test
    public void tr069_gu_223() {
        guPage.setScheduledParameters("Wireless");
    }

    @Test
    public void tr069_gu_224() {
        guPage.setScheduledParameters("DSL settings");
    }

    @Test
    public void tr069_gu_225() {
        guPage.setScheduledParameters("VoIP settings");
    }

    @Test
    public void tr069_gu_226() {
        guPage.setScheduledPolicy("Management");
    }

    @Test
    public void tr069_gu_227() {
        guPage.setScheduledPolicy("Time");
    }

    @Test
    public void tr069_gu_228() {
        guPage.setScheduledPolicy("WAN");
    }

    @Test
    public void tr069_gu_229() {
        guPage.setScheduledPolicy("LAN");
    }

    @Test
    public void tr069_gu_230() {
        guPage.setScheduledPolicy("Wireless");
    }

    @Test
    public void tr069_gu_231() {
        guPage.setScheduledPolicy("DSL settings");
    }

    @Test
    public void tr069_gu_232() {
        guPage.setScheduledPolicy("VoIP settings");
    }

    @Test
    public void tr069_gu_233() {
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
    public void tr069_gu_234() {
        guPage.scheduledCallCustomRPC("GetRPCMethods");
    }

    @Test
    public void tr069_gu_235() {
        guPage.scheduledCallCustomRPC("GetParameterNames");
    }

    @Test
    public void tr069_gu_236() {
        guPage.scheduledCallCustomRPC("GetParameterAttributes");
    }

    @Test
    public void tr069_gu_237() {
        guPage.scheduledCallCustomRPC("GetParameterValues");
    }

    @Test
    public void tr069_gu_238() {
        guPage.scheduledCallCustomRPC("SetParameterValues");
    }

    @Test
    public void tr069_gu_239() {
        guPage.scheduledCallCustomRPC("SetParameterAttributes");
    }

    @Test
    public void tr069_gu_240() {
        guPage.scheduledCallCustomRPC("AddObject");
    }

    @Test
    public void tr069_gu_241() {
        guPage.scheduledCallCustomRPC("DeleteObject");
    }

    @Test
    public void tr069_gu_242() {
        guPage
                .gotoSetParameters()
                .advancedViewButton()
                .setAdvancedParameter("InternetGatewayDevice.Time", 2)
                .saveButton()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr069_gu_243() {
        guPage.getScheduledParameter("Management", 1);
    }

    @Test
    public void tr069_gu_244() {
        guPage.getScheduledParameter("Information", 1);
    }

    @Test
    public void tr069_gu_245() {
        guPage.getScheduledParameter("Time", 1);
    }

    @Test
    public void tr069_gu_246() {
        guPage.getScheduledParameter("WAN", 1);
    }

    @Test
    public void tr069_gu_247() {
        guPage.getScheduledParameter("LAN", 1);
    }

    @Test
    public void tr069_gu_248() {
        guPage.getScheduledParameter("Wireless", 1);
    }

    @Test
    public void tr069_gu_249() {
        guPage.getScheduledParameter("Management", 2);
    }

    @Test
    public void tr069_gu_250() {
        guPage.getScheduledParameter("Information", 2);
    }

    @Test
    public void tr069_gu_251() {
        guPage.getScheduledParameter("Time", 2);
    }

    @Test
    public void tr069_gu_252() {
        guPage.getScheduledParameter("WAN", 2);
    }

    @Test
    public void tr069_gu_253() {
        guPage.getScheduledParameter("LAN", 2);
    }

    @Test
    public void tr069_gu_254() {
        guPage.getScheduledParameter("Wireless", 2);
    }

    @Test
    public void tr069_gu_255() {
        guPage.getScheduledParameter("Management", 3);
    }

    @Test
    public void tr069_gu_256() {
        guPage.getScheduledParameter("Information", 3);
    }

    @Test
    public void tr069_gu_257() {
        guPage.getScheduledParameter("Time", 3);
    }

    @Test
    public void tr069_gu_258() {
        guPage.getScheduledParameter("WAN", 3);
    }

    @Test
    public void tr069_gu_259() {
        guPage.getScheduledParameter("LAN", 3);
    }

    @Test
    public void tr069_gu_260() {
        guPage.getScheduledParameter("Wireless", 3);
    }

    @Test
    public void tr069_gu_261() {
        guPage.getScheduledParameter("Management", 0);
    }

    @Test
    public void tr069_gu_262() {
        guPage.getScheduledParameter("Information", 0);
    }

    @Test
    public void tr069_gu_263() {
        guPage.getScheduledParameter("Time", 0);
    }

    @Test
    public void tr069_gu_264() {
        guPage.getScheduledParameter("WAN", 0);
    }

    @Test
    public void tr069_gu_265() {
        guPage.getScheduledParameter("LAN", 0);
    }

    @Test
    public void tr069_gu_266() {
        guPage.getScheduledParameter("Wireless", 0);
    }

    @Test
    public void tr069_gu_267() {
        guPage
                .gotoBackup()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr069_gu_268() {
        guPage
                .gotoRestore()
                .bottomMenu(NEXT)
                .scheduledTo()
                .setDelay(10)
                .saveAndValidateScheduledTasks();
    }

    @Test
    public void tr069_gu_269() {
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
    public void tr069_gu_270() {
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
    public void tr069_gu_271() {
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
    public void tr069_gu_272() {
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
    public void tr069_gu_273() {
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
    public void tr069_gu_274() {
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
    public void tr069_gu_275() {
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
    public void tr069_gu_276() {
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
    public void tr069_gu_277() {
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
    public void tr069_gu_278() {
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
    public void tr069_gu_279() {
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
    public void tr069_gu_280() {
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
    public void tr069_gu_281() {
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
    public void tr069_gu_282() {
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

    @Test
    public void tr069_gu_283() {
        guPage
                .gotoSetParameters("Information")
                .setParameter(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_284() {
        guPage
                .goToSetPolicies("Information")
                .setAllPolicies()
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_285() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(1)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_286() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(2)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

    @Test
    public void tr069_gu_287() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(3)
                .saveButton()
                .immediatelyActivateAndValidate();
    }

}