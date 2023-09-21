package com.ultreon.mods.lib.client.gui.widget.toolbar;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import org.jetbrains.annotations.NotNull;

public interface IToolbarItem extends Renderable {
    @Override
    void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks);

    int width();

    int height();
}
