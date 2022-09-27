package com.ultreon.mods.lib.core.util;

import com.ultreon.mods.lib.core.util.helpers.UtilityClass;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

/**
 * Render utilities.
 *
 * @author MrCrayfish
 */
@SuppressWarnings("unused")
public final class RenderUtils extends UtilityClass {
    public static void scissor(int x, int y, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        int scale = (int) mc.getWindow().getGuiScale();
        GL11.glScissor(x * scale, mc.getWindow().getScreenHeight() - y * scale - height * scale, width * scale, height * scale);
    }

    public static boolean isMouseInArea(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }
}
