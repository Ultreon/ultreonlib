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
import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.UltreonLibConfig;
import com.ultreon.mods.lib.client.gui.Theme;
import com.ultreon.mods.lib.client.gui.screen.BaseScreen;
import com.ultreon.mods.lib.client.gui.widget.BaseContainerWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Qboi123
 */
public class ContextMenu extends BaseContainerWidget {
    // Constants
    private static final int BORDER_WIDTH = 5;
    private static final ResourceLocation MENU_DARK = UltreonLib.res("textures/gui/widgets/context_menu/dark");
    private static final ResourceLocation MENU_LIGHT = UltreonLib.res("textures/gui/widgets/context_menu/light");
    private static final ResourceLocation MENU_NORMAL = UltreonLib.res("textures/gui/widgets/context_menu/normal");

    // Entries
    private final List<MenuItem> entries = new ArrayList<>();

    // Events.
    private OnClose onClose = menu -> {
    };
    private Theme theme;

    /**
     * @param x     position x to place.
     * @param y     position y to place.
     * @param title context menu title.
     */
    public ContextMenu(int x, int y, @Nullable Component title) {
        this(x, y, title, UltreonLibConfig.THEME.get());
    }

    /**
     * @param x     position x to place.
     * @param y     position y to place.
     * @param title context menu title.
     * @deprecated  Use {@link #ContextMenu(int, int, Component, Theme)} instead. As it uses specific themes.
     */
    @Deprecated
    public ContextMenu(int x, int y, @Nullable Component title, boolean darkMode) {
        this(x, y, title, UltreonLibConfig.THEME.get() == Theme.DARK ? Theme.DARK : Theme.NORMAL);
    }

    public ContextMenu(int x, int y, @Nullable Component title, Theme theme) {
        super(x, y, BORDER_WIDTH * 2, BORDER_WIDTH * 2, title);
        this.theme = theme;
    }

    public boolean isDarkMode() {
        return theme == Theme.DARK;
    }

    public void setDarkMode(boolean darkMode) {
        this.theme = darkMode ? Theme.DARK : Theme.NORMAL;
    }

    /**
     * Updates narration.
     *
     * @param narration output for narration elements.
     */
    @Override
    public void updateWidgetNarration(@NotNull NarrationElementOutput narration) {
        narration.add(NarratedElementType.TITLE, this.createNarrationMessage());
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        gfx.pose().pushPose();
        gfx.pose().translate(0, 0, 100);
        RenderSystem.setShaderTexture(0, switch (getTheme()) {
            case DARK -> MENU_DARK;
            case LIGHT, MIX -> MENU_LIGHT;
            default -> MENU_NORMAL;
        });

        Component message = getMessage();
        boolean hasTitle;
        //noinspection ConstantConditions
        if (message != null) {
            hasTitle = !message.getString().isBlank();
        } else {
            hasTitle = false;
        }

        Font font = Minecraft.getInstance().font;
        BaseScreen.renderFrame(gfx, getX(), getY(), width - 14, height - 4 - (hasTitle ? 0 : font.lineHeight + 1), theme, 42);

        //noinspection ConstantConditions
        if (message != null) {
            gfx.drawString(font, message, getX() + 7, getY() + 5, theme == Theme.DARK ? 0xffffffff : 0xff333333, false);
        }

        this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;

        gfx.pose().pushPose();
        int y = this.getY() + 5 + (hasTitle ? font.lineHeight + 1 : 0);
        int x = this.getX() + 5;
        Stream<MenuItem> entryStream = entries.stream();
        IntStream minWidths = entryStream.mapToInt(MenuItem::getMinWidth);
        int maxMinWidth = minWidths.max().orElse(0);

        for (MenuItem menuItem : entries) {
            width = Math.max(width, menuItem.getMinWidth());
        }

        for (MenuItem menuItem : entries) {
            menuItem.setX(x);
            menuItem.setY(y);
            menuItem.setWidth(Mth.clamp(maxMinWidth, menuItem.getMinWidth(), menuItem.getMaxWidth()));
            menuItem.render(gfx, mouseX, mouseY, partialTicks);

            y += menuItem.getHeight() + 2;
        }

        gfx.pose().popPose();
        gfx.pose().popPose();
    }

    /**
     * Adds a menu item entry.
     *
     * @param menuItem menu item to add.
     * @param <T>      item type.
     * @return the same as menu item parameter.
     */
    public <T extends MenuItem> T add(T menuItem) {
        entries.add(menuItem);
        menuItem.setX(getX() + 5);
        menuItem.setY(getY() + 5);
        invalidateSize();
        return menuItem;
    }

    void invalidateSize() {
        width = BORDER_WIDTH * 2 + entries.stream().mapToInt(MenuItem::getMinWidth).max().orElse(1);
        height = BORDER_WIDTH * 2 + entries.stream().mapToInt(MenuItem::getHeight).sum() + 2 * Math.max(entries.size() - 1, 0);
    }

    /**
     * Get all menu entries currently in the context menu.
     *
     * @return all menu entries (unmodifiable).
     */
    @Override
    public @NotNull List<? extends GuiEventListener> children() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * Set the on close event handler.
     *
     * @param onClose close handler.
     */
    public void setOnClose(OnClose onClose) {
        this.onClose = onClose;
    }

    /**
     * DON'T CALL IF YOU DON'T KNOW WHAT YOU ARE DOING.
     * This method is called for internal usage, and should not be called to close the context menu.
     * Use the {@link BaseScreen#closeContextMenu()} method to close the menu instead.
     */
    public final void onClose() {
        onClose.call(this);
    }

    /**
     * On Close event handler interface.
     * Could be used as lambda.
     */
    @FunctionalInterface
    public interface OnClose {
        /**
         * Handler itself.
         *
         * @param menu context menu for handling the closing with.
         */
        void call(ContextMenu menu);
    }
}
