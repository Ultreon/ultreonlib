package com.ultreon.mods.lib.client.gui.v2;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.apache.commons.compress.utils.Sets;

import java.util.Set;

public class DesktopScreen extends Screen {
    private final McDesktop desktop;

    public DesktopScreen(LaunchOptions options) {
        super(options.title);

        this.desktop = new McDesktop(width, height, Lists.newArrayList(options.windows));
    }

    @Override
    public void renderBackground(PoseStack poseStack, int vOffset) {
        if (this.minecraft != null && this.minecraft.level != null) {
            this.fillGradient(poseStack, 0, 0, this.width, this.height, 0xc0101010, 0xd0101010);
        } else {
            fill(poseStack, 0, 0, this.width, this.height, 0xff303030);
        }
    }

    @Override
    public void renderDirtBackground(int vOffset) {

    }

    @Override
    protected void init() {
        super.init();
        desktop.setWidth(width);
        desktop.setHeight(height);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        desktop.render(poseStack, mouseX, mouseY, partialTicks);
    }

    public McDesktop getDesktop() {
        return desktop;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        desktop.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return desktop.mouseReleased(mouseX, mouseY, button);
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

    public static class LaunchOptions {
        private Component title;
        private final Set<McWindow> windows = Sets.newHashSet();

        public LaunchOptions title(Component title) {
            this.title = title;
            return this;
        }

        public LaunchOptions window(McWindow window) {
            this.windows.add(window);
            return this;
        }
    }
}
