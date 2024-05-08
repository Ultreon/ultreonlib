package dev.ultreon.mods.lib.client.gui.screen.fabric;

import dev.ultreon.mods.lib.client.gui.screen.BaseScreen;
import net.minecraft.client.Minecraft;

public class BaseScreenImpl {
    @SuppressWarnings("DataFlowIssue")
    private static void show(BaseScreen screen) {
        Minecraft.getInstance().setScreen(screen);
    }
}
