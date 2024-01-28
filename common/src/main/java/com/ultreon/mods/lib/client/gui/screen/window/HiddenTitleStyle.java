package com.ultreon.mods.lib.client.gui.screen.window;

import com.ultreon.mods.lib.client.gui.FrameType;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class HiddenTitleStyle extends TitleStyle {
    public HiddenTitleStyle() {
        super(4, 0, "hidden");
    }

    @Override
    public void renderFrame(GuiRenderer renderer, int x, int y, int width, int height, GlobalTheme theme, Component title) {
        GuiGraphics gfx = renderer.gfx();
        gfx.blitSprite(theme.getContentTheme().getFrameSprite(), x, y, width, height);
        renderer.renderContentFrame(x - 7, y - 7, width + 14, height + 14, FrameType.NORMAL);
    }
}
