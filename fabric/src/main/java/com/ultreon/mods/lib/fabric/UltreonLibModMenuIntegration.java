package com.ultreon.mods.lib.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.ultreon.mods.lib.client.InternalConfigScreen;

public class UltreonLibModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return back -> new InternalConfigScreen();
    }
}
