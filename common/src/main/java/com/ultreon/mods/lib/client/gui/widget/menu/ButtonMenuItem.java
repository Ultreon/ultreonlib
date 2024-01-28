package com.ultreon.mods.lib.client.gui.widget.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.commons.Color;
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
    public void renderWidget(@NotNull GuiRenderer renderer, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        this.isHovered = isMouseOver(mouseX, mouseY);

        Color accentColor = getStyle().getAccentColor();
        Color bgColor = getStyle().getSecondaryColor();
        int textColor = isUsingCustomTextColor() ? getTextColor() : getStyle().getTextColor().getRgb();
        renderer.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), (this.isHovered() ? bgColor.brighter() : bgColor));
        renderer.box(getX(), getY(), getWidth(), getHeight(), accentColor.getRgb());
        renderer.textCenter(this.getMessage(), this.getX() + this.width / 2, this.getY() + (this.height - 8) / 2, textColor | Mth.ceil(this.alpha * 255.0F) << 24, false);
    }

    @Override
    public void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        this.defaultButtonNarrationText(output);
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
