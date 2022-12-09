package com.ultreon.commons.collection.table;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Table<R, C, V> {
    static <R, C, V> Table<R, C, V> copyOf(Table<R, C, V> table) {
        Set<Cell<R, C, V>> cells = Set.copyOf(table.cellSet());

        return new AbstractTable<>() {
            @Override
            public Set<Cell<R, C, V>> cellSet() {
                return cells;
            }
        };
    }

    int columnSize();

    int rowSize();

    V get(R row, C column);

    V getOrDefault(R row, C column, V defaultValue);

    V put(R row, C column, V value);

    default V putIfAbsent(R row, C column, V value) {
        V v = get(row, column); // get the old value
        if (v == null) {
            put(row, column, value);
            v = value;
        }
        return v;
    }

    default V putIfPresent(R row, C column, V value) {
        V v = get(row, column); // get the old value
        if (v != null) {
            put(row, column, value);
            v = value;
        }
        return v;
    }

    Map<C, V> row(R row);

    Map<R, V> column(C column);

    Set<R> rowSet();

    Set<C> columnSet();

    Collection<V> values();

    Map<R, Map<C, V>> toRowMap();

    Map<C, Map<R, V>> toColumnMap();

    V remove(R row, C column);

    void clear();

    boolean isEmpty();

    boolean contains(R row, C column);

    default boolean contains(Index<R, C> index) {
        return contains(index.getRow(), index.getColumn());
    }

    default boolean containsRow(R row) {
        return rowSet().contains(row);
    }

    default boolean containsColumn(C column) {
        return columnSet().contains(column);
    }

    default boolean containsValue(V value) {
        return values().contains(value);
    }

    boolean containsAll(Table<R, C, V> table);

    Set<Cell<R, C, V>> cellSet();

    Map<Index<R, C>, V> toMap();

    default V get(Index<R, C> index) {
        return get(index.getRow(), index.getColumn());
    }

    Set<C> columnSet(R row);

    Set<R> rowSet(C column);

    Set<Index<R, C>> indexSet();

    interface Index<R, C> {
        R getRow();

        C getColumn();
    }

    interface Cell<R, C, V> extends Index<R, C> {
        R getRow();

        C getColumn();

        V getValue();
    }
}
