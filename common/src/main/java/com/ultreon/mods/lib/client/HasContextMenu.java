package com.ultreon.mods.lib.client;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.ultreon.mods.lib.client.gui.widget.ContextMenu;
import org.jetbrains.annotations.Nullable;

public interface HasContextMenu {
    @Nullable
    @CanIgnoreReturnValue
    ContextMenu contextMenu(int mouseX, int mouseY, int button);
}
