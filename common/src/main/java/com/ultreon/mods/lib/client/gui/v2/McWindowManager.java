package com.ultreon.mods.lib.client.gui.v2;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

abstract class McWindowManager extends McComponent {
    private final List<McWindow> windows;
    private final Object wmLock = new Object();
    private McWindow windowPress;

    public McWindowManager(int x, int y, int width, int height, List<McWindow> windows) {
        super(x, y, width, height, Component.empty());
        this.windows = windows instanceof CopyOnWriteArrayList<McWindow> ? windows : new CopyOnWriteArrayList<>(windows);
        windows.forEach(window -> window.wm = this);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderWindows(poseStack, mouseX, mouseY, partialTicks);
    }

    private void renderWindows(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        var windows = new ArrayList<>(this.windows);
        McWindow hoveredWindow = getHoveredWindow(mouseX, mouseY);
        for (int i = windows.size() - 1; i > -1; i--) {
            var window = windows.get(i);

            if (Objects.equals(window, hoveredWindow)) window.render(poseStack, mouseX, mouseY, partialTicks);
            else window.render(poseStack, Integer.MAX_VALUE, Integer.MAX_VALUE, partialTicks);
        }
    }

    @Nullable
    private McWindow getHoveredWindow(int mouseX, int mouseY) {
        for (var window : windows) {
            if (window.isMouseOver(mouseX, mouseY)) {
                return window;
            }
        }
        return null;
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
            if (!this.windows.contains(window)) throw new IllegalArgumentException("Windows doesn't exist.");

            this.windows.remove(window);
        }
    }

    public synchronized void moveToForeground(McWindow window) {
        synchronized (wmLock) {
            if (!this.windows.contains(window)) throw new IllegalArgumentException("Window doesn't exist.");

            this.windows.remove(window);
            this.windows.add(0, window);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (var window : windows) {
            if (window.isMouseOver(mouseX, mouseY)) {
                this.moveToForeground(window);
                windowPress = window;
                window.mouseClicked(mouseX, mouseY, button);
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (windowPress != null) {
            windowPress.mouseReleased(mouseX, mouseY, button);
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (windowPress != null) {
            windowPress.mouseDragged(mouseX, mouseY, button, dragX, dragY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        for (var window : windows) {
            if (window.isMouseOver(mouseX, mouseY)) {
                window.mouseMoved(mouseX, mouseY);
                return;
            }
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
