package com.ultreon.mods.lib.commons.size;

public final class Size2d {
    private final double width;
    private final double height;

     public Size2d(double width, double height) {
        if (width < 0) throw new IllegalArgumentException("Width is negative");
        if (height < 0) throw new IllegalArgumentException("Height is negative");
        this.width = width;
        this.height = height;
    }

    public double width() {
        return this.width;
    }

    public double height() {
        return this.height;
    }

    public Size2d grown(double amount) {
        return new Size2d(Math.max(this.width + amount, 0), Math.max(this.height + amount, 0));
    }

    public Size2d shrunk(double amount) {
        return new Size2d(Math.max(this.width - amount, 0), Math.max(this.height - amount, 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Size2d that = (Size2d) o;

        if (Double.compare(that.width, this.width) != 0) return false;
        return Double.compare(that.height, this.height) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(this.width);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.height);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return this.width + "x" + this.height;
    }
}
