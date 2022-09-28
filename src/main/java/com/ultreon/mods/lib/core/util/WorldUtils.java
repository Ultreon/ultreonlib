package com.ultreon.mods.lib.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

public final class WorldUtils extends UtilityClass {
    @SuppressWarnings("MethodWithTooManyParameters")
    public static <T> Map<BlockPos, T> getBlocks(Level world, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, BiFunction<Level, BlockPos, Optional<T>> getter) {
        Map<BlockPos, T> map = new LinkedHashMap<>();
        if (!world.hasChunksAt(xMin, yMin, zMin, xMax, yMax, zMax)) {
            return map;
        }

        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
        for (int x = xMin; x <= xMax; ++x) {
            for (int y = yMin; y <= yMax; ++y) {
                for (int z = zMin; z <= zMax; ++z) {
                    BlockPos pos = new BlockPos(x, y, z);
                    getter.apply(world, blockPos.set(x, y, z)).ifPresent(t -> map.put(pos, t));
                }
            }
        }

        return map;
    }

    public static <T> Map<BlockPos, T> getBlocksInArea(Level world, BlockPos pos, int range, BiFunction<Level, BlockPos, Optional<T>> getter) {
        int xMin = pos.getX() - range;
        int xMax = pos.getX() + range;
        int yMin = pos.getY() - range;
        int yMax = pos.getY() + range;
        int zMin = pos.getZ() - range;
        int zMax = pos.getZ() + range;
        return getBlocks(world, xMin, yMin, zMin, xMax, yMax, zMax, getter);
    }

    public static <T> Map<BlockPos, T> getBlocksInSphere(Level world, BlockPos pos, int radius, BiFunction<Level, BlockPos, Optional<T>> getter) {
        final int radiusSq = radius * radius;
        int xMin = pos.getX() - radius;
        int xMax = pos.getX() + radius;
        int yMin = pos.getY() - radius;
        int yMax = pos.getY() + radius;
        int zMin = pos.getZ() - radius;
        int zMax = pos.getZ() + radius;
        return getBlocks(world, xMin, yMin, zMin, xMax, yMax, zMax, (world1, pos1) -> {
            if (Meth.distanceSq(pos, pos1) <= radiusSq) {
                return getter.apply(world1, pos1);
            }
            return Optional.empty();
        });
    }

    @SuppressWarnings("MethodWithTooManyParameters")
    public static <T extends BlockEntity> Map<BlockPos, T> getTileEntities(Class<? extends T> clazz, Level world, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
        return getBlocks(world, xMin, yMin, zMin, xMax, yMax, zMax, (world1, pos) ->
                getTileEntityOfType(clazz, pos, world1));
    }

    public static <T extends BlockEntity> Map<BlockPos, T> getTileEntitiesInArea(Class<? extends T> clazz, Level world, BlockPos pos, int range) {
        int xMin = pos.getX() - range;
        int xMax = pos.getX() + range;
        int yMin = pos.getY() - range;
        int yMax = pos.getY() + range;
        int zMin = pos.getZ() - range;
        int zMax = pos.getZ() + range;
        return getTileEntities(clazz, world, xMin, yMin, zMin, xMax, yMax, zMax);
    }

    public static <T extends BlockEntity> Map<BlockPos, T> getTileEntitiesInSphere(Class<? extends T> clazz, Level world, BlockPos pos, int radius) {
        final int radiusSq = radius * radius;
        int xMin = pos.getX() - radius;
        int xMax = pos.getX() + radius;
        int yMin = pos.getY() - radius;
        int yMax = pos.getY() + radius;
        int zMin = pos.getZ() - radius;
        int zMax = pos.getZ() + radius;
        return getBlocks(world, xMin, yMin, zMin, xMax, yMax, zMax, (world1, pos1) -> {
            if (Meth.distanceSq(pos, pos1) <= radiusSq) {
                return getTileEntityOfType(clazz, pos, world1);
            }
            return Optional.empty();
        });
    }

    @SuppressWarnings("unchecked")
    private static <T extends BlockEntity> Optional<T> getTileEntityOfType(Class<? extends T> clazz, BlockPos pos, BlockGetter world) {
        BlockEntity te = world.getBlockEntity(pos);
        return clazz.isInstance(te) ? Optional.of((T) te) : Optional.empty();
    }
}
