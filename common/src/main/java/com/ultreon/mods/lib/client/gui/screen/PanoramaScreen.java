package com.ultreon.mods.lib.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.commons.Color;
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
 * @author XyperCode
 */
public abstract class PanoramaScreen extends ULibScreen {
    public static final PanoramaRenderer PANORAMA = new PanoramaRenderer(TitleScreen.CUBE_MAP);
    public static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
    public static final Color COLOR_1 = Color.hex("#101010c0");
    public static final Color COLOR_2 = Color.hex("#101010d0");

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
    public void renderPanorama(@NotNull GuiRenderer gfx, float partialTicks) {
        PANORAMA.render(partialTicks, Mth.clamp(1.0f, 0.0f, 1.0f));
        RenderSystem.enableBlend();
        gfx.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        gfx.blit(PANORAMA_OVERLAY, 0, 0, this.width, this.height, 0, 0, 16, 128, 16, 128);
        gfx.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void renderBackground(GuiRenderer gfx, int mouseX, int mouseY, float partialTicks) {
        assert this.minecraft != null;
        if (this.minecraft.level == null) {
            this.renderPanorama(gfx, partialTicks);
            return;
        }
        gfx.fillGradient(0, 0, this.width, this.height, COLOR_1, COLOR_2);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics gfx, int i, int j, float f) {

    }
}
