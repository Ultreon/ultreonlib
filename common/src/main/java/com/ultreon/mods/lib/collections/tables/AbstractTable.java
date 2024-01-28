package com.ultreon.mods.lib.collections.tables;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * An abstract implementation of {@link Table}.
 *
 * @param <R> the row type.
 * @param <C> the column type.
 * @param <V> the value type.
 * @since 0.2.0
 * @author <a href="https://github.com/XyperCodee">XyperCode</a>
 */
public abstract class AbstractTable<R, C, V> implements Table<R, C, V> {
    public static <R, C> Index<R, C> index(R row, C column) {
        return new SimpleIndex<>(row, column);
    }

    public static <R, C, V> Cell<R, C, V> cell(R row, C column, V value) {
        return new SimpleCell<>(row, column, value);
    }

    @Override
    public int columnSize() {
        return this.columnSet().size();
    }

    @Override
    public int rowSize() {
        return this.rowSet().size();
    }

    @Override
    public V put(R row, C column, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V getOrDefault(R row, C column, V defaultValue) {
        V value = this.get(row, column);
        return value != null ? value : defaultValue;
    }

    @Nullable
    @Override
    public V remove(R row, C column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public V get(Index<R, C> index) {
        return this.get(index.getRow(), index.getColumn());
    }

    @Override
    public V get(R row, C column) {
        return this.row(row).get(column);
    }

    @NotNull
    @Override
    public Map<C, V> row(R row) {
        Map<C, V> map = new HashMap<>();
        for (Cell<R, C, V> cell : this.cellSet()) {
            if (cell.getRow().equals(row)) {
                map.put(cell.getColumn(), cell.getValue());
            }
        }
        return Collections.unmodifiableMap(map);
    }

    @NotNull
    @Override
    public Map<R, V> column(C column) {
        Map<R, V> map = new HashMap<>();
        for (Cell<R, C, V> cell : this.cellSet()) {
            if (cell.getColumn().equals(column)) {
                map.put(cell.getRow(), cell.getValue());
            }
        }
        return Collections.unmodifiableMap(map);
    }

    @NotNull
    @Override
    public Map<R, Map<C, V>> toRowMap() {
        Map<R, Map<C, V>> map = new HashMap<>();
        for (R row : this.rowSet()) {
            map.put(row, this.row(row));
        }
        return Collections.unmodifiableMap(map);
    }

    @NotNull
    @Override
    public Map<C, Map<R, V>> toColumnMap() {
        Map<C, Map<R, V>> map = new HashMap<>();
        for (C column : this.columnSet()) {
            map.put(column, this.column(column));
        }
        return Collections.unmodifiableMap(map);
    }

    @Override
    public Set<R> rowSet() {
        Set<R> set = new HashSet<>();
        for (Cell<R, C, V> cell : this.cellSet()) {
            set.add(cell.getRow());
        }
        return Collections.unmodifiableSet(set);
    }

    @Override
    public Set<R> rowSet(C column) {
        Set<R> set = new HashSet<>();
        for (Cell<R, C, V> cell : this.cellSet()) {
            if (cell.getColumn().equals(column)) {
                set.add(cell.getRow());
            }
        }
        return Collections.unmodifiableSet(set);
    }

    @Override
    public Set<C> columnSet(R row) {
        Set<C> set = new HashSet<>();
        for (Cell<R, C, V> cell : this.cellSet()) {
            if (cell.getRow().equals(row)) {
                set.add(cell.getColumn());
            }
        }
        return Collections.unmodifiableSet(set);
    }

    @Override
    public Set<C> columnSet() {
        Set<C> set = new HashSet<>();
        for (Cell<R, C, V> cell : this.cellSet()) {
            set.add(cell.getColumn());
        }
        return Collections.unmodifiableSet(set);
    }

    @Override
    public Set<Index<R, C>> indexSet() {
        Set<Index<R, C>> set = new HashSet<>();
        for (Cell<R, C, V> cell : this.cellSet()) {
            set.add(new SimpleIndex<>(cell.getRow(), cell.getColumn()));
        }
        return Collections.unmodifiableSet(set);
    }

    @Override
    public Collection<V> values() {
        Set<V> set = new HashSet<>();
        for (Cell<R, C, V> cell : this.cellSet()) {
            set.add(cell.getValue());
        }
        return Collections.unmodifiableSet(set);
    }

    @Override
    public boolean contains(R row, C column) {
        return this.contains(index(row, column));
    }

    @Override
    public boolean contains(Index<R, C> index) {
        return this.indexSet().contains(index);
    }

    @Override
    public boolean isEmpty() {
        return this.indexSet().isEmpty();
    }

    @Override
    public boolean containsRow(R row) {
        return this.rowSet().contains(row);
    }

    @Override
    public boolean containsColumn(C column) {
        return this.columnSet().contains(column);
    }

    @Override
    public boolean containsAll(Table<R, C, V> table) {
        return table.cellSet().stream().allMatch(this::contains);
    }

    @Override
    public Map<Index<R, C>, V> toMap() {
        Map<Index<R, C>, V> map = new HashMap<>();
        for (Cell<R, C, V> cell : this.cellSet()) {
            map.put(new SimpleIndex<>(cell.getRow(), cell.getColumn()), cell.getValue());
        }
        return Collections.unmodifiableMap(map);
    }

    public static class SimpleIndex<R, C> implements Index<R, C> {
        private final R row;
        private final C column;

        public SimpleIndex(R row, C column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public int hashCode() {
            return this.row.hashCode() ^ this.column.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (this.getClass() != obj.getClass())
                return false;

            final SimpleIndex<?, ?> other = (SimpleIndex<?, ?>) obj;
            return Objects.equals(this.row, other.row) && Objects.equals(this.column, other.column);
        }

        @Override
        public String toString() {
            return "(" + this.row + ", " + this.column + ")";
        }

        @Override
        public R getRow() {
            return this.row;
        }

        @Override
        public C getColumn() {
            return this.column;
        }
    }

    public static class SimpleCell<R, C, V> implements Cell<R, C, V> {
        private final R row;
        private final C column;
        private final V value;

        public SimpleCell(R row, C column, V value) {
            this.row = row;
            this.column = column;
            this.value = value;
        }

        @Override
        public int hashCode() {
            return this.row.hashCode() ^ this.column.hashCode() ^ this.value.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;

            if (this.getClass() != obj.getClass())
                return false;

            final SimpleCell<?, ?, ?> other = (SimpleCell<?, ?, ?>) obj;
            return Objects.equals(this.row, other.row)
                    && Objects.equals(this.column, other.column)
                    && Objects.equals(this.value, other.value);
        }

        @Override
        public String toString() {
            return "(" + this.row + ", " + this.column + ")=" + this.value;
        }

        @Override
        public R getRow() {
            return this.row;
        }

        @Override
        public C getColumn() {
            return this.column;
        }

        @Override
        public V getValue() {
            return this.value;
        }
    }
}
