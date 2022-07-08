package com.ultreon.modlib.api;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum ConnectionType implements StringRepresentable {
    NONE,
    IN,
    OUT,
    BOTH;

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
