package com.ultreon.mods.lib.client.gui.v2;

import com.mojang.blaze3d.vertex.PoseStack;

import java.util.ArrayList;

public class McDesktop extends McWindowManager {
    private final DesktopScreen screen;
    private McImage wallpaper;

    public McDesktop(DesktopScreen screen, int width, int height, ArrayList<McWindow> windows) {
        super(0, 0, width, height, windows);

        this.screen = screen;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderDesktop(poseStack, partialTicks);

        super.render(poseStack, mouseX, mouseY, partialTicks);

        // TODO: Add taskbar to desktop.
//        if (taskBar != null) {
//            renderTaskBar(taskBar);
//        }
    }

    public void renderDesktop(PoseStack poseStack, float partialTicks) {
        if (this.wallpaper != null) {
            wallpaper.setWidth(width);
            wallpaper.setHeight(height);
            wallpaper.render(poseStack, Integer.MAX_VALUE, Integer.MAX_VALUE, partialTicks);
        } else {
            fill(poseStack, 0, 0, width, height, 0xff333333);
        }
    }

    public McImage getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(McImage wallpaper) {
        this.wallpaper = wallpaper;
    }

    @Override
    public final void setX(int i) {

    }

    @Override
    public final void setY(int i) {

    }

    @Override
    public final int getX() {
        return 0;
    }

    @Override
    public final int getY() {
        return 0;
    }

    @Override
    public int getWidth() {
        return screen.width;
    }

    @Override
    public int getHeight() {
        return screen.height;
    }
}
