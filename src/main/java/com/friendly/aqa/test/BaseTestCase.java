package com.friendly.aqa.test;

import com.friendly.aqa.gui.Controller;
import com.friendly.aqa.pageobject.*;
import com.friendly.aqa.utils.DataBaseConnector;
import com.friendly.aqa.utils.Table;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.*;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.ROOT;

public class BaseTestCase {
    private LoginPage loginPage;
    protected GroupUpdatePage guPage;
    private long start = System.currentTimeMillis();
    protected static String testName, targetTestName;
    static Properties props;
    static Logger logger;
    private static boolean isInterrupted;
    private Controller controller;

    static {
        props = BasePage.getProps();
        logger = Logger.getLogger(BaseTestCase.class);
        System.setProperty("video.folder", props.getProperty("video_folder"));
        System.setProperty("video.mode", "ALL");
    }

    @BeforeSuite
    public void init() {
        controller = Controller.getController();
        logger.info("\n****************************************STARTING TEST SUITE*****************************************");
        DataBaseConnector.connectDb();
        BasePage.initDriver();
        if (controller != null) {
            controller.testSuiteStarted();
        }
        loginPage = new LoginPage();
        Assert.assertEquals("Login", loginPage.getTitle());
        loginPage.authenticate(props.getProperty("ui_user"), props.getProperty("ui_password"));
        guPage = new GroupUpdatePage();
        testName = "";
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        testName = method.getName();
        if (guPage == null) {
            guPage = new GroupUpdatePage();
        }
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (controller == null) {
            controller = Controller.getController();
        }
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                BasePage.takeScreenshot("screenshots/" + result.getName() + ".png");
                logger.error(result.getName() + " - FAILED");
                if (controller != null) {
                    controller.testFailed(testName);
                }
            } catch (Exception e) {
                logger.info("Exception while taking screenshot " + e.getMessage());
            }
        } else {
            logger.info(result.getName() + " - PASSED");
            if (controller != null) {
                controller.testPassed(testName);
            }
        }
        BasePage.switchToFrame(ROOT);
        Table.flushResult();
        List<WebElement> list = BasePage.getDriver().findElements(By.id("btnAlertOk_btn"));
        if (list.size() > 0 && list.get(0).isDisplayed()) {
            list.get(0).click();
            logger.warn("Unexpected popup detected after test '" + testName + "'. Button 'OK' clicked.");
        }
        if (isInterrupted) {
            throw new SkipException("Test execution interrupted manually");
        }
    }

    @AfterSuite
    public void tearDownMethod() {
        DataBaseConnector.disconnectDb();
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
        logger.info("\n****************************************TEST SUITE COMPLETED****************************************\n");
        BasePage.closeDriver();
        interruptTestRunning(false);
        if (controller != null) {
            controller.testSuiteStopped();
        }
    }

    public static void interruptTestRunning(boolean interrupt) {
        isInterrupted = interrupt;
    }

    protected void setTargetTestName() {
        targetTestName = testName;
    }

    public static String getTestName() {
        return testName;
    }
}

