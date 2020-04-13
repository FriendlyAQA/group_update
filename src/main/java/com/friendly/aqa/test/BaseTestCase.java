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

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.DESKTOP;
import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.ROOT;

public abstract class BaseTestCase {
    private LoginPage loginPage;
    protected DeviceProfilePage dpPage;
    protected GroupUpdatePage guPage;
    protected MonitoringPage monPage;
    private long start = System.currentTimeMillis();
    protected static String testName, targetTestName;
    static Properties props;
    static Logger logger;
    private static boolean isInterrupted;
    private Controller controller;

    static {
        props = BasePage.getProps();
        logger = Logger.getLogger(BaseTestCase.class);
        System.setProperty("video.folder", "video");
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
        loginPage = getLoginPage();
        Assert.assertEquals("Login", loginPage.getTitle());
        loginPage.authenticate(props.getProperty("ui_user"), props.getProperty("ui_password"));
        guPage = getGuPage();
        monPage = getMonPage();
        testName = "";
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        testName = method.getName();
        loginPage = getLoginPage();
        dpPage = getDpPage();
        guPage = getGuPage();
        monPage = getMonPage();
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
        Table.flushResults();
        BasePage.flushResults();
        List<WebElement> popupList = BasePage.getDriver().findElements(By.id("btnAlertOk_btn"));
        List<WebElement> popup2List = BasePage.getDriver().findElements(By.id("popup2"));
        List<WebElement> popup3List = BasePage.getDriver().findElements(By.id("tblPopupTitle"));
        WebElement okBtn = BasePage.getDriver().findElement(By.id("btnOk_btn"));
        BasePage.setImplicitlyWait(0);
        String warn = "Unexpected popup detected after test '" + testName + "'. Button 'OK' clicked.";
        while (okBtn.isDisplayed()) {
            okBtn.click();
            logger.warn(warn);
            loginPage.waitForUpdate();
        }
        while (popupList.size() > 0 && popupList.get(0).isDisplayed()) {
            popupList.get(0).click();
            logger.warn(warn);
            loginPage.waitForUpdate();
        }
        if (popup2List.size() > 0 && popup2List.get(0).isDisplayed()) {
            loginPage.executeScript("PopupHide2('cancel');");
            logger.warn(warn);
        }
        if (popup3List.size() > 0 && popup3List.get(0).isDisplayed()) {
            loginPage.executeScript("PopupHide('cancel');");
            logger.warn(warn);
        }
        BasePage.switchToFrame(DESKTOP);
        List<WebElement> resetViewList = BasePage.getDriver().findElements(By.id("btnDefaultView_btn"));
        if (resetViewList.size() > 0 && resetViewList.get(0).isDisplayed()) {
            resetViewList.get(0).click();
            loginPage.waitForUpdate();
        }
        BasePage.setDefaultImplicitlyWait();
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
        logger.info("\n****************************************TEST SUITE COMPLETED****************************************\n");
        BasePage.closeDriver();
        interruptTestRunning(false);
        if (controller != null) {
            controller.testSuiteStopped();
        }
    }

    protected void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected String getSerial() {
        return BasePage.getSerial();
    }

    protected int getDeviceAmount() {
        return DataBaseConnector.getDeviceAmount(getSerial());
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

    private LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }

    private DeviceProfilePage getDpPage() {
        if (dpPage == null) {
            dpPage = new DeviceProfilePage();
        }
        return dpPage;
    }

    private GroupUpdatePage getGuPage() {
        if (guPage == null) {
            guPage = new GroupUpdatePage();
        }
        return guPage;
    }

    private MonitoringPage getMonPage() {
        if (monPage == null) {
            monPage = new MonitoringPage();
        }
        return monPage;
    }
}

