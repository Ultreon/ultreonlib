package com.ultreon.mods.lib.collections.tables;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Tables are a 3-dimensional {@link Map}.
 *
 * @param <R> the row type.
 * @param <C> the column type.
 * @param <V> the value type.
 * @since 0.2.0
 * @author <a href="https://github.com/XyperCodee">XyperCode</a>
 */
public interface Table<R, C, V> {
    static <R, C, V> Table<R, C, V> copyOf(Table<R, C, V> table) {
        Set<Cell<R, C, V>> cells = Collections.unmodifiableSet(table.cellSet());

        return new AbstractTable<R, C, V>() {
            @Override
            public Set<Table.Cell<R, C, V>> cellSet() {
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
        V v = this.get(row, column); // get the old value
        if (v == null) {
            this.put(row, column, value);
            v = value;
        }
        return v;
    }

    default V putIfPresent(R row, C column, V value) {
        V v = this.get(row, column); // get the old value
        if (v != null) {
            this.put(row, column, value);
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
        return this.contains(index.getRow(), index.getColumn());
    }

    default boolean containsRow(R row) {
        return this.rowSet().contains(row);
    }

    default boolean containsColumn(C column) {
        return this.columnSet().contains(column);
    }

    default boolean containsValue(V value) {
        return this.values().contains(value);
    }

    boolean containsAll(Table<R, C, V> table);

    Set<Cell<R, C, V>> cellSet();

    Map<Index<R, C>, V> toMap();

    default V get(Index<R, C> index) {
        return this.get(index.getRow(), index.getColumn());
    }

    Set<C> columnSet(R row);

    Set<R> rowSet(C column);

    Set<Index<R, C>> indexSet();

    interface Index<R, C> {
        R getRow();

        C getColumn();
    }

    interface Cell<R, C, V> extends Index<R, C> {
        @Override
        R getRow();

        @Override
        C getColumn();

        V getValue();
    }
}
