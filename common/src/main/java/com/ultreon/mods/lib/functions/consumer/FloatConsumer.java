package com.ultreon.mods.lib.functions.consumer;

import java.util.function.Consumer;

@FunctionalInterface
public interface FloatConsumer extends Consumer<Float> {
    @Override
    @Deprecated
    default void accept(Float aFloat) {
        this.accept((float)aFloat);
    }
    
    void accept(float v);
}
