package com.ultreon.modlib.utils;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

/**
 * Render utilities.
 *
 * @author MrCrayfish
 */
@SuppressWarnings("unused")
public final class RenderUtils {
    private RenderUtils() {
        throw ExceptionUtil.utilityConstructor();
    }

    public static void scissor(int x, int y, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        int scale = (int) mc.getWindow().getGuiScale();
        GL11.glScissor(x * scale, mc.getWindow().getScreenHeight() - y * scale - height * scale, width * scale, height * scale);
    }

    public static boolean isMouseInArea(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }
}
