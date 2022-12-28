package com.ultreon.mods.lib.client.gui.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;

public interface IToolbarItem extends Widget {
    @Override
    void render(PoseStack pose, int mouseX, int mouseY, float partialTicks);

    int width();

    int height();
}
