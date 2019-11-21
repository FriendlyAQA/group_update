package com.friendly.aqa.pageobject;

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


public abstract class BasePage {
    static WebDriver driver;
    static Properties props;
    final static Logger LOGGER;
    static final String BROWSER;

    static {
        initProperties();
        LOGGER = Logger.getLogger(BasePage.class);
        initDriver();
        BROWSER = props.getProperty("browser");
    }

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

    private static void initDriver() {
        String browser;
        browser = props.getProperty("browser");
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", props.getProperty("chrome_driver_path"));
                driver = new ChromeDriver();
                LOGGER.info("Chrome driver is running");
                break;
            case "ie":
                System.setProperty("webdriver.ie.driver", props.getProperty("ie_driver_path"));
                driver = new InternetExplorerDriver();
                LOGGER.info("IE driver is running");
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", props.getProperty("edge_driver_path"));
                driver = new EdgeDriver();
                LOGGER.info("Edge driver is running");
                break;
            default:
            case "firefox":
                System.setProperty("webdriver.gecko.driver", props.getProperty("firefox_driver_path"));
                driver = new FirefoxDriver();
                LOGGER.info("Firefox driver is running");
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

    public static void switchToDefaultContent() {
        driver.switchTo().defaultContent();
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

    @FindBy(id = "frmDesktop")
    private WebElement frameDesktop;

    @FindBy(id = "frmButtons")
    private WebElement frameButtons;

    @FindBy(id = "imgLogout")
    private WebElement logOutButton;

    @FindBy(tagName = "table")
    private WebElement buttonTable;

    @FindBy(id = "menuCircularG")
    private WebElement spinningWheel;

    @FindBy(id = "spnAlert")
    protected WebElement alertWindow;

    @FindBy(id = "btnAlertOk_btn")
    protected WebElement okButtonAlertPopUp;

    public void logOut() {
        driver.switchTo().defaultContent();
        logOutButton.click();
    }

    public void scrollTo(WebElement element) {
        ((JavascriptExecutor) BasePage.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void waitForUpdate() {
        driver.switchTo().defaultContent();
        long start = System.currentTimeMillis();
        try {
            new FluentWait<>(driver).withMessage("Element was not found")
                    .withTimeout(Duration.ofSeconds(1))
                    .pollingEvery(Duration.ofMillis(10))
                    .ignoring(org.openqa.selenium.NoSuchElementException.class)
                    .until(ExpectedConditions.visibilityOf(spinningWheel));
//            System.out.println("wheel is visible " + (System.currentTimeMillis() - start));
        } catch (org.openqa.selenium.TimeoutException e) {
//            System.out.println("wheel not found" + (System.currentTimeMillis() - start));
        }
        new FluentWait<>(driver).withMessage("Element was not found")
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(100))
                .until(ExpectedConditions.invisibilityOf(spinningWheel));
//        System.out.println("wheel is hidden " + (System.currentTimeMillis() - start));
        switchToFrameDesktop();
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
        driver.switchTo().defaultContent();
        String out = alertWindow.getText();
        okButtonAlertPopUp.click();
        switchToFrameDesktop();
        return out;
    }

    public BasePage topMenu(TopMenu value) {
        waitForUpdate();
        driver.switchTo().defaultContent();
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
        switchToFrameButtons();
        int timeout = Integer.parseInt(props.getProperty("driver_implicitly_wait"));
//        WebElement btn = buttonTable.findElement(By.id(button.getId()));
        for (int i = 0; i < 3; i++) {
            try {
                new FluentWait<>(driver).withMessage("Button was not found")
                        .withTimeout(Duration.ofSeconds(timeout))
                        .pollingEvery(Duration.ofMillis(100))
                        .until(ExpectedConditions.elementToBeClickable(buttonTable.findElement(By.id(button.getId()))))
                        .click();
                switchToFrameDesktop();
                return;
            } catch (StaleElementReferenceException e) {
                LOGGER.info("Button click failed. Retrying..." + (i + 1) + "time(s)");
            }
        }
        throw new AssertionError("cannot click button!");
    }

    public boolean isInputActive(String id) {
        String attr = getAttributeById(id, "disabled");
        return attr == null || !attr.equals("true");
    }

    public boolean isButtonPresent(GlobalButtons button) {
        switchToFrameButtons();
        boolean out = driver.findElements(By.id(button.getId())).size() > 0;
        switchToFrameDesktop();
        return out;
    }

    public String getAttributeById(String id, String attr) {
        return driver.findElement(By.id(id)).getAttribute(attr);
    }

    public boolean isButtonActive(GlobalButtons button) {
        switchToFrameButtons();
        boolean out = driver.findElement(By.id(button.getId())).getAttribute("class").equals("button_default");
        switchToFrameDesktop();
        return out;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    void switchToFrameDesktop() {
        driver.switchTo().defaultContent().switchTo().frame(frameDesktop);
    }

    void switchToFrameButtons() {
        driver.switchTo().defaultContent().switchTo().frame(frameButtons);
    }

    public static void takeScreenshot(String pathname) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(pathname));
        System.out.println("Successfully captured a screenshot");
    }

    public static void closeDriver() {
        driver.close();
    }
}


