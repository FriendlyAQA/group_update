package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.TopMenu.SETTINGS;
import static com.friendly.aqa.pageobject.SettingsPage.BottomButtons.*;
import static com.friendly.aqa.pageobject.SettingsPage.Left.ACS_USERS;
import static com.friendly.aqa.pageobject.SettingsPage.Left.USER_MANAGEMENT;

@Listeners(UniversalVideoListener.class)
public class SettingsTests extends BaseTestCase {

    @Test
    public void settings_001() {
        settingsPage
                .topMenu(SETTINGS)
                .leftMenu(ACS_USERS)
                .assertPageIsDisplayed()
                .assertButtonsAreEnabled(true, ADD, IMPORT)
                .assertButtonsAreEnabled(false, DELETE);
    }

    @Test
    public void settings_002() {
        settingsPage
                .topMenu(SETTINGS)
                .leftMenu(ACS_USERS)
                .bottomMenu(ADD)
                .fillUsername("autotest_user")
                .fillPassword("1234")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .validateUserPresence("autotest_user");
    }

    @Test
    public void settings_003() {
        settingsPage
                .topMenu(SETTINGS)
                .leftMenu(ACS_USERS)
                .clickOnUser("autotest_user")
                .fillPassword("12345")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .validateUserPresence("autotest_user");
    }

    @Test
    public void settings_004() {
        settingsPage
                .topMenu(SETTINGS)
                .leftMenu(ACS_USERS)
                .selectUser("autotest_user")
                .bottomMenu(DELETE)
                .okButtonPopUp()
                .validateUserAbsence("autotest_user");
    }

    @Test
    public void settings_005() {
        settingsPage
                .topMenu(SETTINGS)
                .leftMenu(ACS_USERS)
                .fillExistingUsername()
                .search()
                .validateSearchingResults();
    }

    @Test
    public void settings_007() {
        settingsPage
                .topMenu(SETTINGS)
                .leftMenu(ACS_USERS)
                .saveUserAmount()
                .fillExistingUsername()
                .search()
                .showAll()
                .assertAllUsersAreDisplayed();
    }

    @Test
    public void settings_008() {
        settingsPage
                .topMenu(SETTINGS)
                .leftMenu(ACS_USERS)
                .importUsersFromFile()
                .validateUserPresence("imported_user");
    }

    @Test
    public void settings_009() {
        settingsPage
                .topMenu(SETTINGS)
                .leftMenu(USER_MANAGEMENT)
                .assertPageIsDisplayed()
                .assertButtonsAreEnabled(true, ADD)
                .assertButtonsAreEnabled(false, DELETE_USER);
    }

    @Test
    public void settings_010() {
        settingsPage
                .topMenu(SETTINGS)
                .leftMenu(USER_MANAGEMENT)
                .bottomMenu(ADD)
                .fillUsername("autotest_user")
                .fillPassword("123asd56")
                .selectDomainIfExists()
                .selectUserGroup("admin")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .validateUserPresence("autotest_user");
    }

    @Test
    public void settings_011() {
        settingsPage
                .topMenu(SETTINGS)
                .leftMenu(USER_MANAGEMENT)
                .clickOnUser("autotest_user")
                .fillPassword("ert754cd")
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .validateUserPresence("autotest_user");
    }

    @Test
    public void settings_012() {
        settingsPage
                .topMenu(SETTINGS)
                .leftMenu(USER_MANAGEMENT)
                .selectUser("autotest_user")
                .bottomMenu(DELETE_USER)
                .okButtonPopUp()
                .validateUserAbsence("autotest_user");
    }

    @Test
    public void settings_013() {
        settingsPage
                .topMenu(SETTINGS)
                .leftMenu(USER_MANAGEMENT)
                .fillExistingUsername()
                .search()
                .validateSearchingResults();
    }

    @Test
    public void settings_014() {
        settingsPage
                .topMenu(SETTINGS)
                .leftMenu(USER_MANAGEMENT)
                .saveUserAmount()
                .fillExistingUsername()
                .search()
                .showAll()
                .assertAllUsersAreDisplayed();
    }

}
