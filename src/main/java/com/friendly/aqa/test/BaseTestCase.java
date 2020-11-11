package com.friendly.aqa.test;

import com.friendly.aqa.gui.Controller;
import com.friendly.aqa.pageobject.*;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.DataBaseConnector;
import com.friendly.aqa.utils.DiscManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.DESKTOP;
import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.ROOT;

public abstract class BaseTestCase {
    private LoginPage loginPage;
    protected DeviceProfilePage dpPage;
    protected DeviceUpdatePage duPage;
    protected GroupUpdatePage guPage;
    protected MonitoringPage monPage;
    protected EventsPage evPage;
    private final long start = System.currentTimeMillis();
    protected static String testName;
    static Properties props;
    static Logger logger;
    private static boolean isInterrupted;
    private Controller controller;

    static {
        props = BasePage.getProps();
        logger = Logger.getLogger(BaseTestCase.class);
        PropertyConfigurator.configure("resources/log4j.properties");   //for launch without Controller from Idea
        System.setProperty("video.folder", "video");
        System.setProperty("video.mode", "ALL");
    }

    @BeforeSuite
    public void init() {
        controller = Controller.getController();
        logger.warn("\n****************************************STARTING TEST SUITE*****************************************");
        DataBaseConnector.connectDb();
        BasePage.initDriver();
        if (controller != null) {
            controller.testSuiteStarted();
        }
        loginPage = getLoginPage();
        Assert.assertEquals(loginPage.getTitle(), "Friendly One-IoT Management Console");
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
        duPage = getDuPage();
        guPage = getGuPage();
        monPage = getMonPage();
        evPage = getEvPage();
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
                logger.error(result.getThrowable()/*.getMessage()*/);    //!!????
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
        BasePage.closeNewWindow();
        BasePage.switchToFrame(ROOT);
//        Table.flushResults();
        BasePage.flushCollections();
        List<WebElement> popupList = BasePage.getDriver().findElements(By.id("btnAlertOk_btn"));
        List<WebElement> popup2List = BasePage.getDriver().findElements(By.id("popup2"));
        List<WebElement> popup3List = BasePage.getDriver().findElements(By.id("tblPopupTitle"));
        WebElement okBtn = BasePage.getDriver().findElement(By.id("btnOk_btn"));
        BasePage.setImplicitlyWait(0);
//        String warn = "Unexpected popup detected after test '" + testName + "'. The window has been closed.";
        while (okBtn.isDisplayed()) {
            okBtn.click();
//            logger.warn(warn);
            loginPage.waitForUpdate();
        }
        while (popupList.size() > 0 && popupList.get(0).isDisplayed()) {
            popupList.get(0).click();
//            logger.warn(warn);
            loginPage.waitForUpdate();
        }
        if (popup2List.size() > 0 && popup2List.get(0).isDisplayed()) {
            loginPage.executeScript("PopupHide2('cancel');");
//            logger.warn(warn);
        }
        if (popup3List.size() > 0 && popup3List.get(0).isDisplayed()) {
            loginPage.executeScript("PopupHide('cancel');");
//            logger.warn(warn);
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
        DiscManager.stopReading();
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
        logger.warn("\n****************************************TEST SUITE COMPLETED****************************************\n");
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

    public String getModelName() {
        return DataBaseConnector.getDevice(getSerial())[1];
    }

    public String getManufacturer() {
        return DataBaseConnector.getDevice(getSerial())[0];
    }

    protected static String now() {
        return CalendarUtil.getDbShiftedDate(0);
    }

    protected String getPartialSerial(int symbols) {    //negative arg returns last symbols
        String serial = BasePage.getSerial();
        if (symbols < 0) {
            return serial.substring(serial.length() + symbols);
        }
        return serial.substring(0, symbols);
    }

    protected int getAmountOfDevices() {
        return DataBaseConnector.getDeviceAmount(getSerial());
    }

    public static void interruptTestRunning(boolean interrupt) {
        isInterrupted = interrupt;
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

    private DeviceUpdatePage getDuPage() {
        if (duPage == null) {
            duPage = new DeviceUpdatePage();
        }
        return duPage;
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

    private EventsPage getEvPage() {
        if (evPage == null) {
            evPage = new EventsPage();
        }
        return evPage;
    }
}

