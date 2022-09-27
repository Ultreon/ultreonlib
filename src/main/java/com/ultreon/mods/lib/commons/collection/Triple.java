package com.ultreon.mods.lib.commons.collection;

import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;

public record Triple<F, S, T>(F first, S second, T third) {
    public static <F, S, T> Triple<F, S, T> of(F first, S second, T third) {
        return new Triple<>(first, second, third);
    }

    public <V> Triple<V, S, T> mapFirst(Function<F, V> mapper) {
        return new Triple<>(mapper.apply(first), second, third);
    }

    public <V> Triple<F, V, T> mapSecond(Function<S, V> mapper) {
        return new Triple<>(first, mapper.apply(second), third);
    }

    public <V> Triple<F, S, V> mapThird(Function<T, V> mapper) {
        return new Triple<>(first, second, mapper.apply(third));
    }

    public <FR, SR, TR> Triple<FR, SR, TR> map(Function<F, FR> firstMapper, Function<S, SR> secondMapper, Function<T, TR> thirdMapper) {
        return new Triple<>(firstMapper.apply(first), secondMapper.apply(second), thirdMapper.apply(third));
    }

    public List<?> toList() {
        return List.of(first, second, third);
    }

    public Set<?> toSet() {
        return Set.of(first, second, third);
    }

    public Stack<Object> toStack() {
        Stack<Object> stack = new Stack<>();
        stack.push(first);
        stack.push(second);
        stack.push(third);
        return stack;
    }

    public Object[] toArray() {
        return new Object[]{first, second, third};
    }
}
