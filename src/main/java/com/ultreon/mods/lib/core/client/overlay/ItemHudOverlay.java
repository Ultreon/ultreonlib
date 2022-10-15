package com.ultreon.mods.lib.core.client.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.core.client.render.Gfx;
import com.ultreon.mods.lib.core.common.interfaces.Renderable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public final class ItemHudOverlay extends Gui implements IGuiOverlay {
    public ItemHudOverlay() {
        super(Minecraft.getInstance(), Minecraft.getInstance().getItemRenderer());
    }

    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
            Item item = heldItem.getItem();
            if (item instanceof Renderable) {
                ((Renderable) item).render(new Gfx(poseStack, Minecraft.getInstance().font));
            }
        }
    }
}
