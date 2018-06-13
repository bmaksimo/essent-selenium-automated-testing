package com.essent.testing.dwp;

public enum DwpTimingParameters {
    INPUT(2000, 1.5),
    TOGGLE_CHECKBOX(3000, 3),
    NEXT_STEP(3000, 3),
    WAIT_NEXT_PAGE(1500, 10),
    SUBMIT_QUOTE(3000, 90),
    UPLOAD_FILE(10000, 10),
    SUBMIT_SUGNATURE(1000, 120);
    private long    sleepInMillis;
    private double  waitInSeconds;

    DwpTimingParameters(long sleepInMillis, double waitInSeconds) {
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
