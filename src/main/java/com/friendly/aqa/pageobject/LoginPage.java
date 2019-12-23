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

    public void authenticate(String username, String password) {
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        loginButton.click();
    }
}

