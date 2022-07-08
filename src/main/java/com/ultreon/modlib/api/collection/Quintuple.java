package com.ultreon.modlib.api.collection;

import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;

public record Quintuple<T1, T2, T3, T4, T5>(T1 first, T2 second, T3 third, T4 fourth, T5 fifth) {
    public static <T1, T2, T3, T4, T5> Quintuple<T1, T2, T3, T4, T5> of(T1 first, T2 second, T3 third, T4 fourth, T5 fifth) {
        return new Quintuple<>(first, second, third, fourth, fifth);
    }

    public <R> Quintuple<R, T2, T3, T4, T5> mapFirst(Function<T1, R> mapper) {
        return new Quintuple<>(mapper.apply(first), second, third, fourth, fifth);
    }

    public <R> Quintuple<T1, R, T3, T4, T5> mapSecond(Function<T2, R> mapper) {
        return new Quintuple<>(first, mapper.apply(second), third, fourth, fifth);
    }

    public <R> Quintuple<T1, T2, R, T4, T5> mapThird(Function<T3, R> mapper) {
        return new Quintuple<>(first, second, mapper.apply(third), fourth, fifth);
    }

    public <R> Quintuple<T1, T2, T3, R, T5> mapFourth(Function<T4, R> mapper) {
        return new Quintuple<>(first, second, third, mapper.apply(fourth), fifth);
    }

    public <R> Quintuple<T1, T2, T3, T4, R> mapFifth(Function<T5, R> mapper) {
        return new Quintuple<>(first, second, third, fourth, mapper.apply(fifth));
    }

    public <R1, R2, R3, R4, R5> Quintuple<R1, R2, R3, R4, R5> map(Function<T1, R1> firstMapper, Function<T2, R2> secondMapper, Function<T3, R3> thirdMapper, Function<T4, R4> fourthMapper, Function<T5, R5> fifthMapper) {
        return new Quintuple<>(firstMapper.apply(first), secondMapper.apply(second), thirdMapper.apply(third), fourthMapper.apply(fourth), fifthMapper.apply(fifth));
    }

    public List<?> toList() {
        return List.of(first, second, third, fourth, fifth);
    }

    public Set<?> toSet() {
        return Set.of(first, second, third, fourth, fifth);
    }

    public Stack<Object> toStack() {
        Stack<Object> stack = new Stack<>();
        stack.push(first);
        stack.push(second);
        stack.push(third);
        stack.push(fourth);
        stack.push(fifth);
        return stack;
    }

    public Object[] toArray() {
        return new Object[]{first, second, third, fourth, fifth};
    }
}
