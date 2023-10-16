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

package com.ultreon.mods.lib.world;

import com.ultreon.mods.lib.nbt.NbtKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class MutableLocation extends Location {
    public static final MutableLocation ZERO = new MutableLocation(0, 0, 0, Level.OVERWORLD);

    private int posX;
    private int posY;
    private int posZ;
    private DimensionId dimension;

    private MutableLocation(BlockPos pos, ResourceKey<Level> dimension) {
        this(pos.getX(), pos.getY(), pos.getZ(), dimension);
    }

    private MutableLocation(int x, int y, int z, ResourceKey<Level> dimension) {
        this(x, y, z, DimensionId.fromId(dimension));
    }

    private MutableLocation(int x, int y, int z, DimensionId dimension) {
        super(x, y, z, dimension);
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.dimension = dimension;
    }

    //endregion

    public static MutableLocation of(BlockPos pos, ResourceKey<Level> dimension) {
        return new MutableLocation(pos, dimension);
    }

    public static MutableLocation of(int x, int y, int z, ResourceKey<Level> dimension) {
        return new MutableLocation(x, y, z, dimension);
    }

    public static MutableLocation of(Entity entity) {
        return new MutableLocation(entity.blockPosition(), entity.level().dimension());
    }

    public static MutableLocation read(CompoundTag tags) {
        return MutableLocation.of(
                tags.getInt(NbtKeys.X),
                tags.getInt(NbtKeys.Y),
                tags.getInt(NbtKeys.Z),
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(tags.getString(NbtKeys.DIMENSION))));
    }

    @Override
    public int getX() {
        return this.posX;
    }

    @Override
    public int getY() {
        return this.posY;
    }

    @Override
    public int getZ() {
        return this.posZ;
    }

    public DimensionId getDimensionId() {
        return dimension;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosZ(int posZ) {
        this.posZ = posZ;
    }

    public void setDimension(DimensionId dimension) {
        this.dimension = dimension;
    }

    @Override
    public DimensionId getDimension() {
        return this.dimension;
    }

    @Override
    public ResourceKey<Level> getDimensionKey() {
        return this.dimension.getKey();
    }

    @Override
    public ResourceLocation getDimensionLocation() {
        return this.dimension.getLocation();
    }

    @Override
    public Level getLevel() {
        return this.dimension.getLevel();
    }

    @Override
    public void write(CompoundTag tags) {
        tags.putInt(NbtKeys.X, this.posX);
        tags.putInt(NbtKeys.Y, this.posY);
        tags.putInt(NbtKeys.Z, this.posZ);
        tags.putString(NbtKeys.DIMENSION, dimension.getLocation().toString());
    }

    /**
     * Converts to a BlockPos
     *
     * @return A BlockPos with the same coordinates
     */
    @Override
    public BlockPos getPos() {
        return new BlockPos(posX, posY, posZ);
    }

    @Override
    public Vec3 centered(double yOffset) {
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
    @Override
    public MutableLocation offset(Direction facing, int n) {
        if (n == 0) {
            return this;
        }
        return new MutableLocation(
                this.posX + facing.getStepX() * n,
                this.posY + facing.getStepY() * n,
                this.posZ + facing.getStepZ() * n,
                this.dimension);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %s) in %s", this.posX, this.posY, this.posZ, this.dimension.getLocation());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MutableLocation pos)) {
            return false;
        }
        return pos.dimension == dimension && pos.posX == posX && pos.posY == posY && pos.posZ == posZ;
    }

    @Override
    public int hashCode() {
        return 31 * (31 * (31 * posX + posY) + posZ) + dimension.getLocation().hashCode();
    }
}
