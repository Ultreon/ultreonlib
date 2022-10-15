package com.ultreon.mods.lib.core.recipes;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class ExtendedShapelessRecipe extends ShapelessRecipe {
    private static final RecipeSerializer<ShapelessRecipe> BASE_SERIALIZER = RecipeSerializer.SHAPELESS_RECIPE;

    private final ShapelessRecipe recipe;

    public ExtendedShapelessRecipe(ShapelessRecipe recipe) {
        super(recipe.getId(), recipe.getGroup(), recipe.getResultItem(), recipe.getIngredients());
        this.recipe = recipe;
    }

    public ShapelessRecipe getBaseRecipe() {
        return this.recipe;
    }

    @NotNull
    @Override
    public abstract RecipeSerializer<?> getSerializer();

    @Override
    public abstract boolean matches(@NotNull CraftingContainer inv, @NotNull Level worldIn);

    @NotNull
    @Override
    public abstract ItemStack assemble(@NotNull CraftingContainer inv);

    public static class Serializer<T extends ExtendedShapelessRecipe> implements RecipeSerializer<T> {
        private final Function<ShapelessRecipe, T> recipeFactory;
        @Nullable
        private final BiConsumer<JsonObject, T> jsonRead;
        @Nullable
        private final BiConsumer<FriendlyByteBuf, T> networkRead;
        @Nullable
        private final BiConsumer<FriendlyByteBuf, T> networkWrite;

        public Serializer(Function<ShapelessRecipe, T> recipeFactory, @Nullable BiConsumer<JsonObject, T> jsonRead, @Nullable BiConsumer<FriendlyByteBuf, T> networkRead, @Nullable BiConsumer<FriendlyByteBuf, T> networkWrite) {
            this.recipeFactory = recipeFactory;
            this.jsonRead = jsonRead;
            this.networkRead = networkRead;
            this.networkWrite = networkWrite;
        }

        public static <S extends ExtendedShapelessRecipe> Serializer<S> basic(Function<ShapelessRecipe, S> recipeFactory) {
            return new Serializer<>(recipeFactory, null, null, null);
        }

        @NotNull
        @Override
        public T fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
            ShapelessRecipe recipe = BASE_SERIALIZER.fromJson(recipeId, json);
            T result = this.recipeFactory.apply(recipe);
            if (this.jsonRead != null) {
                this.jsonRead.accept(json, result);
            }
            return result;
        }

        @Override
        public T fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
            ShapelessRecipe recipe = BASE_SERIALIZER.fromNetwork(recipeId, buffer);
            T result = this.recipeFactory.apply(recipe);
            if (this.networkRead != null) {
                this.networkRead.accept(buffer, result);
            }
            return result;
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, T recipe) {
            BASE_SERIALIZER.toNetwork(buffer, recipe.getBaseRecipe());
            if (this.networkWrite != null) {
                this.networkWrite.accept(buffer, recipe);
            }
        }
    }
}
