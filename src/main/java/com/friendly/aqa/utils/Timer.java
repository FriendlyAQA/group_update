package com.friendly.aqa.utils;

import com.friendly.aqa.pageobject.BasePage;

public class Timer {
    private final long start;
    private final long delay;

    public Timer() {
        start = System.currentTimeMillis();
        delay = BasePage.IMPLICITLY_WAIT * 1000;
    }

    public Timer(long delay) {
        start = System.currentTimeMillis();
        this.delay = delay;
    }

    public boolean timeout() {
        return System.currentTimeMillis() - start > delay;
    }
}
