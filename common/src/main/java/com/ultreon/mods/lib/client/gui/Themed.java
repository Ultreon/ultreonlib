package com.ultreon.mods.lib.client.gui;

import com.ultreon.mods.lib.UltreonLib;

public interface Themed {
    default void reloadTheme() {

    }

    default Theme getTheme() {
        return UltreonLib.getTheme();
    }
}
