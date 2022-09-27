package com.ultreon.mods.lib.core;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.mods.lib.core.client.render.Gfx;
import com.ultreon.mods.lib.core.common.interfaces.Renderable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModdingLibrary.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.LAYER) {
            return;
        }

        PoseStack matrixStack = event.getMatrixStack();

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player != null) {
            ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
            Item item = heldItem.getItem();
            if (item instanceof Renderable) {
                ((Renderable) item).render(new Gfx(matrixStack, mc.font));
            }
        }
    }
}
