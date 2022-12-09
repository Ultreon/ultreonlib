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
        CompoundTag pipeline = serializeForTransfer();
        if (this instanceof BlockEntity blockEntity) {
            Level level = blockEntity.getLevel();
            if (level != null) {
                BlockPos pos = blockEntity.getBlockPos();
                ModdingLibraryNet.get().sendToLevel(new PipelinePacket(pos, pipeline), level);
            }
        }
    }
}
