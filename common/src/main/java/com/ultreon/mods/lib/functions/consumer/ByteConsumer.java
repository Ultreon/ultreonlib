package com.ultreon.mods.lib.functions.consumer;

import java.util.function.Consumer;

@FunctionalInterface
public interface ByteConsumer extends Consumer<Byte> {
    @Override
    @Deprecated
    default void accept(Byte aByte) {
        this.accept((byte)aByte);
    }

    void accept(byte v);
}
