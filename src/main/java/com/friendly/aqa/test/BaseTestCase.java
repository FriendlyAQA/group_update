package com.friendly.aqa.test;

import com.friendly.aqa.gui.Controller;
import com.friendly.aqa.pageobject.*;
import com.friendly.aqa.utils.DataBaseConnector;
import org.apache.log4j.Logger;
import org.testng.*;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.ROOT;

public class BaseTestCase {
    private LoginPage loginPage;
    protected GroupUpdatePage groupUpdatePage;
    private long start = System.currentTimeMillis();
    protected String testName, targetTestName;
    static Properties props;
    static Logger logger;
    private static boolean isInterrupted;
    private Controller controller;

    static {
        props = BasePage.getProps();
        logger = Logger.getLogger(BaseTestCase.class);
    }

    @BeforeSuite
    public void init() {
        controller = Controller.controller;
        logger.info("\n*************************STARTING TEST SUITE**************************");
        DataBaseConnector.connectDb(props.getProperty("db_url"), props.getProperty("db_user"), props.getProperty("db_password"));
        BasePage.initDriver();
        System.out.println("TestNG thread:" + Thread.currentThread().getName());
        controller.testSuiteStarted();
        loginPage = new LoginPage();
        Assert.assertEquals("Login", loginPage.getTitle());
        loginPage.authenticate(props.getProperty("ui_user"), props.getProperty("ui_password"));
        groupUpdatePage = new GroupUpdatePage();
        testName = "";
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
                controller.testFailed(testName);
            } catch (Exception e) {
                logger.info("Exception while taking screenshot " + e.getMessage());
            }
        } else {
            logger.info(result.getName() + " - PASSED");
            controller.testPassed(testName);
        }
        BasePage.switchToFrame(ROOT);
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
        logger.info("\n*************************TEST SUITE COMPLETED*************************\n\n\n");
        BasePage.closeDriver();
        interruptTestRunning(false);
        controller.testSuiteStopped();
    }

    public static void main(String[] args) {
        TestNG testng = new TestNG();
        List<String> suites = new ArrayList<>();
        suites.add("testng.xml");
        testng.setTestSuites(suites);
        testng.run();
    }

    public static void interruptTestRunning(boolean interrupt) {
        isInterrupted = interrupt;
    }

    protected void setTargetTestName() {
        this.targetTestName = testName;
    }
}

