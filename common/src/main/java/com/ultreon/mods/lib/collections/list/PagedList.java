package com.ultreon.mods.lib.collections.list;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Paged list, page 0 is the first page.
 * Page size 1 == 1 object, 5 == 5 objects.
 *
 * @author Qboi123
 * @param <T> the type of items in the list.
 */
public class PagedList<T> extends ArrayList<T> {
    private final int pageSize;

    public PagedList(int pageSize, int initialCapacity) {
        super(initialCapacity);
        this.pageSize = pageSize;
    }

    public PagedList(int pageSize) {
        super();
        this.pageSize = pageSize;
    }

    public PagedList(int pageSize, @NotNull Collection<? extends T> collection) {
        super(collection);
        this.pageSize = pageSize;
    }

    public PagedList(@NotNull PagedList<? extends T> paginatedList) {
        super(paginatedList);
        this.pageSize = paginatedList.pageSize;
    }

    public List<T> getFullPage(int page) {
        return super.subList((page * this.pageSize), (page * this.pageSize) + this.pageSize - 1);
    }

    /**
     * Get the object at the page and index.
     *
     * @see ArrayList#get(int)
     * @param page page number, 0 is first.
     * @param index index at that page.
     * @return the object at the page and index.
     */
    public T get(int page, int index) {
        if (index >= this.pageSize) {
            return null;
        }

        return super.get((page * this.pageSize) + index);
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getPageCount() {
        return (int) Math.ceil((double) this.size() / (double) this.pageSize);
    }

    public boolean isFullPage(int page) {
        return (page * this.pageSize) + this.pageSize > this.size();
    }

    public boolean isLastPage(int page) {
        return (page + 1) * this.pageSize > this.size();
    }

    public boolean isFirstPage(int page) {
        return page == 0;
    }

    public boolean hasNextPage(int page) {
        return (page + 1) * this.pageSize < this.size();
    }

    public boolean hasPreviousPage(int page) {
        return page > 0;
    }

    public Iterator<T> iterator(int page) {
        return super.listIterator((page * this.pageSize));
    }
}
