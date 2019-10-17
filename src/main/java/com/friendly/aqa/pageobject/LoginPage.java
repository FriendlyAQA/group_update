package com.friendly.aqa.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getLeftMenuCssSelector() {
        return null;
    }

    @FindBy(id = "txtName")
    private WebElement username;

    @FindBy(id = "txtPassword")
    private WebElement password;

    @FindBy(className = "button_default")
    private WebElement loginButton;

    private void fillUsernameFiled(String username) {
        this.username.sendKeys(username);
    }

    private void fillPasswordFiled(String username) {
        this.password.sendKeys(username);
    }

    public SystemPage authenticate(String username, String password) {
        fillPasswordFiled(password);
        fillUsernameFiled(username);
        loginButton.click();
        return new SystemPage(driver);
    }
}

