package com.ultreon.mods.lib.fabric;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.UltreonLibClient;
import net.fabricmc.api.ClientModInitializer;

public class UltreonLibFabricClient implements ClientModInitializer {
    private UltreonLibClient ultreonLib;

    @Override
    public void onInitializeClient() {
        ultreonLib = UltreonLibClient.create();
    }

    public UltreonLibClient getUltreonLib() {
        return ultreonLib;
    }
}
