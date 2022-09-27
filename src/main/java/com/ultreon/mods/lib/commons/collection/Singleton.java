package com.ultreon.mods.lib.commons.collection;

import java.util.function.Function;

public record Singleton<T>(T value) {
    public static <T> Singleton<T> of(T value) {
        return new Singleton<>(value);
    }

    public T get() {
        return value;
    }

    public boolean isNull() {
        return value == null;
    }

    public boolean isNotNull() {
        return value != null;
    }

    public Singleton<T> mapIfNull(Function<T, T> mapper) {
        if (value == null) {
            return new Singleton<>(mapper.apply(value));
        } else {
            return this;
        }
    }

    public Singleton<T> mapIfNotNull(Function<T, T> mapper) {
        if (value != null) {
            return new Singleton<>(mapper.apply(value));
        } else {
            return this;
        }
    }
}
