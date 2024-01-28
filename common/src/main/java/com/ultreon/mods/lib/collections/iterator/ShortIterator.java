package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.ShortConsumer;

import java.util.Iterator;

public interface ShortIterator extends Iterator<Short> {
    @Override
    default Short next() {
        return this.nextShort();
    }

    short nextShort();

    default void forEachRemaining(ShortConsumer action) {
        Iterator.super.forEachRemaining(action);
    }
}
