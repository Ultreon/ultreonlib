package com.ultreon.mods.lib.client.gui.widget.toolbar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.Clickable;
import com.ultreon.mods.lib.client.gui.Theme;
import com.ultreon.mods.lib.client.gui.Themed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ToolbarButton extends ToolbarItem implements Themed, Clickable {
    @Nullable
    private CommandCallback command;

    private final Object lock = new Object();
    private Theme theme;

    public ToolbarButton(int x, int y, int width, Component message) {
        this(x, y, width, message, null);
    }

    public ToolbarButton(int x, int y, int width, Component message, @Nullable CommandCallback command) {
        super(x, y, width, 20, message);
        this.command = command;
        reloadTheme();
    }

    @Override
    public void updateWidgetNarration(@NotNull NarrationElementOutput narration) {
        narration.add(NarratedElementType.TITLE, getMessage());
    }

    public void onLeftClick(int clicks) {
        synchronized (lock) {
            if (command != null) {
                command.call(this);
            }
        }
    }

    protected final ResourceLocation getWidgetsTexture() {
        return theme.getWidgetsTexture();
    }

    @Override
    public void reloadTheme() {
        theme = UltreonLib.getTheme();
        setTextColor(theme.getButtonTextColor());
    }

    @Override
    public void renderWidget(@NotNull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, getWidgetsTexture());
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        AbstractButton.blitNineSliced(poseStack, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, this.getTexVOffset());
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        int k = getTextColor();
        this.renderString(poseStack, minecraft.font, k | Mth.ceil(this.alpha * 255.0f) << 24);
    }

    public int getTexVOffset() {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (this.isHoveredOrFocused()) {
            i = 2;
        }
        return 46 + i * 20;
    }

    public void renderString(PoseStack poseStack, Font font, int i) {
        this.renderScrollingString(poseStack, font, 2, i);
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        leftClick();
    }

    public void setCommand(@Nullable CommandCallback command) {
        synchronized (lock) {
            this.command = command;
        }
    }

    @FunctionalInterface
    public interface CommandCallback {
        void call(ToolbarButton button);
    }
}
