package com.ultreon.mods.lib.client.gui.screen.forge;

import com.ultreon.mods.lib.client.gui.screen.GenericMenuScreen;
import net.minecraft.client.Minecraft;

public class GenericMenuScreenImpl {
    public static void popGuiLayer(GenericMenuScreen screen) {
        Minecraft.getInstance().popGuiLayer();
    }
}
