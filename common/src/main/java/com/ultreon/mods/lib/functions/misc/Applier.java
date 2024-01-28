package com.ultreon.mods.lib.functions.misc;

@FunctionalInterface
public interface Applier<T, R> {
    R apply(T obj);
}
