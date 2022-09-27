/*
 * QModLib - EntityHelper
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ultreon.mods.lib.core.silentlib.util;

import com.ultreon.mods.lib.core.ModdingLibrary;
import com.ultreon.mods.lib.core.network.ModdingLibraryNet;
import com.ultreon.mods.lib.core.silentlib.network.internal.SpawnEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelWriter;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Mod.EventBusSubscriber(modid = ModdingLibrary.MOD_ID)
/**
 * @deprecated Removed
 */
@Deprecated
public final class EntityHelper {
    @SuppressWarnings("FieldMayBeFinal") // volatile may not be final
    private static volatile Queue<Entity> entitiesToSpawn = new ConcurrentLinkedDeque<>();

    private EntityHelper() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Thread-safe spawning of entities. The queued entities will be spawned in the START (pre)
     * phase of WorldTickEvent.
     *
     * @param entity The entity to spawn
     * @since 2.1.4
     */
    public static void safeSpawn(Entity entity) {
        // TODO: Is this still needed? What about DeferredWorkQueue, what's that do?
        entitiesToSpawn.add(entity);
    }

    public static void spawnWithClientPacket(LevelWriter world, Entity entity) {
        spawnWithClientPacket(world, entity, 4096);
    }

    public static void spawnWithClientPacket(LevelWriter world, Entity entity, double r2) {
        world.addFreshEntity(entity);
        if (world instanceof ServerLevel) {
            SpawnEntityPacket message = new SpawnEntityPacket(entity);
            ModdingLibraryNet.channel.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(entity.getX(), entity.getY(), entity.getZ(), r2, entity.level.dimension())), message);
        }
    }

    private static void handleSpawns() {
        Entity entity;
        while ((entity = entitiesToSpawn.poll()) != null) {
            entity.level.addFreshEntity(entity);
        }
    }

    /**
     * Heals the player by the given amount. If cancelable is true, this calls the heal method
     * (which fires a cancelable event). If cancelable is false, this uses setHealth instead.
     *
     * @param entity     The entity to heal
     * @param healAmount Amount of health to restore
     * @param cancelable Whether or not to fire a healing event
     * @since 2.2.9
     */
    public static void heal(LivingEntity entity, float healAmount, boolean cancelable) {
        if (cancelable) {
            entity.heal(healAmount);
        } else {
            entity.setHealth(entity.getHealth() + healAmount);
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            handleSpawns();
        }
    }
}
