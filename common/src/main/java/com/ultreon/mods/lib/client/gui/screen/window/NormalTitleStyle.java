package com.ultreon.mods.lib.client.gui.screen.window;

import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.client.theme.Theme;
import com.ultreon.mods.lib.client.theme.WidgetPlacement;
import com.ultreon.mods.lib.commons.Color;
import net.minecraft.network.chat.Component;

public class NormalTitleStyle extends TitleStyle {
    public NormalTitleStyle() {
        super(16, 16, "normal");
    }

    @Override
    public void renderFrame(GuiRenderer renderer, int x, int y, int width, int height, GlobalTheme theme, Component title) {
        var gfx = renderer.gfx();

        gfx.blitSprite(theme.getContentTheme().getFrameSprite(), x, y, width, height);
        renderer.textCenter(title, x + width / 2, y + 6, getTitleColor(theme).getRgb(), false);
    }

    @Override
    public Color getTitleColor(GlobalTheme theme) {
        Theme theme1 = theme.get(WidgetPlacement.CONTENT);
        if (theme1 == null) {
            System.out.println("theme1.getId() = " + theme1.getId());
        }
        return theme1.getTitleColor();
    }
}
