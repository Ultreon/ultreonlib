package com.ultreon.commons.exceptions;

public class DebugException extends RuntimeException {
    public DebugException() {
    }

    public DebugException(String message) {
        super(message);
    }
}
