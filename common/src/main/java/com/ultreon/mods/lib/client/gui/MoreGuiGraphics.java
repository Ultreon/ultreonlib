package com.ultreon.mods.lib.client.gui;

import com.ultreon.mods.lib.commons.Color;
import com.ultreon.mods.lib.util.ScissorStack;
import net.minecraft.client.gui.GuiGraphics;

public class MoreGuiGraphics {
    private static final float[] tmp = new float[4];

    public static void setColor(GuiGraphics gfx, Color accentColor) {
        accentColor.toAwt().getRGBComponents(tmp);
        float r = tmp[0];
        float g = tmp[1];
        float b = tmp[2];
        float a = tmp[3];
        gfx.setColor(r, g, b, a);
    }

    public static void subInstance(GuiGraphics gfx, int x, int y, int width, int height, Runnable func) {
        if (ScissorStack.pushScissorTranslated(gfx, x, y, width, height)) {
            gfx.pose().pushPose();
            gfx.pose().translate(x, y, 0);

            func.run();

            gfx.pose().popPose();
            ScissorStack.popScissor();
        }
    }
}
