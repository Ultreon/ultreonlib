package com.ultreon.mods.lib.network.api;

import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public record NetworkContext(FriendlyByteBuf buf, NetworkDirection direction, Connection connection, @Nullable ServerPlayer sender) {
    public void enqueueWork(Runnable task) {

    }
}
