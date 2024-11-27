package dev.ultreon.mods.lib.util;

import com.google.errorprone.annotations.CheckReturnValue;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Stack;

public class ScissorStack {
    public static Stack<Scissor> scissorStack = new Stack<>();

    @CheckReturnValue
    public static boolean pushScissor(int x, int y, int width, int height) {
        if (!scissorStack.isEmpty()) {
            var scissor = scissorStack.peek();
            x = Math.max(scissor.x, x);
            y = Math.max(scissor.y, y);
            width = x + width > scissor.x + scissor.width ? scissor.x + scissor.width - x : width;
            height = y + height > scissor.y + scissor.height ? scissor.y + scissor.height - y : height;
            if (width <= 0 || height <= 0) return false;
        } else {
            if (width <= 0 || height <= 0) return false;
            GlStateManager._enableScissorTest();
        }


        var mc = Minecraft.getInstance();
        var resolution = new ScaledResolution(mc);
        var scale = resolution.getScaleFactor();
        GlStateManager._scissorBox((int) (x * scale), (int) (mc.getWindow().getHeight() - y * scale - height * scale), (int) Math.max(0, width * scale), (int) Math.max(0, height * scale));
        scissorStack.push(new Scissor(x, y, width, height));

        return true;
    }

    @CheckReturnValue
    public static boolean pushScissorTranslated(PoseStack poseStack, int x, int y, int width, int height) {
        var translation = poseStack.last().pose().getTranslation(new Vector3f());
        x += (int) translation.x;
        y += (int) translation.y;

        if (!scissorStack.isEmpty()) {
            var scissor = scissorStack.peek();
            x = Math.max(scissor.x, x);
            y = Math.max(scissor.y, y);
            width = x + width > scissor.x + scissor.width ? scissor.x + scissor.width - x : width;
            height = y + height > scissor.y + scissor.height ? scissor.y + scissor.height - y : height;
            if (width <= 0 || height <= 0) return false;
        } else {
            if (width <= 0 || height <= 0) return false;
            GlStateManager._enableScissorTest();
        }


        var mc = Minecraft.getInstance();
        var resolution = new ScaledResolution(mc);
        var scale = resolution.getScaleFactor();
        GlStateManager._scissorBox((int) (x * scale), (int) (mc.getWindow().getHeight() - y * scale - height * scale), (int) Math.max(0, width * scale), (int) Math.max(0, height * scale));
        scissorStack.push(new Scissor(x, y, width, height));

        return true;
    }

    public static void popScissor() {
        if (!scissorStack.isEmpty()) {
            scissorStack.pop();
        }
        restoreScissor();
    }

    private static void restoreScissor() {
        if (!scissorStack.isEmpty()) {
            var scissor = scissorStack.peek();
            var mc = Minecraft.getInstance();
            var resolution = new ScaledResolution(mc);
            var scale = resolution.getScaleFactor();
            GlStateManager._scissorBox((int) (scissor.x * scale), (int) (mc.getWindow().getHeight() - scissor.y * scale - scissor.height * scale), (int) Math.max(0, scissor.width * scale), (int) Math.max(0, scissor.height * scale));
        } else {
            GlStateManager._disableScissorTest();
        }
    }

    public static boolean isScissorStackEmpty() {
        return scissorStack.isEmpty();
    }

    public static boolean clearScissorStack() {
        if (scissorStack.isEmpty()) return false;
        scissorStack.clear();
        return true;
    }

    /// @deprecated use [#getPixel(int,int)] instead.
    @Deprecated(forRemoval = true)
    public static Color getPixel(int x, int y) {
        var mc = Minecraft.getInstance();
        var resolution = new ScaledResolution(mc);
        var scale = resolution.getScaleFactor();
        var buffer = BufferUtils.createByteBuffer(3);
        RenderSystem.readPixels((int) (x * scale), (int) (mc.getWindow().getHeight() - y * scale - scale), 1, 1, GL11.GL_RGB, GL11.GL_BYTE, buffer);
        return new Color(Math.min(255, buffer.get(0) % 256*2), Math.min(255, buffer.get(1) % 256*2), Math.min(255, buffer.get(2) % 256*2));
    }

    @CheckReturnValue
    public static boolean pushScissorTranslated(GuiGraphics gfx, int x, int y, int width, int height) {
        return pushScissorTranslated(gfx.pose(), x, y, width, height);
    }

    public static class Scissor {
        public int x;
        public int y;
        public int width;
        public int height;

        Scissor(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
}
