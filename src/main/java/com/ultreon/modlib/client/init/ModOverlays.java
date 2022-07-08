package com.ultreon.modlib.client.init;

import com.ultreon.modlib.ModdingLib;
import com.ultreon.modlib.client.overlay.ItemHudOverlay;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;

import java.util.function.Supplier;

public class ModOverlays {
    public static final ItemHudOverlay ITEM_HUD_OVERLAY = register(ModdingLib.res("item_hud_overlay"), ItemHudOverlay::new);

    private static <T extends IIngameOverlay> T register(ResourceLocation res, Supplier<T> overlay) {
        T registering = overlay.get();
        OverlayRegistry.registerOverlayBelow(ForgeIngameGui.HOTBAR_ELEMENT, res.toString(), registering);
        return registering;
    }

    public static void register() {
        // NO-OP
    }
}
