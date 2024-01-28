package com.ultreon.mods.lib.functions.supplier;

import java.util.function.Supplier;

@FunctionalInterface
public interface CharSupplier extends Supplier<Character> {
    @Override
    default Character get() {
        return this.getChar();
    }

    char getChar();
}
