package testRunner;

import com.friendly.aqa.pageobject.GlobalButtons;
import com.friendly.aqa.pageobject.GroupUpdatePage;
import com.friendly.aqa.pageobject.Table;
import org.testng.Assert;
import org.testng.annotations.*;
import test.BaseTestCase;
import com.friendly.aqa.pageobject.TopMenu;

public class FunctionalTests extends BaseTestCase {
    @Test
    public void test_001() {
        systemPage()
                .topMenu(TopMenu.GROUP_UPDATE);
        Assert.assertTrue(groupUpdatePage().noDataFoundLabelIsPresent());
    }

    @Test
    public void test_002() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer(0)
                .globalButtons(GlobalButtons.CANCEL);
        Assert.assertTrue(groupUpdatePage().noDataFoundLabelIsPresent());
    }

    @Test
    public void test_003() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer()
                .selectModel()
                .fillName("auto_test")
                .globalButtons(GlobalButtons.CANCEL);
        Assert.assertTrue(groupUpdatePage().noDataFoundLabelIsPresent());
    }

    @Test
    public void test_004() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer()
                .selectModel()
                .fillName("autotest")
                .selectSendTo()
                .showList();
        Assert.assertTrue(groupUpdatePage().serialNumberTableIsPresent());
    }

    @Test
    public void test_005() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer()
                .selectModel()
                .fillName("1234")
                .createGroup();
        Assert.assertTrue(groupUpdatePage().isButtonPresent(GlobalButtons.FINISH));
        groupUpdatePage()
                .globalButtons(GlobalButtons.CANCEL)
                .waitForRefresh();
        Assert.assertEquals(groupUpdatePage().getAttributeById("txtName", "value"), "1234");
    }

    @Test
    //A Bug was found while pressing "Next" button when "Name group" is filled;
    public void test_006() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer()
                .selectModel()
                .fillName("1234")
                .selectSendTo()
                .createGroup()
                .fillName("test_group_name");
        //Bug is present when press "Next" button
    }

    @Test
    public void test_011() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer()
                .selectModel()
                .fillName("auto_test")
                .selectSendTo("Individual")
                .getTable("tblDevices")
                .clickOn(1, 0);
        groupUpdatePage().waitForRefresh();
        Assert.assertTrue(groupUpdatePage().isButtonActive(GlobalButtons.NEXT));
        groupUpdatePage().getTable("tblDevices")
                .clickOn(1, 0);
        groupUpdatePage().waitForRefresh();
        Assert.assertFalse(groupUpdatePage().isButtonActive(GlobalButtons.NEXT));
    }

    @Test
    //Doesn't work with Edge
    public void test_012() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer()
                .selectModel()
                .fillName("auto_test")
                .selectSendTo("Import")
                .selectImportFile()
                .showList();
        Assert.assertEquals(groupUpdatePage().getTable("tblDevices").getCellText(1, 0), "FT001SN0000168FF7B63321C");
    }

    @Test
    public void test_014() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer()
                .selectModel()
                .fillName("auto_test")
                .selectSendTo()
                .globalButtons(GlobalButtons.NEXT)
                .immediately()
                .globalButtons(GlobalButtons.NEXT)
                .addNewTask(1)
                .addTaskButton();
        Assert.assertTrue(groupUpdatePage().isElementPresent("tblParamsValue"));
        Assert.assertFalse(groupUpdatePage().isButtonActive(GlobalButtons.SAVE_AND_ACTIVATE));
    }

    @Test
    public void test_015() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer()
                .selectModel()
                .fillName("auto_test")
                .selectSendTo()
                .globalButtons(GlobalButtons.NEXT)
                .immediately()
                .globalButtons(GlobalButtons.NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", Table.Select.VALUE, "60");
        groupUpdatePage()
                .waitForRefresh()
                .globalButtons(GlobalButtons.NEXT)
                .globalButtons(GlobalButtons.SAVE)
                .okButtonPopUp()
                .waitForRefresh();
        Assert.assertEquals(groupUpdatePage()
                .getMainTable()
                .getCellText(4, "auto_test", 1), "Not active");
    }

    @Test
    public void test_016() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .getMainTable()
                .clickOn("auto_test", 4);
        groupUpdatePage()
//                .pause(3000)
                .globalButtons(GlobalButtons.EDIT)
//                .pause(3000)
                .globalButtons(GlobalButtons.NEXT)
                .immediately()
                .globalButtons(GlobalButtons.NEXT)
                .getTable("tblTasks")
                .clickOn("PeriodicInformInterval", 3);
        groupUpdatePage()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", Table.Select.VALUE, "61");
        groupUpdatePage()
                .globalButtons(GlobalButtons.NEXT)
                .globalButtons(GlobalButtons.SAVE)
                .okButtonPopUp()
                .getMainTable()
                .clickOn("auto_test", 4);
        Assert.assertEquals(groupUpdatePage()
                .getTable("tblTasks")
                .getCellText(2, "PeriodicInformInterval", 3), "61");
    }

    @Test
    public void test_017() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer()
                .selectModel()
                .fillName("auto_test_2")
                .selectSendTo()
                .globalButtons(GlobalButtons.NEXT)
                .immediately()
                .globalButtons(GlobalButtons.NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", Table.Select.VALUE, "60");
        groupUpdatePage()
                .waitForRefresh()
                .globalButtons(GlobalButtons.NEXT)
                .globalButtons(GlobalButtons.SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .waitForRefresh();
        softAssert(groupUpdatePage().getMainTable().getCellText(4, "auto_test_2", 1),
                "Completed", "Running");
    }

    @Test
    public void test018() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .getMainTable()
                .clickOn("auto_test_2", 4);
        groupUpdatePage()
                .globalButtons(GlobalButtons.EDIT);
        Assert.assertFalse(groupUpdatePage().isInputActive("ddlSend"));
        groupUpdatePage()
                .globalButtons(GlobalButtons.NEXT);
        Assert.assertFalse(groupUpdatePage().isInputActive("lrbImmediately"));
        groupUpdatePage()
                .globalButtons(GlobalButtons.NEXT);
        Assert.assertFalse(groupUpdatePage().isButtonActive(GlobalButtons.SAVE_AND_ACTIVATE));
//                .globalButtons(GlobalButtons.NEXT)
    }

    @Test
    public void test_019() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer()
                .selectModel()
                .fillName("auto_test_3")
                .selectSendTo()
                .globalButtons(GlobalButtons.NEXT)
                .scheduledToRadioButton()
                .timeHoursSelect(0)
                .globalButtons(GlobalButtons.NEXT);
        Assert.assertEquals(groupUpdatePage().getAlertTextAndClickOk(), "Can't be scheduled to the past");
    }
}
//        System.out.println(groupUpdatePage().checkSorting(9));
//        System.out.println(DataBase.getPendingTaskName("FT001SN000010016E3F0D47666"));
//        groupUpdatePage()
//                .getMainTable()
//                .print()
//                .clickOn(0,8);
//                .selectColumnFilter(1)
//                .compareSelect(8)
//                .globalButtons(GlobalButtons.NEXT)
//                .filterCreatedCheckBox()
//                .deleteFilter()
//                .okButtonPopUp();
//                .insertImportFile();