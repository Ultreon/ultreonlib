package com.ultreon.mods.lib.network;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class NetworkManager {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<ResourceLocation, Network> NETWORKS = new HashMap<>();

    public static void registerNetwork(Network network) {
        if (NETWORKS.containsKey(network.getId())) {
            LOGGER.error("Network with id {} already registered, don't manually register the network class.", network.getId());
            return;
        }

        NETWORKS.put(network.getId(), network);
    }

    public static void init() {
        NETWORKS.values().forEach(Network::init);
    }
}
