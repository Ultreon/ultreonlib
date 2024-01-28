package com.ultreon.mods.lib.client.gui;

import com.google.common.base.Preconditions;
import com.ultreon.mods.lib.client.gui.screen.window.TitleStyle;
import com.ultreon.mods.lib.client.theme.GlobalTheme;
import com.ultreon.mods.lib.commons.Color;
import com.ultreon.mods.lib.util.ScissorStack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;

import java.awt.*;
import java.util.List;

public class GuiRenderer {
    private final GuiGraphics gfx;
    private final GlobalTheme theme;
    private final TitleStyle titleStyle;
    private final int[] tmp4 = new int[4];

    public GuiRenderer(GuiGraphics gfx, GlobalTheme theme, TitleStyle titleStyle) {
        this.gfx = gfx;
        this.theme = theme;
        this.titleStyle = titleStyle;
    }

    public void renderContentFrame(int x, int y, int width, int height, FrameType type) {
        var tex = theme.getContentTheme().getFrameSprite();
        this.gfx.blitSprite(type.mapSprite(tex), x, y, width, height);
    }

    public void renderTitleFrame(int x, int y, int width, int height, FrameType type) {
        var tex = theme.getWindowTheme().getFrameSprite();
        this.gfx.blitSprite(type.mapSprite(tex), x, y, width, height);
    }

    public void renderMenuFrame(int x, int y, int width, int height, FrameType type) {
        var tex = theme.getMenuTheme().getFrameSprite();
        this.gfx.blitSprite(type.mapSprite(tex), x, y, width, height);
    }

    public void renderWindow(int x, int y, int width, int height, Component title) {
        this.titleStyle.renderFrame(this, x, y, width, height, theme, title);
    }

    public TitleStyle getTitleStyle() {
        return titleStyle;
    }

    public GuiGraphics gfx() {
        return gfx;
    }

    public Font font() {
        return Minecraft.getInstance().font;
    }

    public void renderTooltip(Component message, int mouseX, int mouseY) {
        this.gfx.renderTooltip(font(), message, mouseX, mouseY);
    }

    public void renderTooltip(List<Component> lines, int mouseX, int mouseY) {
        this.gfx.renderComponentTooltip(font(), lines, mouseX, mouseY);
    }

    public void renderTooltip(Tooltip tooltip, int mouseX, int mouseY) {
        this.gfx.renderTooltip(font(), tooltip.toCharSequence(Minecraft.getInstance()), mouseX, mouseY);
    }

    public void textLeft(String text, int x, int y, int color) {
        gfx.drawString(font(), text, x, y, color, false);
    }

    public void textLeft(Component text, int x, int y, int color) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font(), formattedCharSequence, x, y, color, false);
    }

    public void textLeft(String text, int x, int y, int color, boolean shadow) {
        gfx.drawString(font(), text, x, y, color, shadow);
    }

    public void textLeft(Component text, int x, int y, int color, boolean shadow) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font(), formattedCharSequence, x, y, color, shadow);
    }

    public void textLeft(String text, int x, int y, Color color) {
        gfx.drawString(font(), text, x, y, color.getRgb(), false);
    }

    public void textLeft(Component text, int x, int y, Color color) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font(), formattedCharSequence, x, y, color.getRgb(), false);
    }

    public void textLeft(String text, int x, int y, Color color, boolean shadow) {
        gfx.drawString(font(), text, x, y, color.getRgb(), shadow);
    }

    public void textLeft(Component text, int x, int y, Color color, boolean shadow) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font(), formattedCharSequence, x, y, color.getRgb(), shadow);
    }

    public void textCenter(String text, int x, int y, int color) {
        gfx.drawString(font(), text, x - font().width(text) / 2, y, color, true);
    }

    public void textCenter(Component text, int x, int y, int color) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font(), formattedCharSequence, x - font().width(formattedCharSequence) / 2, y, color, true);
    }

    public void textCenter(String text, int x, int y, int color, boolean shadow) {
        gfx.drawString(font(), text, x - font().width(text) / 2, y, color, shadow);
    }

    public void textCenter(Component text, int x, int y, int color, boolean shadow) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font(), formattedCharSequence, x - font().width(formattedCharSequence) / 2, y, color, shadow);
    }

    public void textCenter(String text, int x, int y, Color color) {
        gfx.drawString(font(), text, x - font().width(text) / 2, y, color.getRgb(), true);
    }

    public void textCenter(Component text, int x, int y, Color color) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font(), formattedCharSequence, x - font().width(formattedCharSequence) / 2, y, color.getRgb(), true);
    }

    public void textCenter(String text, int x, int y, Color color, boolean shadow) {
        gfx.drawString(font(), text, x - font().width(text) / 2, y, color.getRgb(), shadow);
    }

    public void textCenter(Component text, int x, int y, Color color, boolean shadow) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font(), formattedCharSequence, x - font().width(formattedCharSequence) / 2, y, color.getRgb(), shadow);
    }

    public void textRight(String text, int x, int y, int color) {
        gfx.drawString(font(), text, x - font().width(text), y, color, false);
    }

    public void textRight(Component text, int x, int y, int color) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font(), formattedCharSequence, x - font().width(formattedCharSequence), y, color, false);
    }

    public void textRight(String text, int x, int y, int color, boolean shadow) {
        gfx.drawString(font(), text, x - font().width(text), y, color, shadow);
    }

    public void textRight(Component text, int x, int y, int color, boolean shadow) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font(), formattedCharSequence, x - font().width(formattedCharSequence), y, color, shadow);
    }

    public void textRight(String text, int x, int y, Color color) {
        gfx.drawString(font(), text, x - font().width(text), y, color.getRgb(), false);
    }

    public void textRight(Component text, int x, int y, Color color) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font(), formattedCharSequence, x - font().width(formattedCharSequence), y, color.getRgb(), false);
    }

    public void textRight(String text, int x, int y, Color color, boolean shadow) {
        gfx.drawString(font(), text, x - font().width(text), y, color.getRgb(), shadow);
    }

    public void textRight(Component text, int x, int y, Color color, boolean shadow) {
        FormattedCharSequence formattedCharSequence = text.getVisualOrderText();
        gfx.drawString(font(), formattedCharSequence, x - font().width(formattedCharSequence), y, color.getRgb(), shadow);
    }

    public void fill(int x, int y, int x1, int y1, Color color) {
        gfx.fill(x, y, x1, y1, color.getRgb());
    }

    public void fill(int x, int y, int x1, int y1, int color) {
        gfx.fill(x, y, x1, y1, color);
    }

    public void filledRect(int x, int y, int width, int height, Color color) {
        gfx.fill(x, y, x + width, y + height, color.getRgb());
    }

    public void filledRect(int x, int y, int width, int height, int color) {
        gfx.fill(x, y, x + width, y + height, color);
    }

    public void scissor(int x, int y, int width, int height, Runnable runnable) {
        if (ScissorStack.pushScissorTranslated(gfx, x, y, width, height)) {
            runnable.run();
            ScissorStack.popScissor();
        }
    }

    public boolean pushScissor(int x, int y, int width, int height) {
        return ScissorStack.pushScissorTranslated(gfx, x, y, width, height);
    }

    public ScissorStack.Scissor popScissor() {
        return ScissorStack.popScissor();
    }


    public void drawCenteredStringWithoutShadow(FormattedCharSequence text, int x, int y, int color) {
        gfx.drawString(font(), text, x - font().width(text) / 2, y, color, false);
    }

    public void renderScrollingString(Component component, int x1, int y1, int x2, int y2, int color) {
        renderScrollingString(component, (x1 + x2) / 2, x1, y1, x2, y2, color);
    }

    public void renderScrollingString(Component component, int startOffset, int x1, int y1, int x2, int y2, int color) {
        int textWidth = font().width(component);
        Preconditions.checkNotNull(font(), "font");
        int textY = ((y1 + y2) - 9) / 2 + 1;
        int maxWidth = x2 - x1;
        int textX;
        if (textWidth > maxWidth) {
            textX = textWidth - maxWidth;
            double seconds = (double) Util.getMillis() / 1000.0;
            double easing = Math.max((double) textX * 0.5, 3.0);
            double delta = Math.sin(1.5707963267948966 * Math.cos(6.283185307179586 * seconds / easing)) / 2.0 + 0.5;
            double finalX = Mth.lerp(delta, 0.0, textX);

            if (ScissorStack.pushScissorTranslated(gfx, x1, y1, maxWidth, y2 - y1)) {
                gfx.drawString(font(), component, x1 - (int) finalX, textY, color);
                ScissorStack.popScissor();
            }
        } else {
            textX = Mth.clamp(startOffset, x1 + textWidth / 2, x2 - textWidth / 2);
            gfx.drawCenteredString(font(), component, textX, textY, color);
        }
    }

    public void box(int x, int y, int width, int height, int rgb) {
        gfx.renderOutline(x, y, width, height, rgb);
    }

    public void blitSprite(ResourceLocation searchSprite, int x, int y, int width, int height) {
        gfx.blitSprite(searchSprite, x, y, width, height);
    }

    public void blitSprite(ResourceLocation barProgress, int width, int height, int u, int v, int x, int y, int uvW, int uvH) {
        gfx.blitSprite(barProgress, width, height, u, v, x, y, uvW, uvH);
    }

    public void pushPose() {
        gfx.pose().pushPose();
    }

    public void translate(int x, int y, int z) {
        gfx.pose().translate(x, y, z);
    }

    public void translate(float x, float y, float z) {
        gfx.pose().translate(x, y, z);
    }

    public void translate(double x, double y, double z) {
        gfx.pose().translate(x, y, z);
    }

    public void scale(int x, int y, int z) {
        gfx.pose().scale(x, y, z);
    }

    public void scale(float x, float y, float z) {
        gfx.pose().scale(x, y, z);
    }

    public void rotate(float angle, float x, float y, float z) {
        gfx.pose().mulPose(new Quaternionf(x, y, z, angle));
    }

    public void rotate(float angle, double x, double y, double z) {
        gfx.pose().mulPose(new Quaternionf(x, y, z, angle));
    }

    public void rotateX(float angle) {
        gfx.pose().mulPose(new Quaternionf(1, 0, 0, angle));
    }

    public void rotateY(float angle) {
        gfx.pose().mulPose(new Quaternionf(0, 1, 0, angle));
    }

    public void rotateZ(float angle) {
        gfx.pose().mulPose(new Quaternionf(0, 0, 1, angle));
    }

    public void rotateX(double angle) {
        gfx.pose().mulPose(new Quaternionf(1, 0, 0, angle));
    }

    public void rotateY(double angle) {
        gfx.pose().mulPose(new Quaternionf(0, 1, 0, angle));
    }

    public void rotateZ(double angle) {
        gfx.pose().mulPose(new Quaternionf(0, 0, 1, angle));
    }

    public void rotate(Vector3f axis, float angle) {
        gfx.pose().mulPose(new Quaternionf(axis.x, axis.y, axis.z, angle));
    }

    public void rotate(Vector3f axis, double angle) {
        gfx.pose().mulPose(new Quaternionf(axis.x, axis.y, axis.z, angle));
    }

    public void rotate(Vector3d axis, float angle) {
        gfx.pose().mulPose(new Quaternionf(axis.x, axis.y, axis.z, angle));
    }

    public void rotate(Vector3d axis, double angle) {
        gfx.pose().mulPose(new Quaternionf(axis.x, axis.y, axis.z, angle));
    }

    public void rotate(Quaternionf quaternion) {
        gfx.pose().mulPose(quaternion);
    }

    public void rotateXYZ(float x, float y, float z) {
        this.rotateX(x);
        this.rotateY(y);
        this.rotateZ(z);
    }

    public void rotateXYZ(double x, double y, double z) {
        this.rotateX(x);
        this.rotateY(y);
        this.rotateZ(z);
    }

    public void rotateXYZ(Vector3f xyz) {
        this.rotateX(xyz.x);
        this.rotateY(xyz.y);
        this.rotateZ(xyz.z);
    }

    public void rotateXYZ(Vector3d xyz) {
        this.rotateX(xyz.x);
        this.rotateY(xyz.y);
        this.rotateZ(xyz.z);
    }

    public void rotateZYX(float x, float y, float z) {
        this.rotateZ(z);
        this.rotateY(y);
        this.rotateX(x);
    }

    public void rotateZYX(double x, double y, double z) {
        this.rotateZ(z);
        this.rotateY(y);
        this.rotateX(x);
    }

    public void rotateZXY(float x, float y, float z) {
        this.rotateZ(z);
        this.rotateX(x);
        this.rotateY(y);
    }

    public void rotateZXY(double x, double y, double z) {
        this.rotateZ(z);
        this.rotateX(x);
        this.rotateY(y);
    }

    public void rotateYXZ(float x, float y, float z) {
        this.rotateY(y);
        this.rotateX(x);
        this.rotateZ(z);
    }

    public void rotateYXZ(double x, double y, double z) {
        this.rotateY(y);
        this.rotateX(x);
        this.rotateZ(z);
    }

    public void rotateXZY(float x, float y, float z) {
        this.rotateX(x);
        this.rotateZ(z);
        this.rotateY(y);
    }

    public void rotateXZY(double x, double y, double z) {
        this.rotateX(x);
        this.rotateZ(z);
        this.rotateY(y);
    }

    public void rotateXY(float x, float y) {
        this.rotateX(x);
        this.rotateY(y);
    }

    public void rotateXY(double x, double y) {
        this.rotateX(x);
        this.rotateY(y);
    }

    public void rotateYX(float x, float y) {
        this.rotateY(y);
        this.rotateX(x);
    }

    public void rotateYX(double x, double y) {
        this.rotateY(y);
        this.rotateX(x);
    }

    public Vector3f transform(float x, float y, float z, Vector3f tmp) {
        GL20.glGetIntegerv(GL20.GL_VIEWPORT, tmp4);
        return gfx.pose().last().pose().unproject(x, y, z, tmp4, tmp);
    }

    public void popPose() {
        gfx.pose().popPose();
    }

    public void blit(ResourceLocation texture, int x, int y, int width, int height) {
        blit(texture, x, y, width, height, 0, 0);
    }

    public void blit(ResourceLocation texture, Rectangle region) {
        blit(texture, region.x, region.y, region.width, region.height);
    }

    public void blit(ResourceLocation texture, int x, int y, int width, int height, int u, int v) {
        gfx.blit(texture, x, y, width, height, u, v);
    }

    public void blit(ResourceLocation texture, int x, int y, int width, int height, int u, int v, int uvWidth, int uvHeight) {
        gfx.blit(texture, x, y, width, height, u, v, uvWidth, uvHeight);
    }

    public void blit(ResourceLocation texture, int x, int y, int width, int height, int u, int v, int uvWidth, int uvHeight, int textureWidth, int textureHeight) {
        gfx.blit(texture, x, y, width, height, u, v, uvWidth, uvHeight, textureWidth, textureHeight);
    }

    public void setColor(float r, float g, float b, float a) {
        gfx.setColor(r, g, b, a);
    }

    public void setColor(Color color) {
        gfx.setColor(color.getRed(), color.getRed(), color.getBlue(), color.getAlpha());
    }
}
