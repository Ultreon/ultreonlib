package com.ultreon.mods.lib.client.gui.widget.toolbar;

import com.ultreon.mods.lib.client.gui.widget.ULibWidget;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

public interface ToolbarWidget extends ULibWidget {
    @Override
    void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks);

    int width();

    int height();
}
