package com.ultreon.mods.lib.client.gui.v2;

import net.minecraft.network.chat.Component;

public class McResImage extends McComponent implements IResImage {
    protected int imageU;
    protected int imageV;
    protected int imageUWidth;
    protected int imageVHeight;
    protected int textureWidth;
    protected int textureHeight;

    public McResImage(int x, int y, int width, int height, int imageU, int imageV, int imageUWidth, int imageVHeight, int textureWidth, int textureHeight, Component altText) {
        super(x, y, width, height, altText);
        this.imageU = imageU;
        this.imageV = imageV;
        this.imageUWidth = imageUWidth;
        this.imageVHeight = imageVHeight;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public int imageU() {
        return imageU;
    }

    @Override
    public int imageV() {
        return imageV;
    }

    @Override
    public int imageUWidth() {
        return imageUWidth;
    }

    @Override
    public int imageVHeight() {
        return imageVHeight;
    }

    @Override
    public int textureWidth() {
        return textureWidth;
    }

    @Override
    public int textureHeight() {
        return textureHeight;
    }
}
