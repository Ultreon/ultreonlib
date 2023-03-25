package com.ultreon.mods.lib.client.gui.v2;

import com.mojang.blaze3d.platform.InputConstants;
import com.ultreon.mods.lib.util.KeyboardHelper;
import com.ultreon.mods.lib.util.KeyboardHelper.Modifier;

import java.util.ArrayList;

public class McDesktop extends McWindowManager {
    private final DesktopScreen screen;
    private McImage wallpaper;
    protected Modifier metaKey = Modifier.CTRL;

    public McDesktop(DesktopScreen screen, int width, int height, ArrayList<McWindow> windows) {
        super(0, 0, width, height, windows);

        this.screen = screen;
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

    public void setWindowActiveColor(int windowActiveColor) {
        this.windowActiveColor = windowActiveColor;
    }

    public void setWindowInactiveColor(int windowInactiveColor) {
        this.windowInactiveColor = windowInactiveColor;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        McWindow activeWindow = getActiveWindow();
        if ((keyCode == InputConstants.KEY_Q && KeyboardHelper.isKeyDown(metaKey)) || (keyCode == InputConstants.KEY_F4 && KeyboardHelper.isAltDown())) {
            activeWindow.close();
            return true;
        }
        if (keyCode == InputConstants.KEY_UP && KeyboardHelper.isKeyDown(metaKey)) {
            if (activeWindow.isMinimized()) activeWindow.restore();
            else activeWindow.maximize();
            return true;
        }
        if (keyCode == InputConstants.KEY_DOWN && KeyboardHelper.isKeyDown(metaKey)) {
            if (activeWindow.isMaximized()) activeWindow.restore();
            else activeWindow.minimize();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
