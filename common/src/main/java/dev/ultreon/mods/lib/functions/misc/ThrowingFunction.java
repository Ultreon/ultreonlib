package dev.ultreon.mods.lib.functions.misc;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> {
    R apply(T obj) throws E;
}
