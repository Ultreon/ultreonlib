package com.ultreon.mods.lib.functions;

/**
 * Represents a function that accepts three arguments and produces a result.
 *
 * @param <A> the first argument type
 * @param <B> the second argument type
 * @param <C> the third argument type
 * @param <R> the result type
 * @since 0.2.0
 * @author <a href="https://github.com/XyperCodee">XyperCode</a>
 */
public interface TriFunction<A, B, C, R> {
    R apply(A a, B b, C c);
}
