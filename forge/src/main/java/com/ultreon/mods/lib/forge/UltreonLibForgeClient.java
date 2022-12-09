package com.ultreon.mods.lib.forge;

import com.ultreon.mods.lib.client.UltreonLibClient;

public class UltreonLibForgeClient {
    private final UltreonLibClient ultreonLib;

    public UltreonLibForgeClient() {
        this.ultreonLib = UltreonLibClient.create();
    }

    public UltreonLibClient getUltreonLib() {
        return ultreonLib;
    }
}
