package com.friendly.aqa.test;

import com.friendly.aqa.pageobject.BasePage;
import org.testng.annotations.Test;

import static com.friendly.aqa.pageobject.BasePage.*;
import static com.friendly.aqa.pageobject.GlobalButtons.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.NEW;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.utils.Table.Parameter.VALUE;
import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class GroupUpdateMqttTests extends BaseTestCase {
    @Test
    public void mqtt_gu_001() {
        guPage.deleteAll();
        guPage.topMenu(GROUP_UPDATE);
        waitForUpdate();
        assertTrue(guPage.mainTableIsAbsent());
    }

    @Test
    public void mqtt_gu_002() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .globalButtons(CANCEL);
        assertTrue(guPage.mainTableIsAbsent());
    }

    @Test
    public void mqtt_gu_003() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName()
                .globalButtons(CANCEL);
        waitForUpdate();
        assertTrue(guPage.mainTableIsAbsent());
    }

    @Test
    public void mqtt_gu_004() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName()
                .selectSendTo()
                .showList();
        assertTrue(guPage.serialNumberTableIsPresent());
    }

    @Test
    public void mqtt_gu_005() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
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
    public void mqtt_gu_006() {
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
    public void mqtt_gu_007() {
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
    public void mqtt_gu_008() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName()
                .createGroup()
                .fillName(targetTestName)
                .globalButtons(NEXT);
        assertTrue(guPage.isElementDisplayed("lblNameInvalid"), "Warning 'This name is already in use' isn't displayed!\n");
    }

    @Test
    public void mqtt_gu_009() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName()
                .selectSendTo(targetTestName)
                .editGroupButton()
                .globalButtons(DELETE_GROUP)
                .okButtonPopUp();
        assertFalse(guPage.isOptionPresent("ddlSend", targetTestName), "Option '" + targetTestName + "' is still present on 'Send to' list!\n");
    }

    @Test
    public void mqtt_gu_010() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
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
    public void mqtt_gu_011() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
                .fillName()
                .selectSendTo("Import")
                .selectImportDevicesFile()
                .showList()
                .getTable("tblDevices").assertPresenceOfValue(0, BasePage.getSerial());
    }

    @Test
    public void mqtt_gu_012() {
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
    public void mqtt_gu_013() {
        guPage
                .topMenu(GROUP_UPDATE)
                .leftMenu(NEW)
                .selectManufacturer(getManufacturer())
                .selectModel(getModelName())
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

}
