package com.ultreon.mods.lib.client.gui.screen.neoforge;

import com.ultreon.mods.lib.client.gui.screen.ULibScreen;
import net.minecraft.client.Minecraft;

public class BaseScreenImpl {
    private static void show(ULibScreen screen) {
        Minecraft.getInstance().pushGuiLayer(screen);
    }
}
