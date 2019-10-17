package testRunner;

import com.friendly.aqa.pageobject.GlobalButtons;
import com.friendly.aqa.pageobject.GroupUpdatePage;
import org.junit.Test;
import test.BaseTestCase;
import com.friendly.aqa.pageobject.TopMenu;

public class FunctionalTests extends BaseTestCase {
    @Test
    public void firstTest() {
        logger.info("Test starting");
//        Assert.assertEquals("Login", loginPage.getTitle());
        loginPage()
                .authenticate(props.getProperty("ui_user"), props.getProperty("ui_password"))
                .topMenu(TopMenu.GROUP_UPDATE);
        groupUpdatePage()
                .leftMenu(GroupUpdatePage.Left.NEW)
                .waitForRefresh()
                .selectManufacturer("rotal")
                .selectModel()
                .fillName("autotest")
                .selectSendTo()
                .globalButtons(GlobalButtons.NEXT)
                .immediately()
                .globalButtons(GlobalButtons.NEXT)
                .addNewTask(1)
                .addTaskButton()
                .pause(2000);

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
        loginPage().logOut();
    }
}
