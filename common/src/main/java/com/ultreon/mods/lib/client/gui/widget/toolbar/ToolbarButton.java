package com.ultreon.mods.lib.client.gui.widget.toolbar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.Clickable;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.client.theme.Style;
import com.ultreon.mods.lib.client.theme.Stylized;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ToolbarButton extends ToolbarItem implements Stylized, Clickable {
    @Nullable
    private CommandCallback command;

    private final Object lock = new Object();
    private GlobalTheme globalTheme;

    public ToolbarButton(int width, Component message) {
        this(width, message, null);
    }

    public ToolbarButton(int width, Component message, @Nullable CommandCallback command) {
        super(0, 0, width, 20, message);
        this.command = command;
        reloadTheme();
    }

    @Override
    public void updateWidgetNarration(@NotNull NarrationElementOutput narration) {
        narration.add(NarratedElementType.TITLE, getMessage());
    }

    @Override
    public boolean onLeftClick(int clicks) {
        synchronized (lock) {
            if (command != null) {
                command.call(this);
                return true;
            }
        }
        return false;
    }

    protected final WidgetSprites getWidgetSprites() {
        return globalTheme.getContentTheme().getButtonSprites();
    }

    @Override
    public void reloadTheme() {
        this.globalTheme = UltreonLib.getTheme();
        Style buttonStyle = this.globalTheme.getContentButtonStyle();
        this.setTextColor(buttonStyle.getTextColor().getRgb());
    }

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
        int textColor = this.active ? 0xffffff : 0xa0a0a0;
        this.renderString(gfx, minecraft.font, textColor | Mth.ceil(this.alpha * 255.0f) << 24);
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

    public void renderString(@NotNull GuiGraphics gfx, Font font, int i) {
        this.renderScrollingString(gfx, font, 2, i);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY)) {
            return this.leftClick();
        }
        return super.mouseClicked(mouseX, mouseY, button);
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
