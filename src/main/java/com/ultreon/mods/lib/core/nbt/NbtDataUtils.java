package com.ultreon.mods.lib.core.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * NBT-helper.
 *
 * @author Qboi123
 */
public class NbtDataUtils {
    /**
     * @deprecated Use the write/save/serialize method of the object itself.
     */
    @Deprecated
    public static CompoundTag toNBT(Object o) {
        if (o instanceof ItemStack stack) {
            return stack.save(new CompoundTag());
        }

        return null;
    }

    /**
     * @deprecated Use {@link ItemStack#deserializeNBT(CompoundTag)} instead.
     */
    @SuppressWarnings("ConstantConditions")
    @Deprecated
    private static CompoundTag writeItemStack(ItemStack i) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("count", i.getCount());
        nbt.putString("item", i.getItem().getRegistryName().toString());
        nbt.putByte("type", (byte) 0);
        return nbt;
    }

    /**
     * @deprecated Use the read/load/deserialize method of the object / class itself.
     */
    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Deprecated
    @Nullable
    public static Object fromNBT(@Nonnull CompoundTag compound) {
        return switch (compound.getByte("type")) {
            case 0 -> readItemStack(compound);
            default -> null;
        };
    }

    /**
     * @deprecated Use {@link ItemStack#deserializeNBT(CompoundTag)} instead.
     */
    @Deprecated
    private static ItemStack readItemStack(CompoundTag compound) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(compound.getString("item")));
        int count = compound.getInt("count");
        return new ItemStack(item, count);
    }
}
