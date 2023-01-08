package com.ultreon.mods.lib.network.api.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Supplier;

public abstract non-sealed class BiDirectionalPacket<T extends BiDirectionalPacket<T>> extends BasePacket<T> implements ClientEndpoint, ServerEndpoint {
    public BiDirectionalPacket() {
        super();
    }

    @Override
    public final boolean handle(Supplier<NetworkManager.PacketContext> context) {
        NetworkManager.PacketContext ctx = context.get();
        switch (ctx.getEnvironment()) {
            case CLIENT -> ctx.queue(this::handleClient);
            case SERVER -> ctx.queue(() -> handleServer((ServerPlayer)ctx.getPlayer()));
            default -> {
            }
        }
        return true;
    }

    protected abstract void handleClient();

    protected abstract void handleServer(ServerPlayer sender);
}
