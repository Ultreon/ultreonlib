package dev.ultreon.mods.lib.common.size;

public final class FloatSize {
    private final float width;
    private final float height;

    public FloatSize(float width, float height) {
        if (width < 0) throw new IllegalArgumentException("Width is negative");
        if (height < 0) throw new IllegalArgumentException("Height is negative");
        this.width = width;
        this.height = height;

    }

    public float width() {
        return this.width;
    }

    public float height() {
        return this.height;
    }

    public FloatSize grown(float amount) {
        return new FloatSize(Math.max(this.width + amount, 0), Math.max(this.height + amount, 0));
    }

    public FloatSize shrunk(float amount) {
        return new FloatSize(Math.max(this.width - amount, 0), Math.max(this.height - amount, 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        FloatSize floatSize = (FloatSize) o;

        if (Float.compare(floatSize.width, this.width) != 0) return false;
        return Float.compare(floatSize.height, this.height) == 0;
    }

    @Override
    public int hashCode() {
        int result = (this.width != 0.0f ? Float.floatToIntBits(this.width) : 0);
        result = 31 * result + (this.height != 0.0f ? Float.floatToIntBits(this.height) : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.width + "x" + this.height;
    }
}
