package com.ultreon.mods.lib.client;

import com.ultreon.mods.lib.client.gui.DevPreviewScreen;
import com.ultreon.mods.lib.util.UtilityClass;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;

import java.util.ArrayList;
import java.util.List;

public final class DevPreviewRegistry extends UtilityClass {
    private static final List<Mod> MODS = new ArrayList<>();

    /**
     * Mark the mod with the given id as a "development build".
     *
     * @param modId the id of the mod to register.
     */
    public static void register(String modId) {
        register(Platform.getMod(modId));
    }

    /**
     * Mark the mod as a "development build".
     *
     * @param mod the mod to register.
     */
    public static void register(Mod mod) {
        MODS.add(mod);
    }

    @Environment(EnvType.CLIENT)
    static CompoundEventResult<Screen> onTitleScreen(Screen screen) {
        if (screen instanceof TitleScreen titleScreen && !DevPreviewScreen.isInitialized()) {
            if (MODS.isEmpty()) return CompoundEventResult.pass();
            return CompoundEventResult.interruptTrue(new DevPreviewScreen(MODS, titleScreen));
        }
        return CompoundEventResult.pass();
    }
}
