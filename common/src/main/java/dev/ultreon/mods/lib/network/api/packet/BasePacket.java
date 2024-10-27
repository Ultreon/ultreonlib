package dev.ultreon.mods.lib.network.api.packet;

import dev.architectury.networking.NetworkManager;
import dev.ultreon.mods.lib.network.api.NetworkSystem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract sealed class BasePacket<T extends BasePacket<T>> implements CustomPacketPayload permits BiDirectionalPacket, PacketToClient, PacketToServer {
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

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return NetworkSystem.locatePacket(this.getClass());
    }

    public abstract void toBytes(FriendlyByteBuf buffer);
}
