package com.friendly.aqa.entities;

public enum ParameterType {
    EMPTY_VALUE("sendEmpty"),
    VALUE("sendValue"),
    FALSE("0"),
    TRUE("1"),
    DO_NOT_SEND("notSend"),
    NULL(""),
    CUSTOM(null);

    private String option;

    public String getOption() {
        return option;
    }

    ParameterType(String option) {
        this.option = option;
    }
}
