package dev.ultreon.mods.lib.registries;

import dev.ultreon.mods.lib.UltreonLib;
import dev.ultreon.mods.lib.client.theme.GlobalTheme;
import dev.ultreon.mods.lib.client.theme.Theme;
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
