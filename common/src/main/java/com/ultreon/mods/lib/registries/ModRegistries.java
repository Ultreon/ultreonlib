package com.ultreon.mods.lib.registries;

import com.ultreon.libs.registries.v0.Registry;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.client.theme.Theme;
import net.minecraft.resources.ResourceLocation;

public class ModRegistries {

    public static final ClientRegistry<Theme> THEME = create("theme");

    public static final ClientRegistry<GlobalTheme> GLOBAL_THEME = create("global_theme");

    @SafeVarargs
    @SuppressWarnings("unchecked")
    private static <T> ClientRegistry<T> create(String theme, T... typeGetter) {
        Class<T> clazz = (Class<T>) typeGetter.getClass().getComponentType();
        return new ClientRegistry<T>(new ResourceLocation(UltreonLib.MOD_ID, theme), clazz);
    }

    public static void init() {

    }
}
