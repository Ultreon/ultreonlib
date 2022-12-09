package com.ultreon.mods.lib.client.gui.screen.fabric;

import com.ultreon.mods.lib.client.gui.screen.BaseScreen;
import com.ultreon.mods.lib.client.gui.screen.GenericMenuScreen;
import net.minecraft.client.Minecraft;

public class BaseScreenImpl {
    @SuppressWarnings("DataFlowIssue")
    private static void show(BaseScreen screen) {
        Minecraft.getInstance().setScreen(screen);
    }
}
