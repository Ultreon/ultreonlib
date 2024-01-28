package com.ultreon.mods.lib.commons;

import java.util.function.Consumer;

/**
 * @author Qboi
 */
public class MessengerImpl implements Messenger {
    private final Consumer<String> consumer;

    public MessengerImpl(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void send(String message) {
        this.consumer.accept(message);
    }
}
