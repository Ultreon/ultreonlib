package com.ultreon.mods.lib.client.gui;

public interface Clickable {
    default void leftClick() {
        click();
    }

    @Deprecated
    default void click() {
        leftClick();
    }

    default void middleClick() {

    }

    default void rightClick() {

    }
}
