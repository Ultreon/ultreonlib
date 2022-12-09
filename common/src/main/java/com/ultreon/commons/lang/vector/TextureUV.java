package com.ultreon.commons.lang.vector;

public record TextureUV(double u, double v) {
    public int ui() {
        return (int) u;
    }

    public int vi() {
        return (int) v;
    }

    public double u() {
        return u;
    }

    public double v() {
        return v;
    }
}
