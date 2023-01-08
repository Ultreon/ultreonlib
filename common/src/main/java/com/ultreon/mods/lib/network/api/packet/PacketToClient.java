package com.ultreon.mods.lib.network.api.packet;

import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;

import java.util.function.Supplier;

public abstract non-sealed class PacketToClient<T extends PacketToClient<T>> extends BasePacket<T> implements ClientEndpoint {
    public PacketToClient() {
        super();
    }

    @Override
    public final boolean handle(Supplier<NetworkManager.PacketContext> context) {
        NetworkManager.PacketContext ctx = context.get();
        if (ctx.getEnvironment() == Env.CLIENT)
            ctx.queue(this::handle);
        return true;
    }

    protected abstract void handle();
}
