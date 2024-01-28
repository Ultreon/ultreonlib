package com.ultreon.mods.lib.client.gui.screen.window;

import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.client.theme.WidgetPlacement;
import net.minecraft.network.chat.Component;

public class NormaTitleStyle extends TitleStyle {
    public NormaTitleStyle() {
        super(16, 16, "normal");
    }

    @Override
    public void renderFrame(GuiRenderer renderer, int x, int y, int width, int height, GlobalTheme theme, Component title) {
        var gfx = renderer.gfx();

        gfx.blitSprite(theme.getContentTheme().getFrameSprite(), x, y, width, height);
        renderer.textCenter(title, (int) (x + width / 2f - renderer.font().width(title) / 2), y + 6, theme.getTitleColor(WidgetPlacement.WINDOW).getRgb(), false);
    }
}
