package dev.ultreon.mods.lib.loot;

import dev.ultreon.mods.lib.UltreonLib;
import dev.ultreon.mods.lib.util.UtilityClass;
import dev.architectury.event.events.common.LootEvent;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for injecting loot data into loot tables.
 * For example a custom entity drop. Or custom items in loot chests etc.
 *
 * @author XyperCode
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
        return ResourceLocation.tryBuild(ResourceLocation.DEFAULT_NAMESPACE, path);
    }

    /**
     * Creates a resource location with forge's id.
     *
     * @param path resource path to the injection.
     * @return resource location object.
     */
    public static ResourceLocation forgeId(String path) {
        return ResourceLocation.tryBuild("forge", path);
    }

    /**
     * Creates a resource location with forge's id.
     *
     * @param path resource path to the injection.
     * @return resource location object.
     */
    public static ResourceLocation neoForgeId(String path) {
        return ResourceLocation.tryBuild("neoforge", path);
    }

    /**
     * Creates a resource location with fabric's id.
     *
     * @param path resource path to the injection.
     * @return resource location object.
     */
    public static ResourceLocation fabricId(String path) {
        return ResourceLocation.tryBuild("fabric", path);
    }

    /**
     * Creates a resource location with fabric's "conventional tags" id.
     *
     * @param path resource path to the injection.
     * @return resource location object.
     */
    public static ResourceLocation cId(String path) {
        return ResourceLocation.tryBuild("c", path);
    }

    /**
     * Creates a resource location with fabric's "conventional tags" id.
     *
     * @param path resource path to the injection.
     * @return resource location object.
     */
    public static ResourceLocation ultreonLibId(String path) {
        return ResourceLocation.tryBuild(UltreonLib.MOD_ID, path);
    }

    /**
     * Registers a loot table injection, this will load in {@linkplain #runModifications(ResourceKey, LootEvent.LootTableModificationContext, boolean)}
     *
     * @param target    the loot table to inject.
     * @param injection the injection for the loot table.
     */
    public static void registerInjection(ResourceLocation target, ResourceLocation injection) {
        injections.put(target, new Injector(target, injection));
    }

    /**
     * Registers a loot table injection, this will load in {@linkplain #runModifications(ResourceKey, LootEvent.LootTableModificationContext, boolean)}
     *
     * @param target the loot table to inject.
     * @param modId  the mod's id to get the injection from.
     */
    public static void registerInjection(ResourceLocation target, String modId) {
        registerInjection(target, ResourceLocation.tryBuild(modId, target.getPath()));
    }

    @ApiStatus.Internal
    public static void runModifications(ResourceKey<LootTable> key, LootEvent.LootTableModificationContext context, boolean builtin) {
        if (builtin) {
            Injector injector = injections.get(key.location());
            if (injector != null) {
                context.addPool(injector.createPool());
            }
        }
    }

    private record Injector(ResourceLocation target, ResourceLocation injection) {
        private static LootPoolEntryContainer.Builder<?> createInjectionEntry(ResourceLocation name) {
            return NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.tryBuild(name.getNamespace(), "inject/" + name.getPath()))).setWeight(1);
        }

        private LootPool.Builder createPool() {
            return LootPool.lootPool().add(createInjectionEntry(injection)).setBonusRolls(UniformGenerator.between(0, 1));
        }
    }
}
