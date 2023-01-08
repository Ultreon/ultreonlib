package com.ultreon.mods.lib.network.api.packet;

import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public abstract non-sealed class PacketToServer<T extends PacketToServer<T>> extends BasePacket<T> implements ServerEndpoint {
    @Override
    public final boolean handle(Supplier<NetworkManager.PacketContext> context) {
        NetworkManager.PacketContext ctx = context.get();
        if (ctx.getEnvironment() == Env.SERVER)
            ctx.queue(() -> handle((ServerPlayer)Objects.requireNonNull(ctx.getPlayer(), "Server player was not found while on server side")));
        return true;
    }

    protected abstract void handle(@NotNull ServerPlayer sender);
}
