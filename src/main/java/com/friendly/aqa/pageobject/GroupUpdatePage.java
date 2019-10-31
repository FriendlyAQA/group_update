package com.friendly.aqa.pageobject;

import com.friendly.aqa.utils.Table;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.friendly.aqa.pageobject.GlobalButtons.NEXT;
import static com.friendly.aqa.pageobject.GlobalButtons.REFRESH;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.NEW;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;

public class GroupUpdatePage extends BasePage {
    public GroupUpdatePage() {
        super();
        switchToFrameDesktop();
    }

    @Override
    protected String getLeftMenuCssSelector() {
        return "tr[topmenu='Update Group']";
    }

    @FindBy(id = "ddlManufacturer")
    private WebElement manufacturerComboBox;

    @FindBy(id = "ddlModelName")
    private WebElement modelComboBox;

    @FindBy(name = "ddlUpdateStatus")
    private WebElement updStatusComboBox;

    @FindBy(id = "txtName")
    private WebElement nameField;

    @FindBy(id = "ddlSend")
    private WebElement sendToField;

    @FindBy(name = "btnEditView$btn")
    private WebElement editGroupButton;

    @FindBy(name = "btnNewView$btn")
    private WebElement createGroupButton;

    @FindBy(name = "btnShowDevices$btn")
    private WebElement showList;

    @FindBy(name = "btnBrowse$btn")
    private WebElement browseButton;

    @FindBy(name = "btnShowDevices$btn")
    private WebElement showListButton;

    @FindBy(name = "btnDefaultView$btn")
    private WebElement resetViewButton;

    @FindBy(id = "ddlPageSizes")
    private WebElement itemsOnPageComboBox;

    @FindBy(id = "lrbImmediately")
    private WebElement immediatelyRadioButton;

    @FindBy(id = "lrbWaitScheduled")
    private WebElement scheduledToRadioButton;

    @FindBy(id = "btnAddFilter_btn")
    private WebElement addFilterButton;

    @FindBy(id = "btnDelFilter_btn")
    private WebElement deleteFilterButton;

    @FindBy(id = "ddlColumns")
    private WebElement selectColumnFilter;

    @FindBy(id = "ddlCondition")
    private WebElement compareSelect;

    @FindBy(css = "input[name^='node']")
    private WebElement filterCreatedCheckBox;

    @FindBy(id = "btnOk_btn")
    private WebElement okButtonPopUp;

    @FindBy(id = "btnCancel_btn")
    private WebElement cancelButtonPopUp;

    @FindBy(id = "ddlTasks")
    private WebElement selectTask;

    @FindBy(id = "btnAddTask_btn")
    private WebElement addTaskButton;

    @FindBy(id = "cbPeriod1")
    private WebElement period1CheckBox;

    @FindBy(id = "cbPeriod2")
    private WebElement period2CheckBox;

    @FindBy(id = "txtFailedMax")
    private WebElement thresholdField;

    @FindBy(id = "lblWait")
    private WebElement waitUntilConnectRadioButton;

    @FindBy(id = "lblPush")
    private WebElement requestToConnectRadioButton;

    @FindBy(id = "cbOnlineOnly")
    private WebElement onlineDevicesCheckBox;

    @FindBy(id = "tmScheduled_ddlHour")
    private WebElement timeHoursSelect;

    @FindBy(id = "tmScheduled_ddlMinute")
    private WebElement timeMinutesSelect;

    @FindBy(id = "tblParameters")
    private WebElement mainTable;

    @FindBy(how = How.ID, using = "tblParameters")
    private List<WebElement> mainTableIsPresent;

    @FindBy(how = How.ID, using = "lblDBError")
    private List<WebElement> noDataFound;

    @FindBy(how = How.ID, using = "tblDevices")
    private List<WebElement> serialNumberTableList;

    @FindBy(id = "fuSerials")
    private WebElement importDevicesHiddenField;

    @FindBy(id = "fuImport")
    private WebElement importField;

    @FindBy(id = "frmImportFromFile")
    private WebElement importFrame;

    @FindBy(id = "calDate_image")
    private WebElement calendarIcon;

    @FindBy(id = "calDate_calendar")
    private WebElement divCalendar;

    @FindBy(id = "tabsSettings_tblTabs")
    private WebElement paramTabsTable;

//    @FindBy(id = "tblParamsValue")
//    private WebElement paramTable;

    public void insertImportFile() {
        waitForRefresh();
        importField.sendKeys("D:\\Users\\asp4r\\Desktop\\UpdateGroup(5461_22.10.2019 14-40-05).xml");
    }

    public GroupUpdatePage selectImportFile() {
//        waitForRefresh();
        switchToFrameDesktop();
        driver.switchTo().frame(importFrame);
        String inputText = "D:\\Users\\asp4r\\Desktop\\Report(Inventory_Sercomm_10-29-2019 11-21-37 AM).xml";
        importDevicesHiddenField.sendKeys(inputText);
        driver.switchTo().parentFrame();
        return this;
        // id=fuSerials  frame id=frmImportFromFile
    }

    public Table getMainTable() {
        waitForRefresh();
        mainTable.getText();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        Table table = new Table(mainTable);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return table;
    }

    public Table getTable(String id) {
        waitForRefresh();
        WebElement tableEl = driver.findElement(By.id(id));
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        Table table = new Table(tableEl);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return table;
    }

    public void checkCalendarClickable() {
        boolean exception = false, repeat = false;
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        calendarIcon.click();
        Table calendar = new Table(divCalendar.findElement(By.tagName("table")));
        for (int i = 2; i < calendar.getTableSize()[0]; i++) {
            for (int j = 0; j < calendar.getTableSize()[1]; j++) {
                WebElement cell = calendar.getCellWebElement(i, j);
                String attr = cell.getAttribute("onclick");
                if (attr != null) {
                    int start = attr.indexOf(".SelectDate('") + 13;
                    int end = attr.length() - 2;
                    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                    if (!repeat) {
                        logger.info("First day of month in Sunday. Test case 19 is not effective");
                    }
                    if (exception) {
                        logger.info("An exception was caught while checking grayed-out dates");
                    }
                    try {
                        cell.click();
                    } catch (ElementNotInteractableException e) {
                        logger.info("An exception was caught when click on current date");
                    }
                    return;
                }
                try {
                    cell.click();
                } catch (ElementNotInteractableException e) {
                    exception = true;
                }
                repeat = true;
            }
        }
        throw new AssertionError("Current date isn't found");
    }

    public GroupUpdatePage timeHoursSelect(int index) {
        new Select(timeHoursSelect).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage timeMinutesSelect(int index) {
        new Select(timeMinutesSelect).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage onlineDevicesCheckBox() {
        onlineDevicesCheckBox.click();
        return this;
    }

    public GroupUpdatePage requestToConnectRadioButton() {
        requestToConnectRadioButton.click();
        return this;
    }

    public GroupUpdatePage waitUntilConnectRadioButton() {
        waitUntilConnectRadioButton.click();
        return this;
    }

    public GroupUpdatePage addTaskButton() {
        addTaskButton.click();
        return this;
    }

    public GroupUpdatePage addNewTask(int index) {
        new Select(selectTask).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage filterCreatedCheckBox() {
        filterCreatedCheckBox.click();
        return this;
    }

    public GroupUpdatePage okButtonPopUp() {
        driver.switchTo().defaultContent();
        okButtonPopUp.click();
        switchToFrameDesktop();
        return this;
    }

    public GroupUpdatePage cancelButtonPopUp() {
        driver.switchTo().defaultContent();
        cancelButtonPopUp.click();
        switchToFrameDesktop();
        return this;
    }

    public GroupUpdatePage deleteFilter() {
        deleteFilterButton.click();
        return this;
    }

    public GroupUpdatePage addFilter() {
        addFilterButton.click();
        return this;
    }

    public GroupUpdatePage compareSelect(String sendTo) {
        new Select(compareSelect).selectByValue(sendTo);
        return this;
    }

    public GroupUpdatePage compareSelect(int index) {
        new Select(compareSelect).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage selectColumnFilter(String sendTo) {
        new Select(selectColumnFilter).selectByValue(sendTo);
        return this;
    }

    public GroupUpdatePage selectColumnFilter(int index) {
        new Select(selectColumnFilter).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage scheduledToRadioButton() {
        scheduledToRadioButton.click();
        return this;
    }

    public GroupUpdatePage immediately() {
        immediatelyRadioButton.click();
        return this;
    }

    public GroupUpdatePage itemsOnPage(String number) {
        int index;
        switch (number) {
            case "7":
                index = 0;
                break;
            case "10":
                index = 1;
                break;
            case "20":
                index = 3;
                break;
            case "30":
                index = 4;
                break;
            case "50":
                index = 5;
                break;
            case "100":
                index = 6;
                break;
            case "200":
                index = 7;
                break;
            default:
                index = 2;
        }
        new Select(itemsOnPageComboBox).selectByIndex(index);

        return this;
    }

    public GroupUpdatePage resetView() {
        resetViewButton.click();
        return this;
    }

    public GroupUpdatePage showList() {
        waitForRefresh();
        showListButton.click();
        return this;
    }

    public GroupUpdatePage selectSendTo() {
        new Select(sendToField).selectByValue("All");
        return this;
    }

    public GroupUpdatePage selectSendTo(String sendTo) {
        new Select(sendToField).selectByValue(sendTo);
        return this;
    }

    public GroupUpdatePage selectSendTo(int index) {
        new Select(sendToField).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage fillName(String name) {
        nameField.sendKeys(name);
        return this;
    }

    public GroupUpdatePage selectManufacturer(String manufacturer) {
        waitForRefresh();
        new Select(manufacturerComboBox).selectByValue(manufacturer);
        return this;
    }

    public GroupUpdatePage selectManufacturer(int index) {
        waitForRefresh();
        new Select(manufacturerComboBox).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage selectManufacturer() {
        return selectManufacturer("tp-link");
    }

    public GroupUpdatePage selectModel(String modelName) {
        waitForRefresh();
        new Select(modelComboBox).selectByValue(modelName.toLowerCase());
        return this;
    }

    public GroupUpdatePage selectModel(int index) {
        waitForRefresh();
        new Select(modelComboBox).selectByIndex(index);
        return this;
    }

    public GroupUpdatePage selectModel() {
        waitForRefresh();
        Select modelName = new Select(modelComboBox);
        modelName.selectByIndex(modelName.getOptions().size() - 1);
        return this;
    }

    public GroupUpdatePage globalButtons(GlobalButtons button) {
        clickGlobalButtons(button);
        return this;
    }

    public GroupUpdatePage leftMenu(GroupUpdatePage.Left item) {
        driver.switchTo().defaultContent();
        leftMenuClick(item.getValue());
        switchToFrameDesktop();
        return this;
    }

    public GroupUpdatePage createGroup() {
        waitForRefresh();
        createGroupButton.click();
        return this;
    }

    public GroupUpdatePage pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public GroupUpdatePage waitForRefresh() {
        super.waitForUpdate();
        return this;
    }

    public boolean checkSorting(int column) {
        Table table = getMainTable();
        String[] before = table.getColumn(column);
        System.out.println(Arrays.deepToString(before));
        Arrays.sort(before);
        System.out.println(Arrays.deepToString(before));
        table.clickOn(0, column);
        waitForUpdate();
        table = getMainTable();
        String[] after = table.getColumn(column);
        System.out.println(Arrays.deepToString(after));
        return Arrays.equals(before, after);
    }

    public boolean noDataFoundLabelIsPresent() {
        return noDataFound.size() != 0;
    }

    public boolean serialNumberTableIsPresent() {
        return serialNumberTableList.size() != 0;
    }

    public boolean mainTableIsPresent() {
        return mainTableIsPresent.size() != 0;
    }

    public GroupUpdatePage waitForStatus(String status, String groupName, int timeout) {
        long start = System.currentTimeMillis();
        while (!getMainTable().getCellText(4, groupName, 1).toLowerCase().equals(status.toLowerCase())) {
            switchToFrameButtons();
            globalButtons(REFRESH);
            switchToFrameDesktop();
            if (System.currentTimeMillis() - start > timeout * 1000){
                throw new AssertionError("Timed out while waiting for status " + status);
            }
        }
        return this;
    }

    public Table goToSetParameters(String groupName, String tableId) {
        topMenu(GROUP_UPDATE);
        return leftMenu(NEW)
                .selectManufacturer("sercomm")
                .selectModel()
                .fillName(groupName)
                .selectSendTo()
                .globalButtons(NEXT)
                .immediately()
                .globalButtons(NEXT)
                .addNewTask(1)
                .addTaskButton()
                .getTable(tableId);
    }

    public Table goToSetParameters(String groupName) {
        return goToSetParameters(groupName, "tblParamsValue");
    }

    public enum Left {
        VIEW("View"), IMPORT("Import"), NEW("New");
        private String value;

        Left(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
