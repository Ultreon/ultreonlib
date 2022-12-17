package com.ultreon.mods.lib.util;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ModMessages {
    @NotNull
    private static final List<Component> MESSAGES = new ArrayList<>();

    public static void addMessage(@NotNull Component component) {
        MESSAGES.add(component);
    }

    @ApiStatus.Internal
    public static void sendOnLogin(@Nullable ServerPlayer player) {
        if (player == null) return;

        for (Component message : MESSAGES) {
            player.sendSystemMessage(message);
        }
    }
}
