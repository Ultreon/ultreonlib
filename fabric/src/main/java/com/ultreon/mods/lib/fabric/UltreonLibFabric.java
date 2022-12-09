package com.ultreon.mods.lib.fabric;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.UltreonLibConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UltreonLibFabric implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("UltreonLib:Fabric");
    @Nullable
    private static MinecraftServer server;
    private UltreonLib ultreonLib;

    @Nullable
    public static MinecraftServer getServer() {
        return server;
    }

    @Override
    public void onInitialize() {
        UltreonLibConfig.register(null);

        ultreonLib = UltreonLib.create();
        ultreonLib.initNetworkInstances();

        ServerLifecycleEvents.SERVER_STARTING.register(server -> UltreonLibFabric.server = server);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> UltreonLibFabric.server = null);
    }

    public UltreonLib getUltreonLib() {
        return ultreonLib;
    }
}