package com.ultreon.mods.lib.collections.exceptions;

public class OutOfRangeException extends RuntimeException {
    public OutOfRangeException(Number index, Number start, Number end) {
        this("Index " + index + " out of range (" + start + ":" + end + ")");
    }

    protected OutOfRangeException() {
        super();
    }

    protected OutOfRangeException(String message) {
        super(message);
    }

    protected OutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    protected OutOfRangeException(Throwable cause) {
        super(cause);
    }
}
