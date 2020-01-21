package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.HttpConnector;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.friendly.aqa.pageobject.BasePage.*;
import static com.friendly.aqa.pageobject.GlobalButtons.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.*;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.utils.Table.Conditions.EQUAL;
import static com.friendly.aqa.utils.Table.Parameter.VALUE;
import static org.testng.Assert.*;

@Listeners(UniversalVideoListener.class)
public class GroupUpdateTests extends BaseTestCase {
    @Test
    public void tr069_gu_001() {
        groupUpdatePage.deleteAll();
        groupUpdatePage.topMenu(GROUP_UPDATE);
        waitForUpdate();
        assertTrue(groupUpdatePage.mainTableIsAbsent());
    }

    @Test
    public void tr069_gu_002() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .globalButtons(CANCEL);
        assertTrue(groupUpdatePage.mainTableIsAbsent());
    }

    @Test
    public void tr069_gu_003() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .globalButtons(CANCEL);
        waitForUpdate();
        assertTrue(groupUpdatePage.mainTableIsAbsent());
    }

    @Test
    public void tr069_gu_004() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .selectSendTo()
                .showList();
        assertTrue(groupUpdatePage.serialNumberTableIsPresent());
    }

    @Test
    public void tr069_gu_005() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .createGroup();
        assertTrue(groupUpdatePage.isButtonPresent(FINISH));
        groupUpdatePage
                .globalButtons(CANCEL);
        waitForUpdate();
        groupUpdatePage.pause(500);
        assertEquals(groupUpdatePage.getAttributeById("txtName", "value"), testName);
    }

    @Test
    //A Bug was found while pressing "Next"
    public void tr069_gu_006() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .selectSendTo()
                .createGroup()
                .fillName("tr069_gu_group_name");
    }

    @Test
    public void tr069_gu_010() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .selectSendTo("Individual")
                .getTable("tblDevices")
                .clickOn(1, 0);
        waitForUpdate();
        assertTrue(groupUpdatePage.isButtonActive(NEXT));
        groupUpdatePage.getTable("tblDevices")
                .clickOn(1, 0);
        waitForUpdate();
        assertFalse(groupUpdatePage.isButtonActive(NEXT));
    }

    @Test
    //Doesn't work with Edge
    public void tr069_gu_011() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .selectSendTo("Import")
                .selectImportDevicesFile()
                .showList();
        assertEquals(groupUpdatePage.getTable("tblDevices").getCellText(1, 0), props.getProperty("cpe_serial"));
    }

    @Test
    public void tr069_gu_013() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton();
        assertTrue(groupUpdatePage.isElementPresent("tblParamsValue"));
        assertFalse(groupUpdatePage.isButtonActive(SAVE_AND_ACTIVATE));
    }

    @Test
    public void tr069_gu_014() {
        groupUpdatePage
                .goToSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        groupUpdatePage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp();
        waitForUpdate();
        assertEquals(groupUpdatePage
                .getMainTable()
                .getCellText(4, testName, 1), "Not active");
        setTargetTestName();
    }

    @Test
    public void tr069_gu_015() {
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
                .clickOn("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", 3)
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
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "61");
    }

    @Test
    public void tr069_gu_016() {
        groupUpdatePage
                .goToSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        groupUpdatePage
                .nextSaveAndActivate();
        setTargetTestName();
    }

    @Test
    public void tr069_gu_017() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .getMainTable()
                .clickOn(targetTestName, 4);
        groupUpdatePage
                .globalButtons(EDIT);
        assertFalse(groupUpdatePage.isInputActive("ddlSend"));
        groupUpdatePage
                .globalButtons(NEXT);
        assertFalse(groupUpdatePage.isInputActive("lrbImmediately"));
        groupUpdatePage
                .globalButtons(NEXT);
        assertFalse(groupUpdatePage.isButtonActive(SAVE_AND_ACTIVATE));
    }

    @Test
    public void tr069_gu_018() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .timeHoursSelect("0")
                .globalButtons(NEXT);
        waitForUpdate();
        assertEquals(groupUpdatePage.getAlertTextAndClickOk(), "Update can't scheduled to past"/*"Can't be scheduled to the past"*/);
        groupUpdatePage
                .checkIsCalendarClickable();
    }

    @Test
    public void tr069_gu_019() throws IOException {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        assertTrue(HttpConnector.getUrlSource(groupUpdatePage
                .getMainTable()
                .getExportLink(targetTestName))
                .contains("\"InternetGatewayDevice.ManagementServer.PeriodicInformInterval\" value=\"60\""));
    }

    @Test
    public void tr069_gu_020() {
        groupUpdatePage
                .goToSetParameters()
                .setParameter("Username", VALUE, "ftacs")
                .setParameter("Password", VALUE, "ftacs");
        groupUpdatePage
                .nextSaveAndActivate()
                .setPrefix("InternetGatewayDevice.ManagementServer.")
                .checkResults("Username", "ftacs")
                .checkResults("Password", "ftacs");
    }

    @Test
    public void tr069_gu_021() {
        groupUpdatePage
                .goToSetParameters()
                .setParameter("Username", VALUE, "ftacs");
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.Username", "ftacs");
    }

    @Test
    public void tr069_gu_022() {
        groupUpdatePage
                .goToSetParameters("Time")
                .setAllParameters();
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_023() {
        groupUpdatePage
                .goToSetParameters("Time")
                .setParameter(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_024() {
        groupUpdatePage
                .goToSetParameters("Time")
                .setParameter(2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_025() {
        groupUpdatePage
                .goToSetParameters("WAN")
                .setAllParameters();
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_026() {
        groupUpdatePage
                .goToSetParameters("WAN")
                .setParameter(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_027() {
        groupUpdatePage
                .goToSetParameters("WAN")
                .setParameter(3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_028() {
        groupUpdatePage
                .goToSetParameters("LAN")
                .setAllParameters();
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_029() {
        groupUpdatePage
                .goToSetParameters("LAN")
                .setParameter(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_030() {
        groupUpdatePage
                .goToSetParameters("LAN")
                .setParameter(3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_031() {
        groupUpdatePage
                .goToSetParameters("Wireless")
                .setAllParameters();
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_032() {
        groupUpdatePage
                .goToSetParameters("Wireless")
                .setParameter(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_033() {
        groupUpdatePage
                .goToSetParameters("Wireless")
                .setParameter(3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_034() {
        groupUpdatePage
                .goToSetParameters("DSL settings")
                .setAllParameters();
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_035() {
        groupUpdatePage
                .goToSetParameters("DSL settings")
                .setParameter(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_036() {
        groupUpdatePage
                .goToSetParameters("DSL settings")
                .setParameter(3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_037() {
        groupUpdatePage
                .goToSetParameters("VoIP settings")
                .setAllParameters();
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_038() {
        groupUpdatePage
                .goToSetParameters("VoIP settings")
                .setParameter(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_039() {
        groupUpdatePage
                .goToSetParameters("VoIP settings")
                .setParameter(2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_040() {
        groupUpdatePage
                .goToSetPolicies(null)
                .setAllPolicies();
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_041() {
        groupUpdatePage
                .goToSetPolicies(null)
                .setPolicy(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_042() {
        groupUpdatePage
                .goToSetPolicies(null)
                .setPolicy(2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_043() {
        groupUpdatePage
                .goToSetPolicies(null)
                .setPolicy(3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_044() {
        groupUpdatePage
                .goToSetPolicies("Time")
                .setAllPolicies();
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_045() {
        groupUpdatePage
                .goToSetPolicies("Time")
                .setPolicy(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_046() {
        groupUpdatePage
                .goToSetPolicies("Time")
                .setPolicy(2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_047() {
        groupUpdatePage
                .goToSetPolicies("Time")
                .setPolicy(3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_048() {
        groupUpdatePage
                .goToSetPolicies("WAN")
                .setAllPolicies();
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_049() {
        groupUpdatePage
                .goToSetPolicies("WAN")
                .setPolicy(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_050() {
        groupUpdatePage
                .goToSetPolicies("WAN")
                .setPolicy(2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_051() {
        groupUpdatePage
                .goToSetPolicies("WAN")
                .setPolicy(3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_052() {
        groupUpdatePage
                .goToSetPolicies("LAN")
                .setAllPolicies();
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_053() {
        groupUpdatePage
                .goToSetPolicies("LAN")
                .setPolicy(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_054() {
        groupUpdatePage
                .goToSetPolicies("LAN")
                .setPolicy(2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_055() {
        groupUpdatePage
                .goToSetPolicies("LAN")
                .setPolicy(3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_056() {
        groupUpdatePage
                .goToSetPolicies("Wireless")
                .setAllPolicies();
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_057() {
        groupUpdatePage
                .goToSetPolicies("Wireless")
                .setPolicy(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_058() {
        groupUpdatePage
                .goToSetPolicies("Wireless")
                .setPolicy(2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_059() {
        groupUpdatePage
                .goToSetPolicies("Wireless")
                .setPolicy(3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_060() {
        groupUpdatePage
                .goToSetPolicies("DSL settings")
                .setAllPolicies();
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_061() {
        groupUpdatePage
                .goToSetPolicies("DSL settings")
                .setPolicy(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_062() {
        groupUpdatePage
                .goToSetPolicies("DSL settings")
                .setPolicy(2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_063() {
        groupUpdatePage
                .goToSetPolicies("DSL settings")
                .setPolicy(3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_064() {
        groupUpdatePage
                .goToSetPolicies("VoIP settings")
                .setAllPolicies();
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_065() {
        groupUpdatePage
                .goToSetPolicies("VoIP settings")
                .setPolicy(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_066() {
        groupUpdatePage
                .goToSetPolicies("VoIP settings")
                .setPolicy(2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_067() {
        groupUpdatePage
                .goToSetPolicies("VoIP settings")
                .setPolicy(3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }


    @Test
    public void tr069_gu_068() {
        groupUpdatePage
                .gotoAction()
                .reprovisionRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfValue(2, "CPEReprovision");
//                .checkResults("CPEReprovision", "prov_attrib, custom_rpc, prov_object, profile, provision, file");
    }

    @Test
    public void tr069_gu_069() {
        groupUpdatePage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("GetRPCMethods")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "GetRPCMethods");
    }

    @Test
    public void tr069_gu_070() {
        groupUpdatePage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("GetParameterNames")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "GetParameterNames");
    }

    @Test
    public void tr069_gu_071() {
        groupUpdatePage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("GetParameterAttributes")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "GetParameterAttributes");
    }

    @Test
    public void tr069_gu_072() {
        groupUpdatePage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("GetParameterValues")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "GetParameterValues");
    }

    @Test
    public void tr069_gu_073() {
        groupUpdatePage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("SetParameterValues")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "SetParameterValues");
    }

    @Test
    public void tr069_gu_074() {
        groupUpdatePage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("SetParameterAttributes")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "SetParameterAttributes");
    }

    @Test
    public void tr069_gu_075() {
        groupUpdatePage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("AddObject")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "AddObject");
    }

    @Test
    public void tr069_gu_076() {
        groupUpdatePage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("DeleteObject")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "DeleteObject");
    }

    @Test
    public void tr069_gu_077() {
        groupUpdatePage
                .goToSetParameters("time", true)
                .setAllParameters()
                .setAnyAdvancedParameter();
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_078() {
        groupUpdatePage
                .goToSetParameters("time", true)
                .setParameter(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_079() {
        groupUpdatePage
                .goToSetParameters("time", true)
                .setParameter(2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_080() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel()
                .fillName()
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .onlineDevicesCheckBox()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tabsSettings_tblTabs")
                .clickOn("Time")
                .getTable("tblParamsValue")
                .setParameter(1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults()
                .getTable("tblPeriod")
                .checkResults("Online devices", "True");
    }

    @Test
    public void tr069_gu_081() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .assertElementIsPresent("lblTitle1");
    }

    @Test
    public void tr069_gu_082() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .globalButtons(CANCEL)
                .assertElementIsPresent("tblParameters");
    }

    @Test
    public void tr069_gu_083() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Manufacturer", getManufacturer());
    }

    @Test
    public void tr069_gu_084() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Model", getModelName());
    }

    @Test
    public void tr069_gu_085() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Completed")
                .checkFiltering("State", "Not active")
                .checkFiltering("State", "Error")
                .checkFiltering("State", "All");
    }

    @Test
    public void tr069_gu_086() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Manufacturer");
    }

    @Test
    public void tr069_gu_087() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Model");
    }

    @Test
    public void tr069_gu_088() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name");
    }

    @Test
    public void tr069_gu_089() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name")
                .checkSorting("Created");
    }

    @Test
    public void tr069_gu_090() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Creator");
    }

    @Test
    public void tr069_gu_091() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Updated");
    }

    @Test
    public void tr069_gu_092() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Activated");
    }

    @Test
    public void tr069_gu_093() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE)
                .selectManufacturer()
                .checkResetView();
    }

    @Test
    public void tr069_gu_094() {
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
    public void tr069_gu_095() {
        groupUpdatePage
                .gotoGetParameter()
                .getParameter(1, 1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_096() {
        groupUpdatePage
                .gotoGetParameter("Information")
                .getParameter(1, 1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_097() {
        groupUpdatePage
                .gotoGetParameter("Time")
                .getParameter(1, 1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_098() {
        groupUpdatePage
                .gotoGetParameter("WAN")
                .getParameter(1, 1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_099() {
        groupUpdatePage
                .gotoGetParameter("LAN")
                .getParameter(1, 1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_100() {
        groupUpdatePage
                .gotoGetParameter("Wireless")
                .getParameter(1, 1);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_101() {
        groupUpdatePage
                .gotoGetParameter()
                .getParameter(1, 2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_102() {
        groupUpdatePage
                .gotoGetParameter("Information")
                .getParameter(1, 2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_103() {
        groupUpdatePage
                .gotoGetParameter("Time")
                .getParameter(1, 2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_104() {
        groupUpdatePage
                .gotoGetParameter("WAN")
                .getParameter(1, 2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_105() {
        groupUpdatePage
                .gotoGetParameter("LAN")
                .getParameter(1, 2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_106() {
        groupUpdatePage
                .gotoGetParameter("Wireless")
                .getParameter(1, 2);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_107() {
        groupUpdatePage
                .gotoGetParameter()
                .getParameter(1, 3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_108() {
        groupUpdatePage
                .gotoGetParameter("Information")
                .getParameter(1, 3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_109() {
        groupUpdatePage
                .gotoGetParameter("Time")
                .getParameter(1, 3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_110() {
        groupUpdatePage
                .gotoGetParameter("WAN")
                .getParameter(1, 3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_111() {
        groupUpdatePage
                .gotoGetParameter("LAN")
                .getParameter(1, 3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_112() {
        groupUpdatePage
                .gotoGetParameter("Wireless")
                .getParameter(1, 3);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_113() {
        groupUpdatePage
                .gotoGetParameter()
                .getParameter(1, 0);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_114() {
        groupUpdatePage
                .gotoGetParameter("Information")
                .getParameter(1, 0);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_115() {
        groupUpdatePage
                .gotoGetParameter("Time")
                .getParameter(1, 0);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_116() {
        groupUpdatePage
                .gotoGetParameter("WAN")
                .getParameter(1, 0);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_117() {
        groupUpdatePage
                .gotoGetParameter("LAN")
                .getParameter(1, 0);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_118() {
        groupUpdatePage
                .gotoGetParameter("Wireless")
                .getParameter(1, 0);
        groupUpdatePage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_119() {
        groupUpdatePage
                .gotoBackup()
                .saveAndActivate()
                .assertPresenceOfValue(0, "Backup");
    }

    @Test
    public void tr069_gu_120() {
        groupUpdatePage
                .gotoBackup()
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .assertPresenceOfValue(0, "Backup")
                .assertPresenceOfValue(1, "Present");
    }

    @Test
    public void tr069_gu_121() {
        groupUpdatePage
                .gotoBackup()
                .getTable("tblTasks")
                .clickOn(1, 0);
        groupUpdatePage
                .deleteButton();
        waitForUpdate();
        assertFalse(groupUpdatePage.isElementPresent("tblTasks"));
    }

    @Test
    public void tr069_gu_122() {
        groupUpdatePage
                .gotoRestore()
                .saveAndActivate()
                .assertPresenceOfValue(0, "Restore");
    }

    @Test
    public void tr069_gu_123() {
        groupUpdatePage
                .gotoRestore()
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .getMainTable()
                .readTasksFromDB()  //No waiting for "Completed" status
                .clickOn(testName, 4)
                .getTable("tblTasks")
                .assertPresenceOfValue(0, "Restore")
                .assertPresenceOfValue(1, "Present");
    }

    @Test
    public void tr069_gu_124() {
        groupUpdatePage
                .gotoRestore()
                .getTable("tblTasks")
                .clickOn(1, 0);
        groupUpdatePage
                .deleteButton();
        waitForUpdate();
        assertFalse(groupUpdatePage.isElementPresent("tblTasks"));
    }

    @Test
    public void tr069_gu_134() {
        groupUpdatePage
                .goToSetParameters("Time")
                .setParameter(1);
        groupUpdatePage
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForStatusWithoutRefresh("Completed", 65)
                .clickOn(testName, 4)
                .getTable("tblTasks")
                .checkResults();
    }

    @Test
    public void tr069_gu_135() {
        groupUpdatePage
                .gotoFileDownload()
                .selectFileType(2)
                .manualRadioButton()
                .fillUrl(BasePage.getProps().getProperty("ftp_config_file_url"))
                .fillUserName(BasePage.getProps().getProperty("ftp_user"))
                .fillpassword(BasePage.getProps().getProperty("ftp_password"))
                .nextSaveAndActivate()
                .checkResults("Vendor Configuration File", BasePage.getProps().getProperty("ftp_config_file_url"));
    }

    @Test
    public void tr069_gu_136() {
        groupUpdatePage
                .gotoFileDownload()
                .selectFileType(1)
                .manualRadioButton()
                .fillUrl(BasePage.getProps().getProperty("ftp_image_file_url"))
                .fillUserName(BasePage.getProps().getProperty("ftp_user"))
                .fillpassword(BasePage.getProps().getProperty("ftp_password"))
                .nextSaveAndActivate()
                .checkResults("Firmware Image", BasePage.getProps().getProperty("ftp_image_file_url"));
    }

    @Test
    public void tr069_gu_137() {
        groupUpdatePage
                .gotoFileDownload()
                .selectFileType(2)
                .fromListRadioButton()
                .selectFileName(1)
                .nextSaveAndActivate()
                .checkResults("Vendor Configuration File", BasePage.getProps().getProperty("http_config_file"));
    }

    @Test
    public void tr069_gu_138() {
        groupUpdatePage
                .gotoFileDownload()
                .selectFileType(1)
                .fromListRadioButton()
                .selectFileName(1)
                .nextSaveAndActivate()
                .assertPresenceOfValue(2, "Firmware Image");
//                .checkResults("Firmware Image", BasePage.getProps().getProperty("http_image_file"));
    }

    @Test
    public void tr069_gu_139() {
        groupUpdatePage
                .gotoFileUpload()
                .selectUploadFileType(1)
                .manuallyUrlRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .fillUploadUrl(BasePage.getProps().getProperty("upload_url"))
                .nextSaveAndActivate()
                .checkResults("Vendor Configuration File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr069_gu_140() {
        groupUpdatePage
                .gotoFileUpload()
                .selectUploadFileType(2)
                .manuallyUrlRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .fillUploadUrl(BasePage.getProps().getProperty("upload_url"))
                .nextSaveAndActivate()
                .checkResults("Vendor Log File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr069_gu_141() {
        groupUpdatePage
                .gotoFileUpload()
                .selectUploadFileType(1)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .nextSaveAndActivate()
                .checkResults("Vendor Configuration File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr069_gu_144() {
        groupUpdatePage
                .gotoFileUpload()
                .selectUploadFileType(2)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .nextSaveAndActivate()
                .checkResults("Vendor Log File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr069_gu_145() {
        groupUpdatePage
                .gotoFileUpload()
                .selectUploadFileType(1)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test configuration file upload")
                .globalButtons(NEXT)
                .getTable("tblTasks")
                .checkResults("Vendor Configuration File", BasePage.getProps().getProperty("upload_url"))
                .clickOn(1, 0);
        groupUpdatePage
                .deleteButton()
                .assertResultTableIsAbsent();
    }

    @Test
    public void tr069_gu_146() {
        groupUpdatePage
                .gotoAction()
                .rebootRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void tr069_gu_147() {
        groupUpdatePage
                .gotoAction()
                .factoryResetRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("FactoryReset");
    }

    @Test//80
    public void tr069_gu_148() {
        groupUpdatePage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("Reboot")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "Reboot");
    }

    @Test
    public void tr069_gu_149() {
        groupUpdatePage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("Download")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "Download");
    }

    @Test
    public void tr069_gu_150() {
        groupUpdatePage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("Upload")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "Upload");
    }

    @Test
    public void tr069_gu_151() {
        groupUpdatePage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("FactoryReset")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "FactoryReset");
    }

//    @Test
//    public void tr069_gu_s01() {
//        groupUpdatePage
//                .topMenu(GROUP_UPDATE)
//                .leftMenu(NEW)
//                .selectManufacturer()
//                .selectModel()
//                .fillName()
//                .selectSendTo()
//                .globalButtons(NEXT)
//                .scheduledToRadioButton()
//                .setDelay(10)
//                .globalButtons(NEXT)
//                .addNewTask(1)
//                .addTaskButton()
//                .getTable("tblParamsValue")
//                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
//        groupUpdatePage
//                .globalButtons(NEXT)
//                .globalButtons(SAVE)
//                .okButtonPopUp()
//                .addToScheduled(testName)
//                .waitForStatus("Scheduled", testName, 5)
//                .clickOn(testName, 4);
//        groupUpdatePage
//                .getTable("tblTasks")
//                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
//    }
//
//    @Test
//    public void tr069_gu_s02() {
//        groupUpdatePage
//                .topMenu(GROUP_UPDATE)
//                .leftMenu(NEW)
//                .selectManufacturer()
//                .selectModel()
//                .fillName()
//                .selectSendTo()
//                .globalButtons(NEXT)
//                .scheduledToRadioButton()
//                .setDelay(10)
//                .waitUntilConnectRadioButton()
//                .globalButtons(NEXT)
//                .addNewTask(1)
//                .addTaskButton()
//                .getTable("tblParamsValue")
//                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
//        groupUpdatePage
//                .globalButtons(NEXT)
//                .globalButtons(SAVE)
//                .okButtonPopUp()
//                .addToScheduled(testName)
//                .waitForStatus("Scheduled", testName, 5)
//                .clickOn(testName, 4);
//        groupUpdatePage
//                .getTable("tblTasks")
//                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
//    }
//
//    @Test
//    public void tr069_gu_s03() {
//        groupUpdatePage
//                .topMenu(GROUP_UPDATE)
//                .leftMenu(NEW)
//                .selectManufacturer()
//                .selectModel()
//                .fillName()
//                .selectSendTo()
//                .globalButtons(NEXT)
//                .scheduledToRadioButton()
//                .setDelay(10)
//                .setPeriod(1)
//                .globalButtons(NEXT)
//                .addNewTask(1)
//                .addTaskButton()
//                .getTable("tblParamsValue")
//                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
//        groupUpdatePage
//                .globalButtons(NEXT)
//                .globalButtons(SAVE)
//                .okButtonPopUp()
//                .addToScheduled(testName)
//                .waitForStatus("Scheduled", testName, 5)
//                .clickOn(testName, 4);
//        groupUpdatePage
//                .getTable("tblTasks")
//                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
//    }
//
//    @Test
//    public void tr069_gu_s04() {
//        groupUpdatePage
//                .topMenu(GROUP_UPDATE)
//                .leftMenu(NEW)
//                .selectManufacturer()
//                .selectModel()
//                .fillName()
//                .selectSendTo()
//                .globalButtons(NEXT)
//                .scheduledToRadioButton()
//                .setDelay(10)
//                .setPeriod(1)
//                .waitUntilConnectRadioButton()
//                .globalButtons(NEXT)
//                .addNewTask(1)
//                .addTaskButton()
//                .getTable("tblParamsValue")
//                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
//        groupUpdatePage
//                .globalButtons(NEXT)
//                .globalButtons(SAVE)
//                .okButtonPopUp()
//                .addToScheduled(testName)
//                .waitForStatus("Scheduled", testName, 5)
//                .clickOn(testName, 4);
//        groupUpdatePage
//                .getTable("tblTasks")
//                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
//    }
//
//    @Test
//    public void tr069_gu_s05() {
//        groupUpdatePage
//                .topMenu(GROUP_UPDATE)
//                .leftMenu(NEW)
//                .selectManufacturer()
//                .selectModel()
//                .fillName()
//                .selectSendTo()
//                .globalButtons(NEXT)
//                .scheduledToRadioButton()
//                .setDelay(10)
//                .setPeriod(1)
//                .setPeriod(2)
//                .globalButtons(NEXT)
//                .addNewTask(1)
//                .addTaskButton()
//                .getTable("tblParamsValue")
//                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
//        groupUpdatePage
//                .globalButtons(NEXT)
//                .globalButtons(SAVE)
//                .okButtonPopUp()
//                .addToScheduled(testName)
//                .waitForStatus("Scheduled", testName, 5)
//                .clickOn(testName, 4);
//        groupUpdatePage
//                .getTable("tblTasks")
//                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
//    }
//
//    @Test
//    public void tr069_gu_s06() {
//        groupUpdatePage
//                .topMenu(GROUP_UPDATE)
//                .leftMenu(NEW)
//                .selectManufacturer()
//                .selectModel()
//                .fillName()
//                .selectSendTo()
//                .globalButtons(NEXT)
//                .scheduledToRadioButton()
//                .setDelay(10)
//                .setPeriod(1)
//                .setPeriod(2)
//                .waitUntilConnectRadioButton()
//                .globalButtons(NEXT)
//                .addNewTask(1)
//                .addTaskButton()
//                .getTable("tblParamsValue")
//                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
//        groupUpdatePage
//                .globalButtons(NEXT)
//                .globalButtons(SAVE)
//                .okButtonPopUp()
//                .addToScheduled(testName)
//                .waitForStatus("Scheduled", testName, 5)
//                .clickOn(testName, 4);
//        groupUpdatePage
//                .getTable("tblTasks")
//                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
//    }
//
//    @Test
//    public void tr069_gu_s07() {
//        groupUpdatePage
//                .topMenu(GROUP_UPDATE)
//                .leftMenu(NEW)
//                .selectManufacturer()
//                .selectModel()
//                .fillName()
//                .selectSendTo()
//                .globalButtons(NEXT)
//                .scheduledToRadioButton()
//                .setDelay(10)
//                .onlineDevicesCheckBox()
//                .globalButtons(NEXT)
//                .addNewTask(1)
//                .addTaskButton()
//                .getTable("tblParamsValue")
//                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
//        groupUpdatePage
//                .globalButtons(NEXT)
//                .globalButtons(SAVE)
//                .okButtonPopUp()
//                .addToScheduled(testName)
//                .waitForStatus("Scheduled", testName, 5)
//                .clickOn(testName, 4);
//        groupUpdatePage
//                .getTable("tblTasks")
//                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
//    }
//
//    @Test
//    public void tr069_gu_s08() {
//        groupUpdatePage
//                .topMenu(GROUP_UPDATE)
//                .leftMenu(NEW)
//                .selectManufacturer()
//                .selectModel()
//                .fillName()
//                .selectSendTo()
//                .globalButtons(NEXT)
//                .scheduledToRadioButton()
//                .setDelay(10)
//                .onlineDevicesCheckBox()
//                .waitUntilConnectRadioButton()
//                .globalButtons(NEXT)
//                .addNewTask(1)
//                .addTaskButton()
//                .getTable("tblParamsValue")
//                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
//        groupUpdatePage
//                .globalButtons(NEXT)
//                .globalButtons(SAVE)
//                .okButtonPopUp()
//                .addToScheduled(testName)
//                .waitForStatus("Scheduled", testName, 5)
//                .clickOn(testName, 4);
//        groupUpdatePage
//                .getTable("tblTasks")
//                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
//    }
//
//    @Test
//    public void tr069_gu_s09() {
//        groupUpdatePage
//                .topMenu(GROUP_UPDATE)
//                .leftMenu(NEW)
//                .selectManufacturer()
//                .selectModel()
//                .fillName()
//                .selectSendTo()
//                .globalButtons(NEXT)
//                .scheduledToRadioButton()
//                .setDelay(10)
//                .setPeriod(1)
//                .onlineDevicesCheckBox()
//                .globalButtons(NEXT)
//                .addNewTask(1)
//                .addTaskButton()
//                .getTable("tblParamsValue")
//                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
//        groupUpdatePage
//                .globalButtons(NEXT)
//                .globalButtons(SAVE)
//                .okButtonPopUp()
//                .addToScheduled(testName)
//                .waitForStatus("Scheduled", testName, 5)
//                .clickOn(testName, 4);
//        groupUpdatePage
//                .getTable("tblTasks")
//                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
//    }
//
//    @Test
//    public void tr069_gu_s10() {
//        groupUpdatePage
//                .topMenu(GROUP_UPDATE)
//                .leftMenu(NEW)
//                .selectManufacturer()
//                .selectModel()
//                .fillName()
//                .selectSendTo()
//                .globalButtons(NEXT)
//                .scheduledToRadioButton()
//                .setDelay(10)
//                .setPeriod(1)
//                .onlineDevicesCheckBox()
//                .waitUntilConnectRadioButton()
//                .globalButtons(NEXT)
//                .addNewTask(1)
//                .addTaskButton()
//                .getTable("tblParamsValue")
//                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
//        groupUpdatePage
//                .globalButtons(NEXT)
//                .globalButtons(SAVE)
//                .okButtonPopUp()
//                .addToScheduled(testName)
//                .waitForStatus("Scheduled", testName, 5)
//                .clickOn(testName, 4);
//        groupUpdatePage
//                .getTable("tblTasks")
//                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
//    }
//

}