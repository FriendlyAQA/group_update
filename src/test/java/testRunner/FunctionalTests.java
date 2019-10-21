package testRunner;

import com.friendly.aqa.pageobject.GlobalButtons;
import com.friendly.aqa.pageobject.GroupUpdatePage;
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
        Assert.assertTrue(groupUpdatePage().buttonIsPresent(GlobalButtons.FINISH));
        groupUpdatePage()
                .globalButtons(GlobalButtons.CANCEL)
                .waitForRefresh();
        Assert.assertEquals(groupUpdatePage().getNameValue(), "1234");
    }

    @Test
    //A Bug was found while pressing "Next" button after "Name group" is filled;
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