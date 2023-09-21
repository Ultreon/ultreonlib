package com.ultreon.mods.lib.client.gui;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import org.jetbrains.annotations.NotNull;

public class Hud implements Renderable {
    protected final Minecraft minecraft;
    private final Window window;

    public Hud() {
        this.minecraft = Minecraft.getInstance();
        this.window = minecraft.getWindow();
    }

    @Override
    public final void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        this.render(gfx, partialTick, width(), height());
    }

    public void render(@NotNull GuiGraphics gfx, float partialTick, int width, int height) {

    }

    private int width() {
        return window.getGuiScaledWidth();
    }

    private int height() {
        return window.getGuiScaledHeight();
    }
}
