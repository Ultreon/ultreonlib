package com.ultreon.mods.lib.client.gui.widget;

@FunctionalInterface
public interface Callback<T> {
    void call(T caller);
}
