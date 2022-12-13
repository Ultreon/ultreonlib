package com.ultreon.mods.lib.actionmenu;

import net.minecraft.network.chat.Component;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.NotNull;

public interface IActionMenuItem {
    @NotNull
    default Component getText() {
        return Component.literal("...");
    }

    @Environment(EnvType.CLIENT)
    default boolean isEnabled() {
        return true;
    }

    void onActivate();

    int id();

    int serverId();

    @NotNull
    String path();
}
