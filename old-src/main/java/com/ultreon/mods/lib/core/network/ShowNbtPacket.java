package com.ultreon.mods.lib.core.network;

import com.ultreon.mods.lib.core.client.gui.nbt.NbtViewerScreen;
import com.ultreon.mods.lib.networking.network.PacketToClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShowNbtPacket extends PacketToClient<ShowNbtPacket> {
    private final CompoundTag nbt;
    private final Component title;

    public ShowNbtPacket(FriendlyByteBuf buf) {
        nbt = buf.readNbt();
        title = buf.readComponent();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void handle(Connection connection) {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player != null) {
            NbtViewerScreen screen = new NbtViewerScreen(nbt, title);
            Minecraft.getInstance().setScreen(screen);
        }
    }

    public ShowNbtPacket(CompoundTag nbt, Component title) {
        this.nbt = nbt;
        this.title = title;
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeNbt(this.nbt);
        buffer.writeComponent(this.title);
    }
}
