package dev.ultreon.mods.lib.functions.misc;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Throwable> {
    void accept(T obj) throws E;
}
