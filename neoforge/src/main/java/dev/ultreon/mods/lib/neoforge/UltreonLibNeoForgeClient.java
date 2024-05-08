package dev.ultreon.mods.lib.neoforge;

import dev.ultreon.mods.lib.client.InternalConfigScreen;
import dev.ultreon.mods.lib.client.UltreonLibClient;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.ConfigScreenHandler;

public class UltreonLibNeoForgeClient {
    private final UltreonLibClient ultreonLib;

    public UltreonLibNeoForgeClient() {
        this.ultreonLib = UltreonLibClient.create();
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new InternalConfigScreen()));
    }

    public UltreonLibClient getUltreonLib() {
        return ultreonLib;
    }
}
