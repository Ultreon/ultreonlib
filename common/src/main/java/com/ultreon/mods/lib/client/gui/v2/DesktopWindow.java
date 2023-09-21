package com.ultreon.mods.lib.client.gui.v2;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class DesktopWindow extends McWindow {
    private final Window window;
    private McImage wallpaper;

    public DesktopWindow() {
        super(0, 0, 0, 0, Component.empty());
        window = Minecraft.getInstance().getWindow();

        wallpaper = new McImage(0, 0, 0, 0);
        wallpaper.loadFrom(new File("wallpaper.png"));
        add(wallpaper);
        addOnClosingListener(() -> false);
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        this.setUndecorated(true);
        this.setX(0);
        this.setY(0);
        this.setWidth(window.getGuiScaledWidth());
        this.setHeight(window.getGuiScaledHeight());
        this.wallpaper.setX(0);
        this.wallpaper.setY(0);
        this.wallpaper.setWidth(getWidth());
        this.wallpaper.setHeight(getHeight());

        this.setBottomMost(true);

        super.render(gfx, mouseX, mouseY, partialTicks);
    }

    public void setWallpaper(McImage wallpaper) {
        this.wallpaper = wallpaper;
    }
}
