package com.ultreon.mods.lib.collections.iterator;

import com.ultreon.mods.lib.functions.consumer.CharConsumer;

import java.util.Iterator;

public interface CharIterator extends Iterator<Character> {
    @Override
    default Character next() {
        return this.nextChar();
    }

    char nextChar();

    default void forEachRemaining(CharConsumer action) {
        Iterator.super.forEachRemaining(action);
    }
}
