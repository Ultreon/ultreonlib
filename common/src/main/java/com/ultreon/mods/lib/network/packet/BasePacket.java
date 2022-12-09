package com.ultreon.mods.lib.network.packet;

import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public abstract sealed class BasePacket<T extends BasePacket<T>> permits BiDirectionalPacket, PacketToClient, PacketToServer {
    protected abstract boolean handle(Supplier<NetworkManager.PacketContext> context);

    public final boolean handlePacket(Supplier<NetworkManager.PacketContext> context) {
        try {
            handle(context);
        } catch (Throwable t) {
            System.err.println("Couldn't handle packet.");
            t.printStackTrace();
        }
        return true;
    }

    public abstract void toBytes(FriendlyByteBuf buffer);
}
