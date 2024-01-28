package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.FloatConsumer;
import org.jetbrains.annotations.NotNull;

public interface FloatIterable extends Iterable<Float> {
    @NotNull
    @Override
    FloatIterator iterator();

    default void forEach(FloatConsumer action) {
        Iterable.super.forEach(action);
    }
}
