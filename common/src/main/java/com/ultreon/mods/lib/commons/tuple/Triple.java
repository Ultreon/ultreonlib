package com.ultreon.mods.lib.commons.tuple;

import java.util.Objects;

/**
 * This is an object for having 2 values / objects inside one object, or in other words having a pair create objects.
 *
 * @param <T1> first object,
 * @param <T2> second object.
 * @author Qboi
 */
public class Triple<T1, T2, T3> implements Cloneable {
    private T1 first;
    private T2 second;
    private T3 third;

    public Triple(T1 first, T2 second, T3 third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T1 getFirst() {
        return this.first;
    }

    public void setFirst(T1 first) {
        this.first = first;
    }

    public T2 getSecond() {
        return this.second;
    }

    public void setSecond(T2 second) {
        this.second = second;
    }

    public T3 getThird() {
        return this.third;
    }

    public void setThird(T3 third) {
        this.third = third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Triple<?, ?, ?> triple = (Triple<?, ?, ?>) o;
        return Objects.equals(this.getFirst(), triple.getFirst()) && Objects.equals(this.getSecond(), triple.getSecond()) && Objects.equals(this.getThird(), triple.getThird());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getFirst(), this.getSecond(), this.getThird());
    }

    @Override
    public String toString() {
        return "(" + this.first + ", " + this.second + ", " + this.third + ')';
    }

    @Override
    @SuppressWarnings("unchecked")
    public Triple<T1, T2, T3> clone() throws CloneNotSupportedException {
        return (Triple<T1, T2, T3>) super.clone();
    }
}
