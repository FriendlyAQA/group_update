package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.CalendarUtil;
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
        guPage.deleteAll();
        guPage.topMenu(GROUP_UPDATE);
        waitForUpdate();
        assertTrue(guPage.mainTableIsAbsent());
    }

    @Test
    public void tr069_gu_002() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .globalButtons(CANCEL);
        assertTrue(guPage.mainTableIsAbsent());
    }

    @Test
    public void tr069_gu_003() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .globalButtons(CANCEL);
        waitForUpdate();
        assertTrue(guPage.mainTableIsAbsent());
    }

    @Test
    public void tr069_gu_004() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .selectSendTo()
                .showList();
        assertTrue(guPage.serialNumberTableIsPresent());
    }

    @Test
    public void tr069_gu_005() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .createGroup();
        assertTrue(guPage.isButtonPresent(FINISH));
        guPage
                .globalButtons(CANCEL);
        waitForUpdate();
        guPage.pause(500);
        assertEquals(guPage.getAttributeById("txtName", "value"), testName);
    }

    @Test
    public void tr069_gu_006() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("device_created")
                .compareSelect("IsNull")
                .globalButtons(NEXT);
        assertFalse(guPage.isButtonActive("btnDelFilter_btn"));
        guPage.filterRecordsCheckbox();
        assertTrue(guPage.isButtonActive("btnDelFilter_btn"));
        guPage
                .globalButtons(FINISH)
                .okButtonPopUp();
        setTargetTestName();
        assertEquals(testName, guPage.getSelectedValue("ddlSend"));
    }

    @Test
    public void tr069_gu_007() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("cust2")
                .compareSelect("Equal")
                .inputTextField("111")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertEquals(testName, guPage.getSelectedValue("ddlSend"), "Created group isn't selected!\n");
        assertTrue(guPage.isElementDisplayed("lblNoSelectedCpes"), "Warning 'No devices selected' isn't displayed!\n");
    }

    @Test
    public void tr069_gu_008() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .createGroup()
                .fillName(targetTestName)
                .globalButtons(NEXT);
        assertTrue(guPage.isElementDisplayed("lblNameInvalid"), "Warning 'This name is already in use' isn't displayed!\n");
    }

    @Test
    public void tr069_gu_009() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .selectSendTo(targetTestName)
                .editGroupButton()
                .globalButtons(DELETE_GROUP)
                .okButtonPopUp();
        assertFalse(guPage.isOptionPresent("ddlSend", targetTestName), "Option '" + targetTestName + "' is still present on 'Send to' list!\n");
    }

    @Test
    public void tr069_gu_010() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .selectSendTo("Individual")
                .getTable("tblDevices")
                .clickOn(1, 0);
        waitForUpdate();
        assertTrue(guPage.isButtonActive(NEXT));
        guPage.getTable("tblDevices")
                .clickOn(1, 0);
        waitForUpdate();
        assertFalse(guPage.isButtonActive(NEXT));
    }

    @Test
    //Doesn't work with Edge
    public void tr069_gu_011() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .selectSendTo("Import")
                .selectImportDevicesFile()
                .showList();
        assertEquals(guPage.getTable("tblDevices").getCellText(1, 0), props.getProperty("cpe_serial"));
    }

    @Test
    public void tr069_gu_012() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("device_created")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectSendTo(testName)
                .showList();
        guPage.getTable("tblDevices").assertPresenceOfValue(0, props.getProperty("cpe_serial"));
    }

    @Test
    public void tr069_gu_013() {
        guPage
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
        assertTrue(guPage.isElementPresent("tblParamsValue"));
        assertFalse(guPage.isButtonActive(SAVE_AND_ACTIVATE));
    }

    @Test
    public void tr069_gu_014() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp();
        waitForUpdate();
        assertEquals(guPage
                .getMainTable()
                .getCellText(4, testName, 1), "Not active");
        setTargetTestName();
    }

    @Test
    public void tr069_gu_015() {
        guPage
                .topMenu(GROUP_UPDATE)
                .getMainTable()
                .clickOn(targetTestName, 4);
        guPage
                .globalButtons(EDIT)
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .getTable("tblTasks")
                .clickOn("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", 3)
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "61");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .getMainTable()
                .clickOn(targetTestName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "61");
    }

    @Test
    public void tr069_gu_016() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate();
        setTargetTestName();
    }

    @Test
    public void tr069_gu_017() {
        guPage
                .topMenu(GROUP_UPDATE)
                .getMainTable()
                .clickOn(targetTestName, 4);
        guPage
                .globalButtons(EDIT);
        assertFalse(guPage.isInputActive("ddlSend"));
        guPage
                .globalButtons(NEXT);
        assertFalse(guPage.isInputActive("lrbImmediately"));
        guPage
                .globalButtons(NEXT);
        assertFalse(guPage.isButtonActive(SAVE_AND_ACTIVATE));
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .timeHoursSelect("0")
                .globalButtons(NEXT);
        waitForUpdate();
        assertEquals(guPage.getAlertTextAndClickOk(), "Update can't scheduled to past"/*"Can't be scheduled to the past"*/);
        guPage
                .checkIsCalendarClickable();
    }

    @Test
    public void tr069_gu_019() throws IOException {
        guPage
                .topMenu(GROUP_UPDATE);
        assertTrue(HttpConnector.getUrlSource(guPage
                .getMainTable()
                .getExportLink(targetTestName))
                .contains("\"InternetGatewayDevice.ManagementServer.PeriodicInformInterval\" value=\"60\""));
    }

    @Test
    public void tr069_gu_020() {
        guPage
                .gotoSetParameters()
                .setParameter("Username", VALUE, "ftacs")
                .setParameter("Password", VALUE, "ftacs");
        guPage
                .nextSaveAndActivate()
                .setPrefix("InternetGatewayDevice.ManagementServer.")
                .checkResults("Username", "ftacs")
                .checkResults("Password", "ftacs");
    }

    @Test
    public void tr069_gu_021() {
        guPage
                .gotoSetParameters()
                .setParameter("Username", VALUE, "ftacs");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.Username", "ftacs");
    }

    @Test
    public void tr069_gu_022() {
        guPage
                .gotoSetParameters("Time")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_023() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_024() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_025() {
        guPage
                .gotoSetParameters("WAN")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_026() {
        guPage
                .gotoSetParameters("WAN")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_027() {
        guPage
                .gotoSetParameters("WAN")
                .setParameter(3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_028() {
        guPage
                .gotoSetParameters("LAN")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_029() {
        guPage
                .gotoSetParameters("LAN")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_030() {
        guPage
                .gotoSetParameters("LAN")
                .setParameter(3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_031() {
        guPage
                .gotoSetParameters("Wireless")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_032() {
        guPage
                .gotoSetParameters("Wireless")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_033() {
        guPage
                .gotoSetParameters("Wireless")
                .setParameter(3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_034() {
        guPage
                .gotoSetParameters("DSL settings")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_035() {
        guPage
                .gotoSetParameters("DSL settings")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_036() {
        guPage
                .gotoSetParameters("DSL settings")
                .setParameter(3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_037() {
        guPage
                .gotoSetParameters("VoIP settings")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_038() {
        guPage
                .gotoSetParameters("VoIP settings")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_039() {
        guPage
                .gotoSetParameters("VoIP settings")
                .setParameter(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_040() {
        guPage
                .goToSetPolicies(null)
                .setAllPolicies();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_041() {
        guPage
                .goToSetPolicies(null)
                .setPolicy(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_042() {
        guPage
                .goToSetPolicies(null)
                .setPolicy(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_043() {
        guPage
                .goToSetPolicies(null)
                .setPolicy(3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_044() {
        guPage
                .goToSetPolicies("Time")
                .setAllPolicies();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_045() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_046() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_047() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_048() {
        guPage
                .goToSetPolicies("WAN")
                .setAllPolicies();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_049() {
        guPage
                .goToSetPolicies("WAN")
                .setPolicy(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_050() {
        guPage
                .goToSetPolicies("WAN")
                .setPolicy(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_051() {
        guPage
                .goToSetPolicies("WAN")
                .setPolicy(3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_052() {
        guPage
                .goToSetPolicies("LAN")
                .setAllPolicies();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_053() {
        guPage
                .goToSetPolicies("LAN")
                .setPolicy(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_054() {
        guPage
                .goToSetPolicies("LAN")
                .setPolicy(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_055() {
        guPage
                .goToSetPolicies("LAN")
                .setPolicy(3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_056() {
        guPage
                .goToSetPolicies("Wireless")
                .setAllPolicies();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_057() {
        guPage
                .goToSetPolicies("Wireless")
                .setPolicy(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_058() {
        guPage
                .goToSetPolicies("Wireless")
                .setPolicy(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_059() {
        guPage
                .goToSetPolicies("Wireless")
                .setPolicy(3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_060() {
        guPage
                .goToSetPolicies("DSL settings")
                .setAllPolicies();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_061() {
        guPage
                .goToSetPolicies("DSL settings")
                .setPolicy(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_062() {
        guPage
                .goToSetPolicies("DSL settings")
                .setPolicy(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_063() {
        guPage
                .goToSetPolicies("DSL settings")
                .setPolicy(3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_064() {
        guPage
                .goToSetPolicies("VoIP settings")
                .setAllPolicies();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_065() {
        guPage
                .goToSetPolicies("VoIP settings")
                .setPolicy(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_066() {
        guPage
                .goToSetPolicies("VoIP settings")
                .setPolicy(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_067() {
        guPage
                .goToSetPolicies("VoIP settings")
                .setPolicy(3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }


    @Test
    public void tr069_gu_068() {
        guPage
                .gotoAction()
                .reprovisionRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfValue(2, "CPEReprovision");
//                .checkResults("CPEReprovision", "prov_attrib, custom_rpc, prov_object, profile, provision, file");
    }

    @Test
    public void tr069_gu_069() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("GetRPCMethods")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "GetRPCMethods");
    }

    @Test
    public void tr069_gu_070() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("GetParameterNames")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "GetParameterNames");
    }

    @Test
    public void tr069_gu_071() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("GetParameterAttributes")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "GetParameterAttributes");
    }

    @Test
    public void tr069_gu_072() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("GetParameterValues")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "GetParameterValues");
    }

    @Test
    public void tr069_gu_073() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("SetParameterValues")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "SetParameterValues");
    }

    @Test
    public void tr069_gu_074() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("SetParameterAttributes")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "SetParameterAttributes");
    }

    @Test
    public void tr069_gu_075() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("AddObject")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "AddObject");
    }

    @Test
    public void tr069_gu_076() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("DeleteObject")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "DeleteObject");
    }

    @Test
    public void tr069_gu_077() {
        guPage
                .gotoSetParameters("time", true)
                .setAllParameters()
                .setAnyAdvancedParameter();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_078() {
        guPage
                .gotoSetParameters("time", true)
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_079() {
        guPage
                .gotoSetParameters("time", true)
                .setParameter(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
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
        guPage
                .nextSaveAndActivate()
                .checkResults()
                .getTable("tblPeriod")
                .checkResults("Online devices", "True");
    }

    @Test
    public void tr069_gu_081() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .assertElementIsPresent("lblTitle1");
    }

    @Test
    public void tr069_gu_082() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .globalButtons(CANCEL)
                .assertElementIsPresent("tblParameters");
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
                .checkSorting("Manufacturer");
    }

    @Test
    public void tr069_gu_087() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Model");
    }

    @Test
    public void tr069_gu_088() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name");
    }

    @Test
    public void tr069_gu_089() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name")
                .checkSorting("Created");
    }

    @Test
    public void tr069_gu_090() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Creator");
    }

    @Test
    public void tr069_gu_091() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Updated");
    }

    @Test
    public void tr069_gu_092() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Activated");
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
                .getMainTable()
                .clickOn("Manufacturer");
        guPage
                .checkResetView();
        guPage
                .leftMenu(VIEW)
                .itemsOnPage("10")
                .pause(5000);
    }

    @Test
    public void tr069_gu_095() {
        guPage
                .gotoGetParameter()
                .getParameter(1, 1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_096() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_097() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_098() {
        guPage
                .gotoGetParameter("WAN")
                .getParameter(1, 1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_099() {
        guPage
                .gotoGetParameter("LAN")
                .getParameter(1, 1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_100() {
        guPage
                .gotoGetParameter("Wireless")
                .getParameter(1, 1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_101() {
        guPage
                .gotoGetParameter()
                .getParameter(1, 2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_102() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_103() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_104() {
        guPage
                .gotoGetParameter("WAN")
                .getParameter(1, 2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_105() {
        guPage
                .gotoGetParameter("LAN")
                .getParameter(1, 2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_106() {
        guPage
                .gotoGetParameter("Wireless")
                .getParameter(1, 2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_107() {
        guPage
                .gotoGetParameter()
                .getParameter(1, 3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_108() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_109() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_110() {
        guPage
                .gotoGetParameter("WAN")
                .getParameter(1, 3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_111() {
        guPage
                .gotoGetParameter("LAN")
                .getParameter(1, 3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_112() {
        guPage
                .gotoGetParameter("Wireless")
                .getParameter(1, 3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_113() {
        guPage
                .gotoGetParameter()
                .getParameter(1, 0);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_114() {
        guPage
                .gotoGetParameter("Information")
                .getParameter(1, 0);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_115() {
        guPage
                .gotoGetParameter("Time")
                .getParameter(1, 0);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_116() {
        guPage
                .gotoGetParameter("WAN")
                .getParameter(1, 0);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_117() {
        guPage
                .gotoGetParameter("LAN")
                .getParameter(1, 0);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_118() {
        guPage
                .gotoGetParameter("Wireless")
                .getParameter(1, 0);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr069_gu_119() {
        guPage
                .gotoBackup()
                .saveAndActivate()
                .assertPresenceOfValue(0, "Backup");
    }

    @Test
    public void tr069_gu_120() {
        guPage
                .gotoBackup()
                .addCondition(1, "ManagementServer", "PeriodicInformInterval, sec", EQUAL, "60")
                .saveAndActivate(false)
                .assertPresenceOfValue(0, "Backup")
                .assertPresenceOfValue(1, "Present");
    }

    @Test
    public void tr069_gu_121() {
        guPage
                .gotoBackup()
                .getTable("tblTasks")
                .clickOn(1, 0);
        guPage
                .deleteButton();
        waitForUpdate();
        assertFalse(guPage.isElementPresent("tblTasks"));
    }

    @Test
    public void tr069_gu_122() {
        guPage
                .gotoRestore()
                .saveAndActivate()
                .assertPresenceOfValue(0, "Restore");
    }

    @Test
    public void tr069_gu_123() {
        guPage
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
        guPage
                .gotoRestore()
                .getTable("tblTasks")
                .clickOn(1, 0);
        guPage
                .deleteButton();
        waitForUpdate();
        assertFalse(guPage.isElementPresent("tblTasks"));
    }

    @Test
    public void tr069_gu_125() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("IPPing Diagnostic")
                .inputHostField("8.8.8.8")
                .nextSaveAndActivate()
                .checkResults("IPPing Diagnostic", "8.8.8.8");
    }

    @Test
    public void tr069_gu_126() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Trace Diagnostic")
                .inputHostField("8.8.8.8")
                .numOfRepetitionsField("3")
                .nextSaveAndActivate()
                .checkResults("Trace Diagnostic", "8.8.8.8");
    }

    @Test
    public void tr069_gu_127() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Download Diagnostic")
                .nextSaveAndActivate()
                .assertPresenceOfValue(-2, "Download Diagnostic");
    }

    @Test
    public void tr069_gu_128() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Upload Diagnostic")
                .nextSaveAndActivate()
                .assertPresenceOfValue(-2, "Upload Diagnostic");
    }

    @Test
    public void tr069_gu_129() {
        guPage
                .gotoDiagnostic()
                .selectDiagnostic("Wi-Fi Neighboring Diagnostic")
                .nextSaveAndActivate()
                .assertPresenceOfValue(-2, "Wi-Fi Neighboring Diagnostic");
    }

    @Test
    public void tr069_gu_134() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(1);
        guPage
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
        guPage
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
        guPage
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
        guPage
                .gotoFileDownload()
                .selectFileType(2)
                .fromListRadioButton()
                .selectFileName(1)
                .nextSaveAndActivate()
                .checkResults("Vendor Configuration File", BasePage.getProps().getProperty("http_config_file"));
    }

    @Test
    public void tr069_gu_138() {
        guPage
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
        guPage
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
        guPage
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
        guPage
                .gotoFileUpload()
                .selectUploadFileType(1)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .nextSaveAndActivate()
                .checkResults("Vendor Configuration File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr069_gu_142() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask(5)
                .addTaskButton()
                .selectUploadFileType(1)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test config file upload")
                .nextSaveAndActivate()
                .checkResults("Vendor Configuration File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr069_gu_143() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask(5)
                .addTaskButton()
                .selectUploadFileType(2)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .nextSaveAndActivate()
                .checkResults("Vendor Log File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr069_gu_144() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType(2)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test log file upload")
                .nextSaveAndActivate()
                .checkResults("Vendor Log File", BasePage.getProps().getProperty("upload_url"));
    }

    @Test
    public void tr069_gu_145() {
        guPage
                .gotoFileUpload()
                .selectUploadFileType(1)
                .defaultUploadRadioButton()
                .fillDescriptionUploadFile("test configuration file upload")
                .globalButtons(NEXT)
                .getTable("tblTasks")
                .checkResults("Vendor Configuration File", BasePage.getProps().getProperty("upload_url"))
                .clickOn(1, 0);
        guPage
                .deleteButton()
                .assertResultTableIsAbsent();
    }

    @Test
    public void tr069_gu_146() {
        guPage
                .gotoAction()
                .rebootRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void tr069_gu_147() {
        guPage
                .gotoAction()
                .factoryResetRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("FactoryReset");
    }

    @Test//80
    public void tr069_gu_148() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("Reboot")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "Reboot");
    }

    @Test
    public void tr069_gu_149() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("Download")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "Download");
    }

    @Test
    public void tr069_gu_150() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("Upload")
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .getMainTable()
                .clickOn(1, 0);
        guPage
                .globalButtons(PAUSE)
                .okButtonPopUp()
                .waitForStatus("Paused", 5)
                .readTasksFromDB()
                .clickOn(testName, 4)
                .getTable("tblTasks")
                .checkResults("CustomRPC", "Upload");
    }

    @Test
    public void tr069_gu_151() {
        guPage
                .gotoAction()
                .customRpcRadioButton()
                .selectMethod("FactoryReset")
                .nextSaveAndActivate()
                .checkResults("CustomRPC", "FactoryReset");
    }

    @Test
    public void tr069_gu_154() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Paused");
    }

    @Test
    public void tr069_gu_157() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("device_created")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected!\n");
        guPage.globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }


    @Test //doesn't work correctly (filter 'Created - on day')
    public void tr069_gu_158() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("device_created")
                .compareSelect("On Day")
                .clickOn("calFilterDate_image")
                .selectTodayDate(CalendarUtil.getTodayDateString())
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr069_gu_159() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Prior to")
                .clickOn("calFilterDate_image")
                .selectTodayDate(CalendarUtil.getTodayDateString())
                .inputText("txtTimeHour", CalendarUtil.getHours())
                .inputText("txtTimeMinute", CalendarUtil.getMinutes())
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr069_gu_160() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Later than")
                .clickOn("calFilterDate_image")
                .selectTodayDate(CalendarUtil.getMonthBeforeDate())
                .inputText("txtTimeHour", CalendarUtil.getHours())
                .inputText("txtTimeMinute", CalendarUtil.getMinutes())
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selectedby filter 'Created - Later than'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr069_gu_161() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Today")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Today'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr069_gu_162() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Before Today")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Before Today'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr069_gu_163() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Yesterday")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Yesterday'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr069_gu_164() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Prev 7 days")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Prev 7 days'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr069_gu_165() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Prev X days")
                .inputText("txtInt", "4")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'Created - Prev X days'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr069_gu_166() {
        guPage
                .presetFilter("mycust03", testName)
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("=")
                .inputText("txtText", testName)
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - equals'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr069_gu_167() {
        guPage
                .presetFilter("mycust03", testName)
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("!=")
                .inputText("txtText", testName)
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - not equals'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr069_gu_169() {
        guPage
                .presetFilter("mycust03", testName)
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("Like")
                .inputText("txtText", testName.substring(1, 5))
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Like'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr069_gu_170() {
        guPage
                .presetFilter("mycust03", testName)
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("No like")
                .inputText("txtText", testName.substring(1, 5))
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - No like'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr069_gu_171() {
        guPage
                .presetFilter("mycust03", testName)
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("Is null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Is null'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr069_gu_172() {
        guPage
                .presetFilter("mycust03", testName)
                .gotoAddFilter()
                .selectColumnFilter("mycust03")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp();
        assertFalse(guPage.isElementDisplayed("lblNoSelectedCpes"), "No devices selected by filter 'mycust03 - Is not null'!\n");
        guPage
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate()
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }

    @Test
    public void tr069_gu_173() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Is not null")
                .globalButtons(CANCEL);
        waitForUpdate();
        assertTrue(guPage.isElementDisplayed("lblHead"), "Filter creation didn't cancel properly!\n");
    }

    @Test
    public void tr069_gu_174() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .selectModel(BasePage.getModelName())
                .fillName()
                .createGroup()
                .fillName()
                .globalButtons(NEXT)
                .globalButtons(PREVIOUS)
                .globalButtons(CANCEL);
        waitForUpdate();
        assertFalse(guPage.isOptionPresent("ddlSend", testName), "Option '" + testName + "' is present on 'Send to' list!\n");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setPeriod(2)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setPeriod(2)
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .onlineDevicesCheckBox()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .immediately()
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .immediately()
                .setPeriod(1)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .immediately()
                .setPeriod(1)
                .setPeriod(2)
                .onlineDevicesCheckBox()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .immediately()
                .setPeriod(1)
                .setPeriod(2)
                .onlineDevicesCheckBox()
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setThreshold(50)
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
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
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .setDelay(10)
                .setPeriod(1)
                .setThreshold(50)
                .waitUntilConnectRadioButton()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .checkResults("InternetGatewayDevice.ManagementServer.PeriodicInformInterval", "60");
    }


}