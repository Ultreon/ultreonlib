package com.ultreon.mods.lib.commons.lang.holders;

import com.google.common.base.Suppliers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@FunctionalInterface
public interface LevelHolder {
    @Nullable
    Level getLevel();

    @Nullable
    default ResourceKey<Level> getDimensionKey() {
        Level level = getLevel();
        return level != null ? level.dimension() : null;
    }

    static LevelHolder self(Level level) {
        return () -> level;
    }

    static LevelHolder of(Supplier<Level> supplier) {
        return Suppliers.memoize(supplier::get)::get;
    }

    static LevelHolder of(ResourceKey<Level> key) {
        return new LevelHolder() {
            @Override
            public @Nullable Level getLevel() {
                return ServerLifecycleHooks.getCurrentServer().getLevel(key);
            }

            @Override
            public @Nullable ResourceKey<Level> getDimensionKey() {
                return key;
            }
        };
    }
}