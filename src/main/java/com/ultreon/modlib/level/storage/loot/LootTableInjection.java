package com.ultreon.modlib.level.storage.loot;

import com.ultreon.modlib.ModdingLib;
import com.ultreon.modlib.utils.ExceptionUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for injecting loot data into loot tables.
 * For example a custom entity drop. Or custom items in loot chests etc.
 * 
 * @author Qboi123
 */
@Mod.EventBusSubscriber(modid = ModdingLib.MOD_ID)
public class LootTableInjection {
    private static final Map<ResourceLocation, Injector> injections = new HashMap<>();

    /**
     * Creates a resource location with minecraft's id.
     * 
     * @param path resource path to the injection.
     * @return resource location object.
     */
    public static ResourceLocation mcLoc(String path) {
        return new ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, path);
    }

    /**
     * Creates a resource location with forge's id.
     * 
     * @param path resource path to the injection.
     * @return resource location object.
     */
    public static ResourceLocation forgeLoc(String path) {
        return new ResourceLocation("forge", path);
    }

    /**
     * Creates a resource location from the mod's loading context.
     * <b>Warning: Use only on the mod's constructor or other places when the mod's loading contexts to the expected mod.</b>
     *
     * @see ModLoadingContext
     * @param path resource path to the injection.
     * @return resource location object.
     */
    public static ResourceLocation modLoc(String path) {
        return new ResourceLocation(ModLoadingContext.get().getActiveNamespace(), path);
    }

    private LootTableInjection() {
        throw ExceptionUtil.utilityConstructor();
    }

    /**
     * This method injects the loot tables when a loot table gets loaded, this method isn't supposed to get called manually.
     * Use {@linkplain #registerInjection(ResourceLocation, String)} or {@linkplain #registerInjection(ResourceLocation, ResourceLocation)} instead.
     * 
     * @param evt the event for loading loot tables.
     */
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent evt) {
        ResourceLocation toBeInjected = evt.getName();

        Injector injector = injections.get(toBeInjected);
        if (injector != null) {
            evt.getTable().addPool(injector.createPool());
        }
    }

    /**
     * Registers a loot table injection, this will load in {@linkplain #onLootTableLoad(LootTableLoadEvent)}
     * 
     * @param target the loot table to inject.
     * @param injection the injection for the loot table.
     */
    public static void registerInjection(ResourceLocation target, ResourceLocation injection) {
        injections.put(target, new Injector(target, injection));
    }

    /**
     * Registers a loot table injection, this will load in {@linkplain #onLootTableLoad(LootTableLoadEvent)}
     * 
     * @param target the loot table to inject.
     * @param modId the mod's id to get the injection from.
     */
    public static void registerInjection(ResourceLocation target, String modId) {
        registerInjection(target, new ResourceLocation(modId, target.getPath()));
    }

    private record Injector(ResourceLocation target, ResourceLocation injection) {
        private LootPool createPool() {
            return LootPool.lootPool().add(createInjectionEntry(injection)).setBonusRolls(UniformGenerator.between(0, 1)).name("randomthingz_injection_" + injection.getNamespace() + "_" + injection.getPath().replaceAll("/", "_")).build();
        }

        private static LootPoolEntryContainer.Builder<?> createInjectionEntry(ResourceLocation name) {
            return LootTableReference.lootTableReference(new ResourceLocation(name.getNamespace(), "inject/" + name.getPath())).setWeight(1);
        }
    }
}
