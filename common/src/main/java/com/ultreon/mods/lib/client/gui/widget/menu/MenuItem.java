package com.ultreon.mods.lib.client.gui.widget.menu;

import com.ultreon.mods.lib.client.gui.widget.UIWidget;
import com.ultreon.mods.lib.client.theme.Stylized;
import com.ultreon.mods.lib.client.theme.WidgetPlacement;
import net.minecraft.network.chat.Component;
import org.joml.Vector2i;

public abstract class MenuItem extends UIWidget implements Stylized {
    private final ContextMenu menu;
    private int minWidth = 0;
    private int maxWidth = Integer.MAX_VALUE;

    public MenuItem(int height, ContextMenu menu, Component message) {
        super(message);
        this.height = height;
        this.menu = menu;
    }

    @Override
    public int getTextColor() {
        if (this.isUsingCustomTextColor()) {
            return this.getTextColor();
        }
        return this.getStyle().getTextColor().getRgb();
    }

    @Override
    public void revalidate() {
        this.setPosition((Vector2i) this.positionGetter.get());
        this.setWidth(((Vector2i) this.sizeGetter.get()).x);
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
        menu.invalidateSize();
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public ContextMenu getMenu() {
        return menu;
    }

    @Override
    public WidgetPlacement getPlacement() {
        return WidgetPlacement.MENU;
    }
}
