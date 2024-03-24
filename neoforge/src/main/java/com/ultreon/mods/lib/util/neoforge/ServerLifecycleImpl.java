package com.ultreon.mods.lib.util.neoforge;

import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class ServerLifecycleImpl {
    public static MinecraftServer getCurrentServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }
}
