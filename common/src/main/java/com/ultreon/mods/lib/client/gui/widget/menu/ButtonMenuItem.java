/*
 * Copyright (c) 2022. - Qboi SMP Development Team
 * Do NOT redistribute, or copy in any way, and do NOT modify in any way.
 * It is not allowed to hack into the code, use cheats against the code and/or compiled form.
 * And it is not allowed to decompile, modify or/and patch parts of code or classes or in full form.
 * Sharing this file isn't allowed either, and is hereby strictly forbidden.
 * Sharing decompiled code on social media or an online platform will cause in a report on that account.
 *
 * ONLY the owner can bypass these rules.
 */

package com.ultreon.mods.lib.client.gui.widget.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class ButtonMenuItem extends BaseButtonMenuItem {
    private final CommandCallback callback;

    public ButtonMenuItem(ContextMenu menu, Component message) {
        this(menu, message, btn -> {});
    }

    public ButtonMenuItem(ContextMenu menu, Component message, CommandCallback callback) {
        super(menu, message);
        this.callback = callback;
    }

    @Override
    public void render(@NotNull PoseStack pose, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        isHovered = isMouseOver(mouseX, mouseY);

        int color = switch (getTheme()) {
            case DARK -> isHovered ? 0xff505050 :  0xff404040;
            case LIGHT, MIX -> isHovered ? 0xffffffff :  0xffd0d0d0;
            default -> isHovered ? 0xff707070 :  0xffb0b0b0;
        };
        int textColor = isUsingCustomTextColor() ? getTextColor() : switch (getTheme()) {
            case DARK -> 0xffffffff;
            case LIGHT, MIX -> isHovered ? 0xff404040 : 0xff101010;
            default -> isHovered ? 0xfffffff : 0xff303030;
        };
        fill(pose, getX(), getY(), getX() + getWidth(), getY() + getHeight(), color);
        drawCenteredStringWithoutShadow(pose, font, this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, textColor | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void updateWidgetNarration(@NotNull NarrationElementOutput narration) {
        this.defaultButtonNarrationText(narration);
    }

    @Override
    protected void click() {
        this.callback.run(this);
    }

    @FunctionalInterface
    public interface CommandCallback {
        void run(ButtonMenuItem menuItem);
    }
}
