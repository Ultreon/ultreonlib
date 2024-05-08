package dev.ultreon.mods.lib.client.gui.screen.neoforge;

import dev.ultreon.mods.lib.client.gui.screen.BaseScreen;
import net.minecraft.client.Minecraft;

public class BaseScreenImpl {
    private static void show(BaseScreen screen) {
        Minecraft.getInstance().pushGuiLayer(screen);
    }
}
