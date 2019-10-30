package test;

//import com.friendly.aqa.database.DataBase;
import com.friendly.aqa.pageobject.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTestCase extends TestBase {
    private LoginPage loginPage;
    protected SystemPage systemPage;
    protected GroupUpdatePage groupUpdatePage;

    @BeforeSuite
    public void init() {
        logger.info("\n*************************STARTING TEST SUITE**************************");
        loginPage = new LoginPage();
        systemPage = new SystemPage();
//        DataBase.connectDb(props.getProperty("db_url"), props.getProperty("db_user"), props.getProperty("db_password"));
        Assert.assertEquals("Login", loginPage.getTitle());
        loginPage.authenticate(props.getProperty("ui_user"), props.getProperty("ui_password"));
        groupUpdatePage = new GroupUpdatePage();
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                BasePage.takeScreenshot("screenshots/" + result.getName() + ".png");
                logger.error(result.getName() + " - FAILED");
            } catch (Exception e) {
                logger.info("Exception while taking screenshot " + e.getMessage());
            }
        } else {
            logger.info(result.getName() + " - PASSED");
        }
        BasePage.switchToDefaultContent();
    }

    @AfterSuite
    public void tearDownMethod() {
//        loginPage.logOut();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//            DataBase.disconnectDb();
        logger.info("\n*************************TEST SUITE COMPLETED*************************\n\n\n");
        BasePage.closeDriver();
    }
}

