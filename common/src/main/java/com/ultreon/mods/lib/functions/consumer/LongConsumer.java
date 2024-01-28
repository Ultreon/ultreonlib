package com.ultreon.mods.lib.functions.consumer;

import java.util.function.Consumer;

@FunctionalInterface
public interface LongConsumer extends Consumer<Long> {
    @Override
    @Deprecated
    default void accept(Long aLong) {
        this.accept((long)aLong);
    }

    void accept(long v);
}
