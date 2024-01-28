package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.ShortConsumer;
import org.jetbrains.annotations.NotNull;

public interface ShortIterable extends Iterable<Short> {
    @NotNull
    @Override
    ShortIterator iterator();

    default void forEach(ShortConsumer action) {
        Iterable.super.forEach(action);
    }
}
