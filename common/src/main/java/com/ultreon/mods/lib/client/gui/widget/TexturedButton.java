package com.ultreon.mods.lib.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.WidgetSprites;
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
    @Deprecated
    protected abstract ResourceLocation getWidgetsTexture();

    protected abstract WidgetSprites getWidgetSprites();

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        gfx.setColor(1.0f, 1.0f, 1.0f, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        // Blit sprite texture.
        gfx.blitSprite(this.getWidgetSprites().get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());

        // Set color, and render the button text.
        gfx.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int textColor = this.getTextColor();
        this.renderString(gfx, minecraft.font, textColor| Mth.ceil(this.alpha * 255.0f) << 24);
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

    public void renderString(GuiGraphics gfx, Font font, int i) {
        this.renderScrollingString(gfx, font, 2, i);
    }
}
