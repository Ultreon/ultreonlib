package com.ultreon.mods.lib.client.gui.v2;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

public class McImage extends McResImage {
    private ImageLoader loader;
    private ResourceLocation resource;
    private final List<ClickCallback> onClick = new ArrayList<>();

    public McImage(int x, int y, int width, int height) {
        this(x, y, width, height, Component.empty());
    }

    public McImage(int x, int y, int width, int height, Component altText) {
        super(x, y, width, height, 0, 0, width, height, width, height, altText);
    }

    public final Component getAltText() {
        return getMessage();
    }

    public final void setAltText(Component message) {
        setMessage(message);
    }

    public void loadFrom(URL url) {
        @NotNull String protocol = url.getProtocol();
        switch (protocol) {
            case "file" -> {
                try {
                    this.loader = new FileImageLoader(new File(url.toURI()));
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
            case "http", "https" -> this.loader = new URLImageLoader(url);
            default -> throw new UnsupportedOperationException("Protocol not supported: " + protocol);
        }
        refresh();
    }

    public void loadFrom(File file) {
        this.loader = new FileImageLoader(file);
        refresh();
    }

    public void loadFrom(InputStream stream) {
        this.loader = new InputStreamImageLoader(stream);
        refresh();
    }

    public void loadFrom(byte[] bytes) {
        this.loader = new ByteArrayImageLoader(bytes);
        refresh();
    }

    public void loadFrom(ByteArrayTag nbt) {
        this.loader = new ByteArrayImageLoader(nbt.getAsByteArray());
        refresh();
    }

    public void loadFrom(FriendlyByteBuf buf) {
        this.loader = new ByteArrayImageLoader(buf.readByteArray());
        refresh();
    }

    public void loadFrom(FriendlyByteBuf buf, int maxLength) {
        this.loader = new ByteArrayImageLoader(buf.readByteArray(maxLength));
        refresh();
    }

    public void loadFrom(ByteBuffer buffer) {
        this.loader = new ByteBufferImageLoader(buffer);
        refresh();
    }

    private void refresh() {
        this.loader.load();
    }

    public ResourceLocation getResource() {
        return resource;
    }

    public void setResource(ResourceLocation resource, int imageWidth, int imageHeight) {
        this.resource = resource;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        ResourceLocation resource = getResource();
        fill(poseStack, getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0xff555555);
        fill(poseStack, getX() + 1, getY() + 1, getX() + getWidth() - 1, getY() + getHeight() - 1, 0xff333333);
        if (resource != null) {
            RenderSystem.setShaderTexture(0, resource);
            blit(poseStack, getX(), getY(), getWidth(), getHeight(), 0, 0, textureWidth(), textureHeight(), textureWidth(), textureHeight());
        } else if (loader != null && loader.error != null) {
            drawCenteredStringWithoutShadow(poseStack, font, Component.literal(loader.error.getLocalizedMessage()), getX() + getWidth() / 2, getY() + getHeight() / 2, 0xffffdddd);
        } else {
            drawLoadingIcon(poseStack, getWidth() / 2, getHeight() / 2);
        }
    }

    public static void drawLoadingIcon(PoseStack poseStack, int x, int y) {
        var timeWrap = (int)(System.currentTimeMillis() % 1500);
        var frame = timeWrap / 500;

        switch (frame) {
            case 0 -> {
                fill(poseStack, x - 2 - 6 - 1, y - 2 - 1, x + 1 - 6 + 1, y + 1 + 1, 0xff666666);
                fill(poseStack, x - 2, y - 2, x + 1, y + 1, 0xff555555);
                fill(poseStack, x - 2 + 6, y - 2, x + 1 + 6, y + 1, 0xff555555);
            }
            case 1 -> {
                fill(poseStack, x - 2 - 6, y - 2, x + 1 - 6, y + 1, 0xff555555);
                fill(poseStack, x - 2 - 1, y - 2 - 1, x + 1 + 1, y + 1 + 1, 0xff666666);
                fill(poseStack, x - 2 + 6, y - 2, x + 1 + 6, y + 1, 0xff555555);
            }
            case 2 -> {
                fill(poseStack, x - 2 - 6, y - 2, x + 1 - 6, y + 1, 0xff555555);
                fill(poseStack, x - 2, y - 2, x + 1, y + 1, 0xff555555);
                fill(poseStack, x - 2 + 6 - 1, y - 2 - 1, x + 1 + 6 + 1, y + 1 + 1, 0xff666666);
            }
        }
    }

    @Override
    public void onLeftClick(int clicks) {
        super.onLeftClick(clicks);
        for (var callback : onClick) {
            callback.onClick(this, clicks);
        }
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return this.active && this.visible && mouseX >= (double)this.getX() && mouseY >= (double)this.getY() && mouseX < (double)(this.getX() + this.getWidth()) && mouseY < (double)(this.getY() + this.getHeight());
    }


    public void addClickHandler(ClickCallback onClick) {
        this.onClick.add(onClick);
    }

    public void removeClickHandler(ClickCallback onClick) {
        this.onClick.remove(onClick);
    }

    public abstract class ImageLoader {
        private boolean loaded = false;
        private IOException error = null;

        protected abstract void doLoad() throws IOException;

        public final void load() {
            if (this.loaded) {
                return;
            }
            var thread = new Thread(() -> {
                try {
                    doLoad();
                } catch (IOException e) {
                    e.printStackTrace();
                    fail(e);
                }
            }, "MC-ImageLoader " + getClass().getSimpleName());
            thread.start();
        }

        protected final void register(NativeImage image, Consumer<ResourceLocation> callback) {
            var minecraft = Minecraft.getInstance();
            var textureManager = minecraft.getTextureManager();
            minecraft.doRunTask(() -> {
                var id = new UUID(System.nanoTime(), new Random().nextInt() ^ image.hashCode());
                var location = textureManager.register("ultreonlib_dynamic/%s".formatted(id).replaceAll("-", ""), new DynamicTexture(image));
                callback.accept(location);
            });
        }

        protected final void done(ResourceLocation res) {
            McImage.this.resource = res;
            this.loaded = true;
        }

        protected final void fail(IOException exception) {
            McImage.this.resource = new ResourceLocation("minecraft:");
            this.error = exception;
            this.loaded = true;
        }

        public boolean isLoaded() {
            return loaded;
        }
    }

    private class FileImageLoader extends ImageLoader {
        private final File file;

        public FileImageLoader(File file) {
            this.file = file;
        }

        protected final void doLoad() throws IOException {
            NativeImage image;
            try (InputStream stream = new FileInputStream(file)) {
                image = NativeImage.read(stream);
            }
            register(image, res -> {
                McImage.this.textureWidth = image.getWidth();
                McImage.this.textureHeight = image.getHeight();
                done(res);
            });
        }
    }

    private class InputStreamImageLoader extends ImageLoader {
        private final InputStream stream;

        public InputStreamImageLoader(InputStream stream) {
            this.stream = stream;
        }

        protected final void doLoad() throws IOException {
            NativeImage image;
            try (stream) {
                image = NativeImage.read(this.stream);
            }
            register(image, res -> {
                McImage.this.textureWidth = image.getWidth();
                McImage.this.textureHeight = image.getHeight();
                done(res);
            });
        }
    }

    private class ByteArrayImageLoader extends ImageLoader {
        private final byte[] bytes;

        public ByteArrayImageLoader(byte[] bytes) {
            this.bytes = bytes;
        }

        protected final void doLoad() throws IOException {
            NativeImage image;
            try (InputStream stream = new ByteArrayInputStream(this.bytes)) {
                image = NativeImage.read(stream);
            }
            register(image, res -> {
                McImage.this.textureWidth = image.getWidth();
                McImage.this.textureHeight = image.getHeight();
                done(res);
            });
        }
    }

    private class ByteBufferImageLoader extends ImageLoader {

        private final ByteBuffer buffer;

        public ByteBufferImageLoader(ByteBuffer buffer) {
            this.buffer = buffer;
        }

        protected final void doLoad() throws IOException {
            var image = NativeImage.read(this.buffer);
            register(image, res -> {
                McImage.this.textureWidth = image.getWidth();
                McImage.this.textureHeight = image.getHeight();
                done(res);
            });
        }
    }

    private class URLImageLoader extends ImageLoader {
        private final URL url;

        public URLImageLoader(URL url) {
            this.url = url;
        }

        protected final void doLoad() throws IOException {
            NativeImage image;
            try (var stream = url.openStream()) {
                image = NativeImage.read(stream);
            }
            register(image, res -> {
                McImage.this.textureWidth = image.getWidth();
                McImage.this.textureHeight = image.getHeight();
                done(res);
            });
        }
    }

    @FunctionalInterface
    public interface ClickCallback {
        void onClick(McImage image, int clicks);
    }
}