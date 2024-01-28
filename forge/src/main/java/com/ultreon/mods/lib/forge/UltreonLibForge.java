package com.ultreon.mods.lib.forge;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.UltreonLibConfig;
import com.ultreon.mods.lib.network.api.service.NetworkService;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

@Mod(UltreonLib.MOD_ID)
public class UltreonLibForge {
    public static final Logger LOGGER = LoggerFactory.getLogger("UltreonLib:Forge");

    private final UltreonLib ultreonLib;

    public UltreonLibForge() {
        ultreonLib = UltreonLib.create();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(UltreonLib.MOD_ID, modEventBus);
        ModLoadingContext loadingCtx = ModLoadingContext.get();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        // Common side stuff
        forgeEventBus.register(this);
        LOGGER.info("Registering common setup handler, and load complete handler.");

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::loadComplete);

        LOGGER.info("Initializing mod config.");
        UltreonLibConfig.register(loadingCtx);

        // Server side stuff
        LOGGER.info("Registering server setup handler.");
        modEventBus.addListener(this::serverSetup);

        // IMC stuff
        LOGGER.info("Registering IMC handlers.");

        // Register ourselves for server and other game events we are interested in
        LOGGER.info("Registering mod class to forge events.");
        forgeEventBus.register(this);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> UltreonLibForgeClient::new);
    }

    private void commonSetup(FMLCommonSetupEvent t) {
        ServiceLoader<NetworkService> service = ServiceLoader.load(NetworkService.class);
        service.stream().map(ServiceLoader.Provider::get).forEach(NetworkService::setup);

        ultreonLib.initNetworkInstances();
    }

    private void loadComplete(FMLLoadCompleteEvent t) {
        ultreonLib.loadComplete();
    }

    private void serverSetup(FMLDedicatedServerSetupEvent t) {
        ultreonLib.serverSetup();
    }

    public UltreonLib getUltreonLib() {
        return ultreonLib;
    }
}
