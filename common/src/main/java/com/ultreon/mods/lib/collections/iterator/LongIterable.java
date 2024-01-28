package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.LongConsumer;
import org.jetbrains.annotations.NotNull;

public interface LongIterable extends Iterable<Long> {
    @NotNull
    @Override
    LongIterator iterator();

    default void forEach(LongConsumer action) {
        Iterable.super.forEach(action);
    }
}
