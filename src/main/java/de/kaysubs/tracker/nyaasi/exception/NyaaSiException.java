package de.kaysubs.tracker.nyaasi.exception;

public class NyaaSiException extends RuntimeException {

    public NyaaSiException() {}

    public NyaaSiException(String message) {
        super(message);
    }

    public NyaaSiException(String message, Throwable cause) {
        super(message, cause);
    }

    public NyaaSiException(Throwable cause) {
        super(cause);
    }

    public NyaaSiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
