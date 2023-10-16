package com.ultreon.mods.lib.client.gui.v2;

import com.ultreon.libs.commons.v0.Color;
import com.ultreon.mods.lib.UltreonLib;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.resources.Resource;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public class McDesktopWindow extends McWindow {
    private final McImage wallpaper;
    private Color backgroundColor;

    public McDesktopWindow(McDesktopApplication application) {
        super(application, 0, 0, 0, 0, Component.empty());
        this.setAbsolute(true);

        this.wallpaper = new McImage(0, 0, 0, 0);
        this.setBackgroundColor(Color.black);
        this.add(this.wallpaper);
        this.addOnClosingListener(() -> false);

        assert this.minecraft != null;
        Resource resource = this.minecraft.getResourceManager().getResource(UltreonLib.res("textures/wallpaper.png")).orElseThrow();
        Path path = Path.of("./wallpaper.png");
        if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
            try (InputStream stream = resource.open()) {
                Files.copy(stream, path);
            } catch (Exception e) {
                UltreonLib.LOGGER.warn("Failed to extract wallpaper file:", e);
            }
        }
        this.loadWallpaper(path);
    }

    @Override
    protected void renderInternal(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        this.setUndecorated(true);
        this.setX(0);
        this.setY(0);
        this.setWidth(this.getScreenWidth());
        this.setHeight(this.getScreenHeight());
        this.wallpaper.setX(0);
        this.wallpaper.setY(0);
        this.wallpaper.setWidth(getWidth());
        this.wallpaper.setHeight(getHeight());

        this.setBottomMost(true);

        super.renderInternal(gfx, mouseX, mouseY, partialTicks);
    }

    public void loadWallpaper(File file) {
        this.wallpaper.loadFrom(file);
        this.backgroundColor = null;
    }

    public void loadWallpaper(Path path) {
        this.wallpaper.loadFrom(path);
        this.backgroundColor = null;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }
}
