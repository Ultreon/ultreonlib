package com.ultreon.mods.lib.networking;

import com.mojang.logging.LogUtils;
import com.ultreon.mods.lib.networking.network.Network;
import com.ultreon.mods.lib.networking.service.NetworkService;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

@Mod("ultreonlib_net")
public class NetworkLib {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<ResourceLocation, Network> NETWORKS = new HashMap<>();

    public NetworkLib() {
        // Register the setup method for mod-loading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void registerNetwork(Network network) {
        if (NETWORKS.containsKey(network.getId())) {
            LOGGER.error("Network with id {} already registered, don't manually register the network class.", network.getId());
            return;
        }

        NETWORKS.put(network.getId(), network);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ServiceLoader<NetworkService> service = ServiceLoader.load(NetworkService.class);
        service.stream().map(ServiceLoader.Provider::get).forEach(NetworkService::setup);

        NETWORKS.values().forEach(Network::init);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
