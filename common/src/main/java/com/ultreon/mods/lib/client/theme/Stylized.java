package com.ultreon.mods.lib.client.theme;

import com.ultreon.mods.lib.UltreonLib;

public interface Stylized {
    ThemeComponent getPlacement();

    default void reloadTheme() {

    }

    default Style getStyle() {
        return UltreonLib.getTheme().getStyle(this.getPlacement());
    }
}
