package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.BooleanConsumer;
import org.jetbrains.annotations.NotNull;

public interface BooleanIterable extends Iterable<Boolean> {
    @NotNull
    @Override
    BooleanIterator iterator();

    default void forEach(BooleanConsumer action) {
        Iterable.super.forEach(action);
    }
}
