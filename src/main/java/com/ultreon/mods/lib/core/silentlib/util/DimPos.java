/*
 * QModLib - DimPos
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

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Basically a BlockPos with a dimension coordinate.
 * <p>
 * I tried to extend BlockPos here, but that caused some strange issues with Silent's Gems'
 * teleporters. They would apparently fail to link, but would work correctly after reloading the
 * world. No idea why this happens, but extending BlockPos seems to be the cause.
 */

/**
 * @deprecated Removed
 */
@Deprecated
public final class DimPos {
    /**
     * Origin (0, 0, 0) in dimension 0
     */
    public static final DimPos ZERO = new DimPos(0, 0, 0, Level.OVERWORLD);

    private final int posX;
    private final int posY;
    private final int posZ;
    private final DimensionId dimension;

    //region Static factory methods

    private DimPos(BlockPos pos, ResourceKey<Level> dimension) {
        this(pos.getX(), pos.getY(), pos.getZ(), dimension);
    }

    private DimPos(int x, int y, int z, ResourceKey<Level> dimension) {
        this(x, y, z, DimensionId.fromId(dimension));
    }

    private DimPos(int x, int y, int z, DimensionId dimension) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.dimension = dimension;
    }

    //endregion

    public static DimPos of(BlockPos pos, ResourceKey<Level> dimension) {
        return new DimPos(pos, dimension);
    }

    public static DimPos of(int x, int y, int z, ResourceKey<Level> dimension) {
        return new DimPos(x, y, z, dimension);
    }

    public static DimPos of(Entity entity) {
        return new DimPos(entity.blockPosition(), entity.level.dimension());
    }

    public static DimPos read(CompoundTag tags) {
        return DimPos.of(
                tags.getInt("posX"),
                tags.getInt("posY"),
                tags.getInt("posZ"),
                ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(tags.getString("dim"))));
    }

    public int getX() {
        return this.posX;
    }

    public int getY() {
        return this.posY;
    }

    public int getZ() {
        return this.posZ;
    }

    public DimensionId getDimensionId() {
        return dimension;
    }

    public ResourceKey<Level> getDimension() {
        return this.dimension.getId();
    }

    public void write(CompoundTag tags) {
        tags.putInt("posX", this.posX);
        tags.putInt("posY", this.posY);
        tags.putInt("posZ", this.posZ);
        tags.putString("dim", dimension.getRegistryName().toString());
    }

    /**
     * Converts to a BlockPos
     *
     * @return A BlockPos with the same coordinates
     */
    public BlockPos getPos() {
        return new BlockPos(posX, posY, posZ);
    }

    public Vec3 getPosCentered(double yOffset) {
        return new Vec3(posX + 0.5, posY + yOffset, posZ + 0.5);
    }

    /**
     * Offset the DimPos in the given direction by the given distance.
     *
     * @param facing The direction to offset
     * @param n      The distance
     * @return A new DimPos with offset coordinates.
     * @since 4.0.10
     */
    public DimPos offset(Direction facing, int n) {
        if (n == 0) {
            return this;
        }
        return new DimPos(
                this.posX + facing.getStepX() * n,
                this.posY + facing.getStepY() * n,
                this.posZ + facing.getStepZ() * n,
                this.dimension);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %s) in %s", this.posX, this.posY, this.posZ, dimension.getRegistryName());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DimPos pos)) {
            return false;
        }
        return pos.dimension == dimension && pos.posX == posX && pos.posY == posY && pos.posZ == posZ;
    }

    @Override
    public int hashCode() {
        return 31 * (31 * (31 * posX + posY) + posZ) + dimension.getRegistryName().hashCode();
    }
}
