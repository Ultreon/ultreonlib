package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.ByteConsumer;

import java.util.Iterator;

public interface ByteIterator extends Iterator<Byte> {
    @Override
    default Byte next() {
        return this.nextByte();
    }

    byte nextByte();

    default void forEachRemaining(ByteConsumer action) {
        Iterator.super.forEachRemaining(action);
    }
}
