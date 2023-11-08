package com.ultreon.mods.lib.util.holders;

import dev.architectury.fluid.FluidStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

@FunctionalInterface
public interface FluidHolder extends RegistryEntryHolder {
    Fluid getFluid();

    //Note: Uses FluidStack in case we want to check NBT or something
    default boolean fluidMatches(FluidStack other) {
        return getFluid() == other.getFluid();
    }

    default FluidStack getFluidStack(int size) {
        return FluidStack.create(this::getFluid, size);
    }

    @Override
    default ResourceLocation getRegistryName() {
        return getFluid().arch$registryName();
    }


}