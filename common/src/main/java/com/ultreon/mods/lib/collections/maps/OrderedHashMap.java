package com.ultreon.mods.lib.collections.maps;

import org.jetbrains.annotations.NotNull;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.*;

@SuppressWarnings({"unused", "JavaDoc"})
public class OrderedHashMap<K, V> implements Map<K, V>, Cloneable, Externalizable {
    private static final int KEY = 0;

    private static final int VALUE = 1;

    private static final int ENTRY = 2;

    private static final int REMOVED_MASK = 0x80000000;

    private static final long serialVersionUID = 964071416243835645L;

    private Entry<K, V> sentinel;

    private HashMap<K, Entry<K, V>> entries;

    private transient long modCount = 0;

    public OrderedHashMap() {
        this.sentinel = createSentinel();
        this.entries = new HashMap<>();
    }

    public OrderedHashMap(int initialSize) {
        this.sentinel = createSentinel();
        this.entries = new HashMap<>(initialSize);
    }

    public OrderedHashMap(int initialSize, float loadFactor) {
        this.sentinel = createSentinel();
        this.entries = new HashMap<>(initialSize, loadFactor);
    }

    public OrderedHashMap(Map<K, V> m) {
        this();
        this.putAll(m);
    }

    private static <K, V> Entry<K, V> createSentinel() {
        Entry<K, V> s = new Entry<>(null, null);
        s.prev = s;
        s.next = s;
        return s;
    }

    private void removeEntry(Entry<K, V> entry) {
        entry.next.prev = entry.prev;
        entry.prev.next = entry.next;
    }

    private void insertEntry(Entry<K, V> entry) {
        entry.next = this.sentinel;
        entry.prev = this.sentinel.prev;
        this.sentinel.prev.next = entry;
        this.sentinel.prev = entry;
    }

    public int size() {
        return this.entries.size();
    }

    public boolean isEmpty() {
        return this.sentinel.next == this.sentinel;
    }

    public boolean containsKey(Object key) {
        return this.entries.containsKey(key);
    }

    public boolean containsValue(Object value) {
        if (value == null) {
            for (Entry<K, V> pos = this.sentinel.next; pos != this.sentinel; pos = pos.next) {
                if (pos.getValue() == null) return true;
            }
        } else {
            for (Entry<K, V> pos = this.sentinel.next; pos != this.sentinel; pos = pos.next) {
                if (value.equals(pos.getValue())) return true;
            }
        }
        return false;
    }

    public V get(Object o) {
        Entry<K, V> entry = this.entries.get(o);

        if (entry == null) return null;
        return entry.getValue();
    }

    public Map.Entry<K, V> getFirst() {
        return (this.isEmpty()) ? null : this.sentinel.next;
    }

    public Object getFirstKey() {
        return this.sentinel.next.getKey();
    }

    public Object getFirstValue() {
        return this.sentinel.next.getValue();
    }

    public Map.Entry<K, V> getLast() {
        return (this.isEmpty()) ? null : this.sentinel.prev;
    }

    public Object getLastKey() {
        return this.sentinel.prev.getKey();
    }

    public Object getLastValue() {
        return this.sentinel.prev.getValue();
    }

    public V put(K key, V value) {
        this.modCount++;
        V oldValue = null;

        Entry<K, V> e = this.entries.get(key);

        if (e != null) {
            this.removeEntry(e);
            oldValue = e.setValue(value);
        } else {
            e = new Entry<>(key, value);
            this.entries.put(key, e);
        }

        this.insertEntry(e);
        return oldValue;
    }

    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        Entry<K, V> e = this.removeImpl((K) key);
        return (e == null) ? null : e.getValue();
    }

    private Entry<K, V> removeImpl(K key) {
        Entry<K, V> e = this.entries.remove(key);

        if (e == null) return null;

        this.modCount++;
        this.removeEntry(e);
        return e;
    }

    public void putAll(Map<? extends K, ? extends V> t) {
        for (Map.Entry<? extends K, ? extends V> entry : t.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    public void clear() {
        this.modCount++;

        this.entries.clear();

        this.sentinel.next = this.sentinel;
        this.sentinel.prev = this.sentinel;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Map)) return false;
        return this.entrySet().equals(((Map<?, ?>) obj).entrySet());
    }

    public int hashCode() {
        return this.entrySet().hashCode();
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append('[');
        for (Entry<K, V> pos = this.sentinel.next; pos != this.sentinel; pos = pos.next) {
            buf.append(pos.getKey());
            buf.append('=');
            buf.append(pos.getValue());
            if (pos.next != this.sentinel) {
                buf.append(',');
            }
        }
        buf.append(']');
        return buf.toString();
    }

    public @NotNull Set<K> keySet() {
        return new AbstractSet<K>() {
            public @NotNull Iterator<K> iterator() {
                return new OrderedIterator<>(KEY);
            }

            @SuppressWarnings("unchecked")
            public boolean remove(Object o) {
                Entry<K, V> e = OrderedHashMap.this.removeImpl((K) o);
                return (e != null);
            }

            public void clear() {
                OrderedHashMap.this.clear();
            }

            public int size() {
                return OrderedHashMap.this.size();
            }

            public boolean isEmpty() {
                return OrderedHashMap.this.isEmpty();
            }

            public boolean contains(Object o) {
                return OrderedHashMap.this.containsKey(o);
            }
        };
    }
    public @NotNull Collection<V> values() {
        return new AbstractCollection<V>() {
            public @NotNull Iterator<V> iterator() {
                return new OrderedIterator<>(VALUE);
            }

            public boolean remove(Object value) {
                if (value == null) {
                    for (Entry<K, V> pos = OrderedHashMap.this.sentinel.next; pos != OrderedHashMap.this.sentinel; pos = pos.next) {
                        if (pos.getValue() == null) {
                            OrderedHashMap.this.removeImpl(pos.getKey());
                            return true;
                        }
                    }
                } else {
                    for (Entry<K, V> pos = OrderedHashMap.this.sentinel.next; pos != OrderedHashMap.this.sentinel; pos = pos.next) {
                        if (value.equals(pos.getValue())) {
                            OrderedHashMap.this.removeImpl(pos.getKey());
                            return true;
                        }
                    }
                }
                return false;
            }

            public void clear() {
                OrderedHashMap.this.clear();
            }

            public int size() {
                return OrderedHashMap.this.size();
            }

            public boolean isEmpty() {
                return OrderedHashMap.this.isEmpty();
            }

            public boolean contains(Object o) {
                return OrderedHashMap.this.containsValue(o);
            }
        };
    }

    public @NotNull Set<Map.Entry<K, V>> entrySet() {
        return new AbstractSet<Map.Entry<K, V>>() {
            private Entry<K, V> findEntry(Map.Entry<K, V> o) {
                if (o == null) {
                    return null;
                }
                Entry<K, V> entry = OrderedHashMap.this.entries.get(o.getKey());
                if ((entry != null) && entry.equals(o)) {
                    return entry;
                } else {
                    return null;
                }
            }

            public @NotNull Iterator<Map.Entry<K, V>> iterator() {
                return new OrderedIterator<>(ENTRY);
            }

            @SuppressWarnings("unchecked")
            @Override
            public boolean remove(Object o) {
                if (!(o instanceof Map.Entry)) {
                    throw new ClassCastException("Cannot cast " + o.getClass().getSimpleName() + " to Map.Entry");
                }

                Map.Entry<K, V> e = this.findEntry((Map.Entry<K, V>) o);
                if (e == null) {
                    return false;
                }
                return OrderedHashMap.this.removeImpl(e.getKey()) != null;
            }

            public void clear() {
                OrderedHashMap.this.clear();
            }

            public int size() {
                return OrderedHashMap.this.size();
            }

            public boolean isEmpty() {
                return OrderedHashMap.this.isEmpty();
            }

            @SuppressWarnings("unchecked")
            @Override
            public boolean contains(Object o) {
                if (!(o instanceof Map.Entry)) {
                    throw new ClassCastException("Cannot cast " + o.getClass().getSimpleName() + " to Map.Entry");
                }

                return this.findEntry((Map.Entry<K, V>) o) != null;
            }
        };
    }

    @SuppressWarnings("unchecked")
    public OrderedHashMap<K, V> clone() throws CloneNotSupportedException {
        OrderedHashMap<K, V> map = (OrderedHashMap<K, V>) super.clone();

        map.sentinel = createSentinel();
        map.entries = new HashMap<>();
        map.putAll(this);

        return map;
    }

    private Map.Entry<K, V> getEntry(int index) {
        Entry<K, V> pos = this.sentinel;
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index + " < 0");
        }

        int i = -1;
        while ((i < (index - 1)) && (pos.next != this.sentinel)) {
            i++;
            pos = pos.next;
        }

        if (pos.next == this.sentinel) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + (i + 1));
        }
        return pos.next;
    }

    public Object get(int index) {
        return this.getEntry(index).getKey();
    }

    public Object getValue(int index) {
        return this.getEntry(index).getValue();
    }

    public int indexOf(K key) {
        Entry<K, V> e = this.entries.get(key);
        int pos = 0;
        while (e.prev != this.sentinel) {
            pos++;
            e = e.prev;
        }
        return pos;
    }

    public Iterator<K> iterator() {
        return this.keySet().iterator();
    }

    public int lastIndexOf(K key) {
        return this.indexOf(key);
    }

    public List<K> sequence() {
        List<K> l = new ArrayList<>(this.size());
        l.addAll(this.keySet());
        return Collections.unmodifiableList(l);
    }

    public Object remove(int index) {
        return this.remove(this.get(index));
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException, ClassCastException {
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked") K key = (K) in.readObject();
            @SuppressWarnings("unchecked") V value = (V) in.readObject();
            this.put(key, value);
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.size());
        for (Entry<K, V> pos = this.sentinel.next; pos != this.sentinel; pos = pos.next) {
            out.writeObject(pos.getKey());
            out.writeObject(pos.getValue());
        }
    }

    private static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;

        private V value;

        Entry<K, V> next = null;

        Entry<K, V> prev = null;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public int hashCode() {
            return (((this.getKey() == null) ? 0 : this.getKey().hashCode()) ^
                    ((this.getValue() == null) ? 0 : this.getValue().hashCode()));
        }

        public boolean equals(Map.Entry<K, V> obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }

            return (((this.getKey() == null) ? (obj.getKey() == null) : this.getKey().equals(obj.getKey()))
                    && ((this.getValue() == null) ? (obj.getValue() == null) : this.getValue().equals(obj.getValue())));
        }

        public String toString() {
            return "[" + this.getKey() + '=' + this.getValue() + ']';
        }
    }

    private class OrderedIterator<T> implements Iterator<T> {
        private int returnType;

        private Entry<K, V> pos = OrderedHashMap.this.sentinel;

        private transient long expectedModCount = OrderedHashMap.this.modCount;

        public OrderedIterator(int returnType) {
            this.returnType = returnType | REMOVED_MASK;
        }

        public boolean hasNext() {
            return this.pos.next != OrderedHashMap.this.sentinel;
        }

        @SuppressWarnings("unchecked")
        public T next() {
            if (OrderedHashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (this.pos.next == OrderedHashMap.this.sentinel) {
                throw new NoSuchElementException();
            }

            this.returnType = this.returnType & ~REMOVED_MASK;
            this.pos = this.pos.next;

            switch (this.returnType) {
                case KEY:
                    return (T) this.pos.getKey();
                case VALUE:
                    return (T) this.pos.getValue();
                case ENTRY:
                    return (T) this.pos;
                default:
                    throw new Error("bad iterator type: " + this.returnType);
            }
        }

        public void remove() {
            if ((this.returnType & REMOVED_MASK) != 0) {
                throw new IllegalStateException("remove() must follow next()");
            }
            if (OrderedHashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }

            OrderedHashMap.this.removeImpl(this.pos.getKey());
            this.expectedModCount++;
            this.returnType = this.returnType | REMOVED_MASK;
        }
    }
}
