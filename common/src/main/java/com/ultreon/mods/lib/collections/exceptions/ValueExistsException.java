package com.ultreon.mods.lib.collections.exceptions;

public class ValueExistsException extends RuntimeException {
    public ValueExistsException() {
        super();
    }

    public ValueExistsException(String message) {
        super(message);
    }

    public ValueExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValueExistsException(Throwable cause) {
        super(cause);
    }
}
