package com.ultreon.mods.lib.forge;

import com.ultreon.mods.lib.client.InternalConfigScreen;
import com.ultreon.mods.lib.client.UltreonLibClient;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

public class UltreonLibForgeClient {
    private final UltreonLibClient ultreonLib;

    public UltreonLibForgeClient() {
        this.ultreonLib = UltreonLibClient.create();
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new InternalConfigScreen()));
    }

    public UltreonLibClient getUltreonLib() {
        return ultreonLib;
    }
}
