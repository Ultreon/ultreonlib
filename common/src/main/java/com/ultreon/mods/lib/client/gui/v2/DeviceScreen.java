package com.ultreon.mods.lib.client.gui.v2;

import com.google.common.collect.Lists;
import com.ultreon.mods.lib.client.gui.screen.BaseScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import org.apache.commons.compress.utils.Sets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.Set;

/**
 * Screen for showing the window manager, the desktop and the taskbar.
 */
public class DeviceScreen extends BaseScreen {
    private final Screen back;
    private final McApplication kernel;
    protected int desktopX = 0;
    protected int desktopY = 0;
    protected int desktopWidth;
    protected int desktopHeight;
    private McOperatingSystem system;

    public DeviceScreen(LaunchOptions options) {
        super(options.title);

        this.back = options.back;

        this.desktopWidth = this.width;
        this.desktopHeight = this.height;

        this.system = new McOperatingSystem(this, this.desktopX, this.desktopY, this.desktopWidth, this.desktopHeight, Lists.newArrayList(options.windows), this.createDesktopApp());
        this.system.border = new Insets(0, 0, 20, 0);
        this.kernel = this.system.kernel;
    }


    @SafeVarargs
    protected final <T extends McApplication> void registerApp(ApplicationId id, McApplicationFactory<T> factory, T... typeGetter) {
        this.system.registerApp(id, factory, typeGetter);
    }

    protected McDesktopApplication createDesktopApp() {
        return new McDesktopApplication();
    }

    /**
     * @return the desktop window related to the {@link #getSystem()} method.
     */
    public final McDesktopApplication getDesktopApp() {
        return this.system.getDesktop();
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gfx, mouseX, mouseY, partialTicks);

        double[] xpos = new double[1];
        double[] ypos = new double[1];

        assert this.minecraft != null;
        GLFW.glfwGetCursorPos(this.minecraft.getWindow().getWindow(), xpos, ypos);

        if (xpos[0] < 0) mouseX = Integer.MIN_VALUE;
        if (ypos[0] < 0) mouseY = Integer.MIN_VALUE;

        if (xpos[0] > this.minecraft.getWindow().getWidth()) mouseX = Integer.MAX_VALUE;
        if (ypos[0] > this.minecraft.getWindow().getHeight()) mouseY = Integer.MAX_VALUE;

        this.system.setWidth(this.width);
        this.system.setHeight(this.height);
        this.system.render(gfx, mouseX, mouseY, partialTicks);
    }

    /**
     * @return always null, we don't need a close button on a device screen.
     */
    @Override
    public @Nullable Vec2 getCloseButtonPos() {
        return null;
    }

    /**
     * @return the device's OS desktop.
     */
    public IMcOperatingSystem getSystem() {
        return this.system;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        this.system.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return this.system.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return system.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return system.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amountX, double amountY) {
        return system.mouseScrolled(mouseX, mouseY, amountX, amountY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return system.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return system.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return system.charTyped(codePoint, modifiers);
    }

    public void onShutdown() {
        this.system = null;
        Minecraft.getInstance().setScreen(back);
    }

    public McApplication getKernel() {
        return kernel;
    }

    public static class LaunchOptions {
        private Component title;
        private Screen back;
        private final Set<McWindow> windows = Sets.newHashSet();

        public LaunchOptions title(Component title) {
            this.title = title;
            return this;
        }

        public LaunchOptions back(Screen back) {
            this.back = back;
            return this;
        }

        public LaunchOptions window(McWindow window) {
            this.windows.add(window);
            return this;
        }
    }
}
