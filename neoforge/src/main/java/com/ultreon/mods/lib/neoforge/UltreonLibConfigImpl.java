package com.ultreon.mods.lib.neoforge;

import com.ultreon.mods.lib.UltreonLibConfig;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;

public class UltreonLibConfigImpl {
    public static void register(Object context) {
        (ModLoadingContext.get()).registerConfig(ModConfig.Type.CLIENT, UltreonLibConfig.CLIENT);
        (ModLoadingContext.get()).registerConfig(ModConfig.Type.COMMON, UltreonLibConfig.COMMON);
        (ModLoadingContext.get()).registerConfig(ModConfig.Type.SERVER, UltreonLibConfig.SERVER);
    }
}
