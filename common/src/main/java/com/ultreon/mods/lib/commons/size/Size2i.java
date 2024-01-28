package com.ultreon.mods.lib.commons.size;

import java.awt.*;

public final class Size2i {
    private final int width;
    private final int height;

    public Size2i(int width, int height) {
        if (width < 0) throw new IllegalArgumentException("Width is negative");
        if (height < 0) throw new IllegalArgumentException("Height is negative");
        this.width = width;
        this.height = height;

    }

    public Size2i(Dimension size) {
        this(size.width, size.height);
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public Size2i grown(int amount) {
        return new Size2i(Math.max(this.width + amount, 0), Math.max(this.height + amount, 0));
    }

    public Size2i shrunk(int amount) {
        return new Size2i(Math.max(this.width - amount, 0), Math.max(this.height - amount, 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Size2i intSize = (Size2i) o;

        if (this.width != intSize.width) return false;
        return this.height == intSize.height;
    }

    @Override
    public int hashCode() {
        int result = this.width;
        result = 31 * result + this.height;
        return result;
    }

    @Override
    public String toString() {
        return this.width + "x" + this.height;
    }
}
