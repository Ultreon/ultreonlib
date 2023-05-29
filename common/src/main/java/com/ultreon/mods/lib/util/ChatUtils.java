package com.ultreon.mods.lib.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Chat utilities.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
public final class ChatUtils extends UtilityClass {
    public static void broadcastMessage(@NotNull Level level, @NotNull String message) {
        broadcastMessage(level, Component.literal(message));
    }

    public static void broadcastMessage(@NotNull Level server, @NotNull Component message) {
        for (Player player : server.players()) {
            player.sendSystemMessage(message);
        }
    }

    public static void broadcastMessage(@NotNull MinecraftServer server, @NotNull String message) {
        broadcastMessage(server, Component.literal(message));
    }

    public static void broadcastMessage(@NotNull MinecraftServer server, @NotNull Component message) {
        for (Player player : server.getPlayerList().getPlayers()) {
            player.sendSystemMessage(message);
        }
    }

    public static boolean broadcastMessage(@NotNull EntitySelector server, @NotNull String message) {
        return broadcastMessage(server, Component.literal(message));
    }

    public static boolean broadcastMessage(@NotNull EntitySelector server, @NotNull Component message) {
        try {
            for (Player player : server.findPlayers(ServerLifecycle.getCurrentServer().createCommandSourceStack())) {
                player.sendSystemMessage(message);
            }
        } catch (CommandSyntaxException e) {
            return false;
        }
        return true;
    }
}
