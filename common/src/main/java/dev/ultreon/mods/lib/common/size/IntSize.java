package dev.ultreon.mods.lib.common.size;

import java.awt.*;

public final class IntSize {
    private final int width;
    private final int height;

    public IntSize(int width, int height) {
        if (width < 0) throw new IllegalArgumentException("Width is negative");
        if (height < 0) throw new IllegalArgumentException("Height is negative");
        this.width = width;
        this.height = height;

    }

    public IntSize(Dimension size) {
        this(size.width, size.height);
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public IntSize grown(int amount) {
        return new IntSize(Math.max(this.width + amount, 0), Math.max(this.height + amount, 0));
    }

    public IntSize shrunk(int amount) {
        return new IntSize(Math.max(this.width - amount, 0), Math.max(this.height - amount, 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        IntSize intSize = (IntSize) o;

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
