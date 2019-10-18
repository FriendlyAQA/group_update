package com.friendly.aqa.pageobject;

import org.openqa.selenium.WebDriver;

public class SystemPage extends BasePage {
    public SystemPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getLeftMenuCssSelector() {
        return "topmenu=\"System\"";
    }

    public SystemPage waitForRefresh() {
        super.waitForUpdate();
        return this;
    }
}
