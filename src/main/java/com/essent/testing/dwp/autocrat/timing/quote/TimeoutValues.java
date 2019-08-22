package com.essent.testing.dwp.autocrat.timing.quote;

public enum TimeoutValues {
    INPUT(5000, 1.5),
    TOGGLE_CHECKBOX(3000, 3),
    NEXT_STEP(3000, 3),
    WAIT_NEXT_PAGE(1500, 10),
    SUBMIT_QUOTE(3000, 90),
    UPLOAD_FILE(10000, 10),
    SUBMIT_SUGNATURE(1000, 120);
    private long    sleepInMillis;
    private double  waitInSeconds;

    TimeoutValues(long sleepInMillis, double waitInSeconds) {
        this.sleepInMillis = sleepInMillis;
        this.waitInSeconds = waitInSeconds;
    }

    public long getSleepInMillis() {
        return sleepInMillis;
    }

    public double getWaitInSeconds() {
        return waitInSeconds;
    }
}
