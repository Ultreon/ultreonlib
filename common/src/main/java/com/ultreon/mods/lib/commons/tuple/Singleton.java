package com.ultreon.mods.lib.commons.tuple;

import java.util.Objects;

public class Singleton<T> {
    private T value;

    public Singleton(T value) {
        this.value = value;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Singleton<?> singleton = (Singleton<?>) o;
        return Objects.equals(this.value, singleton.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value);
    }

    @Override
    public String toString() {
        return "(" + this.value + ')';
    }

    @Override
    @SuppressWarnings("unchecked")
    public Singleton<T> clone() throws CloneNotSupportedException {
        return (Singleton<T>) super.clone();
    }
}
