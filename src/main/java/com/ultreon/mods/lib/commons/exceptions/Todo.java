package com.ultreon.mods.lib.commons.exceptions;

public class Todo extends RuntimeException {
    public Todo() {
    }

    public Todo(String message) {
        super(message);
    }
}
