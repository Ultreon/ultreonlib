package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.IntConsumer;
import org.jetbrains.annotations.NotNull;

public interface IntIterable extends Iterable<Integer> {
    @NotNull
    @Override
    IntIterator iterator();

    default void forEach(IntConsumer action) {
        Iterable.super.forEach(action);
    }
}
