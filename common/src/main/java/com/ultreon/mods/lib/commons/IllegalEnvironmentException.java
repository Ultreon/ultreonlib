package com.ultreon.mods.lib.commons;

public class IllegalEnvironmentException extends RuntimeException {
    public IllegalEnvironmentException() {

    }

    public IllegalEnvironmentException(String message) {
        super(message);
    }

    public IllegalEnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalEnvironmentException(Throwable cause) {
        super(cause);
    }
}
