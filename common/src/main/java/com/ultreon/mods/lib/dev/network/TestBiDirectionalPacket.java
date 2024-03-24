package com.ultreon.mods.lib.dev.network;

import com.ultreon.mods.lib.network.api.packet.BiDirectionalPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class TestBiDirectionalPacket extends BiDirectionalPacket<TestBiDirectionalPacket> {
    private final UUID uuid;

    public TestBiDirectionalPacket(UUID uuid) {
        this.uuid = uuid;
    }

    public TestBiDirectionalPacket(FriendlyByteBuf buffer) {
        this.uuid = buffer.readUUID();
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUUID(uuid);
    }

    @Override
    protected void handleClient() {
        DevNetwork.get().sendToServer(new TestBiDirectionalPacket(uuid));
    }

    @Override
    protected void handleServer(ServerPlayer sender) {
        sender.displayClientMessage(Component.literal("Sent uuid: " + uuid), true);
    }
}
