package com.ultreon.mods.lib.registries;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import dev.architectury.registry.registries.Registrar;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class ModRegistryKeys {
    public static final ResourceKey<Registry<GlobalTheme>> GLOBAL_THEME = ResourceKey.createRegistryKey(UltreonLib.res("global_theme"));

    @SafeVarargs
    private static <T> Registrar<T> create(String name, T... objectGetter) {
        return UltreonLib.REGISTRAR_MANAGER.builder(UltreonLib.res(name), objectGetter).build();
    }

    public static void init() {

    }
}
