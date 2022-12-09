package com.ultreon.mods.lib.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.MinecraftServer;

public class ServerLifecycle {
    @ExpectPlatform
    public static MinecraftServer getCurrentServer() {
        throw new AssertionError();
    }
}
