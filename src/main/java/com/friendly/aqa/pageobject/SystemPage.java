package com.friendly.aqa.pageobject;

public class SystemPage extends BasePage {

    @Override
    protected String getLeftMenuCssSelector() {
        return "topmenu=\"System\"";
    }

    public SystemPage waitForRefresh() {
        super.waitForUpdate();
        return this;
    }
}
