package com.ultreon.mods.lib.fabric;

import com.ultreon.mods.lib.DevPreviewRegistry;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.UltreonLibClient;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import net.fabricmc.api.ClientModInitializer;

public class UltreonLibFabricClient implements ClientModInitializer {
    private UltreonLibClient ultreonLib;

    @Override
    public void onInitializeClient() {
        ultreonLib = UltreonLibClient.create();

        for (Mod mod : Platform.getMods()) {
            DevPreviewRegistry.register(mod);
        }
    }

    public UltreonLibClient getUltreonLib() {
        return ultreonLib;
    }
}
