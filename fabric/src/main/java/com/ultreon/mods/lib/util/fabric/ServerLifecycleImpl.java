package com.ultreon.mods.lib.util.fabric;

import com.ultreon.mods.lib.fabric.UltreonLibFabric;
import net.minecraft.server.MinecraftServer;

public class ServerLifecycleImpl {
    public static MinecraftServer getCurrentServer() {
        return UltreonLibFabric.getServer();
    }
}
