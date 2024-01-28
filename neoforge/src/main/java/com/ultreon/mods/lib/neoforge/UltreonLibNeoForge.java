package com.ultreon.mods.lib.neoforge;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.UltreonLibConfig;
import com.ultreon.mods.lib.network.api.service.NetworkService;
import dev.architectury.utils.EnvExecutor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(UltreonLib.MOD_ID)
public class UltreonLibNeoForge {
    public static final Logger LOGGER = LoggerFactory.getLogger("UltreonLib:Forge");
    private IEventBus eventBus;

    private final UltreonLib ultreonLib;
    private static UltreonLibNeoForge instance;

    public UltreonLibNeoForge(IEventBus modEventBus) {
        UltreonLibNeoForge.instance = this;
        ultreonLib = UltreonLib.create();

        this.eventBus = modEventBus;

        ModLoadingContext loadingCtx = ModLoadingContext.get();
        IEventBus neoForgeEventBus = NeoForge.EVENT_BUS;

        // Common side stuff
        neoForgeEventBus.register(this);
        LOGGER.info("Registering common setup handler, and load complete handler.");

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::loadComplete);

        LOGGER.info("Initializing mod config.");
        UltreonLibConfig.register(loadingCtx);

        // Client side stuff
        if (!DatagenModLoader.isRunningDataGen()) {
            LOGGER.info("Registering the reload listener.");
//            ((ReloadableResourceManager) Minecraft.getInstance().getResourceManager()).registerReloadListener(this);
        }

        // Server side stuff
        LOGGER.info("Registering server setup handler.");
        modEventBus.addListener(this::serverSetup);

        // IMC stuff
        LOGGER.info("Registering IMC handlers.");

        // Register ourselves for server and other game events we are interested in
        LOGGER.info("Registering mod class to forge events.");
        neoForgeEventBus.register(this);

        EnvExecutor.runInEnv(Dist.CLIENT, () -> UltreonLibNeoForgeClient::new);
    }

    public static IEventBus getEventBus() {
        return instance.eventBus;
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
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
