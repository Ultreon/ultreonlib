package com.ultreon.mods.lib.core;

import net.fabricmc.api.EnvType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

@Mod.EventBusSubscriber(modid = ModdingLibrary.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = EnvType.DEDICATED_SERVER)
public class ServerModEvents {
    @SubscribeEvent
    public static void onServerStart(FMLDedicatedServerSetupEvent event) {

    }
}
