package com.ultreon.mods.lib.core.silentutils;

import java.util.function.Supplier;

/**
 * @deprecated Removed
 */
@Deprecated
public class Lazy<T> {
    private T value;
    private Supplier<T> supplier;

    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    public T get() {
        if (supplier != null) {
            value = supplier.get();
            supplier = null;
        }
        return value;
    }
}
