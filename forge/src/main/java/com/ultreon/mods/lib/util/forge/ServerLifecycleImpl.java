package com.ultreon.mods.lib.util.forge;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.ServerLifecycleHooks;

public class ServerLifecycleImpl {
    @SuppressWarnings("UnstableApiUsage")
    public static MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
