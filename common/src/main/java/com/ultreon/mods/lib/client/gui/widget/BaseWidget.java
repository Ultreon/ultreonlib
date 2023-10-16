package com.ultreon.mods.lib.client.gui.widget;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.platform.InputConstants;
import com.ultreon.mods.lib.client.HasContextMenu;
import com.ultreon.mods.lib.client.gui.Clickable;
import com.ultreon.mods.lib.client.theme.Stylized;
import com.ultreon.mods.lib.client.theme.ThemeComponent;
import com.ultreon.mods.lib.client.theme.ThemeRootComponent;
import com.ultreon.mods.lib.util.ScissorStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings("SameParameterValue")
public abstract class BaseWidget extends AbstractWidget implements Clickable, Stylized {
    protected final Minecraft minecraft;
    protected final Font font;
    private long multiClickDelay = 500L;
    private int textColor;
    private boolean usingCustomTextColor;
    private int clicks;
    private long lastClickTime;

    public BaseWidget(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);

        this.minecraft = Minecraft.getInstance();
        this.font = minecraft.font;
    }

    @Override
    public final boolean leftClick() {
        clicks = clicks + 1;
        lastClickTime = System.currentTimeMillis();
        return onLeftClick(clicks);
    }

    public boolean onLeftClick(int clicks) {
        return false;
    }

    public final boolean doubleClick() {
        clicks = getClicks() + 2;
        lastClickTime = System.currentTimeMillis();
        return this.onLeftClick(clicks);
    }

    protected final void renderScrollingString(@NotNull GuiGraphics gfx, @NotNull Font font, int inset, int color) {
        int k = this.getX() + inset;
        int l = this.getX() + this.getWidth() - inset;
        renderScrollingString0(gfx, font, this.getMessage(), k, this.getY(), l, this.getY() + this.getHeight(), color);
    }

    protected final void renderScrollingString(GuiGraphics gfx, Font font, int x, int y, int color) {
        renderScrollingString0(gfx, font, this.getMessage(), x, y, x + this.getWidth(), y + this.getHeight(), color);
    }

    protected final void renderScrollingString(GuiGraphics gfx, Font font, int x, int y, int width, int height, int color) {
        renderScrollingString0(gfx, font, this.getMessage(), x, y, x + width, y + height, color);
    }

    protected void renderScrollingString0(GuiGraphics guiGraphics, Font font, Component component, int x1, int y1, int x2, int y2, int color) {
        renderScrollingString0(guiGraphics, font, component, (x1 + x2) / 2, x1, y1, x2, y2, color);
    }

    protected void renderScrollingString0(GuiGraphics gfx, Font font, Component component, int i, int x1, int y1, int x2, int y2, int color) {
        int textWidth = font.width(component);
        Preconditions.checkNotNull(font, "font");
        int textY = ((y1 + y2) - 9) / 2 + 1;
        int maxWidth = x2 - x1;
        int textX;
        if (textWidth > maxWidth) {
            textX = textWidth - maxWidth;
            double seconds = (double) Util.getMillis() / 1000.0;
            double easing = Math.max((double)textX * 0.5, 3.0);
            double delta = Math.sin(1.5707963267948966 * Math.cos(6.283185307179586 * seconds / easing)) / 2.0 + 0.5;
            double finalX = Mth.lerp(delta, 0.0, textX);

            ScissorStack.pushScissorTranslated(gfx, x1, y1, maxWidth, y2 - y1);
            gfx.drawString(font, component, x1 - (int)finalX, textY, color);
            ScissorStack.popScissor();
        } else {
            textX = Mth.clamp(i, x1 + textWidth / 2, x2 - textWidth / 2);
            gfx.drawCenteredString(font, component, textX, textY, color);
        }

    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {

    }

    @Override
    public final boolean middleClick() {
        if (this instanceof TabCloseable tabCloseable) {
            tabCloseable.closeTab();
            return true;
        } else {
            return onMiddleClick();
        }
    }

    public boolean onMiddleClick() {
        return false;
    }

    @Override
    public final boolean rightClick() {
        if (this instanceof HasContextMenu hasContextMenu)
            return hasContextMenu.contextMenu(getX(), getY(), InputConstants.MOUSE_BUTTON_RIGHT) != null;
        else
            return onRightClick();
    }

    public boolean onRightClick() {
        return false;
    }

    public final int getClicks() {
        long timeSinceLastClick = getTimeSinceLastClick();
        clicks = timeSinceLastClick < multiClickDelay ? clicks : 0;
        return clicks;
    }

    @Override
    public boolean isFocused() {
        return GLFW.glfwGetWindowAttrib(this.minecraft.getWindow().getWindow(), GLFW.GLFW_FOCUSED) == GLFW.GLFW_TRUE && super.isFocused();
    }

    protected final long getTimeSinceLastClick() {
        return this.lastClickTime - System.currentTimeMillis();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY)) {
            return switch (button) {
                case InputConstants.MOUSE_BUTTON_LEFT -> leftClick();
                case InputConstants.MOUSE_BUTTON_MIDDLE -> middleClick();
                case InputConstants.MOUSE_BUTTON_RIGHT -> rightClick();
                default -> false;
            };
        }

        return false;
    }

    public long getMultiClickDelay() {
        return multiClickDelay;
    }

    public void setMultiClickDelay(long multiClickDelay) {
        this.multiClickDelay = multiClickDelay;
    }

    @Override
    public ThemeComponent getThemeComponent() {
        return ThemeRootComponent.CONTENT;
    }

    public int getTextColor() {
        if (this.isUsingCustomTextColor()) {
            return textColor;
        }
        return this.getStyle().getTextColor().getRgb();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public boolean isUsingCustomTextColor() {
        return usingCustomTextColor;
    }

    public void setUsingCustomTextColor(boolean usingCustomTextColor) {
        this.usingCustomTextColor = usingCustomTextColor;
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static void drawCenteredStringWithoutShadow(GuiGraphics gfx, Font font, String text, int x, int y, int color) {
        gfx.drawString(font, text, x - font.width(text) / 2, y, color, false);
    }

    public static void drawCenteredStringWithoutShadow(GuiGraphics gfx, Font font, Component text, int x, int y, int color) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font, formattedCharSequence, x - font.width(formattedCharSequence) / 2, y, color, false);
    }

    public static void drawCenteredStringWithoutShadow(GuiGraphics gfx, Font font, FormattedCharSequence text, int x, int y, int color) {
        gfx.drawString(font, text, x - font.width(text) / 2, y, color, false);
    }
}
