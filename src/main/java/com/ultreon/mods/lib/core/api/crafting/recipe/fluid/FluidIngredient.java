package com.ultreon.mods.lib.core.api.crafting.recipe.fluid;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.ultreon.mods.lib.core.ModdingLibrary;
import com.ultreon.mods.lib.core.silentlib.util.NameUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * An {@code Ingredient}-equivalent for fluids. Can match fluids or fluid tags. Can also consider
 * fluid amount.
 */

/**
 * @deprecated Removed
 */
@Deprecated
public class FluidIngredient implements Predicate<FluidStack> {
    public static final FluidIngredient EMPTY = new FluidIngredient();

    @Nullable
    private final TagKey<Fluid> tag;
    @Nullable
    private final Fluid fluid;
    private final int amount;

    public FluidIngredient(@Nonnull TagKey<Fluid> tag) {
        this(tag, 1000);
    }

    public FluidIngredient(@Nonnull TagKey<Fluid> tag, int amount) {
        this.tag = tag;
        this.fluid = null;
        this.amount = amount;
    }

    public FluidIngredient(@Nonnull Fluid fluid) {
        this(fluid, 1000);
    }

    public FluidIngredient(@Nonnull Fluid fluid, int amount) {
        this.fluid = fluid;
        this.tag = null;
        this.amount = amount;
    }

    private FluidIngredient() {
        this.tag = null;
        this.fluid = null;
        this.amount = 1000;
    }

    /**
     * Deserialize a {@link FluidIngredient} from JSON.
     *
     * @param json The JSON object
     * @return A new FluidIngredient
     * @throws JsonSyntaxException If the JSON cannot be parsed
     */
    public static FluidIngredient deserialize(JsonObject json) {
        if (json.has("tag") && json.has("fluid")) {
            throw new JsonSyntaxException("Fluid ingredient should have 'tag' or 'fluid', not both");
        }

        int amount = GsonHelper.getAsInt(json, "amount", 1000);

        if (json.has("tag")) {
            ResourceLocation id = new ResourceLocation(GsonHelper.getAsString(json, "tag"));
            return new FluidIngredient(FluidTags.create(id), amount);
        }
        if (json.has("fluid")) {
            ResourceLocation id = new ResourceLocation(GsonHelper.getAsString(json, "fluid"));
            Fluid fluid = Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(id));
            return new FluidIngredient(fluid, amount);
        }
        throw new JsonSyntaxException("Fluid ingredient should have either 'tag' or 'fluid'");
    }

    /**
     * Reads a {@link FluidIngredient} from a packet buffer. Use with {@link #write(FriendlyByteBuf)}.
     *
     * @param buffer The packet buffer
     * @return A new FluidIngredient
     */
    public static FluidIngredient read(FriendlyByteBuf buffer) {
        boolean isTag = buffer.readBoolean();
        ResourceLocation id = buffer.readResourceLocation();
        int amount = buffer.readVarInt();
        return isTag
                ? new FluidIngredient(FluidTags.create(id), amount)
                : new FluidIngredient(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(id)), amount);
    }

    @Nullable
    public TagKey<Fluid> getTag() {
        return tag;
    }

    /**
     * Get a list of all {@link FluidStack}s which match this ingredient. Used for JEI support.
     *
     * @return A list of matching fluids
     */
    public List<FluidStack> getFluids() {
        if (tag != null) {
            return getFluidsFromTagHack();
        }
        if (fluid != null) {
            return Collections.singletonList(new FluidStack(fluid, this.amount));
        }
        return Collections.emptyList();
    }

    private List<FluidStack> getFluidsFromTagHack() {
        // TODO: Should change this once I figure out what exactly is going on here...
        @NotNull ITag<Fluid> tags;
        try {
            assert this.tag != null;

            tags = Objects.requireNonNull(ForgeRegistries.FLUIDS.tags()).getTag(this.tag);
        } catch (IllegalStateException ex) {
            ModdingLibrary.LOGGER.warn("Fluid tags not bound when needed! Trying to fetch tags...");
            return List.of();
        }

        return tags.stream().map(f -> new FluidStack(f, this.amount)).collect(Collectors.toList());
    }

    public int getAmount() {
        return amount;
    }

    /**
     * Test for a match. Also considers the fluid amount, use {@link #testIgnoreAmount(FluidStack)}
     * to ignore the amount.
     *
     * @param stack The fluid
     * @return True if the fluid matches the ingredient and has the same amount of fluid or more
     */
    @Override
    public boolean test(FluidStack stack) {
        return stack.getAmount() >= amount && testIgnoreAmount(stack);
    }

    /**
     * Test for a match without considering the amount of fluid in the stack
     *
     * @param stack The fluid
     * @return True if the fluid matches the ingredient, ignoring amount
     */
    public boolean testIgnoreAmount(FluidStack stack) {
        return (tag != null && stack.getFluid().is(tag)) || (fluid != null && stack.getFluid() == fluid);
    }

    public JsonObject serialize() {
        JsonObject json = new JsonObject();

        if (this.tag != null) {
            json.addProperty("tag", this.tag.location().toString());
        } else if (this.fluid != null) {
            json.addProperty("fluid", NameUtils.from(this.fluid).toString());
        } else {
            throw new IllegalStateException("Fluid ingredient is missing both tag and fluid");
        }

        json.addProperty("amount", this.amount);

        return json;
    }

    /**
     * Writes the ingredient to a packet buffer. Use with {@link #read(FriendlyByteBuf)}.
     *
     * @param buffer The packet buffer
     */
    public void write(FriendlyByteBuf buffer) {
        boolean isTag = tag != null;
        buffer.writeBoolean(isTag);
        if (isTag)
            buffer.writeResourceLocation(tag.location());
        else if (fluid != null)
            buffer.writeResourceLocation(Objects.requireNonNull(fluid.getRegistryName()));
        else
            buffer.writeResourceLocation(new ResourceLocation("null"));
        buffer.writeVarInt(this.amount);
    }
}
