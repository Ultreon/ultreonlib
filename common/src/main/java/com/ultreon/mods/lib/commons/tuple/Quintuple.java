package com.ultreon.mods.lib.commons.tuple;

import java.util.Objects;

/**
 * This is an object for having 2 values / objects inside one object, or in other words having a pair create objects.
 *
 * @param <T1> first object,
 * @param <T2> second object.
 * @author Qboi
 */
public class Quintuple<T1, T2, T3, T4, T5> implements Cloneable {
    private T1 first;
    private T2 second;
    private T3 third;
    private T4 fourth;
    private T5 fifth;

    public Quintuple(T1 first, T2 second, T3 third, T4 fourth, T5 fifth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
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

    public T4 getFourth() {
        return this.fourth;
    }

    public void setFourth(T4 fourth) {
        this.fourth = fourth;
    }

    public T5 getFifth() {
        return this.fifth;
    }

    public void setFifth(T5 fifth) {
        this.fifth = fifth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Quintuple<?, ?, ?, ?, ?> quintuple = (Quintuple<?, ?, ?, ?, ?>) o;
        return Objects.equals(this.getFirst(), quintuple.getFirst()) && Objects.equals(this.getSecond(), quintuple.getSecond()) && Objects.equals(this.getThird(), quintuple.getThird()) && Objects.equals(this.getFourth(), quintuple.getFourth()) && Objects.equals(this.getFifth(), quintuple.getFifth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getFirst(), this.getSecond(), this.getThird(), this.getFourth(), this.getFifth());
    }

    @Override
    public String toString() {
        return "(" + this.first + ", " + this.second + ", " + this.third + ", " + this.fourth + ", " + this.fifth + ')';
    }

    @Override
    @SuppressWarnings("unchecked")
    public Quintuple<T1, T2, T3, T4, T5> clone() throws CloneNotSupportedException {
        return (Quintuple<T1, T2, T3, T4, T5>) super.clone();
    }
}
