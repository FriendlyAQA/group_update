package com.friendly.aqa.pageobject;

import com.friendly.aqa.entities.*;
import com.friendly.aqa.test.BaseTestCase;
import com.friendly.aqa.utils.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.util.List;

import static com.friendly.aqa.entities.BottomButtons.*;
import static com.friendly.aqa.entities.TopMenu.EVENTS;
import static com.friendly.aqa.pageobject.BasePage.FrameSwitch.*;
import static com.friendly.aqa.pageobject.DeviceUpdatePage.Left.DEVICE_INFO;
import static com.friendly.aqa.pageobject.EventsPage.Left.*;

public class EventsPage extends BasePage {

    @FindBy(id = "tbName")
    private WebElement eventNameField;

    @FindBy(id = "btnSelectDevices_btn")
    private WebElement selectButton;

    @FindBy(id = "btnNewView_btn")
    protected WebElement newGroupButton;

    @FindBy(id = "btnSave_btn")
    protected WebElement saveButton;

    @FindBy(id = "imgSpoilerParams")
    protected WebElement parametersMonitoringExpander;


    public EventsPage expandParametersMonitor() {
        if (parametersMonitoringExpander.getAttribute("src").endsWith("expand.png")) {
            parametersMonitoringExpander.click();
        }
        return this;
    }

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

    @Override
    public EventsPage selectDiagnostic(String value) {
        return (EventsPage) super.selectDiagnostic(value);
    }

    @Override
    public EventsPage inputHost(String text) {
        return (EventsPage) super.inputHost(text);
    }

    @Override
    public EventsPage inputNumOfRepetitions(String text) {
        return (EventsPage) super.inputNumOfRepetitions(text);
    }

    public EventsPage assertLogfileContainsEventSoap() {
        System.out.println("Start looking for SOAP...(" + BaseTestCase.getTestName() + ")");
        Timer timer = new Timer(Long.parseLong(props.getProperty("soap_searching_time")) * 1000);
        boolean firstTime = true;
        while (!timer.timeout()) {
            if (DiscManager.isEventFound()) {
                System.out.println("SOAP appeared within " + (timer.stop() / 1000) + " s");
                return this;
            }
            if (timer.halfTime() && firstTime) {
                forceProfileApplying();
                firstTime = false;
            }
            pause(100);
            Thread.yield();
        }
        System.out.println("SOAP not found, going to deactivate event");
        stopEvent();
        throw new AssertionError("Appropriate SOAP not found in server.log file!");
    }

    private void forceProfileApplying() {
        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ftac=\"http://ftacs.com/\">" +
                "<soapenv:Header/><soapenv:Body><ftac:reprovisionCPE><cpeList><id>" + DataBaseConnector.getDeviceId(getSerial()) +
                "</id></cpeList><sendProfile>1</sendProfile><sendCPEProvision>0</sendCPEProvision><sendCPEProvisionAttribute>0</sendCPEProvisionAttribute>" +
                "<user>" + props.getProperty("ui_user") + "</user></ftac:reprovisionCPE></soapenv:Body></soapenv:Envelope>";
        try {
            HttpConnector.sendSoapRequest(request);
            System.out.println("Profile applying forced");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EventsPage newGroupButton() {
        showPointer(newGroupButton).click();
//        newGroupButton.click();
        waitForUpdate();
        return this;
    }

    public EventsPage saveButton() {
        showPointer(saveButton).click();
//        saveButton.click();
        pause(500);
        waitForUpdate();
        return this;
    }

    public EventsPage validateAddedEventTasks(String eventName) {
        validateAddedTasks(new Table("tblDataEvents"), eventName);
        return this;
    }

    public EventsPage validateAddedEventTask(String eventName, String taskName) {
        waitForUpdate();
        validateAddedTask(new Table("tblDataEvents"), eventName, taskName);
        cancelButtonPopUp();
        return this;
    }

    public EventsPage validateAddedEventTask(String eventName, String parameter, String value) {
        validateAddedTask(new Table("tblDataEvents"), eventName, parameter, value);
        return this;
    }

    @Override
    public EventsPage manuallyDownloadRadioButton() {
        return (EventsPage) super.manuallyDownloadRadioButton();
    }

    @Override
    public EventsPage selectDownloadFileType(String type) {
        return (EventsPage) super.selectDownloadFileType(type);
    }

    @Override
    public EventsPage selectUploadFileType(String type) {
        return (EventsPage) super.selectUploadFileType(type);
    }

    @Override
    public EventsPage manuallyUploadRadioButton() {
        return (EventsPage) super.manuallyUploadRadioButton();
    }

    @Override
    public EventsPage fillUploadUrl() {
        return (EventsPage) super.fillUploadUrl();
    }

    @Override
    public EventsPage fillDownloadUrl() {
        return (EventsPage) super.fillDownloadUrl();
    }

    @Override
    public EventsPage fillUsername() {
        return (EventsPage) super.fillUsername();
    }

    @Override
    public EventsPage fillUploadUserName() {
        return (EventsPage) super.fillUploadUserName();
    }

    @Override
    public EventsPage fillUploadPassword() {
        return (EventsPage) super.fillUploadPassword();
    }

    @Override
    public EventsPage fillPassword() {
        return (EventsPage) super.fillPassword();
    }

    @Override
    public EventsPage selectAction(String action) {
        return (EventsPage) super.selectAction(action);
    }

    @Override
    public EventsPage selectMethod(String value) {
        return (EventsPage) super.selectMethod(value);
    }

    @Override
    public EventsPage selectSendTo(String value) {
        return (EventsPage) super.selectSendTo(value);
    }

    @Override
    public EventsPage addTask(String task) {
        return (EventsPage) super.addTask(task);
    }

    @Override
    public EventsPage assertButtonIsEnabled(boolean expectedActive, String id) {
        return (EventsPage) super.assertButtonIsEnabled(expectedActive, id);
    }

    @Override
    public EventsPage validateParametersMonitor() {
        return (EventsPage) super.validateParametersMonitor();
    }

    @Override
    public EventsPage getParameter(int row, int column) {
        return (EventsPage) super.getParameter(row, column);
    }

    @Override
    public EventsPage selectBranch(String branch) {
        return (EventsPage) super.selectBranch(branch);
    }


    public EventsPage validateAddedMonitorTasks() {
        validateAddedTasks(new Table("tblDataParams"), null);
        return this;
    }

    public EventsPage validateAddedMonitorTask(String taskName) {
        validateAddedTask(new Table("tblDataParams"), null, taskName);
        cancelButtonPopUp();
        return this;
    }

    public EventsPage validateAddedMonitorTask(String parameter, String value) {
        validateAddedTask(new Table("tblDataParams"), null, parameter, value);
        return this;
    }

    public EventsPage triggerEventOnParameter() {
        return (EventsPage) super.setParameterOverApi(parametersMonitor.getName(), parametersMonitor.getCurrentValue());
    }

    public void setProfileOverSoap() {
        String prefix = (getProtocolPrefix().equals("tr069") ? "InternetGateway" : "") + "Device.ManagementServer.";
        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ftac=\"http://ftacs.com/\">" +
                "<soapenv:Header/><soapenv:Body><ftac:createProfile><profileList><Profile><fullTree>0</fullTree><name>Precondition_profile</name>" +
                "<productClassGroupId>" + DataBaseConnector.getGroupId(getSerial()) + "</productClassGroupId><profileParameterList>" +
                "<profileParameter><name>" + prefix + "ConnectionRequestPassword</name><value>ftacs</value></profileParameter>" +
                "<profileParameter><name>" + prefix + "ConnectionRequestUsername</name><value>ftacs</value></profileParameter>" +
                "<profileParameter><name>" + prefix + "PeriodicInformEnable</name><value>1</value></profileParameter>" +
                "<profileParameter><name>" + prefix + "PeriodicInformInterval</name><value>10</value></profileParameter>" +
                "</profileParameterList><sendProvision>1</sendProvision><isActive>1</isActive><version>1</version><locationId>0</locationId></Profile>" +
                "</profileList><user>" + props.getProperty("ui_user") + "</user></ftac:createProfile></soapenv:Body></soapenv:Envelope>";
        String expectedResponse = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<soap:Body><ns2:createProfileResponse xmlns:ns2=\"http://ftacs.com/\"/></soap:Body></soap:Envelope>";
        try {
            assertEquals(HttpConnector.sendSoapRequest(request), expectedResponse, "Unexpected response after profile set!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EventsPage setParameter(String paramName, ParameterType option, String value) {
        return (EventsPage) setParameter(new Table("tblParamsValue"), paramName, option, value);
    }

    public EventsPage setParametersMonitor(String parameter, Condition condition, String value, String triggerValue) {
        return setParametersMonitor(parameter, condition, value, triggerValue, false);
    }

    public EventsPage setParametersMonitor(String parameter, Condition condition, String value) {
        return setParametersMonitor(parameter, condition, value, null, false);
    }

    public EventsPage setParametersMonitor(String parameter, Condition condition, String value, String triggerValue, boolean addTask) {
        Table table = getTable("tblDataParams");
        setParametersMonitor(table, parameter, condition, value);
        parametersMonitor.setCurrentValue(triggerValue);
        if (addTask) {
            table.clickOn(parameter, 3);
        }
        return this;
    }

    public EventsPage setEvent(Event event, boolean addTask) {
        return (EventsPage) setEvent(event, new Table("tblDataEvents"), addTask);
    }

    public EventsPage validateAddedEventAction(String eventName, String parameter, String value) {
        validateAddedAction(new Table("tblDataEvents"), eventName, parameter, value);
        cancelButtonPopUp();
        return this;
    }

    public EventsPage createImmediatelyEventOn(String trigger) {
        topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .immediately()
                .selectMainTab(trigger);
        return this;
    }

    public EventsPage createScheduledEventsOn(String trigger) {
        topMenu(EVENTS)
                .leftMenu(NEW)
                .selectManufacturer()
                .selectModelName()
                .fillName()
                .selectSendTo()
                .scheduledTo()
                .setStartWithDelay(10)
                .selectMainTab(trigger);
        return this;
    }

    public EventsPage createPreconditions() {
        deleteAllCustomViews();
        deleteAllMonitoring();
        new DeviceProfilePage().deleteAllProfiles();
        setProfileOverSoap();
        setSinglePage();
        Table table;
        while (!(table = getMainTable()).isEmpty()) {
            table.clickOn(0, 0);
            bottomMenu(DELETE);
            okButtonPopUp();
            waitForUpdate();
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
                .waitForStatus("Not active", 5);
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

    public void deleteEvent() {
        String testName = BaseTestCase.getTestName();
        topMenu(TopMenu.EVENTS);
        selectItem(testName);
        bottomMenu(DELETE);
        okButtonPopUp();
        getMainTable().assertAbsenceOfValue(testName);
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
        String request = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ftac=\"http://ftacs.com/\">" +
                "<soapenv:Header/><soapenv:Body><ftac:push><cpe>" + DataBaseConnector.getDeviceId(getSerial()) +
                "</cpe></ftac:push></soapenv:Body></soapenv:Envelope>";
        try {
            HttpConnector.sendSoapRequest(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        topMenu(TopMenu.DEVICE_UPDATE);
//        DeviceUpdatePage duPage = new DeviceUpdatePage();
//        duPage.getMainTableWithText(getSerial(), "Last connection").clickOn(getSerial());
//        switchToFrame(ROOT);
//        if (!findElement("lblSentCount").getText().equals("0")) {
//            duPage.clearDeviceActivity();
//            duPage.leftMenu(DEVICE_INFO);
//        }
//        switchToFrame(DESKTOP);
//        duPage.recheckStatus();
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
        Table mainTable = getMainTable();
        if (!mainTable.isEmpty() && mainTable.contains(BaseTestCase.getTestName())) {
            selectItem();
            bottomMenu(DELETE);
            okButtonPopUp();
            waitForUpdate();
        }
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
    public EventsPage scheduledTo() {
        return (EventsPage) super.scheduledTo();
    }

    @Override
    public EventsPage waitForStatus(String status, int timeoutSec) {
        return (EventsPage) super.waitForStatus(status, timeoutSec);
    }

    @Override
    public EventsPage bottomMenu(IBottomButtons button) {
        if (button == SAVE_AND_ACTIVATE) {
            boolean event = eventMap != null && eventMap.size() == 1;
            boolean param = parametersMonitor != null;
            if (event || param) {
                DiscManager.startReading();
                while (!DiscManager.isReady()) {
                    pause(100);
                    Thread.yield();
                }
                if (event) {
                    DiscManager.setRegex(getSingleEvent().getRegex());
                } else {
                    DiscManager.setRegex(parametersMonitor.getRegex());
//                    System.out.println("regex:");
//                    System.out.println(parametersMonitor.getRegex());
                }
            }
        }
        return (EventsPage) super.bottomMenu(button);
    }

    public EventsPage setStartWithDelay(int minutes) {
        switchToFrame(DESKTOP);
        String[] date = CalendarUtil.getShiftedTimeBy(minutes).split("\\s");
        String[] time = date[1].split(":");
        executeScript("CalendarPopup_FindCalendar('calDate').SelectDate('" + date[0] + "')");
        scheduledHours.clear();
        scheduledHours.sendKeys(time[0]);
        scheduledMinutes.clear();
        scheduledMinutes.sendKeys(time[1]);
        return this;
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

    public EventsPage setTaskPolicy(int scenario) {
        return (EventsPage) setPolicy(new Table("tblParamsValue"), scenario);
    }

    public EventsPage pause(long millis) {
        return (EventsPage) super.pause(millis);
    }

    private Event getSingleEvent() {
        return eventMap.values().iterator().next();
    }

    @Override
    public EventsPage selectMainTab(String tab) {
        return (EventsPage) super.selectMainTab(tab);
    }

    @Override
    public EventsPage selectTab(String tab) {
        return (EventsPage) super.selectTab(tab);
    }

    public EventsPage selectParametersTab(String tab) {
        getTable("tabsParameters_tblTabs").clickOn(tab);
        waitForUpdate();
        return this;
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
