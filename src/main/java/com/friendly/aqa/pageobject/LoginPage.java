package com.friendly.aqa.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @Override
    public String getMainTableId() {
        return null;
    }

//    @FindBy(id = "txtName")
//    private WebElement username;

    @FindBy(id = "txtPassword")
    private WebElement password;

    @FindBy(className = "button_default")
    private WebElement loginButton;

    public void authenticate(String username, String password) {
        this.nameField.clear();
        this.nameField.sendKeys(username);
        this.password.clear();
        this.password.sendKeys(password);
        loginButton.click();
    }
}

