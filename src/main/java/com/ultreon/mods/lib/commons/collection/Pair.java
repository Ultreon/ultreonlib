package com.ultreon.mods.lib.commons.collection;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;

public record Pair<F, S>(F first, S second) {
    public static <F, S> Pair<F, S> of(F first, S third) {
        return new Pair<>(first, third);
    }

    public <R> Pair<R, S> mapFirst(Function<F, R> mapper) {
        return new Pair<>(mapper.apply(first), second);
    }

    public <R> Pair<F, R> mapSecond(Function<S, R> mapper) {
        return new Pair<>(first, mapper.apply(second));
    }

    public <FR, SR> Pair<FR, SR> map(Function<F, FR> firstMapper, Function<S, SR> secondMapper) {
        return new Pair<>(firstMapper.apply(first), secondMapper.apply(second));
    }

    public List<?> toList() {
        return List.of(first, second);
    }

    public Set<?> toSet() {
        return Set.of(first, second);
    }

    public Map.Entry<F, S> toMapEntry() {
        return Map.entry(first, second);
    }

    public Map<F, S> toMap() {
        return Map.of(first, second);
    }

    public Stack<Object> toStack() {
        Stack<Object> stack = new Stack<>();
        stack.push(first);
        stack.push(second);
        return stack;
    }

    public Object[] toArray() {
        return new Object[]{first, second};
    }

    public boolean isAllNull() {
        return first == null && second == null;
    }

    public boolean isAllNonNull() {
        return first != null && second != null;
    }

    public boolean isAnyNull() {
        return first == null || second == null;
    }

    public boolean isAnyNonNull() {
        return first != null || second != null;
    }
}
