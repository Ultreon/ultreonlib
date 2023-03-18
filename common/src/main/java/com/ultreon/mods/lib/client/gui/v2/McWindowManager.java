package com.ultreon.mods.lib.client.gui.v2;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;

import java.util.List;

abstract class McWindowManager extends McComponent {
    private final List<McWindow> windows;
    private final Object wmLock = new Object();

    public McWindowManager(int x, int y, int width, int height, List<McWindow> windows) {
        super(x, y, width, height, Component.empty());
        this.windows = windows;
        windows.forEach(window -> window.wm = this);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderWindows(poseStack, mouseX, mouseY, partialTicks);
    }

    private void renderWindows(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        for (var window : windows) {
            window.render(poseStack, mouseX, mouseY, partialTicks);
        }
    }

    public synchronized void createWindow(McWindow window) {
        synchronized (this.wmLock) {
            if (this.windows.contains(window)) throw new IllegalArgumentException("Window already exists.");

            window.wm = this;
            this.windows.add(window);
        }
    }

    synchronized void destroyWindow(McWindow window) {
        synchronized (this.wmLock) {
            if (this.windows.contains(window)) throw new IllegalArgumentException("Window already exists.");

            this.windows.add(window);
        }
    }

    public synchronized void moveToForeground(McWindow window) {
        synchronized (wmLock) {
            if (!this.windows.contains(window)) throw new IllegalArgumentException("Window doesn't exist.");

            this.windows.remove(window);
            this.windows.add(window);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (var window : windows) {
            if (window.isMouseOver(mouseX, mouseY) && window.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        var ret = false;
        for (var window : windows) {
            ret |= window.mouseReleased(mouseX, mouseY, button);
        }
        if (ret) return true;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        for (var window : windows) {
            if (window.isMouseOver(mouseX, mouseY) && window.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
                return true;
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        for (var window : windows) {
            window.mouseMoved(mouseX, mouseY);
        }
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        for (var window : windows) {
            if (window.isMouseOver(mouseX, mouseY) && window.mouseScrolled(mouseX, mouseY, delta)) {
                return true;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }
}
