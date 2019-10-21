package com.friendly.aqa.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public abstract class BasePage {
    WebDriver driver;

    BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
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

    public void logOut() {
        driver.switchTo().defaultContent();
        logOutButton.click();
    }

    void waitForUpdate() {
        driver.switchTo().defaultContent();
        long start = System.currentTimeMillis();
        try {
            new FluentWait<>(driver).withMessage("Element was not found")
                    .withTimeout(Duration.ofSeconds(1))
                    .pollingEvery(Duration.ofMillis(10))
                    .ignoring(org.openqa.selenium.NoSuchElementException.class)
                    .until(ExpectedConditions.visibilityOf(spinningWheel));
            System.out.println("wheel is visible " + (System.currentTimeMillis() - start));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("wheel not found" + (System.currentTimeMillis() - start));
        }
        new FluentWait<>(driver).withMessage("Element was not found")
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(100))
                .until(ExpectedConditions.invisibilityOf(spinningWheel));
        System.out.println("wheel is hidden " + (System.currentTimeMillis() - start));
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

    public void topMenu(TopMenu value) {
        driver.switchTo().defaultContent();
        for (WebElement btn : topMenuTable.findElements(By.tagName("td"))) {
            if (btn.getText().equals(value.getItem())) {
                btn.click();
            }
        }
    }

    void clickGlobalButtons(GlobalButtons button) {
        switchToFrameButtons();
        WebElement btn = buttonTable.findElement(By.id(button.getId()));
        new FluentWait<>(driver).withMessage("Element was not found")
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(100))
                .until(ExpectedConditions.elementToBeClickable(btn))
                .click();
        switchToFrameDesktop();
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
}


