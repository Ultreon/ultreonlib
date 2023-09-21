package com.ultreon.mods.lib.client.gui.v2;

import com.google.common.collect.Iterators;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

abstract class McWindowManager extends McComponent {
    private final List<McWindow> windows;
    private final List<McWindow> midStack;
    private final List<McWindow> topStack = new ArrayList<>();
    private final List<McWindow> bottomStack = new ArrayList<>();
    private final Object wmLock = new Object();
    private McWindow windowPress;

    private McWindow activeWindow;
    protected int windowActiveColor = 0xffff3000;
    protected int windowInactiveColor = 0xff555555;
    Insets border;

    public McWindowManager(int x, int y, int width, int height) {
        this(x, y, width, height, new ArrayList<>());
    }

    public McWindowManager(int x, int y, int width, int height, List<McWindow> windows) {
        super(x, y, width, height, Component.empty());
        this.windows = windows instanceof CopyOnWriteArrayList<McWindow> ? windows : new CopyOnWriteArrayList<>(windows);
        this.midStack = windows;
        windows.forEach(window -> window.wm = this);
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        renderWindows(gfx, mouseX, mouseY, partialTicks);
    }

    private void renderWindows(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        synchronized (wmLock) {
            renderWindowsBS(gfx, mouseX, mouseY, partialTicks);
            renderWindowsNS(gfx, mouseX, mouseY, partialTicks);
            renderWindowsTS(gfx, mouseX, mouseY, partialTicks);
        }
    }

    private void renderWindowsNS(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        var windows = new ArrayList<>(this.midStack);
        var hoveredWindow = getHoveredWindow(mouseX, mouseY);
        for (var i = windows.size() - 1; i > -1; i--) {
            var window = windows.get(i);

            if (Objects.equals(window, hoveredWindow)) window.render(gfx, mouseX, mouseY, partialTicks);
            else window.render(gfx, Integer.MAX_VALUE, Integer.MAX_VALUE, partialTicks);
        }
    }

    private void renderWindowsTS(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        var windows = new ArrayList<>(this.topStack);
        var hoveredWindow = getHoveredWindow(mouseX, mouseY);
        for (var i = windows.size() - 1; i > -1; i--) {
            var window = windows.get(i);

            if (Objects.equals(window, hoveredWindow)) window.render(gfx, mouseX, mouseY, partialTicks);
            else window.render(gfx, Integer.MAX_VALUE, Integer.MAX_VALUE, partialTicks);
        }
    }

    private void renderWindowsBS(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        var windows = new ArrayList<>(this.bottomStack);
        var hoveredWindow = getHoveredWindow(mouseX, mouseY);
        for (var i = windows.size() - 1; i > -1; i--) {
            var window = windows.get(i);

            if (Objects.equals(window, hoveredWindow)) window.render(gfx, mouseX, mouseY, partialTicks);
            else window.render(gfx, Integer.MAX_VALUE, Integer.MAX_VALUE, partialTicks);
        }
    }

    @Nullable
    private McWindow getHoveredWindow(int mouseX, int mouseY) {
        synchronized (wmLock) {
            for (var window : windows()) {
                if (window.isMouseOver(mouseX, mouseY)) {
                    return window;
                }
            }
            return null;
        }
    }

    public synchronized void createWindow(McWindow window) {
        synchronized (this.wmLock) {
            if (this.windows.contains(window)) throw new IllegalArgumentException("Window already exists.");

            window.wm = this;
            this.windows.add(window);
            this.midStack.add(window);
            window.topMost = false;
            window.bottomMost = false;
        }
    }

    private synchronized void setTopStack(McWindow window) {
        synchronized (this.wmLock) {
            this.bottomStack.remove(window);
            this.midStack.remove(window);
            this.topStack.add(window);
            window.topMost = true;
            window.bottomMost = false;
        }
    }

    private synchronized void setBottomStack(McWindow window) {
        synchronized (this.wmLock) {
            this.topStack.remove(window);
            this.midStack.remove(window);
            this.bottomStack.add(window);
            window.topMost = false;
            window.bottomMost = true;
        }
    }

    private synchronized void setMidStack(McWindow window) {
        synchronized (this.wmLock) {
            this.topStack.remove(window);
            this.midStack.remove(window);
            this.bottomStack.add(window);
            window.topMost = false;
            window.bottomMost = true;
        }
    }

    public synchronized void setTopMost(McWindow window, boolean topMost) {
        synchronized (this.wmLock) {
            if (topMost) {
                if (window.topMost) {
                    return;
                }
                setTopStack(window);
            } else if (window.topMost) {
                setMidStack(window);
            }
        }
    }

    public synchronized void setBottomMost(McWindow window, boolean bottomMost) {
        synchronized (this.wmLock) {
            if (bottomMost) {
                if (window.bottomMost) {
                    return;
                }
                setBottomStack(window);
            } else if (window.bottomMost) {
                setMidStack(window);
            }
        }
    }

    synchronized void destroyWindow(McWindow window) {
        synchronized (this.wmLock) {
            if (!this.windows.contains(window)) throw new IllegalArgumentException("Windows doesn't exist.");

            this.windows.remove(window);
            this.topStack.remove(window);
            this.midStack.remove(window);
            this.bottomStack.remove(window);
        }
    }

    public synchronized void moveToForeground(McWindow window) {
        synchronized (wmLock) {
            if (!this.windows.contains(window)) throw new IllegalArgumentException("Window doesn't exist.");

            this.windows.remove(window);
            this.windows.add(0, window);
            if (window.topMost) {
                this.topStack.remove(window);
                this.topStack.add(0, window);
            } else if (window.bottomMost) {
                this.bottomStack.remove(window);
                this.bottomStack.add(0, window);
            } else {
                this.midStack.remove(window);
                this.midStack.add(0, window);
            }

            this.setActiveWindow(window);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        synchronized (wmLock) {
            for (var window : windows()) {
                if (window.isMouseOver(mouseX, mouseY)) {
                    this.moveToForeground(window);
                    windowPress = window;
                    window.mouseClicked(mouseX, mouseY, button);
                    return true;
                }
            }
            return super.mouseClicked(mouseX, mouseY, button);
        }
    }

    private Iterable<McWindow> windows() {
        var concat = Iterators.concat(topStack.iterator(), midStack.iterator(), bottomStack.iterator());
        return () -> concat;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        synchronized (wmLock) {
            if (windowPress != null) {
                windowPress.mouseReleased(mouseX, mouseY, button);
                return true;
            }
            return super.mouseReleased(mouseX, mouseY, button);
        }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        synchronized (wmLock) {
            if (windowPress != null) {
                windowPress.mouseDragged(mouseX, mouseY, button, dragX, dragY);
                return true;
            }
            return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
        }
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        synchronized (wmLock) {
            for (var window : windows()) {
                if (window.isMouseOver(mouseX, mouseY)) {
                    window.mouseMoved(mouseX, mouseY);
                    return;
                }
            }
            super.mouseMoved(mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        synchronized (wmLock) {
            for (var window : windows()) {
                if (window.isMouseOver(mouseX, mouseY) && window.mouseScrolled(mouseX, mouseY, delta)) {
                    return true;
                }
            }
            return super.mouseScrolled(mouseX, mouseY, delta);
        }
    }

    public boolean isActiveWindow(McWindow window) {
        return activeWindow == window;
    }

    public McWindow getActiveWindow() {
        return activeWindow;
    }

    public int getWindowActiveColor() {
        return windowActiveColor;
    }

    public int getWindowInactiveColor() {
        return windowInactiveColor;
    }

    public void setActiveWindow(McWindow activeWindow) {
        synchronized (wmLock) {
            var oldWindow = this.activeWindow;
            if (oldWindow != null) {
                oldWindow.handleSetInactive();
            }
            this.activeWindow = activeWindow;
            activeWindow.handleSetActive();
        }
    }

    public Insets getBorder() {
        return border;
    }
}
