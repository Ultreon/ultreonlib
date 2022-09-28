package com.ultreon.mods.lib.gui.client;

import com.ultreon.mods.lib.gui.client.gui.widget.ContextMenu;
import org.jetbrains.annotations.Nullable;

public interface HasContextMenu {
    @Nullable ContextMenu contextMenu(int mouseX, int mouseY, int button);
}
