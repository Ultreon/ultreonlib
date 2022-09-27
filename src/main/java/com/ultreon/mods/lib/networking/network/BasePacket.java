package com.ultreon.mods.lib.networking.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public abstract sealed class BasePacket<T extends BasePacket<T>> permits BiDirectionalPacket, PacketToClient, PacketToServer {
    protected abstract boolean handle(Supplier<NetworkEvent.Context> context);

    public final boolean handlePacket(Supplier<NetworkEvent.Context> context) {
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
