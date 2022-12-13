package com.ultreon.mods.lib.core;

import com.ultreon.mods.lib.core.client.init.ModOverlays;
import net.fabricmc.api.EnvType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ModdingLibrary.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = EnvType.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ModOverlays.register();
    }
}
