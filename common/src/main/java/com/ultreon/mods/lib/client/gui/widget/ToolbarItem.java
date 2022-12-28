package com.ultreon.mods.lib.client.gui.widget;

import com.ultreon.mods.lib.client.HasContextMenu;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public abstract class ToolbarItem extends BaseWidget implements IToolbarItem, HasContextMenu {
    public ToolbarItem(int x, int y, int width, Component message) {
        super(x, y, width, 16, message);
    }

    public ToolbarItem(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Override
    public int width() {
        return getWidth();
    }

    @Override
    public int height() {
        return getHeight();
    }

    @Nullable
    @Override
    public ContextMenu contextMenu(int mouseX, int mouseY, int button) {
        return null;
    }
}
