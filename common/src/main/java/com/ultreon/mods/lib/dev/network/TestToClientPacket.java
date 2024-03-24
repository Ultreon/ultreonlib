package com.ultreon.mods.lib.dev.network;

import com.ultreon.mods.lib.network.api.packet.PacketToClient;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class TestToClientPacket extends PacketToClient<TestToClientPacket> {
    private final UUID uuid;

    public TestToClientPacket(UUID uuid) {
        this.uuid = uuid;
    }

    public TestToClientPacket(FriendlyByteBuf buffer) {
        this.uuid = buffer.readUUID();
    }

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUUID(uuid);
    }

    @Override
    protected void handle() {
        EnvExecutor.runInEnv(Env.CLIENT, () -> () -> Minecraft.getInstance().getToasts().addToast(new SystemToast(SystemToast.SystemToastId.PERIODIC_NOTIFICATION, Component.literal("DevTest System"), Component.literal("Received uuid: " + uuid))));
    }
}
