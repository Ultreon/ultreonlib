package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.ByteConsumer;
import org.jetbrains.annotations.NotNull;

public interface ByteIterable extends Iterable<Byte> {
    @NotNull
    @Override
    ByteIterator iterator();

    default void forEach(ByteConsumer action) {
        Iterable.super.forEach(action);
    }
}
