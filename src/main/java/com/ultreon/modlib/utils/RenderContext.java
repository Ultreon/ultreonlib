package com.ultreon.modlib.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings({"unused", "ClassCanBeRecord"})
public final class RenderContext {
    private final ItemRenderer itemRenderer;
    private final PoseStack poseStack;
    private final Font font;

    public RenderContext(ItemRenderer itemRenderer, PoseStack poseStack, Font font) {
        this.itemRenderer = itemRenderer;
        this.poseStack = poseStack;
        this.font = font;
    }

    public void blit(int x, int y, int blitOffset, int width, int height, TextureAtlasSprite sprite) {
        AbstractContainerScreen.blit(poseStack, x, y, blitOffset, width, height, sprite);
    }

    public void blit(int x, int y, int blitOffset, float uOffset, float vOffset, int uWidth, int vHeight, int textureHeight, int textureWidth) {
        AbstractContainerScreen.blit(poseStack, x, y, blitOffset, uOffset, vOffset, uWidth, vHeight, textureHeight, textureWidth);
    }

    public void blit(int x, int y, int width, int height, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        AbstractContainerScreen.blit(poseStack, x, y, width, height, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
    }

    public void blit(int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        AbstractContainerScreen.blit(poseStack, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        drawCenteredString(text, x, y, color, false);
    }

    public void drawCenteredString(String text, float x, float y, int color, boolean shadow) {
        if (shadow) {
            font.drawShadow(poseStack, text, x - (int) ((float) font.width(text) / 2), y, color);
        } else {
            font.draw(poseStack, text, x - (int) ((float) font.width(text) / 2), y, color);
        }
    }

    /**
     * Draws an ItemStack.
     * <p>
     * The z index is increased by 32 (and not decreased afterwards), and the item is then rendered at z=200.
     */
    public void drawItemStack(PoseStack pose, ItemStack stack, int x, int y, String altText) {
        pose.translate(0, 0, 32);
        this.itemRenderer.blitOffset = 200.0F;

        Font font = Minecraft.getInstance().font;

        this.itemRenderer.renderAndDecorateItem(stack, x, y);
        this.itemRenderer.renderGuiItemDecorations(font, stack, x, y - (stack.isEmpty() ? 0 : 8), altText);
        this.itemRenderer.blitOffset = 0.0F;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public ItemRenderer getItemRenderer() {
        return itemRenderer;
    }

    public Font getFont() {
        return font;
    }
}
