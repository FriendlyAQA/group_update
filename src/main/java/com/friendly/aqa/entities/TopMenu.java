package com.friendly.aqa.entities;

public enum TopMenu {
    SYSTEM("System"),
    DEVICE_PROFILE("Device Profile"),
    DEVICE_UPDATE("Device Update"),
    GROUP_UPDATE("Group Update"),
    MONITORING("Monitoring"),
    EVENTS("Events"),
    REPORTS("Reports"),
    FILE_MANAGEMENT("File Management"),
    SETTINGS("Settings");

    private final String item;

    TopMenu(String item) {
        this.item = item;
    }

    public String getItem() {
        return item;
    }
}
