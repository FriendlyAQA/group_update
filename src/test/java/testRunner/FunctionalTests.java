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
    public void test_1() {
        systemPage()
                .topMenu(TopMenu.GROUP_UPDATE);
        Assert.assertTrue(groupUpdatePage().mainTableIsPresent());
    }

    @Test
    public void test_2() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer(0)
                .globalButtons(GlobalButtons.CANCEL)
                .selectManufacturer(2);
        Assert.assertTrue(groupUpdatePage().noDataFoundLabelIsPresent());
    }

    @Test
    public void test_3() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer()
                .selectModel()
                .globalButtons(GlobalButtons.CANCEL)
                .selectManufacturer(2);
        Assert.assertTrue(groupUpdatePage().noDataFoundLabelIsPresent());
    }

    @Test
    public void test_4() {
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
    public void test_5() {
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
//        Assert.assertEquals(groupUpdatePage().getNameValue(), "1234");
        Assert.assertEquals(groupUpdatePage().getAttributeById("txtName", "value"), "1234");
    }

     @Test
    //A Bug was found while pressing "Next" button when "Name group" is filled;
    public void test_6() {
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
        //Bug detected when press "Next" button
    }

    @Test
    public void test_11() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer()
                .selectModel()
                .fillName("auto_test")
                .selectSendTo("Individual")
                .getTable("tblDevices")
                .clickOn(1,0);
        groupUpdatePage().waitForRefresh();
        Assert.assertTrue(groupUpdatePage().isButtonActive(GlobalButtons.NEXT));
        groupUpdatePage().getTable("tblDevices")
                .clickOn(1,0);
        groupUpdatePage().waitForRefresh();
        Assert.assertFalse(groupUpdatePage().isButtonActive(GlobalButtons.NEXT));
    }

    @Test
    //Doesn't work with Edge
    public void test_12() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer()
                .selectModel(1)
                .fillName("auto_test")
                .selectSendTo("Import")
                .selectImportFile()
                .showList();
        Assert.assertEquals(groupUpdatePage().getTable("tblDevices").getCellText(1,0), "34E8943DA030");
    }

    @Test
    public void test_14() {
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
        Assert.assertFalse(groupUpdatePage().isButtonActive(GlobalButtons.SAVE_AND_ACTIVATE));
    }

    @Test
    public void test_15() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .selectManufacturer(1)
                .selectModel()
                .fillName("auto_test")
                .selectSendTo()
                .globalButtons(GlobalButtons.NEXT)
                .immediately()
                .globalButtons(GlobalButtons.NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable("tblParamsValue")
                .setParameter("PeriodicInformInterval, sec", Table.Select.VALUE, "60")
                .clickOn(0,0);
        groupUpdatePage()
                .waitForRefresh()
                .globalButtons(GlobalButtons.NEXT)
                .globalButtons(GlobalButtons.SAVE)
                .okButtonPopUp()
                .waitForRefresh();
        Assert.assertEquals(groupUpdatePage().getMainTable().getCellText(1,4), "auto_test");
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