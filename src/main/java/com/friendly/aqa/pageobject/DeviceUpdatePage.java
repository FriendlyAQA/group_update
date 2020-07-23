package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.IGlobalButtons;
import com.friendly.aqa.entities.Table;
import com.friendly.aqa.entities.TopMenu;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.DataBaseConnector;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;
import static com.friendly.aqa.entities.TopMenu.*;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.GlobalButtons.*;

public class DeviceUpdatePage extends BasePage {
    private static final Logger logger = Logger.getLogger(DeviceUpdatePage.class);
    private Date xmlFileTime;
    private Date csvFileTime;

    @FindBy(id = "btnSaveUsr_btn")
    private WebElement saveButton;

    @FindBy(id = "IsDefaultViewForUser")
    private WebElement defaultViewCheckbox;

    @FindBy(id = "txtSerial")
    private WebElement inputSerial;

    @Override
    protected String getLeftMenuCssSelector() {
        return null;
    }

    @Override
    public Table getMainTable() {
        return getTable("tbl");
    }

    @Override
    public DeviceUpdatePage topMenu(TopMenu value) {
        return (DeviceUpdatePage) super.topMenu(value);
    }

    @Override
    public DeviceUpdatePage assertMainPageIsDisplayed() {
        assertElementIsPresent("tbl");
        return (DeviceUpdatePage) super.assertMainPageIsDisplayed();
    }

    @Override
    public DeviceUpdatePage selectItem(String text) {
        return (DeviceUpdatePage) super.selectItem(text);
    }

    @Override
    public DeviceUpdatePage pause(int millis) {
        return (DeviceUpdatePage) super.pause(millis);
    }

    @Override
    public DeviceUpdatePage selectItem(Table table, String text, int startFromRow) {
        List<Integer> list = table.getRowsWithText(text);
        for (int i : list) {
            if (i >= startFromRow) {
                table.clickOn(i, 0);
                break;
            }
        }
        waitForUpdate();
        return this;
    }

    @Override
    public DeviceUpdatePage newViewButton() {
        return (DeviceUpdatePage) super.newViewButton();
    }

    @Override
    public DeviceUpdatePage assertPresenceOfOptions(String comboBoxId, String... options) {
        return (DeviceUpdatePage) super.assertPresenceOfOptions(comboBoxId, options);
    }

    @Override
    public DeviceUpdatePage fillName() {
        return (DeviceUpdatePage) super.fillName();
    }

    @Override
    public DeviceUpdatePage fillName(String name) {
        return (DeviceUpdatePage) super.fillName(name);
    }

    @Override
    public DeviceUpdatePage assertInputHasText(String id, String text) {
        return (DeviceUpdatePage) super.assertInputHasText(id, text);
    }

    @Override
    public DeviceUpdatePage setViewColumns(int startParam, int endParam) {
        return (DeviceUpdatePage) super.setViewColumns(startParam, endParam);
    }

    @Override
    public DeviceUpdatePage selectColumnFilter(String option) {
        return (DeviceUpdatePage) super.selectColumnFilter(option);
    }

    @Override
    public DeviceUpdatePage selectCompare(String option) {
        return (DeviceUpdatePage) super.selectCompare(option);
    }

    @Override
    public DeviceUpdatePage selectFilterModelName(String value) {
        return (DeviceUpdatePage) super.selectFilterModelName(value);
    }

    @Override
    public DeviceUpdatePage filterRecordsCheckbox() {
        return (DeviceUpdatePage) super.filterRecordsCheckbox();
    }

    @Override
    public DeviceUpdatePage selectView(String value) {
        return (DeviceUpdatePage) super.selectView(value);
    }

    @Override
    public DeviceUpdatePage editButton() {
        return (DeviceUpdatePage) super.editButton();
    }

    @Override
    public DeviceUpdatePage clickOnTable(String id, int row, int column, int tagNum) {
        return (DeviceUpdatePage) super.clickOnTable(id, row, column, tagNum);
    }

    @Override
    public DeviceUpdatePage addSubFilter() {
        return (DeviceUpdatePage) super.addSubFilter();
    }

    @Override
    public DeviceUpdatePage andRadioButton() {
        return (DeviceUpdatePage) super.andRadioButton();
    }

    @Override
    public DeviceUpdatePage deleteFilter() {
        return (DeviceUpdatePage) super.deleteFilter();
    }

    public DeviceUpdatePage defaultViewForCurrentUserCheckbox() {
        defaultViewCheckbox.click();
        return this;
    }

    @Override
    public DeviceUpdatePage resetView() {
        return (DeviceUpdatePage) super.resetView();
    }

    public DeviceUpdatePage inputSerial() {
        inputSerial.sendKeys(getSerial());
        waitUntilButtonIsEnabled(START);
        return this;
    }

    public void assertDuplicateNameErrorIsDisplayed() {
        setImplicitlyWait(0);
        List<WebElement> list = driver.findElements(By.id("lblNameInvalid"));
        setDefaultImplicitlyWait();
        if (list.size() == 1) {
            return;
        }
        String warn = "Error message 'This name is already in use' not found on current page!";
        logger.warn(warn);
        throw new AssertionError(warn);
    }

    public void checkFiltering(String filter) {
        WebElement comboBox = filter.equals("Manufacturer") ? filterManufacturerComboBox : filterModelNameComboBox;
        List<String> optionList = getOptionList(comboBox);
        optionList.remove("All");
        for (String option : optionList) {
            selectComboBox(comboBox, option);
            waitForUpdate();
            Set<String> itemSet = new HashSet<>(Arrays.asList(getMainTable().getColumn(filter)));
            if (itemSet.size() > 1 || (itemSet.size() == 1 && !itemSet.iterator().next().equals(option))) {
                throw new AssertionError("Column '" + filter + "' has unexpected content!\n"
                        + "Expected: " + option + ", but found: " + itemSet);
            }
        }

    }

    public void assertChangingView() {
        selectView("Default");
        String[] defColumns = getMainTable().getRow(0);
        List<String> optList = getOptionList(filterViewComboBox);
        for (String option : optList) {
            if (option.equals("Default")) {
                continue;
            }
            selectView(option);
            String[] newColumns = getMainTable().getRow(0);
            if (!Arrays.deepEquals(newColumns, defColumns)) {
//                System.out.println(option + ": OK\n" + Arrays.deepToString(defColumns) + "\n" + Arrays.deepToString(newColumns));
                return;
            }
//            System.out.println(option + ": Fail\n" + Arrays.deepToString(defColumns) + "\n" + Arrays.deepToString(newColumns));
        }
        throw new AssertionError("View has not been changed! Make sure that you have enough different view for this test case!");
    }

    public DeviceUpdatePage assertSelectedViewIs(String expectedView) {
        if (getSelectedValue(filterViewComboBox).toLowerCase().equals(expectedView.toLowerCase())) {
            return this;
        }
        throw new AssertionError("Actual and expected view don't match! Expected: " + expectedView
                + "; actual: " + getSelectedValue(filterViewComboBox));
    }

    public DeviceUpdatePage presetFilter(String parameter, String value) {
        topMenu(DEVICE_UPDATE);
        enterToDevice();
        Table table = new Table("tblUserInfo");
        if (!table.isEmpty()) {
            try {
                int rowNum = table.getRowNumberByText(parameter);
                if (rowNum >= 0 && table.getCellText(rowNum, 1).equals(value)) {
                    return this;
                }
            } catch (AssertionError e) {
                System.out.println("filter does not exist, go to create new one");
            }
        }
        clickOn("btnEditUserInfo_lnk");
        switchToFrame(POPUP);
        WebElement saveButton = driver.findElement(By.id("btnSaveUsr_btn"));
        setUserInfo(parameter, value);
        waitUntilElementIsEnabled("btnSaveUsr_btn");
        saveButton.click();
        okButtonPopUp();
        pause(500);
        return this;
    }

    public void createPreconditionsForSorting() {
        waitForUpdate();
        Table mainTable = getMainTable();
        String[] serials = mainTable.getColumn("Serial");
        for (String serial : serials) {
            mainTable.clickOn(serial);
            clickOn("btnEditUserInfo_lnk");
            switchToFrame(POPUP);
            Table userInfoTable = getTable("tblMain", 18, true);
            String[] items = userInfoTable.getWholeColumn(0);
            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                if (userInfoTable.getInputText(i, 1).isEmpty()) {
                    setUserInfo(userInfoTable, item, getRandomStringValue(10));
                }
            }
            waitUntilElementIsEnabled("btnSaveUsr_btn");
            saveButton.click();
            okButtonPopUp();
            pause(500);
            waitForUpdate();
            topMenu(DEVICE_UPDATE);
            mainTable = getMainTable();
        }
    }

    public DeviceUpdatePage enterToDevice() {
        try {
            getMainTable().clickOn(getSerial());
            pause(500);
        } catch (AssertionError e) {
            selectComboBox(itemsOnPageComboBox, "200");
            waitForUpdate();
            getTable("tbl").clickOn(getSerial());
        }
        waitForUpdate();
        return this;
    }

    public void setUserInfo(String paramName, String value) {
        setUserInfo(new Table("tblMain"), paramName, value);
    }

    public void setUserInfo(Table table, String paramName, String value) {
        int rowNum = table.getRowNumberByText(0, paramName);
        if (rowNum < 0) {
            throw new AssertionError("Parameter name '" + paramName + "' not found");
        }
        WebElement paramCell = table.getCellWebElement(rowNum, 1);
        if (props.getProperty("browser").equals("edge")) {
            BasePage.scrollToElement(paramCell);
        }
        WebElement input = paramCell.findElement(By.tagName("input"));
        input.clear();
        pause(300);
        input.sendKeys(value);
    }

    public DeviceUpdatePage globalButtons(GlobalButtons button) {
        clickGlobalButtons(button);
        return this;
    }

    @Override
    public DeviceUpdatePage okButtonPopUp() {
        return (DeviceUpdatePage) super.okButtonPopUp();
    }

    @Override
    public DeviceUpdatePage cancelButtonPopUp() {
        return (DeviceUpdatePage) super.cancelButtonPopUp();
    }

    public DeviceUpdatePage leftMenu(Left item) {
        switchToFrame(ROOT);
        getTable("tblLeftMenu").clickOn(item.value);
        waitForUpdate();
        switchToFrame(DESKTOP);
        return this;
    }

    public DeviceUpdatePage selectAnyDevice() { //except target device
        Table table = getMainTable();
        String[] serials = table.getColumn("Serial");
        for (int i = 0; i < serials.length; i++) {
            String serial = serials[i];
            if (!getSerial().equals(serial)) {
                table.clickOn(i + 1, 0);
                parameterSet = new HashSet<>();
                parameterSet.add(serial);
                return this;
            }
        }
        throw new AssertionError("there are no suitable devices to be selected!");
    }

    public DeviceUpdatePage assertAbsenceOfValue() {
        return (DeviceUpdatePage) super.assertAbsenceOfValue("tbl", parameterSet.iterator().next());
    }

    public DeviceUpdatePage assertTraceWindowIsOpened() {
        switchToNewWindow();
        assertEquals(findElement("lblTitle").getText(), "Trace of: Serial = " + getSerial()
                + " / ID = " + DataBaseConnector.getDeviceId(getSerial()));
        closeNewWindow();
        return this;
    }

    public DeviceUpdatePage saveFileName() {
        switchToFrame(ROOT);
        String message = findElement("spnAlert").getText();
        Pattern datePattern = Pattern.compile("Inventory_.+_(.+)\\)\\.");
        Pattern extPattern = Pattern.compile("\\)\\.([xmlcsv]{3})'");
        Matcher m = datePattern.matcher(message);
        Matcher extM = extPattern.matcher(message);
        if (m.find()) {
            try {
                Date date = CalendarUtil.getDate(m.group(1));
                if (extM.find()) {
                    System.out.println(extM.group(1));
                    if (extM.group(1).equals("csv")) {
                        csvFileTime = date;
                        System.out.println("saved csv");
                    } else if (extM.group(1).equals("xml")) {
                        xmlFileTime = date;
                        System.out.println("saved xml");
                    } else {
                        throw new AssertionError("File extension parsing error!");
                    }
                }
            } catch (ParseException e) {
                System.out.println("Date parsing error! \n" + message);
            }
        }
        return this;
    }

    public void checkSavedExport() {
        switchToFrame(POPUP);
        Table table = getTable("tbl");
        table.assertPresenceOfValue(1, "Report(Inventory_Default_" + CalendarUtil.getCsvFileFormat(csvFileTime) + ").csv");
        table.assertPresenceOfValue(1, "Report(Inventory_Default_" + CalendarUtil.getCsvFileFormat(xmlFileTime) + ").xml");
    }

    public enum Left {
        LIST("List"), DEVICE_INFO("Device Info"), DEVICE_SETTINGS("Device Settings"), ADVANCED_VIEW("Advanced View"),
        PROVISION_MANAGER("Provision Manager"), DEVICE_MONITORING("Device Monitoring"), FILE_DOWNLOAD("File Download"),
        FILE_UPLOAD("File Upload"), DEVICE_DIAGNOSTIC("Device Diagnostics"), CUSTOM_RPC("Custom RPC"),
        DEVICE_HISTORY("Device History"), DEVICE_ACTIVITY("Device Activity");

        private String value;

        Left(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum GlobalButtons implements IGlobalButtons {

        ACTIVATE("btnActivate_btn"),
        ADVANCED_VIEW("btnAdvView_btn"),
        CANCEL("btnCancel_btn"),
        DEACTIVATE("btnDeactivate_btn"),
        DELETE("btnDelete_btn"),
        DELETE_GROUP("btnDeleteView_btn"),
        DUPLICATE("btnDuplicate_btn"),
        EDIT("btnEdit_btn"),
        EXPORTS("btnCompletedExports_btn"),
        EXPORT_TO_CSV("btnExport_btn"),
        EXPORT_TO_XML("btnExport2XML_btn"),
        FINISH("btnFinish_btn"),
        GET_CURRENT("UcDeviceSettingsControls1_btnGetCurrent_btn"),
        NEXT("btnNext_btn"),
        PAUSE("btnPause_btn"),
        REFRESH("btnRefresh_btn"),
        REPROVISION("btnCPEReprovision_btn"),
        PREVIOUS("btnPrev_btn"),
        SAVE("btnSave_btn"),
        SAVE_AND_ACTIVATE("btnSaveActivate_btn"),
        SIMPLE_VIEW("btnTabView_btn"),
        SHOW_ON_MAP("btnMap_btn"),
        SHOW_TRACE("btnShowTrace_btn"),
        START("btnSendUpdate_btn"),
        STOP_TRACE("btnStopTrace_btn"),
        STOP("btnStop_btn"),
        STOP_WITH_RESET("btnStopWithReset_btn"),
        TRACE("btnTrace_btn");

        GlobalButtons(String id) {
            this.id = id;
        }

        private String id;

        public String getId() {
            return id;
        }
    }

}
