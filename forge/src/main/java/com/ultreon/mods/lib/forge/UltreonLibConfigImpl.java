package com.ultreon.mods.lib.forge;

import com.ultreon.mods.lib.UltreonLibConfig;
import fuzs.forgeconfigapiport.forge.api.neoforge.v4.NeoForgeConfigRegistry;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class UltreonLibConfigImpl {
    public static void register(Object context) {
        NeoForgeConfigRegistry.INSTANCE.register(ModConfig.Type.CLIENT, UltreonLibConfig.CLIENT);
        NeoForgeConfigRegistry.INSTANCE.register(ModConfig.Type.COMMON, UltreonLibConfig.COMMON);
        NeoForgeConfigRegistry.INSTANCE.register(ModConfig.Type.SERVER, UltreonLibConfig.SERVER);
    }
}
