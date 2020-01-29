package com.friendly.aqa.test;

import com.automation.remarks.testng.UniversalVideoListener;
import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.CalendarUtil;
import com.friendly.aqa.utils.HttpConnector;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.friendly.aqa.pageobject.BasePage.*;
import static com.friendly.aqa.pageobject.GlobalButtons.*;
import static com.friendly.aqa.pageobject.GroupUpdatePage.Left.*;
import static com.friendly.aqa.pageobject.TopMenu.GROUP_UPDATE;
import static com.friendly.aqa.utils.Table.Conditions.EQUAL;
import static com.friendly.aqa.utils.Table.Parameter.VALUE;
import static org.testng.Assert.*;

@Listeners(UniversalVideoListener.class)
public class GroupUpdateTR181Tests extends BaseTestCase {

    @Test
    public void tr181_gu_001() {
        guPage.deleteAll();
        guPage.topMenu(GROUP_UPDATE);
        waitForUpdate();
        assertTrue(guPage.mainTableIsAbsent());
    }
}
