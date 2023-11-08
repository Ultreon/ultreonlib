package com.ultreon.mods.lib.util.vector;

public record TextureUV(double u, double v) {
    public int ui() {
        return (int) u;
    }

    public int vi() {
        return (int) v;
    }

    @Override
    public double u() {
        return u;
    }

    @Override
    public double v() {
        return v;
    }
}
