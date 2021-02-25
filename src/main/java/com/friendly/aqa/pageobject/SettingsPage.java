package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.IBottomButtons;
import com.friendly.aqa.entities.ILeft;
import com.friendly.aqa.entities.Table;
import com.friendly.aqa.entities.TopMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;

public class SettingsPage extends BasePage {

    String username;
    int userAmount;

    @FindBy(id = "tbSearhText")
    private WebElement searchLoginInput;

    @FindBy(id = "btnShowAllUsers_btn")
    private WebElement showAllButton;

    @FindBy(id = "btnSelect_btn")
    private WebElement selectDomainButton;

    @FindBy(id = "ddlPermissions")
    private WebElement userGroupComboBox;

    @FindBy(id = "lblLocationValue")
    private WebElement selectDomainLink;

    public SettingsPage selectUserGroup(String group) {
        selectComboBox(userGroupComboBox, group);
        return this;
    }

    public SettingsPage fillUsername(String username) {
        inputText(userNameUploadField, username);
        return this;
    }

    public SettingsPage fillPassword(String password) {
        inputText(passwordUploadField, password);
        return this;
    }

    public SettingsPage importUsersFromFile() {
        String inputText = new File("import/user_import.csv").getAbsolutePath();
        importField.sendKeys(inputText);
        return this;
    }

    public void validateUserPresence(String username) {
        Table table = getMainTable();
        if (!table.contains(username)) {
            table.print();
            throw new AssertionError("User '" + username + "' not found in table!");
        }
    }

    public void validateUserAbsence(String username) {
        Table table = getMainTable();
        if (table.contains(username)) {
            table.print();
            throw new AssertionError("User '" + username + "' is still present in table!");
        }
    }

    public SettingsPage selectDomainIfExists() {
        if (elementIsPresent("lblLocationValue")) {
            selectDomainLink.click();
            switchToFrame(FrameSwitch.POPUP);
            Table table = getTable("tblTree");
            WebElement cell = table.getCellWebElement(0, 0);
            cell.findElement(By.tagName("input")).click();
            selectDomainButton.click();
            switchToFrame(FrameSwitch.DESKTOP);
        }
        return this;
    }

    public SettingsPage clickOnUser(String username) {
        getMainTable().clickOn(username);
        return this;
    }

    public SettingsPage selectUser(String username) {
        getMainTable().clickOn(username, 0);
        return this;
    }

    public SettingsPage fillExistingUsername() {
        Table table = getMainTable();
        String column = table.contains("User name") ? "User name" : "Login";
        username = table.getCellText(1, column);
        inputText(searchLoginInput, username);
        return this;
    }

    public SettingsPage search() {
        searchButton.click();
        waitForUpdate();
        return this;
    }

    public SettingsPage showAll() {
        showAllButton.click();
        waitForUpdate();
        return this;
    }

    public void validateSearchingResults() {
        Table table = getMainTable();
        if (table.getTableSize()[0] != 2 || !table.contains(username)) {
            table.print();
            throw new AssertionError("Searched login '" + username + "' not found or more than one result!");
        }
    }

    public SettingsPage saveUserAmount() {
        userAmount = getMainTable().getTableSize()[0];
        return this;
    }

    public void assertAllUsersAreDisplayed() {
        if (getMainTable().getTableSize()[0] != userAmount) {
            throw new AssertionError("Table contains only one username after 'Show all' button pressing!");
        }
    }

    public SettingsPage assertPageIsDisplayed() {
        return (SettingsPage) assertElementsArePresent("lblTitleUsers", "tbSearhText", "cbSearchExactly", "btnSearch_btn",
                "btnShowAllUsers_btn", "tblUsers", "pager2_lblPagerTotal", "ddlPageSizes", "pager2_lblItemsPerPage");
    }

    @Override
    public SettingsPage okButtonPopUp() {
        return (SettingsPage) super.okButtonPopUp();
    }

    @Override
    public SettingsPage topMenu(TopMenu value) {
        return (SettingsPage) super.topMenu(value);
    }

    @Override
    public SettingsPage bottomMenu(IBottomButtons button) {
        return (SettingsPage) super.bottomMenu(button);
    }

    @Override
    public SettingsPage leftMenu(ILeft item) {
        return (SettingsPage) super.leftMenu(item);
    }

    @Override
    public String getMainTableId() {
        return "tblUsers";
    }

    public enum Left implements ILeft {
        ACS_USERS("ACS Users"), USER_MANAGEMENT("User Management");
        private final String value;

        Left(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum BottomButtons implements IBottomButtons {

        ADD("btnAdd_btn"),
        DELETE("btnDelete_btn"),
        DELETE_USER("btnDel_btn"),
        IMPORT("btnImport_btn"),
        SAVE("btnSendUpdate_btn");

        BottomButtons(String id) {
            this.id = id;
        }

        private final String id;

        public String getId() {
            return id;
        }
    }
}
