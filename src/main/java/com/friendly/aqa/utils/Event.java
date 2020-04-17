package com.friendly.aqa.utils;

import com.sun.istack.internal.NotNull;

import java.util.Objects;

public class Event {
    @NotNull
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
