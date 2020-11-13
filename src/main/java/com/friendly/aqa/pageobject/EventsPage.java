package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.*;
import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.DiscManager;
import com.friendly.aqa.utils.Timer;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;

public class EventsPage extends BasePage {

    @FindBy(id = "tbName")
    private WebElement eventNameField;

    @FindBy(id = "btnSelectDevices_btn")
    private WebElement selectButton;

    @FindBy(id = "btnNewView_btn")
    protected WebElement newGroupButton;

    public EventsPage selectManufacturer() {
        return (EventsPage) selectManufacturer(getManufacturer());
    }

    public EventsPage selectModelName() {
        return (EventsPage) selectModel(getModelName());
    }

    public EventsPage setEvent(Event event) {
        return (EventsPage) setEvent(event, new Table("tblDataEvents"));
    }

    public EventsPage setEvents(int amount, Event example) {
        return (EventsPage) super.setEvents(amount, example, getTable("tblDataEvents"));
    }

    private Table getMainTableWithText(String text) {
        return getMainTableWithText(text, "Updated");
    }

    public EventsPage validateEvents() {
        assertEquals(readEvents("tblDataEvents"), eventMap, "Events comparison error!");
        return this;
    }

    @Override
    public EventsPage expandEvents() {
        return (EventsPage) super.expandEvents();
    }

    public EventsPage assertLogfileContainsEventSoap() {
        if (eventMap.size() > 1) {
            throw new AssertionError("Multiple event validation not supported");
        }
        Timer timer = new Timer(Integer.parseInt(props.getProperty("soap_searching_time")) * 1000);
        while (!timer.timeout()) {
            if (DiscManager.isEventFound()) {
                System.out.println("Soap appears within " + (timer.stop() / 1000) + " s");
                return this;
            }
            pause(100);
            Thread.yield();
        }
        System.out.println("SOAP not found,  going to deactivate event");
        stopEvent();
        throw new AssertionError("Appropriate SOAP not found in server.log file!");
    }

    public EventsPage newGroupButton() {
        newGroupButton.click();
        waitForUpdate();
        return this;
    }

    @Override
    public EventsPage selectSendTo(String value) {
        return (EventsPage) super.selectSendTo(value);
    }

    @Override
    public EventsPage assertButtonIsEnabled(boolean expectedActive, String id) {
        return (EventsPage) super.assertButtonIsEnabled(expectedActive, id);
    }

//    @Override
//    public EventsPage clickIfPresent(IBottomButtons button) {
//        return (EventsPage) super.clickIfPresent(button);
//    }

    public EventsPage createPreconditions() {
        deleteAllCustomViews();
        Table table = getMainTable();
        if (!table.isEmpty()) {
            setSinglePage();
            table.clickOn(0, 0);
            bottomMenu(DELETE);
            okButtonPopUp();
        }
        leftMenu(Left.NEW)
                .selectManufacturer()
                .selectModelName().
                fillName();
        deleteAllGroups()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", null, "3", "1:hours"))
                .bottomMenu(SAVE)
                .okButtonPopUp()
                .waitForStatus("Not active", 1);
        return this;
    }

    @Override
    public EventsPage cancelButtonPopUp() {
        switchToFrame(POPUP);
        showPointer(cancelButtonPopUp).click();
        waitForUpdate();
        switchToFrame(DESKTOP);
        return this;
    }

    public EventsPage deleteAllGroups() {
        waitForUpdate();
        List<String> optList = getOptionList(sendToComboBox);
        String testName = BaseTestCase.getTestName();
        for (String opt : optList) {
            if (opt.matches(testName.substring(0, testName.length() - 3) + "\\d{3}$")) {
                selectComboBox(sendToComboBox, opt);
                waitForUpdate();
                switchToFrame(ROOT);
                while (okButtonAlertPopUp.isDisplayed()) {
                    showPointer(okButtonAlertPopUp).click();
                    waitForUpdate();
                }
                switchToFrame(DESKTOP);
                editButton.click();
                bottomMenu(DeviceUpdatePage.BottomButtons.DELETE_VIEW);
                okButtonPopUp();
                waitForUpdate();
            }
        }
        return this;
    }

    public EventsPage selectButton() {
        selectButton.click();
        waitForUpdate();
        return this;
    }

    public void stopEvent(String eventName) {
        topMenu(TopMenu.EVENTS);
        selectItem(eventName);
        bottomMenu(DEACTIVATE);
        okButtonPopUp();
        waitForStatus("Not active", 5);
    }

    public void stopEvent() {
        stopEvent(BaseTestCase.getTestName());
    }

    public EventsPage triggerConnectionRequest() {
        topMenu(TopMenu.DEVICE_UPDATE);
        DeviceUpdatePage duPage = new DeviceUpdatePage();
        duPage.getMainTableWithText(getSerial(), "Last connection").clickOn(getSerial());
        duPage.recheckStatus();
        return this;
    }

    public EventsPage enterIntoItem(String itemName) {
        waitForUpdate();
        Table table = getMainTableWithText(itemName);
        table.clickOn(itemName);
        waitForUpdate();
        return this;
    }

    public EventsPage enterIntoItem() {
        return enterIntoItem(BaseTestCase.getTestName());
    }

    @Override
    public EventsPage newViewButton() {
        return (EventsPage) super.newViewButton();
    }

    @Override
    public EventsPage assertButtonsAreEnabled(boolean enabled, IBottomButtons... buttons) {
        return (EventsPage) super.assertButtonsAreEnabled(enabled, buttons);
    }

    @Override
    public EventsPage fillName() {
        eventNameField.sendKeys(BaseTestCase.getTestName());
        return this;
    }

    public EventsPage fillGroupName() {
        nameField.sendKeys(BaseTestCase.getTestName());
        return this;
    }

    public EventsPage fillViewName() {
        return fillGroupName();
    }

    public EventsPage fillGroupName(String name) {
        nameField.sendKeys(name);
        return this;
    }

    public EventsPage fillViewName(String name) {
        return fillGroupName(name);
    }

    @Override
    public EventsPage okButtonPopUp() {
        return (EventsPage) super.okButtonPopUp();
    }

    @Override
    public EventsPage selectSendTo() {
        selectSendTo("Individual");
        selectButton.click();
        waitForUpdate();
        switchToFrame(POPUP);
        getTable("tblDevices").clickOn(getSerial());
        selectButton.click();
        waitForUpdate();
        switchToFrame(DESKTOP);
        return this;
    }

    @Override
    public EventsPage leftMenu(ILeft item) {
        return (EventsPage) super.leftMenu(item);
    }

    @Override
    public EventsPage topMenu(TopMenu value) {
        return (EventsPage) super.topMenu(value);
    }

    @Override
    public EventsPage immediately() {
        return (EventsPage) super.immediately();
    }

    @Override
    public EventsPage bottomMenu(IBottomButtons button) {
        if (button == SAVE_AND_ACTIVATE && eventMap != null && eventMap.size() == 1) {
            DiscManager.startReading();
            while (!DiscManager.isReady()) {
                pause(100);
                Thread.yield();
            }
            DiscManager.setRegex(getSingleEvent().getRegex());
        }
        return (EventsPage) super.bottomMenu(button);
    }

    public EventsPage assertAdvancedViewApplied() {
        waitForUpdate();
        if (elementIsPresent("tabsParameters_tblTabs")) {
            throw new AssertionError("Setting tabs are still present on page!");
        }
        if (findElement("tblTree").findElements(By.tagName("img")).size() == 0) {
            throw new AssertionError("Object tree does not have no one expander");
        }
        assertButtonsArePresent(SIMPLE_VIEW);
        return this;
    }

    public EventsPage assertSimpleViewApplied() {
        waitForUpdate();
        if (elementIsAbsent("tabsParameters_tblTabs")) {
            throw new AssertionError("Setting tabs are still present on page!");
        }
        WebElement tree = findElement("tblTree");
        setImplicitlyWait(0);
        if (tree.findElements(By.tagName("img")).size() != 0) {
            throw new AssertionError("Object tree does not have no one expander");
        }
        setDefaultImplicitlyWait();
        assertButtonsArePresent(ADVANCED_VIEW);
        return this;
    }

    private Event getSingleEvent() {
        return eventMap.values().iterator().next();
    }

    @Override
    public EventsPage selectMainTab(String tab) {
        return (EventsPage) super.selectMainTab(tab);
    }

    public enum Left implements ILeft {
        VIEW("View"), IMPORT("Import"), NEW("New");
        private final String value;

        Left(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
