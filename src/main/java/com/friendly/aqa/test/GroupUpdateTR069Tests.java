package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.DataBaseConnector;
import com.friendly.aqa.utils.XmlWriter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.pageobject.BasePage.getManufacturer;
import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Conditions.EQUAL;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.*;
import static com.friendly.aqa.entities.ParameterType.VALUE;
import static com.friendly.aqa.entities.TopMenu.GROUP_UPDATE;

@Listeners(UniversalVideoListener.class)
public class GroupUpdateTR069Tests extends BaseTestCase {
    @Test
    public void tr069_gu_001() {
        guPage
                .deleteAll()
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
                .deleteFilterGroups()
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
                .createGroupButton()
                .assertButtonsAreEnabled(false, PREVIOUS, NEXT, FINISH)
                .bottomMenu(CANCEL)
                .assertInputHasText("txtName", getTestName());
    }

    @Test
    public void tr069_gu_006() {
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
                .assertElementsArePresent("lblNoSelectedCpes");
    }

    @Test
    public void tr069_gu_008() {//depends on 006
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .createGroupButton()
                .fillName("tr069_gu_006")
                .bottomMenu(NEXT)
                .assertElementsArePresent("lblNameInvalid");
    }

    @Test
    public void tr069_gu_009() {//depends on 006
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo("tr069_gu_006")
                .editGroupButton()
                .bottomMenu(DELETE_GROUP)
                .okButtonPopUp()
                .assertAbsenceOfOptions("ddlSend", "tr069_gu_006");
    }

    @Test
    public void tr069_gu_010() {
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
    public void tr069_gu_011() {
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
                .assertPresenceOfValue("tblDevices", 0, getSerial());
    }

    @Test
    public void tr069_gu_013() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .addNewTask("Set parameter value")
                .addTaskButton()
                .assertElementsArePresent("tblParamsValue")
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void tr069_gu_014() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5);
    }

    @Test
    public void tr069_gu_015() {
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("tr069_gu_014")
                .bottomMenu(EDIT)
                .bottomMenu(NEXT)
                .immediately()
                .bottomMenu(NEXT)
                .clickOnTable("tblTasks", "InternetGatewayDevice.ManagementServer.PeriodicInformInterval")
                .setParameter("PeriodicInformInterval, sec", VALUE, "61")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .enterIntoGroup("tr069_gu_014")
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_016() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .nextSaveAndActivate();
    }

    @Test
    public void tr069_gu_017() {//depends on 16
        guPage
                .topMenu(GROUP_UPDATE)
                .enterIntoGroup("tr069_gu_016")
                .bottomMenu(EDIT)
                .assertInputIsDisabled("ddlSend")
                .bottomMenu(NEXT)
                .assertInputIsDisabled("lrbImmediately")
                .bottomMenu(NEXT)
                .assertButtonsAreEnabled(false, SAVE_AND_ACTIVATE);
    }

    @Test
    public void tr069_gu_018() {
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
    public void tr069_gu_019() {//depends on 16
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
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_021() {
        guPage
                .gotoSetParameters()
                .setParameter("Username", VALUE, "ftacs")
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_022() {
        guPage
                .gotoSetParameters("Time")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_023() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_024() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_025() {
        guPage
                .gotoSetParameters("WAN")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_026() {
        guPage
                .gotoSetParameters("WAN")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_027() {
        guPage
                .gotoSetParameters("WAN")
                .setParameter(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_028() {
        guPage
                .gotoSetParameters("LAN")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_029() {
        guPage
                .gotoSetParameters("LAN")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_030() {
        guPage
                .gotoSetParameters("LAN")
                .setParameter(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_031() {
        guPage
                .gotoSetParameters("Wireless")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_032() {
        guPage
                .gotoSetParameters("Wireless")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_033() {
        guPage
                .gotoSetParameters("Wireless")
                .setParameter(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_034() {
        guPage
                .gotoSetParameters("DSL settings")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_035() {
        guPage
                .gotoSetParameters("DSL settings")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_036() {
        guPage
                .gotoSetParameters("DSL settings")
                .setParameter(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_037() {
        guPage
                .gotoSetParameters("VoIP settings")
                .setAllParameters()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_038() {
        guPage
                .gotoSetParameters("VoIP settings")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_039() {
        guPage
                .gotoSetParameters("VoIP settings")
                .setParameter(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_040() {
        guPage
                .goToSetPolicies("Management")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_041() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_042() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_043() {
        guPage
                .goToSetPolicies("Management")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_044() {
        guPage
                .goToSetPolicies("Time")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_045() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_046() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_047() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_048() {
        guPage
                .goToSetPolicies("WAN")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_049() {
        guPage
                .goToSetPolicies("WAN")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_050() {
        guPage
                .goToSetPolicies("WAN")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_051() {
        guPage
                .goToSetPolicies("WAN")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_052() {
        guPage
                .goToSetPolicies("LAN")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_053() {
        guPage
                .goToSetPolicies("LAN")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_054() {
        guPage
                .goToSetPolicies("LAN")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_055() {
        guPage
                .goToSetPolicies("LAN")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_056() {
        guPage
                .goToSetPolicies("Wireless")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_057() {
        guPage
                .goToSetPolicies("Wireless")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_058() {
        guPage
                .goToSetPolicies("Wireless")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_059() {
        guPage
                .goToSetPolicies("Wireless")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_060() {
        guPage
                .goToSetPolicies("DSL settings")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_061() {
        guPage
                .goToSetPolicies("DSL settings")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_062() {
        guPage
                .goToSetPolicies("DSL settings")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_063() {
        guPage
                .goToSetPolicies("DSL settings")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_064() {
        guPage
                .goToSetPolicies("VoIP settings")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_065() {
        guPage
                .goToSetPolicies("VoIP settings")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_066() {
        guPage
                .goToSetPolicies("VoIP settings")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_067() {
        guPage
                .goToSetPolicies("VoIP settings")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_068() {
        guPage
                .gotoAction()
                .selectAction("Device reprovision")
                .nextSaveAndActivate()
                .validateAddedTask("Device reprovision", "CPEReprovision");
    }

    @Test
    public void tr069_gu_069() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetRPCMethods")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "GetRPCMethods");
    }

    @Test
    public void tr069_gu_070() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetParameterNames")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "GetParameterNames");
    }

    @Test
    public void tr069_gu_071() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetParameterAttributes")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "GetParameterAttributes");
    }

    @Test
    public void tr069_gu_072() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("GetParameterValues")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "GetParameterValues");
    }

    @Test
    public void tr069_gu_073() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("SetParameterValues")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "SetParameterValues");
    }

    @Test
    public void tr069_gu_074() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("SetParameterAttributes")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "SetParameterAttributes");
    }

    @Test
    public void tr069_gu_075() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("AddObject")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "AddObject");
    }

    @Test
    public void tr069_gu_076() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("DeleteObject")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "DeleteObject");
    }

    @Test
    public void tr069_gu_077() {
        guPage
                .gotoSetParameters("time", true)
                .setAllParameters()
                .setAnyAdvancedParameter()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_078() {
        guPage
                .gotoSetParameters("time", true)
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_079() {
        guPage
                .gotoSetParameters("time", true)
                .setParameter(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_080() {
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
    public void tr069_gu_081() {
        XmlWriter.createImportGroupFile();
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .selectSendTo()
                .showList()
                .assertPresenceOfValue("tblDevices", 0, getSerial());
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
                .checkFiltering("Manufacturer", getManufacturer());
    }

    @Test
    public void tr069_gu_084() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Model", getModelName());
    }

    @Test
    public void tr069_gu_085() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Completed")
                .checkFiltering("State", "Not active")
                .checkFiltering("State", "Error")
                .checkFiltering("State", "All");
    }

    @Test
    public void tr069_gu_086() {
        guPage
                .topMenu(GROUP_UPDATE)
                .validateSorting("Manufacturer");
    }

    @Test
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
                .validateSorting("Name")
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
                .enterIntoGroup("Manufacturer")
                .checkResetView()
                .leftMenu(VIEW)
                .itemsOnPage("10");
    }

    @Test
    public void tr069_gu_095() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_096() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_097() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_098() {
        guPage
                .gotoGetParameter("WAN")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_099() {
        guPage
                .gotoGetParameter("LAN")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_100() {
        guPage
                .gotoGetParameter("Wireless")
                .getParameter(1, 1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_101() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_102() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_103() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_104() {
        guPage
                .gotoGetParameter("WAN")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_105() {
        guPage
                .gotoGetParameter("LAN")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_106() {
        guPage
                .gotoGetParameter("Wireless")
                .getParameter(1, 2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_107() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_108() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_109() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_110() {
        guPage
                .gotoGetParameter("WAN")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_111() {
        guPage
                .gotoGetParameter("LAN")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_112() {
        guPage
                .gotoGetParameter("Wireless")
                .getParameter(1, 3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_113() {
        guPage
                .gotoGetParameter("Management")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_114() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_115() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_116() {
        guPage
                .gotoGetParameter("WAN")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_117() {
        guPage
                .gotoGetParameter("LAN")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_118() {
        guPage
                .gotoGetParameter("Wireless")
                .getParameter(1, 0)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_119() {
        guPage
                .gotoBackup()
                .saveAndActivate()
                .assertPresenceOfValue("tblTasks", 0, "Backup");
    }

    @Test
    public void tr069_gu_120() {
        guPage
                .gotoBackup()
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .assertPresenceOfValue("tblTasks", 0, "Backup")
                .assertPresenceOfValue("tblTasks", 1, "Present");
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
                .saveAndActivate()
                .assertPresenceOfValue("tblTasks", 0, "Restore");
    }

    @Test
    public void tr069_gu_123() {
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
                .nextSaveAndActivate()
                .validateAddedTask("IPPing diagnostics", "8.8.8.8");
    }

    @Test //bug: Trace diagnostics is absent from Diagnostic list
    public void tr069_gu_126() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Trace Diagnostics")
                .inputHost("8.8.8.8")
                .inputNumOfRepetitions("3")
                .nextSaveAndActivate()
                .validateAddedTask("Trace Diagnostics", "8.8.8.8");
    }

    @Test //Not supported by AudioCodes
    public void tr069_gu_127() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Download diagnostics")
                .nextSaveAndActivate()
                .validateAddedTask("Download diagnostics", "http://127.0.0.1/webdav/Test.cfg");
    }

    @Test //Not supported by AudioCodes
    public void tr069_gu_128() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Upload Diagnostics")
                .nextSaveAndActivate()
                .assertPresenceOfValue("tblTasks", -2, "Upload Diagnostics");
    }

    @Test //bug: Wi-Fi Neighboring diagnostics is absent from Diagnostic list
    public void tr069_gu_129() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Wi-Fi Neighboring Diagnostic")
                .nextSaveAndActivate()
                .assertPresenceOfValue("tblTasks", -2, "Wi-Fi Neighboring Diagnostic");
    }

    @Test //Not supported by AudioCodes
    public void tr069_gu_130() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("DSL Diagnostic")
                .nextSaveAndActivate()
                .assertPresenceOfValue("tblTasks", -2, "DSL Diagnostic");
    }

    @Test //Not supported by AudioCodes
    public void tr069_gu_131() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("NSlookup")
                .nextSaveAndActivate()
                .assertPresenceOfValue("tblTasks", -2, "NSlookup");
    }

    @Test
    public void tr069_gu_132() {
        guPage.setScheduledParameters("Time");
    }

    @Test
    public void tr069_gu_133() {
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
                .selectItem()
                .bottomMenu(ACTIVATE)
                .okButtonPopUp()
                .waitForStatus("Running", 5)
                .enterIntoGroup()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_134() {
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
    public void tr069_gu_135() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Vendor Configuration File")
//                .selectDownloadFileType("Vendor Configuration File")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .fillUsername()
                .fillPassword()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_136() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Firmware Image")
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .nextSaveAndActivate()
                .validateDownloadFileTasks();
    }

    @Test
    public void tr069_gu_137() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Vendor Configuration File")
                .selectFromListRadioButton()
                .selectFileName("Vendor Configuration File")
                .nextSaveAndActivate()
                .validateDownloadFileTasks();
    }

    @Test
    public void tr069_gu_138() {
        guPage
                .gotoFileDownload()
                .selectDownloadFileType("Firmware Image")
                .selectFromListRadioButton()
                .selectFileName("Firmware Image")
                .nextSaveAndActivate()
                .validateDownloadFileTasks();
    }

    @Test
    public void tr069_gu_139() {
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
    public void tr069_gu_140() {
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
    public void tr069_gu_141() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .nextSaveAndActivate()
                .validateAddedTasks(/*"Vendor Configuration File", props.getProperty("file_server")*/);
    }

    @Test
    public void tr069_gu_142() {
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
                .validateAddedTasks(/*"Vendor Configuration File", props.getProperty("file_server")*/);
    }

    @Test
    public void tr069_gu_143() {
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
                .validateAddedTasks(/*"Vendor Log File", props.getProperty("file_server")*/);
    }

    @Test
    public void tr069_gu_144() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Log File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .nextSaveAndActivate()
                .validateAddedTasks(/*"Vendor Log File", props.getProperty("file_server")*/);
    }

    @Test
    public void tr069_gu_145() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test configuration file upload")
                .bottomMenu(NEXT)
                .validateAddedTasks(/*"Vendor Configuration File", props.getProperty("file_server")*/)
                .clickOnTable("tblTasks", 1, 0)
                .deleteButton()
                .assertResultTableIsAbsent();
    }

    @Test
    public void tr069_gu_146() {
        guPage
                .gotoAction()
                .selectAction("Reboot")
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void tr069_gu_147() {
        guPage
                .gotoAction()
                .selectAction("Factory reset")
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Factory reset");
    }

    @Test
    public void tr069_gu_148() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Reboot")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "Reboot");
    }

    @Test
    public void tr069_gu_149() {
        guPage
                .gotoAction()
                .selectAction("Custom RPC")
                .selectMethod("Download")
                .nextSaveAndActivate()
                .validateAddedTask("Custom RPC", "Download");
    }

    @Test
    public void tr069_gu_150() {
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
                .enterIntoGroup()
                .validateAddedTask("Custom RPC", "Upload");
    }

    @Test
    public void tr069_gu_151() {
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
    public void tr069_gu_152() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Scheduled");
    }

    @Test
    public void tr069_gu_153() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Running");
    }

    @Test
    public void tr069_gu_154() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Paused");
    }

    @Test
    public void tr069_gu_155() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Reactivation");
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
                .assertElementsArePresent("lblNoSelectedCpes");
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
    public void tr069_gu_161() {
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
    public void tr069_gu_162() {
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
    public void tr069_gu_163() {
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
    public void tr069_gu_164() {
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
    public void tr069_gu_165() {
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
                .assertElementsArePresent("lblNoSelectedCpes");
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
    public void tr069_gu_171() {
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
    public void tr069_gu_172() {
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
                .createGroupButton()
                .fillName()
                .bottomMenu(NEXT)
                .bottomMenu(PREVIOUS)
                .bottomMenu(CANCEL)
                .assertAbsenceOfOptions("ddlSend", getTestName());
    }

    @Test
    public void tr069_gu_175() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_176() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_177() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_178() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_179() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_180() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_181() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_182() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_183() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_184() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_185() {
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
    public void tr069_gu_186() {
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
    public void tr069_gu_187() {
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
    public void tr069_gu_188() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_189() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_190() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_191() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_192() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_193() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_194() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_195() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_196() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_197() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_198() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_199() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_200() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_201() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_202() {
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
                .validateAddedTasks();
    }

    @Test   //bug: Test fails if run in Friday :)))
    public void tr069_gu_203() {
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
                .validateAddedTasks();
    }

    @Test   //bug: Test fails if run in Friday :)))
    public void tr069_gu_204() {
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

    @Test   //bug: Test fails if run in Friday :)))
    public void tr069_gu_205() {
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

    @Test   //bug: Test fails if run in Friday :)))
    public void tr069_gu_206() {
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
                .validateAddedTasks();
    }

    @Test   //bug: Test fails if run in Friday :)))
    public void tr069_gu_207() {
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
                .validateAddedTasks();
    }

    @Test   //bug: Test fails if run in Friday :)))
    public void tr069_gu_208() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_209() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_210() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_211() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_212() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_213() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_214() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_215() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_216() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_217() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_218() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_219() {
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
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_220() {
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
                .validateAddedTask("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60")
                .validateAddedTask("InternetGatewayDevice.ManagementServer.Username", "ftacs");
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

    @Test   //bug: "Reprovision" RB is absent from Action list;
    public void tr069_gu_233() {
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
                .selectAction("Device reprovision")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTask("Device reprovision", "CPEReprovision");
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
                .setAdvancedParameter("InternetGatewayDevice.Time", 2)
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks();
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
    public void tr069_gu_268() {
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
    public void tr069_gu_269() {
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
                .selectDiagnostic("IPPing Diagnostics")
                .inputHost("8.8.8.8")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfValue("tblTasks", -2, "IPPing Diagnostics");

    }

    @Test
    public void tr069_gu_270() {
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
    public void tr069_gu_271() {
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
                .manuallyDownloadRadioButton()
                .fillDownloadUrl()
                .fillUsername()
                .fillPassword()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks(/*"Firmware Image", props.getProperty("ftp_image_file_url")*/);
    }

    @Test
    public void tr069_gu_272() {
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
                .selectFileName("Vendor Configuration File")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateDownloadFileTasks();
    }

    @Test
    public void tr069_gu_273() {
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
                .selectFileName("Firmware Image")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateDownloadFileTasks();
    }

    @Test
    public void tr069_gu_274() {
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
    public void tr069_gu_275() {
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
    public void tr069_gu_276() {
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
                .selectUploadFileType("Vendor Configuration File")
                .defaultUploadRadioButton()
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTasks(/*"Vendor Configuration File", props.getProperty("file_server")*/);
    }

    @Test
    public void tr069_gu_277() {
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
                .selectAction("Reboot")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void tr069_gu_278() {
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
                .selectAction("Factory reset")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .assertPresenceOfParameter("Factory reset");
    }

    @Test
    public void tr069_gu_279() {
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
    public void tr069_gu_280() {
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
    public void tr069_gu_281() {
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
    public void tr069_gu_282() {
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
                .selectAction("Custom RPC")
                .selectMethod("FactoryReset")
                .bottomMenu(NEXT)
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .enterIntoGroup()
                .validateAddedTask("Custom RPC", "FactoryReset");
    }

    @Test
    public void tr069_gu_283() {
        guPage
                .gotoSetParameters("Information")
                .setParameter(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_284() {
        guPage
                .goToSetPolicies("Information")
                .setAllPolicies()
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_285() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(1)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_286() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(2)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

    @Test
    public void tr069_gu_287() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(3)
                .nextSaveAndActivate()
                .validateAddedTasks();
    }

}