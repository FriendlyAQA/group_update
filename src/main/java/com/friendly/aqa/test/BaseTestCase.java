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
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;

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
    protected ReportsPage reportsPage;
    protected FileManagementPage fmPage;
    protected SettingsPage settingsPage;
    private final long start = System.currentTimeMillis();
    protected static String testName;
    static Properties props;
    static Logger logger;
    private static boolean isInterrupted;
    private static boolean stopExecutionForced;
    public static boolean suiteCompleted;
    private static Controller controller;

    static {
        controller = Controller.getController();
        props = BasePage.getProps();
        logger = Logger.getLogger(BaseTestCase.class);
        PropertyConfigurator.configure("resources/log4j.properties");   //for launch without Controller from Idea
        System.setProperty("video.folder", "video");
        System.setProperty("video.mode", "ALL");
    }

    @BeforeSuite
    public void init() {
        logger.warn("\n*****************************STARTING TEST SUITE******************************");
        DataBaseConnector.connectDb();
        BasePage.initDriver();
        if (controller != null) {
            controller.testSuiteStarted();
        }
    }

    @BeforeTest
    public void skipClassIfStopped() {
        if (isInterrupted) {
            if (!stopExecutionForced) {
                stopExecutionForced = true;
                new Thread(this::stopForced).start();
            }
            throw new SkipException("Test execution has been interrupted manually");
        }
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        if (isInterrupted) {
            throw new SkipException("Test execution has been interrupted manually");
        }
        testName = method.getName();
        loginPage = getLoginPage();
        dpPage = getDpPage();
        duPage = getDuPage();
        guPage = getGuPage();
        monPage = getMonPage();
        evPage = getEvPage();
        reportsPage = getReportsPage();
        fmPage = getFmPage();
        settingsPage = getSettingsPage();
        loginPage.authenticate();
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
        BasePage.flushCollections();
        List<WebElement> popupList = BasePage.getDriver().findElements(By.id("btnAlertOk_btn"));
        List<WebElement> popup2List = BasePage.getDriver().findElements(By.id("popup2"));
        List<WebElement> popup3List = BasePage.getDriver().findElements(By.id("tblPopupTitle"));
        WebElement okBtn = BasePage.findElement("btnOk_btn");
        BasePage.setImplicitlyWait(0);
        while (okBtn.isDisplayed()) {
            okBtn.click();
            getLoginPage().waitForUpdate();
        }
        while (popupList.size() > 0 && popupList.get(0).isDisplayed()) {
            popupList.get(0).click();
            getLoginPage().waitForUpdate();
        }
        if (popup2List.size() > 0 && popup2List.get(0).isDisplayed()) {
            getLoginPage().executeScript("PopupHide2('cancel');");
        }
        if (popup3List.size() > 0 && popup3List.get(0).isDisplayed()) {
            getLoginPage().executeScript("PopupHide('cancel');");
        }
        BasePage.switchToFrame(DESKTOP);
        List<WebElement> resetViewList = BasePage.getDriver().findElements(By.id("btnDefaultView_btn"));
        if (resetViewList.size() > 0 && resetViewList.get(0).isDisplayed()) {
            resetViewList.get(0).click();
            getLoginPage().waitForUpdate();
        }
        BasePage.setDefaultImplicitlyWait();
        BasePage.switchToFrame(ROOT);
//        if (isInterrupted) {
//            throw new SkipException("Test execution has been interrupted manually");
//        }
    }

    @AfterSuite
    public void tearDownMethod() {
        stopExecutionForced = false;
        DataBaseConnector.disconnectDb();
        DiscManager.stopReading();
        getLoginPage().logOut();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long millis = System.currentTimeMillis() - start;
        logger.warn("Total running time: " + String.format("%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        ));
        logger.warn("\n*****************************TEST SUITE COMPLETED*****************************\n");
        BasePage.closeDriver();
        isInterrupted = false;
        if (controller != null) {
            controller.testSuiteStopped();
        }
    }

    private void stopForced() {
        while (!suiteCompleted) {
            Thread.yield();
        }
        pause(1000);
        if (stopExecutionForced) {
            System.out.println("tearDownMethod forced");
            tearDownMethod();
        }
    }

    protected void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected String getRegexpFor(String parameterName) {
        String value = DataBaseConnector.getParameterValue(getSerial(), parameterName);
        int length = Math.min(value.length(), 3);
        return "^" + (value).substring(0, length) + ".*";
    }

    protected String getSerial() {
        return BasePage.getSerial();
    }

    protected String getPartialSerial() {
        String serial = BasePage.getSerial();
        int length = Math.min(3, serial.length());
        if (serial.contains("_")) {
            length = serial.indexOf('_');
        }
        if (serial.startsWith("FT001SN0000")) {
            length = 11;
        }
        return serial.substring(0, length);
    }

    protected String getPartialSerial(int symbols) {    //negative arg returns last symbols
        String serial = BasePage.getSerial();
        if (symbols < 0) {
            return serial.substring(serial.length() + symbols);
        }
        return serial.substring(0, symbols);
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

    public static void interruptTestRunning() {
        isInterrupted = true;
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

    private ReportsPage getReportsPage() {
        if (reportsPage == null) {
            reportsPage = new ReportsPage();
        }
        return reportsPage;
    }

    private FileManagementPage getFmPage() {
        if (fmPage == null) {
            fmPage = new FileManagementPage();
        }
        return fmPage;
    }

    private SettingsPage getSettingsPage() {
        if (settingsPage == null) {
            settingsPage = new SettingsPage();
        }
        return settingsPage;
    }

    public String deviceToString() {
        String[] device = DataBaseConnector.getDevice(getSerial());
        return device[0] + " " + device[1];
    }
}

