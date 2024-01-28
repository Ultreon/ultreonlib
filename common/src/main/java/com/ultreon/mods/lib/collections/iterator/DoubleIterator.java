package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.DoubleConsumer;

import java.util.Iterator;

public interface DoubleIterator extends Iterator<Double> {
    @Override
    default Double next() {
        return this.nextDouble();
    }

    double nextDouble();

    default void forEachRemaining(DoubleConsumer action) {
        Iterator.super.forEachRemaining(action);
    }
}
