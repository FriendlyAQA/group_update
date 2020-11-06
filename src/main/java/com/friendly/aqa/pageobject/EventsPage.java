package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.*;
import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.DiscManager;
import com.friendly.aqa.utils.Timer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.*;

import static com.friendly.aqa.entities.BottomButtons.REFRESH;
import static com.friendly.aqa.entities.BottomButtons.SAVE_AND_ACTIVATE;
import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;

public class EventsPage extends BasePage {

    @FindBy(id = "tbName")
    private WebElement eventNameField;

    @FindBy(id = "btnSelectDevices_btn")
    private WebElement selectButton;

    public EventsPage selectManufacturer() {
        return (EventsPage) selectManufacturer(getManufacturer());
    }

    public EventsPage selectModelName() {
        return (EventsPage) selectModel(getModelName());
    }

    public EventsPage setEvent(Event event) {
        return (EventsPage) setEvent(event, new Table("tblDataEvents"));
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
        Timer timer = new Timer(60000);
        while (!timer.timeout()) {
            if (DiscManager.isEventFound()) {
                return this;
            }
            pause(100);
            Thread.yield();
        }
        throw new AssertionError("Event entry not found in server.log file!");
    }

    public void checkRefreshPage() {
        assertMainPageIsDisplayed();
        String beforeSorting = null;
        String beforeSortingArrowDirection = null;
        WebElement mainTable;
        if ((mainTable = findElement(getMainTableId())).isDisplayed()) {
            WebElement arrow = mainTable.findElement(By.tagName("img"));
            beforeSorting = arrow.findElement(By.xpath("preceding-sibling::*")).getText();
            beforeSortingArrowDirection = arrow.getAttribute("src");
        }
        List<String> before = new ArrayList<>(Arrays.asList(getSelectedOption(filterViewComboBox), getSelectedOption(filterManufacturerComboBox), getSelectedOption(filterModelNameComboBox)));
        bottomMenu(REFRESH);
        assertMainPageIsDisplayed();
        List<String> after = new ArrayList<>(Arrays.asList(getSelectedOption(filterViewComboBox), getSelectedOption(filterManufacturerComboBox), getSelectedOption(filterModelNameComboBox)));
        assertEquals(after, before, "Values of bottom dropdowns are changed!");
        if (beforeSorting != null) {
            mainTable = findElement(getMainTableId());
            WebElement arrow = mainTable.findElement(By.tagName("img"));
            String afterSorting = arrow.findElement(By.xpath("preceding-sibling::*")).getText();
            String afterSortingArrowDirection = arrow.getAttribute("src");
            assertEquals(afterSorting, beforeSorting, "Sorting arrow changed location!");
            assertEquals(afterSortingArrowDirection, beforeSortingArrowDirection, "Sorting arrow direction changed!");
        }
    }

    public EventsPage enterIntoItem(String itemName) {
        Table table = getMainTableWithText(itemName);
        table.clickOn(itemName);
        waitForUpdate();
        return this;
    }

    public EventsPage enterIntoItem() {
        return enterIntoItem(BaseTestCase.getTestName());
    }

    @Override
    public EventsPage fillName() {
        eventNameField.sendKeys(BaseTestCase.getTestName());
        return this;
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
