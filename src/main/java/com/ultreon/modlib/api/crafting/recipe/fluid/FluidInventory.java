package com.ultreon.modlib.api.crafting.recipe.fluid;

import net.minecraft.world.Container;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * Just extends {@link Container} and {@link IFluidHandler}, to make {@link FluidRecipe}
 * implementation a bit cleaner.
 */
public interface FluidInventory extends Container, IFluidHandler {
}
