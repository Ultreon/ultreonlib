package dev.ultreon.mods.lib.actionmenu;

import net.minecraft.network.chat.Component;

@Deprecated(forRemoval = true)
public interface IMenuHandler {
    Submenu getMenu();

    default Component getText() {
        return Component.literal("...");
    }

    boolean isEnabled();
}
