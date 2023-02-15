package com.ultreon.mods.lib.fabric;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.UltreonLibConfig;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraftforge.fml.config.ModConfig;

public class UltreonLibConfigImpl {
    public static void register(Object context) {
        ForgeConfigRegistry.INSTANCE.register(UltreonLib.MOD_ID, ModConfig.Type.CLIENT, UltreonLibConfig.CLIENT);
    }
}
