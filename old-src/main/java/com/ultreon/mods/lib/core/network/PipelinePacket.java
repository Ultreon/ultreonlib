package com.ultreon.mods.lib.core.network;

import com.ultreon.mods.lib.core.block.entity.HasPipeline;
import com.ultreon.mods.lib.networking.network.PacketToClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PipelinePacket extends PacketToClient<PipelinePacket> {
    private final BlockPos blockPos;
    private final CompoundTag tags;

    public PipelinePacket(FriendlyByteBuf buf) {
        blockPos = buf.readBlockPos();
        tags = buf.readNbt();
    }

    public PipelinePacket(BlockPos pos, CompoundTag tags) {
        this.blockPos = pos;
        this.tags = tags;
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(blockPos);
        buffer.writeNbt(tags);
    }

    @Override
    protected void handle(Connection connection) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof HasPipeline dataTransfer) {
            dataTransfer.receiveUpdate(tags);
        }
    }
}
