package com.ultreon.mods.lib.client.gui;

public interface Clickable {
    default boolean leftClick() {
        return false;
    }

    default boolean middleClick() {
        return false;
    }

    default boolean rightClick() {
        return false;
    }
}
