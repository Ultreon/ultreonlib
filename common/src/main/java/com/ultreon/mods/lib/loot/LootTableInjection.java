package com.ultreon.mods.lib.loot;

import com.ultreon.mods.lib.util.UtilityClass;
import dev.architectury.event.events.common.LootEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for injecting loot data into loot tables.
 * For example a custom entity drop. Or custom items in loot chests etc.
 *
 * @author Qboi123
 */
public class LootTableInjection extends UtilityClass {
    private static final Map<ResourceLocation, Injector> injections = new HashMap<>();

    private LootTableInjection() {
        super();
    }

    /**
     * Creates a resource location with minecraft's id.
     *
     * @param path resource path to the injection.
     * @return resource location object.
     */
    public static ResourceLocation mcId(String path) {
        return new ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, path);
    }

    /**
     * Creates a resource location with forge's id.
     *
     * @param path resource path to the injection.
     * @return resource location object.
     */
    public static ResourceLocation forgeId(String path) {
        return new ResourceLocation("forge", path);
    }

    /**
     * Registers a loot table injection, this will load in {@linkplain #runModifications(LootTables, ResourceLocation, LootEvent.LootTableModificationContext, boolean)}
     *
     * @param target    the loot table to inject.
     * @param injection the injection for the loot table.
     */
    public static void registerInjection(ResourceLocation target, ResourceLocation injection) {
        injections.put(target, new Injector(target, injection));
    }

    /**
     * Registers a loot table injection, this will load in {@linkplain #runModifications(LootTables, ResourceLocation, LootEvent.LootTableModificationContext, boolean)}
     *
     * @param target the loot table to inject.
     * @param modId  the mod's id to get the injection from.
     */
    public static void registerInjection(ResourceLocation target, String modId) {
        registerInjection(target, new ResourceLocation(modId, target.getPath()));
    }

    public static void runModifications(LootTables manager, ResourceLocation id, LootEvent.LootTableModificationContext context, boolean builtin) {
        if (builtin) {
            Injector injector = injections.get(id);
            if (injector != null) {
                context.addPool(injector.createPool());
            }
        }
    }

    private record Injector(ResourceLocation target, ResourceLocation injection) {
        private static LootPoolEntryContainer.Builder<?> createInjectionEntry(ResourceLocation name) {
            return LootTableReference.lootTableReference(new ResourceLocation(name.getNamespace(), "inject/" + name.getPath())).setWeight(1);
        }

        private LootPool createPool() {
            return LootPool.lootPool().add(createInjectionEntry(injection)).setBonusRolls(UniformGenerator.between(0, 1)).build();
        }
    }
}
