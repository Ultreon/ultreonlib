package com.ultreon.mods.lib.commons.collection;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Either<L, R> {
    public static <L, R> Either<L, R> left(L value) {
        return new Left<>(value);
    }

    public static <L, R> Either<L, R> right(R value) {
        return new Right<>(value);
    }


    public abstract boolean isLeft();

    public abstract boolean isRight();

    public abstract L getLeft();

    public abstract R getRight();

    public void ifLeft(Consumer<L> consumer) {
        if (isLeft()) {
            consumer.accept(getLeft());
        }
    }

    public void ifRight(Consumer<R> consumer) {
        if (isRight()) {
            consumer.accept(getRight());
        }
    }

    public <T> Either<T, R> mapLeft(Function<L, T> mapper) {
        if (isLeft()) {
            return new Left<>(mapper.apply(getLeft()));
        } else {
            return new Right<>(getRight());
        }
    }

    public <T> Either<L, T> mapRight(Function<R, T> mapper) {
        if (isRight()) {
            return new Right<>(mapper.apply(getRight()));
        } else {
            return new Left<>(getLeft());
        }
    }

    public <TL, TR> Either<TL, TR> map(Function<L, TL> leftMapper, Function<R, TR> rightMapper) {
        if (isLeft()) {
            return new Left<>(leftMapper.apply(getLeft()));
        } else {
            return new Right<>(rightMapper.apply(getRight()));
        }
    }

    public void ifLeft(Consumer<L> consumer, Consumer<R> elseConsumer) {
        if (isLeft()) {
            consumer.accept(getLeft());
        } else {
            elseConsumer.accept(getRight());
        }
    }

    public void ifRight(Consumer<R> consumer, Consumer<L> elseConsumer) {
        if (isRight()) {
            consumer.accept(getRight());
        } else {
            elseConsumer.accept(getLeft());
        }
    }

    public <T> Either<T, R> mapLeft(Function<L, T> mapper, Consumer<R> elseConsumer) {
        if (isLeft()) {
            return new Left<>(mapper.apply(getLeft()));
        } else {
            elseConsumer.accept(getRight());
            return new Right<>(getRight());
        }
    }

    public <T> Either<L, T> mapRight(Function<R, T> mapper, Consumer<L> elseConsumer) {
        if (isRight()) {
            return new Right<>(mapper.apply(getRight()));
        } else {
            elseConsumer.accept(getLeft());
            return new Left<>(getLeft());
        }
    }

    public L getLeftOrElse(L defaultValue) {
        if (isLeft()) {
            return getLeft();
        } else {
            return defaultValue;
        }
    }

    public R getRightOrElse(R defaultValue) {
        if (isRight()) {
            return getRight();
        } else {
            return defaultValue;
        }
    }

    private static class Left<L, R> extends Either<L, R> {
        private final L value;

        public Left(L value) {
            this.value = value;
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public L getLeft() {
            return value;
        }

        @Override
        public R getRight() {
            throw new IllegalStateException("Either is Left");
        }
    }

    private static class Right<L, R> extends Either<L, R> {
        private final R value;

        public Right(R value) {
            this.value = value;
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        public L getLeft() {
            throw new IllegalStateException("Either is Right");
        }

        @Override
        public R getRight() {
            return value;
        }
    }
}
