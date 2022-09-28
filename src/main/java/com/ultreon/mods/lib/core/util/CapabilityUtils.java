package com.ultreon.mods.lib.core.util;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public final class CapabilityUtils extends UtilityClass {
    @Nullable
    public static <T> T getOrNull(ICapabilityProvider provider, Capability<T> cap) {
        LazyOptional<T> lazy = provider.getCapability(cap);
        if (lazy.isPresent()) {
            return lazy.orElseThrow(IllegalStateException::new);
        }
        return null;
    }
}
