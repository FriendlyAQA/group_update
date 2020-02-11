package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.pageobject.BasePage.*;
import static com.friendly.aqa.pageobject.GlobalButtons.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.*;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.utils.Table.Conditions.NOT_EQUAL;
import static org.testng.Assert.*;

@Listeners(UniversalVideoListener.class)
public class GroupUpdateUspTests extends BaseTestCase {
    @Test
    public void usp_gu_001() {
        guPage.deleteAll();
        guPage.topMenu(GROUP_UPDATE);
        waitForUpdate();
        assertTrue(guPage.mainTableIsAbsent());
    }

    @Test
    public void usp_gu_002() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .globalButtons(CANCEL);
        assertTrue(guPage.mainTableIsAbsent());
    }

    @Test
    public void usp_gu_003() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel(getModelName())
                .fillName()
                .deleteFilterGroups()
                .globalButtons(CANCEL);
        waitForUpdate();
        assertTrue(guPage.mainTableIsAbsent());
    }

    @Test
    public void usp_gu_004() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel(getModelName())
                .fillName()
                .selectSendTo()
                .showList();
        assertTrue(guPage.serialNumberTableIsPresent());
    }

    @Test
    public void usp_gu_005() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel(getModelName())
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
    public void usp_gu_006() {
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
    public void usp_gu_007() {
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
    public void usp_gu_008() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel(getModelName())
                .fillName()
                .createGroup()
                .fillName(targetTestName)
                .globalButtons(NEXT);
        assertTrue(guPage.isElementDisplayed("lblNameInvalid"), "Warning 'This name is already in use' isn't displayed!\n");
    }

    @Test
    public void usp_gu_009() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel(getModelName())
                .fillName()
                .selectSendTo(targetTestName)
                .editGroupButton()
                .globalButtons(DELETE_GROUP)
                .okButtonPopUp();
        assertFalse(guPage.isOptionPresent("ddlSend", targetTestName), "Option '" + targetTestName + "' is still present on 'Send to' list!\n");
    }

    @Test
    public void usp_gu_010() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel(getModelName())
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
    public void usp_gu_011() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel(getModelName())
                .fillName()
                .selectSendTo("Import")
                .selectImportDevicesFile()
                .showList()
                .getTable("tblDevices").assertPresenceOfValue(0, BasePage.getSerial());
    }

    @Test
    public void usp_gu_012() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("device_created")
                .compareSelect("Is not null")
                .globalButtons(NEXT)
                .globalButtons(FINISH)
                .okButtonPopUp()
                .selectSendTo(testName)
                .showList();
        guPage.getTable("tblDevices").assertPresenceOfValue(0, BasePage.getSerial());
    }

    @Test
    public void usp_gu_018() {
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
    public void usp_gu_032() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .rebootRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void usp_gu_033() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .rebootRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "NodeAddr", NOT_EQUAL, "127.0.0.1");
        guPage
                .saveAndActivate(false)
                .assertPresenceOfValue(2, "Reboot");
    }

    @Test
    public void usp_gu_034() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .factoryResetRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("FactoryReset");
    }

    @Test
    public void usp_gu_035() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .factoryResetRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "NodeAddr", NOT_EQUAL, "127.0.0.1");
        guPage
                .saveAndActivate(false)
                .assertPresenceOfValue(2, "FactoryReset");
    }

    @Test
    public void usp_gu_036() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .reprovisionRadioButton()
                .nextSaveAndActivate()
                .assertPresenceOfParameter("CPEReprovision");
    }

    @Test
    public void usp_gu_037() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel(getModelName())
                .fillName(BaseTestCase.getTestName())
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask("Action")
                .addTaskButton()
                .reprovisionRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "NodeAddr", NOT_EQUAL, "127.0.0.1");
        guPage
                .saveAndActivate(false)
                .assertPresenceOfValue(2, "CPEReprovision");
    }

    @Test
    public void usp_gu_039() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .selectImportGuFile()
                .assertElementIsPresent("lblTitle1");
    }

    @Test
    public void usp_gu_040() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(IMPORT)
                .globalButtons(CANCEL)
                .assertElementIsPresent("tblParameters");
    }

    @Test
    public void usp_gu_041() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Manufacturer", getManufacturer());
    }

    @Test
    public void usp_gu_042() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("Model", getModelName());
    }

    @Test
    public void usp_gu_043() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Completed")
                .checkFiltering("State", "Not active")
                .checkFiltering("State", "Error")
                .checkFiltering("State", "All");
    }

    @Test
    public void usp_gu_044() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Manufacturer");
    }

    @Test
    public void usp_gu_045() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Model");
    }

    @Test
    public void usp_gu_046() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name");
    }

    @Test
    public void usp_gu_047() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Name")
                .checkSorting("Created");
    }

    @Test
    public void usp_gu_048() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Creator");
    }

    @Test
    public void usp_gu_049() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Updated");
    }

    @Test
    public void usp_gu_050() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkSorting("Activated");
    }

    @Test
    public void usp_gu_051() {
        guPage
                .topMenu(GROUP_UPDATE)
                .selectManufacturer()
                .checkResetView();
    }

    @Test
    public void usp_gu_052() {
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
    public void usp_gu_054() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Scheduled")
                .resetView();
    }

    @Test
    public void usp_gu_055() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Running")
                .resetView();
    }

    @Test
    public void usp_gu_056() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Paused")
                .resetView();
    }

    @Test
    public void usp_gu_057() {
        guPage
                .topMenu(GROUP_UPDATE)
                .checkFiltering("State", "Reactivation")
                .resetView();
    }

    @Test
    public void usp_gu_075() {
        guPage
                .gotoAddFilter()
                .selectColumnFilter("Created")
                .compareSelect("Is not null")
                .globalButtons(CANCEL);
        waitForUpdate();
        assertTrue(guPage.isElementDisplayed("lblHead"), "Filter creation didn't cancel properly!\n");
    }

    @Test
    public void usp_gu_076() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModel(getModelName())
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
    public void usp_gu_136() {
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
                .addNewTask("Action")
                .addTaskButton()
                .rebootRadioButton()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void usp_gu_137() {
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
                .addNewTask("Action")
                .addTaskButton()
                .rebootRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "NodeAddr", NOT_EQUAL, "127.0.0.1")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .assertPresenceOfParameter("Reboot");
    }

    @Test
    public void usp_gu_138() {
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
                .addNewTask("Action")
                .addTaskButton()
                .factoryResetRadioButton()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .assertPresenceOfParameter("FactoryReset");
    }

    @Test
    public void usp_gu_139() {
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
                .addNewTask("Action")
                .addTaskButton()
                .factoryResetRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "NodeAddr", NOT_EQUAL, "127.0.0.1")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .assertPresenceOfParameter("FactoryReset");
    }

    @Test
    public void usp_gu_140() {
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
                .addNewTask("Action")
                .addTaskButton()
                .reprovisionRadioButton()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .assertPresenceOfParameter("CPEReprovision");
    }

    @Test
    public void usp_gu_141() {
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
                .addNewTask("Action")
                .addTaskButton()
                .reprovisionRadioButton()
                .globalButtons(NEXT)
                .addCondition(1, "ManagementServer", "NodeAddr", NOT_EQUAL, "127.0.0.1")
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForStatus("Scheduled", 5)
                .clickOn(testName, 4);
        guPage
                .getTable("tblTasks")
                .assertPresenceOfParameter("CPEReprovision");
    }
}
