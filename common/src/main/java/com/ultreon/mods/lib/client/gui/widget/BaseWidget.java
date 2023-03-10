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

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.client.HasContextMenu;
import com.ultreon.mods.lib.client.gui.Clickable;
import com.ultreon.mods.lib.client.gui.Themed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public abstract class BaseWidget extends AbstractWidget implements Clickable, Themed {
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
    public final void leftClick() {
        clicks = clicks + 1;
        lastClickTime = System.currentTimeMillis();
        onLeftClick(clicks);
    }

    public void onLeftClick(int clicks) {

    }

    public final void doubleClick() {
        clicks = getClicks() + 2;
        lastClickTime = System.currentTimeMillis();
        onLeftClick(clicks);
    }

    @Override
    public final void middleClick() {
        if (this instanceof TabCloseable tabCloseable) {
            tabCloseable.closeTab();
        } else {
            onMiddleClick();
        }
    }

    public void onMiddleClick() {

    }

    @Override
    public final void rightClick() {
        if (this instanceof HasContextMenu hasContextMenu) {
            hasContextMenu.contextMenu(getX(), getY(), InputConstants.MOUSE_BUTTON_RIGHT);
        } else {
            onRightClick();
        }
    }

    public void onRightClick() {

    }

    public final int getClicks() {
        long timeSinceLastClick = getTimeSinceLastClick();
        clicks = timeSinceLastClick < multiClickDelay ? clicks : 0;
        return clicks;
    }

    protected final long getTimeSinceLastClick() {
        return this.lastClickTime - System.currentTimeMillis();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isMouseOver(mouseX, mouseY)) {
            switch (button) {
                case InputConstants.MOUSE_BUTTON_LEFT -> leftClick();
                case InputConstants.MOUSE_BUTTON_MIDDLE -> middleClick();
                case InputConstants.MOUSE_BUTTON_RIGHT -> rightClick();
                default -> {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    public long getMultiClickDelay() {
        return multiClickDelay;
    }

    public void setMultiClickDelay(long multiClickDelay) {
        this.multiClickDelay = multiClickDelay;
    }

    public int getTextColor() {
        if (this.isUsingCustomTextColor()) {
            return textColor;
        }
        return getTheme().getTextColor();
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

    public static void drawCenteredStringWithoutShadow(PoseStack poseStack, Font font, String text, int x, int y, int color) {
        font.draw(poseStack, text, (float)(x - font.width(text) / 2), (float)y, color);
    }

    public static void drawCenteredStringWithoutShadow(PoseStack poseStack, Font font, Component text, int x, int y, int color) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        font.draw(poseStack, formattedCharSequence, (float)(x - font.width(formattedCharSequence) / 2), (float)y, color);
    }

    public static void drawCenteredStringWithoutShadow(PoseStack poseStack, Font font, FormattedCharSequence text, int x, int y, int color) {
        font.draw(poseStack, text, (float)(x - font.width(text) / 2), (float)y, color);
    }
}
