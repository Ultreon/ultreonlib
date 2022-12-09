package com.ultreon.mods.lib.core.network;

import com.ultreon.mods.lib.core.ModdingLibrary;
import com.ultreon.mods.lib.core.util.GameUtil;
import com.ultreon.mods.lib.networking.service.NetworkService;

public class ModdingLibraryNetService implements NetworkService {
    @Override
    public void setup() {
        if (GameUtil.isDeveloperEnv()) {
            ModdingLibrary.LOGGER.trace("Setting up Modding Library Network");
        }
        ModdingLibraryNet.instance = new ModdingLibraryNet();
    }
}
