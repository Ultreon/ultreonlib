package com.ultreon.mods.lib.functions.consumer;

import java.util.function.Consumer;

@FunctionalInterface
public interface CharConsumer extends Consumer<Character> {
    @Override
    @Deprecated
    default void accept(Character aCharacter) {
        this.accept((char)aCharacter);
    }

    void accept(char v);
}
