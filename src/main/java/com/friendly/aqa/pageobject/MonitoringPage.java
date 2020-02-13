package com.friendly.aqa.pageobject;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.DESKTOP;
import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.ROOT;

public class MonitoringPage extends BasePage {
    private static Logger logger = Logger.getLogger(MonitoringPage.class);

    @Override
    protected String getLeftMenuCssSelector() {
        return "tr[topmenu='Monitoring']";
    }

    public MonitoringPage topMenu(TopMenu value) {
        return (MonitoringPage) super.topMenu(value);
    }

    public MonitoringPage globalButtons(GlobalButtons button) {
        clickGlobalButtons(button);
        return this;
    }

    @FindBy(id = "ddlView")
    private WebElement filterViewComboBox;

    @FindBy(id = "ddlManuf")
    private WebElement filterManufacturerComboBox;

    @FindBy(id = "ddlModel")
    private WebElement filterModelNameComboBox;

    @FindBy(id = "btnEditView_btn")
    private WebElement editViewButton;

    @FindBy(id = "btnNewView_btn")
    private WebElement newViewButton;

    @FindBy(id = "btnDefaultView_btn")
    private WebElement resetViewButton;

    @FindBy(id = "IsDefaultViewForPublic")
    private WebElement forPublicCheckbox;

    @FindBy(id = "IsDefaultViewForUser")
    private WebElement forUserCheckbox;


    public MonitoringPage newViewButton() {
        newViewButton.click();
        return this;
    }

    public MonitoringPage forPublicCheckbox() {
        forPublicCheckbox.click();
        return this;
    }

    public MonitoringPage forUserCheckbox() {
        forUserCheckbox.click();
        return this;
    }

    public MonitoringPage editViewButton() {
        editViewButton.click();
        return this;
    }

    public MonitoringPage assertMainPageDisplayed() {
        try {
            boolean viewComboBox = filterViewComboBox.isDisplayed() && filterViewComboBox.isEnabled();
            boolean manufacturerComboBox = filterManufacturerComboBox.isDisplayed() && filterManufacturerComboBox.isEnabled();
            boolean modelNameComboBox = filterModelNameComboBox.isDisplayed() && filterModelNameComboBox.isEnabled();
            boolean editViewBtn = editViewButton.isDisplayed() && editViewButton.isEnabled();
            boolean newViewBtn = newViewButton.isDisplayed() && newViewButton.isEnabled();
            boolean resetViewBtn = resetViewButton.isDisplayed() && resetViewButton.isEnabled();
            if (viewComboBox && manufacturerComboBox && modelNameComboBox && editViewBtn && newViewBtn && resetViewBtn) {
                return this;
            }
        } catch (NoSuchElementException e) {
            logger.warn(e.getMessage());
        }
        throw new AssertionError("One or more elements not found on Monitoring tab main page");
    }

    public MonitoringPage selectView(String value) {
        selectComboBox(filterViewComboBox, value);
        waitForUpdate();
        return this;
    }

    public MonitoringPage selectManufacturer(String value) {
        selectComboBox(filterManufacturerComboBox, value);
        return this;
    }

    public MonitoringPage selectModelName(String value) {
        selectComboBox(filterModelNameComboBox, value);
        return this;
    }

    public MonitoringPage selectColumnFilter(String option) {
        selectComboBox(selectColumnFilter, option);
        return this;
    }

    public MonitoringPage compareSelect(String option) {
        selectComboBox(compareSelect, option);
        return this;
    }

    @Override
    public MonitoringPage assertButtonsAreEnabled(boolean enabled, GlobalButtons... buttons) {
        return (MonitoringPage) super.assertButtonsAreEnabled(enabled, buttons);
    }

    @Override
    public MonitoringPage assertButtonsArePresent(GlobalButtons... buttons) {
        return (MonitoringPage) super.assertButtonsArePresent(buttons);
    }

    @Override
    public MonitoringPage fillName() {
        return (MonitoringPage) super.fillName();
    }

    @Override
    public MonitoringPage fillName(String name) {
        return (MonitoringPage) super.fillName(name);
    }

    @Override
    public MonitoringPage addFilter() {
        return (MonitoringPage) super.addFilter();
    }

    @Override
    public MonitoringPage okButtonPopUp() {
        return (MonitoringPage) super.okButtonPopUp();
    }

    public MonitoringPage assertElementIsPresent(String id) {
        return (MonitoringPage) super.assertElementIsPresent(id);
    }

    public MonitoringPage leftMenu(Left item) {
        switchToFrame(ROOT);
        leftMenuClick(item.getValue());
        waitForUpdate();
        switchToFrame(DESKTOP);
        return this;
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
