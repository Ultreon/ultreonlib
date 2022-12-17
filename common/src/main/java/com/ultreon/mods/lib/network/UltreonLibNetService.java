package com.ultreon.mods.lib.network;

import com.ultreon.mods.lib.network.api.service.NetworkService;

public class UltreonLibNetService implements NetworkService {
    @Override
    public void setup() {
        UltreonLibNetwork.create();
    }
}
