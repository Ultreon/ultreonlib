package com.ultreon.commons.exceptions;

public class Todo extends RuntimeException {
    public Todo() {
    }

    public Todo(String message) {
        super(message);
    }
}
