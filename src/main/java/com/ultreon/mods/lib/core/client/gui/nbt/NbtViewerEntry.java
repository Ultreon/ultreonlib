package com.ultreon.mods.lib.core.client.gui.nbt;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.core.util.FontRenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public final class NbtViewerEntry extends ObjectSelectionList.Entry<NbtViewerEntry> {
    private final String text;
    private final Minecraft mc;

    public NbtViewerEntry(String text) {
        this.text = text;
        this.mc = Minecraft.getInstance();
    }

    @Override
    public void render(@NotNull PoseStack matrix, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
        FontRenderUtils.renderScaled(matrix, this.mc.font, Component.literal(this.text).getVisualOrderText(), p_230432_4_, p_230432_3_, 1.0f, 0xFFFFFF, true);
    }

    @NotNull
    @Override
    public Component getNarration() {
        return Component.literal(this.text);
    }
}
