package com.ultreon.mods.lib.util;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.objects.Reference2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import net.minecraft.core.IdMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.UnaryOperator;

public class IdRegistry<T> implements Set<T>, List<T>, IdMap<T> {
    private final Reference2IntMap<T> value2id = new Reference2IntArrayMap<>();
    private final List<T> values = new ArrayList<>();


    @Override
    public int getId(@NotNull T value) {
        return value2id.getInt(value);
    }

    @Nullable
    @Override
    public T byId(int id) {
        return values.get(id);
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return values.contains(o);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }

    @NotNull
    @Override
    public Object @NotNull [] toArray() {
        return values.toArray();
    }

    @NotNull
    @Override
    @SafeVarargs
    public final <T1> T1 @NotNull [] toArray(@NotNull T1 @NotNull ... a) {
        return values.toArray(a);
    }

    @Override
    public boolean add(@NotNull T t) {
        Preconditions.checkNotNull(t, "t");

        synchronized (this) {
            if (values.contains(t)) {
                return false;
            }
            values.add(t);
            value2id.put(t, values.size());
            return true;
        }
    }

    @Override
    public boolean remove(@NotNull Object o) {
        Preconditions.checkNotNull(o, "o");

        synchronized (this) {
            boolean remove = values.remove(o);
            value2id.removeInt(o);
            return remove;
        }
    }

    public int pop(@NotNull Object o) {
        Preconditions.checkNotNull(o, "o");

        synchronized (this) {
            int id = value2id.getInt(o);
            values.remove(id);
            value2id.removeInt(o);
            return id;
        }
    }

    @SuppressWarnings("SlowListContainsAll")
    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        synchronized (this) {
            return values.containsAll(c);
        }
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        for (T t : c) {
            add(t);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
        for (T t : c) {
            add(index++, t);
        }
        return true;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        synchronized (this) {
            return values.removeAll(c);
        }
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        synchronized (this) {
            return values.retainAll(c);
        }
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        List.super.replaceAll(operator);
    }

    @Override
    public void sort(Comparator<? super T> c) {
        List.super.sort(c);
    }

    @Override
    public void clear() {
        synchronized (this) {
            values.clear();
            value2id.clear();
        }
    }

    @Override
    public Spliterator<T> spliterator() {
        return stream().spliterator();
    }

    @Override
    public T get(int index) {
        synchronized (this) {
            return values.get(index);
        }
    }

    @Override
    public T set(int id, T element) {
        synchronized (this) {
            T set = values.set(id, element);
            value2id.put(element, id);
            return set;
        }
    }

    @Override
    public void add(int index, T element) {
        values.add(index, element);
    }

    @Override
    public T remove(int index) {
        return values.remove(index);
    }

    @Override
    @Deprecated
    public int indexOf(Object o) {
        return values.indexOf(o);
    }

    @Override
    @Deprecated
    public int lastIndexOf(Object o) {
        return values.lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return values.listIterator();
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return values.listIterator(index);
    }

    @NotNull
    @Override
    @Deprecated
    public List<T> subList(int fromIndex, int toIndex) {
        return values.subList(fromIndex, toIndex);
    }
}
