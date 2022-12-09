package com.ultreon.mods.lib.world;

import com.google.common.base.Suppliers;
import com.ultreon.mods.lib.util.ServerLifecycle;
import net.minecraft.core.Registry;
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
 * <a href="https://github.com/McJtyMods/McJtyLib/blob/1.16/src/main/java/mcjty/lib/varia/DimensionId.java">...</a>
 */
@SuppressWarnings("unused")
public class DimensionId {
    private final static Supplier<DimensionId> OVERWORLD = Suppliers.memoize(() -> new DimensionId(Level.OVERWORLD));
    private final ResourceKey<Level> id;

    private DimensionId(ResourceKey<Level> id) {
        this.id = id;
    }

    public static DimensionId overworld() {
        return OVERWORLD.get();
    }

    public static DimensionId fromId(ResourceKey<Level> id) {
        return new DimensionId(id);
    }

    public static DimensionId fromPacket(FriendlyByteBuf buf) {
        ResourceKey<Level> key = ResourceKey.create(Registry.DIMENSION_REGISTRY, buf.readResourceLocation());
        return new DimensionId(key);
    }

    public static DimensionId fromWorld(Level world) {
        return new DimensionId(world.dimension());
    }

    public static DimensionId fromResourceLocation(ResourceLocation location) {
        ResourceKey<Level> key = ResourceKey.create(Registry.DIMENSION_REGISTRY, location);
        return new DimensionId(key);
    }

    public static boolean sameDimension(Level world1, Level world2) {
        return world1.dimension().equals(world2.dimension());
    }

    public ResourceKey<Level> getId() {
        return id;
    }

    public ResourceLocation getRegistryName() {
        return id.location();
    }

    // Is this a good way to get the dimension name?
    public String getName() {
        return id.location().getPath();
    }

    public boolean isOverworld() {
        return id.equals(Level.OVERWORLD);
    }

    public void toBytes(FriendlyByteBuf buf) {
        // @todo use numerical ID
        buf.writeResourceLocation(id.location());
    }

    @SuppressWarnings("ConstantConditions")
    public ServerLevel loadWorld() {
        // Worlds in 1.16 are always loaded
        MinecraftServer server = ServerLifecycle.getCurrentServer();
        return server.getLevel(id);
    }

    // Do not load the world if it is not there
    @SuppressWarnings("ConstantConditions")
    public ServerLevel getWorld() {
        MinecraftServer server = ServerLifecycle.getCurrentServer();
        return server.getLevel(id);
    }

    @SuppressWarnings("ConstantConditions")
    public ServerLevel loadWorld(Level otherWorld) {
        // Worlds in 1.16 are always loaded
        return Objects.requireNonNull(otherWorld.getServer()).getLevel(id);
    }

    public boolean sameDimension(Level world) {
        return id.equals(world.dimension());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DimensionId that = (DimensionId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
