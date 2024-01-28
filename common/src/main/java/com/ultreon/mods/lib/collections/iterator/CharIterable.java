package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.CharConsumer;
import org.jetbrains.annotations.NotNull;

public interface CharIterable extends Iterable<Character> {
    @NotNull
    @Override
    CharIterator iterator();

    default void forEach(CharConsumer action) {
        Iterable.super.forEach(action);
    }
}
