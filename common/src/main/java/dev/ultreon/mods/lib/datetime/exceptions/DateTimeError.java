package dev.ultreon.mods.lib.datetime.exceptions;

public class DateTimeError extends Error {
    public DateTimeError() {
        super();
    }

    public DateTimeError(String message) {
        super(message);
    }

    public DateTimeError(String message, Throwable cause) {
        super(message, cause);
    }

    public DateTimeError(Throwable cause) {
        super(cause);
    }
}
