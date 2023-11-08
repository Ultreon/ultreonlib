package com.ultreon.mods.lib.util.holders;

import com.google.common.base.Suppliers;
import com.ultreon.mods.lib.util.ServerLifecycle;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
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
                return ServerLifecycle.getCurrentServer().getLevel(key);
            }

            @Override
            public @Nullable ResourceKey<Level> getDimensionKey() {
                return key;
            }
        };
    }
}