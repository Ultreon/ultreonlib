package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.FloatConsumer;

import java.util.Iterator;

public interface FloatIterator extends Iterator<Float> {
    @Override
    default Float next() {
        return this.nextFloat();
    }

    float nextFloat();

    default void forEachRemaining(FloatConsumer action) {
        Iterator.super.forEachRemaining(action);
    }
}
