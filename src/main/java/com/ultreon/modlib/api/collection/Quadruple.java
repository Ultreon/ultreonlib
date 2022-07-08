package com.ultreon.modlib.api.collection;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;

public record Quadruple<T1, T2, T3, T4>(T1 first, T2 second, T3 third, T4 fourth) {
    public static <T1, T2, T3, T4> Quadruple<T1, T2, T3, T4> of(T1 first, T2 second, T3 third, T4 fourth) {
        return new Quadruple<>(first, second, third, fourth);
    }

    public <R> Quadruple<R, T2, T3, T4> mapFirst(Function<T1, R> mapper) {
        return new Quadruple<>(mapper.apply(first), second, third, fourth);
    }

    public <R> Quadruple<T1, R, T3, T4> mapSecond(Function<T2, R> mapper) {
        return new Quadruple<>(first, mapper.apply(second), third, fourth);
    }

    public <R> Quadruple<T1, T2, R, T4> mapThird(Function<T3, R> mapper) {
        return new Quadruple<>(first, second, mapper.apply(third), fourth);
    }

    public <R> Quadruple<T1, T2, T3, R> mapFourth(Function<T4, R> mapper) {
        return new Quadruple<>(first, second, third, mapper.apply(fourth));
    }

    public <R1, R2, R3, R4> Quadruple<R1, R2, R3, R4> map(Function<T1, R1> firstMapper, Function<T2, R2> secondMapper, Function<T3, R3> thirdMapper, Function<T4, R4> fourthMapper) {
        return new Quadruple<>(firstMapper.apply(first), secondMapper.apply(second), thirdMapper.apply(third), fourthMapper.apply(fourth));
    }

    public List<?> toList() {
        return List.of(first, second, third, fourth);
    }

    public Set<?> toSet() {
        return Set.of(first, second, third, fourth);
    }

    public Map<?, ?> toMap() {
        return Map.of(first, second, third, fourth);
    }

    public Stack<Object> toStack() {
        Stack<Object> stack = new Stack<>();
        stack.push(first);
        stack.push(second);
        stack.push(third);
        stack.push(fourth);
        return stack;
    }

    public Map<Pair<T1, T2>, Pair<T3, T4>> toPairMap() {
        return Map.of(Pair.of(first, second), Pair.of(third, fourth));
    }

    public Map.Entry<Pair<T1, T2>, Pair<T3, T4>> toPairMapEntry() {
        return Map.entry(Pair.of(first, second), Pair.of(third, fourth));
    }

    public Object[] toArray() {
        return new Object[]{first, second, third, fourth};
    }
}
