package com.ultreon.mods.lib.client.gui.v2;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TaskbarWindow extends McWindow {
    private final int size;

    public TaskbarWindow(int size) {
        super(0, 0, 0, size, Component.empty());
        addOnClosingListener(() -> false);

        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        setWidth(this.minecraft.getWindow().getGuiScaledWidth());
        setHeight(this.size);
        setX(0);
        setY(this.minecraft.getWindow().getGuiScaledHeight() - size);
        setTopMost(true);

        fill(poseStack, 0, this.minecraft.getWindow().getGuiScaledHeight() - this.size,
                this.minecraft.getWindow().getGuiScaledWidth(),
                this.minecraft.getWindow().getGuiScaledHeight(), 0xff111111);

        fill(poseStack, 0, this.minecraft.getWindow().getGuiScaledHeight() - this.size,
                20, this.minecraft.getWindow().getGuiScaledHeight(), 0xff292929);

        this.minecraft.getItemRenderer().renderGuiItem(poseStack, new ItemStack(Items.GRASS_BLOCK), 2, this.minecraft.getWindow().getGuiScaledHeight() - size + 2);
    }
}
