package com.ultreon.mods.lib.forge;

import com.ultreon.mods.lib.UltreonLibConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class UltreonLibConfigImpl {
    public static void register(Object context) {
        ((ModLoadingContext)context).registerConfig(ModConfig.Type.CLIENT, UltreonLibConfig.CLIENT);
        ((ModLoadingContext)context).registerConfig(ModConfig.Type.COMMON, UltreonLibConfig.COMMON);
        ((ModLoadingContext)context).registerConfig(ModConfig.Type.SERVER, UltreonLibConfig.SERVER);
    }
}
