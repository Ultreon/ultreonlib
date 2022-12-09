package com.ultreon.mods.lib.fabric;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.UltreonLibConfig;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class UltreonLibConfigImpl {
    public static void register(Object ignored) {
        ModLoadingContext.registerConfig(UltreonLib.MOD_ID, ModConfig.Type.CLIENT, UltreonLibConfig.CLIENT);
    }
}
