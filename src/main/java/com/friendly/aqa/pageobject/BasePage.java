package com.friendly.aqa.pageobject;

import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.DataBaseConnector;
import com.friendly.aqa.utils.Table;
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
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;
import static com.friendly.aqa.pageobject.GlobalButtons.REFRESH;
import static com.friendly.aqa.pageobject.TopMenu.DEVICE_UPDATE;


public abstract class BasePage {
    static WebDriver driver;
    static Properties props;
    public static final String BROWSER;
    private static Logger logger;
    static FrameSwitch frame;
    private static FrameSwitch previousFrame;
    protected Table currentTable;
    protected static Set<String> parameterSet;
    protected static Map<String, String> parameterMap;
    protected String selectedName;

    static {
        initProperties();
        logger = Logger.getLogger(BasePage.class);
        BROWSER = props.getProperty("browser");
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

    @FindBy(id = "btnAlertOk_btn")
    protected WebElement okButtonAlertPopUp;

    @FindBy(id = "btnAddFilter_btn")
    private WebElement addFilterButton;

    @FindBy(id = "ddlColumns")
    protected WebElement selectColumnFilter;

    @FindBy(id = "ddlCondition")
    protected WebElement compareSelect;

    @FindBy(id = "btnOk_btn")
    protected WebElement okButtonPopUp;

    @FindBy(id = "ddlManufacturer")
    protected WebElement manufacturerComboBox;

    @FindBy(id = "ddlModelName")
    protected WebElement modelComboBox;

    @FindBy(id = "ddlSend")
    protected WebElement sendToComboBox;

    @FindBy(id = "lrbImmediately")
    protected WebElement immediatelyRadioButton;

    @FindBy(id = "lrbWaitScheduled")
    protected WebElement scheduledToRadioButton;

    @FindBy(id = "frmImportFromFile")
    protected WebElement importFrame;

    @FindBy(id = "fuSerials")
    protected WebElement importDeviceField;

    @FindBy(id = "ddlPageSizes")
    protected WebElement itemsOnPageComboBox;

    @FindBy(id = "btnDefaultView_btn")
    protected WebElement resetViewButton;

    public void logOut() {
        switchToFrame(ROOT);
        waitForUpdate();
        logOutButton.click();
    }

    public BasePage resetView() {
        resetViewButton.click();
        waitForUpdate();
        return this;
    }

    public void scrollTo(WebElement element) {
        ((JavascriptExecutor) BasePage.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public BasePage selectManufacturer(String manufacturer) {
        selectComboBox(manufacturerComboBox, manufacturer);
        return this;
    }

    public BasePage selectModel(String modelName) {
        selectComboBox(modelComboBox, modelName);
        return this;
    }

    public BasePage selectModel() {
        selectComboBox(modelComboBox, getModelName());
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

    public BasePage immediately() {
        immediatelyRadioButton.click();
        return this;
    }

    public BasePage scheduledToRadioButton() {
        scheduledToRadioButton.click();
        return this;
    }

    public BasePage filterRecordsCheckbox() {
        driver.findElement(By.id("tblTree")).findElement(By.tagName("input")).click();
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

    public BasePage assertButtonIsActive(boolean assertActive, String id) {
        if (isButtonActive(id) == assertActive) {
            return this;
        }
        throw new AssertionError("Button id='" + id + "' has unexpected state (" +
                (assertActive ? "disabled" : "enabled") + ")");
    }

    public BasePage selectImportDevicesFile() {
        switchToFrame(DESKTOP);
        driver.switchTo().frame(importFrame);
        String inputText = new File(getImportCpeFile()).getAbsolutePath();
        importDeviceField.sendKeys(inputText);
        driver.switchTo().parentFrame();
        return this;
    }

    public boolean isOptionPresent(String comboBoxId, String text) {
        waitForUpdate();
        WebElement comboBox = driver.findElement(By.id(comboBoxId));
        List<WebElement> options = comboBox.findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (option.getText().toLowerCase().equals(text.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean isButtonActive(String id) {
        return !driver.findElement(By.id(id)).getAttribute("class").equals("button_disabled");
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
        long start = System.currentTimeMillis();
        while (!(getMainTable()).getCellText(testName, "State").equals(status)) {
            globalButtons(REFRESH);
            if (System.currentTimeMillis() - start > timeout * 1000) {
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
        driver.findElement(By.id(id)).sendKeys(text);
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

    String getTestName() {
        return BaseTestCase.getTestName();
    }

    public Table getTable(String id, FrameSwitch frame) {
        waitForUpdate();
        if (frame != null) {
            switchToFrame(frame);
        }
        WebElement tableEl = driver.findElement(By.id(id));
        setImplicitlyWait(0);
        Table table = new Table(tableEl);
        setDefaultImplicitlyWait();
        return currentTable = table;
    }

    public Table getTable(String id) {
        return getTable(id, null);
    }

    public Table getTabTable() {
        return getTable("tabsSettings_tblTabs", null);
    }

    public BasePage waitForUpdate() {
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

    public BasePage assertElementIsPresent(String id) {
        waitForUpdate();
        List<WebElement> list = driver.findElements(By.id(id));
        if (list.size() == 0) {
            String warn = "Element with id='" + id + "' not found on current page";
            logger.warn(warn);
            throw new AssertionError(warn);
        }
        return this;
    }

    public boolean elementIsAbsent(String id) {
        waitForUpdate();
        setImplicitlyWait(0);
        boolean out = driver.findElements(By.id(id)).size() == 0;
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

    public void assertPresenceOfParameter(String tableId, String value) {
        getTable(tableId).print().assertPresenceOfParameter(value);
    }

    public BasePage assertPresenceOfOptions(String comboBoxId, String... options) {
        WebElement comboBox = driver.findElement(By.id(comboBoxId));
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
        driver.findElement(By.id(id)).click();
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

    void clickGlobalButtons(IGlobalButtons button) {
        waitForUpdate();
        switchToFrame(BUTTONS);
        int timeout = Integer.parseInt(props.getProperty("driver_implicitly_wait"));
        for (int i = 0; i < 3; i++) {
            try {
                new FluentWait<>(driver)
                        .withMessage("Button " + button + " not found/not active")
                        .withTimeout(Duration.ofSeconds(timeout))
                        .pollingEvery(Duration.ofMillis(100))
                        .until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id(button.getId()))))
                        .click();
                switchToPreviousFrame();
                return;
            } catch (StaleElementReferenceException e) {
                logger.info("Button click failed. Retrying..." + (i + 1) + "time(s)");
            }
        }
        throw new AssertionError("cannot click button!");
    }

    public String getSelectedValue(String inputId) {
        return getSelectedValue(driver.findElement(By.id(inputId)));
    }

    private String getSelectedValue(WebElement comboBox) {
        List<WebElement> optList = comboBox.findElements(By.tagName("option"));
        for (WebElement el : optList) {
            if (el.getAttribute("selected") != null) {
                return el.getText();
            }
        }
        return null;
    }

    public boolean isInputActive(String id) {
        String attr = getAttributeById(id, "disabled");
        return attr == null || !attr.equals("true");
    }

    public boolean isButtonPresent(GlobalButtons button) {
        switchToFrame(BUTTONS);
        boolean out = driver.findElements(By.id(button.getId())).size() == 1;
        switchToPreviousFrame();
        return out;
    }

    public boolean isButtonActive(IGlobalButtons button) {
        switchToFrame(BUTTONS);
        List<WebElement> list = driver.findElements(By.id(button.getId()));
        boolean out = list.size() == 1 && list.get(0).getAttribute("class").equals("button_default");
        switchToPreviousFrame();
        return out;
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
        for (IGlobalButtons button : buttons) {
            List<WebElement> list = driver.findElements(By.id(button.getId()));
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
        for (IGlobalButtons button : buttons) {
            WebElement btn = driver.findElement(By.id(button.getId()));
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
        return driver.findElement(By.id(id)).getAttribute(attr);
    }

    public static String getElementText(String id) {
        return driver.findElement(By.id(id)).getText();
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) BasePage.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static String getConfigPrefix() {
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
        branchTable.clickOn(branch);
        waitForUpdate();
        return this;
    }

    public void selectBranch() {
        String branch = getElementText("divPath");
        Table branchTable = new Table("tblTree");
        String[] column = branchTable.getColumn(0);
        for (int i = 0; i < column.length; i++) {
            WebElement cell = branchTable.getCellWebElement(i, 0);
            List<WebElement> tagList = cell.findElements(By.xpath("child::img | child::span | child::input"));
            tagList.get(tagList.size() - 1).click();
            if (!getElementText("divPath").equals(branch)) {
                return;
            }
            if (tagList.get(0).getTagName().equals("img") && tagList.get(0).getAttribute("src").endsWith("expand.png")) {
                tagList.get(0).click();
            }
        }
    }

    String generateValue(String parameter, int increment){
        String value = null;
        String paramType = DataBaseConnector.getValueType(parameter).toLowerCase();
        switch (paramType) {
            case "string":
                value = "value" + increment;
                break;
            case "int":
            case "integer":
            case "unsignedint":
                value = "" + increment;
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

    public static String getImportCpeFile() {
        return props.getProperty(getConfigPrefix() + "import_cpe");
    }

    public static String getImportGuFile() {
        return props.getProperty(getConfigPrefix() + "import_group");
    }

    public static String getImportMonitorFile() {
        return props.getProperty(getConfigPrefix() + "import_monitor");
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

    public static String deviceToString() {
        String[] device = DataBaseConnector.getDevice(getSerial());
        return device[0] + " " + device[1];
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public static void flushResults() {
        parameterSet = null;
        parameterMap = null;
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
        System.out.println("Successfully captured a screenshot");
    }

    public BasePage readTasksFromDb() {
        String groupName = BaseTestCase.getTestName();
        List<String[]> groupList;
        int count = Integer.parseInt(props.getProperty("pending_tasks_check_time"));
        for (int i = 0; i < count; i++) {
            long start = System.currentTimeMillis();
            groupList = DataBaseConnector.getTaskList(DataBaseConnector.getGroupId(groupName));
            if (groupList.isEmpty()) {
                String warn = "There are no tasks created by '" + groupName + "' Group Update";
                logger.warn(warn);
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
        selectedName = "";
        Table table = getMainTable();
        List<Integer> list = table.getRowsContainText(text);
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
        WebElement element = driver.findElement(By.id(id));
        if (element.isSelected()) {
            return this;
        }
        String type = element.getAttribute("type");
        String el = type.equals("radio") ? "Radiobutton" : type.equals("checkbox") ? "Checkbox" : "Element";
        throw new AssertionError(el + " id='" + id + "' is not selected!");
    }

    public BasePage checkSorting(String column) {
        waitForUpdate();
        Table table = getMainTable();
        int colNum = table.getColumnNumber(0, column);
        for (int i = 0; i < 2; i++) {
            table.clickOn(0, colNum);
            waitForUpdate();
            table = getMainTable();
            boolean descending = table.getCellWebElement(0, colNum).findElement(By.tagName("img")).getAttribute("src").endsWith("down.png");
            String[] arr = table.getColumn(colNum, true);
            arr = toLowerCase(arr);
            String[] arr2 = Arrays.copyOf(arr, arr.length);
            Arrays.sort(arr, descending ? Comparator.reverseOrder() : Comparator.naturalOrder());
            if (!Arrays.deepEquals(arr, arr2)) {
                System.out.println("arr2:" + Arrays.toString(arr2));
                System.out.println("arr:" + Arrays.toString(arr));
                String warn = "sorting by column '" + column + "' failed!";
                warn = (descending ? "Descending " : "Ascending ") + warn;
                logger.warn(warn);
                throw new AssertionError(warn);
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


    public enum FrameSwitch {
        ROOT(null), DESKTOP("frmDesktop"), BUTTONS("frmButtons"),
        CONDITIONS("frmPopup2"), POPUP("frmPopup");

        FrameSwitch(String frameId) {
            this.frameId = frameId;
        }

        String frameId;
    }
}


