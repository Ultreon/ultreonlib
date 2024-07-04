package dev.ultreon.mods.lib.neoforge;

import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import dev.ultreon.mods.lib.UltreonLib;
import dev.ultreon.mods.lib.client.InternalConfigScreen;
import dev.ultreon.mods.lib.client.UltreonLibClient;
import net.neoforged.fml.ModLoadingContext;

public class UltreonLibNeoForgeClient {
    private final UltreonLibClient ultreonLib;

    public UltreonLibNeoForgeClient() {
        this.ultreonLib = UltreonLibClient.create();
    }

    public UltreonLibClient getUltreonLib() {
        return ultreonLib;
    }
}
