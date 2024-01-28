package com.ultreon.mods.lib.functions.misc;

@FunctionalInterface
public interface ParameterizedRunnable<T> {
    void run(T t);
}
