package com.friendly.aqa.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class GroupUpdatePage extends BasePage {
    public GroupUpdatePage(WebDriver driver) {
        super(driver);
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

    public Table getMainTable() {
        mainTable.getText();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        Table table = new Table(mainTable);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return table;
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
        new Select(manufacturerComboBox).selectByValue("tp-link");
//        manufacturerComboBox = driver.findElement(By.id("ddlManufacturer"));
        return this;
    }

    public GroupUpdatePage selectManufacturer() {
        return selectManufacturer("tp-link");
    }

    public GroupUpdatePage selectModel(String modelName) {
        switchToFrameDesktop();
        new Select(modelComboBox).selectByValue(modelName);
        return this;
    }

    public GroupUpdatePage selectModel() {
        switchToFrameDesktop();
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
        waitForUpdate(); //need to think about replace a pause by more correct thing...
        table = getMainTable();
        String[] after = table.getColumn(column);
        System.out.println(Arrays.deepToString(after));
        return Arrays.equals(before, after);
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
