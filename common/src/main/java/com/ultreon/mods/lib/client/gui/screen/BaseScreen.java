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
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.HasContextMenu;
import com.ultreon.mods.lib.client.gui.Theme;
import com.ultreon.mods.lib.client.gui.Themed;
import com.ultreon.mods.lib.client.gui.widget.menu.ButtonMenuItem;
import com.ultreon.mods.lib.client.gui.widget.menu.ContextMenu;
import com.ultreon.mods.lib.client.input.MouseButton;
import com.ultreon.mods.lib.mixin.common.ScreenAccess;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class BaseScreen extends Screen implements Themed {
    private static final String CLOSE_ICON = "×";
    private static final String CLOSE_ICON_HOVER = ChatFormatting.RED + CLOSE_ICON;

    private static final ResourceLocation WIDGETS_DARK = UltreonLib.res("textures/gui/widgets_dark.png");
    private static final ResourceLocation WIDGETS = UltreonLib.res("textures/gui/widgets.png");
    private static final ResourceLocation WIDGETS_LIGHT = UltreonLib.res("textures/gui/widgets_light.png");

    protected static final int BORDER_SIZE = 7;

    private ContextMenu contextMenu = null;
    private Theme theme;
    private Screen back;

    protected BaseScreen(Component title) {
        this(title, null);
    }

    protected BaseScreen(Component title, Screen back) {
        super(title);
        this.theme = UltreonLib.getTheme();
        this.back = back;
    }

    public void show() {
        show(this);
    }

    @SuppressWarnings("unused")
    private static void show(BaseScreen screen) {
        throw new AssertionError();
    }

    public final Theme getTheme() {
        return theme;
    }

    @Override
    public void reloadTheme() {
        this.theme = UltreonLib.getTheme();
    }

    @Override
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        renderCloseButton(gfx, mouseX, mouseY);

        boolean flag = contextMenu != null && contextMenu.isMouseOver(mouseX, mouseY);

        int mx = flag ? Integer.MIN_VALUE : mouseX;
        int my = flag ? Integer.MIN_VALUE : mouseY;
        for (Renderable widget : ((ScreenAccess) this).getRenderables()) {
            widget.render(gfx, mx, my, partialTicks);
        }

        renderContextMenu(gfx, mouseX, mouseY, partialTicks);
    }

    private void renderContextMenu(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        ContextMenu menu = contextMenu;
        if (menu != null) {
            RenderSystem.disableDepthTest();
            menu.render(gfx, mouseX, mouseY, partialTicks);
        }
    }

    @Nullable
    public abstract Vec2 getCloseButtonPos();

    protected final boolean isPointBetween(int mouseX, int mouseY, int x, int y, int w, int h) {
        final int x1 = x + w;
        final int y1 = y + h;

        return mouseX >= x && mouseY >= y && mouseX <= x1 && mouseY <= y1;
    }

    protected final void renderCloseButton(GuiGraphics gfx, int mouseX, int mouseY) {
        Vec2 iconPos = getCloseButtonPos();
        if (iconPos != null) {
            int iconX = (int) iconPos.x;
            int iconY = (int) iconPos.y;
            if (isPointBetween(mouseX, mouseY, iconX, iconY, 6, 6)) {
                gfx.drawString(this.font, CLOSE_ICON_HOVER, iconX, iconY, theme.getTitleColor(), false);
            } else {
                gfx.drawString(this.font, CLOSE_ICON, iconX, iconY, theme.getTitleColor(), false);
            }
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (isHoveringContextMenu((int) mouseX, (int) mouseY) && contextMenu.mouseReleased(mouseX, mouseY, button)) {
            return true;
        }

        return super.mouseReleased(mouseX, mouseY, button);
    }

    private boolean isHoveringContextMenu(int mouseX, int mouseY) {
        return contextMenu != null &&
                mouseX >= contextMenu.getX() && mouseX <= contextMenu.getX() + contextMenu.getWidth() &&
                mouseY >= contextMenu.getY() && mouseY <= contextMenu.getY() + contextMenu.getHeight();
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        if (isHoveringContextMenu((int) mouseX, (int) mouseY)) {
            contextMenu.mouseMoved(mouseX, mouseY);
            return;
        }

        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isAtCloseButton(mouseX, mouseY)) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            back();
            return true;
        }

        if (isHoveringContextMenu((int) mouseX, (int) mouseY) && contextMenu.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        if (button == MouseButton.RIGHT && isAtTitleBar(mouseX, mouseY)) {
            ContextMenu menu = new ContextMenu((int) mouseX, (int) mouseY, null);
            menu.add(new ButtonMenuItem(menu, Component.literal("Close"), menuItem -> closeScreen()));
            placeContextMenu(menu);
            return true;
        }
        if (button == MouseButton.RIGHT) {
            ContextMenu menu = getContextMenu((int) mouseX, (int) mouseY);
            GuiEventListener it = getChildAt(mouseX, mouseY).orElse(null);
            if (it instanceof HasContextMenu contextMenuHolder) {
                contextMenuHolder.contextMenu((int) mouseX, (int) mouseY, button);
            }
            if (menu != null) {
                placeContextMenu(menu);
                return true;
            }
        }
        if (button == MouseButton.LEFT && contextMenu != null && !contextMenu.isMouseOver(mouseX, mouseY)) {
            contextMenu.onClose();
            contextMenu = null;
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public final void closeScreen() {
        onClose();
    }

    protected ContextMenu getContextMenu(int x, int y) {
        return null;
    }

    protected boolean isAtTitleBar(double mouseX, double mouseY) {
        return false;
    }

    public void placeContextMenu(@NotNull ContextMenu menu) {
        this.contextMenu = Objects.requireNonNull(menu);
    }

    public void closeContextMenu() {
        this.contextMenu = null;
    }

    protected void back() {
        Minecraft.getInstance().setScreen(back);
    }

    protected final boolean isAtCloseButton(int mouseX, int mouseY) {
        Vec2 iconPos = getCloseButtonPos();
        if (iconPos == null) {
            return false;
        }

        return isPointBetween(mouseX, mouseY, (int) iconPos.x, (int) iconPos.y, 6, 6);
    }

    protected final boolean isAtCloseButton(double mouseX, double mouseY) {
        return isAtCloseButton((int) mouseX, (int) mouseY);
    }

    public static void renderFrame(GuiGraphics gfx, int x, int y, int width, int height, Theme theme) {
        renderFrame(gfx, x, y, width, height, theme, 0);
    }

    @SuppressWarnings("PointlessArithmeticExpression")
    public static void renderFrame(GuiGraphics gfx, int x, int y, int width, int height, Theme theme, int u) {
        var tex = switch (theme) {
            case DARK -> WIDGETS_DARK;
            case LIGHT, MIX -> WIDGETS_LIGHT;
            default -> WIDGETS;
        };
        gfx.blit(tex, x, y, 7, 7, u + 0, 0, 7, 7, 256, 256);
        gfx.blit(tex, x + 7, y, width, 7, u + 7, 0, 7, 7, 256, 256);
        gfx.blit(tex, x + 7 + width, y, 7, 7, u + 14, 0, 7, 7, 256, 256);
        gfx.blit(tex, x, y + 7, 7, height, u + 0, 7, 7, 7, 256, 256);
        gfx.blit(tex, x + 7, y + 7, width, height, u + 7, 7, 7, 7, 256, 256);
        gfx.blit(tex, x + 7 + width, y + 7, 7, height, u + 14, 7, 7, 7, 256, 256);
        gfx.blit(tex, x, y + 7 + height, 7, 7, u + 0, 14, 7, 7, 256, 256);
        gfx.blit(tex, x + 7, y + 7 + height, width, 7, u + 7, 14, 7, 7, 256, 256);
        gfx.blit(tex, x + 7 + width, y + 7 + height, 7, 7, u + 14, 14, 7, 7, 256, 256);
    }

    public static void renderTitleFrame(GuiGraphics gfx, int x, int y, int width, int height, Theme theme) {
        renderTitleFrame(gfx, x, y, width, height, theme, 0);
    }

    @SuppressWarnings("PointlessArithmeticExpression")
    public static void renderTitleFrame(GuiGraphics gfx, int x, int y, int width, int height, Theme theme, int u) {
        var tex =switch (theme) {
            case DARK, MIX -> WIDGETS_DARK;
            case LIGHT -> WIDGETS_LIGHT;
            default -> WIDGETS;
        };
        gfx.blit(tex, x, y, 7, 7, u + 0, 0, 7, 7, 256, 256);
        gfx.blit(tex, x + 7, y, width, 7, u + 7, 0, 7, 7, 256, 256);
        gfx.blit(tex, x + 7 + width, y, 7, 7, u + 14, 0, 7, 7, 256, 256);
        gfx.blit(tex, x, y + 7, 7, height, u + 0, 7, 7, 7, 256, 256);
        gfx.blit(tex, x + 7, y + 7, width, height, u + 7, 7, 7, 7, 256, 256);
        gfx.blit(tex, x + 7 + width, y + 7, 7, height, u + 14, 7, 7, 7, 256, 256);
        gfx.blit(tex, x, y + 7 + height, 7, 7, u + 0, 14, 7, 7, 256, 256);
        gfx.blit(tex, x + 7, y + 7 + height, width, 7, u + 7, 14, 7, 7, 256, 256);
        gfx.blit(tex, x + 7 + width, y + 7 + height, 7, 7, u + 14, 14, 7, 7, 256, 256);
    }

    public void open() {
        this.back = Minecraft.getInstance().screen;
        Minecraft.getInstance().setScreen(this);
    }

    public static void drawCenteredStringWithoutShadow(GuiGraphics gfx, Font font, String text, int x, int y, int color) {
        gfx.drawString(font, text, x - font.width(text) / 2, y, color);
    }

    public static void drawCenteredStringWithoutShadow(GuiGraphics gfx, Font font, Component text, int x, int y, int color) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font, formattedCharSequence, x - font.width(formattedCharSequence) / 2, y, color);
    }

    public static void drawCenteredStringWithoutShadow(GuiGraphics gfx, Font font, FormattedCharSequence text, int x, int y, int color) {
        gfx.drawString(font, text, x - font.width(text) / 2, y, color);
    }
}
