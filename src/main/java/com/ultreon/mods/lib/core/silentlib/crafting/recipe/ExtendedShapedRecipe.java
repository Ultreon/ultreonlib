package com.ultreon.mods.lib.core.silentlib.crafting.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Allows quick extensions of vanilla shaped crafting.
 */

/**
 * @deprecated Removed
 */
@Deprecated
public abstract class ExtendedShapedRecipe extends ShapedRecipe {
    private static final RecipeSerializer<ShapedRecipe> BASE_SERIALIZER = RecipeSerializer.SHAPED_RECIPE;

    private final ShapedRecipe recipe;

    public ExtendedShapedRecipe(ShapedRecipe recipe) {
        super(recipe.getId(), recipe.getGroup(), recipe.getRecipeWidth(), recipe.getRecipeHeight(), recipe.getIngredients(), recipe.getResultItem());
        this.recipe = recipe;
    }

    public ShapedRecipe getBaseRecipe() {
        return this.recipe;
    }

    @Override
    public abstract RecipeSerializer<?> getSerializer();

    @Override
    public abstract boolean matches(CraftingContainer inv, Level worldIn);

    @Override
    public abstract ItemStack assemble(CraftingContainer inv);

    /**
     * @deprecated Removed
     */
    @Deprecated
    public static class Serializer<T extends ExtendedShapedRecipe> extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
        private final Function<ShapedRecipe, T> recipeFactory;
        @Nullable
        private final BiConsumer<JsonObject, T> readJson;
        @Nullable
        private final BiConsumer<FriendlyByteBuf, T> readBuffer;
        @Nullable
        private final BiConsumer<FriendlyByteBuf, T> writeBuffer;

        public Serializer(Function<ShapedRecipe, T> recipeFactory,
                          @Nullable BiConsumer<JsonObject, T> readJson,
                          @Nullable BiConsumer<FriendlyByteBuf, T> readBuffer,
                          @Nullable BiConsumer<FriendlyByteBuf, T> writeBuffer) {
            this.recipeFactory = recipeFactory;
            this.readJson = readJson;
            this.readBuffer = readBuffer;
            this.writeBuffer = writeBuffer;
        }

        public static <S extends ExtendedShapedRecipe> Serializer<S> basic(Function<ShapedRecipe, S> recipeFactory) {
            return new Serializer<>(recipeFactory, null, null, null);
        }

        @Deprecated
        public static <S extends ExtendedShapedRecipe> Serializer<S> basic(ResourceLocation serializerId, Function<ShapedRecipe, S> recipeFactory) {
            return new Serializer<>(recipeFactory, null, null, null);
        }

        @Override
        public T fromJson(ResourceLocation recipeId, JsonObject json) {
            ShapedRecipe recipe = BASE_SERIALIZER.fromJson(recipeId, json);
            T result = this.recipeFactory.apply(recipe);
            if (this.readJson != null) {
                this.readJson.accept(json, result);
            }
            return result;
        }

        @Override
        public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            ShapedRecipe recipe = BASE_SERIALIZER.fromNetwork(recipeId, buffer);
            T result = this.recipeFactory.apply(recipe);
            if (this.readBuffer != null) {
                this.readBuffer.accept(buffer, result);
            }
            return result;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, T recipe) {
            BASE_SERIALIZER.toNetwork(buffer, recipe.getBaseRecipe());
            if (this.writeBuffer != null) {
                this.writeBuffer.accept(buffer, recipe);
            }
        }
    }
}
