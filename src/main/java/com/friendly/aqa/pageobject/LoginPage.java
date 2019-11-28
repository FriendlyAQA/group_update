package com.friendly.aqa.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

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

    public void authenticate(String username, String password) {
        fillPasswordFiled(password);
        fillUsernameFiled(username);
        loginButton.click();
    }
}

