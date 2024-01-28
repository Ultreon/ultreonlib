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
        renderer.renderTitleFrame(x, y, width, this.titleBarHeight, theme.getFrameType());
        renderer.renderContentFrame(x, y + this.titleBarHeight + 1, width, height - this.titleBarHeight - 1, theme.getFrameType());
        renderer.textCenter(title, x + width / 2, y + 6, this.getTitleColor(theme), false);
    }
}
