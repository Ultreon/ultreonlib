package dev.ultreon.mods.lib.client;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dev.ultreon.mods.lib.client.gui.widget.menu.ContextMenu;
import org.jetbrains.annotations.Nullable;

public interface HasContextMenu {
    @Nullable
    @CanIgnoreReturnValue
    ContextMenu contextMenu(int mouseX, int mouseY, int button);
}
