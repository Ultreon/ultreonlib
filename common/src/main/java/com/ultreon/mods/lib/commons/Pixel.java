package com.ultreon.mods.lib.commons;

import java.awt.*;
import java.io.Serializable;

@SuppressWarnings("unused")
public class Pixel implements Serializable {
    private final com.ultreon.mods.lib.commons.Color color;
    private final Point pos;

    public Pixel(int x, int y, com.ultreon.mods.lib.commons.Color color) {
        this.pos = new Point(x, y);
        this.color = color;
    }

    public Pixel(Point pos, com.ultreon.mods.lib.commons.Color color) {
        this.pos = pos;
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public Point getPos() {
        return this.pos;
    }

    public int getX() {
        return this.pos.x;
    }

    public int getY() {
        return this.pos.y;
    }
}
