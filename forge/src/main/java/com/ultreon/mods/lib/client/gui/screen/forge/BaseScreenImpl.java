package com.ultreon.mods.lib.client.gui.screen.forge;

import com.ultreon.mods.lib.client.gui.screen.BaseScreen;
import net.minecraft.client.Minecraft;

public class BaseScreenImpl {
    private static void show(BaseScreen screen) {
        Minecraft.getInstance().pushGuiLayer(screen);
    }
}
