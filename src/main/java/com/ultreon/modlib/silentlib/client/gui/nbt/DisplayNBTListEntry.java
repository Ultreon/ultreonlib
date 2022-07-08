package com.ultreon.modlib.silentlib.client.gui.nbt;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.modlib.silentlib.util.TextRenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public final class DisplayNBTListEntry extends ObjectSelectionList.Entry<DisplayNBTListEntry> {
    private final String text;
    private final Minecraft mc;

    public DisplayNBTListEntry(String text) {
        this.text = text;
        this.mc = Minecraft.getInstance();
    }

    @Override
    public void render(@NotNull PoseStack matrix, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
        TextRenderUtils.renderScaled(matrix, this.mc.font, new TextComponent(this.text).getVisualOrderText(), p_230432_4_, p_230432_3_, 1.0f, 0xFFFFFF, true);
    }

    @Override
    public Component getNarration() {
        return new TextComponent(this.text);
    }
}
