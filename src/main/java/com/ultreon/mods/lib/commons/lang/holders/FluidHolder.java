package com.ultreon.mods.lib.commons.lang.holders;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface FluidHolder extends BaseHolder {

    @Nonnull
    Fluid getFluid();

    //Note: Uses FluidStack in case we want to check NBT or something
    default boolean fluidMatches(FluidStack other) {
        return getFluid() == other.getFluid();
    }

    @Nonnull
    default FluidStack getFluidStack(int size) {
        return new FluidStack(getFluid(), size);
    }

    @Override
    default ResourceLocation getRegistryName() {
        return getFluid().getRegistryName();
    }

    @Override
    default Component getTranslation() {
        return getFluid().getAttributes().getDisplayName(getFluidStack(1));
    }

    @Override
    default String getTranslationId() {
        return getFluid().getAttributes().getTranslationKey();
    }
}