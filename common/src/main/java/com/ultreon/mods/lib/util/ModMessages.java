package com.ultreon.mods.lib.util;

import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Send messages to the player when they log in.
 */
public final class ModMessages {
    @NotNull
    private static final List<Component> MESSAGES = new ArrayList<>();

    static {
        // Register events for when a player logs in
        PlayerEvent.PLAYER_JOIN.register(ModMessages::sendOnLogin);
    }

    private ModMessages() {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds a message to show in chat when a player logs in
     *
     * @param component the message to show in chat
     */
    public static void addMessage(@NotNull Component component) {
        MESSAGES.add(component);
    }

    /**
     * Adds a message with the given prefix to show in chat when a player logs in
     *
     * @param prefix the prefix for the message, e.g. the mod's name.
     * @param component the message to show in chat
     */
    public static void addMessage(@NotNull String prefix, @NotNull Component component) {
        MESSAGES.add(Component.literal(ChatFormatting.YELLOW + "[").append(ChatFormatting.AQUA + prefix).append(ChatFormatting.YELLOW + "] ").append(component));
    }

    private static void sendOnLogin(@Nullable ServerPlayer player) {
        // In some cases the player may be null
        if (player == null) return;

        // Send all messages to the player
        MESSAGES.forEach(player::sendSystemMessage);
    }

    @ApiStatus.Internal
    public static void init() {
        // No-op
    }
}
