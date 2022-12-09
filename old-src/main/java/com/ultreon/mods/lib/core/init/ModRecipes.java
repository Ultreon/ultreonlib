package com.ultreon.mods.lib.core.init;

import com.ultreon.mods.lib.core.ModdingLibrary;
import com.ultreon.mods.lib.core.recipes.DamageItemRecipe;
import com.ultreon.mods.lib.core.recipes.ExtendedShapelessRecipe;
import com.ultreon.mods.lib.core.util.UtilityClass;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes extends UtilityClass {
    private static final DeferredRegister<RecipeSerializer<?>> REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ModdingLibrary.MOD_ID);
    public static final RegistryObject<ExtendedShapelessRecipe.Serializer<DamageItemRecipe>> DAMAGE_ITEM_CRAFTING = REGISTER.register("damage_item", () -> DamageItemRecipe.SERIALIZER);

    public static void register(IEventBus modEvents) {
        REGISTER.register(modEvents);
    }
}
