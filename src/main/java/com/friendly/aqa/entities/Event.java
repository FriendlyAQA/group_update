package com.friendly.aqa.entities;

import com.friendly.aqa.pageobject.BasePage;

import java.util.Objects;

public class Event {
    private final String name;
    private Boolean onEachEvent;
    private String countOfEvents;
    private String duration;

    public Event(String name, Boolean onEachEvent, String countOfEvents, String duration) {
        this.name = name;
        this.onEachEvent = onEachEvent;
        this.countOfEvents = countOfEvents;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public Boolean isOnEachEvent() {
        return onEachEvent;
    }

    public String getCountOfEvents() {
        return countOfEvents;
    }

    public String getDuration() {
        return duration;
    }

    public void setOnEachEvent(boolean onEachEvent) {
        this.onEachEvent = onEachEvent;
    }

    public void setCountOfEvents(String countOfEvents) {
        this.countOfEvents = countOfEvents;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getRegex() {
        String serial = BasePage.getSerial();
        String duration;
        String countOfEvents;
        if (onEachEvent != null && onEachEvent) {
            duration = "1";
            countOfEvents = "1";
        } else {
            if (this.countOfEvents == null) {
                countOfEvents = "1";
            } else {
                countOfEvents = this.countOfEvents;
            }
            if (this.duration == null) {
                duration = "1";
            } else {
                String[] d = this.duration.split(":");
                if (d[1].equalsIgnoreCase("hours")) {
                    duration = String.valueOf(Integer.parseInt(d[0]) * 60);
                } else {
                    duration = d[0];
                }
            }
        }
        return "^<soapenv:Envelope.+?<(ftacs:)?CPEEventMonitorResult>.+?<serialNumber>" + serial + "</serialNumber>.+?<eventCode>"
                + name + "</eventCode><quantityFromMonitor>" + countOfEvents + "</quantityFromMonitor>.+?<duration>"
                + duration + "</duration>.+?</(ftacs:)?CPEEventMonitorResult></soapenv:Body></soapenv:Envelope>$";
//        return "^<soapenv:Envelope.+?<fri:CPEEventMonitorResult>.+?<fri:serialNumber>" + serial + "</fri:serialNumber>.+?<fri:eventCode>"
//                + name + "</fri:eventCode><fri:quantityFromMonitor>" + countOfEvents + "</fri:quantityFromMonitor>.+?<fri:duration>"
//                + duration + "</fri:duration>.+?</fri:CPEEventMonitorResult></soapenv:Body></soapenv:Envelope>$";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return onEachEvent == event.onEachEvent &&
                name.equals(event.name) &&
                countOfEvents.equals(event.countOfEvents) &&
                duration.equals(event.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, onEachEvent, countOfEvents, duration);
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", onEachEvent=" + onEachEvent +
                ", countOfEvents='" + countOfEvents + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}
