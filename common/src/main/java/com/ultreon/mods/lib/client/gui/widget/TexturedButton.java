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

package com.ultreon.mods.lib.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("DuplicatedCode")
public abstract sealed class TexturedButton extends BaseButton permits Button {
    public TexturedButton(int x, int y, int width, int height, Component title, CommandCallback callback) {
        super(x, y, width, height, title, callback);
    }

    public TexturedButton(int x, int y, int width, int height, Component title, CommandCallback callback, TooltipFactory onTooltip) {
        super(x, y, width, height, title, callback, onTooltip);
    }

    /**
     * @return the widgets texture to render the button from.
     */
    protected abstract ResourceLocation getWidgetsTexture();

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
}
