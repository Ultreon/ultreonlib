package dev.ultreon.mods.lib.common;

import org.joml.Vector2i;

import java.io.Serializable;

public record Pixel(Vector2i pos, Color color) implements Serializable {
    public Pixel(int x, int y, Color color) {
        this(new Vector2i(x, y), color);
    }
}
