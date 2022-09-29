package com.ultreon.mods.lib.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ExtendedBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, Tickable, HasPipeline {
    protected CompoundTag pipeline = new CompoundTag();

    protected ExtendedBlockEntity(BlockEntityType<?> p_155076_, BlockPos p_155077_, BlockState p_155078_) {
        super(p_155076_, p_155077_, p_155078_);
    }

    @Override
    public final void tick() {
        this.execute();

        if (!pipeline.isEmpty()) {
            sendUpdate();
        }
    }

    protected abstract void execute();

    @Override
    public final void receiveUpdate(CompoundTag tag) {
        handlePipeline(tag);
    }

    protected abstract void handlePipeline(CompoundTag tag);

    @Override
    public final CompoundTag serializeForTransfer() {
        return pipeline;
    }

    @Override
    public final void sendUpdate() {
        HasPipeline.super.sendUpdate();
    }
}
