package com.ultreon.mods.lib.core.silentlib.network.internal;

import com.ultreon.mods.lib.core.silentlib.client.gui.nbt.DisplayNBTScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @deprecated Removed
 */
@Deprecated
public class DisplayNBTPacket {
    private CompoundTag nbt;
    private Component title;

    public DisplayNBTPacket() {
    }

    public DisplayNBTPacket(CompoundTag nbt, Component title) {
        this.nbt = nbt;
        this.title = title;
    }

    public static DisplayNBTPacket fromBytes(FriendlyByteBuf buffer) {
        DisplayNBTPacket packet = new DisplayNBTPacket();
        packet.nbt = buffer.readNbt();
        packet.title = buffer.readComponent();
        return packet;
    }

    public static void handle(DisplayNBTPacket packet, Supplier<NetworkEvent.Context> context) {
        ClientWrapper.handle(packet);
        context.get().setPacketHandled(true);
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeNbt(this.nbt);
        buffer.writeComponent(this.title);
    }

    /**
     * @deprecated Removed
     */
    @Deprecated
    private static class ClientWrapper {
        private static void handle(DisplayNBTPacket packet) {
            LocalPlayer player = Minecraft.getInstance().player;

            if (player != null) {
                DisplayNBTScreen screen = new DisplayNBTScreen(packet.nbt, packet.title);
                Minecraft.getInstance().setScreen(screen);
            }
        }
    }
}
