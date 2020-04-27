package com.friendly.aqa.entities;

public class ParametersMonitor {
    String name;
    Condition condition;
    String value;

    public ParametersMonitor(String name, Condition condition, String value) {
        this.name = name;
        this.condition = condition;
        this.value = value;
    }

    public String getName() {
        return name;
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

    @Override
    public String toString() {
        return "ParametersMonitor{" + name + ":" + condition + ":" + value + '}';
    }
}
