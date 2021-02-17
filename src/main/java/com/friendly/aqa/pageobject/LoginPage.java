package com.friendly.aqa.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @Override
    public String getMainTableId() {
        return null;
    }

    @FindBy(id = "txtPassword")
    private WebElement password;

    @FindBy(className = "button_default")
    private WebElement loginButton;

    public void authenticate() {
        if (elementIsPresent("lblLoginTitle")){
            this.nameField.clear();
            this.nameField.sendKeys(props.getProperty("ui_user"));
            this.password.clear();
            this.password.sendKeys(props.getProperty("ui_password"));
            loginButton.click();
        }
    }
}

