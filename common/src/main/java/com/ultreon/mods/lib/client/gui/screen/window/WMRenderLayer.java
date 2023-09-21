package com.ultreon.mods.lib.client.gui.screen.window;

import com.ultreon.mods.lib.client.gui.Hud;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

public class WMRenderLayer extends Hud {
    public WMRenderLayer() {
        super();
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, float partialTick, int width, int height) {
        if (Minecraft.getInstance().screen != null) {
            WindowManager.INSTANCE.renderAllWindows(gfx, Integer.MAX_VALUE, Integer.MAX_VALUE, partialTick);
        }
    }
}
