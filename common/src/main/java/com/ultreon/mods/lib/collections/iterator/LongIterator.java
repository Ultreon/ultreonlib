package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.LongConsumer;

import java.util.Iterator;

public interface LongIterator extends Iterator<Long> {
    @Override
    default Long next() {
        return this.nextLong();
    }

    long nextLong();

    default void forEachRemaining(LongConsumer action) {
        Iterator.super.forEachRemaining(action);
    }
}
