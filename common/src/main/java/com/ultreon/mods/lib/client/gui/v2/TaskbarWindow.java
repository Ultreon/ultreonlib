package com.ultreon.mods.lib.client.gui.v2;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

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
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        setWidth(this.minecraft.getWindow().getGuiScaledWidth());
        setHeight(this.size);
        setX(0);
        setY(this.minecraft.getWindow().getGuiScaledHeight() - size);
        setTopMost(true);

        gfx.fill(0, this.minecraft.getWindow().getGuiScaledHeight() - this.size,
                this.minecraft.getWindow().getGuiScaledWidth(),
                this.minecraft.getWindow().getGuiScaledHeight(), 0xff111111);

        gfx.fill(0, this.minecraft.getWindow().getGuiScaledHeight() - this.size,
                20, this.minecraft.getWindow().getGuiScaledHeight(), 0xff292929);

        gfx.renderItem(new ItemStack(Items.GRASS_BLOCK), 2, this.minecraft.getWindow().getGuiScaledHeight() - size + 2);
    }
}
