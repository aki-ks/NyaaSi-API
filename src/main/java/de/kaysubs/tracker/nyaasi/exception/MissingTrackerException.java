package de.kaysubs.tracker.nyaasi.exception;

public class MissingTrackerException extends NyaaSiException {
    private final String tracker;

    public MissingTrackerException(String tracker) {
        super(tracker);
        this.tracker = tracker;
    }

    public String getTracker() {
        return tracker;
    }
}
