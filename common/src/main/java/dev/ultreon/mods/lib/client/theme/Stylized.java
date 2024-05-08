package dev.ultreon.mods.lib.client.theme;

import dev.ultreon.mods.lib.UltreonLib;

public interface Stylized {
    ThemeComponent getThemeComponent();

    default void reloadTheme() {

    }

    default Style getStyle() {
        return UltreonLib.getTheme().getStyle(this.getThemeComponent());
    }
}
