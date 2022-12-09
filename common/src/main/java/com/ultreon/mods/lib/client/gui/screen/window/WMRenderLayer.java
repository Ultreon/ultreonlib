package com.ultreon.mods.lib.client.gui.screen.window;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.client.gui.Hud;
import net.minecraft.client.Minecraft;

public class WMRenderLayer extends Hud {
    public WMRenderLayer() {
        super();
    }

    @Override
    public void render(PoseStack poseStack, float partialTick, int width, int height) {
        if (Minecraft.getInstance().screen != null) {
            WindowManager.INSTANCE.renderAllWindows(poseStack, Integer.MAX_VALUE, Integer.MAX_VALUE, partialTick);
        }
    }
}
