package test;

import com.friendly.aqa.database.DataBase;
import com.friendly.aqa.pageobject.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class BaseTestCase extends TestBase {

    @BeforeSuite
    public void init() {
        initProperties();
        logger.info("\n*************************STARTING TEST SUITE**************************");
        initDriver();
//        DataBase.connectDb(props.getProperty("db_url"), props.getProperty("db_user"), props.getProperty("db_password"));
        Assert.assertEquals("Login", loginPage().getTitle());
        loginPage().authenticate(props.getProperty("ui_user"), props.getProperty("ui_password"));
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                TakesScreenshot screenshot = (TakesScreenshot) driver;
                File src = screenshot.getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(src, new File("screenshots/" + result.getName() + ".png"));
                System.out.println("Successfully captured a screenshot");
                logger.error(result.getName() + " - FAILED");
            } catch (Exception e) {
                logger.info("Exception while taking screenshot " + e.getMessage());
            }
        } else {
            logger.info(result.getName() + " - PASSED");
        }
        driver.manage().timeouts().implicitlyWait(Long.parseLong(props.getProperty("driver_implicitly_wait")), TimeUnit.SECONDS);
        driver.switchTo().defaultContent();
    }

    @AfterSuite
    public void tearDownMethod() {
        if (driver != null) {
            loginPage().logOut();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
//            DataBase.disconnectDb();
            logger.info("\n*************************TEST SUITE COMPLETED*************************\n\n\n");
        }
    }

    private LoginPage loginPage() {
        return new LoginPage(driver);
    }

    protected GroupUpdatePage groupUpdatePage() {
        return new GroupUpdatePage(driver);
    }

    protected SystemPage systemPage() {
        return new SystemPage(driver);
    }
}

