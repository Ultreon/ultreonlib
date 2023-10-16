package com.ultreon.mods.lib.client.gui.widget.menu;

import com.ultreon.mods.lib.client.theme.ThemeComponent;
import com.ultreon.mods.lib.client.theme.ThemeRootComponent;
import com.ultreon.mods.lib.client.theme.Stylized;
import com.ultreon.mods.lib.client.gui.widget.BaseWidget;
import net.minecraft.network.chat.Component;

public abstract class MenuItem extends BaseWidget implements Stylized {
    private final ContextMenu menu;
    private int minWidth = 0;
    private int maxWidth = Integer.MAX_VALUE;

    public MenuItem(int height, ContextMenu menu, Component message) {
        super(menu.getX(), menu.getY(), menu.getWidth(), height, message);
        this.menu = menu;
    }

    @Override
    public int getTextColor() {
        if (this.isUsingCustomTextColor()) {
            return this.getTextColor();
        }
        return this.getStyle().getTextColor().getRgb();
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
    public ThemeComponent getThemeComponent() {
        return ThemeRootComponent.MENU;
    }
}
