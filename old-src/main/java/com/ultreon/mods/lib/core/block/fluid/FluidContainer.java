package com.ultreon.mods.lib.core.block.fluid;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * An item that contains a fluid
 */
public interface FluidContainer {
    /**
     * Convenience method to get the fluid of either a bucket or {@code IFluidContainer}.
     *
     * @param stack The bucket or fluid container item
     * @return The stored fluid, or {@link FluidStack#EMPTY} if the container is empty or not a
     * valid fluid container
     */
    static FluidStack getBucketOrContainerFluid(ItemStack stack) {
        Item item = stack.getItem();
        //noinspection ChainOfInstanceofChecks
        if (item instanceof BucketItem) {
            Fluid fluid = ((BucketItem) item).getFluid();
            return new FluidStack(fluid, 1000);
        }
        if (item instanceof FluidContainer) {
            return ((FluidContainer) item).getFluid(stack);
        }
        return FluidStack.EMPTY;
    }

    /**
     * Convenience method to fill either a bucekt or {@code IFluidContainer} with a fluid.
     *
     * @param empty The empty fluid container
     * @param fluid The fluid to store
     * @return A filled fluid container, or {@link ItemStack#EMPTY} if the container is not a valid
     * fluid container
     */
    static ItemStack fillBucketOrFluidContainer(ItemStack empty, FluidStack fluid) {
        Item item = empty.getItem();
        //noinspection ChainOfInstanceofChecks
        if (item instanceof BucketItem) {
            return new ItemStack(fluid.getFluid().getBucket());
        }
        if (item instanceof FluidContainer) {
            return ((FluidContainer) item).fillWithFluid(empty, fluid);
        }
        return ItemStack.EMPTY;
    }

    /**
     * Gets the fluid stored in the item
     *
     * @param stack The fluid container
     * @return The stored fluid
     */
    FluidStack getFluid(ItemStack stack);

    /**
     * Creates a fluid container filled with the given fluid. Should not modify the empty
     * container.
     *
     * @param empty The empty fluid container
     * @param fluid The fluid to store
     * @return A new filled container
     */
    ItemStack fillWithFluid(ItemStack empty, FluidStack fluid);
}
