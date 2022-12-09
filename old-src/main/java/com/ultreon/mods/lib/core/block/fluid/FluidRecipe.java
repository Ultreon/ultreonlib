package com.ultreon.mods.lib.core.block.fluid;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.ultreon.mods.lib.core.ModdingLibrary;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * An {@link Recipe} which can use fluids as ingredients or results. Can also handle items if
 * needed. Also see {@link FluidInventory} and {@link FluidIngredient}.
 * <p>
 * All the fluid-related input/output methods return a list. If these do not apply to the recipe
 * type, you should return an empty list.
 *
 * @param <C> The inventory type
 */
public interface FluidRecipe<C extends FluidInventory> extends Recipe<C> {
    /**
     * Deserialize a {@link FluidStack} from JSON, including a fluid amount.
     *
     * @param json The JSON object
     * @return A FluidStack
     * @throws JsonSyntaxException If the object cannot be parsed or the fluid does not exist.
     */
    static FluidStack deserializeFluid(JsonObject json) {
        ResourceLocation fluidId = new ResourceLocation(GsonHelper.getAsString(json, "fluid"));
        int amount = GsonHelper.getAsInt(json, "amount", 1000);
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
        if (fluid == null) {
            throw new JsonSyntaxException("Unknown fluid: " + fluidId);
        }
        return new FluidStack(fluid, amount);
    }

    /**
     * Reads a {@link FluidStack} from a packet buffer. Use with {@link #writeFluid(FriendlyByteBuf, FluidStack)}.
     *
     * @param buffer The packet buffer
     * @return A new FluidStack
     */
    static FluidStack readFluid(FriendlyByteBuf buffer) {
        ResourceLocation fluidId = buffer.readResourceLocation();
        int amount = buffer.readVarInt();
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(fluidId);
        if (fluid != null) {
            return new FluidStack(fluid, amount);
        } else {
            ModdingLibrary.LOGGER.error("Unknown fluid: {}", fluidId);
            return FluidStack.EMPTY;
        }
    }

    /**
     * Writes a {@link FluidStack} to a packet buffer. Use with {@link #readFluid(FriendlyByteBuf)}.
     *
     * @param buffer The packet buffer
     * @param stack  A new FluidStack
     */
    static void writeFluid(FriendlyByteBuf buffer, FluidStack stack) {
        buffer.writeResourceLocation(Objects.requireNonNull(stack.getFluid().getRegistryName()));
        buffer.writeVarInt(stack.getAmount());
    }

    /**
     * Get the fluids which result from this recipe. Similar to {@link #getFluidOutputs()}.
     *
     * @param inv The inventory
     * @return A list of fluids produced
     */
    List<FluidStack> getFluidResults(C inv);

    /**
     * Get the fluids which result from this recipe, ignoring the inventory. Similar to {@link #getFluidResults(FluidInventory)}, needed for JEI support.
     *
     * @return A list of fluids produced
     */
    List<FluidStack> getFluidOutputs();

    /**
     * Get the fluid ingredients. Similar to {@link #getIngredients()}.
     *
     * @return A list of fluid ingredients which represent the inputs.
     */
    List<FluidIngredient> getFluidIngredients();

    @Override
    default @NotNull ItemStack assemble(C inv) {
        return getResultItem();
    }

    @Override
    default @NotNull ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    default boolean canCraftInDimensions(int width, int height) {
        return true;
    }
}
