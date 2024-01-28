package com.ultreon.mods.lib.client.gui.screen.window;

import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import net.minecraft.network.chat.Component;

public class DetachedTitleStyle extends TitleStyle {

    public DetachedTitleStyle() {
        super(25, 20, "detached");
    }

    @Override
    public void renderFrame(GuiRenderer renderer, int x, int y, int width, int height, GlobalTheme theme, Component title) {

    }
}
