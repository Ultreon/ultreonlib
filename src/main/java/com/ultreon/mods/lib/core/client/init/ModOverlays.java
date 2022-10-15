package com.ultreon.mods.lib.core.client.init;

import com.ultreon.mods.lib.core.client.overlay.ItemHudOverlay;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModOverlays {
    private static final Map<String, IGuiOverlay> REGISTRY = new HashMap<>();

    public static final ItemHudOverlay ITEM_HUD_OVERLAY = register("item_hud_overlay", ItemHudOverlay::new);

    @ApiStatus.Internal
    private static <T extends IGuiOverlay> T register(String name, Supplier<T> overlay) {
        T gui = overlay.get();
        REGISTRY.put(name, gui);
        return gui;
    }

    private static void handleRegistration(RegisterGuiOverlaysEvent event) {
        for (String key : REGISTRY.keySet()) {
            event.registerBelow(VanillaGuiOverlay.HOTBAR.id(), key, REGISTRY.get(key));
        }
    }

    @ApiStatus.Internal
    public static void register() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModOverlays::handleRegistration);
    }
}
