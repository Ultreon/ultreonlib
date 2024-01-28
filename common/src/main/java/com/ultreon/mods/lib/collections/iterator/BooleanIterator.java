package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.BooleanConsumer;

import java.util.Iterator;

public interface BooleanIterator extends Iterator<Boolean> {
    @Override
    default Boolean next() {
        return this.nextBoolean();
    }

    boolean nextBoolean();

    default void forEachRemaining(BooleanConsumer action) {
        Iterator.super.forEachRemaining(action);
    }
}
