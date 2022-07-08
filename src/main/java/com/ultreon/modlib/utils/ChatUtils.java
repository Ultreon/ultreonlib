package com.ultreon.modlib.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Chat utilities.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
public final class ChatUtils {
    private ChatUtils() {
        throw ExceptionUtil.utilityConstructor();
    }

    public static void broadcastMessage(@NotNull Level worldIn, @NotNull Component msg) {
        if (worldIn.getServer() != null) {
            for (Player player : worldIn.getServer().getPlayerList().getPlayers()) {
                player.sendMessage(msg, player.getUUID());
            }
            return;
        }
        for (Player player : worldIn.players()) {
            player.sendMessage(msg, player.getUUID());
        }
    }
}
