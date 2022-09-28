package com.ultreon.mods.lib.core.util;

import com.ultreon.mods.lib.core.ModdingLibrary;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class FirstSpawnItems {
    private static final HashMap<ResourceLocation, Function<Player, Collection<ItemStack>>> spawnItems = new HashMap<>();

    static {
        new FirstSpawnItems();
    }

    private FirstSpawnItems() {
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerLoggedIn);
    }

    public static void register(ResourceLocation id, Supplier<ItemStack> supplier) {
        spawnItems.put(id, player -> {
            ItemStack value = supplier.get();
            if (value.isEmpty()) return Collections.emptyList();
            return List.of(value);
        });
    }

    public static void register(ResourceLocation id, Function<Player, Collection<ItemStack>> factory) {
        spawnItems.put(id, factory);
    }

    private static void spawnItems(Player player, CompoundTag givenItems, ResourceLocation key, Collection<ItemStack> items) {
        if (items.isEmpty()) return;

        String nbtKey = key.toString().replace(':', '.');
        if (!givenItems.getBoolean(nbtKey)) {
            items.forEach(stack -> {
                ModdingLibrary.LOGGER.debug("Giving player {} spawn item \"{}\": {}", player.getScoreboardName(), nbtKey, stack);
                PlayerUtils.giveItem(player, stack);
                givenItems.putBoolean(nbtKey, true);
            });
        }
    }

    private void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        CompoundTag givenItems = PlayerUtils.getPersistedCompound(player, ModdingLibrary.MOD_ID + ".FirstSpawnItems");

        spawnItems.forEach((key, factory) -> spawnItems(player, givenItems, key, factory.apply(player)));
    }
}
