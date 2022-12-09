package com.ultreon.mods.lib.core.util;

public final class Exceptions {
    private Exceptions() {
        throw utilityConstructor();
    }

    public static UnsupportedOperationException utilityConstructor() {
        return new UnsupportedOperationException("Tried to initialize constructor of utility class.");
    }
}
