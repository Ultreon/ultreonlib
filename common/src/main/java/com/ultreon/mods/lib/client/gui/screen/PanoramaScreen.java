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

package com.ultreon.mods.lib.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

/**
 * Panorama screen, for rendering a panorama in the background of your screen / menu.
 *
 * @author Qboi123
 */
public abstract class PanoramaScreen extends BaseScreen {
    public static final PanoramaRenderer PANORAMA = new PanoramaRenderer(TitleScreen.CUBE_MAP);
    public static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");

    /**
     * Panorama screen constructor.
     *
     * @param title screen title.
     */
    protected PanoramaScreen(Component title) {
        super(title);
    }

    /**
     * Render the panorama background/
     *
     * @param gfx         pose stack.
     * @param partialTicks render frame time.
     */
    public void renderPanorama(GuiGraphics gfx, float partialTicks) {
        PANORAMA.render(partialTicks, Mth.clamp(1.0f, 0.0f, 1.0f));
        RenderSystem.enableBlend();
        gfx.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        gfx.blit(PANORAMA_OVERLAY, 0, 0, this.width, this.height, 0.0f, 0.0f, 16, 128, 16, 128);
        gfx.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public void renderBackground(GuiGraphics gfx, float partialTicks) {
        assert this.minecraft != null;
        if (this.minecraft.level == null) {
            this.renderPanorama(gfx, partialTicks);
            return;
        }
        gfx.fillGradient(0, 0, this.width, this.height, 0xC0101010, 0xD0101010);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics gfx) {

    }
}
