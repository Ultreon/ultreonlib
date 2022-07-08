package com.ultreon.modlib.silentlib.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ultreon.modlib.ModdingLib;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class TestRecipeProvider extends RecipeProvider {
    public TestRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ModdingLib.LOGGER.warn("Running test recipe provider! These files should NOT be included in release!");

        DamageItemRecipeBuilder.builder(Items.DIAMOND, 9)
                .damageToItems(3)
                .addIngredient(Items.DIAMOND_PICKAXE)
                .addIngredient(Blocks.DIAMOND_BLOCK)
                .addCriterion("has_item", has(Blocks.DIAMOND_BLOCK))
                .build(consumer, ModdingLib.res("damage_item_test1"));
        DamageItemRecipeBuilder.builder(Items.EMERALD, 9)
                .damageToItems(3)
                .addExtraData(json -> json.addProperty("test", "This is a test!"))
                .addIngredient(Items.DIAMOND_PICKAXE)
                .addIngredient(Blocks.EMERALD_BLOCK)
                .build(consumer, ModdingLib.res("damage_item_test2"));
        ExtendedShapelessRecipeBuilder.vanillaBuilder(Blocks.DIRT, 10)
                .addIngredient(Tags.Items.GEMS_EMERALD)
                .addExtraData(json -> json.addProperty("test2", "Can you hear me now?"))
                .build(consumer, ModdingLib.res("extended_shapeless_test1"));

        ExtendedShapedRecipeBuilder.vanillaBuilder(Items.DIAMOND_SWORD)
                .patternLine("  #")
                .patternLine(" # ")
                .patternLine("/  ")
                .key('#', Tags.Items.GEMS_DIAMOND)
                .key('/', Tags.Items.RODS_WOODEN)
                .addExtraData(json -> addLore(json, "Diagonal sword!", "<3 data generators"))
                .build(consumer, ModdingLib.res("extended_shaped_test1"));
    }

    private void addLore(JsonObject json, String... lore) {
        JsonObject result = GsonHelper.getAsJsonObject(json, "result");
        JsonObject display = new JsonObject();
        JsonObject nbt = new JsonObject();
        JsonArray array = new JsonArray();
        for (String line : lore) {
            array.add("\"" + line + "\"");
        }
        display.add("Lore", array);
        nbt.add("display", display);
        result.add("nbt", nbt);
    }
}
