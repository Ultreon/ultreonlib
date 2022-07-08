package com.ultreon.modlib.block.entity;

import com.ultreon.modlib.silentlib.util.MCMathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

@SuppressWarnings("unused")
public abstract class InventoryBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, Tickable {
    private final LazyOptional<? extends IItemHandler>[] handlers =
            SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    public final int size;
    protected int timer;
    protected boolean requiresUpdate;

    public final UmlItemStackHandler inventory;
    protected LazyOptional<ItemStackHandler> handler;

    protected InventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state);
        if (size <= 0) {
            size = 1;
        }

        this.size = size;
        this.inventory = createInventory();
        this.handler = LazyOptional.of(() -> this.inventory);
    }

    public ItemStack insertItem(int slot, ItemStack stack) {
        final ItemStack copy = stack.copy();
        stack.shrink(copy.getCount());
        this.requiresUpdate = true;
        return this.handler.map(inv -> inv.insertItem(slot, copy, false)).orElse(ItemStack.EMPTY);
    }

    public ItemStack extractItem(int slot) {
        final int count = getItemInSlot(slot).getCount();
        this.requiresUpdate = true;
        return this.handler.map(inv -> inv.extractItem(slot, count, false)).orElse(ItemStack.EMPTY);
    }

    public ItemStack getItemInSlot(int slot) {
        return this.handler.map(inv -> inv.getStackInSlot(slot)).orElse(ItemStack.EMPTY);
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        if (index < 0 || index >= inventory.getSlots()) {
            return ItemStack.EMPTY;
        }
        return inventory.getStackInSlot(index);
    }

    @Override
    public @NotNull ItemStack removeItem(int index, int count) {
        return inventory.removeItem(index, count);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int index) {
        return inventory.removeItemNoUpdate(index);
    }

    @Override
    public void setItem(int index, @NotNull ItemStack stack) {
        inventory.setStackInSlot(index, stack);
    }

    @Override
    public int getContainerSize() {
        return inventory.getSlots();
    }

    public LazyOptional<ItemStackHandler> getHandler() {
        return this.handler;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return serializeNBT();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        load(tag);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.handler.invalidate();
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.inventory.deserializeNBT(tag.getCompound("Inventory"));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(Objects.requireNonNull(pkt.getTag()));
    }

    public void tick() {
        this.timer++;
        if (this.requiresUpdate && this.level != null) {
            update();
            this.requiresUpdate = false;
        }
    }

    public void update() {
        requestModelDataUpdate();
        setChanged();
        if (this.level != null) {
            this.level.setBlockAndUpdate(this.worldPosition, getBlockState());
        }
    }
    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", this.inventory.serializeNBT());
    }

    private UmlItemStackHandler createInventory() {
        return new UmlItemStackHandler(this.size) {
            @Override
            public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
                InventoryBlockEntity.this.update();
                return super.extractItem(slot, amount, simulate);
            }

            @Override
            public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                InventoryBlockEntity.this.update();
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.inventory.getStacks()) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return level != null && level.getBlockEntity(worldPosition) == this && MCMathUtils.distanceSq(player, worldPosition) <= 64;
    }

    @Override
    public void clearContent() {
        this.inventory.clear();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.UP)
                return handlers[0].cast();
            if (side == Direction.DOWN)
                return handlers[1].cast();
            return handlers[2].cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : handlers) {
            handler.invalidate();
        }
    }


}
