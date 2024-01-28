package com.ultreon.mods.lib.client.gui.screen.fabric;

import com.ultreon.mods.lib.client.gui.screen.ULibScreen;
import net.minecraft.client.Minecraft;

public class BaseScreenImpl {
    @SuppressWarnings("DataFlowIssue")
    private static void show(ULibScreen screen) {
        Minecraft.getInstance().setScreen(screen);
    }
}
