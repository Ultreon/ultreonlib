package com.ultreon.mods.lib.core.util;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.ultreon.mods.lib.core.util.helpers.UtilityClass;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

/**
 * Chat utilities.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
public final class ChatUtils extends UtilityClass {
    public static void broadcastMessage(@NotNull Level level, @NotNull String message) {
        broadcastMessage(level, new TextComponent(message));
    }

    public static void broadcastMessage(@NotNull Level server, @NotNull Component message) {
        for (Player player : server.players()) {
            player.sendMessage(message, player.getUUID());
        }
    }

    public static void broadcastMessage(@NotNull MinecraftServer server, @NotNull String message) {
        broadcastMessage(server, new TextComponent(message));
    }

    public static void broadcastMessage(@NotNull MinecraftServer server, @NotNull Component message) {
        for (Player player : server.getPlayerList().getPlayers()) {
            player.sendMessage(message, player.getUUID());
        }
    }

    public static boolean broadcastMessage(@NotNull EntitySelector server, @NotNull String message) {
        return broadcastMessage(server, new TextComponent(message));
    }

    public static boolean broadcastMessage(@NotNull EntitySelector server, @NotNull Component message) {
        try {
            for (Player player : server.findPlayers(ServerLifecycleHooks.getCurrentServer().createCommandSourceStack())) {
                player.sendMessage(message, player.getUUID());
            }
        } catch (CommandSyntaxException e) {
            return false;
        }
        return true;
    }
}
