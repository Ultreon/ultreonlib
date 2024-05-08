package dev.ultreon.mods.lib.util.fabric;

import dev.ultreon.mods.lib.fabric.UltreonLibFabric;
import net.minecraft.server.MinecraftServer;

public class ServerLifecycleImpl {
    public static MinecraftServer getCurrentServer() {
        return UltreonLibFabric.getServer();
    }
}
