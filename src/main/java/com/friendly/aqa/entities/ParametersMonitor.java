package com.friendly.aqa.entities;

import com.friendly.aqa.pageobject.BasePage;
import com.friendly.aqa.utils.DataBaseConnector;

public class ParametersMonitor {
    String name;
    Condition condition;
    String value;
    String currentValue;

    public ParametersMonitor(String name, Condition condition, String value) {
        this.name = name;
        this.condition = condition;
        this.value = value;
        this.currentValue = "";
    }

    public String getName() {
        return name;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public Condition getCondition() {
        return condition;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRegex() {
        String serial = BasePage.getSerial();
        String id = DataBaseConnector.getDeviceId(serial);
        return "^<soapenv:Envelope.+?<ftacs:cpeParameterMonitorResult><cpeParameterMonitor><cpeId>" + id + "</cpeId><serialNumber>" +
                serial + "</serialNumber>.+?<parameterName>" + name + "</parameterName><valueFromMonitor>" + value +
                "</valueFromMonitor><currentValue>" + currentValue + "</currentValue><condition>" + condition +
                "</condition>.+?</cpeParameterMonitor></ftacs:cpeParameterMonitorResult></soapenv:Body></soapenv:Envelope>$";
    }

    @Override
    public String toString() {
        return "ParametersMonitor{" + name + " | " + condition + " | " + value + '}';
    }
}
