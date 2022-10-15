package com.ultreon.mods.lib.gui.client.gui.screen.window;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.ApiStatus;

public class WMRenderLayer extends Gui implements IGuiOverlay {
    public WMRenderLayer(Minecraft minecraft) {
        super(minecraft, minecraft.getItemRenderer());
    }

    @ApiStatus.Internal
    @SubscribeEvent
    public static void register(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("wm_render_layer", new WMRenderLayer(Minecraft.getInstance()));
    }

    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
        if (Minecraft.getInstance().screen != null) {
            WindowManager.INSTANCE.renderAllWindows(poseStack, Integer.MAX_VALUE, Integer.MAX_VALUE, partialTick);
        }
    }
}
