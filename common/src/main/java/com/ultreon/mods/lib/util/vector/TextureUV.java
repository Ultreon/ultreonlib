package com.ultreon.mods.lib.util.vector;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record TextureUV(double u, double v) {
    public static final Codec<TextureUV> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("u").forGetter(TextureUV::u),
            Codec.DOUBLE.fieldOf("v").forGetter(TextureUV::v)
    ).apply(instance, TextureUV::new));

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

    public double[] asArray() {
        return new double[]{u, v};
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", u, v);
    }
}
