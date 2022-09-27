package com.ultreon.mods.lib.core.api;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@Deprecated
public enum ConnectionType implements StringRepresentable {
    NONE,
    IN,
    OUT,
    BOTH;

    @NotNull
    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    public boolean canReceive() {
        return this == IN || this == BOTH;
    }

    public boolean canExtract() {
        return this == OUT || this == BOTH;
    }
}
