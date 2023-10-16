package com.ultreon.mods.lib.registries;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.client.theme.Theme;
import dev.architectury.registry.registries.Registrar;

public class ModRegistries {

    public static final Registrar<Theme> THEME = ModRegistries.create("theme");
    public static final Registrar<GlobalTheme> GLOBAL_THEME = ModRegistries.create("global_theme");

    @SafeVarargs
    private static <T> Registrar<T> create(String name, T... objectGetter) {
        return UltreonLib.REGISTRAR_MANAGER.builder(UltreonLib.res(name), objectGetter).build();
    }

    public static void init() {

    }
}
