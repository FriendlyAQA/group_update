package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.*;
import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.DataBaseConnector;
import com.friendly.aqa.utils.Timer;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Collator;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.friendly.aqa.entities.GlobalButtons.REFRESH;
import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;


public abstract class BasePage {
    static WebDriver driver;
    static Properties props;
    public static final String BROWSER;
    public static String mainWindow;
    private static final Logger logger;
    public static FrameSwitch frame;
    private static FrameSwitch previousFrame;
    protected Table savedTable;
    protected static Set<String> parameterSet;
    protected static Map<String, String> parameterMap;
    protected static Map<String, Event> eventMap;
    protected static Map<String, ParametersMonitor> parametersMonitorMap;
    protected String selectedName;
    public static final long IMPLICITLY_WAIT;

    static {
        initProperties();
        logger = Logger.getLogger(BasePage.class);
        BROWSER = props.getProperty("browser");
        IMPLICITLY_WAIT = Long.parseLong(props.getProperty("driver_implicitly_wait"));
        frame = ROOT;
    }

    BasePage() {
        PageFactory.initElements(driver, this);
        selectedName = "";
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
//        long implWait = Long.parseLong(props.getProperty("driver_implicitly_wait"));
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get(props.getProperty("ui_url"));
        mainWindow = driver.getWindowHandle();
    }

    public static void setImplicitlyWait(long seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public static void setDefaultImplicitlyWait() {
        setImplicitlyWait(IMPLICITLY_WAIT);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static Properties getProps() {
        return props;
    }

    abstract protected String getLeftMenuCssSelector();

    public abstract Table getMainTable();

    @FindBy(id = "tblTopMenu")
    private WebElement topMenuTable;

    @FindBy(id = "tblLeftMenu")
    private WebElement leftMenuTable;

    @FindBy(id = "imgLogout")
    private WebElement logOutButton;

    @FindBy(tagName = "table")
    private WebElement buttonTable;

    @FindBy(id = "txtName")
    protected WebElement nameField;

    @FindBy(id = "menuCircularG")
    private static WebElement spinningWheel;

    @FindBy(id = "spnAlert")
    protected WebElement alertWindow;

    @FindBy(id = "btnAddFilter_btn")
    protected WebElement addFilterButton;

    @FindBy(id = "btnAddSubFilter_btn")
    protected WebElement addSubilterButton;

    @FindBy(id = "btnDelFilter_btn")
    protected WebElement deleteFilterButton;

    @FindBy(id = "btnUp_btn")
    protected WebElement upButton;

    @FindBy(id = "btnDown_btn")
    protected WebElement downButton;

    @FindBy(id = "btnTop_btn")
    protected WebElement topButton;

    @FindBy(id = "btnBottom_btn")
    protected WebElement bottomButton;


    @FindBy(id = "ddlColumns")
    protected WebElement selectColumnFilter;

    @FindBy(id = "ddlCondition")
    protected WebElement selectCompare;

    @FindBy(id = "btnOk_btn")
    protected WebElement okButtonPopUp;

    @FindBy(id = "btnCancel_btn")
    protected WebElement cancelButtonPopUp;

    @FindBy(id = "btnAlertOk_btn")
    protected WebElement okButtonAlertPopUp;

    @FindBy(id = "ddlManufacturer")
    protected WebElement manufacturerComboBox;

    @FindBy(id = "ddlModelName")
    protected WebElement modelComboBox;

    @FindBy(id = "ddlView")
    protected WebElement filterViewComboBox;

    @FindBy(id = "ddlManuf")
    protected WebElement filterManufacturerComboBox;

    @FindBy(id = "ddlModel")
    protected WebElement filterModelNameComboBox;

    @FindBy(id = "btnEditView_btn")
    protected WebElement editButton;

    @FindBy(id = "btnNewView_btn")
    protected WebElement newViewButton;

    @FindBy(id = "ddlSend")
    protected WebElement sendToComboBox;

    @FindBy(id = "lrbImmediately")
    protected WebElement immediatelyRadioButton;

    @FindBy(id = "lrbWaitScheduled")
    protected WebElement scheduledToRadioButton;

    @FindBy(id = "rdAnd")
    protected WebElement andRadioButton;

    @FindBy(id = "frmImportFromFile")
    protected WebElement importFrame;

    @FindBy(id = "fuSerials")
    protected WebElement importDeviceField;

    @FindBy(id = "ddlPageSizes")
    protected WebElement itemsOnPageComboBox;

    @FindBy(id = "btnDefaultView_btn")
    protected WebElement resetViewButton;

    @FindBy(id = "UcFirmware1_ddlFileType")
    protected WebElement selectDownloadFileTypeComboBox;

    @FindBy(id = "ddlFileType")
    protected WebElement selectUploadFileTypeComboBox;

    @FindBy(id = "UcFirmware1_rdUrl")
    protected WebElement manualRadioButton;

    @FindBy(id = "UcFirmware1_rdTarget")
    protected WebElement fromListRadioButton;

    @FindBy(id = "UcFirmware1_tbUrl")
    protected WebElement urlField;

    @FindBy(id = "tbUrl")
    protected WebElement uploadUrlField;

    @FindBy(id = "UcFirmware1_tbLogin")
    protected WebElement userNameField;

    @FindBy(id = "UcFirmware1_tbPass")
    protected WebElement passwordField;

    @FindBy(id = "ddlTasks")
    protected WebElement selectTask;

    @FindBy(id = "btnAddTask_btn")
    protected WebElement addTaskButton;

    @FindBy(id = "rdReboot")
    protected WebElement rebootRadioButton;

    @FindBy(id = "rdFactoryReset")
    protected WebElement factoryResetRadioButton;

    @FindBy(id = "rdCPEReprovision")
    protected WebElement reprovisionRadioButton;

    @FindBy(id = "rdCustomRPC")
    protected WebElement customRpcRadioButton;

    @FindBy(id = "ddlMethods")
    protected WebElement selectMethodComboBox;

    @FindBy(id = "rdUrlUpload")
    protected WebElement manuallyUrlRadioButton;

    @FindBy(id = "tbLogin")
    protected WebElement userNameUploadField;

    @FindBy(id = "ddlDiagnostics")
    protected WebElement diagnosticTypeComboBox;

    @FindBy(id = "txtHost")
    protected WebElement inputHostField;

    @FindBy(id = "txtNumberRepetitions")
    protected WebElement numOfRepetitionsField;

    @FindBy(id = "txtDnsServer")
    protected WebElement inputDnsField;

    @FindBy(id = "UcFirmware1_ddlFileName")
    protected WebElement fileNameComboBox;

    @FindBy(id = "rdReset Min and Max Measured Values")
    protected WebElement resetMinMaxValues;

    @FindBy(id = "rdReset Cumulative energy")
    protected WebElement resetCumulativeEnergy;

    @FindBy(id = "rdResetErrors")
    protected WebElement resetErrors;

    @FindBy(id = "rdDisable")
    protected WebElement disableRadiobutton;

    @FindBy(id = "rdRegistrationUpdateTrigger")
    protected WebElement radioRegistrationUpdateTrigger;

    @FindBy(id = "rdStartOrReset")
    protected WebElement radioStartOrReset;

    public void logOut() {
        switchToFrame(ROOT);
        waitForUpdate();
        logOutButton.click();
    }

    public BasePage manuallyUrlRadioButton() {
        waitForUpdate();
        manuallyUrlRadioButton.click();
        return this;
    }

    public BasePage inputDnsField(String text) {
        inputDnsField.clear();
        inputDnsField.sendKeys(text);
        return this;
    }

    public BasePage inputNumOfRepetitions(String text) {
        numOfRepetitionsField.clear();
        numOfRepetitionsField.sendKeys(text);
        return this;
    }

    public BasePage inputHost(String text) {
        inputHostField.clear();
        inputHostField.sendKeys(text);
        return this;
    }

    public BasePage resetView() {
        resetViewButton.click();
        waitForUpdate();
        return this;
    }

    public BasePage newViewButton() {
        newViewButton.click();
        return this;
    }

    public BasePage selectDiagnostic(String value) {
        try {
            selectComboBox(diagnosticTypeComboBox, value);
        } catch (NoSuchElementException e) {
            String warn = value + " type is not supported by current device!";
            logger.warn('(' + BaseTestCase.getTestName() + ')' + '(' + BaseTestCase.getTestName() + ')' + warn);
            throw new AssertionError(warn);
        }
        return this;
    }

    public BasePage selectFilterManufacturer(String value) {
        selectComboBox(filterManufacturerComboBox, value);
        waitForUpdate();
        return this;
    }

    public List<WebElement> findElements(String byId) {
        return driver.findElements(By.id(byId));
    }

    public static WebElement findElement(String byId) {
        return driver.findElement(By.id(byId));
    }

    public BasePage rebootRadioButton() {
        waitForUpdate();
        rebootRadioButton.click();
        return this;
    }

    public BasePage factoryResetRadioButton() {
        waitForUpdate();
        factoryResetRadioButton.click();
        return this;
    }

    public BasePage reprovisionRadioButton() {
        waitForUpdate();
        reprovisionRadioButton.click();
        return this;
    }

    public BasePage resetErrors() {//
        waitForUpdate();
        resetErrors.click();
        return this;
    }

    public BasePage disableRadiobutton() {//
        waitForUpdate();
        disableRadiobutton.click();
        return this;
    }

    public BasePage editButton() {
        editButton.click();
        waitForUpdate();
        return this;
    }

    public BasePage radioRegistrationUpdateTrigger() {//
        waitForUpdate();
        radioRegistrationUpdateTrigger.click();
        return this;
    }

    public BasePage radioStartOrReset() {//
        waitForUpdate();
        radioStartOrReset.click();
        return this;
    }

    public BasePage fillUploadUrl() {
        uploadUrlField.clear();
        uploadUrlField.sendKeys(props.getProperty("upload_url"));
        userNameUploadField.click();
        return this;
    }

    public BasePage customRpcRadioButton() {
        waitForUpdate();
        customRpcRadioButton.click();
        return this;
    }

    public BasePage selectMethod(String value) {
        selectComboBox(selectMethodComboBox, value);
        return this;
    }

    public BasePage selectDownloadFileType(String type) {
        selectComboBox(new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(100))
                .until(ExpectedConditions.elementToBeClickable(selectDownloadFileTypeComboBox)), type);
        return this;
    }

    public BasePage selectUploadFileType(String type) {
        selectComboBox(new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(100))
                .until(ExpectedConditions.elementToBeClickable(selectUploadFileTypeComboBox)), type);
        return this;
    }

    public BasePage manualRadioButton() {
        manualRadioButton.click();
        waitForUpdate();
        return this;
    }

    public BasePage selectFromListRadioButton() {
        fromListRadioButton.click();
        return this;
    }

    public BasePage fillUrl() {
        urlField.sendKeys(getProps().getProperty("ftp_config_file_url"));
        return this;
    }

    public BasePage fillUsername() {
        userNameField.sendKeys(BasePage.getProps().getProperty("ftp_user"));
        return this;
    }

    public BasePage fillPassword() {
        passwordField.sendKeys(BasePage.getProps().getProperty("ftp_password"));
        return this;
    }

    public void scrollTo(WebElement element) {
        ((JavascriptExecutor) BasePage.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public BasePage selectManufacturer(String manufacturer) {
        selectComboBox(manufacturerComboBox, manufacturer);
        waitForUpdate();
        return this;
    }

    public BasePage selectModel(String modelName) {
        List<String> list = getOptionList(modelComboBox);
        if (list.size() == 1) {
            selectComboBox(modelComboBox, list.get(0));
        } else {
            selectComboBox(modelComboBox, modelName);
        }
        waitForUpdate();
        return this;
    }

    public BasePage selectModel() {
        selectComboBox(modelComboBox, getModelName());
        return this;
    }

    public BasePage selectFilterModelName(String value) {
        selectComboBox(filterModelNameComboBox, value);
        return this;
    }

    public BasePage selectSendTo(String value) {
        selectComboBox(sendToComboBox, value);
        waitForUpdate();
        return this;
    }

    public BasePage selectSendTo() {
        return selectSendTo("All");
    }

    public BasePage addDeviceWithoutTemplate() {
        String[] device = props.getProperty("device_without_template").split(":");
        selectManufacturer(device[0]);
        selectModel(device[1]);
        return this;
    }

    public BasePage addFilter() {
        addFilterButton.click();
        return this;
    }

    public BasePage addSubFilter() {
        addSubilterButton.click();
        return this;
    }

    public BasePage immediately() {
        immediatelyRadioButton.click();
        return this;
    }

    public BasePage scheduledToRadioButton() {
        scheduledToRadioButton.click();
        return this;
    }

    public BasePage andRadioButton() {
        andRadioButton.click();
        return this;
    }

    public BasePage filterRecordsCheckbox() {
        Table table = new Table("tblTree");
        for (int i = 0; i < table.getTableSize()[0]; i++) {
            table.getInput(i, 0).click();
        }
//        findElement("tblTree").findElement(By.tagName("input")).click();
        return this;
    }

    protected void selectComboBox(WebElement comboBox, String value) {
        waitForUpdate();
        List<WebElement> options = comboBox.findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (option.getText().toLowerCase().equals(value.toLowerCase())) {
                new Select(comboBox).selectByValue(option.getAttribute("value"));
                return;
            }
        }
        new Select(comboBox).selectByValue(value);
    }

    public boolean isCheckboxSelected(WebElement checkbox) {
        if (!checkbox.getAttribute("type").equals("checkbox")) {
            throw new AssertionError(" The WebElement #'" + checkbox.getAttribute("id") + "' is not a checkbox!");
        }
        return checkbox.isSelected();
    }

    public BasePage assertCheckboxIsSelected(String id, boolean expectedState) {
        assertTrue(isCheckboxSelected(findElement(id)) == expectedState, "The checkbox #" + id + " has unexpected state!");
        return this;
    }

    public BasePage assertCheckboxIsSelected(WebElement checkbox, boolean expectedState) {
        assertTrue(isCheckboxSelected(checkbox) == expectedState, "The checkbox #" + checkbox.getAttribute("id") + " has unexpected state!");
        return this;
    }

    public BasePage assertCheckboxesAreSelected(String tableId, boolean expectedState, int column, int... rows) {
        Table table = new Table(tableId);
        for (int row : rows) {
            if (row < 0) {
                for (int i = Math.abs(row); i < table.getTableSize()[0]; i++) {
                    assertTrue(isCheckboxSelected(table.getInput(i, column)) == expectedState,
                            "The checkbox in table cell " + i + ":" + column + " has unexpected state!");
                }
                break;
            }
            assertTrue(isCheckboxSelected(table.getInput(row, column)) == expectedState,
                    "The checkbox in table cell " + row + ":" + column + " has unexpected state!");
        }
        return this;
    }

    public BasePage deleteFilter() {
        deleteFilterButton.click();
        return this;
    }

    public BasePage getParameter(int row, int column) {
        Table table = new Table("tblParamsValue");
        if (parameterMap == null) {
            parameterMap = new HashMap<>();
        }
        String hint = table.getHint(row);
        String values;
        if (column < 1) {
            values = "values,names,attributes";
            for (int i = 1; i < table.getRowLength(row); i++) {
                table.clickOn(row, i, 0);
            }
        } else {
            String[] valuesArr = {"", "names", "values", "attributes"};
            values = valuesArr[column];
            table.clickOn(row, column, 0);
        }
        parameterMap.put(hint, values);
        return this;
    }

    public BasePage assertMainPageIsDisplayed() {
        try {
            boolean viewComboBox = filterViewComboBox.isDisplayed() && filterViewComboBox.isEnabled();
            boolean manufacturerComboBox = filterManufacturerComboBox.isDisplayed() && filterManufacturerComboBox.isEnabled();
            boolean modelNameComboBox = filterModelNameComboBox.isDisplayed() && filterModelNameComboBox.isEnabled();
            boolean editViewBtn = editButton.isDisplayed() && editButton.isEnabled();
            boolean newViewBtn = newViewButton.isDisplayed() && newViewButton.isEnabled();
            boolean resetViewBtn = resetViewButton.isDisplayed() && resetViewButton.isEnabled();
            if (viewComboBox && manufacturerComboBox && modelNameComboBox && editViewBtn && newViewBtn && resetViewBtn) {
                return this;
            }
        } catch (NoSuchElementException e) {
            logger.warn('(' + BaseTestCase.getTestName() + ") - " + e.getMessage());
        }
        throw new AssertionError("One or more elements not found on main page");
    }

    public BasePage assertButtonIsEnabled(boolean expectedActive, String id) {
        if (isButtonActive(id) == expectedActive) {
            return this;
        }
        throw new AssertionError("Button id='" + id + "' has unexpected state (" +
                (expectedActive ? "disabled" : "enabled") + ")");
    }

    public BasePage selectImportDevicesFile() {
        switchToFrame(DESKTOP);
        driver.switchTo().frame(importFrame);
        String inputText = new File("import/" + getProtocolPrefix() + "_import_cpe.xml").getAbsolutePath();
        importDeviceField.sendKeys(inputText);
        driver.switchTo().parentFrame();
        return this;
    }

    public BasePage selectView(String value) {
        selectComboBox(filterViewComboBox, value);
        waitForUpdate();
        return this;
    }

    public boolean isOptionPresent(String comboBoxId, String text) {
        waitForUpdate();
        WebElement comboBox = findElement(comboBoxId);
        List<WebElement> options = comboBox.findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (option.getText().toLowerCase().equals(text.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean isButtonActive(String id) {
        return !findElement(id).getAttribute("class").equals("button_disabled");
    }

    protected void waitUntilElementIsDisplayed(String id) {
        WebElement element = findElement(id);
        new FluentWait<>(driver)
                .withMessage("Element '#" + id + "' not found")
                .withTimeout(Duration.ofSeconds(IMPLICITLY_WAIT))
                .pollingEvery(Duration.ofMillis(100))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public BasePage fillName(String name) {
        nameField.sendKeys(name);
        return this;
    }

    public BasePage fillName() {
        nameField.sendKeys(BaseTestCase.getTestName());
        return this;
    }

    public BasePage globalButtons(IGlobalButtons button) {
        clickGlobalButtons(button);
        return this;
    }

    public BasePage waitForStatus(String status, String testName, int timeout) {
        Timer timer = new Timer(timeout * 1000);
        while (!(getMainTable()).getCellText(testName, "State").equals(status)) {
            globalButtons(REFRESH);
            if (timer.timeout()) {
                throw new AssertionError("Timed out while waiting for status " + status);
            }
        }
        return this;
    }

    public BasePage waitForStatus(String status, int timeout) {
        return waitForStatus(status, BaseTestCase.getTestName(), timeout);
    }

    public void executeScript(String script) {
        ((JavascriptExecutor) getDriver()).executeScript(script);
    }

    public BasePage inputText(String id, String text) {
        findElement(id).sendKeys(text);
        return this;
    }

    public BasePage selectCompare(String option) {
        selectComboBox(selectCompare, option);
        return this;
    }

    public BasePage selectDate(String date) {
        executeScript("CalendarPopup_FindCalendar('calFilterDate').SelectDate('" + date + "')");
        return this;
    }

    public BasePage selectShiftedDate(String id, int value) {
        executeScript("CalendarPopup_FindCalendar('" + id + "').SelectDate('" + CalendarUtil.getShiftedDate(value) + "')");
        return this;
    }

    public Table getTable(String id, FrameSwitch frame) {
        waitForUpdate();
        if (frame != null) {
            switchToFrame(frame);
        }
        Table table = null;
        Timer timer = new Timer(30000);
        while (!timer.timeout()) {
            try {
                table = new Table(id);
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("StaleElementReferenceException handled. Re-parse table...");
            }
        }
        if (table == null) {
            throw new AssertionError("Web table parsing process failed! (WebElement id = '" + id + "'");
        }
        return savedTable = table;
    }

    public Table getTable(String id) {
        return getTable(id, null);
    }

    public Table getTable(String id, int expectedRowsNumber, boolean checkAsymmetry) {
        Table table = new Table(id);
        Timer timer = new Timer();
        while (!timer.timeout()) {
            if (table.getTableSize()[0] == expectedRowsNumber && (!checkAsymmetry || !table.hasAsymmetry())) {
                return table;
            }
            table = new Table(id);
        }
        throw new AssertionError("Table rows number != " + expectedRowsNumber);
    }

    public Table getTabTable() {
        return getTable("tabsSettings_tblTabs", null);
    }

    public BasePage selectTab(String tab) {
        return selectTab(tab, getTabTable());
    }

    public BasePage selectTab(String tab, Table tabTable) {
        if (!tab.equals("Management") && !tab.equals("Device")) {
            String start;
            try {
                start = new Table("tblTree").getCellText(0, 0);
            } catch (StaleElementReferenceException e) {
                start = new Table("tblTree").getCellText(0, 0);
            }
            tabTable.clickOn(tab);
            long from = System.currentTimeMillis();
            do {
                waitForUpdate();
                try {
                    if (!new Table("tblTree").getCellText(0, 0).equals(start)) {
                        break;
                    }
                } catch (StaleElementReferenceException e) {
                    System.out.println("DPP:209 - StaleElementReferenceException handled");
                }
            } while (System.currentTimeMillis() - from < 10000);
        }
        return this;
    }

    public BasePage waitForUpdate() {   //TODO rework with ExpectedCondition
        long start = System.currentTimeMillis();
        switchToFrame(ROOT);
        String style;
        do {
            style = spinningWheel.getAttribute("style");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!style.contains("display: none;") || System.currentTimeMillis() > start + 30000);
        switchToPreviousFrame();
        return this;
    }

    void leftMenuClick(String value) {
        for (WebElement we : leftMenuTable.findElements(By.cssSelector(this.getLeftMenuCssSelector()))) {
            WebElement item = we.findElement(By.tagName("td"));
            if (item.getText().equals(value)) {
                item.click();
            }
        }
    }

    public BasePage okButtonPopUp() {
        switchToFrame(ROOT);
        waitForUpdate();
        while (okButtonPopUp.isDisplayed()) {
            okButtonPopUp.click();
            waitForUpdate();
        }
        while (okButtonAlertPopUp.isDisplayed()) {
            okButtonAlertPopUp.click();
            waitForUpdate();
        }
        switchToFrame(DESKTOP);
        return this;
    }

    public BasePage cancelButtonPopUp() {
        switchToFrame(ROOT);
        waitForUpdate();
        while (cancelButtonPopUp.isDisplayed()) {
            cancelButtonPopUp.click();
            waitForUpdate();
        }
        switchToFrame(DESKTOP);
        return this;
    }

    public BasePage assertEqualsAlertMessage(String expectedMessage) {
        switchToFrame(ROOT);
        String out = alertWindow.getText();
        okButtonAlertPopUp.click();
        switchToPreviousFrame();
        Assert.assertEquals(out, expectedMessage);
        return this;
    }

    public BasePage topMenu(TopMenu value) {
        waitForUpdate();
        switchToFrame(ROOT);
        for (WebElement btn : topMenuTable.findElements(By.tagName("td"))) {
            if (btn.getText().equals(value.getItem())) {
                btn.click();
            }
        }
        switchToFrame(DESKTOP);
        waitForUpdate();
        return this;
    }

    public BasePage upButton() {
        upButton.click();
        return this;
    }

    public BasePage downButton() {
        downButton.click();
        return this;
    }

    public BasePage topButton() {
        topButton.click();
        return this;
    }

    public BasePage bottomButton() {
        bottomButton.click();
        return this;
    }

    public BasePage assertElementIsPresent(String id) {
        waitForUpdate();
        List<WebElement> list = findElements(id);
        if (list.size() == 0) {
            String warn = "Element with id='" + id + "' not found on current page";
            logger.warn('(' + BaseTestCase.getTestName() + ')' + '(' + BaseTestCase.getTestName() + ')' + warn);
            throw new AssertionError(warn);
        }
        return this;
    }

    public BasePage assertTableColumnNumberIs(int number, String tableId) {
        int actual = getTable(tableId).getTableSize()[1];
        if (actual != number) {
            throw new AssertionError("Wrong number of table column! Expected: " + number + ", but found :" + actual);
        }
        return this;
    }

    public boolean elementIsAbsent(String id) {
        waitForUpdate();
        setImplicitlyWait(0);
        boolean out = findElements(id).size() == 0;
        setDefaultImplicitlyWait();
        return out;
    }

    public boolean elementIsPresent(String id) {
        return !elementIsAbsent(id);
    }

    public BasePage assertPresenceOfValue(String tableId, int column, String value) {
        getTable(tableId).assertPresenceOfValue(column, value);
        return this;
    }

    public BasePage assertAbsenceOfValue(String tableId, String value) {
        getTable(tableId).assertAbsenceOfValue(value);
        return this;
    }

    public void assertPresenceOfParameter(String value) {
        getTable("tblTasks")/*.print()*/.assertPresenceOfParameter(value);
    }

    public BasePage assertPresenceOfOptions(String comboBoxId, String... options) {
        WebElement comboBox = findElement(comboBoxId);
        List<WebElement> optList = comboBox.findElements(By.tagName("option"));
        Set<String> optSet = new HashSet<>(optList.size());
        optList.forEach(o -> optSet.add(o.getText()));
        Set<String> valueSet = new HashSet<>(Arrays.asList(options));
        valueSet.removeAll(optSet);
        if (!valueSet.isEmpty()) {
            throw new AssertionError("Options " + valueSet + " not found inside dropdown #" + comboBoxId + "'");
        }
        return this;
    }

    public BasePage assertAbsenceOfOptions(String comboBoxId, String... options) {
        Arrays.asList(options).forEach(opt -> {
            List<String> actualOptList = getOptionList(findElement(comboBoxId));
            if (actualOptList.contains(opt)) {
                throw new AssertionError("Unexpected option (" + opt + ") found inside dropdown #" + comboBoxId);
            }
        });
        return this;
    }

    public BasePage selectColumnFilter(String option) {
        selectComboBox(selectColumnFilter, option);
        return this;
    }

    public void assertCellStartsWith(String tabId, int row, int column, String expectedText) {
        getTable(tabId).assertStartsWith(row, column, expectedText);
    }

    public void assertCellEndsWith(String tabId, int row, int column, String expectedText) {
        getTable(tabId).assertEndsWith(row, column, expectedText);
    }

    public void assertCellMatches(String tabId, int row, int column, String regex) {
        String s = getTable(tabId).getCellText(row, column);
        if (!s.matches(regex)) {
            throw new AssertionError("Table cell text doesn't match regex!");
        }
    }

    public BasePage clickOn(String id) {
        waitForUpdate();
        findElement(id).click();
        return this;
    }

//    public boolean isElementPresent(String id) {
//        List<WebElement> list = driver.findElements(By.id(id));
//        return list.size() == 1;
//    }

    public List<String> getOptionList(WebElement comboBox) {
        List<String> out = new ArrayList<>();
        new Select(comboBox)
                .getOptions()
                .forEach(element -> out.add(element.getText()));
        return out;
    }

    public BasePage clickButton(WebElement button) {
        waitForUpdate();
        for (int i = 0; i < 3; i++) {
            try {
                new FluentWait<>(driver)
                        .withMessage("Button '" + button + "' not found/not active")
                        .withTimeout(Duration.ofSeconds(IMPLICITLY_WAIT))
                        .pollingEvery(Duration.ofMillis(100))
                        .until(ExpectedConditions.elementToBeClickable(button))
                        .click();
                waitForUpdate();
                return this;
            } catch (StaleElementReferenceException e) {
                logger.info("Button click failed. Retrying..." + (i + 1) + "time(s)");
            }
        }
        throw new AssertionError("cannot click button!");
    }

    void clickGlobalButtons(IGlobalButtons button) {
        waitForUpdate();
        switchToFrame(BUTTONS);
        for (int i = 0; i < 3; i++) {
            try {
                new FluentWait<>(driver)
                        .withMessage("Button " + button + " not found/not active")
                        .withTimeout(Duration.ofSeconds(IMPLICITLY_WAIT))
                        .pollingEvery(Duration.ofMillis(100))
                        .until(ExpectedConditions.elementToBeClickable(findElement(button.getId())))
                        .click();
                waitForUpdate();
                return;
            } catch (StaleElementReferenceException e) {
                logger.info("Button click failed. Retrying..." + (i + 1) + "time(s)");
            } finally {
                switchToFrame(DESKTOP);
            }
        }
        throw new AssertionError("cannot click button!");
    }

    public String getSelectedValue(String comboBoxId) {
        return getSelectedValue(findElement(comboBoxId));
    }

    public String getSelectedValue(WebElement comboBox) {
        List<WebElement> optList = comboBox.findElements(By.tagName("option"));
        for (WebElement el : optList) {
            if (el.getAttribute("selected") != null) {
                return el.getText();
            }
        }
        return null;
    }

    public String getGuExportLink(String groupName) {
        return props.getProperty("ui_url") + "/Update/Export.aspx?updateId=" + DataBaseConnector.getGroupUpdateId(groupName);
    }

    public Map<String, Event> readEvents() {
        Table table = new Table("tblEvents");
        setImplicitlyWait(0);
        Map<String, Event> map = new HashMap<>();
        for (int i = 1; i < table.getTableSize()[0]; i++) { //Very slow performance!!!
            String countOfEvents = getSelectedValue(table.getCellWebElement(i, 2).findElement(By.tagName("select")));
            if (countOfEvents.isEmpty()) {
                continue;
            }
            boolean onEachEvent = table.getInput(i, 1).isSelected();
            List<WebElement> selectList = table.getCellWebElement(i, 3).findElements(By.tagName("select"));
            String num = getSelectedValue(selectList.get(0));
            String units = getSelectedValue(selectList.get(1));
            String name = table.getCellText(i, 0);
            map.put(name, new Event(name, onEachEvent, countOfEvents, num + ":" + units));
//            if (table.getInput(i, 1).isSelected()) {
//                list.add(new Event(table.getCellText(i, 0), true, "1", "1:minutes"));
//                continue;
//            }
//            String countOfEvents = getSelectedValue(table.getCellWebElement(i, 2).findElement(By.tagName("select")));
//            if (!countOfEvents.isEmpty()) {
//                List<WebElement> selectList = table.getCellWebElement(i, 3).findElements(By.tagName("select"));
//                String num = getSelectedValue(selectList.get(0));
//                String units = getSelectedValue(selectList.get(1));
//                list.add(new Event(table.getCellText(i, 0), false, countOfEvents, num + ":" + units));
//            }
        }
        setDefaultImplicitlyWait();
        return map;
    }

    public BasePage saveTable(String tableId) {
        savedTable = new Table(tableId);
        return this;
    }

    public BasePage compareTable(String tableId) {
        Table table = new Table(tableId);
        if (savedTable.equals(table)) {
            return this;
        }
        System.out.println("Table 1:");
        savedTable.print();
        System.out.println("Table 2:");
        table.print();
        throw new AssertionError("Table comparsion failed!");
    }

    public BasePage setEvent(Event event, boolean addTask) {
        return setEvent(event, new Table("tblEvents"), addTask);
    }

    public BasePage setEvent(Event event) {
        return setEvent(event, new Table("tblEvents"));
    }

    public BasePage setEvent(Event event, Table table) {
        return setEvent(event, table, false);
    }

    public BasePage setEvent(Event event, Table table, boolean addTask) {
        if (eventMap == null) {
            eventMap = new HashMap<>();
        }
        if (table.getTableSize()[0] < 2) {
            pause(1000);
            waitForUpdate();
            table = new Table("tblEvents");
        }
        int rowNum = table.getRowNumberByText(0, event.getName());
        WebElement input = table.getInput(rowNum, 1);
        if (event.isOnEachEvent() != null) {
            if (event.isOnEachEvent() != input.isSelected()) {
                input.click();
            }
        } else {
            event.setOnEachEvent(input.isSelected());
        }
        WebElement select = table.getCellWebElement(rowNum, 2).findElement(By.tagName("select"));
        if (event.getCountOfEvents() != null) {
            if (select.isEnabled()) {
                selectComboBox(select, event.getCountOfEvents());
            }
        } else {
            event.setCountOfEvents(getSelectedValue(select));
        }
        List<WebElement> selectList = table.getCellWebElement(rowNum, 3).findElements(By.tagName("select"));
        if (event.getDuration() != null) {
            if (selectList.get(0).isEnabled()) {
                selectComboBox(selectList.get(0), event.getDuration().split(":")[0]);
                selectComboBox(selectList.get(1), event.getDuration().split(":")[1]);
            }
        } else {
            String num = getSelectedValue(selectList.get(0));
            String units = getSelectedValue(selectList.get(1));
            event.setDuration(num + ":" + units);
        }
        if (Objects.requireNonNull(getSelectedValue(select)).isEmpty()) {
            eventMap.remove(event.getName());
        } else {
            eventMap.put(event.getName(), event);
        }
        if (addTask) {
            table.clickOn(rowNum, 4);
        }
        waitForUpdate();
        return this;
    }

    public BasePage setEvents(int amount, Event example) {
        Table table = new Table("tblEvents");
        String[] names = table.getColumn(0);
        if (names.length == 0) {
            String warn = "No one events was found!";
            logger.warn(warn);
            throw new AssertionError(warn);
        }
        for (int i = 0; i < Math.min(amount, names.length); i++) {
            setEvent(new Event(names[i], example.isOnEachEvent(), example.getCountOfEvents(), example.getDuration()), table);
        }
        return this;
    }

    public BasePage checkEvents() {
        if (!eventMap.equals(readEvents())) {
            String warn = "Events comparison error!";
            logger.warn('(' + BaseTestCase.getTestName() + ')' + "expected:" + eventMap);
            logger.warn('(' + BaseTestCase.getTestName() + ')' + "  actual:" + readEvents());
            logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
            throw new AssertionError(warn);
        }
        return this;
    }

    public BasePage disableAllEvents() {
        Table table = new Table("tblEvents");
        for (int i = 1; i < table.getTableSize()[0]; i++) {
            setEvent(new Event(table.getCellText(i, 0), false, "", null), table);
        }
        return this;
    }

    public void checkObjectTree() {
        Table table = new Table("tblTree");
        List<Integer> plusList = new ArrayList<>();
        List<Integer> minusList = new ArrayList<>();
        setImplicitlyWait(0);
        for (int i = 0; i < table.getTableSize()[0]; i++) {
            List<WebElement> images = table.getCellWebElement(i, 0).findElements(By.tagName("img"));
            if (!images.isEmpty()) {
                if (images.get(0).getAttribute("src").endsWith("collapse.png")) {
                    minusList.add(i);
                } else {
                    plusList.add(i);
                }
            }
        }
        setDefaultImplicitlyWait();
        if (plusList.isEmpty() || minusList.size() != 1 || minusList.get(0) != 0) {
            throw new AssertionError("Object tree looks like corrupt...");
        }
    }

    public boolean isInputActive(String id) {
        String attr = getAttributeById(id, "disabled");
        return attr == null || !attr.equals("true");
    }

    public boolean isButtonPresent(GlobalButtons button) {
        switchToFrame(BUTTONS);
        boolean out = findElements(button.getId()).size() == 1;
        switchToPreviousFrame();
        return out;
    }

    public boolean isButtonActive(IGlobalButtons button) {
        switchToFrame(BUTTONS);
        List<WebElement> list = findElements(button.getId());
        boolean out = list.size() == 1 && list.get(0).getAttribute("class").equals("button_default");
        switchToPreviousFrame();
        return out;
    }

    public void waitUntilButtonIsDisplayed(IGlobalButtons button) {
        switchToFrame(BUTTONS);
        try {
            waitUntilElementIsDisplayed(button.getId());
        } catch (AssertionError e) {
            String warn = "Button '" + button + "' not found";
            throw new AssertionError(warn);
        }
        switchToPreviousFrame();
    }

    public void waitUntilButtonIsEnabled(IGlobalButtons button) {
        switchToFrame(BUTTONS);
        try {
            waitUntilElementIsEnabled(button.getId());
        } catch (AssertionError e) {
            String warn = "Button '" + button + "' not found/not active";
            throw new AssertionError(warn);
        }
        switchToPreviousFrame();
    }

    public void waitUntilElementIsEnabled(String id) {
        long start = System.currentTimeMillis();
        long implWait = IMPLICITLY_WAIT * 1000;
        boolean success = false;
        while (System.currentTimeMillis() - start < implWait) {
            List<WebElement> list = findElements(id);
            if (list.size() == 1 && list.get(0).isEnabled()) {
                success = true;
                break;
            }
//            boolean out = list.size() == 1 && list.get(0).getAttribute("class").equals("button_default");
        }
        if (!success) {
            String warn = "Element with id='" + id + "' not found/not active";
            logger.warn(warn);
            throw new AssertionError(warn);
        }
    }

    public BasePage assertTableIsEmpty(String id) {
        Table table = getTable(id);
        if (table.getTableSize()[0] > 0) {
            throw new AssertionError("Unexpected table content (expected: empty table)");
        }
        return this;
    }

    public BasePage assertTableHasContent(String id) {
        Table table = getTable(id);
        if (table.getTableSize()[0] == 0) {
            throw new AssertionError("Unexpected table content (expected: not empty table)");
        }
        return this;
    }

    public BasePage enterIntoGroup(String groupName) {
        getMainTable().clickOn(groupName);
        waitForUpdate();
        return this;
    }

    protected BasePage assertButtonsArePresent(IGlobalButtons... buttons) {
        switchToFrame(BUTTONS);
        setDefaultImplicitlyWait();
        for (IGlobalButtons button : buttons) {
            List<WebElement> list = findElements(button.getId());
            if (list.size() != 1 || !list.get(0).isDisplayed()) {
                switchToPreviousFrame();
                throw new AssertionError("Button " + button + " not found");
            }
        }
        switchToPreviousFrame();
        return this;
    }

    public BasePage assertButtonsAreEnabled(boolean enabled, IGlobalButtons... buttons) {
        waitForUpdate();
        assertButtonsArePresent(buttons);
        switchToFrame(BUTTONS);
        setDefaultImplicitlyWait();
        for (IGlobalButtons button : buttons) {
            WebElement btn = findElement(button.getId());
            if (btn.getAttribute("class").equals("button_disabled") == enabled) {
                switchToPreviousFrame();
                throw new AssertionError("Button " + button + " has unexpected state (" + (enabled ? "disabled)" : "enabled)"));
            }
        }
        switchToPreviousFrame();
        return this;
    }

    public BasePage assertEquals(String actual, String expected) {
        Assert.assertEquals(actual, expected);
        return this;
    }

    public BasePage assertEquals(String actual, String expected, String message) {
        Assert.assertEquals(actual, expected, message);
        return this;
    }

    public BasePage assertTrue(boolean condition) {
        Assert.assertTrue(condition);
        return this;
    }

    public BasePage assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
        return this;
    }

    public BasePage assertFalse(boolean condition) {
        Assert.assertFalse(condition);
        return this;
    }

    public BasePage assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
        return this;
    }

    public String getAttributeById(String id, String attr) {
        return findElement(id).getAttribute(attr);
    }

    public static String getElementText(String id) {
        return driver.findElement(By.id(id)).getText();
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) BasePage.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static String getProtocolPrefix() {
        return BaseTestCase.getTestName().split("_")[0];
    }

    public BasePage assertInputHasText(String inputId, String text) {
        assertEquals(findElement(inputId).getAttribute("value"), text);
        return this;
    }

    public BasePage setViewColumns(int startParam, int endParam) {
        Table table = getTable("tblFilter");
        int size = table.getTableSize()[0] - 1;
        endParam = Math.min(size, endParam);
        startParam = Math.min(startParam, endParam);
        parameterSet = new HashSet<>();
        for (int i = startParam; i <= endParam; i++) {
            table.clickOn(i, 1);
            parameterSet.add(table.getCellText(i, 0));
        }
        return this;
    }

    public BasePage setViewColumns(String paramName) {
        Table table = getTable("tblFilter");
        parameterSet = new HashSet<>();
        table.clickOn(paramName, 0);
        parameterSet.add(paramName);
        return this;
    }

    public void validateViewColumns() {
        Timer timer = new Timer();
        while (!timer.timeout()) {
            if (isButtonActive("btnEditView_btn")) {
                break;
            }
        }
        List<String> columnList = new ArrayList<>(Arrays.asList(getMainTable().getRow(0)));
        columnList.removeIf(s -> s.equals(""));
        if (parameterSet.size() == columnList.size() && parameterSet.removeAll(columnList) && parameterSet.isEmpty()) {
            return;
        }
        System.out.println("paramSize:" + parameterSet.size() + "columnList.size:" + columnList.size() + "parameterSet.isEmpty:" + parameterSet.isEmpty());
        if (!parameterSet.isEmpty()) {
            StringBuilder sb = new StringBuilder("Below columns have not been applied to the view:");
            parameterSet.forEach(sb::append);
            logger.warn('(' + BaseTestCase.getTestName() + ')' + sb.toString());
        }
        throw new AssertionError("Checking column headers failed!");
    }

    public BasePage selectBranch(String branch) {
        waitForUpdate();
        List<String> nodeList = new ArrayList<>();
        Pattern p = Pattern.compile("(.+?(\\.\\d+)?)(\\.|$)");
        Matcher m = p.matcher(branch);
        while (m.find()) {
            if (m.group(1).matches(".+\\.\\d+")) {
                nodeList.add(m.group(1).split("\\.\\d+")[0]);
            }
            nodeList.add(m.group(1));
        }
        Table branchTable = new Table("tblTree");
        for (String node : nodeList) {
            int rowNum = branchTable.getRowNumberByText(node);
            WebElement cell = branchTable.getCellWebElement(rowNum, 0);
            List<WebElement> tagList = cell.findElements(By.xpath("child::img | child::span | child::input"));
            if (tagList.get(0).getTagName().equals("img") && tagList.get(0).getAttribute("src").endsWith("expand.png")) {
                tagList.get(0).click();
            } else {
                tagList.get(tagList.size() - 1).click();
            }
        }
        waitForUpdate();
        return this;
    }

    //1
    public void selectAnotherBranch() {
        String branch = getElementText("divPath");
        Table branchTable = new Table("tblTree");
        for (int i = 0; i < branchTable.getTableSize()[0]; i++) {
            if (branchTable.getCellWebElement(i, 0).isDisplayed()) {
                branchTable.clickOn(i, 0, -1);
                if (!getElementText("divPath").equals(branch)) {
                    setDefaultImplicitlyWait();
                    return;
                }
            }
            setImplicitlyWait(0);
            List<WebElement> tagList = branchTable.getCellWebElement(i, 0).findElements(By.tagName("img"));
            if (!tagList.isEmpty() && tagList.get(0).getAttribute("src").endsWith("expand.png")) {
                tagList.get(0).click();
            }
        }
    }

    String generateValue(String parameter, int index) {
        String value = "1";
        String paramType = DataBaseConnector.getValueType(parameter).toLowerCase();
        switch (paramType) {
            case "string":
                value = "value" + index;
                break;
            case "int":
            case "integer":
            case "unsignedint":
                value = "" + index;
                break;
            case "datetime":
                value = "2019-10-27T02:00:0";
                break;
            case "opaque":
                value = " ";
                break;
            case "time":
                value = CalendarUtil.getTimeStamp();
                break;
            case "boolean":
                break;
            default:
                throw new AssertionError("Unsupported data type:" + paramType);
        }
        return value;
    }

    public void setPolicy(Table table, String policyName, IPolicy notification, IPolicy accessList) {
        int rowNum = table.getRowNumberByText(0, policyName);
        if (rowNum < 0) {
            throw new AssertionError("Policy name '" + policyName + "' not found");
        }
        WebElement notificationCell = table.getCellWebElement(rowNum, 1);
        WebElement accessListCell = table.getCellWebElement(rowNum, 2);
        if (BROWSER.equals("edge")) {
            scrollToElement(notificationCell);
        }
        if (notification != null) {
            new Select(notificationCell.findElement(By.tagName("select"))).selectByValue(notification.getOption());
        }
        waitForUpdate();
        if (accessList != null) {
            new Select(accessListCell.findElement(By.tagName("select"))).selectByValue(accessList.getOption());
        }
        waitForUpdate();
//        clickOn(0, 0);
    }

    public static String getImportMonitorFile() {
        return props.getProperty(getProtocolPrefix() + "_import_monitor");
    }

    public static String getSerial() {
        return props.getProperty(getProtocolPrefix() + "_cpe_serial");
    }

    public static String getManufacturer() {
        return DataBaseConnector.getDevice(getSerial())[0];
    }

    public static String getModelName() {
        return DataBaseConnector.getDevice(getSerial())[1];
    }

    public static String deviceToString() {
        String[] device = DataBaseConnector.getDevice(getSerial());
        return device[0] + " " + device[1];
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public static void flushCollections() {
        parameterSet = null;
        parameterMap = null;
        eventMap = null;
        parametersMonitorMap = null;
    }

    public BasePage pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public BasePage clickOnTable(String id, int row, int column) {
        getTable(id).clickOn(row, column);
        return this;
    }

    public BasePage clickOnTable(String id, int row, int column, int tagNum) {
        getTable(id).clickOn(row, column, tagNum);
        return this;
    }

    public BasePage clickOnTable(String id, String text) {
        getTable(id).clickOn(text);
        return this;
    }

    public static void takeScreenshot(String pathname) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(pathname));
//        System.out.println("Successfully captured a screenshot");
    }

    public BasePage readTasksFromDb() {
        List<String[]> groupList;
        int count = Integer.parseInt(props.getProperty("pending_tasks_check_time"));
        for (int i = 0; i < count; i++) {
            long start = System.currentTimeMillis();
            groupList = DataBaseConnector.getTaskList();
            if (groupList.isEmpty()) {
                String warn = "There are no tasks created by '" + BaseTestCase.getTestName() + "' Group Update";
                logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
                throw new AssertionError(warn);
            }
            Set<String> StateSet = new HashSet<>();
            for (String[] line : groupList) {
                StateSet.add(line[2]);
            }
            if (!StateSet.contains("1")) {
                if (StateSet.size() != 1 || !StateSet.contains("2")) {
                    logger.info("All tasks created. One or more tasks failed or rejected");
                }
                return this;
            }
            long timeout;
            if ((timeout = 1000 - System.currentTimeMillis() + start) > 0) {
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("All tasks created. One or more tasks remains in pending state");
        return this;
    }

    public BasePage selectItem() {
        selectItem(BaseTestCase.getTestName());
        return this;
    }

//    public BasePage selectItem(String groupName) {
//        Table table = getMainTable();
//        int rowNum = table.getRowNumberByText(groupName);
//        table.clickOn(rowNum, 0);
//        return this;
//    }

    public BasePage selectItem(String text) {
        return selectItem(text, 1);
    }

    public BasePage selectItem(String text, int startFromRow) {
        return selectItem(getMainTable(), text, startFromRow);
    }

    public BasePage selectItem(Table table, String text, int startFromRow) {
        selectedName = "";
        List<Integer> list = table.getRowsWithText(text);
        for (int i : list) {
            if (i >= startFromRow) {
                table.clickOn(i, 0);
                int colNumber = table.getColumnNumber(0, "Name");
                if (colNumber >= 0) {
                    selectedName = table.getCellText(i, colNumber);
                }
                break;
            }
        }
        return this;
    }

    public BasePage assertColumnHasSingleValue(String column, String value) {
        waitForUpdate();
        String[] col = getMainTable().getColumn(column);
        Set<String> set = new HashSet<>(Arrays.asList(col));
        if (set.size() > 1) {
            throw new AssertionError("Column '" + column + "' has more than one value!");
        }
        if (!set.contains(value)) {
            throw new AssertionError("Column '" + column + "' doesn't contain '" + value + "'!");
        }
        return this;
    }

    public BasePage assertColumnHasSeveralValues(String column) {
        waitForUpdate();
        String[] col = getMainTable().getColumn(column);
        Set<String> set = new HashSet<>(Arrays.asList(col));
        if (set.size() > 1) {
            return this;
        }
        throw new AssertionError("Column '" + column + "' has 0 or single value!");
    }

    public BasePage assertItemIsSelected() {
        return assertItemIsSelected(selectedName);
    }

    public BasePage assertItemIsSelected(String text) {
        Table table = getMainTable();
        WebElement cell = table.getCellWebElement(table.getRowNumberByText(text), 0);
        List<WebElement> checkBoxList = cell.findElements(By.tagName("input"));
        if (checkBoxList.size() > 0) {
            if (!checkBoxList.get(0).isSelected()) {
                throw new AssertionError("Table item with text '" + text + "' is not selected!");
            }
            return this;
        }
        throw new AssertionError("Checkbox not found!");
    }

    public BasePage assertElementIsSelected(String id) {
        WebElement element = findElement(id);
        if (element.isSelected()) {
            return this;
        }
        String type = element.getAttribute("type");
        String el = type.equals("radio") ? "Radiobutton" : type.equals("checkbox") ? "Checkbox" : "Element";
        throw new AssertionError(el + " id='" + id + "' is not selected!");
    }

    public String getRandomStringValue(int length) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < length; i++) {
//            sb.append((char) (Math.random() * 91 + 32));
//        }
//        System.out.println(sb.toString());
//        return sb.toString().replaceAll("<", " ");
        String sample = "()*,-./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(sample.charAt((int) (Math.random() * 69)));
        }
        return sb.toString();
    }

    public BasePage checkSorting(String column) {
        Table table;
        int colNum;
        try {
            waitForUpdate();
            table = getMainTable();
            colNum = table.getColumnNumber(0, column);
            table.clickOn(0, colNum);
        } catch (StaleElementReferenceException e) {
            waitForUpdate();
            table = getMainTable();
            colNum = table.getColumnNumber(0, column);
            table.clickOn(0, colNum);
        }
        Timer timer = new Timer(30000);
        for (int i = 0; i < 2; i++) {
            try {
                waitForUpdate();
                table = getMainTable();
                WebElement pointer = table.getCellWebElement(0, colNum).findElement(By.tagName("img"));
                boolean descending = pointer.getAttribute("src").endsWith("down.png");
                String[] arr = table.getColumn(colNum, true);
                arr = toLowerCase(arr);
                String[] arr2 = Arrays.copyOf(arr, arr.length);
                Arrays.sort(arr, descending ? Comparator.reverseOrder() : Comparator.naturalOrder());
                if (!Arrays.deepEquals(arr, arr2)) {
                    System.out.println("Expected:" + Arrays.toString(arr));
                    System.out.println("Found   :" + Arrays.toString(arr2));
                    String warn = "sorting by column '" + column + "' failed!";
                    warn = (descending ? "Descending " : "Ascending ") + warn;
                    logger.warn('(' + BaseTestCase.getTestName() + ')' + warn);
                    throw new AssertionError(warn);
                }
                System.out.println((descending ? "Descending " : "Ascending ") + "sorting by column '" + column + "' is OK!");
                if (i < 1) {
                    table.clickOn(0, colNum);
                }
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                System.out.println("StaleElementReferenceException | NoSuchElementException e");
                if (!timer.timeout())
                    i--;
            }
        }
        return this;
    }

    String[] toLowerCase(String[] array) {
        String[] output = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            output[i] = array[i].toLowerCase();
        }
        return output;
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
        if (frame == SUB_FRAME) {
            driver.switchTo().frame(driver.findElement(By.id(DESKTOP.frameId)));
        }
        WebElement frameEl = driver.findElement(By.id(frame.frameId));
        driver.switchTo().frame(frameEl);
    }

    public static void switchToPreviousFrame() {
        switchToFrame(previousFrame);
    }

    public BasePage assertColumnContainsValue(String column, String value) {
        waitForUpdate();
        String[] col = getMainTable().getColumn(column);
        if (!Arrays.asList(col).contains(value)) {
            throw new AssertionError("Column '" + column + "' doesn't contain the value '" + value + "'!");
        }
        return this;
    }

    public BasePage setParameter(Table table, String paramName, ParameterType option, String value) {
//        Table table = new Table("tblParamsValue");
        int rowNum = table.getRowNumberByText(paramName);
        if (parameterMap == null) {
            parameterMap = new HashMap<>();
        }
        String hint = table.getHint(rowNum);
        WebElement paramCell = table.getCellWebElement(rowNum, 1);
        if (props.getProperty("browser").equals("edge")) {
            scrollToElement(paramCell);
        }
        new Select(paramCell.findElement(By.tagName("select"))).selectByValue(option != ParameterType.CUSTOM ? option.getOption() : value);
        if (value != null && option == ParameterType.VALUE) {
            waitForUpdate();
            WebElement input = paramCell.findElement(By.tagName("input"));
            input.clear();
            input.sendKeys(value);
        }
        parameterMap.put(hint, value);
        if (!BROWSER.equals("edge")) {
            table.clickOn(0, 0);
        }
        return this;
    }

    public BasePage setParametersMonitor(Table table, String name, Condition condition, String value) {
        if (parametersMonitorMap == null) {
            parametersMonitorMap = new HashMap<>();
        }
        ParametersMonitor monitor = new ParametersMonitor(null, condition, value);
        String[] names = table.getColumn(0);
        for (int i = 0; i < names.length; i++) {
            String nm = names[i];
            if (nm.equals(name) || table.getHint(i + 1).equals(name)) {
                WebElement select = table.getCellWebElement(i + 1, 1).findElement(By.tagName("select"));
                selectComboBox(select, condition.toString());
                waitForUpdate();
                if (condition != Condition.VALUE_CHANGE) {
                    WebElement field = table.getInput(i + 1, 2);
                    field.clear();
                    field.sendKeys(value + " ");
                    waitForUpdate();
                    field.sendKeys(Keys.BACK_SPACE);
                    waitForUpdate();
                } else {
                    monitor.setValue("");
                }
                monitor.setName(table.getHint(i + 1));
                parametersMonitorMap.put(monitor.getName(), monitor);
            }
        }
        return this;
    }

    public BasePage setParametersMonitor(Condition condition) {
        return setParametersMonitor(condition, false);
    }

    public BasePage setParametersMonitor(Condition condition, boolean addTask) {
        Table table = new Table("tblParamsMonitoring");
        String name = table.getColumn(0)[0];
        String value = generateValue(table.getHint(1), 1);
        setParametersMonitor(table, name, condition, value);
        if (addTask) {
            table.clickOn(1, 3);
        }
        return this;
    }

    public BasePage checkParametersMonitor() {
        Table table = new Table("tblParamsMonitoring");
        String[] names = table.getColumn(0);
        if (parametersMonitorMap.size() != names.length) {
            throw new AssertionError("Expected number of Parameters Monitor is " + parametersMonitorMap.size() + ", but found: " + names.length);
        }
        parametersMonitorMap.forEach((name, monitor) -> {
            boolean isFound = false;
            for (int i = 1; i < table.getTableSize()[0]; i++) {
                if (name.equals(table.getHint(i)) && monitor.getValue().equals(table.getInputText(i, 2))
                        && monitor.getCondition().toString().equals(getSelectedValue(table.getSelect(i, 1)))) {
//                    WebElement select = table.getCellWebElement(i, 1).findElement(By.tagName("select"));
//                    if (getSelectedValue(select).equals(monitor.getCondition().toString())) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                String warn = "Expected: " + monitor + "\n  Actual: " + "ParametersMonitor{" + table.getHint(1) + " | "
                        + getSelectedValue(table.getSelect(1, 1)) + " | " + table.getInputText(1, 2) + '}';
                logger.warn(warn);
                throw new AssertionError("Expected Parameters Monitoring not found on current page!\n" + table.print());
            }
        });
        return this;
    }

    public BasePage checkAddedTask(String parameter, String value) {
        return checkAddedTask(parameter, value, 0);
    }

    public BasePage checkAddedTask(String parameter, String value, int shift) {
        Table table = getTable("tblTasks");
        Timer timer = new Timer();
        while ((table.getTableSize()[0] == 1 || table.getRowLength(1) < 2) && !timer.timeout()) {
            table = getTable("tblTasks");
        }
        int[] tableSize = table.getTableSize();
        boolean match = false;
        for (int i = 0; i < tableSize[0]; i++) {
            try {
                int length = table.getRowLength(i);
                if (table.getCellText(i, length - 2 - shift).toLowerCase().equals(parameter.toLowerCase())
                        && table.getCellText(i, length - 1 - shift).toLowerCase().equals(value.toLowerCase())) {
                    match = true;
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
        if (!match) {
            String warning = "Pair '" + parameter + "' : '" + value + "' not found";
            table.print();
            logger.warn('(' + BaseTestCase.getTestName() + ')' + warning);
            throw new AssertionError(warning);
        }
        return this;
    }

    public void checkAddedTasks() {
        Set<Map.Entry<String, String>> entrySet = parameterMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            checkAddedTask(entry.getKey(), entry.getValue());   // don't use trim()
        }
    }

    public void forceFail() {
        throw new AssertionError("Test is ok, but was down manually");
    }

    public void assertPageWasRefreshed() {
        try {
            savedTable.clickOn(0, 0);
            throw new AssertionError("Page has not been refreshed!");
        } catch (StaleElementReferenceException e) {
            System.out.println("StaleElementReferenceException handled successfully!");
        }
    }

    public void switchToNewWindow() {
        Set<String> windows = driver.getWindowHandles();
        windows.forEach(window -> {
            if (!window.equalsIgnoreCase(mainWindow)) {
                driver.switchTo().window(window);
            }
        });
    }

    public static void closeNewWindow() {
        if (!driver.getWindowHandle().equalsIgnoreCase(mainWindow)) {
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }

    public enum FrameSwitch {
        ROOT(null), DESKTOP("frmDesktop"), BUTTONS("frmButtons"),
        CONDITIONS("frmPopup2"), POPUP("frmPopup"), SUB_FRAME("frmSubFrame");

        FrameSwitch(String frameId) {
            this.frameId = frameId;
        }

        String frameId;
    }
}


