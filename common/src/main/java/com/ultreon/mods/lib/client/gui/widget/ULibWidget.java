package com.ultreon.mods.lib.client.gui.widget;

import com.ultreon.mods.lib.UltreonLib;
import com.ultreon.mods.lib.client.gui.GuiRenderer;
import com.ultreon.mods.lib.client.gui.widget.menu.ContextMenu;
import com.ultreon.mods.lib.client.theme.Theme;
import com.ultreon.mods.lib.client.theme.WidgetPlacement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

public interface ULibWidget extends Renderable, GuiEventListener, NarratableEntry {
    Component getMessage();

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    @Override
    @ApiStatus.NonExtendable
    default void render(@NotNull GuiGraphics gfx, int i, int j, float f) {
        this.render(new GuiRenderer(gfx, UltreonLib.getTheme(), UltreonLib.getTitleStyle()), i, j, f);
    }

    void render(GuiRenderer renderer, int mouseX, int mouseY, float partialTicks);

    boolean isFocused();

    boolean isUsingCustomTextColor();

    boolean isHovered();

    boolean isDragging();

    void setFocused(boolean focused);

    void revalidate();

    default void setPosition(Vector2i pos) {
        this.setX(pos.x);
        this.setY(pos.y);
    }

    void setX(int x);

    void setY(int y);

    default void setSize(Vector2i size) {
        this.setWidth(size.x);
        this.setHeight(size.y);
    }

    void setWidth(int x);

    void setHeight(int y);

    default void renderScrollingString(@NotNull GuiRenderer renderer, int inset, int color) {
        int k = this.getX() + inset;
        int l = this.getX() + this.getWidth() - inset;
        renderer.renderScrollingString(this.getMessage(), k, this.getY(), l, this.getY() + this.getHeight(), color);
    }

    default void renderScrollingString(@NotNull GuiRenderer renderer, int x, int y, int width, int height, int color) {
        renderer.renderScrollingString(this.getMessage(), x, y, x + width, y + height, color);
    }

    default void renderScrollingString(GuiRenderer renderer, int x, int y, int color) {
        renderer.renderScrollingString(this.getMessage(), x, y, x + this.getWidth(), y + this.getHeight(), color);
    }

    default int getTextColor() {
        if (this.isUsingCustomTextColor()) return 0xffffffff;
        return this.getTheme().getTextColor().getRgb();
    }

    Theme getTheme();

    void setPlacement(WidgetPlacement placement);

    void setParent(WidgetsContainer parent);

    ContextMenu createContextMenu(int x, int y);

    WidgetPlacement getPlacement();

    WidgetsContainer getParent();
}
