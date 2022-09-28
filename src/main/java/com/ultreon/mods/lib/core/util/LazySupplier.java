package com.ultreon.mods.lib.core.util;

import java.util.function.Supplier;

/**
 * Reimplementation of {@link net.minecraft.util.LazyLoadedValue}, because the class there is deprecated.
 */
public class LazySupplier<T> {
    private T value;
    private Supplier<T> supplier;

    public LazySupplier(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> LazySupplier<T> of(Supplier<T> supplier) {
        return new LazySupplier<>(supplier);
    }

    public static <T> LazySupplier<T> of(T obj) {
        return new LazySupplier<>(() -> obj);
    }

    public T get() {
        if (supplier != null) {
            value = supplier.get();
            supplier = null;
        }
        return value;
    }
}
