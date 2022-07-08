package com.ultreon.modlib.core;

import com.ultreon.modlib.ModdingLib;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

@Mod.EventBusSubscriber(modid = ModdingLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
public class ServerModEvents {
    @SubscribeEvent
    public static void onServerStart(FMLDedicatedServerSetupEvent event) {

    }
}
