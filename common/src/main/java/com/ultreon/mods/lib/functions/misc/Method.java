package com.ultreon.mods.lib.functions.misc;

@FunctionalInterface
public interface Method<T> {
    Object call(T instance, Object... params);
}
