package com.ultreon.modlib.listener;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.modlib.UltreonModLib;
import com.ultreon.modlib.common.interfaces.Renderable;
import com.ultreon.modlib.graphics.MCGraphics;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.ParametersAreNonnullByDefault;

@Mod.EventBusSubscriber(modid = UltreonModLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class HudListener {
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
                ((Renderable) item).render(new MCGraphics(matrixStack, mc.font));
            }
        }
    }
}
