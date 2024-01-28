package com.ultreon.mods.lib.commons;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @param <T>
 * @since 0.2.0
 * @author <a href="https://github.com/XyperCodee">XyperCode</a>
 */
public class Result<T> {
    private final Value<T> value;
    private final Failure failure;

    private Result(Value<T> value, Failure failure) {
        this.value = value;
        this.failure = failure;
    }

    public static <T> Result<T> left(T left) {
        return new Result<>(new Value<>(left), null);
    }

    public static <T> Result<T> right(Throwable right) {
        return new Result<>(null, new Failure(right));
    }

    public T getValue() {
        if (this.value == null) throw new NoSuchElementException("The value is not present.");
        return this.value.value;
    }

    public Throwable getFailure() {
        if (this.failure == null) throw new NoSuchElementException("The failure is not present.");
        return this.failure.throwable;
    }

    public boolean isValuePresent() {
        return this.value != null;
    }

    public boolean isFailurePresent() {
        return this.failure != null;
    }

    public void ifValue(Consumer<T> onValue) {
        if (this.value != null) onValue.accept(this.value.value);
    }

    public void ifFailure(Consumer<Throwable> onFailure) {
        if (this.failure != null) onFailure.accept(this.failure.throwable);
    }

    public void ifValueOrElse(Consumer<T> onValue, Runnable runnable) {
        if (this.value != null) onValue.accept(this.value.value);
        else runnable.run();
    }

    public void ifFailureOrElse(Consumer<Throwable> onFailure, Runnable runnable) {
        if (this.failure != null) onFailure.accept(this.failure.throwable);
        else runnable.run();
    }

    public T getValueOrNull() {
        return this.value.value;
    }

    public Throwable getFailureOrNull() {
        return this.failure.throwable;
    }

    public T getValueOrNullOr(T other) {
        T value = this.value.value;
        return value == null ? other : value;
    }

    public Throwable getFailureOrNullOr(Throwable other) {
        Throwable value = this.failure.throwable;
        return value == null ? other : value;
    }

    public T getValueOrNullOrGet(Supplier<? extends T> other) {
        T value = this.value.value;
        return value == null ? other.get() : value;
    }

    public Throwable getFailureOrNullOr(Supplier<? extends Throwable> other) {
        Throwable value = this.failure.throwable;
        return value == null ? other.get() : value;
    }

    public void ifAny(Consumer<T> onValue, Consumer<Throwable> onFailure) {
        if (this.value != null) onValue.accept(this.value.value);
        else if (this.failure != null) onFailure.accept(this.failure.throwable);
    }

    private static class Value<L> {
        private final L value;

        public Value(L value) {
            this.value = value;
        }
    }

    private static class Failure {
        private final Throwable throwable;

        public Failure(Throwable throwable) {
            this.throwable = throwable;
        }
    }
}
