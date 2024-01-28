package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.IntConsumer;

import java.util.Iterator;

public interface IntIterator extends Iterator<Integer> {
    @Override
    default Integer next() {
        return this.nextInt();
    }

    int nextInt();

    default void forEachRemaining(IntConsumer action) {
        Iterator.super.forEachRemaining(action);
    }
}
