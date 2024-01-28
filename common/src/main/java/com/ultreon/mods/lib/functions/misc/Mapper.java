package com.ultreon.mods.lib.functions.misc;

@FunctionalInterface
public interface Mapper<A, B> {
    B map(A value);
}
