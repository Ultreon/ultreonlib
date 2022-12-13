package com.ultreon.mods.lib.actionmenu;

import net.minecraft.network.chat.Component;

public interface IMenuHandler {
    Submenu getMenu();

    default Component getText() {
        return Component.literal("...");
    }

    boolean isEnabled();
}
