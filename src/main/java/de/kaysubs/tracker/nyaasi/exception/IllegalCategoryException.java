package de.kaysubs.tracker.nyaasi.exception;

public class IllegalCategoryException extends NyaaSiException {

    public IllegalCategoryException() {}

    public IllegalCategoryException(String message) {
        super(message);
    }

    public IllegalCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCategoryException(Throwable cause) {
        super(cause);
    }

    public IllegalCategoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
