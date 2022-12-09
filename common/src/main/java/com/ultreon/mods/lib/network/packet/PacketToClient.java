package com.ultreon.mods.lib.network.packet;

import com.ultreon.mods.lib.network.NetworkContext;
import com.ultreon.mods.lib.network.NetworkDirection;
import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import net.fabricmc.api.EnvType;
import net.minecraft.network.Connection;

import java.util.function.Supplier;

public abstract non-sealed class PacketToClient<T extends PacketToClient<T>> extends BasePacket<T> {
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
