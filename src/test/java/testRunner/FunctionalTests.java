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
        groupUpdatePage()
                .getMainTable();
    }

    @Test
    public void test_2() {
        groupUpdatePage()
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .waitForRefresh()
                .selectManufacturer(0)
                .globalButtons(GlobalButtons.CANCEL)
                .waitForRefresh()
                .selectManufacturer(2);
        Assert.assertTrue(groupUpdatePage().noDataFoundLabelIsPresent());
    }
}

//                .leftMenu(GroupUpdatePage.Left.NEW)
//                .waitForRefresh()
//                .selectManufacturer("rotal")
//                .selectModel()
//                .fillName("autotest")
//                .selectSendTo()
//                .globalButtons(GlobalButtons.NEXT)
//                .immediately()
//                .globalButtons(GlobalButtons.NEXT)
//                .addNewTask(1)
//                .addTaskButton();

//        System.out.println(groupUpdatePage().checkSorting(9));
//        System.out.println(DataBase.getPendingTaskName("FT001SN000010016E3F0D47666"));
//        groupUpdatePage()
//                .getMainTable()
//                .print()
//                .clickOn(0,8);
//                .pause(1000)
//                .clickOn(0,8);
//                .createGroup()
//                .fillName("asdf")
//                .globalButtons(GlobalButtons.NEXT)
//                .addFilter()
//                .selectColumnFilter(1)
//                .compareSelect(8)
//                .globalButtons(GlobalButtons.NEXT)
//                .filterCreatedCheckBox()
//                .deleteFilter()
//                .okButtonPopUp();