package com.ultreon.mods.lib.fabric;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.UltreonLibConfig;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.neoforged.fml.config.ModConfig;

public class UltreonLibConfigImpl {
    public static void register(Object context) {
        NeoForgeConfigRegistry.INSTANCE.register(UltreonLib.MOD_ID, ModConfig.Type.CLIENT, UltreonLibConfig.CLIENT);
    }
}
