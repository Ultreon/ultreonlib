package com.ultreon.mods.lib.world;

import com.google.common.base.Suppliers;
import com.ultreon.mods.lib.util.ServerLifecycle;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Copied from McJtyLib on 2022-09-28. It's pretty useful.
 * <p>
 * <a href="https://github.com/McJtyMods/McJtyLib/blob/1.16/src/main/java/mcjty/lib/varia/DimensionId.java">Link to original source code</a>
 * <p>
 * Edited by <a href="https://github.com/XyperCode">XyperCode</a> for use in modern versions.
 */
@SuppressWarnings("unused")
public class DimensionId {
    private final static Supplier<DimensionId> OVERWORLD = Suppliers.memoize(() -> new DimensionId(Level.OVERWORLD));
    private final static Supplier<DimensionId> NETHER = Suppliers.memoize(() -> new DimensionId(Level.NETHER));
    private final static Supplier<DimensionId> END = Suppliers.memoize(() -> new DimensionId(Level.END));
    private final ResourceKey<Level> key;

    private DimensionId(ResourceKey<Level> key) {
        this.key = key;
    }

    public static DimensionId overworld() {
        return OVERWORLD.get();
    }

    public static DimensionId nether() {
        return NETHER.get();
    }

    public static DimensionId end() {
        return END.get();
    }

    public static DimensionId fromId(ResourceKey<Level> id) {
        return new DimensionId(id);
    }

    public static DimensionId fromPacket(FriendlyByteBuf buf) {
        ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, buf.readResourceLocation());
        return new DimensionId(key);
    }

    public static DimensionId fromLevel(Level level) {
        return new DimensionId(level.dimension());
    }

    public static DimensionId fromResourceLocation(ResourceLocation location) {
        ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, location);
        return new DimensionId(key);
    }

    public static boolean sameDimension(Level level1, Level level2) {
        return level1.dimension().equals(level2.dimension());
    }

    public ResourceKey<Level> getKey() {
        return key;
    }

    public ResourceLocation getLocation() {
        return key.location();
    }

    // Is this a good way to get the dimension name?
    public String getName() {
        return key.location().getPath();
    }

    public boolean isOverworld() {
        return key.equals(Level.OVERWORLD);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(key.registry());
    }

    @SuppressWarnings("ConstantConditions")
    @Deprecated
    public ServerLevel loadWorld() {
        // Worlds in 1.16 are always loaded
        MinecraftServer server = ServerLifecycle.getCurrentServer();
        return server.getLevel(key);
    }

    // Do not load the world if it is not there
    @SuppressWarnings("ConstantConditions")
    public ServerLevel getLevel() {
        MinecraftServer server = ServerLifecycle.getCurrentServer();
        return server.getLevel(key);
    }

    @SuppressWarnings("ConstantConditions")
    public ServerLevel getLevelFrom(Level other) {
        // Worlds in 1.16 are always loaded
        return Objects.requireNonNull(other.getServer()).getLevel(key);
    }

    public boolean sameDimension(Level level) {
        return key.equals(level.dimension());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DimensionId that = (DimensionId) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
