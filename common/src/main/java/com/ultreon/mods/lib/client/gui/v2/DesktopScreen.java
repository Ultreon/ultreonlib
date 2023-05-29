package com.ultreon.mods.lib.client.gui.v2;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.apache.commons.compress.utils.Sets;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Set;

public class DesktopScreen extends Screen {
    private final Screen back;
    private McDesktop desktop;

    public DesktopScreen(LaunchOptions options) {
        super(options.title);

        this.back = options.back;

        this.desktop = new McDesktop(this, this.width, this.height, Lists.newArrayList(options.windows));
        this.desktop.createWindow(new DesktopWindow());
        this.desktop.createWindow(new TaskbarWindow(20));
        this.desktop.border = new Insets(0, 0, 20, 0);
    }

    @Override
    @SuppressWarnings("SuspiciousNameCombination")
    public void renderBackground(@NotNull PoseStack poseStack) {
        if (this.minecraft != null && this.minecraft.level != null) {
            fillGradient(poseStack, 0, 0, this.width, this.height, 0xc0101010, 0xd0101010);
        } else {
            fill(poseStack, 0, 0, this.width, this.height, 0x00000000);
        }
    }

    @Override
    public void renderDirtBackground(@NotNull PoseStack poseStack) {

    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);

        this.desktop.setWidth(this.width);
        this.desktop.setHeight(this.height);
        this.desktop.render(poseStack, mouseX, mouseY, partialTicks);
    }

    public McDesktop getDesktop() {
        return this.desktop;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        this.desktop.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return this.desktop.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return desktop.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return desktop.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        return desktop.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return desktop.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return desktop.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return desktop.charTyped(codePoint, modifiers);
    }

    public void shutdown() {
        this.desktop = null;
        Minecraft.getInstance().setScreen(back);
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
