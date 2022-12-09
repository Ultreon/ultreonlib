package com.ultreon.mods.lib.client.gui;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;

public class Hud extends GuiComponent implements Widget {
    protected final Minecraft minecraft;
    private final Window window;

    public Hud() {
        this.minecraft = Minecraft.getInstance();
        this.window = minecraft.getWindow();
    }

    @Override
    public final void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        this.render(poseStack, partialTick, width(), height());
    }

    public void render(PoseStack poseStack, float partialTick, int width, int height) {

    }

    private int width() {
        return window.getGuiScaledWidth();
    }

    private int height() {
        return window.getGuiScaledHeight();
    }
}
