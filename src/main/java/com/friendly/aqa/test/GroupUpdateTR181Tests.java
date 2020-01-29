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
public class GroupUpdateTR181Tests extends BaseTestCase {

    @Test
    public void tr181_gu_001() {
//        guPage.deleteAll();
        guPage.topMenu(GROUP_UPDATE);
        waitForUpdate();
        assertTrue(guPage.mainTableIsAbsent());
    }

    @Test
    public void tr181_gu_002() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(BasePage.getManufacturer())
                .globalButtons(CANCEL);
        assertTrue(guPage.mainTableIsAbsent());
    }

    @Test
    public void tr181_gu_003() {
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
    public void tr181_gu_004() {
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
    public void tr181_gu_005() {
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
    public void tr181_gu_006() {
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
    public void tr181_gu_007() {
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
    public void tr181_gu_008() {
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
    public void tr181_gu_009() {
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
    public void tr181_gu_010() {
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
    public void tr181_gu_011() {
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
    public void tr181_gu_012() {
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
    public void tr181_gu_013() {
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
    public void tr181_gu_014() {
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
    public void tr181_gu_015() {
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
                .clickOn("Device.ManagementServer.PeriodicInformInterval", 3)
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
                .checkResults("Device.ManagementServer.PeriodicInformInterval", "61");
    }

    @Test
    public void tr181_gu_016() {
        guPage
                .gotoSetParameters()
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        guPage
                .nextSaveAndActivate();
        setTargetTestName();
    }

    @Test
    public void tr181_gu_017() {
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
    public void tr181_gu_018() {
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
    public void tr181_gu_019() throws IOException {
        guPage
                .topMenu(GROUP_UPDATE);
        assertTrue(HttpConnector.getUrlSource(guPage
                .getMainTable()
                .getExportLink(targetTestName))
                .contains("\"Device.ManagementServer.PeriodicInformInterval\" value=\"60\""));
    }

    @Test
    public void tr181_gu_020() {
        guPage
                .gotoSetParameters()
                .setParameter("Username", VALUE, "ftacs")
                .setParameter("Password", VALUE, "ftacs");
        guPage
                .nextSaveAndActivate()
                .setPrefix("Device.ManagementServer.")
                .checkResults("Username", "ftacs")
                .checkResults("Password", "ftacs");
    }

    @Test
    public void tr181_gu_021() {
        guPage
                .gotoSetParameters()
                .setParameter("Username", VALUE, "ftacs");
        guPage
                .nextSaveAndActivate()
                .checkResults("Device.ManagementServer.Username", "ftacs");
    }

    @Test
    public void tr181_gu_022() {
        guPage
                .gotoSetParameters("Information")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_023() {
        guPage
                .gotoSetParameters("Time")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_024() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_025() {
        guPage
                .gotoSetParameters("Time")
                .setParameter(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_026() {
        guPage
                .gotoSetParameters("WiFi")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_027() {
        guPage
                .gotoSetParameters("WiFi")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_028() {
        guPage
                .gotoSetParameters("WiFi")
                .setParameter(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_029() {
        guPage
                .gotoSetParameters("IP")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_030() {
        guPage
                .gotoSetParameters("IP")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_031() {
        guPage
                .gotoSetParameters("IP")
                .setParameter(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_032() {
        guPage
                .gotoSetParameters("Firewall")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_033() {
        guPage
                .gotoSetParameters("Firewall")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_034() {
        guPage
                .gotoSetParameters("Firewall")
                .setParameter(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_035() {
        guPage
                .gotoSetParameters("DHCPv4")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_036() {
        guPage
                .gotoSetParameters("DHCPv4")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_037() {
        guPage
                .gotoSetParameters("DHCPv4")
                .setParameter(2);  //Test failed due to only one parameter on device
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_038() {
        guPage
                .gotoSetParameters("DHCPv6")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_039() {
        guPage
                .gotoSetParameters("DHCPv6")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_040() {
        guPage
                .gotoSetParameters("DHCPv6")
                .setParameter(2);  //Test failed due to only one parameter on device
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_041() {
        guPage
                .gotoSetParameters("DNS")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_042() {
        guPage
                .gotoSetParameters("DNS")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_043() {
        guPage
                .gotoSetParameters("DNS")
                .setParameter(2);  //Test failed due to only one parameter on device
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_044() {
        guPage
                .gotoSetParameters("Users")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_045() {
        guPage
                .gotoSetParameters("Users")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_046() {
        guPage
                .gotoSetParameters("Users")
                .setParameter(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_047() {
        guPage
                .gotoSetParameters("Ethernet")
                .setAllParameters();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_048() {
        guPage
                .gotoSetParameters("Ethernet")
                .setParameter(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_049() {
        guPage
                .gotoSetParameters("Ethernet")
                .setParameter(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_050() {
        guPage
                .goToSetPolicies(null)
                .setAllPolicies();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_051() {
        guPage
                .goToSetPolicies(null)
                .setPolicy(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_052() {
        guPage
                .goToSetPolicies(null)
                .setPolicy(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_053() {
        guPage
                .goToSetPolicies(null)
                .setPolicy(3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_054() {
        guPage
                .goToSetPolicies("Information")
                .setAllPolicies();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_055() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_056() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_057() {
        guPage
                .goToSetPolicies("Information")
                .setPolicy(3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_058() {
        guPage
                .goToSetPolicies("Time")
                .setAllPolicies();
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_059() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(1);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_060() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(2);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }

    @Test
    public void tr181_gu_061() {
        guPage
                .goToSetPolicies("Time")
                .setPolicy(3);
        guPage
                .nextSaveAndActivate()
                .checkResults();
    }



}
