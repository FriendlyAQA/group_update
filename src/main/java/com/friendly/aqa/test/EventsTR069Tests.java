package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.entities.Event;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.friendly.aqa.entities.BottomButtons.SAVE_AND_ACTIVATE;
import static com.friendly.aqa.entities.TopMenu.EVENTS;
import static com.friendly.aqa.pageobject.EventsPage.Left.NEW;

@Listeners(UniversalVideoListener.class)
public class EventsTR069Tests extends BaseTestCase {

    @Test
    public void tr069_ev_001() {
        evPage
                .topMenu(EVENTS)
                .assertMainPageIsDisplayed();
    }

    @Test
    public void tr069_ev_002() {
        evPage
                .topMenu(EVENTS)
                .checkRefreshPage();
    }

    @Test
    public void tr069_ev_030() {
        evPage
                .topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab("Events")
                .setEvent(new Event("2 PERIODIC", null, "1", "3:hours"))
                .bottomMenu(SAVE_AND_ACTIVATE)
                .okButtonPopUp()
                .enterIntoItem()
                .expandEvents()
                .validateEvents()
                .assertLogfileContainsEventSoap();
    }


}
