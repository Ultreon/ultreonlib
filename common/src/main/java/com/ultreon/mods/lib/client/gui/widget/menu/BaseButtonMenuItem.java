package com.ultreon.mods.lib.client.gui.widget.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public abstract class BaseButtonMenuItem extends MenuItem {
    public BaseButtonMenuItem(ContextMenu menu, Component message) {
        super(20, menu, message);

        setMinWidth(Minecraft.getInstance().font.width(message) + 8 + 4);
    }

    protected abstract void click();

    @Override
    public boolean onLeftClick(int clicks) {
        click();
        return true;
    }
}
