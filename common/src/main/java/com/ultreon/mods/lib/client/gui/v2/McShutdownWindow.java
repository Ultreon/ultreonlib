package com.ultreon.mods.lib.client.gui.v2;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

class McShutdownWindow extends McWindow {

    public McShutdownWindow(@NotNull McApplication application, int x, int y, int width, int height, String title) {
        super(application, x, y, width, height, title);
    }

    public McShutdownWindow(McApplication application, int x, int y, int width, int height, Component title) {
        super(application, x, y, width, height, title);
    }
}
