package testRunner;

import com.friendly.aqa.utils.HttpGetter;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.BaseTestCase;

import java.io.IOException;

import static com.friendly.aqa.pageobject.GlobalButtons.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.NEW;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.utils.Table.Select.VALUE;

public class FunctionalTests extends BaseTestCase {
    @Test
    public void test_001() {
        systemPage.topMenu(GROUP_UPDATE);
        groupUpdatePage.waitForRefresh();
        Assert.assertTrue(groupUpdatePage.noDataFoundLabelIsPresent());
    }

    @Test
    public void test_002() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .globalButtons(CANCEL);
        Assert.assertTrue(groupUpdatePage.noDataFoundLabelIsPresent());
    }

    @Test
    public void test_003() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("auto_test_1")
                .globalButtons(CANCEL);
        Assert.assertTrue(groupUpdatePage.noDataFoundLabelIsPresent());
    }

    @Test
    public void test_004() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("autotest")
                .selectSendTo()
                .showList();
        Assert.assertTrue(groupUpdatePage.serialNumberTableIsPresent());
    }

    @Test
    public void test_005() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("1234")
                .createGroup();
        Assert.assertTrue(groupUpdatePage.isButtonPresent(FINISH));
        groupUpdatePage
                .globalButtons(CANCEL)
                .waitForRefresh();
        Assert.assertEquals(groupUpdatePage.getAttributeById("txtName", "value"), "1234");
    }

    @Test
    //A Bug was found while pressing "Next" button when "Name group" is filled;
    public void test_006() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("1234")
                .selectSendTo()
                .createGroup()
                .fillName("test_group_name");
        //Bug is present when press "Next" button
    }

    @Test
    public void test_011() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("auto_test_1")
                .selectSendTo("Individual")
                .getTable("tblDevices")
                .clickOn(1, 0);
        groupUpdatePage.waitForRefresh();
        Assert.assertTrue(groupUpdatePage.isButtonActive(NEXT));
        groupUpdatePage.getTable("tblDevices")
                .clickOn(1, 0);
        groupUpdatePage.waitForRefresh();
        Assert.assertFalse(groupUpdatePage.isButtonActive(NEXT));
    }

    @Test
    //Doesn't work with Edge
    public void test_012() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("auto_test_1")
                .selectSendTo("Import")
                .selectImportFile()
                .showList();
        Assert.assertEquals(groupUpdatePage.getTable("tblDevices").getCellText(1, 0), "FT001SN00001SD18F7FFF521");
    }

    @Test
    public void test_014() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("auto_test_1")
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton();
        Assert.assertTrue(groupUpdatePage.isElementPresent("tblParamsValue"));
        Assert.assertFalse(groupUpdatePage.isButtonActive(SAVE_AND_ACTIVATE));
    }

    @Test
    public void test_015() {
        groupUpdatePage
                .goToSetParameters("auto_test_1")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE)
                .okButtonPopUp()
                .waitForRefresh();
        Assert.assertEquals(groupUpdatePage
                .getMainTable()
                .getCellText(4, "auto_test_1", 1), "Not active");
    }

    @Test
    public void test_016() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .getMainTable()
                .clickOn("auto_test_1", 4);
        groupUpdatePage
                .globalButtons(EDIT)
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .getTable("tblTasks")
                .clickOn("PeriodicInformInterval", 3);
        groupUpdatePage
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", VALUE, "61");
        groupUpdatePage
                .globalButtons(NEXT)
                .globalButtons(SAVE)

                .okButtonPopUp()
                .getMainTable()
                .clickOn("auto_test_1", 4);
        Assert.assertEquals(groupUpdatePage
                .getTable("tblTasks")
                .getCellText(2, "PeriodicInformInterval", 3), "61");
    }

    @Test
    public void test_017() {
        groupUpdatePage
                .goToSetParameters("auto_test_2")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForRefresh();
        softAssert(groupUpdatePage.getMainTable().getCellText(4, "auto_test_2", 1),
                "Completed", "Running");
    }

    @Test
    public void test_018() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .getMainTable()
                .clickOn("auto_test_2", 4);
        groupUpdatePage
                .globalButtons(EDIT);
        Assert.assertFalse(groupUpdatePage.isInputActive("ddlSend"));
        groupUpdatePage
                .globalButtons(NEXT);
        Assert.assertFalse(groupUpdatePage.isInputActive("lrbImmediately"));
        groupUpdatePage
                .globalButtons(NEXT);
        Assert.assertFalse(groupUpdatePage.isButtonActive(SAVE_AND_ACTIVATE));
    }

    @Test
    public void test_019() {
        groupUpdatePage
                .topMenu(GROUP_UPDATE);
        groupUpdatePage
                .leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName("auto_test_2")
                .selectSendTo()
                .globalButtons(NEXT)
                .scheduledToRadioButton()
                .timeHoursSelect(0)
                .globalButtons(NEXT)
                .waitForRefresh();
        Assert.assertEquals(groupUpdatePage.getAlertTextAndClickOk(), "Can't be scheduled to the past");
        groupUpdatePage
                .checkCalendarClickable();
    }

    @Test
    public void test_021() throws IOException {
        systemPage.topMenu(GROUP_UPDATE);
        groupUpdatePage.waitForRefresh();
        Assert.assertTrue(HttpGetter.getUrlSource(groupUpdatePage
                .getMainTable()
                .getExportLink("auto_test_2"))
                .contains("\"InternetGatewayDevice.ManagementServer.PeriodicInformInterval\" value=\"60\""));
    }

    @Test
    public void test_022() {
        groupUpdatePage
                .goToSetParameters("auto_test_3")
                .setParameter("PeriodicInformInterval, sec", VALUE, "60")
                .setParameter("Username", VALUE, "ftacs")
                .setParameter("Password", VALUE, "ftacs");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .getMainTable()
                .clickOn("auto_test_3", 4);
        groupUpdatePage
                .getTable("tblTasks")
                .checkResults("PeriodicInformInterval", "60")
                .checkResults("Username", "ftacs")
                .checkResults("Password", "ftacs");
    }

    @Test
    public void test_023() {
        groupUpdatePage
                .goToSetParameters("auto_test_4")
                .setParameter("Username", VALUE, "ftacs");
        groupUpdatePage
                .waitForRefresh()
                .globalButtons(NEXT)
                .globalButtons(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .getMainTable()
                .clickOn("auto_test_3", 4);
        groupUpdatePage
                .getTable("tblTasks")
                .checkResults("Username", "ftacs");
    }
}