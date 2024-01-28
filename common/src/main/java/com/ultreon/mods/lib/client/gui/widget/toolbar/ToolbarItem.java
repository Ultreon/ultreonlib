package com.ultreon.mods.lib.client.gui.widget.toolbar;

import com.ultreon.mods.lib.client.HasContextMenu;
import com.ultreon.mods.lib.client.gui.widget.UIWidget;
import com.ultreon.mods.lib.client.gui.widget.menu.ContextMenu;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

public abstract class ToolbarItem extends UIWidget implements IToolbarItem, HasContextMenu {
    public ToolbarItem(Component message) {
        super(message);

        this.sizeGetter = () -> new Vector2i(16, 16);
    }

    @Override
    public int width() {
        return getWidth();
    }

    @Override
    public int height() {
        return getHeight();
    }

    @Override
    public void revalidate() {
        this.setPosition((Vector2i) this.positionGetter.get());
        this.setWidth(((Vector2i) this.sizeGetter.get()).x);
    }

    @Override
    public @Nullable ContextMenu contextMenu(int mouseX, int mouseY, int button) {
        return null;
    }
}
