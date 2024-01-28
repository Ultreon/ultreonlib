package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.DoubleConsumer;
import org.jetbrains.annotations.NotNull;

public interface DoubleIterable extends Iterable<Double> {
    @NotNull
    @Override
    DoubleIterator iterator();

    default void forEach(DoubleConsumer action) {
        Iterable.super.forEach(action);
    }
}
