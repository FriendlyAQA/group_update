package com.friendly.aqa.pageobject;

import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.DataBaseConnector;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.BUTTONS;
import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.ROOT;


public abstract class BasePage {
    static WebDriver driver;
    static Properties props;
    public static final String BROWSER;
    private static Logger logger;
    static FrameSwitch frame;
    private static FrameSwitch previousFrame;
    static String[] device;

    static {
        initProperties();
        logger = Logger.getLogger(BasePage.class);
        BROWSER = props.getProperty("browser");
        frame = ROOT;
    }

//    public static void initDevice() {
//        device = DataBaseConnector.getDevice();
//    }

    BasePage() {
        PageFactory.initElements(driver, this);
    }

    private static void initProperties() {
        props = new Properties();
        try (InputStream input = new FileInputStream("resources/config.properties")) {
            props.load(input);
        } catch (IOException ex) {
            System.out.println("File 'config.properties' is not found!");
            System.exit(1);
        }
    }

    public static void initDriver() {
        String browser;
        browser = props.getProperty("browser");
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", props.getProperty("chrome_driver_path"));
                driver = new ChromeDriver();
                logger.info("Chrome driver is running");
                break;
            case "ie":
                System.setProperty("webdriver.ie.driver", props.getProperty("ie_driver_path"));
                driver = new InternetExplorerDriver();
                logger.info("IE driver is running");
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", props.getProperty("edge_driver_path"));
                driver = new EdgeDriver();
                logger.info("Edge driver is running");
                break;
            default:
            case "firefox":
                System.setProperty("webdriver.gecko.driver", props.getProperty("firefox_driver_path"));
                driver = new FirefoxDriver();
                logger.info("Firefox driver is running");
        }
        long implWait = Long.parseLong(props.getProperty("driver_implicitly_wait"));
        driver.manage().timeouts().implicitlyWait(implWait, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(props.getProperty("ui_url"));
    }

    public static void setImplicitlyWait(long seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public static void setDefaultImplicitlyWait() {
        setImplicitlyWait(Long.parseLong(props.getProperty("driver_implicitly_wait")));
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static Properties getProps() {
        return props;
    }

    abstract protected String getLeftMenuCssSelector();

    @FindBy(id = "tblTopMenu")
    private WebElement topMenuTable;

    @FindBy(id = "tblLeftMenu")
    private WebElement leftMenuTable;

    @FindBy(id = "imgLogout")
    private WebElement logOutButton;

    @FindBy(tagName = "table")
    private WebElement buttonTable;

    @FindBy(id = "menuCircularG")
    private static WebElement spinningWheel;

    @FindBy(id = "spnAlert")
    protected WebElement alertWindow;

    @FindBy(id = "btnAlertOk_btn")
    protected WebElement okButtonAlertPopUp;

    public void logOut() {
        switchToFrame(ROOT);
        waitForUpdate();
        logOutButton.click();
    }

    public void scrollTo(WebElement element) {
        ((JavascriptExecutor) BasePage.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void waitForUpdate() {
        switchToFrame(ROOT);
        String style;
        do {
            style = spinningWheel.getAttribute("style");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!style.contains("display: none;"));
        switchToPrevious();
    }

    void leftMenuClick(String value) {
        for (WebElement we : leftMenuTable.findElements(By.cssSelector(this.getLeftMenuCssSelector()))) {
            WebElement item = we.findElement(By.tagName("td"));
            if (item.getText().equals(value)) {
                item.click();
            }
        }
    }

    public String getAlertTextAndClickOk() {
        switchToFrame(ROOT);
        String out = alertWindow.getText();
        okButtonAlertPopUp.click();
        switchToPrevious();
        return out;
    }

    public BasePage topMenu(TopMenu value) {
        waitForUpdate();
        switchToFrame(ROOT);
        for (WebElement btn : topMenuTable.findElements(By.tagName("td"))) {
            if (btn.getText().equals(value.getItem())) {
                btn.click();
            }
        }
        return this;
    }

    public boolean isElementPresent(String id) {
        List<WebElement> list = driver.findElements(By.id(id));
        return list.size() == 1;
    }

    void clickGlobalButtons(GlobalButtons button) {
        waitForUpdate();
        switchToFrame(BUTTONS);
        int timeout = Integer.parseInt(props.getProperty("driver_implicitly_wait"));
        for (int i = 0; i < 3; i++) {
            try {
                new FluentWait<>(driver).withMessage("Button was not found")
                        .withTimeout(Duration.ofSeconds(timeout))
                        .pollingEvery(Duration.ofMillis(100))
                        .until(ExpectedConditions.elementToBeClickable(buttonTable.findElement(By.id(button.getId()))))
                        .click();
                switchToPrevious();
                return;
            } catch (StaleElementReferenceException e) {
                logger.info("Button click failed. Retrying..." + (i + 1) + "time(s)");
            }
        }
        throw new AssertionError("cannot click button!");
    }

    public boolean isInputActive(String id) {
        String attr = getAttributeById(id, "disabled");
        return attr == null || !attr.equals("true");
    }

    public boolean isButtonPresent(GlobalButtons button) {
        switchToFrame(BUTTONS);
        boolean out = driver.findElements(By.id(button.getId())).size() == 1;
        switchToPrevious();
        return out;
    }

    public String getAttributeById(String id, String attr) {
        return driver.findElement(By.id(id)).getAttribute(attr);
    }

    public static String getElementText(String id) {
        return driver.findElement(By.id(id)).getText();
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) BasePage.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public boolean isButtonActive(GlobalButtons button) {
        switchToFrame(BUTTONS);
        boolean out = driver.findElement(By.id(button.getId())).getAttribute("class").equals("button_default");
        switchToPrevious();
        return out;
    }

    public static String getConfigPrefix(){
        String testName = BaseTestCase.getTestName();
        if (testName.contains("tr069")) {
            return "tr069_";
        } else if (testName.contains("tr181")) {
            return "tr181_";
        } else if (testName.contains("lwm2m")) {
            return "lwm2m_";
        } else if (testName.contains("mqtt")) {
            return "mqtt_";
        } else {
            return "usp_";
        }
    }

    public static String getImportCpeFile() {
        return props.getProperty(getConfigPrefix() + "import_cpe");
    }

    public static String getImportGroupFile() {
        return props.getProperty(getConfigPrefix() + "import_group");
    }

    public static String getSerial() {
        return props.getProperty(getConfigPrefix() + "cpe_serial");
    }

    public static String getManufacturer() {
        return DataBaseConnector.getDevice(getSerial())[0];
    }

    public static String getModelName() {
        return DataBaseConnector.getDevice(getSerial())[1];
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public static void takeScreenshot(String pathname) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(pathname));
        System.out.println("Successfully captured a screenshot");
    }

    public static void closeDriver() {
        driver.quit();
    }

    public static void switchToFrame(FrameSwitch frame) {
        BasePage.previousFrame = BasePage.frame;
        if (BasePage.frame == frame) {
            return;
        }
        driver.switchTo().defaultContent();
        BasePage.frame = frame;
        if (frame == ROOT) {
            return;
        }
        WebElement frameEl = driver.findElement(By.id(frame.frameId));
        driver.switchTo().frame(frameEl);
    }

    public static void switchToPrevious() {
        switchToFrame(previousFrame);
    }


    public enum FrameSwitch {
        ROOT(null), DESKTOP("frmDesktop"), BUTTONS("frmButtons"),
        CONDITIONS("frmPopup2"), USER_INFO("frmPopup");

        FrameSwitch(String frameId) {
            this.frameId = frameId;
        }

        String frameId;
    }
}


