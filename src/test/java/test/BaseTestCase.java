package test;

//import com.friendly.aqa.database.DataBase;

import com.friendly.aqa.pageobject.*;
import com.friendly.aqa.utils.DataBase;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;

public class BaseTestCase extends TestBase {
    private LoginPage loginPage;
    protected SystemPage systemPage;
    protected GroupUpdatePage groupUpdatePage;
    private long start = System.currentTimeMillis();
    protected String testName, targetTestName;

    @BeforeSuite
    public void init() {
        logger.info("\n*************************STARTING TEST SUITE**************************");
        loginPage = new LoginPage();
        systemPage = new SystemPage();
        DataBase.connectDb(props.getProperty("db_url"), props.getProperty("db_user"), props.getProperty("db_password"));
        Assert.assertEquals("Login", loginPage.getTitle());
        loginPage.authenticate(props.getProperty("ui_user"), props.getProperty("ui_password"));
        groupUpdatePage = new GroupUpdatePage();
        testName = "";
//        groupUpdatePage.deleteAll();
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        testName = method.getName();
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
        BasePage.switchToFrame(ROOT);
    }

    @AfterSuite
    public void tearDownMethod() {
        DataBase.disconnectDb();
        loginPage.logOut();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long millis = System.currentTimeMillis() - start;
        logger.info("Total running time: " + String.format("%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        ));
        logger.info("\n*************************TEST SUITE COMPLETED*************************\n\n\n");
        BasePage.closeDriver();
    }

    public static void main(String[] args) {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        List<String> suites = new ArrayList<>();
        suites.add("testng.xml");//path to xml..
        testng.setTestSuites(suites);
        testng.run();
    }

    protected void setTargetTestName() {
        this.targetTestName = testName;
    }
}

