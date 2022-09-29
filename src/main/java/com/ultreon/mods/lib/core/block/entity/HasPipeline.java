package com.ultreon.mods.lib.core.block.entity;

import com.ultreon.mods.lib.core.network.ModdingLibraryNet;
import com.ultreon.mods.lib.core.network.PipelinePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface HasPipeline {
    void receiveUpdate(CompoundTag tag);

    CompoundTag serializeForTransfer();

    default void sendUpdate() {
        CompoundTag tags = serializeForTransfer();
        if (this instanceof BlockEntity blockEntity) {
            Level level = blockEntity.getLevel();
            if (level != null) {
                BlockPos blockPos = blockEntity.getBlockPos();
                ModdingLibraryNet.get().sendToLevel(new PipelinePacket(blockPos, tags), level);
            }
        }
    }
}
