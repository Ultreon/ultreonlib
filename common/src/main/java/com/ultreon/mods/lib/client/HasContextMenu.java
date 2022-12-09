package com.ultreon.mods.lib.client;

import com.ultreon.mods.lib.client.gui.widget.ContextMenu;
import org.jetbrains.annotations.Nullable;

public interface HasContextMenu {
    @Nullable ContextMenu contextMenu(int mouseX, int mouseY, int button);
}
